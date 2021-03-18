package io.github.xuyao5.dkl.eskits.support.setting;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 18/03/21 21:32
 * @apiNote IndexSettingSupporter
 * @implNote IndexSettingSupporter
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class IndexSettingSupporter {

    public static final IndexSettingSupporter getInstance() {
        return IndexSettingSupporter.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final IndexSettingSupporter INSTANCE = new IndexSettingSupporter();
    }
}
