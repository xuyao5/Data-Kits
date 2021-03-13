package io.github.xuyao5.datakitsserver.vo;

import io.github.xuyao5.dkl.eskits.schema.StandardDocument;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@Data(staticConstructor = "of")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class MyDocument extends StandardDocument {

    private String uuid;
    private long cashAmount;
    private String desc;
    private Date dateTime;
}
