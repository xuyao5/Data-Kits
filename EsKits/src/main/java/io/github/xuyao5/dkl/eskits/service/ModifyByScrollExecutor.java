package io.github.xuyao5.dkl.eskits.service;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import io.github.xuyao5.dkl.eskits.context.AbstractExecutor;
import io.github.xuyao5.dkl.eskits.schema.base.BaseDocument;
import io.github.xuyao5.dkl.eskits.service.config.ModifyByScrollConfig;
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
 * @apiNote ModifyByScrollExecutor
 * @implNote ModifyByScrollExecutor
 */
@Slf4j
public final class ModifyByScrollExecutor extends AbstractExecutor {

    private final int bulkThreads;

    public ModifyByScrollExecutor(@NotNull RestHighLevelClient esClient, int threads) {
        super(esClient);
        bulkThreads = threads;
    }

    public <T extends BaseDocument> void upsertByScroll(@NotNull ModifyByScrollConfig config, EventFactory<T> document, Function<T, Map<String, Object>> operator) {
        BulkSupporter.getInstance().bulk(client, bulkThreads, function -> {
            final Disruptor<T> disruptor = new Disruptor<>(document, RING_SIZE, DaemonThreadFactory.INSTANCE, ProducerType.SINGLE, new BlockingWaitStrategy());

            disruptor.handleEventsWith((standardDocument, sequence, endOfBatch) -> function.apply(BulkSupporter.buildUpsertRequest(config.getTargetIndex(), standardDocument.get_id(), operator.apply(standardDocument))));

            publish(disruptor, config.getQueryBuilder(), config.getSourceIndex());
        });
    }

    public <T extends BaseDocument> void deleteByScroll(@NotNull ModifyByScrollConfig config, EventFactory<T> document) {
        BulkSupporter.getInstance().bulk(client, bulkThreads, function -> {
            final Disruptor<T> disruptor = new Disruptor<>(document, RING_SIZE, DaemonThreadFactory.INSTANCE, ProducerType.SINGLE, new BlockingWaitStrategy());

            disruptor.handleEventsWith((standardDocument, sequence, endOfBatch) -> function.apply(BulkSupporter.buildDeleteRequest(config.getTargetIndex(), standardDocument.get_id())));

            publish(disruptor, config.getQueryBuilder(), config.getSourceIndex());
        });
    }

    private void publish(@NotNull Disruptor<? extends BaseDocument> disruptor, @NotNull QueryBuilder queryBuilder, @NotNull String... indices) {
        RingBuffer<? extends BaseDocument> ringBuffer = disruptor.start();
        try {
            ScrollSupporter.getInstance().scroll(client, searchHits -> ringBuffer.publishEvent((standardDocument, sequence, searchHitList) -> {
                searchHitList.forEach(documentFields -> {
                    standardDocument.set_index(documentFields.getIndex());//demo
                });
            }, Arrays.asList(searchHits)), queryBuilder, indices);
        } finally {
            disruptor.shutdown();
        }
    }
}
