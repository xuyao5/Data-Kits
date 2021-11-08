package io.github.xuyao5.dkl.eskits.schema.httpfs;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

/**
 * @author Thomas.XU(xuyao)
 * @version 7/09/21 23:45
 */
@Data(staticConstructor = "of")
public final class FileStatuses {

    @SerializedName(value = "fileStatus", alternate = {"FileStatus"})
    private List<FileStatus> fileStatusList;
}