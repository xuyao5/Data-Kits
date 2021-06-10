package io.github.xuyao5.dkl.eskits.support.general;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.script.mustache.MultiSearchTemplateRequest;
import org.elasticsearch.script.mustache.MultiSearchTemplateResponse;
import org.elasticsearch.script.mustache.SearchTemplateRequest;
import org.elasticsearch.script.mustache.SearchTemplateResponse;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.List;
import java.util.Map;

import static org.elasticsearch.client.RequestOptions.DEFAULT;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 1/05/20 22:48
 * @apiNote SearchSupporter
 * @implNote SearchSupporter
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SearchSupporter {

    public static SearchSupporter getInstance() {
        return SearchSupporter.SingletonHolder.INSTANCE;
    }

    /**
     * Search API
     */
    @SneakyThrows
    public SearchResponse search(RestHighLevelClient client, QueryBuilder query, int from, int size, String... indices) {
        return client.search(new SearchRequest(indices).source(SearchSourceBuilder.searchSource().query(query).from(from).size(size)), DEFAULT);
    }

    /**
     * Multi-Search API
     */
    @SneakyThrows
    public MultiSearchResponse multiSearch(RestHighLevelClient client, List<SearchRequest> searchRequests) {
        return client.msearch(searchRequests.stream().collect(MultiSearchRequest::new, MultiSearchRequest::add, (item1, item2) -> {
        }), DEFAULT);
    }

    /**
     * Search Template API
     */
    @SneakyThrows
    public SearchTemplateResponse searchTemplate(RestHighLevelClient client, String code, Map<String, Object> params, String... indices) {
        SearchTemplateRequest request = new SearchTemplateRequest();
        request.setRequest(new SearchRequest(indices));
        request.setScriptType(ScriptType.INLINE);
        request.setScript(code);
        request.setScriptParams(params);
        return client.searchTemplate(request, DEFAULT);
    }

    /**
     * Multi Search Template API
     */
    @SneakyThrows
    public MultiSearchTemplateResponse multiSearchTemplate(RestHighLevelClient client, List<SearchTemplateRequest> searchTemplateRequests) {
        return client.msearchTemplate(searchTemplateRequests.stream().collect(MultiSearchTemplateRequest::new, MultiSearchTemplateRequest::add, (item1, item2) -> {
        }), DEFAULT);
    }

    /**
     * Count API
     */
    @SneakyThrows
    public CountResponse count(RestHighLevelClient client, QueryBuilder query, String... indices) {
        return client.count(new CountRequest(indices).query(query), DEFAULT);
    }

    private static class SingletonHolder {
        private static final SearchSupporter INSTANCE = new SearchSupporter();
    }
}