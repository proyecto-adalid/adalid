/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.entidad.comun.control.prueba;

import adalid.core.ProcessOperation;
import adalid.core.Tab;
import adalid.core.annotations.Allocation;
import adalid.core.annotations.BusinessKey;
import adalid.core.annotations.ColumnField;
import adalid.core.annotations.DescriptionProperty;
import adalid.core.annotations.EntityClass;
import adalid.core.annotations.EntityConsoleView;
import adalid.core.annotations.EntityDataGen;
import adalid.core.annotations.EntityDeleteOperation;
import adalid.core.annotations.EntityDetailView;
import adalid.core.annotations.EntityInsertOperation;
import adalid.core.annotations.EntitySelectOperation;
import adalid.core.annotations.EntityTableView;
import adalid.core.annotations.EntityTreeView;
import adalid.core.annotations.EntityTriggers;
import adalid.core.annotations.EntityUpdateOperation;
import adalid.core.annotations.FileReference;
import adalid.core.annotations.ForeignKey;
import adalid.core.annotations.InstanceReference;
import adalid.core.annotations.ManyToOne;
import adalid.core.annotations.NameProperty;
import adalid.core.annotations.OwnerProperty;
import adalid.core.annotations.ParameterField;
import adalid.core.annotations.ProcessOperationClass;
import adalid.core.annotations.PropertyField;
import adalid.core.annotations.StringField;
import adalid.core.enums.DefaultCondition;
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
import adalid.core.properties.BooleanProperty;
import adalid.core.properties.StringProperty;
import adalid.core.properties.TimestampProperty;
import java.lang.reflect.Field;
import meta.entidad.base.PersistentEntityBase;
import meta.entidad.comun.configuracion.basica.CondicionEjeFun;
import meta.entidad.comun.control.acceso.Usuario;
import meta.proyecto.base.ProyectoBase;

/**
 * @author Jorge Campins
 */
@EntityClass(independent = Kleenean.TRUE, resourceType = ResourceType.TESTING, resourceGender = ResourceGender.FEMININE)
@EntitySelectOperation(enabled = Kleenean.TRUE)
@EntityInsertOperation(enabled = Kleenean.TRUE)
@EntityUpdateOperation(enabled = Kleenean.TRUE)
@EntityDeleteOperation(enabled = Kleenean.TRUE)
@EntityTableView(enabled = Kleenean.TRUE, inserts = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.TRUE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.TRUE)
@EntityTriggers(afterValue = Kleenean.TRUE)
@EntityDataGen(start = 1, stop = 100, step = 0)
public class EjecucionPrueba extends PersistentEntityBase {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    @Deprecated
    private EjecucionPrueba() {
        this(null, null);
    }

    public EjecucionPrueba(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        setDefaultLabel("ejecución de programa de prueba");
        setDefaultShortLabel("ejecución de programa");
        setDefaultCollectionLabel("Ejecuciones de Programas de Prueba");
        setDefaultCollectionShortLabel("Ejecuciones de Programas");
    }

    @BusinessKey
    @StringField(maxLength = 80)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.FALSE, defaultCondition = DefaultCondition.UNCONDITIONALLY)
    public StringProperty codigo;

    @NameProperty
    @PropertyField(table = Kleenean.FALSE)
    public StringProperty nombre;

    @DescriptionProperty
    @PropertyField(create = Kleenean.TRUE)
    public StringProperty descripcion;

    @PropertyField(create = Kleenean.TRUE)
    public StringProperty comentarios;

    @ColumnField(nullable = Kleenean.FALSE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(required = Kleenean.FALSE, create = Kleenean.TRUE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public AmbientePrueba ambiente;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.TABLE)
    @PropertyField(create = Kleenean.TRUE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public PaquetePrueba paquete;

    @ColumnField(nullable = Kleenean.FALSE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.TABLE_AND_DETAIL)
    @PropertyField(create = Kleenean.TRUE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public ProgramaPrueba programa;

    @ColumnField(nullable = Kleenean.FALSE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public CondicionEjeFun condicionEjecucion;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE)
    public TimestampProperty fechaHoraCondicionEjecucion;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public TipoResultadoPrueba tipoResultado;

    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public BooleanProperty resultadoEsperado;

    @OwnerProperty
    @ColumnField(nullable = Kleenean.FALSE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.FALSE, report = Kleenean.FALSE)
    public Usuario responsable;

    @Override
    protected void settleProperties() {
        super.settleProperties();
        codigo.setDefaultValue(programa.codigo.concat("-").concat(id.toCharString()));
        condicionEjecucion.setInitialValue(condicionEjecucion.EJECUCION_PENDIENTE);
        condicionEjecucion.setDefaultValue(condicionEjecucion.EJECUCION_PENDIENTE);
        fechaHoraCondicionEjecucion.setInitialValue(SpecialTemporalValue.CURRENT_TIMESTAMP);
        fechaHoraCondicionEjecucion.setDefaultValue(SpecialTemporalValue.CURRENT_TIMESTAMP);
        responsable.setDefaultLabel("responsable de la ejecución");
        responsable.setDefaultShortLabel("responsable");
    }

    protected Tab tab1, tab2, tab3;

    @Override
    protected void settleTabs() {
        super.settleTabs();
        tab1.setDefaultLabel("datos básicos");
        tab1.newTabField(descripcion, comentarios);
        tab2.setDefaultLabel("enlaces");
        tab2.newTabField(ambiente, paquete, programa, responsable);
        tab3.setDefaultLabel("condición");
        tab3.newTabField(condicionEjecucion, fechaHoraCondicionEjecucion, tipoResultado, resultadoEsperado);
    }

    protected Cargar cargar;

    @ProcessOperationClass(overloading = Kleenean.FALSE)
    public class Cargar extends ProcessOperation {

        @InstanceReference
        @Allocation(maxDepth = 1, maxRound = 0)
        protected EjecucionPrueba ejecucion;

        @FileReference
        @ParameterField(required = Kleenean.TRUE)
        protected StringParameter archivo;

        @ParameterField(required = Kleenean.FALSE)
        protected StringParameter descripcionArchivo;

    }

    protected AsignarResponsable asignarResponsable;

    @ProcessOperationClass(overloading = Kleenean.FALSE)
    public class AsignarResponsable extends ProcessOperation {

        @InstanceReference
        @Allocation(maxDepth = 1, maxRound = 0)
        protected EjecucionPrueba ejecucion;

        @ParameterField(required = Kleenean.TRUE, linkedField = "responsable")
        protected Usuario responsable;

    }

}
