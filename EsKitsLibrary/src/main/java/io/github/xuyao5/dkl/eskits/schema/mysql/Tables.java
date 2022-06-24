package io.github.xuyao5.dkl.eskits.schema.mysql;

import java.io.Serializable;
import java.util.Date;

public class Tables implements Serializable {
    private String tableCatalog;

    private String tableSchema;

    private String tableName;

    private String tableType;

    private String engine;

    private static final long serialVersionUID = 1L;

    private String rowFormat;

    private Long tableRows;

    private Long avgRowLength;

    private Long dataLength;

    private Long maxDataLength;

    private Long indexLength;

    private Long dataFree;

    private Long autoIncrement;

    private Date createTime;

    private Date updateTime;

    private Date checkTime;

    private String tableCollation;

    private Long checksum;

    private String createOptions;

    private String tableComment;
    private Integer version;

    public String getTableCatalog() {
        return tableCatalog;
    }

    public Tables withTableCatalog(String tableCatalog) {
        this.setTableCatalog(tableCatalog);
        return this;
    }

    public void setTableCatalog(String tableCatalog) {
        this.tableCatalog = tableCatalog == null ? null : tableCatalog.trim();
    }

    public String getTableSchema() {
        return tableSchema;
    }

    public Tables withTableSchema(String tableSchema) {
        this.setTableSchema(tableSchema);
        return this;
    }

    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema == null ? null : tableSchema.trim();
    }

    public String getTableName() {
        return tableName;
    }

    public Tables withTableName(String tableName) {
        this.setTableName(tableName);
        return this;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName == null ? null : tableName.trim();
    }

    public String getTableType() {
        return tableType;
    }

    public Tables withTableType(String tableType) {
        this.setTableType(tableType);
        return this;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType == null ? null : tableType.trim();
    }

    public String getEngine() {
        return engine;
    }

    public Tables withEngine(String engine) {
        this.setEngine(engine);
        return this;
    }

    public void setEngine(String engine) {
        this.engine = engine == null ? null : engine.trim();
    }

    public Integer getVersion() {
        return version;
    }

    public Tables withVersion(Integer version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getRowFormat() {
        return rowFormat;
    }

    public Tables withRowFormat(String rowFormat) {
        this.setRowFormat(rowFormat);
        return this;
    }

    public void setRowFormat(String rowFormat) {
        this.rowFormat = rowFormat == null ? null : rowFormat.trim();
    }

    public Long getTableRows() {
        return tableRows;
    }

    public Tables withTableRows(Long tableRows) {
        this.setTableRows(tableRows);
        return this;
    }

    public void setTableRows(Long tableRows) {
        this.tableRows = tableRows;
    }

    public Long getAvgRowLength() {
        return avgRowLength;
    }

    public Tables withAvgRowLength(Long avgRowLength) {
        this.setAvgRowLength(avgRowLength);
        return this;
    }

    public void setAvgRowLength(Long avgRowLength) {
        this.avgRowLength = avgRowLength;
    }

    public Long getDataLength() {
        return dataLength;
    }

    public Tables withDataLength(Long dataLength) {
        this.setDataLength(dataLength);
        return this;
    }

    public void setDataLength(Long dataLength) {
        this.dataLength = dataLength;
    }

    public Long getMaxDataLength() {
        return maxDataLength;
    }

    public Tables withMaxDataLength(Long maxDataLength) {
        this.setMaxDataLength(maxDataLength);
        return this;
    }

    public void setMaxDataLength(Long maxDataLength) {
        this.maxDataLength = maxDataLength;
    }

    public Long getIndexLength() {
        return indexLength;
    }

    public Tables withIndexLength(Long indexLength) {
        this.setIndexLength(indexLength);
        return this;
    }

    public void setIndexLength(Long indexLength) {
        this.indexLength = indexLength;
    }

    public Long getDataFree() {
        return dataFree;
    }

    public Tables withDataFree(Long dataFree) {
        this.setDataFree(dataFree);
        return this;
    }

    public void setDataFree(Long dataFree) {
        this.dataFree = dataFree;
    }

    public Long getAutoIncrement() {
        return autoIncrement;
    }

    public Tables withAutoIncrement(Long autoIncrement) {
        this.setAutoIncrement(autoIncrement);
        return this;
    }

    public void setAutoIncrement(Long autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Tables withCreateTime(Date createTime) {
        this.setCreateTime(createTime);
        return this;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public Tables withUpdateTime(Date updateTime) {
        this.setUpdateTime(updateTime);
        return this;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public Tables withCheckTime(Date checkTime) {
        this.setCheckTime(checkTime);
        return this;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public String getTableCollation() {
        return tableCollation;
    }

    public Tables withTableCollation(String tableCollation) {
        this.setTableCollation(tableCollation);
        return this;
    }

    public void setTableCollation(String tableCollation) {
        this.tableCollation = tableCollation == null ? null : tableCollation.trim();
    }

    public Long getChecksum() {
        return checksum;
    }

    public Tables withChecksum(Long checksum) {
        this.setChecksum(checksum);
        return this;
    }

    public void setChecksum(Long checksum) {
        this.checksum = checksum;
    }

    public String getCreateOptions() {
        return createOptions;
    }

    public Tables withCreateOptions(String createOptions) {
        this.setCreateOptions(createOptions);
        return this;
    }

    public void setCreateOptions(String createOptions) {
        this.createOptions = createOptions == null ? null : createOptions.trim();
    }

    public String getTableComment() {
        return tableComment;
    }

    public Tables withTableComment(String tableComment) {
        this.setTableComment(tableComment);
        return this;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment == null ? null : tableComment.trim();
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
        Tables other = (Tables) that;
        return (this.getTableCatalog() == null ? other.getTableCatalog() == null : this.getTableCatalog().equals(other.getTableCatalog()))
                && (this.getTableSchema() == null ? other.getTableSchema() == null : this.getTableSchema().equals(other.getTableSchema()))
                && (this.getTableName() == null ? other.getTableName() == null : this.getTableName().equals(other.getTableName()))
                && (this.getTableType() == null ? other.getTableType() == null : this.getTableType().equals(other.getTableType()))
                && (this.getEngine() == null ? other.getEngine() == null : this.getEngine().equals(other.getEngine()))
                && (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
                && (this.getRowFormat() == null ? other.getRowFormat() == null : this.getRowFormat().equals(other.getRowFormat()))
                && (this.getTableRows() == null ? other.getTableRows() == null : this.getTableRows().equals(other.getTableRows()))
                && (this.getAvgRowLength() == null ? other.getAvgRowLength() == null : this.getAvgRowLength().equals(other.getAvgRowLength()))
                && (this.getDataLength() == null ? other.getDataLength() == null : this.getDataLength().equals(other.getDataLength()))
                && (this.getMaxDataLength() == null ? other.getMaxDataLength() == null : this.getMaxDataLength().equals(other.getMaxDataLength()))
                && (this.getIndexLength() == null ? other.getIndexLength() == null : this.getIndexLength().equals(other.getIndexLength()))
                && (this.getDataFree() == null ? other.getDataFree() == null : this.getDataFree().equals(other.getDataFree()))
                && (this.getAutoIncrement() == null ? other.getAutoIncrement() == null : this.getAutoIncrement().equals(other.getAutoIncrement()))
                && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
                && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
                && (this.getCheckTime() == null ? other.getCheckTime() == null : this.getCheckTime().equals(other.getCheckTime()))
                && (this.getTableCollation() == null ? other.getTableCollation() == null : this.getTableCollation().equals(other.getTableCollation()))
                && (this.getChecksum() == null ? other.getChecksum() == null : this.getChecksum().equals(other.getChecksum()))
                && (this.getCreateOptions() == null ? other.getCreateOptions() == null : this.getCreateOptions().equals(other.getCreateOptions()))
                && (this.getTableComment() == null ? other.getTableComment() == null : this.getTableComment().equals(other.getTableComment()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getTableCatalog() == null) ? 0 : getTableCatalog().hashCode());
        result = prime * result + ((getTableSchema() == null) ? 0 : getTableSchema().hashCode());
        result = prime * result + ((getTableName() == null) ? 0 : getTableName().hashCode());
        result = prime * result + ((getTableType() == null) ? 0 : getTableType().hashCode());
        result = prime * result + ((getEngine() == null) ? 0 : getEngine().hashCode());
        result = prime * result + ((getVersion() == null) ? 0 : getVersion().hashCode());
        result = prime * result + ((getRowFormat() == null) ? 0 : getRowFormat().hashCode());
        result = prime * result + ((getTableRows() == null) ? 0 : getTableRows().hashCode());
        result = prime * result + ((getAvgRowLength() == null) ? 0 : getAvgRowLength().hashCode());
        result = prime * result + ((getDataLength() == null) ? 0 : getDataLength().hashCode());
        result = prime * result + ((getMaxDataLength() == null) ? 0 : getMaxDataLength().hashCode());
        result = prime * result + ((getIndexLength() == null) ? 0 : getIndexLength().hashCode());
        result = prime * result + ((getDataFree() == null) ? 0 : getDataFree().hashCode());
        result = prime * result + ((getAutoIncrement() == null) ? 0 : getAutoIncrement().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getCheckTime() == null) ? 0 : getCheckTime().hashCode());
        result = prime * result + ((getTableCollation() == null) ? 0 : getTableCollation().hashCode());
        result = prime * result + ((getChecksum() == null) ? 0 : getChecksum().hashCode());
        result = prime * result + ((getCreateOptions() == null) ? 0 : getCreateOptions().hashCode());
        result = prime * result + ((getTableComment() == null) ? 0 : getTableComment().hashCode());
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
        sb.append(", tableType=").append(tableType);
        sb.append(", engine=").append(engine);
        sb.append(", version=").append(version);
        sb.append(", rowFormat=").append(rowFormat);
        sb.append(", tableRows=").append(tableRows);
        sb.append(", avgRowLength=").append(avgRowLength);
        sb.append(", dataLength=").append(dataLength);
        sb.append(", maxDataLength=").append(maxDataLength);
        sb.append(", indexLength=").append(indexLength);
        sb.append(", dataFree=").append(dataFree);
        sb.append(", autoIncrement=").append(autoIncrement);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", checkTime=").append(checkTime);
        sb.append(", tableCollation=").append(tableCollation);
        sb.append(", checksum=").append(checksum);
        sb.append(", createOptions=").append(createOptions);
        sb.append(", tableComment=").append(tableComment);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}