package io.github.xuyao5.dkl.eskits.client;

import org.elasticsearch.client.RestHighLevelClient;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 1/05/20 22:45
 * @apiNote EsClient
 * @implNote EsClient
 */
public interface EsClient {

    <T> T invokeFunction(Function<RestHighLevelClient, T> function);

    void invokeConsumer(Consumer<RestHighLevelClient> consumer);

    int hostsCount();
}