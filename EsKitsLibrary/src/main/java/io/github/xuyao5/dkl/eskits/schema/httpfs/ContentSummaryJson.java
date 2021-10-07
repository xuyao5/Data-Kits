package io.github.xuyao5.dkl.eskits.schema.httpfs;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Thomas.XU(xuyao)
 * @version 7/09/21 23:59
 */
@Data(staticConstructor = "of")
public final class ContentSummaryJson implements Serializable {

    @SerializedName(value = "contentSummary", alternate = {"ContentSummary"})
    private ContentSummary contentSummary;
}
