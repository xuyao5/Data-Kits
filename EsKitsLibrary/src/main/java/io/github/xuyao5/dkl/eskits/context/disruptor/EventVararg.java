package io.github.xuyao5.dkl.eskits.context.disruptor;

import com.lmax.disruptor.EventTranslatorVararg;
import lombok.NonNull;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 5/07/21 00:52
 * @apiNote EventVararg
 * @implNote EventVararg
 */
@FunctionalInterface
public interface EventVararg<E> {

    void translate(@NonNull EventTranslatorVararg<E> translator, @NonNull Object... var4);
}
