package io.github.xuyao5.dkl.eskits.client;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;

import static org.elasticsearch.client.RestClientBuilder.DEFAULT_MAX_CONN_PER_ROUTE;
import static org.elasticsearch.client.RestClientBuilder.DEFAULT_MAX_CONN_TOTAL;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 1/05/20 22:45
 * @apiNote ElasticsearchClient
 * @implNote ElasticsearchClient
 */
@Slf4j
public final class EsClient {

    private final ThreadLocal<RestHighLevelClient> CLIENT_THREAD_LOCAL = new ThreadLocal<>();

    public EsClient(@NotNull String[] clientUrls, String clientUsername, String clientPassword, int connMulti) {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(clientUsername, clientPassword));
        CLIENT_THREAD_LOCAL.set(new RestHighLevelClient(RestClient.builder(url2HttpHost(clientUrls))
                .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder
                        .setMaxConnPerRoute(DEFAULT_MAX_CONN_PER_ROUTE * connMulti)
                        .setMaxConnTotal(DEFAULT_MAX_CONN_TOTAL * connMulti)
                        .setDefaultCredentialsProvider(credentialsProvider))));
    }

    @SneakyThrows
    public <T> T execute(Function<RestHighLevelClient, T> function) {
        try (RestHighLevelClient client = CLIENT_THREAD_LOCAL.get()) {
            return function.apply(client);
        }
    }

    @SneakyThrows
    public void destroy() {
        if (Objects.nonNull(CLIENT_THREAD_LOCAL.get())) {
            CLIENT_THREAD_LOCAL.get().close();
            CLIENT_THREAD_LOCAL.remove();
        }
    }

    private HttpHost[] url2HttpHost(@NotNull String[] url) {
        return Arrays.stream(url)
                .filter(StringUtils::isNoneEmpty)
                .map(HttpHost::create)
                .distinct()
                .toArray(HttpHost[]::new);
    }
}