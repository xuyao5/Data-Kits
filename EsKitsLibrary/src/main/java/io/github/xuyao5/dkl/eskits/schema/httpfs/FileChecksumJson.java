package io.github.xuyao5.dkl.eskits.schema.httpfs;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Thomas.XU(xuyao)
 * @version 11/09/21 19:15
 */
@Data(staticConstructor = "of")
public final class FileChecksumJson implements Serializable {

    @SerializedName(value = "FileChecksum", alternate = "fileChecksum")
    private FileChecksum fileChecksum;
}
