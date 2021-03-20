package io.github.xuyao5.dkl.eskits.service;

import io.github.xuyao5.dkl.eskits.context.AbstractExecutor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 20/03/21 13:12
 * @apiNote StoredSearchExecutor
 * @implNote StoredSearchExecutor
 */
@Slf4j
public final class StoredSearchExecutor extends AbstractExecutor {

    public StoredSearchExecutor(RestHighLevelClient client) {
        super(client);
    }

    //用存储在ES中的代码来搜索
    public void execute() {

    }
}
