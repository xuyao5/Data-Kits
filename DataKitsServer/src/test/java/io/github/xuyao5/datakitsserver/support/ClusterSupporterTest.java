package io.github.xuyao5.datakitsserver.support;

import io.github.xuyao5.datakitsserver.context.AbstractTest;
import io.github.xuyao5.dkl.eskits.support.general.ClusterSupporter;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.cluster.settings.ClusterGetSettingsResponse;
import org.elasticsearch.client.cluster.RemoteInfoResponse;
import org.junit.jupiter.api.Test;

public class ClusterSupporterTest extends AbstractTest {

    @Test
    void testCreate() {
        ClusterSupporter instance = ClusterSupporter.getInstance();
        ClusterHealthResponse clusterHealthResponse = instance.health(esClient);
        System.out.println(clusterHealthResponse.getNumberOfNodes());
        System.out.println(clusterHealthResponse.getNumberOfDataNodes());
        ClusterGetSettingsResponse settings = instance.getSettings(esClient);
        System.out.println(settings);
        RemoteInfoResponse remoteInfoResponse = instance.remoteInfo(esClient);
        System.out.println(remoteInfoResponse.getInfos());
    }
}
