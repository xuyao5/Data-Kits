package io.github.xuyao5.dkl.eskits.util;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

import static io.github.xuyao5.dkl.eskits.util.MyDateUtils.STD_DATETIME_FORMAT;

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
                .setDateFormat(STD_DATETIME_FORMAT.getPattern())
                .setPrettyPrinting()
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
        return GSON.toJson(obj);
    }

    public static <T extends Serializable> T json2Obj(@NotNull String json, @NotNull TypeToken<?> typeToken) {
        return GSON.fromJson(GSON.toJson(json), typeToken.getType());
    }

    public static <T extends Serializable> T json2Obj2(@NotNull String json, @NotNull TypeToken<?> typeToken) {
        return GSON.fromJson(json, typeToken.getType());
    }
}