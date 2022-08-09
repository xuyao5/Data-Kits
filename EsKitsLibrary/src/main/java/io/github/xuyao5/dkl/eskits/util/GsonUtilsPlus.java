package io.github.xuyao5.dkl.eskits.util;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.common.geo.GeoPoint;

import java.lang.reflect.Type;

/**
 * @author Thomas.XU(xuyao)
 * @version 10/10/20 11:00
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
                .registerTypeAdapter(GeoPoint.class, new GeoPointJsonDeserializer())
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

    public static <T> String obj2Json(@NonNull T obj) {
        return GSON.toJson(obj);
    }

    public static Object deserialize(@NonNull String obj, @NonNull Class<?> clz) {
        return GSON.fromJson(GSON.toJson(obj), TypeToken.get(clz).getType());
    }

    public static <T> T json2Obj(@NonNull String json) {
        return GSON.fromJson(json, new TypeToken<T>() {
        }.getType());
    }

    public static <T> T json2Obj(@NonNull String json, @NonNull Class<T> clz) {
        return GSON.fromJson(json, TypeToken.get(clz).getType());
    }

    public static <T> T json2Obj(@NonNull String json, @NonNull Type rawType, @NonNull Type... typeArguments) {
        return GSON.fromJson(json, TypeToken.getParameterized(rawType, typeArguments).getType());
    }

    public static <T> T json2Obj(@NonNull JsonReader reader, @NonNull Class<T> clz) {
        return GSON.fromJson(reader, TypeToken.get(clz).getType());
    }

    public static <T> T json2Obj(@NonNull JsonReader reader, @NonNull Type rawType, @NonNull Type... typeArguments) {
        return GSON.fromJson(reader, TypeToken.getParameterized(rawType, typeArguments).getType());
    }

    static class GeoPointJsonDeserializer implements JsonDeserializer<GeoPoint> {

        @Override
        public GeoPoint deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            if (GeoPoint.class.equals(type)) {//这个仅仅是防御性编程
                if (jsonElement.isJsonPrimitive() && jsonElement.getAsJsonPrimitive().isString() && StringUtils.isNotBlank(jsonElement.getAsString())) {
                    return new GeoPoint(jsonElement.getAsString());
                } else if (jsonElement.isJsonArray() && jsonElement.getAsJsonArray().size() == 2) {
                    JsonElement longitude = jsonElement.getAsJsonArray().get(0);
                    JsonElement latitude = jsonElement.getAsJsonArray().get(1);
                    if (longitude.isJsonPrimitive() && longitude.getAsJsonPrimitive().isNumber()
                            && latitude.isJsonPrimitive() && latitude.getAsJsonPrimitive().isNumber()) {
                        return new GeoPoint(latitude.getAsDouble(), longitude.getAsDouble());
                    }
                } else if (jsonElement.isJsonObject() && jsonElement.getAsJsonObject().has("lat") && jsonElement.getAsJsonObject().has("lon")) {
                    JsonElement longitude = jsonElement.getAsJsonObject().get("lon");
                    JsonElement latitude = jsonElement.getAsJsonObject().get("lat");
                    if (longitude.isJsonPrimitive() && longitude.getAsJsonPrimitive().isNumber()
                            && latitude.isJsonPrimitive() && latitude.getAsJsonPrimitive().isNumber()) {
                        return new GeoPoint(latitude.getAsDouble(), longitude.getAsDouble());
                    }
                } else if (jsonElement.isJsonNull()) {
                    return new GeoPoint();
                } else {
                    return new GeoPoint();
                }
            }
            return new GeoPoint();
        }
    }
}