package io.github.xuyao5.dkl.eskits.schema.httpfs;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 7/09/21 23:47
 * @apiNote HomeDirectory
 * @implNote HomeDirectory
 */
@Data(staticConstructor = "of")
public final class HomeDirectory implements Serializable {

    @SerializedName(value = "path", alternate = {"Path"})
    private String path;
}