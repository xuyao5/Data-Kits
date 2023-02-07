package io.github.xuyao5.dkl.eskits.service.config;

import io.github.xuyao5.dkl.eskits.consts.DisruptorThresholdConst;
import lombok.Data;

/**
 * @author Thomas.XU(xuyao)
 * @version 18/07/22 22:24
 */
@Data(staticConstructor = "of")
public final class Db2DbConfig {

    private int bufferSize = DisruptorThresholdConst.BUFFER_SIZE.getThreshold();

    private int threads = Runtime.getRuntime().availableProcessors();

    private int threshold = DisruptorThresholdConst.BATCH_SIZE.getThreshold();
}