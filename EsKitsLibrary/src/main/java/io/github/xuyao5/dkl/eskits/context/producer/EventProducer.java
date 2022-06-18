package io.github.xuyao5.dkl.eskits.context.producer;

import com.lmax.disruptor.RingBuffer;

/**
 * @author Thomas.XU(xuyao)
 * @version 18/06/22 16:17
 */
@FunctionalInterface
public interface EventProducer<T> {

    void produce(RingBuffer<T> ringBuffer);
}
