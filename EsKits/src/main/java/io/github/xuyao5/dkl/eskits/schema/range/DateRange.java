package io.github.xuyao5.dkl.eskits.schema.range;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 13/03/21 18:42
 * @apiNote DateRange
 * @implNote DateRange
 */
@Data(staticConstructor = "of")
public final class DateRange implements Serializable {

    private Date from;
    private Date to;
}
