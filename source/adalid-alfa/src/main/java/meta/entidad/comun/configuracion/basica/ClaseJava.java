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
package meta.entidad.comun.configuracion.basica;

import adalid.core.*;
import adalid.core.annotations.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import adalid.core.properties.*;
import java.lang.reflect.Field;

/**
 * @author Jorge Campins
 */
@EntityClass(independent = Kleenean.TRUE, resourceType = ResourceType.CONFIGURATION, resourceGender = ResourceGender.FEMININE)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE, access = OperationAccess.PUBLIC, rowsLimit = 0)
@EntityInsertOperation(enabled = Kleenean.FALSE)
@EntityUpdateOperation(enabled = Kleenean.FALSE)
@EntityDeleteOperation(enabled = Kleenean.FALSE)
@EntityTableView(enabled = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.FALSE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
public class ClaseJava extends AbstractPersistentEnumerationEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public ClaseJava(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of ClaseJava's attributes">
        setLocalizedLabel(ENGLISH, "java class");
        setLocalizedLabel(SPANISH, "clase java");
        setLocalizedShortLabel(ENGLISH, "class");
        setLocalizedShortLabel(SPANISH, "clase");
        setLocalizedCollectionLabel(ENGLISH, "Java Classes");
        setLocalizedCollectionLabel(SPANISH, "Clases Java");
        setLocalizedCollectionShortLabel(ENGLISH, "Classes");
        setLocalizedCollectionShortLabel(SPANISH, "Clases");
        /**/
        setLocalizedDescription(ENGLISH, "Each instance of " + b("Java Classes") + " represents a "
            + "Java class that encapsulates, hides, or wraps a data type."
            + "");
        setLocalizedDescription(SPANISH, "Cada instancia de " + b("Clases Java") + " representa una "
            + "clase Java que encapsula, oculta o envuelve un tipo de datos."
            + "");
        /**/
        setLocalizedShortDescription(ENGLISH, "Java class that encapsulates, hides, or wraps a data type");
        setLocalizedShortDescription(SPANISH, "clase Java que encapsula, oculta o envuelve un tipo de datos");
        /**/
        // </editor-fold>
    }

    @PrimaryKey
    public IntegerProperty numero;

    @BusinessKey
    public StringProperty codigo;

    @NameProperty
    public StringProperty nombre;

    @ColumnField(nullable = Kleenean.FALSE)
    public CharacterProperty letra;

    @Override
    protected void settleProperties() {
        super.settleProperties();
        /**/
        nombre.setDefaultValue(Object.class.getName());
        letra.setDefaultValue("?"); // requerido por el upgrade
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of ClaseJava's properties">
        /**/
        numero.setLocalizedLabel(ENGLISH, "java class number");
        numero.setLocalizedLabel(SPANISH, "número de la clase java");
        numero.setLocalizedShortLabel(ENGLISH, "number");
        numero.setLocalizedShortLabel(SPANISH, "número");
        /**/
        codigo.setLocalizedLabel(ENGLISH, "java class code");
        codigo.setLocalizedLabel(SPANISH, "código de la clase java");
        codigo.setLocalizedShortLabel(ENGLISH, "code");
        codigo.setLocalizedShortLabel(SPANISH, "código");
        /**/
        // </editor-fold>
    }

    public Instance JAVA_BIG_DECIMAL;

    public Instance JAVA_BIG_INTEGER;

    public Instance JAVA_BINARY;

    public Instance JAVA_BOOLEAN;

    public Instance JAVA_BYTE;

    public Instance JAVA_CHARACTER;

    public Instance JAVA_DATE;

    public Instance JAVA_DOUBLE;

    public Instance JAVA_FLOAT;

    public Instance JAVA_INTEGER;

    public Instance JAVA_LONG;

    public Instance JAVA_SHORT;

    public Instance JAVA_STRING;

    public Instance JAVA_TIME;

    public Instance JAVA_TIMESTAMP;

    public Instance JAVA_OBJECT;

    @Override
    protected void settleInstances() {
        super.settleInstances();
        /**/
        int i = 0;
        JAVA_BIG_DECIMAL.newInstanceField(numero, ++i);
        JAVA_BIG_INTEGER.newInstanceField(numero, ++i);
        JAVA_BINARY.newInstanceField(numero, ++i);
        JAVA_BOOLEAN.newInstanceField(numero, ++i);
        JAVA_BYTE.newInstanceField(numero, ++i);
        JAVA_CHARACTER.newInstanceField(numero, ++i);
        JAVA_DATE.newInstanceField(numero, ++i);
        JAVA_DOUBLE.newInstanceField(numero, ++i);
        JAVA_FLOAT.newInstanceField(numero, ++i);
        JAVA_INTEGER.newInstanceField(numero, ++i);
        JAVA_LONG.newInstanceField(numero, ++i);
        JAVA_SHORT.newInstanceField(numero, ++i);
        JAVA_STRING.newInstanceField(numero, ++i);
        JAVA_TIME.newInstanceField(numero, ++i);
        JAVA_TIMESTAMP.newInstanceField(numero, ++i);
        JAVA_OBJECT.newInstanceField(numero, ++i);
        /**/
        // ADVERTENCIA: las letras se usan para dar valor a la propiedad clases de la entidad CampoValorTemporal
        /**/
        JAVA_BIG_DECIMAL.newInstanceField(letra, "A");
        JAVA_BIG_INTEGER.newInstanceField(letra, "B");
        JAVA_BINARY.newInstanceField(letra, "C");
        JAVA_BOOLEAN.newInstanceField(letra, "D");
        JAVA_BYTE.newInstanceField(letra, "E");
        JAVA_CHARACTER.newInstanceField(letra, "F");
        JAVA_DATE.newInstanceField(letra, "G");
        JAVA_DOUBLE.newInstanceField(letra, "H");
        JAVA_FLOAT.newInstanceField(letra, "I");
        JAVA_INTEGER.newInstanceField(letra, "J");
        JAVA_LONG.newInstanceField(letra, "K");
        JAVA_SHORT.newInstanceField(letra, "L");
        JAVA_STRING.newInstanceField(letra, "M");
        JAVA_TIME.newInstanceField(letra, "N");
        JAVA_TIMESTAMP.newInstanceField(letra, "O");
        JAVA_OBJECT.newInstanceField(letra, "P");
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of ClaseJava's instances">
        /**/
        JAVA_BIG_DECIMAL.newInstanceField(codigo, "BigDecimal", ENGLISH);
        JAVA_BIG_DECIMAL.newInstanceField(codigo, "BigDecimal", SPANISH);
        /**/
        JAVA_BIG_INTEGER.newInstanceField(codigo, "BigInteger", ENGLISH);
        JAVA_BIG_INTEGER.newInstanceField(codigo, "BigInteger", SPANISH);
        /**/
        JAVA_BINARY.newInstanceField(codigo, "Binary", ENGLISH);
        JAVA_BINARY.newInstanceField(codigo, "Binary", SPANISH);
        /**/
        JAVA_BOOLEAN.newInstanceField(codigo, "Boolean", ENGLISH);
        JAVA_BOOLEAN.newInstanceField(codigo, "Boolean", SPANISH);
        /**/
        JAVA_BYTE.newInstanceField(codigo, "Byte", ENGLISH);
        JAVA_BYTE.newInstanceField(codigo, "Byte", SPANISH);
        /**/
        JAVA_CHARACTER.newInstanceField(codigo, "Character", ENGLISH);
        JAVA_CHARACTER.newInstanceField(codigo, "Character", SPANISH);
        /**/
        JAVA_DATE.newInstanceField(codigo, "Date", ENGLISH);
        JAVA_DATE.newInstanceField(codigo, "Date", SPANISH);
        /**/
        JAVA_DOUBLE.newInstanceField(codigo, "Double", ENGLISH);
        JAVA_DOUBLE.newInstanceField(codigo, "Double", SPANISH);
        /**/
        JAVA_FLOAT.newInstanceField(codigo, "Float", ENGLISH);
        JAVA_FLOAT.newInstanceField(codigo, "Float", SPANISH);
        /**/
        JAVA_INTEGER.newInstanceField(codigo, "Integer", ENGLISH);
        JAVA_INTEGER.newInstanceField(codigo, "Integer", SPANISH);
        /**/
        JAVA_LONG.newInstanceField(codigo, "Long", ENGLISH);
        JAVA_LONG.newInstanceField(codigo, "Long", SPANISH);
        /**/
        JAVA_SHORT.newInstanceField(codigo, "Short", ENGLISH);
        JAVA_SHORT.newInstanceField(codigo, "Short", SPANISH);
        /**/
        JAVA_STRING.newInstanceField(codigo, "String", ENGLISH);
        JAVA_STRING.newInstanceField(codigo, "String", SPANISH);
        /**/
        JAVA_TIME.newInstanceField(codigo, "Time", ENGLISH);
        JAVA_TIME.newInstanceField(codigo, "Time", SPANISH);
        /**/
        JAVA_TIMESTAMP.newInstanceField(codigo, "Timestamp", ENGLISH);
        JAVA_TIMESTAMP.newInstanceField(codigo, "Timestamp", SPANISH);
        /**/
        JAVA_OBJECT.newInstanceField(codigo, "Object", ENGLISH);
        JAVA_OBJECT.newInstanceField(codigo, "Object", SPANISH);
        /**/
        // </editor-fold>
        /**/
        JAVA_BIG_DECIMAL.newInstanceField(nombre, java.math.BigDecimal.class.getName());
        JAVA_BIG_INTEGER.newInstanceField(nombre, java.math.BigInteger.class.getName());
        JAVA_BINARY.newInstanceField(nombre, Object.class.getName());
        JAVA_BOOLEAN.newInstanceField(nombre, Boolean.class.getName());
        JAVA_BYTE.newInstanceField(nombre, Byte.class.getName());
        JAVA_CHARACTER.newInstanceField(nombre, Character.class.getName());
        JAVA_DATE.newInstanceField(nombre, java.sql.Date.class.getName());
        JAVA_DOUBLE.newInstanceField(nombre, Double.class.getName());
        JAVA_FLOAT.newInstanceField(nombre, Float.class.getName());
        JAVA_INTEGER.newInstanceField(nombre, Integer.class.getName());
        JAVA_LONG.newInstanceField(nombre, Long.class.getName());
        JAVA_SHORT.newInstanceField(nombre, Short.class.getName());
        JAVA_STRING.newInstanceField(nombre, String.class.getName());
        JAVA_TIME.newInstanceField(nombre, java.sql.Time.class.getName());
        JAVA_TIMESTAMP.newInstanceField(nombre, java.sql.Timestamp.class.getName());
        JAVA_OBJECT.newInstanceField(nombre, Object.class.getName());
        /**/
    }

}
