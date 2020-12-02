package io.github.xuyao5.dal.generator.repository.paces.dao;

import io.github.xuyao5.dal.generator.repository.BaseMapper;
import io.github.xuyao5.dal.generator.repository.paces.model.BatchJobInstance;
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

import static io.github.xuyao5.dal.generator.repository.paces.dao.BatchJobInstanceDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

@Mapper
public interface BatchJobInstanceMapper extends BaseMapper {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    BasicColumn[] selectList = BasicColumn.columnList(jobInstanceId, version, jobName, jobKey);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateAllColumns(BatchJobInstance record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(jobInstanceId).equalTo(record::getJobInstanceId)
                .set(version).equalTo(record::getVersion)
                .set(jobName).equalTo(record::getJobName)
                .set(jobKey).equalTo(record::getJobKey);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    static UpdateDSL<UpdateModel> updateSelectiveColumns(BatchJobInstance record, UpdateDSL<UpdateModel> dsl) {
        return dsl.set(jobInstanceId).equalToWhenPresent(record::getJobInstanceId)
                .set(version).equalToWhenPresent(record::getVersion)
                .set(jobName).equalToWhenPresent(record::getJobName)
                .set(jobKey).equalToWhenPresent(record::getJobKey);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type = SqlProviderAdapter.class, method = "select")
    long count(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @DeleteProvider(type = SqlProviderAdapter.class, method = "delete")
    int delete(DeleteStatementProvider deleteStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @InsertProvider(type = SqlProviderAdapter.class, method = "insert")
    int insert(InsertStatementProvider<BatchJobInstance> insertStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @InsertProvider(type = SqlProviderAdapter.class, method = "insertMultiple")
    int insertMultiple(MultiRowInsertStatementProvider<BatchJobInstance> multipleInsertStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type = SqlProviderAdapter.class, method = "select")
    @ResultMap("BatchJobInstanceResult")
    Optional<BatchJobInstance> selectOne(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type = SqlProviderAdapter.class, method = "select")
    @Results(id = "BatchJobInstanceResult", value = {
            @Result(column = "JOB_INSTANCE_ID", property = "jobInstanceId", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "VERSION", property = "version", jdbcType = JdbcType.BIGINT),
            @Result(column = "JOB_NAME", property = "jobName", jdbcType = JdbcType.VARCHAR),
            @Result(column = "JOB_KEY", property = "jobKey", jdbcType = JdbcType.VARCHAR)
    })
    List<BatchJobInstance> selectMany(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @UpdateProvider(type = SqlProviderAdapter.class, method = "update")
    int update(UpdateStatementProvider updateStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, batchJobInstance, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int delete(DeleteDSLCompleter completer) {
        return MyBatis3Utils.deleteFrom(this::delete, batchJobInstance, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int deleteByPrimaryKey(Long jobInstanceId_) {
        return delete(c ->
                c.where(jobInstanceId, isEqualTo(jobInstanceId_))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insert(BatchJobInstance record) {
        return MyBatis3Utils.insert(this::insert, record, batchJobInstance, c ->
                c.map(jobInstanceId).toProperty("jobInstanceId")
                        .map(version).toProperty("version")
                        .map(jobName).toProperty("jobName")
                        .map(jobKey).toProperty("jobKey")
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insertMultiple(Collection<BatchJobInstance> records) {
        return MyBatis3Utils.insertMultiple(this::insertMultiple, records, batchJobInstance, c ->
                c.map(jobInstanceId).toProperty("jobInstanceId")
                        .map(version).toProperty("version")
                        .map(jobName).toProperty("jobName")
                        .map(jobKey).toProperty("jobKey")
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int insertSelective(BatchJobInstance record) {
        return MyBatis3Utils.insert(this::insert, record, batchJobInstance, c ->
                c.map(jobInstanceId).toPropertyWhenPresent("jobInstanceId", record::getJobInstanceId)
                        .map(version).toPropertyWhenPresent("version", record::getVersion)
                        .map(jobName).toPropertyWhenPresent("jobName", record::getJobName)
                        .map(jobKey).toPropertyWhenPresent("jobKey", record::getJobKey)
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default Optional<BatchJobInstance> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, batchJobInstance, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<BatchJobInstance> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, batchJobInstance, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<BatchJobInstance> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, batchJobInstance, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default Optional<BatchJobInstance> selectByPrimaryKey(Long jobInstanceId_) {
        return selectOne(c ->
                c.where(jobInstanceId, isEqualTo(jobInstanceId_))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int update(UpdateDSLCompleter completer) {
        return MyBatis3Utils.update(this::update, batchJobInstance, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKey(BatchJobInstance record) {
        return update(c ->
                c.set(version).equalTo(record::getVersion)
                        .set(jobName).equalTo(record::getJobName)
                        .set(jobKey).equalTo(record::getJobKey)
                        .where(jobInstanceId, isEqualTo(record::getJobInstanceId))
        );
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default int updateByPrimaryKeySelective(BatchJobInstance record) {
        return update(c ->
                c.set(version).equalToWhenPresent(record::getVersion)
                        .set(jobName).equalToWhenPresent(record::getJobName)
                        .set(jobKey).equalToWhenPresent(record::getJobKey)
                        .where(jobInstanceId, isEqualTo(record::getJobInstanceId))
        );
    }
}