package io.github.xuyao5.dkl.eskits.support.batch;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.ReindexRequest;

import static org.elasticsearch.client.RequestOptions.DEFAULT;
import static org.elasticsearch.index.reindex.AbstractBulkByScrollRequest.AUTO_SLICES;
import static org.elasticsearch.index.reindex.AbstractBulkByScrollRequest.DEFAULT_SCROLL_TIMEOUT;

/**
 * @author Thomas.XU(xuyao)
 * @version 19/02/21 22:05
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ReindexSupporter {

    public static ReindexSupporter getInstance() {
        return ReindexSupporter.SingletonHolder.INSTANCE;
    }

    /**
     * Reindex API
     *
     * @param client           客户端
     * @param queryBuilder     查询构建器
     * @param destinationIndex 目标索引
     * @param scrollSize       批量数
     * @param sourceIndices    源索引
     * @return BulkByScrollResponse
     */
    @SneakyThrows
    public BulkByScrollResponse reindex(@NonNull RestHighLevelClient client, @NonNull QueryBuilder queryBuilder, @NonNull String destinationIndex, int scrollSize, @NonNull String... sourceIndices) {
        ReindexRequest reindexRequest = new ReindexRequest()
                .setSourceIndices(sourceIndices)
                .setDestIndex(destinationIndex)
                .setSourceBatchSize(scrollSize)
                .setSlices(AUTO_SLICES)
                .setScroll(DEFAULT_SCROLL_TIMEOUT)
                .setSourceQuery(queryBuilder);
        reindexRequest.setConflicts("proceed");
        return client.reindex(reindexRequest, DEFAULT);
    }

    /**
     * Reindex API
     *
     * @param client           客户端
     * @param destinationIndex 目标索引
     * @param scrollSize       批量数
     * @param sourceIndices    源索引
     * @return BulkByScrollResponse
     */
    @SneakyThrows
    public BulkByScrollResponse reindex(@NonNull RestHighLevelClient client, @NonNull String destinationIndex, int scrollSize, @NonNull String... sourceIndices) {
        ReindexRequest reindexRequest = new ReindexRequest()
                .setSourceIndices(sourceIndices)
                .setDestIndex(destinationIndex)
                .setDestOpType("create")
                .setSourceBatchSize(scrollSize)
                .setSlices(AUTO_SLICES)
                .setScroll(DEFAULT_SCROLL_TIMEOUT);
        reindexRequest.setConflicts("proceed");
        return client.reindex(reindexRequest, DEFAULT);
    }

    private static class SingletonHolder {
        private static final ReindexSupporter INSTANCE = new ReindexSupporter();
    }
}
