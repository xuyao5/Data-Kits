package io.github.xuyao5.dkl.eskits.schema.cat;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 18/09/21 21:47
 * @apiNote TODO 这里输入文件说明
 * @implNote TODO 这里输入实现说明
 */
@Data(staticConstructor = "of")
public final class Segments4Cat implements Serializable {

    @SerializedName(value = "index", alternate = {"INDEX"})
    private String index;

    @SerializedName(value = "shard", alternate = {"SHARDS"})
    private String shard;

    @SerializedName(value = "prirep", alternate = {"PRIREP"})
    private String prirep;

    @SerializedName(value = "ip", alternate = {"IP"})
    private String ip;

    @SerializedName(value = "segment", alternate = {"SEGMENT"})
    private String segment;

    @SerializedName(value = "generation", alternate = {"GENERATION"})
    private String generation;

    @SerializedName(value = "docs.count", alternate = {"DOCS.COUNT"})
    private String docsCount;

    @SerializedName(value = "docs.deleted", alternate = {"DOCS.DELETED"})
    private String docsDeleted;

    @SerializedName(value = "size", alternate = {"SIZE"})
    private String size;

    @SerializedName(value = "size.memory", alternate = {"SIZE.MEMORY"})
    private String sizeMemory;

    @SerializedName(value = "committed", alternate = {"COMMITTED"})
    private String committed;

    @SerializedName(value = "searchable", alternate = {"SEARCHABLE"})
    private String searchable;

    @SerializedName(value = "version", alternate = {"VERSION"})
    private String version;

    @SerializedName(value = "compound", alternate = {"COMPOUND"})
    private String compound;
}
