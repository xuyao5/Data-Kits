package io.github.xuyao5.dal.eskitsserver.search.param;

import lombok.Data;
import org.elasticsearch.index.query.QueryBuilder;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 5/05/20 14:42
 * @apiNote SearchParams
 * @implNote SearchParams
 */
@Data(staticConstructor = "of")
public final class SearchParams {

    private final String index;
    private QueryBuilder queryBuilder;
}
