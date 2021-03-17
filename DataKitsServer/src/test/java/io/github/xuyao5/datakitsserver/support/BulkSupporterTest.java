package io.github.xuyao5.datakitsserver.support;

import io.github.xuyao5.datakitsserver.context.AbstractTest;
import io.github.xuyao5.datakitsserver.vo.MyDocument;
import io.github.xuyao5.dkl.eskits.support.batch.BulkSupporter;
import io.github.xuyao5.dkl.eskits.util.MyDateUtils;
import org.junit.jupiter.api.Test;

public class BulkSupporterTest extends AbstractTest {

    @Test
    void testBulk() {
        new BulkSupporter().bulk(esClient, esClientConfig.getEsBulkThreads(), function -> {
            for (int i = 0; i < 1000000; i++) {
                MyDocument myDocument = MyDocument.of();
                myDocument.setUuid(String.valueOf(snowflake.nextId()));
                myDocument.setCashAmount(Long.MAX_VALUE);
                myDocument.setDesc("好");
                myDocument.setDateTime(MyDateUtils.now());
                function.apply(BulkSupporter.buildIndexRequest("test_index_5", myDocument));
            }
        });
    }
}
