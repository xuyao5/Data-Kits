package io.github.xuyao5.dkl.eskits.schema.standard;

import com.github.shyiko.mysql.binlog.event.Event;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Thomas.XU(xuyao)
 * @version 24/02/21 22:46
 */
@Data(staticConstructor = "of")
public final class StandardMySQLRow implements Serializable {

    private int rowNo;
    private Event event;
}
