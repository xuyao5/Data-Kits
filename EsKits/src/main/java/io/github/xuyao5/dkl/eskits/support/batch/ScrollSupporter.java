package io.github.xuyao5.dkl.eskits.support.batch;

import io.github.xuyao5.dkl.eskits.abstr.AbstractSupporter;
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
 * @apiNote TODO 这里输入文件说明
 * @implNote TODO 这里输入实现说明
 */
@Slf4j
public final class ScrollSupporter extends AbstractSupporter {

    private final int KEEP_ALIVE;

    public ScrollSupporter(RestHighLevelClient client, int keepAlive) {
        super(client);
        KEEP_ALIVE = keepAlive;
    }

    public ScrollSupporter(RestHighLevelClient client) {
        this(client, 1);
    }

    /**
     * Search Scroll API
     */
    @SneakyThrows
    public ClearScrollResponse scroll(Consumer<SearchHit[]> consumer, @NotNull QueryBuilder queryBuilder, int size, @NotNull String... indices) {
        final Scroll scroll = new Scroll(TimeValue.timeValueMinutes(KEEP_ALIVE));

        SearchResponse searchResponse = client.search(new SearchRequest(indices).source(new SearchSourceBuilder().query(queryBuilder).size(size)).scroll(scroll), DEFAULT);
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
