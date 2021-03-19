package io.github.xuyao5.dkl.eskits.support.online;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 19/03/21 23:25
 * @apiNote JoinSupporter
 * @implNote JoinSupporter
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SearchPlusSupporter {

    public static final SearchPlusSupporter getInstance() {
        return SearchPlusSupporter.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final SearchPlusSupporter INSTANCE = new SearchPlusSupporter();
    }
}
