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
import meta.entidad.comun.configuracion.basica.SubtipoGrafico;
import meta.entidad.comun.configuracion.basica.TipoFuncion;
import meta.entidad.comun.configuracion.basica.TipoGrafico;
import meta.entidad.comun.configuracion.basica.TipoInforme;
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
@EntityTableView(enabled = Kleenean.TRUE, responsiveMode = TableResponsiveMode.PRIORITY, readingViewBelowTableSnippet = "/resources/snippets/base/entity/RastroInforme/overlayPanel")
@EntityDetailView(enabled = Kleenean.TRUE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.TRUE, menu = Kleenean.FALSE)
@EntityTriggers(afterValue = Kleenean.TRUE)
@EntityWarnings(aboutBusinessKey = Kleenean.FALSE)
public class RastroInforme extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public RastroInforme(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    @PropertyField(hidden = Kleenean.FALSE, detail = Kleenean.TRUE, heading = Kleenean.TRUE)
    public LongProperty id;

    @ColumnField(nullable = Kleenean.FALSE, indexed = Kleenean.TRUE)
    @PropertyField(responsivePriority = 6, sequence = 100, table = Kleenean.TRUE, report = Kleenean.TRUE, search = Kleenean.TRUE, heading = Kleenean.FALSE, overlay = Kleenean.TRUE)
    public TimestampProperty fechaHoraInicioEjecucion;

    @PropertyField(sequence = 100)
    public TimestampProperty fechaHoraFinEjecucion;

    @ColumnField(indexed = Kleenean.TRUE)
    @PropertyField(sequence = 100, table = Kleenean.TRUE, search = Kleenean.TRUE, report = Kleenean.TRUE, overlay = Kleenean.TRUE)
    @StringField(maxLength = 200)
    public StringProperty nombreInforme;

    @ColumnField(indexed = Kleenean.TRUE)
    @PropertyField(responsivePriority = 6, sequence = 200, table = Kleenean.TRUE, search = Kleenean.TRUE, report = Kleenean.TRUE, overlay = Kleenean.TRUE)
    @StringField(maxLength = 5)
    public StringProperty formatoInforme;

    @PropertyField(sequence = 410, table = Kleenean.FALSE, search = Kleenean.FALSE, report = Kleenean.FALSE)
    public IntegerProperty limiteFilas;

    @OwnerProperty
    @SegmentProperty
    @ColumnField(nullable = Kleenean.TRUE)
//  20231209: remove foreign-key referring to Usuario because it might cause ARJUNA012117 and/or ARJUNA012121
//  @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(sequence = 250, table = Kleenean.FALSE, export = Kleenean.FALSE, report = Kleenean.FALSE, heading = Kleenean.FALSE, overlay = Kleenean.FALSE, search = Kleenean.FALSE, filter = Kleenean.FALSE, column = Kleenean.FALSE) // hidden until 02/11/2024
    @QueryMapping(mapKeyProperties = Kleenean.FALSE)
    public Usuario usuario;

    @ColumnField(indexed = Kleenean.TRUE)
    @PropertyField(responsivePriority = 6, sequence = 250, table = Kleenean.TRUE, search = Kleenean.TRUE, report = Kleenean.TRUE)
    @StringField(maxLength = MAX_EMAIL_ADDRESS_LENGTH, displayLength = 36) // maxLength = 36 until 01/12/2023
    public StringProperty codigoUsuario;

    @ColumnField(indexed = Kleenean.TRUE)
    @PropertyField(sequence = 250, table = Kleenean.FALSE, search = Kleenean.TRUE, report = Kleenean.TRUE, overlay = Kleenean.TRUE, anchor = "codigoUsuario", anchorType = AnchorType.INLINE)
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
    @PropertyField(sequence = 300, table = Kleenean.FALSE, export = Kleenean.FALSE, report = Kleenean.FALSE, heading = Kleenean.FALSE, overlay = Kleenean.FALSE, search = Kleenean.FALSE, filter = Kleenean.FALSE, column = Kleenean.FALSE) // hidden until 11/01/2025
    @QueryMapping(mapKeyProperties = Kleenean.FALSE)
    public Funcion funcion;

//  @ColumnField(indexed = Kleenean.TRUE)
    @PropertyField(sequence = 300)
    @StringField(maxLength = 200)
    public StringProperty codigoFuncion;

//  @ColumnField(indexed = Kleenean.TRUE)
    @PropertyField(sequence = 300, table = Kleenean.FALSE, search = Kleenean.TRUE, report = Kleenean.TRUE, heading = Kleenean.FALSE, overlay = Kleenean.TRUE, anchor = "codigoFuncion", anchorType = AnchorType.INLINE)
    @StringField(maxLength = 200)
    public StringProperty nombreFuncion;

    @ColumnField(calculable = Kleenean.TRUE)
    @PropertyField(table = Kleenean.FALSE)
    @StringField(maxLength = 0)
    public StringProperty descripcionFuncion;

    @UniformResourceLocator(urlType = UrlType.INTERNAL, urlDisplayType = UrlDisplayType.BUTTON)
    @PropertyField(sequence = 300, hidden = Kleenean.TRUE)
    public StringProperty paginaFuncion;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(sequence = 300)
    public TipoFuncion tipoFuncion;

    @PropertyField(sequence = 300, hidden = Kleenean.TRUE)
    public LongProperty idClaseRecursoValor;

    @ColumnField(indexed = Kleenean.TRUE)
    @PropertyField(sequence = 300, search = Kleenean.TRUE)
    @StringField(maxLength = 100)
    public StringProperty codigoClaseRecursoValor;

    @ColumnField(indexed = Kleenean.TRUE)
    @PropertyField(sequence = 300, search = Kleenean.TRUE, overlay = Kleenean.TRUE, anchor = "codigoClaseRecursoValor", anchorType = AnchorType.INLINE)
    @StringField(maxLength = 100)
    public StringProperty nombreClaseRecursoValor;

    @ColumnField(nullable = Kleenean.TRUE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(sequence = 300, hidden = Kleenean.TRUE)
    public RecursoValor recursoValor;

    @PropertyField(sequence = 300, hidden = Kleenean.TRUE)
    public LongProperty idRecurso;

    @PropertyField(sequence = 300, hidden = Kleenean.TRUE)
    public LongProperty versionRecurso;

    @ColumnField(indexed = Kleenean.FALSE)
    @PropertyField(responsivePriority = 5, sequence = 300, table = Kleenean.TRUE, report = Kleenean.TRUE, search = Kleenean.TRUE, overlay = Kleenean.TRUE)
    public StringProperty codigoRecurso;

    @ColumnField(indexed = Kleenean.FALSE)
    @PropertyField(sequence = 300, table = Kleenean.FALSE, report = Kleenean.TRUE, search = Kleenean.TRUE, overlay = Kleenean.TRUE, anchor = "codigoRecurso", anchorType = AnchorType.INLINE)
    public StringProperty nombreRecurso;

    @PropertyField(sequence = 300, hidden = Kleenean.TRUE)
    public LongProperty idPropietarioRecurso;

    @PropertyField(sequence = 300, hidden = Kleenean.TRUE)
    public LongProperty idSegmentoRecurso;

    @UniformResourceLocator(urlType = UrlType.INTERNAL, urlDisplayType = UrlDisplayType.BUTTON)
    @PropertyField(sequence = 300, hidden = Kleenean.TRUE)
    public StringProperty paginaRecurso;

    @PropertyField(sequence = 400)
    @StringField(maxLength = 0)
    public StringProperty criteriosBusqueda;

    @PropertyField(sequence = 400)
    @StringField(maxLength = 0)
    public StringProperty instruccionSelect;

    @PropertyField(sequence = 400)
    public BooleanProperty selectRestringido;

    @PropertyField(sequence = 400)
    public LongProperty filasSeleccionadas;

    @PropertyField(sequence = 410)
    @StringField(maxLength = 20)
    public StringProperty etiquetaLenguaje;

    @ColumnField(nullable = Kleenean.FALSE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(sequence = 500, table = Kleenean.TRUE, report = Kleenean.TRUE, heading = Kleenean.TRUE, overlay = Kleenean.TRUE)
    public CondicionEjeFun condicionEjeFun;

    @FileReference(loadField = "fechaHoraInicioEjecucion")
    @PropertyField(sequence = 150, table = Kleenean.TRUE, report = Kleenean.FALSE, search = Kleenean.FALSE,
        readingTableSnippet = "/resources/snippets/base/entity/RastroInforme/overlayPanelLink")
    public StringProperty nombreArchivo;

    @PropertyField(sequence = 500)
    @StringField(maxLength = 0)
    public StringProperty descripcionError;

    @ColumnField(nullable = Kleenean.TRUE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(responsivePriority = 6, sequence = 200, table = Kleenean.TRUE, search = Kleenean.TRUE, report = Kleenean.TRUE, overlay = Kleenean.TRUE, anchor = "formatoInforme", anchorType = AnchorType.INLINE)
    public TipoInforme tipoInforme;

    @ColumnField(nullable = Kleenean.TRUE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(sequence = 200, table = Kleenean.FALSE, search = Kleenean.FALSE, report = Kleenean.FALSE, overlay = Kleenean.TRUE, anchor = "formatoInforme", anchorType = AnchorType.INLINE)
    public TipoGrafico tipoGrafico;

    @ColumnField(nullable = Kleenean.TRUE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(sequence = 200, table = Kleenean.FALSE, search = Kleenean.FALSE, report = Kleenean.FALSE, overlay = Kleenean.TRUE, anchor = "formatoInforme", anchorType = AnchorType.INLINE)
    public SubtipoGrafico subtipoGrafico;

    @ColumnField(nullable = Kleenean.TRUE)
//  20231209: remove foreign-key referring to Usuario because it might cause ARJUNA012117 and/or ARJUNA012121
//  @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(sequence = 250, table = Kleenean.FALSE, export = Kleenean.FALSE, report = Kleenean.FALSE, heading = Kleenean.FALSE, overlay = Kleenean.FALSE, search = Kleenean.FALSE, filter = Kleenean.FALSE, column = Kleenean.FALSE) // hidden until 02/11/2024
    @QueryMapping(mapKeyProperties = Kleenean.FALSE)
    public Usuario remitente;

    @ColumnField(indexed = Kleenean.TRUE)
    @PropertyField(responsivePriority = 6, sequence = 250, table = Kleenean.FALSE, search = Kleenean.FALSE, report = Kleenean.TRUE)
    @StringField(maxLength = MAX_EMAIL_ADDRESS_LENGTH, displayLength = 36) // maxLength = 36 until 01/12/2023
    public StringProperty codigoRemitente;

    @ColumnField(indexed = Kleenean.TRUE)
    @PropertyField(sequence = 250, table = Kleenean.FALSE, search = Kleenean.FALSE, report = Kleenean.TRUE, overlay = Kleenean.TRUE, anchor = "codigoRemitente", anchorType = AnchorType.INLINE)
    @StringField(maxLength = 100)
    public StringProperty nombreRemitente;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(sequence = 210)
    public BooleanProperty leido;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(sequence = 210)
    public BooleanProperty descargado;

    @ColumnField(nullable = Kleenean.FALSE, indexed = Kleenean.FALSE)
    @PropertyField(responsivePriority = 6, sequence = 200, table = Kleenean.TRUE, search = Kleenean.FALSE, report = Kleenean.TRUE, overlay = Kleenean.TRUE)
    public BooleanProperty personal;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of RastroInforme's attributes">
        setLocalizedLabel(ENGLISH, "report audit trail");
        setLocalizedLabel(SPANISH, "rastro de auditoría de informe");
        setLocalizedShortLabel(ENGLISH, "report trail");
        setLocalizedShortLabel(SPANISH, "rastro de informe");
        setLocalizedCollectionLabel(ENGLISH, "Report Audit Trails");
        setLocalizedCollectionLabel(SPANISH, "Rastros de Auditoría de Informes");
        setLocalizedCollectionShortLabel(ENGLISH, "Report Trails");
        setLocalizedCollectionShortLabel(SPANISH, "Rastros de Informes");
        /**/
        setLocalizedDescription(ENGLISH, "Each instance of " + b("Report Audit Trails") + " represents an "
            + "audit trail of the execution of export and report operations."
            + "");
        setLocalizedDescription(SPANISH, "Cada instancia de " + b("Rastros de Auditoría de Informes") + " representa un "
            + "rastro de auditoría de la generación de archivos e informes."
            + "");
        /**/
        setLocalizedShortDescription(ENGLISH, "audit trail of the execution of export and report operations");
        setLocalizedShortDescription(SPANISH, "rastro de auditoría de la generación de archivos e informes");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        /**/
        fechaHoraInicioEjecucion.setInitialValue(SpecialTemporalValue.CURRENT_TIMESTAMP);
        fechaHoraInicioEjecucion.setDefaultValue(SpecialTemporalValue.CURRENT_TIMESTAMP);
        /*
        nombreInforme.setDefaultValue(fechaHoraInicioEjecucion.toLocaleString());
        nombreInforme.setPrimalDefaultValue("Lorem ipsum dolor sit amet");
        /**/
        descripcionFuncion.setCalculableValueExpression(funcion.descripcionFuncion);
        /**/
        nombreArchivo.setFileDownloadStopFunction("hideDialogoMostrarStatusDownloadPlusRefresh");
        nombreArchivo.setFileViewerDialogReturnUpdate("mainForm", "northForm", "@(.xs-bar-updatable-component)");
        /**/
        leido.setInitialValue(false);
        leido.setDefaultValue(false);
        /**/
        descargado.setInitialValue(false);
        descargado.setDefaultValue(false);
        /**/
        personal.setInitialValue(false);
        personal.setDefaultValue(false);
        /**/
        condicionEjeFun.setInitialValue(condicionEjeFun.EJECUCION_PENDIENTE);
        condicionEjeFun.setDefaultValue(condicionEjeFun.EJECUCION_PENDIENTE);
        /*
        RastroUtils.setGraphicImageExpressions(condicionEjeFun);
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of RastroInforme's properties">
        /**/
        fechaHoraInicioEjecucion.setLocalizedLabel(ENGLISH, "start");
        fechaHoraInicioEjecucion.setLocalizedLabel(SPANISH, "inicio");
        /**/
        fechaHoraFinEjecucion.setLocalizedLabel(ENGLISH, "end");
        fechaHoraFinEjecucion.setLocalizedLabel(SPANISH, "fin");
        /**/
        nombreInforme.setLocalizedLabel(ENGLISH, "report");
        nombreInforme.setLocalizedLabel(SPANISH, "informe");
        /**/
        formatoInforme.setLocalizedLabel(ENGLISH, "format");
        formatoInforme.setLocalizedLabel(SPANISH, "formato");
        formatoInforme.setLocalizedAnchorLabel(ENGLISH, "format, type...");
        formatoInforme.setLocalizedAnchorLabel(SPANISH, "formato, tipo...");
        /**/
        tipoInforme.setLocalizedLabel(ENGLISH, "type");
        tipoInforme.setLocalizedLabel(SPANISH, "tipo");
        /**/
        tipoGrafico.setLocalizedLabel(ENGLISH, "chart");
        tipoGrafico.setLocalizedLabel(SPANISH, "gráfico");
        /**/
        subtipoGrafico.setLocalizedLabel(ENGLISH, "subtype");
        subtipoGrafico.setLocalizedLabel(SPANISH, "subtipo");
        /**/
        leido.setLocalizedLabel(ENGLISH, "read");
        leido.setLocalizedLabel(SPANISH, "leído");
        /**/
        descargado.setLocalizedLabel(ENGLISH, "downloaded");
        descargado.setLocalizedLabel(SPANISH, "descargado");
        /**/
        personal.setLocalizedTooltip(ENGLISH, "user-defined report");
        personal.setLocalizedTooltip(SPANISH, "informe definido por el usuario");
        /**/
        limiteFilas.setLocalizedLabel(ENGLISH, "limit");
        limiteFilas.setLocalizedLabel(SPANISH, "límite");
        /**/
        usuario.setLocalizedDescription(ENGLISH, "user who executed or received a copy of the report");
        usuario.setLocalizedDescription(SPANISH, "usuario que ejecutó o recibió una copia del informe");
        usuario.setLocalizedShortDescription(ENGLISH, "user who executed or received a copy of the report");
        usuario.setLocalizedShortDescription(SPANISH, "usuario que ejecutó o recibió una copia del informe");
        usuario.setLocalizedLabel(ENGLISH, "user");
        usuario.setLocalizedLabel(SPANISH, "usuario");
        /**/
        codigoUsuario.setLocalizedDescription(ENGLISH, "code of the user who executed or received a copy of the report");
        codigoUsuario.setLocalizedDescription(SPANISH, "código del usuario que ejecutó o recibió una copia del informe");
        codigoUsuario.setLocalizedLabel(ENGLISH, "user code");
        codigoUsuario.setLocalizedLabel(SPANISH, "código del usuario");
        codigoUsuario.setLocalizedColumnHeader(ENGLISH, "user");
        codigoUsuario.setLocalizedColumnHeader(SPANISH, "usuario");
        codigoUsuario.setLocalizedAnchorLabel(ENGLISH, "user");
        codigoUsuario.setLocalizedAnchorLabel(SPANISH, "usuario");
        codigoUsuario.setLocalizedAnchoredLabel(ENGLISH, "code");
        codigoUsuario.setLocalizedAnchoredLabel(SPANISH, "código");
        /**/
        nombreUsuario.setLocalizedDescription(ENGLISH, "name of the user who executed or received a copy of the report");
        nombreUsuario.setLocalizedDescription(SPANISH, "nombre del usuario que ejecutó o recibió una copia del informe");
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
        remitente.setLocalizedDescription(ENGLISH, "user who sent the report");
        remitente.setLocalizedDescription(SPANISH, "usuario que envió el informe");
        remitente.setLocalizedShortDescription(ENGLISH, "user who sent the report");
        remitente.setLocalizedShortDescription(SPANISH, "usuario que envió el informe");
        remitente.setLocalizedLabel(ENGLISH, "sender");
        remitente.setLocalizedLabel(SPANISH, "remitente");
        /**/
        codigoRemitente.setLocalizedDescription(ENGLISH, "código del usuario of the user who sent the report");
        codigoRemitente.setLocalizedDescription(SPANISH, "usuario que envió el informe");
        codigoRemitente.setLocalizedLabel(ENGLISH, "sender code");
        codigoRemitente.setLocalizedLabel(SPANISH, "código del remitente");
        codigoRemitente.setLocalizedColumnHeader(ENGLISH, "sender");
        codigoRemitente.setLocalizedColumnHeader(SPANISH, "remitente");
        codigoRemitente.setLocalizedAnchorLabel(ENGLISH, "sender");
        codigoRemitente.setLocalizedAnchorLabel(SPANISH, "remitente");
        codigoRemitente.setLocalizedAnchoredLabel(ENGLISH, "code");
        codigoRemitente.setLocalizedAnchoredLabel(SPANISH, "código");
        /**/
        nombreRemitente.setLocalizedDescription(ENGLISH, "name of the user who sent the report");
        nombreRemitente.setLocalizedDescription(SPANISH, "nombre del usuario que envió el informe");
        nombreRemitente.setLocalizedLabel(ENGLISH, "sender name");
        nombreRemitente.setLocalizedLabel(SPANISH, "nombre del remitente");
        nombreRemitente.setLocalizedAnchoredLabel(ENGLISH, "name");
        nombreRemitente.setLocalizedAnchoredLabel(SPANISH, "nombre");
        /**/
        funcion.setLocalizedLabel(ENGLISH, "function");
        funcion.setLocalizedLabel(SPANISH, "función");
        /**/
        tipoFuncion.setLocalizedLabel(ENGLISH, "function type");
        tipoFuncion.setLocalizedLabel(SPANISH, "tipo de función");
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
        idClaseRecursoValor.setLocalizedDescription(ENGLISH, "resource class of the report");
        idClaseRecursoValor.setLocalizedDescription(SPANISH, "clase de recurso del informe");
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
        codigoRecurso.setLocalizedDescription(ENGLISH, "code of the resource on which the report was executed");
        codigoRecurso.setLocalizedDescription(SPANISH, "código del recurso sobre el que se ejecutó el informe");
        codigoRecurso.setLocalizedLabel(ENGLISH, "resource code");
        codigoRecurso.setLocalizedLabel(SPANISH, "código del recurso");
        codigoRecurso.setLocalizedColumnHeader(ENGLISH, "resource");
        codigoRecurso.setLocalizedColumnHeader(SPANISH, "recurso");
        codigoRecurso.setLocalizedAnchorLabel(ENGLISH, "resource");
        codigoRecurso.setLocalizedAnchorLabel(SPANISH, "recurso");
        codigoRecurso.setLocalizedAnchoredLabel(ENGLISH, "code");
        codigoRecurso.setLocalizedAnchoredLabel(SPANISH, "código");
        /**/
        nombreRecurso.setLocalizedDescription(ENGLISH, "name of the resource on which the report was executed");
        nombreRecurso.setLocalizedDescription(SPANISH, "nombre del recurso sobre el que se ejecutó el informe");
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
        criteriosBusqueda.setLocalizedLabel(ENGLISH, "search criteria");
        criteriosBusqueda.setLocalizedLabel(SPANISH, "criterios de búsqueda");
        /**/
        instruccionSelect.setLocalizedLabel(ENGLISH, "select statement");
        instruccionSelect.setLocalizedLabel(SPANISH, "instrucción select");
        /**/
        selectRestringido.setLocalizedLabel(ENGLISH, "restricted select");
        selectRestringido.setLocalizedLabel(SPANISH, "select restringido");
        /**/
        filasSeleccionadas.setLocalizedLabel(ENGLISH, "selected rows");
        filasSeleccionadas.setLocalizedLabel(SPANISH, "filas seleccionadas");
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
        nombreArchivo.setLocalizedLabel(ENGLISH, "file");
        nombreArchivo.setLocalizedLabel(SPANISH, "archivo");
        nombreArchivo.setLocalizedTooltip(ENGLISH, "open the report file");
        nombreArchivo.setLocalizedTooltip(SPANISH, "abrir el archivo del informe");
        /**/
        descripcionError.setLocalizedLabel(ENGLISH, "message");
        descripcionError.setLocalizedLabel(SPANISH, "mensaje");
        /**/
        // </editor-fold>
    }

    /**/
    protected Key ix_rastro_informe_0001;

    @Override
    protected void settleKeys() {
        super.settleKeys();
        ix_rastro_informe_0001.setUnique(false);
        ix_rastro_informe_0001.newKeyField(idClaseRecursoValor, recursoValor);
    }

    /**/
    protected Tab tab1, tab2, tab3;

    @Override
    protected void settleTabs() {
        super.settleTabs();
        /**/
        tab1.newTabField(id, fechaHoraInicioEjecucion, fechaHoraFinEjecucion, nombreInforme, nombreArchivo,
            formatoInforme, tipoInforme, tipoGrafico, subtipoGrafico, personal,
            usuario, remitente, funcion,
            codigoClaseRecursoValor, nombreClaseRecursoValor,
            codigoRecurso, nombreRecurso,
            condicionEjeFun, descripcionError, direccionIp
        );
        /**/
        tab2.newTabField(codigoUsuario, nombreUsuario, codigoRemitente, nombreRemitente, codigoFuncion, nombreFuncion, descripcionFuncion, paginaFuncion, tipoFuncion, // 9
            idClaseRecursoValor, codigoClaseRecursoValor, nombreClaseRecursoValor, // 3
            recursoValor, idRecurso, versionRecurso, codigoRecurso, nombreRecurso, idPropietarioRecurso, idSegmentoRecurso, paginaRecurso, // 8
            leido, descargado, etiquetaLenguaje // 3
        );
        /**/
        tab3.newTabField(
            criteriosBusqueda, instruccionSelect, selectRestringido, filasSeleccionadas, limiteFilas // 5
        );
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of RastroInforme's tabs">
        /**/
        tab1.setLocalizedLabel(ENGLISH, "general");
        tab1.setLocalizedLabel(SPANISH, "general");
        /**/
        tab2.setLocalizedLabel(ENGLISH, "details");
        tab2.setLocalizedLabel(SPANISH, "detalles");
        /**/
        tab3.setLocalizedLabel(ENGLISH, "query");
        tab3.setLocalizedLabel(SPANISH, "consulta");
        /**/
        // </editor-fold>
    }

    protected Segment finalizado, pendiente, mis, finalizadoActual, pendienteActual;

    @Override
    protected void settleExpressions() {
        super.settleExpressions();
        /**/
        finalizado = condicionEjeFun.isIn(
            condicionEjeFun.EJECUTADO_SIN_ERRORES,
            condicionEjeFun.EJECUTADO_CON_ERRORES,
            condicionEjeFun.EJECUCION_CANCELADA
        );
        pendiente = and(finalizado, not(leido), not(descargado));
        /**/
        mis = codigoUsuario.isEqualTo(CURRENT_USER_CODE);
        finalizadoActual = mis.and(finalizado);
        pendienteActual = mis.and(pendiente);
        /**/
        finalizado.setLocalizedCollectionLabel(ENGLISH, "reports that have already finished");
        finalizado.setLocalizedCollectionLabel(SPANISH, "informes que ya han finalizado");
        finalizado.setLocalizedCollectionShortLabel(ENGLISH, "Finished reports");
        finalizado.setLocalizedCollectionShortLabel(SPANISH, "Informes finalizados");
        finalizado.setLocalizedDescription(ENGLISH, "the report has already finished");
        finalizado.setLocalizedDescription(SPANISH, "el informe ya ha finalizado");
        finalizado.setLocalizedErrorMessage(ENGLISH, "the report has not finished");
        finalizado.setLocalizedErrorMessage(SPANISH, "el informe no ha finalizado");
        /**/
        pendiente.setLocalizedCollectionLabel(ENGLISH, "finished reports not read nor downloaded");
        pendiente.setLocalizedCollectionLabel(SPANISH, "informes finalizados no leídos ni descargados");
        pendiente.setLocalizedCollectionShortLabel(ENGLISH, "Unread and undownloaded reports");
        pendiente.setLocalizedCollectionShortLabel(SPANISH, "Informes no leídos ni descargados");
        pendiente.setLocalizedDescription(ENGLISH, "the report has not been read or downloaded");
        pendiente.setLocalizedDescription(SPANISH, "el informe no ha sido leído o descargado");
        pendiente.setLocalizedErrorMessage(ENGLISH, "the report has already been read or downloaded");
        pendiente.setLocalizedErrorMessage(SPANISH, "el informe ya fue leído o descargado");
        /**/
        mis.setLocalizedCollectionLabel(ENGLISH, "reports executed by the current user");
        mis.setLocalizedCollectionLabel(SPANISH, "informes ejecutados por el usuario actual");
        mis.setLocalizedCollectionShortLabel(ENGLISH, "My reports");
        mis.setLocalizedCollectionShortLabel(SPANISH, "Mis informes");
        /**/
        finalizadoActual.setLocalizedCollectionLabel(ENGLISH, "reports executed by the current user that have already finished");
        finalizadoActual.setLocalizedCollectionLabel(SPANISH, "informes ejecutados por el usuario actual que ya han finalizado");
        finalizadoActual.setLocalizedCollectionShortLabel(ENGLISH, "My finished reports");
        finalizadoActual.setLocalizedCollectionShortLabel(SPANISH, "Mis informes finalizados");
        finalizadoActual.setLocalizedDescription(ENGLISH, "the report has already finished");
        finalizadoActual.setLocalizedDescription(SPANISH, "el informe ya ha finalizado");
        finalizadoActual.setLocalizedErrorMessage(ENGLISH, "the report has not finished");
        finalizadoActual.setLocalizedErrorMessage(SPANISH, "el informe no ha finalizado");
        /**/
        pendienteActual.setLocalizedCollectionLabel(ENGLISH, "finished reports executed by the current user not read nor downloaded");
        pendienteActual.setLocalizedCollectionLabel(SPANISH, "informes finalizados ejecutados por el usuario actual no leídos ni descargados");
        pendienteActual.setLocalizedCollectionShortLabel(ENGLISH, "My unread and undownloaded reports");
        pendienteActual.setLocalizedCollectionShortLabel(SPANISH, "Mis informes no leídos ni descargados");
        pendienteActual.setLocalizedDescription(ENGLISH, "the report has not been read or downloaded");
        pendienteActual.setLocalizedDescription(SPANISH, "el informe no ha sido leído o descargado");
        pendienteActual.setLocalizedErrorMessage(ENGLISH, "the report has already been read or downloaded");
        pendienteActual.setLocalizedErrorMessage(SPANISH, "el informe ya fue leído o descargado");
        /**/
    }

    @Override
    protected void settleFilters() {
        super.settleFilters();
        /**/
        addSelectSegment(finalizado, pendiente);
        addSelectSegment(mis, true);
        addSelectSegment(finalizadoActual, pendienteActual);
        /**/
        usuario.setRenderingFilter(usuario.id.isNotNull()); // mostrar si, y solo si, el usuario no ha sido eliminado
        remitente.setRenderingFilter(remitente.id.isNotNull()); // mostrar si, y solo si, el remitente no ha sido eliminado
        tipoGrafico.setRenderingFilter(tipoInforme.isEqualTo(tipoInforme.GRAFICO));
        subtipoGrafico.setRenderingFilter(tipoInforme.isEqualTo(tipoInforme.GRAFICO).and(tipoGrafico.isNotEqualTo(tipoGrafico.TORTA)));
        /**/
    }

    protected MarcarComoLeido marcarComoLeido;

    @OperationClass(access = OperationAccess.RESTRICTED)
    public class MarcarComoLeido extends ProcessOperation {

        @InstanceReference
        protected RastroInforme informe;

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            /**/
            // <editor-fold defaultstate="collapsed" desc="localization of MarcarComoLeido's attributes">
            /**/
            setLocalizedLabel(ENGLISH, "mark as read");
            setLocalizedLabel(SPANISH, "marcar como leído");
            /**/
            setLocalizedDescription(ENGLISH, "mark report as read");
            setLocalizedDescription(SPANISH, "marcar informe como leído");
            /**/
            setLocalizedSuccessMessage(ENGLISH, "the report was marked as read");
            setLocalizedSuccessMessage(SPANISH, "el informe fue marcado como leído");
            /**/
            // </editor-fold>
            /**/
        }

        @Override
        protected void settleParameters() {
            super.settleParameters();
            /**/
            informe.leido.setCurrentValue(true);
            /**/
            // <editor-fold defaultstate="collapsed" desc="localization of MarcarComoLeido's parameters">
            /**/
            informe.setLocalizedLabel(ENGLISH, "report");
            informe.setLocalizedLabel(SPANISH, "informe");
            /**/
            // </editor-fold>
            /**/
        }

        protected Check check101, check102, check103;

        @Override
        protected void settleExpressions() {
            super.settleExpressions();
            /**/
            check101 = informe.condicionEjeFun.isIn(
                informe.condicionEjeFun.EJECUTADO_SIN_ERRORES,
                informe.condicionEjeFun.EJECUTADO_CON_ERRORES,
                informe.condicionEjeFun.EJECUCION_CANCELADA
            );
            check102 = informe.codigoUsuario.isEqualTo(CURRENT_USER_CODE);
            check103 = not(informe.leido.or(informe.descargado));
            /**/
            // <editor-fold defaultstate="collapsed" desc="localization of MarcarComoLeido's expressions">
            /**/
            check101.setLocalizedDescription(ENGLISH, "the report has finished");
            check101.setLocalizedDescription(SPANISH, "el informe ha terminado");
            check101.setLocalizedErrorMessage(ENGLISH, "it is not allowed to mark as read those reports that have not finished");
            check101.setLocalizedErrorMessage(SPANISH, "no se permite marcar como leídos aquellos informes que no hayan finalizado");
            /**/
            check102.setLocalizedDescription(ENGLISH, "the report belongs to the current user");
            check102.setLocalizedDescription(SPANISH, "el informe le pertenece al usuario actual");
            check102.setLocalizedErrorMessage(ENGLISH, "the report does not belong to your user");
            check102.setLocalizedErrorMessage(SPANISH, "el informe no le pertenece a su usuario");
            /**/
            check103.setLocalizedDescription(ENGLISH, "the report has not been read nor downloaded");
            check103.setLocalizedDescription(SPANISH, "el informe no ha sido leído ni descargado");
            check103.setLocalizedErrorMessage(ENGLISH, "the report has been read or downloaded");
            check103.setLocalizedErrorMessage(SPANISH, "el informe ya fue leído o descargado");
            /**/
            // </editor-fold>
            /**/
        }

    }

    protected MarcarComoNoLeido marcarComoNoLeido;

    @OperationClass(access = OperationAccess.RESTRICTED)
    public class MarcarComoNoLeido extends ProcessOperation {

        @InstanceReference
        protected RastroInforme informe;

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            /**/
            // <editor-fold defaultstate="collapsed" desc="localization of MarcarComoNoLeido's attributes">
            /**/
            setLocalizedLabel(ENGLISH, "mark as unread and undownloaded");
            setLocalizedLabel(SPANISH, "marcar como no leído ni descargado");
            /**/
            setLocalizedDescription(ENGLISH, "mark report as unread and undownloaded");
            setLocalizedDescription(SPANISH, "marcar informe como no leído ni descargado");
            /**/
            setLocalizedSuccessMessage(ENGLISH, "the report was marked as unread and undownloaded");
            setLocalizedSuccessMessage(SPANISH, "el informe fue marcado como no leído ni descargado");
            /**/
            // </editor-fold>
            /**/
        }

        @Override
        protected void settleParameters() {
            super.settleParameters();
            /**/
            informe.leido.setCurrentValue(false);
            informe.descargado.setCurrentValue(false);
            /**/
            // <editor-fold defaultstate="collapsed" desc="localization of MarcarComoNoLeido's parameters">
            /**/
            informe.setLocalizedLabel(ENGLISH, "report");
            informe.setLocalizedLabel(SPANISH, "informe");
            /**/
            // </editor-fold>
            /**/
        }

        protected Check check101, check102, check103;

        @Override
        protected void settleExpressions() {
            super.settleExpressions();
            /**/
            check101 = informe.condicionEjeFun.isIn(
                informe.condicionEjeFun.EJECUTADO_SIN_ERRORES,
                informe.condicionEjeFun.EJECUTADO_CON_ERRORES,
                informe.condicionEjeFun.EJECUCION_CANCELADA
            );
            check102 = informe.codigoUsuario.isEqualTo(CURRENT_USER_CODE);
            check103 = informe.leido.or(informe.descargado);
            /**/
            // <editor-fold defaultstate="collapsed" desc="localization of MarcarComoNoLeido's expressions">
            /**/
            check101.setLocalizedDescription(ENGLISH, "the report has finished");
            check101.setLocalizedDescription(SPANISH, "el informe ha terminado");
            check101.setLocalizedErrorMessage(ENGLISH, "it is not allowed to mark as unread those reports that have not finished");
            check101.setLocalizedErrorMessage(SPANISH, "no se permite marcar como no leídos aquellos informes que no hayan finalizado");
            /**/
            check102.setLocalizedDescription(ENGLISH, "the report belongs to the current user");
            check102.setLocalizedDescription(SPANISH, "el informe le pertenece al usuario actual");
            check102.setLocalizedErrorMessage(ENGLISH, "the report does not belong to your user");
            check102.setLocalizedErrorMessage(SPANISH, "el informe no le pertenece a su usuario");
            /**/
            check103.setLocalizedDescription(ENGLISH, "the report has been read or downloaded");
            check103.setLocalizedDescription(SPANISH, "el informe ya fue leído o descargado");
            check103.setLocalizedErrorMessage(ENGLISH, "the report has not been read nor downloaded");
            check103.setLocalizedErrorMessage(SPANISH, "el informe no ha sido leído ni descargado");
            /**/
            // </editor-fold>
            /**/
        }

    }

    protected EnviarCopia enviarCopia;

    @OperationClass(access = OperationAccess.PROTECTED)
    public class EnviarCopia extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            /**/
            // <editor-fold defaultstate="collapsed" desc="localization of EnviarCopia's attributes">
            /**/
            setLocalizedLabel(ENGLISH, "send a copy");
            setLocalizedLabel(SPANISH, "enviar copia");
            /**/
            setLocalizedDescription(ENGLISH, "send a copy of the report to the specified recipient");
            setLocalizedDescription(SPANISH, "enviar una copia del informe al destinatario especificado");
            /**/
            setLocalizedSuccessMessage(ENGLISH, "a copy of the report was sent to the specified recipient");
            setLocalizedSuccessMessage(SPANISH, "se envió una copia del informe al destinatario especificado");
            /**/
            // </editor-fold>
            /**/
        }

        @InstanceReference
        protected RastroInforme informe;

        @ParameterField(required = Kleenean.TRUE)
        @EntityReferenceConversionValidation(restrictedAccess = Kleenean.FALSE)
        protected Usuario destinatario;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            /**/
            // <editor-fold defaultstate="collapsed" desc="localization of EnviarCopia's parameters">
            /**/
            informe.setLocalizedLabel(ENGLISH, "report");
            informe.setLocalizedLabel(SPANISH, "informe");
            /**/
            destinatario.setLocalizedLabel(ENGLISH, "recipient user");
            destinatario.setLocalizedLabel(SPANISH, "usuario destinatario");
            destinatario.setLocalizedShortLabel(ENGLISH, "recipient");
            destinatario.setLocalizedShortLabel(SPANISH, "destinatario");
            destinatario.setLocalizedShortDescription(ENGLISH, "report recipient");
            destinatario.setLocalizedShortDescription(SPANISH, "destinatario del informe");
            destinatario.setLocalizedTooltip(ENGLISH, "user code of the report recipient");
            destinatario.setLocalizedTooltip(SPANISH, "código de usuario del destinatario del informe");
            /**/
            destinatario.codigoUsuario.setLocalizedShortLabel(ENGLISH, "recipient code");
            destinatario.codigoUsuario.setLocalizedShortLabel(SPANISH, "destinatario");
            /**/
            // </editor-fold>
            /**/
        }

        protected Check check101, check201, check202;

        @Override
        protected void settleExpressions() {
            super.settleExpressions();
            /**/
            check101 = informe.condicionEjeFun.isEqualTo(informe.condicionEjeFun.EJECUTADO_SIN_ERRORES);
            /**/
            check201 = destinatario.esUsuarioEspecial.isFalse();
            check202 = destinatario.id.isNotEqualTo(CURRENT_USER_ID);
            /**/
            // <editor-fold defaultstate="collapsed" desc="localization of EnviarCopia's expressions">
            /**/
            check101.setLocalizedDescription(ENGLISH, "the report has finished without errors");
            check101.setLocalizedDescription(SPANISH, "el informe ha terminado sin errores");
            check101.setLocalizedErrorMessage(ENGLISH, "it is not allowed to send copies of reports that have not finished without errors");
            check101.setLocalizedErrorMessage(SPANISH, "no está permitido enviar copias de informes que no hayan terminado sin errores");
            /**/
            check201.setLocalizedDescription(ENGLISH, "the recipient is not a special user");
            check201.setLocalizedDescription(SPANISH, "el destinatario no es un usuario especial");
            check201.setLocalizedErrorMessage(ENGLISH, "it is not allowed to send copies of reports to special users");
            check201.setLocalizedErrorMessage(SPANISH, "no está permitido enviar copias de informes a usuarios especiales");
            /**/
            check202.setLocalizedDescription(ENGLISH, "the recipient is not the current user");
            check202.setLocalizedDescription(SPANISH, "el destinatario no es el usuario actual");
            check202.setLocalizedErrorMessage(ENGLISH, "it is not allowed to send copies of reports to yourself");
            check202.setLocalizedErrorMessage(SPANISH, "no está permitido enviarse copias de informes a uno mismo");
            /**/
            // </editor-fold>
            /**/
        }

        @Override
        protected void settleFilters() {
            super.settleFilters();
            /**/
            destinatario.setSearchQueryFilter(and(check201, check202));
            /**/
        }

    }

}
