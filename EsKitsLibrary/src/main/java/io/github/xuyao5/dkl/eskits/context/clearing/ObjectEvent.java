package io.github.xuyao5.dkl.eskits.context.clearing;

/**
 * @author Thomas.XU(xuyao)
 * @version 9/01/22 22:40
 */
class ObjectEvent<T> {

    T val;

    void clear() {
        val = null;
    }
}