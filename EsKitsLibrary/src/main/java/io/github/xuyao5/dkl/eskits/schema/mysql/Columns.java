package io.github.xuyao5.dkl.eskits.schema.mysql;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 30/09/21 10:44
 * @apiNote information_schema
 * @implNote Columns
 */
@Data(staticConstructor = "of")
public final class Columns implements Serializable {

    private String tableSchema;

    private String tableName;

    private String columnName;

    private long ordinalPosition;

    private String columnKey;

    private String extra;
}