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
package meta.entidad.comun.configuracion.basica.ext;

import adalid.core.*;
import adalid.core.annotations.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import adalid.core.properties.*;
import java.lang.reflect.Field;
import meta.entidad.comun.configuracion.basica.GrupoProceso;
import meta.entidad.comun.configuracion.basica.TipoFuncion;
import meta.entidad.comun.configuracion.basica.TipoRastroFun;

/**
 * @author Jorge Campins
 */
@EntityClass(catalog = Kleenean.TRUE, independent = Kleenean.TRUE, resourceType = ResourceType.CONFIGURATION, resourceGender = ResourceGender.FEMININE)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE, access = OperationAccess.PUBLIC, onload = SelectOnloadOption.EXECUTE, rowsLimit = 500)
@EntityInsertOperation(enabled = Kleenean.FALSE)
@EntityUpdateOperation(enabled = Kleenean.FALSE)
@EntityDeleteOperation(enabled = Kleenean.FALSE)
@EntityTableView(enabled = Kleenean.TRUE, menu = ViewMenuOption.READING, quickFilter = Kleenean.TRUE)
@EntityDetailView(enabled = Kleenean.TRUE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
@EntityReferenceDisplay(style = EntityReferenceStyle.NAME_AND_CHARACTER_KEY)
public class Funcion extends meta.entidad.comun.configuracion.basica.Funcion {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public Funcion(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(overlay = Kleenean.TRUE)
    public TipoFuncion tipoFuncion;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(overlay = Kleenean.TRUE)
    public TipoRastroFun tipoRastroFun;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(hidden = Kleenean.TRUE)
    public Dominio dominio;

    @ColumnField(calculable = Kleenean.TRUE)
//  @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(search = Kleenean.TRUE, table = Kleenean.TRUE, report = Kleenean.TRUE, export = Kleenean.TRUE, overlay = Kleenean.TRUE)
    public ClaseRecurso clase;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.TRUE)
    public ClaseRecurso rango;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.CASCADE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(overlay = Kleenean.TRUE)
    public GrupoProceso grupoProceso;

    @ColumnField(nullable = Kleenean.FALSE)
    public IntegerProperty transiciones;

    @ColumnField(nullable = Kleenean.TRUE)
    public IntegerProperty minutos;

    @ColumnField(nullable = Kleenean.TRUE)
    public IntegerProperty prioridad;

    @PropertyField(hidden = Kleenean.TRUE)
    public IntegerProperty minutosNotificar;

    @ColumnField(nullable = Kleenean.TRUE)
    public IntegerProperty cantidadNotificar;

    @ColumnField(nullable = Kleenean.TRUE)
    @StringField(maxLength = 10)
    @PropertyField(anchor = "cantidadNotificar", anchorType = AnchorType.INLINE)
    public StringProperty unidadNotificar;

    @ColumnField(nullable = Kleenean.TRUE)
    public BooleanProperty notificarSupervisor;

    @ColumnField(nullable = Kleenean.TRUE)
    public BooleanProperty notificarAsignacion;

    @ColumnField(nullable = Kleenean.TRUE)
    public BooleanProperty notificarAsuncion;

    @ColumnField(nullable = Kleenean.TRUE)
    public BooleanProperty notificarRelevacion;

    @ColumnField(nullable = Kleenean.TRUE)
    public BooleanProperty notificarAbandono;

    @ColumnField(nullable = Kleenean.TRUE)
    public BooleanProperty notificarCancelacion;

    @ColumnField(nullable = Kleenean.TRUE)
    public BooleanProperty notificarFinalizacion;

    @ColumnField(nullable = Kleenean.TRUE)
    public BooleanProperty asuncionAutomatica;

    @PropertyField(hidden = Kleenean.TRUE)
    public IntegerProperty minutosAdvertirAsignar1;

    @ColumnField(nullable = Kleenean.TRUE)
    public IntegerProperty cantidadAdvertirAsignar1;

    @ColumnField(nullable = Kleenean.TRUE)
    @StringField(maxLength = 10)
    @PropertyField(anchor = "cantidadAdvertirAsignar1", anchorType = AnchorType.INLINE)
    public StringProperty unidadAdvertirAsignar1;

    @PropertyField(hidden = Kleenean.TRUE)
    public IntegerProperty minutosAdvertirFinalizar1;

    @ColumnField(nullable = Kleenean.TRUE)
    public IntegerProperty cantidadAdvertirFinalizar1;

    @ColumnField(nullable = Kleenean.TRUE)
    @StringField(maxLength = 10)
    @PropertyField(anchor = "cantidadAdvertirFinalizar1", anchorType = AnchorType.INLINE)
    public StringProperty unidadAdvertirFinalizar1;

    @PropertyField(hidden = Kleenean.TRUE)
    public IntegerProperty minutosEscalarAsignar1;

    @ColumnField(nullable = Kleenean.TRUE)
    public IntegerProperty cantidadEscalarAsignar1;

    @ColumnField(nullable = Kleenean.TRUE)
    @StringField(maxLength = 10)
    @PropertyField(anchor = "cantidadEscalarAsignar1", anchorType = AnchorType.INLINE)
    public StringProperty unidadEscalarAsignar1;

    @PropertyField(hidden = Kleenean.TRUE)
    public IntegerProperty minutosEscalarFinalizar1;

    @ColumnField(nullable = Kleenean.TRUE)
    public IntegerProperty cantidadEscalarFinalizar1;

    @ColumnField(nullable = Kleenean.TRUE)
    @StringField(maxLength = 10)
    @PropertyField(anchor = "cantidadEscalarFinalizar1", anchorType = AnchorType.INLINE)
    public StringProperty unidadEscalarFinalizar1;

    @PropertyField(hidden = Kleenean.TRUE)
    public IntegerProperty minutosAdvertirAsignar2;

    @ColumnField(nullable = Kleenean.TRUE)
    public IntegerProperty cantidadAdvertirAsignar2;

    @ColumnField(nullable = Kleenean.TRUE)
    @StringField(maxLength = 10)
    @PropertyField(anchor = "cantidadAdvertirAsignar2", anchorType = AnchorType.INLINE)
    public StringProperty unidadAdvertirAsignar2;

    @PropertyField(hidden = Kleenean.TRUE)
    public IntegerProperty minutosAdvertirFinalizar2;

    @ColumnField(nullable = Kleenean.TRUE)
    public IntegerProperty cantidadAdvertirFinalizar2;

    @ColumnField(nullable = Kleenean.TRUE)
    @StringField(maxLength = 10)
    @PropertyField(anchor = "cantidadAdvertirFinalizar2", anchorType = AnchorType.INLINE)
    public StringProperty unidadAdvertirFinalizar2;

    @PropertyField(hidden = Kleenean.TRUE)
    public IntegerProperty minutosEscalarAsignar2;

    @ColumnField(nullable = Kleenean.TRUE)
    public IntegerProperty cantidadEscalarAsignar2;

    @ColumnField(nullable = Kleenean.TRUE)
    @StringField(maxLength = 10)
    @PropertyField(anchor = "cantidadEscalarAsignar2", anchorType = AnchorType.INLINE)
    public StringProperty unidadEscalarAsignar2;

    @PropertyField(hidden = Kleenean.TRUE)
    public IntegerProperty minutosEscalarFinalizar2;

    @ColumnField(nullable = Kleenean.TRUE)
    public IntegerProperty cantidadEscalarFinalizar2;

    @ColumnField(nullable = Kleenean.TRUE)
    @StringField(maxLength = 10)
    @PropertyField(anchor = "cantidadEscalarFinalizar2", anchorType = AnchorType.INLINE)
    public StringProperty unidadEscalarFinalizar2;

    @Override
    protected void addAllocationStrings() {
        super.addAllocationStrings();
        super.addAllocationStrings(
            "dominio.claseRecurso"
        );
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        /**/
        esPublica.setInitialValue(false);
        esPublica.setDefaultValue(false);
        esProgramatica.setInitialValue(false);
        esProgramatica.setDefaultValue(false);
        esProtegida.setInitialValue(false);
        esProtegida.setDefaultValue(false);
        esPersonalizable.setInitialValue(false);
        esPersonalizable.setDefaultValue(false);
        esSegmentable.setInitialValue(false);
        esSegmentable.setDefaultValue(false);
        esSupervisable.setInitialValue(false);
        esSupervisable.setDefaultValue(false);
        esConstructor.setInitialValue(false);
        esConstructor.setDefaultValue(false);
        esHeredada.setInitialValue(false);
        esHeredada.setDefaultValue(false);
        /**/
        tipoRastroFun.setInitialValue(tipoRastroFun.NINGUNO);
        tipoRastroFun.setDefaultValue(tipoRastroFun.NINGUNO);
        /**/
        transiciones.setDefaultValue(0);
        transiciones.setInitialValue(0);
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of Funcion's properties">
        /**/
        codigoFuncion.setLocalizedLabel(ENGLISH, "function code");
        codigoFuncion.setLocalizedLabel(SPANISH, "código de la función");
        codigoFuncion.setLocalizedShortLabel(ENGLISH, "code");
        codigoFuncion.setLocalizedShortLabel(SPANISH, "código");
        /**/
        nombreFuncion.setLocalizedLabel(ENGLISH, "function name");
        nombreFuncion.setLocalizedLabel(SPANISH, "nombre de la función");
        nombreFuncion.setLocalizedShortLabel(ENGLISH, "name");
        nombreFuncion.setLocalizedShortLabel(SPANISH, "nombre");
        /**/
        nombreJava.setLocalizedLabel(ENGLISH, "java name");
        nombreJava.setLocalizedLabel(SPANISH, "nombre java");
        /**/
        nombreSql.setLocalizedLabel(ENGLISH, "sql name");
        nombreSql.setLocalizedLabel(SPANISH, "nombre sql");
        /**/
        descripcionFuncion.setLocalizedLabel(ENGLISH, "function description");
        descripcionFuncion.setLocalizedLabel(SPANISH, "descripción de la función");
        descripcionFuncion.setLocalizedShortLabel(ENGLISH, "description");
        descripcionFuncion.setLocalizedShortLabel(SPANISH, "descripción");
        /**/
        clausulaWhere.setLocalizedLabel(ENGLISH, "where clause");
        clausulaWhere.setLocalizedLabel(SPANISH, "clausula where");
        /**/
        clausulaOrder.setLocalizedLabel(ENGLISH, "order clause");
        clausulaOrder.setLocalizedLabel(SPANISH, "clausula order");
        /**/
        esPublica.setLocalizedLabel(ENGLISH, "public");
        esPublica.setLocalizedLabel(SPANISH, "pública");
        /**/
        esProgramatica.setLocalizedLabel(ENGLISH, "programmatic");
        esProgramatica.setLocalizedLabel(SPANISH, "programática");
        /**/
        esProtegida.setLocalizedLabel(ENGLISH, "protected");
        esProtegida.setLocalizedLabel(SPANISH, "protegida");
        /**/
        esPersonalizable.setLocalizedLabel(ENGLISH, "personalizable");
        esPersonalizable.setLocalizedLabel(SPANISH, "personalizable");
        /**/
        esSegmentable.setLocalizedLabel(ENGLISH, "segmentable");
        esSegmentable.setLocalizedLabel(SPANISH, "segmentable");
        /**/
        esSupervisable.setLocalizedLabel(ENGLISH, "supervisable");
        esSupervisable.setLocalizedLabel(SPANISH, "supervisable");
        /**/
        esConstructor.setLocalizedLabel(ENGLISH, "constructor");
        esConstructor.setLocalizedLabel(SPANISH, "constructor");
        /**/
        esHeredada.setLocalizedLabel(ENGLISH, "inherited");
        esHeredada.setLocalizedLabel(SPANISH, "heredada");
        /**/
        tipoFuncion.setLocalizedLabel(ENGLISH, "function type");
        tipoFuncion.setLocalizedLabel(SPANISH, "tipo de función");
        tipoFuncion.setLocalizedShortLabel(ENGLISH, "type");
        tipoFuncion.setLocalizedShortLabel(SPANISH, "tipo");
        /**/
        tipoRastroFun.setLocalizedLabel(ENGLISH, "function trail type");
        tipoRastroFun.setLocalizedLabel(SPANISH, "tipo de rastro de función");
        tipoRastroFun.setLocalizedShortLabel(ENGLISH, "function trail type");
        tipoRastroFun.setLocalizedShortLabel(SPANISH, "tipo de rastro");
        /**/
        dominio.setLocalizedLabel(ENGLISH, "domain");
        dominio.setLocalizedLabel(SPANISH, "dominio");
        /**/
        clase.setLocalizedLabel(ENGLISH, "resource class");
        clase.setLocalizedLabel(SPANISH, "clase de recurso");
        clase.setLocalizedShortLabel(ENGLISH, "class");
        clase.setLocalizedShortLabel(SPANISH, "clase");
        /**/
        rango.setLocalizedLabel(ENGLISH, "range");
        rango.setLocalizedLabel(SPANISH, "rango");
        /**/
        rango.nombreClaseRecurso.setLocalizedLabel(ENGLISH, "range name");
        rango.nombreClaseRecurso.setLocalizedLabel(SPANISH, "nombre del rango");
        /**/
        grupoProceso.setLocalizedLabel(ENGLISH, "process group");
        grupoProceso.setLocalizedLabel(SPANISH, "grupo de procesos");
        grupoProceso.setLocalizedShortLabel(ENGLISH, "group");
        grupoProceso.setLocalizedShortLabel(SPANISH, "grupo");
        /**/
        transiciones.setLocalizedLabel(ENGLISH, "transition count");
        transiciones.setLocalizedLabel(SPANISH, "transiciones");
        /**/
        minutos.setLocalizedLabel(ENGLISH, "task duration in minutes");
        minutos.setLocalizedLabel(SPANISH, "duración de la tarea en minutos");
        /**/
        prioridad.setLocalizedLabel(ENGLISH, "priority");
        prioridad.setLocalizedLabel(SPANISH, "prioridad");
        /**/
        minutosNotificar.setLocalizedLabel(ENGLISH, "recipient notification minutes");
        minutosNotificar.setLocalizedLabel(SPANISH, "minutos notificación destinatario");
        /**/
        cantidadNotificar.setLocalizedLabel(ENGLISH, "recipient notification");
        cantidadNotificar.setLocalizedLabel(SPANISH, "notificación destinatario");
        /**/
        unidadNotificar.setLocalizedLabel(ENGLISH, "recipient notification unit");
        unidadNotificar.setLocalizedLabel(SPANISH, "unidad notificación destinatario");
        /**/
        notificarSupervisor.setLocalizedLabel(ENGLISH, "notify supervisor");
        notificarSupervisor.setLocalizedLabel(SPANISH, "notificar supervisor");
        /**/
        notificarAsignacion.setLocalizedLabel(ENGLISH, "notify assignment");
        notificarAsignacion.setLocalizedLabel(SPANISH, "notificar asignación");
        /**/
        notificarAsuncion.setLocalizedLabel(ENGLISH, "notify assumption");
        notificarAsuncion.setLocalizedLabel(SPANISH, "notificar asunción");
        /**/
        notificarRelevacion.setLocalizedLabel(ENGLISH, "notify relief");
        notificarRelevacion.setLocalizedLabel(SPANISH, "notificar relevación");
        /**/
        notificarAbandono.setLocalizedLabel(ENGLISH, "notify abandonment");
        notificarAbandono.setLocalizedLabel(SPANISH, "notificar abandono");
        /**/
        notificarCancelacion.setLocalizedLabel(ENGLISH, "notify cancellation");
        notificarCancelacion.setLocalizedLabel(SPANISH, "notificar cancelación");
        /**/
        notificarFinalizacion.setLocalizedLabel(ENGLISH, "notify completion");
        notificarFinalizacion.setLocalizedLabel(SPANISH, "notificar finalización");
        /**/
        asuncionAutomatica.setLocalizedLabel(ENGLISH, "automatic assumption");
        asuncionAutomatica.setLocalizedLabel(SPANISH, "asunción automática");
        /**/
        minutosAdvertirAsignar1.setLocalizedLabel(ENGLISH, "first assignment reminder minutes");
        minutosAdvertirAsignar1.setLocalizedLabel(SPANISH, "minutos primer recordatorio asignación");
        /**/
        cantidadAdvertirAsignar1.setLocalizedLabel(ENGLISH, "first assignment reminder");
        cantidadAdvertirAsignar1.setLocalizedLabel(SPANISH, "primer recordatorio asignación");
        /**/
        unidadAdvertirAsignar1.setLocalizedLabel(ENGLISH, "first assignment reminder unit");
        unidadAdvertirAsignar1.setLocalizedLabel(SPANISH, "unidad primer recordatorio asignación");
        /**/
        minutosAdvertirFinalizar1.setLocalizedLabel(ENGLISH, "first execution reminder minutes");
        minutosAdvertirFinalizar1.setLocalizedLabel(SPANISH, "minutos primer recordatorio finalización");
        /**/
        cantidadAdvertirFinalizar1.setLocalizedLabel(ENGLISH, "first execution reminder");
        cantidadAdvertirFinalizar1.setLocalizedLabel(SPANISH, "primer recordatorio finalización");
        /**/
        unidadAdvertirFinalizar1.setLocalizedLabel(ENGLISH, "first execution reminder unit");
        unidadAdvertirFinalizar1.setLocalizedLabel(SPANISH, "unidad primer recordatorio finalización");
        /**/
        minutosEscalarAsignar1.setLocalizedLabel(ENGLISH, "first assignment escalation minutes");
        minutosEscalarAsignar1.setLocalizedLabel(SPANISH, "minutos primer escalamiento asignación");
        /**/
        cantidadEscalarAsignar1.setLocalizedLabel(ENGLISH, "first assignment escalation");
        cantidadEscalarAsignar1.setLocalizedLabel(SPANISH, "primer escalamiento asignación");
        /**/
        unidadEscalarAsignar1.setLocalizedLabel(ENGLISH, "first assignment escalation unit");
        unidadEscalarAsignar1.setLocalizedLabel(SPANISH, "unidad primer escalamiento asignación");
        /**/
        minutosEscalarFinalizar1.setLocalizedLabel(ENGLISH, "first execution escalation minutes");
        minutosEscalarFinalizar1.setLocalizedLabel(SPANISH, "minutos primer escalamiento finalización");
        /**/
        cantidadEscalarFinalizar1.setLocalizedLabel(ENGLISH, "first execution escalation");
        cantidadEscalarFinalizar1.setLocalizedLabel(SPANISH, "primer escalamiento finalización");
        /**/
        unidadEscalarFinalizar1.setLocalizedLabel(ENGLISH, "first execution escalation unit");
        unidadEscalarFinalizar1.setLocalizedLabel(SPANISH, "unidad primer escalamiento finalización");
        /**/
        minutosAdvertirAsignar2.setLocalizedLabel(ENGLISH, "next assignment reminder minutes");
        minutosAdvertirAsignar2.setLocalizedLabel(SPANISH, "minutos siguiente recordatorio asignación");
        /**/
        cantidadAdvertirAsignar2.setLocalizedLabel(ENGLISH, "next assignment reminder");
        cantidadAdvertirAsignar2.setLocalizedLabel(SPANISH, "siguiente recordatorio asignación");
        /**/
        unidadAdvertirAsignar2.setLocalizedLabel(ENGLISH, "next assignment reminder unit");
        unidadAdvertirAsignar2.setLocalizedLabel(SPANISH, "unidad siguiente recordatorio asignación");
        /**/
        minutosAdvertirFinalizar2.setLocalizedLabel(ENGLISH, "next execution reminder minutes");
        minutosAdvertirFinalizar2.setLocalizedLabel(SPANISH, "minutos siguiente recordatorio finalización");
        /**/
        cantidadAdvertirFinalizar2.setLocalizedLabel(ENGLISH, "next execution reminder");
        cantidadAdvertirFinalizar2.setLocalizedLabel(SPANISH, "siguiente recordatorio finalización");
        /**/
        unidadAdvertirFinalizar2.setLocalizedLabel(ENGLISH, "next execution reminder unit");
        unidadAdvertirFinalizar2.setLocalizedLabel(SPANISH, "unidad siguiente recordatorio finalización");
        /**/
        minutosEscalarAsignar2.setLocalizedLabel(ENGLISH, "next assignment escalation minutes");
        minutosEscalarAsignar2.setLocalizedLabel(SPANISH, "minutos siguiente escalamiento asignación");
        /**/
        cantidadEscalarAsignar2.setLocalizedLabel(ENGLISH, "next assignment escalation");
        cantidadEscalarAsignar2.setLocalizedLabel(SPANISH, "siguiente escalamiento asignación");
        /**/
        unidadEscalarAsignar2.setLocalizedLabel(ENGLISH, "next assignment escalation unit");
        unidadEscalarAsignar2.setLocalizedLabel(SPANISH, "unidad siguiente escalamiento asignación");
        /**/
        minutosEscalarFinalizar2.setLocalizedLabel(ENGLISH, "next execution escalation minutes");
        minutosEscalarFinalizar2.setLocalizedLabel(SPANISH, "minutos siguiente escalamiento finalización");
        /**/
        cantidadEscalarFinalizar2.setLocalizedLabel(ENGLISH, "next execution escalation");
        cantidadEscalarFinalizar2.setLocalizedLabel(SPANISH, "siguiente escalamiento finalización");
        /**/
        unidadEscalarFinalizar2.setLocalizedLabel(ENGLISH, "next execution escalation unit");
        unidadEscalarFinalizar2.setLocalizedLabel(SPANISH, "unidad siguiente escalamiento finalización");
        /**/
        cantidadNotificar.setLocalizedAnchorLabel(ENGLISH, "recipient notification");
        cantidadNotificar.setLocalizedAnchorLabel(SPANISH, "notificación destinatario");
        /**/
        cantidadNotificar.setLocalizedAnchoredLabel(ENGLISH, "quantity");
        cantidadNotificar.setLocalizedAnchoredLabel(SPANISH, "cantidad");
        /**/
        unidadNotificar.setLocalizedAnchoredLabel(ENGLISH, "unit");
        unidadNotificar.setLocalizedAnchoredLabel(SPANISH, "unidad");
        /**/
        cantidadAdvertirAsignar1.setLocalizedAnchorLabel(ENGLISH, "first assignment reminder");
        cantidadAdvertirAsignar1.setLocalizedAnchorLabel(SPANISH, "primer recordatorio asignación");
        /**/
        cantidadAdvertirAsignar1.setLocalizedAnchoredLabel(ENGLISH, "quantity");
        cantidadAdvertirAsignar1.setLocalizedAnchoredLabel(SPANISH, "cantidad");
        /**/
        unidadAdvertirAsignar1.setLocalizedAnchoredLabel(ENGLISH, "unit");
        unidadAdvertirAsignar1.setLocalizedAnchoredLabel(SPANISH, "unidad");
        /**/
        cantidadAdvertirFinalizar1.setLocalizedAnchorLabel(ENGLISH, "first execution reminder");
        cantidadAdvertirFinalizar1.setLocalizedAnchorLabel(SPANISH, "primer recordatorio finalización");
        /**/
        cantidadAdvertirFinalizar1.setLocalizedAnchoredLabel(ENGLISH, "quantity");
        cantidadAdvertirFinalizar1.setLocalizedAnchoredLabel(SPANISH, "cantidad");
        /**/
        unidadAdvertirFinalizar1.setLocalizedAnchoredLabel(ENGLISH, "unit");
        unidadAdvertirFinalizar1.setLocalizedAnchoredLabel(SPANISH, "unidad");
        /**/
        cantidadEscalarAsignar1.setLocalizedAnchorLabel(ENGLISH, "first assignment escalation");
        cantidadEscalarAsignar1.setLocalizedAnchorLabel(SPANISH, "primer escalamiento asignación");
        /**/
        cantidadEscalarAsignar1.setLocalizedAnchoredLabel(ENGLISH, "quantity");
        cantidadEscalarAsignar1.setLocalizedAnchoredLabel(SPANISH, "cantidad");
        /**/
        unidadEscalarAsignar1.setLocalizedAnchoredLabel(ENGLISH, "unit");
        unidadEscalarAsignar1.setLocalizedAnchoredLabel(SPANISH, "unidad");
        /**/
        cantidadEscalarFinalizar1.setLocalizedAnchorLabel(ENGLISH, "first execution escalation");
        cantidadEscalarFinalizar1.setLocalizedAnchorLabel(SPANISH, "primer escalamiento finalización");
        /**/
        cantidadEscalarFinalizar1.setLocalizedAnchoredLabel(ENGLISH, "quantity");
        cantidadEscalarFinalizar1.setLocalizedAnchoredLabel(SPANISH, "cantidad");
        /**/
        unidadEscalarFinalizar1.setLocalizedAnchoredLabel(ENGLISH, "unit");
        unidadEscalarFinalizar1.setLocalizedAnchoredLabel(SPANISH, "unidad");
        /**/
        cantidadAdvertirAsignar2.setLocalizedAnchorLabel(ENGLISH, "next assignment reminder");
        cantidadAdvertirAsignar2.setLocalizedAnchorLabel(SPANISH, "siguiente recordatorio asignación");
        /**/
        cantidadAdvertirAsignar2.setLocalizedAnchoredLabel(ENGLISH, "quantity");
        cantidadAdvertirAsignar2.setLocalizedAnchoredLabel(SPANISH, "cantidad");
        /**/
        unidadAdvertirAsignar2.setLocalizedAnchoredLabel(ENGLISH, "unit");
        unidadAdvertirAsignar2.setLocalizedAnchoredLabel(SPANISH, "unidad");
        /**/
        cantidadAdvertirFinalizar2.setLocalizedAnchorLabel(ENGLISH, "next execution reminder");
        cantidadAdvertirFinalizar2.setLocalizedAnchorLabel(SPANISH, "siguiente recordatorio finalización");
        /**/
        cantidadAdvertirFinalizar2.setLocalizedAnchoredLabel(ENGLISH, "quantity");
        cantidadAdvertirFinalizar2.setLocalizedAnchoredLabel(SPANISH, "cantidad");
        /**/
        unidadAdvertirFinalizar2.setLocalizedAnchoredLabel(ENGLISH, "unit");
        unidadAdvertirFinalizar2.setLocalizedAnchoredLabel(SPANISH, "unidad");
        /**/
        cantidadEscalarAsignar2.setLocalizedAnchorLabel(ENGLISH, "next assignment escalation");
        cantidadEscalarAsignar2.setLocalizedAnchorLabel(SPANISH, "siguiente escalamiento asignación");
        /**/
        cantidadEscalarAsignar2.setLocalizedAnchoredLabel(ENGLISH, "quantity");
        cantidadEscalarAsignar2.setLocalizedAnchoredLabel(SPANISH, "cantidad");
        /**/
        unidadEscalarAsignar2.setLocalizedAnchoredLabel(ENGLISH, "unit");
        unidadEscalarAsignar2.setLocalizedAnchoredLabel(SPANISH, "unidad");
        /**/
        cantidadEscalarFinalizar2.setLocalizedAnchorLabel(ENGLISH, "next execution escalation");
        cantidadEscalarFinalizar2.setLocalizedAnchorLabel(SPANISH, "siguiente escalamiento finalización");
        /**/
        cantidadEscalarFinalizar2.setLocalizedAnchoredLabel(ENGLISH, "quantity");
        cantidadEscalarFinalizar2.setLocalizedAnchoredLabel(SPANISH, "cantidad");
        /**/
        unidadEscalarFinalizar2.setLocalizedAnchoredLabel(ENGLISH, "unit");
        unidadEscalarFinalizar2.setLocalizedAnchoredLabel(SPANISH, "unidad");
        // </editor-fold>
        /**/
    }

    @Override
    protected void settleLinks() {
        super.settleLinks();
        clase.linkCalculableValueEntityReference(dominio.claseRecurso);
    }

    protected Tab tab110, tab120, tab130, tab140, tab150;

    @Override
    protected void settleTabs() {
        super.settleTabs();
        /**/
        tab110.newTabField(descripcionFuncion, tipoFuncion, tipoRastroFun, dominio, clase, rango, grupoProceso);
        /**/
        tab120.newTabField(nombreJava, nombreSql, clausulaWhere, clausulaOrder, transiciones);
        tab120.newTabField(esPublica, esProgramatica, esProtegida, esPersonalizable, esSegmentable, esSupervisable, esConstructor, esHeredada, esMetodoClase, esMetodoInstancia);
        /**/
        tab130.newTabField(minutos, prioridad);
        tab130.newTabField(asuncionAutomatica);
        tab130.newTabField(minutosNotificar, cantidadNotificar, unidadNotificar);
        tab130.newTabField(notificarSupervisor, notificarAsignacion, notificarAsuncion, notificarRelevacion, notificarAbandono, notificarCancelacion, notificarFinalizacion);
        /**/
        tab140.newTabField(minutosAdvertirAsignar1, cantidadAdvertirAsignar1, unidadAdvertirAsignar1);
        tab140.newTabField(minutosAdvertirFinalizar1, cantidadAdvertirFinalizar1, unidadAdvertirFinalizar1);
        tab140.newTabField(minutosAdvertirAsignar2, cantidadAdvertirAsignar2, unidadAdvertirAsignar2);
        tab140.newTabField(minutosAdvertirFinalizar2, cantidadAdvertirFinalizar2, unidadAdvertirFinalizar2);
        /**/
        tab150.newTabField(minutosEscalarAsignar1, cantidadEscalarAsignar1, unidadEscalarAsignar1);
        tab150.newTabField(minutosEscalarFinalizar1, cantidadEscalarFinalizar1, unidadEscalarFinalizar1);
        tab150.newTabField(minutosEscalarAsignar2, cantidadEscalarAsignar2, unidadEscalarAsignar2);
        tab150.newTabField(minutosEscalarFinalizar2, cantidadEscalarFinalizar2, unidadEscalarFinalizar2);
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of Funcion's tabs">
        /**/
        tab110.setLocalizedLabel(ENGLISH, "general");
        tab110.setLocalizedLabel(SPANISH, "general");
        /**/
        tab120.setLocalizedLabel(ENGLISH, "details");
        tab120.setLocalizedLabel(SPANISH, "detalles");
        /**/
        tab130.setLocalizedLabel(ENGLISH, "notifications");
        tab130.setLocalizedLabel(SPANISH, "notificaciones");
        /**/
        tab140.setLocalizedLabel(ENGLISH, "remainders ");
        tab140.setLocalizedLabel(SPANISH, "recordatorios");
        /**/
        tab150.setLocalizedLabel(ENGLISH, "escalations");
        tab150.setLocalizedLabel(SPANISH, "escalamientos");
        /**/
        // </editor-fold>
    }

    protected Segment CRUD;

    @Override
    protected void settleExpressions() {
        super.settleExpressions();
        /**/
        CRUD = tipoFuncion.isIn(tipoFuncion.CONSULTA, tipoFuncion.CREACION, tipoFuncion.MODIFICACION, tipoFuncion.ELIMINACION);
        CRUD.setLocalizedDescription(ENGLISH, "basic database operations (CRUD)");
        CRUD.setLocalizedDescription(SPANISH, "operaciones básicas de base de datos (CRUD)");
        /**/

    }

}
