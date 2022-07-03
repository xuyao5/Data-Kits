package io.github.xuyao5.dkl.eskits.context.translator;

import com.lmax.disruptor.EventTranslator;
import lombok.NonNull;

/**
 * @author Thomas.XU(xuyao)
 * @version 5/07/21 00:52
 */
@FunctionalInterface
public interface ZeroArgEventTranslator<E> {

    void translate(@NonNull EventTranslator<E> translator);
}
