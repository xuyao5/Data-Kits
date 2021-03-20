package io.github.xuyao5.dkl.eskits.consts;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Optional;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 19/03/21 22:04
 * @apiNote OptimizationConst
 * @implNote OptimizationConst
 */
@RequiredArgsConstructor
public enum OptimizationConst {

    DEFAULT(0);

    @Getter
    private final int value;


    public static Optional<OptimizationConst> getOptimizationByValue(@NotNull int value) {
        return Arrays.stream(OptimizationConst.values()).filter(optimizationConst -> optimizationConst.value == value).findAny();
    }
}
