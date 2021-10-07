package io.github.xuyao5.dkl.eskits.schema.httpfs;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 7/09/21 23:47
 * @apiNote HomeDirectoryJson
 * @implNote HomeDirectoryJson
 */
@Data(staticConstructor = "of")
public final class HomeDirectoryJson implements Serializable {

    @SerializedName(value = "path", alternate = {"Path"})
    private String path;
}