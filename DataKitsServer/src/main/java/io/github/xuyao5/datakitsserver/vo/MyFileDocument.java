package io.github.xuyao5.datakitsserver.vo;

import io.github.xuyao5.dkl.eskits.context.annotation.FileField;
import io.github.xuyao5.dkl.eskits.schema.base.BaseDocument;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data(staticConstructor = "of")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class MyFileDocument extends BaseDocument {

    //UUID          CASH_AMOUNT          DESC          DATE_TIME_1          DATE_TIME_2
    @FileField("UUID")
    private String uuid;

    @FileField("CASH_AMOUNT")
    private BigDecimal cashAmount;

    @FileField("DESC")
    private String desc;

    @FileField("DATE_TIME_1")
    private long dateTime1;

    @FileField("DATE_TIME_2")
    private Date dateTime2;

    private BigDecimal discount;

    private NestedTags tags;

    @Data(staticConstructor = "of")
    public static class NestedTags implements Serializable {

        private final String name;
        private final boolean isSuccess;
    }
}
