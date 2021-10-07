package io.github.xuyao5.dkl.eskits.schema.httpfs;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * @author Thomas.XU(xuyao)
 * @version 7/09/21 23:44
 */
@Data(staticConstructor = "of")
public final class FileStatus {

    @SerializedName(value = "pathSuffix", alternate = {"PathSuffix"})
    private String pathSuffix;

    @SerializedName(value = "type", alternate = {"Type"})
    private String type;

    @SerializedName(value = "length", alternate = {"Length"})
    private long length;

    @SerializedName(value = "owner", alternate = {"Owner"})
    private String owner;

    @SerializedName(value = "group", alternate = {"Group"})
    private String group;

    @SerializedName(value = "permission", alternate = {"Permission"})
    private String permission;

    @SerializedName(value = "accessTime", alternate = {"AccessTime"})
    private long accessTime;

    @SerializedName(value = "modificationTime", alternate = {"ModificationTime"})
    private long modificationTime;

    @SerializedName(value = "blockSize", alternate = {"BlockSize"})
    private long blockSize;

    @SerializedName(value = "replication", alternate = {"Replication"})
    private long replication;
}