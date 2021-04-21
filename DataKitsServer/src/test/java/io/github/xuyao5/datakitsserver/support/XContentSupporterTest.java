package io.github.xuyao5.datakitsserver.support;

import io.github.xuyao5.datakitsserver.context.AbstractTest;
import io.github.xuyao5.datakitsserver.vo.AllTypeDocument;
import io.github.xuyao5.dkl.eskits.support.general.IndexSupporter;
import io.github.xuyao5.dkl.eskits.support.mapping.XContentSupporter;
import org.junit.jupiter.api.Test;

public class XContentSupporterTest extends AbstractTest {

    @Test
    void buildMapping() {
        String index = "all_type_document";
        IndexSupporter indexSupporter = IndexSupporter.getInstance();
        if (indexSupporter.exists(esClient, index)) {
            indexSupporter.delete(esClient, index);
        }
        indexSupporter.create(esClient, index, 1, 0, new String[]{"dateTag", "serialNo", "createDate", "modifyDate"}, new String[]{"desc", "desc", "desc", "desc"}, XContentSupporter.buildMapping(AllTypeDocument.of()));
    }
}
