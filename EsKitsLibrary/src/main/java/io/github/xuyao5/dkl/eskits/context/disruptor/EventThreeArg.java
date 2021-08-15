package io.github.xuyao5.dkl.eskits.context.disruptor;

import com.lmax.disruptor.EventTranslatorThreeArg;
import lombok.NonNull;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 5/07/21 00:52
 * @apiNote EventThreeArg
 * @implNote EventThreeArg
 */
@FunctionalInterface
public interface EventThreeArg<E> {

    <A, B, C> void translate(@NonNull EventTranslatorThreeArg<E, A, B, C> translator, @NonNull A arg0, @NonNull B arg1, @NonNull C arg2);
}
