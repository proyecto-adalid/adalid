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
package adalid.core;

import adalid.core.enums.*;
import java.awt.Color;

/**
 * @author Jorge Campins
 */
public class InstanceTag {

    /**
     * Etiqueta sin colores y sin conversión de texto
     */
    public static final InstanceTag PLAIN_TEXT = new InstanceTag(null, null, null);

    /**
     * Etiqueta sin colores y todas las letras mayúsculas
     */
    public static final InstanceTag COLORLESS = new InstanceTag(null, null);

    /**
     * Etiqueta de color azul con letras blancas, todas mayúsculas
     */
    public static final InstanceTag BLUE = new InstanceTag(Color.decode("#2196F3"), Color.decode("#FFFFFF"));

    /**
     * Etiqueta de colores azulados y todas las letras mayúsculas
     */
    public static final InstanceTag BLUISH = new InstanceTag(Color.decode("#B3E5FC"), Color.decode("#23547B"));

    /**
     * Etiqueta de color verde con letras blancas, todas mayúsculas
     */
    public static final InstanceTag GREEN = new InstanceTag(Color.decode("#689F38"), Color.decode("#FFFFFF"));

    /**
     * Etiqueta de colores verdosos y todas las letras mayúsculas
     */
    public static final InstanceTag GREENISH = new InstanceTag(Color.decode("#C8E6C9"), Color.decode("#256029"));

    /**
     * Etiqueta de color verde claro con letras color carbón, todas mayúsculas
     */
    public static final InstanceTag LIGHT_GREEN = new InstanceTag(Color.decode("#90EE90"), Color.decode("#212529"));

    /**
     * Etiqueta de color naranja con letras color carbón, todas mayúsculas
     */
    public static final InstanceTag ORANGE = new InstanceTag(Color.decode("#FF9900"), Color.decode("#212529"));

    /**
     * Etiqueta de color anaranjado con letras color carbón, todas mayúsculas
     */
    public static final InstanceTag ORANGISH = new InstanceTag(Color.decode("#FFAD33"), Color.decode("#212529"));

    /**
     * Etiqueta de color arena con letras color carbón, todas mayúsculas
     */
    public static final InstanceTag SANDY_BEACH = new InstanceTag(Color.decode("#FFD8B2"), Color.decode("#212529"));

    /**
     * Etiqueta de color púrpura con letras blancas, todas mayúsculas
     */
    public static final InstanceTag PURPLE = new InstanceTag(Color.decode("#694382"), Color.decode("#FFFFFF"));

    /**
     * Etiqueta de colores púrpura y todas las letras mayúsculas
     */
    public static final InstanceTag PURPLISH = new InstanceTag(Color.decode("#ECCFFF"), Color.decode("#694382"));

    /**
     * Etiqueta de color rojo con letras blancas, todas mayúsculas
     */
    public static final InstanceTag RED = new InstanceTag(Color.decode("#D32F2F"), Color.decode("#FFFFFF"));

    /**
     * Etiqueta de colores rojizos y todas las letras mayúsculas
     */
    public static final InstanceTag REDISH = new InstanceTag(Color.decode("#FFCDD2"), Color.decode("#C63737"));

    /**
     * Etiqueta de color amarillo con letras color carbón, todas mayúsculas
     */
    public static final InstanceTag YELLOW = new InstanceTag(Color.decode("#FBC02D"), Color.decode("#212529"));

    /**
     * Etiqueta de colores amarillentos y todas las letras mayúsculas
     */
    public static final InstanceTag YELLOWISH = new InstanceTag(Color.decode("#FEEDAF"), Color.decode("#8A5340"));

    /**
     * El método copy construye una etiqueta copiando otra etiqueta.
     *
     * @param tag etiqueta original
     *
     * @return copia de la etiqueta original.
     */
    public static InstanceTag copy(InstanceTag tag) {
        return tag == null ? null : tag.copy();
    }

    /**
     * El método of construye una etiqueta con los colores especificados y letras mayúsculas.
     *
     * @param backgroundColor color de fondo. Especifique <code>null</code> para utilizar el color de fondo del tema.
     * @param textColor color del texto. Especifique <code>null</code> para utilizar el color del texto del tema.
     *
     * @return etiqueta con los colores especificados y letras mayúsculas.
     */
    public static InstanceTag of(Color backgroundColor, Color textColor) {
        return new InstanceTag(backgroundColor, textColor);
    }

    /**
     * El método of construye una etiqueta con los colores especificados y la conversión de texto especificada.
     *
     * @param backgroundColor color de fondo. Especifique <code>null</code> para utilizar el color de fondo del tema.
     * @param textColor color del texto. Especifique <code>null</code> para utilizar el color del texto del tema.
     * @param letterCase un elemento de la enumeración LetterCase. Especifique LOWER, UPPER o CAPITALIZED para convertir todos los caracteres a
     * minúsculas, todos a mayúsculas, o para capitalizar (convertir el primer carácter de cada palabra a mayúscula y el resto a minúsculas),
     * respectivamente. Alternativamente, especifique UNSPECIFIED para no ejecutar conversión alguna.
     *
     * @return etiqueta con los colores especificados y la conversión de texto especificada.
     */
    public static InstanceTag of(Color backgroundColor, Color textColor, LetterCase letterCase) {
        return new InstanceTag(backgroundColor, textColor, letterCase);
    }

    private InstanceTag(Color backgroundColor, Color textColor) {
        this(backgroundColor, textColor, LetterCase.UPPER);
    }

    private InstanceTag(Color backgroundColor, Color textColor, LetterCase letterCase) {
        this(backgroundColor, textColor, letterCase, false);
    }

    private InstanceTag(Color backgroundColor, Color textColor, LetterCase letterCase, boolean pillShaped) {
        _backgroundColor = backgroundColor;
        _textColor = textColor;
        _letterCase = letterCase;
        _pillShaped = pillShaped;
    }

    /**
     * El método copy construye una etiqueta copiando esta etiqueta.
     *
     * @return copia de esta etiqueta.
     */
    public InstanceTag copy() {
        return new InstanceTag(_backgroundColor, _textColor, _letterCase, _pillShaped);
    }

    private LetterCase _letterCase;

    /**
     * @return el tipo de conversión del texto de la etiqueta personalizada
     */
    public LetterCase getLetterCase() {
        return _letterCase == null ? LetterCase.UNSPECIFIED : _letterCase;
    }

    /**
     * El método setLetterCase se utiliza para establecer el tipo de conversión del texto de la etiqueta personalizada.
     *
     * @param letterCase un elemento de la enumeración LetterCase. Especifique LOWER, UPPER o CAPITALIZED para convertir todos los caracteres a
     * minúsculas, todos a mayúsculas, o para capitalizar (convertir el primer carácter de cada palabra a mayúscula y el resto a minúsculas),
     * respectivamente. Alternativamente, especifique UNSPECIFIED para no ejecutar conversión alguna.
     *
     * @return la etiqueta personalizada
     */
    public InstanceTag setLetterCase(LetterCase letterCase) {
        _letterCase = letterCase;
        return this;
    }

    private boolean _pillShaped;

    /**
     * @return el indicador de esquinas redondeadas en forma de píldora
     */
    public boolean isPillShaped() {
        return _pillShaped;
    }

    /**
     * El método setPillShaped se utiliza para establecer el indicador de esquinas redondeadas en forma de píldora de la etiqueta personalizada.
     *
     * @param pillShaped true para que la etiqueta tenga esquinas redondeadas en forma de píldora
     *
     * @return la etiqueta personalizada
     */
    public InstanceTag setPillShaped(boolean pillShaped) {
        _pillShaped = pillShaped;
        return this;
    }

    private Color _backgroundColor;

    /**
     * @return el color de fondo de la etiqueta personalizada
     */
    public Color getBackgroundColor() {
        return _backgroundColor;
    }

    /**
     * El método setBackgroundColor se utiliza para establecer el color de fondo de la etiqueta personalizada.
     *
     * @param color color de fondo
     *
     * @return la etiqueta personalizada
     */
    public InstanceTag setBackgroundColor(Color color) {
        _backgroundColor = color;
        return this;
    }

    public String getHtmlBackgroundColor() {
        return htmlColor(_backgroundColor);
    }

    private Color _textColor;

    /**
     * @return el color del texto de la etiqueta personalizada
     */
    public Color getTextColor() {
        return _textColor;
    }

    /**
     * El método setTextColor se utiliza para establecer el color del texto de la etiqueta personalizada.
     *
     * @param color color del texto
     *
     * @return la etiqueta personalizada
     */
    public InstanceTag setTextColor(Color color) {
        _textColor = color;
        return this;
    }

    public String getHtmlTextColor() {
        return htmlColor(_textColor);
    }

    private String htmlColor(Color color) {
        return color == null ? "UNSPECIFIED" : "#" + Integer.toHexString(color.getRGB()).substring(2).toUpperCase();
    }

}
