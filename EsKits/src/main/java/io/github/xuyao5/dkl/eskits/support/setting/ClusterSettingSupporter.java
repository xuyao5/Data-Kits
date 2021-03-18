package io.github.xuyao5.dkl.eskits.support.setting;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 18/03/21 21:33
 * @apiNote ClusterSettingSupporter
 * @implNote ClusterSettingSupporter
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ClusterSettingSupporter {

    public static final ClusterSettingSupporter getInstance() {
        return ClusterSettingSupporter.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final ClusterSettingSupporter INSTANCE = new ClusterSettingSupporter();
    }
}
