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
        esClient.execute(client -> {
            System.out.println(new DocumentSupporter(client).index("test_index_1", "1", Pojo.of("测试")));
            return null;
        });
    }

    @Test
    void testGet() {
        esClient.execute(client -> {
            System.out.println(new DocumentSupporter(client).get("test_index_5", "2"));
            return null;
        });
    }

    @Test
    void testGetSource() {
        esClient.execute(client -> {
            System.out.println(new DocumentSupporter(client).getSource("test_index_5", "2"));
            return null;
        });
    }

    @Test
    void testExists() {
        esClient.execute(client -> {
            System.out.println(new DocumentSupporter(client).exists("test_index_5", "2"));
            return null;
        });
    }

    @Test
    void testDelete() {
        esClient.execute(client -> {
            System.out.println(new DocumentSupporter(client).delete("test_index_5", "2"));
            return null;
        });
    }

    @Test
    void testUpdate() {
        esClient.execute(client -> {
            System.out.println(new DocumentSupporter(client).update("test_index_5", "1", Pojo.of("测试更新：" + System.currentTimeMillis())));
            return null;
        });
    }

    @Test
    void testBulk() {
        esClient.execute(client -> {
            List<DocWriteRequest<?>> requestList = new ArrayList<>();
            requestList.add(BulkSupporter.genIndexRequest("test_index_5", "100", Pojo.of("测试更新：" + System.currentTimeMillis())));
            requestList.add(BulkSupporter.genIndexRequest("test_index_5", "101", Pojo.of("测试更新：" + System.currentTimeMillis())));
            System.out.println(new BulkSupporter(client).bulk(requestList));
            return null;
        });
    }

    @Test
    void testMultiGet() {
        esClient.execute(client -> {
            List<MultiGetRequest.Item> list = new ArrayList<>();
            list.add(new MultiGetRequest.Item("test_index_5", "3"));
            list.add(new MultiGetRequest.Item("test_index_5", "100"));
            list.add(new MultiGetRequest.Item("test_index_5", "101"));
            new MultiFetchSupporter(client).multiGet(list).iterator().forEachRemaining(multiGetItemResponse -> {
                System.out.println(multiGetItemResponse.getResponse());
            });
            return null;
        });
    }

    void testReindex() {
        esClient.execute(client -> {
            new ReindexSupporter(client).reindex("", 1);
            return null;
        });
    }
}
