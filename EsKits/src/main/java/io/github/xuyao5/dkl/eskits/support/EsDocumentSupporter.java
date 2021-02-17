package io.github.xuyao5.dkl.eskits.support;

import io.github.xuyao5.dkl.common.util.GsonUtils;
import io.github.xuyao5.dkl.eskits.abstr.AbstractSupporter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.DocWriteRequest;
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
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.GetSourceRequest;
import org.elasticsearch.client.core.GetSourceResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.index.reindex.ReindexRequest;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

import static org.elasticsearch.client.RequestOptions.DEFAULT;
import static org.elasticsearch.search.fetch.subphase.FetchSourceContext.DO_NOT_FETCH_SOURCE;

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
    public IndexResponse index(@NotNull String index, @NotNull String id, @NotNull Serializable obj) {
        return client.index(new IndexRequest(index).id(id).source(GsonUtils.obj2Json(obj), XContentType.JSON), DEFAULT);
    }

    /**
     * Get API
     */
    @SneakyThrows
    public GetResponse get(@NotNull String index, @NotNull String id) {
        return client.get(new GetRequest(index, id), DEFAULT);
    }

    /**
     * Get Source API
     */
    @SneakyThrows
    public GetSourceResponse getSource(@NotNull String index, @NotNull String id) {
        return client.getSource(new GetSourceRequest(index, id), DEFAULT);
    }

    /**
     * Exists API
     */
    @SneakyThrows
    public boolean exists(@NotNull String index, @NotNull String id) {
        return client.exists(new GetRequest(index, id).fetchSourceContext(DO_NOT_FETCH_SOURCE).storedFields("_none_"), DEFAULT);
    }

    /**
     * Delete API
     */
    @SneakyThrows
    public DeleteResponse delete(@NotNull String index, @NotNull String id) {
        return client.delete(new DeleteRequest(index, id), DEFAULT);
    }

    /**
     * Update API
     */
    @SneakyThrows
    public UpdateResponse update(@NotNull String index, @NotNull String id, @NotNull Serializable obj) {
        return client.update(new UpdateRequest(index, id).doc(GsonUtils.obj2Json(obj), XContentType.JSON), DEFAULT);
    }

    /**
     * Bulk API
     */
    @SneakyThrows
    public BulkResponse bulk(@NotNull List<DocWriteRequest<?>> requests) {
        return client.bulk(new BulkRequest().add(requests), DEFAULT);
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
        return client.mget(request, DEFAULT);
    }

    /**
     * Reindex API
     */
    @SneakyThrows
    public BulkByScrollResponse reindex() {
        ReindexRequest request = new ReindexRequest();
        request.setSourceIndices("source1", "source2");
        request.setDestIndex("dest");
        return client.reindex(request, DEFAULT);
    }

    /**
     * Update By Query API
     */
    @SneakyThrows
    public BulkByScrollResponse updateByQuery() {
        UpdateByQueryRequest request = new UpdateByQueryRequest("source1", "source2");
        return client.updateByQuery(request, DEFAULT);
    }

    /**
     * Delete By Query API
     */
    @SneakyThrows
    public BulkByScrollResponse deleteByQuery() {
        DeleteByQueryRequest request = new DeleteByQueryRequest("source1", "source2");
        return client.deleteByQuery(request, DEFAULT);
    }
}
