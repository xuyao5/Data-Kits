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
    private int defaultBufferSize = 8192;

    @SafeVarargs
    public final void processZeroArgEvent(EventFactory<T> factory, Consumer<ZeroArgEventTranslator<T>> publisher, ObjLongConsumer<T> exceptionHandler, EventHandler<T>... handlers) {
        processEvent(factory, ringBuffer -> publisher.accept(ringBuffer::publishEvent), exceptionHandler, handlers);
    }

    @SafeVarargs
    public final void processOneArgEvent(EventFactory<T> factory, Consumer<OneArgEventTranslator<T>> publisher, ObjLongConsumer<T> exceptionHandler, EventHandler<T>... handlers) {
        processEvent(factory, ringBuffer -> publisher.accept(ringBuffer::publishEvent), exceptionHandler, handlers);
    }

    @SafeVarargs
    public final void processTwoArgEvent(EventFactory<T> factory, Consumer<TwoArgEventTranslator<T>> publisher, ObjLongConsumer<T> exceptionHandler, EventHandler<T>... handlers) {
        processEvent(factory, ringBuffer -> publisher.accept(ringBuffer::publishEvent), exceptionHandler, handlers);
    }

    @SafeVarargs
    public final void processThreeArgEvent(EventFactory<T> factory, Consumer<ThreeArgEventTranslator<T>> publisher, ObjLongConsumer<T> exceptionHandler, EventHandler<T>... handlers) {
        processEvent(factory, ringBuffer -> publisher.accept(ringBuffer::publishEvent), exceptionHandler, handlers);
    }

    @SafeVarargs
    public final void processVarargEvent(EventFactory<T> factory, Consumer<VarargEventTranslator<T>> publisher, ObjLongConsumer<T> exceptionHandler, EventHandler<T>... handlers) {
        processEvent(factory, ringBuffer -> publisher.accept(ringBuffer::publishEvent), exceptionHandler, handlers);
    }

    /**
     * 单线程消费者版本
     */
    @SafeVarargs
    private final void processEvent(EventFactory<T> factory, Consumer<RingBuffer<T>> eventProducer, ObjLongConsumer<T> exceptionHandler, EventHandler<T>... handlers) {
        Disruptor<T> disruptor = new Disruptor<>(factory, defaultBufferSize, DaemonThreadFactory.INSTANCE, ProducerType.SINGLE, new BlockingWaitStrategy());
        disruptor.handleEventsWith(handlers).then((t, sequence, endOfBatch) -> t = null);
        disruptor.setDefaultExceptionHandler(new ExceptionHandler<T>() {
            @Override
            public void handleEventException(Throwable throwable, long sequence, T t) {
                log.error(StringUtils.joinWith("|", "EventHandler", sequence, t), throwable);
                exceptionHandler.accept(t, sequence);
            }

            @Override
            public void handleOnStartException(Throwable throwable) {
                log.error("EventHandler Exception during onStart()", throwable);
            }

            @Override
            public void handleOnShutdownException(Throwable throwable) {
                log.error("EventHandler Exception during onShutdown()", throwable);
            }
        });
        try {
            eventProducer.accept(disruptor.start());
        } finally {
            disruptor.shutdown();
        }
    }

    /**
     * 多线程消费者版本
     */
    @SafeVarargs
    private final void processEvent(EventFactory<T> factory, Consumer<RingBuffer<T>> eventProducer, ObjLongConsumer<T> exceptionHandler, WorkHandler<T>... handlers) {
        Disruptor<T> disruptor = new Disruptor<>(factory, defaultBufferSize, DaemonThreadFactory.INSTANCE, ProducerType.SINGLE, new BlockingWaitStrategy());
        disruptor.handleEventsWithWorkerPool(handlers).then((t, sequence, endOfBatch) -> t = null);
        disruptor.setDefaultExceptionHandler(new ExceptionHandler<T>() {
            @Override
            public void handleEventException(Throwable throwable, long sequence, T t) {
                log.error(StringUtils.joinWith("|", "WorkHandler", sequence, t), throwable);
                exceptionHandler.accept(t, sequence);
            }

            @Override
            public void handleOnStartException(Throwable throwable) {
                log.error("WorkHandler Exception during onStart()", throwable);
            }

            @Override
            public void handleOnShutdownException(Throwable throwable) {
                log.error("WorkHandler Exception during onShutdown()", throwable);
            }
        });
        try {
            eventProducer.accept(disruptor.start());
        } finally {
            disruptor.shutdown();
        }
    }
}