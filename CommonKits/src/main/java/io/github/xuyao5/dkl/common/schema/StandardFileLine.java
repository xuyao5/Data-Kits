package io.github.xuyao5.dkl.common.schema;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 24/02/21 22:46
 * @apiNote StandardFileLine
 * @implNote StandardFileLine
 */
@Data(staticConstructor = "of")
public final class StandardFileLine implements Serializable {

    private int lineNo;//行号
    private String lineRecord;//行记录
}
