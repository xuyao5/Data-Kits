package io.github.xuyao5.dkl.eskits.support;

import io.github.xuyao5.dkl.eskits.abstr.AbstractSupporter;
import io.github.xuyao5.dkl.eskits.support.param.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
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
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.RethrottleRequest;
import org.elasticsearch.client.core.MultiTermVectorsRequest;
import org.elasticsearch.client.core.MultiTermVectorsResponse;
import org.elasticsearch.client.core.TermVectorsRequest;
import org.elasticsearch.client.core.TermVectorsResponse;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.index.reindex.ReindexRequest;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.tasks.TaskId;

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
    public IndexResponse index(@NotNull IndexParams params) {
        IndexRequest request = new IndexRequest("posts");
        request.id("1");
        String jsonString = "{" +
                "\"user\":\"kimchy\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";
        request.source(jsonString, XContentType.JSON);
        return restHighLevelClient.index(request, RequestOptions.DEFAULT);
    }

    /**
     * Get API
     */
    @SneakyThrows
    public GetResponse get(@NotNull GetParams params) {
        GetRequest getRequest = new GetRequest(
                "posts",
                "1");
        return restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
    }

    /**
     * Exists API
     */
    @SneakyThrows
    public boolean exists(@NotNull GetParams params) {
        GetRequest getRequest = new GetRequest(
                "posts",
                "1");
        getRequest.fetchSourceContext(new FetchSourceContext(false));
        getRequest.storedFields("_none_");
        return restHighLevelClient.exists(getRequest, RequestOptions.DEFAULT);
    }

    /**
     * Delete API
     */
    @SneakyThrows
    public DeleteResponse delete(@NotNull DeleteParams params) {
        DeleteRequest request = new DeleteRequest(
                "posts",
                "1");
        return restHighLevelClient.delete(request, RequestOptions.DEFAULT);
    }

    /**
     * Update API
     */
    @SneakyThrows
    public UpdateResponse update(@NotNull UpdateParams params) {
        UpdateRequest request = new UpdateRequest(
                "posts",
                "1");
        return restHighLevelClient.update(request, RequestOptions.DEFAULT);
    }

    /**
     * Term Vectors API
     */
    @SneakyThrows
    public TermVectorsResponse termvectors(@NotNull TermVectorsParams params) {
        TermVectorsRequest request = new TermVectorsRequest("authors", "1");
        request.setFields("user");
        return restHighLevelClient.termvectors(request, RequestOptions.DEFAULT);
    }

    /**
     * Bulk API
     */
    @SneakyThrows
    public BulkResponse bulk(@NotNull BulkParams params) {
        BulkRequest request = new BulkRequest();
        request.add(new IndexRequest("posts").id("1")
                .source(XContentType.JSON, "field", "foo"));
        request.add(new IndexRequest("posts").id("2")
                .source(XContentType.JSON, "field", "bar"));
        request.add(new IndexRequest("posts").id("3")
                .source(XContentType.JSON, "field", "baz"));
        return restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
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
     * Rethrottle API
     */
    @SneakyThrows
    public void reindexRethrottle(@NotNull RethrottleParams params) {
        RethrottleRequest request = new RethrottleRequest(TaskId.EMPTY_TASK_ID);
        restHighLevelClient.reindexRethrottle(request, RequestOptions.DEFAULT);
        restHighLevelClient.updateByQueryRethrottle(request, RequestOptions.DEFAULT);
        restHighLevelClient.deleteByQueryRethrottle(request, RequestOptions.DEFAULT);
    }

    /**
     * Multi Term Vectors API
     */
    @SneakyThrows
    public MultiTermVectorsResponse mtermvectors(@NotNull MultiTermVectorsParams params) {
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
        return restHighLevelClient.mtermvectors(request, RequestOptions.DEFAULT);
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
            bulkProcessor.flush();
        }
    }
}
