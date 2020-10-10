package io.github.xuyao5.dal.elasticsearch.system;

import io.github.xuyao5.dal.elasticsearch.abstr.AbstractTest;
import io.github.xuyao5.dal.elasticsearch.client.EsClient;
import io.github.xuyao5.dal.elasticsearch.consts.ScriptConst;
import io.github.xuyao5.dal.elasticsearch.search.param.SearchTemplateParams;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.script.mustache.SearchTemplateResponse;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

@Slf4j
class ElasticsearchTest extends AbstractTest {

    @Resource(name = "esClient")
    private EsClient client;

    @SneakyThrows
    @Test
    void testElasticsearch() {
        final String[] INDEX = {"recipes"};//必须要小写

        String script = scriptSupporter.getScript(ScriptConst.MATCH_ALL);

        SearchTemplateParams params = SearchTemplateParams.of(INDEX);
        params.setScript(script);

        String execute = client.execute((client) -> {
            SearchTemplateResponse response = searchSupporter.searchTemplate(client, params);
            response.getResponse().getHits().forEach(documentFields -> {
                System.out.println(documentFields);
            });
            return "成功执行";
        });
        System.out.println(execute);
    }
}
