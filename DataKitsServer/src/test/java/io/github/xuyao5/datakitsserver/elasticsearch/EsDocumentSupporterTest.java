package io.github.xuyao5.datakitsserver.elasticsearch;

import io.github.xuyao5.datakitsserver.abstr.AbstractTest;
import io.github.xuyao5.dkl.eskits.support.EsDocumentSupporter;
import org.junit.jupiter.api.Test;

public class EsDocumentSupporterTest extends AbstractTest {

    @Test
    void testIndex() {
        getEsClient().execute(client -> {
            EsDocumentSupporter esDocumentSupporter = new EsDocumentSupporter(client);

            return null;
        });
    }
}
