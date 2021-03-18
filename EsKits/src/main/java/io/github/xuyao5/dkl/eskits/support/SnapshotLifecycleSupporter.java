package io.github.xuyao5.dkl.eskits.support;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 19/02/21 22:18
 * @apiNote EsSnapshotLifecycleSupporter
 * @implNote EsSnapshotLifecycleSupporter
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SnapshotLifecycleSupporter {

    public static final SnapshotLifecycleSupporter getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final SnapshotLifecycleSupporter INSTANCE = new SnapshotLifecycleSupporter();
    }
}
