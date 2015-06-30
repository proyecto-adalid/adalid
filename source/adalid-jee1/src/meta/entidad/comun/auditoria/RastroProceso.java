/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.entidad.comun.auditoria;

import adalid.core.AbstractPersistentEntity;
import adalid.core.Tab;
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
import adalid.core.annotations.EntityTriggers;
import adalid.core.annotations.EntityUpdateOperation;
import adalid.core.annotations.FileReference;
import adalid.core.annotations.ForeignKey;
import adalid.core.annotations.ManyToOne;
import adalid.core.annotations.OwnerProperty;
import adalid.core.annotations.PrimaryKey;
import adalid.core.annotations.PropertyField;
import adalid.core.annotations.SegmentProperty;
import adalid.core.annotations.StringField;
import adalid.core.enums.Kleenean;
import adalid.core.enums.MasterDetailView;
import adalid.core.enums.Navigability;
import adalid.core.enums.OnDeleteAction;
import adalid.core.enums.OnUpdateAction;
import adalid.core.enums.ResourceGender;
import adalid.core.enums.ResourceType;
import adalid.core.enums.SortOption;
import adalid.core.enums.SpecialTemporalValue;
import adalid.core.interfaces.Artifact;
import adalid.core.properties.IntegerProperty;
import adalid.core.properties.LongProperty;
import adalid.core.properties.StringProperty;
import adalid.core.properties.TimestampProperty;
import java.lang.reflect.Field;
import meta.entidad.comun.configuracion.basica.CondicionEjeFun;
import meta.entidad.comun.configuracion.basica.Funcion;
import meta.entidad.comun.control.acceso.Usuario;
import meta.entidad.comun.operacion.basica.RecursoValor;
import meta.proyecto.base.ProyectoBase;

/**
 * @author Jorge Campins
 */
@EntityClass(independent = Kleenean.TRUE, resourceType = ResourceType.OPERATION, resourceGender = ResourceGender.MASCULINE)
@EntitySelectOperation(enabled = Kleenean.TRUE, sortOption = SortOption.DESC)
@EntityInsertOperation(enabled = Kleenean.FALSE)
@EntityUpdateOperation(enabled = Kleenean.FALSE)
@EntityDeleteOperation(enabled = Kleenean.FALSE)
@EntityTableView(enabled = Kleenean.TRUE)
@EntityDetailView(enabled = Kleenean.TRUE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
@EntityTriggers(afterValue = Kleenean.TRUE)
public class RastroProceso extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    @Deprecated
    private RastroProceso() {
        this(null, null);
    }

    public RastroProceso(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public LongProperty idRastroProceso;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE, search = Kleenean.FALSE)
    public TimestampProperty fechaHoraInicioEjecucion;

    public TimestampProperty fechaHoraFinEjecucion;

    @OwnerProperty
    @SegmentProperty
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(hidden = Kleenean.TRUE)
    @Allocation(maxDepth = 1, maxRound = 0)
    public Usuario idUsuario;

    @PropertyField(table = Kleenean.TRUE, search = Kleenean.TRUE, report = Kleenean.TRUE)
    @StringField(maxLength = 30)
    public StringProperty codigoUsuario;

    @PropertyField(table = Kleenean.TRUE, search = Kleenean.TRUE, report = Kleenean.TRUE)
    @StringField(maxLength = 100)
    public StringProperty nombreUsuario;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE)
    @Allocation(maxDepth = 1, maxRound = 0)
    public Funcion idFuncion;

    @PropertyField(hidden = Kleenean.TRUE)
    public LongProperty idClaseRecursoValor;

    @Allocation(maxDepth = 1, maxRound = 0)
    @ColumnField(nullable = Kleenean.TRUE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(table = Kleenean.TRUE, detail = Kleenean.TRUE, search = Kleenean.FALSE, report = Kleenean.FALSE, heading = Kleenean.FALSE)
    public RecursoValor recursoValor;

    @PropertyField(hidden = Kleenean.TRUE)
    public LongProperty idRecurso;

    @PropertyField(hidden = Kleenean.TRUE)
    public LongProperty versionRecurso;

    @PropertyField(table = Kleenean.FALSE, detail = Kleenean.FALSE, search = Kleenean.TRUE, report = Kleenean.TRUE)
    public StringProperty codigoRecurso;

    @PropertyField(table = Kleenean.FALSE, detail = Kleenean.FALSE, search = Kleenean.TRUE, report = Kleenean.TRUE)
    public StringProperty nombreRecurso;

    @PropertyField(hidden = Kleenean.TRUE)
    public LongProperty idPropietarioRecurso;

    @PropertyField(hidden = Kleenean.TRUE)
    public LongProperty idSegmentoRecurso;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE)
    @Allocation(maxDepth = 1, maxRound = 0)
    public CondicionEjeFun numeroCondicionEjeFun;

    @FileReference
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.FALSE, search = Kleenean.FALSE)
    public StringProperty nombreArchivo;

//  @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE)
    public StringProperty descripcionError;

    public LongProperty idGrupoProceso;

    public LongProperty idRastroProcesoSuperior;

    @ColumnField(nullable = Kleenean.FALSE)
    public IntegerProperty subprocesos;

    @ColumnField(nullable = Kleenean.FALSE)
    public IntegerProperty subprocesosPendientes;

    @ColumnField(nullable = Kleenean.FALSE)
    public IntegerProperty subprocesosEnProgreso;

    @ColumnField(nullable = Kleenean.FALSE)
    public IntegerProperty subprocesosSinErrores;

    @ColumnField(nullable = Kleenean.FALSE)
    public IntegerProperty subprocesosConErrores;

    @ColumnField(nullable = Kleenean.FALSE)
    public IntegerProperty subprocesosCancelados;

    @StringField(maxLength = 100)
    public StringProperty procedimientoAfterUpdate;

    @PropertyField(hidden = Kleenean.TRUE)
    @StringField(maxLength = 100)
    public StringProperty paginaFuncion;

    @PropertyField(hidden = Kleenean.TRUE)
    @StringField(maxLength = 100)
    public StringProperty paginaRecurso;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        setDefaultLabel("rastro de proceso");
        setDefaultShortLabel("rastro");
        setDefaultCollectionLabel("Rastros de Proceso");
        setDefaultCollectionShortLabel("Rastros");
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        codigoUsuario.setDefaultLabel("usuario");
        codigoUsuario.setDefaultTooltip("usuario");
        nombreUsuario.setDefaultLabel("nombre del usuario");
        nombreUsuario.setDefaultTooltip("nombre del usuario");
        recursoValor.setDefaultLabel("recurso");
        recursoValor.setDefaultTooltip("recurso");
        recursoValor.codigo.setDefaultLabel("recurso");
        recursoValor.codigo.setDefaultTooltip("recurso");
        recursoValor.nombre.setDefaultLabel("nombre del recurso");
        recursoValor.nombre.setDefaultTooltip("nombre del recurso");
        codigoRecurso.setDefaultLabel("recurso");
        codigoRecurso.setDefaultTooltip("recurso");
        nombreRecurso.setDefaultLabel("nombre del recurso");
        nombreRecurso.setDefaultTooltip("nombre del recurso");
        fechaHoraInicioEjecucion.setInitialValue(SpecialTemporalValue.CURRENT_TIMESTAMP);
        fechaHoraInicioEjecucion.setDefaultValue(SpecialTemporalValue.CURRENT_TIMESTAMP);
        numeroCondicionEjeFun.setInitialValue(numeroCondicionEjeFun.EJECUCION_PENDIENTE);
        numeroCondicionEjeFun.setDefaultValue(numeroCondicionEjeFun.EJECUCION_PENDIENTE);
        subprocesos.setInitialValue(0);
        subprocesos.setDefaultValue(0);
        subprocesosPendientes.setInitialValue(0);
        subprocesosPendientes.setDefaultValue(0);
        subprocesosEnProgreso.setInitialValue(0);
        subprocesosEnProgreso.setDefaultValue(0);
        subprocesosSinErrores.setInitialValue(0);
        subprocesosSinErrores.setDefaultValue(0);
        subprocesosConErrores.setInitialValue(0);
        subprocesosConErrores.setDefaultValue(0);
        subprocesosCancelados.setInitialValue(0);
        subprocesosCancelados.setDefaultValue(0);
        fechaHoraInicioEjecucion.setDefaultLabel("inicio");
        fechaHoraFinEjecucion.setDefaultLabel("fin");
        nombreArchivo.setDefaultLabel("archivo");
        descripcionError.setDefaultLabel("mensaje");
        idGrupoProceso.setDefaultLabel("grupo de procesos");
    }

    protected Tab tab1, tab2;

    @Override
    protected void settleTabs() {
        super.settleTabs();
        tab1.setDefaultLabel("general");
        tab1.newTabField(fechaHoraInicioEjecucion, fechaHoraFinEjecucion, idUsuario, codigoUsuario, nombreUsuario, idFuncion, recursoValor,
            numeroCondicionEjeFun, nombreArchivo, descripcionError);
        tab2.setDefaultLabel("subprocesos");
        tab2.newTabField(idGrupoProceso, idRastroProcesoSuperior, subprocesos, subprocesosPendientes, subprocesosEnProgreso,
            subprocesosSinErrores, subprocesosConErrores, subprocesosCancelados, procedimientoAfterUpdate);
    }

}
