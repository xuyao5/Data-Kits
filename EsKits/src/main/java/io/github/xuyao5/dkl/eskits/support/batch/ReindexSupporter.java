package io.github.xuyao5.dkl.eskits.support.batch;

import io.github.xuyao5.dkl.eskits.abstr.AbstractSupporter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
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
public final class ReindexSupporter extends AbstractSupporter {

    public ReindexSupporter(RestHighLevelClient client) {
        super(client);
    }

    @SneakyThrows
    public static XContentBuilder buildMappingContent() {
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.field("dynamic", false);

            builder.startObject("properties");
            {
                builder.startObject("message");
                {
                    builder.field("type", "keyword");
                    builder.field("ignore_above", 256);
                }
                builder.endObject();
            }
            builder.endObject();
        }
        builder.endObject();
        return builder;
    }

    /**
     * Reindex API
     */
    @SneakyThrows
    public BulkByScrollResponse reindex(@NotNull String destinationIndex, int sourceBatchSize, @NotNull String... sourceIndices) {
        return client.reindex(new ReindexRequest()
                .setSourceIndices(sourceIndices)
                .setDestIndex(destinationIndex)
                .setSourceBatchSize(sourceBatchSize), DEFAULT);
    }
}
