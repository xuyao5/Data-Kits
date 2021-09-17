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

    @SneakyThrows
    public List<Indices4Cat> getCatIndices(@NonNull RestHighLevelClient client, String index) {
        return GsonUtilsPlus.getGSON().fromJson(new JsonReader(new BufferedReader(new InputStreamReader(client.getLowLevelClient().performRequest(new Request("GET", "/_cat/indices?format=json")).getEntity().getContent(), StandardCharsets.UTF_8))), TypeToken.getParameterized(List.class, Indices4Cat.class).getType());
    }

    private static class SingletonHolder {
        private static final CatSupporter INSTANCE = new CatSupporter();
    }
}
