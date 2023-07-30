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

import adalid.core.annotations.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import java.lang.reflect.Field;
import meta.entidad.comun.abstracta.TransicionAbstracta;
import meta.entidad.comun.control.acceso.Usuario;

/**
 * @author Jorge Campins
 */
public class TransicionTareaUsuario extends TransicionAbstracta {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public TransicionTareaUsuario(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of TransicionTareaUsuario's attributes">
        setLocalizedLabel(ENGLISH, "task transition");
        setLocalizedLabel(SPANISH, "transición de tarea");
        setLocalizedCollectionLabel(ENGLISH, "Task Transitions");
        setLocalizedCollectionLabel(SPANISH, "Transiciones de Tareas");
        /**/
        setLocalizedDescription(ENGLISH, "Each instance of " + b("Task Transitions") + " represents a "
            + "change of the state of a task notification."
            + "");
        setLocalizedDescription(SPANISH, "Cada instancia de " + b("Transiciones de Tareas") + " representa un "
            + "cambio del estado de una notificación de tarea."
            + "");
        /**/
        setLocalizedShortDescription(ENGLISH, "change of the state of a task notification");
        setLocalizedShortDescription(SPANISH, "cambio del estado de una notificación de tarea");
        /**/
        // </editor-fold>
    }

    @ColumnField(nullable = Kleenean.FALSE)
    @ForeignKey(onDelete = OnDeleteAction.CASCADE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.BIDIRECTIONAL, view = MasterDetailView.TABLE_AND_DETAIL)
    @PropertyField(table = Kleenean.TRUE, heading = Kleenean.TRUE)
    public TareaUsuario tarea;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE, search = Kleenean.TRUE)
    public CondicionTarea condicionInicial;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE, search = Kleenean.TRUE)
    public CondicionTarea condicionFinal;

    @ColumnField(calculable = Kleenean.TRUE)
//  @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(table = Kleenean.TRUE, detail = Kleenean.FALSE, report = Kleenean.FALSE, search = Kleenean.TRUE)
    public Usuario responsable;

    @ColumnField(nullable = Kleenean.TRUE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(table = Kleenean.FALSE, report = Kleenean.TRUE, search = Kleenean.TRUE)
    public Usuario responsableInicial;

    @ColumnField(nullable = Kleenean.TRUE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(table = Kleenean.FALSE, report = Kleenean.TRUE, search = Kleenean.TRUE)
    public Usuario responsableFinal;

    @Override
    protected void settleProperties() {
        super.settleProperties();
        /**/
        responsable.setCalculableValueExpression(responsableFinal.isNull().then(responsableInicial).otherwise(responsableFinal));
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of TransicionTareaUsuario's properties">
        /**/
        tarea.setLocalizedLabel(ENGLISH, "task");
        tarea.setLocalizedLabel(SPANISH, "tarea");
        /**/
        condicionInicial.setLocalizedLabel(ENGLISH, "initial condition");
        condicionInicial.setLocalizedLabel(SPANISH, "condición inicial");
        /**/
        condicionFinal.setLocalizedLabel(ENGLISH, "final condition");
        condicionFinal.setLocalizedLabel(SPANISH, "condición final");
        /**/
        responsable.setLocalizedLabel(ENGLISH, "responsible");
        responsable.setLocalizedLabel(SPANISH, "responsable");
        /**/
        responsableInicial.setLocalizedLabel(ENGLISH, "initial responsible");
        responsableInicial.setLocalizedLabel(SPANISH, "responsable inicial");
        /**/
        responsableFinal.setLocalizedLabel(ENGLISH, "initial responsible");
        responsableFinal.setLocalizedLabel(SPANISH, "responsable final");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleFilters() {
        super.settleFilters();
        /**/
        comentarios.setRenderingFilter(UNTRUTH);
        /**/
    }

}
