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
package adalid.jee2;

import adalid.commons.bundles.Bundle;
import adalid.commons.util.StrUtils;
import adalid.core.Constants;
import adalid.core.Project;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class SpecialPage {

    private static final Logger logger = Logger.getLogger(SpecialPage.class);

    private static final String CODE = SpecialPage.class.getSimpleName();

    private static final String SERVLET_VIEW_FOLDER = "/faces/views/";

    private static final String DEFAULT_VIEW_FOLDER = "/faces/views/custom-made/";

    private static final String DEFAULT_VIEW_EXTENSION = ".xhtml";

    private static int next;

    private final String _code, _view;

    private boolean _publicView, _startOption, _menuOption, _inactiveOption;

    private final Set<Project> _menuModuleSet = new LinkedHashSet<>();

    private String _helpDocument, _helpFileName, _helpFileBookmark, _iconClass;

    private Long _id;

    private final Map<Locale, String> _localizedLabel = new LinkedHashMap<>();

    private final Map<Locale, String> _localizedBreadcrumbLabel = new LinkedHashMap<>();

    private final Map<Locale, String> _localizedDetailLabel = new LinkedHashMap<>();

    private final Map<Locale, String> _localizedMasterLabel = new LinkedHashMap<>();

    private final Map<Locale, String> _localizedShortDescription = new LinkedHashMap<>();

    public static SpecialPage of(String code) {
        return new SpecialPage(code, null);
    }

    public static SpecialPage of(String code, String view) {
        return new SpecialPage(code, view);
    }

    private SpecialPage(String code, String view) {
        code = StringUtils.trimToEmpty(code);
        if (code.isEmpty()) {
            _code = CODE + ++next;
            logger.warn("special page code is missing; \"" + _code + "\" will be used");
            Project.increaseWriterErrorCount();
        } else if (code.length() > 30) {
            _code = CODE + ++next;
            logger.warn("special page code \"" + code + "\" exceeds 30 characters; \"" + _code + "\" will be used instead");
            Project.increaseWriterErrorCount();
        } else if (!code.matches("^[A-Za-z][A-Za-z0-9]*$")) {
            _code = CODE + ++next;
            logger.warn("special page code \"" + code + "\" is invalid; \"" + _code + "\" will be used instead");
            Project.increaseWriterErrorCount();
        } else {
            _code = code;
        }
        _view = StringUtils.defaultIfBlank(view, DEFAULT_VIEW_FOLDER + _code + DEFAULT_VIEW_EXTENSION);
        _startOption = _view.startsWith(SERVLET_VIEW_FOLDER);
    }

    public static String view(String partialView) {
        return DEFAULT_VIEW_FOLDER + partialView + DEFAULT_VIEW_EXTENSION;
    }

    /**
     * @return the page code
     */
    public String getCode() {
        return _code;
    }

    /**
     * @return the page view
     */
    public String getView() {
        return _view;
    }

    public boolean isExternalView() {
        return !isInternalView();
    }

    public boolean isInternalView() {
        return _view.startsWith(SERVLET_VIEW_FOLDER);
    }

    public boolean isPublicView() {
        return _publicView;
    }

    /**
     * El método setPublicView se utiliza para establecer si la página especial es, o no, pública; es decir, si la página puede, o no, ser utilizada
     * por todos los usuarios del sistema, aun cuando no tengan autorización explícita para hacerlo. Su valor predeterminado es false.
     *
     * @param b true para que la página especial sea pública. Su valor predeterminado es false.
     */
    public void setPublicView(boolean b) {
        _publicView = b;
    }

    public boolean isStartOption() {
        return _startOption;
    }

    /**
     * El método setStartOption se utiliza para establecer si la página especial puede, o no, ser utilizada por los usuarios como página de inicio.
     *
     * @param b true para permitir el uso de la página especial como página de inicio. Su valor predeterminado es true, si la página es interna (es
     * decir, si view comienza con <b>/faces/views/</b>); y false, si es externa.
     */
    public void setStartOption(boolean b) {
        _startOption = b;
    }

    public boolean isMenuOption() {
        return _menuOption;
    }

    /**
     * El método addToModuleMenu se utiliza para agregar la página especial al menú de uno o mas módulos de la aplicación.
     *
     * @param modules uno o mas módulos de la aplicación.
     */
    public void addToModuleMenu(Project... modules) {
        if (modules != null) {
            for (Project module : modules) {
                if (module.isMenuModule()) {
                    _menuModuleSet.add(module);
                    _menuOption = true;
                }
            }
        }
    }

    public Set<Project> getMenuModuleSet() {
        return _menuModuleSet;
    }

    public boolean isInactiveOption() {
        return _inactiveOption;
    }

    /**
     * El método setInactiveOption se utiliza para establecer el estado inicial de la página especial como inactivo.
     *
     * @param b true para establecer el estado inicial de la página especial como inactivo. Su valor predeterminado es false.
     */
    public void setInactiveOption(boolean b) {
        _inactiveOption = b;
    }

    /**
     * @return the help document
     */
    public String getHelpDocument() {
        return _helpDocument;
    }

    /**
     * El método setHelpDocument se utiliza para establecer el documento incrustado de ayuda de la página.
     *
     * @param document definición del documento incrustado de ayuda de la página; si utiliza la plataforma jee2, puede ser una URL o un
     * <b>iframe</b> que incluya la URL del documento.
     */
    public void setHelpDocument(String document) {
        if (document != null && document.matches(Constants.EMBEDDED_DOCUMENT_REGEX)) {
            _helpDocument = document;
        } else {
            _helpDocument = null;
            logger.warn("\"" + document + "\" is not a valid help document; " + _code + " help document has been annulled ");
        }
    }

    /**
     * @return the page help file name
     */
    public String getHelpFileName() {
        return _helpFileName;
    }

    /**
     * El método setHelpFileName se utiliza para establecer la ruta y el nombre del archivo de ayuda de la página.
     *
     * @param fileName ruta y nombre del archivo de ayuda de la página; si utiliza la plataforma jee2, la ruta del archivo debe ser relativa al
     * subdirectorio especificado mediante el atributo extraordinario HELP_RESOURCES_FOLDER del proyecto maestro, y cuyo valor por omisión es el
     * subdirectorio resources/help/custom-made del directorio src/main/webapp del módulo Web de la aplicación.
     */
    public void setHelpFileName(String fileName) {
        _helpFileName = fileName;
    }

    /**
     * @return the page help file bookmark
     */
    public String getHelpFileBookmark() {
        return _helpFileBookmark;
    }

    /**
     * El método setHelpFileBookmark se utiliza para establecer el marcador en el archivo de ayuda de la página.
     *
     * @param bookmark marcador en el archivo de ayuda de la página.
     */
    public void setHelpFileBookmark(String bookmark) {
        _helpFileBookmark = bookmark;
    }

    /**
     * @return the page icon CSS class
     */
    public String getIconClass() {
        return _iconClass;
    }

    /**
     * El método setIconClass se utiliza para establecer la clase CSS del icono de la página.
     *
     * @param iconClass clase CSS de la página.
     */
    public void setIconClass(String iconClass) {
        _iconClass = iconClass;
    }

    /**
     * @return the page ID
     */
    public Long getId() {
        return _id;
    }

    /**
     * El método setId se utiliza para establecer el ID de la página.
     *
     * @param id ID de la página.
     */
    public void setId(Long id) {
        _id = id;
    }

    /**
     * @return the page numeric key code
     */
    public String getNumericKeyCode() {
        return _id == null ? StrUtils.getLongNumericKeyCode("SPECIAL_PAGE_" + _code) : _id.toString();
    }

    /**
     * @return the default label
     */
    public String getDefaultLabel() {
        return getLocalizedLabel(null);
    }

    /**
     * El método setDefaultLabel se utiliza para establecer la etiqueta de la página que se almacena en el archivo de recursos por defecto. En caso de
     * que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la aplicación utiliza el archivo de
     * recursos por defecto para obtener el valor de la descripción.
     *
     * @param defaultLabel sustantivo singular, preferiblemente sin complementos, que se usa como etiqueta de la página
     */
    public void setDefaultLabel(String defaultLabel) {
        setLocalizedLabel(null, defaultLabel);
    }

    /**
     * @param locale the locale for the label
     * @return the localized label
     */
    public String getLocalizedLabel(Locale locale) {
        Locale l = localeKey(locale);
        return _localizedLabel.get(l);
    }

    /**
     * El método setLocalizedLabel se utiliza para establecer la etiqueta de la página que se almacena en el archivo de recursos de configuración
     * regional. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la aplicación
     * utiliza el archivo de recursos por defecto para obtener el valor de la descripción.
     *
     * @param locale configuración regional
     * @param localizedLabel sustantivo singular, preferiblemente sin complementos, que se usa como etiqueta de la página
     */
    public void setLocalizedLabel(Locale locale, String localizedLabel) {
        Locale l = localeKey(locale);
        if (localizedLabel == null) {
            _localizedLabel.remove(l);
        } else {
            _localizedLabel.put(l, localizedLabel);
        }
    }

    /**
     * @return the default breadcrumb label
     */
    public String getDefaultBreadcrumbLabel() {
        return getLocalizedBreadcrumbLabel(null);
    }

    /**
     * El método setDefaultBreadcrumbLabel se utiliza para establecer la etiqueta de la miga de pan de la página que se almacena en el archivo de
     * recursos por defecto. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la
     * aplicación utiliza el archivo de recursos por defecto para obtener el valor de la etiqueta.
     *
     * @param defaultBreadcrumbLabel sustantivo singular, preferiblemente sin complementos, que se usa como etiqueta de la miga de pan de la página
     */
    public void setDefaultBreadcrumbLabel(String defaultBreadcrumbLabel) {
        setLocalizedBreadcrumbLabel(null, defaultBreadcrumbLabel);
    }

    /**
     * @param locale the locale for the breadcrumb label
     * @return the localized breadcrumb label
     */
    public String getLocalizedBreadcrumbLabel(Locale locale) {
        Locale l = localeKey(locale);
        return _localizedBreadcrumbLabel.get(l);
    }

    /**
     * El método setLocalizedBreadcrumbLabel se utiliza para establecer la etiqueta de la miga de pan de la página que se almacena en el archivo de
     * recursos de configuración regional. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la
     * interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el valor de la etiqueta.
     *
     * @param locale configuración regional
     * @param localizedBreadcrumbLabel sustantivo singular, preferiblemente sin complementos, que se usa como etiqueta de la miga de pan de la página
     */
    public void setLocalizedBreadcrumbLabel(Locale locale, String localizedBreadcrumbLabel) {
        Locale l = localeKey(locale);
        if (localizedBreadcrumbLabel == null) {
            _localizedBreadcrumbLabel.remove(l);
        } else {
            _localizedBreadcrumbLabel.put(l, localizedBreadcrumbLabel);
        }
    }

    /**
     * @return the default detail label
     */
    public String getDefaultDetailLabel() {
        return getLocalizedDetailLabel(null);
    }

    /**
     * El método setDefaultDetailLabel se utiliza para establecer la etiqueta del detalle de la página que se almacena en el archivo de recursos por
     * defecto. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la aplicación
     * utiliza el archivo de recursos por defecto para obtener el valor de la etiqueta.
     *
     * @param defaultDetailLabel sustantivo singular, preferiblemente sin complementos, que se usa como etiqueta del detalle de la página
     */
    public void setDefaultDetailLabel(String defaultDetailLabel) {
        setLocalizedDetailLabel(null, defaultDetailLabel);
    }

    /**
     * @param locale the locale for the detail label
     * @return the localized detail label
     */
    public String getLocalizedDetailLabel(Locale locale) {
        Locale l = localeKey(locale);
        return _localizedDetailLabel.get(l);
    }

    /**
     * El método setLocalizedDetailLabel se utiliza para establecer la etiqueta del detalle de la página que se almacena en el archivo de recursos de
     * configuración regional. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la
     * aplicación utiliza el archivo de recursos por defecto para obtener el valor de la etiqueta.
     *
     * @param locale configuración regional
     * @param localizedDetailLabel sustantivo singular, preferiblemente sin complementos, que se usa como etiqueta del detalle de la página
     */
    public void setLocalizedDetailLabel(Locale locale, String localizedDetailLabel) {
        Locale l = localeKey(locale);
        if (localizedDetailLabel == null) {
            _localizedDetailLabel.remove(l);
        } else {
            _localizedDetailLabel.put(l, localizedDetailLabel);
        }
    }

    /**
     * @return the default master label
     */
    public String getDefaultMasterLabel() {
        return getLocalizedMasterLabel(null);
    }

    /**
     * El método setDefaultMasterLabel se utiliza para establecer la etiqueta del maestro de la página que se almacena en el archivo de recursos por
     * defecto. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la aplicación
     * utiliza el archivo de recursos por defecto para obtener el valor de la etiqueta.
     *
     * @param defaultMasterLabel sustantivo singular, preferiblemente sin complementos, que se usa como etiqueta del maestro de la página
     */
    public void setDefaultMasterLabel(String defaultMasterLabel) {
        setLocalizedMasterLabel(null, defaultMasterLabel);
    }

    /**
     * @param locale the locale for the master label
     * @return the localized master label
     */
    public String getLocalizedMasterLabel(Locale locale) {
        Locale l = localeKey(locale);
        return _localizedMasterLabel.get(l);
    }

    /**
     * El método setLocalizedMasterLabel se utiliza para establecer la etiqueta del maestro de la página que se almacena en el archivo de recursos de
     * configuración regional. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la
     * aplicación utiliza el archivo de recursos por defecto para obtener el valor de la etiqueta.
     *
     * @param locale configuración regional
     * @param localizedMasterLabel sustantivo singular, preferiblemente sin complementos, que se usa como etiqueta del maestro de la página
     */
    public void setLocalizedMasterLabel(Locale locale, String localizedMasterLabel) {
        Locale l = localeKey(locale);
        if (localizedMasterLabel == null) {
            _localizedMasterLabel.remove(l);
        } else {
            _localizedMasterLabel.put(l, localizedMasterLabel);
        }
    }

    /**
     * @return the default short description
     */
    public String getDefaultShortDescription() {
        return getLocalizedShortDescription(null);
    }

    /**
     * El método setDefaultShortDescription se utiliza para establecer la descripción corta de la página que se almacena en el archivo de recursos por
     * defecto. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la aplicación
     * utiliza el archivo de recursos por defecto para obtener el valor de la descripción.
     *
     * @param defaultShortDescription una o más oraciones que describen brevemente la página
     */
    public void setDefaultShortDescription(String defaultShortDescription) {
        setLocalizedShortDescription(null, defaultShortDescription);
    }

    /**
     * @param locale the locale for the short description
     * @return the localized short description
     */
    public String getLocalizedShortDescription(Locale locale) {
        Locale l = localeKey(locale);
        return _localizedShortDescription.get(l);
    }

    /**
     * El método setLocalizedShortDescription se utiliza para establecer la descripción corta de la página que se almacena en el archivo de recursos
     * de configuración regional. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de
     * la aplicación utiliza el archivo de recursos por defecto para obtener el valor de la descripción.
     *
     * @param locale configuración regional
     * @param localizedShortDescription una o más oraciones que describen brevemente la página
     */
    public void setLocalizedShortDescription(Locale locale, String localizedShortDescription) {
        Locale l = localeKey(locale);
        if (localizedShortDescription == null) {
            _localizedShortDescription.remove(l);
        } else {
            _localizedShortDescription.put(l, localizedShortDescription);
        }
    }

    private Locale localeKey(Locale locale) {
        return locale == null ? Bundle.getLocale() : locale;
    }

}
