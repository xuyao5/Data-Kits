package io.github.xuyao5.dkl.eskits.service.config;

import lombok.Data;
import org.elasticsearch.index.query.QueryBuilder;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 16/03/21 02:52
 * @apiNote ModifyByScrollConfig
 * @implNote ModifyByScrollConfig
 */
@Data(staticConstructor = "of")
public final class ModifyByScrollConfig {

    private final String index;

    private QueryBuilder queryBuilder;
}
