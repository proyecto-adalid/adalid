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
package meta.entidad.comun.auditoria;

import adalid.core.*;
import adalid.core.annotations.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import adalid.core.page.format.*;
import adalid.core.parameters.*;
import adalid.core.properties.*;
import java.lang.reflect.Field;
import meta.entidad.comun.configuracion.basica.CondicionEjeFun;
import meta.entidad.comun.configuracion.basica.Funcion;
import meta.entidad.comun.configuracion.basica.TipoFuncion;
import meta.entidad.comun.control.acceso.Usuario;
import meta.entidad.comun.operacion.basica.RecursoValor;

/**
 * @author Jorge Campins
 */
@EntityClass(independent = Kleenean.TRUE, resourceType = ResourceType.OPERATION, resourceGender = ResourceGender.MASCULINE)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE, onload = SelectOnloadOption.EXECUTE, sortOption = SortOption.DESC)
@EntityInsertOperation(enabled = Kleenean.FALSE)
@EntityUpdateOperation(enabled = Kleenean.FALSE)
@EntityDeleteOperation(enabled = Kleenean.FALSE)
@EntityTableView(enabled = Kleenean.TRUE, responsiveMode = TableResponsiveMode.PRIORITY)
@EntityDetailView(enabled = Kleenean.TRUE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.TRUE)
@EntityTriggers(afterValue = Kleenean.TRUE)
@EntityWarnings(aboutBusinessKey = Kleenean.FALSE)
public class RastroFuncion extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public RastroFuncion(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    @PropertyField(hidden = Kleenean.FALSE, detail = Kleenean.TRUE, heading = Kleenean.TRUE)
    public LongProperty id;

    @ColumnField(nullable = Kleenean.FALSE, indexed = Kleenean.TRUE)
    @PropertyField(responsivePriority = 6, table = Kleenean.TRUE, report = Kleenean.TRUE, search = Kleenean.TRUE, heading = Kleenean.FALSE, overlay = Kleenean.TRUE)
    public TimestampProperty fechaHoraEjecucion;

    @OwnerProperty
    @SegmentProperty
    @ColumnField(nullable = Kleenean.TRUE)
//  20231209: remove foreign-key referring to Usuario because it might cause ARJUNA012117 and/or ARJUNA012121
//  @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(table = Kleenean.FALSE, export = Kleenean.FALSE, report = Kleenean.FALSE, heading = Kleenean.FALSE, overlay = Kleenean.FALSE, search = Kleenean.FALSE, filter = Kleenean.FALSE, column = Kleenean.FALSE) // hidden until 02/11/2024
    @QueryMapping(mapKeyProperties = Kleenean.FALSE)
    public Usuario usuario;

    @ColumnField(indexed = Kleenean.TRUE)
    @PropertyField(responsivePriority = 6, table = Kleenean.TRUE, search = Kleenean.TRUE, report = Kleenean.TRUE, heading = Kleenean.FALSE)
    @StringField(maxLength = MAX_EMAIL_ADDRESS_LENGTH, displayLength = 36) // maxLength = 36 until 01/12/2023
    public StringProperty codigoUsuario;

    @ColumnField(indexed = Kleenean.TRUE)
    @PropertyField(table = Kleenean.FALSE, search = Kleenean.TRUE, report = Kleenean.TRUE, heading = Kleenean.FALSE, overlay = Kleenean.TRUE, anchor = "codigoUsuario", anchorType = AnchorType.INLINE)
    @StringField(maxLength = 100)
    public StringProperty nombreUsuario;

    @ColumnField(nullable = Kleenean.TRUE)
    @PropertyField(table = Kleenean.FALSE)
    @StringField(maxLength = 40) // IPv4: Máximo de 15 caracteres (con puntos incluidos); IPv6: Máximo de 39 caracteres (con dos puntos incluidos)
    public StringProperty direccionIp;

//  20171213: remove foreign-key referring to Funcion
//  @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.TRUE)
    @PropertyField(table = Kleenean.FALSE, export = Kleenean.FALSE, report = Kleenean.FALSE, heading = Kleenean.FALSE, overlay = Kleenean.FALSE, search = Kleenean.FALSE, filter = Kleenean.FALSE, column = Kleenean.FALSE) // hidden until 11/01/2025
    @QueryMapping(mapKeyProperties = Kleenean.FALSE)
    public Funcion funcion;

    @ColumnField(indexed = Kleenean.TRUE)
    @PropertyField(table = Kleenean.TRUE, search = Kleenean.TRUE, report = Kleenean.TRUE, heading = Kleenean.FALSE)
    @StringField(maxLength = 200)
    public StringProperty codigoFuncion;

    @ColumnField(indexed = Kleenean.TRUE)
    @PropertyField(table = Kleenean.FALSE, search = Kleenean.TRUE, report = Kleenean.TRUE, heading = Kleenean.FALSE, overlay = Kleenean.TRUE, anchor = "codigoFuncion", anchorType = AnchorType.INLINE)
    @StringField(maxLength = 200)
    public StringProperty nombreFuncion;

    @ColumnField(calculable = Kleenean.TRUE)
    @PropertyField(table = Kleenean.FALSE)
    @StringField(maxLength = 0)
    public StringProperty descripcionFuncion;

    @UniformResourceLocator(urlType = UrlType.INTERNAL, urlDisplayType = UrlDisplayType.BUTTON)
    @PropertyField(responsivePriority = 6, table = Kleenean.TRUE, report = Kleenean.FALSE, search = Kleenean.FALSE, headertextless = Kleenean.TRUE)
    public StringProperty paginaFuncion;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(search = Kleenean.TRUE)
    public TipoFuncion tipoFuncion;

    @PropertyField(hidden = Kleenean.TRUE)
    public LongProperty idClaseRecursoValor;

    @ColumnField(indexed = Kleenean.TRUE)
    @PropertyField(search = Kleenean.TRUE)
    @StringField(maxLength = 100)
    public StringProperty codigoClaseRecursoValor;

    @ColumnField(indexed = Kleenean.TRUE)
    @PropertyField(search = Kleenean.TRUE, heading = Kleenean.FALSE, overlay = Kleenean.TRUE, anchor = "codigoClaseRecursoValor", anchorType = AnchorType.INLINE)
    @StringField(maxLength = 100)
    public StringProperty nombreClaseRecursoValor;

    @ColumnField(nullable = Kleenean.TRUE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(hidden = Kleenean.TRUE)
    public RecursoValor recursoValor;

    @PropertyField(hidden = Kleenean.TRUE)
    public LongProperty idRecurso;

    @PropertyField(hidden = Kleenean.TRUE)
    public LongProperty versionRecurso;

    @ColumnField(indexed = Kleenean.FALSE)
    @PropertyField(responsivePriority = 5, table = Kleenean.TRUE, report = Kleenean.TRUE, search = Kleenean.TRUE, overlay = Kleenean.TRUE)
    public StringProperty codigoRecurso;

    @ColumnField(indexed = Kleenean.FALSE)
    @PropertyField(table = Kleenean.FALSE, report = Kleenean.TRUE, search = Kleenean.TRUE, overlay = Kleenean.TRUE, anchor = "codigoRecurso", anchorType = AnchorType.INLINE)
    public StringProperty nombreRecurso;

    @PropertyField(hidden = Kleenean.TRUE)
    public LongProperty idPropietarioRecurso;

    @PropertyField(hidden = Kleenean.TRUE)
    public LongProperty idSegmentoRecurso;

    @PropertyField(hidden = Kleenean.TRUE)
    public LongProperty idSelectRecurso;

    @UniformResourceLocator(urlType = UrlType.INTERNAL, urlDisplayType = UrlDisplayType.BUTTON)
    @PropertyField(responsivePriority = 6, table = Kleenean.TRUE, report = Kleenean.FALSE, search = Kleenean.FALSE, headertextless = Kleenean.TRUE)
    public StringProperty paginaRecurso;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE, heading = Kleenean.TRUE, overlay = Kleenean.TRUE)
    public CondicionEjeFun condicionEjeFun;

    @StringField(maxLength = 0)
    public StringProperty descripcionError;

    @OneToOne(navigability = Navigability.UNIDIRECTIONAL, detailView = Kleenean.TRUE)
    public RastroProceso rastroProceso;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of RastroFuncion's attributes">
        setLocalizedLabel(ENGLISH, "function audit trail");
        setLocalizedLabel(SPANISH, "rastro de auditoría de función");
        setLocalizedShortLabel(ENGLISH, "function trail");
        setLocalizedShortLabel(SPANISH, "rastro de función");
        setLocalizedCollectionLabel(ENGLISH, "Function Audit Trails");
        setLocalizedCollectionLabel(SPANISH, "Rastros de Auditoría de Funciones");
        setLocalizedCollectionShortLabel(ENGLISH, "Function Trails");
        setLocalizedCollectionShortLabel(SPANISH, "Rastros de Funciones");
        /**/
        setLocalizedCollectionLabel(ENGLISH, rastroProceso, "Function Audit Trail");
        setLocalizedCollectionLabel(SPANISH, rastroProceso, "Rastro de Auditoría de Función");
        setLocalizedCollectionShortLabel(ENGLISH, rastroProceso, "Function Trail");
        setLocalizedCollectionShortLabel(SPANISH, rastroProceso, "Rastro de Función");
        /**/
        setLocalizedDescription(ENGLISH, "Each instance of " + b("Function Audit Trails") + " represents an "
            + "audit trail of the execution of CRUD operations and business processes."
            + "");
        setLocalizedDescription(SPANISH, "Cada instancia de " + b("Rastros de Auditoría de Funciones") + " representa un "
            + "rastro de auditoría de la ejecución de operaciones de registro y procesos de negocio."
            + "");
        /**/
        setLocalizedShortDescription(ENGLISH, "audit trail of the execution of CRUD operations and business processes");
        setLocalizedShortDescription(SPANISH, "rastro de auditoría de la ejecución de operaciones de registro y procesos de negocio");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        /**/
        fechaHoraEjecucion.setInitialValue(SpecialTemporalValue.CURRENT_TIMESTAMP);
        fechaHoraEjecucion.setDefaultValue(SpecialTemporalValue.CURRENT_TIMESTAMP);
        /**/
        descripcionFuncion.setCalculableValueExpression(funcion.descripcionFuncion);
        /**/
        condicionEjeFun.setInitialValue(condicionEjeFun.EJECUTADO_SIN_ERRORES);
        condicionEjeFun.setDefaultValue(condicionEjeFun.EJECUTADO_SIN_ERRORES);
        /*
        RastroUtils.setGraphicImageExpressions(condicionEjeFun);
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of RastroFuncion's properties">
        /**/
        fechaHoraEjecucion.setLocalizedDescription(ENGLISH, "date and time of the function execution");
        fechaHoraEjecucion.setLocalizedDescription(SPANISH, "fecha y hora de la ejecución de la función");
        fechaHoraEjecucion.setLocalizedLabel(ENGLISH, "timestamp");
        fechaHoraEjecucion.setLocalizedLabel(SPANISH, "fecha y hora");
        /**/
        usuario.setLocalizedDescription(ENGLISH, "user who executed the function");
        usuario.setLocalizedDescription(SPANISH, "usuario que ejecutó la función");
        usuario.setLocalizedShortDescription(ENGLISH, "user who executed the function");
        usuario.setLocalizedShortDescription(SPANISH, "usuario que ejecutó la función");
        usuario.setLocalizedLabel(ENGLISH, "user");
        usuario.setLocalizedLabel(SPANISH, "usuario");
        /**/
        codigoUsuario.setLocalizedDescription(ENGLISH, "code of the user who executed the function");
        codigoUsuario.setLocalizedDescription(SPANISH, "código del usuario que ejecutó la función");
        codigoUsuario.setLocalizedLabel(ENGLISH, "user code");
        codigoUsuario.setLocalizedLabel(SPANISH, "código del usuario");
        codigoUsuario.setLocalizedColumnHeader(ENGLISH, "user");
        codigoUsuario.setLocalizedColumnHeader(SPANISH, "usuario");
        codigoUsuario.setLocalizedAnchorLabel(ENGLISH, "user");
        codigoUsuario.setLocalizedAnchorLabel(SPANISH, "usuario");
        codigoUsuario.setLocalizedAnchoredLabel(ENGLISH, "code");
        codigoUsuario.setLocalizedAnchoredLabel(SPANISH, "código");
        /**/
        nombreUsuario.setLocalizedDescription(ENGLISH, "name of the user who executed the function");
        nombreUsuario.setLocalizedDescription(SPANISH, "nombre del usuario que ejecutó la función");
        nombreUsuario.setLocalizedLabel(ENGLISH, "user name");
        nombreUsuario.setLocalizedLabel(SPANISH, "nombre del usuario");
        nombreUsuario.setLocalizedAnchoredLabel(ENGLISH, "name");
        nombreUsuario.setLocalizedAnchoredLabel(SPANISH, "nombre");
        /**/
        direccionIp.setLocalizedDescription(ENGLISH, "IP address");
        direccionIp.setLocalizedDescription(SPANISH, "dirección IP");
        direccionIp.setLocalizedLabel(ENGLISH, "IP");
        direccionIp.setLocalizedLabel(SPANISH, "IP");
        /**/
        funcion.setLocalizedDescription(ENGLISH, "executed function");
        funcion.setLocalizedDescription(SPANISH, "función ejecutada");
        funcion.setLocalizedShortDescription(ENGLISH, "executed function");
        funcion.setLocalizedShortDescription(SPANISH, "función ejecutada");
        funcion.setLocalizedLabel(ENGLISH, "function");
        funcion.setLocalizedLabel(SPANISH, "función");
        /**/
        tipoFuncion.setLocalizedDescription(ENGLISH, "type of the function");
        tipoFuncion.setLocalizedDescription(SPANISH, "tipo de la función");
        tipoFuncion.setLocalizedShortDescription(ENGLISH, "type of the function");
        tipoFuncion.setLocalizedShortDescription(SPANISH, "tipo de la función");
        tipoFuncion.setLocalizedLabel(ENGLISH, "function type");
        tipoFuncion.setLocalizedLabel(SPANISH, "tipo de función");
        /**/
        codigoFuncion.setLocalizedDescription(ENGLISH, "code of the executed function");
        codigoFuncion.setLocalizedDescription(SPANISH, "código de la función ejecutada");
        codigoFuncion.setLocalizedLabel(ENGLISH, "function code");
        codigoFuncion.setLocalizedLabel(SPANISH, "código de la función");
        codigoFuncion.setLocalizedColumnHeader(ENGLISH, "function");
        codigoFuncion.setLocalizedColumnHeader(SPANISH, "función");
        codigoFuncion.setLocalizedAnchorLabel(ENGLISH, "function");
        codigoFuncion.setLocalizedAnchorLabel(SPANISH, "función");
        codigoFuncion.setLocalizedAnchoredLabel(ENGLISH, "code");
        codigoFuncion.setLocalizedAnchoredLabel(SPANISH, "código");
        /**/
        nombreFuncion.setLocalizedDescription(ENGLISH, "name of the function");
        nombreFuncion.setLocalizedDescription(SPANISH, "nombre de la función");
        nombreFuncion.setLocalizedLabel(ENGLISH, "function name");
        nombreFuncion.setLocalizedLabel(SPANISH, "nombre de la función");
        nombreFuncion.setLocalizedAnchoredLabel(ENGLISH, "name");
        nombreFuncion.setLocalizedAnchoredLabel(SPANISH, "nombre");
        /**/
        descripcionFuncion.setLocalizedDescription(ENGLISH, "description of the function");
        descripcionFuncion.setLocalizedDescription(SPANISH, "descripción de la función");
        descripcionFuncion.setLocalizedLabel(ENGLISH, "function description");
        descripcionFuncion.setLocalizedLabel(SPANISH, "descripción de la función");
        /**/
        paginaFuncion.setLocalizedDescription(ENGLISH, "hyperlink to open the page to execute the function");
        paginaFuncion.setLocalizedDescription(SPANISH, "hipervínculo para abrir la página para ejecutar la función");
        paginaFuncion.setLocalizedLabel(ENGLISH, "function page");
        paginaFuncion.setLocalizedLabel(SPANISH, "página de la función");
        paginaFuncion.setLocalizedTooltip(ENGLISH, "open the function's processing page");
        paginaFuncion.setLocalizedTooltip(SPANISH, "abrir la página de procesamiento de la función");
        /**/
        idClaseRecursoValor.setLocalizedDescription(ENGLISH, "resource class of the function");
        idClaseRecursoValor.setLocalizedDescription(SPANISH, "clase de recurso de la función");
        idClaseRecursoValor.setLocalizedLabel(ENGLISH, "resource class");
        idClaseRecursoValor.setLocalizedLabel(SPANISH, "clase de recurso");
        /**/
        codigoClaseRecursoValor.setLocalizedDescription(ENGLISH, "code of the resource class of the function");
        codigoClaseRecursoValor.setLocalizedDescription(SPANISH, "código de la clase de recurso de la función");
        codigoClaseRecursoValor.setLocalizedLabel(ENGLISH, "resource class code");
        codigoClaseRecursoValor.setLocalizedLabel(SPANISH, "código de la clase de recurso");
        codigoClaseRecursoValor.setLocalizedColumnHeader(ENGLISH, "resource class");
        codigoClaseRecursoValor.setLocalizedColumnHeader(SPANISH, "clase de recurso");
        codigoClaseRecursoValor.setLocalizedAnchorLabel(ENGLISH, "resource class");
        codigoClaseRecursoValor.setLocalizedAnchorLabel(SPANISH, "clase de recurso");
        codigoClaseRecursoValor.setLocalizedAnchoredLabel(ENGLISH, "code");
        codigoClaseRecursoValor.setLocalizedAnchoredLabel(SPANISH, "código");
        /**/
        nombreClaseRecursoValor.setLocalizedDescription(ENGLISH, "name of the resource class of the function");
        nombreClaseRecursoValor.setLocalizedDescription(SPANISH, "nombre de la clase de recurso de la función");
        nombreClaseRecursoValor.setLocalizedLabel(ENGLISH, "resource class name");
        nombreClaseRecursoValor.setLocalizedLabel(SPANISH, "nombre de la clase de recurso");
        nombreClaseRecursoValor.setLocalizedAnchoredLabel(ENGLISH, "name");
        nombreClaseRecursoValor.setLocalizedAnchoredLabel(SPANISH, "nombre");
        /**/
        recursoValor.setLocalizedLabel(ENGLISH, "resource");
        recursoValor.setLocalizedLabel(SPANISH, "recurso");
        /**/
        idRecurso.setLocalizedLabel(ENGLISH, "resource");
        idRecurso.setLocalizedLabel(SPANISH, "recurso");
        /**/
        versionRecurso.setLocalizedLabel(ENGLISH, "resource version");
        versionRecurso.setLocalizedLabel(SPANISH, "versión del recurso");
        /**/
        codigoRecurso.setLocalizedDescription(ENGLISH, "code of the resource on which the function was executed");
        codigoRecurso.setLocalizedDescription(SPANISH, "código del recurso sobre el que se ejecutó la función");
        codigoRecurso.setLocalizedLabel(ENGLISH, "resource code");
        codigoRecurso.setLocalizedLabel(SPANISH, "código del recurso");
        codigoRecurso.setLocalizedColumnHeader(ENGLISH, "resource");
        codigoRecurso.setLocalizedColumnHeader(SPANISH, "recurso");
        codigoRecurso.setLocalizedAnchorLabel(ENGLISH, "resource");
        codigoRecurso.setLocalizedAnchorLabel(SPANISH, "recurso");
        codigoRecurso.setLocalizedAnchoredLabel(ENGLISH, "code");
        codigoRecurso.setLocalizedAnchoredLabel(SPANISH, "código");
        /**/
        nombreRecurso.setLocalizedDescription(ENGLISH, "name of the resource on which the function was executed");
        nombreRecurso.setLocalizedDescription(SPANISH, "nombre del recurso sobre el que se ejecutó la función");
        nombreRecurso.setLocalizedLabel(ENGLISH, "resource name");
        nombreRecurso.setLocalizedLabel(SPANISH, "nombre del recurso");
        nombreRecurso.setLocalizedAnchoredLabel(ENGLISH, "name");
        nombreRecurso.setLocalizedAnchoredLabel(SPANISH, "nombre");
        /**/
        idPropietarioRecurso.setLocalizedLabel(ENGLISH, "resource owner");
        idPropietarioRecurso.setLocalizedLabel(SPANISH, "propietario del recurso");
        /**/
        idSegmentoRecurso.setLocalizedLabel(ENGLISH, "resource segment");
        idSegmentoRecurso.setLocalizedLabel(SPANISH, "segmento del recurso");
        /**/
        idSelectRecurso.setLocalizedLabel(ENGLISH, "resource select function");
        idSelectRecurso.setLocalizedLabel(SPANISH, "función de selección del recurso");
        /**/
        paginaRecurso.setLocalizedDescription(ENGLISH, "hyperlink to open the page to edit the resource");
        paginaRecurso.setLocalizedDescription(SPANISH, "hipervínculo para abrir la página para editar el recurso");
        paginaRecurso.setLocalizedLabel(ENGLISH, "resource page");
        paginaRecurso.setLocalizedLabel(SPANISH, "página del recurso");
        paginaRecurso.setLocalizedTooltip(ENGLISH, "open the resource's registration page");
        paginaRecurso.setLocalizedTooltip(SPANISH, "abrir la página de registro del recurso");
        /**/
        condicionEjeFun.setLocalizedDescription(ENGLISH, "condition code of the function execution");
        condicionEjeFun.setLocalizedDescription(SPANISH, "código de condición de la ejecución de la función");
        condicionEjeFun.setLocalizedShortDescription(ENGLISH, "condition code of the function execution");
        condicionEjeFun.setLocalizedShortDescription(SPANISH, "código de condición de la ejecución de la función");
        condicionEjeFun.setLocalizedLabel(ENGLISH, "function execution condition");
        condicionEjeFun.setLocalizedLabel(SPANISH, "condición de ejecución de función");
        condicionEjeFun.setLocalizedShortLabel(ENGLISH, "condition");
        condicionEjeFun.setLocalizedShortLabel(SPANISH, "condición");
        /**/
        descripcionError.setLocalizedDescription(ENGLISH, "text describing the result of the function execution");
        descripcionError.setLocalizedDescription(SPANISH, "texto que describe el resultado de la ejecución de la función");
        descripcionError.setLocalizedLabel(ENGLISH, "message");
        descripcionError.setLocalizedLabel(SPANISH, "mensaje");
        /**/
        // </editor-fold>
    }

    /**/
    protected Key ix_rastro_funcion_0001;

    @Override
    protected void settleKeys() {
        super.settleKeys();
        ix_rastro_funcion_0001.setUnique(false);
        ix_rastro_funcion_0001.newKeyField(idClaseRecursoValor, recursoValor);
    }

    /**/
    protected Tab tab110, tab120;

    @Override
    protected void settleTabs() {
        super.settleTabs();
        /**/
        tab110.newTabField(id, rastroProceso, fechaHoraEjecucion,
            usuario, funcion,
            codigoClaseRecursoValor, nombreClaseRecursoValor,
            codigoRecurso, nombreRecurso,
            condicionEjeFun, descripcionError, direccionIp
        );
        /**/
        tab120.newTabField(codigoUsuario, nombreUsuario, codigoFuncion, nombreFuncion, descripcionFuncion, paginaFuncion, tipoFuncion,
            idClaseRecursoValor, codigoClaseRecursoValor, nombreClaseRecursoValor, // 3
            recursoValor, idRecurso, versionRecurso, codigoRecurso, nombreRecurso, idPropietarioRecurso, idSegmentoRecurso, idSelectRecurso, paginaRecurso // 8
        );
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of RastroFuncion's tabs">
        /**/
        tab110.setLocalizedLabel(ENGLISH, "general");
        tab110.setLocalizedLabel(SPANISH, "general");
        /**/
        tab120.setLocalizedLabel(ENGLISH, "details");
        tab120.setLocalizedLabel(SPANISH, "detalles");
        /**/
        // </editor-fold>
    }

    protected Segment biz, dml, mis, misbiz, misdml;

    @Override
    protected void settleExpressions() {
        super.settleExpressions();
        /**/
        biz = tipoFuncion.isIn(
            tipoFuncion.PROCEDIMIENTO_PARAMETROS,
            tipoFuncion.PROCESO
        );
        dml = tipoFuncion.isIn(
            tipoFuncion.CREACION,
            tipoFuncion.MODIFICACION,
            tipoFuncion.ELIMINACION
        );
        /**/
        mis = codigoUsuario.isEqualTo(CURRENT_USER_CODE);
        misbiz = mis.and(biz);
        misdml = mis.and(dml);
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of RastroFuncion's expressions">
        /**/
        biz.setLocalizedCollectionLabel(ENGLISH, "Business Procedures and Processes");
        biz.setLocalizedCollectionLabel(SPANISH, "Procedimientos y Procesos de Negocio");
        biz.setLocalizedCollectionShortLabel(ENGLISH, "Business Operations");
        biz.setLocalizedCollectionShortLabel(SPANISH, "Operaciones de Negocio");
        /**/
        dml.setLocalizedCollectionLabel(ENGLISH, "Insert, Update and Delete Operations");
        dml.setLocalizedCollectionLabel(SPANISH, "Operaciones de Creación, Modificación y Eliminación");
        dml.setLocalizedCollectionShortLabel(ENGLISH, "Registration Operations");
        dml.setLocalizedCollectionShortLabel(SPANISH, "Operaciones de Registro");
        /**/
        mis.setLocalizedCollectionLabel(ENGLISH, "Operations executed by the current user");
        mis.setLocalizedCollectionLabel(SPANISH, "Operaciones ejecutadas por el usuario actual");
        mis.setLocalizedCollectionShortLabel(ENGLISH, "My Operations");
        mis.setLocalizedCollectionShortLabel(SPANISH, "Mis Operaciones");
        /**/
        misbiz.setLocalizedCollectionLabel(ENGLISH, "Business Procedures and Processes executed by the current user");
        misbiz.setLocalizedCollectionLabel(SPANISH, "Procedimientos y Procesos de Negocio ejecutados por el usuario actual");
        misbiz.setLocalizedCollectionShortLabel(ENGLISH, "My Business Operations");
        misbiz.setLocalizedCollectionShortLabel(SPANISH, "Mis Operaciones de Negocio");
        /**/
        misdml.setLocalizedCollectionLabel(ENGLISH, "Insert, Update and Delete Operations executed by the current user");
        misdml.setLocalizedCollectionLabel(SPANISH, "Operaciones de Creación, Modificación y Eliminación ejecutadas por el usuario actual");
        misdml.setLocalizedCollectionShortLabel(ENGLISH, "My Registration Operations");
        misdml.setLocalizedCollectionShortLabel(SPANISH, "Mis Operaciones de Registro");
        /**/
        // </editor-fold>
        /**/
    }

    @Override
    protected void settleFilters() {
        super.settleFilters();
        /**/
        addSelectSegment(biz, dml);
        addSelectSegment(mis, true);
        addSelectSegment(misbiz, misdml);
        /**/
        usuario.setRenderingFilter(usuario.id.isNotNull()); // mostrar si, y solo si, el usuario no ha sido eliminado
        /**/
    }

    protected View rastros;

    @Override
    protected void settleViews() {
        super.settleViews();
        /**/
        rastros.setPageFormatClass(DefaultPageFormat.class);
        /**/
        ViewField vf;
        final int controlGroupReportElementsWidth = 720;
        /**/
        vf = rastros.newControlField(codigoUsuario);
        vf.setPixels(controlGroupReportElementsWidth);
        /**/
        vf = rastros.newHeadingField(nombreUsuario, codigoUsuario);
        vf.setPixels(controlGroupReportElementsWidth);
        /**/
        vf = rastros.newDetailField(fechaHoraEjecucion, SortOption.ASC);
        vf.setPixels(125);
        /**/
        vf = rastros.newDetailField(codigoFuncion);
        vf.setPixels(125);
        /**/
        vf = rastros.newDetailField(nombreFuncion);
        vf.setPixels(225);
        /**/
        vf = rastros.newDetailField(codigoRecurso);
        vf.setPixels(125);
        /**/
        vf = rastros.newDetailField(nombreRecurso);
        vf.setPixels(225);
        /**/
        vf = rastros.newDetailField(condicionEjeFun.codigo);
        vf.setPixels(105);
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of RastroFuncion's views">
        rastros.setLocalizedLabel(ENGLISH, "Function Trails");
        rastros.setLocalizedLabel(SPANISH, "Rastros de Función");
        ViewField vf001 = rastros.getField(codigoUsuario);
        if (vf001 != null) {
            vf001.setLocalizedLabel(ENGLISH, "user");
            vf001.setLocalizedLabel(SPANISH, "usuario");
        }
        ViewField vf002 = rastros.getField(nombreUsuario);
        if (vf002 != null) {
            vf002.setLocalizedLabel(ENGLISH, "user name");
            vf002.setLocalizedLabel(SPANISH, "nombre del usuario");
        }
        ViewField vf003 = rastros.getField(fechaHoraEjecucion);
        if (vf003 != null) {
            vf003.setLocalizedLabel(ENGLISH, "timestamp");
            vf003.setLocalizedLabel(SPANISH, "fecha/hora");
        }
        ViewField vf004 = rastros.getField(codigoFuncion);
        if (vf004 != null) {
            vf004.setLocalizedLabel(ENGLISH, "function");
            vf004.setLocalizedLabel(SPANISH, "función");
        }
        ViewField vf005 = rastros.getField(nombreFuncion);
        if (vf005 != null) {
            vf005.setLocalizedLabel(ENGLISH, "function name");
            vf005.setLocalizedLabel(SPANISH, "nombre de la función");
        }
        ViewField vf006 = rastros.getField(codigoRecurso);
        if (vf006 != null) {
            vf006.setLocalizedLabel(ENGLISH, "resource");
            vf006.setLocalizedLabel(SPANISH, "recurso");
        }
        ViewField vf007 = rastros.getField(nombreRecurso);
        if (vf007 != null) {
            vf007.setLocalizedLabel(ENGLISH, "resource name");
            vf007.setLocalizedLabel(SPANISH, "nombre del recurso");
        }
        ViewField vf008 = rastros.getField(condicionEjeFun.codigo);
        if (vf008 != null) {
            vf008.setLocalizedLabel(ENGLISH, "condition");
            vf008.setLocalizedLabel(SPANISH, "condición");
        }
        // </editor-fold>
    }

    protected ExportarArchivoRastros archivoRastros;

    @ExportOperationClass(viewField = "rastros")
    @OperationClass(access = OperationAccess.RESTRICTED)
    public class ExportarArchivoRastros extends ExportOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of ExportarArchivoRastros's attributes">
            setLocalizedLabel(ENGLISH, "generate function trails file");
            setLocalizedLabel(SPANISH, "generar archivo de rastros");
            setLocalizedShortLabel(ENGLISH, "function trails file");
            setLocalizedShortLabel(SPANISH, "archivo de rastros");
//          setLocalizedCollectionLabel(ENGLISH, "Function Trails");
//          setLocalizedCollectionLabel(SPANISH, "Rastros de Función");
            setLocalizedDescription(ENGLISH, "export a file of function execution audit trails");
            setLocalizedDescription(SPANISH, "exportar un archivo de rastros de ejecución de función");
            // </editor-fold>
        }

        @ParameterField(linkedField = "fechaHoraEjecucion", operator = StandardRelationalOp.GTEQ)
        protected TimestampParameter desde;

        @ParameterField(linkedField = "fechaHoraEjecucion", operator = StandardRelationalOp.LTEQ)
        protected TimestampParameter hasta;

        @ParameterField(linkedField = "usuario")
        protected Usuario usuario;

        @ParameterField(linkedField = "funcion")
        protected Funcion funcion;

        @ParameterField(linkedField = "codigoRecurso")
        protected StringParameter codigoRecurso;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            /**/
            desde.setMinValue("2001-01-01");
            desde.setMaxValue(SpecialTemporalValue.CURRENT_TIMESTAMP);
            /**/
            hasta.setMinValue(desde);
            hasta.setMaxValue(SpecialTemporalValue.CURRENT_TIMESTAMP);
            /**/
            // <editor-fold defaultstate="collapsed" desc="localization of ExportarArchivoRastros's parameters">
            desde.setLocalizedLabel(ENGLISH, "from");
            desde.setLocalizedLabel(SPANISH, "desde");
            /**/
            hasta.setLocalizedLabel(ENGLISH, "to");
            hasta.setLocalizedLabel(SPANISH, "hasta");
            /**/
            usuario.setLocalizedLabel(ENGLISH, "user");
            usuario.setLocalizedLabel(SPANISH, "usuario");
            /**/
            funcion.setLocalizedLabel(ENGLISH, "function");
            funcion.setLocalizedLabel(SPANISH, "función");
            /**/
            codigoRecurso.setLocalizedLabel(ENGLISH, "resource code");
            codigoRecurso.setLocalizedLabel(SPANISH, "código del recurso");
            // </editor-fold>
        }

    }

    protected EmitirInformeRastros informeRastros;

    @ReportOperationClass(viewField = "rastros")
    @OperationClass(access = OperationAccess.RESTRICTED)
    public class EmitirInformeRastros extends ReportOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of EmitirInformeRastros's attributes">
            setLocalizedLabel(ENGLISH, "generate function trails report");
            setLocalizedLabel(SPANISH, "generar informe de rastros");
            setLocalizedShortLabel(ENGLISH, "function trails report");
            setLocalizedShortLabel(SPANISH, "informe de rastros");
//          setLocalizedCollectionLabel(ENGLISH, "Function Trails");
//          setLocalizedCollectionLabel(SPANISH, "Rastros de Función");
            setLocalizedDescription(ENGLISH, "issue a report of function execution audit trails");
            setLocalizedDescription(SPANISH, "emitir un informe de rastros de ejecución de función");
            // </editor-fold>
        }

        @ParameterField(linkedField = "fechaHoraEjecucion", operator = StandardRelationalOp.GTEQ)
        protected TimestampParameter desde;

        @ParameterField(linkedField = "fechaHoraEjecucion", operator = StandardRelationalOp.LTEQ)
        protected TimestampParameter hasta;

        @ParameterField(linkedField = "usuario")
        protected Usuario usuario;

        @ParameterField(linkedField = "funcion")
        protected Funcion funcion;

        @ParameterField(linkedField = "codigoRecurso")
        protected StringParameter codigoRecurso;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            /**/
            desde.setMinValue("2001-01-01");
            desde.setMaxValue(SpecialTemporalValue.CURRENT_TIMESTAMP);
            /**/
            hasta.setMinValue(desde);
            hasta.setMaxValue(SpecialTemporalValue.CURRENT_TIMESTAMP);
            /**/
            // <editor-fold defaultstate="collapsed" desc="localization of EmitirInformeRastros's parameters">
            desde.setLocalizedLabel(ENGLISH, "from");
            desde.setLocalizedLabel(SPANISH, "desde");
            /**/
            hasta.setLocalizedLabel(ENGLISH, "to");
            hasta.setLocalizedLabel(SPANISH, "hasta");
            /**/
            usuario.setLocalizedLabel(ENGLISH, "user");
            usuario.setLocalizedLabel(SPANISH, "usuario");
            /**/
            funcion.setLocalizedLabel(ENGLISH, "function");
            funcion.setLocalizedLabel(SPANISH, "función");
            /**/
            codigoRecurso.setLocalizedLabel(ENGLISH, "resource code");
            codigoRecurso.setLocalizedLabel(SPANISH, "código del recurso");
            // </editor-fold>
        }

    }

}
