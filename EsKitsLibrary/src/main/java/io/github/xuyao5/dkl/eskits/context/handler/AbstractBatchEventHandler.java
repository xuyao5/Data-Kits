package io.github.xuyao5.dkl.eskits.context.handler;

import com.lmax.disruptor.LifecycleAware;
import com.lmax.disruptor.Sequence;
import com.lmax.disruptor.SequenceReportingEventHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author Thomas.XU(xuyao)
 * @version 18/06/22 21:32
 */
@Slf4j
public abstract class AbstractBatchEventHandler<T> implements SequenceReportingEventHandler<T>, LifecycleAware {

    private final int THRESHOLD;

    private LongAdder counter;//计数器

    private List<T> list;

    private int batchRemaining;

    private Sequence sequenceCallback;

    protected AbstractBatchEventHandler(int threshold) {
        THRESHOLD = threshold;
    }

    protected abstract void processEvent(List<T> list) throws Exception;

    @Override
    public void setSequenceCallback(Sequence sequence) {
        sequenceCallback = sequence;
    }

    @Override
    public void onEvent(T t, long sequence, boolean endOfBatch) throws Exception {
        boolean logicalChunkOfWorkComplete = --batchRemaining == 0;

        try {
            list.add(t);
        } finally {
            if (logicalChunkOfWorkComplete) {
                sequenceCallback.set(sequence);
            }
        }

        if (logicalChunkOfWorkComplete || endOfBatch) {
            try {
                processEvent(list);
                counter.add(list.size());
            } finally {
                if (!list.isEmpty()) {
                    list.clear();
                }
                batchRemaining = THRESHOLD;
            }
        }
    }

    @Override
    public void onStart() {
        counter = new LongAdder();
        list = new LinkedList<>();
        batchRemaining = THRESHOLD;
        log.info("Start ThreadId:{}, THRESHOLD [{}]", Thread.currentThread().getId(), THRESHOLD);
    }

    @Override
    public void onShutdown() {
        list = null;
        log.info("Shutdown ThreadId:{}, total [{}]", Thread.currentThread().getId(), counter.intValue());
    }
}