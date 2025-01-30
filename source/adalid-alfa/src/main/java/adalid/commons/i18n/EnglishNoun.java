package adalid.commons.i18n;

import java.util.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;

/**
 * An experimental class that provides static methods for testing and computing various properties of strings that are presumed to be English nouns.
 *
 * @author James A. Mason
 * @version 1.01 2002 Feb
 *
 * capitalize and pluralOfMultiwordExpression added by Jorge Campins (don't blame Mr. Mason for the shortcomings of these methods)
 */
public class EnglishNoun {

    private static final String[] articles = {"a", "an", "the"};

    private static final String[] conjunctions = {"after", "although", "and", "because", "before", "both", "but", "either", "for",
        "if", "neither", "nor", "once", "or", "since", "so", "though", "unless", "until", "when", "whereas", "while", "yet"};

    private static final String[] prepositions = {"about", "above", "across", "after", "along", "among", "around", "at",
        "before", "beneath", "beside", "between", "beyond", "by", "down", "during", "except", "from", "in", "inside", "into",
        "like", "near", "of", "off", "on", "outside", "over", "through", "to", "toward", "under", "up", "upon", "with", "within", "without"};

    private static final List<String> lowerCaseWords = new ArrayList<>();

    private static final HashMap<String, Object> irregularSingulars = new HashMap<>(100);

    private static final HashMap<String, Object> irregularPlurals = new HashMap<>(100);

    static {
        lowerCaseWords.addAll(Arrays.asList(articles));
        lowerCaseWords.addAll(Arrays.asList(conjunctions));
        lowerCaseWords.addAll(Arrays.asList(prepositions));
        lowerCaseWords.addAll(Arrays.asList(SpanishNoun.articles));
        lowerCaseWords.addAll(Arrays.asList(SpanishNoun.conjunctions));
        lowerCaseWords.addAll(Arrays.asList(SpanishNoun.prepositions));
        irregularSingulars.put("ache", "aches");
        irregularSingulars.put("alumna", "alumnae");
        irregularSingulars.put("alumnus", "alumni");
        irregularSingulars.put("axis", "axes");
        irregularSingulars.put("bison", "bison");
        ArrayList<Object> busses = new ArrayList<>(2);
        busses.add("buses");
        busses.add("busses");
        irregularSingulars.put("bus", busses);
        irregularSingulars.put("calf", "calves");
        irregularSingulars.put("caribou", "caribou");
        irregularSingulars.put("child", "children");
        irregularSingulars.put("datum", "data");
        irregularSingulars.put("deer", "deer");
        ArrayList<Object> dice = new ArrayList<>(2);
        dice.add("dies");
        dice.add("dice");
        irregularSingulars.put("die", dice);
        irregularSingulars.put("elf", "elves");
        irregularSingulars.put("elk", "elk");
        irregularSingulars.put("fish", "fish");
        irregularSingulars.put("foot", "feet");
        irregularSingulars.put("gentleman", "gentlemen");
        irregularSingulars.put("gentlewoman", "gentlewomen");
        irregularSingulars.put("go", "goes");
        irregularSingulars.put("goose", "geese");
        irregularSingulars.put("grouse", "grouse");
        irregularSingulars.put("half", "halves");
        ArrayList<Object> hoof = new ArrayList<>(2);
        hoof.add("hooves");
        hoof.add("hoofs");
        irregularSingulars.put("hoof", hoof);
        irregularSingulars.put("knife", "knives");
        irregularSingulars.put("leaf", "leaves");
        irregularSingulars.put("life", "lives");
        irregularSingulars.put("louse", "lice");
        irregularSingulars.put("man", "men");
        irregularSingulars.put("money", "monies");
        irregularSingulars.put("moose", "moose");
        irregularSingulars.put("mouse", "mice");
        ArrayList<Object> octopus = new ArrayList<>(3);
        octopus.add("octopodes");
        octopus.add("octopi");
        octopus.add("octopuses");
        irregularSingulars.put("octopus", octopus);
        irregularSingulars.put("ox", "oxen");
        irregularSingulars.put("plus", "pluses");
        irregularSingulars.put("quail", "quail");
        irregularSingulars.put("reindeer", "reindeer");
        ArrayList<Object> scarf = new ArrayList<>(2);
        scarf.add("scarves");
        scarf.add("scarfs");
        irregularSingulars.put("scarf", scarf);
        irregularSingulars.put("self", "selves");
        irregularSingulars.put("sheaf", "sheaves");
        irregularSingulars.put("sheep", "sheep");
        irregularSingulars.put("shelf", "shelves");
        irregularSingulars.put("squid", "squid");
        irregularSingulars.put("thief", "thieves");
        irregularSingulars.put("tooth", "teeth");
        irregularSingulars.put("wharf", "wharves");
        irregularSingulars.put("wife", "wives");
        irregularSingulars.put("wolf", "wolves");
        irregularSingulars.put("woman", "women");
        irregularPlurals.put("aches", "ache");
        irregularPlurals.put("alumnae", "alumna");
        irregularPlurals.put("alumni", "alumnus");
        ArrayList<Object> axes = new ArrayList<>(2);
        axes.add("axe");
        axes.add("axis");
        irregularPlurals.put("axes", axes);
        irregularPlurals.put("bison", "bison");
        irregularPlurals.put("buses", "bus");
        irregularPlurals.put("busses", "bus");
        irregularPlurals.put("brethren", "brother");
        irregularPlurals.put("caches", "cache");
        irregularPlurals.put("calves", "calf");
        irregularPlurals.put("cargoes", "cargo");
        irregularPlurals.put("caribou", "caribou");
        irregularPlurals.put("children", "child");
        irregularPlurals.put("data", "datum");
        irregularPlurals.put("deer", "deer");
        irregularPlurals.put("dice", "die");
        irregularPlurals.put("dies", "die");
        irregularPlurals.put("dominoes", "domino");
        irregularPlurals.put("echoes", "echo");
        irregularPlurals.put("elves", "elf");
        irregularPlurals.put("elk", "elk");
        irregularPlurals.put("embargoes", "embargo");
        irregularPlurals.put("fish", "fish");
        irregularPlurals.put("feet", "foot");
        irregularPlurals.put("gentlemen", "gentleman");
        irregularPlurals.put("gentlewomen", "gentlewoman");
        irregularPlurals.put("geese", "goose");
        irregularPlurals.put("goes", "go");
        irregularPlurals.put("grottoes", "grotto");
        irregularPlurals.put("grouse", "grouse");
        irregularPlurals.put("halves", "half");
        irregularPlurals.put("hooves", "hoof");
        irregularPlurals.put("knives", "knife");
        irregularPlurals.put("leaves", "leaf");
        irregularPlurals.put("lives", "life");
        irregularPlurals.put("lice", "louse");
        irregularPlurals.put("men", "man");
        irregularPlurals.put("monies", "money");
        irregularPlurals.put("moose", "moose");
        irregularPlurals.put("mottoes", "motto");
        irregularPlurals.put("mice", "mouse");
        irregularPlurals.put("octopi", "octopus");
        irregularPlurals.put("octopodes", "octopus");
        irregularPlurals.put("octopuses", "octopus");
        irregularPlurals.put("oxen", "ox");
        irregularPlurals.put("pies", "pie");
        irregularPlurals.put("pluses", "plus");
        irregularPlurals.put("posses", "posse");
        irregularPlurals.put("potatoes", "potato");
        irregularPlurals.put("quail", "quail");
        irregularPlurals.put("reindeer", "reindeer");
        irregularPlurals.put("scarves", "scarf");
        irregularPlurals.put("sheaves", "sheaf");
        irregularPlurals.put("sheep", "sheep");
        irregularPlurals.put("shelves", "shelf");
        irregularPlurals.put("squid", "squid");
        irregularPlurals.put("teeth", "tooth");
        irregularPlurals.put("thieves", "thief");
        irregularPlurals.put("ties", "tie");
        irregularPlurals.put("tomatoes", "tomato");
        irregularPlurals.put("wharves", "wharf");
        irregularPlurals.put("wives", "wife");
        irregularPlurals.put("wolves", "wolf");
        irregularPlurals.put("women", "woman");
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
     * Tests whether the given (presumed) English noun is plural. A word like "sheep" that can be either singular or plural yields true.
     *
     * @param word a word
     * @return true if word is plural; false otherwise
     */
    public static boolean isPlural(String word) {
        if (StringUtils.isBlank(word)) {
            return false;
        }
        word = word.toLowerCase();
        if (irregularPlurals.containsKey(word)) {
            return true;
        }
        if (word.length() <= 1) {
            return false;
        }
        /*
         * If it is not an irregular plural, it must end in -s,
         * but it must not be an irregular singular (like "bus")
         * nor end in -ss (like "boss"). *
         */
        if (word.charAt(word.length() - 1) != 's') {
            return false;
        }
        if (irregularSingulars.containsKey(word)) {
            return false;
        }
        return !(word.length() >= 2 && word.charAt(word.length() - 2) == 's');
    }

    /**
     * Tests whether the given (presumed) English noun is singular. A word like "sheep" that can be either singular or plural yields true.
     *
     * @param word a word
     * @return true if word is singular; false otherwise
     */
    public static boolean isSingular(String word) {
        if (StringUtils.isBlank(word)) {
            return false;
        }
        word = word.toLowerCase();
        if (irregularSingulars.containsKey(word)) {
            return true;
        }
        /*
         * If it is not an irregular singular, it must not be an irregular plural (like "children"),
         * and it must not end in -s unless it ends in -ss (like "boss")). *
         */
        if (irregularPlurals.containsKey(word)) {
            return false;
        }
        if (word.length() <= 0) {
            return false;
        }
        if (word.charAt(word.length() - 1) != 's') {
            return true;
        }
        // word is not irregular, and ends in -s but not -ss
        // word ends in -ss
        return word.length() >= 2 && word.charAt(word.length() - 2) == 's';
    }

    /**
     * Returns the plural of a given (presumed) English word. The given word may be singular or (already) plural.
     *
     * @param word a word
     * @return the plural of word
     */
    public static String pluralOf(String word) {
        if (StringUtils.isBlank(word)) {
            return word;
        }
        word = word.toLowerCase();
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
        int length = word.length();
        if (length <= 1) {
            return word + "'s";
        }
        char lastLetter = word.charAt(length - 1);
        char secondLast = word.charAt(length - 2);
        if ("sxzo".indexOf(lastLetter) >= 0 || (lastLetter == 'h' && (secondLast == 's' || secondLast == 'c'))) {
            return word + "es";
        }
        if (lastLetter == 'y') {
            if ("aeiou".indexOf(secondLast) >= 0) {
                return word + "s";
            } else {
                return word.substring(0, length - 1) + "ies";
            }
        }
        return word + "s";
    }

    /**
     * Returns the singular of a given (presumed) English word. The given word may be plural or (already) singular.
     *
     * @param word a word
     * @return the singular of word
     */
    public static String singularOf(String word) {
        if (StringUtils.isBlank(word)) {
            return word;
        }
        word = word.toLowerCase();
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
        int length = word.length();
        if (length <= 1) {
            return word;
        }
        char lastLetter = word.charAt(length - 1);
        if (lastLetter != 's') {
            return word;  // no final -s
        }
        char secondLast = word.charAt(length - 2);
        if (secondLast == '\'') {
            return word.substring(0, length - 2); // remove -'s
        }
        if (word.equalsIgnoreCase("gas")) {
            return word;
        }
        if (secondLast != 'e' || length <= 3) {
            return word.substring(0, length - 1); // remove final -s
        }
        // Word ends in -es and has length >= 4:
        char thirdLast = word.charAt(length - 3);
        if (thirdLast == 'i') {
            return word.substring(0, length - 3) + "y"; // -ies => -y
        }
        if (thirdLast == 'x') {
            return word.substring(0, length - 2); // -xes => -x
        }
        if (length <= 4) {
            return word.substring(0, length - 1); // e.g. uses => use
        }
        char fourthLast = word.charAt(length - 4);
        if (thirdLast == 'h' && (fourthLast == 'c' || fourthLast == 's')) {
            return word.substring(0, length - 2); // -ches or -shes => -ch or -sh
        }
        if (thirdLast == 's' && fourthLast == 's') {
            return word.substring(0, length - 2); // -sses => -ss
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
                int i = tokens.length - 1;
                tokens[i] = pluralOf(tokens[i]);
                return StringUtils.join(tokens, ' ');
            }
        }
        return mwe;
    }

}
