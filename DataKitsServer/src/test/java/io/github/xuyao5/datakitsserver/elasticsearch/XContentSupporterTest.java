package io.github.xuyao5.datakitsserver.elasticsearch;

import io.github.xuyao5.datakitsserver.abstr.AbstractTest;
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
        IndexSupporter indexSupporter = new IndexSupporter(esClient);
        if (indexSupporter.exists(index)) {
            indexSupporter.delete(index);
        }
        indexSupporter.create(index, 1, XContentSupporter.buildMapping(declaredFieldsMap));
    }
}
