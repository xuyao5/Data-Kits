package io.github.xuyao5.dkl.eskits.service;

import com.lmax.disruptor.EventFactory;
import io.github.xuyao5.dkl.eskits.context.AbstractExecutor;
import io.github.xuyao5.dkl.eskits.schema.base.BaseDocument;
import io.github.xuyao5.dkl.eskits.service.config.Db2EsConfig;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.ResultHandler;
import org.elasticsearch.client.RestHighLevelClient;

import java.util.function.Consumer;
import java.util.function.UnaryOperator;

/**
 * @author Thomas.XU(xuyao)
 * @version 18/07/22 22:22
 */
@Slf4j
public final class Db2EsService<T, R extends BaseDocument> extends AbstractExecutor {

    public Db2EsService(RestHighLevelClient client) {
        super(client);
    }

    public void execute(@NonNull Db2EsConfig config, EventFactory<T> rowFactory, EventFactory<R> documentFactory, Consumer<ResultHandler<T>> rowMapper, UnaryOperator<R> operator) {

    }
}