package io.github.xuyao5.dkl.eskits.context.handler;

/**
 * @author Thomas.XU(xuyao)
 * @version 18/06/22 16:41
 */
@FunctionalInterface
public interface EventExceptionHandler<T> {

    void handle(long sequence, T t);
}
