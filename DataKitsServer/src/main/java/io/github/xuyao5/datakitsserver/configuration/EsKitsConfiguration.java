package io.github.xuyao5.datakitsserver.configuration;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import io.github.xuyao5.dkl.eskits.client.EsClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Thomas.XU(xuyao)
 * @version 22/09/21 22:12
 */
@Configuration
public class EsKitsConfiguration {

    @Bean(destroyMethod = "close")
    public RestHighLevelClient restHighLevelClient(@Value("${es.client.hosts}") String hosts, @Value("${es.client.username}") String username, @Value("${es.client.password}") String password) {
        return new EsClient(new String[]{hosts}, username, password).getRestHighLevelClient();
    }

    @Bean(destroyMethod = "close")
    public ElasticsearchClient elasticsearchClient(@Value("${es.client.hosts}") String hosts, @Value("${es.client.username}") String username, @Value("${es.client.password}") String password) {
        return new EsClient(new String[]{hosts}, username, password).getElasticsearchClient();
    }
}
