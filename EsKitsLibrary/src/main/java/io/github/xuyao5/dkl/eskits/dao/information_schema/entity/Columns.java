package io.github.xuyao5.dkl.eskits.dao.information_schema.entity;

import lombok.Data;

import java.io.Serializable;

@Data(staticConstructor = "of")
public class Columns implements Serializable {

    private String tableCatalog;

    private String tableSchema;

    private String tableName;

    private String columnName;

    private Long ordinalPosition;

    private String isNullable;

    private String dataType;

    private Long characterMaximumLength;

    private Long characterOctetLength;

    private Long numericPrecision;

    private Long numericScale;

    private Long datetimePrecision;

    private String characterSetName;

    private String collationName;

    private String columnKey;

    private String extra;

    private String privileges;

    private String columnComment;

    private String columnDefault;

    private String columnType;

    private String generationExpression;
}