package io.github.xuyao5.dkl.eskits.context;

import com.lmax.disruptor.LifecycleAware;
import com.lmax.disruptor.Sequence;
import com.lmax.disruptor.SequenceReportingEventHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Thomas.XU(xuyao)
 * @version 18/06/22 21:32
 */
public abstract class AbstractSequenceReporting<T> implements SequenceReportingEventHandler<T>, LifecycleAware {

    private final int PROGRESS_THRESHOLD;

    private List<T> list;

    private int batchRemaining;

    private Sequence sequenceCallback;

    protected abstract void processEvent(List<T> list) throws Exception;

    protected AbstractSequenceReporting(int limit) {
        PROGRESS_THRESHOLD = limit;
    }

    @Override
    public void setSequenceCallback(Sequence sequence) {
        sequenceCallback = sequence;
    }

    @Override
    public void onEvent(T t, long sequence, boolean endOfBatch) throws Exception {
        boolean logicalChunkOfWorkComplete = --batchRemaining == 0;
        boolean isBatch = logicalChunkOfWorkComplete || endOfBatch;
        if (logicalChunkOfWorkComplete) {
            sequenceCallback.set(sequence);
        }

        list.add(t);
        if (isBatch) {
            try {
                processEvent(list);
            } finally {
                if (!list.isEmpty()) {
                    list.clear();
                }
            }
        }

        batchRemaining = isBatch ? PROGRESS_THRESHOLD : batchRemaining;
    }

    @Override
    public void onStart() {
        list = new ArrayList<>(PROGRESS_THRESHOLD);
        batchRemaining = PROGRESS_THRESHOLD;
    }

    @Override
    public void onShutdown() {
        list = null;
    }
}