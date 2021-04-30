package io.github.xuyao5.dkl.eskits.support.batch;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.VersionType;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.ReindexRequest;

import javax.validation.constraints.NotNull;

import static org.elasticsearch.client.RequestOptions.DEFAULT;
import static org.elasticsearch.index.reindex.AbstractBulkByScrollRequest.AUTO_SLICES;
import static org.elasticsearch.index.reindex.AbstractBulkByScrollRequest.DEFAULT_SCROLL_SIZE;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 19/02/21 22:05
 * @apiNote ReindexSupporter
 * @implNote ReindexSupporter
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ReindexSupporter {

    public static final ReindexSupporter getInstance() {
        return ReindexSupporter.SingletonHolder.INSTANCE;
    }

    /**
     * Reindex API
     */
    @SneakyThrows
    public BulkByScrollResponse reindex(@NotNull RestHighLevelClient client, @NotNull String destinationIndex, @NotNull String... sourceIndices) {
        ReindexRequest reindexRequest = new ReindexRequest()
                .setSourceIndices(sourceIndices)
                .setDestIndex(destinationIndex)
                .setDestVersionType(VersionType.EXTERNAL)
                .setDestOpType("create")
                .setSourceBatchSize(DEFAULT_SCROLL_SIZE)
                .setSlices(AUTO_SLICES)
                .setScroll(TimeValue.MINUS_ONE);
        reindexRequest.setConflicts("proceed");
        return client.reindex(reindexRequest, DEFAULT);
    }

    private static class SingletonHolder {
        private static final ReindexSupporter INSTANCE = new ReindexSupporter();
    }
}
