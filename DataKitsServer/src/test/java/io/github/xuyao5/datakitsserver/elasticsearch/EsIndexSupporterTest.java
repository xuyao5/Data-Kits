package io.github.xuyao5.datakitsserver.elasticsearch;

import io.github.xuyao5.datakitsserver.abstr.AbstractTest;
import io.github.xuyao5.dkl.eskits.support.EsIndexSupporter;
import org.junit.jupiter.api.Test;

public class EsIndexSupporterTest extends AbstractTest {

    @Test
    void testCreate() {
        String mapping = "{\n" +
                "  \"properties\": {\n" +
                "    \"message\": {\n" +
                "      \"type\": \"keyword\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
        esClient.execute(client -> new EsIndexSupporter(client).create("test_index_5", 1, 0, mapping, "DEMO-5", true));
    }
}
