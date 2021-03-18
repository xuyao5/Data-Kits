package io.github.xuyao5.dkl.eskits.support.concurrency;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 18/03/21 21:57
 * @apiNote OptimisticControlSupporter
 * @implNote OptimisticControlSupporter
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class OptimisticControlSupporter {

    public static final OptimisticControlSupporter getInstance() {
        return OptimisticControlSupporter.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final OptimisticControlSupporter INSTANCE = new OptimisticControlSupporter();
    }
}
