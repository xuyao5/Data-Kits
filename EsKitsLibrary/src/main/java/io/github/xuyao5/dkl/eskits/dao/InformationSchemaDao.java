package io.github.xuyao5.dkl.eskits.dao;

import io.github.xuyao5.dkl.eskits.schema.mysql.Columns;
import io.github.xuyao5.dkl.eskits.schema.mysql.Tables;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.RowSetDynaClass;
import org.apache.commons.lang3.StringUtils;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Thomas.XU(xuyao)
 * @version 25/09/21 09:59
 */
@Slf4j
public final class InformationSchemaDao {

    private static final char APOSTROPHE = 39;//'
    private static final char COMMA = 44;//,

    private final String url;
    private final String username;
    private final String password;

    public InformationSchemaDao(@NonNull String hostname, int port, @NonNull String username, @NonNull String password) {
        this.url = String.format("jdbc:mysql://%s:%d/information_schema?useUnicode=true&characterEncoding=utf8&useSSL=false&connectTimeout=3000&socketTimeout=60000&autoReconnect=true&nullCatalogMeansCurrent=true&allowMultiQueries=true&rewriteBatchedStatements=true&serverTimezone=Asia/Shanghai", hostname, port, ZoneId.systemDefault().getId());
        this.username = username;
        this.password = password;
    }

    @SneakyThrows
    public List<Columns> queryColumns(@NonNull String... table) {
        String COLUMNS_QUERY = "SELECT TABLE_SCHEMA,TABLE_NAME,COLUMN_NAME,ORDINAL_POSITION,COLUMN_KEY,EXTRA FROM COLUMNS WHERE TABLE_NAME in (%s)";
        String sql = String.format(COLUMNS_QUERY, StringUtils.join(Arrays.stream(table).map(s -> StringUtils.wrap(s, APOSTROPHE)).toArray(String[]::new), COMMA));
        try (PreparedStatement preparedStatement = DriverManager.getConnection(url, username, password).prepareStatement(sql)) {
            return new RowSetDynaClass(preparedStatement.executeQuery()).getRows().stream().map(dynaBean -> {
                Columns columns = new Columns();
                columns.setTableSchema((String) dynaBean.get("table_schema"));
                columns.setTableName((String) dynaBean.get("table_name"));
                columns.setColumnName((String) dynaBean.get("column_name"));
                columns.setOrdinalPosition((Integer) dynaBean.get("ordinal_position"));
                columns.setColumnKey((String) dynaBean.get("column_key"));
                columns.setExtra((String) dynaBean.get("extra"));
                return columns;
            }).collect(Collectors.toCollection(LinkedList::new));
        }
    }

    @SneakyThrows
    public List<Tables> queryTables(@NonNull String... table) {
        String COLUMNS_QUERY = "SELECT TABLE_SCHEMA,TABLE_NAME FROM TABLES WHERE TABLE_NAME in (%s)";
        String sql = String.format(COLUMNS_QUERY, StringUtils.join(Arrays.stream(table).map(s -> StringUtils.wrap(s, APOSTROPHE)).toArray(String[]::new), COMMA));
        try (PreparedStatement preparedStatement = DriverManager.getConnection(url, username, password).prepareStatement(sql)) {
            return new RowSetDynaClass(preparedStatement.executeQuery()).getRows().stream().map(dynaBean -> {
                Tables tables = new Tables();
                tables.setTableSchema((String) dynaBean.get("table_schema"));
                tables.setTableName((String) dynaBean.get("table_name"));
                return tables;
            }).collect(Collectors.toCollection(LinkedList::new));
        }
    }
}