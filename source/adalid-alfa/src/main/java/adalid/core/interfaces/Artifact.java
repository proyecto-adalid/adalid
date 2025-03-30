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
package adalid.core.interfaces;

import adalid.commons.util.*;
import adalid.core.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Locale;

/**
 * @author Jorge Campins
 */
public interface Artifact {

    /**
     * @return true if the artifact is annotated
     */
    boolean isAnnotated();

    /**
     * @return true if the artifact is declared
     */
    boolean isDeclared();

    /**
     * @return true if the artifact is not declared
     */
    boolean isNotDeclared();

    /**
     * @return true if the artifact is inherited
     */
    boolean isInherited();

    /**
     * @return true if the artifact is not inherited
     */
    boolean isNotInherited();

    /**
     * @return true if the artifact is inherited from an abstract class
     */
    boolean isInheritedFromAbstract();

    /**
     * @return true if the artifact is not inherited from an abstract class
     */
    boolean isNotInheritedFromAbstract();

    /**
     * @return true if the artifact is inherited from a concrete class
     */
    boolean isInheritedFromConcrete();

    /**
     * @return true if the artifact is not inherited from a concrete class
     */
    boolean isNotInheritedFromConcrete();

    /**
     * @return the name
     */
    String getName();

    /**
     * @return the alias
     */
    String getAlias();

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
    void setAlias(String alias);

    /**
     * @return the SQL name
     */
    String getSqlName();

    /**
     * El método setSqlName se utiliza para establecer el nombre SQL del artefacto. Si este método no es ejecutado, el nombre SQL se determina a
     * partir del nombre del artefacto, sustituyendo cada letra mayúscula por un guion bajo (underscore) seguido de la letra convertida en minúscula.
     *
     * @param sqlName nombre SQL del artefacto
     */
    void setSqlName(String sqlName);

    /**
     * @return the default locale
     */
    Locale getDefaultLocale();

    /**
     * @return the default label
     */
    String getDefaultLabel();

    /**
     * El método setDefaultLabel se utiliza para establecer la etiqueta del artefacto que se almacena en el archivo de recursos por defecto. En caso
     * de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la aplicación utiliza el archivo de
     * recursos por defecto para obtener el valor de la etiqueta.
     *
     * @param defaultLabel sustantivo singular que se usa como etiqueta del artefacto
     */
    void setDefaultLabel(String defaultLabel);

    /**
     * @return the default short label
     */
    String getDefaultShortLabel();

    /**
     * El método setDefaultShortLabel se utiliza para establecer la etiqueta corta del artefacto que se almacena en el archivo de recursos por
     * defecto. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la aplicación
     * utiliza el archivo de recursos por defecto para obtener el valor de la etiqueta.
     *
     * @param defaultShortLabel sustantivo singular, preferiblemente sin complementos, que se usa como etiqueta corta del artefacto
     */
    void setDefaultShortLabel(String defaultShortLabel);

    /**
     * El método setDefaultShortLabel se utiliza para establecer la etiqueta corta del artefacto que se almacena en el archivo de recursos por
     * defecto. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la aplicación
     * utiliza el archivo de recursos por defecto para obtener el valor de la etiqueta.
     *
     * @param defaultShortLabel sustantivo singular, preferiblemente sin complementos, que se usa como etiqueta corta del artefacto
     */
    void setDefaultShortLabel(String... defaultShortLabel);

    /**
     * @return the default column header
     */
    String getDefaultColumnHeader();

    /**
     * El método setDefaultColumnHeader se utiliza para establecer el encabezado de columna del artefacto que se almacena en el archivo de recursos
     * por defecto. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la aplicación
     * utiliza el archivo de recursos por defecto para obtener el valor de la etiqueta.
     *
     * @param defaultColumnHeader sustantivo singular, preferiblemente sin complementos, que se usa como encabezado de columna del artefacto
     */
    void setDefaultColumnHeader(String defaultColumnHeader);

    /**
     * El método setDefaultColumnHeader se utiliza para establecer el encabezado de columna del artefacto que se almacena en el archivo de recursos
     * por defecto. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la aplicación
     * utiliza el archivo de recursos por defecto para obtener el valor de la etiqueta.
     *
     * @param defaultColumnHeader sustantivo singular, preferiblemente sin complementos, que se usa como encabezado de columna del artefacto
     */
    void setDefaultColumnHeader(String... defaultColumnHeader);

    /**
     * @return the default collection label
     */
    String getDefaultCollectionLabel();

    /**
     * El método setDefaultCollectionLabel se utiliza para establecer la etiqueta de colección del artefacto que se almacena en el archivo de recursos
     * por defecto. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la aplicación
     * utiliza el archivo de recursos por defecto para obtener el valor de la etiqueta.
     *
     * @param defaultCollectionLabel sustantivo plural que se usa como etiqueta de colección del artefacto
     */
    void setDefaultCollectionLabel(String defaultCollectionLabel);

    /**
     * @return the default collection short label
     */
    String getDefaultCollectionShortLabel();

    /**
     * El método setDefaultCollectionShortLabel se utiliza para establecer la etiqueta corta de colección del artefacto que se almacena en el archivo
     * de recursos por defecto. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la
     * aplicación utiliza el archivo de recursos por defecto para obtener el valor de la etiqueta.
     *
     * @param defaultCollectionShortLabel sustantivo plural, preferiblemente sin complementos, que se usa como etiqueta corta de colección del
     * artefacto
     */
    void setDefaultCollectionShortLabel(String defaultCollectionShortLabel);

    /**
     * @return the default description
     */
    String getDefaultDescription();

    /**
     * El método setDefaultDescription se utiliza para establecer la descripción del artefacto que se almacena en el archivo de recursos por defecto.
     * En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la aplicación utiliza el
     * archivo de recursos por defecto para obtener el valor de la descripción.
     *
     * @param defaultDescription una o más oraciones que describen el artefacto
     */
    void setDefaultDescription(String defaultDescription);

    /**
     * @return the default short description
     */
    String getDefaultShortDescription();

    /**
     * El método setDefaultShortDescription se utiliza para establecer la descripción corta del artefacto que se almacena en el archivo de recursos
     * por defecto. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la aplicación
     * utiliza el archivo de recursos por defecto para obtener el valor de la descripción.
     *
     * @param defaultShortDescription una o más oraciones que describen brevemente el artefacto
     */
    void setDefaultShortDescription(String defaultShortDescription);

    /**
     * @return the default tooltip
     */
    String getDefaultTooltip();

    /**
     * El método setDefaultTooltip se utiliza para establecer la descripción emergente (tooltip) del artefacto que se almacena en el archivo de
     * recursos por defecto. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la
     * aplicación utiliza el archivo de recursos por defecto para obtener el valor de la descripción.
     *
     * @param defaultTooltip una o más oraciones que describen muy brevemente el artefacto
     */
    void setDefaultTooltip(String defaultTooltip);

    /**
     * @return the default symbol
     */
    String getDefaultSymbol();

    /**
     * El método setDefaultSymbol se utiliza para establecer el símbolo o unidad del artefacto que se almacena en el archivo de recursos por defecto.
     * En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la aplicación utiliza el
     * archivo de recursos por defecto para obtener el valor del símbolo.
     *
     * @param defaultSymbol una o más oraciones que describen muy brevemente el artefacto
     */
    void setDefaultSymbol(String defaultSymbol);

    /**
     * @param locale locale
     * @return the localized label
     */
    String getLocalizedLabel(Locale locale);

    /**
     * El método setLocalizedLabel se utiliza para establecer la etiqueta del artefacto que se almacena en el archivo de recursos de configuración
     * regional. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la aplicación
     * utiliza el archivo de recursos por defecto para obtener el valor de la etiqueta.
     *
     * @param locale configuración regional
     * @param localizedLabel sustantivo singular que se usa como etiqueta del artefacto
     */
    void setLocalizedLabel(Locale locale, String localizedLabel);

    /**
     * @param locale locale
     * @return the localized short label
     */
    String getLocalizedShortLabel(Locale locale);

    /**
     * El método setLocalizedShortLabel se utiliza para establecer la etiqueta corta del artefacto que se almacena en el archivo de recursos de
     * configuración regional. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la
     * aplicación utiliza el archivo de recursos por defecto para obtener el valor de la etiqueta.
     *
     * @param locale configuración regional
     * @param localizedShortLabel sustantivo singular, preferiblemente sin complementos, que se usa como etiqueta corta del artefacto
     */
    void setLocalizedShortLabel(Locale locale, String localizedShortLabel);

    /**
     * El método setLocalizedShortLabel se utiliza para establecer la etiqueta corta del artefacto que se almacena en el archivo de recursos de
     * configuración regional. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la
     * aplicación utiliza el archivo de recursos por defecto para obtener el valor de la etiqueta.
     *
     * @param locale configuración regional
     * @param localizedShortLabel sustantivo singular, preferiblemente sin complementos, que se usa como etiqueta corta del artefacto
     */
    void setLocalizedShortLabel(Locale locale, String... localizedShortLabel);

    /**
     * @param locale locale
     * @return the localized column header
     */
    String getLocalizedColumnHeader(Locale locale);

    /**
     * El método setLocalizedColumnHeader se utiliza para establecer el encabezado de columna del artefacto que se almacena en el archivo de recursos
     * de configuración regional. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de
     * la aplicación utiliza el archivo de recursos por defecto para obtener el valor de la etiqueta.
     *
     * @param locale configuración regional
     * @param localizedColumnHeader sustantivo singular, preferiblemente sin complementos, que se usa como encabezado de columna del artefacto
     */
    void setLocalizedColumnHeader(Locale locale, String localizedColumnHeader);

    /**
     * El método setLocalizedColumnHeader se utiliza para establecer el encabezado de columna del artefacto que se almacena en el archivo de recursos
     * de configuración regional. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de
     * la aplicación utiliza el archivo de recursos por defecto para obtener el valor de la etiqueta.
     *
     * @param locale configuración regional
     * @param localizedColumnHeader sustantivo singular, preferiblemente sin complementos, que se usa como encabezado de columna del artefacto
     */
    void setLocalizedColumnHeader(Locale locale, String... localizedColumnHeader);

    /**
     * @param locale locale
     * @return the localized collection label
     */
    String getLocalizedCollectionLabel(Locale locale);

    /**
     * El método setLocalizedCollectionLabel se utiliza para establecer la etiqueta de colección del artefacto que se almacena en el archivo de
     * recursos de configuración regional. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la
     * interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el valor de la etiqueta.
     *
     * @param locale configuración regional
     * @param localizedCollectionLabel sustantivo plural que se usa como etiqueta de colección del artefacto
     */
    void setLocalizedCollectionLabel(Locale locale, String localizedCollectionLabel);

    /**
     * @param locale locale
     * @return the localized collection short label
     */
    String getLocalizedCollectionShortLabel(Locale locale);

    /**
     * El método setLocalizedCollectionShortLabel se utiliza para establecer la etiqueta corta de colección del artefacto que se almacena en el
     * archivo de recursos de configuración regional. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté
     * disponible, la interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el valor de la etiqueta.
     *
     * @param locale configuración regional
     * @param localizedCollectionShortLabel sustantivo plural, preferiblemente sin complementos, que se usa como etiqueta corta de colección del
     * artefacto
     */
    void setLocalizedCollectionShortLabel(Locale locale, String localizedCollectionShortLabel);

    /**
     * @param locale locale
     * @return the localized description
     */
    String getLocalizedDescription(Locale locale);

    /**
     * El método setLocalizedDescription se utiliza para establecer la descripción del artefacto que se almacena en el archivo de recursos de
     * configuración regional. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la
     * aplicación utiliza el archivo de recursos por defecto para obtener el valor de la descripción.
     *
     * @param locale configuración regional
     * @param localizedDescription una o más oraciones que describen el artefacto
     */
    void setLocalizedDescription(Locale locale, String localizedDescription);

    /**
     * @param locale locale
     * @return the localized short description
     */
    String getLocalizedShortDescription(Locale locale);

    /**
     * El método setLocalizedShortDescription se utiliza para establecer la descripción corta del artefacto que se almacena en el archivo de recursos
     * de configuración regional. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de
     * la aplicación utiliza el archivo de recursos por defecto para obtener el valor de la descripción.
     *
     * @param locale configuración regional
     * @param localizedShortDescription una o más oraciones que describen brevemente el artefacto
     */
    void setLocalizedShortDescription(Locale locale, String localizedShortDescription);

    /**
     * @param locale locale
     * @return the localized tooltip
     */
    String getLocalizedTooltip(Locale locale);

    /**
     * El método setLocalizedTooltip se utiliza para establecer la descripción emergente (tooltip) del artefacto que se almacena en el archivo de
     * recursos de configuración regional. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la
     * interfaz de la aplicación utiliza el archivo de recursos por defecto para obtener el valor de la descripción.
     *
     * @param locale configuración regional
     * @param localizedTooltip una o más oraciones que describen muy brevemente el artefacto
     */
    void setLocalizedTooltip(Locale locale, String localizedTooltip);

    /**
     * @param locale locale
     * @return the localized symbol
     */
    String getLocalizedSymbol(Locale locale);

    /**
     * El método setLocalizedSymbol se utiliza para establecer el símbolo o unidad del artefacto que se almacena en el archivo de recursos de
     * configuración regional. En caso de que el archivo de recursos para el idioma seleccionado por el usuario no esté disponible, la interfaz de la
     * aplicación utiliza el archivo de recursos por defecto para obtener el valor del símbolo.
     *
     * @param locale configuración regional
     * @param localizedSymbol una o más oraciones que describen muy brevemente el artefacto
     */
    void setLocalizedSymbol(Locale locale, String localizedSymbol);

    /**
     * @return the declaring artifact
     */
    Artifact getDeclaringArtifact();

    /**
     * @return the declaring field
     */
    Field getDeclaringField();

    /**
     * @return the declaring field index
     */
    int getDeclaringFieldIndex();

    /**
     * @return the declaring entity if the artifact directly declared by one, null otherwise
     */
    Entity getDeclaringEntity();

    /**
     * @return the declaring entity if the artifact directly declared by one, null otherwise
     */
    Entity getDeclaringEntityRoot();

    /**
     * @return the declaring entity if the artifact directly declared by one, null otherwise
     */
    PersistentEntity getDeclaringPersistentEntity();

    /**
     * @return the declaring entity if the artifact directly declared by one, null otherwise
     */
    PersistentEntity getDeclaringPersistentEntityRoot();

    /**
     * @return the declaring entity if the artifact directly declared by one, null otherwise
     */
    Entity getDeclaringFieldEntityRoot();

    /**
     * @return the declaring entity if the artifact directly declared by one, null otherwise
     */
    PersistentEntity getDeclaringFieldPersistentEntityRoot();

    /**
     * @return the declaring entity if the artifact directly declared by one, null otherwise
     */
    PersistentEntity getDeclaringFieldPersistentEntityTableRoot();

    /**
     * @return the declaring operation if the artifact directly declared by one, null otherwise
     */
    Operation getDeclaringOperation();

    /**
     * @return the depth
     */
    int depth();

    /**
     * @return the round
     */
    int round();

    /**
     *
     * @param annotation annotation
     * @param field field
     * @return field
     */
    Field put(Class<? extends Annotation> annotation, Field field);

    /**
     *
     */
    void clearAttributes();

    /**
     *
     */
    void addAttributes();

    /**
     *
     * @param clazz class
     * @param name name
     * @param value value
     * @return object
     */
    Object addAttribute(Class<?> clazz, String name, KVP value);

    /**
     *
     * @param name name
     * @param value value
     * @return object
     */
    Object addAttribute(String name, KVP value);

    /**
     *
     * @param clazz class
     * @param name name
     * @param value value
     * @return object
     */
    Object addAttribute(Class<?> clazz, String name, KVP... value);

    /**
     *
     * @param name name
     * @param value value
     * @return object
     */
    Object addAttribute(String name, KVP... value);

    /**
     *
     * @param clazz class
     * @param name name
     * @param value value
     * @return object
     */
    Object addAttribute(Class<?> clazz, String name, Object value);

    /**
     *
     * @param name name
     * @param value value
     * @return object
     */
    Object addAttribute(String name, Object value);

    /**
     *
     * @param clazz class
     * @param name name
     * @param value value
     * @return object
     */
    Object addAttribute(Class<?> clazz, String name, Object... value);

    /**
     *
     * @param name name
     * @param value value
     * @return object
     */
    Object addAttribute(String name, Object... value);

    /**
     *
     * @param clazz class
     * @param name name
     * @return object
     */
    Object getAttribute(Class<?> clazz, String name);

    /**
     *
     * @param name name
     * @return object
     */
    Object getAttribute(String name);

    /**
     * @return the class path
     */
    String getClassPath();

    /**
     * @param type class to test
     * @return true if type is present in the class path
     */
    boolean isClassInPath(Class<?> type);

    /**
     * @return the path
     */
    List<Artifact> getPathList();

    /**
     * @return the path string
     */
    String getPathString();

    /**
     * @return the full name
     */
    String getFullName();

    /**
     * @return the partial name
     */
    String getPartialName();

    /**
     * @param prefix prefijo
     * @param suffix sufijo
     * @return the cryptic name
     */
    String getCrypticName(String prefix, String suffix);

    /**
     * @param prefix prefijo
     * @param suffix sufijo
     * @return the key features
     */
    String getKeyFeatures(String prefix, String suffix);

    /**
     * @return true if this artifact is an Operation; otherwise false
     */
    boolean isOperation();

    /**
     * @return true if this artifact is an Expression; otherwise false
     */
    boolean isExpression();

    /**
     * @return true if this artifact is already finalised; otherwise false
     */
    boolean isFinalised();

    /**
     * dot the i's and cross the t's
     *
     * @return true if this artifact was successfully finalised; otherwise false
     */
    boolean finalise();

    /**
     * @return true if this artifact is already finished; otherwise false
     */
    boolean isFinished();

    /**
     * dot the i's and cross the t's
     *
     * @return true if this artifact was successfully finished; otherwise false
     */
    boolean finish();

    /**
     * @return the hex string representation of the hash code
     */
    String hashCodeHexString();

    /**
     * @param n n
     * @return the string representation of the data
     */
    String toString(int n);

    /**
     * @param n n
     * @param key key
     * @return the string representation of the data
     */
    String toString(int n, String key);

    /**
     * @param n n
     * @param key key
     * @param verbose verbose
     * @return the string representation of the data
     */
    String toString(int n, String key, boolean verbose);

    /**
     * @param n n
     * @param key key
     * @param verbose verbose
     * @param fields fields
     * @param maps maps
     * @return the string representation of the data
     */
    String toString(int n, String key, boolean verbose, boolean fields, boolean maps);

}
