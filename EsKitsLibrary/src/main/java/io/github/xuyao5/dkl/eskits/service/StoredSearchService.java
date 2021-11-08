package io.github.xuyao5.dkl.eskits.service;

import io.github.xuyao5.dkl.eskits.context.AbstractExecutor;
import io.github.xuyao5.dkl.eskits.schema.standard.StandardSearchSource;
import io.github.xuyao5.dkl.eskits.service.config.StoredSearchConfig;
import io.github.xuyao5.dkl.eskits.support.boost.AliasesSupporter;
import io.github.xuyao5.dkl.eskits.support.general.ClusterSupporter;
import io.github.xuyao5.dkl.eskits.support.general.DocumentSupporter;
import io.github.xuyao5.dkl.eskits.support.general.IndexSupporter;
import io.github.xuyao5.dkl.eskits.support.general.SearchSupporter;
import io.github.xuyao5.dkl.eskits.support.mapping.XContentSupporter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.mustache.SearchTemplateResponse;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Thomas.XU(xuyao)
 * @version 20/03/21 13:12
 */
@Slf4j
public final class StoredSearchService extends AbstractExecutor {

    private static final String SEARCH_STORED_INDEX = "search_stored_v1";
    private static final String SEARCH_STORED_ALIAS = "SEARCH-STORED";
    private static final String DEFAULT_SEARCH = "default-search";

    public StoredSearchService(@NonNull RestHighLevelClient client) {
        super(client);
    }

    //用存储在ES中的代码来搜索
    public SearchTemplateResponse execute(@NonNull StoredSearchConfig config) {
        DocumentSupporter documentSupporter = DocumentSupporter.getInstance();
        Map<String, Object> source = documentSupporter.getSource(client, SEARCH_STORED_ALIAS, config.getSearchName()).getSource();
        if ("DSL".equals(source.get("searchType"))) {
            return SearchSupporter.getInstance().searchTemplate(client, source.get("searchCode").toString(), new HashMap<>(), config.getIndex());
        } else if ("SQL".equals(source.get("searchType"))) {
            log.warn("暂时无法支持SQL类型搜索，请使用DSL，类型为【{}】", source);
        } else {
            log.error("搜索类型必须为DSL或SQL，错误类型为【{}】", source);
        }
        return null;
    }

    public void initial() {
        String querySource = SearchSourceBuilder.searchSource().query(QueryBuilders.matchAllQuery()).from(0).size(1000).toString();
        StandardSearchSource searchDocument = StandardSearchSource.of(new StringBuilder(querySource));
        searchDocument.setSearchDescription("搜索器示例");
        IndexSupporter indexSupporter = IndexSupporter.getInstance();
        if (!indexSupporter.exists(client, SEARCH_STORED_INDEX)) {
            int numberOfDataNodes = ClusterSupporter.getInstance().health(client).getNumberOfDataNodes();
            indexSupporter.create(client, SEARCH_STORED_INDEX, numberOfDataNodes, 2, XContentSupporter.getInstance().buildMapping(StandardSearchSource.class));
            AliasesSupporter.getInstance().migrate(client, SEARCH_STORED_ALIAS, SEARCH_STORED_INDEX);
        }
        DocumentSupporter.getInstance().index(client, SEARCH_STORED_ALIAS, DEFAULT_SEARCH, searchDocument);
    }
}
