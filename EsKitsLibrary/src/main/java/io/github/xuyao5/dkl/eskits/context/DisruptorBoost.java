package io.github.xuyao5.dkl.eskits.context;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import io.github.xuyao5.dkl.eskits.context.disruptor.*;
import lombok.Builder;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.function.Consumer;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 4/07/21 20:33
 * @apiNote DisruptorBoost
 * @implNote DisruptorBoost
 */
@Slf4j
@Builder(builderMethodName = "factory", buildMethodName = "create")
public final class DisruptorBoost<T> {

    @Builder.Default
    private final int bufferSize = 1 << 10;

    @SafeVarargs
    public final void processZeroArg(@NonNull Consumer<EventZeroArg<T>> eventZeroArgConsumer, @NonNull Consumer<? super T> errorConsumer, @NonNull EventFactory<T> eventFactory, @NonNull EventHandler<? super T>... handlers) {
        process(ringBuffer -> eventZeroArgConsumer.accept(ringBuffer::publishEvent), errorConsumer, eventFactory, handlers);
    }

    @SafeVarargs
    public final void processOneArg(@NonNull Consumer<EventOneArg<T>> eventOneArgConsumer, @NonNull Consumer<? super T> errorConsumer, @NonNull EventFactory<T> eventFactory, @NonNull EventHandler<? super T>... handlers) {
        process(ringBuffer -> eventOneArgConsumer.accept(ringBuffer::publishEvent), errorConsumer, eventFactory, handlers);
    }

    @SafeVarargs
    public final void processTwoArg(@NonNull Consumer<EventTwoArg<T>> eventTwoArgConsumer, @NonNull Consumer<? super T> errorConsumer, @NonNull EventFactory<T> eventFactory, @NonNull EventHandler<? super T>... handlers) {
        process(ringBuffer -> eventTwoArgConsumer.accept(ringBuffer::publishEvent), errorConsumer, eventFactory, handlers);
    }

    @SafeVarargs
    public final void processThreeArg(@NonNull Consumer<EventThreeArg<T>> eventThreeArgConsumer, @NonNull Consumer<? super T> errorConsumer, @NonNull EventFactory<T> eventFactory, @NonNull EventHandler<? super T>... handlers) {
        process(ringBuffer -> eventThreeArgConsumer.accept(ringBuffer::publishEvent), errorConsumer, eventFactory, handlers);
    }

    @SafeVarargs
    public final void processVararg(@NonNull Consumer<EventVararg<T>> eventVarargConsumer, @NonNull Consumer<? super T> errorConsumer, @NonNull EventFactory<T> eventFactory, @NonNull EventHandler<? super T>... handlers) {
        process(ringBuffer -> eventVarargConsumer.accept(ringBuffer::publishEvent), errorConsumer, eventFactory, handlers);
    }

    @SafeVarargs
    private final void process(Consumer<RingBuffer<T>> ringBufferConsumer, Consumer<? super T> errorConsumer, EventFactory<T> eventFactory, EventHandler<? super T>... handlers) {
        Disruptor<T> disruptor = new Disruptor<>(eventFactory, bufferSize, DaemonThreadFactory.INSTANCE, ProducerType.SINGLE, new BlockingWaitStrategy());
        disruptor.handleEventsWith(handlers);
        disruptor.setDefaultExceptionHandler(new ExceptionHandler<T>() {
            @Override
            public void handleEventException(Throwable throwable, long sequence, T t) {
                log.error(StringUtils.join(sequence, '|', t), throwable);
                errorConsumer.accept(t);
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
            disruptor.shutdown();
        }
    }
}