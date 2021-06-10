package io.github.xuyao5.dkl.eskits.service;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import io.github.xuyao5.dkl.eskits.context.AbstractExecutor;
import io.github.xuyao5.dkl.eskits.service.config.JoinSearchConfig;
import io.github.xuyao5.dkl.eskits.support.batch.ScrollSupporter;
import io.github.xuyao5.dkl.eskits.support.general.DocumentSupporter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.elasticsearch.action.get.MultiGetRequest;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static org.elasticsearch.index.reindex.AbstractBulkByScrollRequest.DEFAULT_SCROLL_SIZE;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 28/03/21 21:34
 * @apiNote JoinSearchExecutor
 * @implNote JoinSearchExecutor
 */
@Slf4j
public final class JoinSearchExecutor extends AbstractExecutor {

    private final int scrollSize;

    public JoinSearchExecutor(@NonNull RestHighLevelClient client, int size) {
        super(client);
        scrollSize = size;
    }

    public JoinSearchExecutor(@NonNull RestHighLevelClient client) {
        this(client, DEFAULT_SCROLL_SIZE);
    }

    public void innerJoin(@NonNull JoinSearchConfig config, Consumer<List<String>> consumer) {
        DocumentSupporter documentSupporter = DocumentSupporter.getInstance();

        final Disruptor<List<String>> disruptor = new Disruptor<>(Lists::newArrayList, RING_SIZE, DaemonThreadFactory.INSTANCE, ProducerType.SINGLE, new BlockingWaitStrategy());

        disruptor.handleEventsWith((idList, sequence, endOfBatch) -> {
            List<MultiGetRequest.Item> collect = idList.stream().map(id -> new MultiGetRequest.Item(config.getIndex2(), id)).collect(Collectors.toList());
            MultiGetResponse multiGetItemResponses = documentSupporter.multiGet(client, collect);
            System.out.println(multiGetItemResponses.getResponses().length);
        });

        publish(disruptor, QueryBuilders.matchAllQuery(), config.getIndex1());
    }

    private void publish(@NonNull Disruptor<List<String>> disruptor, @NonNull QueryBuilder queryBuilder, @NonNull String index) {
        RingBuffer<List<String>> ringBuffer = disruptor.start();
        try {
            ScrollSupporter.getInstance().scroll(client, searchHits -> ringBuffer.publishEvent((idList, sequence, ids) -> idList.addAll(ids), Arrays.stream(searchHits).map(SearchHit::getId).collect(Collectors.toList())), queryBuilder, scrollSize, index);
        } finally {
            disruptor.shutdown();
        }
    }
}
