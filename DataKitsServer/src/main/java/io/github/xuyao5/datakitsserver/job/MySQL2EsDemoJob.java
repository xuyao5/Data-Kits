package io.github.xuyao5.datakitsserver.job;

import com.github.shyiko.mysql.binlog.jmx.BinaryLogClientMXBean;
import io.github.xuyao5.datakitsserver.configuration.EsKitsConfig;
import io.github.xuyao5.dkl.eskits.service.MySQL2EsService;
import io.github.xuyao5.dkl.eskits.service.config.MySQL2EsConfig;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

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
        Future<BinaryLogClientMXBean> execute = new MySQL2EsService(esClient, esKitsConfig.getEsBulkThreads()).execute(MySQL2EsConfig.of("BinlogTest", "root", "123456", new String[]{"MyTable"}));
        System.in.read();
    }
}
