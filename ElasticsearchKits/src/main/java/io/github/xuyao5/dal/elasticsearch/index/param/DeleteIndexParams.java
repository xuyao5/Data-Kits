package io.github.xuyao5.dal.elasticsearch.index.param;

import lombok.Data;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 5/05/20 11:59
 * @apiNote DeleteIndexParams
 * @implNote DeleteIndexParams
 */
@Data(staticConstructor = "of")
public final class DeleteIndexParams {

    private final String[] indices;
}
