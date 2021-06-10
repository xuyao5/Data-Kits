package io.github.xuyao5.dkl.eskits.service;

import io.github.xuyao5.dkl.eskits.context.AbstractExecutor;
import io.github.xuyao5.dkl.eskits.service.config.JoinSearchConfig;
import io.github.xuyao5.dkl.eskits.support.batch.ScrollSupporter;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;

import static org.elasticsearch.index.reindex.AbstractBulkByScrollRequest.DEFAULT_SCROLL_SIZE;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 28/03/21 21:34
 * @apiNote JoinSearchExecutor
 * @implNote JoinSearchExecutor
 */
@Slf4j
public final class JoinSearchExecutor extends AbstractExecutor {

    private final int scrollSize;

    public JoinSearchExecutor(RestHighLevelClient client, int size) {
        super(client);
        scrollSize = size;
    }

    public JoinSearchExecutor(RestHighLevelClient client) {
        this(client, DEFAULT_SCROLL_SIZE);
    }

    public void innerJoin(JoinSearchConfig config) {
        ScrollSupporter scrollSupporter = ScrollSupporter.getInstance();
        scrollSupporter.scroll(client, searchHits -> {
            System.out.println(searchHits.length);
        }, null, scrollSize, config.getIndex1());
    }
}
