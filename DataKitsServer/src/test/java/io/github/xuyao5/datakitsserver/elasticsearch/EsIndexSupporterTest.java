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
        getEsClient().execute(client -> new EsIndexSupporter(client).create("TEST_INDEX_1", 1, 1, mapping, "abc", true));
    }
}
