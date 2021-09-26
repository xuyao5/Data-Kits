package io.github.xuyao5.dkl.eskits.schema.httpfs;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 11/09/21 19:11
 * @apiNote FileChecksum
 * @implNote FileChecksum
 */
@Data(staticConstructor = "of")
public final class FileChecksum {

    @SerializedName(value = "Algorithm", alternate = "algorithm")
    private String algorithm;

    @SerializedName(value = "Bytes", alternate = "bytes")
    private String bytes;

    @SerializedName(value = "Length", alternate = "length")
    private int length;
}
