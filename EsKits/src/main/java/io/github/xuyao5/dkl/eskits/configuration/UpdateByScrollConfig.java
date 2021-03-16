package io.github.xuyao5.dkl.eskits.configuration;

import lombok.Data;
import org.elasticsearch.index.query.QueryBuilder;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 16/03/21 02:52
 * @apiNote BatchUpdateConfig
 * @implNote BatchUpdateConfig
 */
@Data(staticConstructor = "of")
public final class UpdateByScrollConfig {

    private final String index;

    private QueryBuilder queryBuilder;
}
