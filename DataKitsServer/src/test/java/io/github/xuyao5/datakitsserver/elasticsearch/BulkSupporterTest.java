package io.github.xuyao5.datakitsserver.elasticsearch;

import io.github.xuyao5.datakitsserver.abstr.AbstractTest;
import io.github.xuyao5.dkl.eskits.support.batch.BulkSupporter;
import org.junit.jupiter.api.Test;

public class BulkSupporterTest extends AbstractTest {

    @Test
    void testBulk() {
        new BulkSupporter().bulk(esClient, esClientConfig.getEsBulkThreads(), function -> {
            for (int i = 0; i < 1000000; i++) {
                function.apply(BulkSupporter.buildIndexRequest("test_index_5", Pojo.of("xu")));
            }
        });
    }
}
