package io.github.xuyao5.dkl.eskits.support.general;

import io.github.xuyao5.dkl.eskits.util.GsonUtilsPlus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
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

import java.io.Serializable;
import java.util.List;

import static org.elasticsearch.client.RequestOptions.DEFAULT;
import static org.elasticsearch.search.fetch.subphase.FetchSourceContext.DO_NOT_FETCH_SOURCE;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 1/05/20 22:49
 * @apiNote DocumentSupporter
 * @implNote DocumentSupporter
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DocumentSupporter {

    public static DocumentSupporter getInstance() {
        return DocumentSupporter.SingletonHolder.INSTANCE;
    }

    /**
     * Index API
     */
    @SneakyThrows
    public IndexResponse index(@NonNull RestHighLevelClient client, @NonNull String index, @NonNull String id, @NonNull Serializable obj) {
        return client.index(new IndexRequest(index).id(id).source(GsonUtilsPlus.obj2Json(obj), XContentType.JSON), DEFAULT);
    }

    /**
     * Get API
     */
    @SneakyThrows
    public GetResponse get(@NonNull RestHighLevelClient client, @NonNull String index, @NonNull String id) {
        return client.get(new GetRequest(index, id), DEFAULT);
    }

    /**
     * Multi-Get API
     */
    @SneakyThrows
    public MultiGetResponse multiGet(@NonNull RestHighLevelClient client, @NonNull List<MultiGetRequest.Item> items) {
        return client.mget(items.stream().collect(MultiGetRequest::new, MultiGetRequest::add, (item1, item2) -> {
        }), DEFAULT);
    }

    /**
     * Get Source API
     */
    @SneakyThrows
    public GetSourceResponse getSource(@NonNull RestHighLevelClient client, @NonNull String index, @NonNull String id) {
        return client.getSource(new GetSourceRequest(index, id), DEFAULT);
    }

    /**
     * Exists API
     */
    @SneakyThrows
    public boolean exists(@NonNull RestHighLevelClient client, @NonNull String index, @NonNull String id) {
        return client.exists(new GetRequest(index, id).fetchSourceContext(DO_NOT_FETCH_SOURCE).storedFields("_none_"), DEFAULT);
    }

    /**
     * Delete API
     */
    @SneakyThrows
    public DeleteResponse delete(@NonNull RestHighLevelClient client, @NonNull String index, @NonNull String id) {
        return client.delete(new DeleteRequest(index, id), DEFAULT);
    }

    /**
     * Update API
     */
    @SneakyThrows
    public UpdateResponse update(@NonNull RestHighLevelClient client, @NonNull String index, @NonNull String id, @NonNull Serializable obj) {
        return client.update(new UpdateRequest(index, id).docAsUpsert(true).doc(GsonUtilsPlus.obj2Json(obj), XContentType.JSON), DEFAULT);
    }

    private static class SingletonHolder {
        private static final DocumentSupporter INSTANCE = new DocumentSupporter();
    }
}