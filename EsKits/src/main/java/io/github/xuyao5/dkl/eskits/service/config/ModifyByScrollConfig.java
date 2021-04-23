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

    //如果sourceIndex和targetIndex相同，等于对单一索引操作
    //如果sourceIndex和targetIndex不同，等于Merge Into操作
    private final String sourceIndex;
    private final String targetIndex;

    private QueryBuilder queryBuilder;
}
