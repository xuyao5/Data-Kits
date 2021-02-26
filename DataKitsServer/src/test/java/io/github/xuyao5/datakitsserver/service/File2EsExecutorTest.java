package io.github.xuyao5.datakitsserver.service;

import io.github.xuyao5.datakitsserver.abstr.AbstractTest;
import io.github.xuyao5.dkl.eskits.support.IndexSupporter;
import org.junit.jupiter.api.Test;

public class File2EsExecutorTest extends AbstractTest {

    @Test
    void recreateIndex() {
        String mapping = "{\n" +
                "  \"properties\": {\n" +
                "    \"message\": {\n" +
                "      \"type\": \"keyword\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
        esClient.run(client -> {
            IndexSupporter indexSupporter = new IndexSupporter(client);
            if (indexSupporter.exists("file2es_disruptor_1")) {
                indexSupporter.delete("file2es_disruptor_1");
            }
            indexSupporter.create("file2es_disruptor_1", 1, 0, mapping, "FILE2ES-DISRUPTOR", true);
            return 0;
        });
    }
}
