package io.github.xuyao5.dkl.eskits.service;

import io.github.xuyao5.dkl.eskits.context.AbstractExecutor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 28/03/21 21:34
 * @apiNote JoinSearchExecutor
 * @implNote JoinSearchExecutor
 */
@Slf4j
public final class JoinSearchExecutor extends AbstractExecutor {

    public JoinSearchExecutor(RestHighLevelClient client) {
        super(client);
    }

    public void leftJoin() {

    }

    public void innerJoin() {

    }
}
