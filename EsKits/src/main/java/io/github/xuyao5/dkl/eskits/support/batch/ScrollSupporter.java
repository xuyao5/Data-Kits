package io.github.xuyao5.dkl.eskits.support.batch;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.*;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.function.Consumer;

import static org.elasticsearch.client.RequestOptions.DEFAULT;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 19/02/21 18:46
 * @apiNote ScrollSupporter
 * @implNote ScrollSupporter
 */
@Slf4j
public final class ScrollSupporter {

    private final int KEEP_ALIVE;

    public ScrollSupporter(int keepAlive) {
        KEEP_ALIVE = keepAlive;
    }

    /**
     * Search Scroll API
     */
    @SneakyThrows
    public ClearScrollResponse scroll(@NotNull RestHighLevelClient client, Consumer<SearchHit[]> consumer, @NotNull QueryBuilder queryBuilder, @NotNull String... indices) {
        final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(KEEP_ALIVE));
        SearchResponse searchResponse = client.search(new SearchRequest(indices).scroll(scroll).source(new SearchSourceBuilder().query(queryBuilder).size(1024)), DEFAULT);
        String scrollId = searchResponse.getScrollId();
        SearchHit[] searchHits = searchResponse.getHits().getHits();

        while (Objects.nonNull(searchHits) && searchHits.length > 0) {
            consumer.accept(searchHits);
            searchResponse = client.scroll(new SearchScrollRequest(scrollId).scroll(scroll), DEFAULT);
            scrollId = searchResponse.getScrollId();
            searchHits = searchResponse.getHits().getHits();
        }

        ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
        clearScrollRequest.addScrollId(scrollId);
        return client.clearScroll(clearScrollRequest, DEFAULT);
    }
}
