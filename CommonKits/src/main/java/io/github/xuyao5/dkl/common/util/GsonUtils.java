package io.github.xuyao5.dkl.common.util;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Type;
import java.util.Optional;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 10/10/20 11:00
 * @apiNote MyGsonUtils
 * @implNote MyGsonUtils
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GsonUtils {

    @Getter
    private static final Gson GSON;

    static {
        GSON = new GsonBuilder()
                .enableComplexMapKeySerialization()
                .serializeSpecialFloatingPointValues()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .setPrettyPrinting()
                .setVersion(1.0)
                .create();
    }

    public static boolean isJsonString(@NotNull String json) {
        try {
            JsonElement jsonElement = JsonParser.parseString(json);
            return jsonElement.isJsonArray() || jsonElement.isJsonNull() || jsonElement.isJsonObject() || jsonElement.isJsonPrimitive();
        } catch (JsonSyntaxException ex) {
            return false;
        }
    }

    public static <T> Optional<String> obj2Json(@NotNull T obj) {
        return Optional.ofNullable(GSON.toJson(obj));
    }

    public static <T> Optional<String> obj2Json(@NotNull T obj, @NotNull Type type) {
        return Optional.ofNullable(GSON.toJson(obj, type));
    }

    public static <T> Optional<T> json2Obj(@NotNull String json, @NotNull Type type) {
        return Optional.ofNullable(GSON.fromJson(json, type));
    }

    public static <T> Optional<T> json2Obj(@NotNull String json, @NotNull Class<T> clz) {
        return Optional.ofNullable(GSON.fromJson(json, clz));
    }

    public static <T> Optional<T> json2Obj(@NotNull String json, @NotNull TypeToken<T> typeToken) {
        return Optional.ofNullable(GSON.fromJson(json, typeToken.getType()));
    }
}