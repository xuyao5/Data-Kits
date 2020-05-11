package io.github.xuyao5.dal.elasticsearch.document;

import io.github.xuyao5.dal.elasticsearch.abstr.AbstractSupporter;
import io.github.xuyao5.dal.elasticsearch.document.param.*;
import lombok.SneakyThrows;
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
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.RethrottleRequest;
import org.elasticsearch.client.core.MultiTermVectorsRequest;
import org.elasticsearch.client.core.MultiTermVectorsResponse;
import org.elasticsearch.client.core.TermVectorsRequest;
import org.elasticsearch.client.core.TermVectorsResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.index.reindex.ReindexRequest;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.tasks.TaskId;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 1/05/20 22:49
 * @apiNote EsDocumentSupporter
 * @implNote EsDocumentSupporter
 */
@Component("esDocumentSupporter")
public final class EsDocumentSupporter extends AbstractSupporter {

    /**
     * Index API
     */
    @SneakyThrows
    public IndexResponse index(@NotNull RestHighLevelClient client, @NotNull IndexParams params) {
        IndexRequest request = new IndexRequest("posts");
        request.id("1");
        String jsonString = "{" +
                "\"user\":\"kimchy\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";
        request.source(jsonString, XContentType.JSON);
        return client.index(request, RequestOptions.DEFAULT);
    }

    /**
     * Get API
     */
    @SneakyThrows
    public GetResponse get(@NotNull RestHighLevelClient client, @NotNull GetParams params) {
        GetRequest getRequest = new GetRequest(
                "posts",
                "1");
        return client.get(getRequest, RequestOptions.DEFAULT);
    }

    /**
     * Exists API
     */
    @SneakyThrows
    public boolean exists(@NotNull RestHighLevelClient client, @NotNull GetParams params) {
        GetRequest getRequest = new GetRequest(
                "posts",
                "1");
        getRequest.fetchSourceContext(new FetchSourceContext(false));
        getRequest.storedFields("_none_");
        return client.exists(getRequest, RequestOptions.DEFAULT);
    }

    /**
     * Delete API
     */
    @SneakyThrows
    public DeleteResponse delete(@NotNull RestHighLevelClient client, @NotNull DeleteParams params) {
        DeleteRequest request = new DeleteRequest(
                "posts",
                "1");
        return client.delete(request, RequestOptions.DEFAULT);
    }

    /**
     * Update API
     */
    @SneakyThrows
    public UpdateResponse update(@NotNull RestHighLevelClient client, @NotNull UpdateParams params) {
        UpdateRequest request = new UpdateRequest(
                "posts",
                "1");
        return client.update(request, RequestOptions.DEFAULT);
    }

    /**
     * Term Vectors API
     */
    @SneakyThrows
    public TermVectorsResponse termvectors(@NotNull RestHighLevelClient client, @NotNull TermVectorsParams params) {
        TermVectorsRequest request = new TermVectorsRequest("authors", "1");
        request.setFields("user");
        return client.termvectors(request, RequestOptions.DEFAULT);
    }

    /**
     * Bulk API
     */
    @SneakyThrows
    public BulkResponse bulk(@NotNull RestHighLevelClient client, @NotNull BulkParams params) {
        BulkRequest request = new BulkRequest();
        request.add(new IndexRequest("posts").id("1")
                .source(XContentType.JSON, "field", "foo"));
        request.add(new IndexRequest("posts").id("2")
                .source(XContentType.JSON, "field", "bar"));
        request.add(new IndexRequest("posts").id("3")
                .source(XContentType.JSON, "field", "baz"));
        return client.bulk(request, RequestOptions.DEFAULT);
    }

    /**
     * Multi-Get API
     */
    @SneakyThrows
    public MultiGetResponse mget(@NotNull RestHighLevelClient client, @NotNull MultiGetParams params) {
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
    public BulkByScrollResponse reindex(@NotNull RestHighLevelClient client, @NotNull ReindexParams params) {
        ReindexRequest request = new ReindexRequest();
        request.setSourceIndices("source1", "source2");
        request.setDestIndex("dest");
        return client.reindex(request, RequestOptions.DEFAULT);
    }

    /**
     * Update By Query API
     */
    @SneakyThrows
    public BulkByScrollResponse updateByQuery(@NotNull RestHighLevelClient client, @NotNull UpdateByQueryParams params) {
        UpdateByQueryRequest request = new UpdateByQueryRequest("source1", "source2");
        return client.updateByQuery(request, RequestOptions.DEFAULT);
    }

    /**
     * Delete By Query API
     */
    @SneakyThrows
    public BulkByScrollResponse deleteByQuery(@NotNull RestHighLevelClient client, @NotNull DeleteByQueryParams params) {
        DeleteByQueryRequest request = new DeleteByQueryRequest("source1", "source2");
        return client.deleteByQuery(request, RequestOptions.DEFAULT);
    }

    /**
     * Rethrottle API
     */
    @SneakyThrows
    public void reindexRethrottle(@NotNull RestHighLevelClient client, @NotNull RethrottleParams params) {
        RethrottleRequest request = new RethrottleRequest(TaskId.EMPTY_TASK_ID);
        client.reindexRethrottle(request, RequestOptions.DEFAULT);
        client.updateByQueryRethrottle(request, RequestOptions.DEFAULT);
        client.deleteByQueryRethrottle(request, RequestOptions.DEFAULT);
    }

    /**
     * Multi Term Vectors API
     */
    @SneakyThrows
    public MultiTermVectorsResponse mtermvectors(@NotNull RestHighLevelClient client, @NotNull MultiTermVectorsParams params) {
        MultiTermVectorsRequest request = new MultiTermVectorsRequest();
        TermVectorsRequest tvrequest1 =
                new TermVectorsRequest("authors", "1");
        tvrequest1.setFields("user");
        request.add(tvrequest1);

        XContentBuilder docBuilder = XContentFactory.jsonBuilder();
        docBuilder.startObject().field("user", "guest-user").endObject();
        TermVectorsRequest tvrequest2 =
                new TermVectorsRequest("authors", docBuilder);
        request.add(tvrequest2);
        return client.mtermvectors(request, RequestOptions.DEFAULT);
    }
}
