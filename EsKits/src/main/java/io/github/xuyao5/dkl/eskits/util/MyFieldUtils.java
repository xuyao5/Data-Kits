package io.github.xuyao5.dkl.eskits.util;

import com.google.gson.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 7/03/21 21:09
 * @apiNote MyFieldUtils
 * @implNote MyFieldUtils
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MyFieldUtils extends FieldUtils {

    public static Map<String, Type> getDeclaredFieldsMap(Class<?> clz) {
        return Arrays.stream(clz.getDeclaredFields()).collect(Collectors.toConcurrentMap(Field::getName, Field::getGenericType));
    }

    public static Map<String, Type> getDeclaredFieldsMap(Type type) {
        return getDeclaredFieldsMap(TypeToken.get(type).getRawType());
    }

    public static <T> Map<String, Type> getDeclaredFieldsMap(T obj) {
        return getDeclaredFieldsMap(obj.getClass());
    }

    public static boolean isSameType(TypeToken<?> typeToken, Type type) {
        return typeToken.getType().equals(type);
    }
}