package io.github.xuyao5.datakitsserver.job;

import io.github.xuyao5.datakitsserver.configuration.EsKitsConfig;
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
    private RestHighLevelClient esClient;

    @Autowired
    private EsKitsConfig esKitsConfig;

    @Override
    public void run() {
        final String NEW_INDEX = "FILE2ES_DISRUPTOR";

        ModifyByScrollConfig modifyByScrollConfig = ModifyByScrollConfig.of(NEW_INDEX);
        modifyByScrollConfig.setQueryBuilder(QueryBuilders.matchAllQuery());
        new ModifyByScrollService(esClient, esKitsConfig.getEsBulkThreads(), esKitsConfig.getEsScrollSize()).upsertByScroll(modifyByScrollConfig, MyFileDocument::of, myFileDocument -> {
            myFileDocument.setModifyDate(DateUtilsPlus.now());
            return myFileDocument;
        });
    }
}
