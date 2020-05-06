package io.github.xuyao5.dal.elasticsearch.index.param;

import lombok.Data;

import java.util.Optional;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 5/05/20 11:50
 * @apiNote CreateIndexParams
 * @implNote CreateIndexParams
 */
@Data(staticConstructor = "of")
public final class CreateIndexParams {

    private final String index;

    private Optional<Integer> numberOfShards = Optional.empty();
    private Optional<Integer> numberOfReplicas = Optional.empty();
}
