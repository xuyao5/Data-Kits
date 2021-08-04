package io.github.xuyao5.dkl.eskits.repository.information_schema;

import javax.annotation.Generated;
import java.io.Serializable;

public class Columns implements Serializable {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String tableCatalog;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String tableSchema;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String tableName;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String columnName;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Long ordinalPosition;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String isNullable;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String dataType;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Long characterMaximumLength;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Long characterOctetLength;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Long numericPrecision;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Long numericScale;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Long datetimePrecision;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String characterSetName;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String collationName;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String columnKey;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String extra;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String privileges;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String columnComment;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String columnDefault;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String columnType;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String generationExpression;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private static final long serialVersionUID = 1L;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Columns(String tableCatalog, String tableSchema, String tableName, String columnName, Long ordinalPosition, String isNullable, String dataType, Long characterMaximumLength, Long characterOctetLength, Long numericPrecision, Long numericScale, Long datetimePrecision, String characterSetName, String collationName, String columnKey, String extra, String privileges, String columnComment, String columnDefault, String columnType, String generationExpression) {
        this.tableCatalog = tableCatalog;
        this.tableSchema = tableSchema;
        this.tableName = tableName;
        this.columnName = columnName;
        this.ordinalPosition = ordinalPosition;
        this.isNullable = isNullable;
        this.dataType = dataType;
        this.characterMaximumLength = characterMaximumLength;
        this.characterOctetLength = characterOctetLength;
        this.numericPrecision = numericPrecision;
        this.numericScale = numericScale;
        this.datetimePrecision = datetimePrecision;
        this.characterSetName = characterSetName;
        this.collationName = collationName;
        this.columnKey = columnKey;
        this.extra = extra;
        this.privileges = privileges;
        this.columnComment = columnComment;
        this.columnDefault = columnDefault;
        this.columnType = columnType;
        this.generationExpression = generationExpression;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getTableCatalog() {
        return tableCatalog;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getTableSchema() {
        return tableSchema;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getTableName() {
        return tableName;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getColumnName() {
        return columnName;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Long getOrdinalPosition() {
        return ordinalPosition;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getIsNullable() {
        return isNullable;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getDataType() {
        return dataType;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Long getCharacterMaximumLength() {
        return characterMaximumLength;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Long getCharacterOctetLength() {
        return characterOctetLength;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Long getNumericPrecision() {
        return numericPrecision;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Long getNumericScale() {
        return numericScale;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Long getDatetimePrecision() {
        return datetimePrecision;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getCharacterSetName() {
        return characterSetName;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getCollationName() {
        return collationName;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getColumnKey() {
        return columnKey;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getExtra() {
        return extra;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getPrivileges() {
        return privileges;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getColumnComment() {
        return columnComment;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getColumnDefault() {
        return columnDefault;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getColumnType() {
        return columnType;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getGenerationExpression() {
        return generationExpression;
    }

    @Override
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Columns other = (Columns) that;
        return (this.getTableCatalog() == null ? other.getTableCatalog() == null : this.getTableCatalog().equals(other.getTableCatalog()))
                && (this.getTableSchema() == null ? other.getTableSchema() == null : this.getTableSchema().equals(other.getTableSchema()))
                && (this.getTableName() == null ? other.getTableName() == null : this.getTableName().equals(other.getTableName()))
                && (this.getColumnName() == null ? other.getColumnName() == null : this.getColumnName().equals(other.getColumnName()))
                && (this.getOrdinalPosition() == null ? other.getOrdinalPosition() == null : this.getOrdinalPosition().equals(other.getOrdinalPosition()))
                && (this.getIsNullable() == null ? other.getIsNullable() == null : this.getIsNullable().equals(other.getIsNullable()))
                && (this.getDataType() == null ? other.getDataType() == null : this.getDataType().equals(other.getDataType()))
                && (this.getCharacterMaximumLength() == null ? other.getCharacterMaximumLength() == null : this.getCharacterMaximumLength().equals(other.getCharacterMaximumLength()))
                && (this.getCharacterOctetLength() == null ? other.getCharacterOctetLength() == null : this.getCharacterOctetLength().equals(other.getCharacterOctetLength()))
                && (this.getNumericPrecision() == null ? other.getNumericPrecision() == null : this.getNumericPrecision().equals(other.getNumericPrecision()))
                && (this.getNumericScale() == null ? other.getNumericScale() == null : this.getNumericScale().equals(other.getNumericScale()))
                && (this.getDatetimePrecision() == null ? other.getDatetimePrecision() == null : this.getDatetimePrecision().equals(other.getDatetimePrecision()))
                && (this.getCharacterSetName() == null ? other.getCharacterSetName() == null : this.getCharacterSetName().equals(other.getCharacterSetName()))
                && (this.getCollationName() == null ? other.getCollationName() == null : this.getCollationName().equals(other.getCollationName()))
                && (this.getColumnKey() == null ? other.getColumnKey() == null : this.getColumnKey().equals(other.getColumnKey()))
                && (this.getExtra() == null ? other.getExtra() == null : this.getExtra().equals(other.getExtra()))
                && (this.getPrivileges() == null ? other.getPrivileges() == null : this.getPrivileges().equals(other.getPrivileges()))
                && (this.getColumnComment() == null ? other.getColumnComment() == null : this.getColumnComment().equals(other.getColumnComment()));
    }

    @Override
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getTableCatalog() == null) ? 0 : getTableCatalog().hashCode());
        result = prime * result + ((getTableSchema() == null) ? 0 : getTableSchema().hashCode());
        result = prime * result + ((getTableName() == null) ? 0 : getTableName().hashCode());
        result = prime * result + ((getColumnName() == null) ? 0 : getColumnName().hashCode());
        result = prime * result + ((getOrdinalPosition() == null) ? 0 : getOrdinalPosition().hashCode());
        result = prime * result + ((getIsNullable() == null) ? 0 : getIsNullable().hashCode());
        result = prime * result + ((getDataType() == null) ? 0 : getDataType().hashCode());
        result = prime * result + ((getCharacterMaximumLength() == null) ? 0 : getCharacterMaximumLength().hashCode());
        result = prime * result + ((getCharacterOctetLength() == null) ? 0 : getCharacterOctetLength().hashCode());
        result = prime * result + ((getNumericPrecision() == null) ? 0 : getNumericPrecision().hashCode());
        result = prime * result + ((getNumericScale() == null) ? 0 : getNumericScale().hashCode());
        result = prime * result + ((getDatetimePrecision() == null) ? 0 : getDatetimePrecision().hashCode());
        result = prime * result + ((getCharacterSetName() == null) ? 0 : getCharacterSetName().hashCode());
        result = prime * result + ((getCollationName() == null) ? 0 : getCollationName().hashCode());
        result = prime * result + ((getColumnKey() == null) ? 0 : getColumnKey().hashCode());
        result = prime * result + ((getExtra() == null) ? 0 : getExtra().hashCode());
        result = prime * result + ((getPrivileges() == null) ? 0 : getPrivileges().hashCode());
        result = prime * result + ((getColumnComment() == null) ? 0 : getColumnComment().hashCode());
        return result;
    }

    @Override
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", tableCatalog=").append(tableCatalog);
        sb.append(", tableSchema=").append(tableSchema);
        sb.append(", tableName=").append(tableName);
        sb.append(", columnName=").append(columnName);
        sb.append(", ordinalPosition=").append(ordinalPosition);
        sb.append(", isNullable=").append(isNullable);
        sb.append(", dataType=").append(dataType);
        sb.append(", characterMaximumLength=").append(characterMaximumLength);
        sb.append(", characterOctetLength=").append(characterOctetLength);
        sb.append(", numericPrecision=").append(numericPrecision);
        sb.append(", numericScale=").append(numericScale);
        sb.append(", datetimePrecision=").append(datetimePrecision);
        sb.append(", characterSetName=").append(characterSetName);
        sb.append(", collationName=").append(collationName);
        sb.append(", columnKey=").append(columnKey);
        sb.append(", extra=").append(extra);
        sb.append(", privileges=").append(privileges);
        sb.append(", columnComment=").append(columnComment);
        sb.append(", columnDefault=").append(columnDefault);
        sb.append(", columnType=").append(columnType);
        sb.append(", generationExpression=").append(generationExpression);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}