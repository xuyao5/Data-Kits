package io.github.xuyao5.datakitsserver.elasticsearch;

import io.github.xuyao5.datakitsserver.abstr.AbstractTest;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Executors;

public class BulkSupporterTest extends AbstractTest {

    @Test
    void testBulk() {
        Executors.newSingleThreadExecutor().execute(() -> esClient.execute(client -> {
            System.out.println(client);
            return null;
        }));


        Executors.newSingleThreadExecutor().execute(() -> esClient.execute(client -> {
            System.out.println(client);
            return null;
        }));
    }
}
