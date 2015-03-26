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
public class Span {

    static final String SS1 = "<span";

    static final String SS2 = ">";

    static final String SS3 = "</span>";

    private String _source;

    private int _beginIndex;

    private int _endIndex;

    private String _string;

    private String _value;

    Span(String source, int beginIndex, int endIndex) {
        _source = source;
        _beginIndex = beginIndex;
        _endIndex = endIndex;
        _string = source.substring(beginIndex, endIndex);
        _value = value();
    }

    private String value() {
        int i = _string.indexOf(SS2) + 1;
        int j = _string.length() - SS3.length();
        return i < 1 || j < i ? "" : _string.substring(i, j);
    }

    /**
     * @return the source
     */
    public String getSource() {
        return _source;
    }

    /**
     * @return the begin index
     */
    public int getBeginIndex() {
        return _beginIndex;
    }

    /**
     * @return the end index
     */
    public int getEndIndex() {
        return _endIndex;
    }

    /**
     * @return the string
     */
    public String getString() {
        return _string;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return _value;
    }

}
