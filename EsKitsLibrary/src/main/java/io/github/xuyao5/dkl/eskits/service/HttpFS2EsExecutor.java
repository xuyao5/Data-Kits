package io.github.xuyao5.dkl.eskits.service;

import io.github.xuyao5.dkl.eskits.context.AbstractExecutor;
import io.github.xuyao5.dkl.eskits.service.config.HttpFS2EsConfig;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 16/06/21 23:31
 * @apiNote MySQL2EsExecutor
 * @implNote MySQL2EsExecutor
 */
@Slf4j
public final class HttpFS2EsExecutor extends AbstractExecutor {

    public HttpFS2EsExecutor(RestHighLevelClient client) {
        super(client);
    }

    @SneakyThrows
    public void execute(@NonNull HttpFS2EsConfig config) {

    }
}
