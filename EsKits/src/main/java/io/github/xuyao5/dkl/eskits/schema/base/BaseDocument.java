package io.github.xuyao5.dkl.eskits.schema.base;

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

    private transient String _index;
    private transient String _id;

    //下列增删字段需要同步修改XContentSupporter.buildMapping()
    private long dna;//DNA，写入永久不变
    private String dateTag;//日期Tag
    private String sourceTag;//数据源Tag
    private Date createDate;
    private Date modifyDate;
}
