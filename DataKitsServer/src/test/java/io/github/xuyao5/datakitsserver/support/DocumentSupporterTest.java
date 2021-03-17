package io.github.xuyao5.datakitsserver.support;

import io.github.xuyao5.datakitsserver.context.AbstractTest;
import io.github.xuyao5.datakitsserver.vo.MyDocument;
import io.github.xuyao5.dkl.eskits.support.DocumentSupporter;
import io.github.xuyao5.dkl.eskits.support.batch.BulkSupporter;
import io.github.xuyao5.dkl.eskits.support.batch.MultiFetchSupporter;
import io.github.xuyao5.dkl.eskits.support.batch.ReindexSupporter;
import io.github.xuyao5.dkl.eskits.util.MyDateUtils;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.get.MultiGetRequest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class DocumentSupporterTest extends AbstractTest {

    @Test
    void testIndex() {
        MyDocument myDocument = MyDocument.of();
        myDocument.setUuid(String.valueOf(snowflake.nextId()));
        myDocument.setCashAmount(Long.MAX_VALUE);
        myDocument.setDesc("好");
        myDocument.setDateTime(MyDateUtils.now());
        System.out.println(new DocumentSupporter().index(esClient, "file2es_disruptor_1", "1", myDocument));
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
        MyDocument myDocument = MyDocument.of();
        myDocument.setUuid(String.valueOf(snowflake.nextId()));
        myDocument.setCashAmount(Long.MAX_VALUE);
        myDocument.setDesc("好");
        myDocument.setDateTime(MyDateUtils.now());
        System.out.println(new DocumentSupporter().update(esClient, "TEST-INDEX", "1", myDocument));
    }

    @Test
    void testBulk() {
        MyDocument myDocument = MyDocument.of();
        myDocument.setUuid(String.valueOf(snowflake.nextId()));
        myDocument.setCashAmount(Long.MAX_VALUE);
        myDocument.setDesc("好");
        myDocument.setDateTime(MyDateUtils.now());
        List<DocWriteRequest<?>> requestList = new ArrayList<>();
        requestList.add(BulkSupporter.buildIndexRequest("TEST-INDEX", myDocument));
        requestList.add(BulkSupporter.buildIndexRequest("TEST-INDEX", myDocument));
        System.out.println(new BulkSupporter().bulk(esClient, requestList));
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
