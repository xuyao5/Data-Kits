package io.github.xuyao5.dkl.eskits.consts;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author Thomas.XU(xuyao)
 * @version 17/07/21 22:15
 */
@RequiredArgsConstructor
public enum SortType {

    ASC("asc"), DESC("desc");

    @Getter
    private final String order;

    public static Optional<SortType> getSortType(@NonNull String order) {
        return Arrays.stream(SortType.values()).filter(sortType -> sortType.order.equalsIgnoreCase(order)).findFirst();
    }
}