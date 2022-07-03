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
import org.elasticsearch.xcontent.XContentType;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static org.elasticsearch.client.RequestOptions.DEFAULT;

/**
 * @author Thomas.XU(xuyao)
 * @version 14/02/21 08:13
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

    public static BulkProcessor buildBulkProcessor(@NonNull RestHighLevelClient client, String name, int threads) {
        return BulkProcessor.builder((request, bulkListener) -> client.bulkAsync(request, DEFAULT, bulkListener), new BulkProcessor.Listener() {
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
        }, name).setConcurrentRequests(threads - 1).build();
    }

    /**
     * Bulk Processor
     *
     * @param client   客户端
     * @param threads  线程数
     * @param consumer 消费者实现
     */
    @SneakyThrows
    public void bulk(@NonNull RestHighLevelClient client, int threads, Consumer<Consumer<DocWriteRequest<?>>> consumer) {
        try (BulkProcessor bulkProcessor = buildBulkProcessor(client, "default-bulk-processor", threads)) {
            consumer.accept(bulkProcessor::add);
            log.info("BulkProcessor awaitClose is {}", bulkProcessor.awaitClose(6, TimeUnit.MINUTES));
        }
    }

    /**
     * Bulk Processor
     *
     * @param bulkProcessor 自定义处理器
     * @param consumer      消费者实现
     */
    public void bulk(@NonNull BulkProcessor bulkProcessor, Consumer<Consumer<DocWriteRequest<?>>> consumer) {
        consumer.accept(bulkProcessor::add);
    }

    /**
     * Bulk API
     *
     * @param client   客户端
     * @param requests 请求
     * @return BulkResponse
     */
    @SneakyThrows
    public BulkResponse bulk(@NonNull RestHighLevelClient client, @NonNull List<DocWriteRequest<?>> requests) {
        return client.bulk(new BulkRequest().add(requests), DEFAULT);
    }

    private static class SingletonHolder {
        private static final BulkSupporter INSTANCE = new BulkSupporter();
    }
}
