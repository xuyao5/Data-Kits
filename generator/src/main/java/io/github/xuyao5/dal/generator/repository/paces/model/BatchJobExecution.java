package io.github.xuyao5.dal.generator.repository.paces.model;

import java.io.Serializable;
import java.util.Date;
import javax.annotation.Generated;

public class BatchJobExecution implements Serializable {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Long jobExecutionId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Long version;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Long jobInstanceId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Date createTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Date startTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Date endTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String status;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String exitCode;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String exitMessage;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private Date lastUpdated;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private String jobConfigurationLocation;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    private static final long serialVersionUID = 1L;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Long getJobExecutionId() {
        return jobExecutionId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public BatchJobExecution withJobExecutionId(Long jobExecutionId) {
        this.setJobExecutionId(jobExecutionId);
        return this;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setJobExecutionId(Long jobExecutionId) {
        this.jobExecutionId = jobExecutionId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Long getVersion() {
        return version;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public BatchJobExecution withVersion(Long version) {
        this.setVersion(version);
        return this;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setVersion(Long version) {
        this.version = version;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Long getJobInstanceId() {
        return jobInstanceId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public BatchJobExecution withJobInstanceId(Long jobInstanceId) {
        this.setJobInstanceId(jobInstanceId);
        return this;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setJobInstanceId(Long jobInstanceId) {
        this.jobInstanceId = jobInstanceId;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Date getCreateTime() {
        return createTime;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public BatchJobExecution withCreateTime(Date createTime) {
        this.setCreateTime(createTime);
        return this;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Date getStartTime() {
        return startTime;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public BatchJobExecution withStartTime(Date startTime) {
        this.setStartTime(startTime);
        return this;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Date getEndTime() {
        return endTime;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public BatchJobExecution withEndTime(Date endTime) {
        this.setEndTime(endTime);
        return this;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getStatus() {
        return status;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public BatchJobExecution withStatus(String status) {
        this.setStatus(status);
        return this;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getExitCode() {
        return exitCode;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public BatchJobExecution withExitCode(String exitCode) {
        this.setExitCode(exitCode);
        return this;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setExitCode(String exitCode) {
        this.exitCode = exitCode == null ? null : exitCode.trim();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getExitMessage() {
        return exitMessage;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public BatchJobExecution withExitMessage(String exitMessage) {
        this.setExitMessage(exitMessage);
        return this;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setExitMessage(String exitMessage) {
        this.exitMessage = exitMessage == null ? null : exitMessage.trim();
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public Date getLastUpdated() {
        return lastUpdated;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public BatchJobExecution withLastUpdated(Date lastUpdated) {
        this.setLastUpdated(lastUpdated);
        return this;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String getJobConfigurationLocation() {
        return jobConfigurationLocation;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public BatchJobExecution withJobConfigurationLocation(String jobConfigurationLocation) {
        this.setJobConfigurationLocation(jobConfigurationLocation);
        return this;
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public void setJobConfigurationLocation(String jobConfigurationLocation) {
        this.jobConfigurationLocation = jobConfigurationLocation == null ? null : jobConfigurationLocation.trim();
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
        BatchJobExecution other = (BatchJobExecution) that;
        return (this.getJobExecutionId() == null ? other.getJobExecutionId() == null : this.getJobExecutionId().equals(other.getJobExecutionId()))
            && (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
            && (this.getJobInstanceId() == null ? other.getJobInstanceId() == null : this.getJobInstanceId().equals(other.getJobInstanceId()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getStartTime() == null ? other.getStartTime() == null : this.getStartTime().equals(other.getStartTime()))
            && (this.getEndTime() == null ? other.getEndTime() == null : this.getEndTime().equals(other.getEndTime()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getExitCode() == null ? other.getExitCode() == null : this.getExitCode().equals(other.getExitCode()))
            && (this.getExitMessage() == null ? other.getExitMessage() == null : this.getExitMessage().equals(other.getExitMessage()))
            && (this.getLastUpdated() == null ? other.getLastUpdated() == null : this.getLastUpdated().equals(other.getLastUpdated()))
            && (this.getJobConfigurationLocation() == null ? other.getJobConfigurationLocation() == null : this.getJobConfigurationLocation().equals(other.getJobConfigurationLocation()));
    }

    @Override
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getJobExecutionId() == null) ? 0 : getJobExecutionId().hashCode());
        result = prime * result + ((getVersion() == null) ? 0 : getVersion().hashCode());
        result = prime * result + ((getJobInstanceId() == null) ? 0 : getJobInstanceId().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getStartTime() == null) ? 0 : getStartTime().hashCode());
        result = prime * result + ((getEndTime() == null) ? 0 : getEndTime().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getExitCode() == null) ? 0 : getExitCode().hashCode());
        result = prime * result + ((getExitMessage() == null) ? 0 : getExitMessage().hashCode());
        result = prime * result + ((getLastUpdated() == null) ? 0 : getLastUpdated().hashCode());
        result = prime * result + ((getJobConfigurationLocation() == null) ? 0 : getJobConfigurationLocation().hashCode());
        return result;
    }

    @Override
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", jobExecutionId=").append(jobExecutionId);
        sb.append(", version=").append(version);
        sb.append(", jobInstanceId=").append(jobInstanceId);
        sb.append(", createTime=").append(createTime);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", status=").append(status);
        sb.append(", exitCode=").append(exitCode);
        sb.append(", exitMessage=").append(exitMessage);
        sb.append(", lastUpdated=").append(lastUpdated);
        sb.append(", jobConfigurationLocation=").append(jobConfigurationLocation);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}