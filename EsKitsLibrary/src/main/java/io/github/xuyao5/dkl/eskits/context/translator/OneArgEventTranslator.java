package io.github.xuyao5.dkl.eskits.context.translator;

import com.lmax.disruptor.EventTranslatorOneArg;
import lombok.NonNull;

/**
 * @author Thomas.XU(xuyao)
 * @version 5/07/21 00:52
 */
@FunctionalInterface
public interface OneArgEventTranslator<E> {

    <A> void translate(@NonNull EventTranslatorOneArg<E, A> translator, @NonNull A arg0);
}
