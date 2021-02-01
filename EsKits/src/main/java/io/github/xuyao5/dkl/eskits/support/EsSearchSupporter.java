package io.github.xuyao5.dkl.eskits.support;

import io.github.xuyao5.dkl.eskits.abstr.AbstractSupporter;
import io.github.xuyao5.dkl.eskits.support.param.*;
import lombok.SneakyThrows;
import org.elasticsearch.action.explain.ExplainRequest;
import org.elasticsearch.action.explain.ExplainResponse;
import org.elasticsearch.action.fieldcaps.FieldCapabilitiesRequest;
import org.elasticsearch.action.fieldcaps.FieldCapabilitiesResponse;
import org.elasticsearch.action.search.*;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.rankeval.*;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.script.mustache.MultiSearchTemplateRequest;
import org.elasticsearch.script.mustache.MultiSearchTemplateResponse;
import org.elasticsearch.script.mustache.SearchTemplateRequest;
import org.elasticsearch.script.mustache.SearchTemplateResponse;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import javax.validation.constraints.NotNull;
import java.util.*;

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
    public SearchResponse search(@NotNull SearchParams params) {
        return restHighLevelClient.search(new SearchRequest(params.getIndex()).source(new SearchSourceBuilder().query(params.getQueryBuilder())
                        .from(0)
                        .size(10)
                        .timeout(TimeValue.timeValueSeconds(60))
                        .fetchSource(false)),
                RequestOptions.DEFAULT);
    }

    /**
     * Search Scroll API
     */
    @SneakyThrows
    public SearchResponse scroll(@NotNull SearchScrollParams params) {
        SearchScrollRequest request = new SearchScrollRequest();
        request.scroll(TimeValue.timeValueSeconds(30));
        return restHighLevelClient.scroll(request, RequestOptions.DEFAULT);
    }


    /**
     * Clear Scroll API
     */
    @SneakyThrows
    public ClearScrollResponse clearScroll(@NotNull ClearScrollParams params) {
        ClearScrollRequest request = new ClearScrollRequest();
        request.addScrollId("scrollId");
        return restHighLevelClient.clearScroll(request, RequestOptions.DEFAULT);
    }

    /**
     * Multi-Search API
     */
    @SneakyThrows
    public MultiSearchResponse msearch(@NotNull MultiSearchParams params) {
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
        return restHighLevelClient.msearch(request, RequestOptions.DEFAULT);
    }

    /**
     * Search Template API
     */
    @SneakyThrows
    public SearchTemplateResponse searchTemplate(@NotNull SearchTemplateParams params) {
        SearchTemplateRequest request = new SearchTemplateRequest();
        request.setRequest(new SearchRequest(params.getIndices()));
        request.setScriptType(ScriptType.INLINE);
        request.setScript(params.getScript());
        request.setScriptParams(params.getScriptParams());
        return restHighLevelClient.searchTemplate(request, RequestOptions.DEFAULT);
    }

    /**
     * Multi-Search-Template API
     */
    @SneakyThrows
    public MultiSearchTemplateResponse msearchTemplate(@NotNull MultiSearchTemplateParams params) {
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
        return restHighLevelClient.msearchTemplate(multiRequest, RequestOptions.DEFAULT);
    }

    /**
     * Field Capabilities API
     */
    @SneakyThrows
    public FieldCapabilitiesResponse fieldCaps(@NotNull FieldCapabilitiesParams params) {
        FieldCapabilitiesRequest request = new FieldCapabilitiesRequest()
                .fields("user")
                .indices("posts", "authors", "contributors");
        return restHighLevelClient.fieldCaps(request, RequestOptions.DEFAULT);
    }

    /**
     * Ranking Evaluation API
     */
    @SneakyThrows
    public RankEvalResponse rankEval(@NotNull RankEvalParams params) {
        EvaluationMetric metric = new PrecisionAtK();
        List<RatedDocument> ratedDocs = new ArrayList<>();
        ratedDocs.add(new RatedDocument("posts", "1", 1));
        SearchSourceBuilder searchQuery = new SearchSourceBuilder();
        searchQuery.query(QueryBuilders.matchQuery("user", "kimchy"));
        RatedRequest ratedRequest =
                new RatedRequest("kimchy_query", ratedDocs, searchQuery);
        List<RatedRequest> ratedRequests = Arrays.asList(ratedRequest);
        RankEvalSpec specification =
                new RankEvalSpec(ratedRequests, metric);
        RankEvalRequest request =
                new RankEvalRequest(specification, new String[]{"posts"});
        return restHighLevelClient.rankEval(request, RequestOptions.DEFAULT);
    }

    /**
     * Explain API
     */
    @SneakyThrows
    public ExplainResponse explain(@NotNull ExplainParams params) {
        ExplainRequest request = new ExplainRequest("contributors", "1");
        request.query(QueryBuilders.termQuery("user", "tanguy"));
        return restHighLevelClient.explain(request, RequestOptions.DEFAULT);
    }

    /**
     * Count API
     */
    @SneakyThrows
    public CountResponse count(@NotNull CountParams params) {
        CountRequest countRequest = new CountRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        return restHighLevelClient.count(countRequest, RequestOptions.DEFAULT);
    }
}
