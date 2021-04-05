package io.github.xuyao5.datakitsserver.vo;

import io.github.xuyao5.dkl.eskits.schema.BaseDocument;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data(staticConstructor = "of")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class AllTypeDocument extends BaseDocument {

    private List<String> strList;
    private String str;
    private String[] strArray;
}
