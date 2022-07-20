package io.github.xuyao5.dkl.eskits.service;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.WorkHandler;
import io.github.xuyao5.dkl.eskits.context.AbstractSequenceReporting;
import io.github.xuyao5.dkl.eskits.context.DisruptorBoost;
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
@Slf4j
public final class Db2DbService<T> {

    public int execute(@NonNull Db2DbConfig config, EventFactory<T> factory, Consumer<ResultHandler<T>> mapper, AbstractSequenceReporting<T> sequenceReporting) {
        //执行计数器
        final AtomicInteger count = new AtomicInteger();

        DisruptorBoost.<T>context().defaultBufferSize(config.getBufferSize()).create().processZeroArgEvent(factory,
                //事件生产
                translator -> mapper.accept(resultContext -> translator.translate((t, sequence) -> {
                    try {
                        BeanUtils.copyProperties(t, resultContext.getResultObject());
                        count.set(resultContext.getResultCount());
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        log.error("Db2DbService#execute#AbstractSequenceReporting", e);
                    }
                })),
                //错误处理
                (order, value) -> log.error("Db2DbService#execute#AbstractSequenceReporting Error:{}|{}", value, order),
                //事件消费
                sequenceReporting);

        return count.intValue();
    }

    public int execute(@NonNull Db2DbConfig config, EventFactory<T> factory, Consumer<ResultHandler<T>> mapper, WorkHandler<T> workHandler) {
        //执行计数器
        final AtomicInteger count = new AtomicInteger();

        DisruptorBoost.<T>context().defaultBufferSize(config.getBufferSize()).create().processZeroArgEvent(factory,
                //事件生产
                translator -> mapper.accept(resultContext -> translator.translate((t, sequence) -> {
                    try {
                        BeanUtils.copyProperties(t, resultContext.getResultObject());
                        count.set(resultContext.getResultCount());
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        log.error("Db2DbService#execute#WorkHandler", e);
                    }
                })),
                //错误处理
                (order, value) -> log.error("Db2DbService#execute#WorkHandler Error:{}|{}", value, order),
                //事件消费
                workHandler,
                //线程数
                Runtime.getRuntime().availableProcessors() * 2);

        return count.intValue();
    }
}