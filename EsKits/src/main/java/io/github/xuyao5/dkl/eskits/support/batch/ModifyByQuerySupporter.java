package io.github.xuyao5.dkl.eskits.support.batch;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;

import java.util.Map;

import static org.elasticsearch.client.RequestOptions.DEFAULT;
import static org.elasticsearch.index.reindex.AbstractBulkByScrollRequest.AUTO_SLICES;
import static org.elasticsearch.index.reindex.AbstractBulkByScrollRequest.DEFAULT_SCROLL_TIMEOUT;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 19/02/21 21:34
 * @apiNote ModifyByQuery
 * @implNote ModifyByQuery
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ModifyByQuerySupporter {

    public static ModifyByQuerySupporter getInstance() {
        return ModifyByQuerySupporter.SingletonHolder.INSTANCE;
    }

    /**
     * Update By Query API
     */
    @SneakyThrows
    public BulkByScrollResponse updateByQuery(@NonNull RestHighLevelClient client, @NonNull QueryBuilder queryBuilder, @NonNull String code, @NonNull Map<String, Object> params, int scrollSize, @NonNull String... indices) {
        UpdateByQueryRequest request = new UpdateByQueryRequest(indices)
                .setQuery(queryBuilder)
                .setBatchSize(scrollSize)
                .setSlices(AUTO_SLICES)
                .setScroll(DEFAULT_SCROLL_TIMEOUT)
                .setScript(new Script(ScriptType.INLINE, "painless", code, params));
        request.setConflicts("proceed");
        return client.updateByQuery(request, DEFAULT);
    }

    /**
     * Delete By Query API
     */
    @SneakyThrows
    public BulkByScrollResponse deleteByQuery(@NonNull RestHighLevelClient client, @NonNull QueryBuilder queryBuilder, int scrollSize, @NonNull String... indices) {
        DeleteByQueryRequest request = new DeleteByQueryRequest(indices)
                .setQuery(queryBuilder)
                .setBatchSize(scrollSize)
                .setSlices(AUTO_SLICES)
                .setScroll(DEFAULT_SCROLL_TIMEOUT);
        request.setConflicts("proceed");
        return client.deleteByQuery(request, DEFAULT);
    }

    private static class SingletonHolder {
        private static final ModifyByQuerySupporter INSTANCE = new ModifyByQuerySupporter();
    }
}