package io.github.xuyao5.dal.elasticsearch.base;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.function.BiFunction;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 1/05/20 22:45
 * @apiNote ElasticsearchClient
 * @implNote ElasticsearchClient
 */
@Slf4j
@RequiredArgsConstructor(staticName = "instance")
public final class EsClient<T, R> {

    private final HttpHost[] HOSTS;
    private final Optional<String> USERNAME;
    private final Optional<String> PASSWORD;

    @SneakyThrows
    public R execute(@NotNull BiFunction<RestHighLevelClient, RequestOptions, R> function) {
        try (RestHighLevelClient client = getRestHighLevelClient()) {
            return function.apply(client, getRequestOptions());
        } finally {
            log.info("execute");
        }
    }

    private RestHighLevelClient getRestHighLevelClient() {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(USERNAME.get(), PASSWORD.get()));
        return new RestHighLevelClient(RestClient.builder(HOSTS).setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider)));
    }

    private RequestOptions getRequestOptions() {
        return RequestOptions.DEFAULT;
    }
}
