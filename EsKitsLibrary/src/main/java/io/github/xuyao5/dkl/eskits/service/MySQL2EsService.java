package io.github.xuyao5.dkl.eskits.service;

import com.github.shyiko.mysql.binlog.jmx.BinaryLogClientMXBean;
import io.github.xuyao5.dkl.eskits.context.AbstractExecutor;
import io.github.xuyao5.dkl.eskits.context.MySQLBinlogBoost;
import io.github.xuyao5.dkl.eskits.service.config.MySQL2EsConfig;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 8/07/21 11:59
 * @apiNote MySQL2EsService
 * @implNote MySQL2EsService
 */
@Slf4j
public final class MySQL2EsService extends AbstractExecutor {

    private final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor();

    public MySQL2EsService(@NonNull RestHighLevelClient client) {
        super(client);
    }

    public Future<BinaryLogClientMXBean> listen(@NonNull MySQL2EsConfig config) {
        return EXECUTOR.submit(() -> MySQLBinlogBoost.context()
                .hostname(config.getHostname())
                .schema(config.getSchema())
                .port(config.getPort())
                .username(config.getUsername())
                .password(config.getPassword())
                .create()
                .open());
    }
}
