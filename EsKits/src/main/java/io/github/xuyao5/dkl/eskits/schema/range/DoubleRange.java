package io.github.xuyao5.dkl.eskits.schema.range;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 13/03/21 18:42
 * @apiNote DoubleRange
 * @implNote DoubleRange
 */
@Data(staticConstructor = "of")
public final class DoubleRange implements Serializable {

    private double from;
    private double to;
}
