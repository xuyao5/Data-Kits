package io.github.xuyao5.dal.elasticsearch.system;

import io.github.xuyao5.dal.elasticsearch.AbstractTest;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.junit.jupiter.api.Test;

import java.io.IOException;

@Slf4j
class ElasticsearchTest extends AbstractTest {

    private static final String INDEX = "twitter";

    @Test
    void testElasticsearch() {
        try (RestHighLevelClient client = getRestHighLevelClient()) {
            boolean exists = client.indices().exists(getIndexRequest(), RequestOptions.DEFAULT);
            if (!exists) {
                CreateIndexResponse response = client.indices().create(createIndexRequest(), RequestOptions.DEFAULT);
                System.out.println("CreateIndexResponse==>" + response.index());
            }
            if (client.indices().exists(getIndexRequest(), RequestOptions.DEFAULT)) {
                AcknowledgedResponse response = client.indices().delete(deleteIndexRequest(), RequestOptions.DEFAULT);
                System.out.println("AcknowledgedResponse==>" + response.toString());
            }

        } catch (IOException ex) {
            log.error("错误：", ex);
        }
    }

    private DeleteIndexRequest deleteIndexRequest() {
        return new DeleteIndexRequest(INDEX);
    }

    private CreateIndexRequest createIndexRequest() {
        return new CreateIndexRequest(INDEX);
    }

    private GetIndexRequest getIndexRequest() {
        return new GetIndexRequest(INDEX);
    }

    private RestHighLevelClient getRestHighLevelClient() {
        return new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));
    }
}
