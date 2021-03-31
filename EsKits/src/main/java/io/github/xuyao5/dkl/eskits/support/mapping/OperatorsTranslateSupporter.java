package io.github.xuyao5.dkl.eskits.support.mapping;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 31/03/21 20:21
 * @apiNote OperatorsTranslateSupporter
 * @implNote OperatorsTranslateSupporter
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class OperatorsTranslateSupporter {

    public static final OperatorsTranslateSupporter getInstance() {
        return OperatorsTranslateSupporter.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final OperatorsTranslateSupporter INSTANCE = new OperatorsTranslateSupporter();
    }
}
