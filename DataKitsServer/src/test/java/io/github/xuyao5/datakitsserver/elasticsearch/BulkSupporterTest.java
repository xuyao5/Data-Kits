package io.github.xuyao5.datakitsserver.elasticsearch;

import io.github.xuyao5.datakitsserver.abstr.AbstractTest;
import io.github.xuyao5.dkl.eskits.client.EsClient;
import org.junit.jupiter.api.Test;

public class BulkSupporterTest extends AbstractTest {

    @Test
    void testBulk() {
        new EsClient(new String[]{"localhost:9200"}, "", "", 10).execute(client -> {
            System.out.println(client);
            return null;
        });

        new EsClient(new String[]{"127.0.0.1:9200"}, "", "", 10).execute(client -> {
            System.out.println(client);
            return null;
        });
    }
}
