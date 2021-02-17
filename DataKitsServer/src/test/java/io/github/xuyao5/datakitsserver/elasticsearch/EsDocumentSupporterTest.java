package io.github.xuyao5.datakitsserver.elasticsearch;

import io.github.xuyao5.datakitsserver.abstr.AbstractTest;
import io.github.xuyao5.dkl.eskits.support.EsDocumentSupporter;
import io.github.xuyao5.dkl.eskits.support.bulk.BulkSupporter;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.get.MultiGetRequest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class EsDocumentSupporterTest extends AbstractTest {

    @Test
    void testIndex() {
        getEsClient().execute(client -> {
            System.out.println(new EsDocumentSupporter(client).index("test_index_1", "1", Pojo.of("测试")));
            return null;
        });
    }

    @Test
    void testGet() {
        getEsClient().execute(client -> {
            System.out.println(new EsDocumentSupporter(client).get("test_index_5", "2"));
            return null;
        });
    }

    @Test
    void testGetSource() {
        getEsClient().execute(client -> {
            System.out.println(new EsDocumentSupporter(client).getSource("test_index_5", "2"));
            return null;
        });
    }

    @Test
    void testExists() {
        getEsClient().execute(client -> {
            System.out.println(new EsDocumentSupporter(client).exists("test_index_5", "2"));
            return null;
        });
    }

    @Test
    void testDelete() {
        getEsClient().execute(client -> {
            System.out.println(new EsDocumentSupporter(client).delete("test_index_5", "2"));
            return null;
        });
    }

    @Test
    void testUpdate() {
        getEsClient().execute(client -> {
            System.out.println(new EsDocumentSupporter(client).update("test_index_5", "1", Pojo.of("测试更新：" + System.currentTimeMillis())));
            return null;
        });
    }

    @Test
    void testBulk() {
        getEsClient().execute(client -> {
            List<DocWriteRequest<?>> requestList = new ArrayList<>();
            requestList.add(BulkSupporter.genIndexRequest("test_index_5", "100", Pojo.of("测试更新：" + System.currentTimeMillis())));
            requestList.add(BulkSupporter.genIndexRequest("test_index_5", "101", Pojo.of("测试更新：" + System.currentTimeMillis())));
            System.out.println(new EsDocumentSupporter(client).bulk(requestList));
            return null;
        });
    }

    @Test
    void testMultiGet() {
        getEsClient().execute(client -> {
            List<MultiGetRequest.Item> list = new ArrayList<>();
            list.add(new MultiGetRequest.Item("test_index_5", "3"));
            list.add(new MultiGetRequest.Item("test_index_5", "100"));
            list.add(new MultiGetRequest.Item("test_index_5", "101"));
            new EsDocumentSupporter(client).multiGet(list).iterator().forEachRemaining(multiGetItemResponse -> {
                System.out.println(multiGetItemResponse.getResponse());
            });
            return null;
        });
    }

    void testReindex() {
        getEsClient().execute(client -> {
            new EsDocumentSupporter(client).reindex("", 1);
            return null;
        });
    }

    void testUpdateByQuery() {
        getEsClient().execute(client -> {
            new EsDocumentSupporter(client).updateByQuery(null, 1);
            return null;
        });
    }

    void testDeleteByQuery() {
        getEsClient().execute(client -> {
            new EsDocumentSupporter(client).deleteByQuery(null, 1);
            return null;
        });
    }
}
