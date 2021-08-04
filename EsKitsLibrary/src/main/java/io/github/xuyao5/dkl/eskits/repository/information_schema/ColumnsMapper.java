package io.github.xuyao5.dkl.eskits.repository.information_schema;

import org.apache.ibatis.annotations.Arg;
import org.apache.ibatis.annotations.ConstructorArgs;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.dynamic.sql.BasicColumn;
import org.mybatis.dynamic.sql.select.CountDSLCompleter;
import org.mybatis.dynamic.sql.select.SelectDSLCompleter;
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;
import org.mybatis.dynamic.sql.util.mybatis3.MyBatis3Utils;

import javax.annotation.Generated;
import java.util.List;
import java.util.Optional;

import static io.github.xuyao5.dkl.eskits.repository.information_schema.ColumnsDynamicSqlSupport.*;

@Mapper
public interface ColumnsMapper {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    BasicColumn[] selectList = BasicColumn.columnList(tableCatalog, tableSchema, tableName, columnName, ordinalPosition, isNullable, dataType, characterMaximumLength, characterOctetLength, numericPrecision, numericScale, datetimePrecision, characterSetName, collationName, columnKey, extra, privileges, columnComment, columnDefault, columnType, generationExpression);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type = SqlProviderAdapter.class, method = "select")
    long count(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type = SqlProviderAdapter.class, method = "select")
    @ConstructorArgs({
            @Arg(column = "TABLE_CATALOG", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Arg(column = "TABLE_SCHEMA", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Arg(column = "TABLE_NAME", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Arg(column = "COLUMN_NAME", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Arg(column = "ORDINAL_POSITION", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Arg(column = "IS_NULLABLE", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Arg(column = "DATA_TYPE", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Arg(column = "CHARACTER_MAXIMUM_LENGTH", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Arg(column = "CHARACTER_OCTET_LENGTH", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Arg(column = "NUMERIC_PRECISION", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Arg(column = "NUMERIC_SCALE", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Arg(column = "DATETIME_PRECISION", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Arg(column = "CHARACTER_SET_NAME", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Arg(column = "COLLATION_NAME", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Arg(column = "COLUMN_KEY", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Arg(column = "EXTRA", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Arg(column = "PRIVILEGES", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Arg(column = "COLUMN_COMMENT", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Arg(column = "COLUMN_DEFAULT", javaType = String.class, jdbcType = JdbcType.LONGVARCHAR),
            @Arg(column = "COLUMN_TYPE", javaType = String.class, jdbcType = JdbcType.LONGVARCHAR),
            @Arg(column = "GENERATION_EXPRESSION", javaType = String.class, jdbcType = JdbcType.LONGVARCHAR)
    })
    Optional<Columns> selectOne(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    @SelectProvider(type = SqlProviderAdapter.class, method = "select")
    @ConstructorArgs({
            @Arg(column = "TABLE_CATALOG", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Arg(column = "TABLE_SCHEMA", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Arg(column = "TABLE_NAME", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Arg(column = "COLUMN_NAME", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Arg(column = "ORDINAL_POSITION", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Arg(column = "IS_NULLABLE", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Arg(column = "DATA_TYPE", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Arg(column = "CHARACTER_MAXIMUM_LENGTH", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Arg(column = "CHARACTER_OCTET_LENGTH", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Arg(column = "NUMERIC_PRECISION", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Arg(column = "NUMERIC_SCALE", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Arg(column = "DATETIME_PRECISION", javaType = Long.class, jdbcType = JdbcType.BIGINT),
            @Arg(column = "CHARACTER_SET_NAME", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Arg(column = "COLLATION_NAME", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Arg(column = "COLUMN_KEY", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Arg(column = "EXTRA", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Arg(column = "PRIVILEGES", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Arg(column = "COLUMN_COMMENT", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Arg(column = "COLUMN_DEFAULT", javaType = String.class, jdbcType = JdbcType.LONGVARCHAR),
            @Arg(column = "COLUMN_TYPE", javaType = String.class, jdbcType = JdbcType.LONGVARCHAR),
            @Arg(column = "GENERATION_EXPRESSION", javaType = String.class, jdbcType = JdbcType.LONGVARCHAR)
    })
    List<Columns> selectMany(SelectStatementProvider selectStatement);

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default long count(CountDSLCompleter completer) {
        return MyBatis3Utils.countFrom(this::count, columns, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default Optional<Columns> selectOne(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectOne(this::selectOne, selectList, columns, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<Columns> select(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectList(this::selectMany, selectList, columns, completer);
    }

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    default List<Columns> selectDistinct(SelectDSLCompleter completer) {
        return MyBatis3Utils.selectDistinct(this::selectMany, selectList, columns, completer);
    }
}