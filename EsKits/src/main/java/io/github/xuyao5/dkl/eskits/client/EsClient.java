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

    private static final int CONN_MULTI = 50;

    private final HttpHost[] hosts;
    private final String username;
    private final String password;

    public EsClient(@NotNull String[] clientUrls, String clientUsername, String clientPassword) {
        hosts = url2HttpHost(clientUrls);
        username = clientUsername;
        password = clientPassword;
    }

    @SneakyThrows
    public <T> T execute(@NotNull Function<RestHighLevelClient, T> function) {
        try (RestHighLevelClient client = getRestHighLevelClient()) {
            return function.apply(client);
        }
    }

    private RestHighLevelClient getRestHighLevelClient() {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
        return new RestHighLevelClient(RestClient.builder(hosts)
                .setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder
                        .setMaxConnPerRoute(DEFAULT_MAX_CONN_PER_ROUTE * CONN_MULTI)
                        .setMaxConnTotal(DEFAULT_MAX_CONN_TOTAL * CONN_MULTI)
                        .setDefaultCredentialsProvider(credentialsProvider)));
    }

    private HttpHost[] url2HttpHost(@NotNull String[] url) {
        return Arrays.stream(url)
                .filter(StringUtils::isNoneEmpty)
                .map(HttpHost::create)
                .distinct()
                .toArray(HttpHost[]::new);
    }
}