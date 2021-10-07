package io.github.xuyao5.dkl.eskits.schema.cat;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Thomas.XU(xuyao)
 * @version 18/09/21 22:00
 */
@Data(staticConstructor = "of")
public final class ThreadPool4Cat implements Serializable {

    @SerializedName(value = "node_name", alternate = {"NODE_NAME"})
    private String nodeName;

    @SerializedName(value = "name", alternate = {"NAME"})
    private String name;

    @SerializedName(value = "active", alternate = {"ACTIVE"})
    private String active;

    @SerializedName(value = "queue", alternate = {"QUEUE"})
    private String queue;

    @SerializedName(value = "rejected", alternate = {"REJECTED"})
    private String rejected;
}
