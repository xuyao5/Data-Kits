package io.github.xuyao5.datakitsserver.context;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import io.github.xuyao5.datakitsserver.DataKitsServer;
import io.github.xuyao5.datakitsserver.configuration.elasticsearch.EsKitsConfig;
import io.github.xuyao5.dkl.eskits.helper.SnowflakeHelper;
import io.github.xuyao5.dkl.eskits.util.IpAddressUtilsPlus;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {DataKitsServer.class})
public abstract class AbstractTest {

    protected final SnowflakeHelper snowflake = new SnowflakeHelper(IpAddressUtilsPlus.getIpAddressSum() % 32, IpAddressUtilsPlus.getIpAddressSum() % 32);

    @Autowired
    protected RestHighLevelClient esClient;

    @Autowired
    protected ElasticsearchClient elasticsearchClient;

    @Autowired
    protected EsKitsConfig esKitsConfig;

    protected static final String PGT_TOKEN = "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";
}
