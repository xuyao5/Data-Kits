package io.github.xuyao5.dkl.eskits.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.security.SecureRandom;

/**
 * @author Thomas.XU(xuyao)
 * @version 11/03/21 11:47
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RandomUtilsPlus {

    private static final SecureRandom RANDOM = new SecureRandom();

    public static long getLong() {
        return RANDOM.nextLong();
    }

    public static int getInt() {
        return RANDOM.nextInt();
    }

    public static double getDouble() {
        return RANDOM.nextDouble();
    }

    public static float getFloat() {
        return RANDOM.nextFloat();
    }
}