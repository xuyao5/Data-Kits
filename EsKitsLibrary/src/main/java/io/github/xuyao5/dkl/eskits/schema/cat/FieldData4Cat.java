package io.github.xuyao5.dkl.eskits.schema.cat;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Thomas.XU(xuyao)
 * @version 18/09/21 22:01
 */
@Data(staticConstructor = "of")
public final class FieldData4Cat implements Serializable {

    @SerializedName(value = "id", alternate = {"ID"})
    private String id;

    @SerializedName(value = "host", alternate = {"HOST"})
    private String host;

    @SerializedName(value = "ip", alternate = {"IP"})
    private String ip;

    @SerializedName(value = "node", alternate = {"NODE"})
    private String node;

    @SerializedName(value = "field", alternate = {"FIELD"})
    private String field;

    @SerializedName(value = "size", alternate = {"SIZE"})
    private String size;
}
