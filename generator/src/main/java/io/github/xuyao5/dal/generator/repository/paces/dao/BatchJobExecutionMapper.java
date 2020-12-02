package io.github.xuyao5.dal.generator.repository.paces.dao;

import io.github.xuyao5.dal.generator.repository.BaseMapper;
import io.github.xuyao5.dal.generator.repository.paces.model.BatchJobExecution;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.delete.DeleteDSLCompleter;
import org.mybatis.dynamic.sql.delete.render.DeleteStatementProvider;
import org.mybatis.dynamic.sql.insert.render.InsertStatementProvider;
import org.mybatis.dynamic.sql.insert.render.MultiRowInsertStatementProvider;
import org.mybatis.dynamic.sql.select.CountDSLCompleter;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.update.UpdateDSL;
import org.mybatis.dynamic.sql.update.UpdateDSLCompleter;
import org.mybatis.dynamic.sql.update.UpdateModel;
import org.mybatis.dynamic.sql.update.render.UpdateStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;
import org.mybatis.dynamic.sql.util.mybatis3.MyBatis3Utils;

import javax.annotation.Generated;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static io.github.xuyao5.dal.generator.repository.paces.dao.BatchJobExecutionDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

@Mapper
public interface BatchJobExecutionMapper extends BaseMapper {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    BasicColumn[] selectList = BasicColumn.columnList(jobExecutionId, version, jobInstanceId, createTime, startTime, endTime, status, exitCode, exitMessage, lastUpdated, jobConfigurationLocation);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateAllColumns(BatchJobExecution record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(jobExecutionId).equalTo(record::getJobExecutionId)
                .set(version).equalTo(record::getVersion)
                .set(jobInstanceId).equalTo(record::getJobInstanceId)
                .set(createTime).equalTo(record::getCreateTime)
                .set(startTime).equalTo(record::getStartTime)
                .set(endTime).equalTo(record::getEndTime)
                .set(status).equalTo(record::getStatus)
                .set(exitCode).equalTo(record::getExitCode)
                .set(exitMessage).equalTo(record::getExitMessage)
                .set(lastUpdated).equalTo(record::getLastUpdated)
                .set(jobConfigurationLocation).equalTo(record::getJobConfigurationLocation);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(BatchJobExecution record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(jobExecutionId).equalToWhenPresent(record::getJobExecutionId)
                .set(version).equalToWhenPresent(record::getVersion)
                .set(jobInstanceId).equalToWhenPresent(record::getJobInstanceId)
                .set(createTime).equalToWhenPresent(record::getCreateTime)
                .set(startTime).equalToWhenPresent(record::getStartTime)
                .set(endTime).equalToWhenPresent(record::getEndTime)
                .set(status).equalToWhenPresent(record::getStatus)
                .set(exitCode).equalToWhenPresent(record::getExitCode)
                .set(exitMessage).equalToWhenPresent(record::getExitMessage)
                .set(lastUpdated).equalToWhenPresent(record::getLastUpdated)
                .set(jobConfigurationLocation).equalToWhenPresent(record::getJobConfigurationLocation);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type = SqlProviderAdapter.class, method = "select")
    long count(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @DeleteProvider(type = SqlProviderAdapter.class, method = "delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @InsertProvider(type = SqlProviderAdapter.class, method = "insert")
    int insert(InsertStatementProvider<BatchJobExecution> insertStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @InsertProvider(type = SqlProviderAdapter.class, method = "insertMultiple")
    int insertMultiple(MultiRowInsertStatementProvider<BatchJobExecution> multipleInsertStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type = SqlProviderAdapter.class, method = "select")
    @ResultMap("BatchJobExecutionResult")
    Optional<BatchJobExecution> selectOne(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type = SqlProviderAdapter.class, method = "select")
    @Results(id = "BatchJobExecutionResult", value = {
            @Result(column = "JOB_EXECUTION_ID", property = "jobExecutionId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "VERSION", property = "version", jdbcType = JdbcType.BIGINT),
            @Result(column = "JOB_INSTANCE_ID", property = "jobInstanceId", jdbcType = JdbcType.BIGINT),
            @Result(column = "CREATE_TIME", property = "createTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "START_TIME", property = "startTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "END_TIME", property = "endTime", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "STATUS", property = "status", jdbcType = JdbcType.VARCHAR),
            @Result(column = "EXIT_CODE", property = "exitCode", jdbcType = JdbcType.VARCHAR),
            @Result(column = "EXIT_MESSAGE", property = "exitMessage", jdbcType = JdbcType.VARCHAR),
            @Result(column = "LAST_UPDATED", property = "lastUpdated", jdbcType = JdbcType.TIMESTAMP),
            @Result(column = "JOB_CONFIGURATION_LOCATION", property = "jobConfigurationLocation", jdbcType = JdbcType.VARCHAR)
    })
    List<BatchJobExecution> selectMany(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @UpdateProvider(type = SqlProviderAdapter.class, method = "update")
    int update(UpdateStatementProvider updateStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, batchJobExecution, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, batchJobExecution, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int deleteByPrimaryKey(Long jobExecutionId_) {
        return delete(c ->
                c.where(jobExecutionId, isEqualTo(jobExecutionId_))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insert(BatchJobExecution record) {
        return MyBatis3Utils.insert(this::insert, record, batchJobExecution, c ->
                c.map(jobExecutionId).toProperty("jobExecutionId")
                        .map(version).toProperty("version")
                        .map(jobInstanceId).toProperty("jobInstanceId")
                        .map(createTime).toProperty("createTime")
                        .map(startTime).toProperty("startTime")
                        .map(endTime).toProperty("endTime")
                        .map(status).toProperty("status")
                        .map(exitCode).toProperty("exitCode")
                        .map(exitMessage).toProperty("exitMessage")
                        .map(lastUpdated).toProperty("lastUpdated")
                        .map(jobConfigurationLocation).toProperty("jobConfigurationLocation")
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insertMultiple(Collection<BatchJobExecution> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, batchJobExecution, c ->
                c.map(jobExecutionId).toProperty("jobExecutionId")
                        .map(version).toProperty("version")
                        .map(jobInstanceId).toProperty("jobInstanceId")
                        .map(createTime).toProperty("createTime")
                        .map(startTime).toProperty("startTime")
                        .map(endTime).toProperty("endTime")
                        .map(status).toProperty("status")
                        .map(exitCode).toProperty("exitCode")
                        .map(exitMessage).toProperty("exitMessage")
                        .map(lastUpdated).toProperty("lastUpdated")
                        .map(jobConfigurationLocation).toProperty("jobConfigurationLocation")
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insertSelective(BatchJobExecution record) {
        return MyBatis3Utils.insert(this::insert, record, batchJobExecution, c ->
                c.map(jobExecutionId).toPropertyWhenPresent("jobExecutionId", record::getJobExecutionId)
                        .map(version).toPropertyWhenPresent("version", record::getVersion)
                        .map(jobInstanceId).toPropertyWhenPresent("jobInstanceId", record::getJobInstanceId)
                        .map(createTime).toPropertyWhenPresent("createTime", record::getCreateTime)
                        .map(startTime).toPropertyWhenPresent("startTime", record::getStartTime)
                        .map(endTime).toPropertyWhenPresent("endTime", record::getEndTime)
                        .map(status).toPropertyWhenPresent("status", record::getStatus)
                        .map(exitCode).toPropertyWhenPresent("exitCode", record::getExitCode)
                        .map(exitMessage).toPropertyWhenPresent("exitMessage", record::getExitMessage)
                        .map(lastUpdated).toPropertyWhenPresent("lastUpdated", record::getLastUpdated)
                        .map(jobConfigurationLocation).toPropertyWhenPresent("jobConfigurationLocation", record::getJobConfigurationLocation)
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default Optional<BatchJobExecution> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, batchJobExecution, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<BatchJobExecution> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, batchJobExecution, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<BatchJobExecution> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, batchJobExecution, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default Optional<BatchJobExecution> selectByPrimaryKey(Long jobExecutionId_) {
        return selectOne(c ->
                c.where(jobExecutionId, isEqualTo(jobExecutionId_))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, batchJobExecution, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKey(BatchJobExecution record) {
        return update(c ->
                c.set(version).equalTo(record::getVersion)
                        .set(jobInstanceId).equalTo(record::getJobInstanceId)
                        .set(createTime).equalTo(record::getCreateTime)
                        .set(startTime).equalTo(record::getStartTime)
                        .set(endTime).equalTo(record::getEndTime)
                        .set(status).equalTo(record::getStatus)
                        .set(exitCode).equalTo(record::getExitCode)
                        .set(exitMessage).equalTo(record::getExitMessage)
                        .set(lastUpdated).equalTo(record::getLastUpdated)
                        .set(jobConfigurationLocation).equalTo(record::getJobConfigurationLocation)
                        .where(jobExecutionId, isEqualTo(record::getJobExecutionId))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKeySelective(BatchJobExecution record) {
        return update(c ->
                c.set(version).equalToWhenPresent(record::getVersion)
                        .set(jobInstanceId).equalToWhenPresent(record::getJobInstanceId)
                        .set(createTime).equalToWhenPresent(record::getCreateTime)
                        .set(startTime).equalToWhenPresent(record::getStartTime)
                        .set(endTime).equalToWhenPresent(record::getEndTime)
                        .set(status).equalToWhenPresent(record::getStatus)
                        .set(exitCode).equalToWhenPresent(record::getExitCode)
                        .set(exitMessage).equalToWhenPresent(record::getExitMessage)
                        .set(lastUpdated).equalToWhenPresent(record::getLastUpdated)
                        .set(jobConfigurationLocation).equalToWhenPresent(record::getJobConfigurationLocation)
                        .where(jobExecutionId, isEqualTo(record::getJobExecutionId))
        );
    }
}