package io.github.xuyao5.dkl.eskits.service;

import io.github.xuyao5.dkl.eskits.context.AbstractExecutor;
import io.github.xuyao5.dkl.eskits.schema.StandardSearchSourceDocument;
import io.github.xuyao5.dkl.eskits.support.ClusterSupporter;
import io.github.xuyao5.dkl.eskits.support.DocumentSupporter;
import io.github.xuyao5.dkl.eskits.support.IndexSupporter;
import io.github.xuyao5.dkl.eskits.support.SearchSupporter;
import io.github.xuyao5.dkl.eskits.support.mapping.XContentSupporter;
import io.github.xuyao5.dkl.eskits.util.MyFieldUtils;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.GetSourceResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.mustache.SearchTemplateResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.Collections;
import java.util.Map;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 20/03/21 13:12
 * @apiNote StoredSearchExecutor
 * @implNote StoredSearchExecutor
 */
@Slf4j
public final class StoredSearchExecutor extends AbstractExecutor {

    private static final String SEARCH_STORED_INDEX = "search_stored_index";
    private static final String SEARCH_STORED_ALIAS = "";

    public StoredSearchExecutor(RestHighLevelClient client) {
        super(client);
    }

    //用存储在ES中的代码来搜索
    public void execute() {
        String id = "1";
        GetSourceResponse source = DocumentSupporter.getInstance().getSource(client, SEARCH_STORED_INDEX, id);
        Map<String, Object> map = source.getSource();
        if (id.equals(map.get("searchId"))) {
            String code = map.get("query").toString();
            SearchTemplateResponse searchResponse = SearchSupporter.getInstance().searchTemplate(client, code, Collections.EMPTY_MAP, "file2es_disruptor_1");
            SearchHit[] hits = searchResponse.getResponse().getHits().getHits();
            for (int i = 0; i < hits.length; i++) {
                SearchHit hit = hits[i];
                System.out.println(hit);
            }
        }
    }

    public void initial() {
        String querySource = SearchSourceBuilder.searchSource()
                .query(QueryBuilders.matchAllQuery())
                .from(0)
                .size(1000)
                .toString();
        StandardSearchSourceDocument searchDocument = StandardSearchSourceDocument.of("1", querySource);

        IndexSupporter indexSupporter = IndexSupporter.getInstance();
        if (!indexSupporter.exists(client, SEARCH_STORED_INDEX)) {
            Map<String, Class<?>> declaredFieldsMap = MyFieldUtils.getDeclaredFieldsMap(searchDocument);
            int numberOfDataNodes = ClusterSupporter.getInstance().health(client).getNumberOfDataNodes();
            indexSupporter.create(client, SEARCH_STORED_INDEX, numberOfDataNodes, 0, XContentSupporter.buildMapping(declaredFieldsMap));
            //设置别名
        }

        DocumentSupporter.getInstance().index(client, SEARCH_STORED_INDEX, searchDocument.getSearchId(), searchDocument);

        //设置别名
    }
}
