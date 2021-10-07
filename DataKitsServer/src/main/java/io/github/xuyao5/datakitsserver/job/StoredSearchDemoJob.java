package io.github.xuyao5.datakitsserver.job;

import io.github.xuyao5.datakitsserver.configuration.EsKitsConfig;
import io.github.xuyao5.dkl.eskits.service.StoredSearchService;
import io.github.xuyao5.dkl.eskits.service.config.StoredSearchConfig;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.script.mustache.SearchTemplateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("storedSearchJob")
public final class StoredSearchDemoJob implements Runnable {

    @Autowired
    protected RestHighLevelClient esClient;

    @Autowired
    protected EsKitsConfig esKitsConfig;

    @Override
    public void run() {
        SearchTemplateResponse execute = new StoredSearchService(esClient).execute(StoredSearchConfig.of("default-search", "SEARCH-STORED"));
        System.out.println(execute);
    }
}
