package io.github.xuyao5.dkl.eskits.service;

import io.github.xuyao5.dkl.eskits.configuration.MergeIntoConfig;
import io.github.xuyao5.dkl.eskits.context.AbstractExecutor;
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
public final class MergeIntoExecutor extends AbstractExecutor {

    public MergeIntoExecutor(RestHighLevelClient client) {
        super(client);
    }

    public void execute(@NotNull MergeIntoConfig config) {
    }
}
