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
import meta.entidad.comun.control.acceso.Usuario;

/**
 * @author Jorge Campins
 */
@EntityClass(base = Kleenean.TRUE, independent = Kleenean.FALSE, viewType = EntityViewType.MASTER_DETAIL, resourceType = ResourceType.OPERATION, resourceGender = ResourceGender.MASCULINE)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntityDocGen(stateDiagram = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE)
@EntityInsertOperation(enabled = Kleenean.FALSE)
@EntityUpdateOperation(enabled = Kleenean.FALSE)
@EntityDeleteOperation(enabled = Kleenean.FALSE)
@EntityTableView(enabled = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.TRUE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
public class TareaUsuarioCorreo extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public TareaUsuarioCorreo(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public LongProperty id;

    @VersionProperty
    public LongProperty version;

    @ColumnField(nullable = Kleenean.FALSE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @OneToOne(navigability = Navigability.UNIDIRECTIONAL, detailView = Kleenean.TRUE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE, search = Kleenean.TRUE)
    public TareaUsuario tareaUsuario;

    @ColumnField(nullable = Kleenean.FALSE, indexed = Kleenean.TRUE)
    @PropertyField(hidden = Kleenean.TRUE, defaultCheckpoint = Checkpoint.USER_INTERFACE)
    public LongProperty tarea;

    @ColumnField(nullable = Kleenean.TRUE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE, search = Kleenean.TRUE)
    public Usuario supervisorSuperior;

    @PropertyField(hidden = Kleenean.TRUE)
    public BooleanProperty tieneSupervisorSuperior;

    @ColumnField(nullable = Kleenean.TRUE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE, search = Kleenean.TRUE)
    public TimestampProperty fechaHoraUltimoCorreo;

    /**
     * notificar a destinatarios de nuevas tareas
     */
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE, search = Kleenean.TRUE)
    public BooleanProperty notificarDestinatario;

    /**
     * fecha y hora de la próxima notificación a destinatarios
     */
    @ColumnField(nullable = Kleenean.TRUE)
    public TimestampProperty proximaNotaDestinatario;

    /**
     * fecha y hora de la última notificación a destinatarios
     */
    @ColumnField(nullable = Kleenean.TRUE)
    public TimestampProperty ultimaNotaDestinatario;

    /**
     * notificar a supervisores de nuevas tareas para sus supervisados
     */
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE, search = Kleenean.TRUE)
    public BooleanProperty notificarSupervisor;

    /**
     * fecha y hora de la próxima notificación a supervisores
     */
    @ColumnField(nullable = Kleenean.TRUE)
    public TimestampProperty proximaNotaSupervisor;

    /**
     * fecha y hora de la última notificación a supervisores
     */
    @ColumnField(nullable = Kleenean.TRUE)
    public TimestampProperty ultimaNotaSupervisor;

    /**
     * notificar a destinatarios de tareas asignadas por sus supervisores
     */
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE, search = Kleenean.TRUE)
    public BooleanProperty notificarAsignacion;

    /**
     * fecha y hora de la próxima notificación a destinatarios de tareas asignadas por sus supervisores
     */
    @ColumnField(nullable = Kleenean.TRUE)
    public TimestampProperty proximaNotaAsignacion;

    /**
     * fecha y hora de la última notificación a destinatarios de tareas asignadas por sus supervisores
     */
    @ColumnField(nullable = Kleenean.TRUE)
    public TimestampProperty ultimaNotaAsignacion;

    /**
     * notificar a supervisores de tareas asumidas por sus supervisados
     */
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE, search = Kleenean.TRUE)
    public BooleanProperty notificarAsuncion;

    /**
     * fecha y hora de la próxima notificación a supervisores de tareas asumidas por sus supervisados
     */
    @ColumnField(nullable = Kleenean.TRUE)
    public TimestampProperty proximaNotaAsuncion;

    /**
     * fecha y hora de la última notificación a supervisores de tareas asumidas por sus supervisados
     */
    @ColumnField(nullable = Kleenean.TRUE)
    public TimestampProperty ultimaNotaAsuncion;

    /**
     * notificar a destinatarios de tareas relevadas por sus supervisores
     */
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE, search = Kleenean.TRUE)
    public BooleanProperty notificarRelevacion;

    /**
     * fecha y hora de la próxima notificación a destinatarios de tareas relevadas por sus supervisores
     */
    @ColumnField(nullable = Kleenean.TRUE)
    public TimestampProperty proximaNotaRelevacion;

    /**
     * fecha y hora de la última notificación a destinatarios de tareas relevadas por sus supervisores
     */
    @ColumnField(nullable = Kleenean.TRUE)
    public TimestampProperty ultimaNotaRelevacion;

    /**
     * notificar a supervisores de tareas abandonadas por sus supervisados
     */
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE, search = Kleenean.TRUE)
    public BooleanProperty notificarAbandono;

    /**
     * fecha y hora de la próxima notificación a supervisores de tareas abandonadas por sus supervisados
     */
    @ColumnField(nullable = Kleenean.TRUE)
    public TimestampProperty proximaNotaAbandono;

    /**
     * fecha y hora de la última notificación a supervisores de tareas abandonadas por sus supervisados
     */
    @ColumnField(nullable = Kleenean.TRUE)
    public TimestampProperty ultimaNotaAbandono;

    /**
     * notificar a destinatarios de tareas canceladas por sus supervisores
     */
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE, search = Kleenean.TRUE)
    public BooleanProperty notificarCancelacion;

    /**
     * fecha y hora de la próxima notificación a destinatarios de tareas canceladas por sus supervisores
     */
    @ColumnField(nullable = Kleenean.TRUE)
    public TimestampProperty proximaNotaCancelacion;

    /**
     * fecha y hora de la última notificación a destinatarios de tareas canceladas por sus supervisores
     */
    @ColumnField(nullable = Kleenean.TRUE)
    public TimestampProperty ultimaNotaCancelacion;

    /**
     * notificar a supervisores de tareas finalizadas por sus supervisados
     */
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE, search = Kleenean.TRUE)
    public BooleanProperty notificarFinalizacion;

    /**
     * fecha y hora de la próxima notificación a supervisores de tareas finalizadas por sus supervisados
     */
    @ColumnField(nullable = Kleenean.TRUE)
    public TimestampProperty proximaNotaFinalizacion;

    /**
     * fecha y hora de la última notificación a supervisores de tareas finalizadas por sus supervisados
     */
    @ColumnField(nullable = Kleenean.TRUE)
    public TimestampProperty ultimaNotaFinalizacion;

    /**
     * advertir a supervisores de tareas no asumidas por sus supervisados
     */
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE, search = Kleenean.TRUE)
    public BooleanProperty advertirAsignar;

    /**
     * fecha y hora de la próxima advertencia de tarea no asignada
     */
    @ColumnField(nullable = Kleenean.TRUE)
    public TimestampProperty proximoAdvertirAsignar;

    /**
     * fecha y hora de la última advertencia de tarea no asignada
     */
    @ColumnField(nullable = Kleenean.TRUE)
    public TimestampProperty ultimoAdvertirAsignar;

    /**
     * periodo de espera para enviar las sucesivas advertencias de tareas no asignadas
     */
    @PropertyField(hidden = Kleenean.TRUE)
    public IntegerProperty minutosAdvertirAsignar;

    /**
     * advertir a supervisores de tareas no finalizadas por sus supervisados
     */
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE, search = Kleenean.TRUE)
    public BooleanProperty advertirFinalizar;

    /**
     * fecha y hora de la próxima advertencia de tarea no finalizada
     */
    @ColumnField(nullable = Kleenean.TRUE)
    public TimestampProperty proximoAdvertirFinalizar;

    /**
     * fecha y hora de la última advertencia de tarea no finalizada
     */
    @ColumnField(nullable = Kleenean.TRUE)
    public TimestampProperty ultimoAdvertirFinalizar;

    /**
     * periodo de espera para enviar las sucesivas advertencias de tareas no realizadas
     */
    @PropertyField(hidden = Kleenean.TRUE)
    public IntegerProperty minutosAdvertirFinalizar;

    /**
     * escalar a superiores de tareas no asignadas por sus supervisados
     */
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE, search = Kleenean.TRUE)
    public BooleanProperty escalarAsignar;

    /**
     * fecha y hora del próximo escalamiento de tarea no asignada
     */
    @ColumnField(nullable = Kleenean.TRUE)
    public TimestampProperty proximoEscalarAsignar;

    /**
     * fecha y hora del último escalamiento de tarea no asignada
     */
    @ColumnField(nullable = Kleenean.TRUE)
    public TimestampProperty ultimoEscalarAsignar;

    /**
     * periodo de espera para enviar las sucesivas advertencias de escalamiento de tareas no asignadas
     */
    @PropertyField(hidden = Kleenean.TRUE)
    public IntegerProperty minutosEscalarAsignar;

    /**
     * escalar a superiores de tareas no finalizadas por sus supervisados
     */
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE, search = Kleenean.TRUE)
    public BooleanProperty escalarFinalizar;

    /**
     * fecha y hora del próximo escalamiento de tarea no finalizada
     */
    @ColumnField(nullable = Kleenean.TRUE)
    public TimestampProperty proximoEscalarFinalizar;

    /**
     * fecha y hora del último escalamiento de tarea no finalizada
     */
    @ColumnField(nullable = Kleenean.TRUE)
    public TimestampProperty ultimoEscalarFinalizar;

    /**
     * periodo de espera para enviar las sucesivas advertencias de escalamiento de tareas no realizadas
     */
    @PropertyField(hidden = Kleenean.TRUE)
    public IntegerProperty minutosEscalarFinalizar;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of TareaUsuarioCorreo's attributes">
        setLocalizedLabel(ENGLISH, "task mail");
        setLocalizedLabel(SPANISH, "correo de tarea");
        setLocalizedCollectionLabel(ENGLISH, "Task Mails");
        setLocalizedCollectionLabel(SPANISH, "Correos de Tareas");
        /**/
        setLocalizedDescription(ENGLISH, "Each instance of " + b("Task Mails") + " represents a "
            + "task notification e-mail."
            + "");
        setLocalizedDescription(SPANISH, "Cada instancia de " + b("Correos de Tareas") + " representa un "
            + "correo electrónico de notificación de tarea."
            + "");
        /**/
        setLocalizedShortDescription(ENGLISH, "task notification e-mail");
        setLocalizedShortDescription(SPANISH, "correo electrónico de notificación de tarea");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        /**/
        tarea.setInitialValue(tareaUsuario.tarea);
        tarea.setDefaultValue(tareaUsuario.tarea);
        /**/
        notificarDestinatario.setInitialValue(false);
        notificarDestinatario.setDefaultValue(false);
        notificarSupervisor.setInitialValue(false);
        notificarSupervisor.setDefaultValue(false);
        notificarAsignacion.setInitialValue(false);
        notificarAsignacion.setDefaultValue(false);
        notificarAsuncion.setInitialValue(false);
        notificarAsuncion.setDefaultValue(false);
        notificarRelevacion.setInitialValue(false);
        notificarRelevacion.setDefaultValue(false);
        notificarAbandono.setInitialValue(false);
        notificarAbandono.setDefaultValue(false);
        notificarCancelacion.setInitialValue(false);
        notificarCancelacion.setDefaultValue(false);
        notificarFinalizacion.setInitialValue(false);
        notificarFinalizacion.setDefaultValue(false);
        advertirAsignar.setInitialValue(false);
        advertirAsignar.setDefaultValue(false);
        advertirFinalizar.setInitialValue(false);
        advertirFinalizar.setDefaultValue(false);
        escalarAsignar.setInitialValue(false);
        escalarAsignar.setDefaultValue(false);
        escalarFinalizar.setInitialValue(false);
        escalarFinalizar.setDefaultValue(false);
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of TareaUsuarioCorreo's properties">
        /**/
        tareaUsuario.setLocalizedLabel(ENGLISH, "task");
        tareaUsuario.setLocalizedLabel(SPANISH, "tarea");
        /**/
        tarea.setLocalizedLabel(ENGLISH, "task number");
        tarea.setLocalizedLabel(SPANISH, "número de tarea");
        /**/
        supervisorSuperior.setLocalizedLabel(ENGLISH, "next escalation supervisor");
        supervisorSuperior.setLocalizedLabel(SPANISH, "supervisor próximo escalamiento");
        /**/
        tieneSupervisorSuperior.setLocalizedLabel(ENGLISH, "has top supervisor");
        tieneSupervisorSuperior.setLocalizedLabel(SPANISH, "tiene supervisor superior");
        /**/
        fechaHoraUltimoCorreo.setLocalizedLabel(ENGLISH, "last e-mail timestamp");
        fechaHoraUltimoCorreo.setLocalizedLabel(SPANISH, "fecha/hora último correo");
        fechaHoraUltimoCorreo.setLocalizedShortLabel(ENGLISH, "last e-mail");
        fechaHoraUltimoCorreo.setLocalizedShortLabel(SPANISH, "último correo");
        /**/
        notificarDestinatario.setLocalizedLabel(ENGLISH, "notify recipient");
        notificarDestinatario.setLocalizedLabel(SPANISH, "notificar destinatario");
        /**/
        proximaNotaDestinatario.setLocalizedLabel(ENGLISH, "next recipient notification");
        proximaNotaDestinatario.setLocalizedLabel(SPANISH, "próxima notificación destinatario");
        /**/
        ultimaNotaDestinatario.setLocalizedLabel(ENGLISH, "last recipient notification");
        ultimaNotaDestinatario.setLocalizedLabel(SPANISH, "última notificación destinatario");
        /**/
        notificarSupervisor.setLocalizedLabel(ENGLISH, "notify supervisor");
        notificarSupervisor.setLocalizedLabel(SPANISH, "notificar supervisor");
        /**/
        proximaNotaSupervisor.setLocalizedLabel(ENGLISH, "next supervisor notification");
        proximaNotaSupervisor.setLocalizedLabel(SPANISH, "próxima notificación supervisor");
        /**/
        ultimaNotaSupervisor.setLocalizedLabel(ENGLISH, "last supervisor notification");
        ultimaNotaSupervisor.setLocalizedLabel(SPANISH, "última notificación supervisor");
        /**/
        notificarAsignacion.setLocalizedLabel(ENGLISH, "notify assignment");
        notificarAsignacion.setLocalizedLabel(SPANISH, "notificar asignación");
        /**/
        proximaNotaAsignacion.setLocalizedLabel(ENGLISH, "next assignment notification");
        proximaNotaAsignacion.setLocalizedLabel(SPANISH, "próxima notificación asignación");
        /**/
        ultimaNotaAsignacion.setLocalizedLabel(ENGLISH, "last assignment notification");
        ultimaNotaAsignacion.setLocalizedLabel(SPANISH, "última notificación asignación");
        /**/
        notificarAsuncion.setLocalizedLabel(ENGLISH, "notify assumption");
        notificarAsuncion.setLocalizedLabel(SPANISH, "notificar asunción");
        /**/
        proximaNotaAsuncion.setLocalizedLabel(ENGLISH, "next assumption notification");
        proximaNotaAsuncion.setLocalizedLabel(SPANISH, "próxima notificación asunción");
        /**/
        ultimaNotaAsuncion.setLocalizedLabel(ENGLISH, "last assumption notification");
        ultimaNotaAsuncion.setLocalizedLabel(SPANISH, "última notificación asunción");
        /**/
        notificarRelevacion.setLocalizedLabel(ENGLISH, "notify relief");
        notificarRelevacion.setLocalizedLabel(SPANISH, "notificar relevación");
        /**/
        proximaNotaRelevacion.setLocalizedLabel(ENGLISH, "next relief notification");
        proximaNotaRelevacion.setLocalizedLabel(SPANISH, "próxima notificación relevación");
        /**/
        ultimaNotaRelevacion.setLocalizedLabel(ENGLISH, "last relief notification");
        ultimaNotaRelevacion.setLocalizedLabel(SPANISH, "última notificación relevación");
        /**/
        notificarAbandono.setLocalizedLabel(ENGLISH, "notify abandonment");
        notificarAbandono.setLocalizedLabel(SPANISH, "notificar abandono");
        /**/
        proximaNotaAbandono.setLocalizedLabel(ENGLISH, "next abandonment notification");
        proximaNotaAbandono.setLocalizedLabel(SPANISH, "próxima notificación abandono");
        /**/
        ultimaNotaAbandono.setLocalizedLabel(ENGLISH, "last abandonment notification");
        ultimaNotaAbandono.setLocalizedLabel(SPANISH, "última notificación abandono");
        /**/
        notificarCancelacion.setLocalizedLabel(ENGLISH, "notify cancellation");
        notificarCancelacion.setLocalizedLabel(SPANISH, "notificar cancelación");
        /**/
        proximaNotaCancelacion.setLocalizedLabel(ENGLISH, "next cancellation notification");
        proximaNotaCancelacion.setLocalizedLabel(SPANISH, "próxima notificación cancelación");
        /**/
        ultimaNotaCancelacion.setLocalizedLabel(ENGLISH, "last cancellation notification");
        ultimaNotaCancelacion.setLocalizedLabel(SPANISH, "última notificación cancelación");
        /**/
        notificarFinalizacion.setLocalizedLabel(ENGLISH, "notify cancellation");
        notificarFinalizacion.setLocalizedLabel(SPANISH, "notificar finalización");
        /**/
        proximaNotaFinalizacion.setLocalizedLabel(ENGLISH, "next cancellation notification");
        proximaNotaFinalizacion.setLocalizedLabel(SPANISH, "próxima notificación finalización");
        /**/
        ultimaNotaFinalizacion.setLocalizedLabel(ENGLISH, "last cancellation notification");
        ultimaNotaFinalizacion.setLocalizedLabel(SPANISH, "última notificación finalización");
        /**/
        advertirAsignar.setLocalizedLabel(ENGLISH, "remind assignment");
        advertirAsignar.setLocalizedLabel(SPANISH, "recordar asignación");
        /**/
        proximoAdvertirAsignar.setLocalizedLabel(ENGLISH, "next assignment reminder");
        proximoAdvertirAsignar.setLocalizedLabel(SPANISH, "próximo recordatorio asignación");
        /**/
        ultimoAdvertirAsignar.setLocalizedLabel(ENGLISH, "last assignment reminder");
        ultimoAdvertirAsignar.setLocalizedLabel(SPANISH, "último recordatorio asignación");
        /**/
        minutosAdvertirAsignar.setLocalizedLabel(ENGLISH, "next assignment reminder minutes");
        minutosAdvertirAsignar.setLocalizedLabel(SPANISH, "minutos siguiente recordatorio asignación");
        /**/
        advertirFinalizar.setLocalizedLabel(ENGLISH, "remind execution");
        advertirFinalizar.setLocalizedLabel(SPANISH, "recordar finalización");
        /**/
        proximoAdvertirFinalizar.setLocalizedLabel(ENGLISH, "next execution reminder");
        proximoAdvertirFinalizar.setLocalizedLabel(SPANISH, "próximo recordatorio finalización");
        /**/
        ultimoAdvertirFinalizar.setLocalizedLabel(ENGLISH, "last execution reminder");
        ultimoAdvertirFinalizar.setLocalizedLabel(SPANISH, "último recordatorio finalización");
        /**/
        minutosAdvertirFinalizar.setLocalizedLabel(ENGLISH, "next execution reminder minutes");
        minutosAdvertirFinalizar.setLocalizedLabel(SPANISH, "minutos siguiente recordatorio finalización");
        /**/
        escalarAsignar.setLocalizedLabel(ENGLISH, "escalate assignment");
        escalarAsignar.setLocalizedLabel(SPANISH, "escalar asignación");
        /**/
        proximoEscalarAsignar.setLocalizedLabel(ENGLISH, "next assignment escalation");
        proximoEscalarAsignar.setLocalizedLabel(SPANISH, "próximo escalamiento asignación");
        /**/
        ultimoEscalarAsignar.setLocalizedLabel(ENGLISH, "last assignment escalation");
        ultimoEscalarAsignar.setLocalizedLabel(SPANISH, "último escalamiento asignación");
        /**/
        minutosEscalarAsignar.setLocalizedLabel(ENGLISH, "next assignment escalation minutes");
        minutosEscalarAsignar.setLocalizedLabel(SPANISH, "minutos siguiente escalamiento asignación");
        /**/
        escalarFinalizar.setLocalizedLabel(ENGLISH, "escalate execution");
        escalarFinalizar.setLocalizedLabel(SPANISH, "escalar finalización");
        /**/
        proximoEscalarFinalizar.setLocalizedLabel(ENGLISH, "next execution escalation");
        proximoEscalarFinalizar.setLocalizedLabel(SPANISH, "próximo escalamiento finalización");
        /**/
        ultimoEscalarFinalizar.setLocalizedLabel(ENGLISH, "last execution escalation");
        ultimoEscalarFinalizar.setLocalizedLabel(SPANISH, "último escalamiento finalización");
        /**/
        minutosEscalarFinalizar.setLocalizedLabel(ENGLISH, "next execution escalation minutes");
        minutosEscalarFinalizar.setLocalizedLabel(SPANISH, "minutos siguiente escalamiento finalización");
        /**/
        // </editor-fold>
    }

    protected Tab notificaciones, eventos, advertencias, escalamientos;

    @Override
    protected void settleTabs() {
        super.settleTabs();
        /**/
        notificaciones.newTabField(notificarDestinatario, proximaNotaDestinatario, ultimaNotaDestinatario);
        notificaciones.newTabField(notificarSupervisor, proximaNotaSupervisor, ultimaNotaSupervisor);
        /**/
        eventos.newTabField(notificarAsignacion, proximaNotaAsignacion, ultimaNotaAsignacion);
        eventos.newTabField(notificarAsuncion, proximaNotaAsuncion, ultimaNotaAsuncion);
        eventos.newTabField(notificarRelevacion, proximaNotaRelevacion, ultimaNotaRelevacion);
        eventos.newTabField(notificarAbandono, proximaNotaAbandono, ultimaNotaAbandono);
        eventos.newTabField(notificarCancelacion, proximaNotaCancelacion, ultimaNotaCancelacion);
        eventos.newTabField(notificarFinalizacion, proximaNotaFinalizacion, ultimaNotaFinalizacion);
        /**/
        advertencias.newTabField(advertirAsignar, proximoAdvertirAsignar, ultimoAdvertirAsignar, minutosAdvertirAsignar);
        advertencias.newTabField(advertirFinalizar, proximoAdvertirFinalizar, ultimoAdvertirFinalizar, minutosAdvertirFinalizar);
        /**/
        escalamientos.newTabField(supervisorSuperior, tieneSupervisorSuperior);
        escalamientos.newTabField(escalarAsignar, proximoEscalarAsignar, ultimoEscalarAsignar, minutosEscalarAsignar);
        escalamientos.newTabField(escalarFinalizar, proximoEscalarFinalizar, ultimoEscalarFinalizar, minutosEscalarFinalizar);
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of TareaUsuarioCorreo's tabs">
        /**/
        notificaciones.setLocalizedLabel(ENGLISH, "notifications");
        notificaciones.setLocalizedLabel(SPANISH, "notificaciones");
        notificaciones.setLocalizedDescription(ENGLISH, "initial notifications");
        notificaciones.setLocalizedDescription(SPANISH, "notificaciones iniciales");
        /**/
        eventos.setLocalizedLabel(ENGLISH, "events");
        eventos.setLocalizedLabel(SPANISH, "eventos");
        eventos.setLocalizedDescription(ENGLISH, "event notifications");
        eventos.setLocalizedDescription(SPANISH, "notificaciones de eventos");
        /**/
        advertencias.setLocalizedLabel(ENGLISH, "reminders");
        advertencias.setLocalizedLabel(SPANISH, "recordatorios");
        /**/
        escalamientos.setLocalizedLabel(ENGLISH, "escalations");
        escalamientos.setLocalizedLabel(SPANISH, "escalamientos");
        /**/
        // </editor-fold>
    }

}
