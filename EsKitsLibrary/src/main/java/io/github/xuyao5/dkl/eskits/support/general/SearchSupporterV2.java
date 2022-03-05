package io.github.xuyao5.dkl.eskits.support.general;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Thomas.XU(xuyao)
 * @version 1/05/20 22:48
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SearchSupporterV2 {

    public static SearchSupporterV2 getInstance() {
        return SearchSupporterV2.SingletonHolder.INSTANCE;
    }

    @SneakyThrows
    public <T> SearchResponse<T> search(@NonNull ElasticsearchClient client, @NonNull Class<T> docClass) {
        return client.search(_0 -> _0
                .query(_1 -> _1
                        .intervals(_2 -> _2
                                .field("my_text")
                                .allOf(_3 -> _3
                                        .ordered(true)
                                        .intervals(_4 -> _4
                                                .match(_5 -> _5
                                                        .query("my favorite food")
                                                        .maxGaps(0)
                                                        .ordered(true)
                                                )
                                        )
                                        .intervals(_4 -> _4
                                                .anyOf(_5 -> _5
                                                        .intervals(_6 -> _6
                                                                .match(_7 -> _7
                                                                        .query("hot water")
                                                                )
                                                        )
                                                        .intervals(_6 -> _6
                                                                .match(_7 -> _7
                                                                        .query("cold porridge")
                                                                )
                                                        )
                                                )
                                        )
                                )
                        )
                ), docClass
        );
    }

    private static class SingletonHolder {
        private static final SearchSupporterV2 INSTANCE = new SearchSupporterV2();
    }
}