package io.github.xuyao5.dal.file2es.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({})
@ComponentScan(basePackages = {"io.github.xuyao5.dal.file2es"})
public class File2EsConfiguration {

    @Bean
    public File2EsConfigBean generatorConfigBean() {
        return File2EsConfigBean.of();
    }
}
