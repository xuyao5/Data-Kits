package io.github.xuyao5.dkl.eskits.support;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.script.mustache.SearchTemplateRequest;
import org.elasticsearch.script.mustache.SearchTemplateResponse;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import javax.validation.constraints.NotNull;
import java.util.Map;

import static org.elasticsearch.client.RequestOptions.DEFAULT;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 1/05/20 22:48
 * @apiNote EsIndexSupporter
 * @implNote EsIndexSupporter
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SearchSupporter {

    public static final SearchSupporter getInstance() {
        return SearchSupporter.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final SearchSupporter INSTANCE = new SearchSupporter();
    }

    /**
     * Search API
     */
    @SneakyThrows
    public SearchResponse search(@NotNull RestHighLevelClient client, @NotNull QueryBuilder query, int from, int size, @NotNull String... indices) {
        return client.search(new SearchRequest(indices).source(new SearchSourceBuilder().query(query).from(from).size(size)), DEFAULT);
    }

    /**
     * Search Template API
     */
    @SneakyThrows
    public SearchTemplateResponse searchTemplate(@NotNull RestHighLevelClient client, @NotNull String code, @NotNull Map<String, Object> params, @NotNull String... indices) {
        SearchTemplateRequest request = new SearchTemplateRequest();
        request.setRequest(new SearchRequest(indices));
        request.setScriptType(ScriptType.INLINE);
        request.setScript(code);
        request.setScriptParams(params);
        return client.searchTemplate(request, DEFAULT);
    }

    /**
     * Count API
     */
    @SneakyThrows
    public CountResponse count(@NotNull RestHighLevelClient client, @NotNull QueryBuilder query, @NotNull String... indices) {
        return client.count(new CountRequest(indices).query(query), DEFAULT);
    }
}
