package io.github.xuyao5.datakitsserver.service;

import io.github.xuyao5.datakitsserver.abstr.AbstractTest;
import io.github.xuyao5.datakitsserver.elasticsearch.Pojo;
import io.github.xuyao5.dkl.eskits.service.File2EsExecutor;
import io.github.xuyao5.dkl.eskits.support.IndexSupporter;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.charset.StandardCharsets;

public class File2EsExecutorTest extends AbstractTest {

    @Test
    void test() {
        new File2EsExecutor(esClient).execute(standardFileLine -> Pojo.of(standardFileLine.toString()), new File("/Users/xuyao/Downloads/INT_PAB_CC_TEST-NAME-LIST_20201010_T_00.txt"), StandardCharsets.UTF_8, "file2es_disruptor_1");
    }

    @Test
    void recreateIndex() {
        String mapping = "{\n" +
                "  \"properties\": {\n" +
                "    \"message\": {\n" +
                "      \"type\": \"keyword\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
        esClient.execute(client -> {
            IndexSupporter indexSupporter = new IndexSupporter(client);
            if (indexSupporter.exists("file2es_disruptor_1")) {
                indexSupporter.delete("file2es_disruptor_1");
            }
            indexSupporter.create("file2es_disruptor_1", 1, 0, mapping, "FILE2ES-DISRUPTOR", true);
            return 0;
        });
    }
}
