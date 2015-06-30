/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.entidad.comun.control.prueba;

import adalid.core.ProcessOperation;
import adalid.core.annotations.AbstractClass;
import adalid.core.annotations.Allocation;
import adalid.core.annotations.DescriptionProperty;
import adalid.core.annotations.DiscriminatorColumn;
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
import adalid.core.annotations.EntityWarnings;
import adalid.core.annotations.FileReference;
import adalid.core.annotations.ForeignKey;
import adalid.core.annotations.InheritanceMapping;
import adalid.core.annotations.InstanceReference;
import adalid.core.annotations.ManyToOne;
import adalid.core.annotations.ParameterField;
import adalid.core.annotations.ProcessOperationClass;
import adalid.core.annotations.PropertyField;
import adalid.core.enums.InheritanceMappingStrategy;
import adalid.core.enums.Kleenean;
import adalid.core.enums.MasterDetailView;
import adalid.core.enums.Navigability;
import adalid.core.enums.OnDeleteAction;
import adalid.core.enums.OnUpdateAction;
import adalid.core.enums.ResourceGender;
import adalid.core.enums.ResourceType;
import adalid.core.enums.SpecialTemporalValue;
import adalid.core.interfaces.Artifact;
import adalid.core.parameters.StringParameter;
import adalid.core.properties.StringProperty;
import adalid.core.properties.TimestampProperty;
import java.lang.reflect.Field;
import meta.entidad.base.PersistentEntityBase;
import meta.entidad.comun.auditoria.ArchivoAdjunto;
import meta.proyecto.base.ProyectoBase;

/**
 * @author Jorge Campins
 */
@AbstractClass
@InheritanceMapping(strategy = InheritanceMappingStrategy.SINGLE_TABLE)
@EntityClass(independent = Kleenean.FALSE, resourceType = ResourceType.TESTING, resourceGender = ResourceGender.MASCULINE)
@EntitySelectOperation(enabled = Kleenean.TRUE)
@EntityInsertOperation(enabled = Kleenean.FALSE)
@EntityUpdateOperation(enabled = Kleenean.TRUE)
@EntityDeleteOperation(enabled = Kleenean.TRUE)
@EntityTableView(enabled = Kleenean.TRUE, inserts = Kleenean.FALSE, updates = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.TRUE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.TRUE)
@EntityDataGen(start = 1, stop = 1000, step = 0)
@EntityWarnings(enabled = Kleenean.FALSE)
public class DocumentoPrueba extends PersistentEntityBase {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    @Deprecated
    private DocumentoPrueba() {
        this(null, null);
    }

    public DocumentoPrueba(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        setDefaultLabel("documento de prueba");
        setDefaultShortLabel("documento");
        setDefaultCollectionLabel("Documentos de Prueba");
        setDefaultCollectionShortLabel("Documentos");
    }

    @DiscriminatorColumn
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    public TipoDocumentoPrueba tipo;

    @FileReference
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public StringProperty archivo;

    @DescriptionProperty
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.TRUE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public StringProperty descripcion;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public ArchivoAdjunto adjunto;

    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public TimestampProperty fechaHoraCarga;

    protected Cargar cargar;

    @ProcessOperationClass(overloading = Kleenean.FALSE)
    public class Cargar extends ProcessOperation {

        @InstanceReference
        @Allocation(maxDepth = 1, maxRound = 0)
        protected DocumentoPrueba documento;

        @FileReference(joinField = "adjunto")
        @ParameterField(required = Kleenean.TRUE, linkedField = "archivo")
        protected StringParameter archivo;

        @ParameterField(required = Kleenean.FALSE, linkedField = "descripcion")
        protected StringParameter descripcion;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            documento.fechaHoraCarga.setCurrentValue(SpecialTemporalValue.CURRENT_TIMESTAMP);
        }

    }

}
