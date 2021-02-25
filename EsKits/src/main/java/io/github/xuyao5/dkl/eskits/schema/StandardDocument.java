package io.github.xuyao5.dkl.eskits.schema;

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
public abstract class StandardDocument implements Serializable {

    private transient String index;

    private String recordId;
    private String dateTag;
    private String serialNo;
    private String collapse;
    private String allFieldMd5;
    private Date createDate;
    private Date modifyDate;
}
