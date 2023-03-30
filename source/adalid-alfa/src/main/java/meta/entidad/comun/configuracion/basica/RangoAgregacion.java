/*
 * Copyright 2017 Jorge Campins y David Uzcategui
 *
 * Este archivo forma parte de Adalid.
 *
 * Adalid es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos de la
 * licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Adalid se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNO GARANTIA; sin
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
public class RangoAgregacion extends AbstractPersistentEnumerationEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public RangoAgregacion(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public IntegerProperty numero;

    @BusinessKey
    public StringProperty codigo;

    public Instance VACIO;

    public Instance CUENTA;

    public Instance CUENTA_MINIMO_MAXIMO;

    public Instance SUMA_CUENTA_PROMEDIO_DESVIACION_MINIMO_MAXIMO;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of RangoAgregacion's attributes">
        setLocalizedLabel(ENGLISH, "aggregation operator range");
        setLocalizedLabel(SPANISH, "rango de operadores de agregación");
        setLocalizedShortLabel(ENGLISH, "operator range");
        setLocalizedShortLabel(SPANISH, "rango de operadores");
        setLocalizedCollectionLabel(ENGLISH, "Aggregation Operator Ranges");
        setLocalizedCollectionLabel(SPANISH, "Rangos de Operadores de Agregación");
        setLocalizedCollectionShortLabel(ENGLISH, "Operator Ranges");
        setLocalizedCollectionShortLabel(SPANISH, "Rangos de Operadores");
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        // <editor-fold defaultstate="collapsed" desc="localization of RangoAgregacion's properties">
        /**/
        numero.setLocalizedLabel(ENGLISH, "aggregation operator range number");
        numero.setLocalizedLabel(SPANISH, "número del rango de operadores de agregación");
        numero.setLocalizedShortLabel(ENGLISH, "number");
        numero.setLocalizedShortLabel(SPANISH, "número");
        /**/
        codigo.setLocalizedLabel(ENGLISH, "aggregation operator range code");
        codigo.setLocalizedLabel(SPANISH, "código del rango de operadores de agregación");
        codigo.setLocalizedShortLabel(ENGLISH, "code");
        codigo.setLocalizedShortLabel(SPANISH, "código");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleInstances() {
        super.settleInstances();
        // <editor-fold defaultstate="collapsed" desc="localization of TipoDatoPar's instances">
        /**/
        VACIO.newInstanceField(codigo, "No aggregation operators", ENGLISH);
        VACIO.newInstanceField(codigo, "Sin operadores de agregación", SPANISH);
        /**/
        CUENTA.newInstanceField(codigo, "Count", ENGLISH);
        CUENTA.newInstanceField(codigo, "Cuenta", SPANISH);
        /**/
        CUENTA_MINIMO_MAXIMO.newInstanceField(codigo, "C/Min/Max", ENGLISH);
        CUENTA_MINIMO_MAXIMO.newInstanceField(codigo, "C/Mín/Máx", SPANISH);
        /**/
        SUMA_CUENTA_PROMEDIO_DESVIACION_MINIMO_MAXIMO.newInstanceField(codigo, "S/C/A/D/Min/Max", ENGLISH);
        SUMA_CUENTA_PROMEDIO_DESVIACION_MINIMO_MAXIMO.newInstanceField(codigo, "S/C/P/D/Mín/Máx", SPANISH);
        /**/
        // </editor-fold>
        /**/
        // ADVERTENCIA: los números se usan para dar valor a la propiedad rangos de la entidad TipoAgregacion
        /**/
        VACIO.newInstanceField(numero, 0);
        CUENTA.newInstanceField(numero, 1);
        CUENTA_MINIMO_MAXIMO.newInstanceField(numero, 2);
        SUMA_CUENTA_PROMEDIO_DESVIACION_MINIMO_MAXIMO.newInstanceField(numero, 3);
        /**/
    }

}
