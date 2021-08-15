package io.github.xuyao5.dkl.eskits.context.disruptor;

import com.lmax.disruptor.EventTranslatorOneArg;
import lombok.NonNull;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 5/07/21 00:52
 * @apiNote EventOneArg
 * @implNote EventOneArg
 */
@FunctionalInterface
public interface EventOneArg<E> {

    <A> void translate(@NonNull EventTranslatorOneArg<E, A> translator, @NonNull A arg0);
}
