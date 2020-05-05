package io.github.xuyao5.dal.elasticsearch.index.param;

import lombok.Data;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 5/05/20 11:50
 * @apiNote CreateIndexParams
 * @implNote CreateIndexParams
 */
@Data(staticConstructor = "of")
public final class CreateIndexParams {

    private final String index;
}
