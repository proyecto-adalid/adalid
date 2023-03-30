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

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public class MarkupUtils {

    // <editor-fold defaultstate="collapsed" desc="Creole&HTML pseudo-tags">
    public static final String AX = "<::"; // TAG HYPERLINK

    public static final String AY = ">:>"; // MID HYPERLINK

    public static final String AZ = "::>"; // END HYPERLINK

    public static final String B1 = "<**"; // TAG BOLD TEXT

    public static final String B2 = "**>"; // END BOLD TEXT

    public static final String CX = "<;;"; // TAG CSS CLASS

    public static final String CY = ">;>"; // MID CSS CLASS

    public static final String CZ = ";;>"; // END CSS CLASS

    public static final String G1 = "<++"; // TAG GRAPHIC IMAGE

    public static final String G2 = "++>"; // END GRAPHIC IMAGE

    public static final String I1 = "<//"; // TAG ITALIC TEXT

    public static final String I2 = "//>"; // END ITALIC TEXT

    public static final String M1 = "<~~"; // TAG MARKED TEXT

    public static final String M2 = "~~>"; // END MARKED TEXT

    public static final String S1 = "<--"; // TAG STRIKED TEXT

    public static final String S2 = "-->"; // END STRIKED TEXT

    public static final String U1 = "<__"; // TAG UNDERLINED TEXT

    public static final String U2 = "__>"; // END UNDERLINED TEXT

    public static final String H1T1 = "<!!"; // TAG HEADING 1 TEXT

    public static final String H1T2 = "!!>"; // END HEADING 1 TEXT

    public static final String H2T1 = "<@@"; // TAG HEADING 2 TEXT

    public static final String H2T2 = "@@>"; // END HEADING 2 TEXT

    public static final String H3T1 = "<##"; // TAG HEADING 3 TEXT

    public static final String H3T2 = "##>"; // END HEADING 3 TEXT

    public static final String H4T1 = "<$$"; // TAG HEADING 4 TEXT

    public static final String H4T2 = "$$>"; // END HEADING 4 TEXT

    public static final String H5T1 = "<%%"; // TAG HEADING 5 TEXT

    public static final String H5T2 = "%%>"; // END HEADING 5 TEXT

    public static final String H6T1 = "<^^"; // TAG HEADING 6 TEXT

    public static final String H6T2 = "^^>"; // END HEADING 6 TEXT

    public static final String BR = "\n";

    public static final String HT = "\t";

    public static final String ULLI = "\n\r\n\t";

    public static final String LILI = "\n\t";

    public static final String LIUL = "\n\r\n\b";

    public static final String NBSP = "\u00A0"; // &nbsp;

    public static final String LAQUO = "\u00AB"; // &laquo;

    public static final String RAQUO = "\u00BB"; // &raquo;

    public static final String FWLTS = "\uFF1C"; // Fullwidth Less-Than Sign

    public static final String FWGTS = "\uFF1E"; // Fullwidth Greater-Than Sign

    public static final String BULLET = "\u2022"; // &bull;

    public static final String MIDDOT = "\u00B7"; // &middot;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Creole tags">
    public static final String bb = "**";

    public static final String hh = "~~"; // wave-underlined is very fuzzy so highlighted is bold + underlined

    public static final String ii = "//";

    public static final String ss = "--";

    public static final String uu = "__";
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="HTML tags">
    public static final String ax = "<a href=\"";

    public static final String ay = "\">";

    public static final String az = "</a>";

    public static final String b1 = "<b>";

    public static final String b2 = "</b>";

    public static final String br = "<br>";

    public static final String cx = "<i class=\"";

    public static final String cy = "\">";

    public static final String cz = "</i>";

    public static final String g1 = "<img";

    public static final String g2 = "></img>";

    public static final String ht = StringUtils.repeat("&nbsp;", 4);

    public static final String i1 = "<i>";

    public static final String i2 = "</i>";

    public static final String m1 = "<mark>";

    public static final String m2 = "</mark>";

    public static final String s1 = "<del>";

    public static final String s2 = "</del>";

    public static final String u1 = "<ins>";

    public static final String u2 = "</ins>";

    public static final String h1t1 = "<h1>";

    public static final String h1t2 = "</h1>";

    public static final String h2t1 = "<h2>";

    public static final String h2t2 = "</h2>";

    public static final String h3t1 = "<h3>";

    public static final String h3t2 = "</h3>";

    public static final String h4t1 = "<h4>";

    public static final String h4t2 = "</h4>";

    public static final String h5t1 = "<h5>";

    public static final String h5t2 = "</h5>";

    public static final String h6t1 = "<h6>";

    public static final String h6t2 = "</h6>";

    public static final String ulli = "<ul><li>";

    public static final String lili = "</li><li>";

    public static final String liul = "</li></ul>";

    public static final String nbsp = "&nbsp;";

    public static final String bullet = "&bull;";

    public static final String middot = "&middot;";
    // </editor-fold>

    /**
     * Returns true if <code>string</code> contains any of the HTML pseudo-tags.
     *
     * @param string text to search for HTML pseudo-tags
     * @return true if <code>string</code> contains any of the HTML pseudo-tags; otherwise false
     */
    public static boolean isPseudoHTML(String string) {
        if (StringUtils.isBlank(string)) {
            return false;
        }
        final String[] tags = {ULLI, LIUL, AX, AY, AZ, B1, B2, CX, CY, CZ, G1, G2, I1, I2, M1, M2, S1, S2, U1, U2,
            H1T1, H1T2, H2T1, H2T2, H3T1, H3T2, H4T1, H4T2, H5T1, H5T2, H6T1, H6T2};
        for (String tag : tags) { // LILI, BR and HT are intentionally excluded
            if (string.contains(tag)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if <code>string</code> contains HTML tags.
     *
     * @param string text to search for HTML pseudo-tags
     * @return true if <code>string</code> contains HTML tags; otherwise false
     */
    public static boolean containsHTML(String string) {
        // Dotall mode enabled via the embedded flag expression (?s)
        return string != null && string.matches("(?s)([^<]*<\\w+(\\s[^>]*)?>)+.*");
    }

    private static final String request_context_path = "#{request.contextPath}";

    private static final String webapp_image_library = request_context_path + "/resources/images/base";

    private static final String img_argument_pattern = "src=\"{0}\" width=\"{1}\" height=\"{2}\"";

    private static final char img_argument_begin = '[';

    private static final char img_argument_close = ']';

    private static final char img_argument_delim = '|';

    public static String img(String name, int w, int h) {
        return img(webapp_image_library, name, w, h);
    }

    public static String img(String path, String name, int w, int h) {
        final String prefix = G1 + img_argument_begin;
        final String suffix = img_argument_close + G2;
        final String src = StringUtils.trimToEmpty(path) + "/" + name;
        final String[] arguments = {src, "" + w, "" + h};
        return prefix + StringUtils.join(arguments, img_argument_delim) + suffix;
    }

    private static String img(String html) {
        final String prefix = g1 + img_argument_begin;
        final String suffix = img_argument_close + g2;
        final String[] substrings = StringUtils.substringsBetween(html, prefix, suffix);
        Object[] arguments;
        String attributes;
        if (substrings != null) {
            for (String substring : substrings) {
                arguments = StringUtils.split(substring, img_argument_delim);
                if (arguments != null && arguments.length == 3) {
                    attributes = MessageFormat.format(img_argument_pattern, arguments);
//                  attributes = attributes.replace(request_context_path, URX.WEB);
                    html = StringUtils.replaceOnce(html, prefix + substring + suffix, g1 + " " + attributes + g2);
                }
            }
        }
        return html;
    }

    // <editor-fold defaultstate="collapsed" desc="pseudo-tagging methods">
    /**
     * This method builds an HTML {@code<a>} pseudo-tag, which defines a hyperlink.
     *
     * @param href Specifies the URL of the page the link goes to
     * @param text string to display instead of the URL
     * @return an HTML {@code<a>} pseudo-tag
     */
    public static String a(String href, String text) {
        return AX + href + AY + text + AZ;
    }

    /**
     * This method builds an HTML {@code<b>} pseudo-tag, which defines bold text without any extra importance.
     *
     * @param string text to display in bold
     * @return an HTML {@code<b>} pseudo-tag
     */
    public static String b(String string) {
        return x(B1, B2, string);
    }

    /**
     * This method builds several HTML {@code<b>} pseudo-tags, separated by <code>separator</code>.
     *
     * @param separator a text to separate the pseudo-tags
     * @param strings one or more texts to display in bold
     * @return several HTML {@code<b>} pseudo-tags, separated by <code>separator</code>.
     */
    public static String b(String separator, String... strings) {
        return x(B1, B2, separator, strings);
    }

    /**
     * This method builds an HTML {@code<i>} pseudo-tag for a CSS class.
     *
     * @param clazz Specifies the CSS class
     * @return an HTML {@code<i>} pseudo-tag
     */
    public static String ic(String clazz) {
        return ic(clazz, null);
    }

    /**
     * This method builds an HTML {@code<i>} pseudo-tag, which defines text with a CSS class.
     *
     * @param clazz Specifies the CSS class
     * @param text string to display
     * @return an HTML {@code<i>} pseudo-tag
     */
    public static String ic(String clazz, String text) {
        return CX + clazz + CY + StringUtils.trimToEmpty(text) + CZ;
    }

    /**
     * This method builds an HTML {@code<i>} pseudo-tag, which defines italic text.
     *
     * @param string text to display in italic
     * @return an HTML {@code<i>} pseudo-tag
     */
    public static String i(String string) {
        return x(I1, I2, string);
    }

    /**
     * This method builds several HTML {@code<i>} pseudo-tags, separated by <code>separator</code>.
     *
     * @param separator a text to separate the pseudo-tags
     * @param strings one or more texts to display in italic
     * @return several HTML {@code<i>} pseudo-tags, separated by <code>separator</code>.
     */
    public static String i(String separator, String... strings) {
        return x(I1, I2, separator, strings);
    }

    /**
     * This method builds an HTML {@code<mark>} pseudo-tag, which defines text that should be marked or highlighted.
     *
     * @param string text to be marked or highlighted
     * @return an HTML {@code<mark>} pseudo-tag
     */
    public static String m(String string) {
        return x(M1, M2, string);
    }

    /**
     * This method builds several HTML {@code<mark>} pseudo-tags, separated by <code>separator</code>.
     *
     * @param separator a text to separate the pseudo-tags
     * @param strings one or more texts to be marked or highlighted
     * @return several HTML {@code<mark>} pseudo-tags, separated by <code>separator</code>.
     */
    public static String m(String separator, String... strings) {
        return x(M1, M2, separator, strings);
    }

    /**
     * This method builds an HTML {@code<del>} pseudo-tag, which defines text that should be struck out.
     *
     * @param string text to be struck out
     * @return an HTML {@code<del>} pseudo-tag
     */
    public static String s(String string) {
        return x(S1, S2, string);
    }

    /**
     * This method builds several HTML {@code<del>} pseudo-tags, separated by <code>separator</code>.
     *
     * @param separator a text to separate the pseudo-tags
     * @param strings one or more texts to be struck out
     * @return several HTML {@code<del>} pseudo-tags, separated by <code>separator</code>.
     */
    public static String s(String separator, String... strings) {
        return x(S1, S2, separator, strings);
    }

    /**
     * This method builds an HTML {@code<ins>} pseudo-tag, which defines text that should be underlined.
     *
     * @param string text to be underlined
     * @return an HTML {@code<ins>} pseudo-tag
     */
    public static String u(String string) {
        return x(U1, U2, string);
    }

    /**
     * This method builds several HTML {@code<ins>} pseudo-tags, separated by <code>separator</code>.
     *
     * @param separator a text to separate the pseudo-tags
     * @param strings one or more texts to be underlined
     * @return several HTML {@code<ins>} pseudo-tags, separated by <code>separator</code>.
     */
    public static String u(String separator, String... strings) {
        return x(U1, U2, separator, strings);
    }

    private static final String lorem_ipsum = m("Lorem ipsum dolor sit amet, consectetur adipiscing elit:");

    /**
     * This method builds an {@code HTML <ul> pseudo-tag}, which defines an unordered (bulleted) list.
     *
     * @param strings one or more texts defining the list items
     * @return an {@code HTML <ul> pseudo-tag}
     */
    public static String ul(String... strings) {
        if (strings == null || strings.length == 0) {
            return "";
        }
        String ul = "";
        String li = ULLI;
        for (String string : strings) {
            if (string.startsWith(ULLI)) {
                li = li.equals(ULLI) ? ULLI + lorem_ipsum : "";
            }
            ul += li + string;
            li = LILI;
        }
        return ul + LIUL;
    }

    /**
     * This method builds an HTML {@code<h1>} pseudo-tag, which defines a level 1 heading.
     *
     * @param string header text
     * @return an HTML {@code<h1>} pseudo-tag
     */
    public static String h1(String string) {
        return x(H1T1, H1T2, string);
    }

    /**
     * This method builds an HTML {@code<h2>} pseudo-tag, which defines a level 2 heading.
     *
     * @param string header text
     * @return an HTML {@code<h2>} pseudo-tag
     */
    public static String h2(String string) {
        return x(H2T1, H2T2, string);
    }

    /**
     * This method builds an HTML {@code<h3>} pseudo-tag, which defines a level 3 heading.
     *
     * @param string header text
     * @return an HTML {@code<h3>} pseudo-tag
     */
    public static String h3(String string) {
        return x(H3T1, H3T2, string);
    }

    /**
     * This method builds an HTML {@code<h4>} pseudo-tag, which defines a level 4 heading.
     *
     * @param string header text
     * @return an HTML {@code<h4>} pseudo-tag
     */
    public static String h4(String string) {
        return x(H4T1, H4T2, string);
    }

    /**
     * This method builds an HTML {@code<h5>} pseudo-tag, which defines a level 5 heading.
     *
     * @param string header text
     * @return an HTML {@code<h5>} pseudo-tag
     */
    public static String h5(String string) {
        return x(H5T1, H5T2, string);
    }

    /**
     * This method builds an HTML {@code<h6>} pseudo-tag, which defines a level 6 heading.
     *
     * @param string header text
     * @return an HTML {@code<h6>} pseudo-tag
     */
    public static String h6(String string) {
        return x(H6T1, H6T2, string);
    }

    private static String x(String open, String close, String string) {
        return StringUtils.isBlank(string) ? "" : open + string + close;
    }

    private static String x(String open, String close, String separator, String... strings) {
        if (strings == null || strings.length == 0) {
            return "";
        }
        String sep = StringUtils.defaultIfBlank(separator, MIDDOT);
        List<String> list = new ArrayList<>();
        for (String string : strings) {
            if (StringUtils.isBlank(string)) {
                continue;
            }
            list.add(open + string + close);
        }
        return StringUtils.join(list, sep);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="formatting methods">
    /**
     * Returns <code>string</code> replacing the pseudo-tags with their corresponding XHTML tags.
     *
     * @param string text to search and replace the pseudo-tags
     * @return <code>string</code> with XHTML tags
     */
    public static String getXhtmlFormattedString(String string) {
        final String br1 = "<br>";
        final String br2 = "<br/>";
        String html = getHtmlFormattedString(string);
        return StringUtils.replace(html, br1, br2);
    }

    /**
     * Returns <code>string</code> replacing the pseudo-tags with their corresponding HTML tags.
     *
     * @param string text to search and replace the pseudo-tags
     * @return <code>string</code> with HTML tags
     */
    public static String getHtmlFormattedString(String string) {
        if (StringUtils.isBlank(string)) {
            return null;
        }
        String html = StringUtils.removeEnd(string, "\b" + NBSP);
        if (isPseudoHTML(html)) {
            html = htmlFormattedString(html);
        } else {
            html = html.replace("<<", LAQUO);
            html = html.replace(">>", RAQUO);
            html = html.replace(FWLTS, "&lt;");
            html = html.replace(FWGTS, "&gt;");
        }
        return html;
    }

    private static String htmlFormattedString(String string) {
        String html = StringEscapeUtils.escapeHtml(string);
        final String[] bullets = {"*", "-", "&bull;", "&middot;"};
        final String seudoULLI = StringEscapeUtils.escapeHtml(ULLI);
        final String seudoLILI = StringEscapeUtils.escapeHtml(LILI);
        for (String b : bullets) {
            html = StringUtils.replace(html, seudoULLI + b + "\t", seudoULLI);
            html = StringUtils.replace(html, seudoULLI + b, seudoULLI);
        }
        for (String b : bullets) {
            html = StringUtils.replace(html, seudoLILI + b + "\t", seudoLILI);
            html = StringUtils.replace(html, seudoLILI + b, seudoLILI);
        }
        final String[] TAGS = {ULLI, LILI, LIUL, AX, AY, AZ, B1, B2, CX, CY, CZ, G1, G2, I1, I2, M1, M2, S1, S2, U1, U2,
            H1T1, H1T2, H2T1, H2T2, H3T1, H3T2, H4T1, H4T2, H5T1, H5T2, H6T1, H6T2, BR, HT}; // BR and HT must be last
        final String[] tags = {ulli, lili, liul, ax, ay, az, b1, b2, cx, cy, cz, g1, g2, i1, i2, m1, m2, s1, s2, u1, u2,
            h1t1, h1t2, h2t1, h2t2, h3t1, h3t2, h4t1, h4t2, h5t1, h5t2, h6t1, h6t2, br, ht}; // br and ht must be last
        for (int i = 0; i < tags.length; i++) { // replace HTML pseudo-tags
            html = StringUtils.replace(html, StringEscapeUtils.escapeHtml(TAGS[i]), tags[i]);
        }
        html = img(html);
        html = html.replace("&lt;&lt;", LAQUO);
        html = html.replace("&gt;&gt;", RAQUO);
        html = html.replace(StringEscapeUtils.escapeHtml(FWLTS), "&lt;");
        html = html.replace(StringEscapeUtils.escapeHtml(FWGTS), "&gt;");
        html = html.replaceAll("\\p{Cntrl}", "");
        html = html.trim();
        return html;
    }

    /**
     * Returns <code>string</code> replacing the pseudo-tags with their corresponding Creole tags.
     *
     * @param string text to search and replace the pseudo-tags
     * @return <code>string</code> with Creole tags
     */
    public static String getCreoleFormattedString(String string) {
        if (StringUtils.isBlank(string)) {
            return null;
        }
        String creole = StringUtils.removeEnd(string, "\b" + NBSP);
        final String[] BMUC = {B1 + M1, M2 + B2, M1 + B1, B2 + M2, M1 + U1, U2 + M2, U1 + M1, M2 + U2};
        final String[] UBBU = {U1 + B1, B2 + U2, U1 + B1, B2 + U2, U1 + B1, B2 + U2, U1 + B1, B2 + U2};
        for (int i = 0; i < UBBU.length; i++) { // replace B, M, U combinations with U+B and B+U pseudo-tags
            creole = StringUtils.replace(creole, BMUC[i], UBBU[i]);
        }
        creole = creole.replaceAll(AX + ".*" + AY, "");
        creole = creole.replaceAll(CX + ".*" + CY, "");
        final String[] NCPT = {AZ, CZ, G1, G2, H1T1, H1T2, H2T1, H2T2, H3T1, H3T2, H4T1, H4T2, H5T1, H5T2, H6T1, H6T2};
        for (String tag : NCPT) {  // remove non creole pseudo-tags; ULLI, LILI, LIUL, BR and HT are intentionally excluded
            creole = StringUtils.remove(creole, tag);
        }
        final String ub = uu + bb; // wave-underlined is very fuzzy so...
        final String bu = bb + uu; // highlighted is bold + underlined
        final String[] TAGS = {B1, B2, I1, I2, M1, M2, S1, S2, U1, U2};
        final String[] tags = {bb, bb, ii, ii, ub, bu, ss, ss, uu, uu};
        for (int i = 0; i < tags.length; i++) { // replace creole pseudo-tags
            creole = StringUtils.replace(creole, TAGS[i], tags[i]);
        }
//      creole = creole.replace("<<", "&#x2BC7;");
//      creole = creole.replace(">>", "&#x2BC8;");
//      creole = creole.replace("<<", "<&caret-left>");
//      creole = creole.replace(">>", "<&caret-right>");
        creole = creole.replace("<<", LAQUO);
        creole = creole.replace(">>", RAQUO);
        creole = creole.replace(FWLTS, "<");
        creole = creole.replace(FWGTS, ">");
//      creole = creole.replace(" + ", " &#x2795 ");
//      creole = creole.replace(" - ", " &#x2796 ");
        creole = creole.replace(" + ", " <&plus> ");
        creole = creole.replace(" - ", " <&minus> ");
//      creole = replacePrefixesAndInfixes(creole);
        creole = creole.trim();
        return creole;
    }

    // <editor-fold defaultstate="collapsed" desc="replace prefixes and infixes">
    /*
    private static String replacePrefixesAndInfixes(String string) {
        final String[] prefixes = {
            "si", "sí",
            "if"
        };
        final String[] infixes1 = {
            "de lo contrario", "en caso contrario", "en otro caso", "en otros casos", "si no", "sí no",
            "else", "otherwise"
        };
        final String[] infixes2 = {
            "entonces",// "y", "o", "y/o",
            "then"//, "and", "or", "and/or"
        };
        final String nil = "";
        string = replacePrefixes(string, prefixes);
        string = replaceInfixes(string, ";", ",", infixes1);
        string = replaceInfixes(string, ";", nil, infixes1);
        string = replaceInfixes(string, ";", nil, prefixes); // after replacing infixes1 so "si no" and "sí no"have already been replaced
        string = replaceInfixes(string, nil, nil, infixes2);
        return string;
    }

    private static String replacePrefixes(String string, String... prefixes) {
        for (String prefix : prefixes) {
            if (StringUtils.startsWithIgnoreCase(string, prefix + " ")) {
                string = ii + prefix + ii + string.substring(prefix.length());
                break;
            }
        }
        return string;
    }

    private static String replaceInfixes(String string, String semicolon, String comma, String... infixes) {
        for (String infix : infixes) {
            string = string.replace(semicolon + " " + infix + comma + " ", semicolon + " " + ii + infix + ii + comma + " ");
        }
        return string;
    }

    /**/
    // </editor-fold>
//
    /**
     * Returns <code>string</code> removing the pseudo-tags.
     *
     * @param string text to search and remove the pseudo-tags
     * @return <code>string</code> without pseudo-tags
     */
    public static String getPlainTextString(String string) {
        return getTextFormattedString(string);
    }

    /**
     * Returns <code>string</code> removing the pseudo-tags.
     *
     * @param string text to search and remove the pseudo-tags
     * @return <code>string</code> without pseudo-tags
     */
    public static String getTextFormattedString(String string) {
        if (StringUtils.isBlank(string)) {
            return null;
        }
        String text = StringUtils.removeEnd(string, "\b" + NBSP);
        if (isPseudoHTML(string)) {
            text = text.replaceAll(AX + ".*" + AY, "");
            text = text.replaceAll(CX + ".*" + CY, "");
            final String[] TAGS = {AZ, B1, B2, CZ, G1, G2, I1, I2, M1, M2, S1, S2, U1, U2,
                H1T1, H1T2, H2T1, H2T2, H3T1, H3T2, H4T1, H4T2, H5T1, H5T2, H6T1, H6T2};
            for (String tag : TAGS) { // ULLI, LILI, LIUL, BR and HT are intentionally excluded
                text = StringUtils.remove(text, tag);
            }
        }
        text = text.replace("<<", LAQUO);
        text = text.replace(">>", RAQUO);
        text = text.replace(FWLTS, "<");
        text = text.replace(FWGTS, ">");
        text = text.trim();
        return text;
    }
    // </editor-fold>

}
