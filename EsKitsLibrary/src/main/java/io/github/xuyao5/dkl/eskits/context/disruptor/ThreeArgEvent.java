package io.github.xuyao5.dkl.eskits.context.disruptor;

import com.lmax.disruptor.EventTranslatorThreeArg;
import lombok.NonNull;

/**
 * @author Thomas.XU(xuyao)
 * @version 5/07/21 00:52
 */
@FunctionalInterface
public interface ThreeArgEvent<E> {

    <A, B, C> void translate(@NonNull EventTranslatorThreeArg<E, A, B, C> translator, @NonNull A arg0, @NonNull B arg1, @NonNull C arg2);
}
