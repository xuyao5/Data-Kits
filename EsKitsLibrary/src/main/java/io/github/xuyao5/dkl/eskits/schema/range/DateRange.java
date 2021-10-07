package io.github.xuyao5.dkl.eskits.schema.range;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Thomas.XU(xuyao)
 * @version 13/03/21 18:42
 */
@Data(staticConstructor = "of")
public final class DateRange implements Serializable {

    private final Date min;
    private final Date max;
}
