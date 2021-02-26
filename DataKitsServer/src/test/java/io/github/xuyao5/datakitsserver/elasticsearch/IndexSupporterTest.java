package io.github.xuyao5.datakitsserver.elasticsearch;

import io.github.xuyao5.datakitsserver.abstr.AbstractTest;
import io.github.xuyao5.dkl.eskits.support.IndexSupporter;
import org.junit.jupiter.api.Test;

public class IndexSupporterTest extends AbstractTest {

    @Test
    void testCreate() {
        String mapping = "{\n" +
                "  \"properties\": {\n" +
                "    \"message\": {\n" +
                "      \"type\": \"keyword\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
        esClient.run(client -> new IndexSupporter(client).create("file2es_disruptor_1", 1, 0, mapping, "FILE2ES-DISRUPTOR", true));
    }
}
