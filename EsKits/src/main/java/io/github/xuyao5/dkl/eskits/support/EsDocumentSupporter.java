package io.github.xuyao5.dkl.eskits.support;

import io.github.xuyao5.dkl.common.util.GsonUtils;
import io.github.xuyao5.dkl.eskits.abstr.AbstractSupporter;
import io.github.xuyao5.dkl.eskits.support.param.DeleteByQueryParams;
import io.github.xuyao5.dkl.eskits.support.param.MultiGetParams;
import io.github.xuyao5.dkl.eskits.support.param.ReindexParams;
import io.github.xuyao5.dkl.eskits.support.param.UpdateByQueryParams;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetRequest;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.VersionType;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.index.reindex.ReindexRequest;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 1/05/20 22:49
 * @apiNote EsDocumentSupporter
 * @implNote EsDocumentSupporter
 */
@Slf4j
public final class EsDocumentSupporter extends AbstractSupporter {

    public EsDocumentSupporter(@NotNull RestHighLevelClient client) {
        super(client);
    }

    /**
     * Index API
     */
    @SneakyThrows
    public <T> IndexResponse index(@NotNull String index, @NotNull String id, @NotNull T obj) {
        return restHighLevelClient.index(new IndexRequest(index)
                .id(id)
                .source(GsonUtils.obj2Json(obj), XContentType.JSON)
                .setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL)
                .versionType(VersionType.EXTERNAL)
                .opType(DocWriteRequest.OpType.CREATE), RequestOptions.DEFAULT);
    }

    /**
     * Get API
     */
    @SneakyThrows
    public GetResponse get(@NotNull String index, @NotNull String id) {
        return restHighLevelClient.get(new GetRequest(index, id), RequestOptions.DEFAULT);
    }

    /**
     * Exists API
     */
    @SneakyThrows
    public boolean exists(@NotNull String index, @NotNull String id) {
        return restHighLevelClient.exists(new GetRequest(index, id).fetchSourceContext(new FetchSourceContext(false)).storedFields("_none_"), RequestOptions.DEFAULT);
    }

    /**
     * Delete API
     */
    @SneakyThrows
    public DeleteResponse delete(@NotNull String index, @NotNull String id) {
        return restHighLevelClient.delete(new DeleteRequest(index, id), RequestOptions.DEFAULT);
    }

    /**
     * Update API
     */
    @SneakyThrows
    public UpdateResponse update(@NotNull String index, @NotNull String id) {
        return restHighLevelClient.update(new UpdateRequest(index, id), RequestOptions.DEFAULT);
    }

    /**
     * Multi-Get API
     */
    @SneakyThrows
    public MultiGetResponse mget(@NotNull MultiGetParams params) {
        MultiGetRequest request = new MultiGetRequest();
        request.add(new MultiGetRequest.Item(
                "index",
                "example_id"));
        request.add(new MultiGetRequest.Item("index", "another_id"));
        return restHighLevelClient.mget(request, RequestOptions.DEFAULT);
    }

    /**
     * Reindex API
     */
    @SneakyThrows
    public BulkByScrollResponse reindex(@NotNull ReindexParams params) {
        ReindexRequest request = new ReindexRequest();
        request.setSourceIndices("source1", "source2");
        request.setDestIndex("dest");
        return restHighLevelClient.reindex(request, RequestOptions.DEFAULT);
    }

    /**
     * Update By Query API
     */
    @SneakyThrows
    public BulkByScrollResponse updateByQuery(@NotNull UpdateByQueryParams params) {
        UpdateByQueryRequest request = new UpdateByQueryRequest("source1", "source2");
        return restHighLevelClient.updateByQuery(request, RequestOptions.DEFAULT);
    }

    /**
     * Delete By Query API
     */
    @SneakyThrows
    public BulkByScrollResponse deleteByQuery(@NotNull DeleteByQueryParams params) {
        DeleteByQueryRequest request = new DeleteByQueryRequest("source1", "source2");
        return restHighLevelClient.deleteByQuery(request, RequestOptions.DEFAULT);
    }

    /**
     * Bulk Processor
     */
    public void bulk(@NotNull List<IndexRequest> indexRequestList) {
        try (BulkProcessor bulkProcessor = BulkProcessor.builder((request, bulkListener) -> restHighLevelClient.bulkAsync(request, RequestOptions.DEFAULT, bulkListener),
                new BulkProcessor.Listener() {
                    @Override
                    public void beforeBulk(long executionId, BulkRequest request) {
                        int numberOfActions = request.numberOfActions();
                        log.debug("Executing bulk [{}] with {} requests", executionId, numberOfActions);
                    }

                    @Override
                    public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
                        if (response.hasFailures()) {
                            log.warn("Bulk [{}] executed with failures", executionId);
                        } else {
                            log.debug("Bulk [{}] completed in {} milliseconds", executionId, response.getTook().getMillis());
                        }
                    }

                    @Override
                    public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
                        log.error("Failed to execute bulk", failure);
                    }
                }).setBulkActions(5000)//default:1000
                .setBulkSize(new ByteSizeValue(10, ByteSizeUnit.MB))//default:5
                .build()) {
            indexRequestList.parallelStream().forEachOrdered(bulkProcessor::add);
        }
    }
}
