package io.github.xuyao5.dkl.eskits.support.boost;

import io.github.xuyao5.dkl.eskits.schema.cat.Indices4Cat;
import io.github.xuyao5.dkl.eskits.support.general.IndexSupporter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RestHighLevelClient;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 28/09/21 19:56
 * @apiNote CleaningSupporter
 * @implNote CleaningSupporter
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CleaningSupporter {

    public static CleaningSupporter getInstance() {
        return CleaningSupporter.SingletonHolder.INSTANCE;
    }

    public List<AcknowledgedResponse> deleteClosedIndex(@NonNull RestHighLevelClient client, @NonNull String index, Predicate<Indices4Cat> predicate) {
        return CatSupporter.getInstance().getCatIndices(client, index).stream()
                .filter(indices4Cat -> predicate.test(indices4Cat) && "close".equals(indices4Cat.getStatus()))
                .map(indices4Cat -> IndexSupporter.getInstance().delete(client, indices4Cat.getIndex()))
                .collect(Collectors.toList());
    }

    private static class SingletonHolder {
        private static final CleaningSupporter INSTANCE = new CleaningSupporter();
    }
}