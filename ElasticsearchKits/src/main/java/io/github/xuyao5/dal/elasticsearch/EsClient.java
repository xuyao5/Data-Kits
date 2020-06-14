package io.github.xuyao5.dal.elasticsearch;

import io.github.xuyao5.dal.elasticsearch.configuration.ElasticsearchKitsConfigBean;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
@Component("esClient")
public final class EsClient<T> {

    @Autowired
    private ElasticsearchKitsConfigBean configBean;

    private HttpHost[] hosts;
    private Optional<String> username;
    private Optional<String> password;

    @PostConstruct
    private void postConstruct() {
        hosts = urls2HttpHost(configBean.getEsClientHosts());
        username = Optional.ofNullable(configBean.getEsClientUsername());
        password = Optional.ofNullable(configBean.getEsClientPassword());
    }

    @SneakyThrows
    public T execute(@NotNull Function<RestHighLevelClient, T> function) {
        try (RestHighLevelClient client = getRestHighLevelClient()) {
            return function.apply(client);
        } finally {
            log.info("execute");
        }
    }

    private RestHighLevelClient getRestHighLevelClient() {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username.get(), password.get()));
        return new RestHighLevelClient(RestClient.builder(hosts).setHttpClientConfigCallback(httpClientBuilder -> httpClientBuilder
                .setMaxConnPerRoute(DEFAULT_MAX_CONN_PER_ROUTE * 10)
                .setMaxConnTotal(DEFAULT_MAX_CONN_TOTAL * 10)
                .setDefaultCredentialsProvider(credentialsProvider)));
    }

    private HttpHost[] urls2HttpHost(@NotNull String[] url) {
        List<HttpHost> result = new ArrayList<>();
        Arrays.asList(url).forEach(str -> {
            if (StringUtils.isNoneEmpty(str)) {
                HttpHost myHttpHost = HttpHost.create(str);
                if (!result.contains(myHttpHost)) {
                    result.add(myHttpHost);
                }
            }
        });
        return result.toArray(new HttpHost[result.size()]);
    }
}
