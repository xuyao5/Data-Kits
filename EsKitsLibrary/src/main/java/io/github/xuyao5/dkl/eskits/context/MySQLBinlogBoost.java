package io.github.xuyao5.dkl.eskits.context;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.deserialization.EventDeserializer;
import com.github.shyiko.mysql.binlog.jmx.BinaryLogClientMXBean;
import lombok.Builder;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

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
    private final String hostname = "localhost";

    @Builder.Default
    private final int port = 3306;

    @Builder.Default
    private final long timeout = 1000;

    private final String schema;
    private final String username;
    private final String password;

    @SneakyThrows
    public BinaryLogClientMXBean listen() {
        EventDeserializer eventDeserializer = new EventDeserializer();
        eventDeserializer.setCompatibilityMode(
                EventDeserializer.CompatibilityMode.DATE_AND_TIME_AS_LONG,
                EventDeserializer.CompatibilityMode.CHAR_AND_BINARY_AS_BYTE_ARRAY
        );
        // do not deserialize EXT_DELETE_ROWS event data, return it as a byte array
//        eventDeserializer.setEventDataDeserializer(EventType.EXT_DELETE_ROWS, new ByteArrayEventDataDeserializer());
        // skip EXT_WRITE_ROWS event data altogether
//        eventDeserializer.setEventDataDeserializer(EventType.EXT_WRITE_ROWS, new NullEventDataDeserializer());
        // use custom event data deserializer for EXT_DELETE_ROWS
//        eventDeserializer.setEventDataDeserializer(EventType.EXT_DELETE_ROWS, byteArrayInputStream -> null);

        BinaryLogClient client = new BinaryLogClient(hostname, port, schema, username, password);
        client.setEventDeserializer(eventDeserializer);
        client.registerEventListener(event -> {
            System.out.println(event.toString());
            System.out.println("---------------------------------------");
//            EventType eventType = event.getHeader().getEventType();
//            if (EventType.isWrite(eventType)) {
//                WriteRowsEventData writeRowsEventData = event.getData();
//                System.out.println(writeRowsEventData);
//                System.out.println("================================================================================================================");
//            } else if (EventType.isUpdate(eventType)) {
//                UpdateRowsEventData updateRowsEventData = event.getData();
//                System.out.println(updateRowsEventData);
//                System.out.println("================================================================================================================");
//            } else if (EventType.isDelete(eventType)) {
//                DeleteRowsEventData deleteRowsEventData = event.getData();
//                System.out.println(deleteRowsEventData);
//                System.out.println("================================================================================================================");
//            }
//            if (event.getData() instanceof TableMapEventData) {
//                TableMapEventData tableMapEventData = event.getData();
//                String dataBase = tableMapEventData.getDatabase();
//                String tableName = tableMapEventData.getTable();
//                System.out.println(dataBase);
//                System.out.println(tableName);
//            }
            System.out.println("================================================================================================================");

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
