package io.github.xuyao5.dkl.eskits.schema.httpfs;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @author Thomas.XU(xuyao)
 * @version 7/09/21 23:49
 */
@Data(staticConstructor = "of")
public final class ContentSummary {

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
