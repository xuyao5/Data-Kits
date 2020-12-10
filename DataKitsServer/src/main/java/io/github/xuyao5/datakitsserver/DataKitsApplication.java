package io.github.xuyao5.datakitsserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "io.github.xuyao5.datakitsserver")
public class DataKitsApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataKitsApplication.class, args);
    }
}
