package io.github.xuyao5.dkl.common.disruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import io.github.xuyao5.dkl.common.standard.StandardFileLine;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 1/12/20 09:47
 * @apiNote DisruptorHandler
 * @implNote DisruptorHandler
 */
@Slf4j
public final class DisruptorHandler {

    private final int bufferSize;

    public DisruptorHandler() {
        bufferSize = 1 << 10;
    }

    public Disruptor<StandardFileLine> startStandardFileLineDisruptor() {
        Disruptor<StandardFileLine> disruptor = new Disruptor<>(() -> StandardFileLine.of(), bufferSize, DaemonThreadFactory.INSTANCE, ProducerType.SINGLE, new BlockingWaitStrategy());
        disruptor.handleEventsWith((event, sequence, endOfBatch) -> System.out.println("Event: " + event));
        disruptor.start();
        return disruptor;
    }
}
