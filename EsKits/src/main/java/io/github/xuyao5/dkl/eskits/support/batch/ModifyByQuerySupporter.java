package io.github.xuyao5.dkl.eskits.support.batch;

import io.github.xuyao5.dkl.eskits.abstr.AbstractSupporter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
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

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 19/02/21 21:34
 * @apiNote ModifyByQuery
 * @implNote ModifyByQuery
 */
@Slf4j
public final class ModifyByQuerySupporter extends AbstractSupporter {

    private final int KEEP_ALIVE;

    public ModifyByQuerySupporter(RestHighLevelClient client, int keepAlive) {
        super(client);
        KEEP_ALIVE = keepAlive;
    }

    public ModifyByQuerySupporter(RestHighLevelClient client) {
        this(client, 10);
    }

    /**
     * Update By Query API
     */
    @SneakyThrows
    public BulkByScrollResponse updateByQuery(@NotNull QueryBuilder query, int batchSize, @NotNull String code, @NotNull Map<String, Object> params, @NotNull String... indices) {
        UpdateByQueryRequest request = new UpdateByQueryRequest(indices);
        request.setConflicts("proceed");
        request.setQuery(query);
        request.setBatchSize(batchSize);
        request.setSlices(AUTO_SLICES);
        request.setScroll(TimeValue.timeValueMinutes(KEEP_ALIVE));
        request.setScript(new Script(ScriptType.INLINE, "painless", code, params));
        return client.updateByQuery(request, DEFAULT);
    }

    /**
     * Delete By Query API
     */
    @SneakyThrows
    public BulkByScrollResponse deleteByQuery(@NotNull QueryBuilder query, int batchSize, @NotNull String... indices) {
        DeleteByQueryRequest request = new DeleteByQueryRequest(indices);
        request.setConflicts("proceed");
        request.setQuery(query);
        request.setBatchSize(batchSize);
        request.setSlices(AUTO_SLICES);
        request.setScroll(TimeValue.timeValueMinutes(KEEP_ALIVE));
        return client.deleteByQuery(request, DEFAULT);
    }
}
