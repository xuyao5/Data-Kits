package io.github.xuyao5.dkl.eskits.support.batch;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.ReindexRequest;

import javax.validation.constraints.NotNull;

import static org.elasticsearch.client.RequestOptions.DEFAULT;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 19/02/21 22:05
 * @apiNote MultiFamilySupporter
 * @implNote MultiFamilySupporter
 */
@Slf4j
public final class ReindexSupporter {

    /**
     * Reindex API
     */
    @SneakyThrows
    public BulkByScrollResponse reindex(@NotNull RestHighLevelClient client, @NotNull String destinationIndex, int sourceBatchSize, @NotNull String... sourceIndices) {
        return client.reindex(new ReindexRequest()
                .setSourceIndices(sourceIndices)
                .setDestIndex(destinationIndex)
                .setSourceBatchSize(sourceBatchSize), DEFAULT);
    }
}
