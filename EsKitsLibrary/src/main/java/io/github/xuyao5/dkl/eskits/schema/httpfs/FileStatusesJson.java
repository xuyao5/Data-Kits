package io.github.xuyao5.dkl.eskits.schema.httpfs;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 7/09/21 23:46
 * @apiNote FileStatusesJson
 * @implNote FileStatusesJson
 */
@Data(staticConstructor = "of")
public final class FileStatusesJson implements Serializable {

    @SerializedName(value = "fileStatuses", alternate = {"FileStatuses"})
    private FileStatuses fileStatuses;
}