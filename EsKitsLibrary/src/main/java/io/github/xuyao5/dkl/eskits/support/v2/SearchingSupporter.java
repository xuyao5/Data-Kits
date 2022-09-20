package io.github.xuyao5.dkl.eskits.support.v2;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.util.ObjectBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Thomas.XU(xuyao)
 * @version 1/05/20 22:48
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SearchingSupporter {

    public static SearchingSupporter getInstance() {
        return SearchingSupporter.SingletonHolder.INSTANCE;
    }

    @SneakyThrows
    @SuppressWarnings("unchecked")
    public <T> List<T> search(@NonNull ElasticsearchClient client, int from, int size, Function<Query.Builder, ObjectBuilder<Query>> query) {
        return client.search(builder -> builder.query(query).from(from).size(size), (Class<T>) new TypeToken<T>() {
        }.getRawType()).hits().hits().parallelStream().map(Hit::source).collect(Collectors.toCollection(LinkedList::new));
    }

    @SneakyThrows
    @SuppressWarnings("unchecked")
    public <T> List<T> search(@NonNull ElasticsearchClient client, int from, int size, Function<Query.Builder, ObjectBuilder<Query>> query, Function<SortOptions.Builder, ObjectBuilder<SortOptions>> sort) {
        return client.search(builder -> builder.query(query).from(from).size(size).sort(sort), (Class<T>) new TypeToken<T>() {
        }.getRawType()).hits().hits().parallelStream().map(Hit::source).collect(Collectors.toCollection(LinkedList::new));
    }

    private static class SingletonHolder {
        private static final SearchingSupporter INSTANCE = new SearchingSupporter();
    }
}