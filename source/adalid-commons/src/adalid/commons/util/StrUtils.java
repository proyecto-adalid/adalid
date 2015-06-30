/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los términos
 * de la licencia "GNU General Public License" publicada por la Fundación "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser útil, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas información.
 */
package adalid.commons.util;

import adalid.commons.bundles.Bundle;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.UUID;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class StrUtils {

    private static final Logger logger = Logger.getLogger(StrUtils.class);

    private static final String EMPTY = "";

    public static final String NULL_VALUE_STRING = Bundle.getString("null_value.string");

    public static String ID_PREFIX_STRING = Bundle.getString("id_prefix.string");

    public static boolean allAreBlank(String... strings) {
        for (String string : strings) {
            if (StringUtils.isNotBlank(string)) {
                return false;
            }
        }
        return true;
    }

    public static boolean noneIsBlank(String... strings) {
        for (String string : strings) {
            if (StringUtils.isBlank(string)) {
                return false;
            }
        }
        return true;
    }

    public static boolean notAllAreBlank(String... strings) {
        return !allAreBlank(strings);
    }

    public static boolean oneIsBlank(String... strings) {
        return !noneIsBlank(strings);
    }

    /*
     * used at velocity templates
     */
    public static boolean equalsAny(String string, String strings) {
        return string != null && strings != null && string.equals(strings);
    }

    public static boolean equalsAny(String string, String... strings) {
        return string != null && strings != null && ArrayUtils.contains(strings, string);
    }

    public static String coalesce(String... strings) {
        for (String string : strings) {
            if (StringUtils.isNotBlank(string)) {
                return string.trim();
            }
        }
        return null;
    }

    /*
     * used at velocity templates
     */
    public static String coalesceToDefault(String defaultString, String strings) {
        String string = coalesce(strings);
        return string == null ? defaultString : string;
    }

    public static String coalesceToDefault(String defaultString, String... strings) {
        String string = coalesce(strings);
        return string == null ? defaultString : string;
    }

    /*
     * used at velocity templates
     */
    public static String coalesceToEmpty(String strings) {
        String string = coalesce(strings);
        return string == null ? EMPTY : string;
    }

    public static String coalesceToEmpty(String... strings) {
        String string = coalesce(strings);
        return string == null ? EMPTY : string;
    }

    /*
     * used at velocity templates
     */
    public static String coalesceToNull(String strings) {
        String string = coalesce(strings);
        return string == null ? NULL_VALUE_STRING : string;
    }

    public static String coalesceToNull(String... strings) {
        String string = coalesce(strings);
        return string == null ? NULL_VALUE_STRING : string;
    }

    public static String getNumericCode(String string) {
        if (StringUtils.isBlank(string)) {
            return null;
        }
        String num = getNumericString(string);
        int digit = Damm.digit(num);
        char[] chars = string.trim().toCharArray();
        int len = chars.length;
        int sum = 0;
        for (char c : chars) {
            sum += c;
        }
        return digit + String.format("%02d", len % 100) + String.format("%02d", sum % 100);
    }

    public static String getLongNumericCode(String string) {
        if (StringUtils.isBlank(string)) {
            return null;
        }
        String clean = string.trim();
        String first = firstDigit(clean);
        String split = StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(clean), " ");
        String[] chunks = StringUtils.split(split, "!@#$%^&*()-_=+[]{}\\|;:'\",.<>/? ");
        for (int i = 0; i < chunks.length; i++) {
            chunks[i] = Damm.digit(getNumericString(chunks[i])) + "";
        }
        String nine;
        String join = StringUtils.join(chunks);
        int n = join.length();
        if (n < 10) {
            nine = StringUtils.rightPad(join, 9, "0");
        } else {
            int m = n / 2;
            nine = join.substring(0, 3) + join.substring(m - 1, m + 2) + join.substring(n - 3);
        }
        String numst = getNumericString(clean);
        String tenth = Damm.digit(numst) + "";
        char[] chars = clean.toCharArray();
        int len = chars.length;
        int sum = 0;
        for (char c : chars) {
            sum += c;
        }
        chunks = new String[]{first, nine, tenth, String.format("%03d", len % 1000), String.format("%05d", sum % 100000)};
        return StringUtils.join(chunks);
    }

    private static String firstDigit(String string) {
        char c = Character.toUpperCase(string.charAt(0));
        int i = c < 'A' ? 0 : c < 'D' ? 1 : c < 'G' ? 2 : c < 'J' ? 3 : c < 'M' ? 4 : c < 'P' ? 5 : c < 'T' ? 6 : c < 'W' ? 7 : c > 'Z' ? 0 : 8;
        return i + "";
    }

    public static String getNumericString(String string) {
        if (StringUtils.isBlank(string)) {
            return null;
        }
        char[] chars = string.trim().toCharArray();
        String num = "";
        int i;
        for (char c : chars) {
            i = c;
            num += String.format("%03d", i);
        }
        return num;
    }

    public static String getRandomString() {
        return getRandomString(0);
    }

    public static String getRandomString(int length) {
        String uuid = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
        int endIndex = length < 1 || length > uuid.length() ? uuid.length() : length;
        return UUID.randomUUID().toString().toUpperCase().replaceAll("-", "").substring(0, endIndex);
    }

    public static String getSimpleString(Object obj) {
        String type = obj == null ? "" : "<" + obj.getClass().getSimpleName() + ">";
        return type + getString(obj);
    }

    public static String getString(int n, char c) {
        String string = "";
        for (int i = 0; i < n; i++, string += c) {
        }
        return string;
    }

    public static String getString(Object obj) {
        if (obj == null) {
            return null;
        } else if (obj instanceof Date) {
            return TimeUtils.defaultDateString(obj);
        } else if (obj instanceof Time) {
            return TimeUtils.defaultTimeString(obj);
        } else if (obj instanceof java.util.Date) {
            return TimeUtils.defaultTemporalString(obj);
        } else {
            return String.valueOf(obj);
        }
    }

    public static String getStringDelimitado(Object obj) {
        String string = getString(obj);
        if (string == null) {
            return string;
        }
        if (obj instanceof String) {
            return "'" + string + "'";
        }
        return string;
    }

    public static String getStringNoDelimitado(String delimitado) {
        String string = delimitado;
        if (string == null) {
            return string;
        }
        if ((string.startsWith("'") && string.endsWith("'"))
            || (string.startsWith("(") && string.endsWith(")"))
            || (string.startsWith("[") && string.endsWith("]"))
            || (string.startsWith("{") && string.endsWith("}"))
            || (string.startsWith("<") && string.endsWith(">"))
            || (string.startsWith("¡") && string.endsWith("!"))
            || (string.startsWith("¿") && string.endsWith("?"))) {
            string = string.substring(1, (string.length() - 1)).trim();
        }
        return string;
    }

    public static String getStringParametrizado(String patron, Object... argumentos) {
        if (StringUtils.isBlank(patron)) {
            return null;
        }
        ArrayList<Object> objetos = new ArrayList<>();
        for (Object arg : argumentos) {
            objetos.add(StringUtils.trimToEmpty(getString(arg)));
        }
        return MessageFormat.format(patron, objetos.toArray());
    }

    public static String getStringSql(Object obj) {
        if (obj == null) {
            return null;
        } else if (obj instanceof Date) {
            return TimeUtils.jdbcDateString(obj);
        } else if (obj instanceof Time) {
            return TimeUtils.jdbcTimeString(obj);
        } else if (obj instanceof java.util.Date) {
            return TimeUtils.jdbcTimestampString(obj);
        } else {
            return String.valueOf(obj);
        }
    }

    public static String getStringSqlDelimitado(Object obj) {
        String string = getStringSql(obj);
        if (string == null) {
            return null;
        } else if (obj instanceof String) {
            return "'" + string + "'";
        } else if (obj instanceof Date) {
            return "'" + string + "'";
        } else if (obj instanceof Time) {
            return "'" + string + "'";
        } else if (obj instanceof Timestamp || obj instanceof java.util.Date) {
            return "'" + string + "'";
        } else {
            return string;
        }
    }

    public static String getStringSqlNoDelimitado(Object obj) {
        return getStringNoDelimitado(getStringSql(obj));
    }

    public static String getStringSqlParametrizado(String patron, Object... argumentos) {
        if (StringUtils.isBlank(patron)) {
            return null;
        }
        ArrayList<Object> objetos = new ArrayList<>();
        for (Object arg : argumentos) {
            objetos.add(StringUtils.trimToEmpty(getStringSql(arg)));
        }
        return MessageFormat.format(patron, objetos.toArray());
    }

    public static String getString(ResourceBundle resourceBundle, String key) {
        return getString(resourceBundle, key, null, null);
    }

    public static String getString(ResourceBundle resourceBundle, String key, String left, String right) {
        if (resourceBundle == null || StringUtils.isBlank(key)) {
            return null;
        }
        try {
            return getNullStringWhenBlankOrEqualsToKey(resourceBundle.getString(key), key, left, right);
        } catch (MissingResourceException e) {
            return null;
        }
    }

    public static String getNullStringWhenBlankOrEqualsToKey(String string, String key) {
        return getNullStringWhenBlankOrEqualsToKey(string, key, null, null);
    }

    public static String getNullStringWhenBlankOrEqualsToKey(String string, String key, String left, String right) {
        String s = StringUtils.trimToEmpty(string);
        String k = StringUtils.trimToEmpty(key);
        String l = StringUtils.trimToEmpty(left);
        String r = StringUtils.trimToEmpty(right);
        return StringUtils.isBlank(s) || s.equals(k) ? null : l + s + r;
    }

    public static boolean esIdentificadorSqlValido(String string) {
        String validChars = "abcdefghijklmnopqrstuvwxyz_1234567890";
        return (StringUtils.isNotBlank(string) && StringUtils.containsOnly(string, validChars) && StringUtils.isAlpha(string.substring(0, 1)));
    }

    public static boolean esInvocacionFuncionSql(String string) {
        if (StringUtils.isNotBlank(string)) {
            int i = string.indexOf('(');
            if (i > 0) {
                String name = string.substring(0, i);
                String rest = string.substring(i);
                return esIdentificadorSqlValido(name) && isDelimited(rest, '(', ')');
            }
        }
        return false;
    }

    public static boolean esIdentificadorArchivoValido(String string) {
        String validChars = "abcdefghijklmnopqrstuvwxyz_1234567890";
        return (StringUtils.isNotBlank(string) && StringUtils.containsOnly(string, validChars));
    }

    public static String getIdentificadorSql(String string, int maxLength) {
        return getIdentificadorSql(null, string, null, maxLength);
    }

    public static String getIdentificadorSql(String prefix, String string, String suffix, int maxLength) {
        if (StringUtils.isBlank(string)) {
            return string;
        }
        char separator = string.contains("$") ? '$' : '_';
        String trimmedPrefix = StringUtils.trimToEmpty(prefix);
        String trimmedString = StringUtils.trimToEmpty(string);
        String trimmedSuffix = StringUtils.trimToEmpty(suffix);
        String shorterString = trimmedPrefix + trimmedString + trimmedSuffix;
        String endlessString = shorterString;
        if (maxLength > 0 && shorterString.length() > maxLength) {
            String numericCode = separator + getNumericCode(trimmedString);
            String[] chunks = StringUtils.split(trimmedString, separator);
            String[] shorterChunks = new String[chunks.length];
            int chunkCount = chunks.length;
            int separators = chunkCount - 1;
            int available1 = maxLength - trimmedPrefix.length() - numericCode.length() - trimmedSuffix.length();
            int available2 = available1 - separators;
            int averageChunkLength = chunkCount > 1 ? available2 / chunkCount : available1;
            if (available1 > 0 && available2 > 0 && averageChunkLength > 0) {
                int overAverageChunkCount = 0;
                int lastOverAverageChunkIndex = 0;
                int used = 0;
                int chunkLength;
                String chunk;
                for (int i = 0; i < chunks.length; i++) {
                    chunk = chunks[i];
                    chunkLength = chunk.length();
                    if (chunkLength > averageChunkLength) {
                        overAverageChunkCount++;
                        lastOverAverageChunkIndex = i;
                        used += averageChunkLength;
                    } else {
                        used += chunkLength;
                    }
                }
                int free = available2 - used;
                int over = overAverageChunkCount == 0 ? 0 : Long.valueOf(Math.round((double) free / overAverageChunkCount)).intValue();
                int plus, size;
                for (int i = 0; i < chunks.length; i++) {
                    chunk = chunks[i];
                    chunkLength = chunk.length();
                    if (chunkLength > averageChunkLength) {
                        plus = averageChunkLength + over > chunkLength ? chunkLength - averageChunkLength : over;
                        if (i == lastOverAverageChunkIndex) {
                            size = averageChunkLength + free;
                            free = 0;
                        } else if (free < plus) {
                            size = averageChunkLength;
                        } else {
                            size = averageChunkLength + plus;
                            free -= plus;
                        }
                    } else {
                        size = chunkLength;
                    }
                    if (chunk.contains("_")) {
                        shorterChunks[i] = getElementoIdentificadorSql(chunk, Math.min(size, chunkLength));
                    } else {
                        shorterChunks[i] = chunk.substring(0, Math.min(size, chunkLength));
                    }
                }
                shorterString = trimmedPrefix + StringUtils.join(shorterChunks, separator) + numericCode + trimmedSuffix;
            } else if (available1 > 0) {
                shorterString = trimmedPrefix + trimmedString.substring(0, available1) + numericCode + trimmedSuffix;
            } else {
                shorterString = trimmedPrefix + numericCode + trimmedSuffix;
            }
        }
        logIdentificadorSql(endlessString, shorterString);
        return shorterString;
    }

    private static String getElementoIdentificadorSql(String string, int maxLength) {
        if (StringUtils.isBlank(string)) {
            return string;
        }
        char separator = '_';
        String trimmedString = StringUtils.trimToEmpty(string);
        String shorterString = trimmedString;
        String endlessString = shorterString;
        if (maxLength > 0 && shorterString.length() > maxLength) {
            String[] chunks = StringUtils.split(trimmedString, separator);
            String[] shorterChunks = new String[chunks.length];
            int chunkCount = chunks.length;
            int separators = chunkCount - 1;
            int available1 = maxLength;
            int available2 = available1 - separators;
            int averageChunkLength = chunkCount > 1 ? available2 / chunkCount : available1;
            if (available1 > 0 && available2 > 0 && averageChunkLength > 0) {
                int overAverageChunkCount = 0;
                int lastOverAverageChunkIndex = 0;
                int used = 0;
                int chunkLength;
                String chunk;
                for (int i = 0; i < chunks.length; i++) {
                    chunk = chunks[i];
                    chunkLength = chunk.length();
                    if (chunkLength > averageChunkLength) {
                        overAverageChunkCount++;
                        lastOverAverageChunkIndex = i;
                        used += averageChunkLength;
                    } else {
                        used += chunkLength;
                    }
                }
                int free = available2 - used;
                int over = overAverageChunkCount == 0 ? 0 : Long.valueOf(Math.round((double) free / overAverageChunkCount)).intValue();
                int plus, size;
                for (int i = 0; i < chunks.length; i++) {
                    chunk = chunks[i];
                    chunkLength = chunk.length();
                    if (chunkLength > averageChunkLength) {
                        plus = averageChunkLength + over > chunkLength ? chunkLength - averageChunkLength : over;
                        if (i == lastOverAverageChunkIndex) {
                            size = averageChunkLength + free;
                            free = 0;
                        } else if (free < plus) {
                            size = averageChunkLength;
                        } else {
                            size = averageChunkLength + plus;
                            free -= plus;
                        }
                    } else {
                        size = chunkLength;
                    }
                    shorterChunks[i] = chunk.substring(0, Math.min(size, chunkLength));
                }
                shorterString = StringUtils.join(shorterChunks, separator);
            } else if (available1 > 0) {
                shorterString = trimmedString.substring(0, available1);
            } else {
                shorterString = trimmedString.substring(0, maxLength);
            }
        }
        logIdentificadorSql(endlessString, shorterString);
        return shorterString;
    }

    private static final Set<String> setIdentificadorSql = new LinkedHashSet<>();

    private static void logIdentificadorSql(String endless, String shorter) {
        if (endless.equals(shorter)) {
            return;
        }
        String shorterLength = String.format("%03d", shorter.length());
        String endlessLength = String.format("%03d", endless.length());
        String string = "[" + shorterLength + " < " + endlessLength + "] " + shorter + " < " + endless;
        if (setIdentificadorSql.contains(string)) {
        } else {
            setIdentificadorSql.add(string);
            logger.trace(string);
        }
    }

    public static String getIdentificadorSql(String string) {
        if (string == null) {
            return null;
        }
        String x = getStringAscii(string);
        String y = "";
        String z;
        char c;
        boolean b = false;
        for (int i = 0; i < x.length(); i++) {
            c = x.charAt(i);
            if ((c >= 'a') && (c <= 'z') || (c >= 'A') && (c <= 'Z') || (c >= '0') && (c <= '9')) {
                y += c;
                b = false;
            } else {
                z = b ? "" : "_";
                y += z;
                b = true;
            }
        }
        return y;
    }

    public static String getIdentificadorSqlLowerCase(String string) {
        return string == null ? null : getIdentificadorSql(string).toLowerCase();
    }

    public static String getIdentificadorSqlUpperCase(String string) {
        return string == null ? null : getIdentificadorSql(string).toUpperCase();
    }

    public static String getIdentifier(String string) {
        return getIdentifier(string, "_");
    }

    public static String getIdentifier(String string, char separator) {
        return getIdentifier(string, "" + separator);
    }

    public static String getIdentifier(String string, String separator) {
        if (string == null) {
            return null;
        }
        String x = getStringAscii(string);
        String y = "";
        String z;
        char c;
        boolean b = false;
        for (int i = 0; i < x.length(); i++) {
            c = x.charAt(i);
            if ((c >= 'a') && (c <= 'z') || (c >= 'A') && (c <= 'Z') || (c >= '0') && (c <= '9')) {
                y += c;
                b = false;
            } else {
                z = b ? "" : separator;
                y += z;
                b = true;
            }
        }
        return y;
    }

    public static String getLowerCaseIdentifier(String string) {
        return string == null ? null : getLowerCaseIdentifier(string, "_");
    }

    public static String getLowerCaseIdentifier(String string, char separator) {
        return getLowerCaseIdentifier(string, "" + separator);
    }

    public static String getLowerCaseIdentifier(String string, String separator) {
        return string == null ? null : getHumplessCase(getIdentifier(string, separator), separator).toLowerCase();
    }

    public static String getUpperCaseIdentifier(String string) {
        return string == null ? null : getUpperCaseIdentifier(string, "_");
    }

    public static String getUpperCaseIdentifier(String string, char separator) {
        return getUpperCaseIdentifier(string, "" + separator);
    }

    public static String getUpperCaseIdentifier(String string, String separator) {
        return string == null ? null : getHumplessCase(getIdentifier(string, separator), separator).toUpperCase();
    }

    public static String getQualifiedName(String name, String qualifier) {
        String n, q;
        if (StringUtils.isNotBlank(name)) {
            n = name.trim();
            if (StringUtils.isNotBlank(qualifier)) {
                q = qualifier.trim();
                return q + "." + n;
            }
            return n;
        }
        return null;
    }

    public static String getUnqualifiedName(String name, String qualifier) {
        String n, q;
        if (StringUtils.isNotBlank(name)) {
            n = name.trim();
            if (StringUtils.isNotBlank(qualifier)) {
                q = qualifier.trim();
                n = StringUtils.removeStart(n, q + ".");
            }
            return n;
        }
        return null;
    }

    public static String getQualifiedShortName(String name, String qualifier) {
        String n, q;
        if (StringUtils.isNotBlank(name)) {
            n = name.trim();
            if (StringUtils.isNotBlank(qualifier)) {
                q = qualifier.trim();
                n = StringUtils.removeStart(n, q + ".");
                n = StringUtils.removeStart(n, q + "_");
                n = StringUtils.removeEnd(n, "_" + q);
                return q + "." + n;
            }
            return n;
        }
        return null;
    }

    public static String getUnqualifiedShortName(String name, String qualifier) {
        String n, q;
        if (StringUtils.isNotBlank(name)) {
            n = name.trim();
            if (StringUtils.isNotBlank(qualifier)) {
                q = qualifier.trim();
                n = StringUtils.removeStart(n, q + ".");
                n = StringUtils.removeStart(n, q + "_");
                n = StringUtils.removeEnd(n, "_" + q);
            }
            return n;
        }
        return null;
    }

    public static String getStringAscii(String string) {
        if (string == null) {
            return null;
        }
        String s = StringUtils.trimToEmpty(string);
        s = s.replace("á", "a");
        s = s.replace("é", "e");
        s = s.replace("í", "i");
        s = s.replace("ó", "o");
        s = s.replace("ú", "u");
        s = s.replace("ü", "u");
        s = s.replace("ñ", "n");
        s = s.replace("Á", "A");
        s = s.replace("É", "E");
        s = s.replace("Í", "I");
        s = s.replace("Ó", "O");
        s = s.replace("Ú", "U");
        s = s.replace("Ü", "U");
        s = s.replace("Ñ", "N");
        try {
            byte[] bytes = s.getBytes();
            return new String(bytes, "US-ASCII");
        } catch (UnsupportedEncodingException ex) {
            logger.fatal(ThrowableUtils.getString(ex), ex);
        }
        return s;
    }

    public static String getStringUtf8(String string) {
        if (string == null) {
            return null;
        }
        try {
            byte[] bytes = StringUtils.trimToEmpty(string).getBytes();
            return new String(bytes, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            logger.fatal(ThrowableUtils.getString(ex), ex);
        }
        return null;
    }

    public static String getCamelCase(String string) {
        return getCamelCase(string, null);
    }

    public static String getCamelCase(String string, String gap) {
        return getCamelCase(string, gap, false);
    }

    public static String getCamelCase(String string, boolean toLowerCaseLess) {
        return getCamelCase(string, null, toLowerCaseLess);
    }

    public static String getCamelCase(String string, String gap, boolean toLowerCaseLess) {
        if (string == null) {
            return null;
        }
        String x = string.trim();
        String y = "";
        String z = StringUtils.isBlank(gap) ? EMPTY : gap.trim();
        boolean b = false;
        boolean g = false;
        char c;
        for (int i = 0; i < x.length(); i++) {
            c = x.charAt(i);
            switch (c) {
                case '_':
                case '-':
                case '.':
                    b = true;
                    break;
                default:
                    if (b) {
                        y += g ? z : "";
                        y += Character.toUpperCase(c);
                    } else {
                        y += toLowerCaseLess ? c : Character.toLowerCase(c);
                    }
                    b = false;
                    g = true;
                    break;
            }
        }
        return y;
    }

    public static String getLowerCamelCase(String string) {
        return getCamelCase(string);
    }

    public static String getUpperCamelCase(String string) {
        return getCamelCase('_' + string.trim());
    }

    public static String getSpaceCamelCase(String string) {
        return getCamelCase('_' + string.trim(), " ");
    }

    public static String getHumplessCase(String string) {
        return getHumplessCase(string, '_');
    }

    public static String getHumplessCase(String string, char hump) {
        return getHumplessCase(string, "" + hump);
    }

    public static String getHumplessCase(String string, String hump) {
        if (string == null) {
            return null;
        }
        if (hump == null) {
            return null;
        }
        if (isNotMixedCase(string)) {
            return string;
        }
        String x = string.trim();
        String y = "";
        boolean b = false;
        char c;
        for (int i = 0; i < x.length(); i++) {
            c = x.charAt(i);
            if (Character.isUpperCase(c)) {
                if (b) {
                    y += hump;
                }
                y += Character.toLowerCase(c);
            } else {
                y += c;
            }
            b = true;
        }
        return y;
    }

    public static String getLowerHumplessCase(String string) {
        String humpless = getHumplessCase(string);
        return humpless == null ? null : humpless.toLowerCase();
    }

    public static String getUpperHumplessCase(String string) {
        String humpless = getHumplessCase(string);
        return humpless == null ? null : humpless.toUpperCase();
    }

    public static boolean isMixedCase(String string) {
        String trimmed = StringUtils.trimToEmpty(string);
        if (trimmed.isEmpty()) {
            return false;
        }
        trimmed = trimmed.replaceAll("[^a-zA-Z]", "");
        return !trimmed.isEmpty() && !StringUtils.isAllLowerCase(trimmed) && !StringUtils.isAllUpperCase(trimmed);
    }

    public static boolean isNotMixedCase(String string) {
        return !isMixedCase(string);
    }

    public static String getWordyString(String string) {
        return StringUtils.isBlank(string) ? EMPTY
            : isMixedCase(string) ? getHumplessCase(string, ' ')
                : string.toLowerCase().replace('_', ' ').replaceAll("  ", " ").trim();
    }

    public static String getUnderscoreless(String string) {
        if (string == null) {
            return null;
        }
        return string.trim().replace('_', ' ');
    }

    public static String getStringHtml(String string) {
        return StringUtils.isBlank(string) ? null : StringEscapeUtils.escapeHtml(string);
    }

    public static String getStringJava(String string) {
        String s = StringUtils.isBlank(string) ? null : StringEscapeUtils.escapeJava(string);
        return s == null ? null : s.replace("\\/", "/");
    }

    public static String getStringXml(String string) {
        return StringUtils.isBlank(string) ? null : StringEscapeUtils.escapeXml(string);
    }

    public static String getToken(String string) {
        return getToken(string, 0);
    }

    public static String getToken(String string, int index) {
        return getToken(string, index, " ");
    }

    public static String getToken(String string, String regex) {
        return getToken(string, 0, regex);
    }

    public static String getToken(String string, int index, String regex) {
        if (string == null) {
            return null;
        }
        String[] tokens = string.split(regex);
        return tokens == null || tokens.length == 0 ? string : index < tokens.length ? tokens[index] : tokens[tokens.length - 1];
    }

    public static String getPatronParametrizado(String string) {
        String patron = StringUtils.trimToEmpty(string);
        String[] subs = StringUtils.substringsBetween(patron, "{", "}");
        int i = 0;
        for (String sub : subs) {
            patron = patron.replace("{" + sub + "}", "{" + i++ + "}");
        }
        return patron;
    }

    public static String[] getParametros(String string) {
        return StringUtils.substringsBetween(string, "{", "}");
    }

    public static Object getObjeto(String string) {
        Object objeto = null;
        String cadena = StringUtils.trimToNull(string);
        if (cadena == null) {
            return null;
        }
        if (StringUtils.isNumeric(cadena)) {
//          objeto = getObjeto(cadena, EnumTipoDatoParametro.ENTERO);
            objeto = getObjeto(cadena, Integer.class);
        }
        if (objeto == null && StringUtils.isNumeric(cadena)) {
//          objeto = getObjeto(cadena, EnumTipoDatoParametro.ENTERO_GRANDE);
            objeto = getObjeto(cadena, BigInteger.class);
        }
        if (objeto == null && cadena.startsWith(ID_PREFIX_STRING)) {
            String substr = cadena.substring(1);
            if (StringUtils.isNumeric(substr)) {
//              objeto = getObjeto(substr, EnumTipoDatoParametro.ENTERO_GRANDE);
                objeto = getObjeto(substr, BigInteger.class);
            }
        }
        if (objeto == null) {
//          objeto = getObjeto(cadena, EnumTipoDatoParametro.NUMERICO);
            objeto = getObjeto(cadena, BigDecimal.class);
        }
        if (objeto == null) {
//          objeto = getObjeto(cadena, EnumTipoDatoParametro.FECHA_HORA);
            objeto = getObjeto(cadena, Timestamp.class);
        }
        if (objeto == null) {
//          objeto = getObjeto(cadena, EnumTipoDatoParametro.ALFANUMERICO);
            objeto = getObjeto(cadena, String.class);
        }
        return objeto;
    }

    public static Object getObjeto(String string, String clazz) {
        if (StringUtils.isNotBlank(string)) {
            try {
                return getObjeto(string, Class.forName(clazz));
            } catch (ClassNotFoundException ex) {
                return null;
            }
        }
        return null;
    }

    public static Object getObjeto(String string, Class<?> clazz) {
        if (string == null || clazz == null) {
            return null;
        }
        try {
            String value = StringUtils.trimToNull(string);
            if (value == null) {
                return null;
            } else if (Character.class.isAssignableFrom(clazz)) {
                return value.charAt(0);
            } else if (String.class.isAssignableFrom(clazz)) {          // ALFANUMERICO
                return string;
            } else if (Boolean.class.isAssignableFrom(clazz)) {
                return BitUtils.valueOf(value);
            } else if (Byte.class.isAssignableFrom(clazz)) {
                return new BigDecimal(value).byteValue();
            } else if (Short.class.isAssignableFrom(clazz)) {
                return new BigDecimal(value).shortValue();
            } else if (Integer.class.isAssignableFrom(clazz)) {         // ENTERO
                return new BigDecimal(value).intValue();
            } else if (Long.class.isAssignableFrom(clazz)) {
                return new BigDecimal(value).longValue();
            } else if (Float.class.isAssignableFrom(clazz)) {
                return new BigDecimal(value).floatValue();
            } else if (Double.class.isAssignableFrom(clazz)) {
                return new BigDecimal(value).doubleValue();
            } else if (BigInteger.class.isAssignableFrom(clazz)) {      // ENTERO_GRANDE
                return new BigDecimal(value).longValue();
            } else if (BigDecimal.class.isAssignableFrom(clazz)) {      // NUMERICO
                return new BigDecimal(value);
            } else if (java.util.Date.class.isAssignableFrom(clazz)) {  // FECHA_HORA
                int year = 0;
                int month = 0;
                int day = 0;
                int hour = 0;
                int minute = 0;
                int second = 0;
                String xm = "";
                String xs = "";
                int len = value.length();
                switch (len) {
                    case 22:
                        xm = value.substring(20);
                    case 19:
                        if (xm.equals("")) {
                            xs = value.substring(17, 19);
                        }
                        if (xs.equalsIgnoreCase("AM") || xs.equalsIgnoreCase("PM")) {
                            xm = xs;
                        } else {
                            second = Integer.parseInt(value.substring(17, 19));
                        }
                    case 16:
                        minute = Integer.parseInt(value.substring(14, 16));
                        hour = Integer.parseInt(value.substring(11, 13));
                        if (xm.equalsIgnoreCase("AM") && hour == 12) {
                            hour = 0;
                        }
                        if (xm.equalsIgnoreCase("PM") && hour <= 11) {
                            hour += 12;
                        }
                    case 10:
                        year = Integer.parseInt(value.substring(6, 10));
                        month = Integer.parseInt(value.substring(3, 5));
                        day = Integer.parseInt(value.substring(0, 2));
                        break;
                }
                if (nbw(year, 1, 9999) || nbw(month, 1, 12) || nbw(day, 1, 31) || nbw(hour, 0, 23) || nbw(minute, 0, 59) || nbw(second, 0, 59)) {
                    return null;
                }
                if (Timestamp.class.isAssignableFrom(clazz)) {
                    return TimeUtils.newTimestamp(year, month, day, hour, minute, second);
                } else if (Time.class.isAssignableFrom(clazz)) {
                    return TimeUtils.newTime(hour, minute, second);
                } else if (Date.class.isAssignableFrom(clazz)) {
                    return TimeUtils.newDate(year, month, day);
                } else {
                    return TimeUtils.newUtilDate(year, month, day, hour, minute, second);
                }
            }
//      } catch (NumberFormatException e) {
//          return null;
        } catch (RuntimeException e) {
            return null;
        }
        return null;
    }

    private static boolean nbw(int i, int lower, int upper) {
        return i < lower || i > upper;
    }

    public static boolean esObjetoEnRango(Object objeto, Object minimo, Object maximo) {
        boolean es = true;
//      EnumTipoDatoParametro tipo;
        if (objeto == null) {
            return false;
        } else if (objeto instanceof String) {
//          tipo = EnumTipoDatoParametro.ALFANUMERICO;
            String val1 = (String) objeto;
            String min1 = (String) minimo;
            String max1 = (String) maximo;
            if (min1 != null && val1.compareTo(min1) < 0) {
                es = false;
            }
            if (max1 != null && val1.compareTo(max1) > 0) {
                es = false;
            }
        } else if (objeto instanceof Integer) {
//          tipo = EnumTipoDatoParametro.ENTERO;
            Integer val4 = (Integer) objeto;
            Integer min4 = (Integer) minimo;
            Integer max4 = (Integer) maximo;
            if (min4 != null && val4.compareTo(min4) < 0) {
                es = false;
            }
            if (max4 != null && val4.compareTo(max4) > 0) {
                es = false;
            }
        } else if (objeto instanceof Long || objeto instanceof BigInteger) {
//          tipo = EnumTipoDatoParametro.ENTERO_GRANDE;
            Long val5 = objeto instanceof BigInteger ? ((BigInteger) objeto).longValue() : (Long) objeto;
            Long min5 = (Long) minimo;
            Long max5 = (Long) maximo;
            if (min5 != null && val5.compareTo(min5) < 0) {
                es = false;
            }
            if (max5 != null && val5.compareTo(max5) > 0) {
                es = false;
            }
        } else if (objeto instanceof BigDecimal) {
//          tipo = EnumTipoDatoParametro.NUMERICO;
            BigDecimal val2 = (BigDecimal) objeto;
            BigDecimal min2 = (BigDecimal) minimo;
            BigDecimal max2 = (BigDecimal) maximo;
            if (min2 != null && val2.compareTo(min2) < 0) {
                es = false;
            }
            if (max2 != null && val2.compareTo(max2) > 0) {
                es = false;
            }
        } else if (objeto instanceof Timestamp) {
//          tipo = EnumTipoDatoParametro.FECHA_HORA;
            Timestamp val3 = (Timestamp) objeto;
            Timestamp min3 = (Timestamp) minimo;
            Timestamp max3 = (Timestamp) maximo;
            if (min3 != null && val3.compareTo(min3) < 0) {
                es = false;
            }
            if (max3 != null && val3.compareTo(max3) > 0) {
                es = false;
            }
        } else {
            return false;
        }
        return es;
    }

    public static String lineSeparator() {
        return System.getProperty("line.separator");
    }

    public static char newline() {
        return '\n';
    }

    public static char tab() {
        return '\t';
    }

    public static String tabs(int n) {
        return StringUtils.repeat("\t", n);
    }

    public static boolean isDelimited(String string, char delimiter) {
        if (StringUtils.isBlank(string)) {
            return false;
        }
        String s = StringUtils.trimToEmpty(string);
        int j = 1; // delimiter length
        int k = 2 * j;
        int l = s.length();
        int n = l - j;
        if (l >= k && s.charAt(0) == delimiter && s.charAt(n) == delimiter) {
            return l == k || s.substring(j, n).indexOf(delimiter) < 0;
        }
        return false;
    }

    public static boolean isDelimited(String string, String delimiter) {
        if (StringUtils.isBlank(string) || StringUtils.isBlank(delimiter)) {
            return false;
        }
        String s = StringUtils.trimToEmpty(string);
        int j = delimiter.length();
        int k = 2 * j;
        int l = s.length();
        int n = l - j;
        if (l >= k && s.startsWith(delimiter) && s.endsWith(delimiter)) {
            return l == k || !s.substring(j, n).contains(delimiter);
        }
        return false;
    }

    public static boolean isDelimited(String string, char open, char close) {
        if (StringUtils.isBlank(string)) {
            return false;
        }
        if (open == close) {
            return isDelimited(string, open);
        }
        String s = StringUtils.trimToEmpty(string);
        int l = s.length();
        int n = l - 1;
        if (s.charAt(0) == open && s.charAt(n) == close) {
            int opened = 0;
            int closed = 0;
            for (int x = 0; x < l; x++) {
                if (s.charAt(x) == open) {
                    opened++;
                } else if (s.charAt(x) == close) {
                    closed++;
                }
                if (opened > closed) {
                } else {
                    return opened == closed && x == n;
                }
            }
        }
        return false;
    }

    public static boolean isDelimited(String string, String open, String close) {
        if (StringUtils.isBlank(string) || StringUtils.isBlank(open) || StringUtils.isBlank(close)) {
            return false;
        }
        if (open.equals(close)) {
            return isDelimited(string, open);
        }
        String s = StringUtils.trimToEmpty(string);
        int i = open.length();
        int j = close.length();
        int k = i + j;
        int l = s.length();
        int n = l - j;
        if (l >= k && s.startsWith(open) && s.endsWith(close)) {
            int opened = 0;
            int closed = 0;
            int x = 0;
            int y, z;
            for (; x < l;) {
                y = s.indexOf(open, x);
                z = s.indexOf(close, x);
                if (y >= x && y < z) {
                    opened++;
                    x = y + open.length();
                } else if (z >= x) {
                    closed++;
                    x = z + close.length();
                }
                if (opened > closed) {
                } else {
                    return opened == closed && x == l;
                }
            }
        }
        return false;
    }

    public static String encloseSqlExpression(String expression) {
        return expression == null ? null
            : esInvocacionFuncionSql(expression) ? expression
                : enclose(expression, '(', ')');
    }

    public static String enclose(String argument) {
        return argument == null ? null
            : enclose(argument, '(', ')');
    }

    public static String enclose(String argument, char delimiter) {
        return argument == null ? null
            : isDelimited(argument, delimiter) ? argument
                : delimiter + argument + delimiter;
    }

    public static String enclose(String argument, char open, char close) {
        return argument == null ? null
            : isDelimited(argument, open, close) ? argument
                : open + argument + close;
    }

    public static String enclose(String argument, String delimiter) {
        return argument == null ? null
            : isDelimited(argument, delimiter) ? argument
                : delimiter + argument + delimiter;
    }

    public static String enclose(String argument, String open, String close) {
        return argument == null ? null
            : isDelimited(argument, open, close) ? argument
                : open + argument + close;
    }

    public static String disclose(String argument) {
        return argument == null ? null
            : disclose(argument, '(', ')');
    }

    public static String disclose(String argument, char delimiter) {
        return argument == null ? null
            : isDelimited(argument, delimiter) ? argument.substring(1, argument.length() - 1)
                : argument;
    }

    public static String disclose(String argument, char open, char close) {
        return argument == null ? null
            : isDelimited(argument, open, close) ? argument.substring(1, argument.length() - 1)
                : argument;
    }

    public static String disclose(String argument, String delimiter) {
        return argument == null ? null
            : isDelimited(argument, delimiter) ? argument.substring(1, argument.length() - 1)
                : argument;
    }

    public static String disclose(String argument, String open, String close) {
        return argument == null ? null
            : isDelimited(argument, open, close) ? argument.substring(1, argument.length() - 1)
                : argument;
    }

    public static String removeWords(String string, Class<?> dataType) {
        return removeWords(string, dataType, null);
    }

    public static String removeWords(String string, Class<?> dataType, String separator) {
        if (string == null || dataType == null) {
            return string;
        }
        string = removeWords(string, dataType, 'p', separator);
        string = removeWords(string, dataType, 's', separator);
        string = removeWords(string, dataType, '*', separator);
        return string;
    }

    public static String removeWords(String string, Class<?> dataType, char affixType) {
        return removeWords(string, dataType, affixType, null);
    }

    public static String removeWords(String string, Class<?> dataType, char affixType, String separator) {
        if (string == null || dataType == null) {
            return string;
        }
        String tag = affixType == 'p' ? "name.prefix" : affixType == 's' ? "name.suffix" : "name.infix";
        String key = dataType.getSimpleName() + "." + tag;
        String remove = Bundle.getTrimmedToNullString(key);
        return remove == null ? string : removeWords(string, remove, affixType, separator);
    }

    public static String removeWords(String string, String remove) {
        return removeWords(string, remove, null);
    }

    public static String removeWords(String string, String remove, String separator) {
        if (string == null || remove == null) {
            return string;
        }
        string = removeWords(string, remove, 'p', separator);
        string = removeWords(string, remove, 's', separator);
        string = removeWords(string, remove, '*', separator);
        return string;
    }

    public static String removeWords(String string, String remove, char affixType) {
        return removeWords(string, remove, affixType, null);
    }

    public static String removeWords(String string, String remove, char affixType, String separator) {
        if (string == null || remove == null) {
            return string;
        }
        String separatorChars = ", ";
        String[] tokens = StringUtils.split(remove, separatorChars);
        for (String token : tokens) {
            remove = getWordyString(token);
            string = removeWholeWord(string, remove, affixType, separator);
        }
        return string;
    }

    public static String removeWholeWord(String string, String remove) {
        return removeWholeWord(string, remove, null);
    }

    public static String removeWholeWord(String string, String remove, String separator) {
        if (string == null || remove == null) {
            return string;
        }
        string = removeWholeWord(string, remove, 'p', separator);
        string = removeWholeWord(string, remove, 's', separator);
        string = removeWholeWord(string, remove, '*', separator);
        return string;
    }

    public static String removeWholeWord(String string, String remove, char affixType) {
        return removeWholeWord(string, remove, affixType, null);
    }

    public static String removeWholeWord(String string, String remove, char affixType, String separator) {
        if (separator == null) {
            separator = " ";
        }
        return string == null || remove == null ? string
            : affixType == 'p' ? StringUtils.removeStart(string, remove + separator)
                : affixType == 's' ? StringUtils.removeEnd(string, separator + remove)
                    : StringUtils.replace(string, separator + remove + separator, separator);
    }

}
