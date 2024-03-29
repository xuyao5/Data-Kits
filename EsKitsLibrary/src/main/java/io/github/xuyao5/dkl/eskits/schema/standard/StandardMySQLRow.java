package io.github.xuyao5.dkl.eskits.schema.standard;

import com.github.shyiko.mysql.binlog.event.Event;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 24/02/21 22:46
 * @apiNote StandardMySQLRow
 * @implNote StandardMySQLRow
 */
@Data(staticConstructor = "of")
public final class StandardMySQLRow implements Serializable {

    private int rowNo;
    private Event event;
}
