package io.github.xuyao5.dkl.eskits.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;
import java.util.Locale;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 5/07/20 17:10
 * @apiNote MyDateUtils
 * @implNote MyDateUtils
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MyDateUtils extends DateFormatUtils {

    public static final String STD_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String STD_DATE_FORMAT = "yyyyMMdd";

    public static Date now() {
        return new Date();
    }

    public static String getCurrentDate() {
        return format(now(), STD_DATE_FORMAT, Locale.ROOT);
    }

    public static String getCurrentDatetime() {
        return format(now(), STD_DATETIME_FORMAT, Locale.ROOT);
    }
}