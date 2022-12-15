package io.github.xuyao5.datakitsserver;

import io.github.xuyao5.datakitsserver.configuration.elasticsearch.EsKitsConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@SpringBootApplication()
@Import({})//注入@Configuration
@EnableConfigurationProperties({EsKitsConfig.class})//注入@ConfigurationProperties
public class DataKitsServer {

    public static void main(String[] args) {
        SpringApplication.run(DataKitsServer.class, args);
    }
}