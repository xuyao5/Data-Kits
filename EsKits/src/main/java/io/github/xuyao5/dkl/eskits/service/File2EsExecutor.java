package io.github.xuyao5.dkl.eskits.service;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import io.github.xuyao5.dkl.eskits.abstr.AbstractExecutor;
import io.github.xuyao5.dkl.eskits.client.EsClient;
import io.github.xuyao5.dkl.eskits.configuration.File2EsConfig;
import io.github.xuyao5.dkl.eskits.schema.StandardDocument;
import io.github.xuyao5.dkl.eskits.schema.StandardFileLine;
import io.github.xuyao5.dkl.eskits.support.ClusterSupporter;
import io.github.xuyao5.dkl.eskits.support.IndexSupporter;
import io.github.xuyao5.dkl.eskits.support.batch.BulkSupporter;
import io.github.xuyao5.dkl.eskits.support.batch.ReindexSupporter;
import io.github.xuyao5.dkl.eskits.util.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.LineIterator;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 10/10/20 15:41
 * @apiNote File2EsExecutor
 * @implNote File2Es执行入口
 */
@Slf4j
public final class File2EsExecutor extends AbstractExecutor {

    private static final int RING_BUFFER_SIZE = 1 << 10;

    public File2EsExecutor(EsClient esClient) {
        super(esClient);
    }

    public void execute(EventFactory<? extends StandardDocument> document, @NotNull File2EsConfig config) {
        //检查文件和索引是否存在
        if (!config.getFile().exists()) {
            return;
        }

        //用户自定义格式
        Map<String, Class<?>> declaredFieldsMap = MyFieldUtils.getDeclaredFieldsMap(document.newInstance());

        esClient.invokeConsumer(client -> {
            int numberOfDataNodes = new ClusterSupporter(client).health().getNumberOfDataNodes();

            IndexSupporter indexSupporter = new IndexSupporter(client);
            if (!indexSupporter.exists(config.getIndex())) {
                indexSupporter.create(config.getIndex(), numberOfDataNodes, ReindexSupporter.buildMapping(declaredFieldsMap));
            }

            new BulkSupporter(client, config.getBulkSize()).bulk(function -> {
                Disruptor<StandardFileLine> disruptor = new Disruptor<>(StandardFileLine::of, RING_BUFFER_SIZE, DaemonThreadFactory.INSTANCE, ProducerType.SINGLE, new BlockingWaitStrategy());

                String[][] metadataArray = new String[1][];

                disruptor.handleEventsWith((standardFileLine, sequence, endOfBatch) -> {
                    if (MyStringUtils.isBlank(standardFileLine.getLineRecord()) || MyStringUtils.startsWith(standardFileLine.getLineRecord(), config.getFileComments())) {
                        return;
                    }

                    String[] recordArray = MyStringUtils.split(standardFileLine.getLineRecord(), config.getRecordSeparator());

                    if (standardFileLine.getLineNo() == 1) {
                        metadataArray[0] = Arrays.stream(recordArray).map(MyCaseUtils::toCamelCaseDefault).toArray(String[]::new);
                    } else {
                        StandardDocument standardDocument = document.newInstance();
                        standardDocument.setIndex(config.getIndex());
                        standardDocument.setRecordId(recordArray[config.getIdColumn() - 1]);
                        standardDocument.setDateTag(MyDateUtils.getCurrentDateTag());
                        standardDocument.setSerialNo(snowflake.nextId());
                        standardDocument.setAllFieldMd5(DigestUtils.md5Hex(standardFileLine.getLineRecord()).toUpperCase(Locale.ROOT));
                        standardDocument.setRandomNum(MyRandomUtils.getLong());
                        standardDocument.setCreateDate(MyDateUtils.now());
                        standardDocument.setModifyDate(MyDateUtils.now());

                        for (int i = 0; i < recordArray.length; i++) {
                            String fieldName = metadataArray[0][i];
                            GsonUtils.json2Obj(recordArray[i], declaredFieldsMap.get(fieldName)).ifPresent(obj -> {
                                try {
                                    MyFieldUtils.writeDeclaredField(standardDocument, fieldName, obj, true);
                                } catch (IllegalAccessException ex) {
                                    log.error("类型解析错误", ex);
                                }
                            });
                        }

                        if (config.getIdColumn() != 0) {
                            function.apply(BulkSupporter.buildIndexRequest(config.getIndex(), recordArray[config.getIdColumn() - 1], standardDocument));
                        } else {
                            function.apply(BulkSupporter.buildIndexRequest(config.getIndex(), standardDocument));
                        }
                    }
                });

                RingBuffer<StandardFileLine> ringBuffer = disruptor.start();
                LongAdder longAdder = new LongAdder();
                try (LineIterator lineIterator = MyFileUtils.lineIterator(config.getFile(), config.getCharset().name())) {
                    while (lineIterator.hasNext()) {
                        longAdder.increment();
                        ringBuffer.publishEvent((standardFileLine, sequence, lineNo, lineRecord) -> {
                            standardFileLine.setLineNo(lineNo);
                            standardFileLine.setLineRecord(lineRecord);
                            standardFileLine.setEndRecord(!lineIterator.hasNext());
                        }, longAdder.intValue(), lineIterator.nextLine());
                    }
                } catch (IOException ex) {
                    log.error("Read File ERROR", ex);
                } finally {
                    disruptor.shutdown();
                }
            });
        });

//        List<File> decisionFiles = MyFileUtils.getDecisionFiles(task.getFilePath(), task.getFilenameRegex(), task.getFileConfirmRegex());

//        decisionFiles.stream()
//                .filter(file -> Files.exists(Paths.get(MyFilenameUtils.getConfirmFilename(file.getPath(), task.getFilenameSeparator(), task.getFileConfirmPrefix(), task.getFileConfirmSuffix()))))
//                .forEach(file -> {
//                    System.out.println(file);
//                });
    }
}