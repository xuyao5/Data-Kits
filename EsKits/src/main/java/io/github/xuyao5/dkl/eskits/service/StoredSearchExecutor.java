package io.github.xuyao5.dkl.eskits.service;

import io.github.xuyao5.dkl.eskits.configuration.StoredSearchConfig;
import io.github.xuyao5.dkl.eskits.context.AbstractExecutor;
import io.github.xuyao5.dkl.eskits.schema.StandardSearchSourceDocument;
import io.github.xuyao5.dkl.eskits.support.general.ClusterSupporter;
import io.github.xuyao5.dkl.eskits.support.general.DocumentSupporter;
import io.github.xuyao5.dkl.eskits.support.general.IndexSupporter;
import io.github.xuyao5.dkl.eskits.support.general.SearchSupporter;
import io.github.xuyao5.dkl.eskits.support.mapping.XContentSupporter;
import io.github.xuyao5.dkl.eskits.util.MyFieldUtils;
import io.github.xuyao5.dkl.eskits.util.MyGsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.mustache.SearchTemplateResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

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
    public void execute(@NotNull StoredSearchConfig config) {
        String searchName = "my-search-1";
        QueryBuilder queryBuilder = QueryBuilders.boolQuery().filter(QueryBuilders.termQuery("searchName", searchName));
        SearchSupporter searchSupporter = SearchSupporter.getInstance();
        SearchResponse search = searchSupporter.search(client, queryBuilder, 0, 1, SEARCH_STORED_INDEX);

        if (search.getHits().getTotalHits().value > 0) {
            SearchHit[] hits1 = search.getHits().getHits();
            SearchHit documentFields = hits1[0];
            String sourceAsString = documentFields.getSourceAsString();
            Optional<StandardSearchSourceDocument> standardSearchSourceDocument = MyGsonUtils.json2Obj(sourceAsString);
            standardSearchSourceDocument.ifPresent(document -> {
                SearchTemplateResponse searchResponse = searchSupporter.searchTemplate(client, document.getQuery(), Collections.EMPTY_MAP, "file2es_disruptor_1");
                if (searchResponse.getResponse().getHits().getTotalHits().value > 0) {
                    System.out.println(searchResponse.getResponse().getHits().getTotalHits().value);
                    SearchHit[] hits = searchResponse.getResponse().getHits().getHits();
                    for (int i = 0; i < hits.length; i++) {
                        System.out.println(hits[i]);
                    }
                }
            });
        }
    }

    public void initial() {
        String querySource = SearchSourceBuilder.searchSource()
                .query(QueryBuilders.matchAllQuery())
                .from(0)
                .size(1000)
                .toString();
        StandardSearchSourceDocument searchDocument = StandardSearchSourceDocument.of("my-search-1", querySource);

        IndexSupporter indexSupporter = IndexSupporter.getInstance();
        if (!indexSupporter.exists(client, SEARCH_STORED_INDEX)) {
            Map<String, Class<?>> declaredFieldsMap = MyFieldUtils.getDeclaredFieldsMap(searchDocument);
            int numberOfDataNodes = ClusterSupporter.getInstance().health(client).getNumberOfDataNodes();
            indexSupporter.create(client, SEARCH_STORED_INDEX, numberOfDataNodes, 0, XContentSupporter.buildMapping(declaredFieldsMap));
            //设置别名
        }

        DocumentSupporter.getInstance().index(client, SEARCH_STORED_INDEX, searchDocument);

        //设置别名
    }
}
