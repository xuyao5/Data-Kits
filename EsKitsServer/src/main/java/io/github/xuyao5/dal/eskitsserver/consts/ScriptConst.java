package io.github.xuyao5.dal.eskitsserver.consts;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Optional;

import static org.springframework.util.ResourceUtils.CLASSPATH_URL_PREFIX;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 27/06/20 20:18
 * @apiNote TODO 这里输入文件说明
 * @implNote TODO 这里输入实现说明
 */
@RequiredArgsConstructor
public enum ScriptConst {

    MATCH_ALL("MatchAll", CLASSPATH_URL_PREFIX + "script/search/MatchAll.json"),
    ;

    @Getter
    private final String code;

    @Getter
    private final String path;

    public static Optional<ScriptConst> getScriptConstByCode(@NotNull String code) {
        return Arrays.stream(ScriptConst.values()).parallel()
                .filter(scriptConst -> scriptConst.code.equalsIgnoreCase(code))
                .findAny();
    }
}
