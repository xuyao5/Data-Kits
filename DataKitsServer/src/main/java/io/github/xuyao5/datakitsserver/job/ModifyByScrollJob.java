package io.github.xuyao5.datakitsserver.job;

import io.github.xuyao5.datakitsserver.configuration.EsClientConfig;
import io.github.xuyao5.datakitsserver.vo.MyDocument;
import io.github.xuyao5.dkl.eskits.service.ModifyByScrollExecutor;
import io.github.xuyao5.dkl.eskits.service.config.ModifyByScrollConfig;
import io.github.xuyao5.dkl.eskits.util.MyDateUtils;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("modifyByScrollJob")
public final class ModifyByScrollJob implements Runnable {

    @Autowired
    protected RestHighLevelClient esClient;

    @Autowired
    protected EsClientConfig esClientConfig;

    @Override
    public void run() {
        final String[] OLD_INDEX = {"file2es_disruptor_100w_1619626642176"};
        final String NEW_INDEX = "file2es_disruptor_100w_1619626642176";

        ModifyByScrollConfig modifyByScrollConfig = ModifyByScrollConfig.of(OLD_INDEX, NEW_INDEX);
        modifyByScrollConfig.setQueryBuilder(QueryBuilders.boolQuery().filter(QueryBuilders.termQuery("uuid", "583852675070689280")));
        new ModifyByScrollExecutor(esClient, esClientConfig.getEsBulkThreads()).upsertByScroll(modifyByScrollConfig, MyDocument::of, myDocument -> {
            myDocument.setModifyDate(MyDateUtils.now());
            return myDocument;
        });
    }
}
