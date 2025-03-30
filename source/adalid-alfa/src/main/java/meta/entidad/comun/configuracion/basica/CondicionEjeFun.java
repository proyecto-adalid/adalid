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
package meta.entidad.comun.configuracion.basica;

import adalid.core.*;
import adalid.core.annotations.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import adalid.core.properties.*;
import java.lang.reflect.Field;

/**
 * @author Jorge Campins
 */
@EntityClass(independent = Kleenean.TRUE, resourceType = ResourceType.CONFIGURATION, resourceGender = ResourceGender.FEMININE)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE, access = OperationAccess.PUBLIC, rowsLimit = 0)
@EntityInsertOperation(enabled = Kleenean.FALSE)
@EntityUpdateOperation(enabled = Kleenean.FALSE)
@EntityDeleteOperation(enabled = Kleenean.FALSE)
@EntityTableView(enabled = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.FALSE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
@EntityReferenceDisplay(filterType = EntityReferenceFilterType.LIST)
@EntityReferenceSearch(listStyle = ListStyle.CHARACTER_KEY)
public class CondicionEjeFun extends AbstractPersistentEnumerationEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public CondicionEjeFun(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public IntegerProperty numero;

    @BusinessKey
    public StringProperty codigo;

    @NameProperty
    public StringProperty nombre;

    @ColumnField(nullable = Kleenean.FALSE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    public SeveridadMensaje severidadMensaje;

    public Instance EJECUCION_PENDIENTE;

    public Instance EJECUCION_EN_PROGRESO;

    public Instance EJECUTADO_SIN_ERRORES;

    public Instance EJECUTADO_CON_ERRORES;

    public Instance EJECUCION_CANCELADA;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of CondicionEjeFun's attributes">
        setLocalizedLabel(ENGLISH, "function execution condition");
        setLocalizedLabel(SPANISH, "condición de ejecución de función");
        setLocalizedShortLabel(ENGLISH, "condition");
        setLocalizedShortLabel(SPANISH, "condición");
        setLocalizedCollectionLabel(ENGLISH, "Function Execution Conditions");
        setLocalizedCollectionLabel(SPANISH, "Condiciones de Ejecución de Función");
        setLocalizedCollectionShortLabel(ENGLISH, "Conditions");
        setLocalizedCollectionShortLabel(SPANISH, "Condiciones");
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        /**/
        severidadMensaje.setDefaultValue(severidadMensaje.INFORMATIVO);
        severidadMensaje.setInitialValue(severidadMensaje.INFORMATIVO);
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of CondicionEjeFun's properties">
        /**/
        numero.setLocalizedLabel(ENGLISH, "function execution condition number");
        numero.setLocalizedLabel(SPANISH, "número de la condición de ejecución de función");
        numero.setLocalizedShortLabel(ENGLISH, "number");
        numero.setLocalizedShortLabel(SPANISH, "número");
        /**/
        codigo.setLocalizedLabel(ENGLISH, "function execution condition code");
        codigo.setLocalizedLabel(SPANISH, "código de la condición de ejecución de función");
        codigo.setLocalizedShortLabel(ENGLISH, "code");
        codigo.setLocalizedShortLabel(SPANISH, "código");
        /**/
        severidadMensaje.setLocalizedLabel(ENGLISH, "message severity");
        severidadMensaje.setLocalizedLabel(SPANISH, "severidad de mensaje");
        severidadMensaje.setLocalizedShortLabel(ENGLISH, "severity");
        severidadMensaje.setLocalizedShortLabel(SPANISH, "severidad");
        /**/
 /**/
        // </editor-fold>
    }

    @Override
    protected void settleInstances() {
        super.settleInstances();
        /**/
        EJECUCION_PENDIENTE.setCustomTag(InstanceTag.GREENISH.copy().setLetterCase(LetterCase.UNSPECIFIED).setPillShaped(true));
        EJECUCION_EN_PROGRESO.setCustomTag(InstanceTag.GREEN.copy().setLetterCase(LetterCase.UNSPECIFIED).setPillShaped(true));
        EJECUTADO_SIN_ERRORES.setCustomTag(InstanceTag.LIGHT_GREEN.copy().setLetterCase(LetterCase.UNSPECIFIED).setPillShaped(true));
        EJECUTADO_CON_ERRORES.setCustomTag(InstanceTag.RED.copy().setLetterCase(LetterCase.UNSPECIFIED).setPillShaped(true));
        EJECUCION_CANCELADA.setCustomTag(InstanceTag.RED.copy().setLetterCase(LetterCase.UPPER).setPillShaped(true));
        /**/
        EJECUCION_PENDIENTE.newInstanceField(numero, 11);
        EJECUCION_PENDIENTE.newInstanceField(severidadMensaje, severidadMensaje.INFORMATIVO);
        /**/
        EJECUCION_EN_PROGRESO.newInstanceField(numero, 12);
        EJECUCION_EN_PROGRESO.newInstanceField(severidadMensaje, severidadMensaje.INFORMATIVO);
        /**/
        EJECUTADO_SIN_ERRORES.newInstanceField(numero, 21);
        EJECUTADO_SIN_ERRORES.newInstanceField(severidadMensaje, severidadMensaje.INFORMATIVO);
        /**/
        EJECUTADO_CON_ERRORES.newInstanceField(numero, 22);
        EJECUTADO_CON_ERRORES.newInstanceField(severidadMensaje, severidadMensaje.ERROR);
        /**/
        EJECUCION_CANCELADA.newInstanceField(numero, 23);
        EJECUCION_CANCELADA.newInstanceField(severidadMensaje, severidadMensaje.FATAL);
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of CondicionEjeFun's instances">
        /**/
        EJECUCION_PENDIENTE.newInstanceField(codigo, "Pending", ENGLISH);
        EJECUCION_PENDIENTE.newInstanceField(codigo, "Pendiente", SPANISH);
        /**/
        EJECUCION_EN_PROGRESO.newInstanceField(codigo, "Running", ENGLISH);
        EJECUCION_EN_PROGRESO.newInstanceField(codigo, "Ejecutando", SPANISH);
        /**/
        EJECUTADO_SIN_ERRORES.newInstanceField(codigo, "Without errors", ENGLISH);
        EJECUTADO_SIN_ERRORES.newInstanceField(codigo, "Sin errores", SPANISH);
        /**/
        EJECUTADO_CON_ERRORES.newInstanceField(codigo, "With errors", ENGLISH);
        EJECUTADO_CON_ERRORES.newInstanceField(codigo, "Con errores", SPANISH);
        /**/
        EJECUCION_CANCELADA.newInstanceField(codigo, "Cancelled", ENGLISH);
        EJECUCION_CANCELADA.newInstanceField(codigo, "Cancelada", SPANISH);
        /**/
        EJECUCION_PENDIENTE.newInstanceField(nombre, "Pending execution", ENGLISH);
        EJECUCION_PENDIENTE.newInstanceField(nombre, "Ejecución pendiente", SPANISH);
        /**/
        EJECUCION_EN_PROGRESO.newInstanceField(nombre, "Execution in progress", ENGLISH);
        EJECUCION_EN_PROGRESO.newInstanceField(nombre, "Ejecución en progreso", SPANISH);
        /**/
        EJECUTADO_SIN_ERRORES.newInstanceField(nombre, "Finished without errors", ENGLISH);
        EJECUTADO_SIN_ERRORES.newInstanceField(nombre, "Ejecutado sin errores", SPANISH);
        /**/
        EJECUTADO_CON_ERRORES.newInstanceField(nombre, "Finished with errors", ENGLISH);
        EJECUTADO_CON_ERRORES.newInstanceField(nombre, "Ejecutado con errores", SPANISH);
        /**/
        EJECUCION_CANCELADA.newInstanceField(nombre, "Execution canceled", ENGLISH);
        EJECUCION_CANCELADA.newInstanceField(nombre, "Ejecución cancelada", SPANISH);
        /**/
        // </editor-fold>
    }

}
