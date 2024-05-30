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
package meta.entidad.comun.operacion.basica;

import adalid.core.*;
import adalid.core.annotations.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import adalid.core.properties.*;
import java.lang.reflect.Field;
import meta.entidad.comun.auditoria.RastroProceso;
import meta.entidad.comun.configuracion.basica.Funcion;
import meta.entidad.comun.control.acceso.ext.Usuario;

/**
 * @author Jorge Campins
 */
@EntityClass(base = Kleenean.TRUE, independent = Kleenean.TRUE, resourceType = ResourceType.OPERATION, resourceGender = ResourceGender.FEMININE)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntityDocGen(stateDiagram = Kleenean.TRUE)
@EntitySelectOperation(enabled = Kleenean.TRUE, onload = SelectOnloadOption.EXECUTE, sortOption = SortOption.ASC)
@EntityInsertOperation(enabled = Kleenean.FALSE)
@EntityUpdateOperation(enabled = Kleenean.FALSE)
@EntityDeleteOperation(enabled = Kleenean.FALSE)
@EntityTableView(enabled = Kleenean.TRUE, responsiveMode = TableResponsiveMode.PRIORITY, readingViewWesternToolbarSnippet = "/resources/snippets/base/entity/TareaUsuario/botonOpenDistribucionTareas")
@EntityDetailView(enabled = Kleenean.TRUE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
@EntityWarnings(aboutBusinessKey = Kleenean.FALSE)
public class TareaUsuario extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public TareaUsuario(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Override
    protected void addAllocationStrings() {
        super.addAllocationStrings();
        super.addAllocationStrings("destinatario.usuarioSupervisor");
    }

    @PrimaryKey
    public LongProperty id;

    @VersionProperty
    public LongProperty version;

    @ColumnField(nullable = Kleenean.FALSE, indexed = Kleenean.TRUE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE, search = Kleenean.TRUE, heading = Kleenean.TRUE, overlay = Kleenean.TRUE)
    public LongProperty tarea;

    @OwnerProperty
    @SegmentProperty
    @ColumnField(nullable = Kleenean.FALSE)
//  20231209: remove foreign-key referring to Usuario because it might cause ARJUNA012117 and/or ARJUNA012121
//  @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE, search = Kleenean.TRUE, heading = Kleenean.TRUE, overlay = Kleenean.TRUE)
    public Usuario destinatario;

    @ColumnField(calculable = Kleenean.TRUE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(hidden = Kleenean.TRUE)
    public Usuario supervisorDestinatario;

    @ColumnField(nullable = Kleenean.FALSE)
//  20171213: remove foreign-key referring to Funcion
//  @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE, search = Kleenean.TRUE, heading = Kleenean.TRUE, overlay = Kleenean.TRUE)
    public Funcion funcion;

    @ColumnField(calculable = Kleenean.TRUE)
    @PropertyField(table = Kleenean.FALSE)
    @StringField(maxLength = 0)
    public StringProperty descripcionFuncion;

    @UniformResourceLocator(urlType = UrlType.INTERNAL, urlDisplayMode = DisplayMode.PROCESSING, urlDisplayType = UrlDisplayType.BUTTON)
    @PropertyField(responsivePriority = 6, create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.FALSE, search = Kleenean.FALSE, headertextless = Kleenean.TRUE)
    public StringProperty paginaFuncion;

    @PropertyField(hidden = Kleenean.TRUE)
    public LongProperty idClaseRecursoValor;

    @ColumnField(indexed = Kleenean.TRUE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.FALSE, report = Kleenean.FALSE, search = Kleenean.TRUE, heading = Kleenean.FALSE, overlay = Kleenean.FALSE)
    @StringField(maxLength = 100)
    public StringProperty codigoClaseRecursoValor;

    @ColumnField(indexed = Kleenean.TRUE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.FALSE, report = Kleenean.FALSE, search = Kleenean.TRUE, heading = Kleenean.FALSE, overlay = Kleenean.TRUE)
    @StringField(maxLength = 100)
    public StringProperty nombreClaseRecursoValor;

    @ColumnField(nullable = Kleenean.TRUE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(hidden = Kleenean.TRUE)
    public RecursoValor recursoValor;

    @PropertyField(hidden = Kleenean.TRUE)
    public LongProperty idRecursoValor;

    @ColumnField(indexed = Kleenean.FALSE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE, search = Kleenean.TRUE, heading = Kleenean.FALSE, overlay = Kleenean.TRUE)
    public StringProperty codigoRecursoValor;

    @ColumnField(indexed = Kleenean.FALSE)
    @PropertyField(responsivePriority = 6, create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE, search = Kleenean.TRUE, heading = Kleenean.TRUE, overlay = Kleenean.TRUE)
    public StringProperty nombreRecursoValor;

    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.FALSE)
    @StringField(maxLength = 0)
    public StringProperty descripcionRecursoValor;

    @UniformResourceLocator(urlType = UrlType.INTERNAL, urlDisplayType = UrlDisplayType.BUTTON)
    @PropertyField(responsivePriority = 6, create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.FALSE, search = Kleenean.FALSE, headertextless = Kleenean.TRUE)
    public StringProperty paginaRecurso;

    @ColumnField(nullable = Kleenean.TRUE)
//  20231209: remove foreign-key referring to Usuario because it might cause ARJUNA012117 and/or ARJUNA012121
//  @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    public Usuario responsable;

    @ColumnField(nullable = Kleenean.TRUE)
//  20231209: remove foreign-key referring to Usuario because it might cause ARJUNA012117 and/or ARJUNA012121
//  @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    public Usuario supervisor;

    @ColumnField(nullable = Kleenean.TRUE)
//  20231209: remove foreign-key referring to Usuario because it might cause ARJUNA012117 and/or ARJUNA012121
//  @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    public Usuario finalizador;

    @StateProperty(transitionUser = "usuarioCondicion", transitionDateTime = "fechaHoraCondicion")
    @ColumnField(nullable = Kleenean.FALSE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE, search = Kleenean.TRUE, heading = Kleenean.TRUE, overlay = Kleenean.TRUE, defaultCondition = DefaultCondition.IF_NULL_ON_INSERT)
    public CondicionTarea condicion;

    @ColumnField(nullable = Kleenean.TRUE)
//  do not add a foreign-key referring to RastroProceso
//  @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(table = Kleenean.FALSE, report = Kleenean.FALSE)
//  @QueryMapping(mapKeyProperties = Kleenean.FALSE)
    public RastroProceso rastroProceso;

    @UserProperty
    @ColumnField(nullable = Kleenean.TRUE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    public Usuario usuarioCondicion;

    @ColumnField(nullable = Kleenean.FALSE, indexed = Kleenean.TRUE)
    @PropertyField(responsivePriority = 6, table = Kleenean.TRUE, report = Kleenean.TRUE, search = Kleenean.TRUE, heading = Kleenean.FALSE, overlay = Kleenean.TRUE, defaultCondition = DefaultCondition.IF_NULL_ON_INSERT)
    public TimestampProperty fechaHoraCondicion;

    @ColumnField(calculable = Kleenean.TRUE)
    @PropertyField(hidden = Kleenean.TRUE)
    public TimestampProperty fechaHoraDisponible;

    @ColumnField(calculable = Kleenean.TRUE)
    @PropertyField(hidden = Kleenean.TRUE)
    public TimestampProperty fechaHoraAtribucion;

    @ColumnField(nullable = Kleenean.TRUE)
    public TimestampProperty fechaHoraAsignacion;

    @ColumnField(nullable = Kleenean.TRUE)
    public TimestampProperty fechaHoraAsuncion;

    @ColumnField(nullable = Kleenean.TRUE)
    public TimestampProperty fechaHoraRelevacion;

    @ColumnField(nullable = Kleenean.TRUE)
    public TimestampProperty fechaHoraAbandono;

    @ColumnField(nullable = Kleenean.TRUE)
    public TimestampProperty fechaHoraCancelacion;

    @ColumnField(nullable = Kleenean.TRUE)
    public TimestampProperty fechaHoraFinalizacion;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(defaultCondition = DefaultCondition.IF_NULL_ON_INSERT)
    public TimestampProperty fechaHoraRegistro;

    @ColumnField(nullable = Kleenean.TRUE)
//  @PropertyField(hidden = Kleenean.TRUE)
    public TimestampProperty fechaHoraLimite;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(hidden = Kleenean.TRUE)
    public IntegerProperty condicionAsignacion;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(hidden = Kleenean.TRUE)
    public IntegerProperty condicionFinalizacion;

    @ColumnField(nullable = Kleenean.TRUE)
//  @PropertyField(hidden = Kleenean.TRUE)
    public IntegerProperty prioridad;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of TareaUsuario's attributes">
        setLocalizedLabel(ENGLISH, "task");
        setLocalizedLabel(SPANISH, "tarea");
        setLocalizedCollectionLabel(ENGLISH, "Tasks");
        setLocalizedCollectionLabel(SPANISH, "Tareas");
        /**/
        setLocalizedDescription(ENGLISH, "Each instance of " + b("Tasks") + " represents a "
            + "task notification."
            + "");
        setLocalizedDescription(SPANISH, "Cada instancia de " + b("Tareas") + " representa una "
            + "notificación de tarea."
            + "");
        /**/
        setLocalizedShortDescription(ENGLISH, "task notification");
        setLocalizedShortDescription(SPANISH, "notificación de tarea");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        /**/
        supervisorDestinatario.setCalculableValueEntityReference(destinatario.usuarioSupervisor);
        /**/
        descripcionFuncion.setCalculableValueExpression(funcion.descripcionFuncion);
        /**/
        condicion.setInitialValue(condicion.DISPONIBLE);
        condicion.setDefaultValue(condicion.DISPONIBLE);
        /**/
        fechaHoraCondicion.setInitialValue(SpecialTemporalValue.CURRENT_TIMESTAMP);
        fechaHoraCondicion.setDefaultValue(SpecialTemporalValue.CURRENT_TIMESTAMP);
        /**/
        fechaHoraRegistro.setInitialValue(SpecialTemporalValue.CURRENT_TIMESTAMP);
        fechaHoraRegistro.setDefaultValue(SpecialTemporalValue.CURRENT_TIMESTAMP);
        /**/
        fechaHoraDisponible.setCalculableValueExpression(max(fechaHoraRelevacion, fechaHoraAbandono, fechaHoraRegistro));
        fechaHoraAtribucion.setCalculableValueExpression(condicion.isNotEqualTo(condicion.DISPONIBLE).then(max(fechaHoraAsignacion, fechaHoraAsuncion)));
        /**/
        setGraphicImageExpressions();
        /**/
        condicionAsignacion.setInitialValue(0);
        condicionAsignacion.setDefaultValue(0);
        /**/
        condicionFinalizacion.setInitialValue(0);
        condicionFinalizacion.setDefaultValue(0);
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of TareaUsuario's properties">
        /**/
        tarea.setLocalizedLabel(ENGLISH, "task");
        tarea.setLocalizedLabel(SPANISH, "tarea");
        /**/
        destinatario.setLocalizedLabel(ENGLISH, "recipient user");
        destinatario.setLocalizedLabel(SPANISH, "usuario destinatario");
        destinatario.setLocalizedShortLabel(ENGLISH, "recipient");
        destinatario.setLocalizedShortLabel(SPANISH, "destinatario");
        destinatario.setLocalizedShortDescription(ENGLISH, "task notification recipient");
        destinatario.setLocalizedShortDescription(SPANISH, "destinatario de la notificación de tarea");
        destinatario.setLocalizedTooltip(ENGLISH, "user code of the task notification recipient");
        destinatario.setLocalizedTooltip(SPANISH, "código de usuario del destinatario de la notificación de tarea");
        /**/
        destinatario.codigoUsuario.setLocalizedShortLabel(ENGLISH, "recipient code");
        destinatario.codigoUsuario.setLocalizedShortLabel(SPANISH, "destinatario");
        /**/
        supervisorDestinatario.setLocalizedLabel(ENGLISH, "recipient's supervisor");
        supervisorDestinatario.setLocalizedLabel(SPANISH, "supervisor del destinatario");
        /**/
        funcion.setLocalizedLabel(ENGLISH, "function");
        funcion.setLocalizedLabel(SPANISH, "función");
        /**/
        funcion.codigoFuncion.setLocalizedShortLabel(ENGLISH, "function code");
        funcion.codigoFuncion.setLocalizedShortLabel(SPANISH, "función");
        /**/
        descripcionFuncion.setLocalizedLabel(ENGLISH, "function description");
        descripcionFuncion.setLocalizedLabel(SPANISH, "descripción de la función");
        /**/
        paginaFuncion.setLocalizedLabel(ENGLISH, "function page");
        paginaFuncion.setLocalizedLabel(SPANISH, "página de la función");
        paginaFuncion.setLocalizedTooltip(ENGLISH, "open the function's processing page");
        paginaFuncion.setLocalizedTooltip(SPANISH, "abrir la página de procesamiento de la función");
        /**/
        idClaseRecursoValor.setLocalizedLabel(ENGLISH, "resource class");
        idClaseRecursoValor.setLocalizedLabel(SPANISH, "clase de recurso");
        /**/
        codigoClaseRecursoValor.setLocalizedLabel(ENGLISH, "resource class");
        codigoClaseRecursoValor.setLocalizedLabel(SPANISH, "clase de recurso");
        /**/
        nombreClaseRecursoValor.setLocalizedLabel(ENGLISH, "resource class name");
        nombreClaseRecursoValor.setLocalizedLabel(SPANISH, "nombre de la clase de recurso");
        /**/
        recursoValor.setLocalizedLabel(ENGLISH, "resource");
        recursoValor.setLocalizedLabel(SPANISH, "recurso");
        /**/
        idRecursoValor.setLocalizedLabel(ENGLISH, "resource value");
        idRecursoValor.setLocalizedLabel(SPANISH, "recurso valor");
        /**/
        codigoRecursoValor.setLocalizedLabel(ENGLISH, "resource");
        codigoRecursoValor.setLocalizedLabel(SPANISH, "recurso");
        /**/
        nombreRecursoValor.setLocalizedLabel(ENGLISH, "resource name");
        nombreRecursoValor.setLocalizedLabel(SPANISH, "nombre del recurso");
        /**/
        descripcionRecursoValor.setLocalizedLabel(ENGLISH, "resource description");
        descripcionRecursoValor.setLocalizedLabel(SPANISH, "descripción del recurso");
        /**/
        paginaRecurso.setLocalizedLabel(ENGLISH, "resource page");
        paginaRecurso.setLocalizedLabel(SPANISH, "página del recurso");
        paginaRecurso.setLocalizedTooltip(ENGLISH, "open the resource's registration page");
        paginaRecurso.setLocalizedTooltip(SPANISH, "abrir la página de registro del recurso");
        /*
        iniciador.setLocalizedLabel(ENGLISH, "initiating user");
        iniciador.setLocalizedLabel(SPANISH, "usuario iniciador");
        iniciador.setLocalizedShortLabel(ENGLISH, "initiator");
        iniciador.setLocalizedShortLabel(SPANISH, "iniciador");
        iniciador.setLocalizedShortDescription(ENGLISH, "person whose actions lead to the generation of the task");
        iniciador.setLocalizedShortDescription(SPANISH, "persona cuyas acciones derivan en la generación de la tarea");
        iniciador.setLocalizedTooltip(ENGLISH, "user code of the person person whose actions lead to the generation of the task");
        iniciador.setLocalizedTooltip(SPANISH, "código de usuario de la persona cuyas acciones derivan en la generación de la tarea");
        /**/
        responsable.setLocalizedLabel(ENGLISH, "responsible user");
        responsable.setLocalizedLabel(SPANISH, "usuario responsable");
        responsable.setLocalizedShortLabel(ENGLISH, "responsible");
        responsable.setLocalizedShortLabel(SPANISH, "responsable");
        responsable.setLocalizedShortDescription(ENGLISH, "responsible for carrying out the task");
        responsable.setLocalizedShortDescription(SPANISH, "responsable de la ejecución de la tarea");
        responsable.setLocalizedTooltip(ENGLISH, "user code of the responsible for carrying out the task");
        responsable.setLocalizedTooltip(SPANISH, "código de usuario del responsable de la ejecución de la tarea");
        /**/
        supervisor.setLocalizedLabel(ENGLISH, "supervising user");
        supervisor.setLocalizedLabel(SPANISH, "usuario supervisor");
        supervisor.setLocalizedShortLabel(ENGLISH, "supervisor");
        supervisor.setLocalizedShortLabel(SPANISH, "supervisor");
        supervisor.setLocalizedShortDescription(ENGLISH, "person who assigns responsibility to carry out the task");
        supervisor.setLocalizedShortDescription(SPANISH, "persona que asigna la responsabilidad de ejecutar la tarea");
        supervisor.setLocalizedTooltip(ENGLISH, "user code of the person who assigns responsibility to carry out the task");
        supervisor.setLocalizedTooltip(SPANISH, "código de usuario de la persona que asigna la responsabilidad de ejecutar la tarea");
        /**/
        finalizador.setLocalizedLabel(ENGLISH, "executing user");
        finalizador.setLocalizedLabel(SPANISH, "usuario finalizador");
        finalizador.setLocalizedShortLabel(ENGLISH, "executor");
        finalizador.setLocalizedShortLabel(SPANISH, "finalizador");
        finalizador.setLocalizedShortDescription(ENGLISH, "person who completes (executes or cancels) the task");
        finalizador.setLocalizedShortDescription(SPANISH, "persona que finaliza (ejecuta o cancela) la tarea");
        finalizador.setLocalizedTooltip(ENGLISH, "user code of the person who completes (executes or cancels) the task");
        finalizador.setLocalizedTooltip(SPANISH, "código de usuario de la persona que finaliza (ejecuta o cancela) la tarea");
        /**/
        condicion.setLocalizedLabel(ENGLISH, "condition");
        condicion.setLocalizedLabel(SPANISH, "condición");
        /**/
        rastroProceso.setLocalizedDescription(ENGLISH, "audit trail of the execution of the business process corresponding to the task");
        rastroProceso.setLocalizedDescription(SPANISH, "rastro de auditoría de la ejecución del proceso de negocio correspondiente a la tarea");
        rastroProceso.setLocalizedLabel(ENGLISH, "process execution audit trail");
        rastroProceso.setLocalizedLabel(SPANISH, "rastro de ejecución del proceso");
        rastroProceso.setLocalizedShortLabel(ENGLISH, "process audit trail");
        rastroProceso.setLocalizedShortLabel(SPANISH, "rastro del proceso");
        /**/
        usuarioCondicion.setLocalizedLabel(ENGLISH, "transition user");
        usuarioCondicion.setLocalizedLabel(SPANISH, "usuario transición");
        usuarioCondicion.setLocalizedShortLabel(ENGLISH, "user");
        usuarioCondicion.setLocalizedShortLabel(SPANISH, "usuario");
        usuarioCondicion.setLocalizedDescription(ENGLISH, "user who executed the operation that caused the last condition change");
        usuarioCondicion.setLocalizedDescription(SPANISH, "usuario que ejecutó la operación que produjo el último cambio de condición");
        /**/
        fechaHoraCondicion.setLocalizedLabel(ENGLISH, "condition timestamp");
        fechaHoraCondicion.setLocalizedLabel(SPANISH, "fecha/hora condición");
        fechaHoraCondicion.setLocalizedShortLabel(ENGLISH, "timestamp");
        fechaHoraCondicion.setLocalizedShortLabel(SPANISH, "fecha/hora");
        fechaHoraCondicion.setLocalizedDescription(ENGLISH, "timestamp of the last condition change");
        fechaHoraCondicion.setLocalizedDescription(SPANISH, "fecha y hora del último cambio de condición");
        /**/
        fechaHoraDisponible.setLocalizedLabel(ENGLISH, "registration, relief or abandonment timestamp");
        fechaHoraDisponible.setLocalizedLabel(SPANISH, "fecha/hora registro, relevación o abandono");
        fechaHoraDisponible.setLocalizedShortLabel(ENGLISH, "registration, relief or abandonment");
        fechaHoraDisponible.setLocalizedShortLabel(SPANISH, "registro, relevación o abandono");
        /**/
        fechaHoraAtribucion.setLocalizedLabel(ENGLISH, "assignment or assumption timestamp");
        fechaHoraAtribucion.setLocalizedLabel(SPANISH, "fecha/hora asignación o asunción");
        fechaHoraAtribucion.setLocalizedShortLabel(ENGLISH, "assignment or assumption");
        fechaHoraAtribucion.setLocalizedShortLabel(SPANISH, "asignación o asunción");
        /**/
        fechaHoraAsignacion.setLocalizedLabel(ENGLISH, "assignment timestamp");
        fechaHoraAsignacion.setLocalizedLabel(SPANISH, "fecha/hora asignación");
        fechaHoraAsignacion.setLocalizedShortLabel(ENGLISH, "assignment");
        fechaHoraAsignacion.setLocalizedShortLabel(SPANISH, "asignación");
        /**/
        fechaHoraAsuncion.setLocalizedLabel(ENGLISH, "assumption timestamp");
        fechaHoraAsuncion.setLocalizedLabel(SPANISH, "fecha/hora asunción");
        fechaHoraAsuncion.setLocalizedShortLabel(ENGLISH, "assumption");
        fechaHoraAsuncion.setLocalizedShortLabel(SPANISH, "asunción");
        /**/
        fechaHoraRelevacion.setLocalizedLabel(ENGLISH, "relief timestamp");
        fechaHoraRelevacion.setLocalizedLabel(SPANISH, "fecha/hora relevación");
        fechaHoraRelevacion.setLocalizedShortLabel(ENGLISH, "relief");
        fechaHoraRelevacion.setLocalizedShortLabel(SPANISH, "relevación");
        /**/
        fechaHoraAbandono.setLocalizedLabel(ENGLISH, "abandonment timestamp");
        fechaHoraAbandono.setLocalizedLabel(SPANISH, "fecha/hora abandono");
        fechaHoraAbandono.setLocalizedShortLabel(ENGLISH, "abandonment");
        fechaHoraAbandono.setLocalizedShortLabel(SPANISH, "abandono");
        /**/
        fechaHoraCancelacion.setLocalizedLabel(ENGLISH, "cancellation timestamp");
        fechaHoraCancelacion.setLocalizedLabel(SPANISH, "fecha/hora cancelación");
        fechaHoraCancelacion.setLocalizedShortLabel(ENGLISH, "cancellation");
        fechaHoraCancelacion.setLocalizedShortLabel(SPANISH, "cancelación");
        /**/
        fechaHoraFinalizacion.setLocalizedLabel(ENGLISH, "completion timestamp");
        fechaHoraFinalizacion.setLocalizedLabel(SPANISH, "fecha/hora finalización");
        fechaHoraFinalizacion.setLocalizedShortLabel(ENGLISH, "completion");
        fechaHoraFinalizacion.setLocalizedShortLabel(SPANISH, "finalización");
        /**/
        fechaHoraRegistro.setLocalizedLabel(ENGLISH, "registration timestamp");
        fechaHoraRegistro.setLocalizedLabel(SPANISH, "fecha/hora registro");
        fechaHoraRegistro.setLocalizedShortLabel(ENGLISH, "registration");
        fechaHoraRegistro.setLocalizedShortLabel(SPANISH, "registro");
        /**/
        fechaHoraLimite.setLocalizedLabel(ENGLISH, "deadline");
        fechaHoraLimite.setLocalizedLabel(SPANISH, "fecha/hora límite");
        fechaHoraLimite.setLocalizedShortLabel(ENGLISH, "deadline");
        fechaHoraLimite.setLocalizedShortLabel(SPANISH, "fecha/hora límite");
        /**/
        condicionAsignacion.setLocalizedLabel(ENGLISH, "assignment status");
        condicionAsignacion.setLocalizedLabel(SPANISH, "condición asignación");
        /**/
        condicionFinalizacion.setLocalizedLabel(ENGLISH, "execution status");
        condicionFinalizacion.setLocalizedLabel(SPANISH, "condición finalización");
        /**/
        prioridad.setLocalizedLabel(ENGLISH, "priority");
        prioridad.setLocalizedLabel(SPANISH, "prioridad");
        /**/
        // </editor-fold>
    }

    void setGraphicImageExpressions() {
        /*
        final String NULL = fa(FA.NULL_VALUE + FA.WITH_FIXED_WIDTH + CSS.STATUS_NULL_VALUE_IMAGE);
        final String fa11 = fa(FA.GEAR + FA.WITH_SIZE_LG + FA.WITH_SPIN + CSS.STATUS_THEME_COLOR);
        final String fa12 = fa(FA.TIMES + FA.WITH_SIZE_LG + CSS.STATUS_THEME_COLOR);
        final String fa21 = fa(FA.GEAR + FA.WITH_SIZE_LG + CSS.STATUS_THEME_COLOR);
        final String fa22 = fa(FA.CHECK_CIRCLE + FA.WITH_SIZE_LG + CSS.STATUS_THEME_COLOR);
        final String fa99 = fa(FA.EXCLAMATION_CIRCLE + FA.WITH_SIZE_LG + CSS.STATUS_THEME_COLOR);
        final BooleanExpression nil = condicion.isNull();
        final BooleanExpression c11 = condicion.isEqualTo(condicion.ASIGNADA);
        final BooleanExpression c12 = condicion.isEqualTo(condicion.CANCELADA);
        final BooleanExpression c21 = condicion.isEqualTo(condicion.DISPONIBLE);
        final BooleanExpression c22 = condicion.isEqualTo(condicion.EJECUTADA);
        final CharacterExpression expression = nil.then(NULL).
            otherwise(c11.then(fa11).
                otherwise(c12.then(fa12).
                    otherwise(c21.then(fa21).
                        otherwise(c22.then(fa22).
                            otherwise(fa99)))));
        /++/
        condicion.setGraphicImageFontAwesomeClassNameExpression(expression);
        /**/
    }

    @Override
    protected void settleLinks() {
        super.settleLinks();
    }

    protected Key ix_tarea_usuario_0001, ix_tarea_usuario_0002, ix_tarea_usuario_0003;

    @Override
    protected void settleKeys() {
        super.settleKeys();
        /**/
        ix_tarea_usuario_0001.setUnique(false);
        ix_tarea_usuario_0001.newKeyField(tarea);
        /**/
        ix_tarea_usuario_0002.setUnique(false);
        ix_tarea_usuario_0002.newKeyField(condicion, fechaHoraLimite, tarea, destinatario);
        /**/
        ix_tarea_usuario_0003.setUnique(false);
        ix_tarea_usuario_0003.newKeyField(idClaseRecursoValor, recursoValor);
        /**/
        setOrderBy(ix_tarea_usuario_0002);
        /**/
    }

    protected Tab descripcion, participantes, cronologia;

    @Override
    protected void settleTabs() {
        super.settleTabs();
        /**/
        descripcion.newTabField(funcion, descripcionFuncion, paginaFuncion, codigoClaseRecursoValor, nombreClaseRecursoValor,
            recursoValor, codigoRecursoValor, nombreRecursoValor, descripcionRecursoValor, paginaRecurso);
        /**/
        participantes.newTabField(responsable, supervisor, finalizador);
        /**/
        cronologia.newTabField(rastroProceso, usuarioCondicion, fechaHoraCondicion, fechaHoraRegistro, fechaHoraDisponible, fechaHoraAtribucion,
            fechaHoraAsignacion, fechaHoraAsuncion, fechaHoraRelevacion, fechaHoraAbandono, fechaHoraCancelacion, fechaHoraFinalizacion,
            fechaHoraLimite, prioridad);
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of TareaUsuario's tabs">
        descripcion.setLocalizedLabel(ENGLISH, "description");
        descripcion.setLocalizedLabel(SPANISH, "descripción");
        /**/
        participantes.setLocalizedLabel(ENGLISH, "participants");
        participantes.setLocalizedLabel(SPANISH, "participantes");
        /**/
        cronologia.setLocalizedLabel(ENGLISH, "chronology");
        cronologia.setLocalizedLabel(SPANISH, "cronologia");
        /**/
        // </editor-fold>
    }

    protected State disponible, asignada, ejecutada, cancelada;

    protected Segment pendiente, asumible, abandonable, asignable, relevable, finalizada;

    protected Segment propiedadUsuarioActual, responsabilidadUsuarioActual, pendienteUsuarioActual;

    protected Segment propiedadSubordinadoUsuarioActual, responsabilidadSubordinadoUsuarioActual, pendienteSubordinadoUsuarioActual;

    @Override
    protected void settleExpressions() {
        super.settleExpressions();
        /**/
        disponible = condicion.isEqualTo(condicion.DISPONIBLE);
        asignada = condicion.isEqualTo(condicion.ASIGNADA);
        ejecutada = condicion.isEqualTo(condicion.EJECUTADA);
        cancelada = condicion.isEqualTo(condicion.CANCELADA);
        /**/
        disponible.setTransitionTimestamp(fechaHoraDisponible);
        asignada.setTransitionTimestamp(fechaHoraAtribucion);
        ejecutada.setTransitionTimestamp(fechaHoraFinalizacion);
        cancelada.setTransitionTimestamp(fechaHoraCancelacion);
        /**/
        propiedadUsuarioActual = destinatario.id.isEqualTo(CURRENT_USER_ID);
        responsabilidadUsuarioActual = propiedadUsuarioActual.and(responsable.isEqualTo(destinatario));
        /**/
        propiedadSubordinadoUsuarioActual = destinatario.usuarioSupervisor.id.isEqualTo(CURRENT_USER_ID);
        responsabilidadSubordinadoUsuarioActual = propiedadSubordinadoUsuarioActual.and(responsable.isEqualTo(destinatario));
        /**/
        pendiente = condicion.isIn(condicion.DISPONIBLE, condicion.ASIGNADA);
        asumible = disponible.and(propiedadUsuarioActual);
        abandonable = asignada.and(responsabilidadUsuarioActual);
        asignable = disponible.and(propiedadSubordinadoUsuarioActual);
        relevable = asignada.and(responsabilidadSubordinadoUsuarioActual);
        finalizada = condicion.isIn(condicion.EJECUTADA, condicion.CANCELADA);
        /**/
        pendienteUsuarioActual = pendiente.and(propiedadUsuarioActual);
        pendienteSubordinadoUsuarioActual = pendiente.and(propiedadSubordinadoUsuarioActual);
        /**/
        setHappyPath(disponible, asignada, ejecutada);
        setHappyPathDisplaySpots(HappyPathDisplaySpots.DETAIL_VIEW);
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of TareaUsuario's expressions">
        /**/
        propiedadUsuarioActual.setLocalizedCollectionLabel(ENGLISH, "all task notifications addressed to the current user");
        propiedadUsuarioActual.setLocalizedCollectionLabel(SPANISH, "todas las notificaciones de tareas dirigidas al usuario actual");
        propiedadUsuarioActual.setLocalizedCollectionShortLabel(ENGLISH, "My tasks");
        propiedadUsuarioActual.setLocalizedCollectionShortLabel(SPANISH, "Mis tareas");
        propiedadUsuarioActual.setLocalizedDescription(ENGLISH, "the notification is addressed to the current user");
        propiedadUsuarioActual.setLocalizedDescription(SPANISH, "la notificación está dirigida al usuario actual");
        propiedadUsuarioActual.setLocalizedErrorMessage(ENGLISH, "the notification is addressed to another user");
        propiedadUsuarioActual.setLocalizedErrorMessage(SPANISH, "la notificación está dirigida a otro usuario");
        /**/
        pendienteUsuarioActual.setLocalizedCollectionLabel(ENGLISH, "all pending task notifications addressed to the current user");
        pendienteUsuarioActual.setLocalizedCollectionLabel(SPANISH, "todas las notificaciones de tareas pendientes dirigidas al usuario actual");
        pendienteUsuarioActual.setLocalizedCollectionShortLabel(ENGLISH, "My pending tasks");
        pendienteUsuarioActual.setLocalizedCollectionShortLabel(SPANISH, "Mis tareas pendientes");
        pendienteUsuarioActual.setLocalizedDescription(ENGLISH, "the notification is addressed to the current user and the task has not been executed or cancelled");
        pendienteUsuarioActual.setLocalizedDescription(SPANISH, "la notificación está dirigida al usuario actual y la tarea está pendiente");
        pendienteUsuarioActual.setLocalizedErrorMessage(ENGLISH, "the notification is addressed to another user or the task has already been executed or cancelled");
        pendienteUsuarioActual.setLocalizedErrorMessage(SPANISH, "la notificación está dirigida a otro usuario o la tarea está ejecutada o cancelada");
        /**/
        responsabilidadUsuarioActual.setLocalizedCollectionLabel(ENGLISH, "all task notifications for which the current user is or was responsible");
        responsabilidadUsuarioActual.setLocalizedCollectionLabel(SPANISH, "todas las notificaciones de tareas de las que el usuario actual es o fue el responsable");
        responsabilidadUsuarioActual.setLocalizedCollectionShortLabel(ENGLISH, "My responsibilities");
        responsabilidadUsuarioActual.setLocalizedCollectionShortLabel(SPANISH, "Mis responsabilidades");
        responsabilidadUsuarioActual.setLocalizedDescription(ENGLISH, "the current user is responsible for carrying out the task");
        responsabilidadUsuarioActual.setLocalizedDescription(SPANISH, "el usuario actual es el responsable de la ejecución de la tarea");
        responsabilidadUsuarioActual.setLocalizedErrorMessage(ENGLISH, "the current user is not responsible for carrying out the task");
        responsabilidadUsuarioActual.setLocalizedErrorMessage(SPANISH, "el usuario actual no es el responsable de la ejecución de la tarea");
        /**/
        propiedadSubordinadoUsuarioActual.setLocalizedCollectionLabel(ENGLISH, "all task notifications addressed to a subordinate of the current user");
        propiedadSubordinadoUsuarioActual.setLocalizedCollectionLabel(SPANISH, "todas las notificaciones de tareas dirigidas a un subordinado del usuario actual");
        propiedadSubordinadoUsuarioActual.setLocalizedCollectionShortLabel(ENGLISH, "Tasks of my subordinates");
        propiedadSubordinadoUsuarioActual.setLocalizedCollectionShortLabel(SPANISH, "Tareas de mis subordinados");
        propiedadSubordinadoUsuarioActual.setLocalizedDescription(ENGLISH, "the notification is addressed to a subordinate of the current user");
        propiedadSubordinadoUsuarioActual.setLocalizedDescription(SPANISH, "la notificación está dirigida a un subordinado del usuario actual");
        propiedadSubordinadoUsuarioActual.setLocalizedErrorMessage(ENGLISH, "the notification is not addressed to a subordinate of the current user");
        propiedadSubordinadoUsuarioActual.setLocalizedErrorMessage(SPANISH, "la notificación no está dirigida a un subordinado del usuario actual");
        /**/
        pendienteSubordinadoUsuarioActual.setLocalizedCollectionLabel(ENGLISH, "all pending task notifications addressed to a subordinate of the current user");
        pendienteSubordinadoUsuarioActual.setLocalizedCollectionLabel(SPANISH, "todas las notificaciones de tareas pendientes dirigidas a un subordinado del usuario actual");
        pendienteSubordinadoUsuarioActual.setLocalizedCollectionShortLabel(ENGLISH, "Pending tasks of my subordinates");
        pendienteSubordinadoUsuarioActual.setLocalizedCollectionShortLabel(SPANISH, "Tareas pendientes de mis subordinados");
        pendienteSubordinadoUsuarioActual.setLocalizedDescription(ENGLISH, "the notification is addressed to a subordinate of the current user and the task has not been executed or cancelled");
        pendienteSubordinadoUsuarioActual.setLocalizedDescription(SPANISH, "la notificación está dirigida a un subordinado del usuario actual y la tarea está pendiente");
        pendienteSubordinadoUsuarioActual.setLocalizedErrorMessage(ENGLISH, "the notification is not addressed to a subordinate of the current user or the task has already been executed or cancelled");
        pendienteSubordinadoUsuarioActual.setLocalizedErrorMessage(SPANISH, "la notificación no está dirigida a un subordinado del usuario actual o la tarea está ejecutada o cancelada");
        /**/
        responsabilidadSubordinadoUsuarioActual.setLocalizedCollectionLabel(ENGLISH, "all task notifications for which a subordinate of the current user is or was responsible");
        responsabilidadSubordinadoUsuarioActual.setLocalizedCollectionLabel(SPANISH, "todas las notificaciones de tareas de las que un subordinado del usuario actual es o fue el responsable");
        responsabilidadSubordinadoUsuarioActual.setLocalizedCollectionShortLabel(ENGLISH, "Responsibilities of my subordinates");
        responsabilidadSubordinadoUsuarioActual.setLocalizedCollectionShortLabel(SPANISH, "Responsabilidades de mis subordinados");
        responsabilidadSubordinadoUsuarioActual.setLocalizedDescription(ENGLISH, "a subordinate of the current user is responsible for carrying out the task");
        responsabilidadSubordinadoUsuarioActual.setLocalizedDescription(SPANISH, "un subordinado del usuario actual es el responsable de la ejecución de la tarea");
        responsabilidadSubordinadoUsuarioActual.setLocalizedErrorMessage(ENGLISH, "a subordinate of the current user is not responsible for carrying out the task");
        responsabilidadSubordinadoUsuarioActual.setLocalizedErrorMessage(SPANISH, "un subordinado del usuario actual no es el responsable de la ejecución de la tarea");
        /**/
        disponible.setLocalizedCollectionLabel(ENGLISH, "all notifications of tasks that are available");
        disponible.setLocalizedCollectionLabel(SPANISH, "todas las notificaciones de tareas que están disponibles");
        disponible.setLocalizedCollectionShortLabel(ENGLISH, "Available tasks");
        disponible.setLocalizedCollectionShortLabel(SPANISH, "Tareas disponibles");
        disponible.setLocalizedDescription(ENGLISH, "the responsibility for carrying out the task is not assigned and therefore "
            + "the task is available to be assigned or assumed by any of the notified users");
        disponible.setLocalizedDescription(SPANISH, "la responsabilidad de realizar la tarea no está asignada y, por lo tanto, "
            + "la tarea está disponible para ser asignada o asumida por cualquiera de los usuarios notificados");
        disponible.setLocalizedErrorMessage(ENGLISH, "the task is not available");
        disponible.setLocalizedErrorMessage(SPANISH, "la tarea no está disponible");
        disponible.setLocalizedLabel(ENGLISH, "available");
        disponible.setLocalizedLabel(SPANISH, "disponible");
        /**/
        asignada.setLocalizedCollectionLabel(ENGLISH, "all notifications of tasks that are assigned");
        asignada.setLocalizedCollectionLabel(SPANISH, "todas las notificaciones de tareas que están asignadas");
        asignada.setLocalizedCollectionShortLabel(ENGLISH, "Assigned tasks");
        asignada.setLocalizedCollectionShortLabel(SPANISH, "Tareas asignadas");
        asignada.setLocalizedDescription(ENGLISH, "the responsibility for carrying out the task is assigned to one of the notified users");
        asignada.setLocalizedDescription(SPANISH, "la responsabilidad de llevar a cabo la tarea está asignada a uno de los usuarios notificados");
        asignada.setLocalizedErrorMessage(ENGLISH, "the task is not assigned or it was executed or cancelled");
        asignada.setLocalizedErrorMessage(SPANISH, "la tarea no está asignada o fue ejecutada o cancelada");
        asignada.setLocalizedLabel(ENGLISH, "assigned");
        asignada.setLocalizedLabel(SPANISH, "asignada");
        /**/
        ejecutada.setLocalizedCollectionLabel(ENGLISH, "all notifications of tasks that are executed");
        ejecutada.setLocalizedCollectionLabel(SPANISH, "todas las notificaciones de tareas que están ejecutadas");
        ejecutada.setLocalizedCollectionShortLabel(ENGLISH, "Executed tasks");
        ejecutada.setLocalizedCollectionShortLabel(SPANISH, "Tareas ejecutadas");
        ejecutada.setLocalizedDescription(ENGLISH, "the task was executed; "
            + "this state is reached regardless of the result of the execution, that is, it does not imply that it was executed without errors");
        ejecutada.setLocalizedDescription(SPANISH, "la tarea fue ejecutada; "
            + "este estado se alcanza independientemente del resultado de la ejecución, es decir, no implica que se haya ejecutado sido sin errores");
        ejecutada.setLocalizedErrorMessage(ENGLISH, "the task is not executed");
        ejecutada.setLocalizedErrorMessage(SPANISH, "la tarea no está ejecutada");
        ejecutada.setLocalizedLabel(ENGLISH, "executed");
        ejecutada.setLocalizedLabel(SPANISH, "ejecutada");
        /**/
        cancelada.setLocalizedCollectionLabel(ENGLISH, "all notifications of tasks that are cancelled");
        cancelada.setLocalizedCollectionLabel(SPANISH, "todas las notificaciones de tareas que están canceladas");
        cancelada.setLocalizedCollectionShortLabel(ENGLISH, "Cancelled tasks");
        cancelada.setLocalizedCollectionShortLabel(SPANISH, "Tareas canceladas");
        cancelada.setLocalizedDescription(ENGLISH, "the task was cancelled; this state is not reached as a consequence of an error; "
            + "a task can be canceled by an authorized user or automatically by the system, "
            + "if the resource is deleted or modified in such a way that this task should no longer be executed");
        cancelada.setLocalizedDescription(SPANISH, "la tarea fue cancelada; este estado no se alcanza como consecuencia de un error; "
            + "una tarea puede ser cancelada por un usuario autorizado o automáticamente por el sistema, "
            + "si el recurso es eliminado o modificado de tal forma que esta tarea ya no deba ser ejecutada");
        cancelada.setLocalizedErrorMessage(ENGLISH, "the task is not cancelled");
        cancelada.setLocalizedErrorMessage(SPANISH, "la tarea no está cancelada");
        cancelada.setLocalizedLabel(ENGLISH, "cancelled");
        cancelada.setLocalizedLabel(SPANISH, "cancelada");
        /**/
        pendiente.setLocalizedCollectionLabel(ENGLISH, "all notifications of tasks that are available or assigned");
        pendiente.setLocalizedCollectionLabel(SPANISH, "todas las notificaciones de tareas que están disponibles o asignadas");
        pendiente.setLocalizedCollectionShortLabel(ENGLISH, "Pending tasks");
        pendiente.setLocalizedCollectionShortLabel(SPANISH, "Tareas pendientes");
        pendiente.setLocalizedDescription(ENGLISH, "pseudo-state that includes tasks in state \"Available\" or \"Assigned\"");
        pendiente.setLocalizedDescription(SPANISH, "pseudo-estado que incluye tareas en estado \"Disponible\" o \"Asignada\"");
        pendiente.setLocalizedErrorMessage(ENGLISH, "the task was executed or cancelled");
        pendiente.setLocalizedErrorMessage(SPANISH, "la tarea fue ejecutada o cancelada");
        pendiente.setLocalizedLabel(ENGLISH, "pending");
        pendiente.setLocalizedLabel(SPANISH, "pendiente");
        /**/
        asumible.setLocalizedCollectionLabel(ENGLISH, "all notifications of tasks that are available and addressed to the current user");
        asumible.setLocalizedCollectionLabel(SPANISH, "todas las notificaciones de tareas que están disponibles y dirigidas al usuario actual");
        asumible.setLocalizedCollectionShortLabel(ENGLISH, "Assumable tasks");
        asumible.setLocalizedCollectionShortLabel(SPANISH, "Tareas asumibles");
        asumible.setLocalizedDescription(ENGLISH, "the task is available and the notification is addressed to the current user; "
            + "\"Assumable\" is a pseudo-state derived from \"Available\", which is used to determine whether the \"Assign\" and \"Assume\" operations are enabled or not");
        asumible.setLocalizedDescription(SPANISH, "la tarea está disponible y la notificación está dirigida al usuario actual; "
            + "\"Asumible\" es un pseudo-estado derivado de \"Disponible\", que se usa para determinar si las operaciones \"Asignar\" y \"Asumir\" están, o no, habilitadas");
        asumible.setLocalizedErrorMessage(ENGLISH, "the task is not available or not addressed to the current user");
        asumible.setLocalizedErrorMessage(SPANISH, "la tarea no está disponible o no está dirigida al usuario actual");
        asumible.setLocalizedLabel(ENGLISH, "assumable");
        asumible.setLocalizedLabel(SPANISH, "asumible");
        /**/
        abandonable.setLocalizedCollectionLabel(ENGLISH, "all notifications of tasks that are assigned to the current user");
        abandonable.setLocalizedCollectionLabel(SPANISH, "todas las notificaciones de tareas que están asignadas al usuario actual");
        abandonable.setLocalizedCollectionShortLabel(ENGLISH, "Abandonable tasks");
        abandonable.setLocalizedCollectionShortLabel(SPANISH, "Tareas abandonables");
        abandonable.setLocalizedDescription(ENGLISH, "the task is assigned to the current user; "
            + "\"Abandonable\" is a pseudo-state derived from \"Assigned\", which is used to determine whether the \"Relieve\" and  \"Abandon\" operations are enabled or not");
        abandonable.setLocalizedDescription(SPANISH, "la tarea está asignada al usuario actual y la notificación está dirigida al usuario actual; "
            + "\"Abandonable\" es un pseudo-estado derivado de \"Asignada\", que se usa para determinar si las operaciones \"Relevar\" y  \"Abandonar\" están, o no, habilitadas");
        abandonable.setLocalizedErrorMessage(ENGLISH, "the task is not assigned to the current user");
        abandonable.setLocalizedErrorMessage(SPANISH, "la tarea no está asignada al usuario actual");
        abandonable.setLocalizedLabel(ENGLISH, "abandonable");
        abandonable.setLocalizedLabel(SPANISH, "abandonable");
        /**/
        asignable.setLocalizedCollectionLabel(ENGLISH, "all notifications of tasks that are available but not addressed to the current user");
        asignable.setLocalizedCollectionLabel(SPANISH, "todas las notificaciones de tareas que están disponibles pero no dirigidas al usuario actual");
        asignable.setLocalizedCollectionShortLabel(ENGLISH, "Assignable tasks");
        asignable.setLocalizedCollectionShortLabel(SPANISH, "Tareas asignables");
        asignable.setLocalizedDescription(ENGLISH, "the task is available and the notification is addressed to a subordinate of the current user; "
            + "\"Assignable\" is a pseudo-state derived from \"Available\", which is used to determine whether the \"Assign\" and \"Assume\" operations are enabled or not");
        asignable.setLocalizedDescription(SPANISH, "la tarea está disponible y la notificación está dirigida a un subordinado del usuario actual; "
            + "\"Asignable\" es un pseudo-estado derivado de \"Disponible\", que se usa para determinar si las operaciones \"Asignar\" y \"Asumir\" están, o no, habilitadas");
        asignable.setLocalizedErrorMessage(ENGLISH, "the task is not available or it is addressed to the current user");
        asignable.setLocalizedErrorMessage(SPANISH, "la tarea no está disponible o está dirigida al usuario actual");
        asignable.setLocalizedLabel(ENGLISH, "assignable");
        asignable.setLocalizedLabel(SPANISH, "asignable");
        /**/
        relevable.setLocalizedCollectionLabel(ENGLISH, "all notifications of tasks that are assigned to another user");
        relevable.setLocalizedCollectionLabel(SPANISH, "todas las notificaciones de tareas que están asignadas a otro usuario");
        relevable.setLocalizedCollectionShortLabel(ENGLISH, "Relieveable tasks");
        relevable.setLocalizedCollectionShortLabel(SPANISH, "Tareas relevables");
        relevable.setLocalizedDescription(ENGLISH, "the task is assigned to a subordinate of the current user; "
            + "\"Relieveable\" is a pseudo-state derived from \"Assigned\", which is used to determine whether the \"Relieve\" and  \"Abandon\" operations are enabled or not");
        relevable.setLocalizedDescription(SPANISH, "la tarea está asignada al usuario actual y la notificación está dirigida al usuario actual; "
            + "\"Relevable\" es un pseudo-estado derivado de \"Asignada\", que se usa para determinar si las operaciones \"Relevar\" y  \"Abandonar\" están, o no, habilitadas");
        relevable.setLocalizedErrorMessage(ENGLISH, "the task is not assigned or it is assigned to the current user");
        relevable.setLocalizedErrorMessage(SPANISH, "la tarea no está asignada o está asignada al usuario actual");
        relevable.setLocalizedLabel(ENGLISH, "relieveable");
        relevable.setLocalizedLabel(SPANISH, "relevable");
        /**/
        finalizada.setLocalizedCollectionLabel(ENGLISH, "all notifications of tasks that have been executed or cancelled");
        finalizada.setLocalizedCollectionLabel(SPANISH, "todas las notificaciones de tareas que se han ejecutado o cancelado");
        finalizada.setLocalizedCollectionShortLabel(ENGLISH, "Finished tasks");
        finalizada.setLocalizedCollectionShortLabel(SPANISH, "Tareas finalizadas");
        finalizada.setLocalizedDescription(ENGLISH, "pseudo-state that includes tasks in state \"Executed\" or \"Cancelled\"");
        finalizada.setLocalizedDescription(SPANISH, "pseudo-estado que incluye tareas en estado \"Ejecutada\" o \"Cancelada\"");
        finalizada.setLocalizedErrorMessage(ENGLISH, "the task is not executed or cancelled");
        finalizada.setLocalizedErrorMessage(SPANISH, "la tarea no está ejecutada ni cancelada");
        finalizada.setLocalizedLabel(ENGLISH, "finished");
        finalizada.setLocalizedLabel(SPANISH, "finalizada");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleFilters() {
        super.settleFilters();
        /**/
        setSelectFilter(responsable.isNullOrEqualTo(destinatario));
        /**/
        addSelectSegment(pendienteUsuarioActual, true);
        addSelectSegment(pendienteSubordinadoUsuarioActual);
        addSelectSegment(responsabilidadUsuarioActual);
        addSelectSegment(responsabilidadSubordinadoUsuarioActual);
        addSelectSegment(propiedadUsuarioActual);
        addSelectSegment(propiedadSubordinadoUsuarioActual);
        addSelectSegment(pendiente, asumible, abandonable, asignable, relevable, finalizada);
        /**/
        rastroProceso.setRenderingFilter(rastroProceso.isNotNull());
        /**/
    }

    protected NotificarTareas notificarTareas;

    protected Asumir asumir;

    protected Abandonar abandonar;

    protected Asignar asignar;

    protected Relevar relevar;

    protected Ejecutar ejecutar;

    protected Cancelar cancelar;

    @OperationClass(access = OperationAccess.PRIVATE)
    @ProcessOperationClass(bpl = Kleenean.FALSE, sql = Kleenean.FALSE)
    public class NotificarTareas extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of Notificar's attributes">
            /**/
            setLocalizedLabel(ENGLISH, "notify users");
            setLocalizedLabel(SPANISH, "notificar usuarios");
            /**/
            setLocalizedDescription(ENGLISH, "send task notifications to corresponding users");
            setLocalizedDescription(SPANISH, "enviar notificaciones de tareas a los usuarios correspondientes");
            /*
            setLocalizedSuccessMessage(ENGLISH, "task notifications were sent to users");
            setLocalizedSuccessMessage(SPANISH, "se enviaron notificaciones de tareas a los usuarios");
            /**/
            // </editor-fold>
        }

    }

    @OperationClass(access = OperationAccess.RESTRICTED)
    @ProcessOperationClass
    public class Asumir extends ProcessOperation {

        @InstanceReference
        protected TareaUsuario tarea;

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of Asumir's attributes">
            /**/
            setLocalizedLabel(ENGLISH, "assume");
            setLocalizedLabel(SPANISH, "asumir");
            /**/
            setLocalizedDescription(ENGLISH, "assume the responsibility for carrying out a task");
            setLocalizedDescription(SPANISH, "asumir la responsabilidad de llevar a cabo una tarea");
            /**/
            setLocalizedSuccessMessage(ENGLISH, "the responsibility for carrying out the task was assumed");
            setLocalizedSuccessMessage(SPANISH, "se asumió la responsabilidad de llevar a cabo la tarea");
            /**/
            // </editor-fold>
        }

        @Override
        protected void settleParameters() {
            super.settleParameters();
            // <editor-fold defaultstate="collapsed" desc="localization of Asumir's parameters">
            tarea.setLocalizedLabel(ENGLISH, "task");
            tarea.setLocalizedLabel(SPANISH, "tarea");
            // </editor-fold>
        }

        protected Check check101;

        @Override
        protected void settleExpressions() {
            super.settleExpressions();
            /**/
            check101 = tarea.destinatario.id.isEqualTo(CURRENT_USER_ID);
//          check101.setCheckpoint(Checkpoint.USER_INTERFACE);
            /**/
            // <editor-fold defaultstate="collapsed" desc="localization of Asumir's expressions">
            /**/
            check101.setLocalizedDescription(ENGLISH, "to assume a task, notification must be addressed to the user");
            check101.setLocalizedDescription(SPANISH, "para asumir una tarea, la notificacion debe estar dirigida al usuario");
            check101.setLocalizedErrorMessage(ENGLISH, "notification is addressed to another user");
            check101.setLocalizedErrorMessage(SPANISH, "la notificación esta dirigida a otro usuario");
            /**/
            // </editor-fold>
        }

    }

    @OperationClass(access = OperationAccess.RESTRICTED)
    @ProcessOperationClass
    public class Abandonar extends ProcessOperation {

        @InstanceReference
        protected TareaUsuario tarea;

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of Abandonar's attributes">
            /**/
            setLocalizedLabel(ENGLISH, "abandon");
            setLocalizedLabel(SPANISH, "abandonar");
            /**/
            setLocalizedDescription(ENGLISH, "give up the responsibility for carrying out a task");
            setLocalizedDescription(SPANISH, "abandonar la responsabilidad de llevar a cabo una tarea");
            /**/
            setLocalizedSuccessMessage(ENGLISH, "the responsibility for carrying out the task was abandoned");
            setLocalizedSuccessMessage(SPANISH, "se abandonó la responsabilidad de llevar a cabo la tarea");
            /**/
            // </editor-fold>
        }

        @Override
        protected void settleParameters() {
            super.settleParameters();
            // <editor-fold defaultstate="collapsed" desc="localization of Abandonar's parameters">
            tarea.setLocalizedLabel(ENGLISH, "task");
            tarea.setLocalizedLabel(SPANISH, "tarea");
            // </editor-fold>
        }

        protected Check check101, check102;

        @Override
        protected void settleExpressions() {
            super.settleExpressions();
            /**/
            check101 = tarea.destinatario.id.isEqualTo(CURRENT_USER_ID);
//          check101.setCheckpoint(Checkpoint.USER_INTERFACE);
            /**/
            check102 = tarea.responsable.isEqualTo(tarea.destinatario);
            /**/
            // <editor-fold defaultstate="collapsed" desc="localization of Abandonar's expressions">
            /**/
            check101.setLocalizedDescription(ENGLISH, "to abandon a task, notification must be addressed to the user");
            check101.setLocalizedDescription(SPANISH, "para abandonar una tarea, la notificacion debe estar dirigida al usuario");
            check101.setLocalizedErrorMessage(ENGLISH, "notification is addressed to another user");
            check101.setLocalizedErrorMessage(SPANISH, "la notificación esta dirigida a otro usuario");
            /**/
            check102.setLocalizedDescription(ENGLISH, "to abandon a task, notification must be addressed to the user responsible for carrying it out");
            check102.setLocalizedDescription(SPANISH, "para abandonar una tarea, la notificacion debe estar dirigida al usuario responsable de ejecutarla");
            check102.setLocalizedErrorMessage(ENGLISH, "the notification is not addressed to the user responsible for carrying out the task");
            check102.setLocalizedErrorMessage(SPANISH, "la notificacion no está dirigida al usuario responsable de ejecutar la tarea");
            /**/
            // </editor-fold>
        }

    }

    @OperationClass(access = OperationAccess.PROTECTED)
    @ProcessOperationClass
    public class Asignar extends ProcessOperation {

        @InstanceReference
        protected TareaUsuario tarea;

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of Asignar's attributes">
            /**/
            setLocalizedLabel(ENGLISH, "assign");
            setLocalizedLabel(SPANISH, "asignar");
            /**/
            setLocalizedDescription(ENGLISH, "assign the responsibility for carrying out a task");
            setLocalizedDescription(SPANISH, "asignar la responsabilidad de llevar a cabo una tarea");
            /**/
            setLocalizedSuccessMessage(ENGLISH, "the responsibility for carrying out the task was assigned");
            setLocalizedSuccessMessage(SPANISH, "se asignó la responsabilidad de llevar a cabo la tarea");
            /**/
            // </editor-fold>
        }

        @Override
        protected void settleParameters() {
            super.settleParameters();
            // <editor-fold defaultstate="collapsed" desc="localization of Asignar's parameters">
            tarea.setLocalizedLabel(ENGLISH, "task");
            tarea.setLocalizedLabel(SPANISH, "tarea");
            // </editor-fold>
        }

        /**/
        protected Check check101;

        @Override
        protected void settleExpressions() {
            super.settleExpressions();
            /**/
            check101 = tarea.destinatario.id.isNotEqualTo(CURRENT_USER_ID);
//          check101.setCheckpoint(Checkpoint.USER_INTERFACE);
            /**/
            // <editor-fold defaultstate="collapsed" desc="localization of Asignar's expressions">
            /**/
            check101.setLocalizedDescription(ENGLISH, "to assign a task, notification must not be addressed to the user");
            check101.setLocalizedDescription(SPANISH, "para asignar una tarea, la notificacion no debe estar dirigida al usuario");
            check101.setLocalizedErrorMessage(ENGLISH, "notification is addressed to your user");
            check101.setLocalizedErrorMessage(SPANISH, "la notificación está dirigida a su usuario");
            /**/
            // </editor-fold>
        }

        /**/
    }

    @OperationClass(access = OperationAccess.PROTECTED)
    @ProcessOperationClass
    public class Relevar extends ProcessOperation {

        @InstanceReference
        protected TareaUsuario tarea;

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of Relevar's attributes">
            /**/
            setLocalizedLabel(ENGLISH, "relieve");
            setLocalizedLabel(SPANISH, "relevar");
            /**/
            setLocalizedDescription(ENGLISH, "relieve of the responsibility for carrying out a task");
            setLocalizedDescription(SPANISH, "relevar de la responsabilidad de llevar a cabo una tarea");
            /**/
            setLocalizedSuccessMessage(ENGLISH, "the responsibility for carrying out the task was relieved");
            setLocalizedSuccessMessage(SPANISH, "se relevó la responsabilidad de llevar a cabo la tarea");
            /**/
            // </editor-fold>
        }

        @Override
        protected void settleParameters() {
            super.settleParameters();
            // <editor-fold defaultstate="collapsed" desc="localization of Relevar's parameters">
            tarea.setLocalizedLabel(ENGLISH, "task");
            tarea.setLocalizedLabel(SPANISH, "tarea");
            // </editor-fold>
        }

        /**/
        protected Check check101, check102;

        @Override
        protected void settleExpressions() {
            super.settleExpressions();
            /**/
            check101 = tarea.destinatario.id.isNotEqualTo(CURRENT_USER_ID);
//          check101.setCheckpoint(Checkpoint.USER_INTERFACE);
            /**/
            check102 = tarea.responsable.isEqualTo(tarea.destinatario);
            /**/
            // <editor-fold defaultstate="collapsed" desc="localization of Relevar's expressions">
            /**/
            check101.setLocalizedDescription(ENGLISH, "to relieve a task, notification must not be addressed to the user");
            check101.setLocalizedDescription(SPANISH, "para relevar una tarea, la notificacion no debe estar dirigida al usuario");
            check101.setLocalizedErrorMessage(ENGLISH, "notification is addressed to your user");
            check101.setLocalizedErrorMessage(SPANISH, "la notificación está dirigida a su usuario");
            /**/
            check102.setLocalizedDescription(ENGLISH, "to relieve a task, notification must be addressed to the user responsible for carrying it out");
            check102.setLocalizedDescription(SPANISH, "para relevar una tarea, la notificacion debe estar dirigida al usuario responsable de ejecutarla");
            check102.setLocalizedErrorMessage(ENGLISH, "the notification is not addressed to the user responsible for carrying out the task");
            check102.setLocalizedErrorMessage(SPANISH, "la notificacion no está dirigida al usuario responsable de ejecutar la tarea");
            /**/
            // </editor-fold>
        }

        /**/
    }

    @OperationClass(access = OperationAccess.PRIVATE)
    @ProcessOperationClass(bpl = Kleenean.FALSE, sql = Kleenean.FALSE)
    public class Ejecutar extends ProcessOperation {

        @InstanceReference
        protected TareaUsuario tarea;

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of Ejecutar's attributes">
            /**/
            setLocalizedLabel(ENGLISH, "cancel");
            setLocalizedLabel(SPANISH, "ejecutar");
            /**/
            // </editor-fold>
        }

        @Override
        protected void settleParameters() {
            super.settleParameters();
            // <editor-fold defaultstate="collapsed" desc="localization of Ejecutar's parameters">
            tarea.setLocalizedLabel(ENGLISH, "task");
            tarea.setLocalizedLabel(SPANISH, "tarea");
            // </editor-fold>
        }

    }

    @OperationClass(access = OperationAccess.PROTECTED, confirmation = Kleenean.TRUE)
    @ProcessOperationClass
    public class Cancelar extends ProcessOperation {

        @InstanceReference
        protected TareaUsuario tarea;

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of Cancelar's attributes">
            /**/
            setLocalizedLabel(ENGLISH, "cancel");
            setLocalizedLabel(SPANISH, "cancelar");
            /**/
            setLocalizedDescription(ENGLISH, "cancel all notifications of a task");
            setLocalizedDescription(SPANISH, "cancelar todas las notificaciones de una tarea");
            /**/
            setLocalizedSuccessMessage(ENGLISH, "all task's notifications were cancelled");
            setLocalizedSuccessMessage(SPANISH, "se cancelaron todas las notificaciones de la tarea");
            /**/
            // </editor-fold>
        }

        @Override
        protected void settleParameters() {
            super.settleParameters();
            // <editor-fold defaultstate="collapsed" desc="localization of Cancelar's parameters">
            tarea.setLocalizedLabel(ENGLISH, "task");
            tarea.setLocalizedLabel(SPANISH, "tarea");
            // </editor-fold>
        }

    }

    @Override
    protected void settleOperations() {
        super.settleOperations();
//      insert.addTransition(null, disponible);
        asumir.addTransition(disponible, asignada);
        asignar.addTransition(disponible, asignada);
        abandonar.addTransition(asignada, disponible);
        relevar.addTransition(asignada, disponible);
        ejecutar.addTransition(disponible, ejecutada);
        ejecutar.addTransition(asignada, ejecutada);
        cancelar.addTransition(disponible, cancelada);
        cancelar.addTransition(asignada, cancelada);
    }

}
