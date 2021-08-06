package io.github.xuyao5.dkl.eskits.support.batch;

import io.github.xuyao5.dkl.eskits.util.GsonUtilsPlus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
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

import java.io.Serializable;
import java.util.List;
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
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BulkSupporter {

    public static BulkSupporter getInstance() {
        return BulkSupporter.SingletonHolder.INSTANCE;
    }

    public static IndexRequest buildIndexRequest(@NonNull String index, @NonNull String id, @NonNull Serializable obj) {
        return new IndexRequest(index).id(id).source(GsonUtilsPlus.obj2Json(obj), XContentType.JSON);
    }

    public static IndexRequest buildIndexRequest(@NonNull String index, @NonNull Serializable obj) {
        return new IndexRequest(index).source(GsonUtilsPlus.obj2Json(obj), XContentType.JSON);
    }

    public static UpdateRequest buildUpdateRequest(@NonNull String index, @NonNull String id, @NonNull Serializable obj) {
        return new UpdateRequest(index, id).doc(GsonUtilsPlus.obj2Json(obj), XContentType.JSON).docAsUpsert(true);
    }

    public static UpdateRequest buildUpdateRequest(@NonNull String index, @NonNull String id, @NonNull Serializable docObj, @NonNull Serializable upsertObj) {
        return new UpdateRequest(index, id).doc(GsonUtilsPlus.obj2Json(docObj), XContentType.JSON).upsert(GsonUtilsPlus.obj2Json(upsertObj), XContentType.JSON);
    }

    public static DeleteRequest buildDeleteRequest(@NonNull String index, @NonNull String id) {
        return new DeleteRequest(index, id);
    }

    /**
     * Bulk Processor
     */
    @SneakyThrows
    public void bulk(@NonNull RestHighLevelClient client, int threads, Consumer<Function<DocWriteRequest<?>, BulkProcessor>> consumer) {
        try (BulkProcessor bulkProcessor = BulkProcessor.builder((request, bulkListener) -> client.bulkAsync(request, DEFAULT, bulkListener),
                new BulkProcessor.Listener() {
                    @Override
                    public void beforeBulk(long executionId, BulkRequest request) {
                        log.info("Executing bulk [{}] with {} requests", executionId, request.numberOfActions());
                    }

                    @Override
                    public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
                        if (response.hasFailures()) {
                            log.warn("Bulk [{}] executed with {}", executionId, response.buildFailureMessage());
                        } else {
                            log.info("Bulk [{}] completed in {} seconds", executionId, response.getTook().getMillis() / 1000);
                        }
                    }

                    @Override
                    public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
                        log.error("Failed to execute bulk", failure);
                    }
                }).setBulkActions(-1)
                .setBulkSize(new ByteSizeValue(12, ByteSizeUnit.MB))
                .setConcurrentRequests(threads - 1)
                .build()) {
            consumer.accept(bulkProcessor::add);
            log.info("BulkProcessor awaitClose is {}", bulkProcessor.awaitClose(6, TimeUnit.MINUTES));
        }
    }

    /**
     * Bulk API
     */
    @SneakyThrows
    public BulkResponse bulk(@NonNull RestHighLevelClient client, @NonNull List<DocWriteRequest<?>> requests) {
        return client.bulk(new BulkRequest().add(requests), DEFAULT);
    }

    private static class SingletonHolder {
        private static final BulkSupporter INSTANCE = new BulkSupporter();
    }
}
