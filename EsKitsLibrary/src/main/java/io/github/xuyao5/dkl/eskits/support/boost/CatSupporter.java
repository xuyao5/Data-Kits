package io.github.xuyao5.dkl.eskits.support.boost;

import com.google.gson.stream.JsonReader;
import io.github.xuyao5.dkl.eskits.schema.cat.*;
import io.github.xuyao5.dkl.eskits.util.GsonUtilsPlus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author Thomas.XU(xuyao)
 * @version 16/09/21 23:24
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CatSupporter {

    public static CatSupporter getInstance() {
        return CatSupporter.SingletonHolder.INSTANCE;
    }

    public List<Allocation4Cat> getCatAllocation(@NonNull RestHighLevelClient client) {
        return GsonUtilsPlus.json2Obj(getJsonReader(client, "/_cat/allocation?format=json"), List.class, Allocation4Cat.class);
    }

    public List<Shards4Cat> getCatShards(@NonNull RestHighLevelClient client) {
        return GsonUtilsPlus.json2Obj(getJsonReader(client, "/_cat/shards?format=json"), List.class, Shards4Cat.class);
    }

    public List<Shards4Cat> getCatShards(@NonNull RestHighLevelClient client, @NonNull String index) {
        return GsonUtilsPlus.json2Obj(getJsonReader(client, String.format("/_cat/shards/%s?format=json", index)), List.class, Shards4Cat.class);
    }

    public List<Master4Cat> getCatMaster(@NonNull RestHighLevelClient client) {
        return GsonUtilsPlus.json2Obj(getJsonReader(client, "/_cat/master?format=json"), List.class, Master4Cat.class);
    }

    public List<Nodes4Cat> getCatNodes(@NonNull RestHighLevelClient client) {
        return GsonUtilsPlus.json2Obj(getJsonReader(client, "/_cat/nodes?format=json"), List.class, Nodes4Cat.class);
    }

    public List<Tasks4Cat> getCatTasks(@NonNull RestHighLevelClient client) {
        return GsonUtilsPlus.json2Obj(getJsonReader(client, "/_cat/tasks?format=json"), List.class, Tasks4Cat.class);
    }

    public List<Indices4Cat> getCatIndices(@NonNull RestHighLevelClient client) {
        return GsonUtilsPlus.json2Obj(getJsonReader(client, "/_cat/indices?format=json&bytes=b"), List.class, Indices4Cat.class);
    }

    public List<Indices4Cat> getCatIndices(@NonNull RestHighLevelClient client, @NonNull String index) {
        return GsonUtilsPlus.json2Obj(getJsonReader(client, String.format("/_cat/indices/%s?format=json&bytes=b", index)), List.class, Indices4Cat.class);
    }

    public List<Segments4Cat> getCatSegments(@NonNull RestHighLevelClient client) {
        return GsonUtilsPlus.json2Obj(getJsonReader(client, "/_cat/segments?format=json"), List.class, Segments4Cat.class);
    }

    public List<Segments4Cat> getCatSegments(@NonNull RestHighLevelClient client, @NonNull String index) {
        return GsonUtilsPlus.json2Obj(getJsonReader(client, String.format("/_cat/segments/%s?format=json", index)), List.class, Segments4Cat.class);
    }

    public List<Count4Cat> getCatCount(@NonNull RestHighLevelClient client, @NonNull String index) {
        return GsonUtilsPlus.json2Obj(getJsonReader(client, String.format("/_cat/count/%s?format=json", index)), List.class, Count4Cat.class);
    }

    public List<Recovery4Cat> getCatRecovery(@NonNull RestHighLevelClient client, @NonNull String index) {
        return GsonUtilsPlus.json2Obj(getJsonReader(client, String.format("/_cat/recovery/%s?format=json", index)), List.class, Recovery4Cat.class);
    }

    public List<Health4Cat> getCatHealth(@NonNull RestHighLevelClient client) {
        return GsonUtilsPlus.json2Obj(getJsonReader(client, "/_cat/health?format=json"), List.class, Health4Cat.class);
    }

    public List<Aliases4Cat> getCatAliases(@NonNull RestHighLevelClient client) {
        return GsonUtilsPlus.json2Obj(getJsonReader(client, "/_cat/aliases?format=json"), List.class, Aliases4Cat.class);
    }

    public List<Aliases4Cat> getCatAliases(@NonNull RestHighLevelClient client, @NonNull String alias) {
        return GsonUtilsPlus.json2Obj(getJsonReader(client, String.format("/_cat/aliases/%s?format=json", alias)), List.class, Aliases4Cat.class);
    }

    public List<ThreadPool4Cat> getCatThreadPool(@NonNull RestHighLevelClient client) {
        return GsonUtilsPlus.json2Obj(getJsonReader(client, "/_cat/thread_pool?format=json"), List.class, ThreadPool4Cat.class);
    }

    public List<ThreadPool4Cat> getCatThreadPool(@NonNull RestHighLevelClient client, @NonNull String threadPools) {
        return GsonUtilsPlus.json2Obj(getJsonReader(client, String.format("/_cat/thread_pool/%s?format=json", threadPools)), List.class, ThreadPool4Cat.class);
    }

    public List<Plugins4Cat> getCatPlugins(@NonNull RestHighLevelClient client) {
        return GsonUtilsPlus.json2Obj(getJsonReader(client, "/_cat/plugins?format=json"), List.class, Plugins4Cat.class);
    }

    public List<FieldData4Cat> getCatFieldData(@NonNull RestHighLevelClient client) {
        return GsonUtilsPlus.json2Obj(getJsonReader(client, "/_cat/fielddata?format=json"), List.class, FieldData4Cat.class);

    }

    public List<FieldData4Cat> getCatFieldData(@NonNull RestHighLevelClient client, @NonNull String fields) {
        return GsonUtilsPlus.json2Obj(getJsonReader(client, String.format("/_cat/fielddata/%s?format=json", fields)), List.class, FieldData4Cat.class);
    }

    public List<NodeAttrs4Cat> getCatNodeAttrs(@NonNull RestHighLevelClient client) {
        return GsonUtilsPlus.json2Obj(getJsonReader(client, "/_cat/nodeattrs?format=json"), List.class, NodeAttrs4Cat.class);
    }

    public List<Templates4Cat> getCatTemplates(@NonNull RestHighLevelClient client) {
        return GsonUtilsPlus.json2Obj(getJsonReader(client, "/_cat/templates?format=json"), List.class, Templates4Cat.class);
    }

    @SneakyThrows
    private JsonReader getJsonReader(@NonNull RestHighLevelClient client, @NonNull String url) {
        return new JsonReader(new BufferedReader(new InputStreamReader(client.getLowLevelClient().performRequest(new Request("GET", url)).getEntity().getContent(), StandardCharsets.UTF_8)));
    }

    private static class SingletonHolder {
        private static final CatSupporter INSTANCE = new CatSupporter();
    }
}