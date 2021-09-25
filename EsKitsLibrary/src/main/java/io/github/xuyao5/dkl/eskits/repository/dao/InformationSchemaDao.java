package io.github.xuyao5.dkl.eskits.repository.dao;

import io.github.xuyao5.dkl.eskits.schema.mysql.Columns;
import lombok.SneakyThrows;
import org.apache.commons.beanutils.RowSetDynaClass;

import java.math.BigInteger;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 25/09/21 09:59
 * @apiNote InformationSchemaDao
 * @implNote InformationSchemaDao
 */
public class InformationSchemaDao {

    private static final String COLUMNS_QUERY = "SELECT TABLE_SCHEMA,TABLE_NAME,COLUMN_NAME,ORDINAL_POSITION,COLUMN_KEY,EXTRA FROM COLUMNS WHERE `TABLE_NAME`='%s'";

    private final String url;
    private final String user;
    private final String password;

    public InformationSchemaDao(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @SneakyThrows
    public void initial() {
        try (ResultSet resultSet = DriverManager.getConnection(url, user, password).prepareStatement(String.format(COLUMNS_QUERY, "MyTable")).executeQuery()) {
            List<Columns> collect = new RowSetDynaClass(resultSet).getRows().stream().map(dynaBean -> {
                Columns columns = Columns.of();
                columns.setTableSchema((String) dynaBean.get("table_schema"));
                columns.setTableName((String) dynaBean.get("table_name"));
                columns.setColumnName((String) dynaBean.get("column_name"));
                columns.setOrdinalPosition(((BigInteger) dynaBean.get("ordinal_position")).longValue());
                columns.setColumnKey((String) dynaBean.get("column_key"));
                columns.setExtra((String) dynaBean.get("extra"));
                return columns;
            }).collect(Collectors.toList());
            collect.forEach(System.out::println);
        }
    }
}
