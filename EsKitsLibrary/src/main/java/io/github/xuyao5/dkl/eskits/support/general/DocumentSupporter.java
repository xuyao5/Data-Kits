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
 * @version 1/05/20 22:49
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DocumentSupporter {

    public static DocumentSupporter getInstance() {
        return DocumentSupporter.SingletonHolder.INSTANCE;
    }

    /**
     * Index API
     *
     * @param client 客户端
     * @param index  索引
     * @param id     主键
     * @param obj    对象
     * @return 返回对象
     */
    @SneakyThrows
    public IndexResponse index(@NonNull RestHighLevelClient client, @NonNull String index, @NonNull String id, @NonNull Serializable obj) {
        return client.index(new IndexRequest(index).id(id).source(GsonUtilsPlus.obj2Json(obj), XContentType.JSON), DEFAULT);
    }

    /**
     * Get API
     *
     * @param client 客户端
     * @param index 索引
     * @param id 主键
     * @return 返回对象
     */
    @SneakyThrows
    public GetResponse get(@NonNull RestHighLevelClient client, @NonNull String index, @NonNull String id) {
        return client.get(new GetRequest(index, id), DEFAULT);
    }

    /**
     * Multi-Get API
     *
     * @param client 客户端
     * @param items 请求
     * @return 返回对象
     */
    @SneakyThrows
    public MultiGetResponse multiGet(@NonNull RestHighLevelClient client, @NonNull List<MultiGetRequest.Item> items) {
        return client.mget(items.stream().collect(MultiGetRequest::new, MultiGetRequest::add, (item1, item2) -> {
        }), DEFAULT);
    }

    /**
     * Get Source API
     *
     * @param client 客户端
     * @param index 索引
     * @param id 主键
     * @return 返回对象
     */
    @SneakyThrows
    public GetSourceResponse getSource(@NonNull RestHighLevelClient client, @NonNull String index, @NonNull String id) {
        return client.getSource(new GetSourceRequest(index, id), DEFAULT);
    }

    /**
     * Exists API
     *
     * @param client 客户端
     * @param index 索引
     * @param id 主键
     * @return 返回对象
     */
    @SneakyThrows
    public boolean exists(@NonNull RestHighLevelClient client, @NonNull String index, @NonNull String id) {
        return client.exists(new GetRequest(index, id).fetchSourceContext(DO_NOT_FETCH_SOURCE).storedFields("_none_"), DEFAULT);
    }

    /**
     * Delete API
     *
     * @param client 客户端
     * @param index 索引
     * @param id 主键
     * @return 返回对象
     */
    @SneakyThrows
    public DeleteResponse delete(@NonNull RestHighLevelClient client, @NonNull String index, @NonNull String id) {
        return client.delete(new DeleteRequest(index, id), DEFAULT);
    }

    /**
     * Update API
     *
     * @param client 客户端
     * @param index 索引
     * @param id 主键
     * @param obj 对象
     * @return 返回对象
     */
    @SneakyThrows
    public UpdateResponse update(@NonNull RestHighLevelClient client, @NonNull String index, @NonNull String id, @NonNull Serializable obj) {
        return client.update(new UpdateRequest(index, id).doc(GsonUtilsPlus.obj2Json(obj), XContentType.JSON).docAsUpsert(true), DEFAULT);
    }

    /**
     * Update API
     *
     * @param client 客户端
     * @param index 索引
     * @param id 主键
     * @param docObj 新建对象
     * @param upsertObj 更新对象
     * @return 返回对象
     */
    @SneakyThrows
    public UpdateResponse update(@NonNull RestHighLevelClient client, @NonNull String index, @NonNull String id, @NonNull Serializable docObj, @NonNull Serializable upsertObj) {
        return client.update(new UpdateRequest(index, id).doc(GsonUtilsPlus.obj2Json(docObj), XContentType.JSON).upsert(GsonUtilsPlus.obj2Json(upsertObj), XContentType.JSON), DEFAULT);
    }

    private static class SingletonHolder {
        private static final DocumentSupporter INSTANCE = new DocumentSupporter();
    }
}