package io.github.xuyao5.dkl.eskits.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.time.DateFormatUtils;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 5/07/20 17:10
 * @apiNote MyDateFormatUtils
 * @implNote MyDateFormatUtils
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MyDateUtils extends DateFormatUtils {

    public static String convertDate2String(@NotNull String date) {
        return null;
    }

    public static String convertDate2String(@NotNull Date date) {
        return null;
    }

    public static Date convertDate2Date(@NotNull String date) {
        return null;
    }

    public static Date convertDate2Date(@NotNull Date date) {
        return null;
    }
}