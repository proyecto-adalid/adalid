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
@EntityClass(independent = Kleenean.FALSE, resourceType = ResourceType.TESTING, resourceGender = ResourceGender.MASCULINE)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE, onload = SelectOnloadOption.EXECUTE)
@EntityInsertOperation(enabled = Kleenean.TRUE)
@EntityUpdateOperation(enabled = Kleenean.TRUE)
@EntityDeleteOperation(enabled = Kleenean.TRUE)
@EntityTableView(enabled = Kleenean.TRUE, inserts = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.FALSE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.TRUE)
//@EntityDataGen(start = 1, stop = 10)
public class ParteAmbientePrueba extends PersistentEntityBase {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public ParteAmbientePrueba(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Override
    protected void addAllocationStrings() {
        super.addAllocationStrings();
        super.addAllocationStrings(
            "pieza.tipo"
        );
    }

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of ParteAmbientePrueba's attributes">
        setLocalizedLabel(ENGLISH, "component/environment association");
        setLocalizedLabel(SPANISH, "asociación Pieza/Ambiente");
        setLocalizedCollectionLabel(ENGLISH, "Component/Environment Associations");
        setLocalizedCollectionLabel(SPANISH, "Asociaciones Pieza/Ambiente");
        /**/
        setLocalizedCollectionLabel(ENGLISH, ambiente, "Components by Environment");
        setLocalizedCollectionLabel(SPANISH, ambiente, "Piezas por Ambiente");
        setLocalizedCollectionShortLabel(ENGLISH, ambiente, "Components");
        setLocalizedCollectionShortLabel(SPANISH, ambiente, "Piezas");
        /**/
        setLocalizedCollectionLabel(ENGLISH, pieza, "Environments by Component");
        setLocalizedCollectionLabel(SPANISH, pieza, "Ambientes por Pieza");
        setLocalizedCollectionShortLabel(ENGLISH, pieza, "Environments");
        setLocalizedCollectionShortLabel(SPANISH, pieza, "Ambientes");
        // </editor-fold>
    }

    @ColumnField(nullable = Kleenean.FALSE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.TABLE)
    @PropertyField(create = Kleenean.TRUE, update = Kleenean.TRUE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public AmbientePrueba ambiente;

    @ColumnField(nullable = Kleenean.FALSE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(create = Kleenean.TRUE, update = Kleenean.TRUE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public PiezaAmbientePrueba pieza;

    @StringField(maxLength = 30)
    @PropertyField(create = Kleenean.TRUE, update = Kleenean.TRUE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public StringProperty versionImplementacion;

    @Override
    protected void settleProperties() {
        super.settleProperties();
        // <editor-fold defaultstate="collapsed" desc="localization of ParteAmbientePrueba's properties">
        ambiente.setLocalizedLabel(ENGLISH, "environment");
        ambiente.setLocalizedLabel(SPANISH, "ambiente");
        /**/
        pieza.setLocalizedLabel(ENGLISH, "component");
        pieza.setLocalizedLabel(SPANISH, "pieza");
        /**/
        versionImplementacion.setLocalizedLabel(ENGLISH, "implementation version");
        versionImplementacion.setLocalizedLabel(SPANISH, "versión implementación");
        versionImplementacion.setLocalizedShortLabel(ENGLISH, "version");
        versionImplementacion.setLocalizedShortLabel(SPANISH, "versión");
        // </editor-fold>
    }

    Key key1;

    @Override
    protected void settleKeys() {
        super.settleKeys();
        key1.setUnique(true);
        key1.newKeyField(ambiente, pieza);
    }

    @Override
    protected void settleFilters() {
        super.settleFilters();
        versionImplementacion.setRequiringFilter(pieza.tipo.isEqualTo(pieza.tipo.SOFTWARE));
    }

}
