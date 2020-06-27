package io.github.xuyao5.dal.elasticsearch.consts;

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

    MATCH_ALL(CLASSPATH_URL_PREFIX + "script/search/MatchAll.json", "MatchAll"),
    ;

    @Getter
    private final String path;

    @Getter
    private final String description;

    public static Optional<ScriptConst> getFlatFileByType(@NotNull String path) {
        return Arrays.stream(ScriptConst.values()).parallel()
                .filter(scriptConst -> scriptConst.path.equalsIgnoreCase(path))
                .findAny();
    }
}
