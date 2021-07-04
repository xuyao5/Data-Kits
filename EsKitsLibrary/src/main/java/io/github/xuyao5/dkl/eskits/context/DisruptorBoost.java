package io.github.xuyao5.dkl.eskits.context;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import io.github.xuyao5.dkl.eskits.context.disruptor.EventOneArg;
import io.github.xuyao5.dkl.eskits.context.disruptor.EventThreeArg;
import io.github.xuyao5.dkl.eskits.context.disruptor.EventTwoArg;
import io.github.xuyao5.dkl.eskits.util.MyStringUtils;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 4/07/21 20:33
 * @apiNote DisruptorHelper
 * @implNote DisruptorHelper
 */
@Slf4j
public final class DisruptorBoost<T> {

    private final int BUFFER_SIZE;

    public DisruptorBoost(int bufferSize) {
        BUFFER_SIZE = bufferSize;
    }

    public DisruptorBoost() {
        this(1 << 10);
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
    private final void process(Consumer<RingBuffer<T>> ringBufferConsumer, Consumer<? super T> errorConsumer, EventFactory<T> eventFactory, EventHandler<? super T>... handlers) {
        Disruptor<T> disruptor = new Disruptor<>(eventFactory, BUFFER_SIZE, DaemonThreadFactory.INSTANCE, ProducerType.SINGLE, new BlockingWaitStrategy());
        disruptor.handleEventsWith(handlers);
        disruptor.setDefaultExceptionHandler(new ExceptionHandler<T>() {
            @Override
            public void handleEventException(Throwable throwable, long sequence, T t) {
                log.error(MyStringUtils.join(sequence, '|', t), throwable);
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
        disruptor.start();
        ringBufferConsumer.accept(disruptor.getRingBuffer());
        disruptor.shutdown();
    }
}