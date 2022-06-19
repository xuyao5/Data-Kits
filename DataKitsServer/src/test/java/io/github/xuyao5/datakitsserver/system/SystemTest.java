package io.github.xuyao5.datakitsserver.system;

import io.github.xuyao5.datakitsserver.context.AbstractTest;
import io.github.xuyao5.datakitsserver.vo.MyFileDocument;
import io.github.xuyao5.dkl.eskits.context.DisruptorBoost;
import io.github.xuyao5.dkl.eskits.schema.standard.StandardFileLine;
import io.github.xuyao5.dkl.eskits.service.handler.File2EsEventHandler;
import io.github.xuyao5.dkl.eskits.support.boost.CatSupporter;
import io.github.xuyao5.dkl.eskits.util.CompressUtilsPlus;
import io.github.xuyao5.dkl.eskits.util.DateUtilsPlus;
import io.github.xuyao5.dkl.eskits.util.FileUtilsPlus;
import io.github.xuyao5.dkl.eskits.util.GsonUtilsPlus;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.GeoExecType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.Script;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.ScriptSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class SystemTest extends AbstractTest {

    @Test
    void eventTest() {
        DisruptorBoost.<StandardFileLine>context().create().processZeroArgEvent(StandardFileLine::of, translator -> {
            for (int i = 0; i < 50; i++) {
                translator.translate((standardFileLine, l) -> {
                    standardFileLine.setLineNo(System.currentTimeMillis());
                    standardFileLine.setLineRecord(DateUtilsPlus.now().toString());
                });
            }
        }, (standardFileLine, value) -> {
        }, true, new File2EsEventHandler(20));
    }

    @Test
    void compress() {
        FileUtilsPlus.getDecisionFiles("/Users/xuyao/Downloads", "^INT_DISRUPTOR_1W_T_20200710_00.txt$", path -> true).forEach(CompressUtilsPlus::createTarGz);
    }

    @Test
    void httpFs() {
//        HttpFsHelper httpFsHelper = new HttpFsHelper("localhost", 14000, "root");
//        httpFsHelper.mkdirs("/dir1");
//        System.out.println(httpFsHelper.listStatus("/dir1"));
//        System.out.println(httpFsHelper.getContentSummary("/dir1"));
//        System.out.println(httpFsHelper.getFileStatus("/dir1/INT_DISRUPTOR_1K_T_20200711_00.txt"));
//        System.out.println(httpFsHelper.getFileChecksum("/dir1/INT_DISRUPTOR_1K_T_20200711_00.txt"));
//        System.out.println(httpFsHelper.getContentSummary());
//        System.out.println(httpFsHelper.getFileStatus());
//        httpFsHelper.create("/dir1/INT_DISRUPTOR_1K_T_20200711_00.txt");
//        int[] compute = httpFsHelper.compute(864682, 64000);
//        for (int i = 0; i < compute.length; i++) {
//            System.out.println(compute[i]);
//        }
//
//        httpFsHelper.open("/dir1/INT_DISRUPTOR_1K_T_20200711_00.txt", 0);
    }

    @SneakyThrows
    @Test
    void catSupporter() {
        CatSupporter catSupporter = CatSupporter.getInstance();
//        System.out.println(catSupporter.getCatAllocation(esClient));
//        System.out.println(catSupporter.getCatShards(esClient));
//        System.out.println(catSupporter.getCatShards(esClient, "file2es_disruptor_*"));
//        System.out.println(catSupporter.getCatMaster(esClient));
//        System.out.println(catSupporter.getCatNodes(esClient));
//        System.out.println(catSupporter.getCatTasks(esClient));
        System.out.println(catSupporter.getCatIndices(esClient));
        System.out.println(catSupporter.getCatIndices(esClient, "file2es_disruptor_*"));
//        System.out.println(catSupporter.getCatSegments(esClient));
//        System.out.println(catSupporter.getCatSegments(esClient, "file2es_disruptor_*"));
//        System.out.println(catSupporter.getCatCount(esClient, "file2es_disruptor_*"));
//        System.out.println(catSupporter.getCatRecovery(esClient, "file2es_disruptor_*"));
//        System.out.println(catSupporter.getCatHealth(esClient));
//        System.out.println(catSupporter.getCatAliases(esClient));
//        System.out.println(catSupporter.getCatAliases(esClient, "FILE2ES_DISRUPTOR"));
//        System.out.println(catSupporter.getCatThreadPool(esClient));
//        System.out.println(catSupporter.getCatThreadPool(esClient, "flush"));
//        System.out.println(catSupporter.getCatPlugins(esClient));
//        System.out.println(catSupporter.getCatFieldData(esClient));
//        System.out.println(catSupporter.getCatFieldData(esClient, ""));
//        System.out.println(catSupporter.getCatNodeAttrs(esClient));
//        System.out.println(catSupporter.getCatTemplates(esClient));
    }

    @Test
    void gsonTest() {
        MyFileDocument document = MyFileDocument.of();
        document.setDateTime2(DateUtilsPlus.now());
        String json = GsonUtilsPlus.obj2Json(document);
        System.out.println(json);
        System.out.println(GsonUtilsPlus.isJsonString(json));
        Serializable serializable = GsonUtilsPlus.json2Obj(json, ConcurrentHashMap.class);
        System.out.println(serializable);
    }

    @SneakyThrows
    @Test
    void testQueryBuilder() {
        SortBuilder scriptSortBuilder = SortBuilders.scriptSort(new Script("Math.random()"), ScriptSortBuilder.ScriptSortType.NUMBER).order(SortOrder.DESC);
        System.out.println(scriptSortBuilder);

        QueryBuilder nestedQueryBuilder = QueryBuilders.nestedQuery("myPath", QueryBuilders.boolQuery().filter(QueryBuilders.prefixQuery("scc", "11")).filter(QueryBuilders.prefixQuery("scc", "92")).filter(QueryBuilders.prefixQuery("scc", "20")).filter(QueryBuilders.prefixQuery("scc", "23")).filter(QueryBuilders.rangeQuery("age").from(20).to(65)).filter(QueryBuilders.termsQuery("org", "1", "2")), ScoreMode.None);
        System.out.println(nestedQueryBuilder);

        QueryBuilder geoQueryBuilder = QueryBuilders.boolQuery().must(QueryBuilders.matchAllQuery()).filter(QueryBuilders.geoBoundingBoxQuery("location").setCorners(10, 12, 8, 16).type(GeoExecType.INDEXED));
        System.out.println(geoQueryBuilder);

        SearchSourceBuilder searchSourceBuilder = SearchSourceBuilder.searchSource().query(QueryBuilders.matchAllQuery()).from(0).size(1000).sort(SortBuilders.fieldSort("id").order(SortOrder.ASC));
        System.out.println(searchSourceBuilder);
    }
}