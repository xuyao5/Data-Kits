package io.github.xuyao5.dkl.eskits.schema.cat;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 16/09/21 23:57
 * @apiNote Indices4Cat
 * @implNote Indices4Cat
 */
@Data(staticConstructor = "of")
public class Indices4Cat implements Serializable {

    @SerializedName(value = "health", alternate = {"HEALTH"})
    private String health;

    @SerializedName(value = "status", alternate = {"STATUS"})
    private String status;

    @SerializedName(value = "index", alternate = {"INDEX"})
    private String index;

    @SerializedName(value = "uuid", alternate = {"UUID"})
    private String uuid;

    @SerializedName(value = "pri", alternate = {"PRI"})
    private String pri;

    @SerializedName(value = "rep", alternate = {"REP"})
    private String rep;

    @SerializedName(value = "docs.count", alternate = {"DOCS.COUNT"})
    private String docsCount;

    @SerializedName(value = "docs.deleted", alternate = {"DOCS.DELETED"})
    private String docsDeleted;

    @SerializedName(value = "store.size", alternate = {"STORE.SIZE"})
    private String storeSize;

    @SerializedName(value = "pri.store.size", alternate = {"PRI.STORE.SIZE"})
    private String priStoreSize;
}
