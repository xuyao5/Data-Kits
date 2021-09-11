package io.github.xuyao5.dkl.eskits.schema.httpfs;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 11/09/21 19:15
 * @apiNote FileChecksumJson
 * @implNote FileChecksumJson
 */
@Data(staticConstructor = "of")
public class FileChecksumJson implements Serializable {

    @SerializedName(value = "FileChecksum", alternate = "fileChecksum")
    private FileChecksum fileChecksum;
}
