package io.github.xuyao5.dkl.eskits.util;

import com.google.gson.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Thomas.XU(xuyao)
 * @version 7/03/21 21:09
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FieldUtilsPlus {

    public static Map<String, Type> getDeclaredFieldsMap(@NonNull Class<?> clz) {
        return Arrays.stream(clz.getDeclaredFields()).collect(Collectors.toConcurrentMap(Field::getName, Field::getGenericType));
    }

    public static Map<String, Type> getDeclaredFieldsMap(@NonNull Type type) {
        return getDeclaredFieldsMap(TypeToken.get(type).getRawType());
    }

    public static boolean isSameType(@NonNull TypeToken<?> typeToken, @NonNull Type type) {
        return typeToken.getType().equals(type);
    }
}