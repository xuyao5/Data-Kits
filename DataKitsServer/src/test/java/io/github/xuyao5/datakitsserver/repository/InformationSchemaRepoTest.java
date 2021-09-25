package io.github.xuyao5.datakitsserver.repository;

import io.github.xuyao5.datakitsserver.context.AbstractTest;
import io.github.xuyao5.dkl.eskits.repository.InformationSchemaRepo;
import io.github.xuyao5.dkl.eskits.repository.dao.InformationSchemaDao;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class InformationSchemaRepoTest extends AbstractTest {

    private final InformationSchemaRepo informationSchemaRepo = new InformationSchemaRepo();

    @Test
    void test() {
        String url = "jdbc:mysql://%s:%d/information_schema?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=UTC";
        InformationSchemaDao informationSchemaDao = new InformationSchemaDao(String.format(url, esKitsConfig.getMysqlBinlogHostname(), esKitsConfig.getMysqlBinlogPort()), esKitsConfig.getMysqlBinlogUsername(), esKitsConfig.getMysqlBinlogPassword());
        informationSchemaDao.initial();
    }
}