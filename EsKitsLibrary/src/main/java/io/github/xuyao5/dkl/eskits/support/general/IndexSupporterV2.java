package io.github.xuyao5.dkl.eskits.support.general;

import co.elastic.clients.elasticsearch.ElasticsearchAsyncClient;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

/**
 * @author Thomas.XU(xuyao)
 * @version 1/05/20 22:48
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class IndexSupporterV2 {

    public static IndexSupporterV2 getInstance() {
        return IndexSupporterV2.SingletonHolder.INSTANCE;
    }

    @SneakyThrows
    public CreateIndexResponse create(@NonNull ElasticsearchClient client, @NonNull String index) {
        return client.indices()
                .create(createIndexBuilder -> createIndexBuilder
                        .index(index)
                        .aliases("foo", aliasBuilder -> aliasBuilder
                                .isWriteIndex(true)
                        )
                );
    }

    @SneakyThrows
    public CompletableFuture<CreateIndexResponse> create(@NonNull ElasticsearchAsyncClient client, @NonNull String index) {
        return client.indices()
                .create(createIndexBuilder -> createIndexBuilder
                        .index(index)
                        .aliases("foo", aliasBuilder -> aliasBuilder
                                .isWriteIndex(true)
                        )
                );
    }

    private static class SingletonHolder {
        private static final IndexSupporterV2 INSTANCE = new IndexSupporterV2();
    }
}