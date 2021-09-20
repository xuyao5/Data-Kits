package io.github.xuyao5.dkl.eskits.schema.cat;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 18/09/21 18:50
 * @apiNote TODO 这里输入文件说明
 * @implNote TODO 这里输入实现说明
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
