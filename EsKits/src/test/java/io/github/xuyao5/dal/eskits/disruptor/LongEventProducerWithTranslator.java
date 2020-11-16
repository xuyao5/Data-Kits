package io.github.xuyao5.dal.eskits.disruptor;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 26/09/20 20:01
 * @apiNote TODO 这里输入文件说明
 * @implNote TODO 这里输入实现说明
 */
public final class LongEventProducerWithTranslator {

    private static final EventTranslatorOneArg<LongEvent, ByteBuffer> TRANSLATOR = (event, sequence, bb) -> event.setValue(bb.getLong(0));

    private final RingBuffer<LongEvent> ringBuffer;

    public LongEventProducerWithTranslator(RingBuffer<LongEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void onData(ByteBuffer bb) {
        ringBuffer.publishEvent(TRANSLATOR, bb);
    }
}
