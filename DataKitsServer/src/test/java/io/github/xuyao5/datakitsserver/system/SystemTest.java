package io.github.xuyao5.datakitsserver.system;

import io.github.xuyao5.datakitsserver.context.AbstractTest;
import io.github.xuyao5.datakitsserver.vo.MyFileDocument;
import io.github.xuyao5.datakitsserver.vo.MyTableDocument;
import io.github.xuyao5.dkl.eskits.context.boost.DisruptorBoost;
import io.github.xuyao5.dkl.eskits.context.translator.OneArgEventTranslator;
import io.github.xuyao5.dkl.eskits.helper.SnowflakeHelper;
import io.github.xuyao5.dkl.eskits.support.auxiliary.CatSupporter;
import io.github.xuyao5.dkl.eskits.support.v2.SearchingSupporter;
import io.github.xuyao5.dkl.eskits.util.*;
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
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import static io.github.xuyao5.dkl.eskits.util.DateUtilsPlus.STD_DATETIME_FORMAT;

@Slf4j
public class SystemTest extends AbstractTest {

    @Test
    void disruptorTest() {
        OneArgEventTranslator<MyTableDocument> translator = DisruptorBoost.<MyTableDocument>context().create().createOneArgEventTranslator(MyTableDocument::of,
                //异常
                (document, sequence) -> log.info("{}|{}|{}", Thread.currentThread().getName(), document.getId1(), sequence),
                //消费
                (document, sequence, endOfBatch) -> log.info("2|{}|{}", Thread.currentThread().getName(), document.getId1()));

        for (int i = 0; i < 50000; i++) {
            int finalI = i;
            translator.translate(((document, sequence, count) -> {
                document.setId1(finalI);
                log.info("1|{}|{}", Thread.currentThread().getName(), document.getId1());
            }), 0);
        }
    }

    @Test
    void splitDateTest() {
        Date fromDate = DateUtilsPlus.parse2Date("2022-07-02 00:00:11", STD_DATETIME_FORMAT);
        Date toDate = DateUtilsPlus.parse2Date("2022-07-02 00:00:22", STD_DATETIME_FORMAT);
        for (int shard = 1; shard <= 20; shard++) {
            Date[][] dateArray = DateUtilsPlus.dateSharding(fromDate, toDate, shard);
            System.out.println("开始时间：" + DateUtilsPlus.format2Date(fromDate, STD_DATETIME_FORMAT) + ",结束时间：" + DateUtilsPlus.format2Date(toDate, STD_DATETIME_FORMAT) + ",分片数：" + shard);
            for (Date[] dates : dateArray) {
                if (dates[0].before(dates[1]) || dates[0].equals(dates[1])) {
                    System.out.println(DateUtilsPlus.format2Date(dates[0], STD_DATETIME_FORMAT) + "  ->  " + DateUtilsPlus.format2Date(dates[1], STD_DATETIME_FORMAT) + ",差：" + DateUtilsPlus.secondsPeriod(dates[0], dates[1]) + "秒");
                }
            }
        }
    }

    @Test
    void compress() {
        FileUtilsPlus.getDecisionFiles("/Users/xuyao/Downloads", "^INT_DISRUPTOR_1W_T_20200710_00.txt$", path -> true).forEach(CompressUtilsPlus::createTarGz);
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

    @SneakyThrows
    @Test
    void snowflakeTest() {
        SnowflakeHelper snowflakeHelper = new SnowflakeHelper();

        final Set<Long> set = new ConcurrentSkipListSet<>();
        for (int i = 0; i < 20; i++) {
            ThreadPoolUtilsPlus.run(() -> {
                for (int ji = 0; ji < 10000000; ji++) {
                    long id = snowflakeHelper.nextId();
                    if (!set.contains(id)) {
                        set.add(id);
                    } else {
                        System.out.println("重复了");
                    }
                }
            });
        }

        System.out.println("结束：" + (set.size() == (20 * 10000000)));
    }

    @Test
    void searchTest() {
        SearchingSupporter searchingSupporter = SearchingSupporter.getInstance();
        List<MyFileDocument> result = searchingSupporter.search(elasticsearchClient, "FILE2ES_DISRUPTOR", MyFileDocument.class, 0, 2,
                //Query
                builder -> builder.matchAll(t -> t));
        System.out.println(result.size());
    }
}