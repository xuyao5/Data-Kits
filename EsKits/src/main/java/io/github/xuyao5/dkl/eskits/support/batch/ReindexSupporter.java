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

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 19/02/21 22:05
 * @apiNote MultiFamilySupporter
 * @implNote MultiFamilySupporter
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
    public BulkByScrollResponse reindex(@NotNull RestHighLevelClient client, @NotNull String destinationIndex, int sourceBatchSize, @NotNull String... sourceIndices) {
        return client.reindex(new ReindexRequest()
                .setSourceIndices(sourceIndices)
                .setDestIndex(destinationIndex)
                .setDestVersionType(VersionType.EXTERNAL)
                .setDestOpType("create")
                .setAbortOnVersionConflict(false)
                .setSourceBatchSize(sourceBatchSize)
                .setSlices(AUTO_SLICES)
                .setScroll(TimeValue.timeValueMinutes(10)), DEFAULT);
    }

    private static class SingletonHolder {
        private static final ReindexSupporter INSTANCE = new ReindexSupporter();
    }
}
