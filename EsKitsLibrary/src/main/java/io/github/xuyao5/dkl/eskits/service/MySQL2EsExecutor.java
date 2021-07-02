package io.github.xuyao5.dkl.eskits.service;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.deserialization.EventDeserializer;
import com.github.shyiko.mysql.binlog.jmx.BinaryLogClientMXBean;
import io.github.xuyao5.dkl.eskits.context.AbstractExecutor;
import io.github.xuyao5.dkl.eskits.service.config.MySQL2EsConfig;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 16/06/21 23:31
 * @apiNote MySQL2EsExecutor
 * @implNote MySQL2EsExecutor
 */
@Slf4j
public final class MySQL2EsExecutor extends AbstractExecutor {

    private final String hostname;
    private final int port;
    private final String schema;
    private final String username;
    private final String password;

    public MySQL2EsExecutor(@NonNull RestHighLevelClient client, @NonNull String hostname, int port, @NonNull String schema, @NonNull String username, @NonNull String password) {
        super(client);
        this.hostname = hostname;
        this.port = port;
        this.schema = schema;
        this.username = username;
        this.password = password;
    }

    @SneakyThrows
    public BinaryLogClientMXBean listen(@NonNull MySQL2EsConfig config) {
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
        client.connect();
        return client;
    }

    @SneakyThrows
    public void close(@NonNull BinaryLogClientMXBean bean) {
        if (bean.isConnected()) {
            bean.disconnect();
        }
    }
}