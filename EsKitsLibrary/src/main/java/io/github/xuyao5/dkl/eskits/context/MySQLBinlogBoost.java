package io.github.xuyao5.dkl.eskits.context;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import com.github.shyiko.mysql.binlog.event.deserialization.EventDeserializer;
import com.github.shyiko.mysql.binlog.jmx.BinaryLogClientMXBean;
import lombok.Builder;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 7/07/21 22:44
 * @apiNote BinlogBoost
 * @implNote BinlogBoost
 */
@Slf4j
@Builder(builderMethodName = "context", buildMethodName = "create")
public final class MySQLBinlogBoost {

    @Builder.Default
    private final String hostname = "localhost";

    @Builder.Default
    private final int port = 3306;

    @Builder.Default
    private final long timeout = TimeUnit.SECONDS.toMillis(6);

    private final String schema;
    private final String username;
    private final String password;

    @SneakyThrows
    public BinaryLogClientMXBean open() {
        EventDeserializer eventDeserializer = new EventDeserializer();
        eventDeserializer.setCompatibilityMode(
                EventDeserializer.CompatibilityMode.DATE_AND_TIME_AS_LONG
//                EventDeserializer.CompatibilityMode.CHAR_AND_BINARY_AS_BYTE_ARRAY
        );
        final BinaryLogClient client = new BinaryLogClient(hostname, port, schema, username, password);
        client.setEventDeserializer(eventDeserializer);
        client.registerEventListener(event -> {
            final EventType eventType = event.getHeader().getEventType();
            if (EventType.isRowMutation(eventType)) {
                if (EventType.isWrite(eventType)) {
                    WriteRowsEventData data = event.getData();
                    List<Serializable[]> rows = data.getRows();
                    rows.forEach(objs -> {
                        for (Serializable obj : objs) {
                            System.out.println(obj);
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
                        Consumer<RotateEventData> rotateEventConsumer = rotateEventData -> log.info("【ROTATE】binlogFilename={}, binlogPosition={}", rotateEventData.getBinlogFilename(), rotateEventData.getBinlogPosition());
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
                        Consumer<TableMapEventData> tableMapEventConsumer = tableMapEventData -> log.info("【TABLE_MAP】tableId={}, database={}, table={}, columnTypes={}, columnMetadata={}, columnNullability={}, eventMetadata={}", tableMapEventData.getTableId(), tableMapEventData.getDatabase(), tableMapEventData.getTable(), tableMapEventData.getColumnTypes(), tableMapEventData.getColumnMetadata(), tableMapEventData.getColumnNullability(), tableMapEventData.getEventMetadata());
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
        client.connect(timeout);
        return client;
    }

    @SneakyThrows
    public void close(@NonNull BinaryLogClientMXBean bean) {
        if (bean.isConnected()) {
            bean.disconnect();
        }
    }
}