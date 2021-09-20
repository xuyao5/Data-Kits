package io.github.xuyao5.dkl.eskits.support.boost;

import com.google.gson.reflect.TypeToken;
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
 * @implSpec 16/09/21 23:24
 * @apiNote CatSupporter
 * @implNote CatSupporter
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CatSupporter {

    public static CatSupporter getInstance() {
        return CatSupporter.SingletonHolder.INSTANCE;
    }

    public List<Allocation4Cat> getCatAllocation(@NonNull RestHighLevelClient client) {
        return getCat(client, "/_cat/allocation?format=json", TypeToken.getParameterized(List.class, Allocation4Cat.class));
    }

    public List<Shards4Cat> getCatShards(@NonNull RestHighLevelClient client) {
        return getCat(client, "/_cat/shards?format=json", TypeToken.getParameterized(List.class, Shards4Cat.class));
    }

    public List<Shards4Cat> getCatShards(@NonNull RestHighLevelClient client, @NonNull String index) {
        return getCat(client, String.format("/_cat/shards/%s?format=json", index), TypeToken.getParameterized(List.class, Shards4Cat.class));
    }

    public List<Master4Cat> getCatMaster(@NonNull RestHighLevelClient client) {
        return getCat(client, "/_cat/master?format=json", TypeToken.getParameterized(List.class, Master4Cat.class));
    }

    public List<Nodes4Cat> getCatNodes(@NonNull RestHighLevelClient client) {
        return getCat(client, "/_cat/nodes?format=json", TypeToken.getParameterized(List.class, Nodes4Cat.class));
    }

    public List<Tasks4Cat> getCatTasks(@NonNull RestHighLevelClient client) {
        return getCat(client, "/_cat/tasks?format=json", TypeToken.getParameterized(List.class, Tasks4Cat.class));
    }

    @SneakyThrows
    public List<Indices4Cat> getCatIndices(@NonNull RestHighLevelClient client) {
        return getCat(client, "/_cat/indices?format=json", TypeToken.getParameterized(List.class, Indices4Cat.class));
    }

    public List<Indices4Cat> getCatIndices(@NonNull RestHighLevelClient client, @NonNull String index) {
        return getCat(client, String.format("/_cat/indices/%s?format=json", index), TypeToken.getParameterized(List.class, Indices4Cat.class));
    }

    public List<Segments4Cat> getCatSegments(@NonNull RestHighLevelClient client) {
        return getCat(client, "/_cat/segments?format=json", TypeToken.getParameterized(List.class, Segments4Cat.class));
    }

    public List<Segments4Cat> getCatSegments(@NonNull RestHighLevelClient client, @NonNull String index) {
        return getCat(client, String.format("/_cat/segments/%s?format=json", index), TypeToken.getParameterized(List.class, Segments4Cat.class));
    }

    public List<Count4Cat> getCatCount(@NonNull RestHighLevelClient client) {
        return getCat(client, "/_cat/count?format=json", TypeToken.getParameterized(List.class, Count4Cat.class));
    }

    public List<Count4Cat> getCatCount(@NonNull RestHighLevelClient client, @NonNull String index) {
        return getCat(client, String.format("/_cat/count/%s?format=json", index), TypeToken.getParameterized(List.class, Count4Cat.class));
    }

    public List<Recovery4Cat> getCatRecovery(@NonNull RestHighLevelClient client) {
        return getCat(client, "/_cat/recovery?format=json", TypeToken.getParameterized(List.class, Recovery4Cat.class));
    }

    public List<Recovery4Cat> getCatRecovery(@NonNull RestHighLevelClient client, @NonNull String index) {
        return getCat(client, String.format("/_cat/recovery/%s?format=json", index), TypeToken.getParameterized(List.class, Recovery4Cat.class));
    }

    public List<Health4Cat> getCatHealth(@NonNull RestHighLevelClient client) {
        return getCat(client, "/_cat/health?format=json", TypeToken.getParameterized(List.class, Health4Cat.class));
    }

    public List<PendingTasks4Cat> getCatPendingTasks(@NonNull RestHighLevelClient client) {
        return null;
    }

    public List<Aliases4Cat> getCatAliases(@NonNull RestHighLevelClient client) {
        return null;
    }

    public List<Aliases4Cat> getCatAliases(@NonNull RestHighLevelClient client, @NonNull String alias) {
        return null;
    }

    public List<ThreadPool4Cat> getCatThreadPool(@NonNull RestHighLevelClient client) {
        return null;
    }

    public List<ThreadPool4Cat> getCatThreadPool(@NonNull RestHighLevelClient client, @NonNull String threadPools) {
        return null;
    }

    public List<Plugins4Cat> getCatPlugins(@NonNull RestHighLevelClient client) {
        return null;
    }

    public List<FieldData4Cat> getCatFieldData(@NonNull RestHighLevelClient client) {
        return null;
    }

    public List<FieldData4Cat> getCatFieldData(@NonNull RestHighLevelClient client, @NonNull String fields) {
        return null;
    }

    public List<NodeAttrs4Cat> getCatNodeAttrs(@NonNull RestHighLevelClient client) {
        return null;
    }

    public List<Repositories4Cat> getCatRepositories(@NonNull RestHighLevelClient client) {
        return null;
    }

    public List<Snapshots4Cat> getCatSnapshots(@NonNull RestHighLevelClient client, @NonNull String repository) {
        return null;
    }

    public List<Templates4Cat> getCatTemplates(@NonNull RestHighLevelClient client) {
        return null;
    }

    public List<MlAnomalyDetectors4Cat> getCatMlAnomalyDetectors(@NonNull RestHighLevelClient client) {
        return null;
    }

    public List<MlAnomalyDetectors4Cat> getCatMlAnomalyDetectors(@NonNull RestHighLevelClient client, @NonNull String jobId) {
        return null;
    }

    public List<MlTrainedModels4Cat> getCatMlTrainedModels(@NonNull RestHighLevelClient client) {
        return null;
    }

    public List<MlTrainedModels4Cat> getCatMlTrainedModels(@NonNull RestHighLevelClient client, @NonNull String modelId) {
        return null;
    }

    public List<MlDataFeeds4Cat> getCatMlDataFeeds(@NonNull RestHighLevelClient client) {
        return null;
    }

    public List<MlDataFeeds4Cat> getCatMlDataFeeds(@NonNull RestHighLevelClient client, @NonNull String datafeedId) {
        return null;
    }

    public List<MlDataFrameAnalytics4Cat> getCatMlDataFrameAnalytics(@NonNull RestHighLevelClient client) {
        return null;
    }

    public List<MlDataFrameAnalytics4Cat> getCatMlDataFrameAnalytics(@NonNull RestHighLevelClient client, @NonNull String id) {
        return null;
    }

    public List<Transforms4Cat> getCatTransforms(@NonNull RestHighLevelClient client) {
        return null;
    }

    public List<Transforms4Cat> getCatTransforms(@NonNull RestHighLevelClient client, @NonNull String transformId) {
        return null;
    }

    @SneakyThrows
    private <T> T getCat(@NonNull RestHighLevelClient client, @NonNull String url, @NonNull TypeToken<?> typeToken) {
        return GsonUtilsPlus.getGSON().fromJson(new JsonReader(new BufferedReader(new InputStreamReader(client.getLowLevelClient().performRequest(new Request("GET", url)).getEntity().getContent(), StandardCharsets.UTF_8))), typeToken.getType());
    }

    private static class SingletonHolder {
        private static final CatSupporter INSTANCE = new CatSupporter();
    }
}