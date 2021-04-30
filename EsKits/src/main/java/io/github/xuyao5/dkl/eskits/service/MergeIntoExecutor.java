package io.github.xuyao5.dkl.eskits.service;

import com.google.gson.reflect.TypeToken;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import io.github.xuyao5.dkl.eskits.context.AbstractExecutor;
import io.github.xuyao5.dkl.eskits.schema.base.BaseDocument;
import io.github.xuyao5.dkl.eskits.service.config.MergeIntoConfig;
import io.github.xuyao5.dkl.eskits.support.batch.BulkSupporter;
import io.github.xuyao5.dkl.eskits.support.batch.ScrollSupporter;
import io.github.xuyao5.dkl.eskits.util.MyGsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;

import javax.validation.constraints.NotNull;
import java.lang.reflect.InvocationTargetException;
import java.util.function.UnaryOperator;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 15/03/21 20:57
 * @apiNote MergeIntoExecutor
 * @implNote MergeIntoExecutor
 */
@Slf4j
public final class MergeIntoExecutor extends AbstractExecutor {

    private final int bulkThreads;

    public MergeIntoExecutor(@NotNull RestHighLevelClient esClient, int threads) {
        super(esClient);
        bulkThreads = threads;
    }

    public MergeIntoExecutor(@NotNull RestHighLevelClient esClient) {
        this(esClient, 3);
    }

    public <T extends BaseDocument> void upsertByScroll(@NotNull MergeIntoConfig config, EventFactory<T> document, UnaryOperator<T> operator) {
        BulkSupporter.getInstance().bulk(client, bulkThreads, function -> {
            final Disruptor<T> disruptor = new Disruptor<>(document, RING_SIZE, DaemonThreadFactory.INSTANCE, ProducerType.SINGLE, new BlockingWaitStrategy());

            disruptor.handleEventsWith((standardDocument, sequence, endOfBatch) -> function.apply(BulkSupporter.buildUpdateRequest(config.getTargetIndex(), standardDocument.get_id(), operator.apply(standardDocument))));

            publish(disruptor, config.getQueryBuilder(), config.getSourceIndexArray());
        });
    }

    public <T extends BaseDocument> void deleteByScroll(@NotNull MergeIntoConfig config, EventFactory<T> document) {
        BulkSupporter.getInstance().bulk(client, bulkThreads, function -> {
            final Disruptor<T> disruptor = new Disruptor<>(document, RING_SIZE, DaemonThreadFactory.INSTANCE, ProducerType.SINGLE, new BlockingWaitStrategy());

            disruptor.handleEventsWith((standardDocument, sequence, endOfBatch) -> function.apply(BulkSupporter.buildDeleteRequest(config.getTargetIndex(), standardDocument.get_id())));

            publish(disruptor, config.getQueryBuilder(), config.getSourceIndexArray());
        });
    }

    private void publish(@NotNull Disruptor<? extends BaseDocument> disruptor, @NotNull QueryBuilder queryBuilder, @NotNull String[] indexArray) {
        RingBuffer<? extends BaseDocument> ringBuffer = disruptor.start();
        try {
            ScrollSupporter.getInstance().scroll(client, searchHits -> {
                for (SearchHit documentFields : searchHits) {
                    ringBuffer.publishEvent((standardDocument, sequence, searchHit) -> {
                        try {
                            BeanUtils.copyProperties(standardDocument, MyGsonUtils.json2Obj(searchHit.getSourceAsString(), TypeToken.get(standardDocument.getClass())));
                            standardDocument.set_id(searchHit.getId());
                            standardDocument.set_index(searchHit.getIndex());
                        } catch (IllegalAccessException | InvocationTargetException ex) {
                            log.error("StandardDocument类型转换错误", ex);
                        }
                    }, documentFields);
                }
            }, queryBuilder, indexArray);
        } finally {
            disruptor.shutdown();
        }
    }
}
