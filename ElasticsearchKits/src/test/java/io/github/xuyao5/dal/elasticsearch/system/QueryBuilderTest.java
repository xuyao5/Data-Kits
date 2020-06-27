package io.github.xuyao5.dal.elasticsearch.system;

import io.github.xuyao5.dal.elasticsearch.abstr.AbstractTest;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.jupiter.api.Test;

public class QueryBuilderTest extends AbstractTest {

    @Test
    void testQueryBuilder() {
        MatchAllQueryBuilder queryBuilder = QueryBuilders.matchAllQuery();
        System.out.println(queryBuilder);
    }
}
