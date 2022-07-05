package io.github.xuyao5.dkl.eskits.schema.mysql;

import java.io.Serializable;

public class Columns implements Serializable {
    private String tableCatalog;

    private String tableSchema;

    private String tableName;

    private String columnName;

    private static final long serialVersionUID = 1L;

    private String isNullable;

    private Long characterMaximumLength;

    private Long characterOctetLength;

    private Long numericPrecision;

    private Long numericScale;
    private Integer ordinalPosition;

    private String characterSetName;

    private String collationName;

    private String columnKey;

    private String extra;

    private String privileges;

    private Integer srsId;
    private Integer datetimePrecision;

    private String dataType;
    private String columnDefault;

    private String columnComment;

    private String generationExpression;
    private String columnType;

    public String getTableCatalog() {
        return tableCatalog;
    }

    public Columns withTableCatalog(String tableCatalog) {
        this.setTableCatalog(tableCatalog);
        return this;
    }

    public void setTableCatalog(String tableCatalog) {
        this.tableCatalog = tableCatalog == null ? null : tableCatalog.trim();
    }

    public String getTableSchema() {
        return tableSchema;
    }

    public Columns withTableSchema(String tableSchema) {
        this.setTableSchema(tableSchema);
        return this;
    }

    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema == null ? null : tableSchema.trim();
    }

    public String getTableName() {
        return tableName;
    }

    public Columns withTableName(String tableName) {
        this.setTableName(tableName);
        return this;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName == null ? null : tableName.trim();
    }

    public String getColumnName() {
        return columnName;
    }

    public Columns withColumnName(String columnName) {
        this.setColumnName(columnName);
        return this;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName == null ? null : columnName.trim();
    }

    public Integer getOrdinalPosition() {
        return ordinalPosition;
    }

    public Columns withOrdinalPosition(Integer ordinalPosition) {
        this.setOrdinalPosition(ordinalPosition);
        return this;
    }

    public void setOrdinalPosition(Integer ordinalPosition) {
        this.ordinalPosition = ordinalPosition;
    }

    public String getIsNullable() {
        return isNullable;
    }

    public Columns withIsNullable(String isNullable) {
        this.setIsNullable(isNullable);
        return this;
    }

    public void setIsNullable(String isNullable) {
        this.isNullable = isNullable == null ? null : isNullable.trim();
    }

    public Long getCharacterMaximumLength() {
        return characterMaximumLength;
    }

    public Columns withCharacterMaximumLength(Long characterMaximumLength) {
        this.setCharacterMaximumLength(characterMaximumLength);
        return this;
    }

    public void setCharacterMaximumLength(Long characterMaximumLength) {
        this.characterMaximumLength = characterMaximumLength;
    }

    public Long getCharacterOctetLength() {
        return characterOctetLength;
    }

    public Columns withCharacterOctetLength(Long characterOctetLength) {
        this.setCharacterOctetLength(characterOctetLength);
        return this;
    }

    public void setCharacterOctetLength(Long characterOctetLength) {
        this.characterOctetLength = characterOctetLength;
    }

    public Long getNumericPrecision() {
        return numericPrecision;
    }

    public Columns withNumericPrecision(Long numericPrecision) {
        this.setNumericPrecision(numericPrecision);
        return this;
    }

    public void setNumericPrecision(Long numericPrecision) {
        this.numericPrecision = numericPrecision;
    }

    public Long getNumericScale() {
        return numericScale;
    }

    public Columns withNumericScale(Long numericScale) {
        this.setNumericScale(numericScale);
        return this;
    }

    public void setNumericScale(Long numericScale) {
        this.numericScale = numericScale;
    }

    public Integer getDatetimePrecision() {
        return datetimePrecision;
    }

    public Columns withDatetimePrecision(Integer datetimePrecision) {
        this.setDatetimePrecision(datetimePrecision);
        return this;
    }

    public void setDatetimePrecision(Integer datetimePrecision) {
        this.datetimePrecision = datetimePrecision;
    }

    public String getCharacterSetName() {
        return characterSetName;
    }

    public Columns withCharacterSetName(String characterSetName) {
        this.setCharacterSetName(characterSetName);
        return this;
    }

    public void setCharacterSetName(String characterSetName) {
        this.characterSetName = characterSetName == null ? null : characterSetName.trim();
    }

    public String getCollationName() {
        return collationName;
    }

    public Columns withCollationName(String collationName) {
        this.setCollationName(collationName);
        return this;
    }

    public void setCollationName(String collationName) {
        this.collationName = collationName == null ? null : collationName.trim();
    }

    public String getColumnKey() {
        return columnKey;
    }

    public Columns withColumnKey(String columnKey) {
        this.setColumnKey(columnKey);
        return this;
    }

    public void setColumnKey(String columnKey) {
        this.columnKey = columnKey == null ? null : columnKey.trim();
    }

    public String getExtra() {
        return extra;
    }

    public Columns withExtra(String extra) {
        this.setExtra(extra);
        return this;
    }

    public void setExtra(String extra) {
        this.extra = extra == null ? null : extra.trim();
    }

    public String getPrivileges() {
        return privileges;
    }

    public Columns withPrivileges(String privileges) {
        this.setPrivileges(privileges);
        return this;
    }

    public void setPrivileges(String privileges) {
        this.privileges = privileges == null ? null : privileges.trim();
    }

    public Integer getSrsId() {
        return srsId;
    }

    public Columns withSrsId(Integer srsId) {
        this.setSrsId(srsId);
        return this;
    }

    public void setSrsId(Integer srsId) {
        this.srsId = srsId;
    }

    public String getColumnDefault() {
        return columnDefault;
    }

    public Columns withColumnDefault(String columnDefault) {
        this.setColumnDefault(columnDefault);
        return this;
    }

    public void setColumnDefault(String columnDefault) {
        this.columnDefault = columnDefault == null ? null : columnDefault.trim();
    }

    public String getDataType() {
        return dataType;
    }

    public Columns withDataType(String dataType) {
        this.setDataType(dataType);
        return this;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType == null ? null : dataType.trim();
    }

    public String getColumnType() {
        return columnType;
    }

    public Columns withColumnType(String columnType) {
        this.setColumnType(columnType);
        return this;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType == null ? null : columnType.trim();
    }

    public String getColumnComment() {
        return columnComment;
    }

    public Columns withColumnComment(String columnComment) {
        this.setColumnComment(columnComment);
        return this;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment == null ? null : columnComment.trim();
    }

    public String getGenerationExpression() {
        return generationExpression;
    }

    public Columns withGenerationExpression(String generationExpression) {
        this.setGenerationExpression(generationExpression);
        return this;
    }

    public void setGenerationExpression(String generationExpression) {
        this.generationExpression = generationExpression == null ? null : generationExpression.trim();
    }

    @Override
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
                && (this.getSrsId() == null ? other.getSrsId() == null : this.getSrsId().equals(other.getSrsId()))
                && (this.getColumnDefault() == null ? other.getColumnDefault() == null : this.getColumnDefault().equals(other.getColumnDefault()))
                && (this.getDataType() == null ? other.getDataType() == null : this.getDataType().equals(other.getDataType()))
                && (this.getColumnType() == null ? other.getColumnType() == null : this.getColumnType().equals(other.getColumnType()))
                && (this.getColumnComment() == null ? other.getColumnComment() == null : this.getColumnComment().equals(other.getColumnComment()))
                && (this.getGenerationExpression() == null ? other.getGenerationExpression() == null : this.getGenerationExpression().equals(other.getGenerationExpression()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getTableCatalog() == null) ? 0 : getTableCatalog().hashCode());
        result = prime * result + ((getTableSchema() == null) ? 0 : getTableSchema().hashCode());
        result = prime * result + ((getTableName() == null) ? 0 : getTableName().hashCode());
        result = prime * result + ((getColumnName() == null) ? 0 : getColumnName().hashCode());
        result = prime * result + ((getOrdinalPosition() == null) ? 0 : getOrdinalPosition().hashCode());
        result = prime * result + ((getIsNullable() == null) ? 0 : getIsNullable().hashCode());
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
        result = prime * result + ((getSrsId() == null) ? 0 : getSrsId().hashCode());
        result = prime * result + ((getColumnDefault() == null) ? 0 : getColumnDefault().hashCode());
        result = prime * result + ((getDataType() == null) ? 0 : getDataType().hashCode());
        result = prime * result + ((getColumnType() == null) ? 0 : getColumnType().hashCode());
        result = prime * result + ((getColumnComment() == null) ? 0 : getColumnComment().hashCode());
        result = prime * result + ((getGenerationExpression() == null) ? 0 : getGenerationExpression().hashCode());
        return result;
    }

    @Override
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
        sb.append(", srsId=").append(srsId);
        sb.append(", columnDefault=").append(columnDefault);
        sb.append(", dataType=").append(dataType);
        sb.append(", columnType=").append(columnType);
        sb.append(", columnComment=").append(columnComment);
        sb.append(", generationExpression=").append(generationExpression);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}