package io.github.xuyao5.dal.generator.repository.paces.dao;

import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

import javax.annotation.Generated;
import java.sql.JDBCType;

public final class BatchJobInstanceDynamicSqlSupport {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final BatchJobInstance batchJobInstance = new BatchJobInstance();

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Long> jobInstanceId = batchJobInstance.jobInstanceId;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Long> version = batchJobInstance.version;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> jobName = batchJobInstance.jobName;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> jobKey = batchJobInstance.jobKey;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final class BatchJobInstance extends SqlTable {
        public final SqlColumn<Long> jobInstanceId = column("JOB_INSTANCE_ID", JDBCType.BIGINT);

        public final SqlColumn<Long> version = column("VERSION", JDBCType.BIGINT);

        public final SqlColumn<String> jobName = column("JOB_NAME", JDBCType.VARCHAR);

        public final SqlColumn<String> jobKey = column("JOB_KEY", JDBCType.VARCHAR);

        public BatchJobInstance() {
            super("BATCH_JOB_INSTANCE");
        }
    }
}