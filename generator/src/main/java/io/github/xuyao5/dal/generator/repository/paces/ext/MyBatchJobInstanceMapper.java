package io.github.xuyao5.dal.generator.repository.paces.ext;

import io.github.xuyao5.dal.generator.repository.paces.dao.BatchJobInstanceMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;
import org.mybatis.dynamic.sql.util.mybatis3.MyBatis3Utils;

import javax.annotation.Generated;

import static io.github.xuyao5.dal.generator.repository.paces.dao.BatchJobInstanceDynamicSqlSupport.batchJobInstance;
import static io.github.xuyao5.dal.generator.repository.paces.dao.BatchJobInstanceDynamicSqlSupport.jobInstanceId;

@Mapper
public interface MyBatchJobInstanceMapper extends BatchJobInstanceMapper {

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    BasicColumn[] sumColumn = BasicColumn.columnList(SqlBuilder.sum(jobInstanceId));

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    BasicColumn[] avgColumn = BasicColumn.columnList(SqlBuilder.avg(jobInstanceId));

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    BasicColumn[] maxColumn = BasicColumn.columnList(SqlBuilder.max(jobInstanceId));

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    BasicColumn[] minColumn = BasicColumn.columnList(SqlBuilder.min(jobInstanceId));

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type = SqlProviderAdapter.class, method = "select")
    double sum(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type = SqlProviderAdapter.class, method = "select")
    double avg(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type = SqlProviderAdapter.class, method = "select")
    double max(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type = SqlProviderAdapter.class, method = "select")
    double min(SelectStatementProvider selectStatement);

    default double sum(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::sum, sumColumn, batchJobInstance, completer);
    }

    default double avg(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::avg, sumColumn, batchJobInstance, completer);
    }

    default double max(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::max, sumColumn, batchJobInstance, completer);
    }

    default double min(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::min, sumColumn, batchJobInstance, completer);
    }
}
