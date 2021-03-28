package io.github.xuyao5.dkl.eskits.service;

import io.github.xuyao5.dkl.eskits.configuration.ZeroDowntimeMigrationConfig;
import io.github.xuyao5.dkl.eskits.context.AbstractExecutor;
import io.github.xuyao5.dkl.eskits.support.batch.ReindexSupporter;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;

import javax.validation.constraints.NotNull;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 18/03/21 21:12
 * @apiNote ZeroDowntimeMigrationExecutor
 * @implNote ZeroDowntimeMigrationExecutor
 */
@Slf4j
public final class ZeroDowntimeMigrationExecutor extends AbstractExecutor {

    public ZeroDowntimeMigrationExecutor(RestHighLevelClient client) {
        super(client);
    }

    //用别名和reindex来0停机切换
    public void execute(@NotNull ZeroDowntimeMigrationConfig config) {
        ReindexSupporter instance = ReindexSupporter.getInstance();
//        instance.reindex(client,)
    }
}
