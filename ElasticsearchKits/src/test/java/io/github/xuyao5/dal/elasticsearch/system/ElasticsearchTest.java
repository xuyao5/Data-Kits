package io.github.xuyao5.dal.elasticsearch.system;

import io.github.xuyao5.dal.elasticsearch.AbstractTest;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.junit.jupiter.api.Test;

import javax.validation.constraints.NotNull;
import java.io.IOException;

@Slf4j
class ElasticsearchTest extends AbstractTest {

    private static final String INDEX = "twitter";
    private static final String USERNAME = "";
    private static final String PASSWORD = "";

    @Test
    void testElasticsearch() {
        try (RestHighLevelClient client = getRestHighLevelClient()) {
            boolean exists = client.indices().exists(getIndexRequest(INDEX), RequestOptions.DEFAULT);
            if (!exists) {
                CreateIndexResponse response = client.indices().create(createIndexRequest(INDEX), RequestOptions.DEFAULT);
                System.out.println("CreateIndexResponse==>" + response.index());
            }
            ClusterHealthResponse health = client.cluster().health(clusterHealthRequest(), RequestOptions.DEFAULT);
            health.getIndices().forEach((s, clusterShardHealths) -> {
                System.out.println("找到索引：" + s + "|" + clusterShardHealths);
            });

/*            if (client.indices().exists(getIndexRequest(INDEX), RequestOptions.DEFAULT)) {
                AcknowledgedResponse response = client.indices().delete(deleteIndexRequest(INDEX), RequestOptions.DEFAULT);
                System.out.println("AcknowledgedResponse==>" + response.toString());
            }*/

        } catch (IOException ex) {
            log.error("错误：", ex);
        }
    }

    private DeleteIndexRequest deleteIndexRequest(@NotNull String index) {
        return new DeleteIndexRequest(index);
    }

    private CreateIndexRequest createIndexRequest(@NotNull String index) {
        return new CreateIndexRequest(index);
    }

    private GetIndexRequest getIndexRequest(@NotNull String index) {
        return new GetIndexRequest(index);
    }

    private ClusterHealthRequest clusterHealthRequest() {
        return new ClusterHealthRequest();
    }

    private RestHighLevelClient getRestHighLevelClient() {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(USERNAME, PASSWORD));
        return new RestHighLevelClient(RestClient
                .builder(new HttpHost("localhost", 9200, "http"))
                .setHttpClientConfigCallback(httpAsyncClientBuilder -> httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider)));
    }
}
