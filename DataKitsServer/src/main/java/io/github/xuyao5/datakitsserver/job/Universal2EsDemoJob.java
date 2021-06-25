package io.github.xuyao5.datakitsserver.job;

import io.github.xuyao5.datakitsserver.configuration.EsClientConfig;
import io.github.xuyao5.datakitsserver.vo.MyApiDocument;
import io.github.xuyao5.dkl.eskits.service.Universal2EsExecutor;
import io.github.xuyao5.dkl.eskits.service.config.Universal2EsConfig;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component("universal2EsDemoJob")
public final class Universal2EsDemoJob implements Runnable {

    @Autowired
    protected RestHighLevelClient esClient;

    @Autowired
    protected EsClientConfig esClientConfig;

    @Override
    public void run() {
        Universal2EsConfig config = Universal2EsConfig.of();

        new Universal2EsExecutor(esClient).execute(config, obj -> {
            log.info("对象：{}", obj);
            return MyApiDocument.of();
        });
    }
}