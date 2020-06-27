package io.github.xuyao5.dal.elasticsearch.system;

import io.github.xuyao5.dal.elasticsearch.EsClient;
import io.github.xuyao5.dal.elasticsearch.abstr.AbstractTest;
import io.github.xuyao5.dal.elasticsearch.search.param.SearchTemplateParams;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.elasticsearch.script.mustache.SearchTemplateResponse;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import javax.annotation.Resource;
import java.io.File;
import java.nio.charset.StandardCharsets;

import static org.springframework.util.ResourceUtils.CLASSPATH_URL_PREFIX;

@Slf4j
class ElasticsearchTest extends AbstractTest {

    @Resource(name = "esClient")
    private EsClient<String> client;

    @SneakyThrows
    @Test
    void testElasticsearch() {
        final String[] INDEX = {"recipes"};//必须要小写

        File file = ResourceUtils.getFile(CLASSPATH_URL_PREFIX + "script/search/" + "MatchAll.json");
        String script = FileUtils.readFileToString(file, StandardCharsets.UTF_8);

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
