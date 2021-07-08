package io.github.xuyao5.dkl.eskits.service;

import io.github.xuyao5.dkl.eskits.context.AbstractExecutor;
import io.github.xuyao5.dkl.eskits.schema.base.BaseDocument;
import io.github.xuyao5.dkl.eskits.service.config.Universal2EsConfig;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.Serializable;
import java.util.function.Function;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 12/06/21 17:01
 * @apiNote Universal2EsService
 * @implNote Universal2EsService
 */
@Slf4j
public final class Universal2EsService extends AbstractExecutor {

    public Universal2EsService(@NonNull RestHighLevelClient client) {
        super(client);
    }

    public <T extends Serializable, R extends BaseDocument> void execute(@NonNull Universal2EsConfig config, Function<T, R> operator) {

    }
}