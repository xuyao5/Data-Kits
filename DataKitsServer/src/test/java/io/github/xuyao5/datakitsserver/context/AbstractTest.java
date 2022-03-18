package io.github.xuyao5.datakitsserver.context;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import io.github.xuyao5.datakitsserver.DataKitsApplication;
import io.github.xuyao5.datakitsserver.configuration.EsKitsConfig;
import io.github.xuyao5.dkl.eskits.helper.SnowflakeHelper;
import io.github.xuyao5.dkl.eskits.util.IpAddressUtilsPlus;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {DataKitsApplication.class})
public abstract class AbstractTest {

    protected final SnowflakeHelper snowflake = new SnowflakeHelper(IpAddressUtilsPlus.getIpAddressSum() % 32, IpAddressUtilsPlus.getIpAddressSum() % 32);

    @Autowired
    protected RestHighLevelClient esClient;

    @Autowired
    protected ElasticsearchClient elasticsearchClient;

    @Autowired
    protected EsKitsConfig esKitsConfig;
}
