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
package adalid.core.programmers;

import adalid.commons.interfaces.*;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public abstract class AbstractProgrammer implements Programmer {

    // <editor-fold defaultstate="collapsed" desc="special character constants">
    /**
     * BackSpace
     */
    protected static final char BS = '\b';

    /**
     * Horizontal Tab
     */
    protected static final char HT = '\t';

    /**
     * Line Feed
     */
    protected static final char LF = '\n';

    /**
     * Vertical Tab
     */
    protected static final char VT = '\u000B';

    /**
     * Form Feed
     */
    protected static final char FF = '\f';

    /**
     * Carriage Return
     */
    protected static final char CR = '\r';

    /**
     * Left Curly Bracket
     */
    protected static final char LCB = '{';

    /**
     * Right Curly Bracket
     */
    protected static final char RCB = '}';

    /**
     * Left Round Bracket
     */
    protected static final char LRB = '(';

    /**
     * Right Round Bracket
     */
    protected static final char RRB = ')';

    /**
     * Left Square Bracket
     */
    protected static final char LSB = '[';

    /**
     * Right Square Bracket
     */
    protected static final char RSB = ']';

    /**
     * BackSLash
     */
    protected static final char BSL = '\\';

    /**
     * SPaCe
     */
    protected static final char SPC = ' ';

    /**
     * Single Quotation Mark
     */
    protected static final char SQM = '\'';

    /**
     * Double Quotation Mark
     */
    protected static final char DQM = '\"';

    /**
     * DOT
     */
    protected static final char DOT = '.';

    /**
     * COMma
     */
    protected static final char COM = ',';

    /**
     * COLon
     */
    protected static final char COL = ':';

    /**
     * SEMicolon
     */
    protected static final char SEM = ';';

    /**
     * UNDerscore
     */
    protected static final char UND = '_';

    /**
     * ASTerisk
     */
    protected static final char AST = '*';
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="special character string constants">
    /**
     * End Of Line
     */
    protected static final String EOL$ = System.lineSeparator(); // System.getProperty("line.separator");

    /**
     * TAB characters
     */
    protected static final String TAB$ = StringUtils.repeat(" ", 4);

    /**
     * Left Curly Bracket
     */
    protected static final String LCB$ = LCB + "";

    /**
     * Right Curly Bracket
     */
    protected static final String RCB$ = RCB + "";

    /**
     * Left Round Bracket
     */
    protected static final String LRB$ = LRB + "";

    /**
     * Right Round Bracket
     */
    protected static final String RRB$ = RRB + "";

    /**
     * Left Square Bracket
     */
    protected static final String LSB$ = LSB + "";

    /**
     * Right Square Bracket
     */
    protected static final String RSB$ = RSB + "";

    /**
     * BackSLash
     */
    protected static final String BSL$ = BSL + "";

    /**
     * SPaCe
     */
    protected static final String SPC$ = SPC + "";

    /**
     * Single Quotation Mark
     */
    protected static final String SQM$ = SQM + "";

    /**
     * Double Quotation Mark
     */
    protected static final String DQM$ = DQM + "";

    /**
     * DOT
     */
    protected static final String DOT$ = DOT + "";

    /**
     * COMma
     */
    protected static final String COM$ = COM + "";

    /**
     * COLon
     */
    protected static final String COL$ = COL + "";

    /**
     * SEMicolon
     */
    protected static final String SEM$ = SEM + "";

    /**
     * UNDerscore
     */
    protected static final String UND$ = UND + "";

    /**
     * ASTerisk
     */
    protected static final String AST$ = AST + "";
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="MessageFormat argument string constants">
    protected static final String ARG0 = "{0}";

    protected static final String ARG1 = "{1}";

    protected static final String ARG2 = "{2}";

    protected static final String ARG3 = "{3}";

    protected static final String ARG4 = "{4}";

    protected static final String ARG5 = "{5}";

    protected static final String ARG6 = "{6}";

    protected static final String ARG7 = "{7}";

    protected static final String ARG8 = "{8}";

    protected static final String ARG9 = "{9}";
    // </editor-fold>

    public static String format(String pattern, Object... arguments) {
        if (StringUtils.isBlank(pattern)) {
            return pattern;
        }
        String str = pattern;
        if (arguments != null && arguments.length > 0) {
            int i = 0;
            String key, val;
            for (Object argument : arguments) {
                key = "{" + i++ + "}";
                val = argument == null ? "" : argument.toString();
                str = StringUtils.replace(str, key, val);
            }
        }
        str = str.replaceAll("\\{[\\d]*\\}", "");
        return str;
    }

}
