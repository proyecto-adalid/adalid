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
package meta.entidad.comun.operacion.basica;

import adalid.core.*;
import adalid.core.annotations.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import adalid.core.properties.*;
import java.lang.reflect.Field;

/**
 * @author Jorge Campins
 */
@EntityClass(independent = Kleenean.TRUE, resourceType = ResourceType.CONFIGURATION, resourceGender = ResourceGender.MASCULINE, startWith = 0)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE, access = OperationAccess.PUBLIC, rowsLimit = 0)
@EntityInsertOperation(enabled = Kleenean.FALSE)
@EntityUpdateOperation(enabled = Kleenean.FALSE)
@EntityDeleteOperation(enabled = Kleenean.FALSE)
@EntityTableView(enabled = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.FALSE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
public class TipoAgregacion extends AbstractPersistentEnumerationEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public TipoAgregacion(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public IntegerProperty numero;

    @BusinessKey
    public StringProperty codigo;

    @NameProperty
    public StringProperty nombre;

    @PropertyField(update = Kleenean.FALSE)
    @StringField(maxLength = 10)
    public StringProperty rangos;

    public Instance GRUPO;

    public Instance CUENTA;

    public Instance MINIMO;

    public Instance MAXIMO;

    public Instance SUMA;

    public Instance PROMEDIO;

    public Instance DESVIACION;

    public Instance CUENTA_MINIMO_MAXIMO;

    public Instance MINIMO_MAXIMO;

    public Instance SUMA_CUENTA_PROMEDIO;

    public Instance SUMA_CUENTA_PROMEDIO_DESVIACION_MINIMO_MAXIMO;

    public Instance PROMEDIO_DESVIACION;

    public Instance PROMEDIO_DESVIACION_MINIMO_MAXIMO;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of TipoAgregacion's attributes">
        setLocalizedLabel(ENGLISH, "aggregation type");
        setLocalizedLabel(SPANISH, "tipo de agregación");
//      setLocalizedShortLabel(ENGLISH, "type");
//      setLocalizedShortLabel(SPANISH, "tipo");
        setLocalizedCollectionLabel(ENGLISH, "Aggregation Types");
        setLocalizedCollectionLabel(SPANISH, "Tipos de Agregación");
//      setLocalizedCollectionShortLabel(ENGLISH, "Types");
//      setLocalizedCollectionShortLabel(SPANISH, "Tipos");
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        // <editor-fold defaultstate="collapsed" desc="localization of TipoAgregacion's properties">
        numero.setLocalizedLabel(ENGLISH, "aggregation type number");
        numero.setLocalizedLabel(SPANISH, "número del tipo de agregación");
        numero.setLocalizedShortLabel(ENGLISH, "number");
        numero.setLocalizedShortLabel(SPANISH, "número");
        /**/
        codigo.setLocalizedLabel(ENGLISH, "aggregation type code");
        codigo.setLocalizedLabel(SPANISH, "código del tipo de agregación");
        codigo.setLocalizedShortLabel(ENGLISH, "code");
        codigo.setLocalizedShortLabel(SPANISH, "código");
        /**/
        nombre.setLocalizedLabel(ENGLISH, "aggregation type name");
        nombre.setLocalizedLabel(SPANISH, "nombre del tipo de agregación");
        nombre.setLocalizedShortLabel(ENGLISH, "name");
        nombre.setLocalizedShortLabel(SPANISH, "nombre");
        /**/
        rangos.setLocalizedLabel(ENGLISH, "ranges");
        rangos.setLocalizedLabel(SPANISH, "rangos");
        // </editor-fold>
    }

    @Override
    protected void settleInstances() {
        super.settleInstances();
        /**/
        int grupo = 0;
        int cuenta = 1;
        int minimo = 2;
        int maximo = 4;
        int suma = 8;
        int promedio = 16;
        int desviacion = 32;
        /**/
        GRUPO.newInstanceField(numero, grupo);
        CUENTA.newInstanceField(numero, cuenta);
        MINIMO.newInstanceField(numero, minimo);
        MAXIMO.newInstanceField(numero, maximo);
        SUMA.newInstanceField(numero, suma);
        PROMEDIO.newInstanceField(numero, promedio);
        DESVIACION.newInstanceField(numero, desviacion);
        /**/
        CUENTA_MINIMO_MAXIMO.newInstanceField(numero, cuenta + minimo + maximo);
        MINIMO_MAXIMO.newInstanceField(numero, minimo + maximo);
        SUMA_CUENTA_PROMEDIO.newInstanceField(numero, suma + cuenta + promedio);
        SUMA_CUENTA_PROMEDIO_DESVIACION_MINIMO_MAXIMO.newInstanceField(numero, suma + cuenta + promedio + desviacion + minimo + maximo);
        PROMEDIO_DESVIACION.newInstanceField(numero, promedio + desviacion);
        PROMEDIO_DESVIACION_MINIMO_MAXIMO.newInstanceField(numero, promedio + desviacion + minimo + maximo);
        /**/
        GRUPO.newInstanceField(codigo, "Grupo");
        CUENTA.newInstanceField(codigo, "Cuenta");
        MINIMO.newInstanceField(codigo, "Mínimo");
        MAXIMO.newInstanceField(codigo, "Máximo");
        SUMA.newInstanceField(codigo, "Suma");
        PROMEDIO.newInstanceField(codigo, "Promedio");
        DESVIACION.newInstanceField(codigo, "Desviación");
        /**/
        CUENTA_MINIMO_MAXIMO.newInstanceField(codigo, "C/Mín/Máx");
        MINIMO_MAXIMO.newInstanceField(codigo, "Mín/Máx");
        SUMA_CUENTA_PROMEDIO.newInstanceField(codigo, "S/C/P");
        SUMA_CUENTA_PROMEDIO_DESVIACION_MINIMO_MAXIMO.newInstanceField(codigo, "S/C/P/D/Mín/Máx");
        PROMEDIO_DESVIACION.newInstanceField(codigo, "P/D");
        PROMEDIO_DESVIACION_MINIMO_MAXIMO.newInstanceField(codigo, "P/D/Mín/Máx");
        /**/
        GRUPO.newInstanceField(nombre, "Grupo de filas con un mismo valor en la columna");
        CUENTA.newInstanceField(nombre, "Cantidad de filas del grupo con valor en la columna");
        MINIMO.newInstanceField(nombre, "Mínimo valor en la columna de todas las filas del grupo");
        MAXIMO.newInstanceField(nombre, "Máximo valor en la columna de todas las filas del grupo");
        SUMA.newInstanceField(nombre, "Suma de los valores en la columna de todas las filas del grupo");
        PROMEDIO.newInstanceField(nombre, "Promedio de los valores en la columna de todas las filas del grupo");
        DESVIACION.newInstanceField(nombre, "Desviación estándar de los valores en la columna de todas las filas del grupo");
        /**/
        CUENTA_MINIMO_MAXIMO.newInstanceField(nombre, "Cuenta, Mínimo, Máximo");
        MINIMO_MAXIMO.newInstanceField(nombre, "Mínimo, Máximo");
        SUMA_CUENTA_PROMEDIO.newInstanceField(nombre, "Suma, Cuenta, Promedio");
        SUMA_CUENTA_PROMEDIO_DESVIACION_MINIMO_MAXIMO.newInstanceField(nombre, "Suma, Cuenta, Promedio, Desviación, Mínimo, Máximo");
        PROMEDIO_DESVIACION.newInstanceField(nombre, "Promedio, Desviación");
        PROMEDIO_DESVIACION_MINIMO_MAXIMO.newInstanceField(nombre, "Promedio, Desviación, Mínimo, Máximo");
        /**/
        GRUPO.newInstanceField(rangos, "123");
        CUENTA.newInstanceField(rangos, "123");
        MINIMO.newInstanceField(rangos, "23");
        MAXIMO.newInstanceField(rangos, "23");
        SUMA.newInstanceField(rangos, "3");
        PROMEDIO.newInstanceField(rangos, "3");
        DESVIACION.newInstanceField(rangos, "3");
        /**/
        CUENTA_MINIMO_MAXIMO.newInstanceField(rangos, "23");
        MINIMO_MAXIMO.newInstanceField(rangos, "23");
        SUMA_CUENTA_PROMEDIO.newInstanceField(rangos, "3");
        SUMA_CUENTA_PROMEDIO_DESVIACION_MINIMO_MAXIMO.newInstanceField(rangos, "3");
        PROMEDIO_DESVIACION.newInstanceField(rangos, "3");
        PROMEDIO_DESVIACION_MINIMO_MAXIMO.newInstanceField(rangos, "3");
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of TipoAgregacion's instances">
        GRUPO.newInstanceField(codigo, "Group", ENGLISH);
        GRUPO.newInstanceField(codigo, "Grupo", SPANISH);
        /**/
        GRUPO.newInstanceField(nombre, "Group of rows with the same value in the column", ENGLISH);
        GRUPO.newInstanceField(nombre, "Grupo de filas con un mismo valor en la columna", SPANISH);
        /**/
        CUENTA.newInstanceField(codigo, "Count", ENGLISH);
        CUENTA.newInstanceField(codigo, "Cuenta", SPANISH);
        /**/
        CUENTA.newInstanceField(nombre, "Number of rows in the group with value in the column", ENGLISH);
        CUENTA.newInstanceField(nombre, "Cantidad de filas del grupo con valor en la columna", SPANISH);
        /**/
        MINIMO.newInstanceField(codigo, "Minimum", ENGLISH);
        MINIMO.newInstanceField(codigo, "Mínimo", SPANISH);
        /**/
        MINIMO.newInstanceField(nombre, "Minimum value in the column of all rows in the group", ENGLISH);
        MINIMO.newInstanceField(nombre, "Mínimo valor en la columna de todas las filas del grupo", SPANISH);
        /**/
        MAXIMO.newInstanceField(codigo, "Maximum", ENGLISH);
        MAXIMO.newInstanceField(codigo, "Máximo", SPANISH);
        /**/
        MAXIMO.newInstanceField(nombre, "Maximum value in the column of all rows in the group", ENGLISH);
        MAXIMO.newInstanceField(nombre, "Máximo valor en la columna de todas las filas del grupo", SPANISH);
        /**/
        SUMA.newInstanceField(codigo, "Sum", ENGLISH);
        SUMA.newInstanceField(codigo, "Suma", SPANISH);
        /**/
        SUMA.newInstanceField(nombre, "Sum of the values in the column of all rows in the group", ENGLISH);
        SUMA.newInstanceField(nombre, "Suma de los valores en la columna de todas las filas del grupo", SPANISH);
        /**/
        PROMEDIO.newInstanceField(codigo, "Average", ENGLISH);
        PROMEDIO.newInstanceField(codigo, "Promedio", SPANISH);
        /**/
        PROMEDIO.newInstanceField(nombre, "Average of the values in the column of all rows in the group", ENGLISH);
        PROMEDIO.newInstanceField(nombre, "Promedio de los valores en la columna de todas las filas del grupo", SPANISH);
        /**/
        DESVIACION.newInstanceField(codigo, "Deviation", ENGLISH);
        DESVIACION.newInstanceField(codigo, "Desviación", SPANISH);
        /**/
        DESVIACION.newInstanceField(nombre, "Standard Deviation of the values in the column of all rows in the group", ENGLISH);
        DESVIACION.newInstanceField(nombre, "Desviación estándar de los valores en la columna de todas las filas del grupo", SPANISH);
        /**/
        CUENTA_MINIMO_MAXIMO.newInstanceField(codigo, "C/Min/Max", ENGLISH);
        CUENTA_MINIMO_MAXIMO.newInstanceField(codigo, "C/Mín/Máx", SPANISH);
        /**/
        CUENTA_MINIMO_MAXIMO.newInstanceField(nombre, "Count, Minimum, Maximum", ENGLISH);
        CUENTA_MINIMO_MAXIMO.newInstanceField(nombre, "Cuenta, Mínimo, Máximo", SPANISH);
        /**/
        MINIMO_MAXIMO.newInstanceField(codigo, "Min/Max", ENGLISH);
        MINIMO_MAXIMO.newInstanceField(codigo, "Mín/Máx", SPANISH);
        /**/
        MINIMO_MAXIMO.newInstanceField(nombre, "Minimum, Maximum", ENGLISH);
        MINIMO_MAXIMO.newInstanceField(nombre, "Mínimo, Máximo", SPANISH);
        /**/
        SUMA_CUENTA_PROMEDIO.newInstanceField(codigo, "S/C/A", ENGLISH);
        SUMA_CUENTA_PROMEDIO.newInstanceField(codigo, "S/C/P", SPANISH);
        /**/
        SUMA_CUENTA_PROMEDIO.newInstanceField(nombre, "Sum, Count, Average", ENGLISH);
        SUMA_CUENTA_PROMEDIO.newInstanceField(nombre, "Suma, Cuenta, Promedio", SPANISH);
        /**/
        SUMA_CUENTA_PROMEDIO_DESVIACION_MINIMO_MAXIMO.newInstanceField(codigo, "S/C/A/D/Min/Max", ENGLISH);
        SUMA_CUENTA_PROMEDIO_DESVIACION_MINIMO_MAXIMO.newInstanceField(codigo, "S/C/P/D/Mín/Máx", SPANISH);
        /**/
        SUMA_CUENTA_PROMEDIO_DESVIACION_MINIMO_MAXIMO.newInstanceField(nombre, "Sum, Count, Average, Deviation, Minimum, Maximum", ENGLISH);
        SUMA_CUENTA_PROMEDIO_DESVIACION_MINIMO_MAXIMO.newInstanceField(nombre, "Suma, Cuenta, Promedio, Desviación, Mínimo, Máximo", SPANISH);
        /**/
        PROMEDIO_DESVIACION.newInstanceField(codigo, "A/D", ENGLISH);
        PROMEDIO_DESVIACION.newInstanceField(codigo, "P/D", SPANISH);
        /**/
        PROMEDIO_DESVIACION.newInstanceField(nombre, "Average, Deviation", ENGLISH);
        PROMEDIO_DESVIACION.newInstanceField(nombre, "Promedio, Desviación", SPANISH);
        /**/
        PROMEDIO_DESVIACION_MINIMO_MAXIMO.newInstanceField(codigo, "A/D/Min/Max", ENGLISH);
        PROMEDIO_DESVIACION_MINIMO_MAXIMO.newInstanceField(codigo, "P/D/Mín/Máx", SPANISH);
        /**/
        PROMEDIO_DESVIACION_MINIMO_MAXIMO.newInstanceField(nombre, "Average, Deviation, Minimum, Maximum", ENGLISH);
        PROMEDIO_DESVIACION_MINIMO_MAXIMO.newInstanceField(nombre, "Promedio, Desviación, Mínimo, Máximo", SPANISH);
        // </editor-fold>
    }

}
