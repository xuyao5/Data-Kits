package io.github.xuyao5.dkl.eskits.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 5/07/20 17:19
 * @apiNote MyStringUtils
 * @implNote MyStringUtils
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StringUtilsNZ {

    public static <T> String joining(@NonNull List<T> list, @NonNull CharSequence delimiter) {
        return list.stream().map(T::toString).collect(Collectors.joining(delimiter));
    }

    public static <T> String joining(@NonNull List<T> list, @NonNull CharSequence delimiter, @NonNull CharSequence prefix, @NonNull CharSequence suffix) {
        return list.stream().map(T::toString).collect(Collectors.joining(delimiter, prefix, suffix));
    }
}