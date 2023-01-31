package io.github.xuyao5.dkl.eskits.service;

import com.lmax.disruptor.EventFactory;
import io.github.xuyao5.dkl.eskits.context.AbstractExecutor;
import io.github.xuyao5.dkl.eskits.context.DisruptorBoost;
import io.github.xuyao5.dkl.eskits.context.handler.AbstractBatchEventHandler;
import io.github.xuyao5.dkl.eskits.schema.base.BaseDocument;
import io.github.xuyao5.dkl.eskits.service.config.Db2EsConfig;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.ibatis.session.ResultHandler;
import org.elasticsearch.client.RestHighLevelClient;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/**
 * @author Thomas.XU(xuyao)
 * @version 18/07/22 22:22
 */
@Slf4j
public final class Db2EsService<T, R extends BaseDocument> extends AbstractExecutor {

    public Db2EsService(RestHighLevelClient client) {
        super(client);
    }

    public int execute(@NonNull Db2EsConfig config, EventFactory<T> rowFactory, EventFactory<R> documentFactory, Consumer<ResultHandler<T>> rowMapper, AbstractBatchEventHandler<T> batchEventHandler) {
        //执行计数器
        AtomicInteger count = new AtomicInteger();

        DisruptorBoost.<T>context().defaultBufferSize(config.getBufferSize()).create().processZeroArgEvent(rowFactory,
                //事件生产
                translator -> rowMapper.accept(resultContext -> translator.translate((t, sequence) -> {
                    try {
                        BeanUtils.copyProperties(t, resultContext.getResultObject());
                        count.set(resultContext.getResultCount());
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        log.error("Db2DbService#execute#AbstractSequenceReporting", e);
                    }
                })),
                //错误处理
                (order, sequence) -> log.error("Db2EsService#execute#AbstractSequenceReporting Error:{}|{}", sequence, order),
                //事件消费
                batchEventHandler);

        return count.intValue();
    }
}