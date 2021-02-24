package io.github.xuyao5.dkl.common.context;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 1/12/20 09:47
 * @apiNote DisruptorHandler
 * @implNote DisruptorHandler
 */
@Slf4j
public final class StandardFileLineDisruptor {

    private final int bufferSize;

    public StandardFileLineDisruptor(int powerOf2) {
        bufferSize = 1 << powerOf2;
    }

    public StandardFileLineDisruptor() {
        this(10);
    }

    public void call(EventHandler<StandardFileLine>... handlers) {
        Disruptor<StandardFileLine> disruptor = new Disruptor<>(StandardFileLine::new, bufferSize, DaemonThreadFactory.INSTANCE, ProducerType.SINGLE, new BlockingWaitStrategy());
        disruptor.handleEventsWith(handlers);//(event, sequence, endOfBatch) -> System.out.println("Event: " + event)
        RingBuffer<StandardFileLine> ringBuffer = disruptor.start();
        disruptor.shutdown();
    }

    @Data
    public final class StandardFileLine {

        private int lineNo;//行号
        private String lineRecord;//行记录
    }
}
