package io.github.xuyao5.dal.file2es.configuration;

import io.github.xuyao5.dal.file2es.configuration.xml.File2EsConfigXml;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXB;

import static org.springframework.util.ResourceUtils.CLASSPATH_URL_PREFIX;

@Slf4j
@Configuration
@Import({})
@ComponentScan(basePackages = {"io.github.xuyao5.dal.file2es"})
public class File2EsConfiguration {

    @Bean
    public File2EsPropertyBean file2EsPropertyBean() {
        return File2EsPropertyBean.of();
    }

    @SneakyThrows
    @PostConstruct
    void initial() {
        String configFile = CLASSPATH_URL_PREFIX + "File2EsConfig.xml";
        File2EsConfigXml file2EsConfigXml = JAXB.unmarshal(ResourceUtils.getFile(configFile), File2EsConfigXml.class);
        log.info("获取配置文件:{}", file2EsConfigXml);
    }
}
