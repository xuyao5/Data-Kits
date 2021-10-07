package io.github.xuyao5.dkl.eskits.schema.cat;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Thomas.XU(xuyao)
 * @version 18/09/21 18:48
 */
@Data(staticConstructor = "of")
public final class Allocation4Cat implements Serializable {

    @SerializedName(value = "shards", alternate = {"SHARDS"})
    private String shards;

    @SerializedName(value = "disk.indices", alternate = {"DISK.INDICES"})
    private String diskIndices;

    @SerializedName(value = "disk.used", alternate = {"DISK.USED"})
    private String diskUsed;

    @SerializedName(value = "disk.avail", alternate = {"DISK.AVAIL"})
    private String diskAvail;

    @SerializedName(value = "disk.total", alternate = {"DISK.TOTAL"})
    private String diskTotal;

    @SerializedName(value = "disk.percent", alternate = {"DISK.PERCENT"})
    private String diskPercent;

    @SerializedName(value = "host", alternate = {"HOST"})
    private String host;

    @SerializedName(value = "ip", alternate = {"IP"})
    private String ip;

    @SerializedName(value = "node", alternate = {"NODE"})
    private String node;
}
