package io.github.xuyao5.dkl.eskits.service;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import io.github.xuyao5.dkl.eskits.configuration.File2EsConfig;
import io.github.xuyao5.dkl.eskits.context.AbstractExecutor;
import io.github.xuyao5.dkl.eskits.schema.BaseDocument;
import io.github.xuyao5.dkl.eskits.schema.StandardFileLine;
import io.github.xuyao5.dkl.eskits.support.ClusterSupporter;
import io.github.xuyao5.dkl.eskits.support.IndexSupporter;
import io.github.xuyao5.dkl.eskits.support.batch.BulkSupporter;
import io.github.xuyao5.dkl.eskits.support.mapping.XContentSupporter;
import io.github.xuyao5.dkl.eskits.util.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.LineIterator;
import org.elasticsearch.client.RestHighLevelClient;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.UnaryOperator;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 10/10/20 15:41
 * @apiNote File2EsExecutor
 * @implNote File2Es执行入口
 */
@Slf4j
public final class File2EsExecutor extends AbstractExecutor {

    private final int bulkThreads;

    public File2EsExecutor(@NotNull RestHighLevelClient esClient, int threads) {
        super(esClient);
        bulkThreads = threads;
    }

    public <T extends BaseDocument> void execute(@NotNull File2EsConfig config, EventFactory<T> document, UnaryOperator<T> operator) {
        //检查文件和索引是否存在
        if (!config.getFile().exists()) {
            return;
        }

        //用户自定义格式
        Map<String, Class<?>> declaredFieldsMap = MyFieldUtils.getDeclaredFieldsMap(document.newInstance());

        int numberOfDataNodes = ClusterSupporter.getInstance().health(client).getNumberOfDataNodes();

        if (!IndexSupporter.getInstance().exists(client, config.getIndex())) {
            IndexSupporter.getInstance().create(client, config.getIndex(), numberOfDataNodes, 1, XContentSupporter.buildMapping(declaredFieldsMap));
        }

        String[][] metadataArray = new String[1][];//元数据
        BulkSupporter.getInstance().bulk(client, bulkThreads, function -> {
            final Disruptor<StandardFileLine> disruptor = new Disruptor<>(StandardFileLine::of, RING_SIZE, DaemonThreadFactory.INSTANCE, ProducerType.SINGLE, new BlockingWaitStrategy());

            disruptor.handleEventsWith((standardFileLine, sequence, endOfBatch) -> {
                if (MyStringUtils.isBlank(standardFileLine.getLineRecord()) || MyStringUtils.startsWith(standardFileLine.getLineRecord(), config.getFileComments())) {
                    return;
                }

                String[] recordArray = MyStringUtils.split(standardFileLine.getLineRecord(), config.getRecordSeparator());

                if (standardFileLine.getLineNo() == 1) {
                    metadataArray[0] = Arrays.stream(recordArray).map(MyCaseUtils::toCamelCaseDefault).toArray(String[]::new);
                } else {
                    T standardDocument = document.newInstance();
                    standardDocument.setRecordId(recordArray[config.getIdColumn() - 1]);
                    standardDocument.setDateTag(MyDateUtils.getCurrentDateTag());
                    standardDocument.setSerialNo(snowflake.nextId());
                    standardDocument.setAllFieldMd5(DigestUtils.md5Hex(Arrays.stream(recordArray).collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString()).toUpperCase(Locale.ROOT));
                    standardDocument.setRandomNum(MyRandomUtils.getLong());
                    standardDocument.setCreateDate(MyDateUtils.now());
                    standardDocument.setModifyDate(MyDateUtils.now());

                    for (int i = 0; i < recordArray.length; i++) {
                        String fieldName = metadataArray[0][i];
                        MyGsonUtils.json2Obj(recordArray[i], declaredFieldsMap.get(fieldName)).ifPresent(obj -> {
                            try {
                                MyFieldUtils.writeDeclaredField(standardDocument, fieldName, obj, true);
                            } catch (IllegalAccessException ex) {
                                log.error("数据行[" + standardFileLine.getLineNo() + "]类型解析错误", ex);
                            }
                        });
                    }

                    function.apply(BulkSupporter.buildIndexRequest(config.getIndex(), operator.apply(standardDocument)));
                }
            });

            publish(disruptor, config.getFile(), config.getCharset());
        });
    }

    @SneakyThrows
    private void publish(@NotNull Disruptor<StandardFileLine> disruptor, @NotNull File file, @NotNull Charset charset) {
        LongAdder longAdder = new LongAdder();
        RingBuffer<StandardFileLine> ringBuffer = disruptor.start();
        try (LineIterator lineIterator = MyFileUtils.lineIterator(file, charset.name())) {
            while (lineIterator.hasNext()) {
                longAdder.increment();
                ringBuffer.publishEvent((standardFileLine, sequence, lineNo, lineRecord) -> {
                    standardFileLine.setLineNo(lineNo);
                    standardFileLine.setLineRecord(lineRecord);
                }, longAdder.intValue(), lineIterator.nextLine());
            }
        } finally {
            disruptor.shutdown();
        }
    }
}