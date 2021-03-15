package io.github.xuyao5.datakitsserver;

import io.github.xuyao5.datakitsserver.configuration.EsClientConfig;
import io.github.xuyao5.dkl.eskits.client.EsClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@SpringBootApplication()
@Import({})//注入@Configuration
@EnableConfigurationProperties({EsClientConfig.class})//注入@ConfigurationProperties
public class DataKitsApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataKitsApplication.class, args);
    }

    @Bean(name = "myEsClient")
    public EsClient esClient(@Value("${es.client.hosts}") String hosts, @Value("${es.client.username}") String username, @Value("${es.client.password}") String password) {
        return new EsClient(new String[]{hosts}, username, password);
    }
}
