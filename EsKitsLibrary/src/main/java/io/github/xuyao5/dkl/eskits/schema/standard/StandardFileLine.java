package io.github.xuyao5.dkl.eskits.schema.standard;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Thomas.XU(xuyao)
 * @version 24/02/21 22:46
 */
@Data(staticConstructor = "of")
public final class StandardFileLine implements Serializable {

    private long lineNo;//行号
    private String lineRecord;//行记录
}
