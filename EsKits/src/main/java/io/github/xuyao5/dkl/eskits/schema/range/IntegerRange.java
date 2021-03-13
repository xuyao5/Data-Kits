package io.github.xuyao5.dkl.eskits.schema.range;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 13/03/21 18:41
 * @apiNote IntegerRange
 * @implNote IntegerRange
 */
@Data(staticConstructor = "of")
public final class IntegerRange implements Serializable {

    private final int min;
    private final int max;
}
