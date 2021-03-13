package io.github.xuyao5.datakitsserver.abstr;

import io.github.xuyao5.datakitsserver.DataKitsApplication;
import io.github.xuyao5.dkl.eskits.client.EsClient;
import io.github.xuyao5.dkl.eskits.helper.SnowflakeHelper;
import io.github.xuyao5.dkl.eskits.util.MyIpAddressUtils;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest(classes = {DataKitsApplication.class})
public abstract class AbstractTest {

    @Resource(name = "myEsClient")
    protected EsClient esClient;

    protected final SnowflakeHelper snowflake = new SnowflakeHelper(MyIpAddressUtils.getIpAddressSum() % 32, MyIpAddressUtils.getIpAddressSum() % 32);

}
