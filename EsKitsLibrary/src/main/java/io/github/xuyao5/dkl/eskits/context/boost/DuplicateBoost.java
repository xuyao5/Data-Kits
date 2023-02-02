package io.github.xuyao5.dkl.eskits.context.boost;

import com.lmax.disruptor.EventFactory;
import io.github.xuyao5.dkl.eskits.context.handler.AbstractBatchEventHandler;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.ibatis.session.ResultHandler;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author Thomas.XU(xuyao)
 * @version 18/07/22 22:22
 */
@Slf4j
@Builder(builderMethodName = "context", buildMethodName = "create")
public final class DuplicateBoost<T> {

    @Builder.Default
    private int defaultBufferSize = 4_096;

    @Builder.Default
    private int defaultThreshold = 1_024;

    public void execute(EventFactory<T> factory, Consumer<ResultHandler<T>> origConsumer, Consumer<List<T>> destConsumer) {
        DisruptorBoost.<T>context().defaultBufferSize(defaultBufferSize).create().processOneArgEvent(factory,
                //事件生产
                translator -> origConsumer.accept(resultContext -> translator.translate((dest, sequence, orig) -> {
                    try {
                        BeanUtils.copyProperties(dest, orig);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        log.error("Db2DbService#execute#AbstractSequenceReporting", e);
                    }
                }, resultContext.getResultObject())),
                //错误处理
                (orig, sequence) -> log.error("Db2DbService#execute#AbstractSequenceReporting Error:{}|{}", sequence, orig),
                //事件消费
                new AbstractBatchEventHandler<T>(defaultThreshold) {
                    @Override
                    protected void processEvent(List<T> list) {
                        destConsumer.accept(list);
                    }
                });
    }
}