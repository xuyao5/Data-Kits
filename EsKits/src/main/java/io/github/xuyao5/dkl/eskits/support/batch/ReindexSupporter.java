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
import java.util.Date;
import java.util.Map;

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
    public static XContentBuilder buildMapping(@NotNull Map<String, Class<?>> declaredFieldsMap) {
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.field("dynamic", false);

            builder.startObject("properties");
            {
                builder.startObject("recordId");
                {
                    builder.field("type", "keyword");
                    builder.field("ignore_above", 100);
                }
                builder.endObject();

                builder.startObject("dateTag");
                {
                    builder.field("type", "keyword");
                    builder.field("ignore_above", 8);
                }
                builder.endObject();

                builder.startObject("serialNo");
                {
                    builder.field("type", "long");
                }
                builder.endObject();

                builder.startObject("collapse");
                {
                    builder.field("type", "keyword");
                    builder.field("ignore_above", 200);
                }
                builder.endObject();

                builder.startObject("allFieldMd5");
                {
                    builder.field("type", "keyword");
                    builder.field("ignore_above", 32);
                }
                builder.endObject();

                builder.startObject("createDate");
                {
                    builder.field("type", "date");
                }
                builder.endObject();

                builder.startObject("modifyDate");
                {
                    builder.field("type", "date");
                }
                builder.endObject();

                for (Map.Entry<String, Class<?>> entry : declaredFieldsMap.entrySet()) {
                    builder.startObject(entry.getKey());
                    {
                        Class<?> clz = entry.getValue();
                        if (String.class.equals(clz)) {
                            builder.field("type", "keyword");
                            builder.field("ignore_above", 256);
                        } else if (Double.class.equals(clz)) {
                            builder.field("type", "keyword");
                        } else if (Date.class.equals(clz)) {
                            builder.field("type", "date");
                        } else {
                            builder.field("type", "keyword");
                        }
                    }
                    builder.endObject();
                }
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
