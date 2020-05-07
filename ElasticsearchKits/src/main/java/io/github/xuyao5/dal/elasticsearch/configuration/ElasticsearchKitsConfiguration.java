package io.github.xuyao5.dal.elasticsearch.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchKitsConfiguration {

    @Bean
    public ElasticsearchKitsConfigBean elasticsearchKitsConfigBean() {
        return ElasticsearchKitsConfigBean.of();
    }
}
