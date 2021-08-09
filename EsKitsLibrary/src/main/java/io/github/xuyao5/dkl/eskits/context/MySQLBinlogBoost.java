package io.github.xuyao5.dkl.eskits.context;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import com.github.shyiko.mysql.binlog.event.deserialization.EventDeserializer;
import com.github.shyiko.mysql.binlog.jmx.BinaryLogClientMXBean;
import io.github.xuyao5.dkl.eskits.repository.InformationSchemaDao;
import io.github.xuyao5.dkl.eskits.repository.information_schema.Columns;
import lombok.Builder;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.BitSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 7/07/21 22:44
 * @apiNote MySQLBinlogBoost
 * @implNote MySQLBinlogBoost
 */
@Slf4j
@Builder(builderMethodName = "context", buildMethodName = "create")
public final class MySQLBinlogBoost {

    @Builder.Default
    private String driver = "com.mysql.cj.jdbc.Driver";

    @Builder.Default
    private String hostname = "localhost";

    @Builder.Default
    private int port = 3306;

    private String schema;
    private String username;
    private String password;

    @SneakyThrows
    public BinaryLogClientMXBean open(@NonNull String... tables) {
        Map<Long, TableMapEventData> tableMap = new ConcurrentHashMap<>();
        InformationSchemaDao informationSchemaDao = new InformationSchemaDao(driver, hostname, port, username, password);
        List<Columns> columns = informationSchemaDao.queryColumns(schema, tables);

        EventDeserializer eventDeserializer = new EventDeserializer();
        eventDeserializer.setCompatibilityMode(
                EventDeserializer.CompatibilityMode.DATE_AND_TIME_AS_LONG
//                EventDeserializer.CompatibilityMode.CHAR_AND_BINARY_AS_BYTE_ARRAY
        );
        final BinaryLogClient client = new BinaryLogClient(hostname, port, schema, username, password);
        client.setEventDeserializer(eventDeserializer);
        client.registerEventListener(event -> {
            //遇到特殊事件需要获取一下
            final EventType eventType = event.getHeader().getEventType();
            if (EventType.isRowMutation(eventType)) {
                if (EventType.isWrite(eventType)) {
                    WriteRowsEventData data = event.getData();
                    String table = tableMap.get(data.getTableId()).getTable();
                    Map<Long, String> collect = columns.stream().filter(col -> col.getTableName().equalsIgnoreCase(table)).collect(Collectors.toMap(Columns::getOrdinalPosition, Columns::getColumnName));

                    BitSet includedColumns = data.getIncludedColumns();
                    boolean b = includedColumns.get(0);
                    boolean b1 = includedColumns.get(1);
                    boolean b2 = includedColumns.get(2);
                    boolean b3 = includedColumns.get(3);
                    boolean b4 = includedColumns.get(4);

                    List<Serializable[]> rows = data.getRows();
                    rows.forEach(objs -> {
                        for (int i = 0; i < objs.length; i++) {
                            System.out.println(collect);
                            String s = collect.get(i + 1L);
                            log.warn("列{}插入数据{}", s, objs[i]);
                        }
                    });
                }

                if (EventType.isDelete(eventType)) {
                    DeleteRowsEventData data = event.getData();
                    List<Serializable[]> rows = data.getRows();
                    rows.forEach(System.out::println);
                }

                if (EventType.isUpdate(eventType)) {
                    UpdateRowsEventData data = event.getData();
                    List<Entry<Serializable[], Serializable[]>> rows = data.getRows();
                    rows.forEach(System.out::println);
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
        client.connect(TimeUnit.SECONDS.toMillis(6));
        return client;
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