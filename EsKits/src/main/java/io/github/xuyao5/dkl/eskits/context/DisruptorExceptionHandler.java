package io.github.xuyao5.dkl.eskits.context;

import com.lmax.disruptor.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 8/05/21 23:25
 * @apiNote DisruptorExceptionHandler
 * @implNote DisruptorExceptionHandler
 */
@Slf4j
public final class DisruptorExceptionHandler<T> implements ExceptionHandler<T> {

    @Override
    public void handleEventException(Throwable ex, long sequence, T event) {
        log.error("Exception processing: " + sequence + " " + event, ex);
    }

    @Override
    public void handleOnStartException(Throwable ex) {
        log.error("Exception during onStart()", ex);
    }

    @Override
    public void handleOnShutdownException(Throwable ex) {
        log.error("Exception during onShutdown()", ex);
    }
}
