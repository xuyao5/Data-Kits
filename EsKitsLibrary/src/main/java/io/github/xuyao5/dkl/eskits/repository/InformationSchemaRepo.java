package io.github.xuyao5.dkl.eskits.repository;

import io.github.xuyao5.dkl.eskits.dao.information_schema.Columns;
import io.github.xuyao5.dkl.eskits.dao.information_schema.ColumnsMapper;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.util.List;
import java.util.Properties;

import static io.github.xuyao5.dkl.eskits.dao.information_schema.ColumnsDynamicSqlSupport.tableName;
import static io.github.xuyao5.dkl.eskits.dao.information_schema.ColumnsDynamicSqlSupport.tableSchema;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;
import static org.mybatis.dynamic.sql.SqlBuilder.isIn;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 4/08/21 22:48
 * @apiNote InformationSchemaRepo
 * @implNote InformationSchemaRepo
 */
@Slf4j
public final class InformationSchemaRepo {

    private final SqlSessionFactory sqlSessionFactory;

    @SneakyThrows
    public InformationSchemaRepo(@NonNull String driver, @NonNull String hostname, int port, @NonNull String username, @NonNull String password) {
        Properties properties = new Properties();
        switch (driver) {
            case "com.mysql.cj.jdbc.Driver":
                properties.setProperty("mybatis.mysql.url", StringUtils.join("jdbc:mysql://", hostname, ":", port, "/information_schema?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=UTC"));
                break;
            case "com.mysql.jdbc.Driver":
                properties.setProperty("mybatis.mysql.url", StringUtils.join("jdbc:mysql://", hostname, ":", port, "/information_schema?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC"));
                break;
        }
        properties.setProperty("mybatis.mysql.driver", driver);
        properties.setProperty("mybatis.mysql.username", username);
        properties.setProperty("mybatis.mysql.password", password);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"), properties);
    }

    public List<Columns> queryColumns(@NonNull String schema, @NonNull String... tables) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            return session.getMapper(ColumnsMapper.class).select(dsl -> dsl.where(tableSchema, isEqualTo(schema)).and(tableName, isIn(tables)));
        }
    }
}