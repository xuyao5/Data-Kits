package io.github.xuyao5.dkl.eskits.service;

import io.github.xuyao5.dkl.eskits.abstr.AbstractExecutor;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 15/03/21 20:57
 * @apiNote TemplateSearchExecutor
 * @implNote TemplateSearchExecutor
 */
@Slf4j
public final class TemplateSearchExecutor extends AbstractExecutor {

    public TemplateSearchExecutor(RestHighLevelClient esClient) {
        super(esClient);
    }
}
