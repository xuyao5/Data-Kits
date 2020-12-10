package io.github.xuyao5.datakitsserver.disruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 26/09/20 22:06
 * @apiNote TODO 这里输入文件说明
 * @implNote TODO 这里输入实现说明
 */
public class DisruptorTest {

    @SneakyThrows
    @Test
    void test() {
        // Specify the size of the ring buffer, must be power of 2.
        int bufferSize = 1 << 10;

        // Construct the Disruptor
        Disruptor<LongEvent> disruptor = new Disruptor<>(LongEvent::new, bufferSize, DaemonThreadFactory.INSTANCE, ProducerType.SINGLE, new BlockingWaitStrategy());

        // Connect the handler
        disruptor.handleEventsWith((event, sequence, endOfBatch) -> System.out.println("Event: " + event));

        // Start the Disruptor, starts all threads running
        disruptor.start();

        // Get the ring buffer from the Disruptor to be used for publishing.
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();

        ByteBuffer bb = ByteBuffer.allocate(8);
        for (long l = 0; l <= 1000000; l++) {
            bb.putLong(0, l);
            ringBuffer.publishEvent((event, sequence, buffer) -> event.setValue(buffer.getLong(0)), bb);
//            Thread.sleep(1000);
        }
    }
}
