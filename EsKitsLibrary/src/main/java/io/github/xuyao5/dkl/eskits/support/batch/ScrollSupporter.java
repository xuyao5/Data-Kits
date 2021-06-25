package io.github.xuyao5.dkl.eskits.support.batch;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.*;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.Objects;
import java.util.function.Consumer;

import static org.elasticsearch.client.RequestOptions.DEFAULT;
import static org.elasticsearch.index.reindex.AbstractBulkByScrollRequest.DEFAULT_SCROLL_TIMEOUT;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 19/02/21 18:46
 * @apiNote ScrollSupporter
 * @implNote ScrollSupporter
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ScrollSupporter {

    public static ScrollSupporter getInstance() {
        return ScrollSupporter.SingletonHolder.INSTANCE;
    }

    /**
     * Search Scroll API
     */
    @SneakyThrows
    public void scroll(@NonNull RestHighLevelClient client, Consumer<SearchHit[]> consumer, @NonNull QueryBuilder queryBuilder, int scrollSize, @NonNull String... indices) {
        final Scroll scroll = new Scroll(DEFAULT_SCROLL_TIMEOUT);
        SearchResponse searchResponse = client.search(new SearchRequest(indices).scroll(scroll).source(SearchSourceBuilder.searchSource().query(queryBuilder).size(scrollSize)), DEFAULT);
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
        ClearScrollResponse response = client.clearScroll(clearScrollRequest, DEFAULT);
        log.info("ClearScroll succeeded is {}, released {} search contexts", response.isSucceeded(), response.getNumFreed());
    }

    private static class SingletonHolder {
        private static final ScrollSupporter INSTANCE = new ScrollSupporter();
    }
}
