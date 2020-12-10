package io.github.xuyao5.dal.eskitsserver.search.param;

import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 5/05/20 14:54
 * @apiNote SearchTemplateParams
 * @implNote SearchTemplateParams
 */
@Data(staticConstructor = "of")
public class SearchTemplateParams {

    private final String[] indices;
    private String script;
    private Map<String, Object> scriptParams = new ConcurrentHashMap<>();
}
