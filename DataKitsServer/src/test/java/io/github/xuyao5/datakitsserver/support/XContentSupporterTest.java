package io.github.xuyao5.datakitsserver.support;

import io.github.xuyao5.datakitsserver.context.AbstractTest;
import io.github.xuyao5.datakitsserver.vo.AllTypeDocument;
import io.github.xuyao5.dkl.eskits.support.IndexSupporter;
import io.github.xuyao5.dkl.eskits.support.mapping.XContentSupporter;
import io.github.xuyao5.dkl.eskits.util.MyFieldUtils;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class XContentSupporterTest extends AbstractTest {

    @Test
    void testBuildMapping() {
        Map<String, Class<?>> declaredFieldsMap = MyFieldUtils.getDeclaredFieldsMap(AllTypeDocument.of());
        XContentSupporter.buildMapping(declaredFieldsMap);
        String index = "all_type_document";
        IndexSupporter indexSupporter = IndexSupporter.getInstance();
        if (indexSupporter.exists(esClient, index)) {
            indexSupporter.delete(esClient, index);
        }
        indexSupporter.create(esClient, index, 1, 1, XContentSupporter.buildMapping(declaredFieldsMap));
    }
}
