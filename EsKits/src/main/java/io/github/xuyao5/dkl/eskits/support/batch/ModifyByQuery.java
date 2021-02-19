package io.github.xuyao5.dkl.eskits.support.batch;

import io.github.xuyao5.dkl.eskits.abstr.AbstractSupporter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;

import javax.validation.constraints.NotNull;

import static org.elasticsearch.client.RequestOptions.DEFAULT;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 19/02/21 21:34
 * @apiNote TODO 这里输入文件说明
 * @implNote TODO 这里输入实现说明
 */
@Slf4j
public final class ModifyByQuery extends AbstractSupporter {

    public ModifyByQuery(RestHighLevelClient client) {
        super(client);
    }

    /**
     * Update By Query API
     */
    @SneakyThrows
    public BulkByScrollResponse updateByQuery(@NotNull QueryBuilder query, int batchSize, @NotNull String... indices) {
        UpdateByQueryRequest request = new UpdateByQueryRequest(indices);
        request.setConflicts("proceed");
        request.setQuery(query);
        request.setBatchSize(batchSize);
        return client.updateByQuery(request, DEFAULT);
    }

    /**
     * Delete By Query API
     */
    @SneakyThrows
    public BulkByScrollResponse deleteByQuery(@NotNull QueryBuilder query, int batchSize, @NotNull String... indices) {
        DeleteByQueryRequest request = new DeleteByQueryRequest("source1", "source2");
        request.setConflicts("proceed");
        request.setQuery(query);
        request.setBatchSize(batchSize);
        return client.deleteByQuery(request, DEFAULT);
    }
}
