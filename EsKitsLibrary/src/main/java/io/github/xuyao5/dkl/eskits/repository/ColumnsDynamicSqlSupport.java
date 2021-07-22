package io.github.xuyao5.dkl.eskits.repository;

import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;

import javax.annotation.Generated;
import java.sql.JDBCType;

public final class ColumnsDynamicSqlSupport {
    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final Columns columns = new Columns();

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> tableCatalog = columns.tableCatalog;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> tableSchema = columns.tableSchema;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> tableName = columns.tableName;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> columnName = columns.columnName;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Long> ordinalPosition = columns.ordinalPosition;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> isNullable = columns.isNullable;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> dataType = columns.dataType;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Long> characterMaximumLength = columns.characterMaximumLength;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Long> characterOctetLength = columns.characterOctetLength;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Long> numericPrecision = columns.numericPrecision;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Long> numericScale = columns.numericScale;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<Long> datetimePrecision = columns.datetimePrecision;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> characterSetName = columns.characterSetName;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> collationName = columns.collationName;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> columnKey = columns.columnKey;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> extra = columns.extra;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> privileges = columns.privileges;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> columnComment = columns.columnComment;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> columnDefault = columns.columnDefault;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> columnType = columns.columnType;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final SqlColumn<String> generationExpression = columns.generationExpression;

    @Generated("org.mybatis.generator.api.MyBatisGenerator")
    public static final class Columns extends SqlTable {
        public final SqlColumn<String> tableCatalog = column("TABLE_CATALOG", JDBCType.VARCHAR);

        public final SqlColumn<String> tableSchema = column("TABLE_SCHEMA", JDBCType.VARCHAR);

        public final SqlColumn<String> tableName = column("\"TABLE_NAME\"", JDBCType.VARCHAR);

        public final SqlColumn<String> columnName = column("\"COLUMN_NAME\"", JDBCType.VARCHAR);

        public final SqlColumn<Long> ordinalPosition = column("ORDINAL_POSITION", JDBCType.BIGINT);

        public final SqlColumn<String> isNullable = column("IS_NULLABLE", JDBCType.VARCHAR);

        public final SqlColumn<String> dataType = column("DATA_TYPE", JDBCType.VARCHAR);

        public final SqlColumn<Long> characterMaximumLength = column("CHARACTER_MAXIMUM_LENGTH", JDBCType.BIGINT);

        public final SqlColumn<Long> characterOctetLength = column("CHARACTER_OCTET_LENGTH", JDBCType.BIGINT);

        public final SqlColumn<Long> numericPrecision = column("NUMERIC_PRECISION", JDBCType.BIGINT);

        public final SqlColumn<Long> numericScale = column("NUMERIC_SCALE", JDBCType.BIGINT);

        public final SqlColumn<Long> datetimePrecision = column("DATETIME_PRECISION", JDBCType.BIGINT);

        public final SqlColumn<String> characterSetName = column("\"CHARACTER_SET_NAME\"", JDBCType.VARCHAR);

        public final SqlColumn<String> collationName = column("\"COLLATION_NAME\"", JDBCType.VARCHAR);

        public final SqlColumn<String> columnKey = column("COLUMN_KEY", JDBCType.VARCHAR);

        public final SqlColumn<String> extra = column("EXTRA", JDBCType.VARCHAR);

        public final SqlColumn<String> privileges = column("\"PRIVILEGES\"", JDBCType.VARCHAR);

        public final SqlColumn<String> columnComment = column("COLUMN_COMMENT", JDBCType.VARCHAR);

        public final SqlColumn<String> columnDefault = column("COLUMN_DEFAULT", JDBCType.LONGVARCHAR);

        public final SqlColumn<String> columnType = column("COLUMN_TYPE", JDBCType.LONGVARCHAR);

        public final SqlColumn<String> generationExpression = column("GENERATION_EXPRESSION", JDBCType.LONGVARCHAR);

        public Columns() {
            super("COLUMNS");
        }
    }
}