package io.github.xuyao5.dal.generator.repository.paces.model;

import javax.annotation.Generated;
import java.io.Serializable;

public class BatchJobInstance implements Serializable {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private static final long serialVersionUID = 1L;
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Long jobInstanceId;
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Long version;
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String jobName;
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String jobKey;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Long getJobInstanceId() {
        return jobInstanceId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setJobInstanceId(Long jobInstanceId) {
        this.jobInstanceId = jobInstanceId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public BatchJobInstance withJobInstanceId(Long jobInstanceId) {
        this.setJobInstanceId(jobInstanceId);
        return this;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Long getVersion() {
        return version;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setVersion(Long version) {
        this.version = version;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public BatchJobInstance withVersion(Long version) {
        this.setVersion(version);
        return this;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getJobName() {
        return jobName;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setJobName(String jobName) {
        this.jobName = jobName == null ? null : jobName.trim();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public BatchJobInstance withJobName(String jobName) {
        this.setJobName(jobName);
        return this;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getJobKey() {
        return jobKey;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setJobKey(String jobKey) {
        this.jobKey = jobKey == null ? null : jobKey.trim();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public BatchJobInstance withJobKey(String jobKey) {
        this.setJobKey(jobKey);
        return this;
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
        BatchJobInstance other = (BatchJobInstance) that;
        return (this.getJobInstanceId() == null ? other.getJobInstanceId() == null : this.getJobInstanceId().equals(other.getJobInstanceId()))
                && (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
                && (this.getJobName() == null ? other.getJobName() == null : this.getJobName().equals(other.getJobName()))
                && (this.getJobKey() == null ? other.getJobKey() == null : this.getJobKey().equals(other.getJobKey()));
    }

    @Override
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getJobInstanceId() == null) ? 0 : getJobInstanceId().hashCode());
        result = prime * result + ((getVersion() == null) ? 0 : getVersion().hashCode());
        result = prime * result + ((getJobName() == null) ? 0 : getJobName().hashCode());
        result = prime * result + ((getJobKey() == null) ? 0 : getJobKey().hashCode());
        return result;
    }

    @Override
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", jobInstanceId=").append(jobInstanceId);
        sb.append(", version=").append(version);
        sb.append(", jobName=").append(jobName);
        sb.append(", jobKey=").append(jobKey);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}