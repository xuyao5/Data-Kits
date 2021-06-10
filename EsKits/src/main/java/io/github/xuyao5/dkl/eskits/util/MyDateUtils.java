package io.github.xuyao5.dkl.eskits.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.util.Date;
import java.util.Locale;

import static org.apache.commons.lang3.time.DateUtils.parseDate;

/**
 * @author Thomas.XU(xuyao)
 * @implSpec 5/07/20 17:10
 * @apiNote MyDateUtils
 * @implNote MyDateUtils
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MyDateUtils extends DateFormatUtils {

    public static final FastDateFormat STD_DATETIME_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
    public static final FastDateFormat STD_DATE_FORMAT = FastDateFormat.getInstance("yyyyMMdd");

    public static Date now() {
        return new Date();
    }

    public static String format2Date(FastDateFormat format) {
        return format2Date(now(), format);
    }

    public static String format2Date(Date date, FastDateFormat format) {
        return format(date, format.getPattern(), Locale.ROOT);
    }

    @SneakyThrows
    public static Date parse2Date(FastDateFormat format) {
        return parse2Date(format2Date(format), format);
    }

    @SneakyThrows
    public static Date parse2Date(String date, FastDateFormat format) {
        return parseDate(date, Locale.ROOT, format.getPattern());
    }

    public static Date parse2Date(long date) {
        return new Date(date);
    }
}