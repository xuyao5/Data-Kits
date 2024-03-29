package io.github.xuyao5.dkl.eskits.service.config;

import io.github.xuyao5.dkl.eskits.consts.DisruptorThresholdConst;
import lombok.Data;
import org.elasticsearch.index.query.QueryBuilder;

/**
 * @author Thomas.XU(xuyao)
 * @version 16/03/21 02:52
 */
@Data(staticConstructor = "of")
public final class ModifyByScrollConfig {

    private final String index;

    private QueryBuilder queryBuilder;

    private int bufferSize = DisruptorThresholdConst.BUFFER_SIZE.getThreshold();
}
