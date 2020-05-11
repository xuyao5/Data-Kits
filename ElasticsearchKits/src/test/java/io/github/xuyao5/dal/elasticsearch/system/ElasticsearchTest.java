package io.github.xuyao5.dal.elasticsearch.system;

import io.github.xuyao5.dal.elasticsearch.EsClient;
import io.github.xuyao5.dal.elasticsearch.abstr.AbstractTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

@Slf4j
class ElasticsearchTest extends AbstractTest {

    @Resource(name = "esClient")
    private EsClient<String> client;

    @Test
    void testElasticsearch() {
        final String INDEX = "twitter";//必须要小写

        String execute = client.execute((client) -> {
            return "成功执行";
        });
        System.out.println(execute);
    }
}
