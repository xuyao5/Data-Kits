package io.github.xuyao5.dal.elasticsearch.system;

import io.github.xuyao5.dal.elasticsearch.abstr.AbstractTest;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;

import static org.springframework.util.ResourceUtils.CLASSPATH_URL_PREFIX;

public class QueryBuilderTest extends AbstractTest {

    @SneakyThrows
    @Test
    void testQueryBuilder() {
        File file = ResourceUtils.getFile(CLASSPATH_URL_PREFIX + "script/search/" + "MatchAll.json");
        String script = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        System.out.println(script);

        MatchAllQueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
        System.out.println(queryBuilder);
    }
}
