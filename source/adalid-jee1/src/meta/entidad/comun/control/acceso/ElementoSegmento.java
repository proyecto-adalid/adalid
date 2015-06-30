/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.entidad.comun.control.acceso;

import adalid.core.AbstractPersistentEntity;
import adalid.core.Key;
import adalid.core.annotations.Allocation;
import adalid.core.annotations.ColumnField;
import adalid.core.annotations.EntityClass;
import adalid.core.annotations.EntityConsoleView;
import adalid.core.annotations.EntityDeleteOperation;
import adalid.core.annotations.EntityDetailView;
import adalid.core.annotations.EntityInsertOperation;
import adalid.core.annotations.EntitySelectOperation;
import adalid.core.annotations.EntityTableView;
import adalid.core.annotations.EntityTreeView;
import adalid.core.annotations.EntityUpdateOperation;
import adalid.core.annotations.ForeignKey;
import adalid.core.annotations.ManyToOne;
import adalid.core.annotations.PrimaryKey;
import adalid.core.annotations.PropertyField;
import adalid.core.annotations.VersionProperty;
import adalid.core.enums.Kleenean;
import adalid.core.enums.MasterDetailView;
import adalid.core.enums.Navigability;
import adalid.core.enums.OnDeleteAction;
import adalid.core.enums.OnUpdateAction;
import adalid.core.enums.ResourceGender;
import adalid.core.enums.ResourceType;
import adalid.core.interfaces.Artifact;
import adalid.core.interfaces.Segment;
import adalid.core.properties.LongProperty;
import adalid.core.properties.StringProperty;
import java.lang.reflect.Field;
import meta.proyecto.base.ProyectoBase;

/**
 * @author Jorge Campins
 */
@EntityClass(independent = Kleenean.FALSE, resourceType = ResourceType.OPERATION, resourceGender = ResourceGender.MASCULINE)
@EntitySelectOperation(enabled = Kleenean.TRUE)
@EntityInsertOperation(enabled = Kleenean.TRUE)
@EntityUpdateOperation(enabled = Kleenean.TRUE)
@EntityDeleteOperation(enabled = Kleenean.TRUE)
@EntityTableView(enabled = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.FALSE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
public class ElementoSegmento extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    @Deprecated
    private ElementoSegmento() {
        this(null, null);
    }

    public ElementoSegmento(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public LongProperty idElementoSegmento;

    @VersionProperty
    public LongProperty versionElementoSegmento;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(hidden = Kleenean.TRUE)
    public LongProperty segmentoEnteroGrande;

    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.TRUE, detail = Kleenean.TRUE, report = Kleenean.TRUE, export = Kleenean.TRUE, search = Kleenean.FALSE, heading = Kleenean.FALSE)
    @Allocation(maxDepth = 1, maxRound = 0)
    public Segmento idSegmento;

    @PropertyField(table = Kleenean.FALSE, detail = Kleenean.FALSE, search = Kleenean.TRUE)
    public StringProperty codigoSegmento;

    @PropertyField(table = Kleenean.FALSE, detail = Kleenean.FALSE, search = Kleenean.TRUE)
    public StringProperty nombreSegmento;

    @ForeignKey(onDelete = OnDeleteAction.CASCADE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.BIDIRECTIONAL, view = MasterDetailView.TABLE)
    @ColumnField(nullable = Kleenean.FALSE)
    @Allocation(maxDepth = 1, maxRound = 0)
    public ConjuntoSegmento idConjuntoSegmento;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        setDefaultLabel("elemento del conjunto de segmentos");
        setDefaultShortLabel("elemento");
        setDefaultCollectionLabel("Elementos del Conjunto de Segmentos");
        setDefaultCollectionShortLabel("Elementos");
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        idSegmento.setDefaultLabel("segmento");
        idSegmento.setDefaultTooltip("segmento");
        idSegmento.codigoSegmento.setDefaultLabel("segmento");
        idSegmento.codigoSegmento.setDefaultTooltip("segmento");
        idSegmento.nombreSegmento.setDefaultLabel("nombre del segmento");
        idSegmento.nombreSegmento.setDefaultTooltip("nombre del segmento");
        codigoSegmento.setDefaultLabel("segmento");
        codigoSegmento.setDefaultTooltip("segmento");
        nombreSegmento.setDefaultLabel("nombre del segmento");
        nombreSegmento.setDefaultTooltip("nombre del segmento");
    }

    protected Key uk_elemento_segmento_0001;

    @Override
    protected void settleKeys() {
        super.settleKeys();
        uk_elemento_segmento_0001.setUnique(true);
        uk_elemento_segmento_0001.newKeyField(idConjuntoSegmento, idSegmento);
    }

    public Segment modificables;

    @Override
    protected void settleExpressions() {
        super.settleExpressions();
        modificables = not(idConjuntoSegmento.esConjuntoEspecial);
        modificables.setDefaultErrorMessage("el conjunto es un conjunto de configuración básica del sistema; "
            + "no se permite modificarlo ni eliminarlo");
    }

    @Override
    protected void settleFilters() {
        super.settleFilters();
        setInsertFilter(idConjuntoSegmento.modificables);
        setUpdateFilter(modificables);
        setDeleteFilter(modificables);
    }

}
