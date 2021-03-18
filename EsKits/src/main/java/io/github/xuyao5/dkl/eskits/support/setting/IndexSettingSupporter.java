package io.github.xuyao5.dkl.eskits.support.setting;

import io.github.xuyao5.dkl.eskits.support.IndexSupporter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;

import javax.validation.constraints.NotNull;

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

    public boolean setupNumberOfReplicas(@NotNull RestHighLevelClient client, int replicas, @NotNull String... indices) {
        return IndexSupporter.getInstance().putSettings(client, Settings.builder().put("index.number_of_replicas", replicas).build(), indices).isAcknowledged();
    }

    public boolean setupRefreshInterval(@NotNull RestHighLevelClient client, int interval, @NotNull String... indices) {
        return IndexSupporter.getInstance().putSettings(client, Settings.builder().put("index.refresh_interval", interval).build(), indices).isAcknowledged();
    }

    public boolean setupMaxResultWindow(@NotNull RestHighLevelClient client, int window, @NotNull String... indices) {
        return IndexSupporter.getInstance().putSettings(client, Settings.builder().put("index.max_result_window", window).build(), indices).isAcknowledged();
    }

    public boolean setupMergeSchedulerMaxThreadCount(@NotNull RestHighLevelClient client, int count, @NotNull String... indices) {
        return IndexSupporter.getInstance().putSettings(client, Settings.builder().put("index.merge.scheduler.max_thread_count", count).build(), indices).isAcknowledged();
    }

    public boolean setupTranslogSyncInterval(@NotNull RestHighLevelClient client, int interval, @NotNull String... indices) {
        return IndexSupporter.getInstance().putSettings(client, Settings.builder().put("index.translog.sync_interval", interval).build(), indices).isAcknowledged();
    }

    private static class SingletonHolder {
        private static final IndexSettingSupporter INSTANCE = new IndexSettingSupporter();
    }
}
