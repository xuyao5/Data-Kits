package io.github.xuyao5.datakitsserver.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 1/05/20 22:41
 * @apiNote DataKitsConfigBean
 * @implNote DataKitsConfigBean
 */
@Getter
@RequiredArgsConstructor(staticName = "of")
public final class DataKitsConfigBean {

    @Value("${es.client.hosts}")
    private String esClientHosts;
}
