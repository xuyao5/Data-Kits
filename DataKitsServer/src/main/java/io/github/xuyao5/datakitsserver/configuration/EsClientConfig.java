package io.github.xuyao5.datakitsserver.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 1/05/20 22:41
 * @apiNote DataKitsConfigBean
 * @implNote DataKitsConfigBean
 */
@Getter
@ConfigurationProperties("es.client")
public final class EsClientConfig {

    //实验性例子
    @Value("${es.client.hosts}")
    private String hosts;
}
