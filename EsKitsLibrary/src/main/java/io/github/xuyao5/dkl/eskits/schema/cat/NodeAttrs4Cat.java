package io.github.xuyao5.dkl.eskits.schema.cat;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Thomas.XU(xuyao)
 * @version 18/09/21 22:02
 */
@Data(staticConstructor = "of")
public final class NodeAttrs4Cat implements Serializable {

    @SerializedName(value = "node", alternate = {"NODE"})
    private String node;

    @SerializedName(value = "host", alternate = {"HOST"})
    private String host;

    @SerializedName(value = "ip", alternate = {"IP"})
    private String ip;

    @SerializedName(value = "attr", alternate = {"ATTR"})
    private String attr;

    @SerializedName(value = "value", alternate = {"VALUE"})
    private String value;
}
