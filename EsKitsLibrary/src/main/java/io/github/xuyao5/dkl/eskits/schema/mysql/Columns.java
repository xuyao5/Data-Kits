package io.github.xuyao5.dkl.eskits.schema.mysql;

import lombok.Data;

import java.io.Serializable;

@Data(staticConstructor = "of")
public class Columns implements Serializable {

    private String tableSchema;

    private String tableName;

    private String columnName;

    private long ordinalPosition;

    private String columnKey;

    private String extra;
}