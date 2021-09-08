package io.github.xuyao5.dkl.eskits.schema.httpfs;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 7/09/21 23:45
 * @apiNote FileStatuses
 * @implNote FileStatuses
 */
@Data(staticConstructor = "of")
public final class FileStatuses2 implements Serializable {

    @SerializedName(value = "fileStatus", alternate = {"FileStatus"})
    private FileStatus fileStatus;
}