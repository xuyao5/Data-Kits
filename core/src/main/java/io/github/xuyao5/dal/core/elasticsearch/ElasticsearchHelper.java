package io.github.xuyao5.dal.core.elasticsearch;

import org.apache.http.HttpHost;
import org.elasticsearch.action.main.MainResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;
import java.util.function.Function;

public class ElasticsearchHelper {

    public void queryInfo(Function<MainResponse, String> func) throws IOException {
        try (RestHighLevelClient client = getRestHighLevelClient()) {
            MainResponse response = client.info(RequestOptions.DEFAULT);
            func.apply(response);
        }
    }

    private RestHighLevelClient getRestHighLevelClient() {
        return new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")));
    }
}
