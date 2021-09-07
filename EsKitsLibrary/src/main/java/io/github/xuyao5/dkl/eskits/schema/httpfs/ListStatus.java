package io.github.xuyao5.dkl.eskits.schema.httpfs;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 7/09/21 23:46
 * @apiNote ListStatus
 * @implNote ListStatus
 */
@Data(staticConstructor = "of")
public final class ListStatus implements Serializable {

    @SerializedName(value = "fileStatuses", alternate = {"FileStatuses"})
    private FileStatuses fileStatuses;
}