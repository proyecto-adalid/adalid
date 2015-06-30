/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.entidad.comun.operacion.basica;

import adalid.core.AbstractPersistentEntity;
import adalid.core.Key;
import adalid.core.ProcessOperation;
import adalid.core.Tab;
import adalid.core.Transition;
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
import adalid.core.annotations.EntityWarnings;
import adalid.core.annotations.ForeignKey;
import adalid.core.annotations.InstanceReference;
import adalid.core.annotations.ManyToOne;
import adalid.core.annotations.OperationClass;
import adalid.core.annotations.OwnerProperty;
import adalid.core.annotations.PrimaryKey;
import adalid.core.annotations.ProcessOperationClass;
import adalid.core.annotations.PropertyField;
import adalid.core.annotations.SegmentProperty;
import adalid.core.annotations.StringField;
import adalid.core.annotations.VersionProperty;
import adalid.core.enums.Kleenean;
import adalid.core.enums.MasterDetailView;
import adalid.core.enums.Navigability;
import adalid.core.enums.OnDeleteAction;
import adalid.core.enums.OnUpdateAction;
import adalid.core.enums.OperationAccess;
import adalid.core.enums.ResourceGender;
import adalid.core.enums.ResourceType;
import adalid.core.enums.SortOption;
import adalid.core.enums.SpecialCharacterValue;
import adalid.core.enums.SpecialTemporalValue;
import adalid.core.interfaces.Artifact;
import adalid.core.interfaces.Segment;
import adalid.core.interfaces.State;
import adalid.core.properties.BooleanProperty;
import adalid.core.properties.IntegerProperty;
import adalid.core.properties.LongProperty;
import adalid.core.properties.StringProperty;
import adalid.core.properties.TimestampProperty;
import java.lang.reflect.Field;
import meta.entidad.comun.configuracion.basica.Funcion;
import meta.entidad.comun.control.acceso.Usuario;
import meta.proyecto.base.ProyectoBase;

/**
 * @author Jorge Campins
 */
@EntityClass(independent = Kleenean.TRUE, resourceType = ResourceType.OPERATION, resourceGender = ResourceGender.FEMININE)
@EntitySelectOperation(enabled = Kleenean.TRUE, sortOption = SortOption.ASC)
@EntityInsertOperation(enabled = Kleenean.FALSE)
@EntityUpdateOperation(enabled = Kleenean.FALSE)
@EntityDeleteOperation(enabled = Kleenean.FALSE)
@EntityTableView(enabled = Kleenean.TRUE)
@EntityDetailView(enabled = Kleenean.TRUE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
@EntityWarnings(enabled = Kleenean.FALSE)
public class TareaUsuario extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="code templates">
    //
    // Available entity class annotation code templates:
    //
    // $abstract	Code template for an abstract class annotation
    // $allocover	Code template for a single allocation override annotation
    // $allocovers	Code template for a set of allocation override annotations
    // $console         Code template for an entity console view annotation
    // $delete          Code template for an entity delete operation annotation
    // $detail          Code template for an entity detail view annotation
    // $disval          Code template for a discriminator value annotation
    // $entity          Code template for an entity class annotation
    // $export          Code template for an entity export operation annotation
    // $inhmap          Code template for an entity inheritance mapping annotation
    // $insert          Code template for an entity insert operation annotation
    // $report          Code template for an entity report operation annotation
    // $search          Code template for an entity reference search annotation
    // $select          Code template for an entity select operation annotation
    // $table           Code template for an entity table view annotation
    // $tree            Code template for an entity tree view annotation
    // $triggers	Code template for an entity triggers annotation
    // $update          Code template for an entity update operation annotation
    //
    // Available property field code templates:
    //
    // $bigdecpro	Code template for a big decimal property field
    // $bigintpro	Code template for a big integer property field
    // $boolpro         Code template for a boolean property field
    // $bytepro         Code template for a byte property field
    // $charbizkey	Code template for a character business key property field
    // $charkey         Code template for a character key property field
    // $charpro         Code template for a character property field
    // $code            Code template for a business key property field
    // $datepro         Code template for a date property field
    // $description	Code template for a description property field
    // $doublepro	Code template for a double property field
    // $entpro          Code template for an entity reference property field
    // $filerefpro	Code template for a file reference property field
    // $floatpro	Code template for a float property field
    // $id              Code template for a long primary key property field
    // $inactive	Code template for an inactive indicator property field
    // $intpro          Code template for a integer property field
    // $longpro         Code template for a long property field
    // $many            Code template for a many-to-one entity reference property field
    // $name            Code template for a name property field
    // $number          Code template for an integer primary key property field
    // $numbizkey	Code template for a numeric business key property field
    // $numkey          Code template for a numeric key property field
    // $one             Code template for a one-to-one entity reference property field
    // $owner           Code template for an owner entity reference property field
    // $parent          Code template for a parent entity reference property field
    // $segment         Code template for a segment entity reference property field
    // $shortpro	Code template for a short property field
    // $stringpro	Code template for a string property field
    // $timepro         Code template for a time property field
    // $stamppro        Code template for a timestamp property field
    // $url             Code template for an URL property field
    // $version         Code template for a version property field
    //
    // Available property annotation code templates:
    //
    // $alloc           Code template for an allocation annotation
    // $base            Code template for a base field annotation
    // $bigdec          Code template for a big decimal field annotation
    // $casting         Code template for a casting field annotation
    // $column          Code template for a column field annotation
    // $discol          Code template for a discriminator column annotation
    // $extension	Code template for an extension annotation
    // $fileref         Code template for a file reference annotation
    // $property	Code template for a property field annotation
    // $string          Code template for a string field annotation
    // $time            Code template for a time field annotation
    // $stamp           Code template for a timestamp field annotation
    // $unique          Code template for a unique key annotation
    //
    // Available entity key field code templates:
    //
    // $key             Code template for a key field
    //
    // Available entity tab field code templates:
    //
    // $tab             Code template for a tab field
    //
    // Available entity instance field code templates:
    //
    // $instance	Code template for a instance field
    //
    // Available entity trigger field code templates:
    //
    // $trigger         Code template for a trigger field
    //
    // Available entity expression field code templates:
    //
    // $checkx          Code template for a check expression field
    // $segmentx	Code template for a segment expression field
    // $statex          Code template for a state expression field
    //
    // Available operation class code templates:
    //
    // $exportop        Code template for an export operation
    // $operation       Code template for an operation class annotation
    // $procedure       Code template for a procedure operation
    // $process         Code template for a process operation
    // $reportop        Code template for a report operation
    //
    // Available parameter field code templates:
    //
    // $bigdecpar	Code template for a big decimal parameter field
    // $bigintpar	Code template for a big integer parameter field
    // $boolpar         Code template for a boolean parameter field
    // $bytepar         Code template for a byte parameter field
    // $charpar         Code template for a character parameter field
    // $datepar         Code template for a date parameter field
    // $doublepar	Code template for a double parameter field
    // $entpar          Code template for an entity reference parameter field
    // $filerefpar	Code template for a file reference parameter field
    // $floatpar	Code template for a float parameter field
    // $insrefpar	Code template for an instance reference parameter field
    // $intpar          Code template for a integer parameter field
    // $longpar         Code template for a long parameter field
    // $shortpar	Code template for a short parameter field
    // $stringpar	Code template for a string parameter field
    // $timepar         Code template for a time parameter field
    // $stamppar        Code template for a timestamp parameter field
    //
    // Available parameter annotation code templates:
    //
    // $alloc           Code template for an allocation annotation
    // $bigdec          Code template for a big decimal field annotation
    // $fileref         Code template for a file reference annotation
    // $insref          Code template for an instance reference parameter annotation
    // $parameter	Code template for a parameter field annotation
    // $string          Code template for a string field annotation
    // $time            Code template for a time field annotation
    // $stamp           Code template for a timestamp field annotation
    //
    // Available operation expression field code templates:
    //
    // $preconx         Code template for a pre-condition expression field
    // $posconx         Code template for a post-condition expression field
    //
    // Available operation transition field code templates:
    //
    // $transition	Code template for a transition field
    //
    // </editor-fold>
/**/
    // <editor-fold defaultstate="collapsed" desc="class constructors">
    @Deprecated()
    private TareaUsuario() {
        this(null, null);
    }

    public TareaUsuario(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public LongProperty id;

    @VersionProperty
    public LongProperty version;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE, search = Kleenean.TRUE)
    public LongProperty tarea;

    @OwnerProperty
    @SegmentProperty
    @Allocation(maxDepth = 2, maxRound = 1)
    @ColumnField(nullable = Kleenean.FALSE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE, search = Kleenean.TRUE)
    public Usuario destinatario;

    @Allocation(maxDepth = 1, maxRound = 0)
    @ColumnField(nullable = Kleenean.FALSE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE, search = Kleenean.TRUE)
    public Funcion funcion;

    @PropertyField(hidden = Kleenean.TRUE)
    public LongProperty idClaseRecursoValor;

    @Allocation(maxDepth = 1, maxRound = 0)
    @ColumnField(nullable = Kleenean.TRUE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE, search = Kleenean.FALSE, heading = Kleenean.FALSE)
    public RecursoValor recursoValor;

    @PropertyField(hidden = Kleenean.TRUE)
    public LongProperty idRecursoValor;

    @PropertyField(table = Kleenean.FALSE, detail = Kleenean.FALSE, search = Kleenean.TRUE)
    public StringProperty codigoRecursoValor;

    @PropertyField(table = Kleenean.FALSE, detail = Kleenean.FALSE, search = Kleenean.TRUE)
    public StringProperty nombreRecursoValor;

    @Allocation(maxDepth = 1, maxRound = 0)
    @ColumnField(nullable = Kleenean.TRUE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    public Usuario responsable;

    @Allocation(maxDepth = 1, maxRound = 0)
    @ColumnField(nullable = Kleenean.TRUE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    public Usuario supervisor;

    @Allocation(maxDepth = 1, maxRound = 0)
    @ColumnField(nullable = Kleenean.TRUE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    public Usuario finalizador;

    @Allocation(maxDepth = 1, maxRound = 0)
    @ColumnField(nullable = Kleenean.TRUE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(hidden = Kleenean.TRUE)
    public Usuario supervisorSuperior;

    @Allocation(maxDepth = 1, maxRound = 0)
    @ColumnField(nullable = Kleenean.FALSE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE, search = Kleenean.TRUE)
    public CondicionTarea condicion;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE, search = Kleenean.FALSE)
    public TimestampProperty fechaHoraCondicion;

    @ColumnField(nullable = Kleenean.TRUE)
    public TimestampProperty fechaHoraAsignacion;

    @ColumnField(nullable = Kleenean.TRUE)
    public TimestampProperty fechaHoraAbandono;

    @ColumnField(nullable = Kleenean.FALSE)
    public TimestampProperty fechaHoraRegistro;

    @ColumnField(nullable = Kleenean.TRUE)
    @PropertyField(hidden = Kleenean.TRUE)
    public TimestampProperty fechaHoraLimite;

    @ColumnField(nullable = Kleenean.TRUE)
    @PropertyField(hidden = Kleenean.TRUE)
    public IntegerProperty prioridad;

    /**
     * notificar a destinatarios de nuevas tareas
     */
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(hidden = Kleenean.TRUE)
    public BooleanProperty notificarDestinatario;

    /**
     * fecha y hora de la próxima notificación a destinatarios
     */
    @ColumnField(nullable = Kleenean.TRUE)
    @PropertyField(hidden = Kleenean.TRUE)
    public TimestampProperty proximaNotaDestinatario;

    /**
     * fecha y hora de la última notificación a destinatarios
     */
    @ColumnField(nullable = Kleenean.TRUE)
    @PropertyField(hidden = Kleenean.TRUE)
    public TimestampProperty ultimaNotaDestinatario;

    /**
     * notificar a supervisores de nuevas tareas para sus supervisados
     */
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(hidden = Kleenean.TRUE)
    public BooleanProperty notificarSupervisor;

    /**
     * fecha y hora de la próxima notificación a supervisores
     */
    @ColumnField(nullable = Kleenean.TRUE)
    @PropertyField(hidden = Kleenean.TRUE)
    public TimestampProperty proximaNotaSupervisor;

    /**
     * fecha y hora de la última notificación a supervisores
     */
    @ColumnField(nullable = Kleenean.TRUE)
    @PropertyField(hidden = Kleenean.TRUE)
    public TimestampProperty ultimaNotaSupervisor;

    /**
     * advertir a supervisores de tareas no asumidas por sus supervisados
     */
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(hidden = Kleenean.TRUE)
    public BooleanProperty advertirAsignar;

    /**
     * fecha y hora de la próxima advertencia de tarea no asignada
     */
    @ColumnField(nullable = Kleenean.TRUE)
    @PropertyField(hidden = Kleenean.TRUE)
    public TimestampProperty proximoAdvertirAsignar;

    /**
     * fecha y hora de la última advertencia de tarea no asignada
     */
    @ColumnField(nullable = Kleenean.TRUE)
    @PropertyField(hidden = Kleenean.TRUE)
    public TimestampProperty ultimoAdvertirAsignar;

    /**
     * advertir a supervisores de tareas no finalizadas por sus supervisados
     */
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(hidden = Kleenean.TRUE)
    public BooleanProperty advertirFinalizar;

    /**
     * fecha y hora de la próxima advertencia de tarea no finalizada
     */
    @ColumnField(nullable = Kleenean.TRUE)
    @PropertyField(hidden = Kleenean.TRUE)
    public TimestampProperty proximoAdvertirFinalizar;

    /**
     * fecha y hora de la última advertencia de tarea no finalizada
     */
    @ColumnField(nullable = Kleenean.TRUE)
    @PropertyField(hidden = Kleenean.TRUE)
    public TimestampProperty ultimoAdvertirFinalizar;

    /**
     * escalar a superiores de tareas no asignadas por sus supervisados
     */
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(hidden = Kleenean.TRUE)
    public BooleanProperty escalarAsignar;

    /**
     * fecha y hora del próximo escalamiento de tarea no asignada
     */
    @ColumnField(nullable = Kleenean.TRUE)
    @PropertyField(hidden = Kleenean.TRUE)
    public TimestampProperty proximoEscalarAsignar;

    /**
     * fecha y hora del último escalamiento de tarea no asignada
     */
    @ColumnField(nullable = Kleenean.TRUE)
    @PropertyField(hidden = Kleenean.TRUE)
    public TimestampProperty ultimoEscalarAsignar;

    /**
     * escalar a superiores de tareas no finalizadas por sus supervisados
     */
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(hidden = Kleenean.TRUE)
    public BooleanProperty escalarFinalizar;

    /**
     * fecha y hora del próximo escalamiento de tarea no finalizada
     */
    @ColumnField(nullable = Kleenean.TRUE)
    @PropertyField(hidden = Kleenean.TRUE)
    public TimestampProperty proximoEscalarFinalizar;

    /**
     * fecha y hora del último escalamiento de tarea no finalizada
     */
    @ColumnField(nullable = Kleenean.TRUE)
    @PropertyField(hidden = Kleenean.TRUE)
    public TimestampProperty ultimoEscalarFinalizar;

    @PropertyField(hidden = Kleenean.TRUE)
    @StringField(maxLength = 100)
    public StringProperty paginaFuncion;

    @PropertyField(hidden = Kleenean.TRUE)
    @StringField(maxLength = 100)
    public StringProperty paginaRecurso;

    protected Key key1;

    protected Key key2;

    protected Tab descripcion, participantes, cronologia;

    private Segment propiedadUsuarioActual, responsabilidadUsuarioActual;

    protected State disponible, asignada, asumible, abandonable, pendiente, ejecutada, cancelada, finalizada;

    protected Transition asignacion, quite, asuncion, abandono, ejecucion, cancelacion;

    protected Asumir asumir;

    protected Abandonar abandonar;

    protected Asignar asignar;

    protected Quitar quitar;

    protected Cancelar cancelar;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        setDefaultLabel("tarea");
        setDefaultCollectionLabel("Notificaciones de Tareas Pendientes");
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        recursoValor.setDefaultLabel("recurso");
        recursoValor.setDefaultTooltip("recurso");
        recursoValor.codigo.setDefaultLabel("recurso");
        recursoValor.codigo.setDefaultTooltip("recurso");
        recursoValor.nombre.setDefaultLabel("nombre del recurso");
        recursoValor.nombre.setDefaultTooltip("nombre del recurso");
        codigoRecursoValor.setDefaultLabel("recurso");
        codigoRecursoValor.setDefaultTooltip("recurso");
        nombreRecursoValor.setDefaultLabel("nombre del recurso");
        nombreRecursoValor.setDefaultTooltip("nombre del recurso");
        destinatario.setDefaultLabel("usuario destinatario");
        destinatario.setDefaultShortLabel("destinatario");
        destinatario.setDefaultTooltip("código de usuario del destinatario de la notificación de tarea");
        destinatario.setDefaultShortDescription("destinatario de la notificación de tarea");
        responsable.setDefaultLabel("usuario responsable");
        responsable.setDefaultShortLabel("responsable");
        responsable.setDefaultTooltip("código de usuario del responsable de la ejecución de la tarea");
        responsable.setDefaultShortDescription("responsable de la ejecución de la tarea");
        supervisor.setDefaultLabel("usuario supervisor");
        supervisor.setDefaultShortLabel("supervisor");
        supervisor.setDefaultTooltip("código de usuario de la persona que asigna la responsabilidad de ejecutar la tarea");
        supervisor.setDefaultShortDescription("persona que asigna la responsabilidad de ejecutar la tarea");
        finalizador.setDefaultLabel("usuario finalizador");
        finalizador.setDefaultShortLabel("finalizador");
        finalizador.setDefaultTooltip("código de usuario de la persona que finaliza (ejecuta o cancela) la tarea");
        finalizador.setDefaultShortDescription("persona que finaliza (ejecuta o cancela) la tarea");
        condicion.setDefaultLabel("condición");
        condicion.setDefaultShortLabel("condición");
        fechaHoraCondicion.setDefaultLabel("fecha/hora condición");
        fechaHoraCondicion.setDefaultShortLabel("fecha/hora");
        fechaHoraAsignacion.setDefaultLabel("fecha/hora asignación");
        fechaHoraAsignacion.setDefaultShortLabel("asignación");
        fechaHoraAbandono.setDefaultLabel("fecha/hora abandono");
        fechaHoraAbandono.setDefaultShortLabel("abandono");
        fechaHoraRegistro.setDefaultLabel("fecha/hora registro");
        fechaHoraRegistro.setDefaultShortLabel("registro");
        fechaHoraLimite.setDefaultLabel("fecha/hora límite");
        fechaHoraLimite.setDefaultShortLabel("límite");
        condicion.setInitialValue(condicion.DISPONIBLE);
        condicion.setDefaultValue(condicion.DISPONIBLE);
        fechaHoraCondicion.setInitialValue(SpecialTemporalValue.CURRENT_TIMESTAMP);
        fechaHoraCondicion.setDefaultValue(SpecialTemporalValue.CURRENT_TIMESTAMP);
        fechaHoraRegistro.setInitialValue(SpecialTemporalValue.CURRENT_TIMESTAMP);
        fechaHoraRegistro.setDefaultValue(SpecialTemporalValue.CURRENT_TIMESTAMP);
        notificarDestinatario.setInitialValue(true);
        notificarDestinatario.setDefaultValue(true);
        proximaNotaDestinatario.setInitialValue(SpecialTemporalValue.CURRENT_TIMESTAMP);
        proximaNotaDestinatario.setDefaultValue(SpecialTemporalValue.CURRENT_TIMESTAMP);
        notificarSupervisor.setInitialValue(true);
        notificarSupervisor.setDefaultValue(true);
        proximaNotaSupervisor.setInitialValue(SpecialTemporalValue.CURRENT_TIMESTAMP);
        proximaNotaSupervisor.setDefaultValue(SpecialTemporalValue.CURRENT_TIMESTAMP);
        advertirAsignar.setInitialValue(false);
        advertirAsignar.setDefaultValue(false);
        advertirFinalizar.setInitialValue(false);
        advertirFinalizar.setDefaultValue(false);
        escalarAsignar.setInitialValue(false);
        escalarAsignar.setDefaultValue(false);
        escalarFinalizar.setInitialValue(false);
        escalarFinalizar.setDefaultValue(false);
    }

    @Override
    protected void settleKeys() {
        super.settleKeys();
        key1.newKeyField(tarea);
        key2.newKeyField(condicion, fechaHoraLimite, prioridad, tarea, destinatario);
        setOrderBy(key2);
    }

    @Override
    protected void settleTabs() {
        super.settleTabs();
        descripcion.newTabField(funcion, recursoValor, condicion, fechaHoraCondicion, fechaHoraLimite, prioridad);
        participantes.newTabField(responsable, supervisor, finalizador, condicion, fechaHoraCondicion);
        cronologia.newTabField(condicion, fechaHoraCondicion, fechaHoraAsignacion, fechaHoraAbandono, fechaHoraRegistro, fechaHoraLimite);
    }

    @Override
    protected void settleExpressions() {
        super.settleExpressions();
        /**/
        propiedadUsuarioActual = destinatario.codigoUsuario.isEqualTo(SpecialCharacterValue.CURRENT_USER_CODE);
        propiedadUsuarioActual.setDefaultErrorMessage("la notificación está dirigida a otro usuario");
        /**/
        responsabilidadUsuarioActual = responsable.isNotNull().and(responsable.codigoUsuario.isEqualTo(SpecialCharacterValue.CURRENT_USER_CODE));
        responsabilidadUsuarioActual.setDefaultErrorMessage("el usuario actual no es el responsable de la ejecución de la tarea");
        /**/
        disponible = condicion.isEqualTo(condicion.DISPONIBLE);
        disponible.setDefaultErrorMessage("la tarea no está disponible");
        /**/
        asignada = condicion.isEqualTo(condicion.ASIGNADA);
        asignada.setDefaultErrorMessage("la tarea no está asignada o ya está ejecutada o cancelada");
        /**/
        asumible = disponible.and(propiedadUsuarioActual);
        abandonable = asignada.and(responsabilidadUsuarioActual);
        /**/
        pendiente = disponible.or(asignada);
        pendiente.setDefaultErrorMessage("la tarea ya está ejecutada o cancelada");
        /**/
        ejecutada = condicion.isEqualTo(condicion.EJECUTADA);
        ejecutada.setDefaultErrorMessage("la tarea no está ejecutada");
        /**/
        cancelada = condicion.isEqualTo(condicion.CANCELADA);
        cancelada.setDefaultErrorMessage("la tarea no está cancelada");
        /**/
        finalizada = ejecutada.or(cancelada);
        finalizada.setDefaultErrorMessage("la tarea no está ejecutada ni cancelada");
    }

    @Override
    protected void settleFilters() {
        super.settleFilters();
        setSelectFilter(disponible.or(asignada.and(destinatario.isEqualTo(responsable))));
    }

    @Override
    protected void settleTransitions() {
        super.settleTransitions();
        asignacion.settle(disponible, asignada);
        quite.settle(asignada, disponible);
        asuncion.settle(asumible, abandonable);
        abandono.settle(abandonable, disponible);
        ejecucion.settle(pendiente, ejecutada);
        cancelacion.settle(pendiente, cancelada);
    }

    @Override
    protected void settleOperations() {
        super.settleOperations();
        asignar.addTransition(asignacion);
        quitar.addTransition(quite);
        asumir.addTransition(asuncion);
        abandonar.addTransition(abandono);
        cancelar.addTransition(cancelacion);
    }

    // <editor-fold defaultstate="collapsed" desc="Operations">
    @OperationClass(access = OperationAccess.RESTRICTED)
    @ProcessOperationClass(overloading = Kleenean.FALSE)
    public class Asumir extends ProcessOperation {

        @InstanceReference
        @Allocation(maxDepth = 1, maxRound = 0)
        protected TareaUsuario tarea;

    }

    @OperationClass(access = OperationAccess.RESTRICTED)
    @ProcessOperationClass(overloading = Kleenean.FALSE)
    public class Abandonar extends ProcessOperation {

        @InstanceReference
        @Allocation(maxDepth = 1, maxRound = 0)
        protected TareaUsuario tarea;

    }

    @OperationClass(access = OperationAccess.PROTECTED)
    @ProcessOperationClass(overloading = Kleenean.FALSE)
    public class Asignar extends ProcessOperation {

        @InstanceReference
        @Allocation(maxDepth = 1, maxRound = 0)
        protected TareaUsuario tarea;

    }

    @OperationClass(access = OperationAccess.PROTECTED)
    @ProcessOperationClass(overloading = Kleenean.FALSE)
    public class Quitar extends ProcessOperation {

        @InstanceReference
        @Allocation(maxDepth = 1, maxRound = 0)
        protected TareaUsuario tarea;

    }

    @OperationClass(access = OperationAccess.PROTECTED)
    @ProcessOperationClass(overloading = Kleenean.FALSE)
    public class Cancelar extends ProcessOperation {

        @InstanceReference
        @Allocation(maxDepth = 1, maxRound = 0)
        protected TareaUsuario tarea;

    }
    // </editor-fold>

}
