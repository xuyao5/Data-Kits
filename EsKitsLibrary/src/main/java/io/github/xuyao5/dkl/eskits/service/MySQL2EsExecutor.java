package io.github.xuyao5.dkl.eskits.service;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.deserialization.EventDeserializer;
import com.github.shyiko.mysql.binlog.jmx.BinaryLogClientMXBean;
import io.github.xuyao5.dkl.eskits.context.AbstractExecutor;
import io.github.xuyao5.dkl.eskits.service.config.MySQL2EsConfig;
import io.github.xuyao5.dkl.eskits.util.MyGsonUtils;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;

import java.util.Objects;

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
        eventDeserializer.setCompatibilityMode(
                EventDeserializer.CompatibilityMode.DATE_AND_TIME_AS_LONG,
                EventDeserializer.CompatibilityMode.CHAR_AND_BINARY_AS_BYTE_ARRAY
        );
        client.setEventDeserializer(eventDeserializer);
        client.registerEventListener(event -> {
            System.out.println("头：" + event.getHeader());
            if (Objects.nonNull(event.getData())) {
                System.out.println("数据：" + MyGsonUtils.obj2Json(event.getData()));
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