package io.github.xuyao5.dkl.eskits.schema.cat;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 18/09/21 21:48
 * @apiNote Count4Cat
 * @implNote Count4Cat
 */
@Data(staticConstructor = "of")
public final class Count4Cat implements Serializable {

    @SerializedName(value = "epoch", alternate = {"EPOCH"})
    private String epoch;

    @SerializedName(value = "timestamp", alternate = {"TIMESTAMP"})
    private String timestamp;

    @SerializedName(value = "count", alternate = {"COUNT"})
    private String count;
}
