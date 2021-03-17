package io.github.xuyao5.dkl.eskits.support;

import io.github.xuyao5.dkl.eskits.util.MyGsonUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.GetSourceRequest;
import org.elasticsearch.client.core.GetSourceResponse;
import org.elasticsearch.common.xcontent.XContentType;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

import static org.elasticsearch.client.RequestOptions.DEFAULT;
import static org.elasticsearch.search.fetch.subphase.FetchSourceContext.DO_NOT_FETCH_SOURCE;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 1/05/20 22:49
 * @apiNote EsDocumentSupporter
 * @implNote EsDocumentSupporter
 */
@Slf4j
public final class DocumentSupporter {

    /**
     * Index API
     */
    @SneakyThrows
    public IndexResponse index(@NotNull RestHighLevelClient client, @NotNull String index, @NotNull String id, @NotNull Serializable json) {
        return client.index(new IndexRequest(index).id(id).source(MyGsonUtils.obj2Json(json), XContentType.JSON), DEFAULT);
    }

    /**
     * Get API
     */
    @SneakyThrows
    public GetResponse get(@NotNull RestHighLevelClient client, @NotNull String index, @NotNull String id) {
        return client.get(new GetRequest(index, id), DEFAULT);
    }

    /**
     * Get Source API
     */
    @SneakyThrows
    public GetSourceResponse getSource(@NotNull RestHighLevelClient client, @NotNull String index, @NotNull String id) {
        return client.getSource(new GetSourceRequest(index, id), DEFAULT);
    }

    /**
     * Exists API
     */
    @SneakyThrows
    public boolean exists(@NotNull RestHighLevelClient client, @NotNull String index, @NotNull String id) {
        return client.exists(new GetRequest(index, id).fetchSourceContext(DO_NOT_FETCH_SOURCE).storedFields("_none_"), DEFAULT);
    }

    /**
     * Delete API
     */
    @SneakyThrows
    public DeleteResponse delete(@NotNull RestHighLevelClient client, @NotNull String index, @NotNull String id) {
        return client.delete(new DeleteRequest(index, id), DEFAULT);
    }

    /**
     * Update API
     */
    @SneakyThrows
    public UpdateResponse update(@NotNull RestHighLevelClient client, @NotNull String index, @NotNull String id, @NotNull Serializable json) {
        return client.update(new UpdateRequest(index, id).doc(MyGsonUtils.obj2Json(json), XContentType.JSON), DEFAULT);
    }
}
