package io.github.xuyao5.dkl.eskits.service;

import com.google.gson.reflect.TypeToken;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.IgnoreExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import io.github.xuyao5.dkl.eskits.context.AbstractExecutor;
import io.github.xuyao5.dkl.eskits.schema.base.BaseDocument;
import io.github.xuyao5.dkl.eskits.service.config.ModifyByScrollConfig;
import io.github.xuyao5.dkl.eskits.support.batch.BulkSupporter;
import io.github.xuyao5.dkl.eskits.support.batch.ScrollSupporter;
import io.github.xuyao5.dkl.eskits.util.MyGsonUtils;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;

import java.lang.reflect.InvocationTargetException;
import java.util.function.UnaryOperator;

import static org.elasticsearch.index.reindex.AbstractBulkByScrollRequest.DEFAULT_SCROLL_SIZE;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 15/03/21 20:57
 * @apiNote ModifyByScrollExecutor
 * @implNote ModifyByScrollExecutor
 */
@Slf4j
public final class ModifyByScrollExecutor extends AbstractExecutor {

    private final int bulkThreads;
    private final int scrollSize;

    public ModifyByScrollExecutor(@NonNull RestHighLevelClient esClient, int threads, int size) {
        super(esClient);
        bulkThreads = threads;
        scrollSize = size;
    }

    public ModifyByScrollExecutor(@NonNull RestHighLevelClient esClient) {
        this(esClient, 3, DEFAULT_SCROLL_SIZE);
    }

    public <T extends BaseDocument> void upsertByScroll(@NonNull ModifyByScrollConfig config, EventFactory<T> document, UnaryOperator<T> operator) {
        BulkSupporter.getInstance().bulk(client, bulkThreads, function -> {
            final Disruptor<T> disruptor = new Disruptor<>(document, RING_SIZE, DaemonThreadFactory.INSTANCE, ProducerType.SINGLE, new BlockingWaitStrategy());

            disruptor.handleEventsWith((standardDocument, sequence, endOfBatch) -> function.apply(BulkSupporter.buildUpdateRequest(config.getIndex(), standardDocument.get_id(), operator.apply(standardDocument))));

            disruptor.setDefaultExceptionHandler(new IgnoreExceptionHandler());

            publish(disruptor, config.getQueryBuilder(), config.getIndex());
        });
    }

    public <T extends BaseDocument> void deleteByScroll(@NonNull ModifyByScrollConfig config, EventFactory<T> document) {
        BulkSupporter.getInstance().bulk(client, bulkThreads, function -> {
            final Disruptor<T> disruptor = new Disruptor<>(document, RING_SIZE, DaemonThreadFactory.INSTANCE, ProducerType.SINGLE, new BlockingWaitStrategy());

            disruptor.handleEventsWith((standardDocument, sequence, endOfBatch) -> function.apply(BulkSupporter.buildDeleteRequest(config.getIndex(), standardDocument.get_id())));

            publish(disruptor, config.getQueryBuilder(), config.getIndex());
        });
    }

    private void publish(@NonNull Disruptor<? extends BaseDocument> disruptor, @NonNull QueryBuilder queryBuilder, @NonNull String index) {
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
            }, queryBuilder, scrollSize, index);
        } finally {
            disruptor.shutdown();
        }
    }
}
