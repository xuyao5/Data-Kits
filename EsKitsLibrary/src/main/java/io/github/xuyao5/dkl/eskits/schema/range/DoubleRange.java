package io.github.xuyao5.dkl.eskits.schema.range;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Thomas.XU(xuyao)
 * @version 13/03/21 18:42
 */
@Data(staticConstructor = "of")
public final class DoubleRange implements Serializable {

    private final double min;
    private final double max;
}
