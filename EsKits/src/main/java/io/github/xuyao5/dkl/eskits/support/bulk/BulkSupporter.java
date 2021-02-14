package io.github.xuyao5.dkl.eskits.support.bulk;

import io.github.xuyao5.dkl.eskits.abstr.AbstractSupporter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;

import java.util.concurrent.TimeUnit;
import java.util.function.ToLongFunction;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 14/02/21 08:13
 * @apiNote TODO 这里输入文件说明
 * @implNote TODO 这里输入实现说明
 */
@Slf4j
public final class BulkSupporter extends AbstractSupporter {

    protected BulkSupporter(RestHighLevelClient client) {
        super(client);
    }

    /**
     * Bulk Processor
     */
    @SneakyThrows
    public boolean bulk(int actions, long size, ToLongFunction<BulkProcessor> function) {
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
                }).setBulkActions(actions)//default:1000
                .setBulkSize(new ByteSizeValue(size, ByteSizeUnit.MB))//default:5
                .setConcurrentRequests(0)
                .setBackoffPolicy(BackoffPolicy.constantBackoff(TimeValue.timeValueSeconds(1L), 3))
                .build()) {
            return bulkProcessor.awaitClose(function.applyAsLong(bulkProcessor), TimeUnit.SECONDS);
        }
    }
}
