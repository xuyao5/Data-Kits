package io.github.xuyao5.dkl.eskits.consts;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Optional;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 20/02/21 12:10
 * @apiNote ConflictConst
 * @implNote ConflictConst
 */
@RequiredArgsConstructor
public enum ConflictConst {

    PROCEED("proceed"),
    ABORT("abort");

    @Getter
    private final String type;

    public static Optional<ConflictConst> getConflictByType(@NotNull String type) {
        return Arrays.stream(ConflictConst.values()).filter(conflictConst -> conflictConst.type.equalsIgnoreCase(type)).findAny();
    }
}
