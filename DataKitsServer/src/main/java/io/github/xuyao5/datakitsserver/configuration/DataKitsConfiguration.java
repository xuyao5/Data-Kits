package io.github.xuyao5.datakitsserver.configuration;

import io.github.xuyao5.dkl.eskits.client.EsClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataKitsConfiguration {

    @Bean
    public DataKitsConfigBean DataKitsConfigBean() {
        return DataKitsConfigBean.of();
    }

    @Bean(name = "esClient1", destroyMethod = "destroy")
    public EsClient getEsClient() {
        return new EsClient(new String[]{"localhost:9200"}, "", "", 10);
    }
}
