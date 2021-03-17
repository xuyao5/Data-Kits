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
        System.out.println(new DocumentSupporter().index(esClient, "file2es_disruptor_1", "1", Pojo.of("测试")));
    }

    @Test
    void testGet() {
        System.out.println(new DocumentSupporter().get(esClient, "TEST-INDEX", "2"));
    }

    @Test
    void testGetSource() {
        System.out.println(new DocumentSupporter().getSource(esClient, "TEST-INDEX", "2"));
    }

    @Test
    void testExists() {
        System.out.println(new DocumentSupporter().exists(esClient, "TEST-INDEX", "2"));
    }

    @Test
    void testDelete() {
        System.out.println(new DocumentSupporter().delete(esClient, "TEST-INDEX", "2"));
    }

    @Test
    void testUpdate() {
        System.out.println(new DocumentSupporter().update(esClient, "TEST-INDEX", "1", Pojo.of("测试更新：" + System.currentTimeMillis())));
    }

    @Test
    void testBulk() {
        List<DocWriteRequest<?>> requestList = new ArrayList<>();
        requestList.add(BulkSupporter.buildIndexRequest("TEST-INDEX", Pojo.of("测试更新：" + System.currentTimeMillis())));
        requestList.add(BulkSupporter.buildIndexRequest("TEST-INDEX", Pojo.of("测试更新：" + System.currentTimeMillis())));
        System.out.println(new BulkSupporter(esClientConfig.getEsBulkThreads()).bulk(esClient, requestList));
    }

    @Test
    void testMultiGet() {
        List<MultiGetRequest.Item> list = new ArrayList<>();
        list.add(new MultiGetRequest.Item("TEST-INDEX", "3"));
        list.add(new MultiGetRequest.Item("TEST-INDEX", "100"));
        list.add(new MultiGetRequest.Item("TEST-INDEX", "101"));
        new MultiFetchSupporter().multiGet(esClient, list).iterator().forEachRemaining(multiGetItemResponse -> {
            System.out.println(multiGetItemResponse.getResponse());
        });
    }

    void testReindex() {
        new ReindexSupporter().reindex(esClient, "", 1);
    }
}
