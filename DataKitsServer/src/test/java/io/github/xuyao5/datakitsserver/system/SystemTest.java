package io.github.xuyao5.datakitsserver.system;

import io.github.xuyao5.datakitsserver.context.AbstractTest;
import io.github.xuyao5.dkl.eskits.helper.HttpFsHelper;
import io.github.xuyao5.dkl.eskits.schema.cat.Indices4Cat;
import io.github.xuyao5.dkl.eskits.support.boost.CatSupporter;
import io.github.xuyao5.dkl.eskits.util.CompressUtilsPlus;
import io.github.xuyao5.dkl.eskits.util.FileUtilsPlus;
import lombok.SneakyThrows;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.ScriptSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SystemTest extends AbstractTest {

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

    @Test
    void test1() {
        String querySource = SearchSourceBuilder.searchSource()
                .query(QueryBuilders.matchAllQuery())
                .from(0)
                .size(1000)
                .sort(SortBuilders.fieldSort("id").order(SortOrder.ASC))
                .toString();
        System.out.println(querySource);
    }

    @Test
    void compress() {
        FileUtilsPlus.getDecisionFiles("/Users/xuyao/Downloads", "^INT_DISRUPTOR_1W_T_20200710_00.txt$", path -> true).forEach(CompressUtilsPlus::createTarGz);
    }

    @Test
    void httpFs() {
        HttpFsHelper httpFsHelper = new HttpFsHelper("localhost", 14000, "root");
//        httpFsHelper.mkdirs("/dir1");
//        System.out.println(httpFsHelper.listStatus("/dir1"));
//        System.out.println(httpFsHelper.getContentSummary("/dir1"));
//        System.out.println(httpFsHelper.getFileStatus("/dir1/INT_DISRUPTOR_1K_T_20200711_00.txt"));
//        System.out.println(httpFsHelper.getFileChecksum("/dir1/INT_DISRUPTOR_1K_T_20200711_00.txt"));
//        System.out.println(httpFsHelper.getContentSummary());
//        System.out.println(httpFsHelper.getFileStatus());
//        httpFsHelper.create("/dir1/INT_DISRUPTOR_1K_T_20200711_00.txt");
        int[] compute = httpFsHelper.compute(864682, 64000);
        for (int i = 0; i < compute.length; i++) {
            System.out.println(compute[i]);
        }

        httpFsHelper.open("/dir1/INT_DISRUPTOR_1K_T_20200711_00.txt", 0);

    }

    @SneakyThrows
    @Test
    void catSupporter() {
        List<Indices4Cat> catIndices = CatSupporter.getInstance().getCatIndices(esClient, "file2es_disruptor_20200711");
        catIndices.forEach(System.out::println);
    }
}