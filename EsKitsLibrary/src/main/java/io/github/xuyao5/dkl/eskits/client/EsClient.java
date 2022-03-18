package io.github.xuyao5.dkl.eskits.client;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.Closeable;
import java.util.Arrays;
import java.util.Objects;

import static org.elasticsearch.client.RestClientBuilder.*;

/**
 * @author Thomas.XU(xuyao)
 * @version 1/05/20 22:45
 */
@Slf4j
public final class EsClient implements Closeable {

    @Getter
    private final RestHighLevelClient restHighLevelClient;

    @Getter
    private final ElasticsearchClient elasticsearchClient;

    @Getter
    private final ElasticsearchAsyncClient elasticsearchAsyncClient;

    public EsClient(@NonNull String[] clientUrls, @NonNull String clientUsername, @NonNull String clientPassword) {
        restHighLevelClient = new RestHighLevelClient(getRestClientBuilder(url2HttpHost(clientUrls), clientUsername, clientPassword));
        RestClientTransport transport = new RestClientTransport(restHighLevelClient.getLowLevelClient(), new JacksonJsonpMapper());
        elasticsearchClient = new ElasticsearchClient(transport);
        elasticsearchAsyncClient = new ElasticsearchAsyncClient(transport);
    }

    private RestClientBuilder getRestClientBuilder(@NonNull HttpHost[] hosts, @NonNull String username, @NonNull String password) {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));
        return RestClient.builder(hosts).setHttpClientConfigCallback(builder -> builder.setMaxConnPerRoute(DEFAULT_MAX_CONN_PER_ROUTE * 360).setMaxConnTotal(DEFAULT_MAX_CONN_TOTAL * 360).setDefaultCredentialsProvider(credentialsProvider)).setRequestConfigCallback(builder -> builder.setConnectTimeout(DEFAULT_CONNECT_TIMEOUT_MILLIS * 360).setSocketTimeout(DEFAULT_SOCKET_TIMEOUT_MILLIS * 360));
    }

    @SneakyThrows
    @Override
    public void close() {
        if (Objects.nonNull(restHighLevelClient)) {
            restHighLevelClient.close();
        }
    }

    private HttpHost[] url2HttpHost(@NonNull String[] url) {
        return Arrays.stream(url).filter(StringUtils::isNoneEmpty).map(HttpHost::create).distinct().toArray(HttpHost[]::new);
    }
}