package io.github.xuyao5.dkl.eskits.consts;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author Thomas.XU(xuyao)
 * @version 3/02/23 15:10
 */
@RequiredArgsConstructor
public enum DisruptorThresholdConst {

    BUFFER_SIZE(8_192), //Disruptor BufferSize
    BATCH_SIZE(1_000);//AbstractBatchEventHandler BatchRemaining

    @Getter
    private final int threshold;

    public static Optional<DisruptorThresholdConst> getThreshold(int threshold) {
        return Arrays.stream(DisruptorThresholdConst.values()).filter(disruptorThreshold -> disruptorThreshold.threshold == threshold).findFirst();
    }
}