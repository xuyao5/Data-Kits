package io.github.xuyao5.dkl.eskits.schema.cat;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 18/09/21 22:03
 * @apiNote Templates4Cat
 * @implNote Templates4Cat
 */
@Data(staticConstructor = "of")
public final class Templates4Cat implements Serializable {

    @SerializedName(value = "name", alternate = {"NAME"})
    private String name;

    @SerializedName(value = "index_patterns", alternate = {"INDEX_PATTERNS"})
    private String indexPatterns;

    @SerializedName(value = "order", alternate = {"ORDER"})
    private String order;

    @SerializedName(value = "version", alternate = {"VERSION"})
    private String version;

    @SerializedName(value = "composed_of", alternate = {"COMPOSED_OF"})
    private String composedOf;
}
