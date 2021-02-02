package io.github.xuyao5.dkl.eskits.abstr;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;

import javax.validation.constraints.NotNull;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 12/01/21 18:28
 * @apiNote AbstractSupporter
 * @implNote AbstractSupporter
 */
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractSupporter {

    protected static final int DEFAULT_SHARDS_SLICES = 10;
    protected static final int DEFAULT_SCROLL_MINUTES = 10;

    protected final RestHighLevelClient restHighLevelClient;

    /**
     * 记录执行时间的标准化日志
     *
     * @param method          执行方法
     * @param startTimeMillis 开始时间
     */
    protected void logElapsed(@NotNull String method, long startTimeMillis) {
        log.info("[{}]{}ms", method, System.currentTimeMillis() - startTimeMillis);
    }
}
