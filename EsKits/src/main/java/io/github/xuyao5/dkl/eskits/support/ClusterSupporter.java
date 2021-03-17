package io.github.xuyao5.dkl.eskits.support;

import io.github.xuyao5.dkl.eskits.context.AbstractSupporter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public final class ClusterSupporter extends AbstractSupporter {

    public ClusterSupporter(@NotNull RestHighLevelClient client) {
        super(client);
    }

    /**
     * Cluster Get Settings API
     */
    @SneakyThrows
    public ClusterGetSettingsResponse getSettings() {
        return client.cluster().getSettings(new ClusterGetSettingsRequest(), DEFAULT);
    }

    /**
     * Cluster Health API
     */
    @SneakyThrows
    public ClusterHealthResponse health(@NotNull String... indices) {
        return client.cluster().health(new ClusterHealthRequest(indices), DEFAULT);
    }

    /**
     * Remote Cluster Info API
     */
    @SneakyThrows
    public RemoteInfoResponse remoteInfo() {
        return client.cluster().remoteInfo(new RemoteInfoRequest(), DEFAULT);
    }
}
