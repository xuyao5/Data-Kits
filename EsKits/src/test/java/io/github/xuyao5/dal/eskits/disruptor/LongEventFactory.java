package io.github.xuyao5.dal.eskits.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 26/09/20 19:42
 * @apiNote TODO 这里输入文件说明
 * @implNote TODO 这里输入实现说明
 */
public final class LongEventFactory implements EventFactory<LongEvent> {

    @Override
    public LongEvent newInstance() {
        return new LongEvent();
    }
}
