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
package meta.entidad.comun.control.prueba;

import adalid.core.*;
import adalid.core.annotations.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import java.lang.reflect.Field;
import meta.entidad.base.PersistentEnumerationEntityBase;

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
public class TipoResultadoPrueba extends PersistentEnumerationEntityBase {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public TipoResultadoPrueba(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of TipoResultadoPrueba's attributes">
        setLocalizedLabel(ENGLISH, "test result type");
        setLocalizedLabel(SPANISH, "tipo de resultado de prueba");
        setLocalizedShortLabel(ENGLISH, "result type");
        setLocalizedShortLabel(SPANISH, "tipo de resultado");
        setLocalizedCollectionLabel(ENGLISH, "Test Result Types");
        setLocalizedCollectionLabel(SPANISH, "Tipos de Resultados de Prueba");
        setLocalizedCollectionShortLabel(ENGLISH, "Result Types");
        setLocalizedCollectionShortLabel(SPANISH, "Tipos de Resultados");
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        // <editor-fold defaultstate="collapsed" desc="localization of TipoResultadoPrueba's properties">
        numero.setLocalizedLabel(ENGLISH, "test result type number");
        numero.setLocalizedLabel(SPANISH, "número del tipo de resultado de prueba");
        numero.setLocalizedShortLabel(ENGLISH, "number");
        numero.setLocalizedShortLabel(SPANISH, "número");
        /**/
        codigo.setLocalizedLabel(ENGLISH, "test result type code");
        codigo.setLocalizedLabel(SPANISH, "código del tipo de resultado de prueba");
        codigo.setLocalizedShortLabel(ENGLISH, "code");
        codigo.setLocalizedShortLabel(SPANISH, "código");
        // </editor-fold>
    }

    public Instance EXITO;

    public Instance ERROR;

    @Override
    protected void settleInstances() {
        super.settleInstances();
        // <editor-fold defaultstate="collapsed" desc="localization of TipoResultadoPrueba's instances">
        EXITO.newInstanceField(codigo, "Success", ENGLISH);
        EXITO.newInstanceField(codigo, "Exito", SPANISH);
        /**/
        ERROR.newInstanceField(codigo, "Error", ENGLISH);
        ERROR.newInstanceField(codigo, "Error", SPANISH);
        // </editor-fold>
    }

}
