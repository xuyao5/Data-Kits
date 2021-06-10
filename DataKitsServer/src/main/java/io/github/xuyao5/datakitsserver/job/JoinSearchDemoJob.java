package io.github.xuyao5.datakitsserver.job;

import io.github.xuyao5.datakitsserver.configuration.EsClientConfig;
import io.github.xuyao5.dkl.eskits.service.JoinSearchExecutor;
import io.github.xuyao5.dkl.eskits.service.config.JoinSearchConfig;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("joinSearchDemoJob")
public class JoinSearchDemoJob implements Runnable {

    @Autowired
    protected RestHighLevelClient esClient;

    @Autowired
    protected EsClientConfig esClientConfig;

    @Override
    public void run() {
        new JoinSearchExecutor(esClient).innerJoin(JoinSearchConfig.of("FILE2ES_DISRUPTOR_LEFT", "FILE2ES_DISRUPTOR_RIGHT"));
    }
}
