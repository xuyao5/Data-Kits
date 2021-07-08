package io.github.xuyao5.dkl.eskits.listener;

import io.github.xuyao5.dkl.eskits.context.AbstractExecutor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 8/07/21 11:59
 * @apiNote MySQLBinlogListener
 * @implNote MySQLBinlogListener
 */
@Slf4j
public class MySQLBinlogListener extends AbstractExecutor {

    private final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor();

    public MySQLBinlogListener(@NonNull RestHighLevelClient client) {
        super(client);
    }

    public void listen() {

    }
}
