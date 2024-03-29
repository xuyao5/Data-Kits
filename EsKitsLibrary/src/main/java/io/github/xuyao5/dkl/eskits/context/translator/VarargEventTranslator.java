package io.github.xuyao5.dkl.eskits.context.translator;

import com.lmax.disruptor.EventTranslatorVararg;
import lombok.NonNull;

/**
 * @author Thomas.XU(xuyao)
 * @version 5/07/21 00:52
 */
@FunctionalInterface
public interface VarargEventTranslator<E> {

    void translate(@NonNull EventTranslatorVararg<E> translator, @NonNull Object... var4);
}
