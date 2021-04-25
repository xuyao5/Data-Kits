package io.github.xuyao5.dkl.eskits.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import javax.validation.constraints.NotNull;
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

    public static final FastDateFormat STD_DATETIME_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
    public static final FastDateFormat BASIC_DATE = FastDateFormat.getInstance("yyyyMMdd");
    public static final FastDateFormat STRICT_DATE_OPTIONAL_TIME = FastDateFormat.getInstance("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    public static Date now() {
        return new Date();
    }

    public static String getFormatDate(@NotNull FastDateFormat format) {
        return format(now(), format.getPattern(), Locale.ROOT);
    }
}