/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.util.google;

/**
 * @author Jorge Campins
 */
public class SpanBuilder {

    public static Span build(String string) {
        return build(string, null);
    }

    public static Span build(String string, String key) {
        return build(string, key, 0);
    }

    public static Span build(String string, String key, int beginIndex) {
        if (string == null || string.length() == 0) {
            return null;
        }
        key = key == null ? "" : key;
        int i0, i1, i2, i3, open = 0;
        i0 = string.indexOf(Span.SS1 + key, beginIndex);
        if (i0 < 0) {
            return null;
        }
        i2 = string.indexOf(Span.SS2, i0);
        if (i2 < 0) {
            return null;
        }
        open++;
        do {
            i3 = string.indexOf(Span.SS3, i2);
            i1 = string.indexOf(Span.SS1, i2);
            if (i1 < 0 || i1 > i3) {
                open--;
                i2 = string.indexOf(Span.SS2, i3);
            } else {
                open++;
                i2 = string.indexOf(Span.SS2, i1);
            }
        } while (open > 0);
        /**/
        return new Span(string, i0, i3 + Span.SS3.length());
    }

}
