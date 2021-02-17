package io.github.xuyao5.datakitsserver.elasticsearch;

import io.github.xuyao5.datakitsserver.abstr.AbstractTest;
import io.github.xuyao5.dkl.eskits.support.EsDocumentSupporter;
import org.elasticsearch.action.index.IndexResponse;
import org.junit.jupiter.api.Test;

public class EsDocumentSupporterTest extends AbstractTest {

    @Test
    void testIndex() {
        getEsClient().execute(client -> {
            IndexResponse response = new EsDocumentSupporter(client).index("", "", Pojo.of("测试"));
            System.out.println(response);

            return null;
        });
    }
}
