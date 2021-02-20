package io.github.xuyao5.dkl.eskits.consts;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Optional;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 20/02/21 12:10
 * @apiNote ConflictsConst
 * @implNote ConflictsConst
 */
@RequiredArgsConstructor
public enum ConflictsConst {

    PROCEED("proceed", "proceed"),
    ABORT("abort", "abort");

    @Getter
    private final String type;

    @Getter
    private final String description;

    public static Optional<ConflictsConst> getConflictsConstByType(@NotNull String type) {
        return Arrays.stream(ConflictsConst.values()).filter(conflictsConst -> conflictsConst.type.equalsIgnoreCase(type)).findAny();
    }
}
