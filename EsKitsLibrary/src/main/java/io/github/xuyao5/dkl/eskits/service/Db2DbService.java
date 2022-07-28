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
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * @author Thomas.XU(xuyao)
 * @version 18/07/22 20:32
 */
@Slf4j
public final class Db2DbService<T> {

    /**
     * 单线程批量
     */
    public int executeBySequenceReportingEventHandler(@NonNull Db2DbConfig config, EventFactory<T> factory, Consumer<ResultHandler<T>> mapper, AbstractSequenceReporting<T> sequenceReporting) {
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
                (order, sequence) -> log.error("Db2DbService#execute#AbstractSequenceReporting Error:{}|{}", sequence, order),
                //事件消费
                sequenceReporting);

        return count.intValue();
    }

    /**
     * 多线程
     */
    public int executeByWorkerPool(@NonNull Db2DbConfig config, EventFactory<T> factory, Consumer<ResultHandler<T>> mapper, WorkHandler<T> workHandler) {
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
                (order, sequence) -> log.error("Db2DbService#execute#WorkHandler Error:{}|{}", sequence, order),
                //事件消费
                workHandler,
                //线程数
                config.getThreads());

        return count.intValue();
    }

    /**
     * 菱形多线程
     */
    public int executeByWorkerPoolEventHandler(@NonNull Db2DbConfig config, EventFactory<T> factory, Consumer<ResultHandler<T>> mapper, Consumer<List<T>> handler) {
        //执行计数器
        final AtomicInteger count = new AtomicInteger();

        List<T> surplusList = new CopyOnWriteArrayList<>();
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
                (order, sequence) -> log.error("Db2DbService#execute#WorkHandler Error:{}|{}", sequence, order),
                //多线程消费
                () -> new WorkHandler<T>() {
                    private final List<T> list = new LinkedList<>();

                    @Override
                    public void onEvent(T t) {
                        list.add(t);
                        if (list.size() >= config.getThreshold()) {
                            try {
                                handler.accept(list);
                            } finally {
                                list.clear();
                            }
                        }
                    }
                },
                //尾部消费
                (t, sequence, endOfBatch) -> {
                    if (!surplusList.isEmpty() && endOfBatch) {
                        try {
                            handler.accept(surplusList);
                        } finally {
                            surplusList.clear();
                        }
                    }
                },
                //线程数
                config.getThreads());
        return count.intValue();
    }
}