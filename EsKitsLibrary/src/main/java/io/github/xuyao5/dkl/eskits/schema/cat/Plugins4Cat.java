package io.github.xuyao5.dkl.eskits.schema.cat;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Thomas.XU(xuyao)
 * @version 18/09/21 22:01
 */
@Data(staticConstructor = "of")
public final class Plugins4Cat implements Serializable {

    @SerializedName(value = "name", alternate = {"NAME"})
    private String name;

    @SerializedName(value = "component", alternate = {"COMPONENT"})
    private String component;

    @SerializedName(value = "version", alternate = {"VERSION"})
    private String version;
}
