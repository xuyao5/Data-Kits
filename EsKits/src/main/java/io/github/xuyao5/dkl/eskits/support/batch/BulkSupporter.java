package io.github.xuyao5.dkl.eskits.support.batch;

import io.github.xuyao5.dkl.eskits.abstr.AbstractSupporter;
import io.github.xuyao5.dkl.eskits.util.GsonUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.xcontent.XContentType;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.ToIntFunction;

import static org.elasticsearch.client.RequestOptions.DEFAULT;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 14/02/21 08:13
 * @apiNote BulkSupporter
 * @implNote BulkSupporter
 */
@Slf4j
public final class BulkSupporter extends AbstractSupporter {

    private final int BULK_ACTIONS;
    private final long BULK_SIZE;
    private final int CONCURRENT_REQUESTS;

    public BulkSupporter(RestHighLevelClient client, int bulkActions, long bulkSize, int concurrentRequests) {
        super(client);
        BULK_ACTIONS = bulkActions;
        BULK_SIZE = bulkSize;
        CONCURRENT_REQUESTS = concurrentRequests;
    }

    public BulkSupporter(RestHighLevelClient client, int bulkActions, int concurrentRequests) {
        this(client, bulkActions, -1, concurrentRequests);
    }

    public BulkSupporter(RestHighLevelClient client, long bulkSize, int concurrentRequests) {
        this(client, -1, bulkSize, concurrentRequests);
    }

    public BulkSupporter(RestHighLevelClient client) {
        this(client, 1000 * 3, 5 * 3, 1 * 3);
    }

    public static IndexRequest buildIndexRequest(@NotNull String index, @NotNull String id, @NotNull Serializable obj) {
        return new IndexRequest(index).id(id).source(GsonUtils.obj2Json(obj), XContentType.JSON);
    }

    public static UpdateRequest buildUpdateRequest(@NotNull String index, @NotNull String id, boolean isFetchSource, @NotNull Serializable obj) {
        return new UpdateRequest(index, id).doc(obj).fetchSource(isFetchSource);
    }

    public static DeleteRequest buildDeleteRequest(@NotNull String index, @NotNull String id, @NotNull Serializable obj) {
        return new DeleteRequest(index, id);
    }

    /**
     * Bulk Processor
     */
    @SneakyThrows
    public boolean bulk(ToIntFunction<Function<DocWriteRequest<?>, BulkProcessor>> function) {
        try (BulkProcessor bulkProcessor = BulkProcessor.builder((request, bulkListener) -> client.bulkAsync(request, DEFAULT, bulkListener),
                new BulkProcessor.Listener() {
                    @Override
                    public void beforeBulk(long executionId, BulkRequest request) {
                        int numberOfActions = request.numberOfActions();
                        log.debug("Executing bulk [{}] with {} requests", executionId, numberOfActions);
                    }

                    @Override
                    public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
                        if (response.hasFailures()) {
                            log.warn("Bulk [{}] executed with failures", executionId);
                        } else {
                            log.debug("Bulk [{}] completed in {} milliseconds", executionId, response.getTook().getMillis());
                        }
                    }

                    @Override
                    public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
                        log.error("Failed to execute bulk", failure);
                    }
                }).setBulkActions(BULK_ACTIONS)
                .setBulkSize(new ByteSizeValue(BULK_SIZE, ByteSizeUnit.MB))
                .setConcurrentRequests(CONCURRENT_REQUESTS)
                .build()) {
            return bulkProcessor.awaitClose(function.applyAsInt(bulkProcessor::add), TimeUnit.SECONDS);
        }
    }

    /**
     * Bulk API
     */
    @SneakyThrows
    public BulkResponse bulk(@NotNull List<DocWriteRequest<?>> requests) {
        return client.bulk(new BulkRequest().add(requests), DEFAULT);
    }
}
