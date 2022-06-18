package io.github.xuyao5.dkl.eskits.context;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import io.github.xuyao5.dkl.eskits.context.event.*;
import io.github.xuyao5.dkl.eskits.context.handler.EventExceptionHandler;
import io.github.xuyao5.dkl.eskits.context.producer.EventProducer;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.function.Consumer;

/**
 * @author Thomas.XU(xuyao)
 * @version 4/07/21 20:33
 */
@Slf4j
@Builder(builderMethodName = "context", buildMethodName = "create")
public final class DisruptorBoost<T> {

    @Builder.Default
    private int bufferSize = 1_024 * 4;

    public void processZeroArgEvent(Consumer<ZeroArgEvent<T>> eventZeroArgConsumer, EventExceptionHandler<T> exceptionHandler, EventFactory<T> eventFactory, boolean isShutdownFinally, EventHandler<T> handlers) {
        process(ringBuffer -> eventZeroArgConsumer.accept(ringBuffer::publishEvent), exceptionHandler, eventFactory, isShutdownFinally, new AggregateEventHandler<>(handlers));
    }

    public void processOneArgEvent(Consumer<OneArgEvent<T>> eventOneArgConsumer, EventExceptionHandler<T> exceptionHandler, EventFactory<T> eventFactory, boolean isShutdownFinally, EventHandler<T> handlers) {
        process(ringBuffer -> eventOneArgConsumer.accept(ringBuffer::publishEvent), exceptionHandler, eventFactory, isShutdownFinally, new AggregateEventHandler<>(handlers));
    }

    public void processTwoArgEvent(Consumer<TwoArgEvent<T>> eventTwoArgConsumer, EventExceptionHandler<T> exceptionHandler, EventFactory<T> eventFactory, boolean isShutdownFinally, EventHandler<T> handlers) {
        process(ringBuffer -> eventTwoArgConsumer.accept(ringBuffer::publishEvent), exceptionHandler, eventFactory, isShutdownFinally, new AggregateEventHandler<>(handlers));
    }

    public void processThreeArgEvent(Consumer<ThreeArgEvent<T>> eventThreeArgConsumer, EventExceptionHandler<T> exceptionHandler, EventFactory<T> eventFactory, boolean isShutdownFinally, EventHandler<T> handlers) {
        process(ringBuffer -> eventThreeArgConsumer.accept(ringBuffer::publishEvent), exceptionHandler, eventFactory, isShutdownFinally, new AggregateEventHandler<>(handlers));
    }

    public void processVarargEvent(Consumer<VarargEvent<T>> eventVarargConsumer, EventExceptionHandler<T> exceptionHandler, EventFactory<T> eventFactory, boolean isShutdownFinally, EventHandler<T> handlers) {
        process(ringBuffer -> eventVarargConsumer.accept(ringBuffer::publishEvent), exceptionHandler, eventFactory, isShutdownFinally, new AggregateEventHandler<>(handlers));
    }

    private void process(EventProducer<T> eventProducer, EventExceptionHandler<T> exceptionHandler, EventFactory<T> eventFactory, boolean isShutdownFinally, AggregateEventHandler<T> eventHandler) {
        Disruptor<T> disruptor = new Disruptor<>(eventFactory, bufferSize, DaemonThreadFactory.INSTANCE, ProducerType.SINGLE, new BlockingWaitStrategy());
        disruptor.handleEventsWith(eventHandler).then((t, sequence, endOfBatch) -> t = null);
        disruptor.setDefaultExceptionHandler(new ExceptionHandler<T>() {
            @Override
            public void handleEventException(Throwable throwable, long sequence, T t) {
                log.error(StringUtils.join(sequence, '|', t), throwable);
                exceptionHandler.handle(sequence, t);
            }

            @Override
            public void handleOnStartException(Throwable throwable) {
                log.error("Exception during onStart()", throwable);
            }

            @Override
            public void handleOnShutdownException(Throwable throwable) {
                log.error("Exception during onShutdown()", throwable);
            }
        });
        try {
            disruptor.start();
            eventProducer.produce(disruptor.getRingBuffer());
        } finally {
            if (isShutdownFinally) {
                disruptor.shutdown();
            }
        }
    }
}