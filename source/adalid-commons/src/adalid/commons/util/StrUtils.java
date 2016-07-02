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
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.regex.Pattern;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class StrUtils {

    private static final Logger logger = Logger.getLogger(StrUtils.class);

    public static final String VALID_CHARS = "abcdefghijklmnopqrstuvwxyz_1234567890";

    public static String ltrim(String s) {
        return s == null ? null : s.replaceAll("^\\s+", "");
    }

    public static String rtrim(String s) {
        return s == null ? null : s.replaceAll("\\s+$", "");
    }

    public static String toString(Object obj) {
        return obj == null ? null : obj instanceof String ? (String) obj : obj.toString();
    }

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
        return string == null ? StringUtils.EMPTY : string;
    }

    public static String coalesceToEmpty(String... strings) {
        String string = coalesce(strings);
        return string == null ? StringUtils.EMPTY : string;
    }

    /*
     * used at velocity templates
     */
    public static String coalesceToNull(String strings) {
        String string = coalesce(strings);
        return string == null ? Bundle.getString("null_value.string") : string;
    }

    public static String coalesceToNull(String... strings) {
        String string = coalesce(strings);
        return string == null ? Bundle.getString("null_value.string") : string;
    }

    private static final int MDD = 13; // maximum number of damm digits

    private static final double X2MDD = Math.pow(10, MDD); // ten raised to the maximum number of damm digits

    public static String getLongNumericCode(String string) {
        if (StringUtils.isBlank(string)) {
            return null;
        }
        String clean = string.trim();
        String first = firstDigit(clean);
        String numst = getNumericString(clean);
        String digit = Damm.digit(numst) + "";
        int l = clean.length();
        int m = MDD; // maximum number of damm digits
        int d = l < m ? l : m; // the actual number of damm digits to be determined
        int w = l / d; // the minimum width of each chunk of the string
        int x; // the actual width of the chunk
        int r = l % d; // remainder width
        int beginIndex = 0;
        int endIndex;
        String chunk;
        String digits = "";
        for (int i = 0; i < d; i++) {
            x = i < r ? w + 1 : w;
            endIndex = beginIndex + x;
            chunk = clean.substring(beginIndex, endIndex);
            digits += Damm.digit(getNumericString(chunk)) + "";
            beginIndex = endIndex;
        }
        char[] chars = clean.toCharArray();
        int sum = 0;
        for (char c : chars) {
            sum += c;
        }
        if (m > beginIndex) {
            String filler = ((long) X2MDD * sum / m) + "";
            digits += filler.substring(0, m - beginIndex);
        }
        return first + digits + digit + String.format("%04d", sum % 10000); // 1 + 13 + 1 + 4 = 19
    }

    private static String firstDigit(String string) {
        char c = Character.toUpperCase(string.charAt(0));
        int i = digitBetweenZeroAndEight(c);
        return i + "";
    }
//
//  private static String lastDigit(String string) {
//      char c = Character.toUpperCase(string.charAt(string.length() - 1));
//      int i = Character.isDigit(c) ? 9 : digitBetweenZeroAndEight(c);
//      return i + "";
//  }

    private static int digitBetweenZeroAndEight(char c) {
        return c < 'A' ? 0 : c < 'D' ? 1 : c < 'G' ? 2 : c < 'J' ? 3 : c < 'M' ? 4 : c < 'P' ? 5 : c < 'T' ? 6 : c < 'W' ? 7 : c > 'Z' ? 0 : 8;
    }

    public static String getRandomString() {
        return getRandomString(0);
    }

    public static String getRandomString(int length) {
        String uuid = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
        int endIndex = length < 1 || length > uuid.length() ? uuid.length() : length;
        return uuid.substring(0, endIndex);
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
        } else if (obj instanceof java.util.Date) {
            return TimeUtils.defaultString((java.util.Date) obj);
        } else {
            return obj.toString();
        }
    }

    public static String getStringDelimitado(Object obj) {
        String string = getString(obj);
        if (string == null) {
            return null;
        } else if (obj instanceof String) {
            return "'" + string + "'";
//      } else if (obj instanceof java.util.Date) {
//          return "'" + string + "'";
        } else {
            return string;
        }
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
        } else if (obj instanceof java.util.Date) {
            return TimeUtils.jdbcString((java.util.Date) obj);
        } else {
            return obj.toString();
        }
    }

    public static String getStringSqlDelimitado(Object obj) {
        String string = getStringSql(obj);
        if (string == null) {
            return null;
        } else if (obj instanceof String) {
            return "'" + string + "'";
        } else if (obj instanceof java.util.Date) {
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

    public static String getString(String equals, String separator, String open, String close, Object object) {
        if (ObjUtils.isBlank(object)) {
            return StringUtils.EMPTY;
        }
        if (object.getClass().isArray()) {
            Object[] array = (Object[]) object;
            return getString(equals, separator, open, close, array);
        }
        if (object instanceof KVP) {
            KVP value = (KVP) object;
            return value.toString(equals, separator, open, close);
        }
        if (object instanceof String) {
            String value = (String) object;
            return StringUtils.trimToEmpty(value);
        }
        return StringUtils.trimToEmpty(object.toString());
    }

    public static String getString(String equals, String separator, String open, String close, Object... objects) {
        List<String> strings = new ArrayList<>();
        for (Object object : objects) {
            if (object != null) {
                strings.add(getString(equals, separator, open, close, object));
            }
        }
        return enclose(StringUtils.join(strings, separator), open, close);
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
        return StringUtils.isNotBlank(string) && string.matches("^[a-z][a-z_0-9]*$");
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
        return StringUtils.isNotBlank(string) && string.matches("^[\\w\\-\\.]*$");
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
//      logIdentificadorSql(endlessString, shorterString);
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
//      logIdentificadorSql(endlessString, shorterString);
        return shorterString;
    }
//
//  private static final Set<String> setIdentificadorSql = new LinkedHashSet<>();
//
//  private static void logIdentificadorSql(String endless, String shorter) {
//      if (endless.equals(shorter)) {
//          return;
//      }
//      String shorterLength = String.format("%03d", shorter.length());
//      String endlessLength = String.format("%03d", endless.length());
//      String string = "[" + shorterLength + " < " + endlessLength + "] " + shorter + " < " + endless;
//      if (setIdentificadorSql.contains(string)) {
//      } else {
//          setIdentificadorSql.add(string);
//          logger.trace(string);
//      }
//  }

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

    public static String getIdentificadorSql(String string) {
        return string == null ? null : getIdentifier(string);
    }

    public static String getIdentificadorSqlLowerCase(String string) {
        return string == null ? null : getIdentificadorSql(string).toLowerCase();
    }

    public static String getIdentificadorSqlUpperCase(String string) {
        return string == null ? null : getIdentificadorSql(string).toUpperCase();
    }

    private static final String DEFAULT_SEPARATOR = "_";

    private static final String INVALID_SEPARATOR = "[\\u0000-\\u001F0-9A-Za-z\\u0080-\\uFFFF]";

    public static String getIdentifier(String string) {
        return getIdentifier(string, DEFAULT_SEPARATOR);
    }

    public static String getIdentifier(String string, char separator) {
        return getIdentifier(string, "" + separator);
    }

    public static String getIdentifier(String string, String separator) {
        if (string == null) {
            return null;
        }
        String sep = separator == null ? DEFAULT_SEPARATOR : separator.replaceAll(INVALID_SEPARATOR, DEFAULT_SEPARATOR);
        String invalidCharactersRegex = "[^a-zA-Z0-9]";
        String severalSeparatorsRegex = sep.length() == 0 ? null : Pattern.quote(sep) + "+";
        String dhxless = dhxless(string, sep, invalidCharactersRegex, severalSeparatorsRegex);
        return dhxless;
    }

    public static String getLowerCaseIdentifier(String string) {
        return string == null ? null : getLowerCaseIdentifier(string, DEFAULT_SEPARATOR);
    }

    public static String getLowerCaseIdentifier(String string, char separator) {
        return getLowerCaseIdentifier(string, "" + separator);
    }

    public static String getLowerCaseIdentifier(String string, String separator) {
        return string == null ? null : getHumplessCase(getIdentifier(string, separator), separator).toLowerCase();
    }

    public static String getUpperCaseIdentifier(String string) {
        return string == null ? null : getUpperCaseIdentifier(string, DEFAULT_SEPARATOR);
    }

    public static String getUpperCaseIdentifier(String string, char separator) {
        return getUpperCaseIdentifier(string, "" + separator);
    }

    public static String getUpperCaseIdentifier(String string, String separator) {
        return string == null ? null : getHumplessCase(getIdentifier(string, separator), separator).toUpperCase();
    }

    public static String getMavenIdentifier(String string) {
        String fileName = getFileName(string);
        return fileName == null ? null : fileName.matches("^[a-z].*$") ? fileName : "x" + fileName;
    }

    public static String getFileName(String string) {
        return getFileName(string, null);
    }

    public static String getFileName(String string, String separator) {
        if (string == null) {
            return null;
        }
        final String defaultSeparator = "-";
        final String escapedSeparator = "\\_\\-\\.";
        final String invalidSeparator = "[^" + escapedSeparator + "]";
        final String prefixSeparators = "^[" + escapedSeparator + "]+";
        final String suffixSeparators = "[" + escapedSeparator + "]+$";
        String sep = separator == null ? defaultSeparator : separator.replaceAll(invalidSeparator, defaultSeparator);
        String invalidCharactersRegex = "[^\\w\\-\\.]";
        String severalSeparatorsRegex = Pattern.quote(sep) + "+";
        String dhxless = dhxless(string, sep, invalidCharactersRegex, severalSeparatorsRegex);
        String trimmed = dhxless.replaceAll(prefixSeparators, "").replaceAll(suffixSeparators, "");
        return trimmed;
    }

    private static String dhxless(String string, String separator, String... expressions) {
        assert string != null;
        assert separator != null;
        assert expressions != null;
        assert expressions.length > 0;
        String s = separator.replaceAll(INVALID_SEPARATOR, DEFAULT_SEPARATOR);
        String t = string.trim();
        String d = diacriticless(t);
        String h = getHumplessCase(d, s);
        String x = h;
        for (String regex : expressions) {
            if (regex != null) {
                x = x.replaceAll(regex, s);
            }
        }
        return x;
    }

    private static boolean isLetterOrDigit(char c) {
        return ((c >= 'A') && (c <= 'Z')) || ((c >= 'a') && (c <= 'z')) || ((c >= '0') && (c <= '9'));
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

    private static final String DEFAULT_REPLACEMENT = "_";

    /**
     * replaces all non-printable and control characters in a string with an underscore.
     *
     * @param string
     * @return an ASCII string
     */
    public static String ascii(String string) {
        return ascii(string, '_');
    }

    /**
     * replaces all non-printable and control characters in a string with the specified replacement character.
     *
     * @param string
     * @param replacement
     * @return an ASCII string
     */
    public static String ascii(String string, char replacement) {
        return ascii(string, "" + replacement);
    }

    /**
     * replaces all non-printable and control characters in a string with the specified replacement string.
     *
     * @param string
     * @param replacement
     * @return an ASCII string
     */
    public static String ascii(String string, String replacement) {
        final String invalidCharacters = "[^ -~]";
        if (string == null) {
            return null;
        }
        String valid = replacement == null ? DEFAULT_REPLACEMENT : replacement.replaceAll(invalidCharacters, DEFAULT_REPLACEMENT);
        String ascii = StringUtils.trimToEmpty(string).replaceAll(invalidCharacters, valid);
        return ascii;
    }

    /**
     * replaces all characters with diacritical marks in a string with their corresponding letter.
     *
     * @param string
     * @return a string without diacritical marks
     */
    public static String diacriticless(String string) {
        final String with = "ÀÁÂÃÄÅÇÈÉÊËÌÍÎÏÑÒÓÔÕÖÙÚÛÜÝàáâãäåçèéêëìíîïñòóôõöùúûüýÿ";
        final String sans = "AAAAAACEEEEIIIINOOOOOUUUUYaaaaaaceeeeiiiinooooouuuuyy";
        String dless = string == null ? null : StringUtils.replaceChars(StringUtils.trimToEmpty(string), with, sans);
        return dless;
    }

    /**
     * replaces all characters with diacritical marks with their corresponding letter and all non-printable and control characters with an undersocre.
     *
     * @param string
     * @return an ASCII string
     */
    public static String diacriticlessAscii(String string) {
        return diacriticlessAscii(string, DEFAULT_REPLACEMENT);
    }

    /**
     * replaces all characters with diacritical marks with their corresponding letter and all non-printable and control characters with the specified
     * replacement string.
     *
     * @param string
     * @param replacement
     * @return an ASCII string
     */
    public static String diacriticlessAscii(String string, String replacement) {
        return ascii(diacriticless(string), replacement);
    }

    public static String getStringAscii(String string) {
        if (string == null) {
            return null;
        }
        String dless = diacriticlessAscii(string);
        byte[] bytes = dless.getBytes();
        String ascii = new String(bytes, StandardCharsets.US_ASCII);
        return ascii;
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
        String z = StringUtils.isBlank(gap) ? StringUtils.EMPTY : gap.trim();
        boolean b = false;
        boolean g = false;
        char c;
        for (int i = 0; i < x.length(); i++) {
            c = x.charAt(i);
            if (isLetterOrDigit(c)) {
                if (b) {
                    y += g ? z : "";
                    y += Character.toUpperCase(c);
                } else {
                    y += toLowerCaseLess ? c : Character.toLowerCase(c);
                }
                b = false;
                g = true;
            } else {
                b = true;
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
            b = isLetterOrDigit(c);
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
        return !isNotMixedCase(string);
    }

    public static boolean isNotMixedCase(String string) {
        string = StringUtils.trimToEmpty(string);
        if (string.isEmpty()) {
            return true;
        }
        string = string.replaceAll("[^a-zA-Z]", "");
        if (string.isEmpty()) {
            return true;
        }
        return StringUtils.isAllLowerCase(string) || StringUtils.isAllUpperCase(string);
    }

    public static String getWordyString(String string) {
        return StringUtils.isBlank(string) ? StringUtils.EMPTY
            : isMixedCase(string) ? getHumplessCase(string, ' ')
            : string.toLowerCase().replace('_', ' ').replaceAll(" +", " ").trim();
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

    public static String escapeMeta(String string) {
        final char[] meta = {'\\', '^', '$', '.', '*', '+', '?', '|', '-', '&', '{', '}', '[', ']', '(', ')', '<', '>'};
        if (StringUtils.containsAny(string, meta)) {
            String s;
            for (char c : meta) {
                s = String.valueOf(c);
                if (string.contains(s)) {
                    string = string.replace(s, "\\" + s);
                }
            }
        }
        return string;
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
        String cadena = StringUtils.trimToNull(string);
        if (cadena == null) {
            return null;
        }
        Object objeto = null;
        if (StringUtils.isNumeric(cadena)) {
//          objeto = getObjeto(cadena, EnumTipoDatoParametro.ENTERO);
            objeto = getObjeto(cadena, Integer.class);
            if (objeto == null) {
//              objeto = getObjeto(cadena, EnumTipoDatoParametro.ENTERO_GRANDE);
                objeto = getObjeto(cadena, Long.class);
            }
        }
        if (objeto == null && cadena.startsWith(Bundle.getString("id_prefix.string"))) {
            String substr = cadena.substring(1);
            if (StringUtils.isNumeric(substr)) {
//              objeto = getObjeto(substr, EnumTipoDatoParametro.ENTERO_GRANDE);
                objeto = getObjeto(substr, Long.class);
            }
        }
        if (objeto == null) {
//          objeto = getObjeto(cadena, EnumTipoDatoParametro.NUMERICO);
            objeto = getObjeto(cadena, BigDecimal.class);
        }
        if (objeto == null) {
//          objeto = getObjeto(cadena, EnumTipoDatoParametro.FECHA_HORA);
            objeto = getObjeto(cadena, java.util.Date.class);
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
                return BitUtils.toBoolean(value);
            } else if (Byte.class.isAssignableFrom(clazz)) {
                return NumUtils.toByte(value);
            } else if (Short.class.isAssignableFrom(clazz)) {
                return NumUtils.toShort(value);
            } else if (Integer.class.isAssignableFrom(clazz)) {         // ENTERO
                return NumUtils.toInteger(value);
            } else if (Long.class.isAssignableFrom(clazz)) {            // ENTERO_GRANDE
                return NumUtils.toLong(value);
            } else if (Float.class.isAssignableFrom(clazz)) {
                return NumUtils.toFloat(value);
            } else if (Double.class.isAssignableFrom(clazz)) {
                return NumUtils.toDouble(value);
            } else if (BigInteger.class.isAssignableFrom(clazz)) {
                return NumUtils.toBigInteger(value);
            } else if (BigDecimal.class.isAssignableFrom(clazz)) {      // NUMERICO
                return NumUtils.toBigDecimal(value);
            } else if (java.util.Date.class.isAssignableFrom(clazz)) {  // FECHA_HORA
                return TimeUtils.parse(value);
            }
        } catch (RuntimeException e) {
            return null;
        }
        return null;
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
        int j = 1; // delimiter length
        int k = 2 * j;
        int l = s.length();
        int n = l - j;
        if (l >= k && s.charAt(0) == open && s.charAt(n) == close) {
            // <editor-fold defaultstate="collapsed" desc="section disabled because it fails if one of the delimiters is a literal within the string">
//          int opened = 0;
//          int closed = 0;
//          for (int x = 0; x < l; x++) {
//              if (s.charAt(x) == open) {
//                  opened++;
//              } else if (s.charAt(x) == close) {
//                  closed++;
//              }
//              if (opened > closed) {
//              } else {
//                  return opened == closed && x == n;
//              }
//          }
            // </editor-fold>
            return l == k || (s.substring(j, n).indexOf(open) < 0 && s.substring(j, n).indexOf(close) < 0);
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
            // <editor-fold defaultstate="collapsed" desc="section disabled because it fails if one of the delimiters is a literal within the string">
//          int opened = 0;
//          int closed = 0;
//          int x = 0;
//          int y, z;
//          for (; x < l;) {
//              y = s.indexOf(open, x);
//              z = s.indexOf(close, x);
//              if (y >= x && y < z) {
//                  opened++;
//                  x = y + open.length();
//              } else if (z >= x) {
//                  closed++;
//                  x = z + close.length();
//              }
//              if (opened > closed) {
//              } else {
//                  return opened == closed && x == l;
//              }
//          }
            // </editor-fold>
            return l == k || (!s.substring(i, n).contains(open) && !s.substring(i, n).contains(close));
        }
        return false;
    }

    public static String encloseSqlExpression(String expression) {
        return expression == null ? null
            : esInvocacionFuncionSql(expression) ? expression
            : enclose(expression, '(', ')');
    }

    public static String enclose(String argument) {
        return argument == null ? null : enclose(argument, '(', ')');
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

    /**
     * Removes all occurrences of a String within another String that are found after another String.
     *
     * @param text the String to search and remove in
     * @param search the String to search for
     * @param preceding the String used as lower boundary of the search; if it is null or empty, the search begins at the beginning of text
     * @return the text with any removements processed
     */
    public static String removeAfter(String text, String search, String preceding) {
        return removeBetween(text, search, preceding, null);
    }

    /**
     * Removes all occurrences of a String within another String that are found before another String.
     *
     * @param text the String to search and remove in
     * @param search the String to search for
     * @param following the String used as upper boundary of the search; if it is null or empty, the search ends at the end of text
     * @return the text with any removements processed
     */
    public static String removeBefore(String text, String search, String following) {
        return removeBetween(text, search, null, following);
    }

    /**
     * Removes all occurrences of a String within another String that are found between another two Strings.
     *
     * @param text the String to search and remove in
     * @param search the String to search for
     * @param preceding the String used as lower boundary of the search; if it is null or empty, the search begins at the beginning of text
     * @param following the String used as upper boundary of the search; if it is null or empty, the search ends at the end of text
     * @return the text with any removements processed
     */
    public static String removeBetween(String text, String search, String preceding, String following) {
        return replaceBetween(text, search, "", preceding, following);
    }

    /**
     * Replaces all occurrences of a String within another String that are found after another String.
     *
     * @param text the String to search and replace in
     * @param search the String to search for
     * @param replacement the String to replace it with
     * @param preceding the String used as lower boundary of the search; if it is null or empty, the search begins at the beginning of text
     * @return the text with any replacements processed
     */
    public static String replaceAfter(String text, String search, String replacement, String preceding) {
        return replaceBetween(text, search, replacement, preceding, null);
    }

    /**
     * Replaces all occurrences of a String within another String that are found before another String.
     *
     * @param text the String to search and replace in
     * @param search the String to search for
     * @param replacement the String to replace it with
     * @param following the String used as upper boundary of the search; if it is null or empty, the search ends at the end of text
     * @return the text with any replacements processed
     */
    public static String replaceBefore(String text, String search, String replacement, String following) {
        return replaceBetween(text, search, replacement, null, following);
    }

    /**
     * Replaces all occurrences of a String within another String that are found between another two Strings.
     *
     * @param text the String to search and replace in
     * @param search the String to search for
     * @param replacement the String to replace it with
     * @param preceding the String used as lower boundary of the search; if it is null or empty, the search begins at the beginning of text
     * @param following the String used as upper boundary of the search; if it is null or empty, the search ends at the end of text
     * @return the text with any replacements processed
     */
    public static String replaceBetween(String text, String search, String replacement, String preceding, String following) {
        final String turbofan = "\t\r\b\f\n";
        if (text != null && search != null && replacement != null && text.length() > 0 && search.length() > 0 && text.contains(search)) {
            boolean preceded = preceding != null && preceding.length() > 0;
            if (preceded && !text.contains(preceding)) {
                return text;
            }
            boolean followed = following != null && following.length() > 0;
            if (followed && !text.contains(following)) {
                return text;
            }
            if (preceded || followed) {
                String searchless = text.replace(search, turbofan);
                String beforePreceding = preceded ? StringUtils.substringBefore(searchless, preceding) : "";
                String afterPreceding = preceded ? StringUtils.substringAfter(searchless, preceding) : "";
                String beforeFollowing = followed ? StringUtils.substringBefore(preceded ? afterPreceding : searchless, following) : "";
                String afterFollowing = followed ? StringUtils.substringAfter(preceded ? afterPreceding : searchless, following) : "";
                String inBetween = preceded && followed ? StringUtils.substringBetween(searchless, preceding, following) : "";
                String substring = preceded && followed ? inBetween : preceded ? afterPreceding : beforeFollowing;
                if (StringUtils.contains(substring, turbofan)) {
                    String string = substring.replace(turbofan, replacement);
                    String concat = beforePreceding + (preceded ? preceding : "") + string + (followed ? following : "") + afterFollowing;
                    String result = concat.replace(turbofan, search);
                    return result;
                }
            } else {
                return text.replace(search, replacement);
            }
        }
        return text;
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

    public static String replaceOnceRepeatedly(String text, String searchString, Object... replacements) {
        if (StringUtils.isBlank(text) || StringUtils.isBlank(searchString) || replacements == null || replacements.length == 0) {
            return text;
        }
        String replacement;
        for (Object obj : replacements) {
            replacement = StringUtils.trimToEmpty(getString(obj));
            text = StringUtils.replaceOnce(text, searchString, replacement);
        }
        return text;
    }

}
