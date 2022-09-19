package io.github.xuyao5.dkl.eskits.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Thomas.XU(xuyao)
 * @version 19/09/22 14:13
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ThreadPoolUtilsPlus {

    public static void run(Runnable command) {
        run(Runtime.getRuntime().availableProcessors() * 2, Integer.MAX_VALUE, command);
    }

    public static void run(int threads, Runnable command) {
        run(threads, Integer.MAX_VALUE, command);
    }

    public static void run(int threads, int seconds, Runnable command) {
        ExecutorService executor = Executors.newWorkStealingPool(threads);
        try {
            executor.execute(command);
            executor.shutdown();
            if (!executor.awaitTermination(seconds, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            if (!executor.isShutdown()) {
                executor.shutdownNow();
            }
        }
    }
}
