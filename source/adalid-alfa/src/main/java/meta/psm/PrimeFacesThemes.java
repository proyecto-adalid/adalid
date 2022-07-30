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

    // <editor-fold defaultstate="collapsed" desc="AllThemes/all-themes-1.0.10.jar">
//
//  public static final String AFTERDARK = "afterdark";
//
//  public static final String AFTERNOON = "afternoon";
//
//  public static final String AFTERWORK = "afterwork";
//
//  public static final String ARISTO = "aristo"; // Aristo misbehaves!
//
//  public static final String BLACK_TIE = "black-tie";
//
    public static final String BLITZER = "blitzer";
//
//  public static final String BLUESKY = "bluesky";

    public static final String BOOTSTRAP = "bootstrap";

    public static final String CASABLANCA = "casablanca";
//
//  public static final String CRUZE = "cruze";
//
//  public static final String CUPERTINO = "cupertino";

    public static final String DARK_HIVE = "dark-hive";

    public static final String DELTA = "delta";
//
//  public static final String DOT_LUV = "dot-luv";
//
//  public static final String EGGPLANT = "eggplant";
//
//  public static final String EXCITE_BIKE = "excite-bike";

    public static final String FLICK = "flick";

    public static final String GLASS_X = "glass-x";

    public static final String HOME = "home";

    public static final String HOT_SNEAKS = "hot-sneaks";
//
//  public static final String HUMANITY = "humanity";
//
//  public static final String LE_FROG = "le-frog";
//
//  public static final String MIDNIGHT = "midnight";
//
//  public static final String MINT_CHOC = "mint-choc";
//
//  public static final String OVERCAST = "overcast";

    public static final String PEPPER_GRINDER = "pepper-grinder";

    public static final String REDMOND = "redmond";
//
//  public static final String ROCKET = "rocket";

    public static final String SAM = "sam";

    public static final String SMOOTHNESS = "smoothness";
//
//  public static final String SOUTH_STREET = "south-street";

    public static final String START = "start";
//
//  public static final String SUNNY = "sunny";
//
//  public static final String SWANKY_PURSE = "swanky-purse";
//
//  public static final String TRONTASTIC = "trontastic";
//
//  public static final String UI_DARKNESS = "ui-darkness";
//
//  public static final String UI_LIGHTNESS = "ui-lightness";
//
//  public static final String VADER = "vader";
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Luna/primefaces-11.0.0.jar">
    public static final String LUNA_AMBER = "luna-amber";

    public static final String LUNA_BLUE = "luna-blue";

    public static final String LUNA_GREEN = "luna-green";

    public static final String LUNA_PINK = "luna-pink";
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Nova/primefaces-11.0.0.jar">
    public static final String NOVA_COLORED = "nova-colored";

    public static final String NOVA_DARK = "nova-dark";

    public static final String NOVA_LIGHT = "nova-light";
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="PrimeOne/primefaces-11.0.0.jar">
    public static final String ARYA = "arya";

    public static final String SAGA = "saga";

    public static final String VELA = "vela";
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="allThemesContains">
    static final Set<String> allThemes = new TreeSet<>();

    static {
//      allThemes.add(AFTERDARK);
//      allThemes.add(AFTERNOON);
//      allThemes.add(AFTERWORK);
//      allThemes.add(ARISTO);
//      allThemes.add(BLACK_TIE);
        allThemes.add(BLITZER);
//      allThemes.add(BLUESKY);
        allThemes.add(BOOTSTRAP);
        allThemes.add(CASABLANCA);
//      allThemes.add(CRUZE);
//      allThemes.add(CUPERTINO);
        allThemes.add(DARK_HIVE);
        allThemes.add(DELTA);
//      allThemes.add(DOT_LUV);
//      allThemes.add(EGGPLANT);
//      allThemes.add(EXCITE_BIKE);
        allThemes.add(FLICK);
        allThemes.add(GLASS_X);
        allThemes.add(HOME);
        allThemes.add(HOT_SNEAKS);
//      allThemes.add(HUMANITY);
//      allThemes.add(LE_FROG);
//      allThemes.add(MIDNIGHT);
//      allThemes.add(MINT_CHOC);
//      allThemes.add(OVERCAST);
        allThemes.add(PEPPER_GRINDER);
        allThemes.add(REDMOND);
//      allThemes.add(ROCKET);
        allThemes.add(SAM);
        allThemes.add(SMOOTHNESS);
//      allThemes.add(SOUTH_STREET);
        allThemes.add(START);
//      allThemes.add(SUNNY);
//      allThemes.add(SWANKY_PURSE);
//      allThemes.add(TRONTASTIC);
//      allThemes.add(UI_DARKNESS);
//      allThemes.add(UI_LIGHTNESS);
//      allThemes.add(VADER);
    }

    public static boolean allThemesContains(String theme) {
        return allThemes.contains(theme);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="primefacesJarContains">
    static final Set<String> version11Themes = new TreeSet<>();

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
        version11Themes.add(ARYA);
        version11Themes.add(SAGA);
        version11Themes.add(VELA);
        /**/
        primefacesJarThemes.put("11", version11Themes);
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
