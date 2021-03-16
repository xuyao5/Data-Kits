package io.github.xuyao5.dkl.eskits.support.batch;

import io.github.xuyao5.dkl.eskits.abstr.AbstractSupporter;
import io.github.xuyao5.dkl.eskits.util.MyGsonUtils;
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
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.elasticsearch.client.RequestOptions.DEFAULT;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 14/02/21 08:13
 * @apiNote BulkSupporter
 * @implNote BulkSupporter
 */
@Slf4j
public final class BulkSupporter extends AbstractSupporter {

    private final int CONCURRENT_REQUESTS;

    public BulkSupporter(@NotNull RestHighLevelClient client, int concurrentRequests) {
        super(client);
        CONCURRENT_REQUESTS = concurrentRequests;
    }

    public static IndexRequest buildIndexRequest(@NotNull String index, @NotNull Serializable obj) {
        return new IndexRequest(index).source(MyGsonUtils.obj2Json(obj), XContentType.JSON);
    }

    public static UpdateRequest buildUpdateRequest(@NotNull String index, @NotNull String id, @NotNull Map<String, Object> obj) {
        return new UpdateRequest(index, id).doc(obj);
    }

    public static UpdateRequest buildUpsertRequest(@NotNull String index, @NotNull String id, @NotNull Map<String, Object> obj) {
        return new UpdateRequest(index, id).upsert(obj);
    }

    public static DeleteRequest buildDeleteRequest(@NotNull String index, @NotNull String id) {
        return new DeleteRequest(index, id);
    }

    /**
     * Bulk Processor
     */
    @SneakyThrows
    public boolean bulk(Consumer<Function<DocWriteRequest<?>, BulkProcessor>> consumer) {
        try (BulkProcessor bulkProcessor = BulkProcessor.builder((request, bulkListener) -> client.bulkAsync(request, DEFAULT, bulkListener),
                new BulkProcessor.Listener() {
                    @Override
                    public void beforeBulk(long executionId, BulkRequest request) {
                        int numberOfActions = request.numberOfActions();
                        log.info("Executing bulk [{}] with {} requests", executionId, numberOfActions);
                    }

                    @Override
                    public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
                        if (response.hasFailures()) {
                            log.warn("Bulk [{}] executed with failures", executionId);
                        } else {
                            log.info("Bulk [{}] completed in {} milliseconds", executionId, response.getTook().getMillis());
                        }
                    }

                    @Override
                    public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
                        log.error("Failed to execute bulk", failure);
                    }
                }).setBulkActions(-1)
                .setBulkSize(new ByteSizeValue(12L, ByteSizeUnit.MB))
                .setConcurrentRequests(CONCURRENT_REQUESTS - 1)
                .build()) {
            consumer.accept(bulkProcessor::add);
            return bulkProcessor.awaitClose(30L, TimeUnit.MINUTES);//最大30分钟等待
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
