package io.github.xuyao5.datakitsserver.job;

import io.github.xuyao5.datakitsserver.configuration.EsClientConfig;
import io.github.xuyao5.dkl.eskits.service.MySQL2EsExecutor;
import io.github.xuyao5.dkl.eskits.service.config.MySQL2EsConfig;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("mySQL2EsDemoJob")
public class MySQL2EsDemoJob implements Runnable {

    @Autowired
    protected RestHighLevelClient esClient;

    @Autowired
    protected EsClientConfig esClientConfig;

    @Override
    public void run() {
        MySQL2EsExecutor mySQL2EsExecutor = new MySQL2EsExecutor(esClient, esClientConfig.getMysqlBinlogHost(), esClientConfig.getMysqlBinlogPort(), esClientConfig.getMysqlBinlogUsername(), esClientConfig.getMysqlBinlogPassword());
        mySQL2EsExecutor.listen(MySQL2EsConfig.of());
    }
}
