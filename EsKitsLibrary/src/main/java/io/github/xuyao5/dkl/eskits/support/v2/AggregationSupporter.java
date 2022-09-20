package io.github.xuyao5.dkl.eskits.support.v2;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Thomas.XU(xuyao)
 * @version 21/09/22 00:31
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AggregationSupporter {

    public static AggregationSupporter getInstance() {
        return AggregationSupporter.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final AggregationSupporter INSTANCE = new AggregationSupporter();
    }
}