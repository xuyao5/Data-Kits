package io.github.xuyao5.dkl.eskits.service;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import com.github.shyiko.mysql.binlog.event.deserialization.EventDeserializer;
import com.google.gson.reflect.TypeToken;
import com.lmax.disruptor.EventFactory;
import io.github.xuyao5.dkl.eskits.context.AbstractExecutor;
import io.github.xuyao5.dkl.eskits.context.DisruptorBoost;
import io.github.xuyao5.dkl.eskits.context.annotation.TableField;
import io.github.xuyao5.dkl.eskits.context.disruptor.EventTwoArg;
import io.github.xuyao5.dkl.eskits.repository.InformationSchemaDao;
import io.github.xuyao5.dkl.eskits.repository.information_schema.Columns;
import io.github.xuyao5.dkl.eskits.schema.base.BaseDocument;
import io.github.xuyao5.dkl.eskits.schema.standard.StandardMySQLRow;
import io.github.xuyao5.dkl.eskits.service.config.MySQL2EsConfig;
import io.github.xuyao5.dkl.eskits.support.general.ClusterSupporter;
import io.github.xuyao5.dkl.eskits.support.general.DocumentSupporter;
import io.github.xuyao5.dkl.eskits.support.general.IndexSupporter;
import io.github.xuyao5.dkl.eskits.support.mapping.XContentSupporter;
import io.github.xuyao5.dkl.eskits.util.DateUtilsPlus;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import static io.github.xuyao5.dkl.eskits.util.DateUtilsPlus.STD_DATE_FORMAT;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 8/07/21 11:59
 * @apiNote MySQL2EsService
 * @implNote MySQL2EsService
 */
@Slf4j
public final class MySQL2EsService extends AbstractExecutor {

//    private final int bulkThreads;
    private final String driver;
    private final String hostname;
    private final int port;
    private final String schema;
    private final String username;
    private final String password;

    public MySQL2EsService(@NonNull RestHighLevelClient client, String driver, String hostname, int port, String schema, String username, String password, int threads) {
        super(client);
        this.driver = driver;
        this.hostname = hostname;
        this.port = port;
        this.schema = schema;
        this.username = username;
        this.password = password;
//        bulkThreads = threads;
    }

    public MySQL2EsService(@NonNull RestHighLevelClient client, String schema, String username, String password, int threads) {
        this(client, "com.mysql.cj.jdbc.Driver", "localhost", 3306, schema, username, password, threads);
    }

    private Map<String, List<Columns>> getTableColumnsMap(@NonNull Set<String> tables) {
        InformationSchemaDao informationSchemaDao = new InformationSchemaDao(driver, hostname, port, username, password);
        List<Columns> columnsList = informationSchemaDao.queryColumns(schema, tables.toArray(new String[]{}));
        return tables.stream().collect(Collectors.toMap(Function.identity(), table -> columnsList.stream().filter(col -> col.getTableName().equals(table)).collect(Collectors.toList())));
    }

    @SneakyThrows
    public <T extends BaseDocument> long execute(@NonNull MySQL2EsConfig config, @NonNull Map<String, EventFactory<T>> documentFactory, UnaryOperator<T> writeConsumer) {
        final Map<Long, TableMapEventData> tableMap = new ConcurrentHashMap<>();//表元数据
        final Map<String, Class<? extends BaseDocument>> docClassMap = documentFactory.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().newInstance().getClass()));//获取Document Class
        final Map<String, XContentBuilder> contentBuilderMap = docClassMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> XContentSupporter.getInstance().buildMapping(entry.getValue())));//根据Document Class生成ES的Mapping
        final Map<String, List<Field>> fieldsListMap = docClassMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> FieldUtils.getFieldsListWithAnnotation(entry.getValue(), TableField.class)));//获取被@TableField注解的成员
        final Map<String, Map<String, Field>> tableColumnFieldMap = fieldsListMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().stream().collect(Collectors.toMap(field -> field.getDeclaredAnnotation(TableField.class).column(), Function.identity()))));//类型预存
        final Map<String, Map<String, TypeToken<?>>> tableColumnTypeTokenMap = fieldsListMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().stream().collect(Collectors.toMap(field -> field.getDeclaredAnnotation(TableField.class).column(), field -> TypeToken.get(field.getType())))));//类型预存
        final Map<String, List<Columns>> tableColumnsMap = getTableColumnsMap(documentFactory.keySet());
        final Map<String, Map<Long, String>> tableColumnMap = tableColumnsMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().stream().collect(Collectors.toMap(Columns::getOrdinalPosition, Columns::getColumnName))));
        Map<String, Map<Long, String>> tablePrimaryMap = tableColumnsMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().stream().filter(columns -> columns.getColumnKey().equalsIgnoreCase("PRI")).collect(Collectors.toMap(Columns::getOrdinalPosition, Columns::getColumnName))));

        //执行计数器
        final LongAdder count = new LongAdder();
        DocumentSupporter documentSupporter = DocumentSupporter.getInstance();
        DisruptorBoost.<StandardMySQLRow>context().create().processTwoArg(consumer -> eventConsumer(config, consumer), (sequence, standardMySQLRow) -> errorConsumer(config, standardMySQLRow), StandardMySQLRow::of, false, (standardMySQLRow, sequence, endOfBatch) -> {
            EventHeaderV4 eventHeader = standardMySQLRow.getEvent().getHeader();
            EventType eventType = eventHeader.getEventType();
            if (EventType.isRowMutation(eventType)) {
                if (EventType.isWrite(eventType)) {
                    WriteRowsEventData data = standardMySQLRow.getEvent().getData();
                    String table = tableMap.get(data.getTableId()).getTable();
                    String index = table.toLowerCase(Locale.ROOT);
                    T document = documentFactory.get(table).newInstance();

                    List<Serializable> pkList = new ArrayList<>();
                    data.getRows().forEach(objects -> {
                        for (int i = 0; i < objects.length; i++) {
                            String column = tableColumnMap.get(table).get(i + 1L);
                            Field field = tableColumnFieldMap.get(table).get(column);
                            if (StringUtils.isNotEmpty(field.getDeclaredAnnotation(TableField.class).column()) && Objects.nonNull(objects[i])) {
                                if (tablePrimaryMap.get(table).containsKey(i + 1L)) {
                                    pkList.add(objects[i]);
                                }
                                try {
                                    FieldUtils.writeField(field, document, objects[i], true);
                                } catch (IllegalAccessException ex) {
                                    log.error("为用户对象赋值错误", ex);
                                }
                            }
                        }
                    });

                    //检查索引是否存在
                    IndexSupporter indexSupporter = IndexSupporter.getInstance();
                    final boolean isIndexExist = indexSupporter.exists(client, index);
                    if (!isIndexExist) {
                        Map<String, String> indexSorting = fieldsListMap.get(table).stream()
                                .filter(field -> field.getDeclaredAnnotation(TableField.class).priority() > 0)
                                .sorted(Comparator.comparing(field -> field.getDeclaredAnnotation(TableField.class).priority()))
                                .collect(Collectors.toMap(Field::getName, field -> field.getDeclaredAnnotation(TableField.class).order().getOrder(), (o1, o2) -> null, LinkedHashMap::new));
                        int numberOfDataNodes = ClusterSupporter.getInstance().health(client).getNumberOfDataNodes();
                        log.info("ES服务器数据节点数为:[{}]", numberOfDataNodes);
                        if (!indexSorting.isEmpty()) {
                            indexSupporter.create(client, index, numberOfDataNodes, 0, contentBuilderMap.get(table), indexSorting);
                        } else {
                            indexSupporter.create(client, index, numberOfDataNodes, 0, contentBuilderMap.get(table));
                        }
                    } else {
                        indexSupporter.putMapping(client, contentBuilderMap.get(table), index);
                    }

                    document.setDateTag(DateUtilsPlus.format2Date(STD_DATE_FORMAT));
                    document.setSourceTag(FilenameUtils.getBaseName(table));

                    if (!isIndexExist) {
                        document.setCreateDate(DateUtilsPlus.now());
                        document.setSerialNo(snowflake.nextId());
                        documentSupporter.index(client, index, StringUtils.join(pkList), writeConsumer.apply(document));
                    } else {
                        T updatingDocument = documentFactory.get(table).newInstance();
                        BeanUtils.copyProperties(updatingDocument, document);
                        updatingDocument.setModifyDate(DateUtilsPlus.now());
                        document.setCreateDate(DateUtilsPlus.now());
                        document.setSerialNo(snowflake.nextId());
                        documentSupporter.update(client, index, StringUtils.join(pkList), writeConsumer.apply(updatingDocument), writeConsumer.apply(document));
                    }
                }

                if (EventType.isDelete(eventType)) {
                    DeleteRowsEventData data = standardMySQLRow.getEvent().getData();
                    String table = tableMap.get(data.getTableId()).getTable();

                    data.getRows().forEach(objects -> {
                        for (int i = 0; i < objects.length; i++) {
                            String column = tableColumnMap.get(table).get(i + 1L);
                            log.warn("列{}删除数据{}", column, objects[i]);
                        }
                    });
                }

                if (EventType.isUpdate(eventType)) {
                    UpdateRowsEventData data = standardMySQLRow.getEvent().getData();
                    String table = tableMap.get(data.getTableId()).getTable();

                    data.getRows().forEach(entry -> {
                        if (entry.getKey().length == entry.getValue().length) {
                            for (int i = 0; i < entry.getKey().length; i++) {
                                String column = tableColumnMap.get(table).get(i + 1L);
                                log.warn("列{}更新前数据{}", column, entry.getKey()[i]);
                                log.warn("列{}更新后数据{}", column, entry.getValue()[i]);
                            }
                        }
                    });
                }
            } else {
                switch (eventType) {
                    case ROTATE:
                        Consumer<RotateEventData> rotateEventConsumer = this::rotateEventHandler;
                        rotateEventConsumer.accept(standardMySQLRow.getEvent().getData());
                        break;
                    case FORMAT_DESCRIPTION:
                        Consumer<FormatDescriptionEventData> formatDescriptionEventConsumer = formatDescriptionEventData -> log.info("【FORMAT_DESCRIPTION】binlogVersion=[{}], serverVersion={}, headerLength={}, dataLength={}, checksumType={}", formatDescriptionEventData.getBinlogVersion(), formatDescriptionEventData.getServerVersion(), formatDescriptionEventData.getHeaderLength(), formatDescriptionEventData.getDataLength(), formatDescriptionEventData.getChecksumType());
                        formatDescriptionEventConsumer.accept(standardMySQLRow.getEvent().getData());
                        break;
                    case QUERY:
                        Consumer<QueryEventData> queryEventConsumer = queryEventData -> log.info("【QUERY】threadId={},executionTime={}, errorCode={}, database={}, sql={}", queryEventData.getThreadId(), queryEventData.getExecutionTime(), queryEventData.getErrorCode(), queryEventData.getDatabase(), queryEventData.getSql());
                        queryEventConsumer.accept(standardMySQLRow.getEvent().getData());
                        break;
                    case TABLE_MAP:
                        Consumer<TableMapEventData> tableMapEventConsumer = tableMapEventData -> {
                            log.info("【TABLE_MAP】tableId={}, database={}, tables={}, columnTypes={}, columnMetadata={}, columnNullability={}, eventMetadata={}", tableMapEventData.getTableId(), tableMapEventData.getDatabase(), tableMapEventData.getTable(), tableMapEventData.getColumnTypes(), tableMapEventData.getColumnMetadata(), tableMapEventData.getColumnNullability(), tableMapEventData.getEventMetadata());
                            tableMap.put(tableMapEventData.getTableId(), tableMapEventData);
                        };
                        tableMapEventConsumer.accept(standardMySQLRow.getEvent().getData());
                        break;
                    case XID:
                        Consumer<XidEventData> xidEventConsumer = xidEventData -> log.info("【XID】xid={}", xidEventData.getXid());
                        xidEventConsumer.accept(standardMySQLRow.getEvent().getData());
                        break;
                    case ANONYMOUS_GTID:
                        log.info("【ANONYMOUS_GTID】ANONYMOUS_GTID={}", standardMySQLRow);
                        break;
                    default:
                        log.warn("当前版本暂不支持本事件={}", standardMySQLRow);
                        break;
                }
            }

            count.increment();
        });

        return count.longValue();
    }

    @SneakyThrows
    private void eventConsumer(MySQL2EsConfig config, EventTwoArg<StandardMySQLRow> consumer) {
        AtomicInteger rowCount = new AtomicInteger();
        EventDeserializer eventDeserializer = new EventDeserializer();
        eventDeserializer.setCompatibilityMode(
                EventDeserializer.CompatibilityMode.DATE_AND_TIME_AS_LONG
//                StringUtils.toEncodedString()
//                EventDeserializer.CompatibilityMode.CHAR_AND_BINARY_AS_BYTE_ARRAY
        );
        BinaryLogClient binaryLogClient = new BinaryLogClient(hostname, port, schema, username, password);
        binaryLogClient.setEventDeserializer(eventDeserializer);
        binaryLogClient.registerEventListener(EVENT -> consumer.translate((standardMySQLRow, sequence, count, event) -> {
            standardMySQLRow.setRowNo(count);
            standardMySQLRow.setEvent(event);
        }, rowCount.incrementAndGet(), EVENT));
        try {
            binaryLogClient.connect(TimeUnit.SECONDS.toMillis(6));
        } catch (IOException | TimeoutException ex) {
            log.error(ex.getLocalizedMessage(), ex);
            if (binaryLogClient.isConnected()) {
                binaryLogClient.disconnect();
            }
        }
    }

    @SneakyThrows
    private void errorConsumer(MySQL2EsConfig config, StandardMySQLRow standardMySQLRow) {
        FileUtils.writeLines(Paths.get("MySQL2Es.error").toFile(), config.getCharset().name(), Collections.singletonList(standardMySQLRow), true);
    }

    private void rotateEventHandler(RotateEventData rotateEventData) {
        log.info("【ROTATE】binlogFilename={}, binlogPosition={}", rotateEventData.getBinlogFilename(), rotateEventData.getBinlogPosition());
    }
}
