package io.github.xuyao5.dal.generator.repository.paces.dao;

import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

import javax.annotation.Generated;
import java.sql.JDBCType;
import java.util.Date;

public final class BatchJobExecutionDynamicSqlSupport {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final BatchJobExecution batchJobExecution = new BatchJobExecution();

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Long> jobExecutionId = batchJobExecution.jobExecutionId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Long> version = batchJobExecution.version;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Long> jobInstanceId = batchJobExecution.jobInstanceId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Date> createTime = batchJobExecution.createTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Date> startTime = batchJobExecution.startTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Date> endTime = batchJobExecution.endTime;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> status = batchJobExecution.status;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> exitCode = batchJobExecution.exitCode;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> exitMessage = batchJobExecution.exitMessage;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Date> lastUpdated = batchJobExecution.lastUpdated;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> jobConfigurationLocation = batchJobExecution.jobConfigurationLocation;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final class BatchJobExecution extends SqlTable {
        public final SqlColumn<Long> jobExecutionId = column("JOB_EXECUTION_ID", JDBCType.BIGINT);

        public final SqlColumn<Long> version = column("VERSION", JDBCType.BIGINT);

        public final SqlColumn<Long> jobInstanceId = column("JOB_INSTANCE_ID", JDBCType.BIGINT);

        public final SqlColumn<Date> createTime = column("CREATE_TIME", JDBCType.TIMESTAMP);

        public final SqlColumn<Date> startTime = column("START_TIME", JDBCType.TIMESTAMP);

        public final SqlColumn<Date> endTime = column("END_TIME", JDBCType.TIMESTAMP);

        public final SqlColumn<String> status = column("\"STATUS\"", JDBCType.VARCHAR);

        public final SqlColumn<String> exitCode = column("EXIT_CODE", JDBCType.VARCHAR);

        public final SqlColumn<String> exitMessage = column("EXIT_MESSAGE", JDBCType.VARCHAR);

        public final SqlColumn<Date> lastUpdated = column("LAST_UPDATED", JDBCType.TIMESTAMP);

        public final SqlColumn<String> jobConfigurationLocation = column("JOB_CONFIGURATION_LOCATION", JDBCType.VARCHAR);

        public BatchJobExecution() {
            super("BATCH_JOB_EXECUTION");
        }
    }
}