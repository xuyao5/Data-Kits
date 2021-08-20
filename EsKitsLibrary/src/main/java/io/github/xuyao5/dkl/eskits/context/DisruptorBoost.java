package io.github.xuyao5.dkl.eskits.context;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import io.github.xuyao5.dkl.eskits.context.disruptor.*;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 4/07/21 20:33
 * @apiNote DisruptorBoost
 * @implNote DisruptorBoost
 */
@Slf4j
@Builder(builderMethodName = "context", buildMethodName = "create")
public final class DisruptorBoost<T> {

    @Builder.Default
    private int bufferSize = 1 << 10;

    @SafeVarargs
    public final void processZeroArg(Consumer<EventZeroArg<T>> eventZeroArgConsumer, BiConsumer<Long, ? super T> errorConsumer, EventFactory<T> eventFactory, boolean isShutdownFinally, EventHandler<? super T>... handlers) {
        process(ringBuffer -> eventZeroArgConsumer.accept(ringBuffer::publishEvent), errorConsumer, eventFactory, isShutdownFinally, handlers);
    }

    @SafeVarargs
    public final void processOneArg(Consumer<EventOneArg<T>> eventOneArgConsumer, BiConsumer<Long, ? super T> errorConsumer, EventFactory<T> eventFactory, boolean isShutdownFinally, EventHandler<? super T>... handlers) {
        process(ringBuffer -> eventOneArgConsumer.accept(ringBuffer::publishEvent), errorConsumer, eventFactory, isShutdownFinally, handlers);
    }

    @SafeVarargs
    public final void processTwoArg(Consumer<EventTwoArg<T>> eventTwoArgConsumer, BiConsumer<Long, ? super T> errorConsumer, EventFactory<T> eventFactory, boolean isShutdownFinally, EventHandler<? super T>... handlers) {
        process(ringBuffer -> eventTwoArgConsumer.accept(ringBuffer::publishEvent), errorConsumer, eventFactory, isShutdownFinally, handlers);
    }

    @SafeVarargs
    public final void processThreeArg(Consumer<EventThreeArg<T>> eventThreeArgConsumer, BiConsumer<Long, ? super T> errorConsumer, EventFactory<T> eventFactory, boolean isShutdownFinally, EventHandler<? super T>... handlers) {
        process(ringBuffer -> eventThreeArgConsumer.accept(ringBuffer::publishEvent), errorConsumer, eventFactory, isShutdownFinally, handlers);
    }

    @SafeVarargs
    public final void processVararg(Consumer<EventVararg<T>> eventVarargConsumer, BiConsumer<Long, ? super T> errorConsumer, EventFactory<T> eventFactory, boolean isShutdownFinally, EventHandler<? super T>... handlers) {
        process(ringBuffer -> eventVarargConsumer.accept(ringBuffer::publishEvent), errorConsumer, eventFactory, isShutdownFinally, handlers);
    }

    @SafeVarargs
    private final void process(Consumer<RingBuffer<T>> ringBufferConsumer, BiConsumer<Long, ? super T> errorConsumer, EventFactory<T> eventFactory, boolean isShutdownFinally, EventHandler<? super T>... handlers) {
        Disruptor<T> disruptor = new Disruptor<>(eventFactory, bufferSize, DaemonThreadFactory.INSTANCE, ProducerType.SINGLE, new BlockingWaitStrategy());
        disruptor.handleEventsWith(handlers);
        disruptor.setDefaultExceptionHandler(new ExceptionHandler<T>() {
            @Override
            public void handleEventException(Throwable throwable, long sequence, T t) {
                log.error(StringUtils.join(sequence, '|', t), throwable);
                errorConsumer.accept(sequence, t);
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
            ringBufferConsumer.accept(disruptor.getRingBuffer());
        } finally {
            if (isShutdownFinally) {
                disruptor.shutdown();
            }
        }
    }
}