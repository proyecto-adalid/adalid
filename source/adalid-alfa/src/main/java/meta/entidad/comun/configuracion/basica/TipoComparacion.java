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
@EntityClass(independent = Kleenean.TRUE, resourceType = ResourceType.CONFIGURATION, resourceGender = ResourceGender.MASCULINE)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE, access = OperationAccess.PUBLIC, rowsLimit = 0)
@EntityInsertOperation(enabled = Kleenean.FALSE)
@EntityUpdateOperation(enabled = Kleenean.FALSE)
@EntityDeleteOperation(enabled = Kleenean.FALSE)
@EntityTableView(enabled = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.FALSE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
//@EntityReferenceDisplay(filterType = EntityReferenceFilterType.TEXTBOX)
public class TipoComparacion extends AbstractPersistentEnumerationEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public TipoComparacion(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public IntegerProperty numero;

    @BusinessKey
    public StringProperty codigo;

    public Instance IGUAL_NO_IGUAL;

    public Instance MAYOR_MENOR_O_IGUAL;

    public Instance MAYOR_O_IGUAL_MENOR;

    public Instance COMIENZA_NO_COMIENZA;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of TipoComparacion's attributes">
        setLocalizedLabel(ENGLISH, "comparison type");
        setLocalizedLabel(SPANISH, "tipo de comparación");
        setLocalizedCollectionLabel(ENGLISH, "Comparison Types");
        setLocalizedCollectionLabel(SPANISH, "Tipos de Comparación");
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        // <editor-fold defaultstate="collapsed" desc="localization of TipoComparacion's properties">
        numero.setLocalizedLabel(ENGLISH, "comparison type number");
        numero.setLocalizedLabel(SPANISH, "número del tipo de comparación");
        numero.setLocalizedShortLabel(ENGLISH, "number");
        numero.setLocalizedShortLabel(SPANISH, "número");
        /**/
        codigo.setLocalizedLabel(ENGLISH, "comparison type code");
        codigo.setLocalizedLabel(SPANISH, "código del tipo de comparación");
        codigo.setLocalizedShortLabel(ENGLISH, "code");
        codigo.setLocalizedShortLabel(SPANISH, "código");
        // </editor-fold>
    }

    @Override
    protected void settleInstances() {
        super.settleInstances();
        IGUAL_NO_IGUAL.newInstanceField(numero, 1);
        IGUAL_NO_IGUAL.newInstanceField(codigo, "Igual/No igual");
        MAYOR_MENOR_O_IGUAL.newInstanceField(numero, 2);
        MAYOR_MENOR_O_IGUAL.newInstanceField(codigo, "Mayor/Menor o igual");
        MAYOR_O_IGUAL_MENOR.newInstanceField(numero, 3);
        MAYOR_O_IGUAL_MENOR.newInstanceField(codigo, "Mayor o igual/Menor");
        COMIENZA_NO_COMIENZA.newInstanceField(numero, 4);
        COMIENZA_NO_COMIENZA.newInstanceField(codigo, "Comienza/No comienza");
        // <editor-fold defaultstate="collapsed" desc="localization of TipoComparacion's instances">
        IGUAL_NO_IGUAL.newInstanceField(codigo, "Equal/Not equal", ENGLISH);
        IGUAL_NO_IGUAL.newInstanceField(codigo, "Igual/No igual", SPANISH);
        /**/
        MAYOR_MENOR_O_IGUAL.newInstanceField(codigo, "Greater/Less or equal", ENGLISH);
        MAYOR_MENOR_O_IGUAL.newInstanceField(codigo, "Mayor/Menor o igual", SPANISH);
        /**/
        MAYOR_O_IGUAL_MENOR.newInstanceField(codigo, "Greater or equal/Less", ENGLISH);
        MAYOR_O_IGUAL_MENOR.newInstanceField(codigo, "Mayor o igual/Menor", SPANISH);
        /**/
        COMIENZA_NO_COMIENZA.newInstanceField(codigo, "Start/Do not start with", ENGLISH);
        COMIENZA_NO_COMIENZA.newInstanceField(codigo, "Comienza/No comienza", SPANISH);
        // </editor-fold>
    }

}
