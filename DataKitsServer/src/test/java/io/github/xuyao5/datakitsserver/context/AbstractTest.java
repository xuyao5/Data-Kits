package io.github.xuyao5.datakitsserver.context;

import io.github.xuyao5.datakitsserver.DataKitsApplication;
import io.github.xuyao5.datakitsserver.configuration.EsClientConfig;
import io.github.xuyao5.dkl.eskits.helper.SnowflakeHelper;
import io.github.xuyao5.dkl.eskits.util.IpAddressUtilsNZ;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {DataKitsApplication.class})
public abstract class AbstractTest {

    protected final SnowflakeHelper snowflake = new SnowflakeHelper(IpAddressUtilsNZ.getIpAddressSum() % 32, IpAddressUtilsNZ.getIpAddressSum() % 32);

    @Autowired
    protected RestHighLevelClient esClient;

    @Autowired
    protected EsClientConfig esClientConfig;
}
