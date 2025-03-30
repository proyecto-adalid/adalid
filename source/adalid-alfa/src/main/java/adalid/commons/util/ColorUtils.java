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

import java.awt.Color;

/**
 * @author Jorge Campins
 */
public class ColorUtils {

    /* SOME */
    public static final Color SOME_RED = new Color(255, 85, 85);

    public static final Color SOME_BLUE = new Color(85, 85, 255);

    public static final Color SOME_GREEN = new Color(85, 255, 85);

    public static final Color SOME_YELLOW = new Color(255, 255, 85);

    public static final Color SOME_MAGENTA = new Color(255, 85, 255);

    public static final Color SOME_CYAN = new Color(85, 255, 255);

    /* DARK */
    public static final Color DARK_RED = new Color(192, 0, 0);

    public static final Color DARK_BLUE = new Color(0, 0, 192);

    public static final Color DARK_GREEN = new Color(0, 192, 0);

    public static final Color DARK_YELLOW = new Color(192, 192, 0);

    public static final Color DARK_MAGENTA = new Color(192, 0, 192);

    public static final Color DARK_CYAN = new Color(0, 192, 192);

    /* LIGHT */
    public static final Color LIGHT_RED = new Color(255, 64, 64);

    public static final Color LIGHT_BLUE = new Color(64, 64, 255);

    public static final Color LIGHT_GREEN = new Color(64, 255, 64);

    public static final Color LIGHT_YELLOW = new Color(255, 255, 64);

    public static final Color LIGHT_MAGENTA = new Color(255, 64, 255);

    public static final Color LIGHT_CYAN = new Color(64, 255, 255);

    /* VERY_DARK */
    public static final Color VERY_DARK_RED = new Color(128, 0, 0);

    public static final Color VERY_DARK_BLUE = new Color(0, 0, 128);

    public static final Color VERY_DARK_GREEN = new Color(0, 128, 0);

    public static final Color VERY_DARK_YELLOW = new Color(128, 128, 0);

    public static final Color VERY_DARK_MAGENTA = new Color(128, 0, 128);

    public static final Color VERY_DARK_CYAN = new Color(0, 128, 128);

    /* VERY_LIGHT */
    public static final Color VERY_LIGHT_RED = new Color(255, 128, 128);

    public static final Color VERY_LIGHT_BLUE = new Color(128, 128, 255);

    public static final Color VERY_LIGHT_GREEN = new Color(128, 255, 128);

    public static final Color VERY_LIGHT_YELLOW = new Color(255, 255, 128);

    public static final Color VERY_LIGHT_MAGENTA = new Color(255, 128, 255);

    public static final Color VERY_LIGHT_CYAN = new Color(128, 255, 255);

    public static Color[] chartColorPalette() {
        return new Color[]{
            SOME_RED,
            SOME_BLUE,
            SOME_GREEN,
            SOME_YELLOW,
            SOME_MAGENTA,
            SOME_CYAN,
            //
            //Color.pink,
            //Color.gray,
            //
            DARK_RED,
            DARK_BLUE,
            DARK_GREEN,
            DARK_YELLOW,
            DARK_MAGENTA,
            DARK_CYAN,
            //
            //Color.darkGray,
            //
            LIGHT_RED,
            LIGHT_BLUE,
            LIGHT_GREEN,
            LIGHT_YELLOW,
            LIGHT_MAGENTA,
            LIGHT_CYAN,
            //
            //Color.lightGray,
            //
            VERY_DARK_RED,
            VERY_DARK_BLUE,
            VERY_DARK_GREEN,
            VERY_DARK_YELLOW,
            VERY_DARK_MAGENTA,
            VERY_DARK_CYAN,
            //
            VERY_LIGHT_RED,
            VERY_LIGHT_BLUE,
            VERY_LIGHT_GREEN,
            VERY_LIGHT_YELLOW,
            VERY_LIGHT_MAGENTA,
            VERY_LIGHT_CYAN};
    }

    public static String hexColorCode(Color color) {
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }

}
