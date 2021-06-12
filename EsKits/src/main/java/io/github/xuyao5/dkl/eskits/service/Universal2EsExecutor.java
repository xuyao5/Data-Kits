package io.github.xuyao5.dkl.eskits.service;

import com.lmax.disruptor.EventFactory;
import io.github.xuyao5.dkl.eskits.context.AbstractExecutor;
import io.github.xuyao5.dkl.eskits.service.config.Universal2EsConfig;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;

import java.util.function.UnaryOperator;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 12/06/21 17:01
 * @apiNote Universal2EsExecutor
 * @implNote Universal2EsExecutor
 */
@Slf4j
public final class Universal2EsExecutor extends AbstractExecutor {

    public Universal2EsExecutor(@NonNull RestHighLevelClient client) {
        super(client);
    }

    public <T> void execute(@NonNull Universal2EsConfig config, EventFactory<T> document, UnaryOperator<T> operator) {

    }
}