package io.github.xuyao5.datakitsserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "io.github.xuyao5.datakitsserver")
public class ElasticsearchKitsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElasticsearchKitsApplication.class, args);
    }
}
