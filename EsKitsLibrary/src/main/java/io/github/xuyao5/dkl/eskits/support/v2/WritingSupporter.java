package io.github.xuyao5.dkl.eskits.support.v2;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Thomas.XU(xuyao)
 * @version 21/09/22 00:32
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class WritingSupporter {

    public static WritingSupporter getInstance() {
        return WritingSupporter.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final WritingSupporter INSTANCE = new WritingSupporter();
    }
}