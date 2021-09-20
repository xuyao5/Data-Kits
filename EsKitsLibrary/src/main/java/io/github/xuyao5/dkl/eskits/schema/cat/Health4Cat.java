package io.github.xuyao5.dkl.eskits.schema.cat;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 18/09/21 21:50
 * @apiNote TODO 这里输入文件说明
 * @implNote TODO 这里输入实现说明
 */
@Data(staticConstructor = "of")
public final class Health4Cat implements Serializable {

    @SerializedName(value = "epoch", alternate = {"EPOCH"})
    private String epoch;

    @SerializedName(value = "timestamp", alternate = {"TIMESTAMP"})
    private String timestamp;

    @SerializedName(value = "cluster", alternate = {"CLUSTER"})
    private String cluster;

    @SerializedName(value = "status", alternate = {"STATUS"})
    private String status;

    @SerializedName(value = "node.total", alternate = {"NODE.TOTAL"})
    private String nodeTotal;

    @SerializedName(value = "node.data", alternate = {"NODE.DATA"})
    private String nodeData;

    @SerializedName(value = "shards", alternate = {"SHARDS"})
    private String shards;

    @SerializedName(value = "pri", alternate = {"PRI"})
    private String pri;

    @SerializedName(value = "relo", alternate = {"RELO"})
    private String relo;

    @SerializedName(value = "init", alternate = {"INIT"})
    private String init;

    @SerializedName(value = "unassign", alternate = {"UNASSIGN"})
    private String unassign;

    @SerializedName(value = "pending_tasks", alternate = {"PENDING_TASKS"})
    private String pendingTasks;

    @SerializedName(value = "max_task_wait_time", alternate = {"MAX_TASK_WAIT_TIME"})
    private String maxTaskWaitTime;

    @SerializedName(value = "active_shards_percent", alternate = {"ACTIVE_SHARDS_PERCENT"})
    private String activeShardsPercent;
}
