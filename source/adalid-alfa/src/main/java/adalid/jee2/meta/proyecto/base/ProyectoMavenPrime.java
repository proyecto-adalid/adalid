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
package adalid.jee2.meta.proyecto.base;

import adalid.commons.util.*;
import meta.psm.PrimeFacesThemes;
import org.apache.commons.collections.ExtendedProperties;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public abstract class ProyectoMavenPrime extends ProyectoMaven {

    private static final Logger logger = Logger.getLogger(ProyectoMavenPrime.class);

    protected static final String PRIMEFACES_THEME = "primefaces.theme";

    protected static final String PRIMEFACES_THEME_GROUP_ID = "primefaces.theme.groupId";

    protected static final String PRIMEFACES_THEME_ARTIFACT_ID = "primefaces.theme.artifactId";

    protected static final String PRIMEFACES_THEME_VERSION = "primefaces.theme.version";

    protected static final String PRIMEFACES_THEME_SWITCHING_ENABLED = "primefaces.theme.switching.enabled";

    protected static final String PRIMEFACES_VERSION = "primefaces.version";

    protected static final String VERSION_PRIMEFACES = PRIMEFACES_VERSION;

    protected static final String DEFAULT_THEME = PrimeFacesThemes.HOT_SNEAKS;

    protected static final String DEFAULT_THEME_VERSION = "[1.0.10,)";

    protected static final String DEFAULT_THEME_GROUP_ID = "org.primefaces.themes";

    protected static final String DEFAULT_ALL_THEMES_ARTIFACT_ID = "all-themes";

    private String _theme;

    private String _themeGroupId;

    private String _themeArtifactId;

    private String _themeVersion;

    private Boolean _themeSwitchingEnabled;

    private int _themeDialogWidthSubtrahend = 32; // 64;

    private int _themeDialogHeightSubtrahend = 40; // 80;

    private DialogPosition _themeDialogPosition = DialogPosition.CENTER;

    protected enum DialogPosition {

        CENTER, LEFT, RIGHT, TOP, BOTTOM, LEFT_TOP, LEFT_BOTTOM, RIGHT_TOP, RIGHT_BOTTOM

    }

    @Override
    protected void loadPrivateProperties(Level level, ExtendedProperties properties) {
        super.loadPrivateProperties(level, properties);
        if (_themeSwitchingEnabled == null) {
            setThemeSwitchingEnabled(BitUtils.valueOf(properties.getString(PRIMEFACES_THEME_SWITCHING_ENABLED)));
            logger.log(level, PRIMEFACES_THEME_SWITCHING_ENABLED + "=" + _themeSwitchingEnabled);
        }
        if (_theme == null) {
            setTheme(properties.getString(PRIMEFACES_THEME));
            logger.log(level, PRIMEFACES_THEME + "=" + getTheme());
        }
        if (_themeGroupId == null) {
            setThemeGroupId(properties.getString(PRIMEFACES_THEME_GROUP_ID));
            logger.log(level, PRIMEFACES_THEME_GROUP_ID + "=" + getThemeGroupId());
        }
        if (_themeArtifactId == null) {
            setThemeArtifactId(properties.getString(PRIMEFACES_THEME_ARTIFACT_ID));
            logger.log(level, PRIMEFACES_THEME_ARTIFACT_ID + "=" + getThemeArtifactId());
        }
        if (_themeVersion == null) {
            setThemeVersion(properties.getString(PRIMEFACES_THEME_VERSION));
            logger.log(level, PRIMEFACES_THEME_VERSION + "=" + getThemeVersion());
        }
    }

    /**
     * @return the theme
     */
    public String getTheme() {
        return StringUtils.defaultIfBlank(_theme, getDefaultTheme());
    }

    /**
     * El método setTheme se utiliza para especificar el nombre del tema de interfaz gráfica del proyecto generado. El valor predeterminado de esta
     * propiedad es <b>hot-sneaks</b> (campo HOT_SNEAKS de la clase PrimeFacesThemes).
     *
     * @param theme nombre del tema de interfaz gráfica del proyecto generado; si el proyecto generado permite cambiar el tema, este será el nombre
     * del tema inicial. Puede utilizar como argumento los campos definidos en la clase PrimeFacesThemes.
     */
    public void setTheme(String theme) {
        _theme = StrUtils.getMavenIdentifier(theme);
    }

    protected String getDefaultTheme() {
        return DEFAULT_THEME;
    }

    public int getThemeDialogWidthSubtrahend() {
        return _themeDialogWidthSubtrahend;
    }

    /**
     * El método setThemeDialogWidthSubtrahend se utiliza para especificar la cantidad de pixeles para ajustar el ancho de diálogos dinámicos.
     *
     * @param subtrahend cantidad de pixeles; debe ser un numero entre 0 y 320. El valor prdeterminado es 64.
     */
    public void setThemeDialogWidthSubtrahend(int subtrahend) {
        _themeDialogWidthSubtrahend = subtrahend < 0 ? 0 : subtrahend > 320 ? 320 : subtrahend;
    }

    public int getThemeDialogHeightSubtrahend() {
        return _themeDialogHeightSubtrahend;
    }

    /**
     * El método setThemeDialogHeightSubtrahend se utiliza para especificar la cantidad de pixeles para ajustar el alto de diálogos dinámicos.
     *
     * @param subtrahend cantidad de pixeles; debe ser un numero entre 0 y 240. El valor prdeterminado es 80.
     */
    public void setThemeDialogHeightSubtrahend(int subtrahend) {
        _themeDialogHeightSubtrahend = subtrahend < 0 ? 0 : subtrahend > 240 ? 240 : subtrahend;
    }

    public DialogPosition getThemeDialogPosition() {
        return _themeDialogPosition;
    }

    /**
     * El método setThemeDialogPosition se utiliza para especificar la posición inicial de diálogos dinámicos.
     *
     * @param position elemento de la enumeración ProyectoMavenPrime.DialogPosition. El valor prdeterminado es DialogPosition.CENTER.
     */
    public void setThemeDialogPosition(DialogPosition position) {
        _themeDialogPosition = position == null ? DialogPosition.CENTER : position;
    }

    /**
     * @return the theme group id
     */
    public String getThemeGroupId() {
        return StringUtils.defaultIfBlank(_themeGroupId, getDefaultThemeGroupId());
    }

    /**
     * El método setThemeGroupId se utiliza para especificar el grupo del artefacto Maven del tema de interfaz gráfica del proyecto generado. El valor
     * predeterminado de esta propiedad es <b>org.primefaces.themes</b>.
     *
     * @param groupId grupo del artefacto Maven del tema de la interfaz gráfica del proyecto generado.
     */
    public void setThemeGroupId(String groupId) {
        _themeGroupId = StrUtils.getMavenIdentifier(groupId);
    }

    protected String getDefaultThemeGroupId() {
        return DEFAULT_THEME_GROUP_ID;
    }

    /**
     * @return the theme artifact id
     */
    public String getThemeArtifactId() {
        return StringUtils.defaultIfBlank(_themeArtifactId, getDefaultThemeArtifactId());
    }

    /**
     * El método setThemeArtifactId se utiliza para especificar el nombre del artefacto Maven del tema de interfaz gráfica del proyecto generado. Si
     * el proyecto generado permite cambiar el tema, el valor predeterminado de esta propiedad es <b>all-themes</b>; en caso contrario, el valor
     * predeterminado es el nombre del tema (vea el método setTheme).
     *
     * @param artifactId nombre del artefacto Maven del tema de la interfaz gráfica del proyecto generado.
     */
    public void setThemeArtifactId(String artifactId) {
        _themeArtifactId = StrUtils.getMavenIdentifier(artifactId);
    }

    protected String getDefaultThemeArtifactId() {
        return isThemeSwitchingEnabled() ? DEFAULT_ALL_THEMES_ARTIFACT_ID : getTheme();
    }

    /**
     * @return the theme version
     */
    public String getThemeVersion() {
        return StringUtils.defaultIfBlank(_themeVersion, getDefaultThemeVersion());
    }

    /**
     * El método setThemeVersion se utiliza para especificar la versión del artefacto Maven del tema de interfaz gráfica del proyecto generado. El
     * valor predeterminado de esta propiedad es <b>[1.0.10,)</b> (versión 1.0.10 en adelante).
     *
     * @param version versión del artefacto Maven del tema de la interfaz gráfica del proyecto generado.
     */
    public void setThemeVersion(String version) {
        _themeVersion = StrUtils.getMavenVersion(version);
    }

    protected String getDefaultThemeVersion() {
        return DEFAULT_THEME_VERSION;
    }

    /**
     * @return true if the theme switching is enabled; false otherwise
     */
    public boolean isThemeSwitchingEnabled() {
        return BitUtils.valueOf(_themeSwitchingEnabled);
    }

    /**
     * El método setThemeSwitchingEnabled se utiliza para especificar si el proyecto generado permite, o no, cambiar el tema de la interfaz gráfica.
     * El valor predeterminado de esta propiedad es <b>false</b> (no permite cambiar el tema).
     *
     * @param enabled true, si el proyecto generado permite cambiar el tema de la interfaz gráfica; de lo contrario false.
     */
    public void setThemeSwitchingEnabled(boolean enabled) {
        _themeSwitchingEnabled = enabled;
    }

}