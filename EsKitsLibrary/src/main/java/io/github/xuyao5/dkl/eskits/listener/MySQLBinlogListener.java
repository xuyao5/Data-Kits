package io.github.xuyao5.dkl.eskits.listener;

import com.github.shyiko.mysql.binlog.jmx.BinaryLogClientMXBean;
import io.github.xuyao5.dkl.eskits.context.AbstractExecutor;
import io.github.xuyao5.dkl.eskits.context.BinlogBoost;
import io.github.xuyao5.dkl.eskits.listener.config.MySQLBinlogConfig;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 8/07/21 11:59
 * @apiNote MySQLBinlogListener
 * @implNote MySQLBinlogListener
 */
@Slf4j
public final class MySQLBinlogListener extends AbstractExecutor {

    private final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor();

    public MySQLBinlogListener(@NonNull RestHighLevelClient client) {
        super(client);
    }

    public Future<BinaryLogClientMXBean> listen(@NonNull MySQLBinlogConfig config) {
        return EXECUTOR.submit(() -> BinlogBoost.context()
                .hostname(config.getHostname())
                .schema(config.getSchema())
                .port(config.getPort())
                .username(config.getUsername())
                .password(config.getPassword())
                .create()
                .listen());
    }
}
