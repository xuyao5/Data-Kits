package io.github.xuyao5.dkl.eskits.schema.cat;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 18/09/21 21:49
 * @apiNote Recovery4Cat
 * @implNote Recovery4Cat
 */
@Data(staticConstructor = "of")
public final class Recovery4Cat implements Serializable {

    @SerializedName(value = "index", alternate = {"INDEX"})
    private String index;

    @SerializedName(value = "shard", alternate = {"SHARD"})
    private String shard;

    @SerializedName(value = "time", alternate = {"TIME"})
    private String time;

    @SerializedName(value = "type", alternate = {"TYPE"})
    private String type;

    @SerializedName(value = "stage", alternate = {"STAGE"})
    private String stage;

    @SerializedName(value = "source_host", alternate = {"SOURCE_HOST"})
    private String sourceHost;

    @SerializedName(value = "source_node", alternate = {"SOURCE_NODE"})
    private String sourceNode;

    @SerializedName(value = "target_host", alternate = {"TARGET_HOST"})
    private String targetHost;

    @SerializedName(value = "target_node", alternate = {"TARGET_NODE"})
    private String targetNode;

    @SerializedName(value = "repository", alternate = {"REPOSITORY"})
    private String repository;

    @SerializedName(value = "snapshot", alternate = {"SNAPSHOT"})
    private String snapshot;

    @SerializedName(value = "files", alternate = {"FILES"})
    private String files;

    @SerializedName(value = "files_recovered", alternate = {"FILES_RECOVERED"})
    private String filesRecovered;

    @SerializedName(value = "files_percent", alternate = {"FILES_PERCENT"})
    private String filesPercent;

    @SerializedName(value = "files_total", alternate = {"FILES_TOTAL"})
    private String filesTotal;

    @SerializedName(value = "bytes", alternate = {"BYTES"})
    private String bytes;

    @SerializedName(value = "bytes_recovered", alternate = {"BYTES_RECOVERED"})
    private String bytesRecovered;

    @SerializedName(value = "bytes_percent", alternate = {"BYTES_PERCENT"})
    private String bytesPercent;

    @SerializedName(value = "bytes_total", alternate = {"BYTES_TOTAL"})
    private String bytesTotal;

    @SerializedName(value = "translog_ops", alternate = {"TRANSLOG_OPS"})
    private String translogOps;

    @SerializedName(value = "translog_ops_recovered", alternate = {"TRANSLOG_OPS_RECOVERED"})
    private String translogOpsRecovered;

    @SerializedName(value = "translog_ops_percent", alternate = {"TRANSLOG_OPS_PERCENT"})
    private String translogOpsPercent;
}
