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
import meta.entidad.comun.configuracion.basica.ClaseRecurso;
import meta.proyecto.base.ProyectoBase;

/**
 * @author Jorge Campins
 */
@EntityClass(independent = Kleenean.TRUE, resourceType = ResourceType.OPERATION, resourceGender = ResourceGender.MASCULINE)
@EntitySelectOperation(enabled = Kleenean.TRUE)
@EntityInsertOperation(enabled = Kleenean.TRUE)
@EntityUpdateOperation(enabled = Kleenean.TRUE)
@EntityDeleteOperation(enabled = Kleenean.TRUE)
@EntityTableView(enabled = Kleenean.TRUE, inserts = Kleenean.TRUE, updates = Kleenean.TRUE)
@EntityDetailView(enabled = Kleenean.TRUE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
public class ConjuntoSegmento extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    @Deprecated
    private ConjuntoSegmento() {
        this(null, null);
    }

    public ConjuntoSegmento(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public LongProperty idConjuntoSegmento;

    @VersionProperty
    public LongProperty versionConjuntoSegmento;

    @BusinessKey
    @StringField(maxLength = 200)
    public StringProperty codigoConjuntoSegmento;

    @NameProperty
    @StringField(maxLength = 200)
    public StringProperty nombreConjuntoSegmento;

    @DescriptionProperty
    public StringProperty descripcionConjuntoSegmento;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.FALSE)
    @Allocation(maxDepth = 1, maxRound = 0)
    public ClaseRecurso idClaseRecurso;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        setDefaultLabel("conjunto de segmentos");
        setDefaultShortLabel("conjunto");
        setDefaultCollectionLabel("conjuntos de segmentos");
        setDefaultCollectionShortLabel("conjuntos");
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        idClaseRecurso.setSearchQueryFilter(idClaseRecurso.esClaseRecursoSegmento.isTrue());
        idClaseRecurso.getSearchQueryFilter().setDefaultErrorMessage(""
            + "la clase de recurso no es una clase utilizada para segmentar"
            + "");
    }

}
