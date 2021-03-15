package io.github.xuyao5.datakitsserver.abstr;

import io.github.xuyao5.datakitsserver.DataKitsApplication;
import io.github.xuyao5.datakitsserver.configuration.EsClientConfig;
import io.github.xuyao5.dkl.eskits.helper.SnowflakeHelper;
import io.github.xuyao5.dkl.eskits.util.MyIpAddressUtils;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest(classes = {DataKitsApplication.class})
public abstract class AbstractTest {

    protected final SnowflakeHelper snowflake = new SnowflakeHelper(MyIpAddressUtils.getIpAddressSum() % 32, MyIpAddressUtils.getIpAddressSum() % 32);

    @Resource(name = "myEsClient")
    protected RestHighLevelClient esClient;

    @Autowired
    protected EsClientConfig esClientConfig;
}
