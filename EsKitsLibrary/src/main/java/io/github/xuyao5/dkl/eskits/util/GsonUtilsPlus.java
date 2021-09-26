package io.github.xuyao5.dkl.eskits.util;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.io.Serializable;
import java.lang.reflect.Type;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 10/10/20 11:00
 * @apiNote GsonUtilsNZ
 * @implNote GsonUtilsNZ
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GsonUtilsPlus {

    @Getter
    private static final Gson GSON;

    static {
        GSON = new GsonBuilder()
                .enableComplexMapKeySerialization()
                .serializeSpecialFloatingPointValues()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
    }

    public static boolean isJsonString(@NonNull String json) {
        try {
            JsonElement jsonElement = JsonParser.parseString(json);
            return jsonElement.isJsonArray() || jsonElement.isJsonNull() || jsonElement.isJsonObject() || jsonElement.isJsonPrimitive();
        } catch (JsonSyntaxException ex) {
            return false;
        }
    }

    public static <T extends Serializable> String obj2Json(@NonNull T obj) {
        return GSON.toJson(obj);
    }

    public static Serializable deserialize(@NonNull String obj, @NonNull Class<?> clz) {
        return GSON.fromJson(GSON.toJson(obj), TypeToken.get(clz).getType());
    }

    public static <T extends Serializable> T json2Obj(@NonNull String json, @NonNull Class<T> clz) {
        return GSON.fromJson(json, TypeToken.get(clz).getType());
    }

    public static <T extends Serializable> T json2Obj(@NonNull String json, @NonNull Type rawType, @NonNull Type... typeArguments) {
        return GSON.fromJson(json, TypeToken.getParameterized(rawType, typeArguments).getType());
    }

    public static <T extends Serializable> T json2Obj(@NonNull JsonReader reader, @NonNull Class<T> clz) {
        return GSON.fromJson(reader, TypeToken.get(clz).getType());
    }

    public static <T extends Serializable> T json2Obj(@NonNull JsonReader reader, @NonNull Type rawType, @NonNull Type... typeArguments) {
        return GSON.fromJson(reader, TypeToken.getParameterized(rawType, typeArguments).getType());
    }
}