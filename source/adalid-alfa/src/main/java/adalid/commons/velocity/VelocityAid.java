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
package adalid.commons.velocity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class VelocityAid {

    private static final Logger logger = Logger.getLogger(VelocityAid.class);

    // <editor-fold defaultstate="collapsed" desc="boolean constants">
    private static final Boolean TRUE = Boolean.TRUE;

    private static final Boolean FALSE = Boolean.FALSE;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="character constants">
    private static final char bs = '\b';

    private static final char ht = '\t';

    private static final char lf = '\n';

    private static final char vt = '\u000B';

    private static final char ff = '\f';

    private static final char cr = '\r';

    private static final char lcb = '{';

    private static final char rcb = '}';

    private static final char lrb = '(';

    private static final char rrb = ')';

    private static final char lsb = '[';

    private static final char rsb = ']';

    private static final char bsl = '\\';

    private static final char spc = ' ';

    private static final char sqm = '\'';

    private static final char dqm = '\"';

    private static final char dot = '.';

    private static final char com = ',';

    private static final char und = '_';

    private static final char ast = '*';
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="string constants">
    private static final String EOL = System.lineSeparator(); // System.getProperty("line.separator");

    private static final String TAB = StringUtils.repeat(" ", 4);

    private static final String EMPTY = "";
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="character-string constants">
    private static final String BS = EMPTY + bs;

    private static final String HT = EMPTY + ht;

    private static final String LF = EMPTY + lf;

    private static final String VT = EMPTY + vt;

    private static final String FF = EMPTY + ff;

    private static final String CR = EMPTY + cr;

    private static final String LCB = EMPTY + lcb;

    private static final String RCB = EMPTY + rcb;

    private static final String LRB = EMPTY + lrb;

    private static final String RRB = EMPTY + rrb;

    private static final String LSB = EMPTY + lsb;

    private static final String RSB = EMPTY + rsb;

    private static final String BSL = EMPTY + bsl;

    private static final String SPC = EMPTY + spc;

    private static final String SQM = EMPTY + sqm;

    private static final String DQM = EMPTY + dqm;

    private static final String DOT = EMPTY + dot;

    private static final String COM = EMPTY + com;

    private static final String UND = EMPTY + und;

    private static final String AST = EMPTY + ast;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="boolean constants getters">
    public static Boolean TRUE() {
        return TRUE;
    }

    public static Boolean FALSE() {
        return FALSE;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="character constants getters">
    public static char bs() {
        return bs;
    }

    public static char ht() {
        return ht;
    }

    public static char lf() {
        return lf;
    }

    public static char vt() {
        return vt;
    }

    public static char ff() {
        return ff;
    }

    public static char cr() {
        return cr;
    }

    public static char lcb() {
        return lcb;
    }

    public static char rcb() {
        return rcb;
    }

    public static char lrb() {
        return lrb;
    }

    public static char rrb() {
        return rrb;
    }

    public static char lsb() {
        return lsb;
    }

    public static char rsb() {
        return rsb;
    }

    public static char bsl() {
        return bsl;
    }

    public static char spc() {
        return spc;
    }

    public static char sqm() {
        return sqm;
    }

    public static char dqm() {
        return dqm;
    }

    public static char dot() {
        return dot;
    }

    public static char com() {
        return com;
    }

    public static char und() {
        return und;
    }

    public static char ast() {
        return ast;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="string constants getters">
    public static String EOL() {
        return EOL;
    }

    public static String TAB() {
        return TAB;
    }

    public static String EMPTY() {
        return EMPTY;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="character-string constants getters">
    public static String BS() {
        return BS;
    }

    public static String HT() {
        return HT;
    }

    public static String LF() {
        return LF;
    }

    public static String VT() {
        return VT;
    }

    public static String FF() {
        return FF;
    }

    public static String CR() {
        return CR;
    }

    public static String LCB() {
        return LCB;
    }

    public static String RCB() {
        return RCB;
    }

    public static String LRB() {
        return LRB;
    }

    public static String RRB() {
        return RRB;
    }

    public static String LSB() {
        return LSB;
    }

    public static String RSB() {
        return RSB;
    }

    public static String BSL() {
        return BSL;
    }

    public static String SPC() {
        return SPC;
    }

    public static String SQM() {
        return SQM;
    }

    public static String DQM() {
        return DQM;
    }

    public static String DOT() {
        return DOT;
    }

    public static String COM() {
        return COM;
    }

    public static String UND() {
        return UND;
    }

    public static String AST() {
        return AST;
    }
    // </editor-fold>

    public static String echo(String string) {
        return "";
    }

    public static String log(String level, String message) {
        return log(level, message, false);
    }

    public static String log(String level, String message, boolean echo) {
        logger.log(Level.toLevel(level, Level.DEBUG), message);
        return echo ? message : "";
    }

    public static <T extends Comparable<? super T>> Collection<T> sortCollection(Collection<T> collection) {
        if (collection instanceof List && !collection.isEmpty()) {
            List<T> list = (List<T>) collection;
            Collections.sort(list);
        }
        return collection;
    }

    public static Enum valueOf(Class<?> clazz, String name) {
        if (clazz == null || name == null) {
            return null;
        }
        Object object = values(clazz);
        if (object instanceof Enum[]) {
            Enum[] values = (Enum[]) object;
            for (Enum e : values) {
                if (name.equals(e.name())) {
                    return e;
                }
            }
        }
        return null;
    }

    public static Object values(Class<?> clazz) {
        if (clazz == null) {
            return null;
        }
        try {
            Method method = clazz.getMethod("values");
            Object object = method.invoke(null);
            return object;
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            return null;
        }
    }

    /**
     * @return a new instance of Stack
     */
    public static Stack getNewStack() {
        return new Stack();
    }

    /**
     * @return a new instance of ArrayList
     */
    public static ArrayList getNewArrayList() {
        return new ArrayList();
    }

    /**
     * @return a new instance of LinkedHashMap
     */
    public static LinkedHashMap getNewLinkedHashMap() {
        return new LinkedHashMap();
    }

    /**
     * @return a new instance of TreeSet
     */
    public static LinkedHashSet getNewLinkedHashSet() {
        return new LinkedHashSet();
    }

    /**
     * @return a new instance of TreeMap
     */
    public static TreeMap getNewTreeMap() {
        return new TreeMap();
    }

    /**
     * @return a new instance of TreeSet
     */
    public static TreeSet getNewTreeSet() {
        return new TreeSet();
    }

    public static Object[] getNewArray(Map map) {
        return map == null ? null : getNewArray(map.values());
    }

    public static Object[] getNewArray(Collection col) {
        return col == null ? null : col.toArray();
    }

    public static String[] toStringArray(Object... objects) {
        return Arrays.copyOf(objects, objects.length, String[].class);
    }

    public static String toArrayString(Object... objects) {
        return Arrays.toString(objects);
    }

    /**
     * @param objects objects to test
     * @return the first non-null parameter
     */
    public static Object coalesce(Object... objects) {
        if (objects != null && objects.length > 0) {
            for (Object object : objects) {
                if (object != null) {
                    return object;
                }
            }
        }
        return null;
    }

    /**
     * @param objects objects to test
     * @return the first non-null/non-blank parameter
     */
    public static Object nvl(Object... objects) {
        if (objects != null && objects.length > 0) {
            for (Object object : objects) {
                if (object instanceof String) {
                    if (StringUtils.isNotBlank((String) object)) {
                        return object;
                    }
                } else if (object != null) {
                    return object;
                }
            }
        }
        return null;
    }

    public static String[] array(String... string) {
        return string;
    }

    public static int[] intArray(int... i) {
        return i;
    }

    public static int[] intArrayFor(int start, int stop, int step) {
        int s = start;
        int n = 1 + (stop - start) / step;
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = s;
            s += step;
        }
        return array;
    }

    public static String intArrayForString(int start, int stop, int step, String separator) {
        int[] array1 = intArrayFor(start, stop, step);
        Integer[] array2 = ArrayUtils.toObject(array1);
        return StringUtils.join(array2, separator);
    }

    private static final String JAVADOC_PARAGRAPH_TAG = "<p>";

    public static List<String> javadocLines(String string) {
        return javadocLines(string, 72);
    }

    public static List<String> javadocLines(String string, int max) {
        return split(string, max, JAVADOC_PARAGRAPH_TAG, true);
    }

    public static List<String> split(String string) {
        return split(string, 80);
    }

    public static List<String> split(String string, int max) {
        return split(string, max, null);
    }

    public static List<String> split(String string, int max, String separator) {
        return split(string, max, separator, false);
    }

    public static List<String> split(String string, int max, String separator, boolean separatorLine) {
        return split(string, max, separator, separatorLine, null);
    }

    /**
     * split a text into a list of lines.
     *
     * @param string the text to split.
     * @param max the maximum line length.
     * @param separator the paragraph separator string.
     * @param separatorLine if true, the paragraph separator is added as a line between paragraphs.
     * @param prefix the new line prefix
     * @return the list of split.
     */
    public static List<String> split(String string, int max, String separator, boolean separatorLine, String prefix) {
        List<String> strings = new ArrayList<>();
        String str = StringUtils.trimToNull(string);
        if (str != null) {
            int maxLineLength, lineLength, wordLength, lastParagraph;
            String line, paragraph;
            String[] words, paragraphs;
            String sep = StringUtils.trimToNull(separator);
            String nlp = prefix == null ? "" : prefix;
            boolean sl = sep != null && separatorLine;
            paragraphs = sep == null ? new String[]{str} : StringUtils.splitByWholeSeparator(str, separator);
            lastParagraph = paragraphs.length - 1;
            for (int i = 0; i < paragraphs.length; i++) {
//              paragraph = paragraphs[i].trim();
                paragraph = paragraphs[i].replaceAll(" +$", ""); // remove spaces at the end of the paragraph
                if (max > 0) {
                    maxLineLength = max;
                    if (paragraph.length() > maxLineLength) {
                        line = "";
                        words = StringUtils.split(paragraph);
                        for (String word : words) {
                            lineLength = line.length();
                            wordLength = word.length() + 1;
                            if (lineLength == 0) {
                                line = word;
                            } else if (lineLength + wordLength > maxLineLength) {
                                strings.add(line);
                                maxLineLength = max - nlp.length();
                                line = nlp + word;
                            } else {
                                line += " " + word;
                            }
                        }
                        strings.add(line);
                    } else {
                        strings.add(paragraph);
                    }
                } else {
                    strings.add(paragraph);
                }
                if (sl && i < lastParagraph) {
                    strings.add(sep);
                }
            }
        }
        return strings;
    }

    public static String fixPath(String path) {
        String sep = System.getProperty("file.separator");
        return StringUtils.replace(path, "/", sep);
    }

    public static Set<String> sortedPropertyNames(Properties properties) {
        /*
        String[] names = new String[properties.stringPropertyNames().size()];
        properties.stringPropertyNames().toArray(names);
        Arrays.sort(names);
        /**/
        Set<String> names = new TreeSet<>(properties.stringPropertyNames());
        return names;
    }

}
