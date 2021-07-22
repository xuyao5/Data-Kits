package io.github.xuyao5.datakitsserver.system;

import io.github.xuyao5.datakitsserver.context.AbstractTest;
import io.github.xuyao5.dkl.eskits.repository.Columns;
import io.github.xuyao5.dkl.eskits.repository.ColumnsMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.github.xuyao5.dkl.eskits.repository.ColumnsDynamicSqlSupport.tableSchema;
import static org.mybatis.dynamic.sql.SqlBuilder.isEqualTo;

@Slf4j
public class MyBatisTest extends AbstractTest {

    @SneakyThrows
    @Test
    void test() {
        final SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"));
        try (SqlSession session = sqlSessionFactory.openSession()) {
            ColumnsMapper mapper = session.getMapper(ColumnsMapper.class);
            List<Columns> information_schema = mapper.select(dsl -> {
                return dsl.where(tableSchema, isEqualTo("BinlogTest"));
//                        .and(tableName, isEqualTo("MyTable"));
            });
            information_schema.forEach(System.out::println);
        }
    }
}