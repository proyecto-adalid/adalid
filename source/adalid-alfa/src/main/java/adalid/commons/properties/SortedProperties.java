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
package adalid.commons.properties;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Jorge Campins
 */
public class SortedProperties extends Properties {

    @SuppressWarnings("unchecked")
    @Override
    public synchronized Enumeration<Object> keys() {
        Enumeration<Object> keys = super.keys();
        ArrayList list = new ArrayList();
        while (keys.hasMoreElements()) {
            list.add(keys.nextElement());
        }
        Collections.sort(list);
        return Collections.enumeration(list);
    }

    public Set<String> stringPropertyNamesSortedList() {
        /*
        return asSortedList(super.stringPropertyNames());
        /**/
        return new TreeSet<>(super.stringPropertyNames());
    }

    // <editor-fold defaultstate="collapsed" desc="store">
    @Override
    public void store(Writer writer, String comments) throws IOException {
        store(writer, comments, null);
    }

    public void store(Writer writer, String comments, String lineSeparator) throws IOException {
        BufferedWriter bw = writer instanceof BufferedWriter ? (BufferedWriter) writer : new BufferedWriter(writer);
        store(bw, comments, lineSeparator, false);
    }

    @Override
    public void store(OutputStream out, String comments) throws IOException {
        store(out, comments, null);
    }

    public void store(OutputStream out, String comments, String lineSeparator) throws IOException {
        Writer writer = new OutputStreamWriter(out, "8859_1");
        BufferedWriter bw = new BufferedWriter(writer);
        store(bw, comments, lineSeparator, true);
    }

    private void store(BufferedWriter bw, String comments, String lineSeparator, boolean escUnicode) throws IOException {
        if (comments != null) {
            writeComments(bw, comments);
        }
//      bw.write("#" + new Date().toString());
//      bw.newLine();
        synchronized (this) {
            for (Enumeration e = keys(); e.hasMoreElements();) {
                String key = (String) e.nextElement();
                String val = (String) get(key);
                key = saveConvert(key, true, escUnicode);
                val = saveConvert(val, false, escUnicode);
                bw.write(key + "=" + val);
                if (lineSeparator == null) {
                    bw.newLine();
                } else {
                    bw.write(lineSeparator);
                }
            }
        }
        bw.flush();
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="saveConvert">
    private String saveConvert(String theString, boolean escapeSpace, boolean escapeUnicode) {
        int len = theString.length();
        int bufLen = len * 2;
        if (bufLen < 0) {
            bufLen = Integer.MAX_VALUE;
        }
        StringBuilder outBuffer = new StringBuilder(bufLen);
        for (int x = 0; x < len; x++) {
            char aChar = theString.charAt(x);
            // Handle common case first, selecting largest block that
            // avoids the specials below
            if ((aChar > 61) && (aChar < 127)) {
                if (aChar == '\\') {
                    outBuffer.append('\\');
                    outBuffer.append('\\');
                    continue;
                }
                outBuffer.append(aChar);
                continue;
            }
            switch (aChar) {
                case ' ' -> {
                    if (x == 0 || escapeSpace) {
                        outBuffer.append('\\');
                    }
                    outBuffer.append(' ');
                }
                case '\t' -> {
                    outBuffer.append('\\');
                    outBuffer.append('t');
                }
                case '\n' -> {
                    outBuffer.append('\\');
                    outBuffer.append('n');
                }
                case '\r' -> {
                    outBuffer.append('\\');
                    outBuffer.append('r');
                }
                case '\f' -> {
                    outBuffer.append('\\');
                    outBuffer.append('f');
                }
                case '=', ':', '#', '!' -> {
                    outBuffer.append('\\');
                    outBuffer.append(aChar);
                }
                default -> {
                    if (((aChar < 0x0020) || (aChar > 0x007e)) & escapeUnicode) {
                        outBuffer.append('\\');
                        outBuffer.append('u');
                        outBuffer.append(toHex((aChar >> 12) & 0xF));
                        outBuffer.append(toHex((aChar >> 8) & 0xF));
                        outBuffer.append(toHex((aChar >> 4) & 0xF));
                        outBuffer.append(toHex(aChar & 0xF));
                    } else {
                        outBuffer.append(aChar);
                    }
                }
            }
        }
        return outBuffer.toString();
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="writeComments">
    private static void writeComments(BufferedWriter bw, String comments) throws IOException {
        bw.write("#");
        int len = comments.length();
        int current = 0;
        int last = 0;
        char[] uu = new char[6];
        uu[0] = '\\';
        uu[1] = 'u';
        while (current < len) {
            char c = comments.charAt(current);
            if (c > '\u00ff' || c == '\n' || c == '\r') {
                if (last != current) {
                    bw.write(comments.substring(last, current));
                }
                if (c > '\u00ff') {
                    uu[2] = toHex((c >> 12) & 0xf);
                    uu[3] = toHex((c >> 8) & 0xf);
                    uu[4] = toHex((c >> 4) & 0xf);
                    uu[5] = toHex(c & 0xf);
                    bw.write(new String(uu));
                } else {
                    bw.newLine();
                    if (c == '\r'
                        && current != len - 1
                        && comments.charAt(current + 1) == '\n') {
                        current++;
                    }
                    if (current == len - 1
                        || (comments.charAt(current + 1) != '#'
                        && comments.charAt(current + 1) != '!')) {
                        bw.write("#");
                    }
                }
                last = current + 1;
            }
            current++;
        }
        if (last != current) {
            bw.write(comments.substring(last, current));
        }
        bw.newLine();
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="toHex">
    private static char toHex(int nibble) {
        return hexDigit[(nibble & 0xF)];
    }

    private static final char[] hexDigit = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
    };
    // </editor-fold>

}
