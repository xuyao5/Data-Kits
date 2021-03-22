package io.github.xuyao5.dkl.eskits.support.general;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.cluster.settings.ClusterGetSettingsRequest;
import org.elasticsearch.action.admin.cluster.settings.ClusterGetSettingsResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.cluster.RemoteInfoRequest;
import org.elasticsearch.client.cluster.RemoteInfoResponse;

import javax.validation.constraints.NotNull;

import static org.elasticsearch.client.RequestOptions.DEFAULT;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 10/03/21 14:05
 * @apiNote ClusterSupporter
 * @implNote ClusterSupporter
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ClusterSupporter {

    public static final ClusterSupporter getInstance() {
        return ClusterSupporter.SingletonHolder.INSTANCE;
    }

    /**
     * Cluster Get Settings API
     */
    @SneakyThrows
    public ClusterGetSettingsResponse getSettings(@NotNull RestHighLevelClient client) {
        return client.cluster().getSettings(new ClusterGetSettingsRequest(), DEFAULT);
    }

    /**
     * Cluster Health API
     */
    @SneakyThrows
    public ClusterHealthResponse health(@NotNull RestHighLevelClient client, @NotNull String... indices) {
        return client.cluster().health(new ClusterHealthRequest(indices), DEFAULT);
    }

    /**
     * Remote Cluster Info API
     */
    @SneakyThrows
    public RemoteInfoResponse remoteInfo(@NotNull RestHighLevelClient client) {
        return client.cluster().remoteInfo(new RemoteInfoRequest(), DEFAULT);
    }

    private static class SingletonHolder {
        private static final ClusterSupporter INSTANCE = new ClusterSupporter();
    }
}
