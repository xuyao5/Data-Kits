package io.github.xuyao5.dkl.eskits.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 5/07/20 17:19
 * @apiNote MyStringUtils
 * @implNote MyStringUtils
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MyStringUtils extends StringUtils {

    public static <T> String joining(@NotNull List<T> list, @NotNull CharSequence delimiter) {
        return list.stream().map(T::toString).collect(Collectors.joining(delimiter));
    }

    public static <T> String joining(@NotNull List<T> list, @NotNull CharSequence delimiter, @NotNull CharSequence prefix, @NotNull CharSequence suffix) {
        return list.stream().map(T::toString).collect(Collectors.joining(delimiter, prefix, suffix));
    }
}