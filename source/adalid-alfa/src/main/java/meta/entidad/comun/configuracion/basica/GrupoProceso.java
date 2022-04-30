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
import adalid.jee2.constants.*;
import java.lang.reflect.Field;

/**
 * @author Jorge Campins
 */
@EntityClass(catalog = Kleenean.TRUE, independent = Kleenean.TRUE, resourceType = ResourceType.OPERATION, resourceGender = ResourceGender.MASCULINE)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE, access = OperationAccess.PUBLIC, rowsLimit = 0)
@EntityInsertOperation(enabled = Kleenean.FALSE)
@EntityUpdateOperation(enabled = Kleenean.FALSE)
@EntityDeleteOperation(enabled = Kleenean.FALSE)
@EntityTableView(enabled = Kleenean.TRUE)
@EntityDetailView(enabled = Kleenean.TRUE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
public class GrupoProceso extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public GrupoProceso(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Override
    public ViewMenuOption getTableViewMenuOption() {
        return TLC.getProject().getProcessingGroups().isEmpty() ? ViewMenuOption.NONE : ViewMenuOption.ALL;
    }

    @PrimaryKey
    public LongProperty id;

    @VersionProperty
    public LongProperty version;

    @BusinessKey
    public StringProperty codigoGrupoProceso;

    @NameProperty
    @PropertyField(hidden = Kleenean.TRUE)
    public StringProperty nombreGrupoProceso;

    @DescriptionProperty
    @PropertyField(hidden = Kleenean.TRUE)
    @StringField(maxLength = 0)
    public StringProperty descripcionGrupoProceso;

    @PropertyField(hidden = Kleenean.TRUE)
    public LongProperty idRastroProceso;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE)
    public CondicionEjeFun condicionEjeFun;

    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE)
    public TimestampProperty fechaHoraInicioEjecucion;

    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE)
    public TimestampProperty fechaHoraFinEjecucion;

    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE)
    @StringField(maxLength = 200)
    public StringProperty codigoFuncion;

    @PropertyField(table = Kleenean.TRUE, report = Kleenean.FALSE)
    @StringField(maxLength = 200)
    public StringProperty nombreFuncion;

    protected Cancelar cancelar;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        /**/
        setLinkOuterChildren(true);
        setLinkOuterCollaterals(true);
        setOrderBy(codigoGrupoProceso);
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of GrupoProceso's attributes">
        setLocalizedLabel(ENGLISH, "process group");
        setLocalizedLabel(SPANISH, "grupo de procesos");
        setLocalizedShortLabel(ENGLISH, "group");
        setLocalizedShortLabel(SPANISH, "grupo");
        setLocalizedCollectionLabel(ENGLISH, "Process Groups");
        setLocalizedCollectionLabel(SPANISH, "Grupos de Procesos");
        setLocalizedCollectionShortLabel(ENGLISH, "Groups");
        setLocalizedCollectionShortLabel(SPANISH, "Grupos");
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        /**/
        setGraphicImageExpressions(condicionEjeFun);
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of GrupoProceso's properties">
        /**/
        codigoGrupoProceso.setLocalizedLabel(ENGLISH, "process group code");
        codigoGrupoProceso.setLocalizedLabel(SPANISH, "código del grupo de procesos");
        codigoGrupoProceso.setLocalizedShortLabel(ENGLISH, "code");
        codigoGrupoProceso.setLocalizedShortLabel(SPANISH, "código");
        /**/
        nombreGrupoProceso.setLocalizedLabel(ENGLISH, "process group name");
        nombreGrupoProceso.setLocalizedLabel(SPANISH, "nombre del grupo de procesos");
        nombreGrupoProceso.setLocalizedShortLabel(ENGLISH, "name");
        nombreGrupoProceso.setLocalizedShortLabel(SPANISH, "nombre");
        /**/
        descripcionGrupoProceso.setLocalizedLabel(ENGLISH, "process group description");
        descripcionGrupoProceso.setLocalizedLabel(SPANISH, "descripción del grupo de procesos");
        descripcionGrupoProceso.setLocalizedShortLabel(ENGLISH, "description");
        descripcionGrupoProceso.setLocalizedShortLabel(SPANISH, "descripción");
        /**/
        idRastroProceso.setLocalizedLabel(ENGLISH, "process trail");
        idRastroProceso.setLocalizedLabel(SPANISH, "rastro de proceso");
        /**/
        condicionEjeFun.setLocalizedLabel(ENGLISH, "function execution condition");
        condicionEjeFun.setLocalizedLabel(SPANISH, "condición de ejecución de función");
        condicionEjeFun.setLocalizedShortLabel(ENGLISH, "condition");
        condicionEjeFun.setLocalizedShortLabel(SPANISH, "condición");
        /**/
        fechaHoraInicioEjecucion.setLocalizedLabel(ENGLISH, "start");
        fechaHoraInicioEjecucion.setLocalizedLabel(SPANISH, "inicio");
        /**/
        fechaHoraFinEjecucion.setLocalizedLabel(ENGLISH, "end");
        fechaHoraFinEjecucion.setLocalizedLabel(SPANISH, "fin");
        /**/
        codigoFuncion.setLocalizedLabel(ENGLISH, "function");
        codigoFuncion.setLocalizedLabel(SPANISH, "función");
        /**/
        nombreFuncion.setLocalizedLabel(ENGLISH, "function name");
        nombreFuncion.setLocalizedLabel(SPANISH, "nombre de la función");
        /**/
        // </editor-fold>
    }

    void setGraphicImageExpressions(CondicionEjeFun condicionEjeFun) {
        final String NULL = fa(FA.NULL_VALUE + FA.WITH_FIXED_WIDTH + CSS.STATUS_NULL_VALUE_IMAGE);
        final String fa1x = fa(W3.TEXT_DEEP_ORANGE + FA.EXCLAMATION_CIRCLE + FA.WITH_SIZE_LG);
        final String fa2x = fa(W3.TEXT_GREEN + FA.CHECK_CIRCLE + FA.WITH_SIZE_LG);
        final String fa99 = fa(W3.TEXT_RED + FA.EXCLAMATION_CIRCLE + FA.WITH_SIZE_LG);
        final BooleanExpression nil = condicionEjeFun.isNull();
        final BooleanExpression c11 = condicionEjeFun.isEqualTo(condicionEjeFun.EJECUCION_PENDIENTE);
        final BooleanExpression c12 = condicionEjeFun.isEqualTo(condicionEjeFun.EJECUCION_EN_PROGRESO);
        final BooleanExpression c21 = condicionEjeFun.isEqualTo(condicionEjeFun.EJECUTADO_SIN_ERRORES);
        final BooleanExpression c22 = condicionEjeFun.isEqualTo(condicionEjeFun.EJECUTADO_CON_ERRORES);
        final BooleanExpression c23 = condicionEjeFun.isEqualTo(condicionEjeFun.EJECUCION_CANCELADA);
        final CharacterExpression expression = nil.then(NULL).
            otherwise(or(c11, c12).then(fa1x).
                otherwise(or(c21, c22, c23).then(fa2x).
                    otherwise(fa99)));
        /**/
        condicionEjeFun.setGraphicImageFontAwesomeClassNameExpression(expression);
        /**/
        condicionEjeFun.setLocalizedGraphicImageTooltip(ENGLISH, NULL, "unspecified value");
        condicionEjeFun.setLocalizedGraphicImageTooltip(SPANISH, NULL, "valor no especificado");
        condicionEjeFun.setLocalizedGraphicImageTooltip(ENGLISH, fa1x, "unfinished process that is locking the group");
        condicionEjeFun.setLocalizedGraphicImageTooltip(SPANISH, fa1x, "proceso inconcluso que está bloqueando el grupo");
        condicionEjeFun.setLocalizedGraphicImageTooltip(ENGLISH, fa2x, "finished process");
        condicionEjeFun.setLocalizedGraphicImageTooltip(SPANISH, fa2x, "proceso finalizado");
        condicionEjeFun.setLocalizedGraphicImageTooltip(ENGLISH, fa99, "unexpected value");
        condicionEjeFun.setLocalizedGraphicImageTooltip(SPANISH, fa99, "valor inesperado");
        /**/
    }

    // <editor-fold defaultstate="collapsed" desc="Operations">
    @OperationClass(confirmation = Kleenean.TRUE)
    @ProcessOperationClass
    public class Cancelar extends ProcessOperation {

        @InstanceReference
        protected GrupoProceso grupoProceso;

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of Cancelar's attributes">
            /**/
            setLocalizedLabel(ENGLISH, "unlock");
            setLocalizedLabel(SPANISH, "desbloquear");
            /**/
            setLocalizedDescription(ENGLISH, "cancel the lock set by the last process");
            setLocalizedDescription(SPANISH, "cancelar el bloqueo establecido por el último proceso");
            /**/
            setLocalizedSuccessMessage(ENGLISH, "the lock set by the last process was canceled");
            setLocalizedSuccessMessage(SPANISH, "el bloqueo establecido por el último proceso fue cancelado");
            /**/
            // </editor-fold>
        }

        @Override
        protected void settleParameters() {
            super.settleParameters();
            // <editor-fold defaultstate="collapsed" desc="localization of Cancelar's parameters">
            /**/
            grupoProceso.setLocalizedLabel(ENGLISH, "process group");
            grupoProceso.setLocalizedLabel(SPANISH, "grupo de procesos");
            grupoProceso.setLocalizedShortLabel(ENGLISH, "group");
            grupoProceso.setLocalizedShortLabel(SPANISH, "grupo");
            /**/
            // </editor-fold>
        }

    }
    // </editor-fold>

}
