package io.github.xuyao5.datakitsbatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@SpringBootApplication()
@Import({})//注入@Configuration
@EnableConfigurationProperties({})//注入@ConfigurationProperties
public class DataKitsBatch {

    public static void main(String[] args) {
        SpringApplication.run(DataKitsBatch.class, args);
    }
}