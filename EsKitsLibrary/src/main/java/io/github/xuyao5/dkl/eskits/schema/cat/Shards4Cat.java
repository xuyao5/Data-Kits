package io.github.xuyao5.dkl.eskits.schema.cat;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 18/09/21 18:50
 * @apiNote Shards4Cat
 * @implNote Shards4Cat
 */
@Data(staticConstructor = "of")
public final class Shards4Cat implements Serializable {

    @SerializedName(value = "index", alternate = {"INDEX"})
    private String index;

    @SerializedName(value = "shard", alternate = {"SHARD"})
    private String shard;

    @SerializedName(value = "prirep", alternate = {"PRIREP"})
    private String prirep;

    @SerializedName(value = "state", alternate = {"STATE"})
    private String state;

    @SerializedName(value = "docs", alternate = {"DOCS"})
    private String docs;

    @SerializedName(value = "store", alternate = {"STORE"})
    private String store;

    @SerializedName(value = "ip", alternate = {"IP"})
    private String ip;

    @SerializedName(value = "node", alternate = {"NODE"})
    private String node;
}
