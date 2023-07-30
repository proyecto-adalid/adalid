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
import meta.entidad.comun.control.acceso.Usuario;

/**
 * @author Jorge Campins
 */
@EntityClass(base = Kleenean.TRUE, independent = Kleenean.TRUE, resourceType = ResourceType.OPERATION, resourceGender = ResourceGender.FEMININE)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntityDocGen(stateDiagram = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE, onload = SelectOnloadOption.EXECUTE, sortOption = SortOption.ASC)
@EntityInsertOperation(enabled = Kleenean.FALSE)
@EntityUpdateOperation(enabled = Kleenean.FALSE)
@EntityDeleteOperation(enabled = Kleenean.FALSE)
@EntityTableView(enabled = Kleenean.TRUE)
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
        /*
        super.addAllocationStrings(
            "rastroProceso.condicionEjeFun"
        );
        /**/
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
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE, search = Kleenean.TRUE, heading = Kleenean.TRUE, overlay = Kleenean.TRUE) //, defaultCheckpoint = Checkpoint.USER_INTERFACE)
    public Usuario destinatario;

    @ColumnField(nullable = Kleenean.FALSE)
//  20171213: remove foreign-key referring to Funcion
//  @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE, search = Kleenean.TRUE, heading = Kleenean.TRUE, overlay = Kleenean.TRUE)
    @EntityReferenceDisplay(style = EntityReferenceStyle.NAME)
    public Funcion funcion;

    @ColumnField(calculable = Kleenean.TRUE)
    @PropertyField(table = Kleenean.FALSE)
    @StringField(maxLength = 0)
    public StringProperty descripcionFuncion;

    @UniformResourceLocator(urlType = UrlType.INTERNAL, urlDisplayMode = DisplayMode.PROCESSING, urlDisplayType = UrlDisplayType.BUTTON)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.FALSE, search = Kleenean.FALSE, headertextless = Kleenean.TRUE)
    public StringProperty paginaFuncion;

    @PropertyField(hidden = Kleenean.TRUE)
    public LongProperty idClaseRecursoValor;

    @ColumnField(indexed = Kleenean.TRUE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.FALSE, report = Kleenean.FALSE, search = Kleenean.TRUE, heading = Kleenean.FALSE, overlay = Kleenean.FALSE)
    @StringField(maxLength = 100)
    public StringProperty codigoClaseRecursoValor;

    @ColumnField(indexed = Kleenean.TRUE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.FALSE, report = Kleenean.FALSE, search = Kleenean.TRUE, heading = Kleenean.FALSE, overlay = Kleenean.FALSE)
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
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE, search = Kleenean.TRUE, heading = Kleenean.TRUE, overlay = Kleenean.TRUE)
    public StringProperty nombreRecursoValor;

    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.FALSE)
    @StringField(maxLength = 0)
    public StringProperty descripcionRecursoValor;

    @UniformResourceLocator(urlType = UrlType.INTERNAL, urlDisplayType = UrlDisplayType.BUTTON)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.FALSE, search = Kleenean.FALSE, headertextless = Kleenean.TRUE)
    public StringProperty paginaRecurso;

    @ColumnField(nullable = Kleenean.TRUE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    public Usuario responsable;

    @ColumnField(nullable = Kleenean.TRUE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    public Usuario supervisor;

    @ColumnField(nullable = Kleenean.TRUE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    public Usuario finalizador;

    @StateProperty
    @ColumnField(nullable = Kleenean.FALSE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE, search = Kleenean.TRUE, heading = Kleenean.TRUE, overlay = Kleenean.TRUE)
    public CondicionTarea condicion;

    /*
    @ColumnField(calculable = Kleenean.TRUE)
//  @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(table = Kleenean.FALSE, report = Kleenean.FALSE)
    public CondicionEjeFun condicionEjeFun;

    /**/
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
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE, search = Kleenean.TRUE, heading = Kleenean.TRUE, overlay = Kleenean.TRUE)
    public TimestampProperty fechaHoraCondicion;

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
    public TimestampProperty fechaHoraRegistro;

    @ColumnField(nullable = Kleenean.TRUE)
//  @PropertyField(hidden = Kleenean.TRUE)
    public TimestampProperty fechaHoraLimite;

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
        descripcionFuncion.setCalculableValueExpression(funcion.descripcionFuncion);
        /**/
        condicion.setInitialValue(condicion.DISPONIBLE);
        condicion.setDefaultValue(condicion.DISPONIBLE);
        /*
        condicionEjeFun.setCalculableValueEntityReference(rastroProceso.condicionEjeFun);
        RastroUtils.setGraphicImageExpressions(condicionEjeFun);
        /**/
        fechaHoraCondicion.setInitialValue(SpecialTemporalValue.CURRENT_TIMESTAMP);
        fechaHoraCondicion.setDefaultValue(SpecialTemporalValue.CURRENT_TIMESTAMP);
        fechaHoraRegistro.setInitialValue(SpecialTemporalValue.CURRENT_TIMESTAMP);
        fechaHoraRegistro.setDefaultValue(SpecialTemporalValue.CURRENT_TIMESTAMP);
        /**/
        setGraphicImageExpressions();
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
        /*
        condicionEjeFun.setLocalizedDescription(ENGLISH, "condition of the execution of the business process corresponding to the task");
        condicionEjeFun.setLocalizedDescription(SPANISH, "condición de la ejecución del proceso de negocio correspondiente a la tarea");
        condicionEjeFun.setLocalizedLabel(ENGLISH, "process execution condition");
        condicionEjeFun.setLocalizedLabel(SPANISH, "condición de ejecución del proceso");
        condicionEjeFun.setLocalizedShortLabel(ENGLISH, "process condition");
        condicionEjeFun.setLocalizedShortLabel(SPANISH, "condición del proceso");
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

    protected Key key1, key2, key3;

    @Override
    protected void settleKeys() {
        super.settleKeys();
        key1.newKeyField(tarea);
        key2.newKeyField(condicion, fechaHoraLimite, tarea, destinatario);
        key3.newKeyField(idClaseRecursoValor, recursoValor);
        /**/
        setOrderBy(key2);
        /**/
    }

    protected Tab descripcion, participantes, cronologia;

    @Override
    protected void settleTabs() {
        super.settleTabs();
        /**/
        descripcion.newTabField(funcion, descripcionFuncion, paginaFuncion, codigoClaseRecursoValor, nombreClaseRecursoValor, recursoValor, codigoRecursoValor, nombreRecursoValor, descripcionRecursoValor, paginaRecurso);
        descripcion.newTabField(condicion);
//      descripcion.newTabField(condicionEjeFun);
        descripcion.newTabField(rastroProceso);
        descripcion.newTabField(fechaHoraCondicion);
        descripcion.newTabField(fechaHoraLimite, prioridad);
        /**/
        participantes.newTabField(responsable, supervisor, finalizador);
        /**/
        cronologia.newTabField(condicion);
//      cronologia.newTabField(condicionEjeFun);
        cronologia.newTabField(rastroProceso);
        cronologia.newTabField(usuarioCondicion,
            fechaHoraCondicion, fechaHoraRegistro, fechaHoraAsignacion, fechaHoraAsuncion, fechaHoraRelevacion, fechaHoraAbandono, fechaHoraCancelacion, fechaHoraFinalizacion);
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

    protected Segment propiedadUsuarioActual, responsabilidadUsuarioActual;

    protected Segment pendienteUsuarioActual;

    protected State disponible, asignada, asumible, abandonable, pendiente, ejecutada, cancelada, finalizada;

    @Override
    protected void settleExpressions() {
        super.settleExpressions();
        /*
        propiedadUsuarioActual = destinatario.codigoUsuario.isEqualTo(SpecialCharacterValue.CURRENT_USER_CODE);
        responsabilidadUsuarioActual = responsable.isNotNull().and(responsable.codigoUsuario.isEqualTo(SpecialCharacterValue.CURRENT_USER_CODE));
        /**/
        propiedadUsuarioActual = destinatario.id.isEqualTo(CURRENT_USER_ID);
        responsabilidadUsuarioActual = responsable.isNotNull().and(responsable.id.isEqualTo(CURRENT_USER_ID));
        /**/
        disponible = condicion.isEqualTo(condicion.DISPONIBLE);
        asignada = condicion.isEqualTo(condicion.ASIGNADA);
        asumible = disponible.and(propiedadUsuarioActual);
        abandonable = asignada.and(propiedadUsuarioActual).and(responsabilidadUsuarioActual);
        pendiente = condicion.isIn(condicion.DISPONIBLE, condicion.ASIGNADA);
        ejecutada = condicion.isEqualTo(condicion.EJECUTADA);
        cancelada = condicion.isEqualTo(condicion.CANCELADA);
        finalizada = condicion.isIn(condicion.EJECUTADA, condicion.CANCELADA);
        /**/
        pendienteUsuarioActual = propiedadUsuarioActual.and(pendiente);
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
        pendienteUsuarioActual.setLocalizedDescription(ENGLISH, "the notification is addressed to the current user and the task has not been executed or canceled");
        pendienteUsuarioActual.setLocalizedDescription(SPANISH, "la notificación está dirigida al usuario actual y la tarea está pendiente");
        pendienteUsuarioActual.setLocalizedErrorMessage(ENGLISH, "the notification is addressed to another user or the task has already been executed or canceled");
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
        disponible.setLocalizedDescription(ENGLISH, "the task is available");
        disponible.setLocalizedDescription(SPANISH, "la tarea está disponible");
        disponible.setLocalizedErrorMessage(ENGLISH, "the task is not available");
        disponible.setLocalizedErrorMessage(SPANISH, "la tarea no está disponible");
        /**/
        asignada.setLocalizedDescription(ENGLISH, "the task is assigned");
        asignada.setLocalizedDescription(SPANISH, "la tarea está asignada");
        asignada.setLocalizedErrorMessage(ENGLISH, "the task is not assigned or it has already been executed or canceled");
        asignada.setLocalizedErrorMessage(SPANISH, "la tarea no está asignada o está ejecutada o cancelada");
        /**/
        asumible.setLocalizedDescription(ENGLISH, "the task is available and the notification is addressed to the current user");
        asumible.setLocalizedDescription(SPANISH, "la tarea está disponible y la notificación está dirigida al usuario actual");
        /**/
        abandonable.setLocalizedDescription(ENGLISH, "the task is assigned and the notification is addressed to the current user and "
            + "the current user is responsible for carrying out the task");
        abandonable.setLocalizedDescription(SPANISH, "la tarea está asignada y la notificación está dirigida al usuario actual y "
            + "el usuario actual es el responsable de la ejecución de la tarea");
        /**/
        pendiente.setLocalizedCollectionLabel(ENGLISH, "all notifications of tasks that have not been executed or canceled");
        pendiente.setLocalizedCollectionLabel(SPANISH, "todas las notificaciones de tareas que no se han ejecutado o cancelado");
        pendiente.setLocalizedCollectionShortLabel(ENGLISH, "Pending tasks");
        pendiente.setLocalizedCollectionShortLabel(SPANISH, "Tareas pendientes");
        pendiente.setLocalizedDescription(ENGLISH, "the task has not been executed or canceled");
        pendiente.setLocalizedDescription(SPANISH, "la tarea no está ejecutada ni cancelada");
        pendiente.setLocalizedErrorMessage(ENGLISH, "the task has already been executed or canceled");
        pendiente.setLocalizedErrorMessage(SPANISH, "la tarea está ejecutada o cancelada");
        /**/
        ejecutada.setLocalizedDescription(ENGLISH, "the task is executed");
        ejecutada.setLocalizedDescription(SPANISH, "la tarea está ejecutada");
        ejecutada.setLocalizedErrorMessage(ENGLISH, "the task is not executed");
        ejecutada.setLocalizedErrorMessage(SPANISH, "la tarea no está ejecutada");
        /**/
        cancelada.setLocalizedDescription(ENGLISH, "the task is canceled");
        cancelada.setLocalizedDescription(SPANISH, "la tarea está cancelada");
        cancelada.setLocalizedErrorMessage(ENGLISH, "the task is not canceled");
        cancelada.setLocalizedErrorMessage(SPANISH, "la tarea no está cancelada");
        /**/
        finalizada.setLocalizedCollectionLabel(ENGLISH, "all notifications of tasks that have been executed or canceled");
        finalizada.setLocalizedCollectionLabel(SPANISH, "todas las notificaciones de tareas que se han ejecutado o cancelado");
        finalizada.setLocalizedCollectionShortLabel(ENGLISH, "Finished tasks");
        finalizada.setLocalizedCollectionShortLabel(SPANISH, "Tareas finalizadas");
        finalizada.setLocalizedDescription(ENGLISH, "the task is executed or canceled");
        finalizada.setLocalizedDescription(SPANISH, "la tarea está ejecutada o cancelada");
        finalizada.setLocalizedErrorMessage(ENGLISH, "the task is not executed or canceled");
        finalizada.setLocalizedErrorMessage(SPANISH, "la tarea no está ejecutada ni cancelada");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleFilters() {
        super.settleFilters();
        /**/
        addSelectSegment(pendiente, true);
        addSelectSegment(finalizada);
        addSelectSegment(propiedadUsuarioActual, pendienteUsuarioActual, responsabilidadUsuarioActual);
        setSelectFilter(responsable.isNullOrEqualTo(destinatario));
        /**/
        rastroProceso.setRenderingFilter(rastroProceso.isNotNull());
        /**/
    }

    protected NotificarTareas notificarTareas;

    protected Asumir asumir;

    protected Abandonar abandonar;

    protected Asignar asignar;

    protected Relevar relevar;

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

        /*
        protected Check check101;

        @Override
        protected void settleExpressions() {
            super.settleExpressions();
            check101 = tarea.destinatario.id.isEqualTo(CURRENT_USER_ID);
            check101.setCheckpoint(Checkpoint.USER_INTERFACE);
            // <editor-fold defaultstate="collapsed" desc="localization of Asumir's expressions">
            check101.setLocalizedDescription(ENGLISH, "to assume a task, notification must be addressed to the user");
            check101.setLocalizedDescription(SPANISH, "para asumir una tarea, la notificacion debe estar dirigida al usuario");
            check101.setLocalizedErrorMessage(ENGLISH, "notification is addressed to another user");
            check101.setLocalizedErrorMessage(SPANISH, "la notificación esta dirigida a otro usuario");
            // </editor-fold>
        }

        /**/
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

        /*
        protected Check check101;

        @Override
        protected void settleExpressions() {
            super.settleExpressions();
            check101 = tarea.destinatario.id.isEqualTo(CURRENT_USER_ID);
            check101.setCheckpoint(Checkpoint.USER_INTERFACE);
            // <editor-fold defaultstate="collapsed" desc="localization of Abandonar's expressions">
            check101.setLocalizedDescription(ENGLISH, "to abandon a task, notification must be addressed to the user");
            check101.setLocalizedDescription(SPANISH, "para abandonar una tarea, la notificacion debe estar dirigida al usuario");
            check101.setLocalizedErrorMessage(ENGLISH, "notification is addressed to another user");
            check101.setLocalizedErrorMessage(SPANISH, "la notificación esta dirigida a otro usuario");
            // </editor-fold>
        }

        /**/
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
            check101 = tarea.destinatario.id.isNotEqualTo(CURRENT_USER_ID);
            check101.setCheckpoint(Checkpoint.USER_INTERFACE);
            // <editor-fold defaultstate="collapsed" desc="localization of Asignar's expressions">
            check101.setLocalizedDescription(ENGLISH, "to assign a task, notification must not be addressed to the user");
            check101.setLocalizedDescription(SPANISH, "para asignar una tarea, la notificacion no debe estar dirigida al usuario");
            check101.setLocalizedErrorMessage(ENGLISH, "notification is addressed to your user");
            check101.setLocalizedErrorMessage(SPANISH, "la notificación está dirigida a su usuario");
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
        protected Check check101;

        @Override
        protected void settleExpressions() {
            super.settleExpressions();
            check101 = tarea.destinatario.id.isNotEqualTo(CURRENT_USER_ID);
            check101.setCheckpoint(Checkpoint.USER_INTERFACE);
            // <editor-fold defaultstate="collapsed" desc="localization of Relevar's expressions">
            check101.setLocalizedDescription(ENGLISH, "to relieve a task, notification must not be addressed to the user");
            check101.setLocalizedDescription(SPANISH, "para relevar una tarea, la notificacion no debe estar dirigida al usuario");
            check101.setLocalizedErrorMessage(ENGLISH, "notification is addressed to your user");
            check101.setLocalizedErrorMessage(SPANISH, "la notificación está dirigida a su usuario");
            // </editor-fold>
        }

        /**/
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
            setLocalizedSuccessMessage(ENGLISH, "all task's notifications were canceled");
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
        asignar.addTransition(disponible, asignada);
        relevar.addTransition(asignada, disponible);
        asumir.addTransition(asumible, abandonable);
        abandonar.addTransition(abandonable, disponible);
        cancelar.addTransition(pendiente, cancelada);
    }

}
