package io.github.xuyao5.dkl.eskits.support.alias;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 18/03/21 21:57
 * @apiNote OptimisticControlSupporter
 * @implNote OptimisticControlSupporter
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AliasesSupporter {

    public static final AliasesSupporter getInstance() {
        return AliasesSupporter.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final AliasesSupporter INSTANCE = new AliasesSupporter();
    }
}
