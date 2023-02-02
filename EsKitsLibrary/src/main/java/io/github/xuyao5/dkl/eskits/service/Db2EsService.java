package io.github.xuyao5.dkl.eskits.service;

import com.lmax.disruptor.EventFactory;
import io.github.xuyao5.dkl.eskits.context.AbstractExecutor;
import io.github.xuyao5.dkl.eskits.context.DisruptorBoost;
import io.github.xuyao5.dkl.eskits.context.handler.AbstractBatchEventHandler;
import io.github.xuyao5.dkl.eskits.schema.base.BaseDocument;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.ibatis.session.ResultHandler;
import org.elasticsearch.client.RestHighLevelClient;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * @author Thomas.XU(xuyao)
 * @version 18/07/22 22:22
 */
@Slf4j
public final class Db2EsService<T, R extends BaseDocument> extends AbstractExecutor {

    public Db2EsService(RestHighLevelClient client) {
        super(client);
    }

    public int execute(int bufferSize, EventFactory<T> rowFactory, Consumer<ResultHandler<T>> consumer, Consumer<List<T>> listConsumer) {
        //执行计数器
        AtomicInteger count = new AtomicInteger();

        DisruptorBoost.<T>context().defaultBufferSize(bufferSize).create().processTwoArgEvent(rowFactory,
                //事件生产
                translator -> consumer.accept(resultContext -> translator.translate((dest, sequence, orig, resultCount) -> {
                    try {
                        BeanUtils.copyProperties(dest, orig);
                        count.set(resultCount);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        log.error("Db2DbService#execute#AbstractSequenceReporting", e);
                    }
                }, resultContext.getResultObject(), resultContext.getResultCount())),
                //错误处理
                (t, sequence) -> log.error("Db2DbService#execute#AbstractSequenceReporting Error:{}|{}", sequence, t),
                //事件消费
                new AbstractBatchEventHandler<T>(100) {
                    @Override
                    protected void processEvent(List<T> list) {
                        listConsumer.accept(list);
                    }
                });

        return count.intValue();
    }
}