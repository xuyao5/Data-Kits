package io.github.xuyao5.datakitsserver.job;

import io.github.xuyao5.datakitsserver.configuration.EsKitsConfig;
import io.github.xuyao5.dkl.eskits.service.MySQL2EsService;
import io.github.xuyao5.dkl.eskits.service.config.MySQL2EsConfig;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component("mySQL2EsDemoJob")
public final class MySQL2EsDemoJob implements Runnable {

    @Autowired
    protected RestHighLevelClient esClient;

    @Autowired
    protected EsKitsConfig esKitsConfig;

    @SneakyThrows
    @Override
    public void run() {
        new MySQL2EsService(esClient, "BinlogTest", "root", "123456", esKitsConfig.getEsBulkThreads()).execute(MySQL2EsConfig.of(new String[]{"MyTable"}));
        System.in.read();
    }
}
