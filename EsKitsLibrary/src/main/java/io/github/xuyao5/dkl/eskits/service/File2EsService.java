package io.github.xuyao5.dkl.eskits.service;

import com.google.gson.reflect.TypeToken;
import com.lmax.disruptor.EventFactory;
import io.github.xuyao5.dkl.eskits.context.AbstractExecutor;
import io.github.xuyao5.dkl.eskits.context.DisruptorBoost;
import io.github.xuyao5.dkl.eskits.context.annotation.FileField;
import io.github.xuyao5.dkl.eskits.context.disruptor.EventTwoArg;
import io.github.xuyao5.dkl.eskits.schema.base.BaseDocument;
import io.github.xuyao5.dkl.eskits.schema.standard.StandardFileLine;
import io.github.xuyao5.dkl.eskits.service.config.File2EsConfig;
import io.github.xuyao5.dkl.eskits.support.batch.BulkSupporter;
import io.github.xuyao5.dkl.eskits.support.general.ClusterSupporter;
import io.github.xuyao5.dkl.eskits.support.general.IndexSupporter;
import io.github.xuyao5.dkl.eskits.support.mapping.XContentSupporter;
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
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.lang.reflect.Field;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import static io.github.xuyao5.dkl.eskits.util.DateUtilsPlus.STD_DATE_FORMAT;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 10/10/20 15:41
 * @apiNote File2EsService
 * @implNote File2EsService
 */
@Slf4j
public final class File2EsService extends AbstractExecutor {

    private final int bulkThreads;

    public File2EsService(@NonNull RestHighLevelClient esClient, int threads) {
        super(esClient);
        bulkThreads = threads;
    }

    public <T extends BaseDocument> long execute(@NonNull File2EsConfig config, EventFactory<T> documentFactory, UnaryOperator<T> operator) {
        log.info("File2ES服务输入配置为:[{}]", config);

        //检查文件是否存在
        if (!config.getFile().exists()) {
            log.warn("读取文件不存在输入配置为:[{}]", config);
            return -1;
        }

        //检查索引是否存在
        IndexSupporter indexSupporter = IndexSupporter.getInstance();
        final boolean isIndexExist = indexSupporter.exists(client, config.getIndex());
        log.info("索引是否存在标志为:[{}]", isIndexExist);

        //预存必须数据
        final String[][] metadataArray = new String[1][];//元数据
        final Class<? extends BaseDocument> docClass = documentFactory.newInstance().getClass();//获取Document Class
        final XContentBuilder contentBuilder = XContentSupporter.getInstance().buildMapping(docClass);//根据Document Class生成ES的Mapping
        final List<Field> fieldsList = FieldUtils.getFieldsListWithAnnotation(docClass, FileField.class);//获取被@FileField注解的成员
        final Map<String, Field> columnFieldMap = fieldsList.stream().collect(Collectors.toMap(field -> field.getDeclaredAnnotation(FileField.class).column(), Function.identity()));//类型预存
        final Map<String, TypeToken<?>> columnTypeTokenMap = fieldsList.stream().collect(Collectors.toMap(field -> field.getDeclaredAnnotation(FileField.class).column(), field -> TypeToken.get(field.getType())));//类型预存

        //执行计数器
        final LongAdder count = new LongAdder();
        BulkSupporter.getInstance().bulk(client, bulkThreads, function -> DisruptorBoost.<StandardFileLine>context().create().processTwoArg(consumer -> eventConsumer(config, consumer), (sequence, standardFileLine) -> errorConsumer(config, standardFileLine), StandardFileLine::of, true, (standardFileLine, sequence, endOfBatch) -> {
            if (StringUtils.isBlank(standardFileLine.getLineRecord()) || StringUtils.startsWith(standardFileLine.getLineRecord(), config.getFileComments())) {
                return;
            }

            String[] recordArray = StringUtils.splitPreserveAllTokens(standardFileLine.getLineRecord(), config.getRecordSeparator());

            if (standardFileLine.getLineNo() == 1) {
                metadataArray[0] = Arrays.stream(recordArray).toArray(String[]::new);
                log.info("索引Mapping为:[{}]", Strings.toString(contentBuilder));
                if (!isIndexExist) {
                    Map<String, String> indexSorting = fieldsList.stream()
                            .filter(field -> field.getDeclaredAnnotation(FileField.class).priority() > 0)
                            .sorted(Comparator.comparing(field -> field.getDeclaredAnnotation(FileField.class).priority()))
                            .collect(Collectors.toMap(Field::getName, field -> field.getDeclaredAnnotation(FileField.class).order().getOrder(), (o1, o2) -> null, LinkedHashMap::new));
                    int numberOfDataNodes = ClusterSupporter.getInstance().health(client).getNumberOfDataNodes();
                    log.info("ES服务器数据节点数为:[{}]", numberOfDataNodes);
                    if (!indexSorting.isEmpty()) {
                        indexSupporter.create(client, config.getIndex(), numberOfDataNodes, 0, contentBuilder, indexSorting);
                    } else {
                        indexSupporter.create(client, config.getIndex(), numberOfDataNodes, 0, contentBuilder);
                    }
                } else {
                    indexSupporter.putMapping(client, contentBuilder, config.getIndex());
                    indexSupporter.open(client, config.getIndex());
                }
            } else {
                T document = documentFactory.newInstance();

                for (int i = 0; i < metadataArray[0].length; i++) {
                    Field field = columnFieldMap.get(metadataArray[0][i]);
                    if (StringUtils.isNotEmpty(field.getDeclaredAnnotation(FileField.class).column()) && StringUtils.isNotBlank(recordArray[i])) {
                        FieldUtils.writeField(field, document, GsonUtilsPlus.deserialize(recordArray[i], columnTypeTokenMap.get(metadataArray[0][i])), true);
                    }
                }

                document.setDateTag(DateUtilsPlus.format2Date(STD_DATE_FORMAT));
                document.setSourceTag(FilenameUtils.getBaseName(config.getFile().getName()));

                if (!isIndexExist) {
                    document.setCreateDate(DateUtilsPlus.now());
                    document.setSerialNo(snowflake.nextId());
                    function.apply(BulkSupporter.buildIndexRequest(config.getIndex(), recordArray[config.getIdColumn()], operator.apply(document)));
                } else {
                    T updatingDocument = documentFactory.newInstance();
                    BeanUtils.copyProperties(updatingDocument, document);
                    updatingDocument.setModifyDate(DateUtilsPlus.now());
                    document.setCreateDate(DateUtilsPlus.now());
                    document.setSerialNo(snowflake.nextId());
                    function.apply(BulkSupporter.buildUpdateRequest(config.getIndex(), recordArray[config.getIdColumn()], operator.apply(updatingDocument), operator.apply(document)));
                }

                count.increment();
            }
        }));
        return count.longValue();
    }

    @SneakyThrows
    private void errorConsumer(File2EsConfig config, StandardFileLine standardFileLine) {
        FileUtils.writeLines(Paths.get(config.getFile().getCanonicalPath() + ".error").toFile(), config.getCharset().name(), Collections.singletonList(standardFileLine.getLineRecord()), true);
    }

    @SneakyThrows
    private void eventConsumer(File2EsConfig config, EventTwoArg<StandardFileLine> consumer) {
        AtomicInteger lineCount = new AtomicInteger();
        try (LineIterator lineIterator = FileUtils.lineIterator(config.getFile(), config.getCharset().name())) {
            while (lineIterator.hasNext()) {
                consumer.translate((standardFileLine, sequence, lineNo, lineRecord) -> {
                    standardFileLine.setLineNo(lineNo);
                    standardFileLine.setLineRecord(lineRecord);
                }, lineCount.incrementAndGet(), lineIterator.nextLine());
            }
        }
    }
}