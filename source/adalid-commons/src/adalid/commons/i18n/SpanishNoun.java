/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.commons.i18n;

import java.util.*;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public class SpanishNoun {

    private static final String[] prepositions = {"a", "ante", "bajo", "con", "de", "desde", "durante", "en", "entre", "excepto", "hacia", "hasta", "mediante", "para", "por", "salvo", "según", "sin", "sobre", "tras"};

    private static final HashMap<String, Object> irregularSingulars = new HashMap<>(100);

    private static final HashMap<String, Object> irregularPlurals = new HashMap<>(100);

    static {
        irregularSingulars.put("lunes", "lunes");
        irregularSingulars.put("martes", "martes");
        irregularSingulars.put("miércoles", "miércoles");
        irregularSingulars.put("jueves", "jueves");
        irregularSingulars.put("viernes", "viernes");
        Object value;
        for (String key : irregularSingulars.keySet()) {
            value = irregularSingulars.get(key);
            if (value instanceof String) {
                irregularPlurals.put(value.toString(), key);
            }
        }
    }

    /**
     * Tests whether the given (presumed) English noun is plural. A word like "sheep" that can be either singular or plural yields true.
     */
    public static boolean isPlural(String word) {
        word = word.trim().toLowerCase();
        int length = word.length();
        if (length <= 1) {
            return false;
        }
        if (irregularPlurals.containsKey(word)) {
            return true;
        }
        if (irregularSingulars.containsKey(word)) {
            return false;
        }
        if (word.charAt(length - 1) != 's') {
            return false;
        }
        // TODO: word is not irregular, and ends in -s
        return false;
    }

    /**
     * Tests whether the given (presumed) English noun is singular. A word like "sheep" that can be either singular or plural yields true.
     */
    public static boolean isSingular(String word) {
        word = word.trim().toLowerCase();
        int length = word.length();
        if (length <= 1) {
            return true;
        }
        if (irregularSingulars.containsKey(word)) {
            return true;
        }
        if (irregularPlurals.containsKey(word)) {
            return false;
        }
        if (word.charAt(length - 1) != 's') {
            return true;
        }
        // TODO: word is not irregular, and ends in -s
        return false;
    }

    /**
     * Returns the plural of a given (presumed) English word. The given word may be singular or (already) plural.
     */
    public static String pluralOf(String word) {
        word = word.trim().toLowerCase();
        int length = word.length();
        if (length <= 1) {
            return word;
        }
        if (isPlural(word)) {
            return word;
        }
        Object singularLookup = irregularSingulars.get(word);
        if (singularLookup != null) {
            if (singularLookup instanceof ArrayList) {
                return (String) (((ArrayList) singularLookup).get(0));
            } else {
                return (String) singularLookup;
            }
        }
        char lastLetter = word.charAt(length - 1);
        if ("aeiou".indexOf(lastLetter) >= 0) {
            return word + "s";
        }
        if (lastLetter == 'z') {
            return word.substring(0, length - 1) + "ces";
        }
        return word + "es";
    }

    /**
     * Returns the singular of a given (presumed) English word. The given word may be plural or (already) singular.
     */
    public static String singularOf(String word) {
        word = word.trim().toLowerCase();
        int length = word.length();
        if (length <= 1) {
            return word;
        }
        if (isSingular(word)) {
            return word;
        }
        Object pluralLookup = irregularPlurals.get(word);
        if (pluralLookup != null) {
            if (pluralLookup instanceof ArrayList) {
                return (String) (((ArrayList) pluralLookup).get(0));
            } else {
                return (String) pluralLookup;
            }
        }
        char lastLetter = word.charAt(length - 1);
        if (lastLetter != 's') {
            return word;  // no final -s
        }
        char secondLast = word.charAt(length - 2);
        if (secondLast != 'e' || length <= 3) {
            return word.substring(0, length - 1); // remove final -s
        }
        // Word ends in -es and has length >= 4:
        char thirdLast = word.charAt(length - 3);
        if (thirdLast == 'c') {
            return word.substring(0, length - 3) + "z"; // -ces => -z
        }
        return word.substring(0, length - 1);  // keep the final e.
    }

    public static String pluralOfMultiwordExpression(String mwe) {
        if (StringUtils.isNotBlank(mwe)) {
            String[] tokens = StringUtils.split(mwe);
            if (tokens.length == 1) {
                return pluralOf(tokens[0]);
            }
            if (tokens.length > 1) {
                for (int i = 0; i < tokens.length; i++) {
                    if (ArrayUtils.contains(prepositions, tokens[i].toLowerCase())) {
                        break;
                    }
                    tokens[i] = pluralOf(tokens[i]);
                }
                return StringUtils.join(tokens, ' ');
            }
        }
        return mwe;
    }

}
