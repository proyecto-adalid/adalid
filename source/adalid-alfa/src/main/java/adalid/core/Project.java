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
import adalid.commons.enums.*;
import adalid.commons.interfaces.*;
import adalid.commons.properties.*;
import adalid.commons.util.*;
import adalid.commons.velocity.*;
import adalid.core.annotations.*;
import adalid.core.comparators.*;
import adalid.core.enums.*;
import adalid.core.exceptions.*;
import adalid.core.interfaces.*;
import adalid.core.programmers.*;
import adalid.core.properties.*;
import adalid.core.sql.*;
import adalid.core.wrappers.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.MessageFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;
import org.apache.commons.collections.ExtendedProperties;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import static adalid.core.enums.RoleType.*;

/**
 * @author Jorge Campins
 */
public abstract class Project extends AbstractArtifact implements ProjectBuilder, ProjectWriter, Comparable<Project> {

    // <editor-fold defaultstate="collapsed" desc="static fields">
    private static final Logger logger = Logger.getLogger(Project.class);

    private static final String EOL = "\n";

    private static final String TAB = "\t";

    private static int _defaultMaxDepth = 1;

    private static int _defaultMaxRound = 0;

    private static boolean _defaultEntityCodeGenBPL = true;

    private static boolean _defaultEntityCodeGenBWS = false;

    private static boolean _defaultEntityCodeGenDAF = true;

    private static boolean _defaultEntityCodeGenFWS = false;

    private static boolean _defaultEntityCodeGenGUI = true;

    private static boolean _defaultEntityCodeGenSQL = true;

    private static boolean _defaultEntityCodeGenSMC = true;

    private static Boolean _defaultPropertyFieldSerializable;

    private static Boolean _defaultPropertyFieldSerializableIUID;

    private static boolean _acerose = false; // entity data logging

    private static boolean _foliose = false; // entity data logging

    private static boolean _spinose = false; // memory usage logging

    private static boolean _verbose = false;

    private static boolean _warnose = false;

    private static Level _alertLevel = Level.OFF;

    private static Level _detailLevel = Level.OFF;

    private static Level _trackingLevel = Level.OFF;

    private static Level _transitionLevel = Level.OFF; // 20200905 - change Level.ERROR to Level.OFF to instantiate references using field type

    private static Level _specialExpressionLevel = Level.OFF;

    private static Level _unusualExpressionLevel = Level.WARN;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="static fields">
    private static int _maximumStringFieldMaxLength = Constants.MAX_STRING_LENGTH;

    private static int _maximumStringIndexMaxLength = Constants.DEFAULT_STRING_INDEX_MAX_LENGTH;

    private static int _defaultStringFieldMaxLength = Constants.DEFAULT_STRING_FIELD_MAX_LENGTH;

    private static int _defaultStringIndexMaxLength = Constants.DEFAULT_STRING_INDEX_MAX_LENGTH;

    private static int _defaultCharacterKeyMaxLength = Constants.DEFAULT_CHARACTER_KEY_MAX_LENGTH;

    private static int _defaultNamePropertyMaxLength = Constants.DEFAULT_NAME_PROPERTY_MAX_LENGTH;

    private static int _defaultDescriptionPropertyMaxLength = Constants.DEFAULT_DESCRIPTION_PROPERTY_MAX_LENGTH;

    private static int _defaultUrlPropertyMaxLength = Constants.DEFAULT_URL_PROPERTY_MAX_LENGTH;

    private static int _defaultEmbeddedDocumentMaxLength = Constants.DEFAULT_EMBEDDED_DOCUMENT_MAX_LENGTH;

    private static int _defaultFileReferenceMaxLength = Constants.DEFAULT_FILE_REFERENCE_MAX_LENGTH;

    public static final int STRING_FIELD_MAX_LENGTH = -1000032767;

    public static final int STRING_INDEX_MAX_LENGTH = -1000001596;

    public static final int CHARACTER_KEY_MAX_LENGTH = -1000000030;

    public static final int NAME_PROPERTY_MAX_LENGTH = -1000000100;

    public static final int DESCRIPTION_PROPERTY_MAX_LENGTH = Integer.MIN_VALUE;

    public static final int FILE_REFERENCE_MAX_LENGTH = -1000002001;

    public static final int URL_PROPERTY_MAX_LENGTH = -1000002002;

    public static final int EMBEDDED_DOCUMENT_MAX_LENGTH = -1000002003;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="static fields public getters and setters">
    /**
     * El método setBootstrappingFileName se utiliza para establecer el nombre del archivo de configuración inicial del proyecto, en caso de que se
     * deba utilizar un archivo diferente al predeterminado (bootstrapping.properties).
     *
     * @param name nombre del archivo, sin la ruta; el archivo debe estar almacenado en el subdirectorio src/main/resources, donde mismo se encuentra
     * el archivo de configuración inicial predeterminado (bootstrapping.properties).
     */
    public static void setBootstrappingFileName(String name) {
        BootstrappingFile.setName(name);
    }

    /**
     * @return the locale
     */
    public static Locale getLocale() {
        return Bundle.getLocale();
    }

    /**
     * El método setLocale se utiliza para establecer el objeto Locale predeterminado del proyecto (un objeto Locale representa una o región
     * geográfica, política, cultural, etc.). El objeto Locale determina, entre otras cosas, el idioma del archivo de recursos por defecto y el
     * formato predeterminado de fecha y hora. El valor predeterminado de esta propiedad es el Locale predeterminado de la JVM.
     *
     * @param locale objeto Locale
     */
    public static void setLocale(Locale locale) {
        Bundle.setLocale(locale);
    }

    /**
     * El método setDecimalSeparator se utiliza para establecer el separador decimal para un Locale específico. El separador decimal es un carácter
     * usado para indicar la separación entre la parte entera y la parte fraccional de un número decimal.
     *
     * @param locale objeto Locale que representa una o región geográfica, política, cultural, etc.
     * @param separator separador decimal
     */
    public static void setDecimalSeparator(Locale locale, char separator) {
        NumUtils.setDecimalSeparator(locale, separator);
    }

    /**
     * El método setThousandSeparator se utiliza para establecer el separador de millares para un Locale específico. El separador de millares es un
     * carácter situado entre los dígitos de la parte entera de un número para facilitar su lectura, agrupando las cifras de tres en tres.
     *
     * @param locale objeto Locale que representa una o región geográfica, política, cultural, etc.
     * @param separator separador decimal
     */
    public static void setThousandSeparator(Locale locale, char separator) {
        NumUtils.setThousandSeparator(locale, separator);
    }

    /**
     * El método setDateFormat se utiliza para establecer el formato de fecha para un Locale específico. El formato se especifica mediante un patrón
     * de fecha y hora. Para obtener información sobre patrones de fecha y hora consulte la documentación de <b>SimpleDateFormat</b>.
     *
     * @param locale objeto Locale que representa una o región geográfica, política, cultural, etc.
     * @param format patrón de fecha y hora
     *
     * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/text/SimpleDateFormat.html">SimpleDateFormat</a>
     */
    public static void setDateFormat(Locale locale, String format) {
        TimeUtils.setDateFormat(locale, format);
    }

    /**
     * El método setTimeFormat se utiliza para establecer el formato de hora para un Locale específico. El formato se especifica mediante un patrón de
     * fecha y hora. Para obtener información sobre patrones de fecha y hora consulte la documentación de <b>SimpleDateFormat</b>.
     *
     * @param locale objeto Locale que representa una o región geográfica, política, cultural, etc.
     * @param format patrón de fecha y hora
     *
     * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/text/SimpleDateFormat.html">SimpleDateFormat</a>
     */
    public static void setTimeFormat(Locale locale, String format) {
        TimeUtils.setTimeFormat(locale, format);
    }

    /**
     * El método setTimestampFormat se utiliza para establecer el formato de fecha y hora para un Locale específico. El formato se especifica mediante
     * un patrón de fecha y hora. Para obtener información sobre patrones de fecha y hora consulte la documentación de <b>SimpleDateFormat</b>.
     *
     * @param locale objeto Locale que representa una o región geográfica, política, cultural, etc.
     * @param format patrón de fecha y hora
     *
     * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/text/SimpleDateFormat.html">SimpleDateFormat</a>
     */
    public static void setTimestampFormat(Locale locale, String format) {
        TimeUtils.setTimestampFormat(locale, format);
    }

    /**
     * @return the maximum string field max length in characters
     */
    public static int getMaximumStringFieldMaxLength() {
        return _maximumStringFieldMaxLength;
    }

    /**
     * El método setMaximumStringFieldMaxLength se utiliza para establecer la cantidad máxima de caracteres de propiedades StringProperty y parámetros
     * StringParameter con longitud limitada.
     *
     * @param maxLength cantidad de caracteres. Su valor debe ser un número entero entre 2.000 y 32.767. El valor predeterminado es 32.767.
     */
    public static void setMaximumStringFieldMaxLength(int maxLength) {
        int min = Constants.DEFAULT_STRING_FIELD_MAX_LENGTH; // 2.000
        int max = Constants.MAX_STRING_LENGTH; // 32.767
        _maximumStringFieldMaxLength = maxLength < min ? min : maxLength > max ? max : maxLength;
        if (_maximumStringFieldMaxLength != maxLength) {
            _maximumStringFieldMaxLength = Constants.MAX_STRING_LENGTH;
            logger.warn(maxLength + " is outside the valid range of maximumStringFieldMaxLength; it was set to " + _maximumStringFieldMaxLength);
        }
    }

    /**
     * @return the maximum string index max length in characters
     */
    public static int getMaximumStringIndexMaxLength() {
        int max = maxStringIndexMaxLength();
        return _maximumStringIndexMaxLength > max ? max : _maximumStringIndexMaxLength;
    }

    /**
     * El método setMaximumStringIndexMaxLength se utiliza para establecer la cantidad máxima de caracteres de propiedades StringProperty que son
     * indexadas y, por lo tanto, con longitud limitada.
     *
     * @param maxLength cantidad de caracteres. Su valor debe ser un número entero entre 1 y el menor entre 6.384 y la cantidad máxima de caracteres
     * de propiedades StringProperty con longitud limitada, que por omisión es 32.767 y puede ser establecida mediante el método
     * setMaximumStringFieldMaxLength. El valor predeterminado es 1.596 (menos de 6398 bytes utilizando el conjunto de caracteres AL32UTF8). El máximo
     * valor que se podría especificar dependerá de la configuración de la base de datos de la aplicación. Tanto en Oracle como en PostgreSQL,
     * dependerá del conjunto de caracteres que utiliza la base de datos. En Oracle, también dependerá del tamaño del bloque del tablespace donde se
     * crea el índice de la propiedad.
     *
     * @see <a href="https://blogs.oracle.com/sql/post/how-to-fix-ora-01450-maximum-key-length-6398-exceeded-errors">How to Fix ORA-01450: Maximum Key
     * Length (6398) Exceeded Errors</a>
     */
    public static void setMaximumStringIndexMaxLength(int maxLength) {
        int max = maxStringIndexMaxLength();
        _maximumStringIndexMaxLength = maxLength < 1 ? 1 : maxLength > max ? max : maxLength;
        if (_maximumStringIndexMaxLength != maxLength) {
            _maximumStringIndexMaxLength = Constants.DEFAULT_STRING_INDEX_MAX_LENGTH;
            logger.warn(maxLength + " is outside the valid range of maximumStringIndexMaxLength; it was set to " + _maximumStringIndexMaxLength);
        }
    }

    private static int maxStringIndexMaxLength() {
        int max = getMaximumStringFieldMaxLength();
        return max > Constants.MAX_STRING_INDEX_LENGTH ? Constants.MAX_STRING_INDEX_LENGTH : max;
    }

    /**
     * @return the default string field max length in characters
     */
    public static int getDefaultStringFieldMaxLength() {
        int max = getMaximumStringFieldMaxLength();
        return _defaultStringFieldMaxLength > max ? max : _defaultStringFieldMaxLength;
    }

    /**
     * El método setDefaultStringFieldMaxLength se utiliza para establecer la cantidad predeterminada de caracteres de propiedades StringProperty y de
     * parámetros StringParameter con longitud limitada.
     *
     * @param maxLength cantidad de caracteres. Su valor debe ser un número entero entre 0 y la cantidad máxima de caracteres de propiedades
     * StringProperty y de parámetros StringParameter con longitud limitada, que por omisión es 32.767 y puede ser establecida mediante el método
     * setMaximumStringFieldMaxLength. El valor predeterminado es 2.000. Especifique 0 para utilizar el máximo permitido por la plataforma, el cual
     * dependerá del conjunto de caracteres que utiliza la base de datos de la aplicación.
     */
    public static void setDefaultStringFieldMaxLength(int maxLength) {
        int max = getMaximumStringFieldMaxLength();
        _defaultStringFieldMaxLength = maxLength < 0 ? 0 : maxLength > max ? max : maxLength;
        if (_defaultStringFieldMaxLength != maxLength) {
            _defaultStringFieldMaxLength = Constants.DEFAULT_STRING_FIELD_MAX_LENGTH;
            logger.warn(maxLength + " is outside the valid range of defaultStringFieldMaxLength; it was set to " + _defaultStringFieldMaxLength);
        }
    }

    /**
     * @return the default string index max length in characters
     */
    public static int getDefaultStringIndexMaxLength() {
        int max = getMaximumStringIndexMaxLength();
        return _defaultStringIndexMaxLength > max ? max : _defaultStringIndexMaxLength;
    }

    /**
     * El método setDefaultStringIndexMaxLength se utiliza para establecer la cantidad predeterminada de caracteres de propiedades StringProperty que
     * son indexadas y, por lo tanto, con longitud limitada.
     *
     * @param maxLength cantidad de caracteres. Su valor debe ser un número entero entre 1 y la cantidad máxima de caracteres de propiedades
     * StringProperty que son indexadas, que por omisión es 6.384 y puede ser establecida mediante el método setMaximumStringIndexMaxLength. El valor
     * predeterminado es 1.596 (menos de 6398 bytes utilizando el conjunto de caracteres AL32UTF8).
     */
    public static void setDefaultStringIndexMaxLength(int maxLength) {
        int max = getMaximumStringIndexMaxLength();
        _defaultStringIndexMaxLength = maxLength < 1 ? 1 : maxLength > max ? max : maxLength;
        if (_defaultStringIndexMaxLength != maxLength) {
            _defaultStringIndexMaxLength = Constants.DEFAULT_STRING_INDEX_MAX_LENGTH;
            logger.warn(maxLength + " is outside the valid range of defaultStringIndexMaxLength; it was set to " + _defaultStringIndexMaxLength);
        }
    }

    /**
     * @return the default character key max length in characters
     */
    public static int getDefaultCharacterKeyMaxLength() {
        int max = getMaximumStringIndexMaxLength();
        return _defaultCharacterKeyMaxLength > max ? max : _defaultCharacterKeyMaxLength;
    }

    /**
     * El método setDefaultCharacterKeyMaxLength se utiliza para establecer la cantidad predeterminada de caracteres de propiedades StringProperty que
     * son la clave de negocio de la entidad y, por lo tanto, indexadas y con longitud limitada.
     *
     * @param maxLength cantidad de caracteres. Su valor debe ser un número entero entre 30 y la cantidad máxima de caracteres de propiedades
     * StringProperty que son indexadas, que por omisión es 1.596 y puede ser establecida mediante el método setMaximumStringIndexMaxLength. El valor
     * predeterminado es 30.
     */
    public static void setDefaultCharacterKeyMaxLength(int maxLength) {
        int min = Constants.DEFAULT_CHARACTER_KEY_MAX_LENGTH; // 30
        int max = getMaximumStringIndexMaxLength();
        _defaultCharacterKeyMaxLength = maxLength < min ? min : maxLength > max ? max : maxLength;
        if (_defaultCharacterKeyMaxLength != maxLength) {
            _defaultCharacterKeyMaxLength = Constants.DEFAULT_CHARACTER_KEY_MAX_LENGTH;
            logger.warn(maxLength + " is outside the valid range of defaultCharacterKeyMaxLength; it was set to " + _defaultCharacterKeyMaxLength);
        }
    }

    /**
     * @return the default name property max length in characters
     */
    public static int getDefaultNamePropertyMaxLength() {
        int max = getMaximumStringIndexMaxLength();
        return _defaultNamePropertyMaxLength > max ? max : _defaultNamePropertyMaxLength;
    }

    /**
     * El método setDefaultNamePropertyMaxLength se utiliza para establecer la cantidad predeterminada de caracteres de propiedades StringProperty que
     * son la propiedad nombre de la entidad y, por lo tanto, indexadas y con longitud limitada.
     *
     * @param maxLength cantidad de caracteres. Su valor debe ser un número entero entre 100 y la cantidad máxima de caracteres de propiedades
     * StringProperty que son indexadas, que por omisión es 1.596 y puede ser establecida mediante el método setMaximumStringIndexMaxLength. El valor
     * predeterminado es 100.
     */
    public static void setDefaultNamePropertyMaxLength(int maxLength) {
        int min = Constants.DEFAULT_NAME_PROPERTY_MAX_LENGTH; // 100
        int max = getMaximumStringIndexMaxLength();
        _defaultNamePropertyMaxLength = maxLength < min ? min : maxLength > max ? max : maxLength;
        if (_defaultNamePropertyMaxLength != maxLength) {
            _defaultNamePropertyMaxLength = Constants.DEFAULT_NAME_PROPERTY_MAX_LENGTH;
            logger.warn(maxLength + " is outside the valid range of defaultNamePropertyMaxLength; it was set to " + _defaultNamePropertyMaxLength);
        }
    }

    /**
     * @return the default description property max length in characters
     */
    public static int getDefaultDescriptionPropertyMaxLength() {
        int max = getMaximumStringFieldMaxLength();
        return _defaultDescriptionPropertyMaxLength > max ? max : _defaultDescriptionPropertyMaxLength;
    }

    /**
     * El método setDefaultDescriptionPropertyMaxLength se utiliza para establecer la cantidad predeterminada de caracteres de propiedades
     * StringProperty que son la propiedad descripción de la entidad.
     *
     * @param maxLength cantidad de caracteres. Su valor debe ser un número entero entre 0 y la cantidad máxima de caracteres de propiedades
     * StringProperty con longitud limitada, que por omisión es 32.767 y puede ser establecida mediante el método setMaximumStringFieldMaxLength. El
     * valor predeterminado es 2.000. Especifique 0 para utilizar el máximo permitido por la plataforma, el cual dependerá del conjunto de caracteres
     * que utiliza la base de datos de la aplicación.
     */
    public static void setDefaultDescriptionPropertyMaxLength(int maxLength) {
        int max = getMaximumStringFieldMaxLength();
        _defaultDescriptionPropertyMaxLength = maxLength < 0 ? 0 : maxLength > max ? max : maxLength;
        if (_defaultDescriptionPropertyMaxLength != maxLength) {
            _defaultDescriptionPropertyMaxLength = Constants.DEFAULT_DESCRIPTION_PROPERTY_MAX_LENGTH;
            logger.warn(maxLength + " is outside the valid range of defaultDescriptionPropertyMaxLength; it was set to " + _defaultDescriptionPropertyMaxLength);
        }
    }

    /**
     * @return the default file reference max length in characters
     */
    public static int getDefaultFileReferenceMaxLength() {
        int max = getMaximumStringFieldMaxLength();
        return _defaultFileReferenceMaxLength > max ? max : _defaultFileReferenceMaxLength;
    }

    /**
     * El método setDefaultFileReferenceMaxLength se utiliza para establecer la cantidad predeterminada de caracteres de propiedades StringProperty y
     * de parámetros StringParameter que hacen referencia a un archivo.
     *
     * @param maxLength cantidad de caracteres. Su valor debe ser un número entero entre 1 y la cantidad máxima de caracteres de propiedades
     * StringProperty con longitud limitada, que por omisión es 32.767 y puede ser establecida mediante el método setMaximumStringFieldMaxLength. El
     * valor predeterminado es 2.000.
     */
    public static void setDefaultFileReferenceMaxLength(int maxLength) {
        int max = getMaximumStringFieldMaxLength();
        _defaultFileReferenceMaxLength = maxLength < 1 ? 1 : maxLength > max ? max : maxLength;
        if (_defaultFileReferenceMaxLength != maxLength) {
            _defaultFileReferenceMaxLength = Constants.DEFAULT_FILE_REFERENCE_MAX_LENGTH;
            logger.warn(maxLength + " is outside the valid range of defaultFileReferenceMaxLength; it was set to " + _defaultFileReferenceMaxLength);
        }
    }

    /**
     * @return the default url property max length in characters
     */
    public static int getDefaultUrlPropertyMaxLength() {
        int max = getMaximumStringFieldMaxLength();
        return _defaultUrlPropertyMaxLength > max ? max : _defaultUrlPropertyMaxLength;
    }

    /**
     * El método setDefaultUrlPropertyMaxLength se utiliza para establecer la cantidad predeterminada de caracteres de propiedades StringProperty que
     * que contienen una URL.
     *
     * @param maxLength cantidad de caracteres. Su valor debe ser un número entero entre 1 y la cantidad máxima de caracteres de propiedades
     * StringProperty con longitud limitada, que por omisión es 32.767 y puede ser establecida mediante el método setMaximumStringFieldMaxLength. El
     * valor predeterminado es 2.000.
     */
    public static void setDefaultUrlPropertyMaxLength(int maxLength) {
        int max = getMaximumStringFieldMaxLength();
        _defaultUrlPropertyMaxLength = maxLength < 1 ? 1 : maxLength > max ? max : maxLength;
        if (_defaultUrlPropertyMaxLength != maxLength) {
            _defaultUrlPropertyMaxLength = Constants.DEFAULT_URL_PROPERTY_MAX_LENGTH;
            logger.warn(maxLength + " is outside the valid range of defaultUrlPropertyMaxLength; it was set to " + _defaultUrlPropertyMaxLength);
        }
    }

    /**
     * @return the default embedded document max length in characters
     */
    public static int getDefaultEmbeddedDocumentMaxLength() {
        int max = getMaximumStringFieldMaxLength();
        return _defaultEmbeddedDocumentMaxLength > max ? max : _defaultEmbeddedDocumentMaxLength;
    }

    /**
     * El método setDefaultEmbeddedDocumentMaxLength se utiliza para establecer la cantidad predeterminada de caracteres de propiedades StringProperty
     * que contienen la definición de un documento incrustado.
     *
     * @param maxLength cantidad de caracteres. Su valor debe ser un número entero entre 1 y la cantidad máxima de caracteres de propiedades
     * StringProperty con longitud limitada, que por omisión es 32.767 y puede ser establecida mediante el método setMaximumStringFieldMaxLength. El
     * valor predeterminado es 2.000.
     */
    public static void setDefaultEmbeddedDocumentMaxLength(int maxLength) {
        int max = getMaximumStringFieldMaxLength();
        _defaultEmbeddedDocumentMaxLength = maxLength < 1 ? 1 : maxLength > max ? max : maxLength;
        if (_defaultEmbeddedDocumentMaxLength != maxLength) {
            _defaultEmbeddedDocumentMaxLength = Constants.DEFAULT_EMBEDDED_DOCUMENT_MAX_LENGTH;
            logger.warn(maxLength + " is outside the valid range of defaultEmbeddedDocumentMaxLength; it was set to " + _defaultEmbeddedDocumentMaxLength);
        }
    }

    /**
     * @return the default max depth
     */
    public static int getDefaultMaxDepth() {
        return _defaultMaxDepth;
    }

    /**
     * @param depth the default max depth to set
     */
    public static void setDefaultMaxDepth(int depth) {
        _defaultMaxDepth = depth < 1 ? 1 : depth;
    }

    /**
     * @return the default max round
     */
    public static int getDefaultMaxRound() {
        return _defaultMaxRound;
    }

    /**
     * @param round the default max round to set
     */
    public static void setDefaultMaxRound(int round) {
        _defaultMaxRound = round < 0 ? 0 : round;
    }

    /**
     * @return the default @EntityCodeGen BPL (Business Process Logic) value
     */
    public static boolean getDefaultEntityCodeGenBPL() {
        return _defaultEntityCodeGenBPL;
    }

    /**
     * El método setDefaultEntityCodeGenBPL se utiliza para establecer el valor predeterminado del atributo bpl de las meta-entidades. El atributo
     * indica si se debe, o no, generar código BPL (Business Process Logic) para la entidad.
     *
     * El método setDefaultEntityCodeGenBPL es un método estático que debe ejecutarse en el método setStaticAttributes del proyecto maestro.
     *
     * @param b valor predeterminado del atributo bpl
     */
    public static void setDefaultEntityCodeGenBPL(boolean b) {
        _defaultEntityCodeGenBPL = b;
    }

    /**
     * @return the default @EntityCodeGen BWS (Business Web Service) value
     */
    public static boolean getDefaultEntityCodeGenBWS() {
        return _defaultEntityCodeGenBWS;
    }

    /**
     * El método setDefaultEntityCodeGenBWS se utiliza para establecer el valor predeterminado del atributo bws de las meta-entidades. El atributo
     * indica si se debe, o no, generar código BWS (Business Web Service) para la entidad.
     *
     * El método setDefaultEntityCodeGenBWS es un método estático que debe ejecutarse en el método setStaticAttributes del proyecto maestro.
     *
     * @param b valor predeterminado del atributo bws
     */
    public static void setDefaultEntityCodeGenBWS(boolean b) {
        _defaultEntityCodeGenBWS = b;
    }

    /**
     * @return the default @EntityCodeGen DAF (Data Access Facade) value
     */
    public static boolean getDefaultEntityCodeGenDAF() {
        return _defaultEntityCodeGenDAF;
    }

    /**
     * El método setDefaultEntityCodeGenDAF se utiliza para establecer el valor predeterminado del atributo daf de las meta-entidades. El atributo
     * indica si se debe, o no, generar una fachada de acceso a datos (DAF, por las siglas en inglés de Data Access Facade) para la entidad.
     *
     * El método setDefaultEntityCodeGenDAF es un método estático que debe ejecutarse en el método setStaticAttributes del proyecto maestro.
     *
     * @param b valor predeterminado del atributo daf
     */
    public static void setDefaultEntityCodeGenDAF(boolean b) {
        _defaultEntityCodeGenDAF = b;
    }

    /**
     * @return the default @EntityCodeGen FWS (Facade Web Service) value
     */
    public static boolean getDefaultEntityCodeGenFWS() {
        return _defaultEntityCodeGenFWS;
    }

    /**
     * El método setDefaultEntityCodeGenFWS se utiliza para establecer el valor predeterminado del atributo fws de las meta-entidades. El atributo
     * indica si se debe, o no, generar código FWS (Facade Web Service) para la entidad.
     *
     * El método setDefaultEntityCodeGenFWS es un método estático que debe ejecutarse en el método setStaticAttributes del proyecto maestro.
     *
     * @param b valor predeterminado del atributo fws
     */
    public static void setDefaultEntityCodeGenFWS(boolean b) {
        _defaultEntityCodeGenFWS = b;
    }

    /**
     * @return the default @EntityCodeGen GUI (Graphical User Interface) value
     */
    public static boolean getDefaultEntityCodeGenGUI() {
        return _defaultEntityCodeGenGUI;
    }

    /**
     * El método setDefaultEntityCodeGenGUI se utiliza para establecer el valor predeterminado del atributo gui de las meta-entidades. El atributo
     * indica si se debe, o no, generar código GUI (Graphical User Interface) para la entidad.
     *
     * El método setDefaultEntityCodeGenGUI es un método estático que debe ejecutarse en el método setStaticAttributes del proyecto maestro.
     *
     * @param b valor predeterminado del atributo gui
     */
    public static void setDefaultEntityCodeGenGUI(boolean b) {
        _defaultEntityCodeGenGUI = b;
    }

    /**
     * @return the default @EntityCodeGen SQL value
     */
    public static boolean getDefaultEntityCodeGenSQL() {
        return _defaultEntityCodeGenSQL;
    }

    /**
     * El método setDefaultEntityCodeGenSQL se utiliza para establecer el valor predeterminado del atributo sql de las meta-entidades. El atributo
     * indica si se debe, o no, generar código SQL (Structured Query Language) para la entidad.
     *
     * El método setDefaultEntityCodeGenSQL es un método estático que debe ejecutarse en el método setStaticAttributes del proyecto maestro.
     *
     * @param b valor predeterminado del atributo sql
     */
    public static void setDefaultEntityCodeGenSQL(boolean b) {
        _defaultEntityCodeGenSQL = b;
    }

    /**
     * @return the default @EntityCodeGen SMC (State Machine Code) value
     */
    public static boolean getDefaultEntityCodeGenSMC() {
        return _defaultEntityCodeGenSMC;
    }

    /**
     * El método setDefaultEntityCodeGenSMC se utiliza para establecer el valor predeterminado del atributo state de las meta-entidades. El atributo
     * indica si se debe, o no, generar código SMC (State Machine Code) para la entidad.
     *
     * El método setDefaultEntityCodeGenSMC es un método estático que debe ejecutarse en el método setStaticAttributes del proyecto maestro.
     *
     * @param b valor predeterminado del atributo state
     */
    public static void setDefaultEntityCodeGenSMC(boolean b) {
        _defaultEntityCodeGenSMC = b;
    }

    /**
     * @return the default @PropertyField.serializable value
     */
    public static Boolean getDefaultPropertyFieldSerializable() {
        return _defaultPropertyFieldSerializable;
    }

    /**
     * El método setDefaultPropertyFieldSerializable se utiliza para establecer el valor predeterminado del elemento serializable de la anotación
     * PropertyField de las meta-propiedades. El atributo indica si la propiedad se debe serializar, o no.
     *
     * El método setDefaultPropertyFieldSerializable es un método estático que debe ejecutarse en el método setStaticAttributes del proyecto maestro.
     *
     * @param b valor predeterminado del atributo serializable
     */
    public static void setDefaultPropertyFieldSerializable(boolean b) {
        _defaultPropertyFieldSerializable = b;
    }

    /**
     * @return the default @PropertyField.serializableIUID value
     */
    public static Boolean getDefaultPropertyFieldSerializableIUID() {
        return _defaultPropertyFieldSerializableIUID;
    }

    /**
     * El método setDefaultPropertyFieldSerializableIUID se utiliza para establecer el valor predeterminado del elemento serializableIUID de la
     * anotación PropertyField de las meta-propiedades. El atributo indica si la propiedad se debe serializar, o no, como un IUID (Item Unique
     * Identification).
     *
     * El método setDefaultPropertyFieldSerializableIUID es un método estático que debe ejecutarse en el método setStaticAttributes del proyecto
     * maestro.
     *
     * @param b valor predeterminado del atributo serializableIUID
     */
    public static void setDefaultPropertyFieldSerializableIUID(boolean b) {
        _defaultPropertyFieldSerializableIUID = b;
    }

    /**
     * @return the acerose
     */
    public static boolean isAcerose() {
        return _acerose;
    }

    /**
     * @param acerose the acerose indicator to set
     */
    public static void setAcerose(boolean acerose) {
        _acerose = acerose;
    }

    /**
     * @return the foliose
     */
    public static boolean isFoliose() {
        return _foliose;
    }

    /**
     * @param foliose the foliose indicator to set
     */
    public static void setFoliose(boolean foliose) {
        _foliose = foliose;
    }

    /**
     * @return the spinose
     */
    public static boolean isSpinose() {
        return _spinose;
    }

    /**
     * @param spinose the spinose indicator to set
     */
    public static void setSpinose(boolean spinose) {
        _spinose = spinose;
    }

    /**
     * @return the verbose
     */
    public static boolean isVerbose() {
        return _verbose;
    }

    /**
     * @param verbose the verbose indicator to set
     */
    public static void setVerbose(boolean verbose) {
        _verbose = verbose;
    }

    /**
     * @return the warnose
     */
    public static boolean isWarnose() {
        return _warnose;
    }

    /**
     * El método setWarnose se utiliza para habilitar o inhabilitar las anotaciones EntityWarnings, de manera que al generar la aplicación se
     * muestren, o no, todas las advertencias de todas las entidades.
     *
     * El método setWarnose es un método estático que debe ejecutarse en el método main del proyecto maestro, antes de ejecutar el método build.
     *
     * @param warnose true para inhabilitar las anotaciones y, en consecuencia, emitir todos los mensajes; y false para habilitar las anotaciones y,
     * en consecuencia, emitir los mensajes según los elementos de cada anotación.
     */
    public static void setWarnose(boolean warnose) {
        _warnose = warnose;
    }

    /**
     * @return the alert messages logging level
     */
    public static Level getAlertLevel() {
        return _alertLevel;
    }

    /**
     * @param level the alert messages logging level to set; WARN will be used if level is null; ERROR and FATAL are downgraded to WARN; OFF disables
     * alert messages logging
     */
    public static void setAlertLevel(Level level) {
        _alertLevel = LogUtils.check(level, Level.OFF, Level.WARN);
    }

    /**
     * @return the detail messages logging level
     */
    public static Level getDetailLevel() {
        return _detailLevel;
    }

    /**
     * @param level the detail messages logging level to set; INFO will be used if level is null; WARN, ERROR and FATAL are downgraded to INFO; OFF
     * disables detail messages logging
     */
    public static void setDetailLevel(Level level) {
        _detailLevel = LogUtils.check(level, Level.OFF, Level.INFO);
    }

    /**
     * @return the tracking messages logging level
     */
    public static Level getTrackingLevel() {
        return _trackingLevel;
    }

    /**
     * @param level the tracking messages logging level to set; TRACE will be used if level is null; WARN, ERROR and FATAL are downgraded to INFO; OFF
     * disables tracking messages logging
     */
    public static void setTrackingLevel(Level level) {
        _trackingLevel = LogUtils.check(level, Level.OFF, Level.INFO);
    }

    /**
     * @return the transition messages logging level
     */
    public static Level getTransitionLevel() {
        return _transitionLevel;
    }

    /**
     * @param level the transition messages logging level to set; ERROR will be used if level is null; OFF disables transition messages logging
     */
    public static void setTransitionLevel(Level level) {
        _transitionLevel = LogUtils.check(level, Level.OFF, Level.FATAL);
    }

    /**
     * @return the special expression messages logging level
     */
    public static Level getSpecialExpressionLevel() {
        return _specialExpressionLevel;
    }

    /**
     * @param level the special expression messages logging level to set; ERROR will be used if level is null; OFF disables special expression
     * messages logging
     */
    public static void setSpecialExpressionLevel(Level level) {
        _specialExpressionLevel = LogUtils.check(level, Level.OFF, Level.WARN);
    }

    /**
     * @return the unusual expression messages logging level
     */
    public static Level getUnusualExpressionLevel() {
        return _unusualExpressionLevel;
    }

    /**
     * @param level the unusual expression messages logging level to set; ERROR will be used if level is null; OFF disables unusual expression
     * messages logging
     */
    public static void setUnusualExpressionLevel(Level level) {
        _unusualExpressionLevel = LogUtils.check(level, Level.OFF, Level.WARN);
    }

    /**
     * @return the alert messages logging level
     */
    public static LoggingLevel getAlertLoggingLevel() {
        return LoggingLevel.getLoggingLevel(_alertLevel);
    }

    /**
     * El método setAlertLoggingLevel se utiliza para establecer el nivel de severidad de los mensajes de alerta que se emiten al generar la
     * aplicación. El valor predeterminado de esta propiedad es OFF (no emitir mensajes de alerta).
     *
     * El método setAlertLoggingLevel es un método estático que debe ejecutarse en el método main del proyecto maestro, antes de ejecutar el método
     * build.
     *
     * @param level elemento de la enumeración LoggingLevel que determina el nivel de severidad de los mensajes de alerta que se emiten al generar la
     * aplicación. Especifique TRACE, DEBUG, INFO o WARN para emitir los mensajes con uno de esos niveles.
     */
    public static void setAlertLoggingLevel(LoggingLevel level) {
        setAlertLevel(level.getLevel());
    }

    /**
     * @return the detail messages logging level
     */
    public static LoggingLevel getDetailLoggingLevel() {
        return LoggingLevel.getLoggingLevel(_detailLevel);
    }

    /**
     * El método setDetailLoggingLevel se utiliza para establecer el nivel de severidad de los mensajes de detalle que se emiten al generar la
     * aplicación. El valor predeterminado de esta propiedad es OFF (no emitir mensajes de detalle).
     *
     * El método setDetailLoggingLevel es un método estático que debe ejecutarse en el método main del proyecto maestro, antes de ejecutar el método
     * build.
     *
     * @param level elemento de la enumeración LoggingLevel que determina el nivel de severidad de los mensajes de detalle que se emiten al generar la
     * aplicación. Especifique TRACE, DEBUG o INFO para emitir los mensajes con uno de esos niveles.
     */
    public static void setDetailLoggingLevel(LoggingLevel level) {
        setDetailLevel(level.getLevel());
    }

    /**
     * @return the tracking messages logging level
     */
    public static LoggingLevel getTrackingLoggingLevel() {
        return LoggingLevel.getLoggingLevel(_trackingLevel);
    }

    /**
     * El método setTrackingLoggingLevel se utiliza para establecer el nivel de severidad de los mensajes de seguimiento que se emiten al generar la
     * aplicación. El valor predeterminado de esta propiedad es OFF (no emitir mensajes de seguimiento).
     *
     * El método setTrackingLoggingLevel es un método estático que debe ejecutarse en el método main del proyecto maestro, antes de ejecutar el método
     * build.
     *
     * @param level elemento de la enumeración LoggingLevel que determina el nivel de severidad de los mensajes de seguimiento que se emiten al
     * generar la aplicación. Especifique TRACE, DEBUG o INFO para emitir los mensajes con uno de esos niveles.
     */
    public static void setTrackingLoggingLevel(LoggingLevel level) {
        setTrackingLevel(level.getLevel());
    }

    /**
     * @return the transition messages logging level
     */
    public static LoggingLevel getTransitionLoggingLevel() {
        return LoggingLevel.getLoggingLevel(_transitionLevel);
    }

    /**
     * El método setTransitionLoggingLevel se utiliza para establecer el nivel de severidad de los mensajes de transición que se emiten al generar la
     * aplicación. El valor predeterminado de esta propiedad es OFF (no emitir mensajes de transición).
     *
     * El método setTransitionLoggingLevel es un método estático que debe ejecutarse en el método main del proyecto maestro, antes de ejecutar el
     * método build.
     *
     * @param level elemento de la enumeración LoggingLevel que determina el nivel de severidad de los mensajes de transición que se emiten al generar
     * la aplicación. Especifique TRACE, DEBUG, INFO, WARN, ERROR o FATAL para emitir los mensajes con uno de esos niveles.
     */
    public static void setTransitionLoggingLevel(LoggingLevel level) {
        setTransitionLevel(level.getLevel());
    }

    /**
     * @return the special expression messages logging level
     */
    public static LoggingLevel getSpecialExpressionLoggingLevel() {
        return LoggingLevel.getLoggingLevel(_specialExpressionLevel);
    }

    /**
     * El método setSpecialExpressionLoggingLevel se utiliza para establecer el nivel de severidad de los mensajes acerca de las expresiones
     * especiales encontradas al generar la aplicación. El valor predeterminado de esta propiedad es OFF (no emitir mensajes acerca de expresiones
     * especiales).
     *
     * El método setSpecialExpressionLoggingLevel es un método estático que debe ejecutarse en el método main del proyecto maestro, antes de ejecutar
     * el método build.
     *
     * @param level elemento de la enumeración LoggingLevel que determina el nivel de severidad de los mensajes acerca de expresiones especiales que
     * se emiten al generar la aplicación. Especifique TRACE, DEBUG, INFO o WARN para emitir los mensajes con uno de esos niveles.
     */
    public static void setSpecialExpressionLoggingLevel(LoggingLevel level) {
        setSpecialExpressionLevel(level.getLevel());
    }

    /**
     * @return the unusual expression messages logging level
     */
    public static LoggingLevel getUnusualExpressionLoggingLevel() {
        return LoggingLevel.getLoggingLevel(_unusualExpressionLevel);
    }

    /**
     * El método setUnusualExpressionLoggingLevel se utiliza para establecer el nivel de severidad de los mensajes acerca de las expresiones inusuales
     * encontradas al generar la aplicación. El valor predeterminado de esta propiedad es WARN (emitir los mensajes como advertencias).
     *
     * El método setUnusualExpressionLoggingLevel es un método estático que debe ejecutarse en el método main del proyecto maestro, antes de ejecutar
     * el método build.
     *
     * @param level elemento de la enumeración LoggingLevel que determina el nivel de severidad de los mensajes acerca de expresiones inusuales que se
     * emiten al generar la aplicación. Especifique TRACE, DEBUG, INFO o WARN para emitir los mensajes con uno de esos niveles.
     */
    public static void setUnusualExpressionLoggingLevel(LoggingLevel level) {
        setUnusualExpressionLevel(level.getLevel());
    }

    public static String[] getHelpFileTypes() {
        Project project = TLC.getProject();
        return project == null ? null : project.helpFileTypes();
    }

    public static String getHelpFileTypesCSV() {
        Project project = TLC.getProject();
        return project == null ? null : project.helpFileTypesCSV();
    }

    public static boolean isMetaHelpEnabled() {
        Project project = TLC.getProject();
        return project == null ? null : project.metaHelpEnabled();
    }

    public static void addEntity(Entity entity) {
        Project project = TLC.getProject();
        if (project != null) {
            project.getParser().addEntity(entity);
        }
    }

    public static void addQueryTable(QueryTable queryTable) {
        Project project = TLC.getProject();
        if (project != null) {
            project.getParser().addQueryTable(queryTable);
        }
    }

    public static void increaseParserWarningCount() {
        Project project = TLC.getProject();
        if (project != null) {
            project.getParser().increaseWarningCount();
        }
    }

    public static void increaseParserErrorCount() {
        Project project = TLC.getProject();
        if (project != null) {
            project.getParser().increaseErrorCount();
        }
    }

    public static void logParserMessage(Level level, String message) {
        Project project = TLC.getProject();
        if (project != null) {
            project.getParser().log(level, message);
        }
    }

    public static void increaseWriterWarningCount() {
        Project project = TLC.getProject();
        if (project != null) {
            project.getWriter().increaseWarningCount();
        }
    }

    public static void increaseWriterErrorCount() {
        Project project = TLC.getProject();
        if (project != null) {
            project.getWriter().increaseErrorCount();
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="instance fields">
    private Project _master;

    private Parser _parser;

    private Writer _writer;

    private final Set<Locale> _supportedLocales = new LinkedHashSet<>();

    private final Set<Class<?>> _foreignEntityClasses = new LinkedHashSet<>();

    private final Set<Class<?>> _privateEntityClasses = new LinkedHashSet<>();

    private final Map<String, String> _environmentVariables = new LinkedHashMap<>();

    private final Map<String, ProjectEntityReference> _entityReferences = new LinkedHashMap<>();

    private final Map<String, ProjectReference> _projectReferences = new LinkedHashMap<>();

    private final Map<String, Display> _displays = new LinkedHashMap<>();

    private final Set<Artifact> _artifacts = new LinkedHashSet<>();

    private final Set<Method> _addAttributesMethods = new LinkedHashSet<>();

    private final Set<String> _processingGroups = new TreeSet<>();

    private final Set<UserFlow> _userFlows = new TreeSet<>();

    private final List<Pattern> _fileExclusionPatterns = new ArrayList<>();

    private final List<Pattern> _filePreservationPatterns = new ArrayList<>();

    private boolean _abort, _built;

    private boolean _annotatedWithMasterProject;

    private boolean _annotatedWithProjectModule;

    private boolean _annotatedWithProjectModuleDocGen;

    private boolean _menuModule;

    private boolean _roleModule;

    private boolean _foreignModule;

    private boolean _privateModule;

    private boolean _disablePrivateAndOtherContextEntitiesBplCodeGen; // since 10/02/2023 disable selected entities BPL code generation

    private boolean _disablePrivateAndOtherContextEntitiesBwsCodeGen; // since 10/02/2023 disable selected entities BWS code generation

    private boolean _disablePrivateAndOtherContextEntitiesDafCodeGen; // since 11/02/2023 disable selected entities DAF code generation

    private boolean _disablePrivateAndOtherContextEntitiesFwsCodeGen; // since 10/02/2023 disable selected entities FWS code generation

    private String _acronym = "";

    private String _helpDocument = "";

    private String _helpFileName = "";

    private MenuType _moduleMenuType = MenuType.UNSPECIFIED;

    private RoleType[] _moduleRoleTypes = new RoleType[]{REGISTRAR, PROCESSOR, READER, CONFIGURATOR, MANAGER};

    private HelpFileAutoName _helpFileAutoName = HelpFileAutoName.NONE;

    private String _helpFileAutoType = Constants.DEFAULT_HELP_FILE_TYPE;

    private boolean _moduleClassDiagramGenEnabled = true;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="instance fields public getters and setters">
    /**
     * @return the secondary locales set
     */
    public List<Locale> getSecondaryLocales() {
        Locale primary = Bundle.getLocale();
//      String languageTag = primary.toLanguageTag();
        return _supportedLocales.stream().filter(locale -> !locale.equals(primary)).collect(Collectors.toList());
    }

    /**
     * @return the supported locales set
     */
    public Set<Locale> getSupportedLocales() {
        return _supportedLocales;
    }

    /**
     * El método setSupportedLocales se utiliza para especificar el conjunto de configuraciones regionales soportadas por el proyecto. Se genera un
     * archivo de recursos para cada configuración regional soportada. En caso de que el archivo de recursos para el idioma seleccionado por el
     * usuario no esté disponible, la interfaz de la aplicación utiliza el archivo de recursos por defecto. La configuración regional del archivo de
     * recursos por defecto se especifica con el método setLocale.
     *
     * @param locales configuraciones regionales soportadas por el proyecto.
     */
    public void setSupportedLocales(Locale... locales) {
        _supportedLocales.clear();
        for (Locale locale : locales) {
            if (Bundle.isSupportedLocale(locale)) {
                _supportedLocales.add(locale);
            } else {
                logger.warn("Locale " + locale + " not supported yet.");
                increaseWarningCount();
            }
        }
        Locale locale = Bundle.getLocale();
        if (!ArrayUtils.contains(locales, locale)) {
            _supportedLocales.add(locale);
        }
    }

    /**
     * El método clearSupportedLocales se utiliza para borrar el conjunto de configuraciones regionales soportadas por el proyecto.
     */
    public void clearSupportedLocales() {
        _supportedLocales.clear();
    }

    /**
     * @return the foreign entity classes set
     */
    public Set<Class<?>> getForeignEntityClasses() {
        return _foreignEntityClasses;
    }

    /**
     * El método setForeignEntityClasses se utiliza para especificar el conjunto de clases de entidades foráneas del proyecto. Las entidades foráneas
     * son entidades cuyas correspondientes tablas no están definidas en la base de datos del proyecto, sino en otra que tipicamente reside en un
     * servidor diferente.
     *
     * @param classes clases de entidades foráneas del proyecto.
     */
    public void setForeignEntityClasses(Set<Class<?>> classes) {
        _foreignEntityClasses.clear();
        addForeignEntityClasses(classes);
    }

    /**
     * El método setForeignEntityClasses se utiliza para especificar el conjunto de clases de entidades foráneas del proyecto. Las entidades foráneas
     * son entidades cuyas correspondientes tablas no están definidas en la base de datos del proyecto, sino en otra que tipicamente reside en un
     * servidor diferente.
     *
     * @param clazz clase que contiene la declaración de clases de entidades foráneas del proyecto.
     */
    public void setForeignEntityClasses(Class<?> clazz) {
        _foreignEntityClasses.clear();
        addForeignEntityClasses(clazz);
    }

    /**
     * El método addForeignEntityClasses se utiliza para agregar clases al conjunto de clases de entidades foráneas del proyecto. Las entidades
     * foráneas son entidades cuyas correspondientes tablas no están definidas en la base de datos del proyecto, sino en otra que tipicamente reside
     * en un servidor diferente.
     *
     * @param classes clases de entidades foráneas del proyecto.
     */
    public void addForeignEntityClasses(Set<Class<?>> classes) {
        if (classes != null && !classes.isEmpty()) {
            _foreignEntityClasses.addAll(classes);
        }
    }

    /**
     * El método addForeignEntityClasses se utiliza para agregar clases al conjunto de clases de entidades foráneas del proyecto. Las entidades
     * foráneas son entidades cuyas correspondientes tablas no están definidas en la base de datos del proyecto, sino en otra que tipicamente reside
     * en un servidor diferente.
     *
     * @param clazz clase que contiene la declaración de clases de entidades foráneas del proyecto.
     */
    public void addForeignEntityClasses(Class<?> clazz) {
        Set<Class<?>> classes = XS2.getLocallyDeclaredEntityClasses(clazz);
        addForeignEntityClasses(classes);
    }

    /**
     * El método clearForeignEntityClasses se utiliza para borrar el conjunto de clases de entidades foráneas del proyecto.
     */
    public void clearForeignEntityClasses() {
        _foreignEntityClasses.clear();
    }

    /**
     * @return the private entity classes set
     */
    public Set<Class<?>> getPrivateEntityClasses() {
        return _privateEntityClasses;
    }

    /**
     * El método setPrivateEntityClasses se utiliza para especificar el conjunto de clases de entidades privadas del proyecto. Las entidades privadas
     * son entidades para las que no se deben generar vistas.
     *
     * @param classes clases de entidades privadas del proyecto.
     */
    public void setPrivateEntityClasses(Set<Class<?>> classes) {
        _privateEntityClasses.clear();
        addPrivateEntityClasses(classes);
    }

    /**
     * El método setPrivateEntityClasses se utiliza para especificar el conjunto de clases de entidades privadas del proyecto. Las entidades privadas
     * son entidades para las que no se deben generar vistas.
     *
     * @param clazz clase que contiene la declaración de clases de entidades privadas del proyecto.
     */
    public void setPrivateEntityClasses(Class<?> clazz) {
        _privateEntityClasses.clear();
        addPrivateEntityClasses(clazz);
    }

    /**
     * El método addPrivateEntityClasses se utiliza para agregar clases al conjunto de clases de entidades privadas del proyecto. Las entidades
     * privadas son entidades para las que no se deben generar vistas.
     *
     * @param classes clases de entidades privadas del proyecto.
     */
    public void addPrivateEntityClasses(Set<Class<?>> classes) {
        if (classes != null && !classes.isEmpty()) {
            _privateEntityClasses.addAll(classes);
        }
    }

    /**
     * El método addPrivateEntityClasses se utiliza para agregar clases al conjunto de clases de entidades privadas del proyecto. Las entidades
     * privadas son entidades para las que no se deben generar vistas.
     *
     * @param clazz clase que contiene la declaración de clases de entidades privadas del proyecto.
     */
    public void addPrivateEntityClasses(Class<?> clazz) {
        Set<Class<?>> classes = XS2.getLocallyDeclaredEntityClasses(clazz);
        addPrivateEntityClasses(classes);
    }

    /**
     * El método clearPrivateEntityClasses se utiliza para borrar el conjunto de clases de entidades privadas del proyecto.
     */
    public void clearPrivateEntityClasses() {
        _privateEntityClasses.clear();
    }

    /**
     * @return the environment variables map
     */
    public Map<String, String> getEnvironmentVariables() {
        return _environmentVariables;
    }

    /**
     * @param key key
     * @return an environment variable value
     */
    public String getEnvironmentVariable(String key) {
        return _environmentVariables.get(key);
    }

    /**
     * El método putEnvironmentVariable se utiliza para agregar una variable a la lista de variables de ambiente del proyecto. El método puede
     * utilizarse repetidamente para agregar varias variables.
     *
     * Alternativamente, las variables de ambiente se pueden obtener del archivo private.properties mediante el método loadEnvironmentVariables.
     *
     * Las variables de ambiente son parejas clave/valor, de modo que si se agregan varias variables con la misma clave, el valor de tal variable será
     * el último valor agregado.
     *
     * @param key clave con la que se asociará el valor especificado
     * @param value valor que se asociará con la clave especificada
     * @return el valor anterior asociado con la clave, o nulo si no había una asignación para la clave.
     */
    public String putEnvironmentVariable(String key, String value) {
        return _environmentVariables.put(key, value);
    }

    /**
     * @param key key
     * @return an environment variable value with all backslashes replaced by slashes
     */
    public String getSlashedEnvironmentVariable(String key) {
        String value = getEnvironmentVariable(key);
        return value == null ? null : value.replace('\\', '/');
    }

    /**
     * @param key key
     * @return an environment variable value with all slashes replaced by backslashes
     */
    public String getBackslashedEnvironmentVariable(String key) {
        String value = getEnvironmentVariable(key);
        return value == null ? null : value.replace('/', '\\');
    }

    private static final String ENVIRONMENT_VARIABLE_PREFIX = "environment.variable.";

    /**
     * El método loadEnvironmentVariables se utiliza para obtener el valor de las variables de ambiente del proyecto definidas en el archivo
     * private.properties. Las variables de ambiente son parejas clave/valor, de modo que si se agregan varias variables con la misma clave, el valor
     * de tal variable será el último valor agregado.
     */
    public void loadEnvironmentVariables() {
        loadEnvironmentVariables(LoggingLevel.TRACE);
    }

    /**
     * El método loadEnvironmentVariables se utiliza para obtener el valor de las variables de ambiente del proyecto definidas en el archivo
     * private.properties. Las variables de ambiente son parejas clave/valor, de modo que si se agregan varias variables con la misma clave, el valor
     * de tal variable será el último valor agregado.
     *
     * @param loggingLevel elemento de la enumeración LoggingLevel que determina el nivel de severidad de los mensajes que se emiten al obtener el
     * valor de las variables de ambiente del proyecto. Especifique TRACE, DEBUG, INFO, WARN, ERROR o FATAL para emitir los mensajes con ese nivel de
     * severidad. Especifique OFF para no emitir ningún mensaje.
     */
    public void loadEnvironmentVariables(LoggingLevel loggingLevel) {
        ExtendedProperties properties = PropertiesHandler.getPrivateProperties();
        if (properties == null || properties.isEmpty()) {
            _abort = true;
        } else {
            ArrayList<String> list = new ArrayList<>();
            for (Iterator i = properties.getKeys(); i.hasNext();) {
                list.add((String) i.next());
            }
            Level level = loggingLevel.getLevel();
            String key, value;
            for (String name : list) {
                if (StringUtils.startsWithIgnoreCase(name, ENVIRONMENT_VARIABLE_PREFIX)) {
                    key = StringUtils.removeStartIgnoreCase(name, ENVIRONMENT_VARIABLE_PREFIX);
                    value = properties.getString(name);
                    putEnvironmentVariable(key, value);
                    logger.log(level, key + " = " + value);
                }
            }
        }
    }

    /**
     * @return the master project if this is a nested project; null otherwise
     */
    public Project getMaster() {
        return _master;
    }

    /**
     * @param master the master project to set
     */
    private void setMaster(Project master) {
        _master = master;
    }

    /**
     * @param classSimpleName class simple name
     * @return true if the project references an entity of a class with the specified simple name
     */
    public boolean referencesEntity(String classSimpleName) {
        return _entityReferences.containsKey(classSimpleName);
    }

    /**
     * @param type class
     * @return true if the project references an entity of the class specified by type
     */
    public boolean referencesEntity(Class<?> type) {
        Entity entity = getEntity(type);
        return entity != null;
    }

    /**
     * getTypifiedEntity was renamed to getTypedEntity on 09/02/2023
     *
     * @param <T> generic class
     * @param type entity class
     * @return the root entity of the specified class
     */
    public <T extends Entity> T getTypedEntity(Class<T> type) {
        Entity entity = getEntity(type);
        return entity == null ? null : (T) entity;
    }

    /**
     * @param type entity class
     * @return the root entity of the specified class
     */
    public Entity getEntity(Class<?> type) {
        ProjectEntityReference reference = getEntityReference(type);
        return reference == null ? null : reference.getEntity();
    }

    /**
     * @param name entity class simple name
     * @return the root entity of the specified class
     */
    public Entity getEntity(String name) {
        ProjectEntityReference reference = getEntityReference(name);
        return reference == null ? null : reference.getEntity();
    }

    /**
     * @return the entities
     */
    public List<Entity> getEntitiesList() {
        List<Entity> list = new ArrayList<>();
        for (ProjectEntityReference reference : _entityReferences.values()) {
            if (reference.getEntity() != null) {
                list.add(reference.getEntity());
            }
        }
        return list;
    }

    /**
     * @return the entities
     */
    public Map<String, Entity> getEntitiesMap() {
        Map<String, Entity> entities = new LinkedHashMap<>();
        for (ProjectEntityReference reference : _entityReferences.values()) {
            if (reference.getEntity() != null) {
                entities.put(reference.getEntityClass().getSimpleName(), reference.getEntity());
            }
        }
        return entities;
    }

    /**
     * @param className class name
     * @return true if the project references a module of a class with the specified name
     */
    public boolean referencesModule(String className) {
        ProjectReference reference = _projectReferences.get(className);
        Project project = reference == null ? null : reference.getProject();
        return project != null && project.getMaster() != null;
    }

    /**
     * @param type class
     * @return true if the project references a module of the class specified by type
     */
    public boolean referencesModule(Class<?> type) {
        Project module = getModule(type);
        return module != null;
    }

    /**
     * @param <T> generic class
     * @param type entity class
     * @return the root entity of the specified class
     */
    public <T extends Project> T getTypedModule(Class<T> type) {
        Project project = getModule(type);
        return project == null ? null : (T) project;
    }

    /**
     * @param type class
     * @return the root module of the specified class
     */
    public Project getModule(Class<?> type) {
        Project project = getProject(type);
        return project == null || project.getMaster() == null ? null : project;
    }

    /**
     * @return the modules
     */
    public List<Project> getModulesList() {
        List<Project> list = new ArrayList<>();
        for (ProjectReference reference : _projectReferences.values()) {
            Project module = reference.getProject();
            if (module != null && module.getMaster() != null) {
                list.add(module);
            }
        }
        return list;
    }

    /**
     * @return the modules
     */
    public Map<String, Project> getModulesMap() {
        Map<String, Project> projects = new LinkedHashMap<>();
        for (ProjectReference reference : _projectReferences.values()) {
            Project module = reference.getProject();
            if (module != null && module.getMaster() != null) {
                projects.put(reference.getProjectClass().getName(), module);
            }
        }
        return projects;
    }

    /**
     * @param className class name
     * @return true if the project references a project of a class with the specified name
     */
    public boolean referencesProject(String className) {
        return _projectReferences.containsKey(className);
    }

    /**
     * @param type class
     * @return true if the project references a project of the class specified by type
     */
    public boolean referencesProject(Class<?> type) {
        Project project = getProject(type);
        return project != null;
    }

    /**
     * @param type class
     * @return the root project of the specified class
     */
    public Project getProject(Class<?> type) {
//      Class<?> clazz = XS1.getNamedClass(type);
//      return getProjectsMap().get(clazz.getName());
        ProjectReference reference = getProjectReference(type);
        return reference == null ? null : reference.getProject();
    }

    /**
     * @return the projects
     */
    public List<Project> getProjectsList() {
        List<Project> list = new ArrayList<>();
        for (ProjectReference reference : _projectReferences.values()) {
            if (reference.getProject() != null) {
                list.add(reference.getProject());
            }
        }
        return list;
    }

    /**
     * @return the projects
     */
    public Map<String, Project> getProjectsMap() {
        Map<String, Project> projects = new LinkedHashMap<>();
        for (ProjectReference reference : _projectReferences.values()) {
            if (reference.getProject() != null) {
                projects.put(reference.getProjectClass().getName(), reference.getProject());
            }
        }
        return projects;
    }

    /**
     * @return the displays list
     */
    public List<? extends Display> getDisplaysList() {
        return new ArrayList<>(getDisplaysMap().values());
    }

    /**
     * @return the displays map
     */
    public Map<String, ? extends Display> getDisplaysMap() {
        return _displays;
    }

    Set<String> crossReferencedExpressionsSet;

    /**
     * @return the cross-referenced expressions
     */
    public Set<String> getCrossReferencedExpressionsSet() {
        if (crossReferencedExpressionsSet == null) {
            crossReferencedExpressionsSet = new LinkedHashSet<>();
            PersistentEntity pent;
            List<Entity> entities = getEntitiesList();
            for (Entity entity : entities) {
                if (entity instanceof PersistentEntity) {
                    pent = (PersistentEntity) entity;
                    crossReferencedExpressionsSet.addAll(pent.getCrossReferencedExpressionsSet());
                }
            }
        }
        return crossReferencedExpressionsSet;
    }

    public boolean containsCrossReferencedExpression(Expression expression) {
        String key = expression == null ? null : expression.getCrossReferencedExpressionsKey();
        return key != null && getCrossReferencedExpressionsSet().contains(key);
    }

    Set<String> schemasSet;

    public Set<String> getSchemasSet() {
        String schema;
        if (schemasSet == null) {
            schemasSet = new LinkedHashSet<>();
            PersistentEntity pent;
            List<Entity> entities = getEntitiesList();
            for (Entity entity : entities) {
                if (entity instanceof PersistentEntity) {
                    pent = (PersistentEntity) entity;
                    schema = pent.getSchema();
                    if (StringUtils.isNotBlank(schema)) {
                        schemasSet.add(schema.trim());
                    }
                }
            }
        }
        return schemasSet;
    }

    Map<String, String> tablesMap;

    public Map<String, String> getTablesMap() {
        String table;
        if (tablesMap == null) {
            tablesMap = new LinkedHashMap<>();
            PersistentEntity pent;
            PersistentEntityWrapper wrapper;
            List<Entity> entities = getEntitiesList();
            for (Entity entity : entities) {
                if (entity instanceof PersistentEntity) {
                    pent = (PersistentEntity) entity;
                    wrapper = new PersistentEntityWrapper(pent);
                    table = wrapper.getSqlName();
                    if (StringUtils.isNotBlank(table)) {
                        tablesMap.put(table.trim(), pent.getClass().getName());
                    }
                }
            }
        }
        return tablesMap;
    }

    Map<String, String> catalogTablesMap;

    public Map<String, String> getCatalogTablesMap() {
        String table;
        if (catalogTablesMap == null) {
            catalogTablesMap = new LinkedHashMap<>();
            PersistentEntity pent;
            PersistentEntityWrapper wrapper;
            List<Entity> entities = getEntitiesList();
            for (Entity entity : entities) {
                if (entity instanceof PersistentEntity && entity.isCatalogEntity()) {
                    pent = (PersistentEntity) entity;
                    wrapper = new PersistentEntityWrapper(pent);
                    table = wrapper.getSqlName();
                    if (StringUtils.isNotBlank(table)) {
                        catalogTablesMap.put(table.trim(), pent.getClass().getName());
                    }
                }
            }
        }
        return catalogTablesMap;
    }

    /**
     *
     */
    private Kleenean _businessOperationConfirmationRequired;

    /**
     * @return the business operation confirmation indicator
     */
    public Kleenean getBusinessOperationConfirmationRequired() {
        return _businessOperationConfirmationRequired == null ? Kleenean.UNSPECIFIED : _businessOperationConfirmationRequired;
    }

    /**
     * El método setBusinessOperationConfirmation se utiliza para especificar el valor predeterminado del elemento confirmation de la anotación
     * OperationClass.
     *
     * @param confirmation TRUE para solicitar confirmación; FALSE para no solicitar confirmación.
     *
     * @see adalid.core.annotations.OperationClass#confirmation() OperationClass.confirmation
     *
     */
    public void setBusinessOperationConfirmationRequired(Kleenean confirmation) {
        _businessOperationConfirmationRequired = confirmation;
    }

    /**
     *
     */
    private Kleenean _databaseOperationConfirmationRequired;

    /**
     * @return the database operation confirmation indicator
     */
    public Kleenean getDatabaseOperationConfirmationRequired() {
        return _databaseOperationConfirmationRequired == null ? Kleenean.UNSPECIFIED : _databaseOperationConfirmationRequired;
    }

    /**
     * El método setDatabaseOperationConfirmation se utiliza para especificar el valor predeterminado del elemento confirmation de las anotaciones
     * EntityInsertOperation, EntityUpdateOperation y EntityDeleteOperation.
     *
     * @param confirmation TRUE para solicitar confirmación; FALSE para no solicitar confirmación; UNSPECIFIED para utilizar el valor predeterminado.
     * El valor predeterminado es TRUE para la operación <b>delete</b>, y FALSE para las demás operaciones.
     *
     * @see adalid.core.annotations.EntityInsertOperation#confirmation() EntityInsertOperation.confirmation
     * @see adalid.core.annotations.EntityUpdateOperation#confirmation() EntityUpdateOperation.confirmation
     * @see adalid.core.annotations.EntityDeleteOperation#confirmation() EntityDeleteOperation.confirmation
     *
     */
    public void setDatabaseOperationConfirmationRequired(Kleenean confirmation) {
        _databaseOperationConfirmationRequired = confirmation;
    }

    private Boolean _databaseDefaultValuesMustBeSingleEntityExpression;

    /**
     * @return the database default.values.must.be.single.entity.expression indicator
     */
    public boolean isDatabaseDefaultValuesMustBeSingleEntityExpression() {
        if (_databaseDefaultValuesMustBeSingleEntityExpression == null) {
            ExtendedProperties bootstrapping = PropertiesHandler.getBootstrapping();
            return bootstrapping != null && !bootstrapping.isEmpty()
                && BitUtils.valueOf(bootstrapping.getString("database.default.values.must.be.single.entity.expression", "false"));
        }
        return _databaseDefaultValuesMustBeSingleEntityExpression;
    }

    /**
     * El método setDatabaseDefaultValuesMustBeSingleEntityExpression se utiliza para especificar si los valores por default que sean expresiones
     * deben ser o no, expresiones de una sola entidad, para poder ser implementados en funciones de base de datos.
     *
     * @param b true si las expresiones deben ser de una sola entidad; de lo contrario, false
     */
    public void setDatabaseDefaultValuesMustBeSingleEntityExpression(boolean b) {
        _databaseDefaultValuesMustBeSingleEntityExpression = b;
    }

    /**
     *
     */
    private String _missingValueGraphicImageName;

    /**
     * @return the missing value graphic image name of all properties
     */
    public String getMissingValueGraphicImageName() {
        return _missingValueGraphicImageName;
    }

    /**
     * El método setMissingValueGraphicImageName se utiliza para establecer el nombre de imagen gráfica de valor requerido no especificado asociada a
     * todas las propiedades de todas las entidades del proyecto. La imagen de la propiedad se utiliza para resaltar su valor en las vistas (páginas)
     * de consulta y registro.
     *
     * @param name nombre de imagen gráfica de valor requerido no especificado asociada a las propiedades
     */
    public void setMissingValueGraphicImageName(String name) {
        _missingValueGraphicImageName = fairGraphicImageName(name);
    }

    public boolean isMissingValueGraphicImageNameFontAwesomeClass() {
        return isFontAwesomeClass(_missingValueGraphicImageName);
    }

    /**
     *
     */
    private String _nullValueGraphicImageName;

    /**
     * @return the null value graphic image name of all properties
     */
    public String getNullValueGraphicImageName() {
        return _nullValueGraphicImageName;
    }

    /**
     * El método setNullValueGraphicImageName se utiliza para establecer el nombre de imagen gráfica de valor nulo asociada a todas las propiedades de
     * todas las entidades del proyecto. La imagen de la propiedad se utiliza para resaltar su valor en las vistas (páginas) de consulta y registro.
     *
     * @param name nombre de imagen gráfica de valor nulo asociada a las propiedades
     */
    public void setNullValueGraphicImageName(String name) {
        _nullValueGraphicImageName = fairGraphicImageName(name);
    }

    public boolean isNullValueGraphicImageNameFontAwesomeClass() {
        return isFontAwesomeClass(_nullValueGraphicImageName);
    }

    /**
     *
     */
    private String _unnecessaryValueGraphicImageName;

    /**
     * @return the unnecessary value graphic image name of all properties
     */
    public String getUnnecessaryValueGraphicImageName() {
        return _unnecessaryValueGraphicImageName;
    }

    /**
     * El método setUnnecessaryValueGraphicImageName se utiliza para establecer el nombre de imagen gráfica de valor innecesario (no aplicable)
     * asociada a todas las propiedades de todas las entidades del proyecto. La imagen de la propiedad se utiliza para resaltar su valor en las vistas
     * (páginas) de consulta y registro.
     *
     * @param name nombre de imagen gráfica de valor innecesario asociada a las propiedades
     */
    public void setUnnecessaryValueGraphicImageName(String name) {
        _unnecessaryValueGraphicImageName = fairGraphicImageName(name);
    }

    public boolean isUnnecessaryValueGraphicImageNameFontAwesomeClass() {
        return isFontAwesomeClass(_unnecessaryValueGraphicImageName);
    }

    /**
     *
     */
    private BooleanDisplayType _defaultBooleanDisplayType = BooleanDisplayType.TOGGLE;

    /**
     * @return the default boolean display type
     */
    public BooleanDisplayType getDefaultBooleanDisplayType() {
        return _defaultBooleanDisplayType;
    }

    /**
     * El método setDefaultRequiredBooleanDisplayType se utiliza para establecer el tipo predeterminado de componente que se utiliza para mostrar el
     * valor de propiedades Boolean que no permiten valores nulos en las vistas (páginas) de consulta y registro, y de parámetros Boolean requeridos
     * en las vistas (páginas) de ejecución de operaciones de negocio.
     *
     * @param type tipo de componente Boolean
     */
    public void setDefaultBooleanDisplayType(BooleanDisplayType type) {
        _defaultBooleanDisplayType = type == null ? BooleanDisplayType.DROPDOWN : type;
    }

    /**
     * @return the MasterProject annotation indicator
     */
    public boolean isAnnotatedWithMaster() {
        return _annotatedWithMasterProject;
    }

    /**
     * @return the ProjectModule annotation indicator
     */
    public boolean isAnnotatedWithModule() {
        return _annotatedWithProjectModule;
    }

    /**
     * @return the ProjectModuleDocGen annotation indicator
     */
    public boolean isAnnotatedWithModuleDocGen() {
        return _annotatedWithProjectModuleDocGen;
    }

    /**
     * @return the menu module indicator
     */
    public boolean isMenuModule() {
        return _menuModule;
    }

    /**
     * @return the role module indicator
     */
    public boolean isRoleModule() {
        return _roleModule;
    }

    /**
     * @return the foreign module indicator
     */
    public boolean isForeignModule() {
        return _foreignModule;
    }

    /**
     * @return the private module indicator
     */
    public boolean isPrivateModule() {
        return _privateModule;
    }

    public boolean isDisablePrivateAndOtherContextEntitiesBplCodeGen() {
        return _disablePrivateAndOtherContextEntitiesBplCodeGen;
    }

    /**
     * El método setDisablePrivateAndOtherContextEntitiesBplCodeGen se utiliza para inhabilitar la generación de código BPL (Business Process Logic)
     * de entidades privadas y de entidades cuyas vistas (páginas) se encuentran en el módulo Web de la aplicación empresarial de otro proyecto.
     *
     * El método setDisablePrivateAndOtherContextEntitiesBplCodeGen debe ejecutarse en el método configureGenerator del proyecto.
     *
     * @param disable true o false para inhabilitar, o no, la generación de código BPL
     */
    public void setDisablePrivateAndOtherContextEntitiesBplCodeGen(boolean disable) {
        _disablePrivateAndOtherContextEntitiesBplCodeGen = disable;
    }

    public boolean isDisablePrivateAndOtherContextEntitiesBwsCodeGen() {
        return _disablePrivateAndOtherContextEntitiesBwsCodeGen;
    }

    /**
     * El método setDisablePrivateAndOtherContextEntitiesBwsCodeGen se utiliza para inhabilitar la generación de código BWS (Business Web Service) de
     * entidades privadas y de entidades cuyas vistas (páginas) se encuentran en el módulo Web de la aplicación empresarial de otro proyecto.
     *
     * El método setDisablePrivateAndOtherContextEntitiesBwsCodeGen debe ejecutarse en el método configureGenerator del proyecto.
     *
     * @param disable true o false para inhabilitar, o no, la generación de código BWS
     */
    public void setDisablePrivateAndOtherContextEntitiesBwsCodeGen(boolean disable) {
        _disablePrivateAndOtherContextEntitiesBwsCodeGen = disable;
    }

    public boolean isDisablePrivateAndOtherContextEntitiesDafCodeGen() {
        return _disablePrivateAndOtherContextEntitiesDafCodeGen;
    }

    /**
     * El método setDisablePrivateAndOtherContextEntitiesDafCodeGen se utiliza para inhabilitar la generación de fachadas de acceso a datos (código
     * DAF, por las siglas en inglés de Data Access Façade) de entidades privadas y de entidades cuyas vistas (páginas) se encuentran en el módulo Web
     * de la aplicación empresarial de otro proyecto.
     *
     * El método setDisablePrivateAndOtherContextEntitiesDafCodeGen debe ejecutarse en el método configureGenerator del proyecto.
     *
     * @param disable true o false para inhabilitar, o no, la generación de código DAF
     */
    public void setDisablePrivateAndOtherContextEntitiesDafCodeGen(boolean disable) {
        _disablePrivateAndOtherContextEntitiesDafCodeGen = disable;
    }

    public boolean isDisablePrivateAndOtherContextEntitiesFwsCodeGen() {
        return _disablePrivateAndOtherContextEntitiesFwsCodeGen;
    }

    /**
     * El método setDisablePrivateAndOtherContextEntitiesFwsCodeGen se utiliza para inhabilitar la generación de código FWS (Façade Web Service) de
     * entidades privadas y de entidades cuyas vistas (páginas) se encuentran en el módulo Web de la aplicación empresarial de otro proyecto.
     *
     * El método setDisablePrivateAndOtherContextEntitiesFwsCodeGen debe ejecutarse en el método configureGenerator del proyecto.
     *
     * @param disable true o false para inhabilitar, o no, la generación de código FWS
     */
    public void setDisablePrivateAndOtherContextEntitiesFwsCodeGen(boolean disable) {
        _disablePrivateAndOtherContextEntitiesFwsCodeGen = disable;
    }

    /**
     * @return the module's menu type
     */
    public MenuType getModuleMenuType() {
        return _moduleMenuType;
    }

    /**
     * @return the module's role types
     */
    public RoleType[] getModuleRoleTypes() {
        return _moduleRoleTypes;
    }

    /**
     * @return the module's role type numbers
     */
    public int[] getModuleRoleTypesNumbers() {
        if (_moduleRoleTypes == null || _moduleRoleTypes.length == 0) {
            return null;
        }
        int[] numbers = new int[_moduleRoleTypes.length];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = _moduleRoleTypes[i].getNumber();
        }
        return numbers;
    }

    public String getModuleRoleTypesNumbersString() {
        int[] numbers = getModuleRoleTypesNumbers();
        if (numbers == null || numbers.length == 0) {
            return null;
        }
        String string = Arrays.toString(numbers);
        return StrUtils.disclose(string, '[', ']');
    }

    public String getAcronym() {
        return _acronym;
    }

    private void setAcronym(String acronym) {
        _acronym = acronym;
    }

    /**
     * @return the help document
     */
    public String getHelpDocument() {
        return _helpDocument;
    }

    /**
     * El método setHelpDocument se utiliza para establecer el documento incrustado de ayuda del proyecto.
     *
     * Cada módulo y cada entidad del proyecto podrían tener su propio documento incrustado de ayuda, según lo establecido mediante los elementos de
     * sus anotaciones ProjectModule y EntityClass. También se puede especificar un documento incrustado diferente para cada formato de vista de cada
     * entidad, mediante las anotaciones EntityTableView, EntityDetailView, EntityTreeView y EntityConsoleView.
     *
     * Las vistas utilizarán el documento incrustado definido para el correspondiente formato de vista de la entidad, la entidad de la vista, el
     * módulo al que pertenece la entidad de la vista o el proyecto maestro, buscando en ese orden; si ninguno de ellos está definido, no habrá un
     * documento incrustado disponible para la vista.
     *
     * @param document definición del documento incrustado de ayuda del proyecto; si utiliza la plataforma jee2, puede ser una URL o un
     * <b>iframe</b> que incluya la URL del documento.
     */
    public void setHelpDocument(String document) {
        if (StringUtils.isBlank(document)) {
            _helpDocument = "";
        } else if (isValidEmbeddedDocument(document)) {
            _helpDocument = document;
        } else {
            logger.error(getName() + " help document is invalid ");
            increaseErrorCount();
        }
    }

    /**
     * @return the help file name
     */
    public String getHelpFileName() {
        return _helpFileName;
    }

    /**
     * El método setHelpFileName se utiliza para establecer la ruta y el nombre del archivo de ayuda del proyecto.
     *
     * Cada módulo, cada entidad y cada vista (página) de cada entidad del proyecto podrían tener su propio archivo de ayuda, según lo establecido
     * mediante los elementos de las anotaciones ProjectModule y EntityClass de cada módulo y cada entidad. También se puede especificar un archivo
     * diferente para cada formato de vista de cada entidad, mediante las anotaciones EntityTableView, EntityDetailView, EntityTreeView y
     * EntityConsoleView.
     *
     * La vista que no tenga su propio archivo de ayuda utilizará el definido para el correspondiente formato de vista de la entidad, la entidad de la
     * vista, el módulo al que pertenece la entidad de la vista o el proyecto maestro, buscando en ese orden; si ninguno de ellos está definido, la
     * página de ayuda no estará disponible para la vista.
     *
     * @param fileName ruta y nombre del archivo de ayuda del proyecto; si utiliza la plataforma jee2, la ruta del archivo debe ser relativa al
     * subdirectorio especificado mediante el atributo extraordinario HELP_RESOURCES_FOLDER del proyecto maestro, y cuyo valor por omisión es el
     * subdirectorio resources/help/custom-made del directorio src/main/webapp del módulo Web de la aplicación.
     */
    public void setHelpFileName(String fileName) {
        fileName = fixedHelpFileName(fileName);
        if (StringUtils.isBlank(fileName)) {
            _helpFileName = "";
        } else if (isValidHelpFileName(fileName)) {
            if (isValidHelpFileType(fileName)) {
                _helpFileName = fileName;
            } else {
                logger.error(getName() + " help file type is missing or invalid; valid types are: " + Project.getHelpFileTypesCSV());
                increaseErrorCount();
            }
        } else {
            logger.error(getName() + " help file name is invalid ");
            increaseErrorCount();
        }
    }

    private String fixedHelpFileName(String fileName) {
        String autoType = fixedHelpFileAutoType();
        String fileType = StringUtils.substringAfterLast(fileName, ".");
        return StringUtils.isBlank(autoType) || StringUtils.isNotBlank(fileType) ? fileName : fileName + "." + autoType;
    }

    /**
     * @return the help file auto name
     */
    public HelpFileAutoName getHelpFileAutoName() {
        return _helpFileAutoName;
    }

    protected void setHelpFileAutoName(HelpFileAutoName helpFileAutoName) {
        _helpFileAutoName = coalesce(helpFileAutoName, HelpFileAutoName.NONE);
    }

    private void checkHelpFileAutoName() {
        if (HelpFileAutoName.META.equals(_helpFileAutoName) && !isAnnotatedWithMaster()) {
            logger.error(getName() + " META help file auto-type can only be specified in a MasterProject annotation");
            increaseErrorCount();
        }
    }

    /**
     * @return the help file auto type
     */
    public String getHelpFileAutoType() {
        return _helpFileAutoType;
    }

    protected void setHelpFileAutoType(String helpFileAutoType) {
        _helpFileAutoType = StringUtils.defaultIfBlank(helpFileAutoType, Constants.DEFAULT_HELP_FILE_TYPE);
    }

    private void checkHelpFileAutoType() {
        if (HelpFileAutoName.NONE.equals(_helpFileAutoName) || HelpFileAutoName.META.equals(_helpFileAutoName)) {
            _helpFileAutoType = "";
        } else if (StringUtils.isBlank(_helpFileAutoType)) {
            _helpFileAutoType = Constants.DEFAULT_HELP_FILE_TYPE;
        } else if (!ArrayUtils.contains(projectHelpFileTypes(), _helpFileAutoType)) {
            logger.error(getName() + " help file auto-type is invalid; valid types are: " + projectHelpFileTypesCSV());
            increaseErrorCount();
        }
    }

    private String fixedHelpFileAutoType() {
        return HelpFileAutoName.NONE.equals(_helpFileAutoName) || HelpFileAutoName.META.equals(_helpFileAutoName) ? ""
            : (StringUtils.defaultIfBlank(_helpFileAutoType, Constants.DEFAULT_HELP_FILE_TYPE));
    }

    /**
     * @return the module class diagram generation indicator
     */
    public boolean isModuleClassDiagramGenEnabled() {
        return _moduleClassDiagramGenEnabled;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="instance fields package getters and setters">
    Parser getParser() {
        if (_parser == null) {
            _parser = new Parser();
        }
        return _parser;
    }

    Writer getWriter() {
        if (_writer == null) {
            _writer = new Writer(this, "project");
        }
        return _writer;
    }

    /**
     * @param type entity class
     * @return the entity reference of the specified class
     */
    ProjectEntityReference getEntityReference(Class<?> type) {
        Class<?> clazz = type == null ? null : XS1.getNamedClass(type);
        return clazz == null ? null : getEntityReference(clazz.getSimpleName());
    }

    /**
     * @param name entity class simple name
     * @return the entity reference of the specified class
     */
    ProjectEntityReference getEntityReference(String name) {
        return _entityReferences.get(name);
    }

    /**
     * @return the true type of the specified class
     */
    Class<?> getTrueType(Class<?> type) {
        Class<?> clazz = XS1.getNamedClass(type);
        String key = clazz.getSimpleName();
        if (_entityReferences.containsKey(key)) {
            ProjectEntityReference reference = _entityReferences.get(key);
            return reference == null ? null : reference.getEntityClass();
        }
        return type;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="instance fields public getters and setters">
    /**
     * @return the entity references map
     */
    public Map<String, ProjectEntityReference> getEntityReferences() {
        return _entityReferences;
    }

    /**
     * @return the project reference of the specified class
     */
    ProjectReference getProjectReference(Class<?> type) {
        Class<?> clazz = XS1.getNamedClass(type);
        return _projectReferences.get(clazz.getName());
    }

    /**
     * @return the project references map
     */
    public Map<String, ProjectReference> getProjectReferences() {
        return _projectReferences;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="instance fields public getters and setters">
    /**
     * @return the artifacts set
     */
    public Set<Artifact> getArtifacts() {
        return _artifacts;
    }

    /**
     * clears the artifacts set
     */
    private void clearArtifacts() {
        _artifacts.clear();
    }

    /**
     * clears the attributes set of every artifact
     */
    private void clearArtifactsAttributes() {
        for (Artifact artifact : _artifacts) {
            artifact.clearAttributes();
        }
    }

    /**
     * executes the addAttributes method of every artifact
     */
    private void addArtifactsAttributes() {
        for (Artifact artifact : _artifacts) {
            artifact.addAttributes();
        }
    }

    /**
     * adds an artifact to the set
     *
     * @param artifact artifact
     * @return true if this set did not already contain the specified element
     */
    public boolean addArtifact(Artifact artifact) {
        return _artifacts.add(artifact);
    }

    /**
     * @return the addAttributes methods set
     */
    public Set<Method> getAddAttributesMethods() {
        return _addAttributesMethods;
    }

    /**
     * El método clearAddAttributesMethods se utiliza para borrar la lista de clases que contienen métodos Add Attributes.
     */
    public void clearAddAttributesMethods() {
        _addAttributesMethods.clear();
    }

    public void attachAddAttributesMethods() {
        attachAddAttributesMethods(getClass());
    }

    /**
     * El método attachAddAttributesMethods se utiliza para agregar una clase a la lista de clases que contienen métodos Add Attributes. El método
     * puede utilizarse repetidamente para agregar varias clases. Para posteriormente borrar la lista se utiliza el método clearAddAttributesMethods.
     *
     * Todo método Add Attributes debe ser público, estático y sin valor de retorno; con un único parámetro que implemente, directa o indirectamente,
     * la interfaz Artifact; y decorado con la anotación AddAttributesMethod. El nombre del método puede ser cualquiera que cumpla con las reglas de
     * Java.
     *
     * Típicamente, un método Add Attributes agrega uno o más atributos extraordinarios, es decir, atributos propios del PSM (Platform Specific
     * Model), al artefacto que recibe como parámetro, y/o a sus artefactos relacionados, ejecutando repetidamente el método addAttribute.
     *
     * Al generar el proyecto, los métodos Add Attributes de las clases agregadas son ejecutados automáticamente, para cada uno de los artefactos del
     * proyecto, que sean asignables a partir de la clase o interfaz del parámetro, antes de ejecutar el método addAttributes propio del artefacto. El
     * orden de ejecución de los métodos está determinado, en primer lugar, por el orden en el que se agregan las clases a la lista y, en segundo
     * lugar, por el valor especificado en la anotación AddAttributesMethod. Si el orden de ejecución dentro de la clase no es relevante, tal valor
     * puede ser omitido. Nótese que, dado que el método addAttributes propio de cada artefacto se ejecuta después de todos los métodos "Add
     * Attributes" de las clases agregadas, tal método es apropiado para agregar atributos específicos del artefacto.
     *
     * @param clazz clase que contiene métodos Add Attributes
     */
    public void attachAddAttributesMethods(Class<?> clazz) {
        logger.debug(signature("attachAddAttributesMethods", clazz));
        String name;
        boolean addAttributesMethod;
        int modifiers;
        Class<?> returnType;
        Class<?>[] parameterTypes;
        Method[] methods = clazz.getDeclaredMethods();
        List<Method> list = Arrays.asList(methods);
        Comparator<Method> comparator = new ByMethodSequence();
        list = (List<Method>) ColUtils.sort(list, comparator);
        for (Method method : list) {
            name = method.getName();
            addAttributesMethod = method.isAnnotationPresent(AddAttributesMethod.class);
            modifiers = method.getModifiers();
            returnType = method.getReturnType();
            parameterTypes = method.getParameterTypes();
            if (addAttributesMethod
                && Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers)
                && void.class.equals(returnType)
                && parameterTypes.length == 1 && Artifact.class.isAssignableFrom(parameterTypes[0])) {
                logger.debug(signature(clazz.getSimpleName() + "." + name, parameterTypes[0]));
                _addAttributesMethods.add(method);
            }
        }
    }

    /**
     *
     */
    private void invokeAddAttributesMethods() {
        String name;
        Class<?> clazz;
        Class<?> parameterType;
        for (Method method : _addAttributesMethods) {
            clazz = method.getDeclaringClass();
            name = method.getName();
            parameterType = method.getParameterTypes()[0];
            for (Artifact artifact : _artifacts) {
                if (parameterType.isAssignableFrom(artifact.getClass())) {
                    if (Entity.class.isAssignableFrom(artifact.getClass()) && artifact.depth() != 0) {
                        continue;
                    }
                    try {
                        logger.debug(signature(clazz.getSimpleName() + "." + name, parameterType + " " + artifact.getClassPath()));
                        method.invoke(null, artifact);
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                        fatal(ex);
                    }
                }
            }
        }
    }

    public void addEntityAttribute(String attributeName, Object attributeValue, Class<? extends Entity>... entityClasses) {
        if (StringUtils.isNotBlank(attributeName) && attributeValue != null && entityClasses != null && entityClasses.length > 0) {
            forEntityAttribute(attributeName, attributeValue, entityNames(entityClasses));
        }
    }

    private void forEntityAttribute(String attributeName, Object attributeValue, Set<String> entityNames) {
        for (String name : entityNames) {
            Entity entity = getEntity(name);
            if (entity != null) {
                entity.addAttribute(attributeName, attributeValue);
            }
        }
    }

    /**
     * El método setForeignEntityClass se utiliza para especificar un conjunto de entidades que se debe agregar, o no, al conjunto de entidades
     * foráneas de la aplicación. Las entidades foráneas son entidades cuyas correspondientes tablas no están definidas en la base de datos de la
     * aplicación, sino en otra que típicamente reside en un servidor diferente.
     *
     * El método setForeignEntityClass debe ejecutarse en el método configureAnalyzer del proyecto.
     *
     * @param foreignEntityClass true o false para agregar o no las entidades, respectivamente; null, para agregar las entidades solo si pertenecen a
     * un módulo de entidades foráneas (vea el elemento foreign de la Anotación ProjectModule)
     * @param moduleClass clase del módulo que contiene las entidades que se van a agregar, o no, al conjunto de entidades foráneas de la aplicación
     */
    public void setForeignEntityClass(Boolean foreignEntityClass, Class<? extends Project> moduleClass) {
        if (moduleClass != null) {
            Project module = getModule(moduleClass);
            if (module != null) {
                for (Entity entity : module.getEntitiesList()) {
                    entity.setForeignEntityClass(foreignEntityClass);
                }
            }
        }
    }

    /**
     * El método setForeignEntityClass se utiliza para especificar un conjunto de entidades que se debe agregar, o no, al conjunto de entidades
     * foráneas de la aplicación. Las entidades foráneas son entidades cuyas correspondientes tablas no están definidas en la base de datos de la
     * aplicación, sino en otra que típicamente reside en un servidor diferente.
     *
     * El método setForeignEntityClass debe ejecutarse en el método configureAnalyzer del proyecto.
     *
     * @param foreignEntityClass true o false para agregar o no las entidades, respectivamente; null, para agregar las entidades solo si pertenecen a
     * un módulo de entidades foráneas (vea el elemento foreign de la Anotación ProjectModule)
     * @param entityClasses una o más clases de entidades que se van a agregar, o no, al conjunto de entidades foráneas de la aplicación
     */
    public void setForeignEntityClass(Boolean foreignEntityClass, Class<? extends Entity>... entityClasses) {
        if (entityClasses != null && entityClasses.length > 0) {
            forForeignEntityClass(foreignEntityClass, entityNames(entityClasses));
        }
    }

    private void forForeignEntityClass(Boolean foreignEntityClass, Set<String> entityNames) {
        for (String name : entityNames) {
            Entity entity = getEntity(name);
            if (entity != null) {
                entity.setForeignEntityClass(foreignEntityClass);
            }
        }
    }

    /**
     * El método setPrivateEntityClass se utiliza para especificar un conjunto de entidades que se debe agregar, o no, al conjunto de entidades
     * privadas de la aplicación. Las entidades privadas son entidades para las que no se deben generar vistas.
     *
     * El método setPrivateEntityClass debe ejecutarse en el método configureAnalyzer del proyecto.
     *
     * @param privateEntityClass true o false para agregar o no las entidades, respectivamente; null, para agregar las entidades solo si pertenecen a
     * un módulo de entidades privadas (vea el elemento private de la Anotación ProjectModule)
     * @param moduleClass clase del módulo que contiene las entidades que se van a agregar, o no, al conjunto de entidades privadas de la aplicación
     */
    public void setPrivateEntityClass(Boolean privateEntityClass, Class<? extends Project> moduleClass) {
        if (moduleClass != null) {
            Project module = getModule(moduleClass);
            if (module != null) {
                for (Entity entity : module.getEntitiesList()) {
                    entity.setPrivateEntityClass(privateEntityClass);
                }
            }
        }
    }

    /**
     * El método setPrivateEntityClass se utiliza para especificar un conjunto de entidades que se debe agregar, o no, al conjunto de entidades
     * privadas de la aplicación. Las entidades privadas son entidades para las que no se deben generar vistas.
     *
     * El método setPrivateEntityClass debe ejecutarse en el método configureAnalyzer del proyecto.
     *
     * @param privateEntityClass true o false para agregar o no las entidades, respectivamente; null, para agregar las entidades solo si pertenecen a
     * un módulo de entidades privadas (vea el elemento private de la Anotación ProjectModule)
     * @param entityClasses una o más clases de entidades que se van a agregar, o no, al conjunto de entidades privadas de la aplicación
     */
    public void setPrivateEntityClass(Boolean privateEntityClass, Class<? extends Entity>... entityClasses) {
        if (entityClasses != null && entityClasses.length > 0) {
            forPrivateEntityClass(privateEntityClass, entityNames(entityClasses));
        }
    }

    private void forPrivateEntityClass(Boolean privateEntityClass, Set<String> entityNames) {
        for (String name : entityNames) {
            Entity entity = getEntity(name);
            if (entity != null) {
                entity.setPrivateEntityClass(privateEntityClass);
            }
        }
    }

    /**
     * El método setApplicationOrigin se utiliza para establecer el origen de la aplicación empresarial que contiene las vistas (páginas) de un
     * conjunto de entidades.
     *
     * Este atributo solo tiene efecto si el proyecto generado puede utilizar vistas (páginas) de otros proyectos. Utilice el método
     * setMultiApplication del proyecto maestro para permitir el uso de vistas de otros proyectos.
     *
     * El método setApplicationOrigin debe ejecutarse en el método configureGenerator del proyecto.
     *
     * @param origin origen de las vistas de las entidades del conjunto definido por el siguiente parámetro; consta del protocolo, el nombre o
     * dirección IP del servidor y el número de puerto. Por ejemplo, http://86.48.31.84:8080
     *
     * @param moduleClass clase del módulo que contiene las entidades cuyo origen se va a establecer
     */
    public void setApplicationOrigin(String origin, Class<? extends Project> moduleClass) {
        if (moduleClass != null) {
            Project module = getModule(moduleClass);
            if (module != null) {
                for (Entity entity : module.getEntitiesList()) {
                    entity.setApplicationOrigin(origin);
                }
            }
        }
    }

    /**
     * El método setApplicationOrigin se utiliza para establecer el origen de la aplicación empresarial que contiene las vistas (páginas) de un
     * conjunto de entidades.
     *
     * Este atributo solo tiene efecto si el proyecto generado puede utilizar vistas (páginas) de otros proyectos. Utilice el método
     * setMultiApplication del proyecto maestro para permitir el uso de vistas de otros proyectos.
     *
     * El método setApplicationOrigin debe ejecutarse en el método configureGenerator del proyecto.
     *
     * @param origin origen de las vistas de las entidades del conjunto definido por el siguiente parámetro; consta del protocolo, el nombre o
     * dirección IP del servidor y el número de puerto. Por ejemplo, http://86.48.31.84:8080
     *
     * @param entityClasses una o más clases de entidades cuyo origen se va a establecer
     */
    public void setApplicationOrigin(String origin, Class<? extends Entity>... entityClasses) {
        if (entityClasses != null && entityClasses.length > 0) {
            forApplicationOrigin(origin, entityNames(entityClasses));
        }
    }

    private void forApplicationOrigin(String origin, Set<String> entityNames) {
        for (String name : entityNames) {
            Entity entity = getEntity(name);
            if (entity != null) {
                entity.setApplicationOrigin(origin);
            }
        }
    }

    /**
     * El método setApplicationContextRoot se utiliza para establecer la raíz de contexto del módulo Web de la aplicación empresarial que contiene las
     * vistas (páginas) de un conjunto de entidades.
     *
     * Este atributo solo tiene efecto si el proyecto generado puede utilizar vistas (páginas) de otros proyectos. Utilice el método
     * setMultiApplication del proyecto maestro para permitir el uso de vistas de otros proyectos.
     *
     * El método setApplicationContextRoot debe ejecutarse en el método configureGenerator del proyecto.
     *
     * @param contextRoot raíz de contexto del módulo Web de la aplicación que contiene las vistas de las entidades del conjunto definido por el
     * siguiente parámetro. Por ejemplo, <b>showcase102-web</b>
     *
     * @param moduleClass clase del módulo que contiene las entidades cuya raíz de contexto se va a establecer
     */
    public void setApplicationContextRoot(String contextRoot, Class<? extends Project> moduleClass) {
        if (moduleClass != null) {
            Project module = getModule(moduleClass);
            if (module != null) {
                for (Entity entity : module.getEntitiesList()) {
                    entity.setApplicationContextRoot(contextRoot);
                }
            }
        }
    }

    /**
     * El método setApplicationContextRoot se utiliza para establecer la raíz de contexto del módulo Web de la aplicación empresarial que contiene las
     * vistas (páginas) de un conjunto de entidades.
     *
     * Este atributo solo tiene efecto si el proyecto generado puede utilizar vistas (páginas) de otros proyectos. Utilice el método
     * setMultiApplication del proyecto maestro para permitir el uso de vistas de otros proyectos.
     *
     * El método setApplicationContextRoot debe ejecutarse en el método configureGenerator del proyecto.
     *
     * @param contextRoot raíz de contexto del módulo Web de la aplicación que contiene las vistas de las entidades del conjunto definido por el
     * siguiente parámetro. Por ejemplo, <b>showcase102-web</b>
     *
     * @param entityClasses una o más clases de entidades cuya raíz de contexto se va a establecer
     */
    public void setApplicationContextRoot(String contextRoot, Class<? extends Entity>... entityClasses) {
        if (entityClasses != null && entityClasses.length > 0) {
            forApplicationContextRoot(contextRoot, entityNames(entityClasses));
        }
    }

    private void forApplicationContextRoot(String contextRoot, Set<String> entityNames) {
        for (String name : entityNames) {
            Entity entity = getEntity(name);
            if (entity != null) {
                entity.setApplicationContextRoot(contextRoot);
            }
        }
    }

    /**
     * El método setApplicationConsolePath se utiliza para establecer la parte intermedia del path de las vistas (páginas) de procesamiento de un
     * conjunto de entidades.
     *
     * El método setApplicationConsolePath debe ejecutarse en el método configureGenerator del proyecto.
     *
     * @param consolePath parte intermedia del path de las vistas (páginas) de procesamiento de las entidades del conjunto definido por el siguiente
     * parámetro. Por ejemplo, <b>faces/views/base/crop/procesamiento</b>
     *
     * @param moduleClass clase del módulo que contiene las entidades cuya parte intermedia del path se va a establecer
     */
    public void setApplicationConsolePath(String consolePath, Class<? extends Project> moduleClass) {
        if (moduleClass != null) {
            Project module = getModule(moduleClass);
            if (module != null) {
                for (Entity entity : module.getEntitiesList()) {
                    entity.setApplicationConsolePath(consolePath);
                }
            }
        }
    }

    /**
     * El método setApplicationConsolePath se utiliza para establecer la parte intermedia del path de las vistas (páginas) de procesamiento de un
     * conjunto de entidades.
     *
     * El método setApplicationConsolePath debe ejecutarse en el método configureGenerator del proyecto.
     *
     * @param consolePath parte intermedia del path de las vistas (páginas) de procesamiento de las entidades del conjunto definido por el siguiente
     * parámetro. Por ejemplo, <b>faces/views/base/crop/procesamiento</b>
     *
     * @param entityClasses una o más clases de entidades cuya parte intermedia del path se va a establecer
     */
    public void setApplicationConsolePath(String consolePath, Class<? extends Entity>... entityClasses) {
        if (entityClasses != null && entityClasses.length > 0) {
            forApplicationConsolePath(consolePath, entityNames(entityClasses));
        }
    }

    private void forApplicationConsolePath(String consolePath, Set<String> entityNames) {
        for (String name : entityNames) {
            Entity entity = getEntity(name);
            if (entity != null) {
                entity.setApplicationConsolePath(consolePath);
            }
        }
    }

    /**
     * El método setApplicationReadingPath se utiliza para establecer la parte intermedia del path de las vistas (páginas) de consulta de un conjunto
     * de entidades.
     *
     * El método setApplicationReadingPath debe ejecutarse en el método configureGenerator del proyecto.
     *
     * @param readingPath parte intermedia del path de las vistas (páginas) de consulta de las entidades del conjunto definido por el siguiente
     * parámetro. Por ejemplo, <b>faces/views/base/crop/consulta</b>
     *
     * @param moduleClass clase del módulo que contiene las entidades cuya parte intermedia del path se va a establecer
     */
    public void setApplicationReadingPath(String readingPath, Class<? extends Project> moduleClass) {
        if (moduleClass != null) {
            Project module = getModule(moduleClass);
            if (module != null) {
                for (Entity entity : module.getEntitiesList()) {
                    entity.setApplicationReadingPath(readingPath);
                }
            }
        }
    }

    /**
     * El método setApplicationReadingPath se utiliza para establecer la parte intermedia del path de las vistas (páginas) de consulta de un conjunto
     * de entidades.
     *
     * El método setApplicationReadingPath debe ejecutarse en el método configureGenerator del proyecto.
     *
     * @param readingPath parte intermedia del path de las vistas (páginas) de consulta de las entidades del conjunto definido por el siguiente
     * parámetro. Por ejemplo, <b>faces/views/base/crop/consulta</b>
     *
     * @param entityClasses una o más clases de entidades cuya parte intermedia del path se va a establecer
     */
    public void setApplicationReadingPath(String readingPath, Class<? extends Entity>... entityClasses) {
        if (entityClasses != null && entityClasses.length > 0) {
            forApplicationReadingPath(readingPath, entityNames(entityClasses));
        }
    }

    private void forApplicationReadingPath(String readingPath, Set<String> entityNames) {
        for (String name : entityNames) {
            Entity entity = getEntity(name);
            if (entity != null) {
                entity.setApplicationReadingPath(readingPath);
            }
        }
    }

    /**
     * El método setApplicationWritingPath se utiliza para establecer la parte intermedia del path de las vistas (páginas) de registro de un conjunto
     * de entidades.
     *
     * El método setApplicationWritingPath debe ejecutarse en el método configureGenerator del proyecto.
     *
     * @param writingPath parte intermedia del path de las vistas (páginas) de registro de las entidades del conjunto definido por el siguiente
     * parámetro. Por ejemplo, <b>faces/views/base/crop/registro</b>
     *
     * @param moduleClass clase del módulo que contiene las entidades cuya parte intermedia del path se va a establecer
     */
    public void setApplicationWritingPath(String writingPath, Class<? extends Project> moduleClass) {
        if (moduleClass != null) {
            Project module = getModule(moduleClass);
            if (module != null) {
                for (Entity entity : module.getEntitiesList()) {
                    entity.setApplicationWritingPath(writingPath);
                }
            }
        }
    }

    /**
     * El método setApplicationWritingPath se utiliza para establecer la parte intermedia del path de las vistas (páginas) de registro de un conjunto
     * de entidades.
     *
     * El método setApplicationWritingPath debe ejecutarse en el método configureGenerator del proyecto.
     *
     * @param writingPath parte intermedia del path de las vistas (páginas) de registro de las entidades del conjunto definido por el siguiente
     * parámetro. Por ejemplo, <b>faces/views/base/crop/registro</b>
     *
     * @param entityClasses una o más clases de entidades cuya parte intermedia del path se va a establecer
     */
    public void setApplicationWritingPath(String writingPath, Class<? extends Entity>... entityClasses) {
        if (entityClasses != null && entityClasses.length > 0) {
            forApplicationWritingPath(writingPath, entityNames(entityClasses));
        }
    }

    private void forApplicationWritingPath(String writingPath, Set<String> entityNames) {
        for (String name : entityNames) {
            Entity entity = getEntity(name);
            if (entity != null) {
                entity.setApplicationWritingPath(writingPath);
            }
        }
    }

    /**
     * El método setBplCodeGenEnabled se utiliza para especificar si se debe, o no, generar código BPL (Business Process Logic) para un conjunto de
     * entidades.
     *
     * El método setBplCodeGenEnabled debe ejecutarse en el método configureGenerator del proyecto.
     *
     * @param enabled true o false para generar, o no, código BPL para las entidades del conjunto definido por el parámetro moduleClass
     * @param moduleClass clase del módulo que contiene las entidades cuya generación de código BPL se va a establecer
     */
    public void setBplCodeGenEnabled(boolean enabled, Class<? extends Project> moduleClass) {
        setBplCodeGenEnabled(enabled, false, moduleClass);
    }

    /**
     * El método setBplCodeGenEnabled se utiliza para especificar si se debe, o no, generar código BPL (Business Process Logic) para un conjunto de
     * entidades.El método setBplCodeGenEnabled debe ejecutarse en el método configureGenerator del proyecto.
     *
     *
     * @param enabled true o false para generar, o no, código BPL para las entidades del conjunto definido por el parámetro moduleClass
     * @param updateOnlyEntities true para actualizar solo las entidades; o false, para actualizar el atributo de las entidades y sus operaciones
     * @param moduleClass clase del módulo que contiene las entidades cuya generación de código BPL se va a establecer
     */
    public void setBplCodeGenEnabled(boolean enabled, boolean updateOnlyEntities, Class<? extends Project> moduleClass) {
        if (moduleClass != null) {
            Project module = getModule(moduleClass);
            if (module != null) {
                for (Entity entity : module.getEntitiesList()) {
                    forBplCodeGenEnabled(enabled, updateOnlyEntities, entity);
                }
            }
        }
    }

    /**
     * El método setBplCodeGenEnabled se utiliza para especificar si se debe, o no, generar código BPL (Business Process Logic) para un conjunto de
     * entidades.
     *
     * El método setBplCodeGenEnabled debe ejecutarse en el método configureGenerator del proyecto.
     *
     * @param enabled true o false para generar, o no, código BPL para las entidades del conjunto definido por el parámetro entityClasses
     * @param entityClasses una o más clases de entidades cuya generación de código BPL se va a establecer
     */
    public void setBplCodeGenEnabled(boolean enabled, Class<? extends Entity>... entityClasses) {
        setBplCodeGenEnabled(enabled, false, entityClasses);
    }

    /**
     * El método setBplCodeGenEnabled se utiliza para especificar si se debe, o no, generar código BPL (Business Process Logic) para un conjunto de
     * entidades.
     *
     * El método setBplCodeGenEnabled debe ejecutarse en el método configureGenerator del proyecto.
     *
     * @param enabled true o false para generar, o no, código BPL para las entidades del conjunto definido por el parámetro entityClasses
     * @param updateOnlyEntities true para actualizar solo las entidades; o false, para actualizar el atributo de las entidades y sus operaciones
     * @param entityClasses una o más clases de entidades cuya generación de código BPL se va a establecer
     */
    public void setBplCodeGenEnabled(boolean enabled, boolean updateOnlyEntities, Class<? extends Entity>... entityClasses) {
        if (entityClasses != null && entityClasses.length > 0) {
            forBplCodeGenEnabled(enabled, updateOnlyEntities, entityNames(entityClasses));
        }
    }

    private void forBplCodeGenEnabled(boolean enabled, boolean updateOnlyEntities, Set<String> entityNames) {
        for (String name : entityNames) {
            Entity entity = getEntity(name);
            if (entity != null) {
                forBplCodeGenEnabled(enabled, updateOnlyEntities, entity);
            }
        }
    }

    private void forBplCodeGenEnabled(boolean enabled, boolean updateOnlyEntities, Entity entity) {
        if (isOptionalBplCodeGen(entity)) {
            entity.setBplCodeGenEnabled(enabled);
            if (!updateOnlyEntities) {
                for (Operation operation : entity.getOperationsList()) {
                    if (operation instanceof ProcessOperation) {
                        ProcessOperation processOperation = (ProcessOperation) operation;
                        processOperation.setBplCodeGenEnabled(enabled);
                    }
                }
            }
        }
    }

    /**
     * El método setBwsCodeGenEnabled se utiliza para especificar si se debe, o no, generar código BWS (Business Web Service) para un conjunto de
     * entidades.
     *
     * El método setBwsCodeGenEnabled debe ejecutarse en el método configureGenerator del proyecto.
     *
     * @param enabled true o false para generar, o no, código BWS para las entidades del conjunto definido por el parámetro moduleClass
     * @param moduleClass clase del módulo que contiene las entidades cuya generación de código BWS se va a establecer
     */
    public void setBwsCodeGenEnabled(boolean enabled, Class<? extends Project> moduleClass) {
        if (moduleClass != null) {
            Project module = getModule(moduleClass);
            if (module != null) {
                for (Entity entity : module.getEntitiesList()) {
                    entity.setBwsCodeGenEnabled(enabled);
                }
            }
        }
    }

    /**
     * El método setBwsCodeGenEnabled se utiliza para especificar si se debe, o no, generar código BWS (Business Web Service) para un conjunto de
     * entidades.
     *
     * El método setBwsCodeGenEnabled debe ejecutarse en el método configureGenerator del proyecto.
     *
     * @param enabled true o false para generar, o no, código BWS para las entidades del conjunto definido por el parámetro entityClasses
     * @param entityClasses una o más clases de entidades cuya generación de código BWS se va a establecer
     */
    public void setBwsCodeGenEnabled(boolean enabled, Class<? extends Entity>... entityClasses) {
        if (entityClasses != null && entityClasses.length > 0) {
            forBwsCodeGenEnabled(enabled, entityNames(entityClasses));
        }
    }

    private void forBwsCodeGenEnabled(boolean enabled, Set<String> entityNames) {
        for (String name : entityNames) {
            Entity entity = getEntity(name);
            if (entity != null) {
                entity.setBwsCodeGenEnabled(enabled);
            }
        }
    }

    /**
     * El método setDafCodeGenEnabled se utiliza para especificar si se debe, o no, generar una fachada de acceso a datos (código DAF, por las siglas
     * en inglés de Data Access Façade) para un conjunto de entidades.
     *
     * El método setDafCodeGenEnabled debe ejecutarse en el método configureGenerator del proyecto.
     *
     * @param enabled true o false para generar, o no, código DAF para las entidades del conjunto definido por el parámetro moduleClass
     * @param moduleClass clase del módulo que contiene las entidades cuya generación de código DAF se va a establecer
     */
    public void setDafCodeGenEnabled(boolean enabled, Class<? extends Project> moduleClass) {
        if (moduleClass != null) {
            Project module = getModule(moduleClass);
            if (module != null) {
                for (Entity entity : module.getEntitiesList()) {
                    if (isOptionalDafCodeGen(entity)) {
                        entity.setDafCodeGenEnabled(enabled);
                    }
                }
            }
        }
    }

    /**
     * El método setDafCodeGenEnabled se utiliza para especificar si se debe, o no, generar una fachada de acceso a datos (código DAF, por las siglas
     * en inglés de Data Access Façade) para un conjunto de entidades.
     *
     * El método setDafCodeGenEnabled debe ejecutarse en el método configureGenerator del proyecto.
     *
     * @param enabled true o false para generar, o no, código DAF para las entidades del conjunto definido por el parámetro entityClasses
     * @param entityClasses una o más clases de entidades cuya generación de código DAF se va a establecer
     */
    public void setDafCodeGenEnabled(boolean enabled, Class<? extends Entity>... entityClasses) {
        if (entityClasses != null && entityClasses.length > 0) {
            forDafCodeGenEnabled(enabled, entityNames(entityClasses));
        }
    }

    private void forDafCodeGenEnabled(boolean enabled, Set<String> entityNames) {
        for (String name : entityNames) {
            Entity entity = getEntity(name);
            if (entity != null) {
                if (isOptionalDafCodeGen(entity)) {
                    entity.setDafCodeGenEnabled(enabled);
                }
            }
        }
    }

    /**
     * El método setFwsCodeGenEnabled se utiliza para especificar si se debe, o no, generar código FWS (Façade Web Service) para un conjunto de
     * entidades.
     *
     * El método setFwsCodeGenEnabled debe ejecutarse en el método configureGenerator del proyecto.
     *
     * @param enabled true o false para generar, o no, código FWS para las entidades del conjunto definido por el parámetro moduleClass
     * @param moduleClass clase del módulo que contiene las entidades cuya generación de código FWS se va a establecer
     */
    public void setFwsCodeGenEnabled(boolean enabled, Class<? extends Project> moduleClass) {
        if (moduleClass != null) {
            Project module = getModule(moduleClass);
            if (module != null) {
                for (Entity entity : module.getEntitiesList()) {
                    entity.setFwsCodeGenEnabled(enabled);
                }
            }
        }
    }

    /**
     * El método setFwsCodeGenEnabled se utiliza para especificar si se debe, o no, generar código FWS (Façade Web Service) para un conjunto de
     * entidades.
     *
     * El método setFwsCodeGenEnabled debe ejecutarse en el método configureGenerator del proyecto.
     *
     * @param enabled true o false para generar, o no, código FWS para las entidades del conjunto definido por el parámetro entityClasses
     * @param entityClasses una o más clases de entidades cuya generación de código FWS se va a establecer
     */
    public void setFwsCodeGenEnabled(boolean enabled, Class<? extends Entity>... entityClasses) {
        if (entityClasses != null && entityClasses.length > 0) {
            forFwsCodeGenEnabled(enabled, entityNames(entityClasses));
        }
    }

    private void forFwsCodeGenEnabled(boolean enabled, Set<String> entityNames) {
        for (String name : entityNames) {
            Entity entity = getEntity(name);
            if (entity != null) {
                entity.setFwsCodeGenEnabled(enabled);
            }
        }
    }

    private Set<String> entityNames(Class<? extends Entity>... entityClasses) {
        Set<String> entityNames = new LinkedHashSet<>();
        for (Class<?> entityClass : entityClasses) {
            if (Entity.class.isAssignableFrom(entityClass)) {
                entityNames.add(entityClass.getSimpleName());
            }
        }
        return entityNames;
    }

    /**
     * @return the processing groups set
     */
    public Set<String> getProcessingGroups() {
        return _processingGroups;
    }

    /**
     * @return the user flows set
     */
    public Set<UserFlow> getUserFlows() {
        return _userFlows;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="evaluate predicates">
    public boolean evaluateEntity(String entityName, String predicateName) {
        if (entityName != null && predicateName != null) {
            Entity entity = getEntity(entityName);
            if (entity != null) {
                boolean not = predicateName.startsWith("!");
                String string = not ? predicateName.substring(1) : predicateName;
                String prefix = string.contains(".") ? "" : "adalid.core.predicates.";
                Object object = XS1.newInstance(prefix + string);
                if (object instanceof org.apache.commons.collections.Predicate) {
                    org.apache.commons.collections.Predicate predicate = (org.apache.commons.collections.Predicate) object;
                    boolean b = predicate.evaluate(entity);
                    return not ? !b : b;
                }
            }
        }
        return false;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="VSEC">
    public Class<? extends Entity> getUploadedFileEntityClass() {
        return null;
    }

    public Class<? extends Entity> getUserEntityClass() {
        return null;
    }

    public List<Class<? extends Entity>> unsetSpecialEntityClasses() {
        return null;
    }

    public List<NativeQuerySegment> getSpecialNativeQuerySegments(Entity entity) {
        return null;
    }
    // </editor-fold>

    public Project() {
        super();
        init();
    }

    private void init() {
        Class<?> namedClass = getNamedClass();
        String className = namedClass.getSimpleName();
        setDeclared(className);
    }

    /**
     * El método loadPrivateProperties se utiliza para obtener el valor de las variables del proyecto (sin incluir las variables de ambiente, las
     * cuales se obtienen con el método loadEnvironmentVariables) definidas en el archivo private.properties. Las variables del proyecto son parejas
     * clave/valor, de modo que si se agregan varias variables con la misma clave, el valor de tal variable será el último valor agregado.
     */
    public void loadPrivateProperties() {
        loadPrivateProperties(LoggingLevel.TRACE);
    }

    /**
     * El método loadPrivateProperties se utiliza para obtener el valor de las variables del proyecto (sin incluir las variables de ambiente, las
     * cuales se obtienen con el método loadEnvironmentVariables) definidas en el archivo private.properties. Las variables del proyecto son parejas
     * clave/valor, de modo que si se agregan varias variables con la misma clave, el valor de tal variable será el último valor agregado.
     *
     * @param loggingLevel elemento de la enumeración LoggingLevel que determina el nivel de severidad de los mensajes que se emiten al obtener el
     * valor de las variables del proyecto. Especifique TRACE, DEBUG, INFO, WARN, ERROR o FATAL para emitir los mensajes con ese nivel de severidad.
     * Especifique OFF para no emitir ningún mensaje.
     */
    public void loadPrivateProperties(LoggingLevel loggingLevel) {
        if (loggingLevel != null) {
            ExtendedProperties properties = PropertiesHandler.getPrivateProperties();
            if (properties == null || properties.isEmpty()) {
                _abort = true;
            } else {
                loadPrivateProperties(loggingLevel, properties);
            }
        }
    }

    protected void loadPrivateProperties(LoggingLevel loggingLevel, ExtendedProperties properties) {
        if (loggingLevel != null && properties != null && !properties.isEmpty()) {
            loadPrivateProperties(loggingLevel.getLevel(), properties);
        }
    }

    protected void loadPrivateProperties(Level level, ExtendedProperties properties) {
        assert level != null;
        assert properties != null && !properties.isEmpty();
    }

    @Override
    public String getAlias() {
        String name = getName();
        String alias = super.getAlias();
        return name != null && name.equals(alias) ? name.toLowerCase() : alias;
    }

    private void settle() {
        settleAttributes();
    }

    protected void settleAttributes() {
        track("settleAttributes");
    }

    /**
     * El método clearDirectives se utiliza para borrar la lista de directrices del proyecto.
     */
    public void clearDirectives() {
        track("clearDirectives");
        _fileExclusionPatterns.clear();
        _filePreservationPatterns.clear();
    }

    /**
     * Adds the platform-specific directives
     */
    public void addDirectives() {
        track("addDirectives");
    }

    /**
     * El método addFileExclusionPattern se utiliza para agregar una directriz de exclusión de archivos a la lista de directrices del proyecto. El
     * método puede utilizarse repetidamente para agregar varias directrices.
     *
     * Al ejecutar el proyecto Maestro, no se generarán aquellos archivos cuyos nombres satisfacen alguna de las directrices de exclusión del
     * proyecto; más aún, se eliminarán los archivos previamente generados cuyos nombres satisfacen alguna de tales directrices.
     *
     * @param regex expresión regular para evaluar el nombre de los archivos que se deben excluir
     */
    public void addFileExclusionPattern(String regex) {
        if (StringUtils.isNotBlank(regex)) {
            try {
                _fileExclusionPatterns.add(Pattern.compile(regex));
            } catch (PatternSyntaxException ex) {
                getParser().error(regex + " is an invalid regular expression; file exclusion pattern cannot be added");
            }
        }
    }

    /**
     * El método addFilePreservationPattern se utiliza para agregar una directriz de preservación de archivos a la lista de directrices del proyecto.
     * El método puede utilizarse repetidamente para agregar varias directrices.
     *
     * Al ejecutar el proyecto Maestro, no se generarán aquellos archivos que ya existen y cuyos nombres satisfacen alguna de las directrices de
     * preservación del proyecto.
     *
     * @param regex expresión regular para evaluar el nombre de los archivos que se deben preservar
     */
    public void addFilePreservationPattern(String regex) {
        if (StringUtils.isNotBlank(regex)) {
            try {
                _filePreservationPatterns.add(Pattern.compile(regex));
            } catch (PatternSyntaxException ex) {
                getParser().error(regex + " is an invalid regular expression; file preservation pattern cannot be added");
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="annotate">
    @Override
    public void annotate() {
        super.annotate();
        checkHelpFileAutoName();
        checkHelpFileAutoType();
    }

    @Override
    void annotate(Class<?> type) {
        super.annotate(type);
        if (type != null) {
            annotateMaster(type);
            annotateModule(type);
            annotateModuleDocGen(type);
        }
    }

    @Override
    void annotate(Field field) {
        super.annotate(field);
        if (field != null) {
            annotateModule(field);
            annotateModuleDocGen(field);
        }
    }

    @Override
    protected List<Class<? extends Annotation>> getValidTypeAnnotations() {
        List<Class<? extends Annotation>> valid = super.getValidTypeAnnotations();
        valid.add(MasterProject.class);
        valid.add(ProjectModule.class);
        valid.add(ProjectModuleDocGen.class);
        return valid;
    }

    @Override
    protected List<Class<? extends Annotation>> getValidFieldAnnotations() {
        List<Class<? extends Annotation>> valid = super.getValidFieldAnnotations();
        valid.add(ProjectModule.class);
        valid.add(ProjectModuleDocGen.class);
        return valid;
    }

    private void annotateMaster(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, MasterProject.class);
        if (annotatedClass != null) {
            MasterProject annotation = annotatedClass.getAnnotation(MasterProject.class);
            if (annotation != null) {
                _annotatedWithMasterProject = true;
                _acronym = annotation.acronym();
                _helpFileAutoName = specified(annotation.helpFileAutoName(), _helpFileAutoName);
                _helpFileAutoType = specified(annotation.helpFileAutoType(), _helpFileAutoType);
                String alias = annotation.alias();
                if (StringUtils.isNotBlank(alias)) {
                    setAlias(alias);
                }
                String document = annotation.helpDocument();
                if (StringUtils.isNotBlank(document)) {
                    setHelpDocument(document);
                }
                String fileName = annotation.helpFile();
                if (StringUtils.isNotBlank(fileName)) {
                    setHelpFileName(fileName); // must be executed after setting _helpFileAutoName and _helpFileAutoType
                }
            }
        }
    }

    private void annotateModule(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, ProjectModule.class);
        if (annotatedClass != null) {
            ProjectModule annotation = annotatedClass.getAnnotation(ProjectModule.class);
            if (annotation != null) {
                _annotatedWithProjectModule = true;
                _helpFileAutoName = specified(annotation.helpFileAutoName(), _helpFileAutoName);
                _helpFileAutoType = specified(annotation.helpFileAutoType(), _helpFileAutoType);
                _menuModule = annotation.menu().toBoolean(_menuModule);
                _roleModule = annotation.role().toBoolean(_roleModule);
                _foreignModule = annotation.foreign().toBoolean(_foreignModule);
                _privateModule = annotation.privacy().toBoolean(_privateModule);
                _moduleMenuType = annotation.menuType();
                _moduleRoleTypes = annotation.roleTypes();
                String document = annotation.helpDocument();
                if (StringUtils.isNotBlank(document)) {
                    setHelpDocument(document);
                }
                String fileName = annotation.helpFile();
                if (StringUtils.isNotBlank(fileName)) {
                    setHelpFileName(fileName); // must be executed after setting _helpFileAutoName and _helpFileAutoType
                }
            }
        }
        finalizeModuleAnnotation();
    }

    private void annotateModule(Field field) {
        _annotatedWithProjectModule = field.isAnnotationPresent(ProjectModule.class);
        if (_annotatedWithProjectModule) {
            ProjectModule annotation = field.getAnnotation(ProjectModule.class);
            _helpFileAutoName = specified(annotation.helpFileAutoName(), _helpFileAutoName);
            _helpFileAutoType = specified(annotation.helpFileAutoType(), _helpFileAutoType);
            _menuModule = annotation.menu().toBoolean(_menuModule);
            _roleModule = annotation.role().toBoolean(_roleModule);
            _foreignModule = annotation.foreign().toBoolean(_foreignModule);
            _privateModule = annotation.privacy().toBoolean(_privateModule);
            _moduleMenuType = annotation.menuType();
            _moduleRoleTypes = annotation.roleTypes();
            String document = annotation.helpDocument();
            if (StringUtils.isNotBlank(document)) {
                setHelpDocument(document);
            }
            String fileName = annotation.helpFile();
            if (StringUtils.isNotBlank(fileName)) {
                setHelpFileName(fileName); // must be executed after setting _helpFileAutoName and _helpFileAutoType
            }
        }
        finalizeModuleAnnotation();
    }

    private void finalizeModuleAnnotation() {
        if (_master != null) {
            Class<?> namedClass = getNamedClass();
            if (_foreignModule) {
                _master.addForeignEntityClasses(namedClass);
            }
            if (_privateModule) {
                _master.addPrivateEntityClasses(namedClass);
            }
        }
    }

    private void finalizeModuleAnnotation(Field field) {
        if (_master != null && (StringUtils.isNotBlank(_helpDocument) || StringUtils.isNotBlank(_helpFileName))) {
            Class<?> clazz = field.getDeclaringClass();
            if (clazz.isAnnotationPresent(MasterProject.class)) {
                List<Entity> entities = getEntitiesList();
                for (Entity entity : entities) {
                    if (StringUtils.isBlank(entity.getHelpDocument()) && StringUtils.isNotBlank(_helpDocument)) {
                        entity.setHelpDocument(_helpDocument);
                    }
                    if (StringUtils.isBlank(entity.getHelpFileName()) && StringUtils.isNotBlank(_helpFileName)) {
                        entity.setHelpFileName(_helpFileName);
                    }
                }
            }
        }
    }

    private void annotateModuleDocGen(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, ProjectModuleDocGen.class);
        if (annotatedClass != null) {
            ProjectModuleDocGen annotation = annotatedClass.getAnnotation(ProjectModuleDocGen.class);
            if (annotation != null) {
                _annotatedWithProjectModuleDocGen = true;
                _moduleClassDiagramGenEnabled = annotation.classDiagram().toBoolean(_moduleClassDiagramGenEnabled);
            }
        }
    }

    private void annotateModuleDocGen(Field field) {
        _annotatedWithProjectModuleDocGen = field.isAnnotationPresent(ProjectModuleDocGen.class);
        if (_annotatedWithProjectModuleDocGen) {
            ProjectModuleDocGen annotation = field.getAnnotation(ProjectModuleDocGen.class);
            _moduleClassDiagramGenEnabled = annotation.classDiagram().toBoolean(_moduleClassDiagramGenEnabled);
        }
    }
    // </editor-fold>

    @Override
    public boolean build(String platform) {
        /* until 24/02/2023
        return build() && generate(platform);
        /**/
        Class<? extends Project> clazz = getClass();
        long millis1 = System.currentTimeMillis();
        logger.warn("build and generate platform " + platform + " for project " + RunUtils.starting(clazz));
        boolean built = build();
        logger.warn("build project " + RunUtils.finished(clazz, millis1));
        boolean generated = false;
        if (built) {
            long millis2 = System.currentTimeMillis();
            generated = generate(platform);
            logger.warn("generate platform " + platform + " for project " + RunUtils.finished(clazz, millis2));
        }
        logger.warn("build and generate platform " + platform + " for project " + RunUtils.finished(clazz, millis1));
        return built && generated;
    }

    public boolean build() {
        logger.info(signature("build", getClass().getName()));
        getBuildTimestamp();
        if (PropertiesHandler.missingBootstrappingProperties()) {
            logger.error("build aborted due to missing or invalid bootstrapping properties");
            return false;
        }
//      logAdalidProjectVersion();
        TLC.setProject(this);
        clearArtifacts();
        addArtifact(this);
        annotate();
        configureBuilder();
        _built = parse() && analyze();
        _abort |= !_built;
        return _built;
    }

    private String _buildTimestamp;

    public String getBuildTimestamp() {
        if (_buildTimestamp == null) {
            _buildTimestamp = timestamp().substring(0, 13);
        }
        return _buildTimestamp;
    }

    public String getBuildDate() {
        String timestamp = getBuildTimestamp(); // yyyyMMdd-HHmm
        return timestamp.substring(0, 8);
    }

    private String timestamp() {
        return TimeUtils.simpleTimestampString(TimeUtils.actualTimestamp()); // yyyyMMdd-HHmmss-SSS;
    }

    private final ProjectObjectModelReader pom = new adalid.core.ProjectObjectModel();

    public ProjectObjectModelReader getProjectObjectModel() {
        return pom;
    }

    public String getAdalidProjectVersion() {
        return pom.getProjectVersionNumber();
    }

    protected void logAdalidProjectVersion() {
        pom.logProjectVersion();
    }

    /**
     * set builder settings
     */
    public void configureBuilder() {
        track("configureBuilder");
    }

    protected boolean parse() {
        logger.info(signature("parse", getClass().getName()));
        TLC.setProject(this);
        return getParser().parse();
    }

    protected boolean analyze() {
        logger.info(signature("analyze", getClass().getName()));
        TLC.setProject(this);
        configureAnalyzer();
        List<Project> modulesList = getModulesList();
        Collections.sort(modulesList);
        boolean analyzed = true;
        String name;
        for (Project module : modulesList) {
            analyzed &= module.assemble();
            if (analyzed) {
                List<? extends Display> displaysList = module.getDisplaysList();
                for (Display display : displaysList) {
                    name = display.getName();
                    if (_displays.containsKey(name)) {
                    } else {
                        _displays.put(name, display);
                    }
                }
                continue;
            }
            break;
        }
        return analyzed;
    }

    protected boolean assemble() {
        log(_detailLevel, signature("assemble", getClass().getName()));
        return true;
    }

    public boolean generate(String platform) {
        logger.info(signature("generate", "platform=" + platform));
        TLC.setProject(this);
        boolean fee = readyToWrite(platform);
        boolean faa = checkBootstrappingProperties();
        boolean foo = checkProjectAlias();
        if (_abort || !fee || !faa || !foo) {
            logger.error("generation aborted due to previous errors");
            return false;
        }
        if (!_built) {
            logger.error("project was not built; generation aborted");
            return false;
        }
        configureGenerator();
        disablePrivateAndOtherContextEntitiesCodeGen();
        configureWriter();
        Writer writer = getWriter();
        writer.setFileExclusionPatterns(_fileExclusionPatterns);
        writer.setFilePreservationPatterns(_filePreservationPatterns);
        writer.setAvailableResourceNames(getEntitiesMap().keySet());
        writer.setForeignResourceNames(foreignEntitySimpleNames());
        writer.setPrivateResourceNames(privateEntitySimpleNames());
        boolean b1 = writer.write(platform);
        boolean b2 = afterWriting(b1);
        boolean ok = b1 && b2;
        printSummary(ok);
        return ok;
    }

    protected void disablePrivateAndOtherContextEntitiesCodeGen() {
        if (disableEntitiesCodeGen()) {
            for (Entity entity : getEntitiesList()) {
                if (entity.isPrivateEntityClass() || !entity.isApplicationDefaultLocation()) {
                    if (_disablePrivateAndOtherContextEntitiesBplCodeGen) {
                        if (isOptionalBplCodeGen(entity)) {
                            entity.setBplCodeGenEnabled(false);
                        }
                    }
                    if (_disablePrivateAndOtherContextEntitiesBwsCodeGen) {
                        entity.setBwsCodeGenEnabled(false);
                    }
                    if (_disablePrivateAndOtherContextEntitiesDafCodeGen) {
                        if (isOptionalDafCodeGen(entity)) {
                            entity.setDafCodeGenEnabled(false);
                        }
                    }
                    if (_disablePrivateAndOtherContextEntitiesFwsCodeGen) {
                        entity.setFwsCodeGenEnabled(false);
                    }
                }
            }
        }
    }

    private boolean disableEntitiesCodeGen() {
        return _disablePrivateAndOtherContextEntitiesBplCodeGen
            || _disablePrivateAndOtherContextEntitiesBwsCodeGen
            || _disablePrivateAndOtherContextEntitiesDafCodeGen
            || _disablePrivateAndOtherContextEntitiesFwsCodeGen;
    }

    protected boolean isOptionalBplCodeGen(Entity entity) {
        return entity != null;
    }

    protected boolean isOptionalDafCodeGen(Entity entity) {
        return entity != null;
    }

    private Set<String> foreignEntitySimpleNames() {
        Set<String> set = new LinkedHashSet<>();
        List<Entity> list = getEntitiesList();
        for (Entity entity : list) {
            if (entity.isForeignEntityClass()) {
                set.add(entity.getClass().getSimpleName());
            }
        }
        return set;
    }

    private Set<String> privateEntitySimpleNames() {
        Set<String> set = new LinkedHashSet<>();
        List<Entity> list = getEntitiesList();
        for (Entity entity : list) {
            if (entity.isPrivateEntityClass() || !entity.isApplicationDefaultLocation()) {
                set.add(entity.getClass().getSimpleName());
            }
        }
        return set;
    }

    @Override
    public boolean beforeWriting() {
        return true;
    }

    protected boolean afterWriting(boolean ok) {
        return ok;
    }

    protected void printSummary(boolean ok) {
        String alias = getAlias();
        if (ok) {
            logger.info("project " + alias + " successfully generated");
        } else {
            logger.warn("project " + alias + " generated with errors");
        }
    }

    /**
     * set analyzer settings
     */
    public void configureAnalyzer() {
        track("configureAnalyzer");
    }

    /**
     * set generator settings
     */
    public void configureGenerator() {
        track("configureGenerator");
        clearDirectives();
        addDirectives();
        clearArtifactsAttributes();
        attachAddAttributesMethods();
        invokeAddAttributesMethods();
        addArtifactsAttributes();
    }

    protected boolean readyToWrite(String platform) {
        logger.trace(signature("readyToWrite", "platform=" + platform));
        return true;
    }

    private boolean checkBootstrappingProperties() {
        ExtendedProperties bootstrapping = PropertiesHandler.getBootstrapping();
        return bootstrapping != null && !bootstrapping.isEmpty();
    }

    private boolean checkProjectAlias() {
        String alias = getAlias();
        if (StringUtils.isBlank(alias)) {
            logger.error("unspecified project alias");
            return false;
        } else if (!alias.matches("^[a-z][a-z0-9]*$")) {
            logger.error(alias + " is an invalid project alias");
            return false;
        } else if (alias.equalsIgnoreCase("meta") || alias.equalsIgnoreCase("workspace")) {
            logger.error(alias + " is a restricted project alias");
            return false;
        }
        String ALIAS = alias.toUpperCase();
        String acronym = getAcronym();
        String instead = "; the alias of the project in uppercase will be used instead";
        if (StringUtils.isBlank(acronym)) {
            logger.info("unspecified project acronym" + instead);
            setAcronym(ALIAS);
        } else if (!acronym.matches("^[A-Za-z][A-Za-z0-9]*$")) {
            logger.warn(acronym + " is an invalid project acronym; acronym" + instead);
            setAcronym(ALIAS);
        } else if (acronym.equalsIgnoreCase("meta") || acronym.equalsIgnoreCase("workspace")) {
            logger.warn(acronym + " is a restricted project acronym" + instead);
            setAcronym(ALIAS);
        }
        return true;
    }

    private void configureWriter() {
        Writer.setAlertLevel(_alertLevel);
        Writer.setDetailLevel(_detailLevel);
        Writer.setTrackingLevel(_trackingLevel);
    }

    private String signature(String method, Object... parameters) {
        String pattern = "{0}({1})";
        return MessageFormat.format(pattern, method, StringUtils.join(parameters, ", "));
    }

    public Display getDisplayOf(String name) {
        if (StringUtils.isNotBlank(name)) {
            List<? extends Display> displays = getDisplaysList();
            for (Display display : displays) {
                if (name.equals(display.getName())) {
                    return display;
                }
            }
        }
        return null;
    }

    // <editor-fold defaultstate="collapsed" desc="entity display getters">
    public Display getReadingTableDisplayOf(Entity entity) {
        return getDisplayOf(entity, DisplayMode.READING, DisplayFormat.TABLE);
    }

    public Display getReadingDetailDisplayOf(Entity entity) {
        return getDisplayOf(entity, DisplayMode.READING, DisplayFormat.DETAIL);
    }

    public Display getReadingTreeDisplayOf(Entity entity) {
        return getDisplayOf(entity, DisplayMode.READING, DisplayFormat.TREE);
    }

    public Display getWritingTableDisplayOf(Entity entity) {
        return getDisplayOf(entity, DisplayMode.WRITING, DisplayFormat.TABLE);
    }

    public Display getWritingDetailDisplayOf(Entity entity) {
        return getDisplayOf(entity, DisplayMode.WRITING, DisplayFormat.DETAIL);
    }

    public Display getWritingTreeDisplayOf(Entity entity) {
        return getDisplayOf(entity, DisplayMode.WRITING, DisplayFormat.TREE);
    }

    public Display getProcessingConsoleDisplayOf(Entity entity) {
        return getDisplayOf(entity, DisplayMode.PROCESSING, DisplayFormat.CONSOLE);
    }

    private Display getDisplayOf(Entity entity, DisplayMode mode, DisplayFormat format) {
        if (entity == null) {
            return null;
        }
        Entity displayEntity;
        Entity displayMaster;
        DisplayMode displayMode;
        DisplayFormat displayFormat;
        List<? extends Display> displays = getDisplaysList();
        for (Display display : displays) {
            displayEntity = display.getEntity();
            displayMaster = display.getMaster();
            displayMode = display.getDisplayMode();
            displayFormat = display.getDisplayFormat();
            if (entity.equals(displayEntity) && displayMaster == null && mode.equals(displayMode) && format.equals(displayFormat)) {
                logger.debug(entity.getName() + " " + mode + " " + format + " display is " + display.getName() + " @ " + getName());
                return display;
            }
        }
        logger.debug(entity.getName() + " " + mode + " " + format + " display is not @ " + getName());
        return null;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="entity display getters">
    public Display getReadingTableDisplayOf(Entity detail, Entity master, EntityReference reference) {
        return getDisplayOf(detail, master, reference, DisplayMode.READING, DisplayFormat.TABLE);
    }

    public Display getReadingDetailDisplayOf(Entity detail, Entity master, EntityReference reference) {
        return getDisplayOf(detail, master, reference, DisplayMode.READING, DisplayFormat.DETAIL);
    }

    public Display getWritingTableDisplayOf(Entity detail, Entity master, EntityReference reference) {
        return getDisplayOf(detail, master, reference, DisplayMode.WRITING, DisplayFormat.TABLE);
    }

    public Display getWritingDetailDisplayOf(Entity detail, Entity master, EntityReference reference) {
        return getDisplayOf(detail, master, reference, DisplayMode.WRITING, DisplayFormat.DETAIL);
    }

    private Display getDisplayOf(Entity detail, Entity master, EntityReference reference, DisplayMode mode, DisplayFormat format) {
        if (detail == null || master == null || reference == null) {
            return null;
        }
        Entity displayEntity;
        Entity displayMaster;
        EntityReference displayReference;
        DisplayMode displayMode;
        DisplayFormat displayFormat;
        List<? extends Display> displays = getDisplaysList();
        for (Display display : displays) {
            displayEntity = display.getEntity();
            displayMaster = display.getMaster();
            displayReference = display.getReference();
            displayMode = display.getDisplayMode();
            displayFormat = display.getDisplayFormat();
            if (detail.equals(displayEntity) && master.equals(displayMaster) && displayReference.equals(reference) && mode.equals(displayMode) && format.equals(displayFormat)) {
                logger.debug(detail.getName() + " " + mode + " " + format + " display is " + display.getName() + " @ " + getName());
                return display;
            }
        }
        logger.debug(detail.getName() + " " + mode + " " + format + " display is not @ " + getName());
        return null;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="sibling display getters">
    public Display getTableSiblingOf(Display display) {
        return getSiblingOf(display, DisplayFormat.TABLE);
    }

    public Display getDetailSiblingOf(Display display) {
        return getSiblingOf(display, DisplayFormat.DETAIL);
    }

    public Display getTreeSiblingOf(Display display) {
        return getSiblingOf(display, DisplayFormat.TREE);
    }

    public Display getConsoleSiblingOf(Display display) {
        return getSiblingOf(display, DisplayFormat.CONSOLE);
    }

    private Display getSiblingOf(Display display, DisplayFormat format) {
        if (display == null) {
            return null;
        }
        Entity displayEntity = display.getEntity();
        Entity displayMaster = display.getMaster();
        if (displayEntity == null) {
            return null;
        }
        DisplayMode displayMode = display.getDisplayMode();
        DisplayFormat displayFormat = display.getDisplayFormat();
        if (displayMode == null || displayFormat == null) {
            return null;
        }
        DisplayMode mode = DisplayFormat.CONSOLE.equals(format) ? DisplayMode.PROCESSING
            : DisplayFormat.CONSOLE.equals(displayFormat) ? DisplayMode.UNSPECIFIED
            : displayMode;
        /**/
        Entity siblingEntity;
        Entity siblingMaster;
        DisplayMode siblingMode;
        DisplayFormat siblingFormat;
        List<? extends Display> siblings = getDisplaysList();
        for (Display sibling : siblings) {
            if (sibling.equals(display)) {
                continue;
            }
            siblingEntity = sibling.getEntity();
            siblingMaster = sibling.getMaster();
            siblingMode = sibling.getDisplayMode();
            siblingFormat = sibling.getDisplayFormat();
            if (displayEntity.equals(siblingEntity)) {
                if (format.equals(DisplayFormat.UNSPECIFIED) || format.equals(siblingFormat)) {
                    if (mode.equals(DisplayMode.UNSPECIFIED) || mode.equals(siblingMode)) {
                        if (siblingMaster == null && displayMaster == null) {
                            logger.debug(display.getName() + " " + format + " sibling is " + sibling.getName() + " @ " + getName());
                            return sibling;
                        }
                        if (siblingMaster != null && siblingMaster.equals(displayMaster)) {
                            logger.debug(display.getName() + " " + format + " sibling is " + sibling.getName() + " @ " + getName());
                            return sibling;
                        }
                    }
                }
            }
        }
        logger.debug(display.getName() + " " + format + " sibling is not @ " + getName());
        return null;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="cousin display getters">
    public Display getTableCousinOf(Display display) {
        return getCousinOf(display, DisplayFormat.TABLE);
    }

    public Display getDetailCousinOf(Display display) {
        return getCousinOf(display, DisplayFormat.DETAIL);
    }

    public Display getTreeCousinOf(Display display) {
        return getCousinOf(display, DisplayFormat.TREE);
    }

    public Display getConsoleCousinOf(Display display) {
        return getCousinOf(display, DisplayFormat.CONSOLE);
    }

    private Display getCousinOf(Display display, DisplayFormat format) {
        if (display == null) {
            return null;
        }
        Entity displayEntity = display.getEntity();
//      Entity displayMaster = display.getMaster();
        if (displayEntity == null) {
            return null;
        }
        DisplayMode displayMode = display.getDisplayMode();
        DisplayFormat displayFormat = display.getDisplayFormat();
        if (displayMode == null || displayFormat == null) {
            return null;
        }
        DisplayMode mode = DisplayFormat.CONSOLE.equals(format) ? DisplayMode.PROCESSING
            : DisplayFormat.CONSOLE.equals(displayFormat) ? DisplayMode.UNSPECIFIED
            : displayMode;
        /**/
        Entity cousinEntity;
        Entity cousinMaster;
        DisplayMode cousinMode;
        DisplayFormat cousinFormat;
        List<? extends Display> cousins = getDisplaysList();
        for (Display cousin : cousins) {
            if (cousin.equals(display)) {
                continue;
            }
            cousinEntity = cousin.getEntity();
            cousinMaster = cousin.getMaster();
            cousinMode = cousin.getDisplayMode();
            cousinFormat = cousin.getDisplayFormat();
            if (displayEntity.equals(cousinEntity)) {
                if (format.equals(DisplayFormat.UNSPECIFIED) || format.equals(cousinFormat)) {
                    if (mode.equals(DisplayMode.UNSPECIFIED) || mode.equals(cousinMode)) {
                        if (cousinMaster == null) {
                            logger.debug(display.getName() + " " + format + " cousin is " + cousin.getName() + " @ " + getName());
                            return cousin;
                        }
                    }
                }
            }
        }
        logger.debug(display.getName() + " " + format + " cousin is not @ " + getName());
        return null;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="reference display getters">
    public AlternativeDisplay getReadingTableAlternativeTo(Display display, Entity entity) {
        return getAlternativeTo(display, entity, DisplayMode.READING, DisplayFormat.TABLE);
    }

    public AlternativeDisplay getReadingDetailAlternativeTo(Display display, Entity entity) {
        return getAlternativeTo(display, entity, DisplayMode.READING, DisplayFormat.DETAIL);
    }

    public AlternativeDisplay getReadingTreeAlternativeTo(Display display, Entity entity) {
        return getAlternativeTo(display, entity, DisplayMode.READING, DisplayFormat.TREE);
    }

    public AlternativeDisplay getWritingTableAlternativeTo(Display display, Entity entity) {
        return getAlternativeTo(display, entity, DisplayMode.WRITING, DisplayFormat.TABLE);
    }

    public AlternativeDisplay getWritingDetailAlternativeTo(Display display, Entity entity) {
        return getAlternativeTo(display, entity, DisplayMode.WRITING, DisplayFormat.DETAIL);
    }

    public AlternativeDisplay getWritingTreeAlternativeTo(Display display, Entity entity) {
        return getAlternativeTo(display, entity, DisplayMode.WRITING, DisplayFormat.TREE);
    }

    public AlternativeDisplay getProcessingConsoleAlternativeTo(Display display, Entity entity) {
        return getAlternativeTo(display, entity, DisplayMode.PROCESSING, DisplayFormat.CONSOLE);
    }

    private AlternativeDisplay getAlternativeTo(Display display, Entity entity, DisplayMode mode, DisplayFormat format) {
        if (display == null || entity == null || format == null || mode == null) {
            return null;
        }
        String parameters = display.getName() + "-" + entity.getName() + "-" + mode + "-" + format;
        List<? extends Display> displays = getDisplaysList();
        // look for an existentially independent alternative
        AlternativeDisplay alternative = getAlternativeTo(display, entity, mode, format, displays);
        if (alternative != null) {
            return alternative;
        }
        Entity thisEntity = display.getEntity();
        if (thisEntity == null) {
            return null;
        }
        if (thisEntity.equals(entity)) {
            return null; // avoid recursion
        }
        // look for a master/detail alternative
        List<Entity> someEntities;
        final String[] kinships = {"dependent", "collateral"}; // offspring, sibling
        for (String kinship : kinships) {
            someEntities = someEntityList(display, kinship);
            if (someEntities == null || someEntities.isEmpty()) {
                continue;
            }
            for (Entity someEntity : someEntities) {
                // look for a master/detail alternative with explicit reference
                alternative = getAlternativeTo(display, entity, mode, format, displays, "explicit", kinship, someEntity);
                if (alternative != null) {
                    return alternative;
                }
                // look for a master/detail alternative with implicit reference
                alternative = getAlternativeTo(display, entity, mode, format, displays, "implicit", kinship, someEntity);
                if (alternative != null) {
                    return alternative;
                }
            }
        }
        logger.debug(parameters + " alternative is not @ " + getName());
        return null;
    }

    private AlternativeDisplay getAlternativeTo(Display display, Entity entity, DisplayMode mode, DisplayFormat format, List<? extends Display> displays) {
        return getAlternativeTo(display, entity, mode, format, displays, "existentially", "independent", entity);
    }

    private AlternativeDisplay getAlternativeTo(Display display, Entity entity, DisplayMode mode, DisplayFormat format, List<? extends Display> displays,
        String qualifier, String kinship, Entity someEntity) {
        Entity thatEntity;
        Entity thatMaster;
        EntityReference thatReference;
        DisplayFormat thatFormat;
        DisplayMode thatMode;
        boolean fair;
        Level level = Level.DEBUG;
        String parameters = display.getName() + "-" + entity.getName() + "-" + mode + "-" + format + "-" + qualifier + "-" + kinship;
        EntityReference thisReference = display.getReference();
        for (Display that : displays) {
            if (!kinship.equals("independent")) {
//              level = Level.WARN;
                if (display.equals(that)) {
                    continue; // avoid recursion
                }
            }
            thatEntity = that.getEntity();
            thatMaster = that.getMaster();
            thatReference = that.getReference();
            thatFormat = that.getDisplayFormat();
            thatMode = that.getDisplayMode();
            if (entity.equals(thatEntity) && format.equals(thatFormat) && mode.equals(thatMode)) {
                switch (qualifier) {
                    case "explicit":
                        fair = explicitSwitch(kinship, someEntity, thatMaster, thatReference);
                        break;
                    case "implicit":
                        fair = implicitSwitch(kinship, someEntity, thatMaster, thatReference, thisReference);
                        break;
                    default:
                        fair = thatMaster == null;
                        break;
                }
                if (fair) {
                    logger.log(level, parameters + " alternative is " + that.getName() + " @ " + getName());
                    return new AlternativeDisplay(that, display, entity, mode, format, qualifier, kinship, someEntity);
                }
            }
        }
        return null;
    }

    private boolean explicitSwitch(String kinship, Entity someEntity, Entity thatMaster, EntityReference thatReference) {
        switch (kinship) {
            case "dependent":
            case "collateral":
                return equalEntity(thatMaster, someEntity) && mainReference(thatReference);
            default:
                return false;
        }
    }

    private boolean implicitSwitch(String kinship, Entity someEntity, Entity thatMaster, EntityReference thatReference, EntityReference thisReference) {
        switch (kinship) {
            case "dependent":
                return equalEntity(thatMaster, someEntity) && alikeName(thatReference, someEntity);
            case "collateral":
                return equalEntity(thatMaster, someEntity) && equalName(thatReference, thisReference);
            default:
                return false;
        }
    }

    private List<Entity> someEntityList(Display display, String kinship) {
        switch (kinship) {
            case "dependent":
                return someEntityList(display.getEntity());
            case "collateral":
                return someEntityList(display.getMaster());
            default:
                return null;
        }
    }

    private List<Entity> someEntityList(Entity entity) {
        List<Entity> list = new ArrayList<>();
        if (entity != null) {
            list.add(entity);
            list.addAll(someEntityList(entity.getBaseRoot()));
        }
        return list;
    }

    private boolean equalEntity(Entity that, Entity thiz) {
        if (that == null || thiz == null) {
            return false;
        }
        return that.equals(thiz);
    }

    // <editor-fold defaultstate="collapsed" desc="superEntity">
    /*
    private boolean superEntity(Entity that, Entity thiz) {
        if (that == null || thiz == null) {
            return false;
        }
        return that.getDataClass().isAssignableFrom(thiz.getDataClass());
    }
    /**/
    // </editor-fold>
/**/
    private boolean mainReference(EntityReference thatReference) {
        return thatReference != null && thatReference.isMainRelationship();
    }

    private boolean alikeName(EntityReference thatReference, Entity thisEntity) {
        // one of the child pages: same entity and reference name means main reference
        if (thatReference == null || thisEntity == null) {
            return false;
        }
        String thatReferenceName = thatReference.getName();
        String thisReferenceName = StringUtils.uncapitalize(thisEntity.getName());
        return thatReferenceName != null && thatReferenceName.equals(thisReferenceName);
    }

    private boolean equalName(EntityReference thatReference, EntityReference thisReference) {
        // one of the collateral pages: same reference name means same role
        if (thatReference == null || thisReference == null) {
            return false;
        }
        String thatReferenceName = thatReference.getName();
        String thisReferenceName = thisReference.getName();
        return thatReferenceName != null && thatReferenceName.equals(thisReferenceName);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="locally declared entity classes getters">
    public Set<Class<?>> getLocallyDeclaredEntityClasses() {
        Class<?> namedClass = XS2.getNamedClass(this);
        return XS2.getLocallyDeclaredEntityClasses(namedClass);
    }

    public Set<String> getLocallyDeclaredEntityClassSimpleNames() {
        Set<Class<?>> classes = getLocallyDeclaredEntityClasses();
        Set<String> names = new LinkedHashSet<>();
        for (Class<?> clazz : classes) {
            names.add(clazz.getSimpleName());
        }
        return names;
    }

    public String[] getLocallyDeclaredEntityClassSimpleNamesArray() {
        Set<String> names = getLocallyDeclaredEntityClassSimpleNames();
        String[] array = new String[names.size()];
        return names.toArray(array);
    }
    // </editor-fold>

    private Project project() {
        Project project = TLC.getProject();
        return project == null ? this : project;
    }

    protected void increaseWarningCount() {
        project().getParser().increaseWarningCount();
    }

    protected void increaseErrorCount() {
        project().getParser().increaseErrorCount();
    }

    protected void increaseWriterWarnings(int count) {
        project().getWriter().increaseWarningCount(count);
    }

    protected void increaseWriterErrors(int count) {
        project().getWriter().increaseErrorCount(count);
    }

    private String[] projectHelpFileTypes() {
        return project().helpFileTypes();
    }

    private String projectHelpFileTypesCSV() {
        return project().helpFileTypesCSV();
    }

    private String[] helpFileTypes() {
        if (metaHelpEnabled()) {
            ArrayList<String> list = new ArrayList<>(Arrays.asList(Constants.VALID_HELP_FILE_TYPES));
            list.add("java");
            String[] array = new String[list.size()];
            return list.toArray(array);
        }
        return Constants.VALID_HELP_FILE_TYPES;
    }

    private String helpFileTypesCSV() {
        String csv = Constants.VALID_HELP_FILE_TYPES_CSV;
        return metaHelpEnabled() ? csv + ", java" : csv;
    }

    private boolean metaHelpEnabled() {
        return HelpFileAutoName.META.equals(_helpFileAutoName);
    }

    private void fatal(Throwable throwable) {
        Throwable cause = ThrowableUtils.getCause(throwable);
        String message = throwable.equals(cause) ? throwable.getClass().getSimpleName() : throwable.getMessage();
        logger.fatal(message, cause);
    }

    private void log(Level level, String method, Object... parameters) {
        if (LogUtils.foul(logger, level)) {
            return;
        }
        String message = signature(method, parameters);
        logger.log(level, message);
    }

    private void track(String method) {
        track(method, this);
    }

    private void track(String method, Object... parameters) {
        getParser().track(depth(), round(), getClassPath(), method, parameters);
    }

    // <editor-fold defaultstate="collapsed" desc="iframe">
    /**
     * Cree la definición de un iframe usando MessageFormat.format
     * <p>
     * @param src URL del documento a incrustar en el iframe
     * @return la definición de un iframe de 300 x 150 píxeles
     */
    protected static String iframe(String src) {
        return iframe(src, 0, 0);
    }

    /**
     * Cree la definición de un iframe usando MessageFormat.format
     * <p>
     * @param src URL del documento a incrustar en el iframe
     * @param width ancho del iframe en píxeles. Un número menor o igual a 0 es equivalente a 300; un número mayor que 0 y menor que 100 es
     * equivalente a 100.
     * @param height alto del iframe en píxeles. Un número menor o igual a 0 es equivalente a 150; un número mayor que 0 y menor que 50 es equivalente
     * a 50.
     * @return la definición de un iframe
     */
    protected static String iframe(String src, int width, int height) {
        return XS2.iframe(src, width, height);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Comparable">
    @Override
    public int compareTo(Project o) {
        Project that;
        if (o != null) {
            that = o;
            String thisName = StringUtils.trimToEmpty(this.getName());
            String thatName = StringUtils.trimToEmpty(that.getName());
            return thisName.compareTo(thatName);
        }
        return 0;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="print">
    public void print() {
        System.out.println(this);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="toString">
    @Override
    public String toString() {
        String str1 = getName();
        String str2 = getNamedClass().getSimpleName();
        String str3 = getAlias();
        String str4 = str1 == null || str1.equals(str2) ? str2 : str2 + "[" + str1 + "]";
        String str5 = str3 == null || str3.equals(str1) ? str4 : str4 + "[" + str3 + "]";
        String str6 = str5.replace("][", ", ");
//      return str6 + "@" + Integer.toHexString(hashCode());
        return str6;
    }

    @Override
    protected String fieldsToString(int n, String key, boolean verbose, boolean fields, boolean maps) {
        String tab = verbose ? StringUtils.repeat(" ", 4) : "";
        String fee = verbose ? StringUtils.repeat(tab, n) : "";
        String faa = " = ";
        String foo = verbose ? EOL : ", ";
        String string = super.fieldsToString(n, key, verbose, fields, maps);
        if (fields || verbose) {
            string += fee + tab + "entities" + faa + _entityReferences.size() + foo;
            string += fee + tab + "projects" + faa + _projectReferences.size() + foo;
        }
        return string;
    }

    @Override
    protected String mapsToString(int n, String key, boolean verbose, boolean fields, boolean maps) {
        String string = super.mapsToString(n, key, verbose, fields, maps);
        if (maps || verbose) {
            for (String clave : _entityReferences.keySet()) {
                ProjectEntityReference valor = _entityReferences.get(clave);
                if (valor.getEntity() != null) {
                    string += valor.getEntity().toString(n + 1, clave, false, fields, false);
                }
            }
            for (String clave : _projectReferences.keySet()) {
                ProjectReference valor = _projectReferences.get(clave);
                if (valor.getProject() != null && valor.getProject() != this) {
                    string += valor.getProject().toString(n + 1, clave, false, fields, maps);
                }
            }
        }
        return string;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Parser">
    class Parser {

        private final Logger logger = Logger.getLogger(Project.Parser.class);

        private int maxDepthReached = 0;

        private int maxRoundReached = 0;

        private final Map<String, EntityData> entities = new TreeMap<>();

        private final List<String> allocations = new ArrayList<>();

        private int alerts = 0;

        private int warnings = 0;

        private int errors = 0;

        public Parser() {
            TLB.clearProgrammers();
            TLB.clearWrapperClasses();
            TLB.setProgrammer("BP", new ResourceBundleProgrammer());
            TLB.setProgrammer("JP", new JDK8Programmer());
            TLB.setProgrammer("SP", new PostgreSqlProgrammer());
        }

        private boolean parse() {
            log(_detailLevel, "parse");
            logJavaClassPath();
//          resetCounters(); moved to the end of the method because of warnings and errors before calling build
            try {
                printSettings();
                checkSpecialEntityClasses();
                putReferences();
                printProjectSummary(Level.INFO);
                printProjectReferencesSummary(Level.INFO);
                printEntityReferencesSummary(Level.INFO);
                if (errors == 0) {
                    initialiseEntityReferences();
                }
                if (errors == 0) {
                    prepareEntityReferences();
                }
                if (errors == 0) {
                    settleEntityReferences();
                }
                if (errors == 0) {
                    finaliseEntityReferences();
                }
                if (errors == 0) {
                    checkEntityReferences();
                }
                if (errors == 0) {
                    initialiseProjectReferences();
                }
                if (errors == 0) {
                    settleProjectReferences();
                }
                setMasterFields();
                printSummary();
                if (_verbose) {
                    printProjectReferencesDetail(_detailLevel);
                    printEntityReferencesDetail(_detailLevel);
                }
            } catch (Throwable throwable) {
                fatal(throwable);
            }
            boolean ok = errors == 0;
            resetCounters(); // just in case parse is called again
            return ok;
        }

        private void logJavaClassPath() {
            String key = "java.class.path";
            String jcp = System.getProperty(key);
            if (jcp != null) {
//              logger.debug(key + "=" + EOL + jcp.replace(";", EOL));
                logger.debug(key);
                String[] strings = StringUtils.splitByWholeSeparator(jcp, ";");
                for (String string : strings) {
                    logger.debug(TAB + string);
                }
            }
        }

        // <editor-fold defaultstate="collapsed" desc="put references">
        private void putReferences() {
            log(_trackingLevel, "putReferences");
            Class<?> type = Project.this.getClass();
            Class<?> clazz = XS1.getNamedClass(type);
            Class<?> declaringType = null;
            putReferences(type, declaringType, Project.this, null);
            String key = clazz.getName();
            ProjectReference reference = _projectReferences.get(key);
            reference.setProject(Project.this);
        }

        private void putReferences(Class<?> type, Class<?> declaringType, Artifact declaringArtifact, Field declaringField) {
            String pattern;
            String remarks;
            Class<?> clazz = XS1.getNamedClass(type);
            int modifiers = clazz.getModifiers();
            boolean restricted = clazz.isPrimitive() || Modifier.isAbstract(modifiers) || !Modifier.isPublic(modifiers);
            if (restricted) {
            } else if (Entity.class.isAssignableFrom(clazz)) {
                String key = clazz.getSimpleName();
                if (_entityReferences.containsKey(key)) {
                    ProjectEntityReference reference = _entityReferences.get(key);
                    Class<?> entityClass = reference.getEntityClass();
                    if (clazz.equals(entityClass)) {
                        reference.putDeclaringType(declaringType);
                        reference.setExplicit(declaringType);
                        reference.setImplicit(declaringType);
                    } else if (clazz.isAssignableFrom(entityClass)) {
                        reference.putDeclaringType(declaringType);
                        reference.setExplicit(declaringType);
                        reference.setImplicit(declaringType);
                        logEntityReferenceOverride(_alertLevel, entityClass, clazz, declaringType, null);
                        alerts++;
                    } else if (entityClass.isAssignableFrom(clazz)) {
                        putEntityReference(clazz, declaringType, reference);
                        logEntityReferenceOverride(_alertLevel, clazz, entityClass, declaringType, null);
                        alerts++;
                    } else {
                        pattern = "{0} is not assignable from {1}";
                        remarks = MessageFormat.format(pattern, entityClass.getName(), clazz.getName());
                        logEntityReferenceOverride(Level.ERROR, clazz, entityClass, declaringType, remarks);
                        errors++;
                    }
                } else {
                    putEntityReference(clazz, declaringType);
                }
            } else if (Project.class.isAssignableFrom(clazz)) {
                if (declaringType == null || Project.class.isAssignableFrom(declaringType)) {
                    String key = clazz.getName();
                    if (_projectReferences.containsKey(key)) {
                        ProjectReference reference = _projectReferences.get(key);
                        reference.putDeclaringType(declaringType);
                    } else {
                        putProjectReference(clazz, declaringType, declaringArtifact, declaringField);
                    }
                }
            }
        }

        /**
         * @param riding = overriding class
         * @param ridden = overridden class
         * @param declaring = declaring class
         */
        private void logEntityReferenceOverride(Level level, Class<?> riding, Class<?> ridden, Class<?> declaring, String remarks) {
            if (LogUtils.foul(logger, level)) {
                return;
            }
            String pattern = level.isGreaterOrEqual(Level.ERROR) ? "failed to override" : "overriding";
            pattern += " reference to entity {0} at {1}";
            String name = riding.getSimpleName();
            String message = MessageFormat.format(pattern, name, typeTitleAndName(declaring));
            logger.log(level, message);
            //
            Level detailLevel = level.isGreaterOrEqual(Level.WARN) ? level : _detailLevel;
            if (LogUtils.foul(logger, detailLevel)) {
                return;
            }
            logger.log(detailLevel, TAB + "overriding class: " + riding.getName());
            logger.log(detailLevel, TAB + "overridden class: " + ridden.getName());
            if (StringUtils.isNotBlank(remarks)) {
                logger.log(detailLevel, TAB + remarks);
            }
        }

        private void putEntityReference(Class<?> type, Class<?> declaringType) {
            ProjectEntityReference previousReference = null;
            putEntityReference(type, declaringType, previousReference);
        }

        private void putEntityReference(Class<?> type, Class<?> declaringType, ProjectEntityReference previousReference) {
            String key = type.getSimpleName();
            Class<?> concreteSuperclass = XS1.getConcreteSuperclass(type);
            if (concreteSuperclass != null) {
                putReferences(concreteSuperclass, type, null, null);
            }
            boolean explicit = previousReference != null && previousReference.isExplicit();
            boolean implicit = previousReference != null && previousReference.isImplicit();
            ProjectEntityReference reference = new ProjectEntityReference(type, Project.this);
            reference.putDeclaringType(declaringType);
            reference.setExplicit(explicit);
            reference.setExplicit(declaringType);
            reference.setImplicit(implicit);
            reference.setImplicit(declaringType);
            _entityReferences.put(key, reference);
            for (Field field1 : XS1.getFields(type, Entity.class)) { // type.getDeclaredFields()
                Class<?> fieldType1 = field1.getType();
                if (Entity.class.isAssignableFrom(fieldType1)) {
                    putReferences(fieldType1, type, null, null);
                } else if (EntityCollection.class.isAssignableFrom(fieldType1)) {
                    Class<? extends Entity> targetEntityClass = targetEntityClass(field1);
                    if (targetEntityClass != null) {
                        putReferences(targetEntityClass, type, null, null);
                    }
                } else if (Operation.class.isAssignableFrom(fieldType1)) {
                    Class<? extends Entity> constructedEntityClass = constructedEntityClass(fieldType1);
                    if (constructedEntityClass != null) {
                        putReferences(constructedEntityClass, type, null, null);
                    }
                    for (Field field2 : XS1.getFields(fieldType1, Operation.class)) { // type.getDeclaredFields()
                        Class<?> fieldType2 = field2.getType();
                        if (Entity.class.isAssignableFrom(fieldType2)) {
                            putReferences(fieldType2, type, null, null);
                        }
                    }
                }
                if (Entity.class.isAssignableFrom(fieldType1) || LongProperty.class.isAssignableFrom(fieldType1)) {
                    Class<? extends Entity> segmentEntityClass = segmentEntityClass(field1);
                    if (segmentEntityClass != null) {
                        putReferences(segmentEntityClass, type, null, null);
                    }
                }
            }
        }

        private Class<? extends Entity> targetEntityClass(Field field) {
            Class<? extends Entity> targetEntityClass = null;
            if (field.isAnnotationPresent(OneToMany.class)) {
                OneToMany annotation = field.getAnnotation(OneToMany.class);
                targetEntityClass = annotation.targetEntity();
            }
            return targetEntityClass != null && targetEntityClass != Entity.class && Entity.class.isAssignableFrom(targetEntityClass) ? targetEntityClass : null;
        }

        private Class<? extends Entity> constructedEntityClass(Class<?> operationClass) {
            Class<? extends Entity> constructedEntityClass = null;
            if (operationClass.isAnnotationPresent(ConstructionOperationClass.class)) {
                ConstructionOperationClass annotation = operationClass.getAnnotation(ConstructionOperationClass.class);
                constructedEntityClass = annotation.type();
            }
            return constructedEntityClass != null && constructedEntityClass != Entity.class && Entity.class.isAssignableFrom(constructedEntityClass) ? constructedEntityClass : null;
        }

        private Class<? extends Entity> segmentEntityClass(Field field) {
            Class<? extends Entity> segmentEntityClass = null;
            if (field.isAnnotationPresent(SegmentProperty.class)) {
                SegmentProperty annotation = field.getAnnotation(SegmentProperty.class);
                segmentEntityClass = annotation.entityClass();
            }
            return segmentEntityClass != null && segmentEntityClass != Entity.class && Entity.class.isAssignableFrom(segmentEntityClass) ? segmentEntityClass : null;
        }

        private void putProjectReference(Class<?> type, Class<?> declaringType, Artifact declaringArtifact, Field declaringField) {
            String key = type.getName();
            ProjectReference reference = new ProjectReference(type, Project.this);
            reference.putDeclaringType(declaringType);
            reference.setDeclaringArtifact(declaringArtifact);
            reference.setDeclaringField(declaringField);
            _projectReferences.put(key, reference);
            for (Field field : XS1.getFields(type, Project.class)) { // type.getDeclaredFields()
                putReferences(field.getType(), type, null, field);
            }
        }
        // </editor-fold>

        private void checkSpecialEntityClasses() {
            List<Class<? extends Entity>> unset = Project.this.unsetSpecialEntityClasses();
            if (unset != null && !unset.isEmpty()) {
                for (Class<? extends Entity> sec : unset) {
                    warn(sec.getSimpleName() + " entity class is not set");
                }
            }
        }

        private void setMasterFields() {
            log(_trackingLevel, "setMasterFields");
            Class<?> type = Project.this.getClass();
            Class<?> fieldType;
//          String fieldName;
            Project project;
            Class<?> projectClass;
            for (ProjectReference reference : _projectReferences.values()) {
                project = reference.getProject();
                projectClass = reference.getProjectClass();
                for (Field field : XS1.getFields(type, Project.class, projectClass)) {
                    fieldType = field.getType();
                    if (projectClass.equals(fieldType)) {
//                      fieldName = field.getName();
                        field.setAccessible(true);
                        try {
                            if (field.get(Project.this) == null) {
                                field.set(Project.this, project);
                            }
                        } catch (IllegalArgumentException | IllegalAccessException ex) {
                            fatal(ex);
                        }
                    }
                }
            }
        }

        // <editor-fold defaultstate="collapsed" desc="initialise, settle and finalise entity references">
        private void initialiseEntityReferences() {
            log(_trackingLevel, "initialiseEntityReferences");
            Entity entity;
            for (ProjectEntityReference reference : _entityReferences.values()) {
                if (reference.getEntity() == null) {
                    entity = getEntityInstance(reference.getEntityClass());
                    XS1.postConstruct(entity);
                    reference.setEntity(entity);
                }
            }
            if (_spinose) {
                RunUtils.logMemory(logger, "initialiseEntityReferences 1/2");
            }
            for (ProjectEntityReference reference : _entityReferences.values()) {
                entity = reference.getEntity();
                if (entity != null) {
                    entity.initialise();
                }
            }
            if (_spinose) {
                RunUtils.logMemory(logger, "initialiseEntityReferences 2/2");
            }
        }

        private void prepareEntityReferences() {
            log(_trackingLevel, "prepareEntityReferences");
            Entity entity;
            for (ProjectEntityReference reference : _entityReferences.values()) {
                entity = reference.getEntity();
                if (entity != null) {
                    entity.prepare();
                }
            }
            if (_spinose) {
                RunUtils.logMemory(logger, "prepareEntityReferences");
            }
        }

        private void settleEntityReferences() {
            log(_trackingLevel, "settleEntityReferences");
            Entity entity;
            for (ProjectEntityReference reference : _entityReferences.values()) {
                entity = reference.getEntity();
                if (entity != null) {
                    entity.settle();
                }
            }
            if (_spinose) {
                RunUtils.logMemory(logger, "settleEntityReferences");
            }
        }

        private void finaliseEntityReferences() {
            log(_trackingLevel, "finaliseEntityReferences");
            Entity entity;
            for (ProjectEntityReference reference : _entityReferences.values()) {
                entity = reference.getEntity();
                if (entity != null) {
                    entity.finalise();
                }
            }
            if (_spinose) {
                RunUtils.logMemory(logger, "finaliseEntityReferences 1/2");
            }
            for (ProjectEntityReference reference : _entityReferences.values()) {
                entity = reference.getEntity();
                if (entity != null) {
                    entity.finish();
                }
            }
            if (_spinose) {
                RunUtils.logMemory(logger, "finaliseEntityReferences 2/2");
            }
        }

        private void checkEntityReferences() {
            log(_trackingLevel, "checkEntityReferences");
            Entity entity;
            List<Entity> extensionsList;
            boolean concreteless;
            for (ProjectEntityReference reference : _entityReferences.values()) {
                entity = reference.getEntity();
                if (entity != null) {
                    if (entity.isAbstractClass()) {
                        concreteless = true;
                        extensionsList = entity.getExtensionsList();
                        for (Entity extension : extensionsList) {
                            if (extension.isAbstractClass()) {
                                continue;
                            }
                            concreteless = false;
                            break;
                        }
                        if (concreteless) {
                            logger.error(entity.getName() + " is an abstract class without concrete extensions");
                            increaseErrorCount();
                        }
                    }
                }
            }
            if (_spinose) {
                RunUtils.logMemory(logger, "checkEntityReferences");
            }
        }

        private Entity getEntityInstance(Class<?> type) {
            String errmsg = "failed to create a new instance of " + type;
            try {
                return (Entity) type.getConstructor(Artifact.class, Field.class).newInstance(Project.this, null);
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InstantiationException | InvocationTargetException ex) {
                throw new InstantiationRuntimeException(errmsg, ex);
            }
        }
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="initialise, settle and finalise project references">
        private void initialiseProjectReferences() {
            log(_trackingLevel, "initialiseProjectReferences");
            Project project;
            for (ProjectReference reference : _projectReferences.values()) {
                if (reference.getProject() == null) {
                    project = getProjectInstance(reference.getProjectClass());
                    project.setMaster(Project.this);
                    project.resetDeclaringArtifact(reference.getDeclaringArtifact());
                    project.resetDeclaringField(reference.getDeclaringField());
//                  project.initializeAnnotations();
//                  project.annotate(project.getNamedClass());
                    project.annotate(reference.getDeclaringField());
                    reference.setProject(project);
                }
            }
            String key;
            ProjectEntityReference entityReference;
            ProjectReference projectReference;
            Map<String, Class<?>> declaredTypes;
            Map<String, ProjectEntityReference> entityReferences;
            Map<String, ProjectReference> projectReferences;
            for (ProjectReference reference : _projectReferences.values()) {
                project = reference.getProject();
                if (project != null && project != Project.this) {
                    entityReferences = project.getEntityReferences();
                    projectReferences = project.getProjectReferences();
                    declaredTypes = reference.getDeclaredTypes();
                    for (Class<?> declaredType : declaredTypes.values()) {
                        if (Entity.class.isAssignableFrom(declaredType)) {
                            key = declaredType.getSimpleName();
                            if (_entityReferences.containsKey(key)) {
                                entityReference = _entityReferences.get(key);
                                entityReferences.put(key, entityReference);
                            }
                        } else if (Project.class.isAssignableFrom(declaredType)) {
                            key = declaredType.getName();
                            if (_projectReferences.containsKey(key)) {
                                projectReference = _projectReferences.get(key);
                                projectReferences.put(key, projectReference);
                            }
                        }
                    }
                    project.getParser().printProjectSummary(_detailLevel);
                    project.getParser().printProjectReferencesSummary(_detailLevel);
                    project.getParser().printEntityReferencesSummary(_detailLevel);
                }
            }
            if (_spinose) {
                RunUtils.logMemory(logger, "initialiseProjectReferences");
            }
        }

        private void settleProjectReferences() {
            log(_trackingLevel, "settleProjectReferences");
            Project project;
            Field field;
            for (ProjectReference reference : _projectReferences.values()) {
                project = reference.getProject();
                if (project != null) {
                    project.settle();
                    field = reference.getDeclaringField();
                    if (field != null) {
                        project.finalizeModuleAnnotation(field);
                    }
                }
            }
            if (_spinose) {
                RunUtils.logMemory(logger, "settleProjectReferences");
            }
        }

        private Project getProjectInstance(Class<?> type) {
            String errmsg = "failed to create a new instance of " + type;
            try {
                return (Project) type.getDeclaredConstructor().newInstance();
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InstantiationException | InvocationTargetException ex) {
                throw new InstantiationRuntimeException(errmsg, ex);
            }
        }
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="print references">
        private void printEntityReferencesSummary(Level level) {
            if (LogUtils.foul(logger, level)) {
                return;
            }
            boolean explicit;
            boolean implicit;
            Map<String, Class<?>> references;
            Map<String, Class<?>> referenced;
            Entity e;
            String s;
            int i = _entityReferences.size();
            String pattern = i == 0
                ? "project {0} contains no references to entities"
                : "project {0} contains references to {1} distinct entities";
            String message = MessageFormat.format(pattern, Project.this.getClass().getName(), i);
            logger.log(level, message);
            //
            if (LogUtils.foul(logger, _detailLevel)) {
                return;
            }
            for (ProjectEntityReference reference : _entityReferences.values()) {
                explicit = reference.isExplicit();
                implicit = reference.isImplicit();
                references = reference.getDeclaredTypes();
                referenced = reference.getDeclaringTypes();
                e = reference.getEntity();
                s = reference.getEntityClass().getSimpleName();
                s += " {";
                s += reference.getEntityClass().getName();
                s += e == null ? "" : "@" + Integer.toHexString(e.hashCode());
                s += ", explicit=" + explicit;
                s += ", implicit=" + implicit;
                s += ", references=" + references.size();
                s += ", referenced=" + referenced.size();
                s += "} ";
                logger.log(_detailLevel, TAB + s);
                for (Class<?> declaredType : references.values()) {
                    logger.log(_detailLevel, TAB + TAB + "references " + typeTitleAndName(declaredType));
                }
                for (Class<?> declaringType : referenced.values()) {
                    logger.log(_detailLevel, TAB + TAB + "is referenced by " + typeTitleAndName(declaringType));
                }
            }
        }

        private void printEntityReferencesDetail(Level level) {
            if (LogUtils.foul(logger, level)) {
                return;
            }
            boolean initialised;
            String string;
            for (ProjectEntityReference reference : _entityReferences.values()) {
                initialised = reference.getEntity() != null;
                if (initialised) {
                    string = reference.getEntity().toString(0, null, _verbose, true, true);
                    logger.log(level, string);
                }
            }
        }

        private void printProjectSummary(Level level) {
            if (LogUtils.foul(logger, level)) {
                return;
            }
            String pattern;
            String message;
            if (_master == null) {
                pattern = "project {0} is the master project";
                message = MessageFormat.format(pattern, Project.this.getClass().getName());
            } else {
                pattern = "project {0} is nested within project {1}";
                message = MessageFormat.format(pattern, Project.this.getClass().getName(), _master.getClass().getName());
            }
            logger.log(level, message);
        }

        private void printProjectReferencesSummary(Level level) {
            if (LogUtils.foul(logger, level)) {
                return;
            }
            String pattern;
            String message;
            Map<String, Class<?>> references;
            Map<String, Class<?>> referenced;
            Project p;
            String s;
            Class<? extends Project> clazz = Project.this.getClass();
            int i = 0;
            for (ProjectReference reference : _projectReferences.values()) {
                if (!clazz.equals(reference.getProjectClass())) {
                    i++;
                }
            }
            pattern = i == 0
                ? "project {0} contains no references to other projects"
                : "project {0} contains references to {1} other projects";
            message = MessageFormat.format(pattern, Project.this.getClass().getName(), i);
            logger.log(level, message);
            //
            if (LogUtils.foul(logger, _detailLevel)) {
                return;
            }
            for (ProjectReference reference : _projectReferences.values()) {
                references = reference.getDeclaredTypes();
                referenced = reference.getDeclaringTypes();
                p = reference.getProject();
                s = reference.getProjectClass().getSimpleName();
                s += " {";
                s += reference.getProjectClass().getName();
                s += p == null ? "" : "@" + Integer.toHexString(p.hashCode());
                s += p == null ? "" : ", master=" + p.getMaster();
                s += ", references=" + references.size();
                s += ", referenced=" + referenced.size();
                s += "} ";
                logger.log(_detailLevel, TAB + s);
                for (Class<?> declaredType : references.values()) {
                    logger.log(_detailLevel, TAB + TAB + "references " + typeTitleAndName(declaredType));
                }
                for (Class<?> declaringType : referenced.values()) {
                    logger.log(_detailLevel, TAB + TAB + "is referenced by " + typeTitleAndName(declaringType));
                }
            }
        }

        private void printProjectReferencesDetail(Level level) {
            if (LogUtils.foul(logger, level)) {
                return;
            }
            boolean initialised;
            String string;
            for (ProjectReference reference : _projectReferences.values()) {
                initialised = reference.getProject() != null;
                if (initialised) {
                    string = reference.getProject().toString(0, null, _verbose, true, true);
                    logger.log(level, string);
                }
            }
        }
        // </editor-fold>

        private String typeTitleAndName(Class<?> type) {
            if (Entity.class.isAssignableFrom(type)) {
                return "entity " + type.getName();
            } else if (Project.class.isAssignableFrom(type)) {
                return "project " + type.getName();
            } else {
                return "" + type;
            }
        }

        private void printSettings() {
            logger.debug("defaultMaxDepth=" + _defaultMaxDepth);
            logger.debug("defaultMaxRound=" + _defaultMaxRound);
            logger.debug("defaultPropertyFieldSerializable=" + _defaultPropertyFieldSerializable);
            logger.debug("defaultPropertyFieldSerializableIUID=" + _defaultPropertyFieldSerializableIUID);
            logger.debug("alertLevel=" + _alertLevel);
            logger.debug("detailLevel=" + _detailLevel);
//          logger.debug("dictionaryLevel=" + getDictionaryLevel());
            logger.debug("trackingLevel=" + _trackingLevel);
            logger.debug("transitionLevel=" + _transitionLevel);
            logger.debug("specialExpressionLevel=" + _specialExpressionLevel);
            logger.debug("unusualExpressionLevel=" + _unusualExpressionLevel);
            logger.debug("verbose=" + _verbose);
            logger.debug("warnose=" + _warnose);
        }

        private void printSummary() {
            logger.info("maxDepthReached=" + maxDepthReached);
            logger.info("maxRoundReached=" + maxRoundReached);
            logger.info("artifacts=" + _artifacts.size());
            logger.info("entities=" + entities.size());
            EntityData.log(entities);
            EntityData.log(allocations);
            RunUtils.logMemory(logger);
            if (alerts != 0) {
                if (_alertLevel.equals(Level.WARN)) {
                    logger.warn("alerts=" + alerts);
                } else if (_alertLevel.equals(Level.INFO)) {
                    logger.info("alerts=" + alerts);
                }
            }
            if (warnings == 0) {
                logger.info("warnings=" + warnings);
            } else {
                logger.warn("warnings=" + warnings);
            }
            if (errors == 0) {
                logger.info("errors=" + errors);
            } else {
                logger.warn("errors=" + errors);
            }
        }

        private void resetCounters() {
            maxDepthReached = 0;
            maxRoundReached = 0;
            entities.clear();
            alerts = 0;
            warnings = 0;
            errors = 0;
        }

        /**
         * @param depth the maxDepthReached to set
         */
        void setMaxDepthReached(int depth) {
            if (depth > maxDepthReached) {
                maxDepthReached = depth;
            }
        }

        /**
         * @param round the maxRoundReached to set
         */
        void setMaxRoundReached(int round) {
            if (round > maxRoundReached) {
                maxRoundReached = round;
            }
        }

        /**
         *
         */
        void addEntity(Entity entity) {
            if (_acerose && entity != null) {
                String name = entity.getClass().getName();
                EntityData data = entities.get(name);
                if (data == null) {
                    entities.put(name, new EntityData(entity));
                } else {
                    data.add(entity);
                }
            }
        }

        /**
         *
         */
        void addEntity(Entity entity, String fullName, int depth, int round, int maxDepth, int maxRound) {
            addEntity(entity);
            addAllocation(fullName, depth, round, maxDepth, maxRound);
        }

        void addAllocation(String fullName, int depth, int round, int maxDepth, int maxRound) {
            if (_foliose && fullName != null) {
                allocations.add(EntityData.log(fullName, depth, round, maxDepth, maxRound));
            }
        }

        /**
         *
         */
        void addQueryTable(QueryTable queryTable) {
            if (_acerose && queryTable != null) {
                String name = queryTable.getEntity().getClass().getName();
                EntityData data = entities.get(name);
                if (data == null) {
                    entities.put(name, new EntityData(queryTable));
                } else {
                    data.add(queryTable);
                }
            }
        }

        /*
        void increaseAlertCount() {
            alerts++;
        }

        /**/
        void increaseWarningCount() {
            warnings++;
        }

        /**/
        void increaseErrorCount() {
            errors++;
        }

        /**/
        void increaseTransitionCount() {
            Level level = Project.getTransitionLevel();
            if (level.equals(Level.WARN)) {
                warnings++;
            } else if (level.equals(Level.ERROR) || level.equals(Level.FATAL)) {
                errors++;
            }
        }

        void log(Level level, String message) {
            if (LogUtils.foul(logger, level)) {
                return;
            }
            logger.log(level, message);
        }

        /*
        private void log(Level level, String method, Object... parameters) {
            if (LogUtils.foul(logger, level)) {
                return;
            }
            String message = signature(method, parameters);
            logger.log(level, message);
        }

        /**/
        void info(Object message) {
            logger.info(message);
        }

        void warn(Object message) {
            logger.warn(message);
            warnings++;
        }

        void error(Object message) {
            logger.error(message);
            errors++;
        }

        /**
         * @see AbstractEntity
         * @see EntityFolderAtlas
         * @see Operation
         */
        void track(int depth, int round, String path, String method, Object... parameters) {
            track(_trackingLevel, depth, round, path, signature(method, parameters), null);
        }

        /**
         * @see XS1
         */
        void track(int depth, int round, String path, Class<?> type, String name, String method, String remarks) {
            String tipo = type == null ? "" : type.getSimpleName();
            String note = tipo + "[" + name + "]" + "." + method;
            track(_trackingLevel, depth, round, path, note, remarks);
        }

        /**
         * @see XS1
         */
        /*
        void alert(int depth, int round, String path, Class<?> type, String name, String method, String remarks) {
            String tipo = type == null ? "" : type.getSimpleName();
            String note = tipo + "[" + name + "]" + "." + method;
            track(_alertLevel, depth, round, path, note, remarks);
            alerts++;
        }

        /**/
        private void track(Level level, int depth, int round, String path, String note, String remarks) {
            if (LogUtils.foul(logger, level)) {
                return;
            }
            boolean margin = LogUtils.fair(logger, _trackingLevel);
            String margin1 = margin ? StringUtils.repeat(" ", 5 * depth) : "";
            String margin2 = margin ? StringUtils.repeat(" ", 5 * (depth + 1)) : TAB;
            String message = margin1;
            message += "d=" + depth + ", r=" + round + ", ";
            if (StringUtils.isNotBlank(path)) {
                message += path + ".";
            }
            message += note;
            logger.log(level, message);
            if (StringUtils.isNotBlank(remarks)) {
                logger.log(level, margin2 + remarks);
            }
        }

        private void fatal(Throwable throwable) {
            Throwable cause = ThrowableUtils.getCause(throwable);
            String message = throwable.equals(cause) ? throwable.getClass().getSimpleName() : throwable.getMessage();
            logger.fatal(message, cause);
            errors++;
        }

    }
    // </editor-fold>

}
