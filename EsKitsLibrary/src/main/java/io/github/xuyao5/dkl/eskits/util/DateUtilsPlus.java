package io.github.xuyao5.dkl.eskits.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;

import static org.apache.commons.lang3.time.DateUtils.parseDate;

/**
 * @author Thomas.XU(xuyao)
 * @version 5/07/20 17:10
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DateUtilsPlus {

    public static final FastDateFormat STD_DATETIME_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
    public static final FastDateFormat STD_DATE_FORMAT = FastDateFormat.getInstance("yyyyMMdd");

    public static Date now() {
        return new Date();
    }

    public static String format2Date(@NonNull FastDateFormat format) {
        return format2Date(now(), format);
    }

    public static String format2Date(@NonNull Date date, @NonNull FastDateFormat format) {
        return DateFormatUtils.format(date, format.getPattern(), Locale.ROOT);
    }

    @SneakyThrows
    public static Date parse2Date(@NonNull FastDateFormat format) {
        return parse2Date(format2Date(format), format);
    }

    @SneakyThrows
    public static Date parse2Date(@NonNull String date, @NonNull FastDateFormat format) {
        return parseDate(date, Locale.ROOT, format.getPattern());
    }

    public static Date parse2Date(long date) {
        return new Date(date);
    }

    public static long daysBetween(String dateTag) {
        return ChronoUnit.DAYS.between(LocalDate.parse(dateTag, DateTimeFormatter.BASIC_ISO_DATE), LocalDate.now());
    }

    public static long hoursPeriod(Date beginDate, Date endDate) {
        return ChronoUnit.HOURS.between(beginDate.toInstant(), endDate.toInstant());
    }

    public static long minutesPeriod(Date beginDate, Date endDate) {
        return ChronoUnit.MINUTES.between(beginDate.toInstant(), endDate.toInstant());
    }

    public static long secondsPeriod(Date beginDate, Date endDate) {
        return ChronoUnit.SECONDS.between(beginDate.toInstant(), endDate.toInstant());
    }

    public static Date[][] dateSharding(Date beginDate, Date endDate, int shard) {
        int secondStep = Math.toIntExact(Math.floorDiv(secondsPeriod(beginDate, endDate), shard) - 1);
        Date[][] dateArray = new Date[shard][];
        for (int i = 0; i < dateArray.length; i++) {
            dateArray[i] = new Date[2];
            //处理from
            if (i == 0) {
                dateArray[i][0] = beginDate;
            } else {
                dateArray[i][0] = DateUtils.addSeconds(dateArray[i - 1][1], 1);
            }
            //处理to
            if (i == dateArray.length - 1) {
                dateArray[i][1] = endDate;
            } else {
                dateArray[i][1] = DateUtils.addSeconds(dateArray[i][0], secondStep);
            }
        }
        return dateArray;
    }

    public static Date firstSecondOfDate(Date date) {
        return DateUtils.setHours(DateUtils.setMinutes(DateUtils.setSeconds(date, 0), 0), 0);
    }

    public static Date lastSecondOfDate(Date date) {
        return DateUtils.setHours(DateUtils.setMinutes(DateUtils.setSeconds(date, 59), 59), 23);
    }
}