package io.github.xuyao5.dkl.eskits.support.batch;

import io.github.xuyao5.dkl.eskits.abstr.AbstractSupporter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.get.MultiGetRequest;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.script.mustache.MultiSearchTemplateRequest;
import org.elasticsearch.script.mustache.MultiSearchTemplateResponse;
import org.elasticsearch.script.mustache.SearchTemplateRequest;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.elasticsearch.client.RequestOptions.DEFAULT;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 19/02/21 22:12
 * @apiNote MultiFamilySupporter
 * @implNote MultiFamilySupporter
 */
@Slf4j
public final class MultiFetchSupporter extends AbstractSupporter {

    public MultiFetchSupporter(@NotNull RestHighLevelClient client) {
        super(client);
    }

    /**
     * Multi-Get API
     */
    @SneakyThrows
    public MultiGetResponse multiGet(@NotNull List<MultiGetRequest.Item> items) {
        return client.mget(items.stream().reduce(new MultiGetRequest(), MultiGetRequest::add, (item1, item2) -> null), DEFAULT);
    }

    /**
     * Multi-Search API
     */
    @SneakyThrows
    public MultiSearchResponse multiSearch(@NotNull List<SearchRequest> searchRequests) {
        return client.msearch(searchRequests.stream().reduce(new MultiSearchRequest(), MultiSearchRequest::add, (item1, item2) -> null), DEFAULT);
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
}
