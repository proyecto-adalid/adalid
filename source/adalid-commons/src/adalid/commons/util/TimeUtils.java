/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los términos
 * de la licencia "GNU General Public License" publicada por la Fundación "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser útil, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas información.
 */
package adalid.commons.util;

import adalid.commons.bundles.Bundle;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public class TimeUtils {

    private static long currentTimeMillis = 0;

    private static long lastTimeMillis = 0;

    private static long lastTimeMicros = 0;

    private static long lastTimeNanos = 0;

    private static long micros = 0;

    private static long nanos = 0;

    private static final Calendar calendar = Calendar.getInstance();

    public static synchronized long currentTimeMillis() {
        currentTimeMillis = System.currentTimeMillis();
        if (lastTimeMillis < currentTimeMillis) {
            lastTimeMillis = currentTimeMillis;
        } else {
            lastTimeMillis++;
        }
        lastTimeMicros = lastTimeMillis * 1000;
        lastTimeNanos = lastTimeMicros * 1000;
        micros = 0;
        nanos = 0;
        return lastTimeMillis;
    }

    public static synchronized long currentTimeMicros() {
        currentTimeMillis = System.currentTimeMillis();
        if (lastTimeMillis < currentTimeMillis) {
            currentTimeMillis();
        } else if (++micros < 1000) {
            lastTimeMicros++;
            lastTimeNanos = lastTimeMicros * 1000;
            nanos = 0;
        } else {
            currentTimeMillis();
        }
        return lastTimeMicros;
    }

    public static synchronized long currentTimeNanos() {
        currentTimeMillis = System.currentTimeMillis();
        if (lastTimeMillis < currentTimeMillis) {
            currentTimeMillis();
        } else if (++nanos < 1000) {
            lastTimeNanos++;
        } else {
            currentTimeMicros();
        }
        return lastTimeNanos;
    }

    public static synchronized Date currentDate() {
        calendar.setTimeInMillis(currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Date(calendar.getTimeInMillis());
    }

    public static synchronized Time currentTime() {
        calendar.setTimeInMillis(currentTimeMillis());
        calendar.set(Calendar.YEAR, 1970);
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return new Time(calendar.getTimeInMillis());
    }

    public static synchronized Timestamp currentTimestamp() {
        return new Timestamp(currentTimeMillis());
    }

    public static synchronized Date getDate() {
        return currentDate();
    }

    public static synchronized Date getDate(java.util.Date date) {
        if (date == null) {
            return currentDate();
        }
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(date.getTime());
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return new Date(c.getTimeInMillis());
    }

    public static synchronized Time getTime() {
        return currentTime();
    }

    public static synchronized Time getTime(java.util.Date date) {
        if (date == null) {
            return currentTime();
        }
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(date.getTime());
        c.set(Calendar.YEAR, 1970);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DAY_OF_MONTH, 1);
        return new Time(c.getTimeInMillis());
    }

    public static synchronized Timestamp getTimestamp() {
        return currentTimestamp();
    }

    public static synchronized Timestamp getTimestamp(Date date) {
        return date == null ? currentTimestamp() : new Timestamp(date.getTime());
    }

    private static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";

    private static final String DEFAULT_TIME_FORMAT = "hh:mm aa";

    private static final String DEFAULT_TIMESTAMP_FORMAT = "dd/MM/yyyy hh:mm:ss aa";

    private static final ThreadLocal<SimpleDateFormat> dateFormatter = new ThreadLocal<>();

    private static final ThreadLocal<SimpleDateFormat> timeFormatter = new ThreadLocal<>();

    private static final ThreadLocal<SimpleDateFormat> timestampFormatter = new ThreadLocal<>();

    private static final String BUNDLE_KEY_PREFIX = TimeUtils.class.getName() + ".";

    private static SimpleDateFormat dateFormatter() {
        SimpleDateFormat formatter = dateFormatter.get();
        if (formatter == null) {
            formatter = new SimpleDateFormat(getDateFormat());
            formatter.setLenient(false);
            dateFormatter.set(formatter);
        }
        return formatter;
    }

    private static SimpleDateFormat timeFormatter() {
        SimpleDateFormat formatter = timeFormatter.get();
        if (formatter == null) {
            formatter = new SimpleDateFormat(getTimeFormat());
            formatter.setLenient(false);
            timeFormatter.set(formatter);
        }
        return formatter;
    }

    private static SimpleDateFormat timestampFormatter() {
        SimpleDateFormat formatter = timestampFormatter.get();
        if (formatter == null) {
            formatter = new SimpleDateFormat(getTimestampFormat());
            formatter.setLenient(false);
            timestampFormatter.set(formatter);
        }
        return formatter;
    }

    public static String getDateFormat() {
        String string = Bundle.getTrimmedToNullString(BUNDLE_KEY_PREFIX + "date.pattern");
        String format = StringUtils.defaultIfBlank(string, DEFAULT_DATE_FORMAT);
        return format;
    }

    public static String getTimeFormat() {
        String string = Bundle.getTrimmedToNullString(BUNDLE_KEY_PREFIX + "time.pattern");
        String format = StringUtils.defaultIfBlank(string, DEFAULT_TIME_FORMAT);
        return format;
    }

    public static String getTimestampFormat() {
        String string = Bundle.getTrimmedToNullString(BUNDLE_KEY_PREFIX + "both.pattern");
        String format = StringUtils.defaultIfBlank(string, DEFAULT_TIMESTAMP_FORMAT);
        return format;
    }

    public static String defaultString(java.util.Date util) {
        if (util == null) {
            return null;
        } else if (util instanceof Date) {
            return defaultDateString(util);
        } else if (util instanceof Time) {
            return defaultTimeString(util);
        } else if (util instanceof Timestamp) {
            return defaultTimestampString(util);
        } else {
//          return defaultTemporalString(util);
            return defaultString(getSqlExtension(util));
        }
    }

    public static String defaultDateString() {
        return defaultDateString(currentDate());
    }

    public static String defaultDateString(Object object) {
        java.util.Date date = object instanceof java.util.Date ? (java.util.Date) object : null;
        return date == null ? null : defaultDateString(date);
    }

    public static String defaultDateString(java.util.Date date) {
        return date == null ? null : dateFormatter().format(date);
    }

    public static String defaultTimeString() {
        return defaultTimeString(currentTime());
    }

    public static String defaultTimeString(Object object) {
        java.util.Date date = object instanceof java.util.Date ? (java.util.Date) object : null;
        return date == null ? null : defaultTimeString(date);
    }

    public static String defaultTimeString(java.util.Date date) {
        return date == null ? null : timeFormatter().format(date);
    }

    public static String defaultTimestampString() {
        return defaultTimestampString(currentTimestamp());
    }

    public static String defaultTimestampString(Object object) {
        java.util.Date date = object instanceof java.util.Date ? (java.util.Date) object : null;
        return date == null ? null : defaultTimestampString(date);
    }

    public static String defaultTimestampString(java.util.Date date) {
        return date == null ? null : timestampFormatter().format(date);
    }

    public static String defaultTemporalString(Object object) {
        java.util.Date date = object instanceof java.util.Date ? (java.util.Date) object : null;
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(date.getTime());
        int dd = c.get(Calendar.YEAR);
        int MM = c.get(Calendar.MONTH);
        int yy = c.get(Calendar.DAY_OF_MONTH);
        int hh = c.get(Calendar.HOUR_OF_DAY);
        int mm = c.get(Calendar.MINUTE);
        int ss = c.get(Calendar.SECOND);
        int ms = c.get(Calendar.MILLISECOND);
        boolean dateless = dd == 1 && MM == Calendar.JANUARY && yy == 1970;
        boolean timeless = hh + mm + ss + ms == 0;
        return timeless ? defaultDateString(date) : dateless ? defaultTimeString(date) : defaultTimestampString(date);
    }

    private static final String JDBC_DATE_FORMAT = "yyyy-MM-dd";

    private static final String JDBC_TIME_FORMAT = "HH:mm:ss";

    private static final String JDBC_TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static final SimpleDateFormat JDBC_DATE_FORMATTER = new SimpleDateFormat(JDBC_DATE_FORMAT);

    private static final SimpleDateFormat JDBC_TIME_FORMATTER = new SimpleDateFormat(JDBC_TIME_FORMAT);

    private static final SimpleDateFormat JDBC_TIMESTAMP_FORMATTER = new SimpleDateFormat(JDBC_TIMESTAMP_FORMAT);

    public static String jdbcString(java.util.Date util) {
        if (util == null) {
            return null;
        } else if (util instanceof Date) {
            return jdbcDateString(util);
        } else if (util instanceof Time) {
            return jdbcTimeString(util);
        } else if (util instanceof Timestamp) {
            return jdbcTimestampString(util);
        } else {
            return jdbcString(getSqlExtension(util));
        }
    }

    public static String jdbcDateString() {
        return jdbcDateString(currentDate());
    }

    public static String jdbcDateString(Object object) {
        java.util.Date date = object instanceof java.util.Date ? (java.util.Date) object : null;
        return date == null ? null : jdbcDateString(date);
    }

    public static String jdbcDateString(java.util.Date date) {
        return date == null ? null : JDBC_DATE_FORMATTER.format(date);
    }

    public static String jdbcTimeString() {
        return jdbcTimeString(currentTime());
    }

    public static String jdbcTimeString(Object object) {
        java.util.Date date = object instanceof java.util.Date ? (java.util.Date) object : null;
        return date == null ? null : jdbcTimeString(date);
    }

    public static String jdbcTimeString(java.util.Date date) {
        return date == null ? null : JDBC_TIME_FORMATTER.format(date);
    }

    public static String jdbcTimestampString() {
        return jdbcTimestampString(currentTimestamp());
    }

    public static String jdbcTimestampString(Object object) {
        java.util.Date date = object instanceof java.util.Date ? (java.util.Date) object : null;
        return date == null ? null : jdbcTimestampString(date);
    }

    public static String jdbcTimestampString(java.util.Date date) {
        return date == null ? null : JDBC_TIMESTAMP_FORMATTER.format(date);
    }

    public static String jdbcTemporalString(Object object) {
        java.util.Date date = object instanceof java.util.Date ? (java.util.Date) object : null;
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(date.getTime());
        int dd = c.get(Calendar.YEAR);
        int MM = c.get(Calendar.MONTH);
        int yy = c.get(Calendar.DAY_OF_MONTH);
        int hh = c.get(Calendar.HOUR_OF_DAY);
        int mm = c.get(Calendar.MINUTE);
        int ss = c.get(Calendar.SECOND);
        int ms = c.get(Calendar.MILLISECOND);
        boolean dateless = dd == 1 && MM == Calendar.JANUARY && yy == 1970;
        boolean timeless = hh + mm + ss + ms == 0;
        return timeless ? jdbcDateString(date) : dateless ? jdbcTimeString(date) : jdbcTimestampString(date);
    }

    public static final String SIMPLE_DATE_FORMAT = "yyyyMMdd";

    public static final String SIMPLE_TIME_FORMAT = "HHmmss";

    public static final String SIMPLE_TIMESTAMP_FORMAT = "yyyyMMdd-HHmmss-SSS";

    public static final SimpleDateFormat SIMPLE_DATE_FORMATTER = new SimpleDateFormat(SIMPLE_DATE_FORMAT);

    public static final SimpleDateFormat SIMPLE_TIME_FORMATTER = new SimpleDateFormat(SIMPLE_TIME_FORMAT);

    public static final SimpleDateFormat SIMPLE_TIMESTAMP_FORMATTER = new SimpleDateFormat(SIMPLE_TIMESTAMP_FORMAT);

    public static String simpleDateString() {
        return simpleDateString(currentDate());
    }

    public static String simpleDateString(Object object) {
        java.util.Date date = object instanceof java.util.Date ? (java.util.Date) object : null;
        return date == null ? null : simpleDateString(date);
    }

    public static String simpleDateString(java.util.Date date) {
        return date == null ? null : SIMPLE_DATE_FORMATTER.format(date);
    }

    public static String simpleTimeString() {
        return simpleTimeString(currentTime());
    }

    public static String simpleTimeString(Object object) {
        java.util.Date date = object instanceof java.util.Date ? (java.util.Date) object : null;
        return date == null ? null : simpleTimeString(date);
    }

    public static String simpleTimeString(java.util.Date date) {
        return date == null ? null : SIMPLE_TIME_FORMATTER.format(date);
    }

    public static String simpleTimestampString() {
        return simpleTimestampString(currentTimestamp());
    }

    public static String simpleTimestampString(Object object) {
        java.util.Date date = object instanceof java.util.Date ? (java.util.Date) object : null;
        return date == null ? null : simpleTimestampString(date);
    }

    public static String simpleTimestampString(java.util.Date date) {
        return date == null ? null : SIMPLE_TIMESTAMP_FORMATTER.format(date);
    }

    public static Date newDate(Object obj) {
        if (obj == null) {
            return null;
        } else if (obj instanceof String) {
            String pdq = (String) obj;
            return newDate(parse(pdq));
        } else if (obj instanceof java.util.Date) {
            java.util.Date pdq = (java.util.Date) obj;
            return newDate(pdq);
        } else {
            throw new IllegalArgumentException("(" + obj.getClass() + ")" + obj + " is not an instance of java.util.Date ");
        }
    }

    public static Time newTime(Object obj) {
        if (obj == null) {
            return null;
        } else if (obj instanceof String) {
            String pdq = (String) obj;
            return newTime(parse(pdq));
        } else if (obj instanceof java.util.Date) {
            java.util.Date pdq = (java.util.Date) obj;
            return newTime(pdq);
        } else {
            throw new IllegalArgumentException("(" + obj.getClass() + ")" + obj + " is not an instance of java.util.Date ");
        }
    }

    public static Timestamp newTimestamp(Object obj) {
        if (obj == null) {
            return null;
        } else if (obj instanceof String) {
            String pdq = (String) obj;
            return newTimestamp(parse(pdq));
        } else if (obj instanceof java.util.Date) {
            java.util.Date pdq = (java.util.Date) obj;
            return newTimestamp(pdq);
        } else {
            throw new IllegalArgumentException("(" + obj.getClass() + ")" + obj + " is not an instance of java.util.Date ");
        }
    }

    public static Date newDate(java.util.Date date) {
        return date == null ? null : new Date(newDateCalendar(date).getTimeInMillis());
    }

    public static Calendar newDateCalendar(java.util.Date date) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(date.getTime());
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c;
    }

    public static Time newTime(java.util.Date date) {
        return date == null ? null : new Time(newTimeCalendar(date).getTimeInMillis());
    }

    public static Calendar newTimeCalendar(java.util.Date date) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(date.getTime());
        c.set(Calendar.YEAR, 1970);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DAY_OF_MONTH, 1);
        return c;
    }

    public static Timestamp newTimestamp(java.util.Date date) {
        return date == null ? null : new Timestamp(date.getTime());
    }

    public static Calendar newCalendar(java.util.Date date) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(date.getTime());
        return c;
    }

    public static Date newDate(int year, int monthOfYear, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, monthOfYear - 1);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return new Date(c.getTimeInMillis());
    }

    public static Time newTime(int hourOfDay, int minuteOfHour, int secondOfMinute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 1970);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minuteOfHour);
        c.set(Calendar.SECOND, secondOfMinute);
        c.set(Calendar.MILLISECOND, 0);
        return new Time(c.getTimeInMillis());
    }

    public static Timestamp newTimestamp(int year, int monthOfYear, int dayOfMonth, int hourOfDay, int minuteOfHour, int secondOfMinute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, monthOfYear - 1);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minuteOfHour);
        c.set(Calendar.SECOND, secondOfMinute);
        c.set(Calendar.MILLISECOND, 0);
        return new Timestamp(c.getTimeInMillis());
    }

    public static java.util.Date newUtilDate(int year, int monthOfYear, int dayOfMonth, int hourOfDay, int minuteOfHour, int secondOfMinute) {
        Timestamp t = newTimestamp(year, monthOfYear, dayOfMonth, hourOfDay, minuteOfHour, secondOfMinute);
        return new java.util.Date(t.getTime());
    }

    public static java.util.Date toJavaDate(Object object, Class<? extends java.util.Date> targetClass) {
        if (object == null) {
            return null;
        }
        Class<?> sourceClass = object.getClass();
        if (targetClass == null || targetClass.isAssignableFrom(sourceClass)) {
            return (java.util.Date) object;
        } else if (targetClass.equals(Date.class)) {
            return toDate(object);
        } else if (targetClass.equals(Time.class)) {
            return toTime(object);
        } else if (targetClass.equals(Timestamp.class)) {
            return toTimestamp(object);
        } else {
            return (java.util.Date) object;
        }
    }

    public static Date toDate(Object obj) {
        return obj == null ? null : obj instanceof Date ? (Date) obj : newDate(obj);
    }

    public static Time toTime(Object obj) {
        return obj == null ? null : obj instanceof Time ? (Time) obj : newTime(obj);
    }

    public static Timestamp toTimestamp(Object obj) {
        return obj == null ? null : obj instanceof Timestamp ? (Timestamp) obj : newTimestamp(obj);
    }

    public static java.util.Date parse(String pdq) {
        if (StringUtils.isBlank(pdq)) {
            return null;
        }
        String string = pdq.trim();
        ParsePosition position = new ParsePosition(0);
        java.util.Date util = timestampFormatter().parse(string, position);
        int i = position.getIndex();
        int l = string.length();
        if (util != null && i == l) {
            return new Timestamp(util.getTime());
        }
        position.setIndex(0);
        util = dateFormatter().parse(string, position);
        i = position.getIndex();
        if (util != null) {
            if (i == l) {
                return new Date(util.getTime());
            }
            java.util.Date time = timeFormatter().parse(string, position);
            i = position.getIndex();
            if (time != null && i == l) {
                return merge(util, time);
            }
        }
        position.setIndex(0);
        util = timeFormatter().parse(string, position);
        i = position.getIndex();
        if (util != null && i == l) {
            return new Time(util.getTime());
        }
        return null;
    }

    private static Timestamp merge(java.util.Date date, java.util.Date time) {
        Calendar d = Calendar.getInstance();
        Calendar t = Calendar.getInstance();
        d.setTimeInMillis(date.getTime());
        t.setTimeInMillis(time.getTime());
        d.set(Calendar.HOUR_OF_DAY, t.get(Calendar.HOUR_OF_DAY));
        d.set(Calendar.MINUTE, t.get(Calendar.MINUTE));
        d.set(Calendar.SECOND, t.get(Calendar.SECOND));
        d.set(Calendar.MILLISECOND, t.get(Calendar.MILLISECOND));
        return new Timestamp(d.getTimeInMillis());
    }

    public static java.util.Date getSqlExtension(java.util.Date util) {
        if (util == null) {
            return null;
        } else if (util instanceof Date || util instanceof Time || util instanceof Timestamp) {
            return util;
        } else {
            long milliseconds = util.getTime();
            Date date = newDate(util);
            if (date.getTime() == milliseconds) {
                return date;
            }
            Time time = newTime(util);
            if (time.getTime() == milliseconds) {
                return time;
            }
            return new Timestamp(milliseconds);
        }
    }

    public static Date addDate(java.util.Date date, int addend, char unit) {
        if (date == null) {
            return null;
        }
        Calendar c = newDateCalendar(date);
        if (addend != 0) {
            switch (unit) {
                case 'Y':
                    c.add(Calendar.YEAR, addend);
                    break;
                case 'M':
                    c.add(Calendar.MONTH, addend);
                    break;
                case 'D':
                    c.add(Calendar.DAY_OF_MONTH, addend);
                    break;
                default:
                    break;
            }
        }
        return new Date(c.getTimeInMillis());
    }

    public static Time addTime(java.util.Date date, int addend, char unit) {
        if (date == null) {
            return null;
        }
        Calendar c = newTimeCalendar(date);
        if (addend != 0) {
            switch (unit) {
                case 'h':
                    c.add(Calendar.HOUR, addend);
                    break;
                case 'm':
                    c.add(Calendar.MINUTE, addend);
                    break;
                case 's':
                    c.add(Calendar.SECOND, addend);
                    break;
                default:
                    break;
            }
        }
        return new Time(c.getTimeInMillis());
    }

    public static Timestamp addTimestamp(java.util.Date date, int addend, char unit) {
        if (date == null) {
            return null;
        }
        Calendar c = newCalendar(date);
        if (addend != 0) {
            switch (unit) {
                case 'Y':
                    c.add(Calendar.YEAR, addend);
                    break;
                case 'M':
                    c.add(Calendar.MONTH, addend);
                    break;
                case 'D':
                    c.add(Calendar.DAY_OF_MONTH, addend);
                    break;
                case 'h':
                    c.add(Calendar.HOUR, addend);
                    break;
                case 'm':
                    c.add(Calendar.MINUTE, addend);
                    break;
                case 's':
                    c.add(Calendar.SECOND, addend);
                    break;
                default:
                    break;
            }
        }
        return new Timestamp(c.getTimeInMillis());
    }

    private static final Calendar EPOCH = Calendar.getInstance();

    static {
        EPOCH.set(Calendar.YEAR, 1970);
        EPOCH.set(Calendar.MONTH, Calendar.JANUARY);
        EPOCH.set(Calendar.DAY_OF_MONTH, 1);
        EPOCH.set(Calendar.HOUR_OF_DAY, 0);
        EPOCH.set(Calendar.MINUTE, 0);
        EPOCH.set(Calendar.SECOND, 0);
        EPOCH.set(Calendar.MILLISECOND, 0);
    }

    public static long epochMillis() {
        return EPOCH.getTimeInMillis();
    }

}
