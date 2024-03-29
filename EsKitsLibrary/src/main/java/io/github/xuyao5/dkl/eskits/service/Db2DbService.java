package io.github.xuyao5.dkl.eskits.service;

import com.lmax.disruptor.EventFactory;
import io.github.xuyao5.dkl.eskits.context.boost.DisruptorBoost;
import io.github.xuyao5.dkl.eskits.context.handler.AbstractBatchEventHandler;
import io.github.xuyao5.dkl.eskits.service.config.Db2DbConfig;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.ibatis.session.ResultHandler;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * @author Thomas.XU(xuyao)
 * @version 18/07/22 20:32
 */
@Deprecated
@Slf4j
public final class Db2DbService<T> {

    public int executeByBatchEventHandler(@NonNull Db2DbConfig config, EventFactory<T> factory, Consumer<ResultHandler<T>> consumer, AbstractBatchEventHandler<T> batchEventHandler) {
        //执行计数器
        AtomicInteger count = new AtomicInteger();

        DisruptorBoost.<T>context().defaultBufferSize(config.getBufferSize()).create().processTwoArgEvent(factory,
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
                batchEventHandler);

        return count.intValue();
    }
}