package io.github.xuyao5.dkl.eskits.support.batch;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;

import javax.validation.constraints.NotNull;
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

    public static final ModifyByQuerySupporter getInstance() {
        return ModifyByQuerySupporter.SingletonHolder.INSTANCE;
    }

    /**
     * Update By Query API
     */
    @SneakyThrows
    public BulkByScrollResponse updateByQuery(@NotNull RestHighLevelClient client, @NotNull QueryBuilder queryBuilder, @NotNull String code, @NotNull Map<String, Object> params, int scrollSize, @NotNull String... indices) {
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
    public BulkByScrollResponse deleteByQuery(@NotNull RestHighLevelClient client, @NotNull QueryBuilder queryBuilder, int scrollSize, @NotNull String... indices) {
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