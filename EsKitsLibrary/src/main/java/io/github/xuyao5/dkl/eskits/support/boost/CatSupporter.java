package io.github.xuyao5.dkl.eskits.support.boost;

import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import io.github.xuyao5.dkl.eskits.schema.cat.Indices4Cat;
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

    public void getCatAllocation(@NonNull RestHighLevelClient client) {
    }

    public void getCatShards(@NonNull RestHighLevelClient client) {
    }

    public void getCatShards(@NonNull RestHighLevelClient client, @NonNull String index) {
    }

    public void getCatMaster(@NonNull RestHighLevelClient client) {
    }

    public void getCatNodes(@NonNull RestHighLevelClient client) {
    }

    public void getCatTasks(@NonNull RestHighLevelClient client) {
    }

    @SneakyThrows
    public List<Indices4Cat> getCatIndices(@NonNull RestHighLevelClient client) {
        return getCat(client, "/_cat/indices?format=json", TypeToken.getParameterized(List.class, Indices4Cat.class));
    }

    public List<Indices4Cat> getCatIndices(@NonNull RestHighLevelClient client, @NonNull String index) {
        return getCat(client, String.format("/_cat/indices/%s?format=json", index), TypeToken.getParameterized(List.class, Indices4Cat.class));
    }

    public void getCatSegments(@NonNull RestHighLevelClient client) {
    }

    public void getCatSegments(@NonNull RestHighLevelClient client, @NonNull String index) {
    }

    public void getCatCount(@NonNull RestHighLevelClient client) {
    }

    public void getCatCount(@NonNull RestHighLevelClient client, @NonNull String index) {
    }

    public void getCatRecovery(@NonNull RestHighLevelClient client) {
    }

    public void getCatRecovery(@NonNull RestHighLevelClient client, @NonNull String index) {
    }

    public void getCatHealth(@NonNull RestHighLevelClient client) {
    }

    public void getCatPending_tasks(@NonNull RestHighLevelClient client) {
    }

    public void getCatAliases(@NonNull RestHighLevelClient client) {
    }

    public void getCatAliases(@NonNull RestHighLevelClient client, @NonNull String alias) {
    }

    public void getCatThread_pool(@NonNull RestHighLevelClient client) {
    }

    public void getCatThread_pool(@NonNull RestHighLevelClient client, @NonNull String thread_pools) {
    }

    public void getCatPlugins(@NonNull RestHighLevelClient client) {
    }

    public void getCatFielddata(@NonNull RestHighLevelClient client) {
    }

    public void getCatFielddata(@NonNull RestHighLevelClient client, @NonNull String fields) {
    }

    public void getCatNodeattrs(@NonNull RestHighLevelClient client) {
    }

    public void getCatRepositories(@NonNull RestHighLevelClient client) {
    }

    public void getCatSnapshots(@NonNull RestHighLevelClient client, @NonNull String repository) {
    }

    public void getCatTemplates(@NonNull RestHighLevelClient client) {
    }

    public void getCatMlAnomaly_detectors(@NonNull RestHighLevelClient client) {
    }

    public void getCatMlAnomaly_detectors(@NonNull RestHighLevelClient client, @NonNull String job_id) {
    }

    public void getCatMlTrained_models(@NonNull RestHighLevelClient client) {
    }

    public void getCatMlTrainedModels(@NonNull RestHighLevelClient client, @NonNull String model_id) {
    }

    public void getCatMlDatafeeds(@NonNull RestHighLevelClient client) {
    }

    public void getCatMlDatafeeds(@NonNull RestHighLevelClient client, @NonNull String datafeed_id) {
    }

    public void getCatMlDataFrameAnalytics(@NonNull RestHighLevelClient client) {
    }

    public void getCatMlDataFrameAnalytics(@NonNull RestHighLevelClient client, @NonNull String id) {
    }

    public void getCatTransforms(@NonNull RestHighLevelClient client) {
    }

    public void getCatTransforms(@NonNull RestHighLevelClient client, @NonNull String transformId) {
    }

    @SneakyThrows
    private <T> T getCat(@NonNull RestHighLevelClient client, @NonNull String url, @NonNull TypeToken<?> typeToken) {
        return GsonUtilsPlus.getGSON().fromJson(new JsonReader(new BufferedReader(new InputStreamReader(client.getLowLevelClient().performRequest(new Request("GET", url)).getEntity().getContent(), StandardCharsets.UTF_8))), typeToken.getType());
    }

    private static class SingletonHolder {
        private static final CatSupporter INSTANCE = new CatSupporter();
    }
}