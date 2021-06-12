package io.github.xuyao5.dkl.eskits.support.boost;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.rollover.RolloverRequest;
import org.elasticsearch.client.indices.rollover.RolloverResponse;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;

import java.util.concurrent.TimeUnit;

import static org.elasticsearch.client.RequestOptions.DEFAULT;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 12/06/21 14:31
 * @apiNote RolloverSupporter
 * @implNote RolloverSupporter
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RolloverSupporter {

    public static RolloverSupporter getInstance() {
        return RolloverSupporter.SingletonHolder.INSTANCE;
    }

    /**
     * Rollover Index API
     */
    @SneakyThrows
    public RolloverResponse rollover(@NonNull RestHighLevelClient client, @NonNull String alias, @NonNull String newIndexName, @NonNull int duration, @NonNull long docsCondition, @NonNull long sizeCondition) {
        return client.indices().rollover(new RolloverRequest(alias, newIndexName)
                .addMaxIndexAgeCondition(new TimeValue(duration, TimeUnit.DAYS))
                .addMaxIndexDocsCondition(docsCondition)
                .addMaxIndexSizeCondition(new ByteSizeValue(sizeCondition, ByteSizeUnit.GB)), DEFAULT);
    }

    private static class SingletonHolder {
        private static final RolloverSupporter INSTANCE = new RolloverSupporter();
    }
}
