package io.github.xuyao5.dkl.eskits.service;

import com.google.gson.reflect.TypeToken;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import io.github.xuyao5.dkl.eskits.context.AbstractExecutor;
import io.github.xuyao5.dkl.eskits.context.DisruptorExceptionHandler;
import io.github.xuyao5.dkl.eskits.context.annotation.FileField;
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
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

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

    public File2EsExecutor(@NotNull RestHighLevelClient esClient) {
        this(esClient, 3);
    }

    public <T extends BaseDocument> void execute(@NotNull File2EsConfig config, EventFactory<T> document, UnaryOperator<T> operator) {
        //检查文件和索引是否存在
        if (!config.getFile().exists()) {
            return;
        }

        int numberOfDataNodes = ClusterSupporter.getInstance().health(client).getNumberOfDataNodes();

        IndexSupporter indexSupporter = IndexSupporter.getInstance();
        final boolean isIndexExist = indexSupporter.exists(client, config.getIndex());
        if (!isIndexExist) {
            indexSupporter.create(client, config.getIndex(), numberOfDataNodes, 0, config.getSortField(), config.getSortOrder(), XContentSupporter.buildMapping(document.newInstance()));
        } else {
            indexSupporter.putMapping(client, XContentSupporter.buildMapping(document.newInstance()), config.getIndex());
        }

        final Class<? extends BaseDocument> docClass = document.newInstance().getClass();
        final String[][] metadataArray = new String[1][];//元数据
        final String dateTag = MyDateUtils.format2Date(STD_DATE_FORMAT);//DateTag以开始计算时的Tag为准
        final Map<String, Field> fieldMap = MyFieldUtils.getFieldsListWithAnnotation(docClass, FileField.class).stream().collect(Collectors.toMap(field -> field.getDeclaredAnnotation(FileField.class).value(), Function.identity()));
        final Map<String, TypeToken<?>> typeTokenMap = MyFieldUtils.getFieldsListWithAnnotation(docClass, FileField.class).stream().collect(Collectors.toMap(field -> field.getDeclaredAnnotation(FileField.class).value(), field -> TypeToken.get(field.getType())));

        BulkSupporter.getInstance().bulk(client, bulkThreads, function -> {
            final Disruptor<StandardFileLine> disruptor = new Disruptor<>(StandardFileLine::of, RING_SIZE, DaemonThreadFactory.INSTANCE, ProducerType.SINGLE, new BlockingWaitStrategy());

            disruptor.handleEventsWith((standardFileLine, sequence, endOfBatch) -> {
                if (MyStringUtils.isBlank(standardFileLine.getLineRecord()) || MyStringUtils.startsWith(standardFileLine.getLineRecord(), config.getFileComments())) {
                    return;
                }

                String[] recordArray = MyStringUtils.split(standardFileLine.getLineRecord(), config.getRecordSeparator());

                if (standardFileLine.getLineNo() == 1) {
                    metadataArray[0] = Arrays.stream(recordArray).toArray(String[]::new);
                } else {
                    T standardDocument = document.newInstance();

                    for (int i = 0; i < metadataArray[0].length; i++) {
                        Field field = fieldMap.get(metadataArray[0][i]);
                        if (Objects.nonNull(field)) {
                            MyFieldUtils.writeField(field, standardDocument, MyGsonUtils.deserialize(recordArray[i], typeTokenMap.get(metadataArray[0][i])), true);
                        }
                    }
                    standardDocument.setDateTag(dateTag);
                    standardDocument.setSerialNo(snowflake.nextId());
                    standardDocument.setRecordMd5(DigestUtils.md5Hex(Arrays.stream(recordArray).collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString()).toUpperCase(Locale.ROOT));
                    standardDocument.setCreateDate(MyDateUtils.now());
                    standardDocument.setModifyDate(standardDocument.getCreateDate());

                    if (!isIndexExist) {
                        function.apply(BulkSupporter.buildIndexRequest(config.getIndex(), recordArray[config.getIdColumn()], operator.apply(standardDocument)));
                    } else {
                        function.apply(BulkSupporter.buildUpdateRequest(config.getIndex(), recordArray[config.getIdColumn()], operator.apply(standardDocument), true));
                    }
                }
            });

            disruptor.setDefaultExceptionHandler(new DisruptorExceptionHandler<>());

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