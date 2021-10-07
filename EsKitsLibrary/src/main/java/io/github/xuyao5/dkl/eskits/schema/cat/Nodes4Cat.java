package io.github.xuyao5.dkl.eskits.schema.cat;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 18/09/21 18:52
 * @apiNote Nodes4Cat
 * @implNote Nodes4Cat
 */
@Data(staticConstructor = "of")
public final class Nodes4Cat implements Serializable {

    @SerializedName(value = "ip", alternate = {"IP"})
    private String ip;

    @SerializedName(value = "heap.percent", alternate = {"HEAP.PERCENT"})
    private String heapPercent;

    @SerializedName(value = "ram.percent", alternate = {"RAM.PERCENT"})
    private String ramPercent;

    @SerializedName(value = "cpu", alternate = {"CPU"})
    private String cpu;

    @SerializedName(value = "load_1m", alternate = {"LOAD_1M"})
    private String load_1m;

    @SerializedName(value = "load_5m", alternate = {"LOAD_5M"})
    private String load_5m;

    @SerializedName(value = "load_15m", alternate = {"LOAD_15M"})
    private String load_15m;

    @SerializedName(value = "node.role", alternate = {"NODE.ROLE"})
    private String nodeRole;

    @SerializedName(value = "master", alternate = {"MASTER"})
    private String master;

    @SerializedName(value = "name", alternate = {"NAME"})
    private String name;
}
