package io.github.xuyao5.dkl.eskits.support.boost;

import io.github.xuyao5.dkl.eskits.support.general.SearchSupporter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.geometry.Circle;
import org.elasticsearch.index.query.GeoExecType;
import org.elasticsearch.index.query.QueryBuilders;

/**
 * @author Thomas.XU(xuyao)
 * @version 8/11/21 23:09
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GeoSupporter {

    public static GeoSupporter getInstance() {
        return GeoSupporter.SingletonHolder.INSTANCE;
    }

    public SearchResponse geoBoundingBoxQuery(@NonNull RestHighLevelClient client, @NonNull String index) {
        return SearchSupporter.getInstance().search(client, QueryBuilders.boolQuery()
                .must(QueryBuilders.matchAllQuery())
                .filter(QueryBuilders.geoBoundingBoxQuery("location")
                        .setCorners("")
                        .type(GeoExecType.INDEXED)
                        .ignoreUnmapped(false)), 0, 100, index);
    }

    public SearchResponse geoDistanceQuery(@NonNull RestHighLevelClient client, @NonNull String index) {
        return SearchSupporter.getInstance().search(client, QueryBuilders.boolQuery()
                .must(QueryBuilders.matchAllQuery())
                .filter(QueryBuilders.geoDistanceQuery("location")
                        .distance(100, DistanceUnit.KILOMETERS)
                        .ignoreUnmapped(false)), 0, 100, index);
    }

    public SearchResponse geoPolygonQuery(@NonNull RestHighLevelClient client, @NonNull String index) {
        return SearchSupporter.getInstance().search(client, QueryBuilders.boolQuery()
                .must(QueryBuilders.matchAllQuery())
                .filter(QueryBuilders.geoPolygonQuery("", null)
                        .ignoreUnmapped(false)), 0, 100, index);
    }

    @SneakyThrows
    public SearchResponse geoShapeQuery(@NonNull RestHighLevelClient client, @NonNull String index) {
        return SearchSupporter.getInstance().search(client, QueryBuilders.boolQuery()
                .must(QueryBuilders.matchAllQuery())
                .filter(QueryBuilders.geoShapeQuery("", Circle.EMPTY)
                        .ignoreUnmapped(false)), 0, 100, index);
    }

    private static class SingletonHolder {
        private static final GeoSupporter INSTANCE = new GeoSupporter();
    }
}