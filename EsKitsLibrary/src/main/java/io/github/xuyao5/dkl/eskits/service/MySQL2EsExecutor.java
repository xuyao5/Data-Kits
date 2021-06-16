package io.github.xuyao5.dkl.eskits.service;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.deserialization.EventDeserializer;
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

    public MySQL2EsExecutor(RestHighLevelClient client) {
        super(client);
    }

    @SneakyThrows
    public void execute(@NonNull MySQL2EsConfig config) {
        BinaryLogClient client = new BinaryLogClient("hostname", 3306, "username", "password");
        EventDeserializer eventDeserializer = new EventDeserializer();
        eventDeserializer.setCompatibilityMode(
                EventDeserializer.CompatibilityMode.DATE_AND_TIME_AS_LONG,
                EventDeserializer.CompatibilityMode.CHAR_AND_BINARY_AS_BYTE_ARRAY
        );
        client.setEventDeserializer(eventDeserializer);
        client.registerEventListener(event -> {

        });
        client.connect();
    }
}
