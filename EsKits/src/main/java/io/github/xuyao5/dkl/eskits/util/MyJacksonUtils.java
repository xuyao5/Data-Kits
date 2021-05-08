package io.github.xuyao5.dkl.eskits.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 8/05/21 21:52
 * @apiNote MyJacksonUtils
 * @implNote MyJacksonUtils
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MyJacksonUtils {

    private static final ObjectMapper JACKSON;

    static {
        JACKSON = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ROOT));
    }

    @SneakyThrows
    public static <T extends Serializable> String obj2Json(@NotNull T obj) {
        return JACKSON.writeValueAsString(obj);
    }

    @SneakyThrows
    public static <T extends Serializable> T json2Obj(@NotNull String json, @NotNull Class<T> clz) {
        return JACKSON.readValue(json, clz);
    }

    @SneakyThrows
    public static <T extends Serializable> T json2Obj(@NotNull String json, @NotNull TypeReference<T> typeRef) {
        return JACKSON.readValue(json, typeRef);
    }
}
