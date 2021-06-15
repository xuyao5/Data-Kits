package io.github.xuyao5.dkl.eskits.service;

import com.google.gson.reflect.TypeToken;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import io.github.xuyao5.dkl.eskits.context.AbstractExecutor;
import io.github.xuyao5.dkl.eskits.context.annotation.FileField;
import io.github.xuyao5.dkl.eskits.schema.base.BaseDocument;
import io.github.xuyao5.dkl.eskits.schema.standard.StandardFileLine;
import io.github.xuyao5.dkl.eskits.service.config.File2EsConfig;
import io.github.xuyao5.dkl.eskits.support.batch.BulkSupporter;
import io.github.xuyao5.dkl.eskits.support.general.ClusterSupporter;
import io.github.xuyao5.dkl.eskits.support.general.IndexSupporter;
import io.github.xuyao5.dkl.eskits.support.mapping.XContentSupporter;
import io.github.xuyao5.dkl.eskits.util.*;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.LineIterator;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.collect.List;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
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

    public File2EsExecutor(@NonNull RestHighLevelClient esClient, int threads) {
        super(esClient);
        bulkThreads = threads;
    }

    public <T extends BaseDocument> void execute(@NonNull File2EsConfig config, EventFactory<T> document, UnaryOperator<T> operator) {
        //检查文件和索引是否存在
        if (!config.getFile().exists()) {
            return;
        }

        int numberOfDataNodes = ClusterSupporter.getInstance().health(client).getNumberOfDataNodes();

        Class<? extends BaseDocument> docClass = document.newInstance().getClass();

        IndexSupporter indexSupporter = IndexSupporter.getInstance();
        XContentBuilder xContentBuilder = XContentSupporter.getInstance().buildMapping(docClass);
        boolean isIndexExist = indexSupporter.exists(client, config.getIndex());
        if (!isIndexExist) {
            indexSupporter.create(client, config.getIndex(), numberOfDataNodes, 0, xContentBuilder);
        } else {
            indexSupporter.putMapping(client, xContentBuilder, config.getIndex());
        }

        String[][] metadataArray = new String[1][];//元数据
        Map<String, Field> fieldMap = MyFieldUtils.getFieldsListWithAnnotation(docClass, FileField.class).stream().collect(Collectors.toMap(field -> field.getDeclaredAnnotation(FileField.class).value(), Function.identity()));
        Map<String, TypeToken<?>> typeTokenMap = MyFieldUtils.getFieldsListWithAnnotation(docClass, FileField.class).stream().collect(Collectors.toMap(field -> field.getDeclaredAnnotation(FileField.class).value(), field -> TypeToken.get(field.getType())));

        BulkSupporter.getInstance().bulk(client, bulkThreads, function -> {
            final Disruptor<StandardFileLine> disruptor = new Disruptor<>(StandardFileLine::of, RING_SIZE, DaemonThreadFactory.INSTANCE, ProducerType.SINGLE, new BlockingWaitStrategy());

            disruptor.handleEventsWith((standardFileLine, sequence, endOfBatch) -> {
                if (MyStringUtils.isBlank(standardFileLine.getLineRecord()) || MyStringUtils.startsWith(standardFileLine.getLineRecord(), config.getFileComments())) {
                    return;
                }

                String[] recordArray = MyStringUtils.splitPreserveAllTokens(standardFileLine.getLineRecord(), config.getRecordSeparator());

                if (standardFileLine.getLineNo() == 1) {
                    metadataArray[0] = Arrays.stream(recordArray).toArray(String[]::new);
                } else {
                    T standardDocument = document.newInstance();

                    for (int i = 0; i < metadataArray[0].length; i++) {
                        Field field = fieldMap.get(metadataArray[0][i]);
                        if (Objects.nonNull(field) && MyStringUtils.isNotBlank(recordArray[i])) {
                            MyFieldUtils.writeField(field, standardDocument, MyGsonUtils.deserialize(recordArray[i], typeTokenMap.get(metadataArray[0][i])), true);
                        }
                    }

                    standardDocument.setSerialNo(snowflake.nextId());
                    standardDocument.setDateTag(MyDateUtils.format2Date(STD_DATE_FORMAT));
                    standardDocument.setSourceTag(MyFilenameUtils.getBaseName(config.getFile().getName()));
                    standardDocument.setCreateDate(MyDateUtils.now());
                    standardDocument.setModifyDate(standardDocument.getCreateDate());

                    if (!isIndexExist) {
                        function.apply(BulkSupporter.buildIndexRequest(config.getIndex(), recordArray[config.getIdColumn()], operator.apply(standardDocument)));
                    } else {
                        function.apply(BulkSupporter.buildUpdateRequest(config.getIndex(), recordArray[config.getIdColumn()], operator.apply(standardDocument)));
                    }
                }
            });

            disruptor.setDefaultExceptionHandler(new ExceptionHandler<StandardFileLine>() {
                @SneakyThrows
                @Override
                public void handleEventException(Throwable throwable, long l, StandardFileLine standardFileLine) {
                    log.error(l + "|" + standardFileLine.toString(), throwable);
                    MyFileUtils.writeLines(Paths.get(config.getFile().getCanonicalPath() + ".error").toFile(), config.getCharset().name(), List.of(standardFileLine.getLineRecord()), true);
                }

                @Override
                public void handleOnStartException(Throwable throwable) {
                    log.error("Exception during onStart()", throwable);
                }

                @Override
                public void handleOnShutdownException(Throwable throwable) {
                    log.error("Exception during onShutdown()", throwable);
                }
            });

            publish(disruptor, config.getFile(), config.getCharset());
        });
    }

    @SneakyThrows
    private void publish(@NonNull Disruptor<StandardFileLine> disruptor, @NonNull File file, @NonNull Charset charset) {
        AtomicInteger lineCount = new AtomicInteger();
        RingBuffer<StandardFileLine> ringBuffer = disruptor.start();
        try (LineIterator lineIterator = MyFileUtils.lineIterator(file, charset.name())) {
            while (lineIterator.hasNext()) {
                ringBuffer.publishEvent((standardFileLine, sequence, lineNo, lineRecord) -> {
                    standardFileLine.setLineNo(lineNo);
                    standardFileLine.setLineRecord(lineRecord);
                }, lineCount.incrementAndGet(), lineIterator.nextLine());
            }
        } finally {
            disruptor.shutdown();
            log.info("File2Es读取【{}】共【{}】行记录完成", file.getCanonicalPath(), lineCount.get());
        }
    }
}