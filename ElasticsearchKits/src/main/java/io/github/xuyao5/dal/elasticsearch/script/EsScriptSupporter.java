package io.github.xuyao5.dal.elasticsearch.script;

import io.github.xuyao5.dal.elasticsearch.abstr.AbstractSupporter;
import io.github.xuyao5.dal.elasticsearch.consts.ScriptConst;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;

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
        return FileUtils.readFileToString(ResourceUtils.getFile(scriptConst.getPath()), StandardCharsets.UTF_8);
    }
}
