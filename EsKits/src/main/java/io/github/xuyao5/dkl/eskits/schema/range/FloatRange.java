package io.github.xuyao5.dkl.eskits.schema.range;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 13/03/21 18:42
 * @apiNote FloatRange
 * @implNote FloatRange
 */
@Data(staticConstructor = "of")
public final class FloatRange implements Serializable {

    private final float min;
    private final float max;
}
