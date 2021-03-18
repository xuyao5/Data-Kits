package io.github.xuyao5.dkl.eskits.service;

import io.github.xuyao5.dkl.eskits.context.AbstractExecutor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 18/03/21 21:12
 * @apiNote MigrateByReindex
 * @implNote MigrateByReindex
 */
@Slf4j
public final class ZeroDowntimeMigration extends AbstractExecutor {

    public ZeroDowntimeMigration(RestHighLevelClient client) {
        super(client);
    }

    //用别名和reindex来0停机切换
    public void execute() {

    }
}
