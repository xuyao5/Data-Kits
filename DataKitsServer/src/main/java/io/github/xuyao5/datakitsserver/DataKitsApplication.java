package io.github.xuyao5.datakitsserver;

import io.github.xuyao5.datakitsserver.configuration.DataKitsConfigBean;
import io.github.xuyao5.dkl.eskits.client.EsClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@SpringBootApplication()
@Import({})
public class DataKitsApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataKitsApplication.class, args);
    }

    @Bean
    public DataKitsConfigBean DataKitsConfigBean() {
        return DataKitsConfigBean.of();
    }

    @Bean(name = "esClient1", destroyMethod = "destroy")
    public EsClient getEsClient() {
        return new EsClient(new String[]{"localhost:9200"}, "", "", 10);
    }
}
