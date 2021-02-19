package io.github.xuyao5.dkl.eskits.support.batch;

import io.github.xuyao5.dkl.eskits.abstr.AbstractSupporter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.ClearScrollRequest;
import org.elasticsearch.action.search.ClearScrollResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;

import static org.elasticsearch.client.RequestOptions.DEFAULT;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 19/02/21 18:46
 * @apiNote TODO 这里输入文件说明
 * @implNote TODO 这里输入实现说明
 */
@Slf4j
public final class ScrollSupporter extends AbstractSupporter {

    public ScrollSupporter(RestHighLevelClient client) {
        super(client);
    }

    /**
     * Search Scroll API
     */
    @SneakyThrows
    public SearchResponse scroll() {
        SearchScrollRequest request = new SearchScrollRequest();
        request.scroll(TimeValue.timeValueSeconds(30));
        return client.scroll(request, DEFAULT);
    }


    /**
     * Clear Scroll API
     */
    @SneakyThrows
    public ClearScrollResponse clearScroll() {
        ClearScrollRequest request = new ClearScrollRequest();
        request.addScrollId("scrollId");
        return client.clearScroll(request, DEFAULT);
    }
}
