package io.github.xuyao5.dkl.eskits.schema.httpfs;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 7/09/21 23:59
 * @apiNote ContentSummaryJson
 * @implNote ContentSummaryJson
 */
@Data(staticConstructor = "of")
public final class ContentSummaryJson implements Serializable {

    @SerializedName(value = "contentSummary", alternate = {"ContentSummary"})
    private ContentSummary contentSummary;
}