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
//@EntityReferenceDisplay(filterType = EntityReferenceFilterType.LIST)
public class RangoComparacion extends AbstractPersistentEnumerationEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public RangoComparacion(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public IntegerProperty numero;

    @BusinessKey
    public StringProperty codigo;

    public Instance DISCRETO;

    public Instance LOGICO;

    public Instance TEXTO;

    public Instance CONTINUO;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of RangoComparacion's attributes">
        setLocalizedLabel(ENGLISH, "comparison operator range");
        setLocalizedLabel(SPANISH, "rango de operadores de comparación");
        setLocalizedShortLabel(ENGLISH, "operator range");
        setLocalizedShortLabel(SPANISH, "rango de operadores");
        setLocalizedCollectionLabel(ENGLISH, "Comparison Operator Ranges");
        setLocalizedCollectionLabel(SPANISH, "Rangos de Operadores de Comparación");
        setLocalizedCollectionShortLabel(ENGLISH, "Operator Ranges");
        setLocalizedCollectionShortLabel(SPANISH, "Rangos de Operadores");
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        // <editor-fold defaultstate="collapsed" desc="localization of RangoComparacion's properties">
        /**/
        numero.setLocalizedLabel(ENGLISH, "comparison operator range number");
        numero.setLocalizedLabel(SPANISH, "número del rango de operadores de comparación");
        numero.setLocalizedShortLabel(ENGLISH, "number");
        numero.setLocalizedShortLabel(SPANISH, "número");
        /**/
        codigo.setLocalizedLabel(ENGLISH, "comparison operator range code");
        codigo.setLocalizedLabel(SPANISH, "código del rango de operadores de comparación");
        codigo.setLocalizedShortLabel(ENGLISH, "code");
        codigo.setLocalizedShortLabel(SPANISH, "código");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleInstances() {
        super.settleInstances();
        // <editor-fold defaultstate="collapsed" desc="localization of RangoComparacion's instances">
        /**/
        DISCRETO.newInstanceField(codigo, "Discrete", ENGLISH);
        DISCRETO.newInstanceField(codigo, "Discreto", SPANISH);
        /**/
        LOGICO.newInstanceField(codigo, "Logic", ENGLISH);
        LOGICO.newInstanceField(codigo, "Logico", SPANISH);
        /**/
        TEXTO.newInstanceField(codigo, "Text", ENGLISH);
        TEXTO.newInstanceField(codigo, "Texto", SPANISH);
        /**/
        CONTINUO.newInstanceField(codigo, "Continuous", ENGLISH);
        CONTINUO.newInstanceField(codigo, "Continuo", SPANISH);
        /**/
        // </editor-fold>
        /**/
        // ADVERTENCIA: los números se usan para dar valor a la propiedad rangos de la entidad OperadorCom
        /**/
        DISCRETO.newInstanceField(numero, 0);
        LOGICO.newInstanceField(numero, 1);
        TEXTO.newInstanceField(numero, 2);
        CONTINUO.newInstanceField(numero, 3);
        /**/
    }

}
