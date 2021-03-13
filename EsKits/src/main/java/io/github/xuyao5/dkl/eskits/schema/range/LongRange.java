package io.github.xuyao5.dkl.eskits.schema.range;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 13/03/21 18:42
 * @apiNote LongRange
 * @implNote LongRange
 */
@Data(staticConstructor = "of")
public final class LongRange implements Serializable {

    private long from;
    private long to;
}
