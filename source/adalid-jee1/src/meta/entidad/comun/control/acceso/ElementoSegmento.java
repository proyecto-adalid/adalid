/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.entidad.comun.control.acceso;

import adalid.core.*;
import adalid.core.annotations.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import adalid.core.properties.*;
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

    protected Key uk_elemento_segmento_0001;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        setDefaultLabel("elemento del conjunto de segmentos");
        setDefaultShortLabel("elemento");
        setDefaultCollectionLabel("elementos del conjunto de segmentos");
        setDefaultCollectionShortLabel("elementos");
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

    @Override
    protected void settleKeys() {
        super.settleKeys();
        uk_elemento_segmento_0001.setUnique(true);
        uk_elemento_segmento_0001.newKeyField(idConjuntoSegmento, idSegmento);
    }

}
