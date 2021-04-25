package io.github.xuyao5.dkl.eskits.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
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
    public static final FastDateFormat STD_DATE_FORMAT = FastDateFormat.getInstance("yyyyMMdd");

    public static Date now() {
        return new Date();
    }

    public static String getFormatDate(@NotNull FastDateFormat format) {
        return format(now(), format.getPattern(), Locale.ROOT);
    }

    @SneakyThrows
    public static Date parseDate(@NotNull String date, @NotNull FastDateFormat format) {
        return DateUtils.parseDate(date, format.getPattern());
    }

    public static Date parseDate(@NotNull long date) {
        return new Date(date);
    }
}