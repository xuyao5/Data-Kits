package io.github.xuyao5.dkl.eskits.helper;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
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
public final class DisruptorHelper<T> {

    private final int BUFFER_SIZE;

    public DisruptorHelper(int bufferSize) {
        BUFFER_SIZE = bufferSize;
    }

    public DisruptorHelper() {
        this(1 << 10);
    }

    @SafeVarargs
    public final <A> void process(Consumer<Consumer<EventTranslatorOneArg<T, A>>> function, Consumer<? super T> errorConsumer, @NonNull EventFactory<T> eventFactory, @NonNull A arg0, @NonNull EventHandler<? super T>... handlers) {
        process(ringBuffer -> {
            function.accept(translator -> {
                ringBuffer.publishEvent(translator, arg0);
            });
        }, errorConsumer, eventFactory, handlers);
    }

    @SafeVarargs
    public final <A, B> void process(Consumer<Consumer<EventTranslatorTwoArg<T, A, B>>> function, Consumer<? super T> errorConsumer, @NonNull EventFactory<T> eventFactory, @NonNull A arg0, @NonNull B arg1, @NonNull EventHandler<? super T>... handlers) {
        process(ringBuffer -> {
            function.accept(translator -> {
                ringBuffer.publishEvent(translator, arg0, arg1);
            });
        }, errorConsumer, eventFactory, handlers);
    }

    @SafeVarargs
    public final <A, B, C> void process(Consumer<Consumer<EventTranslatorThreeArg<T, A, B, C>>> function, Consumer<? super T> errorConsumer, @NonNull EventFactory<T> eventFactory, @NonNull A arg0, @NonNull B arg1, @NonNull C arg2, @NonNull EventHandler<? super T>... handlers) {
        process(ringBuffer -> {
            function.accept(translator -> {
                ringBuffer.publishEvent(translator, arg0, arg1, arg2);
            });
        }, errorConsumer, eventFactory, handlers);
    }

    @SafeVarargs
    private final void process(Consumer<RingBuffer<T>> ringBufferConsumer, Consumer<? super T> errorConsumer, EventFactory<T> eventFactory, EventHandler<? super T>... handlers) {
        Disruptor<T> disruptor = new Disruptor<>(eventFactory, BUFFER_SIZE, DaemonThreadFactory.INSTANCE, ProducerType.SINGLE, new BlockingWaitStrategy());
        disruptor.handleEventsWith(handlers);
        disruptor.setDefaultExceptionHandler(new ExceptionHandler<T>() {
            @Override
            public void handleEventException(Throwable throwable, long sequence, T t) {
                log.error(MyStringUtils.join(sequence, "|", t), throwable);
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
        ringBufferConsumer.accept(disruptor.start());
        disruptor.shutdown();
    }
}