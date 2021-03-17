package io.github.xuyao5.dkl.eskits.service;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import io.github.xuyao5.dkl.eskits.configuration.UpdateByScrollConfig;
import io.github.xuyao5.dkl.eskits.context.AbstractExecutor;
import io.github.xuyao5.dkl.eskits.schema.StandardDocument;
import io.github.xuyao5.dkl.eskits.support.batch.BulkSupporter;
import io.github.xuyao5.dkl.eskits.support.batch.ScrollSupporter;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 15/03/21 20:57
 * @apiNote BatchUpdateExecutor
 * @implNote BatchUpdateExecutor
 */
@Slf4j
public final class UpdateByScrollExecutor extends AbstractExecutor {

    public UpdateByScrollExecutor(RestHighLevelClient esClient, int threads) {
        super(esClient, threads);
    }

    public <T extends StandardDocument> void execute(@NotNull UpdateByScrollConfig config, EventFactory<T> document, Function<T, Map<String, Object>> operator) {
        new BulkSupporter(client, bulkThreads).bulk(function -> {
            final Disruptor<T> disruptor = new Disruptor<>(document, RING_BUFFER_SIZE, DaemonThreadFactory.INSTANCE, ProducerType.SINGLE, new BlockingWaitStrategy());

            disruptor.handleEventsWith((standardDocument, sequence, endOfBatch) -> function.apply(BulkSupporter.buildUpsertRequest(config.getIndex(), standardDocument.get_id(), operator.apply(standardDocument))));

            publish(disruptor, config.getQueryBuilder(), config.getIndex());
        });
    }

    private void publish(@NotNull Disruptor<? extends StandardDocument> disruptor, @NotNull QueryBuilder queryBuilder, @NotNull String... indices) {
        RingBuffer<? extends StandardDocument> ringBuffer = disruptor.start();
        try {
            new ScrollSupporter(client).scroll(searchHits -> ringBuffer.publishEvent((standardDocument, sequence, searchHitList) -> {
                searchHitList.forEach(documentFields -> {
                    standardDocument.set_index(documentFields.getIndex());//demo
                });
            }, Arrays.asList(searchHits)), queryBuilder, indices);
        } finally {
            disruptor.shutdown();
        }
    }
}
