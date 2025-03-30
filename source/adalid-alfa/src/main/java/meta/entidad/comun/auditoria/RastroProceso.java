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
import adalid.core.properties.*;
import java.lang.reflect.Field;
import meta.entidad.comun.configuracion.basica.CondicionEjeFun;
import meta.entidad.comun.configuracion.basica.Funcion;
import meta.entidad.comun.configuracion.basica.GrupoProceso;
import meta.entidad.comun.configuracion.basica.SeveridadMensaje;
import meta.entidad.comun.configuracion.basica.TipoFuncion;
import meta.entidad.comun.configuracion.basica.TipoRastroFun;
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
@EntityTableView(enabled = Kleenean.TRUE, responsiveMode = TableResponsiveMode.PRIORITY, readingViewWesternToolbarSnippet = "/resources/snippets/base/entity/RastroProceso/botonOpenCalendarioProcesos")
@EntityDetailView(enabled = Kleenean.TRUE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
@EntityTriggers(afterValue = Kleenean.TRUE)
@EntityWarnings(aboutBusinessKey = Kleenean.FALSE)
public class RastroProceso extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public RastroProceso(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Override
    protected void addAllocationStrings() {
        super.addAllocationStrings();
        /*
        super.addAllocationStrings(
            "condicionEjeFun.severidadMensaje",
            "condicionEjeTem.severidadMensaje"
        );
        /**/
    }

    @PrimaryKey
    @PropertyField(hidden = Kleenean.FALSE, detail = Kleenean.TRUE, heading = Kleenean.TRUE)
    public LongProperty id;

    @ColumnField(nullable = Kleenean.FALSE, indexed = Kleenean.TRUE)
    @PropertyField(responsivePriority = 6, table = Kleenean.TRUE, report = Kleenean.TRUE, search = Kleenean.TRUE, heading = Kleenean.FALSE, overlay = Kleenean.TRUE)
    public TimestampProperty fechaHoraInicioEjecucion;

    public TimestampProperty fechaHoraFinEjecucion;

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
    @PropertyField(responsivePriority = 6, table = Kleenean.TRUE, search = Kleenean.TRUE, report = Kleenean.TRUE)
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
    @PropertyField(table = Kleenean.TRUE, search = Kleenean.TRUE, report = Kleenean.TRUE)
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

    @UniformResourceLocator(urlType = UrlType.INTERNAL, urlDisplayMode = DisplayMode.PROCESSING, urlDisplayType = UrlDisplayType.BUTTON)
    @PropertyField(responsivePriority = 6, table = Kleenean.TRUE, report = Kleenean.FALSE, search = Kleenean.FALSE, headertextless = Kleenean.TRUE)
    public StringProperty paginaFuncion;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    public TipoFuncion tipoFuncion;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    public TipoRastroFun tipoRastroFun;

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

    @UniformResourceLocator(urlType = UrlType.INTERNAL, urlDisplayType = UrlDisplayType.BUTTON)
    @PropertyField(responsivePriority = 6, table = Kleenean.TRUE, report = Kleenean.FALSE, search = Kleenean.FALSE, headertextless = Kleenean.TRUE)
    public StringProperty paginaRecurso;

    @StringField(maxLength = 20)
    public StringProperty etiquetaLenguaje;

    private static final String BORPPSX1 = "/resources/snippets/base/entity/RastroProceso/botonOpenRastroProcesoPorSuperiorX1";

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE, heading = Kleenean.TRUE, overlay = Kleenean.TRUE, readingTableSnippet = BORPPSX1)
    public CondicionEjeFun condicionEjeFun;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.TRUE)
    @PropertyField(table = Kleenean.FALSE, report = Kleenean.FALSE)
    public SeveridadMensaje severidadMensaje;

    @PropertyField(hidden = Kleenean.TRUE)
    public LongProperty idRecursoObtenido;

    @UniformResourceLocator(urlType = UrlType.INTERNAL, urlDisplayType = UrlDisplayType.BUTTON)
    @PropertyField(table = Kleenean.FALSE, report = Kleenean.FALSE, search = Kleenean.FALSE, headertextless = Kleenean.TRUE)
    public StringProperty paginaRecursoObtenido;

    private static final String BOAAPRPCX1 = "/resources/snippets/base/entity/RastroProceso/botonOpenArchivoAdjuntoPorRastroProcesoCargaX1";

    @FileReference(loadField = "fechaHoraInicioEjecucion")
    @PropertyField(responsivePriority = 5, table = Kleenean.TRUE, report = Kleenean.FALSE, search = Kleenean.FALSE, readingTableSnippet = BOAAPRPCX1)
    @StringField(maxLength = Project.FILE_REFERENCE_MAX_LENGTH)
    public StringProperty nombreArchivo;

    private static final String BOAAPRPCX2 = "/resources/snippets/base/entity/RastroProceso/botonOpenArchivoAdjuntoPorRastroProcesoCargaX2";

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(readingDetailSnippet = BOAAPRPCX2)
    public IntegerProperty archivosCargados;

    @PropertyField(hidden = Kleenean.TRUE)
    @StringField(maxLength = 100)
    public StringProperty codigoError;

//  @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE)
    @StringField(maxLength = 0)
    public StringProperty descripcionError;

//  do not add a foreign-key referring to GrupoProceso
//  @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.TRUE)
    @PropertyField(table = Kleenean.FALSE, report = Kleenean.FALSE)
//  @QueryMapping(mapKeyProperties = Kleenean.FALSE)
    public GrupoProceso grupo;

//  do not add a foreign-key referring to RastroProceso
//  @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.BIDIRECTIONAL, view = MasterDetailView.TABLE_AND_DETAIL)
    @ColumnField(nullable = Kleenean.TRUE)
    @PropertyField(table = Kleenean.FALSE, report = Kleenean.FALSE)
//  @QueryMapping(mapKeyProperties = Kleenean.FALSE)
    public RastroProceso superior;

    @ColumnField(nullable = Kleenean.FALSE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(hidden = Kleenean.TRUE)
    public CondicionEjeFun condicionEjeTem;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.TRUE)
    @PropertyField(hidden = Kleenean.TRUE)
    public SeveridadMensaje severidadMensajeTem;

    @PropertyField(hidden = Kleenean.TRUE)
    public LongProperty idRecursoObtenidoTem;

    @PropertyField(hidden = Kleenean.TRUE)
    @StringField(maxLength = Project.FILE_REFERENCE_MAX_LENGTH)
    public StringProperty nombreArchivoTem;

    @PropertyField(hidden = Kleenean.TRUE)
    @StringField(maxLength = 100)
    public StringProperty codigoErrorTem;

    @PropertyField(hidden = Kleenean.TRUE)
    @StringField(maxLength = 0)
    public StringProperty descripcionErrorTem;

    @ColumnField(nullable = Kleenean.TRUE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(hidden = Kleenean.TRUE)
    public CondicionEjeFun condicionEjeAnt;

    @ColumnField(indexed = Kleenean.TRUE)
    @StringField(maxLength = 146) // 48*3+2 for nnnnnnnnn.nnnnnnnnn.nnnnnnnnnnn would be "safe"
    @PropertyField(hidden = Kleenean.TRUE)
    public StringProperty ultimaTransaccion;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(hidden = Kleenean.TRUE)
    public IntegerProperty transacciones;

    @ColumnField(nullable = Kleenean.FALSE)
    public BooleanProperty leido;

    @ColumnField(nullable = Kleenean.FALSE)
    public BooleanProperty descargado;

    @ColumnField(nullable = Kleenean.FALSE)
//  @PropertyField(search = Kleenean.TRUE)
    public BooleanProperty procesoAsincrono;

    @ColumnField(nullable = Kleenean.FALSE)
//  @PropertyField(search = Kleenean.TRUE)
    public BooleanProperty procesoCalendarizado;

    @ColumnField(nullable = Kleenean.FALSE)
//  @PropertyField(search = Kleenean.TRUE)
    public BooleanProperty procesoNativo;

    @ColumnField(nullable = Kleenean.FALSE)
//  @PropertyField(search = Kleenean.TRUE)
    public BooleanProperty procesoWeb;

    private static final String BORPPSX2 = "/resources/snippets/base/entity/RastroProceso/botonOpenRastroProcesoPorSuperiorX2";

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(readingDetailSnippet = BORPPSX2)
    public IntegerProperty subprocesos;

    @ColumnField(nullable = Kleenean.FALSE)
    public IntegerProperty subprocesosPendientes;

    @ColumnField(nullable = Kleenean.FALSE)
    public IntegerProperty subprocesosEnProgreso;

    @ColumnField(nullable = Kleenean.FALSE)
    public IntegerProperty subprocesosSinErrores;

    @ColumnField(nullable = Kleenean.FALSE)
    public IntegerProperty subprocesosConErrores;

    @ColumnField(nullable = Kleenean.FALSE)
    public IntegerProperty subprocesosCancelados;

    @StringField(maxLength = 100)
    public StringProperty procedimientoAfterUpdate;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of RastroProceso's attributes">
        /**/
        setLocalizedLabel(ENGLISH, "process audit trail");
        setLocalizedLabel(SPANISH, "rastro de auditoría de proceso");
        setLocalizedShortLabel(ENGLISH, "process trail");
        setLocalizedShortLabel(SPANISH, "rastro de proceso");
        setLocalizedCollectionLabel(ENGLISH, "Process Audit Trails");
        setLocalizedCollectionLabel(SPANISH, "Rastros de Auditoría de Procesos");
        setLocalizedCollectionShortLabel(ENGLISH, "Process Trails");
        setLocalizedCollectionShortLabel(SPANISH, "Rastros de Procesos");
        setLocalizedCollectionLabel(ENGLISH, superior, "Subprocess Audit Trails by Parent Process Trail");
        setLocalizedCollectionLabel(SPANISH, superior, "Rastros de Auditoría de Subprocesos por Rastro de Proceso Superior");
        setLocalizedCollectionShortLabel(ENGLISH, superior, "Subprocess Trails");
        setLocalizedCollectionShortLabel(SPANISH, superior, "Rastros de Subprocesos");
        /**/
        setLocalizedDescription(ENGLISH, "Each instance of " + b("Process Audit Trails") + " represents an "
            + "audit trail of the execution of business and scheduled processes."
            + "");
        setLocalizedDescription(SPANISH, "Cada instancia de " + b("Rastros de Auditoría de Procesos") + " representa un "
            + "rastro de auditoría de la ejecución de procesos de negocio y calendarizados."
            + "");
        /**/
        setLocalizedShortDescription(ENGLISH, "audit trail of the execution of business and scheduled processes");
        setLocalizedShortDescription(SPANISH, "rastro de auditoría de la ejecución de procesos de negocio y calendarizados");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        /**/
        fechaHoraInicioEjecucion.setInitialValue(SpecialTemporalValue.CURRENT_TIMESTAMP);
        fechaHoraInicioEjecucion.setDefaultValue(SpecialTemporalValue.CURRENT_TIMESTAMP);
        /**/
        descripcionFuncion.setCalculableValueExpression(funcion.descripcionFuncion);
        /**/
        tipoRastroFun.setInitialValue(tipoRastroFun.NINGUNO);
        tipoRastroFun.setDefaultValue(tipoRastroFun.NINGUNO);
        /**/
        condicionEjeFun.setInitialValue(condicionEjeFun.EJECUCION_PENDIENTE);
        condicionEjeFun.setDefaultValue(condicionEjeFun.EJECUCION_PENDIENTE);
        /*
        RastroUtils.setGraphicImageExpressions(condicionEjeFun);
        /*
        BooleanExpression condicionEjeFunX = or(
            condicionEjeFun.isEqualTo(condicionEjeFun.EJECUCION_CANCELADA),
            condicionEjeFun.isEqualTo(condicionEjeFun.EJECUTADO_CON_ERRORES),
            condicionEjeFun.isEqualTo(condicionEjeFun.EJECUTADO_SIN_ERRORES)
        );
        severidadMensaje.setDefaultValue(condicionEjeFunX.then(condicionEjeFun.severidadMensaje));
        /**/
        severidadMensaje.setDefaultValue(condicionEjeFun.isNull().then(SpecialEntityValue.NULL).
            otherwise(condicionEjeFun.isEqualTo(condicionEjeFun.EJECUCION_CANCELADA).then(severidadMensaje.FATAL).
                otherwise(condicionEjeFun.isEqualTo(condicionEjeFun.EJECUTADO_CON_ERRORES).then(severidadMensaje.ERROR).
                    otherwise(severidadMensaje.INFORMATIVO))));
        /**/
        condicionEjeTem.setInitialValue(condicionEjeTem.EJECUCION_PENDIENTE);
        condicionEjeTem.setDefaultValue(condicionEjeTem.EJECUCION_PENDIENTE);
        /*
        BooleanExpression condicionEjeTemX = or(
            condicionEjeTem.isEqualTo(condicionEjeTem.EJECUCION_CANCELADA),
            condicionEjeTem.isEqualTo(condicionEjeTem.EJECUTADO_CON_ERRORES),
            condicionEjeTem.isEqualTo(condicionEjeTem.EJECUTADO_SIN_ERRORES)
        );
        severidadMensajeTem.setDefaultValue(condicionEjeTemX.then(condicionEjeTem.severidadMensaje));
        /**/
        severidadMensajeTem.setDefaultValue(condicionEjeTem.isNull().then(SpecialEntityValue.NULL).
            otherwise(condicionEjeTem.isEqualTo(condicionEjeTem.EJECUCION_CANCELADA).then(severidadMensajeTem.FATAL).
                otherwise(condicionEjeTem.isEqualTo(condicionEjeTem.EJECUTADO_CON_ERRORES).then(severidadMensajeTem.ERROR).
                    otherwise(severidadMensajeTem.INFORMATIVO))));
        /**/
        transacciones.setInitialValue(0);
        transacciones.setDefaultValue(0);
        /**/
        nombreArchivo.setFileDownloadStopFunction("hideDialogoMostrarStatusDownloadPlusRefresh");
        nombreArchivo.setFileViewerDialogReturnUpdate("mainForm", "northForm", "@(.xs-bar-updatable-component)");
        /**/
        archivosCargados.setInitialValue(0);
        archivosCargados.setDefaultValue(0);
        /**/
        leido.setInitialValue(false);
        leido.setDefaultValue(false);
        /**/
        descargado.setInitialValue(false);
        descargado.setDefaultValue(false);
        /**/
        procesoAsincrono.setInitialValue(false);
        procesoAsincrono.setDefaultValue(false);
        /**/
        procesoCalendarizado.setInitialValue(false);
        procesoCalendarizado.setDefaultValue(false);
        /**/
        procesoNativo.setInitialValue(false);
        procesoNativo.setDefaultValue(false);
        /**/
        procesoWeb.setInitialValue(false);
        procesoWeb.setDefaultValue(false);
        /**/
        subprocesos.setInitialValue(0);
        subprocesos.setDefaultValue(0);
        /**/
        subprocesosPendientes.setInitialValue(0);
        subprocesosPendientes.setDefaultValue(0);
        /**/
        subprocesosEnProgreso.setInitialValue(0);
        subprocesosEnProgreso.setDefaultValue(0);
        /**/
        subprocesosSinErrores.setInitialValue(0);
        subprocesosSinErrores.setDefaultValue(0);
        /**/
        subprocesosConErrores.setInitialValue(0);
        subprocesosConErrores.setDefaultValue(0);
        /**/
        subprocesosCancelados.setInitialValue(0);
        subprocesosCancelados.setDefaultValue(0);
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of RastroProceso's properties">
        /**/
        fechaHoraInicioEjecucion.setLocalizedLabel(ENGLISH, "start");
        fechaHoraInicioEjecucion.setLocalizedLabel(SPANISH, "inicio");
        /**/
        fechaHoraFinEjecucion.setLocalizedLabel(ENGLISH, "end");
        fechaHoraFinEjecucion.setLocalizedLabel(SPANISH, "fin");
        /**/
        usuario.setLocalizedDescription(ENGLISH, "user who executed the process");
        usuario.setLocalizedDescription(SPANISH, "usuario que ejecutó el proceso");
        usuario.setLocalizedShortDescription(ENGLISH, "user who executed the process");
        usuario.setLocalizedShortDescription(SPANISH, "usuario que ejecutó el proceso");
        usuario.setLocalizedLabel(ENGLISH, "user");
        usuario.setLocalizedLabel(SPANISH, "usuario");
        /**/
        codigoUsuario.setLocalizedDescription(ENGLISH, "code of the user who executed the process");
        codigoUsuario.setLocalizedDescription(SPANISH, "código del usuario que ejecutó el proceso");
        codigoUsuario.setLocalizedLabel(ENGLISH, "user code");
        codigoUsuario.setLocalizedLabel(SPANISH, "código del usuario");
        codigoUsuario.setLocalizedColumnHeader(ENGLISH, "user");
        codigoUsuario.setLocalizedColumnHeader(SPANISH, "usuario");
        codigoUsuario.setLocalizedAnchorLabel(ENGLISH, "user");
        codigoUsuario.setLocalizedAnchorLabel(SPANISH, "usuario");
        codigoUsuario.setLocalizedAnchoredLabel(ENGLISH, "code");
        codigoUsuario.setLocalizedAnchoredLabel(SPANISH, "código");
        /**/
        nombreUsuario.setLocalizedDescription(ENGLISH, "name of the user who executed the process");
        nombreUsuario.setLocalizedDescription(SPANISH, "nombre del usuario que ejecutó el proceso");
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
        funcion.setLocalizedLabel(ENGLISH, "function");
        funcion.setLocalizedLabel(SPANISH, "función");
        /**/
        tipoFuncion.setLocalizedLabel(ENGLISH, "function type");
        tipoFuncion.setLocalizedLabel(SPANISH, "tipo de función");
        /**/
        tipoRastroFun.setLocalizedLabel(ENGLISH, "function trail type");
        tipoRastroFun.setLocalizedLabel(SPANISH, "tipo de rastro");
        /**/
        codigoFuncion.setLocalizedLabel(ENGLISH, "function code");
        codigoFuncion.setLocalizedLabel(SPANISH, "código de la función");
        codigoFuncion.setLocalizedColumnHeader(ENGLISH, "function");
        codigoFuncion.setLocalizedColumnHeader(SPANISH, "función");
        codigoFuncion.setLocalizedAnchorLabel(ENGLISH, "function");
        codigoFuncion.setLocalizedAnchorLabel(SPANISH, "función");
        codigoFuncion.setLocalizedAnchoredLabel(ENGLISH, "code");
        codigoFuncion.setLocalizedAnchoredLabel(SPANISH, "código");
        /**/
        nombreFuncion.setLocalizedLabel(ENGLISH, "function name");
        nombreFuncion.setLocalizedLabel(SPANISH, "nombre de la función");
        nombreFuncion.setLocalizedAnchoredLabel(ENGLISH, "name");
        nombreFuncion.setLocalizedAnchoredLabel(SPANISH, "nombre");
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
        codigoRecurso.setLocalizedDescription(ENGLISH, "code of the resource on which the process was executed");
        codigoRecurso.setLocalizedDescription(SPANISH, "código del recurso sobre el que se ejecutó el proceso");
        codigoRecurso.setLocalizedLabel(ENGLISH, "resource code");
        codigoRecurso.setLocalizedLabel(SPANISH, "código del recurso");
        codigoRecurso.setLocalizedColumnHeader(ENGLISH, "resource");
        codigoRecurso.setLocalizedColumnHeader(SPANISH, "recurso");
        codigoRecurso.setLocalizedAnchorLabel(ENGLISH, "resource");
        codigoRecurso.setLocalizedAnchorLabel(SPANISH, "recurso");
        codigoRecurso.setLocalizedAnchoredLabel(ENGLISH, "code");
        codigoRecurso.setLocalizedAnchoredLabel(SPANISH, "código");
        /**/
        nombreRecurso.setLocalizedDescription(ENGLISH, "name of the resource on which the process was executed");
        nombreRecurso.setLocalizedDescription(SPANISH, "nombre del recurso sobre el que se ejecutó el proceso");
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
        paginaRecurso.setLocalizedLabel(ENGLISH, "resource page");
        paginaRecurso.setLocalizedLabel(SPANISH, "página del recurso");
        paginaRecurso.setLocalizedTooltip(ENGLISH, "open the resource's registration page");
        paginaRecurso.setLocalizedTooltip(SPANISH, "abrir la página de registro del recurso");
        /**/
        etiquetaLenguaje.setLocalizedLabel(ENGLISH, "language tag");
        etiquetaLenguaje.setLocalizedLabel(SPANISH, "etiqueta de lenguaje");
        etiquetaLenguaje.setLocalizedShortLabel(ENGLISH, "language");
        etiquetaLenguaje.setLocalizedShortLabel(SPANISH, "lenguaje");
        /**/
        condicionEjeFun.setLocalizedLabel(ENGLISH, "function execution condition");
        condicionEjeFun.setLocalizedLabel(SPANISH, "condición de ejecución de función");
        condicionEjeFun.setLocalizedShortLabel(ENGLISH, "condition");
        condicionEjeFun.setLocalizedShortLabel(SPANISH, "condición");
        /**/
        severidadMensaje.setLocalizedLabel(ENGLISH, "message severity");
        severidadMensaje.setLocalizedLabel(SPANISH, "severidad de mensaje");
        severidadMensaje.setLocalizedShortLabel(ENGLISH, "severity");
        severidadMensaje.setLocalizedShortLabel(SPANISH, "severidad");
        /**/
        severidadMensaje.setLocalizedDefaultValueTag(ENGLISH, ""
            + "If " + b("condition") + " is EJECUCION CANCELADA, then FATAL; "
            + "if " + b("condition") + " is EJECUTADO CON ERRORES, then ERROR; "
            + "in all other cases, INFORMATIVO"
        );
        severidadMensaje.setLocalizedDefaultValueTag(SPANISH, ""
            + "Si " + b("condición") + " es EJECUCION CANCELADA, entonces FATAL; "
            + "si " + b("condición") + " es EJECUTADO CON ERRORES, entonces ERROR; "
            + "en los demás casos, INFORMATIVO"
        );
        /**/
        idRecursoObtenido.setLocalizedLabel(ENGLISH, "obtained resource");
        idRecursoObtenido.setLocalizedLabel(SPANISH, "recurso obtenido");
        /**/
        paginaRecursoObtenido.setLocalizedLabel(ENGLISH, "obtained resource page");
        paginaRecursoObtenido.setLocalizedLabel(SPANISH, "página recurso obtenido");
        paginaRecursoObtenido.setLocalizedTooltip(ENGLISH, "open the obtained resource page");
        paginaRecursoObtenido.setLocalizedTooltip(SPANISH, "abrir la página del recurso obtenido");
        /**/
        nombreArchivo.setLocalizedLabel(ENGLISH, "file");
        nombreArchivo.setLocalizedLabel(SPANISH, "archivo");
        nombreArchivo.setLocalizedTooltip(ENGLISH, "open the process file");
        nombreArchivo.setLocalizedTooltip(SPANISH, "abrir el archivo del proceso");
        /**/
        archivosCargados.setLocalizedLabel(ENGLISH, "uploaded files");
        archivosCargados.setLocalizedLabel(SPANISH, "archivos cargados");
        /**/
        codigoError.setLocalizedLabel(ENGLISH, "message code");
        codigoError.setLocalizedLabel(SPANISH, "código del mensaje");
        /**/
        descripcionError.setLocalizedLabel(ENGLISH, "message");
        descripcionError.setLocalizedLabel(SPANISH, "mensaje");
        /*
        mensajeAplicacion.setLocalizedLabel(ENGLISH, "application message");
        mensajeAplicacion.setLocalizedLabel(SPANISH, "mensaje de la aplicación");
        /**/
        grupo.setLocalizedLabel(ENGLISH, "process group");
        grupo.setLocalizedLabel(SPANISH, "grupo de procesos");
        /**/
        superior.setLocalizedLabel(ENGLISH, "parent process trail");
        superior.setLocalizedLabel(SPANISH, "rastro de proceso superior");
//      superior.setLocalizedShortLabel(ENGLISH, "parent process");
//      superior.setLocalizedShortLabel(SPANISH, "proceso superior");
        /**/
        condicionEjeTem.setLocalizedLabel(ENGLISH, "function execution temporal condition");
        condicionEjeTem.setLocalizedLabel(SPANISH, "condición temporal de ejecución de función");
        condicionEjeTem.setLocalizedShortLabel(ENGLISH, "temporal condition");
        condicionEjeTem.setLocalizedShortLabel(SPANISH, "condición temporal");
        /**/
        severidadMensajeTem.setLocalizedLabel(ENGLISH, "message temporal severity");
        severidadMensajeTem.setLocalizedLabel(SPANISH, "severidad temporal de mensaje");
        severidadMensajeTem.setLocalizedShortLabel(ENGLISH, "temporal severity");
        severidadMensajeTem.setLocalizedShortLabel(SPANISH, "severidad temporal");
        /**/
        severidadMensajeTem.setLocalizedDefaultValueTag(ENGLISH, ""
            + "If " + b("condition") + " is EJECUCION CANCELADA, then FATAL; "
            + "if " + b("condition") + " is EJECUTADO CON ERRORES, then ERROR; "
            + "in all other cases, INFORMATIVO"
        );
        severidadMensajeTem.setLocalizedDefaultValueTag(SPANISH, ""
            + "Si " + b("condición") + " es EJECUCION CANCELADA, entonces FATAL; "
            + "si " + b("condición") + " es EJECUTADO CON ERRORES, entonces ERROR; "
            + "en los demás casos, INFORMATIVO"
        );
        /**/
        idRecursoObtenido.setLocalizedLabel(ENGLISH, "temporal obtained resource");
        idRecursoObtenido.setLocalizedLabel(SPANISH, "recurso obtenido temporal");
        /**/
        nombreArchivoTem.setLocalizedLabel(ENGLISH, "temporal file");
        nombreArchivoTem.setLocalizedLabel(SPANISH, "archivo temporal");
        /**/
        codigoErrorTem.setLocalizedLabel(ENGLISH, "temporal message code");
        codigoErrorTem.setLocalizedLabel(SPANISH, "código del mensaje temporal");
        /**/
        descripcionErrorTem.setLocalizedLabel(ENGLISH, "temporal message");
        descripcionErrorTem.setLocalizedLabel(SPANISH, "mensaje temporal");
        /**/
        condicionEjeAnt.setLocalizedLabel(ENGLISH, "function execution previous condition");
        condicionEjeAnt.setLocalizedLabel(SPANISH, "condición anterior de ejecución de función");
        condicionEjeAnt.setLocalizedShortLabel(ENGLISH, "previous condition");
        condicionEjeAnt.setLocalizedShortLabel(SPANISH, "condición anterior");
        /**/
        ultimaTransaccion.setLocalizedLabel(ENGLISH, "last transaction");
        ultimaTransaccion.setLocalizedLabel(SPANISH, "última transacción");
        /**/
        transacciones.setLocalizedLabel(ENGLISH, "transaction count");
        transacciones.setLocalizedLabel(SPANISH, "cantidad de transacciones");
        /**/
        leido.setLocalizedLabel(ENGLISH, "read");
        leido.setLocalizedLabel(SPANISH, "leído");
        /**/
        descargado.setLocalizedLabel(ENGLISH, "downloaded");
        descargado.setLocalizedLabel(SPANISH, "descargado");
        /**/
        procesoAsincrono.setLocalizedLabel(ENGLISH, "asynchronous process");
        procesoAsincrono.setLocalizedLabel(SPANISH, "proceso asíncrono");
        /**/
        procesoCalendarizado.setLocalizedLabel(ENGLISH, "scheduled process");
        procesoCalendarizado.setLocalizedLabel(SPANISH, "proceso calendarizado");
        /**/
        procesoNativo.setLocalizedLabel(ENGLISH, "native process");
        procesoNativo.setLocalizedLabel(SPANISH, "proceso nativo");
        /**/
        procesoWeb.setLocalizedLabel(ENGLISH, "web service");
        procesoWeb.setLocalizedLabel(SPANISH, "servicio web");
        /**/
        subprocesos.setLocalizedLabel(ENGLISH, "subprocesses");
        subprocesos.setLocalizedLabel(SPANISH, "subprocesos");
        /**/
        subprocesosPendientes.setLocalizedLabel(ENGLISH, "pending subprocesses");
        subprocesosPendientes.setLocalizedLabel(SPANISH, "subprocesos pendientes");
        /**/
        subprocesosEnProgreso.setLocalizedLabel(ENGLISH, "running subprocesses");
        subprocesosEnProgreso.setLocalizedLabel(SPANISH, "subprocesos en progreso");
        /**/
        subprocesosSinErrores.setLocalizedLabel(ENGLISH, "subprocesses without errors");
        subprocesosSinErrores.setLocalizedLabel(SPANISH, "subprocesos sin errores");
        /**/
        subprocesosConErrores.setLocalizedLabel(ENGLISH, "subprocesses with errors");
        subprocesosConErrores.setLocalizedLabel(SPANISH, "subprocesos con errores");
        /**/
        subprocesosCancelados.setLocalizedLabel(ENGLISH, "cancelled subprocesses");
        subprocesosCancelados.setLocalizedLabel(SPANISH, "subprocesos cancelados");
        /**/
        procedimientoAfterUpdate.setLocalizedLabel(ENGLISH, "final procedure");
        procedimientoAfterUpdate.setLocalizedLabel(SPANISH, "procedimiento final");
        /**/
        // </editor-fold>
    }

    /**/
    protected Key ix_rastro_proceso_0001;

    @Override
    protected void settleKeys() {
        super.settleKeys();
        ix_rastro_proceso_0001.setUnique(false);
        ix_rastro_proceso_0001.newKeyField(idClaseRecursoValor, recursoValor);
    }

    /**/
    protected Tab tab110, tab120, tab130;

    @Override
    protected void settleTabs() {
        super.settleTabs();
        /**/
        tab110.newTabField(id, superior, fechaHoraInicioEjecucion, fechaHoraFinEjecucion, // 4
            usuario, funcion, // 4
            codigoClaseRecursoValor, nombreClaseRecursoValor, codigoRecurso, nombreRecurso, // 4
            condicionEjeFun, codigoError, descripcionError, severidadMensaje, // 4
            archivosCargados, nombreArchivo, direccionIp // 2
        );
        /**/
        tab120.newTabField(codigoUsuario, nombreUsuario, codigoFuncion, nombreFuncion, descripcionFuncion, paginaFuncion, tipoFuncion, tipoRastroFun, // 7
            idClaseRecursoValor, codigoClaseRecursoValor, nombreClaseRecursoValor, // 3
            recursoValor, idRecurso, versionRecurso, codigoRecurso, nombreRecurso, idPropietarioRecurso, idSegmentoRecurso, paginaRecurso, paginaRecursoObtenido, // 9
            leido, descargado, etiquetaLenguaje // 3
        );
        /**/
        tab130.newTabField(grupo, procesoAsincrono, procesoCalendarizado, procesoNativo, procesoWeb, // 5
            subprocesos, subprocesosPendientes, subprocesosEnProgreso, // 3
            subprocesosSinErrores, subprocesosConErrores, subprocesosCancelados, procedimientoAfterUpdate // 4
        );
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of RastroProceso's tabs">
        /**/
        tab110.setLocalizedLabel(ENGLISH, "general");
        tab110.setLocalizedLabel(SPANISH, "general");
        /**/
        tab120.setLocalizedLabel(ENGLISH, "details");
        tab120.setLocalizedLabel(SPANISH, "detalles");
        /**/
        tab130.setLocalizedLabel(ENGLISH, "process");
        tab130.setLocalizedLabel(SPANISH, "proceso");
        /**/
        // </editor-fold>
    }

    protected Segment finalizado, pendiente, mis, finalizadoActual, pendienteActual;

    protected Segment conArchivosCargados, conSuperior, conSubprocesos;

    protected Segment sinArchivosCargados, sinSuperior, sinSubprocesos;

    @Override
    protected void settleExpressions() {
        super.settleExpressions();
        /**/
        finalizado = condicionEjeFun.isIn(
            condicionEjeFun.EJECUTADO_SIN_ERRORES,
            condicionEjeFun.EJECUTADO_CON_ERRORES,
            condicionEjeFun.EJECUCION_CANCELADA
        );
        pendiente = and(procesoAsincrono, superior.isNull(), finalizado, not(leido), not(descargado));
        /**/
        mis = codigoUsuario.isEqualTo(CURRENT_USER_CODE);
        finalizadoActual = mis.and(finalizado);
        pendienteActual = mis.and(pendiente);
        /**/
        sinArchivosCargados = archivosCargados.isEqualTo(0);
        conArchivosCargados = archivosCargados.isGreaterThan(0);
        /**/
        sinSuperior = superior.isNull();
        conSuperior = superior.isNotNull();
        /**/
        sinSubprocesos = subprocesos.isEqualTo(0);
        conSubprocesos = subprocesos.isGreaterThan(0);
        /**/
        finalizado.setLocalizedCollectionLabel(ENGLISH, "processes that have already finished");
        finalizado.setLocalizedCollectionLabel(SPANISH, "procesos que ya han finalizado");
        finalizado.setLocalizedCollectionShortLabel(ENGLISH, "Finished processes");
        finalizado.setLocalizedCollectionShortLabel(SPANISH, "Procesos finalizados");
        finalizado.setLocalizedDescription(ENGLISH, "the process has already finished");
        finalizado.setLocalizedDescription(SPANISH, "el proceso ya ha finalizado");
        finalizado.setLocalizedErrorMessage(ENGLISH, "the process has not finished");
        finalizado.setLocalizedErrorMessage(SPANISH, "el proceso no ha finalizado");
        /**/
        pendiente.setLocalizedCollectionLabel(ENGLISH, "finished processes not read nor downloaded");
        pendiente.setLocalizedCollectionLabel(SPANISH, "procesos finalizados no leídos ni descargados");
        pendiente.setLocalizedCollectionShortLabel(ENGLISH, "Unread and undownloaded processes");
        pendiente.setLocalizedCollectionShortLabel(SPANISH, "Procesos no leídos ni descargados");
        pendiente.setLocalizedDescription(ENGLISH, "the process has not been read or downloaded");
        pendiente.setLocalizedDescription(SPANISH, "el proceso no ha sido leído o descargado");
        pendiente.setLocalizedErrorMessage(ENGLISH, "the process has already been read or downloaded");
        pendiente.setLocalizedErrorMessage(SPANISH, "el proceso ya fue leído o descargado");
        /**/
        mis.setLocalizedCollectionLabel(ENGLISH, "processes executed by the current user");
        mis.setLocalizedCollectionLabel(SPANISH, "procesos ejecutados por el usuario actual");
        mis.setLocalizedCollectionShortLabel(ENGLISH, "My processes");
        mis.setLocalizedCollectionShortLabel(SPANISH, "Mis procesos");
        /**/
        finalizadoActual.setLocalizedCollectionLabel(ENGLISH, "processes executed by the current user that have already finished");
        finalizadoActual.setLocalizedCollectionLabel(SPANISH, "procesos ejecutados por el usuario actual que ya han finalizado");
        finalizadoActual.setLocalizedCollectionShortLabel(ENGLISH, "My finished processes");
        finalizadoActual.setLocalizedCollectionShortLabel(SPANISH, "Mis procesos finalizados");
        finalizadoActual.setLocalizedDescription(ENGLISH, "the process has already finished");
        finalizadoActual.setLocalizedDescription(SPANISH, "el proceso ya ha finalizado");
        finalizadoActual.setLocalizedErrorMessage(ENGLISH, "the process has not finished");
        finalizadoActual.setLocalizedErrorMessage(SPANISH, "el proceso no ha finalizado");
        /**/
        pendienteActual.setLocalizedCollectionLabel(ENGLISH, "finished processes executed by the current user not read nor downloaded");
        pendienteActual.setLocalizedCollectionLabel(SPANISH, "procesos finalizados ejecutados por el usuario actual no leídos ni descargados");
        pendienteActual.setLocalizedCollectionShortLabel(ENGLISH, "My unread and undownloaded processes");
        pendienteActual.setLocalizedCollectionShortLabel(SPANISH, "Mis procesos no leídos ni descargados");
        pendienteActual.setLocalizedDescription(ENGLISH, "the process has not been read or downloaded");
        pendienteActual.setLocalizedDescription(SPANISH, "el proceso no ha sido leído o descargado");
        pendienteActual.setLocalizedErrorMessage(ENGLISH, "the process has already been read or downloaded");
        pendienteActual.setLocalizedErrorMessage(SPANISH, "el proceso ya fue leído o descargado");
        /**/
        sinArchivosCargados.setLocalizedCollectionLabel(ENGLISH, "processes without any uploaded files");
        sinArchivosCargados.setLocalizedCollectionLabel(SPANISH, "procesos sin ningún archivo cargado");
        sinArchivosCargados.setLocalizedCollectionShortLabel(ENGLISH, "Processes without uploaded files");
        sinArchivosCargados.setLocalizedCollectionShortLabel(SPANISH, "Procesos sin archivos cargados");
        sinArchivosCargados.setLocalizedDescription(ENGLISH, "the process has no uploaded files");
        sinArchivosCargados.setLocalizedDescription(SPANISH, "el proceso no tiene archivos cargados");
        sinArchivosCargados.setLocalizedErrorMessage(ENGLISH, "the process has uploaded files");
        sinArchivosCargados.setLocalizedErrorMessage(SPANISH, "el proceso tiene archivos cargados");
        /**/
        conArchivosCargados.setLocalizedCollectionLabel(ENGLISH, "processes with one or more uploaded files");
        conArchivosCargados.setLocalizedCollectionLabel(SPANISH, "procesos con uno o más archivos cargados");
        conArchivosCargados.setLocalizedCollectionShortLabel(ENGLISH, "Processes with uploaded files");
        conArchivosCargados.setLocalizedCollectionShortLabel(SPANISH, "Procesos con archivos cargados");
        conArchivosCargados.setLocalizedDescription(ENGLISH, "the process has uploaded files");
        conArchivosCargados.setLocalizedDescription(SPANISH, "el proceso tiene archivos cargados");
        conArchivosCargados.setLocalizedErrorMessage(ENGLISH, "the process has no uploaded files");
        conArchivosCargados.setLocalizedErrorMessage(SPANISH, "el proceso no tiene archivos cargados");
        /**/
        sinSuperior.setLocalizedCollectionLabel(ENGLISH, "processes without parent process");
        sinSuperior.setLocalizedCollectionLabel(SPANISH, "procesos sin proceso superior");
        sinSuperior.setLocalizedCollectionShortLabel(ENGLISH, "Main processes");
        sinSuperior.setLocalizedCollectionShortLabel(SPANISH, "Procesos principales");
        sinSuperior.setLocalizedDescription(ENGLISH, "the process has no parent process");
        sinSuperior.setLocalizedDescription(SPANISH, "el proceso no tiene proceso superior");
        sinSuperior.setLocalizedErrorMessage(ENGLISH, "the process has parent process");
        sinSuperior.setLocalizedErrorMessage(SPANISH, "el proceso tiene proceso superior");
        /**/
        conSuperior.setLocalizedCollectionLabel(ENGLISH, "processes with parent process");
        conSuperior.setLocalizedCollectionLabel(SPANISH, "procesos con proceso superior");
        conSuperior.setLocalizedCollectionShortLabel(ENGLISH, "Subprocesses");
        conSuperior.setLocalizedCollectionShortLabel(SPANISH, "Subprocesos");
        conSuperior.setLocalizedDescription(ENGLISH, "the process has parent process");
        conSuperior.setLocalizedDescription(SPANISH, "el proceso tiene proceso superior");
        conSuperior.setLocalizedErrorMessage(ENGLISH, "the process has no parent process");
        conSuperior.setLocalizedErrorMessage(SPANISH, "el proceso no tiene proceso superior");
        /**/
        sinSubprocesos.setLocalizedCollectionLabel(ENGLISH, "processes without any subprocesses");
        sinSubprocesos.setLocalizedCollectionLabel(SPANISH, "procesos sin ningún subproceso");
        sinSubprocesos.setLocalizedCollectionShortLabel(ENGLISH, "Processes without subprocesses");
        sinSubprocesos.setLocalizedCollectionShortLabel(SPANISH, "Procesos sin subprocesos");
        sinSubprocesos.setLocalizedDescription(ENGLISH, "the process has no subprocesses");
        sinSubprocesos.setLocalizedDescription(SPANISH, "el proceso no tiene subprocesos");
        sinSubprocesos.setLocalizedErrorMessage(ENGLISH, "the process has subprocesses");
        sinSubprocesos.setLocalizedErrorMessage(SPANISH, "el proceso tiene subprocesos");
        /**/
        conSubprocesos.setLocalizedCollectionLabel(ENGLISH, "processes with one or more subprocesses");
        conSubprocesos.setLocalizedCollectionLabel(SPANISH, "procesos con uno o más subprocesos");
        conSubprocesos.setLocalizedCollectionShortLabel(ENGLISH, "Processes with subprocesses");
        conSubprocesos.setLocalizedCollectionShortLabel(SPANISH, "Procesos con subprocesos");
        conSubprocesos.setLocalizedDescription(ENGLISH, "the process has subprocesses");
        conSubprocesos.setLocalizedDescription(SPANISH, "el proceso tiene subprocesos");
        conSubprocesos.setLocalizedErrorMessage(ENGLISH, "the process has no subprocesses");
        conSubprocesos.setLocalizedErrorMessage(SPANISH, "el proceso no tiene subprocesos");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleFilters() {
        super.settleFilters();
        /**/
        setMasterDetailFilter(superior.conSubprocesos);
        /**/
        addSelectSegment(finalizado, pendiente);
        addSelectSegment(mis, true);
        addSelectSegment(finalizadoActual, pendienteActual);
        addSelectSegment(sinArchivosCargados, conArchivosCargados);
        addSelectSegment(sinSubprocesos, conSubprocesos);
        addSelectSegment(sinSuperior, conSuperior);
        /**/
        usuario.setRenderingFilter(usuario.id.isNotNull()); // mostrar si, y solo si, el usuario no ha sido eliminado
        /**/
    }

    protected MarcarComoLeido marcarComoLeido;

    @OperationClass(access = OperationAccess.RESTRICTED)
    public class MarcarComoLeido extends ProcessOperation {

        @InstanceReference
        protected RastroProceso proceso;

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            /**/
            // <editor-fold defaultstate="collapsed" desc="localization of MarcarComoLeido's attributes">
            /**/
            setLocalizedLabel(ENGLISH, "mark as read");
            setLocalizedLabel(SPANISH, "marcar como leído");
            /**/
            setLocalizedDescription(ENGLISH, "mark process as read");
            setLocalizedDescription(SPANISH, "marcar proceso como leído");
            /**/
            setLocalizedSuccessMessage(ENGLISH, "the process was marked as read");
            setLocalizedSuccessMessage(SPANISH, "el proceso fue marcado como leído");
            /**/
            // </editor-fold>
            /**/
        }

        @Override
        protected void settleParameters() {
            super.settleParameters();
            /**/
            proceso.leido.setCurrentValue(true);
            /**/
            // <editor-fold defaultstate="collapsed" desc="localization of MarcarComoLeido's parameters">
            /**/
            proceso.setLocalizedLabel(ENGLISH, "process");
            proceso.setLocalizedLabel(SPANISH, "proceso");
            /**/
            // </editor-fold>
            /**/
        }

        protected Check check101, check102, check103, check104, check105;

        @Override
        protected void settleExpressions() {
            super.settleExpressions();
            /**/
            check101 = proceso.condicionEjeFun.isIn(
                proceso.condicionEjeFun.EJECUTADO_SIN_ERRORES,
                proceso.condicionEjeFun.EJECUTADO_CON_ERRORES,
                proceso.condicionEjeFun.EJECUCION_CANCELADA
            );
            check102 = proceso.codigoUsuario.isEqualTo(CURRENT_USER_CODE).or(proceso.usuario.isEqualTo(proceso.usuario.OPERADOR));
            check103 = not(proceso.leido.or(proceso.descargado));
            check104 = proceso.procesoAsincrono.isTrue();
            check105 = proceso.superior.isNull();
            /**/
            // <editor-fold defaultstate="collapsed" desc="localization of MarcarComoLeido's expressions">
            /**/
            check101.setLocalizedDescription(ENGLISH, "the process has finished");
            check101.setLocalizedDescription(SPANISH, "el proceso ha terminado");
            check101.setLocalizedErrorMessage(ENGLISH, "it is not allowed to mark as read those processes that have not finished");
            check101.setLocalizedErrorMessage(SPANISH, "no se permite marcar como leídos aquellos procesos que no hayan finalizado");
            /**/
            check102.setLocalizedDescription(ENGLISH, "the process belongs to the current user");
            check102.setLocalizedDescription(SPANISH, "el proceso le pertenece al usuario actual");
            check102.setLocalizedErrorMessage(ENGLISH, "the process does not belong to your user");
            check102.setLocalizedErrorMessage(SPANISH, "el proceso no le pertenece a su usuario");
            /**/
            check103.setLocalizedDescription(ENGLISH, "the process has not been read nor downloaded");
            check103.setLocalizedDescription(SPANISH, "el proceso no ha sido leído ni descargado");
            check103.setLocalizedErrorMessage(ENGLISH, "the process has been read or downloaded");
            check103.setLocalizedErrorMessage(SPANISH, "el proceso ya fue leído o descargado");
            /**/
            check104.setLocalizedDescription(ENGLISH, "the process is asynchronous");
            check104.setLocalizedDescription(SPANISH, "el proceso es asíncrono");
            check104.setLocalizedErrorMessage(ENGLISH, "the process is synchronous");
            check104.setLocalizedErrorMessage(SPANISH, "el proceso es sincronizado");
            /**/
            check105.setLocalizedDescription(ENGLISH, "the process is not a subprocess");
            check105.setLocalizedDescription(SPANISH, "el proceso no es un subproceso");
            check105.setLocalizedErrorMessage(ENGLISH, "the process is a subprocess");
            check105.setLocalizedErrorMessage(SPANISH, "el proceso es un subproceso");
            /**/
            // </editor-fold>
            /**/
        }

    }

    protected MarcarComoNoLeido marcarComoNoLeido;

    @OperationClass(access = OperationAccess.RESTRICTED)
    public class MarcarComoNoLeido extends ProcessOperation {

        @InstanceReference
        protected RastroProceso proceso;

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            /**/
            // <editor-fold defaultstate="collapsed" desc="localization of MarcarComoNoLeido's attributes">
            /**/
            setLocalizedLabel(ENGLISH, "mark as unread and undownloaded");
            setLocalizedLabel(SPANISH, "marcar como no leído");
            /**/
            setLocalizedDescription(ENGLISH, "mark process as unread and undownloaded");
            setLocalizedDescription(SPANISH, "marcar proceso como no leído");
            /**/
            setLocalizedSuccessMessage(ENGLISH, "the process was marked as unread and undownloaded");
            setLocalizedSuccessMessage(SPANISH, "el proceso fue marcado como no leído");
            /**/
            // </editor-fold>
            /**/
        }

        @Override
        protected void settleParameters() {
            super.settleParameters();
            /**/
            proceso.leido.setCurrentValue(false);
            proceso.descargado.setCurrentValue(false);
            /**/
            // <editor-fold defaultstate="collapsed" desc="localization of MarcarComoNoLeido's parameters">
            /**/
            proceso.setLocalizedLabel(ENGLISH, "process");
            proceso.setLocalizedLabel(SPANISH, "proceso");
            /**/
            // </editor-fold>
            /**/
        }

        protected Check check101, check102, check103, check104, check105;

        @Override
        protected void settleExpressions() {
            super.settleExpressions();
            /**/
            check101 = proceso.condicionEjeFun.isIn(
                proceso.condicionEjeFun.EJECUTADO_SIN_ERRORES,
                proceso.condicionEjeFun.EJECUTADO_CON_ERRORES,
                proceso.condicionEjeFun.EJECUCION_CANCELADA
            );
            check102 = proceso.codigoUsuario.isEqualTo(CURRENT_USER_CODE).or(proceso.usuario.isEqualTo(proceso.usuario.OPERADOR));
            check103 = proceso.leido.or(proceso.descargado);
            check104 = proceso.procesoAsincrono.isTrue();
            check105 = proceso.superior.isNull();
            /**/
            // <editor-fold defaultstate="collapsed" desc="localization of MarcarComoNoLeido's expressions">
            /**/
            check101.setLocalizedDescription(ENGLISH, "the process has finished");
            check101.setLocalizedDescription(SPANISH, "el proceso ha terminado");
            check101.setLocalizedErrorMessage(ENGLISH, "it is not allowed to mark as unread those processes that have not finished");
            check101.setLocalizedErrorMessage(SPANISH, "no se permite marcar como no leídos aquellos procesos que no hayan finalizado");
            /**/
            check102.setLocalizedDescription(ENGLISH, "the process belongs to the current user");
            check102.setLocalizedDescription(SPANISH, "el proceso le pertenece al usuario actual");
            check102.setLocalizedErrorMessage(ENGLISH, "the process does not belong to your user");
            check102.setLocalizedErrorMessage(SPANISH, "el proceso no le pertenece a su usuario");
            /**/
            check103.setLocalizedDescription(ENGLISH, "the process has been read or downloaded");
            check103.setLocalizedDescription(SPANISH, "el proceso ya fue leído o descargado");
            check103.setLocalizedErrorMessage(ENGLISH, "the process has not been read nor downloaded");
            check103.setLocalizedErrorMessage(SPANISH, "el proceso no ha sido leído ni descargado");
            /**/
            check104.setLocalizedDescription(ENGLISH, "the process is asynchronous");
            check104.setLocalizedDescription(SPANISH, "el proceso es asíncrono");
            check104.setLocalizedErrorMessage(ENGLISH, "the process is synchronous");
            check104.setLocalizedErrorMessage(SPANISH, "el proceso es sincronizado");
            /**/
            check105.setLocalizedDescription(ENGLISH, "the process is not a subprocess");
            check105.setLocalizedDescription(SPANISH, "el proceso no es un subproceso");
            check105.setLocalizedErrorMessage(ENGLISH, "the process is a subprocess");
            check105.setLocalizedErrorMessage(SPANISH, "el proceso es un subproceso");
            /**/
            // </editor-fold>
            /**/
        }

    }

}
