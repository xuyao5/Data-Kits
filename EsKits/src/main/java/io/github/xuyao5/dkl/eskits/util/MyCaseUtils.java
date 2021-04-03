package io.github.xuyao5.dkl.eskits.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.text.CaseUtils;

import javax.validation.constraints.NotNull;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 6/03/21 16:31
 * @apiNote MyCaseUtils
 * @implNote MyCaseUtils
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MyCaseUtils extends CaseUtils {

    public static String toCamelCaseDefault(@NotNull String str) {
        return toCamelCase(str, false, '_');
    }
}