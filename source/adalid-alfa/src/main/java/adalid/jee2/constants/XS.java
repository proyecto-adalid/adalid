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
package adalid.jee2.constants;

/**
 * CSS Classes of Unicode symbols
 *
 * @see <a href="https://unicode.org/charts/nameslist/">Unicode Names List Charts</a>
 *
 * @author Jorge Campins
 */
public class XS {

    public static final String EQUALS_SIGN = codePointClass('=');

    public static final String WITH_BOLD = " xs-bold ";

    public static final String WITH_PRIMARY_COLOR = " xs-primary-color ";

    public static final String WITH_PRIMARY_COLOR_TEXT = " xs-primary-color-text ";

    public static final String WITH_TEXT_COLOR = " xs-text-color ";

    public static final String WITH_TEXT_COLOR_SECONDARY = " xs-text-color-secondary ";

    public static final String WITH_SIZE_2X = " xs-2x ";

    public static final String WITH_SIZE_3X = " xs-3x ";

    public static final String WITH_SIZE_4X = " xs-4x ";

    public static final String WITH_SIZE_5X = " xs-5x ";

    public static final String WITH_SIZE_LG = " xs-lg ";

    /**
     * El método codePointClass retorna la clase CSS correspondiente al codePoint.
     *
     * @param codePoint número asignado para representar un carácter abstracto en Unicode
     * @return clase CSS correspondiente al codePoint
     */
    public static String codePointClass(int codePoint) {
        return " xs xs-0x" + Integer.toHexString(codePoint) + " ";
    }

}
