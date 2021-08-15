package io.github.xuyao5.dkl.eskits.context.disruptor;

import com.lmax.disruptor.EventTranslatorTwoArg;
import lombok.NonNull;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 5/07/21 00:52
 * @apiNote EventTwoArg
 * @implNote EventTwoArg
 */
@FunctionalInterface
public interface EventTwoArg<E> {

    <A, B> void translate(@NonNull EventTranslatorTwoArg<E, A, B> translator, @NonNull A arg0, @NonNull B arg1);
}
