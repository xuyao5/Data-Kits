package io.github.xuyao5.dal.eskitsserver.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 1/05/20 22:41
 * @apiNote ElasticsearchKitsConfigBean
 * @implNote ElasticsearchKitsConfigBean
 */
@Getter
@RequiredArgsConstructor(staticName = "of")
public final class ElasticsearchKitsConfigBean {

    @Value("${es.client.hosts}")
    private String[] esClientHosts;

    @Value("${es.client.username}")
    private String esClientUsername;

    @Value("${es.client.password}")
    private String esClientPassword;
}
