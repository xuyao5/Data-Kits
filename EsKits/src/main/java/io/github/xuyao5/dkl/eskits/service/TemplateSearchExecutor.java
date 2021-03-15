package io.github.xuyao5.dkl.eskits.service;

import io.github.xuyao5.dkl.eskits.abstr.AbstractExecutor;
import io.github.xuyao5.dkl.eskits.client.EsClient;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 15/03/21 20:57
 * @apiNote TemplateSearchExecutor
 * @implNote TemplateSearchExecutor
 */
@Slf4j
public final class TemplateSearchExecutor extends AbstractExecutor {

    public TemplateSearchExecutor(EsClient esClient) {
        super(esClient);
    }
}
