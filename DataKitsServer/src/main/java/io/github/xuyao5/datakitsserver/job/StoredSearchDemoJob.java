package io.github.xuyao5.datakitsserver.job;

import io.github.xuyao5.datakitsserver.configuration.EsClientConfig;
import io.github.xuyao5.dkl.eskits.service.StoredSearchExecutor;
import io.github.xuyao5.dkl.eskits.service.config.StoredSearchConfig;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.script.mustache.SearchTemplateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("storedSearchDemoJob")
public final class StoredSearchDemoJob implements Runnable {

    @Autowired
    protected RestHighLevelClient esClient;

    @Autowired
    protected EsClientConfig esClientConfig;

    @Override
    public void run() {
        StoredSearchExecutor storedSearchExecutor = new StoredSearchExecutor(esClient);
        SearchTemplateResponse execute = storedSearchExecutor.execute(StoredSearchConfig.of("default-search", "SEARCH-STORED"));
        System.out.println(execute);
    }
}
