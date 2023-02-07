package io.github.xuyao5.dkl.eskits.consts;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author Thomas.XU(xuyao)
 * @version 6/02/23 13:27
 */
@RequiredArgsConstructor
public enum IndexStatusConst {

    CLOSE("close");

    @Getter
    private final String status;

    public static Optional<IndexStatusConst> getStatusByKey(@NonNull String key) {
        return Arrays.stream(IndexStatusConst.values()).filter(indexStatusConst -> indexStatusConst.status.equalsIgnoreCase(key)).findFirst();
    }
}