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
        esClient.execute(client -> new IndexSupporter(client).create("test_index_5", 1, 0, mapping, "DEMO-5", true));
    }
}
