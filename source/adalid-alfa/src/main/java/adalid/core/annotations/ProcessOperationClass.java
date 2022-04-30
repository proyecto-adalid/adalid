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
package adalid.core.annotations;

import adalid.core.enums.*;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * La anotación ProcessOperationClass se utiliza para establecer atributos de meta operaciones que extienden la clase ProcessOperation.
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ProcessOperationClass {

    /**
     * name especifica el nombre alterno del procedimiento.
     *
     * @return name
     */
    String name() default "";

    /**
     * processingGroup especifica el código que identifica el grupo de procesamiento al que pertenece la operación. Las aplicaciones generadas con la
     * plataforma jee2 no permiten la ejecución simultánea de operaciones de un mismo grupo.
     *
     * @return processingGroup
     */
    String processingGroup() default "";

    /**
     * newTaskNotification especifica el periodo de espera para enviar la notificación de nuevas tareas a los usuarios designados para ejecutar esta
     * clase de procesos.
     *
     * Es una expresión para calcular un valor relativo a la fecha y hora de la ejecución del disparador que da origen a la tarea. Escriba un número
     * entero, mayor o igual que cero, seguido de una letra que identifique el intervalo de tiempo. La letra D mayúscula corresponde a días; la h
     * minúscula, a horas; y la m minúscula, a minutos. Si especifica el número sin la letra, el intervalo será en días. Este atributo no tiene valor
     * predeterminado. Si no se especifica, no se envian notificaciones para esta clase de procesos.
     *
     * @return newTaskNotification
     */
    String newTaskNotification() default "";

    /**
     * notifySupervisor especifica si la notificación de nuevas tareas a los usuarios designados para ejecutar esta clase de procesos también se debe
     * enviar, o no, a los supervisores de tales usuarios. Especifique true para enviar la notificación a los supervisores. El valor predeterminado de
     * este atributo es false. Este atributo es relevante solo si se especificó el valor de newTaskNotification.
     *
     * @return notifySupervisor
     */
    boolean notifySupervisor() default false;

    /**
     * notifyAssignment especifica si se debe enviar, o no, una notificación a los usuarios cuando su supervisor les asigna la responsabilidad de
     * realizar una tarea de esta clase de procesos. El valor predeterminado de este atributo es false.
     *
     * @return notifyAssignment
     */
    boolean notifyAssignment() default false;

    /**
     * notifyAssumption especifica si se debe enviar, o no, una notificación a los supervisores cuando alguno de sus supervisados asume la
     * responsabilidad de realizar una tarea de esta clase de procesos. El valor predeterminado de este atributo es false.
     *
     * @return notifyAssumption
     */
    boolean notifyAssumption() default false;

    /**
     * notifyRelief especifica si se debe enviar, o no, una notificación a los usuarios cuando su supervisor les releva de la responsabilidad de
     * realizar una tarea de esta clase de procesos. El valor predeterminado de este atributo es false.
     *
     * @return notifyRelief
     */
    boolean notifyRelief() default false;

    /**
     * notifyAbandonment especifica si se debe enviar, o no, una notificación a los supervisores cuando alguno de sus supervisados abandona la
     * responsabilidad de realizar una tarea de esta clase de procesos que le fue asignada. El valor predeterminado de este atributo es false.
     *
     * @return notifyAbandonment
     */
    boolean notifyAbandonment() default false;

    /**
     * notifyCancellation especifica si se debe enviar, o no, una notificación a los usuarios que tienen asignada una tarea de esta clase de procesos
     * cuando su supervisor, o el sistema, cancela esa tarea. El valor predeterminado de este atributo es false.
     *
     * @return notifyCancellation
     */
    boolean notifyCancellation() default false;

    /**
     * notifyCompletion especifica si se debe enviar, o no, una notificación a los supervisores cuando alguno de sus supervisados finaliza una tarea
     * de esta clase de procesos. El valor predeterminado de este atributo es false.
     *
     * @return notifyCompletion
     */
    boolean notifyCompletion() default false;

    /**
     * unassignedTaskNotification especifica el periodo de espera para enviar la primera notificación de tareas no asignadas a los supervisores de los
     * usuarios designados para ejecutar esta clase de procesos.
     *
     * Es una expresión para calcular un valor relativo a la fecha y hora de la ejecución del disparador que da origen a la tarea. Escriba un número
     * entero, mayor o igual que cero, seguido de una letra que identifique el intervalo de tiempo. La letra D mayúscula corresponde a días; la h
     * minúscula, a horas; y la m minúscula, a minutos. Si especifica el número sin la letra, el intervalo será en días. Este atributo no tiene valor
     * predeterminado. Si no se especifica, no se envian notificaciones para esta clase de procesos.
     *
     * @return unassignedTaskNotification
     */
    String unassignedTaskNotification() default "";

    /**
     * unfinishedTaskNotification especifica el periodo de espera para enviar la primera notificación de tareas no realizadas a los supervisores de
     * los usuarios designados para ejecutar esta clase de procesos.
     *
     * Es una expresión para calcular un valor relativo a la fecha y hora de la ejecución del disparador que da origen a la tarea. Escriba un número
     * entero, mayor o igual que cero, seguido de una letra que identifique el intervalo de tiempo. La letra D mayúscula corresponde a días; la h
     * minúscula, a horas; y la m minúscula, a minutos. Si especifica el número sin la letra, el intervalo será en días. Este atributo no tiene valor
     * predeterminado. Si no se especifica, no se envian notificaciones para esta clase de procesos.
     *
     * @return unfinishedTaskNotification
     */
    String unfinishedTaskNotification() default "";

    /**
     * unassignedTaskEscalation especifica el periodo de espera para enviar la primera notificación de tareas no asignadas a los supervisores de los
     * supervisores de los usuarios designados para ejecutar esta clase de procesos.
     *
     * Es una expresión para calcular un valor relativo a la fecha y hora de la ejecución del disparador que da origen a la tarea. Escriba un número
     * entero, mayor o igual que cero, seguido de una letra que identifique el intervalo de tiempo. La letra D mayúscula corresponde a días; la h
     * minúscula, a horas; y la m minúscula, a minutos. Si especifica el número sin la letra, el intervalo será en días. Este atributo no tiene valor
     * predeterminado. Si no se especifica, no se envian notificaciones para esta clase de procesos.
     *
     * @return unassignedTaskEscalation
     */
    String unassignedTaskEscalation() default "";

    /**
     * unfinishedTaskEscalation especifica el periodo de espera para enviar la primera notificación de tareas no realizadas a los supervisores de los
     * supervisores de los usuarios designados para ejecutar esta clase de procesos.
     *
     * Es una expresión para calcular un valor relativo a la fecha y hora de la ejecución del disparador que da origen a la tarea. Escriba un número
     * entero, mayor o igual que cero, seguido de una letra que identifique el intervalo de tiempo. La letra D mayúscula corresponde a días; la h
     * minúscula, a horas; y la m minúscula, a minutos. Si especifica el número sin la letra, el intervalo será en días. Este atributo no tiene valor
     * predeterminado. Si no se especifica, no se envian notificaciones para esta clase de procesos.
     *
     * @return unfinishedTaskEscalation
     */
    String unfinishedTaskEscalation() default "";

    /**
     * nextUnassignedTaskNotification especifica el periodo de espera para enviar las sucesivas notificaciones de tareas no asignadas a los
     * supervisores de los usuarios designados para ejecutar esta clase de procesos.
     *
     * Es una expresión para calcular un valor relativo a la fecha y hora de la ejecución del disparador que da origen a la tarea. Escriba un número
     * entero, mayor o igual que cero, seguido de una letra que identifique el intervalo de tiempo. La letra D mayúscula corresponde a días; la h
     * minúscula, a horas; y la m minúscula, a minutos. Si especifica el número sin la letra, el intervalo será en días. Este atributo no tiene valor
     * predeterminado. Si no se especifica, no se envian notificaciones para esta clase de procesos.
     *
     * @return nextUnassignedTaskNotification
     */
    String nextUnassignedTaskNotification() default "";

    /**
     * nextUnfinishedTaskNotification especifica el periodo de espera para enviar las sucesivas notificaciones de tareas no realizadas a los
     * supervisores de los usuarios designados para ejecutar esta clase de procesos.
     *
     * Es una expresión para calcular un valor relativo a la fecha y hora de la ejecución del disparador que da origen a la tarea. Escriba un número
     * entero, mayor o igual que cero, seguido de una letra que identifique el intervalo de tiempo. La letra D mayúscula corresponde a días; la h
     * minúscula, a horas; y la m minúscula, a minutos. Si especifica el número sin la letra, el intervalo será en días. Este atributo no tiene valor
     * predeterminado. Si no se especifica, no se envian notificaciones para esta clase de procesos.
     *
     * @return nextUnfinishedTaskNotification
     */
    String nextUnfinishedTaskNotification() default "";

    /**
     * nextUnassignedTaskEscalation especifica el periodo de espera para enviar las sucesivas notificaciones de tareas no asignadas a los supervisores
     * de los supervisores de los usuarios designados para ejecutar esta clase de procesos.
     *
     * Es una expresión para calcular un valor relativo a la fecha y hora de la ejecución del disparador que da origen a la tarea. Escriba un número
     * entero, mayor o igual que cero, seguido de una letra que identifique el intervalo de tiempo. La letra D mayúscula corresponde a días; la h
     * minúscula, a horas; y la m minúscula, a minutos. Si especifica el número sin la letra, el intervalo será en días. Este atributo no tiene valor
     * predeterminado. Si no se especifica, no se envian notificaciones para esta clase de procesos.
     *
     * @return nextUnassignedTaskEscalation
     */
    String nextUnassignedTaskEscalation() default "";

    /**
     * nextUnfinishedTaskEscalation especifica el periodo de espera para enviar las sucesivas notificaciones de tareas no realizadas a los
     * supervisores de los supervisores de los usuarios designados para ejecutar esta clase de procesos.
     *
     * Es una expresión para calcular un valor relativo a la fecha y hora de la ejecución del disparador que da origen a la tarea. Escriba un número
     * entero, mayor o igual que cero, seguido de una letra que identifique el intervalo de tiempo. La letra D mayúscula corresponde a días; la h
     * minúscula, a horas; y la m minúscula, a minutos. Si especifica el número sin la letra, el intervalo será en días. Este atributo no tiene valor
     * predeterminado. Si no se especifica, no se envian notificaciones para esta clase de procesos.
     *
     * @return nextUnfinishedTaskEscalation
     */
    String nextUnfinishedTaskEscalation() default "";

    /**
     * deadline especifica el máximo periodo de tiempo en el que se deberían realizar las tareas de esta clase de procesos.
     *
     * Es una expresión para calcular un valor relativo a la fecha y hora de la ejecución del disparador que da origen a la tarea. Escriba un número
     * entero, mayor o igual que cero, seguido de una letra que identifique el intervalo de tiempo. La letra D mayúscula corresponde a días; la h
     * minúscula, a horas; y la m minúscula, a minutos. Si especifica el número sin la letra, el intervalo será en días. Este atributo no tiene valor
     * predeterminado. Si no se especifica, no se calcula la fecha/hora límite para esta clase de procesos.
     *
     * @return deadline
     */
    String deadline() default "";

    /**
     * automaticAssumption especifica si el usuario, cuyas acciones generan una tarea de esta clase de procesos, automáticamente asume, o no, la
     * responsabilidad de realizar esa tarea. La asunción automática solo será posible si ejecutar el proceso es tarea del usuario y éste está
     * debidamente autorizado para hacerlo sobre el recurso que corresponde a la tarea; además, si la tarea se dispara por ejecutar la función insert,
     * la asunción se produce solo si la entidad tiene propiedad usuario (vea Anotación UserProperty) o propiedad propietario (vea Anotación
     * OwnerProperty). Si tiene ambas, se utiliza la propiedad usuario. El valor predeterminado de este atributo es false.
     *
     * @return automaticAssumption
     */
    boolean automaticAssumption() default false;

    /**
     * Elemento reservado para las operaciones propias de la plataforma
     *
     * @return builtIn
     */
    boolean builtIn() default false;

    /**
     * priority especifica la prioridad de las tareas de esta clase de procesos. Debe ser un número entero, mayor o igual que cero. El valor
     * predeterminado de este atributo es 0 (la mínima prioridad).
     *
     * @return priority
     */
    int priority() default 0;

    /**
     * bpl indica si se debe, o no, generar código BPL (Business Process Logic) para la operación. Su valor es uno de los elementos de la enumeración
     * Kleenean. Seleccione TRUE para generar código BPL; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o seleccione
     * UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es TRUE. La generación del código BPL
     * también depende del valor especificado mediante el elemento <b>bpl</b> de la anotación <b>EntityCodeGen</b> de la entidad a la que pertenece la
     * operación.
     *
     * @return bpl
     */
    Kleenean bpl() default Kleenean.UNSPECIFIED; // TRUE

    /**
     * sql indica si se debe, o no, generar código SQL para la operación. Su valor es uno de los elementos de la enumeración Kleenean. Seleccione TRUE
     * para generar código SQL; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el
     * valor predeterminado del atributo. El valor predeterminado del atributo es TRUE. La generación del código SQL también depende del valor
     * especificado mediante el elemento <b>bpl</b> de la anotación <b>EntityCodeGen</b> de la entidad a la que pertenece la operación.
     *
     * @return sql
     */
    Kleenean sql() default Kleenean.UNSPECIFIED; // TRUE

    /**
     * overloading indica si al generar procedimientos SQL de la operación se puede, o no, sobrecargar (overload) el nombre del procedimiento. Su
     * valor es uno de los elementos de la enumeración Kleenean. Seleccione TRUE si se puede sobrecargar el nombre; en caso contrario, seleccione
     * FALSE. Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado
     * del atributo es FALSE.
     *
     * @return overloading
     */
    Kleenean overloading() default Kleenean.UNSPECIFIED;

    /**
     * serviceable indica si se debe, o no, generar código BWS (Business Web Service) para la operación. Su valor es uno de los elementos de la
     * enumeración Kleenean. Seleccione TRUE para generar código BWS; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o
     * seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es FALSE. La generación del
     * código BWS también depende del valor especificado mediante el método <b>setInternetAccessAllowed</b> del proyecto maestro, y del elemento
     * <b>bws</b> de la anotación <b>EntityCodeGen</b> de la entidad a la que pertenece la operación.
     *
     * @return serviceable
     */
    Kleenean serviceable() default Kleenean.UNSPECIFIED;

}
