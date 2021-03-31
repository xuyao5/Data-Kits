package io.github.xuyao5.datakitsserver.vo;

import io.github.xuyao5.dkl.eskits.schema.BaseDocument;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@Data(staticConstructor = "of")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class MyDocument extends BaseDocument<MyDocument.MyTags> {

    private String uuid;
    private long cashAmount;
    private String desc;
    private Date dateTime;

    @Data(staticConstructor = "of")
    public static class MyTags {

        private String tag1;
        private Date tag2;
        private long tag3;
    }
}
