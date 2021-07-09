package io.github.xuyao5.dkl.eskits.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Arrays;
import java.util.Locale;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 4/06/21 19:24
 * @apiNote MyHashUtils
 * @implNote MyHashUtils
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class HashUtilsNZ {

    public static String md5(String[] recordArray) {
        return DigestUtils.md5Hex(Arrays.stream(recordArray).collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString()).toUpperCase(Locale.ROOT);
    }
}
