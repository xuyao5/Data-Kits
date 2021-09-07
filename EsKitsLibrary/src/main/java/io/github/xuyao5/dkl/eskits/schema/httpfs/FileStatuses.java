package io.github.xuyao5.dkl.eskits.schema.httpfs;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 7/09/21 23:45
 * @apiNote FileStatuses
 * @implNote FileStatuses
 */
@Data(staticConstructor = "of")
public final class FileStatuses implements Serializable {

    @SerializedName(value = "fileStatus", alternate = {"FileStatus"})
    private List<FileStatus> fileStatusList;
}