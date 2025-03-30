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
import adalid.core.properties.*;
import java.lang.reflect.Field;
import meta.entidad.base.PersistentEntityBase;

/**
 * @author Jorge Campins
 */
@EntityClass(independent = Kleenean.TRUE, resourceType = ResourceType.TESTING, resourceGender = ResourceGender.MASCULINE)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE, onload = SelectOnloadOption.EXECUTE)
@EntityInsertOperation(enabled = Kleenean.TRUE)
@EntityUpdateOperation(enabled = Kleenean.TRUE)
@EntityDeleteOperation(enabled = Kleenean.TRUE)
@EntityTableView(enabled = Kleenean.TRUE, inserts = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.TRUE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.TRUE)
//@EntityDataGen(start = 1, stop = 10)
@EntityReferenceSearch(searchType = SearchType.LIST)
public class AmbientePrueba extends PersistentEntityBase {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public AmbientePrueba(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of AmbientePrueba's attributes">
        setLocalizedLabel(ENGLISH, "test environment");
        setLocalizedLabel(SPANISH, "ambiente de prueba");
        setLocalizedShortLabel(ENGLISH, "environment");
        setLocalizedShortLabel(SPANISH, "ambiente");
        setLocalizedCollectionLabel(ENGLISH, "Test Environments");
        setLocalizedCollectionLabel(SPANISH, "Ambientes de Prueba");
        setLocalizedCollectionShortLabel(ENGLISH, "Environments");
        setLocalizedCollectionShortLabel(SPANISH, "Ambientes");
        // </editor-fold>
    }

    @BusinessKey
    public StringProperty codigo;

    @NameProperty
    public StringProperty nombre;

    @DescriptionProperty
    @PropertyField(create = Kleenean.TRUE)
    @StringField(maxLength = 0)
    public StringProperty descripcion;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.TRUE, defaultCondition = DefaultCondition.IF_NULL_ON_INSERT)
    public BooleanProperty actual;

    @Override
    protected void settleProperties() {
        super.settleProperties();
        actual.setInitialValue(false);
        actual.setDefaultValue(false);
        // <editor-fold defaultstate="collapsed" desc="localization of AmbientePrueba's properties">
        codigo.setLocalizedLabel(ENGLISH, "test environment code");
        codigo.setLocalizedLabel(SPANISH, "código del ambiente de prueba");
        codigo.setLocalizedShortLabel(ENGLISH, "code");
        codigo.setLocalizedShortLabel(SPANISH, "código");
        /**/
        nombre.setLocalizedLabel(ENGLISH, "test environment name");
        nombre.setLocalizedLabel(SPANISH, "nombre del ambiente de prueba");
        nombre.setLocalizedShortLabel(ENGLISH, "name");
        nombre.setLocalizedShortLabel(SPANISH, "nombre");
        /**/
        descripcion.setLocalizedLabel(ENGLISH, "test environment description");
        descripcion.setLocalizedLabel(SPANISH, "descripción del ambiente de prueba");
        descripcion.setLocalizedShortLabel(ENGLISH, "description");
        descripcion.setLocalizedShortLabel(SPANISH, "descripción");
        /**/
        actual.setLocalizedLabel(ENGLISH, "current");
        actual.setLocalizedLabel(SPANISH, "actual");
        // </editor-fold>
    }

    protected DesignarActual designarActual;

    @ProcessOperationClass
    public class DesignarActual extends ProcessOperation {

        @InstanceReference
        protected AmbientePrueba ambiente;

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of DesignarActual's attributes">
            setLocalizedLabel(ENGLISH, "designate current");
            setLocalizedLabel(SPANISH, "designar actual");
            // </editor-fold>
        }

        @Override
        protected void settleParameters() {
            super.settleParameters();
            // <editor-fold defaultstate="collapsed" desc="localization of DesignarActual's parameters">
            ambiente.setLocalizedLabel(ENGLISH, "environment");
            ambiente.setLocalizedLabel(SPANISH, "ambiente");
            // </editor-fold>
        }

    }

}
