package io.github.xuyao5.dkl.eskits.service;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import io.github.xuyao5.dkl.eskits.abstr.AbstractExecutor;
import io.github.xuyao5.dkl.eskits.configuration.BatchUpdateConfig;
import io.github.xuyao5.dkl.eskits.schema.StandardDocument;
import io.github.xuyao5.dkl.eskits.support.batch.BulkSupporter;
import io.github.xuyao5.dkl.eskits.support.batch.ScrollSupporter;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;

import javax.validation.constraints.NotNull;
import java.util.Arrays;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 15/03/21 20:57
 * @apiNote BatchDeleteExecutor
 * @implNote BatchDeleteExecutor
 */
@Slf4j
public final class BatchDeleteExecutor extends AbstractExecutor {

    public BatchDeleteExecutor(RestHighLevelClient esClient, int threads) {
        super(esClient, threads);
    }

    public <T extends StandardDocument> void execute(@NotNull BatchUpdateConfig config, EventFactory<T> document) {
        new BulkSupporter(client, bulkThreads).bulk(function -> {
            final Disruptor<T> disruptor = new Disruptor<>(document, RING_BUFFER_SIZE, DaemonThreadFactory.INSTANCE, ProducerType.SINGLE, new BlockingWaitStrategy());

            disruptor.handleEventsWith((standardDocument, sequence, endOfBatch) -> function.apply(BulkSupporter.buildDeleteRequest(config.getIndex(), standardDocument.get_id())));

            publish(disruptor, config.getQueryBuilder(), config.getBatchSize(), config.getIndex());
        });
    }

    private void publish(@NotNull Disruptor<? extends StandardDocument> disruptor, @NotNull QueryBuilder queryBuilder, int size, @NotNull String... indices) {
        RingBuffer<? extends StandardDocument> ringBuffer = disruptor.start();
        try {
            new ScrollSupporter(client).scroll(searchHits -> ringBuffer.publishEvent((standardDocument, sequence, searchHitList) -> {
                searchHitList.forEach(documentFields -> {
                    standardDocument.set_index(documentFields.getIndex());//demo
                });
            }, Arrays.asList(searchHits)), queryBuilder, size, indices);
        } finally {
            disruptor.shutdown();
        }
    }
}
