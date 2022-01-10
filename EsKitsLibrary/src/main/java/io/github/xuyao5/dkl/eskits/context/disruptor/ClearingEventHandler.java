package io.github.xuyao5.dkl.eskits.context.disruptor;

import com.lmax.disruptor.EventHandler;

/**
 * @author Thomas.XU(xuyao)
 * @version 9/01/22 22:40
 */
public class ClearingEventHandler<T> implements EventHandler<ObjectEvent<T>> {

    public void onEvent(ObjectEvent<T> event, long sequence, boolean endOfBatch) {
        event.clear();
    }
}