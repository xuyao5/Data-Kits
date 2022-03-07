package io.github.xuyao5.datakitsserver.configuration;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import io.github.xuyao5.dkl.eskits.client.EsClient;
import io.github.xuyao5.dkl.eskits.support.batch.BulkSupporter;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Bean
    public ElasticsearchClient elasticsearchClient(@Value("${es.client.hosts}") String hosts, @Value("${es.client.username}") String username, @Value("${es.client.password}") String password) {
        return new EsClient(new String[]{hosts}, username, password).getElasticsearchClient();
    }

    @Bean(destroyMethod = "close")
    public BulkProcessor bulkProcessor(@Autowired RestHighLevelClient restHighLevelClient, @Value("${es.bulk.threads}") int threads) {
        return BulkSupporter.buildBulkProcessor(restHighLevelClient, threads);
    }
}