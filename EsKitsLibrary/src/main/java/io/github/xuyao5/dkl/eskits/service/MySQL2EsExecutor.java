package io.github.xuyao5.dkl.eskits.service;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.EventType;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;
import com.github.shyiko.mysql.binlog.event.deserialization.EventDeserializer;
import com.github.shyiko.mysql.binlog.jmx.BinaryLogClientMXBean;
import io.github.xuyao5.dkl.eskits.context.AbstractExecutor;
import io.github.xuyao5.dkl.eskits.service.config.MySQL2EsConfig;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;

import java.util.ArrayList;
import java.util.List;

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
    private final String username;
    private final String password;

    public MySQL2EsExecutor(@NonNull RestHighLevelClient client, @NonNull String hostname, int port, @NonNull String username, @NonNull String password) {
        super(client);
        this.hostname = hostname;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    @SneakyThrows
    public BinaryLogClientMXBean listen(@NonNull MySQL2EsConfig config) {

        BinaryLogClient client = new BinaryLogClient(hostname, port, username, password);
        EventDeserializer eventDeserializer = new EventDeserializer();
//        eventDeserializer.setCompatibilityMode(
//                EventDeserializer.CompatibilityMode.DATE_AND_TIME_AS_LONG,
//                EventDeserializer.CompatibilityMode.CHAR_AND_BINARY_AS_BYTE_ARRAY
//        );
        client.setEventDeserializer(eventDeserializer);
        client.registerEventListener(event -> {
            /*
             * 不计入position更新的事件类型
             * FORMAT_DESCRIPTION类型为binlog起始时间
             * HEARTBEAT为心跳检测事件，不会写入master的binlog，记录该事件的position会导致重启时报错
             */
            List<EventType> excludePositionEventType = new ArrayList<>();
            excludePositionEventType.add(EventType.FORMAT_DESCRIPTION);
            excludePositionEventType.add(EventType.HEARTBEAT);
/*            if (!excludePositionEventType.contains(eventType)) {
                BinlogPositionEntity binlogPositionEntity = new BinlogPositionEntity();
                //处理rotate事件,这里会替换调binlog fileName
                if (event.getHeader().getEventType().equals(EventType.ROTATE)) {
                    RotateEventData rotateEventData = (RotateEventData) event.getData();
                    binlogPositionEntity.setBinlogName(rotateEventData.getBinlogFilename());
                    binlogPositionEntity.setPosition(rotateEventData.getBinlogPosition());
                    binlogPositionEntity.setServerId(event.getHeader().getServerId());
                } else { //统一处理事件对应的binlog position
                    //在Redis中获取获取binlog的position配置
                    binlogPositionEntity = positionHandler.getPosition(syncConfig);
                    EventHeaderV4 eventHeaderV4 = (EventHeaderV4) event.getHeader();
                    binlogPositionEntity.setPosition(eventHeaderV4.getPosition());
                    binlogPositionEntity.setServerId(event.getHeader().getServerId());
                }
                //将最新的配置保存到Redis中
                log.info("保存的数据{}", JSON.toJSONString(binlogPositionEntity));
                positionHandler.savePosition(syncConfig, binlogPositionEntity);

                //todo 解析结果
            }*/

            EventType eventType = event.getHeader().getEventType();
            if (EventType.isWrite(eventType)) {
                WriteRowsEventData writeRowsEventData = event.getData();
            } else if (EventType.isUpdate(eventType)) {
                UpdateRowsEventData updateRowsEventData = event.getData();
            } else if (EventType.isDelete(eventType)) {
                DeleteRowsEventData deleteRowsEventData = event.getData();
            }
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