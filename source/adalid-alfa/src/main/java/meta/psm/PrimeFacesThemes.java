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
package meta.psm;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * PrimeFaces themes
 *
 * @author Jorge Campins
 */
public class PrimeFacesThemes {

    // <editor-fold defaultstate="collapsed" desc="Luna/primefaces-11">
    public static final String LUNA_AMBER = "luna-amber";

    public static final String LUNA_BLUE = "luna-blue";

    public static final String LUNA_GREEN = "luna-green";

    public static final String LUNA_PINK = "luna-pink";
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Nova/primefaces-11">
    public static final String NOVA_COLORED = "nova-colored";

    public static final String NOVA_DARK = "nova-dark";

    public static final String NOVA_LIGHT = "nova-light";
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="PrimeOne/primefaces-11">
    public static final String ARYA = "arya";

    public static final String SAGA = "saga";

    public static final String VELA = "vela";
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="PrimeOne/primefaces-15">
    public static final String ARYA_BLUE = "arya-blue";

    public static final String SAGA_BLUE = "saga-blue";

    public static final String VELA_BLUE = "vela-blue";

    public static final String BOOTSTRAP_BLUE_DARK = "bootstrap4-blue-dark";

    public static final String BOOTSTRAP_BLUE_LIGHT = "bootstrap4-blue-light";

    public static final String BOOTSTRAP_PURPLE_DARK = "bootstrap4-purple-dark";

    public static final String BOOTSTRAP_PURPLE_LIGHT = "bootstrap4-purple-light";

    public static final String MATERIAL_COMPACT_DEEP_PURPLE_DARK = "material-compact-deeppurple-dark";

    public static final String MATERIAL_COMPACT_DEEP_PURPLE_LIGHT = "material-compact-deeppurple-light";

    public static final String MATERIAL_COMPACT_INDIGO_DARK = "material-compact-indigo-dark";

    public static final String MATERIAL_COMPACT_INDIGO_LIGHT = "material-compact-indigo-light";

    public static final String MATERIAL_DEEP_PURPLE_DARK = "material-deeppurple-dark";

    public static final String MATERIAL_DEEP_PURPLE_LIGHT = "material-deeppurple-light";

    public static final String MATERIAL_INDIGO_DARK = "material-indigo-dark";

    public static final String MATERIAL_INDIGO_LIGHT = "material-indigo-light";
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="primefacesJarContains">
    static final Set<String> version11Themes = new TreeSet<>();

    static final Set<String> version15Themes = new TreeSet<>();

    static final Map<String, Set<String>> primefacesJarThemes = new TreeMap<>();

    static {
        /**/
        version11Themes.add(LUNA_AMBER);
        version11Themes.add(LUNA_BLUE);
        version11Themes.add(LUNA_GREEN);
        version11Themes.add(LUNA_PINK);
        version11Themes.add(NOVA_COLORED);
        version11Themes.add(NOVA_DARK);
        version11Themes.add(NOVA_LIGHT);
        /**/
        version15Themes.addAll(version11Themes);
        /**/
        version11Themes.add(ARYA);
        version11Themes.add(SAGA);
        version11Themes.add(VELA);
        /**/
        version15Themes.add(ARYA_BLUE);
        version15Themes.add(SAGA_BLUE);
        version15Themes.add(VELA_BLUE);
        /**/
        version15Themes.add(BOOTSTRAP_BLUE_DARK);
        version15Themes.add(BOOTSTRAP_BLUE_LIGHT);
        version15Themes.add(BOOTSTRAP_PURPLE_DARK);
        version15Themes.add(BOOTSTRAP_PURPLE_LIGHT);
        version15Themes.add(MATERIAL_COMPACT_DEEP_PURPLE_DARK);
        version15Themes.add(MATERIAL_COMPACT_DEEP_PURPLE_LIGHT);
        version15Themes.add(MATERIAL_COMPACT_INDIGO_DARK);
        version15Themes.add(MATERIAL_COMPACT_INDIGO_LIGHT);
        version15Themes.add(MATERIAL_DEEP_PURPLE_DARK);
        version15Themes.add(MATERIAL_DEEP_PURPLE_LIGHT);
        version15Themes.add(MATERIAL_INDIGO_DARK);
        version15Themes.add(MATERIAL_INDIGO_LIGHT);
        /**/
        primefacesJarThemes.put("11", version11Themes);
        primefacesJarThemes.put("12", version11Themes);
        primefacesJarThemes.put("13", version11Themes);
        primefacesJarThemes.put("14", version11Themes);
        primefacesJarThemes.put("15", version15Themes);
    }

    public static boolean primefacesJarContains(String version, String theme) {
        if (version == null || theme == null) {
            return false;
        }
        Set<String> set = primefacesJarThemes.get(version);
        return set != null && set.contains(theme);
    }
    // </editor-fold>

}
