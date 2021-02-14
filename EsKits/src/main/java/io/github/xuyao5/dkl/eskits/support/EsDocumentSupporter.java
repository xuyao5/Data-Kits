package io.github.xuyao5.dkl.eskits.support;

import io.github.xuyao5.dkl.common.util.GsonUtils;
import io.github.xuyao5.dkl.eskits.abstr.AbstractSupporter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.DocWriteRequest;
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
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.VersionType;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.index.reindex.ReindexRequest;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;

import javax.validation.constraints.NotNull;

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
        return client.index(new IndexRequest(index)
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
        return client.get(new GetRequest(index, id), RequestOptions.DEFAULT);
    }

    /**
     * Exists API
     */
    @SneakyThrows
    public boolean exists(@NotNull String index, @NotNull String id) {
        return client.exists(new GetRequest(index, id).fetchSourceContext(new FetchSourceContext(false)).storedFields("_none_"), RequestOptions.DEFAULT);
    }

    /**
     * Delete API
     */
    @SneakyThrows
    public DeleteResponse delete(@NotNull String index, @NotNull String id) {
        return client.delete(new DeleteRequest(index, id), RequestOptions.DEFAULT);
    }

    /**
     * Update API
     */
    @SneakyThrows
    public UpdateResponse update(@NotNull String index, @NotNull String id) {
        return client.update(new UpdateRequest(index, id), RequestOptions.DEFAULT);
    }

    /**
     * Multi-Get API
     */
    @SneakyThrows
    public MultiGetResponse mget() {
        MultiGetRequest request = new MultiGetRequest();
        request.add(new MultiGetRequest.Item(
                "index",
                "example_id"));
        request.add(new MultiGetRequest.Item("index", "another_id"));
        return client.mget(request, RequestOptions.DEFAULT);
    }

    /**
     * Reindex API
     */
    @SneakyThrows
    public BulkByScrollResponse reindex() {
        ReindexRequest request = new ReindexRequest();
        request.setSourceIndices("source1", "source2");
        request.setDestIndex("dest");
        return client.reindex(request, RequestOptions.DEFAULT);
    }

    /**
     * Update By Query API
     */
    @SneakyThrows
    public BulkByScrollResponse updateByQuery() {
        UpdateByQueryRequest request = new UpdateByQueryRequest("source1", "source2");
        return client.updateByQuery(request, RequestOptions.DEFAULT);
    }

    /**
     * Delete By Query API
     */
    @SneakyThrows
    public BulkByScrollResponse deleteByQuery() {
        DeleteByQueryRequest request = new DeleteByQueryRequest("source1", "source2");
        return client.deleteByQuery(request, RequestOptions.DEFAULT);
    }
}
