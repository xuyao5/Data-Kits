package io.github.xuyao5.dkl.eskits.service;

import com.google.gson.reflect.TypeToken;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import io.github.xuyao5.dkl.eskits.context.AbstractExecutor;
import io.github.xuyao5.dkl.eskits.schema.base.BaseDocument;
import io.github.xuyao5.dkl.eskits.schema.standard.StandardFileLine;
import io.github.xuyao5.dkl.eskits.service.config.File2EsConfig;
import io.github.xuyao5.dkl.eskits.support.batch.BulkSupporter;
import io.github.xuyao5.dkl.eskits.support.general.ClusterSupporter;
import io.github.xuyao5.dkl.eskits.support.general.IndexSupporter;
import io.github.xuyao5.dkl.eskits.support.mapping.XContentSupporter;
import io.github.xuyao5.dkl.eskits.util.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.LineIterator;
import org.elasticsearch.client.RestHighLevelClient;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.UnaryOperator;

import static io.github.xuyao5.dkl.eskits.util.MyDateUtils.STD_DATE_FORMAT;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 10/10/20 15:41
 * @apiNote File2EsExecutor
 * @implNote File2EsExecutor
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

        int numberOfDataNodes = ClusterSupporter.getInstance().health(client).getNumberOfDataNodes();

        if (!IndexSupporter.getInstance().exists(client, config.getIndex())) {
            IndexSupporter.getInstance().create(client, config.getIndex(), numberOfDataNodes, 0, config.getSortField(), config.getSortOrder(), XContentSupporter.buildMapping(document.newInstance()));
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
                    standardDocument.setDateTag(MyDateUtils.format2Date(STD_DATE_FORMAT));
                    standardDocument.setSerialNo(snowflake.nextId());
                    standardDocument.setRecordMd5(DigestUtils.md5Hex(Arrays.stream(recordArray).collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString()).toUpperCase(Locale.ROOT));
                    standardDocument.setCreateDate(MyDateUtils.now());
                    standardDocument.setModifyDate(standardDocument.getCreateDate());

                    for (int i = 0; i < recordArray.length; i++) {
                        String fieldName = metadataArray[0][i];
                        Serializable obj = MyGsonUtils.json2Obj(recordArray[i], TypeToken.get(MyFieldUtils.getDeclaredField(standardDocument.getClass(), fieldName, true).getType()));
                        if (Objects.nonNull(obj)) {
                            MyFieldUtils.writeDeclaredField(standardDocument, fieldName, obj, true);
                        }
                    }

                    if (standardDocument.is_accept()) {
                        function.apply(BulkSupporter.buildIndexRequest(config.getIndex(), operator.apply(standardDocument)));
                    }
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