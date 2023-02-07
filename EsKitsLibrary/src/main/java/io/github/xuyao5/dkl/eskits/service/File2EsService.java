package io.github.xuyao5.dkl.eskits.service;

import com.lmax.disruptor.EventFactory;
import io.github.xuyao5.dkl.eskits.consts.IndexStatusConst;
import io.github.xuyao5.dkl.eskits.context.AbstractExecutor;
import io.github.xuyao5.dkl.eskits.context.annotation.AutoMappingField;
import io.github.xuyao5.dkl.eskits.context.boost.DisruptorBoost;
import io.github.xuyao5.dkl.eskits.context.translator.TwoArgEventTranslator;
import io.github.xuyao5.dkl.eskits.schema.base.BaseDocument;
import io.github.xuyao5.dkl.eskits.schema.standard.StandardFileLine;
import io.github.xuyao5.dkl.eskits.service.config.File2EsConfig;
import io.github.xuyao5.dkl.eskits.support.auxiliary.CatSupporter;
import io.github.xuyao5.dkl.eskits.support.batch.BulkSupporter;
import io.github.xuyao5.dkl.eskits.support.general.IndexSupporter;
import io.github.xuyao5.dkl.eskits.support.mapping.AutoMappingSupporter;
import io.github.xuyao5.dkl.eskits.util.DateUtilsPlus;
import io.github.xuyao5.dkl.eskits.util.GsonUtilsPlus;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.Strings;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import static io.github.xuyao5.dkl.eskits.util.DateUtilsPlus.STD_DATE_FORMAT;

/**
 * @author Thomas.XU(xuyao)
 * @version 10/10/20 15:41
 */
@Slf4j
public final class File2EsService<T extends BaseDocument> extends AbstractExecutor {

    private final int bulkThreads;

    public File2EsService(@NonNull RestHighLevelClient esClient, int threads) {
        super(esClient);
        bulkThreads = threads;
    }

    public long execute(@NonNull File2EsConfig config, EventFactory<T> documentFactory, UnaryOperator<T> operator) {
        log.info("File2ES服务输入配置为:[{}]", config);

        //检查文件是否存在
        if (!config.getFile().exists() || !config.getFile().isFile()) {
            log.error("无法获取到文件请检查是否文件被意外删除，当前配置为:[{}]", config);
            return -1;
        }

        if (config.getIdColumn() < 0) {
            log.warn("由于ID列号被配置为[{}]，系统将自动分配doc_id，此操作将导致无法用主键列更新索引", config.getIdColumn());
        }

        //预存必须数据
        final Class<? extends BaseDocument> docClass = documentFactory.newInstance().getClass();//获取Document Class
        final List<Field> fieldsList = FieldUtils.getFieldsListWithAnnotation(docClass, AutoMappingField.class);//获取被@AutoMappingField注解的成员

        final Map<String, Field> columnFieldMap = fieldsList.stream().filter(field -> StringUtils.isNotBlank(field.getDeclaredAnnotation(AutoMappingField.class).column())).collect(Collectors.toMap(field -> field.getDeclaredAnnotation(AutoMappingField.class).column(), Function.identity()));//类型预存
        final Map<Integer, Field> positionFieldMap = fieldsList.stream().filter(field -> field.getDeclaredAnnotation(AutoMappingField.class).position() > -1).collect(Collectors.toMap(field -> field.getDeclaredAnnotation(AutoMappingField.class).position(), Function.identity()));//类型预存
        if (!columnFieldMap.isEmpty() && !positionFieldMap.isEmpty()) {
            log.error("文档不能同时设置column和position，列名模式和列号模式不能同时生效，当前文档类型为:[{}]", docClass);
            return -2;
        }

        final String[][] metadataArray = new String[1][positionFieldMap.size()];//元数据

        //检查索引是否存在
        IndexSupporter indexSupporter = IndexSupporter.getInstance();
        boolean isIndexExist = indexSupporter.exists(client, config.getIndex());
        log.info("索引是否存在标志为:[{}],[{}]", config.getIndex(), isIndexExist);

        //自动映射，索引不存在会自动新建索引，索引存在会更新Mapping
        AutoMappingSupporter.getInstance().autoMappingField(client, config.getIndex(), config.getPriShards(), 0, docClass);

        //如果close了就重新OPEN
        if (isIndexExist) {
            CatSupporter.getInstance().getCatIndices(client, config.getIndex()).stream().filter(indices4Cat -> indices4Cat.getIndex().equals(config.getIndex()) && indices4Cat.getStatus().equals(IndexStatusConst.CLOSE.getStatus())).findFirst().ifPresent(indices4Cat -> indexSupporter.open(client, indices4Cat.getIndex()));
        }

        //执行计数器
        final LongAdder count = new LongAdder();
        BulkSupporter.getInstance().bulk(client, bulkThreads,
                //开始执行
                function -> DisruptorBoost.<StandardFileLine>context().defaultBufferSize(config.getBufferSize()).create().processTwoArgEvent(StandardFileLine::of,
                        //生产事件
                        translator -> eventConsumer(config, translator),
                        //异常处理
                        (standardFileLine, sequence) -> errorConsumer(config, standardFileLine),
                        //消费处理
                        (standardFileLine, sequence, endOfBatch) -> {
                            if (StringUtils.isBlank(standardFileLine.getLineRecord()) || StringUtils.startsWith(standardFileLine.getLineRecord(), config.getFileComments())) {
                                return;
                            }

                            String[] recordArray = StringUtils.splitPreserveAllTokens(standardFileLine.getLineRecord(), config.getRecordSeparator());
                            if (standardFileLine.getLineNo() == 1 && !columnFieldMap.isEmpty()) {
                                metadataArray[0] = recordArray;
                                log.info("文件Metadata行：[{}]", Strings.arrayToCommaDelimitedString(metadataArray[0]));
                            } else {
                                T document = documentFactory.newInstance();

                                for (int i = 0; i < metadataArray[0].length; i++) {
                                    Field field = !columnFieldMap.isEmpty() ? columnFieldMap.get(metadataArray[0][i]) : positionFieldMap.get(i);
                                    if (Objects.nonNull(field)) {
                                        if (StringUtils.isNotEmpty(field.getDeclaredAnnotation(AutoMappingField.class).column()) && StringUtils.isNotBlank(recordArray[i])) {
                                            FieldUtils.writeField(field, document, GsonUtilsPlus.deserialize(recordArray[i], field.getType()), true);
                                        }
                                    } else {
                                        log.error("行[{}]使用列名[{}]无法找到映射关系，请检查@AutoMappingField的column属性配置是否正确或检查元数据行是否存在,注:带有BOM的txt/csv文件也会引起此问题", standardFileLine, metadataArray[0][i]);
                                    }
                                }

                                document.setDateTag(DateUtilsPlus.format2Date(STD_DATE_FORMAT));
                                document.setSourceTag(FilenameUtils.getBaseName(config.getFile().getName()));

                                if (!isIndexExist) {
                                    document.setCreateDate(DateUtilsPlus.now());
                                    document.setSerialNo(snowflake.nextId());
                                    if (config.getIdColumn() < 0) {
                                        function.accept(BulkSupporter.buildIndexRequest(config.getIndex(), operator.apply(document)));
                                    } else {
                                        function.accept(BulkSupporter.buildIndexRequest(config.getIndex(), recordArray[config.getIdColumn()], operator.apply(document)));
                                    }
                                } else {
                                    if (config.getIdColumn() < 0) {
                                        document.setCreateDate(DateUtilsPlus.now());
                                        document.setSerialNo(snowflake.nextId());
                                        function.accept(BulkSupporter.buildIndexRequest(config.getIndex(), operator.apply(document)));
                                    } else {
                                        T updatingDocument = documentFactory.newInstance();
                                        BeanUtils.copyProperties(updatingDocument, document);
                                        updatingDocument.setModifyDate(DateUtilsPlus.now());
                                        document.setCreateDate(DateUtilsPlus.now());
                                        document.setSerialNo(snowflake.nextId());
                                        function.accept(BulkSupporter.buildUpdateRequest(config.getIndex(), recordArray[config.getIdColumn()], operator.apply(updatingDocument), operator.apply(document)));
                                    }
                                }

                                count.increment();
                            }
                        }));
        return count.longValue();
    }

    private void errorConsumer(File2EsConfig config, StandardFileLine standardFileLine) {
        try {
            FileUtils.writeLines(Paths.get(config.getFile().getCanonicalPath() + ".error").toFile(), config.getCharset().name(), Collections.singletonList(standardFileLine.getLineRecord()), true);
        } catch (IOException ex) {
            log.error("保存错误数据发生异常", ex);
        }
    }

    @SneakyThrows
    private void eventConsumer(File2EsConfig config, TwoArgEventTranslator<StandardFileLine> translator) {
        AtomicLong lineCount = new AtomicLong();
        try (LineIterator lineIterator = FileUtils.lineIterator(config.getFile(), config.getCharset().name())) {
            while (lineIterator.hasNext()) {
                translator.translate((standardFileLine, sequence, lineNo, lineRecord) -> {
                    standardFileLine.setLineNo(lineNo);
                    standardFileLine.setLineRecord(lineRecord);
                }, lineCount.incrementAndGet(), lineIterator.nextLine());
            }
        }
    }
}