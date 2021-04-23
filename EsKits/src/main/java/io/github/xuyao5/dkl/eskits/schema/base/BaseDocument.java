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
    private transient boolean _accept = true;

    //下列增删字段需要同步修改XContentSupporter.buildMapping()
    private String docId;
    private String dateTag;
    private long serialNo;
    private String recordMd5;
    private long randomNum;
    private Date createDate;
    private Date modifyDate;
}
