package io.github.xuyao5.dkl.eskits.support.boost;

import io.github.xuyao5.dkl.eskits.support.general.IndexSupporter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;

import javax.validation.constraints.NotNull;

import static io.github.xuyao5.dkl.eskits.consts.SettingConst.INDEX_NUMBER_OF_REPLICAS;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 18/03/21 21:57
 * @apiNote SettingsSupporter
 * @implNote SettingsSupporter
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SettingsSupporter {

    public static SettingsSupporter getInstance() {
        return SettingsSupporter.SingletonHolder.INSTANCE;
    }

    public boolean updateNumberOfReplicas(@NotNull RestHighLevelClient client, @NotNull String index, int replicas) {
        IndexSupporter indexSupporter = IndexSupporter.getInstance();
        int numberOfReplicas = indexSupporter.getSettings(client, index).getIndexToSettings().get(index).getAsInt(INDEX_NUMBER_OF_REPLICAS.getName(), replicas);
        if (numberOfReplicas != replicas) {
            return indexSupporter.putSettings(client, Settings.builder().put(INDEX_NUMBER_OF_REPLICAS.getName(), replicas).build(), index).isAcknowledged();
        }
        return false;
    }

    private static class SingletonHolder {
        private static final SettingsSupporter INSTANCE = new SettingsSupporter();
    }
}
