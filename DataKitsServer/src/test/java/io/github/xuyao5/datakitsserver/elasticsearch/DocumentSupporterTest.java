package io.github.xuyao5.datakitsserver.elasticsearch;

import io.github.xuyao5.datakitsserver.abstr.AbstractTest;
import io.github.xuyao5.dkl.eskits.support.DocumentSupporter;
import io.github.xuyao5.dkl.eskits.support.batch.BulkSupporter;
import io.github.xuyao5.dkl.eskits.support.batch.MultiFetchSupporter;
import io.github.xuyao5.dkl.eskits.support.batch.ReindexSupporter;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.get.MultiGetRequest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class DocumentSupporterTest extends AbstractTest {

    @Test
    void testIndex() {
        esClient.invokeConsumer(client -> {
            System.out.println(new DocumentSupporter(client).index("file2es_disruptor_1", "1", Pojo.of("测试")));
        });
    }

    @Test
    void testGet() {
        esClient.invokeConsumer(client -> {
            System.out.println(new DocumentSupporter(client).get("TEST-INDEX", "2"));
        });
    }

    @Test
    void testGetSource() {
        esClient.invokeConsumer(client -> {
            System.out.println(new DocumentSupporter(client).getSource("TEST-INDEX", "2"));
        });
    }

    @Test
    void testExists() {
        esClient.invokeConsumer(client -> {
            System.out.println(new DocumentSupporter(client).exists("TEST-INDEX", "2"));
        });
    }

    @Test
    void testDelete() {
        esClient.invokeConsumer(client -> {
            System.out.println(new DocumentSupporter(client).delete("TEST-INDEX", "2"));
        });
    }

    @Test
    void testUpdate() {
        esClient.invokeConsumer(client -> {
            System.out.println(new DocumentSupporter(client).update("TEST-INDEX", "1", Pojo.of("测试更新：" + System.currentTimeMillis())));
        });
    }

    @Test
    void testBulk() {
        esClient.invokeConsumer(client -> {
            List<DocWriteRequest<?>> requestList = new ArrayList<>();
            requestList.add(BulkSupporter.buildIndexRequest("TEST-INDEX", Pojo.of("测试更新：" + System.currentTimeMillis())));
            requestList.add(BulkSupporter.buildIndexRequest("TEST-INDEX", Pojo.of("测试更新：" + System.currentTimeMillis())));
            System.out.println(new BulkSupporter(client, esClientConfig.getEsBulkThreads()).bulk(requestList));
        });
    }

    @Test
    void testMultiGet() {
        esClient.invokeConsumer(client -> {
            List<MultiGetRequest.Item> list = new ArrayList<>();
            list.add(new MultiGetRequest.Item("TEST-INDEX", "3"));
            list.add(new MultiGetRequest.Item("TEST-INDEX", "100"));
            list.add(new MultiGetRequest.Item("TEST-INDEX", "101"));
            new MultiFetchSupporter(client).multiGet(list).iterator().forEachRemaining(multiGetItemResponse -> {
                System.out.println(multiGetItemResponse.getResponse());
            });
        });
    }

    void testReindex() {
        esClient.invokeConsumer(client -> {
            new ReindexSupporter(client).reindex("", 1);
        });
    }
}
