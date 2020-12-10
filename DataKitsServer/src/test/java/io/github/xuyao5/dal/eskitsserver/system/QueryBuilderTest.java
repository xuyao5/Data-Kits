package io.github.xuyao5.dal.eskitsserver.system;

import io.github.xuyao5.dal.eskitsserver.abstr.AbstractTest;
import lombok.SneakyThrows;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.sort.ScriptSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;

public class QueryBuilderTest extends AbstractTest {

    @SneakyThrows
    @Test
    void testQueryBuilder() {
        ScriptSortBuilder scriptSortBuilder = SortBuilders.scriptSort(new Script("Math.random()"), ScriptSortBuilder.ScriptSortType.NUMBER).order(SortOrder.DESC);
        System.out.println(scriptSortBuilder);
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.filter(QueryBuilders.prefixQuery("scc", "11"));
        boolQueryBuilder.filter(QueryBuilders.prefixQuery("scc", "92"));
        boolQueryBuilder.filter(QueryBuilders.prefixQuery("scc", "20"));
        boolQueryBuilder.filter(QueryBuilders.prefixQuery("scc", "23"));
        boolQueryBuilder.filter(QueryBuilders.rangeQuery("age").from(20).to(65));
        boolQueryBuilder.filter(QueryBuilders.termsQuery("org", "1", "2"));
        NestedQueryBuilder myPath = QueryBuilders
                .nestedQuery("myPath", boolQueryBuilder, ScoreMode.None);
        System.out.println(myPath);
    }
}
