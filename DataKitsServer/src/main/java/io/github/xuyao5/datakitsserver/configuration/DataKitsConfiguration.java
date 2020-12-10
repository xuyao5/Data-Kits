package io.github.xuyao5.datakitsserver.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataKitsConfiguration {

    @Bean
    public DataKitsConfigBean DataKitsConfigBean() {
        return DataKitsConfigBean.of();
    }
}
