package io.github.xuyao5.dkl.eskits.support.batch;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 28/03/21 21:34
 * @apiNote JoinSupporter
 * @implNote JoinSupporter
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JoinSupporter {

    public static final JoinSupporter getInstance() {
        return JoinSupporter.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final JoinSupporter INSTANCE = new JoinSupporter();
    }
}
