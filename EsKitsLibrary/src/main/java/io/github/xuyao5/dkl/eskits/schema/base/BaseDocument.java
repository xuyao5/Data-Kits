package io.github.xuyao5.dkl.eskits.schema.base;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 25/02/21 17:12
 * @apiNote StandardDocument
 * @implNote StandardDocument
 */
@Data
public abstract class BaseDocument implements Serializable {

    private transient String _id;

    /*
    下列增删字段需要同步修改XContentSupporter.buildMapping()
     */
    @SerializedName(value = "serialNo", alternate = {"SerialNo"})
    private Long serialNo;//永久不变序列号

    @SerializedName(value = "dateTag", alternate = {"DateTag"})
    private String dateTag;//日期Tag

    @SerializedName(value = "sourceTag", alternate = {"SourceTag"})
    private String sourceTag;//数据源Tag

    @SerializedName(value = "createDate", alternate = {"CreateDate"})
    private Date createDate;

    @SerializedName(value = "modifyDate", alternate = {"ModifyDate"})
    private Date modifyDate;
}
