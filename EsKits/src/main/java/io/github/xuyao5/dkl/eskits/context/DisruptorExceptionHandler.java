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
    public void handleEventException(Throwable throwable, long l, T t) {
        log.error(t.toString(), throwable);
    }

    @Override
    public void handleOnStartException(Throwable throwable) {
        log.error("HandleOnStartException", throwable);
    }

    @Override
    public void handleOnShutdownException(Throwable throwable) {
        log.error("HandleOnShutdownException", throwable);
    }
}
