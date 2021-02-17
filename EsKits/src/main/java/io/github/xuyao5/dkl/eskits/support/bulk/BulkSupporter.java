package io.github.xuyao5.dkl.eskits.support.bulk;

import io.github.xuyao5.dkl.common.util.GsonUtils;
import io.github.xuyao5.dkl.eskits.abstr.AbstractSupporter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.xcontent.XContentType;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.ToIntFunction;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 14/02/21 08:13
 * @apiNote TODO 这里输入文件说明
 * @implNote TODO 这里输入实现说明
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

    public static final <T> IndexRequest genIndexRequest(@NotNull String index, @NotNull String id, @NotNull Serializable obj) {
        return new IndexRequest(index).id(id).source(GsonUtils.obj2Json(obj), XContentType.JSON);
    }

    /**
     * Bulk Processor
     */
    @SneakyThrows
    public boolean bulk(ToIntFunction<Function<DocWriteRequest<?>, BulkProcessor>> function) {
        try (BulkProcessor bulkProcessor = BulkProcessor.builder((request, bulkListener) -> client.bulkAsync(request, RequestOptions.DEFAULT, bulkListener),
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
}
