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

    public void getCatAllocation() {
    }

    public void getCatShards() {
    }

    public void getCatShards(@NonNull String index) {
    }

    public void getCatMaster() {
    }

    public void getCatNodes() {
    }

    public void getCatTasks() {
    }

    @SneakyThrows
    public List<Indices4Cat> getCatIndices(@NonNull RestHighLevelClient client, String index) {
        return getCat(client, String.format("/_cat/indices/%s?format=json", index), TypeToken.getParameterized(List.class, Indices4Cat.class));
    }

    public void getCatIndices(@NonNull String index) {
    }

    public void getCatSegments() {
    }

    public void getCatSegments(@NonNull String index) {
    }

    public void getCatCount() {
    }

    public void getCatCount(@NonNull String index) {
    }

    public void getCatRecovery() {
    }

    public void getCatRecovery(@NonNull String index) {
    }

    public void getCatHealth() {
    }

    public void getCatPending_tasks() {
    }

    public void getCatAliases() {
    }

    public void getCatAliases(@NonNull String alias) {
    }

    public void getCatThread_pool() {
    }

    public void getCatThread_pool(@NonNull String thread_pools) {
    }

    public void getCatPlugins() {
    }

    public void getCatFielddata() {
    }

    public void getCatFielddata(@NonNull String fields) {
    }

    public void getCatNodeattrs() {
    }

    public void getCatRepositories() {
    }

    public void getCatSnapshots(@NonNull String repository) {
    }

    public void getCatTemplates() {
    }

    public void getCatMlAnomaly_detectors() {
    }

    public void getCatMlAnomaly_detectors(@NonNull String job_id) {
    }

    public void getCatMlTrained_models() {
    }

    public void getCatMlTrainedModels(@NonNull String model_id) {
    }

    public void getCatMlDatafeeds() {
    }

    public void getCatMlDatafeeds(@NonNull String datafeed_id) {
    }

    public void getCatMlDataFrameAnalytics() {
    }

    public void getCatMlDataFrameAnalytics(@NonNull String id) {
    }

    public void getCatTransforms() {
    }

    public void getCatTransforms(@NonNull String transformId) {
    }

    @SneakyThrows
    private <T> T getCat(@NonNull RestHighLevelClient client, @NonNull String url, @NonNull TypeToken<?> typeToken) {
        return GsonUtilsPlus.getGSON().fromJson(new JsonReader(new BufferedReader(new InputStreamReader(client.getLowLevelClient().performRequest(new Request("GET", url)).getEntity().getContent(), StandardCharsets.UTF_8))), typeToken.getType());
    }

    private static class SingletonHolder {
        private static final CatSupporter INSTANCE = new CatSupporter();
    }
}