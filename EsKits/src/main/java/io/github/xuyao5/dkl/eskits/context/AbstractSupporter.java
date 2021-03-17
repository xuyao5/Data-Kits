package io.github.xuyao5.dkl.eskits.context;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 12/01/21 18:28
 * @apiNote AbstractSupporter
 * @implNote AbstractSupporter
 */
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractSupporter {

    protected static final int RING_BUFFER_SIZE = 1 << 10;

    protected final RestHighLevelClient client;
}
