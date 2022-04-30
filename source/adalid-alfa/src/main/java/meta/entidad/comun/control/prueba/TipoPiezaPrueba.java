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
public class TipoPiezaPrueba extends PersistentEnumerationEntityBase {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public TipoPiezaPrueba(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of TipoPiezaPrueba's attributes">
        setLocalizedLabel(ENGLISH, "test environment component type");
        setLocalizedLabel(SPANISH, "tipo de pieza de ambiente de prueba");
        setLocalizedShortLabel(ENGLISH, "environment component type");
        setLocalizedShortLabel(SPANISH, "tipo de pieza de ambiente");
        setLocalizedCollectionLabel(ENGLISH, "Test Environment Component Types");
        setLocalizedCollectionLabel(SPANISH, "Tipos de Piezas de Ambientes de Prueba");
        setLocalizedCollectionShortLabel(ENGLISH, "Environment Component Types");
        setLocalizedCollectionShortLabel(SPANISH, "Tipos de Piezas de Ambientes");
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        // <editor-fold defaultstate="collapsed" desc="localization of TipoPiezaPrueba's properties">
        numero.setLocalizedLabel(ENGLISH, "test environment component type number");
        numero.setLocalizedLabel(SPANISH, "número del tipo de pieza de ambiente de prueba");
        numero.setLocalizedShortLabel(ENGLISH, "number");
        numero.setLocalizedShortLabel(SPANISH, "número");
        /**/
        codigo.setLocalizedLabel(ENGLISH, "test environment component type code");
        codigo.setLocalizedLabel(SPANISH, "código del tipo de pieza de ambiente de prueba");
        codigo.setLocalizedShortLabel(ENGLISH, "code");
        codigo.setLocalizedShortLabel(SPANISH, "código");
        // </editor-fold>
    }

    public Instance HARDWARE;

    public Instance SOFTWARE;

    public Instance OTRO;

    @Override
    protected void settleInstances() {
        super.settleInstances();
        // <editor-fold defaultstate="collapsed" desc="localization of TipoPiezaPrueba's instances">
        HARDWARE.newInstanceField(codigo, "Hardware", ENGLISH);
        HARDWARE.newInstanceField(codigo, "Hardware", SPANISH);
        /**/
        SOFTWARE.newInstanceField(codigo, "Software", ENGLISH);
        SOFTWARE.newInstanceField(codigo, "Software", SPANISH);
        /**/
        OTRO.newInstanceField(codigo, "Other", ENGLISH);
        OTRO.newInstanceField(codigo, "Otro", SPANISH);
        // </editor-fold>
    }

}
