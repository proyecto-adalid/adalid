/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.entidad.comun.control.prueba;

import adalid.core.Key;
import adalid.core.annotations.Allocation;
import adalid.core.annotations.ColumnField;
import adalid.core.annotations.EntityClass;
import adalid.core.annotations.EntityConsoleView;
import adalid.core.annotations.EntityDataGen;
import adalid.core.annotations.EntityDeleteOperation;
import adalid.core.annotations.EntityDetailView;
import adalid.core.annotations.EntityInsertOperation;
import adalid.core.annotations.EntitySelectOperation;
import adalid.core.annotations.EntityTableView;
import adalid.core.annotations.EntityTreeView;
import adalid.core.annotations.EntityUpdateOperation;
import adalid.core.annotations.ForeignKey;
import adalid.core.annotations.ManyToOne;
import adalid.core.annotations.PropertyField;
import adalid.core.annotations.StringField;
import adalid.core.enums.Kleenean;
import adalid.core.enums.MasterDetailView;
import adalid.core.enums.Navigability;
import adalid.core.enums.OnDeleteAction;
import adalid.core.enums.OnUpdateAction;
import adalid.core.enums.ResourceGender;
import adalid.core.enums.ResourceType;
import adalid.core.interfaces.Artifact;
import adalid.core.properties.StringProperty;
import java.lang.reflect.Field;
import meta.entidad.base.PersistentEntityBase;
import meta.proyecto.base.ProyectoBase;

/**
 * @author Jorge Campins
 */
@EntityClass(independent = Kleenean.FALSE, resourceType = ResourceType.TESTING, resourceGender = ResourceGender.MASCULINE)
@EntitySelectOperation(enabled = Kleenean.TRUE)
@EntityInsertOperation(enabled = Kleenean.TRUE)
@EntityUpdateOperation(enabled = Kleenean.TRUE)
@EntityDeleteOperation(enabled = Kleenean.TRUE)
@EntityTableView(enabled = Kleenean.TRUE, inserts = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.FALSE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.TRUE)
@EntityDataGen(start = 1, stop = 10, step = 0)
public class ParteAmbientePrueba extends PersistentEntityBase {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    @Deprecated
    private ParteAmbientePrueba() {
        this(null, null);
    }

    public ParteAmbientePrueba(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        setDefaultLabel("asociaci??n Pieza/Ambiente");
        setDefaultCollectionLabel("Asociaciones Pieza/Ambiente");
        setDefaultCollectionLabel(pieza, "Ambientes por Pieza");
        setDefaultCollectionShortLabel(pieza, "Ambientes");
        setDefaultCollectionLabel(ambiente, "Piezas por Ambiente");
        setDefaultCollectionShortLabel(ambiente, "Piezas");
    }

    @ColumnField(nullable = Kleenean.FALSE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.TABLE)
    @PropertyField(create = Kleenean.TRUE, update = Kleenean.TRUE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public AmbientePrueba ambiente;

    @Allocation(maxDepth = 2, maxRound = 0)
    @ColumnField(nullable = Kleenean.FALSE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(create = Kleenean.TRUE, update = Kleenean.TRUE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public PiezaAmbientePrueba pieza;

    @StringField(maxLength = 30)
    @PropertyField(create = Kleenean.TRUE, update = Kleenean.TRUE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public StringProperty version_implementacion;

    @Override
    protected void settleProperties() {
        super.settleProperties();
        version_implementacion.setDefaultLabel("versi??n implementaci??n");
        version_implementacion.setDefaultShortLabel("versi??n");
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
        version_implementacion.setRequiringFilter(pieza.tipo.isEqualTo(pieza.tipo.SOFTWARE));
    }

}
