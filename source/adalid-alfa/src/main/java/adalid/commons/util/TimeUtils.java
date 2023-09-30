/*
 * Copyright 2017 Jorge Campins y David Uzcategui
 *
 * Este archivo forma parte de Adalid.
 *
 * Adalid es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos de la
 * licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Adalid se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA; sin
 * siquiera las garantias implicitas de COMERCIALIZACION e IDONEIDAD PARA UN PROPOSITO PARTICULAR.
 *
 * Para mas detalles vea la licencia "GNU General Public License" en http://www.gnu.org/licenses
 */
package adalid.commons.util;

import adalid.commons.bundles.*;
import adalid.commons.enums.*;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class TimeUtils {

    private static final Logger logger = Logger.getLogger(TimeUtils.class);

    public static synchronized Date actualDate() {
        Calendar c = actualDateCalendar();
        return new Date(c.getTimeInMillis());
    }

    public static synchronized Time actualTime() {
        Calendar c = actualTimeCalendar();
        return new Time(c.getTimeInMillis());
    }

    public static synchronized Timestamp actualTimestamp() {
        return Timestamp.from(Instant.now());
    }

    public static synchronized int actualYear() {
        return actualDateCalendar().get(Calendar.YEAR);
    }

    public static synchronized int actualMonth() {
        return actualDateCalendar().get(Calendar.MONTH) + 1;
    }

    public static synchronized int actualDayOfMonth() {
        return actualDateCalendar().get(Calendar.DAY_OF_MONTH);
    }

    public static synchronized Calendar actualDateCalendar() {
        Calendar c = actualTimestampCalendar();
        setCalendarFields(c, 0, 0, 0, 0);
        return c;
    }

    public static synchronized Calendar actualTimeCalendar() {
        Calendar c = actualTimestampCalendar();
        setCalendarFields(c, 1970, Calendar.JANUARY, 1);
        return c;
    }

    public static synchronized Calendar actualTimestampCalendar() {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(Instant.now().toEpochMilli());
        return c;
    }

    public static Date firstDateOfYear(int year) {
        Calendar c = calendar(year, Calendar.JANUARY, 1);
        return new Date(c.getTimeInMillis());
    }

    public static Date lastDateOfYear(int year) {
        Calendar c = calendar(year, Calendar.DECEMBER, 31);
        return new Date(c.getTimeInMillis());
    }

    public static Time firstTimeOfDate(java.util.Date date) {
        Calendar c = calendar(date);
        if (c == null) {
            return null;
        }
        setCalendarFields(c, 0, 0, 0, 0);
        return new Time(c.getTimeInMillis());
    }

    public static Time lastTimeOfDate(java.util.Date date) {
        Calendar c = calendar(date);
        if (c == null) {
            return null;
        }
        setCalendarFields(c, 23, 59, 59, 999);
        return new Time(c.getTimeInMillis());
    }

    public static Time firstTimeOfDay() {
        Calendar c = calendar(0, 0, 0, 0);
        return new Time(c.getTimeInMillis());
    }

    public static Time lastTimeOfDay() {
        Calendar c = calendar(23, 59, 59, 999);
        return new Time(c.getTimeInMillis());
    }

    public static Timestamp firstTimestampOfYear(int year) {
        Calendar c = calendar(year, Calendar.JANUARY, 1);
        return new Timestamp(c.getTimeInMillis());
    }

    public static Timestamp lastTimestampOfYear(int year) {
        Calendar c = calendar(year, Calendar.DECEMBER, 31, 23, 59, 59, 999);
        return new Timestamp(c.getTimeInMillis());
    }

    private static Calendar calendar(java.util.Date date) {
        return date == null ? null : calendar(date.getTime());
    }

    private static Calendar calendar(long millis) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(millis);
        return c;
    }

    private static Calendar calendar(int year, int month, int day) {
        return calendar(year, month, day, 0, 0, 0, 0);
    }

    private static Calendar calendar(int hour, int minute, int second, int millisecond) {
        return calendar(1970, Calendar.JANUARY, 1, hour, minute, second, millisecond);
    }

    private static Calendar calendar(int year, int month, int day, int hour, int minute, int second, int millisecond) {
        Calendar c = Calendar.getInstance();
        setCalendarFields(c, year, month, day);
        setCalendarFields(c, hour, minute, second, millisecond);
        return c;
    }

    private static void setCalendarFields(Calendar c, int year, int month, int day) {
        c.set(Calendar.DAY_OF_MONTH, 1); // set DAY_OF_MONTH to 1 before changing MONTH or YEAR to avoid overflow
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
    }

    private static void setCalendarFields(Calendar c, int hour, int minute, int second, int millisecond) {
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, second);
        c.set(Calendar.MILLISECOND, millisecond);
    }

    private static long currentTimeMillis = 0;

    private static long lastTimeMillis = 0;

    private static long lastTimeMicros = 0;

    private static long lastTimeNanos = 0;

    private static long micros = 0;

    private static long nanos = 0;

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
        Calendar c = calendar(currentTimeMillis());
        setCalendarFields(c, 0, 0, 0, 0);
        return new Date(c.getTimeInMillis());
    }

    public static synchronized Time currentTime() {
        Calendar c = calendar(currentTimeMillis());
        setCalendarFields(c, 1970, Calendar.JANUARY, 1);
        return new Time(c.getTimeInMillis());
    }

    public static synchronized Timestamp currentTimestamp() {
        return new Timestamp(currentTimeMillis());
    }

    public static synchronized Date getDate() {
        return currentDate();
    }

    public static synchronized Date getDate(java.util.Date date) {
        Calendar c = calendar(date);
        if (c == null) {
            return currentDate();
        }
        setCalendarFields(c, 0, 0, 0, 0);
        return new Date(c.getTimeInMillis());
    }

    public static synchronized Time getTime() {
        return currentTime();
    }

    public static synchronized Time getTime(java.util.Date date) {
        Calendar c = calendar(date);
        if (c == null) {
            return currentTime();
        }
        setCalendarFields(c, 1970, Calendar.JANUARY, 1);
        return new Time(c.getTimeInMillis());
    }

    public static synchronized Timestamp getTimestamp() {
        return currentTimestamp();
    }

    public static synchronized Timestamp getTimestamp(Date date) {
        return date == null ? currentTimestamp() : new Timestamp(date.getTime());
    }

    private static final ThreadLocal<SimpleDateFormat> dateFormatter = new ThreadLocal<>();

    private static final ThreadLocal<SimpleDateFormat> timeFormatter = new ThreadLocal<>();

    private static final ThreadLocal<SimpleDateFormat> timestampFormatter = new ThreadLocal<>();

    private static SimpleDateFormat dateFormatter(EnumTemporalFormat format) {
        if (format == null) {
            return dateFormatter();
        }
        switch (format) {
            case JDBC:
                return JDBC_DATE_FORMATTER;
            case SIMPLE:
                return SIMPLE_DATE_FORMATTER;
            default:
                return dateFormatter();
        }
    }

    private static SimpleDateFormat dateFormatter() {
        SimpleDateFormat formatter = dateFormatter.get();
        if (formatter == null) {
            formatter = new SimpleDateFormat(getDateFormat());
            formatter.setLenient(false);
            dateFormatter.set(formatter);
        }
        return formatter;
    }

    private static SimpleDateFormat timeFormatter(EnumTemporalFormat format) {
        if (format == null) {
            return timeFormatter();
        }
        switch (format) {
            case JDBC:
                return JDBC_TIME_FORMATTER;
            case SIMPLE:
                return SIMPLE_TIME_FORMATTER;
            default:
                return timeFormatter();
        }
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

    private static SimpleDateFormat timestampFormatter(EnumTemporalFormat format) {
        if (format == null) {
            return timestampFormatter();
        }
        switch (format) {
            case JDBC:
                return JDBC_TIMESTAMP_FORMATTER;
            case SIMPLE:
                return SIMPLE_TIMESTAMP_FORMATTER;
            default:
                return timestampFormatter();
        }
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

    private static final String BUNDLE_KEY_PREFIX = TimeUtils.class.getName() + ".";

    private static final Map<Locale, String> _dateFormatMap = new LinkedHashMap<>();

    public static String getDateFormat() {
        Locale locale = Bundle.getLocale();
        if (_dateFormatMap.containsKey(locale)) {
            return _dateFormatMap.get(locale);
        }
        String string = Bundle.getTrimmedToNullString(BUNDLE_KEY_PREFIX + "date.pattern");
        String format = StringUtils.defaultIfBlank(string, DEFAULT_DATE_FORMAT);
        return format;
    }

    public static void setDateFormat(Locale locale, String format) {
        if (locale != null) {
            if (StringUtils.isBlank(format)) {
                _dateFormatMap.remove(locale);
            } else {
                _dateFormatMap.put(locale, format);
                logger.trace(sample(locale, format, "Date-only"));
            }
        }
    }

    private static final Map<Locale, String> _timeFormatMap = new LinkedHashMap<>();

    public static String getTimeFormat() {
        Locale locale = Bundle.getLocale();
        if (_timeFormatMap.containsKey(locale)) {
            return _timeFormatMap.get(locale);
        }
        String string = Bundle.getTrimmedToNullString(BUNDLE_KEY_PREFIX + "time.pattern");
        String format = StringUtils.defaultIfBlank(string, DEFAULT_TIME_FORMAT);
        return format;
    }

    public static void setTimeFormat(Locale locale, String format) {
        if (locale != null) {
            if (StringUtils.isBlank(format)) {
                _timeFormatMap.remove(locale);
            } else {
                _timeFormatMap.put(locale, format);
                logger.trace(sample(locale, format, "Time-only"));
            }
        }
    }

    private static final Map<Locale, String> _timestampFormatMap = new LinkedHashMap<>();

    public static String getTimestampFormat() {
        Locale locale = Bundle.getLocale();
        if (_timestampFormatMap.containsKey(locale)) {
            return _timestampFormatMap.get(locale);
        }
        String string = Bundle.getTrimmedToNullString(BUNDLE_KEY_PREFIX + "both.pattern");
        String format = StringUtils.defaultIfBlank(string, DEFAULT_TIMESTAMP_FORMAT);
        return format;
    }

    public static void setTimestampFormat(Locale locale, String format) {
        if (locale != null) {
            if (StringUtils.isBlank(format)) {
                _timestampFormatMap.remove(locale);
            } else {
                _timestampFormatMap.put(locale, format);
                logger.trace(sample(locale, format, "Date+time"));
            }
        }
    }

    private static final Calendar fie = calendar(1970, Calendar.APRIL, 16, 15, 30, 40, 360);

    private static final Timestamp fih = new Timestamp(fie.getTimeInMillis());

    private static final SimpleDateFormat foh = new SimpleDateFormat("yyyy-MM-dd·HH:mm:ss.SSS");

    private static final String fum = foh.format(fih);

    private static String sample(Locale locale, String format, String label) {
        // fie, fih, foh, fum, I smell the blood of an Englishman!
        String string = (new SimpleDateFormat(format)).format(fih);
        String prefix = label + " formatting example: ";
        return prefix + "value=" + fum + ", locale=\"" + locale + "\", format=\"" + format + "\" --> \"" + string + "\"";
    }

    private static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";

    private static final String DEFAULT_TIME_FORMAT = "hh:mm a";

    private static final String DEFAULT_TIMESTAMP_FORMAT = "dd/MM/yyyy hh:mm:ss a";

    public static java.util.Date defaultObject(String string) {
        return parse(string, EnumTemporalFormat.DEFAULT);
    }

    public static java.util.Date defaultObject(String string, Class<? extends java.util.Date> clazz) {
        if (StringUtils.isBlank(string) || clazz == null) {
            return null;
        } else if (Date.class.isAssignableFrom(clazz)) {
            return defaultDate(string);
        } else if (Time.class.isAssignableFrom(clazz)) {
            return defaultTime(string);
        } else {
            return defaultTimestamp(string);
        }
    }

    public static Date defaultDate(String string) {
        return newDate(parse(string, EnumTemporalFormat.DEFAULT));
    }

    public static Time defaultTime(String string) {
        return newTime(parse(string, EnumTemporalFormat.DEFAULT));
    }

    public static Timestamp defaultTimestamp(String string) {
        return newTimestamp(parse(string, EnumTemporalFormat.DEFAULT));
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

    public static java.util.Date jdbcObject(String string) {
        return parse(string, EnumTemporalFormat.JDBC);
    }

    public static java.util.Date jdbcObject(String string, Class<? extends java.util.Date> clazz) {
        if (StringUtils.isBlank(string) || clazz == null) {
            return null;
        } else if (Date.class.isAssignableFrom(clazz)) {
            return jdbcDate(string);
        } else if (Time.class.isAssignableFrom(clazz)) {
            return jdbcTime(string);
        } else {
            return jdbcTimestamp(string);
        }
    }

    public static Date jdbcDate(String string) {
        return newDate(parse(string, EnumTemporalFormat.JDBC));
    }

    public static Time jdbcTime(String string) {
        return newTime(parse(string, EnumTemporalFormat.JDBC));
    }

    public static Timestamp jdbcTimestamp(String string) {
        return newTimestamp(parse(string, EnumTemporalFormat.JDBC));
    }

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

    public static java.util.Date simpleObject(String string) {
        return parse(string, EnumTemporalFormat.SIMPLE);
    }

    public static java.util.Date simpleObject(String string, Class<? extends java.util.Date> clazz) {
        if (StringUtils.isBlank(string) || clazz == null) {
            return null;
        } else if (Date.class.isAssignableFrom(clazz)) {
            return simpleDate(string);
        } else if (Time.class.isAssignableFrom(clazz)) {
            return simpleTime(string);
        } else {
            return simpleTimestamp(string);
        }
    }

    public static Date simpleDate(String string) {
        return newDate(parse(string, EnumTemporalFormat.SIMPLE));
    }

    public static Time simpleTime(String string) {
        return newTime(parse(string, EnumTemporalFormat.SIMPLE));
    }

    public static Timestamp simpleTimestamp(String string) {
        return newTimestamp(parse(string, EnumTemporalFormat.SIMPLE));
    }

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

    public static String simpleTemporalString(Object object) {
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
        return timeless ? simpleDateString(date) : dateless ? simpleTimeString(date) : simpleTimestampString(date);
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
        Calendar c = calendar(date);
        if (c == null) {
            return null;
        }
        setCalendarFields(c, 0, 0, 0, 0);
        return c;
    }

    public static Time newTime(java.util.Date date) {
        return date == null ? null : new Time(newTimeCalendar(date).getTimeInMillis());
    }

    public static Calendar newTimeCalendar(java.util.Date date) {
        Calendar c = calendar(date);
        if (c == null) {
            return null;
        }
        setCalendarFields(c, 1970, Calendar.JANUARY, 1);
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
        return parse(pdq, null);
    }

    public static java.util.Date parse(String pdq, EnumTemporalFormat format) {
        if (StringUtils.isBlank(pdq)) {
            return null;
        }
        String string = pdq.trim();
        SimpleDateFormat timestampParser = timestampFormatter(format);
        ParsePosition position = new ParsePosition(0);
        java.util.Date util = timestampParser.parse(string, position);
        int i = position.getIndex();
        int l = string.length();
        if (util != null && i == l) {
            return new Timestamp(util.getTime());
        }
        SimpleDateFormat dateParser = dateFormatter(format);
        SimpleDateFormat timeParser = timeFormatter(format);
        position.setIndex(0);
        util = dateParser.parse(string, position);
        i = position.getIndex();
        if (util != null) {
            if (i == l) {
                return new Date(util.getTime());
            }
            java.util.Date time = timeParser.parse(string, position);
            i = position.getIndex();
            if (time != null && i == l) {
                return merge(util, time);
            }
        }
        position.setIndex(0);
        util = timeParser.parse(string, position);
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
        return util == null || util instanceof Date || util instanceof Time || util instanceof Timestamp ? util : getFittestSqlExtension(util);
    }

    public static java.util.Date getFittestSqlExtension(java.util.Date util) {
        if (util == null) {
            return null;
        }
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
        // no need to set DAY_OF_MONTH to 1 before setting MONTH or YEAR because JANUARY has 31 days (no possible overflow)
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
