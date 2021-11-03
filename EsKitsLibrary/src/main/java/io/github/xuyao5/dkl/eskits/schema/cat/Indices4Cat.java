package io.github.xuyao5.dkl.eskits.schema.cat;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Thomas.XU(xuyao)
 * @version 16/09/21 23:57
 */
@Data(staticConstructor = "of")
public final class Indices4Cat implements Serializable {

    @SerializedName(value = "health", alternate = {"HEALTH"})
    private String health;

    @SerializedName(value = "status", alternate = {"STATUS"})
    private String status;

    @SerializedName(value = "index", alternate = {"INDEX"})
    private String index;

    @SerializedName(value = "uuid", alternate = {"UUID"})
    private String uuid;

    @SerializedName(value = "pri", alternate = {"PRI"})
    private int pri;

    @SerializedName(value = "rep", alternate = {"REP"})
    private int rep;

    @SerializedName(value = "docs.count", alternate = {"DOCS.COUNT"})
    private long docsCount;

    @SerializedName(value = "docs.deleted", alternate = {"DOCS.DELETED"})
    private long docsDeleted;

    @SerializedName(value = "store.size", alternate = {"STORE.SIZE"})
    private long storeSize;

    @SerializedName(value = "pri.store.size", alternate = {"PRI.STORE.SIZE"})
    private long priStoreSize;
}
