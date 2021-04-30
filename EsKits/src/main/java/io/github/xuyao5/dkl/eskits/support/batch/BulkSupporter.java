package io.github.xuyao5.dkl.eskits.support.batch;

import io.github.xuyao5.dkl.eskits.util.MyGsonUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
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
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;

import javax.validation.constraints.NotNull;
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

    public static final BulkSupporter getInstance() {
        return BulkSupporter.SingletonHolder.INSTANCE;
    }

    public static IndexRequest buildIndexRequest(@NotNull String index, @NotNull Serializable obj) {
        return new IndexRequest(index).source(MyGsonUtils.obj2Json(obj), XContentType.JSON);
    }

    public static UpdateRequest buildUpdateRequest(@NotNull String index, @NotNull String id, @NotNull Serializable obj) {
        return new UpdateRequest(index, id).doc(MyGsonUtils.obj2Json(obj), XContentType.JSON).docAsUpsert(true);
    }

    public static DeleteRequest buildDeleteRequest(@NotNull String index, @NotNull String id) {
        return new DeleteRequest(index, id);
    }

    /**
     * Bulk Processor
     */
    @SneakyThrows
    public boolean bulk(@NotNull RestHighLevelClient client, int threads, Consumer<Function<DocWriteRequest<?>, BulkProcessor>> consumer) {
        try (BulkProcessor bulkProcessor = BulkProcessor.builder((request, bulkListener) -> client.bulkAsync(request.timeout(TimeValue.MINUS_ONE), DEFAULT, bulkListener),
                new BulkProcessor.Listener() {
                    @Override
                    public void beforeBulk(long executionId, BulkRequest request) {
                        int numberOfActions = request.numberOfActions();
                        log.info("Executing bulk [{}] with {} requests", executionId, numberOfActions);
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
            return bulkProcessor.awaitClose(6, TimeUnit.MINUTES);
        }
    }

    /**
     * Bulk API
     */
    @SneakyThrows
    public BulkResponse bulk(@NotNull RestHighLevelClient client, @NotNull List<DocWriteRequest<?>> requests) {
        return client.bulk(new BulkRequest().add(requests), DEFAULT);
    }

    private static class SingletonHolder {
        private static final BulkSupporter INSTANCE = new BulkSupporter();
    }
}
