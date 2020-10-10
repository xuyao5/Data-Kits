package io.github.xuyao5.dal.file2es.configuration;

import io.github.xuyao5.dal.file2es.configuration.xml.File2EsCollectorXml;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.util.ResourceUtils;

import javax.xml.bind.JAXB;

import static org.springframework.util.ResourceUtils.CLASSPATH_URL_PREFIX;

@Configuration
@Import({})
@ComponentScan(basePackages = {"io.github.xuyao5.dal.file2es"})
public class File2EsConfiguration {

    private static final String XML_CONFIG_FILE = CLASSPATH_URL_PREFIX + "File2EsCollector.xml";

    @Bean
    public File2EsPropertyBean file2EsPropertyBean() {
        return File2EsPropertyBean.of();
    }

    @SneakyThrows
    @Bean
    public File2EsCollectorXml file2EsCollectorXml() {
        return JAXB.unmarshal(ResourceUtils.getFile(XML_CONFIG_FILE), File2EsCollectorXml.class);
    }
}
