package io.github.xuyao5.dkl.eskits.util;

import com.google.gson.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.reflect.FieldUtils;

import javax.validation.constraints.NotNull;
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

    public static <T> Map<String, Type> getDeclaredFieldsMap(T obj) {
        return Arrays.stream(obj.getClass().getDeclaredFields()).collect(Collectors.toConcurrentMap(Field::getName, Field::getGenericType));
    }

    public static boolean isSameType(@NotNull TypeToken<?> typeToken, @NotNull Type type) {
        return typeToken.getType().equals(type);
    }
}