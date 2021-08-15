package io.github.xuyao5.dkl.eskits.service;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import com.github.shyiko.mysql.binlog.event.deserialization.EventDeserializer;
import com.github.shyiko.mysql.binlog.jmx.BinaryLogClientMXBean;
import com.google.gson.reflect.TypeToken;
import com.lmax.disruptor.EventFactory;
import io.github.xuyao5.dkl.eskits.context.AbstractExecutor;
import io.github.xuyao5.dkl.eskits.context.annotation.TableField;
import io.github.xuyao5.dkl.eskits.repository.InformationSchemaDao;
import io.github.xuyao5.dkl.eskits.repository.information_schema.Columns;
import io.github.xuyao5.dkl.eskits.schema.base.BaseDocument;
import io.github.xuyao5.dkl.eskits.service.config.MySQL2EsConfig;
import io.github.xuyao5.dkl.eskits.support.general.ClusterSupporter;
import io.github.xuyao5.dkl.eskits.support.general.IndexSupporter;
import io.github.xuyao5.dkl.eskits.support.mapping.XContentSupporter;
import io.github.xuyao5.dkl.eskits.util.DateUtilsPlus;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
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

    private final int bulkThreads;
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
        bulkThreads = threads;
    }

    public MySQL2EsService(@NonNull RestHighLevelClient client, String schema, String username, String password, int threads) {
        this(client, "com.mysql.cj.jdbc.Driver", "localhost", 3306, schema, username, password, threads);
    }

    private Map<String, Map<Long, String>> getTableColumnMap(@NonNull Set<String> tables) {
        InformationSchemaDao informationSchemaDao = new InformationSchemaDao(driver, hostname, port, username, password);
        List<Columns> columnsList = informationSchemaDao.queryColumns(schema, tables.toArray(new String[]{}));
        return tables.stream().collect(Collectors.toMap(Function.identity(), table -> columnsList.stream().filter(col -> col.getTableName().equalsIgnoreCase(table)).collect(Collectors.toMap(Columns::getOrdinalPosition, Columns::getColumnName))));
    }

    @SneakyThrows
    public <T extends BaseDocument> BinaryLogClientMXBean execute(@NonNull MySQL2EsConfig config, @NonNull Map<String, EventFactory<T>> documentFactory, UnaryOperator<T> writeConsumer) {
        final Map<Long, TableMapEventData> tableMap = new ConcurrentHashMap<>();//表元数据
        final Map<String, Class<? extends BaseDocument>> docClassMap = documentFactory.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().newInstance().getClass()));//获取Document Class
        final Map<String, XContentBuilder> contentBuilderMap = docClassMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> XContentSupporter.getInstance().buildMapping(entry.getValue())));//根据Document Class生成ES的Mapping
        final Map<String, List<Field>> fieldsListMap = docClassMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> FieldUtils.getFieldsListWithAnnotation(entry.getValue(), TableField.class)));//获取被@TableField注解的成员
        final Map<String, Map<String, Field>> tableColumnFieldMap = fieldsListMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().stream().collect(Collectors.toMap(field -> field.getDeclaredAnnotation(TableField.class).column(), Function.identity()))));//类型预存
        final Map<String, Map<String, TypeToken<?>>> tableColumnTypeTokenMap = fieldsListMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().stream().collect(Collectors.toMap(field -> field.getDeclaredAnnotation(TableField.class).column(), field -> TypeToken.get(field.getType())))));//类型预存
        final Map<String, Map<Long, String>> tableColumnMap = getTableColumnMap(documentFactory.keySet());

        EventDeserializer eventDeserializer = new EventDeserializer();
        eventDeserializer.setCompatibilityMode(
                EventDeserializer.CompatibilityMode.DATE_AND_TIME_AS_LONG
//                EventDeserializer.CompatibilityMode.CHAR_AND_BINARY_AS_BYTE_ARRAY
        );
        final BinaryLogClient binaryLogClient = new BinaryLogClient(hostname, port, schema, username, password);
        binaryLogClient.setEventDeserializer(eventDeserializer);
        binaryLogClient.registerEventListener(event -> {
            EventType eventType = event.getHeader().getEventType();
            if (EventType.isRowMutation(eventType)) {
                if (EventType.isWrite(eventType)) {
                    WriteRowsEventData data = event.getData();
                    String table = tableMap.get(data.getTableId()).getTable();

                    T document = documentFactory.get(table).newInstance();

                    data.getRows().forEach(objects -> {
                        for (int i = 0; i < objects.length; i++) {
                            String column = tableColumnMap.get(table).get(i + 1L);
                            Field field = tableColumnFieldMap.get(table).get(column);
                            if (StringUtils.isNotEmpty(field.getDeclaredAnnotation(TableField.class).column()) && Objects.nonNull(objects[i])) {
                                try {
                                    FieldUtils.writeField(field, document, objects[i], true);
                                } catch (IllegalAccessException ex) {
                                    log.error("为用户对象赋值错误", ex);
                                }
                            }
                        }
                    });

                    document.setDateTag(DateUtilsPlus.format2Date(STD_DATE_FORMAT));
                    document.setSourceTag(FilenameUtils.getBaseName(table));

                    //检查索引是否存在
                    IndexSupporter indexSupporter = IndexSupporter.getInstance();
                    final boolean isIndexExist = indexSupporter.exists(client, table.toLowerCase(Locale.ROOT));
                    if (!isIndexExist) {
                        Map<String, String> indexSorting = fieldsListMap.get(table).stream()
                                .filter(field -> field.getDeclaredAnnotation(TableField.class).priority() > 0)
                                .sorted(Comparator.comparing(field -> field.getDeclaredAnnotation(TableField.class).priority()))
                                .collect(Collectors.toMap(Field::getName, field -> field.getDeclaredAnnotation(TableField.class).order().getOrder(), (o1, o2) -> null, LinkedHashMap::new));
                        int numberOfDataNodes = ClusterSupporter.getInstance().health(client).getNumberOfDataNodes();
                        log.info("ES服务器数据节点数为:[{}]", numberOfDataNodes);
                        if (!indexSorting.isEmpty()) {
                            indexSupporter.create(client, table.toLowerCase(Locale.ROOT), numberOfDataNodes, 0, contentBuilderMap.get(table), indexSorting);
                        } else {
                            indexSupporter.create(client, table.toLowerCase(Locale.ROOT), numberOfDataNodes, 0, contentBuilderMap.get(table));
                        }
                    } else {
                        indexSupporter.putMapping(client, contentBuilderMap.get(table), table.toLowerCase(Locale.ROOT));
                    }
                    writeConsumer.apply(document);
                }

                if (EventType.isDelete(eventType)) {
                    DeleteRowsEventData data = event.getData();
                    String table = tableMap.get(data.getTableId()).getTable();

                    data.getRows().forEach(objects -> {
                        for (int i = 0; i < objects.length; i++) {
                            String column = tableColumnMap.get(table).get(i + 1L);
                            log.warn("列{}删除数据{}", column, objects[i]);
                        }
                    });
                }

                if (EventType.isUpdate(eventType)) {
                    UpdateRowsEventData data = event.getData();
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
                        rotateEventConsumer.accept(event.getData());
                        break;
                    case FORMAT_DESCRIPTION:
                        Consumer<FormatDescriptionEventData> formatDescriptionEventConsumer = formatDescriptionEventData -> log.info("【FORMAT_DESCRIPTION】binlogVersion=[{}], serverVersion={}, headerLength={}, dataLength={}, checksumType={}", formatDescriptionEventData.getBinlogVersion(), formatDescriptionEventData.getServerVersion(), formatDescriptionEventData.getHeaderLength(), formatDescriptionEventData.getDataLength(), formatDescriptionEventData.getChecksumType());
                        formatDescriptionEventConsumer.accept(event.getData());
                        break;
                    case QUERY:
                        Consumer<QueryEventData> queryEventConsumer = queryEventData -> log.info("【QUERY】threadId={},executionTime={}, errorCode={}, database={}, sql={}", queryEventData.getThreadId(), queryEventData.getExecutionTime(), queryEventData.getErrorCode(), queryEventData.getDatabase(), queryEventData.getSql());
                        queryEventConsumer.accept(event.getData());
                        break;
                    case TABLE_MAP:
                        Consumer<TableMapEventData> tableMapEventConsumer = tableMapEventData -> {
                            log.info("【TABLE_MAP】tableId={}, database={}, tables={}, columnTypes={}, columnMetadata={}, columnNullability={}, eventMetadata={}", tableMapEventData.getTableId(), tableMapEventData.getDatabase(), tableMapEventData.getTable(), tableMapEventData.getColumnTypes(), tableMapEventData.getColumnMetadata(), tableMapEventData.getColumnNullability(), tableMapEventData.getEventMetadata());
                            tableMap.put(tableMapEventData.getTableId(), tableMapEventData);
                        };
                        tableMapEventConsumer.accept(event.getData());
                        break;
                    case XID:
                        Consumer<XidEventData> xidEventConsumer = xidEventData -> log.info("【XID】xid={}", xidEventData.getXid());
                        xidEventConsumer.accept(event.getData());
                        break;
                    case ANONYMOUS_GTID:
                        log.info("【ANONYMOUS_GTID】ANONYMOUS_GTID={}", event);
                        break;
                    default:
                        log.warn("当前版本暂不支持本事件={}", event);
                        break;
                }
            }
        });
        binaryLogClient.connect(TimeUnit.SECONDS.toMillis(6));
        return binaryLogClient;
    }

    @SneakyThrows
    public void close(@NonNull BinaryLogClientMXBean bean) {
        if (bean.isConnected()) {
            bean.disconnect();
        }
    }

    private void rotateEventHandler(RotateEventData rotateEventData) {
        log.info("【ROTATE】binlogFilename={}, binlogPosition={}", rotateEventData.getBinlogFilename(), rotateEventData.getBinlogPosition());
    }
}
