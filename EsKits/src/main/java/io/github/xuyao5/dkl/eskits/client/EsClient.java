package io.github.xuyao5.dkl.eskits.client;

import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.Closeable;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import static org.elasticsearch.client.RestClientBuilder.*;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 1/05/20 22:45
 * @apiNote EsClientImpl
 * @implNote EsClientImpl
 */
@Slf4j
public final class EsClient implements Closeable {

    @Getter
    private final RestHighLevelClient client;

    public EsClient(@NonNull String[] clientUrls, @NonNull String clientUsername, @NonNull String clientPassword) {
        client = getRestHighLevelClient(url2HttpHost(clientUrls), clientUsername, clientPassword);
    }

    private RestHighLevelClient getRestHighLevelClient(@NonNull HttpHost[] hosts, @NonNull String username, @NonNull String password) {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
        return new RestHighLevelClient(RestClient.builder(hosts)
                .setHttpClientConfigCallback(builder -> builder
                        .setMaxConnPerRoute(DEFAULT_MAX_CONN_PER_ROUTE * 360)//10*360
                        .setMaxConnTotal(DEFAULT_MAX_CONN_TOTAL * 360)//30*360
                        .setDefaultCredentialsProvider(credentialsProvider))
                .setRequestConfigCallback(builder -> builder
                        .setConnectTimeout(DEFAULT_CONNECT_TIMEOUT_MILLIS * 360)//1s*360=6min
                        .setSocketTimeout(DEFAULT_SOCKET_TIMEOUT_MILLIS * 360)));//30s*360=3h
    }

    @Override
    public void close() throws IOException {
        if (Objects.nonNull(client)) {
            client.close();
        }
    }

    private HttpHost[] url2HttpHost(@NonNull String[] url) {
        return Arrays.stream(url)
                .filter(StringUtils::isNoneEmpty)
                .map(HttpHost::create)
                .distinct()
                .toArray(HttpHost[]::new);
    }
}