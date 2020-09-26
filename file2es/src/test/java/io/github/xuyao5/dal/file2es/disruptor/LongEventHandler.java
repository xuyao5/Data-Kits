package io.github.xuyao5.dal.file2es.disruptor;

import com.lmax.disruptor.EventHandler;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 26/09/20 19:43
 * @apiNote TODO 这里输入文件说明
 * @implNote TODO 这里输入实现说明
 */
public final class LongEventHandler implements EventHandler<LongEvent> {

    @Override
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) {
        System.out.println("Event: " + event);
    }
}
