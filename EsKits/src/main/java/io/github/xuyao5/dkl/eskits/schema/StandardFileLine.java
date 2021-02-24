package io.github.xuyao5.dkl.eskits.schema;

import lombok.Data;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 24/02/21 22:46
 * @apiNote StandardFileLine
 * @implNote StandardFileLine
 */
@Data
public final class StandardFileLine {

    private int lineNo;//行号
    private String lineRecord;//行记录
}
