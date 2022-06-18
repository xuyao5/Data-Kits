package io.github.xuyao5.dkl.eskits.context;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import io.github.xuyao5.dkl.eskits.context.translator.*;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.function.Consumer;
import java.util.function.ObjLongConsumer;

/**
 * @author Thomas.XU(xuyao)
 * @version 4/07/21 20:33
 */
@Slf4j
@Builder(builderMethodName = "context", buildMethodName = "create")
public final class DisruptorBoost<T> {

    @Builder.Default
    private int bufferSize = 1_024 * 4;

    public void processZeroArgEvent(EventFactory<T> factory, Consumer<ZeroArgEventTranslator<T>> publisher, ObjLongConsumer<T> exceptionHandler, boolean isShutdownFinally, EventHandler<T>... handlers) {
        processEvent(factory, ringBuffer -> publisher.accept(ringBuffer::publishEvent), exceptionHandler, isShutdownFinally, new AggregateEventHandler<>(handlers));
    }

    public void processOneArgEvent(EventFactory<T> factory, Consumer<OneArgEventTranslator<T>> publisher, ObjLongConsumer<T> exceptionHandler, boolean isShutdownFinally, EventHandler<T>... handlers) {
        processEvent(factory, ringBuffer -> publisher.accept(ringBuffer::publishEvent), exceptionHandler, isShutdownFinally, new AggregateEventHandler<>(handlers));
    }

    public void processTwoArgEvent(EventFactory<T> factory, Consumer<TwoArgEventTranslator<T>> publisher, ObjLongConsumer<T> exceptionHandler, boolean isShutdownFinally, EventHandler<T>... handlers) {
        processEvent(factory, ringBuffer -> publisher.accept(ringBuffer::publishEvent), exceptionHandler, isShutdownFinally, new AggregateEventHandler<>(handlers));
    }

    public void processThreeArgEvent(EventFactory<T> factory, Consumer<ThreeArgEventTranslator<T>> publisher, ObjLongConsumer<T> exceptionHandler, boolean isShutdownFinally, EventHandler<T>... handlers) {
        processEvent(factory, ringBuffer -> publisher.accept(ringBuffer::publishEvent), exceptionHandler, isShutdownFinally, new AggregateEventHandler<>(handlers));
    }

    public void processVarargEvent(EventFactory<T> factory, Consumer<VarargEventTranslator<T>> publisher, ObjLongConsumer<T> exceptionHandler, boolean isShutdownFinally, EventHandler<T>... handlers) {
        processEvent(factory, ringBuffer -> publisher.accept(ringBuffer::publishEvent), exceptionHandler, isShutdownFinally, new AggregateEventHandler<>(handlers));
    }

    private void processEvent(EventFactory<T> factory, Consumer<RingBuffer<T>> eventProducer, ObjLongConsumer<T> exceptionHandler, boolean isShutdownFinally, AggregateEventHandler<T> eventHandler) {
        Disruptor<T> disruptor = new Disruptor<>(factory, bufferSize, DaemonThreadFactory.INSTANCE, ProducerType.SINGLE, new BlockingWaitStrategy());
        disruptor.handleEventsWith(eventHandler).then((t, sequence, endOfBatch) -> t = null);
        disruptor.setDefaultExceptionHandler(new ExceptionHandler<T>() {
            @Override
            public void handleEventException(Throwable throwable, long sequence, T t) {
                log.error(StringUtils.join(sequence, '|', t), throwable);
                exceptionHandler.accept(t, sequence);
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
            eventProducer.accept(disruptor.start());
        } finally {
            if (isShutdownFinally) {
                disruptor.shutdown();
            }
        }
    }
}