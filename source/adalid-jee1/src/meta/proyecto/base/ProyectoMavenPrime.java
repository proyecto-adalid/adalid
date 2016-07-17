/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.proyecto.base;

import adalid.commons.util.StrUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public class ProyectoMavenPrime extends ProyectoMaven {

    protected static final String DEFAULT_THEME = "home";

    protected static final String DEFAULT_THEME_VERSION = "1.0.10";

    private String _theme;

    private String _themeVersion;

    /**
     * @return the theme
     */
    public String getTheme() {
        return StringUtils.defaultIfBlank(_theme, getDefaultTheme());
    }

    /**
     * @param theme the theme to set
     */
    public void setTheme(String theme) {
        _theme = StrUtils.getMavenIdentifier(theme);
    }

    protected String getDefaultTheme() {
        return DEFAULT_THEME;
    }

    /**
     * @return the theme version
     */
    public String getThemeVersion() {
        return StringUtils.defaultIfBlank(_themeVersion, getDefaultThemeVersion());
    }

    /**
     * @param version the theme version to set
     */
    public void setThemeVersion(String version) {
        _themeVersion = StrUtils.getMavenVersion(version);
    }

    protected String getDefaultThemeVersion() {
        return DEFAULT_THEME_VERSION;
    }

}
