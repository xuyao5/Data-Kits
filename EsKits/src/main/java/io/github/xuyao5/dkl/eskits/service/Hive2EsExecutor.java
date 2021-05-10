package io.github.xuyao5.dkl.eskits.service;

import io.github.xuyao5.dkl.eskits.context.AbstractExecutor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;

import javax.validation.constraints.NotNull;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 5/05/21 14:46
 * @apiNote Hive2EsExecutor
 * @implNote Hive2EsExecutor
 */
@Slf4j
public final class Hive2EsExecutor extends AbstractExecutor {

    public Hive2EsExecutor(@NotNull RestHighLevelClient client) {
        super(client);
    }
}
