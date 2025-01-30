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
package adalid.commons.i18n;

import java.util.*;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;

/**
 * @author Jorge Campins
 */
public class SpanishNoun {

    static final String[] articles = {"al", "del", "el", "la", "las", "lo", "los", "un", "una", "unas", "uno", "unos"};

    static final String[] conjunctions = {"aunque", "como", "conque", "cuando", "donde", "e", "empero", "entonces", "ergo", "incluso",
        "luego", "mas", "mientras", "ni", "o", "ora", "pero", "porque", "pues", "que", "sea", "si", "sino", "siquiera", "u", "y", "ya"};

    static final String[] prepositions = {"a", "ante", "bajo", "con", "contra", "de", "del", "desde", "durante", "en", "entre", "excepto",
        "hacia", "hasta", "mediante", "para", "por", "salvo", "según", "sin", "so", "sobre", "tras", "versus", "via"};

    private static final List<String> lowerCaseWords = new ArrayList<>();

    private static final HashMap<String, Object> irregularSingulars = new HashMap<>(100);

    private static final HashMap<String, Object> irregularPlurals = new HashMap<>(100);

    static {
        lowerCaseWords.addAll(Arrays.asList(articles));
        lowerCaseWords.addAll(Arrays.asList(conjunctions));
        lowerCaseWords.addAll(Arrays.asList(prepositions));
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

    public static Set<String> getArticles() {
        return Set.of(articles);
    }

    public static Set<String> getConjunctions() {
        return Set.of(conjunctions);
    }

    public static Set<String> getPrepositions() {
        return Set.of(prepositions);
    }

    /**
     * Returns the nicely capitalized form of a noun.
     *
     * @param noun a noun
     * @return capitalized noun
     */
    public static String capitalize(String noun) {
        if (StringUtils.isBlank(noun)) {
            return noun;
        }
        String word;
        char[] separators = new char[]{'-', '/'};
        String[] split = StringUtils.split(noun);
        for (int i = 0; i < split.length; i++) {
            word = split[i].toLowerCase();
            split[i] = lowerCaseWords_contains(word) ? word : WordUtils.capitalize(word, separators);
        }
        return StringUtils.join(split, ' ');
    }

    private static boolean lowerCaseWords_contains(String word) {
        if (!lowerCaseWords.contains(word)) {
            String[] split = StringUtils.split(word, '/');
            for (String term : split) {
                if (!lowerCaseWords.contains(term)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Tests whether the given (presumed) Spanish noun is plural.
     *
     * @param word a word
     * @return true if word is plural; false otherwise
     */
    public static boolean isPlural(String word) {
        if (StringUtils.isBlank(word)) {
            return false;
        }
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
        // TODO: include plural words not ending in -s and exclude singular words ending in -s
        return word.charAt(length - 1) == 's';
    }

    /**
     * Tests whether the given (presumed) Spanish noun is singular.
     *
     * @param word a word
     * @return true if word is singular; false otherwise
     */
    public static boolean isSingular(String word) {
        if (StringUtils.isBlank(word)) {
            return false;
        }
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
        // TODO: exclude plural words not ending in -s and include singular words ending in -s
        return word.charAt(length - 1) != 's';
    }

    /**
     * Returns the plural of a given (presumed) Spanish word. The given word may be singular or (already) plural.
     *
     * @param word a word
     * @return the plural of word
     */
    public static String pluralOf(String word) {
        if (StringUtils.isBlank(word)) {
            return word;
        }
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
            if (singularLookup instanceof ArrayList arrayList) {
                return (String) (arrayList.get(0));
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
     * Returns the singular of a given (presumed) Spanish word. The given word may be plural or (already) singular.
     *
     * @param word a word
     * @return the singular of word
     */
    public static String singularOf(String word) {
        if (StringUtils.isBlank(word)) {
            return word;
        }
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
            if (pluralLookup instanceof ArrayList arrayList) {
                return (String) (arrayList.get(0));
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
