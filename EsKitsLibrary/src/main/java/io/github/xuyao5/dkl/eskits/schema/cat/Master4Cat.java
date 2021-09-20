package io.github.xuyao5.dkl.eskits.schema.cat;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 18/09/21 18:51
 * @apiNote TODO 这里输入文件说明
 * @implNote TODO 这里输入实现说明
 */
@Data(staticConstructor = "of")
public final class Master4Cat implements Serializable {

    @SerializedName(value = "id", alternate = {"ID"})
    private String id;

    @SerializedName(value = "host", alternate = {"HOST"})
    private String host;

    @SerializedName(value = "ip", alternate = {"IP"})
    private String ip;

    @SerializedName(value = "node", alternate = {"NODE"})
    private String node;
}
