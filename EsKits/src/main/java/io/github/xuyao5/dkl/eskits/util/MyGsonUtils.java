package io.github.xuyao5.dkl.eskits.util;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

import static org.apache.commons.lang3.time.DateFormatUtils.ISO_8601_EXTENDED_DATETIME_TIME_ZONE_FORMAT;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 10/10/20 11:00
 * @apiNote MyGsonUtils
 * @implNote MyGsonUtils
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MyGsonUtils {

    @Getter
    private static final Gson GSON;

    static {
        GSON = new GsonBuilder()
                .enableComplexMapKeySerialization()
                .serializeSpecialFloatingPointValues()
                .setDateFormat(ISO_8601_EXTENDED_DATETIME_TIME_ZONE_FORMAT.getPattern())
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .setPrettyPrinting()
                .setVersion(1.0)
                .registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (json, typeOfT, context) -> new Date(json.getAsJsonPrimitive().getAsLong()))
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

    public static <T extends Serializable> String obj2Json(@NotNull T obj) {
        return GSON.toJson(obj, new TypeToken<T>() {
        }.getType());
    }

    public static <T extends Serializable> T json2Obj(@NotNull String json) {
        return GSON.fromJson(json, new TypeToken<T>() {
        }.getType());
    }
}