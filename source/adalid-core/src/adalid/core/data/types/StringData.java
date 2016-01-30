/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.data.types;

import adalid.core.XS2;
import adalid.core.enums.LetterCase;
import adalid.core.primitives.CharacterPrimitive;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public class StringData extends CharacterPrimitive {

    private static final String EOL = "\n";

    public static final String EMPTY = "";

    {
        XS2.setDataClass(this, StringData.class);
        XS2.setDataType(this, String.class);
    }

    private Integer _minLength = 0;

    private Integer _maxLength;

    private Pattern _pattern;

    private LetterCase _letterCase = LetterCase.UNSPECIFIED;

    private boolean _allowDiacritics = true;

    /**
     * @return the minLength
     */
    public Integer getMinLength() {
        return _minLength;
    }

    /**
     * @param minLength the minLength to set
     */
    public void setMinLength(Integer minLength) {
        XS2.checkAccess();
        _minLength = minLength;
    }

    /**
     * @return the maxLength
     */
    public Integer getMaxLength() {
        return _maxLength;
    }

    /**
     * @param maxLength the maxLength to set
     */
    public void setMaxLength(Integer maxLength) {
        XS2.checkAccess();
        _maxLength = maxLength;
    }

    /**
     * @return the pattern
     */
    public Pattern getPattern() {
        return _pattern;
    }

    /**
     * @param pattern the pattern to set
     */
    public void setPattern(Pattern pattern) {
        XS2.checkAccess();
        _pattern = pattern;
    }

    /**
     * @return the letter case
     */
    public LetterCase getLetterCase() {
        return _letterCase;
    }

    /**
     * @param letterCase the letter case to set
     */
    public void setLetterCase(LetterCase letterCase) {
        XS2.checkAccess();
        _letterCase = letterCase;
    }

    /**
     * @return the allow diacritics indicator
     */
    public boolean getAllowDiacritics() {
        return _allowDiacritics;
    }

    /**
     * @param allowDiacritics the allow diacritics indicator to set
     */
    public void setAllowDiacritics(boolean allowDiacritics) {
        XS2.checkAccess();
        _allowDiacritics = allowDiacritics;
    }

    // <editor-fold defaultstate="collapsed" desc="toString">
    @Override
    protected String fieldsToString(int n, String key, boolean verbose, boolean fields, boolean maps) {
        String tab = verbose ? StringUtils.repeat(" ", 4) : "";
        String fee = verbose ? StringUtils.repeat(tab, n) : "";
        String faa = " = ";
        String foo = verbose ? EOL : ", ";
        String string = super.fieldsToString(n, key, verbose, fields, maps);
        if (fields || verbose) {
            if (verbose) {
                string += fee + tab + "minLength" + faa + _minLength + foo;
                if (_maxLength != null) {
                    string += fee + tab + "maxLength" + faa + _maxLength + foo;
                }
            }
        }
        return string;
    }
    // </editor-fold>

}
