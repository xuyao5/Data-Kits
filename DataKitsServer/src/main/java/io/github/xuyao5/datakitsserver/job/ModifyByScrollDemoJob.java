package io.github.xuyao5.datakitsserver.job;

import io.github.xuyao5.datakitsserver.configuration.EsClientConfig;
import io.github.xuyao5.datakitsserver.vo.MyFileDocument;
import io.github.xuyao5.dkl.eskits.service.ModifyByScrollService;
import io.github.xuyao5.dkl.eskits.service.config.ModifyByScrollConfig;
import io.github.xuyao5.dkl.eskits.util.DateUtilsPlus;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("modifyByScrollJob")
public final class ModifyByScrollDemoJob implements Runnable {

    @Autowired
    protected RestHighLevelClient esClient;

    @Autowired
    protected EsClientConfig esClientConfig;

    @Override
    public void run() {
        final String NEW_INDEX = "file2es_disruptor_100w_";

        ModifyByScrollConfig modifyByScrollConfig = ModifyByScrollConfig.of(NEW_INDEX);
        modifyByScrollConfig.setQueryBuilder(QueryBuilders.matchAllQuery());
        new ModifyByScrollService(esClient, esClientConfig.getEsBulkThreads(), esClientConfig.getEsScrollSize()).upsertByScroll(modifyByScrollConfig, MyFileDocument::of, myFileDocument -> {
            myFileDocument.setModifyDate(DateUtilsPlus.now());
            return myFileDocument;
        });
    }
}
