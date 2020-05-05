package io.github.xuyao5.dal.elasticsearch.system;

import io.github.xuyao5.dal.elasticsearch.base.AbstractTest;
import io.github.xuyao5.dal.elasticsearch.base.EsClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class ElasticsearchTest extends AbstractTest {

    @Test
    void testElasticsearch() {
        final String INDEX = "twitter";//必须要小写

        EsClient<String> elasticsearchClient = factory.getElasticsearchClient();
        String execute = elasticsearchClient.execute((client) -> {
            return "成功执行";
        });
        System.out.println(execute);
    }
}
