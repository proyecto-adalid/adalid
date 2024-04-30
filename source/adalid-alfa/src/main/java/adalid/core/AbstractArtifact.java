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

import adalid.commons.*;
import adalid.commons.bundles.*;
import adalid.commons.interfaces.*;
import adalid.commons.util.*;
import adalid.core.enums.*;
import adalid.core.expressions.*;
import adalid.core.interfaces.*;
import adalid.core.wrappers.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public abstract class AbstractArtifact implements Artifact, Wrappable {

    public static final Locale ENGLISH = Bundle.ENGLISH;

    public static final Locale SPANISH = Bundle.SPANISH;

    public static final Locale PORTUGUESE = Bundle.PORTUGUESE;

    private static final String NBSP = "\u00A0"; // a space in a line that cannot be broken by word wrap

    private static final Logger logger = Logger.getLogger(Artifact.class);

    private static final String EOL = "\n";

    // <editor-fold defaultstate="collapsed" desc="field declarations">
    /**
     *
     */
    private boolean _declared;

    /**
     *
     */
    private Boolean _inherited;

    /**
     *
     */
    private Boolean _inheritedFromAbstract;

    /**
     *
     */
    private Boolean _inheritedFromConcrete;

    /**
     *
     */
    private boolean _finalised;

    /**
     *
     */
    private boolean _finished;

    /**
     *
     */
    private String _name;

    /**
     *
     */
    private String _alias;

    /**
     *
     */
    private String _trace;

    /**
     *
     */
    private String _sqlName;

    /**
     *
     */
    private Locale _defaultLocale;

    /**
     *
     */
    private InlineHelpType _inlineHelpType;

    /**
     *
     */
//  private String _defaultLabel;
    private final Map<Locale, String> _localizedLabel = new LinkedHashMap<>();

    /**
     *
     */
//  private String _defaultShortLabel;
    private final Map<Locale, String> _localizedShortLabel = new LinkedHashMap<>();

    /**
     *
     */
//  private String _defaultColumnHeader;
    private final Map<Locale, String> _localizedColumnHeader = new LinkedHashMap<>();

    /**
     *
     */
//  private String _defaultCollectionLabel;
    private final Map<Locale, String> _localizedCollectionLabel = new LinkedHashMap<>();

    /**
     *
     */
//  private String _defaultCollectionShortLabel;
    private final Map<Locale, String> _localizedCollectionShortLabel = new LinkedHashMap<>();

    /**
     *
     */
//  private String _defaultDescription;
    private final Map<Locale, String> _localizedDescription = new LinkedHashMap<>();

    /**
     *
     */
//  private String _defaultShortDescription;
    private final Map<Locale, String> _localizedShortDescription = new LinkedHashMap<>();

    /**
     *
     */
//  private String _defaultTooltip;
    private final Map<Locale, String> _localizedTooltip = new LinkedHashMap<>();

    /**
     *
     */
//  private String _defaultSymbol;
    private final Map<Locale, String> _localizedSymbol = new LinkedHashMap<>();

    /**
     *
     */
    private Artifact _declaringArtifact;

    /**
     *
     */
    private Field _declaringField;

    /**
     *
     */
    private int _declaringFieldIndex;

    /**
     *
     */
    private int _depth;

    /**
     *
     */
    private int _round;

    /**
     *
     */
    private final Map<Class<? extends Annotation>, Field> _annotations = new LinkedHashMap<>();

    /**
     * platform-specific attributes
     */
    private final Map<String, Object> _attributes = new LinkedHashMap<>();
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="field getters and setters">
    /**
     * @return true if the artifact is declared
     */
    @Override
    public boolean isDeclared() {
        return _declared;
    }

    /**
     * @return true if the artifact is not declared
     */
    @Override
    public boolean isNotDeclared() {
        return !_declared;
    }

    /**
     *
     */
    void setDeclared(String name) {
        setDeclared(name, null);
    }

    /**
     *
     */
    void setDeclared(String name, Artifact declaringArtifact) {
        setDeclared(name, declaringArtifact, null);
    }

    /**
     *
     */
    void setDeclared(String name, Artifact declaringArtifact, Field declaringField) {
        setDeclared(name, declaringArtifact, declaringField, 0);
    }

    /**
     *
     */
    void setDeclared(String name, Artifact declaringArtifact, Field declaringField, int declaringFieldIndex) {
        if (_declared) {
            return;
        }
        setName(name);
        setDeclaringArtifact(declaringArtifact);
        setDeclaringField(declaringField);
        setDeclaringFieldIndex(declaringFieldIndex);
        _declared = true;
    }

    @Override
    public boolean isFinalised() {
        return _finalised;
    }

    @Override
    public boolean finalise() {
        if (_finalised) {
            logger.warn(getFullName() + " already finalised! ");
            Project.increaseParserWarningCount();
            return false;
        }
        checkName();
        _finalised = true;
        return _finalised;
    }

    /**
     * @return the finished indicator
     */
    @Override
    public boolean isFinished() {
        return _finished;
    }

    @Override
    public boolean finish() {
        if (_finished) {
            logger.warn(getFullName() + " already finished! ");
            Project.increaseParserWarningCount();
            return false;
        }
        _finished = true;
        return _finished;
    }

    protected boolean checkName() {
        String name = getName();
        if (StringUtils.isBlank(name)) {
            logger.error("missing artifact name");
            Project.increaseParserErrorCount();
        } else if (name.length() < 2) {
            logger.error(getFullName() + " must be renamed; " + name + " is too short an artifact name");
            Project.increaseParserErrorCount();
        } else if (!name.matches("^[a-zA-Z]\\w*$")) {
            logger.error(getFullName() + " must be renamed; " + name + " is an invalid artifact name");
            Project.increaseParserErrorCount();
        } else if (name.matches("^[a-z][A-Z].*$")) {
            logger.error(getFullName() + " must be renamed; " + name + " is an invalid artifact name (begins with [a-z][A-Z])");
            Project.increaseParserErrorCount();
        } else if (name.matches("^[A-Z][A-Z]+[a-z].*$")) {
            logger.error(getFullName() + " must be renamed; " + name + " is an invalid artifact name (begins with [A-Z][A-Z]+[a-z])");
            Project.increaseParserErrorCount();
        } else {
            return true;
        }
        return false;
    }

    /**
     * @return true if the artifact is inherited
     */
    @Override
    public boolean isInherited() {
        return _declared && inherited();
    }

    /**
     * @return true if the artifact is not inherited
     */
    @Override
    public boolean isNotInherited() {
        return _declared && !inherited();
    }

    private boolean inherited() {
        if (_inherited == null) {
            Field declaringField = getDeclaringField();
            Artifact declaringArtifact = getDeclaringArtifact();
            Class<?> declaringFieldClass = declaringField.getDeclaringClass();
            Class<?> declaringFieldNamedClass = XS1.getNamedClass(declaringFieldClass);
            Class<?> declaringArtifactNamedClass = XS1.getNamedClass(declaringArtifact);
            String declaringFieldNamedClassSimpleName = declaringFieldNamedClass.getSimpleName();
            String declaringArtifactNamedClassSimpleName = declaringArtifactNamedClass.getSimpleName();
            boolean fromAbstract = Modifier.isAbstract(declaringFieldNamedClass.getModifiers());
            boolean fromConcrete = !fromAbstract;
            _inherited = !declaringFieldNamedClassSimpleName.equals(declaringArtifactNamedClassSimpleName);
            _inheritedFromAbstract = _inherited && fromAbstract;
            _inheritedFromConcrete = _inherited && fromConcrete;
        }
        return _inherited;
    }

    /**
     * @return true if the artifact is inherited from an abstract class
     */
    @Override
    public boolean isInheritedFromAbstract() {
        return isInherited() && _inheritedFromAbstract;
    }

    /**
     * @return true if the artifact is not inherited from an abstract class
     */
    @Override
    public boolean isNotInheritedFromAbstract() {
        return !isInheritedFromAbstract();
    }

    /**
     * @return true if the artifact is inherited from a concrete class
     */
    @Override
    public boolean isInheritedFromConcrete() {
        return isInherited() && _inheritedFromConcrete;
    }

    /**
     * @return true if the artifact is not inherited from a concrete class
     */
    @Override
    public boolean isNotInheritedFromConcrete() {
        return !isInheritedFromConcrete();
    }

    /**
     * @return the name
     */
    @Override
    public String getName() {
        return _name;
    }

    /**
     * @param name the name to set
     */
    void setName(String name) {
        if (_declared) {
            return;
        }
        _name = name;
    }

    /**
     * @return the alias
     */
    @Override
    public String getAlias() {
        return _alias == null ? _name : _alias;
    }

    /**
     * El método setAlias se utiliza para establecer el alias (código alterno) del proyecto. El alias solo puede contener letras minúsculas y números,
     * debe comenzar por una letra, y no puede ser jee2ap101, meta o workspace. Se recomienda utilizar un alias que tenga el nombre de su proyecto
     * como prefijo.
     *
     * Si utiliza la plataforma jee2, el alias del proyecto maestro es el nombre del directorio raíz de los archivos generados; por lo tanto, se debe
     * establecer un alias diferente antes de cada ejecución del método generate.
     *
     * @param alias código alterno del proyecto
     */
    @Override
    public void setAlias(String alias) {
        checkScope();
        _alias = alias;
    }

    /**
     * @return the trace message
     */
    public String getTrace() {
        return _trace == null ? "" : _trace.trim();
    }

    /**
     * @param trace message
     */
    public void setTrace(String trace) {
        checkScope();
        _trace = trace;
    }

    /**
     * @return the SQL name
     */
    @Override
    public String getSqlName() {
        return _sqlName;
    }

    /**
     * El método setSqlName se utiliza para establecer el nombre SQL del artefacto. Si este método no es ejecutado, el nombre SQL se determina a
     * partir del nombre del artefacto, sustituyendo cada letra mayúscula por un guion bajo (underscore) seguido de la letra convertida en minúscula.
     *
     * @param sqlName nombre SQL del artefacto
     */
    @Override
    public void setSqlName(String sqlName) {
        checkScope();
        _sqlName = sqlName;
    }

    /**
     * @return the default locale
     */
    @Override
    public Locale getDefaultLocale() {
        if (_defaultLocale != null) {
            return _defaultLocale;
        }
        if (_declaringArtifact != null) {
            return _declaringArtifact.getDefaultLocale();
        }
        return Bundle.getLocale();
    }

    public InlineHelpType getInlineHelpType() {
        return _inlineHelpType != null ? _inlineHelpType : Project.getDefaultInlineHelpType();
    }

    public void setInlineHelpType(InlineHelpType type) {
        checkScope();
        if (!InlineHelpType.UNSPECIFIED.equals(type)) {
            _inlineHelpType = type;
        }
    }

    /**
     * @return the default label
     */
    @Override
    public String getDefaultLabel() {
        return getLocalizedLabel(null);
    }

    /**
     * El método setDefaultLabel se utiliza para establecer la etiqueta del artefacto que se almacena en el archivo de recursos por defecto. En caso
     * de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la aplicación utiliza el archivo de
     * recursos por defecto para obtener el valor de la etiqueta.
     *
     * @param defaultLabel sustantivo singular que se usa como etiqueta del artefacto
     */
    @Override
    public void setDefaultLabel(String defaultLabel) {
        setLocalizedLabel(null, defaultLabel);
    }

    /**
     * @return the default short label
     */
    @Override
    public String getDefaultShortLabel() {
        return getLocalizedShortLabel(null);
    }

    /**
     * El método setDefaultShortLabel se utiliza para establecer la etiqueta corta del artefacto que se almacena en el archivo de recursos por
     * defecto. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la aplicación
     * utiliza el archivo de recursos por defecto para obtener el valor de la etiqueta.
     *
     * @param defaultShortLabel sustantivo singular, preferiblemente sin complementos, que se usa como etiqueta corta del artefacto
     */
    @Override
    public void setDefaultShortLabel(String defaultShortLabel) {
        setLocalizedShortLabel(null, defaultShortLabel);
    }

    /**
     * El método setDefaultShortLabel se utiliza para establecer la etiqueta corta del artefacto que se almacena en el archivo de recursos por
     * defecto. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la aplicación
     * utiliza el archivo de recursos por defecto para obtener el valor de la etiqueta.
     *
     * @param defaultShortLabel sustantivo singular, preferiblemente sin complementos, que se usa como etiqueta corta del artefacto
     */
    @Override
    public void setDefaultShortLabel(String... defaultShortLabel) {
        setLocalizedShortLabel(null, defaultShortLabel);
    }

    /**
     * @return the default column header
     */
    @Override
    public String getDefaultColumnHeader() {
        return getLocalizedColumnHeader(null);
    }

    /**
     * El método setDefaultColumnHeader se utiliza para establecer el encabezado de columna del artefacto que se almacena en el archivo de recursos
     * por defecto. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la aplicación
     * utiliza el archivo de recursos por defecto para obtener el valor del encabezado.
     *
     * @param defaultColumnHeader sustantivo singular, preferiblemente sin complementos, que se usa como encabezado de columna del artefacto
     */
    @Override
    public void setDefaultColumnHeader(String defaultColumnHeader) {
        setLocalizedColumnHeader(null, defaultColumnHeader);
    }

    /**
     * El método setDefaultColumnHeader se utiliza para establecer el encabezado de columna del artefacto que se almacena en el archivo de recursos
     * por defecto. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la aplicación
     * utiliza el archivo de recursos por defecto para obtener el valor del encabezado.
     *
     * @param defaultColumnHeader sustantivo singular, preferiblemente sin complementos, que se usa como encabezado de columna del artefacto
     */
    @Override
    public void setDefaultColumnHeader(String... defaultColumnHeader) {
        setLocalizedColumnHeader(null, defaultColumnHeader);
    }

    /**
     * @return the default collection label
     */
    @Override
    public String getDefaultCollectionLabel() {
        return getLocalizedCollectionLabel(null);
    }

    /**
     * El método setDefaultCollectionLabel se utiliza para establecer la etiqueta de colección del artefacto que se almacena en el archivo de recursos
     * por defecto. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la aplicación
     * utiliza el archivo de recursos por defecto para obtener el valor de la etiqueta.
     *
     * @param defaultCollectionLabel sustantivo plural que se usa como etiqueta de colección del artefacto
     */
    @Override
    public void setDefaultCollectionLabel(String defaultCollectionLabel) {
        setLocalizedCollectionLabel(null, defaultCollectionLabel);
    }

    /**
     * @return the default collection short label
     */
    @Override
    public String getDefaultCollectionShortLabel() {
        return getLocalizedCollectionShortLabel(null);
    }

    /**
     * El método setDefaultCollectionShortLabel se utiliza para establecer la etiqueta corta de colección del artefacto que se almacena en el archivo
     * de recursos por defecto. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la
     * aplicación utiliza el archivo de recursos por defecto para obtener el valor de la etiqueta.
     *
     * @param defaultCollectionShortLabel sustantivo plural, preferiblemente sin complementos, que se usa como etiqueta corta de colección del
     * artefacto
     */
    @Override
    public void setDefaultCollectionShortLabel(String defaultCollectionShortLabel) {
        setLocalizedCollectionShortLabel(null, defaultCollectionShortLabel);
    }

    /**
     * @return the default description
     */
    @Override
    public String getDefaultDescription() {
        return getLocalizedDescription(null);
    }

    /**
     * El método setDefaultDescription se utiliza para establecer la descripción del artefacto que se almacena en el archivo de recursos por defecto.
     * En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la aplicación utiliza el
     * archivo de recursos por defecto para obtener el valor de la descripción.
     *
     * @param defaultDescription una o más oraciones que describen el artefacto
     */
    @Override
    public void setDefaultDescription(String defaultDescription) {
        setLocalizedDescription(null, defaultDescription);
    }

    /**
     * @return the default short description
     */
    @Override
    public String getDefaultShortDescription() {
        return getLocalizedShortDescription(null);
    }

    /**
     * El método setDefaultShortDescription se utiliza para establecer la descripción corta del artefacto que se almacena en el archivo de recursos
     * por defecto. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la aplicación
     * utiliza el archivo de recursos por defecto para obtener el valor de la descripción.
     *
     * @param defaultShortDescription una o más oraciones que describen brevemente el artefacto
     */
    @Override
    public void setDefaultShortDescription(String defaultShortDescription) {
        setLocalizedShortDescription(null, defaultShortDescription);
    }

    /**
     * @return the default tooltip
     */
    @Override
    public String getDefaultTooltip() {
        return getLocalizedTooltip(null);
    }

    /**
     * El método setDefaultTooltip se utiliza para establecer la descripción emergente (tooltip) del artefacto que se almacena en el archivo de
     * recursos por defecto. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la
     * aplicación utiliza el archivo de recursos por defecto para obtener el valor de la descripción.
     *
     * @param defaultTooltip una o más oraciones que describen muy brevemente el artefacto
     */
    @Override
    public void setDefaultTooltip(String defaultTooltip) {
        setLocalizedTooltip(null, defaultTooltip);
    }

    /**
     * @return the default symbol
     */
    @Override
    public String getDefaultSymbol() {
        return getLocalizedSymbol(null);
    }

    /**
     * El método setDefaultSymbol se utiliza para establecer el símbolo o unidad del artefacto que se almacena en el archivo de recursos por defecto.
     * En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la aplicación utiliza el
     * archivo de recursos por defecto para obtener el valor del símbolo.
     *
     * @param defaultSymbol una o más oraciones que describen muy brevemente el artefacto
     */
    @Override
    public void setDefaultSymbol(String defaultSymbol) {
        setLocalizedSymbol(null, defaultSymbol);
    }

    protected void copyLocalizedStrings(Artifact artifact) {
        if (artifact instanceof AbstractArtifact that) {
            if (artifact != this) {
                _localizedLabel.clear();
                _localizedLabel.putAll(that._localizedLabel);
                _localizedShortLabel.clear();
                _localizedShortLabel.putAll(that._localizedShortLabel);
                _localizedCollectionLabel.clear();
                _localizedCollectionLabel.putAll(that._localizedCollectionLabel);
                _localizedCollectionShortLabel.clear();
                _localizedCollectionShortLabel.putAll(that._localizedCollectionShortLabel);
                _localizedDescription.clear();
                _localizedDescription.putAll(that._localizedDescription);
                _localizedShortDescription.clear();
                _localizedShortDescription.putAll(that._localizedShortDescription);
                _localizedSymbol.clear();
                _localizedSymbol.putAll(that._localizedSymbol);
                _localizedTooltip.clear();
                _localizedTooltip.putAll(that._localizedTooltip);
            }
        }
    }

    /**
     * @param locale the locale for the label
     * @return the localized label
     */
    @Override
    public String getLocalizedLabel(Locale locale) {
        Locale l = localeReadingKey(locale);
        return _localizedLabel.get(l);
    }

    /**
     * El método setLocalizedLabel se utiliza para establecer la etiqueta del artefacto que se almacena en el archivo de recursos de configuración
     * regional. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la aplicación
     * utiliza el archivo de recursos por defecto para obtener el valor de la etiqueta.
     *
     * @param locale configuración regional
     * @param localizedLabel sustantivo singular que se usa como etiqueta del artefacto
     */
    @Override
    public void setLocalizedLabel(Locale locale, String localizedLabel) {
        checkScope();
        Locale l = localeWritingKey(locale);
        if (localizedLabel == null) {
            _localizedLabel.remove(l);
        } else {
            _localizedLabel.put(l, localizedLabel);
        }
        setLocalizedShortLabel(locale, localizedLabel);
    }

    /**
     * @param locale the locale for the short label
     * @return the localized short label
     */
    @Override
    public String getLocalizedShortLabel(Locale locale) {
        Locale l = localeReadingKey(locale);
        return _localizedShortLabel.get(l);
    }

    /**
     * El método setLocalizedShortLabel se utiliza para establecer la etiqueta corta del artefacto que se almacena en el archivo de recursos de
     * configuración regional. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la
     * aplicación utiliza el archivo de recursos por defecto para obtener el valor de la etiqueta.
     *
     * @param locale configuración regional
     * @param localizedShortLabel sustantivo singular, preferiblemente sin complementos, que se usa como etiqueta corta del artefacto
     */
    @Override
    public void setLocalizedShortLabel(Locale locale, String localizedShortLabel) {
        checkScope();
        Locale l = localeWritingKey(locale);
        if (localizedShortLabel == null) {
            _localizedShortLabel.remove(l);
        } else {
            _localizedShortLabel.put(l, localizedShortLabel);
        }
    }

    /**
     * El método setLocalizedShortLabel se utiliza para establecer la etiqueta corta del artefacto que se almacena en el archivo de recursos de
     * configuración regional. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la
     * aplicación utiliza el archivo de recursos por defecto para obtener el valor de la etiqueta.
     *
     * @param locale configuración regional
     * @param localizedShortLabel sustantivo singular, preferiblemente sin complementos, que se usa como etiqueta corta del artefacto
     */
    @Override
    public void setLocalizedShortLabel(Locale locale, String... localizedShortLabel) {
        checkScope();
        Locale l = localeWritingKey(locale);
        if (localizedShortLabel == null || localizedShortLabel.length == 0) {
            _localizedShortLabel.remove(l);
        } else {
            _localizedShortLabel.put(l, StringUtils.join(localizedShortLabel, BR));
        }
    }

    /**
     * @param locale the locale for the short label
     * @return the localized column header
     */
    @Override
    public String getLocalizedColumnHeader(Locale locale) {
        Locale l = localeReadingKey(locale);
        return _localizedColumnHeader.get(l);
    }

    /**
     * El método setLocalizedColumnHeader se utiliza para establecer el encabezado de columna del artefacto que se almacena en el archivo de recursos
     * de configuración regional. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de
     * la aplicación utiliza el archivo de recursos por defecto para obtener el valor del encabezado.
     *
     * @param locale configuración regional
     * @param localizedColumnHeader sustantivo singular, preferiblemente sin complementos, que se usa como encabezado de columna del artefacto
     */
    @Override
    public void setLocalizedColumnHeader(Locale locale, String localizedColumnHeader) {
        checkScope();
        Locale l = localeWritingKey(locale);
        if (localizedColumnHeader == null) {
            _localizedColumnHeader.remove(l);
        } else {
            _localizedColumnHeader.put(l, localizedColumnHeader);
        }
    }

    /**
     * El método setLocalizedColumnHeader se utiliza para establecer el encabezado de columna del artefacto que se almacena en el archivo de recursos
     * de configuración regional. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de
     * la aplicación utiliza el archivo de recursos por defecto para obtener el valor del encabezado.
     *
     * @param locale configuración regional
     * @param localizedColumnHeader sustantivo singular, preferiblemente sin complementos, que se usa como encabezado de columna del artefacto
     */
    @Override
    public void setLocalizedColumnHeader(Locale locale, String... localizedColumnHeader) {
        checkScope();
        Locale l = localeWritingKey(locale);
        if (localizedColumnHeader == null || localizedColumnHeader.length == 0) {
            _localizedColumnHeader.remove(l);
        } else {
            _localizedColumnHeader.put(l, StringUtils.join(localizedColumnHeader, BR));
        }
    }

    /**
     * @param locale the locale for the collection label
     * @return the localized collection label
     */
    @Override
    public String getLocalizedCollectionLabel(Locale locale) {
        Locale l = localeReadingKey(locale);
        return _localizedCollectionLabel.get(l);
    }

    /**
     * El método setLocalizedCollectionLabel se utiliza para establecer la etiqueta de colección del artefacto que se almacena en el archivo de
     * recursos de configuración regional. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la
     * interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el valor de la etiqueta.
     *
     * @param locale configuración regional
     * @param localizedCollectionLabel sustantivo plural que se usa como etiqueta de colección del artefacto
     */
    @Override
    public void setLocalizedCollectionLabel(Locale locale, String localizedCollectionLabel) {
        checkScope();
        Locale l = localeWritingKey(locale);
        if (localizedCollectionLabel == null) {
            _localizedCollectionLabel.remove(l);
        } else {
            _localizedCollectionLabel.put(l, localizedCollectionLabel);
        }
        setLocalizedCollectionShortLabel(locale, localizedCollectionLabel);
    }

    /**
     * @param locale the locale for the collection short label
     * @return the localized collection short label
     */
    @Override
    public String getLocalizedCollectionShortLabel(Locale locale) {
        Locale l = localeReadingKey(locale);
        return _localizedCollectionShortLabel.get(l);
    }

    /**
     * El método setLocalizedCollectionShortLabel se utiliza para establecer la etiqueta corta de colección del artefacto que se almacena en el
     * archivo de recursos de configuración regional. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté
     * disponible, la interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el valor de la etiqueta.
     *
     * @param locale configuración regional
     * @param localizedCollectionShortLabel sustantivo plural, preferiblemente sin complementos, que se usa como etiqueta corta de colección del
     * artefacto
     */
    @Override
    public void setLocalizedCollectionShortLabel(Locale locale, String localizedCollectionShortLabel) {
        checkScope();
        Locale l = localeWritingKey(locale);
        if (localizedCollectionShortLabel == null) {
            _localizedCollectionShortLabel.remove(l);
        } else {
            _localizedCollectionShortLabel.put(l, localizedCollectionShortLabel);
        }
    }

    /**
     * @param locale the locale for the description
     * @return the localized description
     */
    @Override
    public String getLocalizedDescription(Locale locale) {
        Locale l = localeReadingKey(locale);
        return _localizedDescription.get(l);
    }

    /**
     * El método setLocalizedDescription se utiliza para establecer la descripción del artefacto que se almacena en el archivo de recursos de
     * configuración regional. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la
     * aplicación utiliza el archivo de recursos por defecto para obtener el valor de la descripción.
     *
     * @param locale configuración regional
     * @param localizedDescription una o más oraciones que describen el artefacto
     */
    @Override
    public void setLocalizedDescription(Locale locale, String localizedDescription) {
        checkScope();
        Locale l = localeWritingKey(locale);
        if (localizedDescription == null) {
            _localizedDescription.remove(l);
        } else {
            if (localizedDescription.endsWith("\b")) {
                localizedDescription += NBSP;
            }
            _localizedDescription.put(l, localizedDescription);
        }
//      setLocalizedShortDescription(locale, localizedDescription); do not set short description here to prevent excessive inline help
    }

    /**
     * @param locale the locale for the short description
     * @return the localized short description
     */
    @Override
    public String getLocalizedShortDescription(Locale locale) {
        Locale l = localeReadingKey(locale);
        return _localizedShortDescription.get(l);
    }

    /**
     * El método setLocalizedShortDescription se utiliza para establecer la descripción corta del artefacto que se almacena en el archivo de recursos
     * de configuración regional. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de
     * la aplicación utiliza el archivo de recursos por defecto para obtener el valor de la descripción.
     *
     * @param locale configuración regional
     * @param localizedShortDescription una o más oraciones que describen brevemente el artefacto
     */
    @Override
    public void setLocalizedShortDescription(Locale locale, String localizedShortDescription) {
        checkScope();
        Locale l = localeWritingKey(locale);
        if (localizedShortDescription == null) {
            _localizedShortDescription.remove(l);
        } else {
            _localizedShortDescription.put(l, localizedShortDescription);
        }
    }

    /**
     * @param locale the locale for the tooltip
     * @return the localized tooltip
     */
    @Override
    public String getLocalizedTooltip(Locale locale) {
        Locale l = localeReadingKey(locale);
        return _localizedTooltip.get(l);
    }

    /**
     * El método setLocalizedTooltip se utiliza para establecer la descripción emergente (tooltip) del artefacto que se almacena en el archivo de
     * recursos de configuración regional. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la
     * interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el valor de la descripción.
     *
     * @param locale configuración regional
     * @param localizedTooltip una o más oraciones que describen muy brevemente el artefacto
     */
    @Override
    public void setLocalizedTooltip(Locale locale, String localizedTooltip) {
        checkScope();
        Locale l = localeWritingKey(locale);
        if (localizedTooltip == null) {
            _localizedTooltip.remove(l);
        } else {
            _localizedTooltip.put(l, localizedTooltip);
        }
    }

    /**
     * @param locale the locale for the symbol
     * @return the localized symbol
     */
    @Override
    public String getLocalizedSymbol(Locale locale) {
        Locale l = localeReadingKey(locale);
        return _localizedSymbol.get(l);
    }

    /**
     * El método setLocalizedSymbol se utiliza para establecer el símbolo o unidad del artefacto que se almacena en el archivo de recursos de
     * configuración regional. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la
     * aplicación utiliza el archivo de recursos por defecto para obtener el valor del símbolo.
     *
     * @param locale configuración regional
     * @param localizedSymbol una o más oraciones que describen muy brevemente el artefacto
     */
    @Override
    public void setLocalizedSymbol(Locale locale, String localizedSymbol) {
        checkScope();
        Locale l = localeWritingKey(locale);
        if (localizedSymbol == null) {
            _localizedSymbol.remove(l);
        } else {
            _localizedSymbol.put(l, localizedSymbol);
        }
    }

    public boolean isLocalizedSymbolDefined() {
        return !_localizedSymbol.isEmpty();
    }

    protected Locale localeReadingKey(Locale locale) {
        return locale == null ? Bundle.getLocale() : locale;
    }

    protected Locale localeWritingKey(Locale locale) {
        /*
        Locale l = locale == null ? Bundle.getLocale() : Bundle.isSupportedLocale(locale) ? locale : null;
        if (l == null) {
            throw new IllegalArgumentException("Locale " + locale + " not supported yet.");
        }
        return l;
        **/
        return localeReadingKey(locale);
    }

    protected char settler() {
        return '?';
    }

    /**
     * @return the declaring artifact
     */
    @Override
    public Artifact getDeclaringArtifact() {
        return _declaringArtifact;
    }

    /*
    String getDeclaringArtifactClassName() {
        return _declaringArtifact == null ? null : _declaringArtifact.getClass().getName();
    }

    /**/
    String getDeclaringArtifactClassSimpleName() {
        return _declaringArtifact == null ? null : _declaringArtifact.getClass().getSimpleName();
    }

    /**
     * @param declaringArtifact the declaring artifact to set
     */
    void initDeclaringArtifact(Artifact declaringArtifact) {
        _declaringArtifact = declaringArtifact;
    }

    /**
     * @param declaringArtifact the declaring artifact to set
     */
    void setDeclaringArtifact(Artifact declaringArtifact) {
        if (_declared) {
            return;
        }
        resetDeclaringArtifact(declaringArtifact);
    }

    void resetDeclaringArtifact(Artifact declaringArtifact) {
        _declaringArtifact = declaringArtifact;
        if (declaringArtifact != null) {
            if (this instanceof Entity) {
                _depth = declaringArtifact.depth() + 1;
                _round = XS1.round(getNamedClass(), declaringArtifact);
            } else {
                _depth = declaringArtifact.depth();
            }
        }
    }

    /**
     * @return the declaring field
     */
    @Override
    public Field getDeclaringField() {
        return _declaringField;
    }

    public String getDeclaringFieldNamedClassSimpleName() {
        Field declaringField = getDeclaringField();
        Class<?> declaringFieldClass = declaringField.getDeclaringClass();
        Class<?> declaringFieldNamedClass = XS1.getNamedClass(declaringFieldClass);
        return declaringFieldNamedClass.getSimpleName();
    }

    /**
     * @param declaringField the declaring field to set
     */
    void setDeclaringField(Field declaringField) {
        if (_declared) {
            return;
        }
        resetDeclaringField(declaringField);
    }

    void resetDeclaringField(Field declaringField) {
        _declaringField = declaringField;
    }

    /**
     * @return the declaring field index
     */
    @Override
    public int getDeclaringFieldIndex() {
        return _declaringFieldIndex;
    }

    /**
     * @param declaringFieldIndex the declaring field index to set
     */
    void setDeclaringFieldIndex(int declaringFieldIndex) {
        if (_declared) {
            return;
        }
        _declaringFieldIndex = declaringFieldIndex;
    }

    /**
     * @return the declaring entity if the artifact directly declared by one, null otherwise
     */
    @Override
    public Entity getDeclaringEntity() {
        return _declaringArtifact instanceof Entity ? (Entity) _declaringArtifact : null;
    }

    /**
     * @return the declaring entity if the artifact directly declared by one, null otherwise
     */
    @Override
    public PersistentEntity getDeclaringPersistentEntity() {
        Entity declaringEntity = getDeclaringEntity();
        return declaringEntity instanceof PersistentEntity ? (PersistentEntity) declaringEntity : null;
    }

    /**
     * @return the declaring entity if the artifact directly declared by one, null otherwise
     */
    @Override
    public Entity getDeclaringEntityRoot() {
        Entity declaringEntity = getDeclaringEntity();
        return declaringEntity == null ? null : declaringEntity.getRoot();
    }

    /**
     * @return the declaring entity if the artifact directly declared by one, null otherwise
     */
    @Override
    public PersistentEntity getDeclaringPersistentEntityRoot() {
        Entity declaringEntityRoot = getDeclaringEntityRoot();
        return declaringEntityRoot instanceof PersistentEntity ? (PersistentEntity) declaringEntityRoot : null;
    }

    /**
     * @return the declaring entity if the artifact directly declared by one, null otherwise
     */
    @Override
    public Entity getDeclaringFieldEntityRoot() {
        Class<?> declaringClass = _declaringField == null ? null : _declaringField.getDeclaringClass();
        Entity declaringEntity = declaringClass == null ? null : getDeclaringEntity();
        return declaringEntity == null ? null : declaringEntity.getDeclaringProject().getEntity(declaringClass);
    }

    /**
     * @return the declaring entity if the artifact directly declared by one, null otherwise
     */
    @Override
    public PersistentEntity getDeclaringFieldPersistentEntityRoot() {
        Entity declaringFieldEntityRoot = getDeclaringFieldEntityRoot();
        return declaringFieldEntityRoot instanceof PersistentEntity ? (PersistentEntity) declaringFieldEntityRoot : null;
    }

    /**
     * @return the declaring entity if the artifact directly declared by one, null otherwise
     */
    @Override
    public PersistentEntity getDeclaringFieldPersistentEntityTableRoot() {
        PersistentEntity dfper = getDeclaringFieldPersistentEntityRoot();
        return dfper == null ? null : dfper.isTable() ? dfper : dfper.getBaseTableRoot();
    }

    /**
     * @return the declaring operation if the artifact directly declared by one, null otherwise
     */
    @Override
    public Operation getDeclaringOperation() {
        return _declaringArtifact instanceof Operation ? (Operation) _declaringArtifact : null;
    }

    /**
     * @return the depth
     */
    @Override
    public int depth() {
        return _depth;
    }

    /**
     * @return the round
     */
    @Override
    public int round() {
        return _round;
    }

    /**
     * @return the annotations map
     */
    Map<Class<? extends Annotation>, Field> getAnnotations() {
        return _annotations;
    }

    @Override
    public void clearAttributes() {
        _attributes.clear();
    }

    @Override
    public void addAttributes() {
    }

    /**
     * @return the attributes map
     */
    public Map<String, Object> getAttributes() {
        return _attributes;
    }

    public Set<String> getAttributesKeySetByRegex(String regex) {
        Set<String> newSet = new TreeSet<>();
        Set<String> keySet = _attributes.keySet();
        for (String string : keySet) {
            if (string.matches(regex)) {
                newSet.add(string);
            }
        }
        return newSet;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="annotate">
    private boolean _annotated;

    @Override
    public boolean isAnnotated() {
        return _annotated;
    }

    void initializeAnnotations() {
    }

//  @Override
    public void annotate() {
        if (_annotated) {
            return;
        }
        _annotated = true;
        initializeAnnotations();
        annotate(getNamedClass());
        annotate(getDeclaringField());
    }

    void annotate(Class<?> type) {
        if (type == null) {
            return;
        }
        Annotation[] annotations = type.getAnnotations();
        List<Class<? extends Annotation>> valid = getValidTypeAnnotations();
        checkAnnotations(type, null, annotations, valid);
    }

    void annotate(Field field) {
        if (field == null) {
            return;
        }
        boolean check = depth() == 0 || !isProperty();
        if (check) {
            Object clazz = null;
            if (depth() == 0 && isParameter()) {
                Entity e = getDeclaringArtifact().getDeclaringEntity();
                if (e != null) {
                    clazz = e.getClass();
                }
            }
            Annotation[] annotations = field.getAnnotations();
            List<Class<? extends Annotation>> valid = getValidFieldAnnotations();
            checkAnnotations(field, clazz, annotations, valid);
        }
    }

    private void checkAnnotations(Object obj1, Object obj2, Annotation[] annotations, List<Class<? extends Annotation>> valid) {
        for (Annotation annotation : annotations) {
            Class<? extends Annotation> annotationType = annotation.annotationType();
            if (valid.contains(annotationType)) {
                continue;
            }
            if (annotationType.getPackage().getName().startsWith("javax.xml.bind.annotation")) {
                continue;
            }
            String pattern = "@{0} {1} {2}" + (obj2 == null ? "" : " referenced by {3}");
            String refusal = annotationType.isAnnotationPresent(Deprecated.class) ? "is deprecated and no longer valid for" : "is not valid for";
            String message = MessageFormat.format(pattern, annotationType.getSimpleName(), refusal, obj1, obj2);
            Project.logParserMessage(Level.ERROR, message);
            Project.increaseParserErrorCount();
        }
    }

    protected boolean isValidTypeAnnotation(Class<? extends Annotation> annotation) {
        return getValidTypeAnnotations().contains(annotation);
    }

    protected List<Class<? extends Annotation>> getValidTypeAnnotations() {
        return new ArrayList<>();
    }

    protected boolean isValidFieldAnnotation(Class<? extends Annotation> annotation) {
        return getValidFieldAnnotations().contains(annotation);
    }

    protected List<Class<? extends Annotation>> getValidFieldAnnotations() {
        return new ArrayList<>();
    }

    @Override
    public Field put(Class<? extends Annotation> annotation, Field field) {
        XS1.checkAccess();
        return _annotations.put(annotation, field);
    }
    // </editor-fold>

    public AbstractArtifact() {
        init();
        add();
    }

    private void init() {
        _defaultLocale = defaultLocale();
        setStaticAttributes();
        copyBootstrappingAttributes();
    }

    protected void setStaticAttributes() {
//      setBootstrappingFileName("bootstrapping.properties");
    }

    private boolean _checkScope;

    protected void copyBootstrappingAttributes() {
        _checkScope = Bootstrapping.checkReferencesToEnclosingEntityMembersWithinEnclosedOperations;
    }

    private void add() {
        Project project = TLC.getProject();
        if (project != null) {
            project.addArtifact(this);
        }
    }

    protected Locale defaultLocale() {
        return null;
    }

    /**
     * El método addAttribute permite agregar un atributo a la lista de atributos extraordinarios del artefacto. Los atributos extraordinarios son
     * parejas clave/valor, de modo que si se agregan varios atributos con la misma clave a un artefacto, el valor de tal atributo será el último
     * valor agregado.
     *
     * @param clazz clase a la que corresponde el atributo
     * @param name clave del atributo
     * @param value valor del atributo
     * @return el valor anterior asociado con la clave, o nulo si no había una asignación para la clave, o si la implementación admite valores nulos.
     */
    @Override
    public Object addAttribute(Class<?> clazz, String name, KVP value) {
        return addAttribute(attributeName(clazz, name), value);
    }

    /**
     * El método addAttribute permite agregar un atributo a la lista de atributos extraordinarios del artefacto. Los atributos extraordinarios son
     * parejas clave/valor, de modo que si se agregan varios atributos con la misma clave a un artefacto, el valor de tal atributo será el último
     * valor agregado.
     *
     * @param name clave del atributo
     * @param value valor del atributo
     * @return el valor anterior asociado con la clave, o nulo si no había una asignación para la clave, o si la implementación admite valores nulos.
     */
    @Override
    public Object addAttribute(String name, KVP value) {
        return value == null ? _attributes.remove(name) : putKeyValuePairAttribute(name, value);
    }

    /**
     * El método addAttribute permite agregar un atributo a la lista de atributos extraordinarios del artefacto. Los atributos extraordinarios son
     * parejas clave/valor, de modo que si se agregan varios atributos con la misma clave a un artefacto, el valor de tal atributo será el último
     * valor agregado.
     *
     * @param clazz clase a la que corresponde el atributo
     * @param name clave del atributo
     * @param value valor del atributo
     * @return el valor anterior asociado con la clave, o nulo si no había una asignación para la clave, o si la implementación admite valores nulos.
     */
    @Override
    public Object addAttribute(Class<?> clazz, String name, KVP... value) {
        return addAttribute(attributeName(clazz, name), value);
    }

    /**
     * El método addAttribute permite agregar un atributo a la lista de atributos extraordinarios del artefacto. Los atributos extraordinarios son
     * parejas clave/valor, de modo que si se agregan varios atributos con la misma clave a un artefacto, el valor de tal atributo será el último
     * valor agregado.
     *
     * @param name clave del atributo
     * @param value valor del atributo
     * @return el valor anterior asociado con la clave, o nulo si no había una asignación para la clave, o si la implementación admite valores nulos.
     */
    @Override
    public Object addAttribute(String name, KVP... value) {
        return value == null ? _attributes.remove(name) : putKeyValuePairAttribute(name, value);
    }

    /**
     * El método addAttribute permite agregar un atributo a la lista de atributos extraordinarios del artefacto. Los atributos extraordinarios son
     * parejas clave/valor, de modo que si se agregan varios atributos con la misma clave a un artefacto, el valor de tal atributo será el último
     * valor agregado.
     *
     * @param clazz clase a la que corresponde el atributo
     * @param name clave del atributo
     * @param value valor del atributo
     * @return el valor anterior asociado con la clave, o nulo si no había una asignación para la clave, o si la implementación admite valores nulos.
     */
    @Override
    public Object addAttribute(Class<?> clazz, String name, Object value) {
        return addAttribute(attributeName(clazz, name), value);
    }

    /**
     * El método addAttribute permite agregar un atributo a la lista de atributos extraordinarios del artefacto. Los atributos extraordinarios son
     * parejas clave/valor, de modo que si se agregan varios atributos con la misma clave a un artefacto, el valor de tal atributo será el último
     * valor agregado.
     *
     * @param name clave del atributo
     * @param value valor del atributo
     * @return el valor anterior asociado con la clave, o nulo si no había una asignación para la clave, o si la implementación admite valores nulos.
     */
    @Override
    public Object addAttribute(String name, Object value) {
        return value == null ? _attributes.remove(name) : _attributes.put(name, value);
    }

    /**
     * El método addAttribute permite agregar un atributo a la lista de atributos extraordinarios del artefacto. Los atributos extraordinarios son
     * parejas clave/valor, de modo que si se agregan varios atributos con la misma clave a un artefacto, el valor de tal atributo será el último
     * valor agregado.
     *
     * @param clazz clase a la que corresponde el atributo
     * @param name clave del atributo
     * @param value valor del atributo
     * @return el valor anterior asociado con la clave, o nulo si no había una asignación para la clave, o si la implementación admite valores nulos.
     */
    @Override
    public Object addAttribute(Class<?> clazz, String name, Object... value) {
        return addAttribute(attributeName(clazz, name), value);
    }

    /**
     * El método addAttribute permite agregar un atributo a la lista de atributos extraordinarios del artefacto. Los atributos extraordinarios son
     * parejas clave/valor, de modo que si se agregan varios atributos con la misma clave a un artefacto, el valor de tal atributo será el último
     * valor agregado.
     *
     * @param name clave del atributo
     * @param value valor del atributo
     * @return el valor anterior asociado con la clave, o nulo si no había una asignación para la clave, o si la implementación admite valores nulos.
     */
    @Override
    public Object addAttribute(String name, Object... value) {
        return value == null ? _attributes.remove(name) : _attributes.put(name, value);
    }

    private Object putKeyValuePairAttribute(String name, KVP value) {
        Object oldValue = _attributes.get(name);
        Object newValue = oldValue == null ? value : oldValue instanceof KVP ? value.toArray(oldValue)
            : KVP.isArray(oldValue) ? ArrUtils.addAll(value.toArray(), KVP.array(oldValue))
            : value;
        /**/
        return _attributes.put(name, newValue);
    }

    private Object putKeyValuePairAttribute(String name, KVP... value) {
        Object oldValue = _attributes.get(name);
        Object newValue = oldValue == null ? value : ArrUtils.addAll(value, KVP.array(oldValue));
        return _attributes.put(name, newValue);
    }

    @Override
    public Object getAttribute(Class<?> clazz, String name) {
        return getAttribute(attributeName(clazz, name));
    }

    @Override
    public Object getAttribute(String name) {
        return _attributes.get(name);
    }

    public Boolean getBooleanAttribute(Class<?> clazz, String name) {
        return getBooleanAttribute(attributeName(clazz, name));
    }

    public Boolean getBooleanAttribute(String name) {
        Object attribute = _attributes.get(name);
        return ObjUtils.toBoolean(attribute);
    }

    public Integer getIntegerAttribute(Class<?> clazz, String name) {
        return getIntegerAttribute(attributeName(clazz, name));
    }

    public Integer getIntegerAttribute(String name) {
        Object attribute = _attributes.get(name);
        return ObjUtils.toInteger(attribute);
    }

    public Integer getIntegerAttribute(Class<?> clazz, String name, Integer min, Integer max) {
        return getIntegerAttribute(attributeName(clazz, name), min, max);
    }

    public Integer getIntegerAttribute(String name, Integer min, Integer max) {
        Object attribute = _attributes.get(name);
        Integer integer = ObjUtils.toInteger(attribute);
        return ObjUtils.between(integer, min, max) ? integer : null;
    }

    public String getStringAttribute(Class<?> clazz, String name) {
        return getStringAttribute(attributeName(clazz, name));
    }

    public String getStringAttribute(String name) {
        return getStringAttribute(name, KVP.EQUALS, KVP.SEPARATOR, KVP.OPEN, KVP.CLOSE);
    }

    public String getStringAttribute(Class<?> clazz, String name, String equals, String separator, String open, String close) {
        return getStringAttribute(attributeName(clazz, name), equals, separator, open, close);
    }

    public String getStringAttribute(String name, String equals, String separator, String open, String close) {
        return StrUtils.getString(equals, separator, open, close, _attributes.get(name));
    }

    public String getStringKeyValuePairAttribute(Class<?> clazz, String name, String key) {
        return getStringKeyValuePairAttribute(clazz, name, key, null);
    }

    public String getStringKeyValuePairAttribute(Class<?> clazz, String name, String key, Object defaultValue) {
        Object object = getKeyValuePairAttribute(attributeName(clazz, name), key, defaultValue);
        return object == null ? null : StringUtils.trimToNull(object.toString());
    }

    public Object getKeyValuePairAttribute(Class<?> clazz, String name, String key) {
        return getKeyValuePairAttribute(attributeName(clazz, name), key);
    }

    public Object getKeyValuePairAttribute(String name, String key) {
        return getKeyValuePairAttribute(name, key, null);
    }

    public Object getKeyValuePairAttribute(Class<?> clazz, String name, String key, Object defaultValue) {
        return getKeyValuePairAttribute(attributeName(clazz, name), key, defaultValue);
    }

    public Object getKeyValuePairAttribute(String name, String key, Object defaultValue) {
        if (name != null && key != null) {
            KVP[] array = KVP.array(getAttributesArray(name));
            if (array != null && array.length > 0) {
                for (KVP kvp : array) {
                    if (key.equals(kvp.getKey())) {
                        Object value = kvp.getValue();
                        if (value != null) {
                            return value;
                        }
                    }
                }
            }
        }
        return defaultValue;
    }

    public Object getAttributesArray(Class<?> clazz, String name) {
        return getAttributesArray(attributeName(clazz, name));
    }

    public Object getAttributesArray(String name) {
        Object value = _attributes.get(name);
        return value == null ? null : value.getClass().isArray() ? value : ArrUtils.arrayOf(value);
    }

    private String attributeName(Class<?> clazz, String name) {
        return (clazz != null ? clazz : getNamedClass()).getSimpleName() + ":" + name;
    }

    /**
     * @return the class path
     */
    @Override
    public String getClassPath() {
        return classPath(this);
    }

    private String classPath(Artifact a) {
        Artifact da = a.getDeclaringArtifact();
        String str1 = a.getName();
        String str2 = XS1.getNamedClass(a).getSimpleName();
        String str3 = str1 == null || str1.equals(str2) ? str2 : str2 + "[" + str1 + "]";
        return da == null ? str3 : classPath(da) + "." + str3;
    }

    /**
     * @param type class to test
     * @return true if type is present in the class path
     */
    @Override
    public boolean isClassInPath(Class<?> type) {
        return isClassInPath(type, this);
    }

    private boolean isClassInPath(Class<?> clazz, Artifact artifact) {
        if (clazz != null) {
            if (artifact != null) {
                if (clazz.isAssignableFrom(XS1.getNamedClass(artifact))) {
                    return true;
                }
                Artifact declaringArtifact = artifact.getDeclaringArtifact();
                if (declaringArtifact != null) {
                    return isClassInPath(clazz, declaringArtifact);
                }
            }
        }
        return false;
    }

    /**
     * @return the path
     */
    @Override
    @SuppressWarnings("unchecked") // unchecked conversion
    public List<Artifact> getPathList() {
        List list = new ArrayList<>();
        addToPathList(list, this);
        return list;
    }

    private void addToPathList(List<Artifact> list, Artifact artifact) {
        Artifact declaringArtifact = artifact.getDeclaringArtifact();
        if (declaringArtifact != null) {
            addToPathList(list, declaringArtifact);
        }
        list.add(artifact);
    }

    /**
     * @return the path string
     */
    @Override
    public String getPathString() {
        return path(this);
    }

    private String path(Artifact a) {
        String p;
        if (a.getDeclaringField() == null || a.getDeclaringArtifact() == null) {
//          p = "_" + a.getAlias();
            p = XS1.getNamedClass(a).getSimpleName() + "." + "this";
        } else {
            p = path(a.getDeclaringArtifact()) + "." + a.getDeclaringField().getName();
            if (a.getDeclaringField().getType().isArray()) {
                p += "[" + a.getDeclaringFieldIndex() + "]";
            }
            p = StringUtils.removeStart(p, ".");
        }
        return p;
    }

    /**
     * @return the full name
     */
    @Override
    public String getFullName() {
        return StringUtils.remove(getPathString(), "." + "this");
    }

    /**
     * @return the partial name
     */
    @Override
    public String getPartialName() {
        return StringUtils.substringAfter(getPathString(), "." + "this" + ".");
    }

    protected String getValueString(Object value) {
        if (value == null) {
            return null;
        } else if (value instanceof Artifact artifact) {
            return getValueString(value, artifact.getPathString());
        } else {
//          return getValueString(value, value);
            return value.toString();
        }
    }

    protected String getValueString(Object object, Object value) {
        return XS1.getNamedClass(object).getSimpleName() + "(" + value.toString() + ")";
    }

    /**
     * @return the java class file name
     */
    String getJavaFileName() {
        return getNamedClass().getCanonicalName().replace('.', '/') + ".java";
    }

    /**
     * @return the named class
     */
    Class<?> getNamedClass() {
        return XS1.getNamedClass(this);
    }

    /**
     * @return true if this artifact is an Operation; otherwise false
     */
    @Override
    public boolean isOperation() {
        return this instanceof Operation;
    }

    private boolean isParameter() {
        return this instanceof DataArtifact && ((DataArtifact) this).isParameter();
    }

    private boolean isProperty() {
        return this instanceof DataArtifact && ((DataArtifact) this).isProperty();
    }

    /**
     * @return true if this artifact is an Expression; otherwise false
     */
    @Override
    public boolean isExpression() {
        return this instanceof Expression;
    }

    protected String fa(String name) {
        return fairGraphicImageName(name);
    }

    String fairGraphicImageName(String name) {
        return isFontAwesomeClass(name) ? name.trim().replaceAll(" +", " ") : name;
    }

    protected boolean isFontAwesomeClass(String name) {
        return name != null && name.matches(Constants.FONT_AWESOME_CLASS_REGEX);
    }

    protected String xs(String name) {
        return fairUnicodeSymbolName(name);
    }

    String fairUnicodeSymbolName(String name) {
        return isUnicodeSymbolClass(name) ? name.trim().replaceAll(" +", " ") : name;
    }

    protected boolean isUnicodeSymbolClass(String name) {
        return name != null && name.matches(Constants.UNICODE_SYMBOL_CLASS_REGEX);
    }

    protected boolean isValidEmbeddedDocument(String document) {
        return document != null && document.matches(Constants.EMBEDDED_DOCUMENT_REGEX);
    }

    protected boolean isValidHelpFileName(String fileName) {
        return fileName != null && fileName.matches(Constants.HELP_FILE_NAME_REGEX);
    }

    protected boolean isValidHelpFileType(String fileName) {
        String fileType = StringUtils.substringAfterLast(fileName, ".");
        return StringUtils.isNotBlank(fileType) && ArrayUtils.contains(Project.getHelpFileTypes(), fileType);
    }

    protected boolean isValidSnippetFileName(String fileName) {
        return fileName != null && fileName.matches(Constants.SNIPPET_FILE_NAME_REGEX);
    }

    protected boolean isValidJavaClassName(String className) {
        return className != null && className.matches(Constants.JAVA_CLASS_NAME_REGEX);
    }

    protected boolean verifyExpression(Expression expression) {
        return verifyExpression(expression, (Artifact) null);
    }

    protected boolean verifyExpression(Expression expression, Artifact artifact) {
        return verifyExpression(expression, artifact, true);
    }

    protected boolean verifyExpression(Expression expression, ExpressionUsage usage) {
        return verifyExpression(expression, (Artifact) null, usage);
    }

    protected boolean verifyExpression(Expression expression, Artifact artifact, ExpressionUsage usage) {
        return verifyExpression(expression, artifact, usage, true);
    }

    protected boolean verifyExpression(Expression expression, Artifact artifact, boolean calculableless) {
        return verifyExpression(expression, artifact, ExpressionUsage.INDETERMINATE, calculableless);
    }

    protected boolean verifyExpression(Expression expression, Artifact artifact, ExpressionUsage usage, boolean calculableless) {
        boolean ok = true;
        String locator = artifact == null ? "at " + getFullName() : "for " + artifact.getFullName();
        locator += stringOf(usage);
        if (expression == null) {
            String message = "null expression defined " + locator;
            logger.error(message);
            Project.increaseParserErrorCount();
            ok = false;
        } else {
            expression.addVerifiedUsage(usage, artifact);
            String expname = StringUtils.isBlank(expression.getName()) ? "" : expression.getName() + " ";
            String message = "invalid expression " + expname + "defined " + locator;
            if (expression instanceof VariantX) {
                boolean b1 = this instanceof Entity && depth() == 0;
                boolean b2 = this instanceof Operation; // Operation --> depth() == 0
                if (b1 || b2) {
                    message += "; expression is not properly defined";
                    logger.error(message);
                    Project.increaseParserErrorCount();
                    ok = false;
                }
            } else {
                ok &= verifyExpression(this, expression, message, usage, calculableless);
            }
        }
        return ok;
    }

    private boolean verifyExpression(Artifact container, Expression expression, String message, ExpressionUsage usage, boolean calculableless) {
        boolean ok = true;
        if (expression != null && container != null) {
            boolean b1, b2, special, unusual, calculable, calculated;
            Artifact artifact;
            Entity declaringEntity;
            List<Artifact> pathList;
            String fullName;
            Level level;
            String text1, text2;
            Object[] operands = expression.getOperands();
            if (operands != null && operands.length > 0) {
                for (Object operand : operands) {
                    if (operand instanceof DataArtifact) {
                        artifact = (Artifact) operand;
                        fullName = artifact.getFullName();
                        pathList = artifact.getPathList();
                        if (pathList.contains(container)) {
                            b1 = container instanceof Entity && container.depth() == 0;
                            b2 = container instanceof Operation;
                            if (b1 || b2) {
                                declaringEntity = b1 ? (Entity) container : b2 ? ((Operation) container).getDeclaringEntity() : null;
                                special = declaringEntity != null && declaringEntity.isSpecialExpressionsWarningsEnabled();
                                unusual = declaringEntity != null && declaringEntity.isUnusualExpressionsWarningsEnabled();
                                calculable = calculableProperty(artifact);
                                calculated = calculatedProperty(artifact);
                                if (calculated) {
                                    if (calculableless || b2) {
                                        level = Level.ERROR;
                                        text1 = message;
                                        text2 = "; operand " + fullName + " is a " + (calculable ? "calculable" : "calculated") + " property";
                                    } else if (b1 && !artifact.getDeclaringEntity().equals(container)) {
                                        if (calculable) {
                                            level = Level.ERROR;
                                            text1 = message;
                                            text2 = "; operand " + fullName + " is a foreign calculable property";
                                        } else if (ExpressionUsage.SEARCH_QUERY_FILTER.equals(usage)) {
                                            level = special ? Project.getSpecialExpressionLevel() : null;
                                            text1 = message.replace("invalid", "special");
                                            text2 = "; operand " + fullName + " is a calculated foreign property";
                                        } else {
                                            level = unusual ? Project.getUnusualExpressionLevel() : null;
                                            text1 = message.replace("invalid", "unusual");
                                            text2 = "; operand " + fullName + " is a calculated foreign property";
                                        }
                                    } else {
                                        level = null;
                                        text1 = null;
                                        text2 = null;
                                    }
                                    if (LogUtils.fair(level)) {
                                        logger.log(level, text1 + text2);
                                        if (Level.ERROR.equals(level) || Level.FATAL.equals(level)) {
                                            Project.increaseParserErrorCount();
                                            ok = false;
                                        } else if (Level.WARN.equals(level)) {
                                            Project.increaseParserWarningCount();
                                        }
                                    }
                                }
                            }
                        } else {
                            logger.error(message + "; operand " + fullName + " is out of scope");
                            Project.increaseParserErrorCount();
                            ok = false;
                        }
                    } else if (operand instanceof VariantX) {
                        artifact = (Artifact) operand;
                        fullName = artifact.getFullName();
                        logger.error(message + "; operand " + fullName + " is out of scope");
                        Project.increaseParserErrorCount();
                        ok = false;
                    } else if (operand instanceof Expression operandExpression) {
                        ok &= verifyExpression(container, operandExpression, message, usage, calculableless);
                    }
                }
            }
        }
        return ok;
    }

    private boolean calculableProperty(Artifact artifact) {
        if (artifact instanceof DataArtifact dataArtifact) {
            if (dataArtifact.isProperty()) {
                return ((Property) artifact).isCalculable();
            }
        }
        return false;
    }

    boolean calculatedProperty(Artifact artifact) {
        if (artifact instanceof DataArtifact dataArtifact) {
            if (dataArtifact.isProperty()) {
                return ((Property) artifact).isCalculable() || calculatedProperty(artifact.getDeclaringArtifact());
            }
        }
        return false;
    }

    private String stringOf(ExpressionUsage usage) {
        return usage == null || usage.equals(ExpressionUsage.INDETERMINATE) ? "" : " (" + StrUtils.getWordyString(usage.toString()) + ")";
    }

//  protected void verifyNames(Class<?> top) {
//      verifyNames(top, Artifact.class);
//  }
//
    protected void verifyNames(Class<?> top, Class<?> clazz) {
        String message;
        String fieldName, artifactName;
        Object object;
        boolean expression = Expression.class.equals(clazz);
//      Class<?> fieldType;
        Class<?> dac = getClass();
        for (Field field : XS1.getFields(dac, top, clazz)) {
            field.setAccessible(true);
            fieldName = field.getName();
//          fieldType = field.getType();
            if (isNotRestricted(field)) { // && isAssignableFrom(clazz, fieldType)
                try {
                    object = field.get(this);
                    if (object instanceof AbstractArtifact artifact) {
                        artifactName = artifact.getName();
                        if (artifactName == null) {
                            artifact.setName(fieldName);
                        }
                        if (expression && _checkScope) { // since 19/02/2024
                            // expressions are not initialized with XS1.initialiseField
                            // therefore its declaring artifact and declaring field are not set yet
                            // and must be set here because they are required at checkScope
                            if (artifact.getDeclaringArtifact() == null) {
                                artifact.setDeclaringArtifact(this);
                            }
                            if (artifact.getDeclaringField() == null) {
                                artifact.setDeclaringField(field);
                            }
                        }
                    }
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    message = "failed to get field \"" + field + "\" at " + this;
                    logger.error(message, ThrowableUtils.getCause(ex));
                    Project.increaseParserErrorCount();
                }
            }
        }
    }

    /*
    private boolean isAssignableFrom(Class<?> clazz, Class<?> type) {
        if (Expression.class.isAssignableFrom(type)) {
            if (Parameter.class.isAssignableFrom(type) || Property.class.isAssignableFrom(type)) {
                return false;
            }
        }
        return clazz.isAssignableFrom(type);
    }

    /**/
    private boolean isNotRestricted(Field field) {
        int modifiers = field.getModifiers();
        return !(Modifier.isPrivate(modifiers) || Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers));
    }

    @SuppressWarnings("unchecked")
    protected <T> T coalesce(T... objects) {
        for (T object : objects) {
            if (object != null) {
                return object;
            }
        }
        return null;
    }

    protected int greaterThanZero(int... values) {
        return values == null || values.length == 0 ? 0 : gt0(values);
    }

    private int gt0(int... values) {
        for (int i : values) {
            if (i > 0) {
                return i;
            }
        }
        return 0;
    }

    private static final String UNSPECIFIED = "UNSPECIFIED";

    @SuppressWarnings("unchecked")
    protected <E extends Enum> E specified(E... enums) {
        return specified(UNSPECIFIED, enums);
    }

    @SuppressWarnings("unchecked")
    protected <E extends Enum> E specified(String unspecified, E... values) {
        E e = null;
        if (values != null && values.length > 0) {
            String u = StringUtils.defaultIfBlank(unspecified, UNSPECIFIED);
            for (E v : values) {
                if (v != null) {
                    if (u.equals(v.name())) {
                        e = v;
                        continue;
                    }
                    return v;
                }
            }
        }
        return e;
    }

    protected String specified(String... strings) {
        if (strings != null && strings.length > 0) {
            for (String string : strings) {
                if (StringUtils.isNotBlank(string)) {
                    return string;
                }
            }
        }
        return "";
    }

    protected String[] specified(String[] strings, String[] defaultStrings) {
        return notBlank(strings) ? strings : notBlank(defaultStrings) ? defaultStrings : null;
    }

    private boolean notBlank(String[] strings) {
        if (strings != null && strings.length > 0) {
            for (String string : strings) {
                if (StringUtils.isNotBlank(string)) {
                    return true;
                }
            }
        }
        return false;
    }

    protected <T> T[] specified(T[] values, T[] defaultValues) {
        return notEmpty(values) ? values : notEmpty(defaultValues) ? defaultValues : null;
    }

    private <T> boolean notEmpty(T[] values) {
        if (values != null && values.length > 0) {
            for (T value : values) {
                if (value != null) {
                    return true;
                }
            }
        }
        return false;
    }

    protected int[] specified(int[] a1, int[] a2) {
        int[] a3 = new int[a1.length > a2.length ? a1.length : a2.length];
        int[] c1 = Arrays.copyOf(a1, a3.length);
        int[] c2 = Arrays.copyOf(a2, a3.length);
        for (int i = 0; i < a3.length; i++) {
            a3[i] = c1[i] > 0 ? c1[i] : c2[i];
        }
        return a3;
    }

    protected Integer specified(Integer... values) {
        if (values != null && values.length > 0) {
            for (Integer v : values) {
                if (v != null && v >= 0) {
                    return v;
                }
            }
        }
        return -1;
    }

    protected Character specified(Character... values) {
        if (values != null && values.length > 0) {
            for (Character v : values) {
                if (v != null && v != ' ') {
                    return v;
                }
            }
        }
        return ' ';
    }

    /**
     * the wrapper
     */
    private Wrapper _wrapper;

    /**
     * @return the wrapper
     */
    @Override
    public Wrapper getWrapper() {
        if (_wrapper == null) {
            Class<?> thisClass = getClass();
            Class<? extends Wrapper> wrapperClass = getWrapperClass(thisClass);
            if (wrapperClass != null) {
                Class<?> parameterType = XS1.getConstructorParameterType(wrapperClass, thisClass);
                if (parameterType != null) {
                    String pattern = "failed to wrap {0} using {1}({2})";
                    String message = MessageFormat.format(pattern, thisClass, wrapperClass, parameterType);
                    try {
                        _wrapper = wrapperClass.getConstructor(parameterType).newInstance(this);
                    } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InstantiationException | InvocationTargetException ex) {
                        throw new RuntimeException(message, ex);
                    }
                } else {
                    String pattern = "{1} is not a valid wrapper for {0}";
                    String message = MessageFormat.format(pattern, thisClass, wrapperClass);
                    throw new RuntimeException(message);
                }
            }
        }
        return _wrapper;
    }

    @SuppressWarnings("unchecked") // unchecked cast
    private Class<? extends Wrapper> getWrapperClass(Class<?> clazz) {
        if (clazz != null && Wrappable.class.isAssignableFrom(clazz)) {
            Class<? extends Wrappable> wrappableClass = (Class<? extends Wrappable>) clazz; // unchecked cast
            Class<? extends Wrapper> wrapperClass = TLB.getWrapperClass(wrappableClass);
            return wrapperClass != null ? wrapperClass : getWrapperClass(clazz.getSuperclass());
        }
        return getDefaultWrapperClass();
    }

    /**
     * @return the default wrapper class
     */
    @Override
    public Class<? extends ArtifactWrapper> getDefaultWrapperClass() {
        return ArtifactWrapper.class;
    }

    boolean sameDataTypeSimpleName(TypedArtifact x, TypedArtifact y) {
        return x != null && y != null
            && x.getDataType() != null && y.getDataType() != null
            && x.getDataType().getSimpleName().equals(y.getDataType().getSimpleName());
    }

    protected void checkScope() { // since 19/02/2024
        if (Bootstrapping.checkReferencesToEnclosingEntityMembersWithinEnclosedOperations) {
            checkScope1();
        }
    }

    /**
     * check references to enclosing entity members within enclosed operations
     */
    private void checkScope1() { // since 19/02/2024
        Entity declaringEntity = getDeclaringEntity();
        if (declaringEntity == null || depth() > 1) {
            return;
        }
        for (Artifact artifact : getPathList()) {
            if (artifact instanceof Operation) {
                return;
            }
        }
        String method;
        String metodo = null;
        Class<?> callerClass, enclosingClass;
        final String[] prefixes = {"adalid.core.AbstractArtifact.checkScope", "java.", "jdk."};
        final StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        for (StackTraceElement element : stack) {
            method = element.getClassName() + "." + element.getMethodName();
            if (StringUtils.startsWithAny(method, prefixes)) {
                continue;
            }
            method += " (" + element.getFileName() + ":" + element.getLineNumber() + ") ";
            if (metodo == null) {
                metodo = method;
            } else {
                callerClass = XS1.forName(element.getClassName());
                if (callerClass == null) {
                    Project.increaseParserErrorCount();
                    logger.error("class " + element.getClassName() + " not found");
                    break;
                }
                if (Artifact.class.isAssignableFrom(callerClass)) {
                    if (Operation.class.isAssignableFrom(callerClass)) {
                        enclosingClass = callerClass.getEnclosingClass();
                        if (enclosingClass != null && Entity.class.isAssignableFrom(enclosingClass)) {
                            Project.increaseParserErrorCount();
                            logger.error("illegal reference to " + getFullName());
                            logger.error("     at " + method);
                        }
                        break;
                    }
                } else {
                    break;
                }
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="MarkupUtils">
    protected static final String BR = MarkupUtils.BR;

    protected static final String HT = MarkupUtils.HT;

    protected static final String FWGTS = MarkupUtils.FWGTS;

    protected static final String FWLTS = MarkupUtils.FWLTS;

    protected static final String _de_ = " de ";

    protected static final String _of_ = " of ";

    /**
     * This method builds an HTML {@code<a>} pseudo-tag, which defines a hyperlink.
     *
     * @param href Specifies the URL of the page the link goes to
     * @param text string to display instead of the URL
     * @return an HTML {@code<a>} pseudo-tag
     */
    protected String a(String href, String text) {
        return MarkupUtils.a(href, text);
    }

    /**
     * This method builds an HTML {@code<b>} pseudo-tag, which defines bold text without any extra importance.
     *
     * @param string text to display in bold
     * @return an HTML {@code<b>} pseudo-tag
     */
    protected String b(String string) {
        return MarkupUtils.b(string);
    }

    /**
     * This method builds several HTML {@code<b>} pseudo-tags, separated by <code>separator</code>.
     *
     * @param separator a text to separate the pseudo-tags
     * @param strings one or more texts to display in bold
     * @return several HTML {@code<b>} pseudo-tags, separated by <code>separator</code>.
     */
    protected String b(String separator, String... strings) {
        return MarkupUtils.b(separator, strings);
    }

    /**
     * This method builds an HTML {@code<i>} pseudo-tag for a CSS class.
     *
     * @param clazz Specifies the CSS class
     * @return an HTML {@code<i>} pseudo-tag
     */
    protected static String ic(String clazz) {
        return MarkupUtils.ic(clazz);
    }

    /**
     * This method builds an HTML {@code<i>} pseudo-tag, which defines text with a CSS class.
     *
     * @param clazz Specifies the CSS class
     * @param text string to display
     * @return an HTML {@code<i>} pseudo-tag
     */
    protected static String ic(String clazz, String text) {
        return MarkupUtils.ic(clazz, text);
    }

    /**
     * This method builds an HTML {@code<i>} pseudo-tag, which defines italic text.
     *
     * @param string text to display in italic
     * @return an HTML {@code<i>} pseudo-tag
     */
    protected String i(String string) {
        return MarkupUtils.i(string);
    }

    /**
     * This method builds several HTML {@code<i>} pseudo-tags, separated by <code>separator</code>.
     *
     * @param separator a text to separate the pseudo-tags
     * @param strings one or more texts to display in italic
     * @return several HTML {@code<i>} pseudo-tags, separated by <code>separator</code>.
     */
    protected String i(String separator, String... strings) {
        return MarkupUtils.i(separator, strings);
    }

    /**
     * This method builds an HTML {@code<mark>} pseudo-tag, which defines text that should be marked or highlighted.
     *
     * @param string text to be marked or highlighted
     * @return an HTML {@code<mark>} pseudo-tag
     */
    protected String m(String string) {
        return MarkupUtils.m(string);
    }

    /**
     * This method builds several HTML {@code<mark>} pseudo-tags, separated by <code>separator</code>.
     *
     * @param separator a text to separate the pseudo-tags
     * @param strings one or more texts to be marked or highlighted
     * @return several HTML {@code<mark>} pseudo-tags, separated by <code>separator</code>.
     */
    protected String m(String separator, String... strings) {
        return MarkupUtils.m(separator, strings);
    }

    /**
     * This method builds an HTML {@code<del>} pseudo-tag, which defines text that should be struck out.
     *
     * @param string text to be struck out
     * @return an HTML {@code<del>} pseudo-tag
     */
    protected String s(String string) {
        return MarkupUtils.s(string);
    }

    /**
     * This method builds several HTML {@code<del>} pseudo-tags, separated by <code>separator</code>.
     *
     * @param separator a text to separate the pseudo-tags
     * @param strings one or more texts to be struck out
     * @return several HTML {@code<del>} pseudo-tags, separated by <code>separator</code>.
     */
    protected String s(String separator, String... strings) {
        return MarkupUtils.s(separator, strings);
    }

    /**
     * This method builds an HTML {@code<ins>} pseudo-tag, which defines text that should be underlined.
     *
     * @param string text to be underlined
     * @return an HTML {@code<ins>} pseudo-tag
     */
    protected String u(String string) {
        return MarkupUtils.u(string);
    }

    /**
     * This method builds several HTML {@code<ins>} pseudo-tags, separated by <code>separator</code>.
     *
     * @param separator a text to separate the pseudo-tags
     * @param strings one or more texts to be underlined
     * @return several HTML {@code<ins>} pseudo-tags, separated by <code>separator</code>.
     */
    protected String u(String separator, String... strings) {
        return MarkupUtils.u(separator, strings);
    }

    /**
     * This method builds an {@code HTML <ul> pseudo-tag}, which defines an unordered (bulleted) list.
     *
     * @param strings one or more texts defining the list items
     * @return an {@code HTML <ul> pseudo-tag}
     */
    protected String ul(String... strings) {
        return MarkupUtils.ul(strings);
    }

    /**
     * This method builds an HTML {@code<h1>} pseudo-tag, which defines a level 1 heading.
     *
     * @param string header text
     * @return an HTML {@code<h1>} pseudo-tag
     */
    protected static String h1(String string) {
        return MarkupUtils.h1(string);
    }

    /**
     * This method builds an HTML {@code<h2>} pseudo-tag, which defines a level 2 heading.
     *
     * @param string header text
     * @return an HTML {@code<h2>} pseudo-tag
     */
    protected static String h2(String string) {
        return MarkupUtils.h2(string);
    }

    /**
     * This method builds an HTML {@code<h3>} pseudo-tag, which defines a level 3 heading.
     *
     * @param string header text
     * @return an HTML {@code<h3>} pseudo-tag
     */
    protected static String h3(String string) {
        return MarkupUtils.h3(string);
    }

    /**
     * This method builds an HTML {@code<h4>} pseudo-tag, which defines a level 4 heading.
     *
     * @param string header text
     * @return an HTML {@code<h4>} pseudo-tag
     */
    protected static String h4(String string) {
        return MarkupUtils.h4(string);
    }

    /**
     * This method builds an HTML {@code<h5>} pseudo-tag, which defines a level 5 heading.
     *
     * @param string header text
     * @return an HTML {@code<h5>} pseudo-tag
     */
    protected static String h5(String string) {
        return MarkupUtils.h5(string);
    }

    /**
     * This method builds an HTML {@code<h6>} pseudo-tag, which defines a level 6 heading.
     *
     * @param string header text
     * @return an HTML {@code<h6>} pseudo-tag
     */
    protected static String h6(String string) {
        return MarkupUtils.h6(string);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="big*">
    protected static BigDecimal bigDecimal(String value) {
        return new BigDecimal(value);
    }

    protected static BigInteger bigInteger(String value) {
        return new BigInteger(value);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="toString">
    @Override
    public String toString() {
        String simpleName = getNamedClass().getSimpleName();
        return toString(simpleName);
    }

    protected String toString(String simpleName) {
        return simpleName + (_name == null ? "@" + hashCodeHexString() : _name.equals(simpleName) ? "" : "[" + _name + "]");
    }

    @Override
    public String hashCodeHexString() {
        return StringUtils.leftPad(Integer.toHexString(hashCode()), 8, '0');
    }

    @Override
    public String toString(int n) {
        return toString(n, null);
    }

    @Override
    public String toString(int n, String key) {
        return toString(n, key, Project.isVerbose());
    }

    @Override
    public String toString(int n, String key, boolean verbose) {
        return toString(n, key, verbose, verbose, verbose);
    }

    @Override
    public String toString(int n, String key, boolean verbose, boolean fields, boolean maps) {
        String r4n = StringUtils.repeat(" ", 4 * n);
        String tab = verbose ? StringUtils.repeat(" ", 4) : "";
        String fee = verbose ? StringUtils.repeat(tab, n) : "";
//      String faa = " = ";
//      String foo = verbose ? EOL : "";
        String eol = verbose ? EOL : ", ";
        String string = EOL;
        String c = classToString(n, key, verbose);
        String f = fieldsToString(n, key, verbose, fields, maps);
        String m = mapsToString(n, key, verbose, fields, maps);
        string += c;
        string += " " + "{" + " " + this + eol;
        string += f;
        string += m;
        if (!verbose) {
            string = StringUtils.removeEnd(string, eol);
            if (c.contains(EOL) || f.contains(EOL) || m.contains(EOL)) {
                fee = r4n;
            }
        }
        string += fee + "}" + EOL;
        return string.replaceAll(EOL + EOL, EOL);
    }

    protected String classToString(int n, String key, boolean verbose) {
        String tab = StringUtils.repeat(" ", 4);
        String fee = StringUtils.repeat(tab, n);
//      String faa = " = ";
//      String foo = EOL;
        String string = "";
        String s1 = getDeclaringField() == null ? "" : getDeclaringField().getType().getSimpleName();
        String s2 = getNamedClass().getSimpleName();
        String s3 = StringUtils.isBlank(s1) || s1.equals(s2) ? s2 : StringUtils.removeEnd(s1, "[]") + " " + "(" + s2 + ")";
        String s4 = StringUtils.isBlank(key) ? "" : " " + key;
//      String s5 = "@" + Integer.toHexString(hashCode());
//      string += fee + s3 + s4 + s5;
        string += fee + s3 + s4;
        return string;
    }

    protected String fieldsToString(int n, String key, boolean verbose, boolean fields, boolean maps) {
        String tab = verbose ? StringUtils.repeat(" ", 4) : "";
        String fee = verbose ? StringUtils.repeat(tab, n) : "";
        String faa = " = ";
        String foo = verbose ? EOL : ", ";
        String string = "";
        if (fields || verbose) {
            if (verbose) {
                if (_declaringArtifact != null) {
                    string += fee + tab + "declaringArtifact" + faa + _declaringArtifact + foo;
                    if (_declaringField != null) {
                        string += fee + tab + "declaringField" + faa + _declaringField + foo;
                        if (_declaringField.getType().isArray()) {
                            string += fee + tab + "declaringFieldIndex" + faa + _declaringFieldIndex + foo;
                        }
                    }
                    string += fee + tab + "path" + faa + getPathString() + foo;
                }
                if (this instanceof Entity) {
                    string += fee + tab + "depth" + faa + _depth + foo;
                    string += fee + tab + "round" + faa + _round + foo;
                }
                if (_name != null) {
                    string += fee + tab + "name" + faa + _name + foo;
                }
                if (_alias != null) {
                    string += fee + tab + "alias" + faa + _alias + foo;
                }
            } else {
                string += fee + tab + "path" + faa + getPathString() + foo;
            }

        }
        return string;
    }

    protected String mapsToString(int n, String key, boolean verbose, boolean fields, boolean maps) {
        return "";
    }
    // </editor-fold>

}
