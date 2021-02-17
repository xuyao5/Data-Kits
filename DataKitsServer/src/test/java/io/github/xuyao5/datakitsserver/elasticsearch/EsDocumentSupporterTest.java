package io.github.xuyao5.datakitsserver.elasticsearch;

import io.github.xuyao5.datakitsserver.abstr.AbstractTest;
import io.github.xuyao5.dkl.eskits.support.EsDocumentSupporter;
import org.junit.jupiter.api.Test;

public class EsDocumentSupporterTest extends AbstractTest {

    @Test
    void testIndex() {
        getEsClient().execute(client -> {
            System.out.println(new EsDocumentSupporter(client).index("test_index_1", "1", Pojo.of("测试")));
            return null;
        });
    }
}
