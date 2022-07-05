package io.github.xuyao5.datakitsserver.dao.primary.model;

import java.io.Serializable;
import java.util.Date;

public class OmsOrder1 implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Date applyDate;
    private String custNo;
    private String orderId;
    private Integer lineNo;
    private String lineRecord;
    private Date createDate;
    private Date updateDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OmsOrder1 withId(Long id) {
        this.setId(id);
        return this;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public OmsOrder1 withApplyDate(Date applyDate) {
        this.setApplyDate(applyDate);
        return this;
    }

    public String getCustNo() {
        return custNo;
    }

    public void setCustNo(String custNo) {
        this.custNo = custNo == null ? null : custNo.trim();
    }

    public OmsOrder1 withCustNo(String custNo) {
        this.setCustNo(custNo);
        return this;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public OmsOrder1 withOrderId(String orderId) {
        this.setOrderId(orderId);
        return this;
    }

    public Integer getLineNo() {
        return lineNo;
    }

    public void setLineNo(Integer lineNo) {
        this.lineNo = lineNo;
    }

    public OmsOrder1 withLineNo(Integer lineNo) {
        this.setLineNo(lineNo);
        return this;
    }

    public String getLineRecord() {
        return lineRecord;
    }

    public void setLineRecord(String lineRecord) {
        this.lineRecord = lineRecord == null ? null : lineRecord.trim();
    }

    public OmsOrder1 withLineRecord(String lineRecord) {
        this.setLineRecord(lineRecord);
        return this;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public OmsOrder1 withCreateDate(Date createDate) {
        this.setCreateDate(createDate);
        return this;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public OmsOrder1 withUpdateDate(Date updateDate) {
        this.setUpdateDate(updateDate);
        return this;
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
        OmsOrder1 other = (OmsOrder1) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getApplyDate() == null ? other.getApplyDate() == null : this.getApplyDate().equals(other.getApplyDate()))
                && (this.getCustNo() == null ? other.getCustNo() == null : this.getCustNo().equals(other.getCustNo()))
                && (this.getOrderId() == null ? other.getOrderId() == null : this.getOrderId().equals(other.getOrderId()))
                && (this.getLineNo() == null ? other.getLineNo() == null : this.getLineNo().equals(other.getLineNo()))
                && (this.getLineRecord() == null ? other.getLineRecord() == null : this.getLineRecord().equals(other.getLineRecord()))
                && (this.getCreateDate() == null ? other.getCreateDate() == null : this.getCreateDate().equals(other.getCreateDate()))
                && (this.getUpdateDate() == null ? other.getUpdateDate() == null : this.getUpdateDate().equals(other.getUpdateDate()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getApplyDate() == null) ? 0 : getApplyDate().hashCode());
        result = prime * result + ((getCustNo() == null) ? 0 : getCustNo().hashCode());
        result = prime * result + ((getOrderId() == null) ? 0 : getOrderId().hashCode());
        result = prime * result + ((getLineNo() == null) ? 0 : getLineNo().hashCode());
        result = prime * result + ((getLineRecord() == null) ? 0 : getLineRecord().hashCode());
        result = prime * result + ((getCreateDate() == null) ? 0 : getCreateDate().hashCode());
        result = prime * result + ((getUpdateDate() == null) ? 0 : getUpdateDate().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", applyDate=").append(applyDate);
        sb.append(", custNo=").append(custNo);
        sb.append(", orderId=").append(orderId);
        sb.append(", lineNo=").append(lineNo);
        sb.append(", lineRecord=").append(lineRecord);
        sb.append(", createDate=").append(createDate);
        sb.append(", updateDate=").append(updateDate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}