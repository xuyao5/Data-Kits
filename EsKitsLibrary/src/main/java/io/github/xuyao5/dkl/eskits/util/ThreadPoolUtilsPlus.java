package io.github.xuyao5.dkl.eskits.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 19/09/22 14:13
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ThreadPoolUtilsPlus {

    public void run() {
        ExecutorService executor = Executors.newWorkStealingPool(Runtime.getRuntime().availableProcessors() * 2);

    }

    public void run(int threads) {
        ExecutorService executor = Executors.newWorkStealingPool(threads);

    }
}
