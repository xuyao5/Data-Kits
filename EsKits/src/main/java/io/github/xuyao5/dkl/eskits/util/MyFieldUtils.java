package io.github.xuyao5.dkl.eskits.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
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

    public static Map<String, Class<?>> getAllClassMap(Class<?> cls) {
        return getAllFieldsList(cls).stream().collect(Collectors.toConcurrentMap(Field::getName, Field::getType));
    }
}
