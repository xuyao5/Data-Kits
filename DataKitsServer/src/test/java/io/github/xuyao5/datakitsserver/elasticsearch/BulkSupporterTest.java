package io.github.xuyao5.datakitsserver.elasticsearch;

import io.github.xuyao5.datakitsserver.abstr.AbstractTest;
import io.github.xuyao5.dkl.eskits.support.bulk.BulkSupporter;
import org.junit.jupiter.api.Test;

public class BulkSupporterTest extends AbstractTest {

    @Test
    void test() {
        getEsClient().execute(restHighLevelClient -> {
            BulkSupporter bulkSupporter = new BulkSupporter(restHighLevelClient, 0, 0);
            bulkSupporter.bulk(val -> {
                val.apply(null);
                return 0;
            });
            return null;
        });
    }
}
