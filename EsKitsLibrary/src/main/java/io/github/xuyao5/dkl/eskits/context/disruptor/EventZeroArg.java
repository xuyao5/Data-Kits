package io.github.xuyao5.dkl.eskits.context.disruptor;

import com.lmax.disruptor.EventTranslator;
import lombok.NonNull;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 5/07/21 00:52
 * @apiNote EventZeroArg
 * @implNote EventZeroArg
 */
@FunctionalInterface
public interface EventZeroArg<E> {

    void translate(@NonNull EventTranslator<E> translator);
}
