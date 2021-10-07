package io.github.xuyao5.dkl.eskits.schema.cat;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Thomas.XU(xuyao)
 * @version 18/09/21 18:53
 */
@Data(staticConstructor = "of")
public final class Tasks4Cat implements Serializable {

    @SerializedName(value = "action", alternate = {"ACTION"})
    private String action;

    @SerializedName(value = "task_id", alternate = {"TASK_ID"})
    private String taskId;

    @SerializedName(value = "parent_task_id", alternate = {"PARENT_TASK_ID"})
    private String parentTaskId;

    @SerializedName(value = "type", alternate = {"TYPE"})
    private String type;

    @SerializedName(value = "start_time", alternate = {"START_TIME"})
    private String startTime;

    @SerializedName(value = "timestamp", alternate = {"TIMESTAMP"})
    private String timestamp;

    @SerializedName(value = "running_time", alternate = {"RUNNING_TIME"})
    private String runningTime;

    @SerializedName(value = "ip", alternate = {"IP"})
    private String ip;

    @SerializedName(value = "node", alternate = {"NODE"})
    private String node;
}
