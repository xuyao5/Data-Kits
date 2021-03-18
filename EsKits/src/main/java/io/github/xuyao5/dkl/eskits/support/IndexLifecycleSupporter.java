package io.github.xuyao5.dkl.eskits.support;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 7/05/20 12:03
 * @apiNote EsIndexLifecycleSupporter
 * @implNote EsIndexLifecycleSupporter
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class IndexLifecycleSupporter {

    public static final IndexLifecycleSupporter getInstance() {
        return IndexLifecycleSupporter.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final IndexLifecycleSupporter INSTANCE = new IndexLifecycleSupporter();
    }
}
