package io.github.xuyao5.dkl.eskits.schema.httpfs;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 7/09/21 23:49
 * @apiNote ContentSummary
 * @implNote ContentSummary
 */
@Data(staticConstructor = "of")
public final class ContentSummary implements Serializable {

    @SerializedName(value = "directoryCount", alternate = {"DirectoryCount"})
    private long directoryCount;

    @SerializedName(value = "fileCount", alternate = {"FileCount"})
    private long fileCount;

    @SerializedName(value = "length", alternate = {"Length"})
    private long length;

    @SerializedName(value = "quota", alternate = {"Quota"})
    private long quota;

    @SerializedName(value = "spaceConsumed", alternate = {"SpaceConsumed"})
    private long spaceConsumed;

    @SerializedName(value = "spaceQuota", alternate = {"SpaceQuota"})
    private long spaceQuota;
}
