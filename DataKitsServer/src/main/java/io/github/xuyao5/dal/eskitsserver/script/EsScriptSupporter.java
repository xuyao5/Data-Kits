package io.github.xuyao5.dal.eskitsserver.script;

import io.github.xuyao5.dal.eskitsserver.abstr.AbstractSupporter;
import io.github.xuyao5.dal.eskitsserver.consts.ScriptConst;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 27/06/20 20:15
 * @apiNote EsScriptSupporter
 * @implNote EsScriptSupporter
 */
@Component("esScriptSupporter")
public final class EsScriptSupporter extends AbstractSupporter {

    @SneakyThrows
    public String getScript(@NotNull ScriptConst scriptConst) {
        return "";
//        return MyFileUtils.readFileToString(ResourceUtils.getFile(scriptConst.getPath()), StandardCharsets.UTF_8);
    }
}
