package io.github.xuyao5.dkl.eskits.support;

import io.github.xuyao5.dkl.eskits.abstr.AbstractSupporter;
import lombok.SneakyThrows;
import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.script.mustache.MultiSearchTemplateRequest;
import org.elasticsearch.script.mustache.MultiSearchTemplateResponse;
import org.elasticsearch.script.mustache.SearchTemplateRequest;
import org.elasticsearch.script.mustache.SearchTemplateResponse;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

import static org.elasticsearch.client.RequestOptions.DEFAULT;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 1/05/20 22:48
 * @apiNote EsIndexSupporter
 * @implNote EsIndexSupporter
 */
public final class EsSearchSupporter extends AbstractSupporter {

    public EsSearchSupporter(@NotNull RestHighLevelClient client) {
        super(client);
    }

    /**
     * Search API
     */
    @SneakyThrows
    public SearchResponse search() {
        return client.search(new SearchRequest("").source(new SearchSourceBuilder().query(null)
                        .from(0)
                        .size(10)
                        .timeout(TimeValue.timeValueSeconds(60))
                        .fetchSource(false)),
                DEFAULT);
    }

    /**
     * Multi-Search API
     */
    @SneakyThrows
    public MultiSearchResponse multiSearch() {
        MultiSearchRequest request = new MultiSearchRequest();
        SearchRequest firstSearchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("user", "kimchy"));
        firstSearchRequest.source(searchSourceBuilder);
        request.add(firstSearchRequest);
        SearchRequest secondSearchRequest = new SearchRequest();
        searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("user", "luca"));
        secondSearchRequest.source(searchSourceBuilder);
        request.add(secondSearchRequest);
        return client.msearch(request, DEFAULT);
    }

    /**
     * Search Template API
     */
    @SneakyThrows
    public SearchTemplateResponse searchTemplate() {
        SearchTemplateRequest request = new SearchTemplateRequest();
        request.setRequest(new SearchRequest(""));
        request.setScriptType(ScriptType.INLINE);
        request.setScript("");
        request.setScriptParams(null);
        return client.searchTemplate(request, DEFAULT);
    }

    /**
     * Multi Search Template API
     */
    @SneakyThrows
    public MultiSearchTemplateResponse multiSearchTemplate() {
        String[] searchTerms = {"elasticsearch", "logstash", "kibana"};

        MultiSearchTemplateRequest multiRequest = new MultiSearchTemplateRequest();
        for (String searchTerm : searchTerms) {
            SearchTemplateRequest request = new SearchTemplateRequest();
            request.setRequest(new SearchRequest("posts"));

            request.setScriptType(ScriptType.INLINE);
            request.setScript(
                    "{" +
                            "  \"query\": { \"match\" : { \"{{field}}\" : \"{{value}}\" } }," +
                            "  \"size\" : \"{{size}}\"" +
                            "}");

            Map<String, Object> scriptParams = new HashMap<>();
            scriptParams.put("field", "title");
            scriptParams.put("value", searchTerm);
            scriptParams.put("size", 5);
            request.setScriptParams(scriptParams);

            multiRequest.add(request);
        }
        return client.msearchTemplate(multiRequest, DEFAULT);
    }

    /**
     * Count API
     */
    @SneakyThrows
    public CountResponse count() {
        CountRequest countRequest = new CountRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        return client.count(countRequest, DEFAULT);
    }
}
