package io.github.xuyao5.dkl.eskits.context;

import com.lmax.disruptor.LifecycleAware;
import com.lmax.disruptor.Sequence;
import com.lmax.disruptor.SequenceReportingEventHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Thomas.XU(xuyao)
 * @version 18/06/22 21:32
 */
@Slf4j
public abstract class AbstractSequenceReporting<T> implements SequenceReportingEventHandler<T>, LifecycleAware {

    private final int THRESHOLD = DisruptorBoost.getDefaultBufferSize();

    private final AtomicLong COUNTER = new AtomicLong();

    private List<T> list;

    private int batchRemaining;

    private Sequence sequenceCallback;

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
                log.info("Process {}@{} current/total[{}/{}]", getClass().getSimpleName(), Thread.currentThread().getId(), list.size(), COUNTER.addAndGet(list.size()));
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
        list = new ArrayList<>(THRESHOLD);
        batchRemaining = THRESHOLD;
        log.info("Start {}@{}, THRESHOLD [{}]", getClass().getSimpleName(), Thread.currentThread().getId(), THRESHOLD);
    }

    @Override
    public void onShutdown() {
        list = null;
        log.info("Shutdown {}@{}, total [{}]", getClass().getSimpleName(), Thread.currentThread().getId(), COUNTER.get());
    }
}