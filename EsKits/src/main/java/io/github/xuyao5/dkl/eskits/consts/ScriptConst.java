package io.github.xuyao5.dkl.eskits.consts;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Optional;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 20/02/21 12:18
 * @apiNote ScriptConst
 * @implNote ScriptConst
 */
@RequiredArgsConstructor
public enum ScriptConst {

    PAINLESS("painless", "painless");

    @Getter
    private final String type;

    @Getter
    private final String description;

    public static Optional<ScriptConst> getScriptByType(@NotNull String type) {
        return Arrays.stream(ScriptConst.values()).filter(scriptConst -> scriptConst.type.equalsIgnoreCase(type)).findAny();
    }
}
