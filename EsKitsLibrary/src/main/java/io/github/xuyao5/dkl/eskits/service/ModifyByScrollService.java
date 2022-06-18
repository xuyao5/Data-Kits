package io.github.xuyao5.dkl.eskits.service;

import com.lmax.disruptor.EventFactory;
import io.github.xuyao5.dkl.eskits.context.AbstractExecutor;
import io.github.xuyao5.dkl.eskits.context.DisruptorBoost;
import io.github.xuyao5.dkl.eskits.context.event.OneArgEvent;
import io.github.xuyao5.dkl.eskits.schema.base.BaseDocument;
import io.github.xuyao5.dkl.eskits.service.config.ModifyByScrollConfig;
import io.github.xuyao5.dkl.eskits.support.batch.BulkSupporter;
import io.github.xuyao5.dkl.eskits.support.batch.ScrollSupporter;
import io.github.xuyao5.dkl.eskits.util.GsonUtilsPlus;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

import static org.elasticsearch.index.reindex.AbstractBulkByScrollRequest.DEFAULT_SCROLL_SIZE;

/**
 * @author Thomas.XU(xuyao)
 * @version 15/03/21 20:57
 */
@Slf4j
public final class ModifyByScrollService extends AbstractExecutor {

    private final int bulkThreads;
    private final int scrollSize;

    public ModifyByScrollService(@NonNull RestHighLevelClient esClient, int threads, int size) {
        super(esClient);
        bulkThreads = threads;
        scrollSize = size;
    }

    public ModifyByScrollService(@NonNull RestHighLevelClient esClient) {
        this(esClient, 3, DEFAULT_SCROLL_SIZE);
    }

    public <T extends BaseDocument> void upsertByScroll(@NonNull ModifyByScrollConfig config, EventFactory<T> document, UnaryOperator<T> operator) {
        BulkSupporter.getInstance().bulk(client, bulkThreads, function -> DisruptorBoost.<T>context().create().processOneArgEvent(consumer -> eventConsumer(config, consumer), (sequence, t) -> log.error(t.toString()), document, true, (standardDocument, sequence, endOfBatch) -> function.apply(BulkSupporter.buildUpdateRequest(config.getIndex(), standardDocument.get_id(), operator.apply(standardDocument)))));
    }

    public <T extends BaseDocument> void deleteByScroll(@NonNull ModifyByScrollConfig config, EventFactory<T> document) {
        BulkSupporter.getInstance().bulk(client, bulkThreads, function -> DisruptorBoost.<T>context().create().processOneArgEvent(consumer -> eventConsumer(config, consumer), (sequence, t) -> log.error(t.toString()), document, true, (standardDocument, sequence, endOfBatch) -> function.apply(BulkSupporter.buildDeleteRequest(config.getIndex(), standardDocument.get_id()))));
    }

    public <T extends BaseDocument> void computeByScroll(@NonNull ModifyByScrollConfig config, EventFactory<T> document, Consumer<T> compute) {
        DisruptorBoost.<T>context().create().processOneArgEvent(consumer -> eventConsumer(config, consumer), (sequence, t) -> log.error(t.toString()), document, true, (standardDocument, sequence, endOfBatch) -> compute.accept(standardDocument));
    }

    private void eventConsumer(ModifyByScrollConfig config, OneArgEvent<? extends BaseDocument> event) {
        ScrollSupporter.getInstance().scroll(client, searchHits -> {
            for (SearchHit documentFields : searchHits) {
                event.translate((standardDocument, sequence, searchHit) -> {
                    try {
                        BeanUtils.copyProperties(standardDocument, GsonUtilsPlus.json2Obj(searchHit.getSourceAsString(), standardDocument.getClass()));
                        standardDocument.set_id(searchHit.getId());
                    } catch (IllegalAccessException | InvocationTargetException ex) {
                        log.error("StandardDocument类型转换错误", ex);
                    }
                }, documentFields);
            }
        }, config.getQueryBuilder(), scrollSize, config.getIndex());
    }
}