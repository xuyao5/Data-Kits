package io.github.xuyao5.dkl.eskits.support.batch;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.*;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import javax.validation.constraints.NotNull;
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

    public static final ScrollSupporter getInstance() {
        return ScrollSupporter.SingletonHolder.INSTANCE;
    }

    /**
     * Search Scroll API
     */
    @SneakyThrows
    public ClearScrollResponse scroll(@NotNull RestHighLevelClient client, Consumer<SearchHit[]> consumer, @NotNull QueryBuilder queryBuilder, int scrollSize, @NotNull String... indices) {
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
        return client.clearScroll(clearScrollRequest, DEFAULT);
    }

    private static class SingletonHolder {
        private static final ScrollSupporter INSTANCE = new ScrollSupporter();
    }
}
