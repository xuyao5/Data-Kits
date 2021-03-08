package io.github.xuyao5.dkl.eskits.abstr;

import io.github.xuyao5.dkl.eskits.schema.StandardDocument;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 8/03/21 19:07
 * @apiNote DocumentFactory
 * @implNote DocumentFactory
 */
@FunctionalInterface
public interface DocumentFactory<T extends StandardDocument> {

    T newDocument();
}
