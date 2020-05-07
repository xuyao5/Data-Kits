package io.github.xuyao5.dal.generator.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({})
@ComponentScan(basePackages = {"io.github.xuyao5.dal.generator"})
public class GeneratorConfiguration {

    @Bean
    public GeneratorConfigBean createPropertyBean() {
        return GeneratorConfigBean.of();
    }
}
