package io.github.xuyao5.dkl.eskits.support;

import io.github.xuyao5.dkl.eskits.abstr.AbstractSupporter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.script.mustache.SearchTemplateRequest;
import org.elasticsearch.script.mustache.SearchTemplateResponse;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import javax.validation.constraints.NotNull;

import static org.elasticsearch.client.RequestOptions.DEFAULT;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 1/05/20 22:48
 * @apiNote EsIndexSupporter
 * @implNote EsIndexSupporter
 */
@Slf4j
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
