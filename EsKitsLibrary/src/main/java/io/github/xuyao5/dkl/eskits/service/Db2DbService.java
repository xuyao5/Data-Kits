package io.github.xuyao5.dkl.eskits.service;

import com.lmax.disruptor.EventFactory;
import io.github.xuyao5.dkl.eskits.context.AbstractSequenceReporting;
import io.github.xuyao5.dkl.eskits.context.DisruptorBoost;
import io.github.xuyao5.dkl.eskits.service.config.Db2DbConfig;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.ibatis.session.ResultHandler;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author Thomas.XU(xuyao)
 * @version 18/07/22 20:32
 */
@Slf4j
public final class Db2DbService<T, R> {

    public void execute(@NonNull Db2DbConfig config, EventFactory<T> sourceFactory, EventFactory<R> targetFactory, Consumer<ResultHandler<T>> sourceMapper, Consumer<List<R>> targetMapper) {
        DisruptorBoost.<T>context().defaultBufferSize(config.getBufferSize()).create().processZeroArgEvent(sourceFactory,
                //事件生产
                translator -> sourceMapper.accept(resultContext -> translator.translate((t, sequence) -> {
                    try {
                        BeanUtils.copyProperties(t, resultContext.getResultObject());
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        log.error("事件生产错误", e);
                    }
                })),
                //错误处理
                (order, value) -> log.error("异常:{}|{}", value, order),
                //事件消费
                new AbstractSequenceReporting<T>(config.getThreshold()) {
                    @Override
                    protected void processEvent(List<T> list) {
                        targetMapper.accept(list.stream().map(t -> {
                            R r = targetFactory.newInstance();
                            try {
                                BeanUtils.copyProperties(r, t);
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                log.error("事件消费错误", e);
                            }
                            return r;
                        }).collect(Collectors.toList()));
                        log.info("批处理：{}", list.size());
                    }
                });
    }

    public void execute(@NonNull Db2DbConfig config, EventFactory<T> sourceFactory, EventFactory<R> targetFactory, Consumer<ResultHandler<T>> sourceMapper, Consumer<R> targetMapper, int threads) {
        DisruptorBoost.<T>context().defaultBufferSize(config.getBufferSize()).create().processZeroArgEvent(sourceFactory,
                //事件生产
                translator -> sourceMapper.accept(resultContext -> translator.translate((t, sequence) -> {
                    try {
                        BeanUtils.copyProperties(t, resultContext.getResultObject());
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        log.error("事件生产错误", e);
                    }
                })),
                //错误处理
                (order, value) -> log.error("异常:{}|{}", value, order),
                //事件消费
                t -> {
                    R r = targetFactory.newInstance();
                    BeanUtils.copyProperties(r, t);
                    targetMapper.accept(r);
                }, threads);
    }
}