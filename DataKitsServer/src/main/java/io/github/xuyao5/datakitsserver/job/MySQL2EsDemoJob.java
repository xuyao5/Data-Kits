package io.github.xuyao5.datakitsserver.job;

import com.lmax.disruptor.EventFactory;
import io.github.xuyao5.datakitsserver.configuration.elasticsearch.EsKitsConfig;
import io.github.xuyao5.datakitsserver.vo.MyTableDocument;
import io.github.xuyao5.dkl.eskits.service.MySQL2EsService;
import io.github.xuyao5.dkl.eskits.service.config.MySQL2EsConfig;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;
import java.util.function.UnaryOperator;

@Slf4j
@Component("mySQL2EsDemoJob")
public final class MySQL2EsDemoJob implements Runnable {

    @Autowired
    private RestHighLevelClient esClient;

    @Autowired
    private EsKitsConfig esKitsConfig;

    @SneakyThrows
    @Override
    public void run() {
        Map<String, EventFactory<MyTableDocument>> tableDocument = Collections.singletonMap("MyTable", MyTableDocument::of);
        long execute = new MySQL2EsService<MyTableDocument>(esClient, esKitsConfig.getMysqlBinlogHostname(), esKitsConfig.getMysqlBinlogPort(), esKitsConfig.getMysqlBinlogSchema(), esKitsConfig.getMysqlBinlogUsername(), esKitsConfig.getMysqlBinlogPassword()).execute(MySQL2EsConfig.of(), tableDocument, UnaryOperator.identity());
        log.info("共计算[{}]条数据", execute);
        System.in.read();
    }
}
