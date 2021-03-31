package io.github.xuyao5.datakitsserver.support;

import io.github.xuyao5.datakitsserver.context.AbstractTest;
import io.github.xuyao5.datakitsserver.vo.MyDocument;
import io.github.xuyao5.dkl.eskits.support.batch.BulkSupporter;
import io.github.xuyao5.dkl.eskits.util.MyDateUtils;
import org.junit.jupiter.api.Test;

public class BulkSupporterTest extends AbstractTest {

    @Test
    void testBulk() {
        BulkSupporter.getInstance().bulk(esClient, esClientConfig.getEsBulkThreads(), function -> {
            for (int i = 0; i < 1000000; i++) {
                MyDocument myDocument = MyDocument.of();
                myDocument.setUuid(String.valueOf(snowflake.nextId()));
                myDocument.setCashAmount(Long.MAX_VALUE);
                myDocument.setDesc("好");
                myDocument.setDateTime(MyDateUtils.now());
                MyDocument.MyTags myTags = MyDocument.MyTags.of();
                myTags.setTag1("");
                myTags.setTag2(MyDateUtils.now());
                myTags.setTag3(0);
                myDocument.setTags(myTags);
                function.apply(BulkSupporter.buildIndexRequest("test_index_5", myDocument));
            }
        });
    }
}
