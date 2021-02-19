package io.github.xuyao5.dkl.eskits.support;

import io.github.xuyao5.dkl.eskits.abstr.AbstractSupporter;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 19/02/21 22:18
 * @apiNote EsSnapshotLifecycleSupporter
 * @implNote EsSnapshotLifecycleSupporter
 */
@Slf4j
public final class SnapshotLifecycleSupporter extends AbstractSupporter {

    public SnapshotLifecycleSupporter(RestHighLevelClient client) {
        super(client);
    }
}
