package io.github.xuyao5.dkl.eskits.context;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import io.github.xuyao5.dkl.eskits.context.handler.EventExceptionHandler;
import io.github.xuyao5.dkl.eskits.context.producer.EventProducer;
import io.github.xuyao5.dkl.eskits.context.translator.*;
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

    public void processZeroArgEvent(EventFactory<T> eventFactory, Consumer<ZeroArgEventTranslator<T>> eventZeroArgConsumer, EventExceptionHandler<T> exceptionHandler, boolean isShutdownFinally, EventHandler<T> handlers) {
        process(eventFactory, ringBuffer -> eventZeroArgConsumer.accept(ringBuffer::publishEvent), exceptionHandler, isShutdownFinally, new AggregateEventHandler<>(handlers));
    }

    public void processOneArgEvent(EventFactory<T> eventFactory, Consumer<OneArgEventTranslator<T>> eventOneArgConsumer, EventExceptionHandler<T> exceptionHandler, boolean isShutdownFinally, EventHandler<T> handlers) {
        process(eventFactory, ringBuffer -> eventOneArgConsumer.accept(ringBuffer::publishEvent), exceptionHandler, isShutdownFinally, new AggregateEventHandler<>(handlers));
    }

    public void processTwoArgEvent(EventFactory<T> eventFactory, Consumer<TwoArgEventTranslator<T>> eventTwoArgConsumer, EventExceptionHandler<T> exceptionHandler, boolean isShutdownFinally, EventHandler<T> handlers) {
        process(eventFactory, ringBuffer -> eventTwoArgConsumer.accept(ringBuffer::publishEvent), exceptionHandler, isShutdownFinally, new AggregateEventHandler<>(handlers));
    }

    public void processThreeArgEvent(EventFactory<T> eventFactory, Consumer<ThreeArgEventTranslator<T>> eventThreeArgConsumer, EventExceptionHandler<T> exceptionHandler, boolean isShutdownFinally, EventHandler<T> handlers) {
        process(eventFactory, ringBuffer -> eventThreeArgConsumer.accept(ringBuffer::publishEvent), exceptionHandler, isShutdownFinally, new AggregateEventHandler<>(handlers));
    }

    public void processVarargEvent(EventFactory<T> eventFactory, Consumer<VarargEventTranslator<T>> eventVarargConsumer, EventExceptionHandler<T> exceptionHandler, boolean isShutdownFinally, EventHandler<T> handlers) {
        process(eventFactory, ringBuffer -> eventVarargConsumer.accept(ringBuffer::publishEvent), exceptionHandler, isShutdownFinally, new AggregateEventHandler<>(handlers));
    }

    private void process(EventFactory<T> eventFactory, EventProducer<T> eventProducer, EventExceptionHandler<T> exceptionHandler, boolean isShutdownFinally, AggregateEventHandler<T> eventHandler) {
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