package io.github.xuyao5.dkl.eskits.context.producer;

import io.github.xuyao5.dkl.eskits.context.translator.ThreeArgEventTranslator;
import io.github.xuyao5.dkl.eskits.context.translator.TwoArgEventTranslator;

/**
 * @author Thomas.XU(xuyao)
 * @version 18/06/22 17:36
 */
public interface EventPublisher<T> {

    void publish(TwoArgEventTranslator<T> t);

    void publish(ThreeArgEventTranslator<T> t);

}
