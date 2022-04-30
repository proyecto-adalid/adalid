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
@EntityTableView(enabled = Kleenean.TRUE)
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
    @PropertyField(detail = Kleenean.TRUE, hidden = Kleenean.FALSE)
    public LongProperty id;

    @ColumnField(nullable = Kleenean.FALSE, indexed = Kleenean.TRUE)
    @PropertyField(sequence = 100, table = Kleenean.TRUE, report = Kleenean.TRUE, search = Kleenean.TRUE, overlay = Kleenean.TRUE)
    public TimestampProperty fechaHoraInicioEjecucion;

    @PropertyField(sequence = 100)
    public TimestampProperty fechaHoraFinEjecucion;

    @ColumnField(indexed = Kleenean.TRUE)
    @PropertyField(sequence = 100, table = Kleenean.TRUE, search = Kleenean.TRUE, report = Kleenean.TRUE, overlay = Kleenean.TRUE)
    @StringField(maxLength = 200)
    public StringProperty nombreInforme;

    @ColumnField(indexed = Kleenean.TRUE)
    @PropertyField(sequence = 200, table = Kleenean.TRUE, search = Kleenean.TRUE, report = Kleenean.TRUE, overlay = Kleenean.TRUE)
    @StringField(maxLength = 5)
    public StringProperty formatoInforme;

    @PropertyField(sequence = 410, table = Kleenean.FALSE, search = Kleenean.FALSE, report = Kleenean.FALSE)
    public IntegerProperty limiteFilas;

    @OwnerProperty
    @SegmentProperty
//->@Allocation(maxDepth = 2, maxRound = 1)
    @ColumnField(nullable = Kleenean.TRUE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(sequence = 250, hidden = Kleenean.TRUE)
    @QueryMapping(mapKeyProperties = Kleenean.FALSE)
    public Usuario usuario;

    @ColumnField(indexed = Kleenean.TRUE)
    @PropertyField(sequence = 250, table = Kleenean.TRUE, search = Kleenean.TRUE, report = Kleenean.TRUE)
    @StringField(maxLength = 36)
    public StringProperty codigoUsuario;

    @ColumnField(indexed = Kleenean.TRUE)
    @PropertyField(sequence = 250, table = Kleenean.FALSE, search = Kleenean.TRUE, report = Kleenean.TRUE, overlay = Kleenean.TRUE)
    @StringField(maxLength = 100)
    public StringProperty nombreUsuario;

//  20171213: remove foreign-key referring to Funcion
//  @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.TRUE)
    @PropertyField(sequence = 300, hidden = Kleenean.TRUE)
    @QueryMapping(mapKeyProperties = Kleenean.FALSE)
    public Funcion funcion;

//  @ColumnField(indexed = Kleenean.TRUE)
    @PropertyField(sequence = 300)
    @StringField(maxLength = 200)
    public StringProperty codigoFuncion;

//  @ColumnField(indexed = Kleenean.TRUE)
    @PropertyField(sequence = 300)
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
    @PropertyField(sequence = 300, search = Kleenean.TRUE)
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
    @PropertyField(sequence = 300, table = Kleenean.FALSE, report = Kleenean.TRUE, search = Kleenean.TRUE)
    public StringProperty codigoRecurso;

    @ColumnField(indexed = Kleenean.FALSE)
    @PropertyField(sequence = 300, table = Kleenean.FALSE, report = Kleenean.TRUE, search = Kleenean.TRUE)
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
    @PropertyField(sequence = 500, table = Kleenean.TRUE, search = Kleenean.TRUE, report = Kleenean.TRUE)
    public CondicionEjeFun condicionEjeFun;

    @FileReference(loadField = "fechaHoraInicioEjecucion")
    @PropertyField(sequence = 150, table = Kleenean.TRUE, report = Kleenean.FALSE, search = Kleenean.FALSE)
    public StringProperty nombreArchivo;

    @PropertyField(sequence = 500)
    @StringField(maxLength = 0)
    public StringProperty descripcionError;

    @ColumnField(nullable = Kleenean.TRUE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(sequence = 200, table = Kleenean.TRUE, search = Kleenean.TRUE, report = Kleenean.TRUE, overlay = Kleenean.TRUE)
    public TipoInforme tipoInforme;

    @ColumnField(nullable = Kleenean.TRUE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(sequence = 200, table = Kleenean.TRUE, search = Kleenean.TRUE, report = Kleenean.TRUE, overlay = Kleenean.TRUE)
    public TipoGrafico tipoGrafico;

    @ColumnField(nullable = Kleenean.TRUE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(sequence = 200, table = Kleenean.TRUE, search = Kleenean.TRUE, report = Kleenean.TRUE, overlay = Kleenean.TRUE)
    public SubtipoGrafico subtipoGrafico;

    @ColumnField(nullable = Kleenean.TRUE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(sequence = 250, hidden = Kleenean.TRUE)
    @QueryMapping(mapKeyProperties = Kleenean.FALSE)
    public Usuario remitente;

    @ColumnField(indexed = Kleenean.TRUE)
    @PropertyField(sequence = 250, table = Kleenean.TRUE, search = Kleenean.TRUE, report = Kleenean.TRUE)
    @StringField(maxLength = 36)
    public StringProperty codigoRemitente;

    @ColumnField(indexed = Kleenean.TRUE)
    @PropertyField(sequence = 250, table = Kleenean.FALSE, search = Kleenean.TRUE, report = Kleenean.TRUE)
    @StringField(maxLength = 100)
    public StringProperty nombreRemitente;

    @ColumnField(nullable = Kleenean.FALSE, indexed = Kleenean.FALSE)
    @PropertyField(sequence = 200, table = Kleenean.TRUE, search = Kleenean.TRUE, report = Kleenean.TRUE, overlay = Kleenean.TRUE)
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
        setLocalizedDescription(ENGLISH, "audit trail of the execution of export and report operations");
        setLocalizedDescription(SPANISH, "rastro de auditoría de la generación de archivos e informes");
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
        personal.setInitialValue(false);
        personal.setDefaultValue(false);
        /**/
        condicionEjeFun.setInitialValue(condicionEjeFun.EJECUCION_PENDIENTE);
        condicionEjeFun.setDefaultValue(condicionEjeFun.EJECUCION_PENDIENTE);
        /**/
        RastroUtils.setGraphicImageExpressions(condicionEjeFun);
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of RastroInforme's properties">
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
        personal.setLocalizedTooltip(ENGLISH, "user-defined report");
        personal.setLocalizedTooltip(SPANISH, "informe definido por el usuario");
        /**/
        limiteFilas.setLocalizedLabel(ENGLISH, "limit");
        limiteFilas.setLocalizedLabel(SPANISH, "límite");
        /**/
        usuario.setLocalizedLabel(ENGLISH, "user");
        usuario.setLocalizedLabel(SPANISH, "usuario");
        /**/
        codigoUsuario.setLocalizedLabel(ENGLISH, "user");
        codigoUsuario.setLocalizedLabel(SPANISH, "usuario");
        /**/
        nombreUsuario.setLocalizedLabel(ENGLISH, "user name");
        nombreUsuario.setLocalizedLabel(SPANISH, "nombre del usuario");
        /**/
        remitente.setLocalizedLabel(ENGLISH, "sender");
        remitente.setLocalizedLabel(SPANISH, "remitente");
        /**/
        codigoRemitente.setLocalizedLabel(ENGLISH, "sender");
        codigoRemitente.setLocalizedLabel(SPANISH, "remitente");
        /**/
        nombreRemitente.setLocalizedLabel(ENGLISH, "sender name");
        nombreRemitente.setLocalizedLabel(SPANISH, "nombre del remitente");
        /**/
        funcion.setLocalizedLabel(ENGLISH, "function");
        funcion.setLocalizedLabel(SPANISH, "función");
        /**/
        tipoFuncion.setLocalizedLabel(ENGLISH, "function type");
        tipoFuncion.setLocalizedLabel(SPANISH, "tipo de función");
        /**/
        codigoFuncion.setLocalizedLabel(ENGLISH, "function");
        codigoFuncion.setLocalizedLabel(SPANISH, "función");
        /**/
        nombreFuncion.setLocalizedLabel(ENGLISH, "function name");
        nombreFuncion.setLocalizedLabel(SPANISH, "nombre de la función");
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
        idRecurso.setLocalizedLabel(ENGLISH, "resource");
        idRecurso.setLocalizedLabel(SPANISH, "recurso");
        /**/
        versionRecurso.setLocalizedLabel(ENGLISH, "resource version");
        versionRecurso.setLocalizedLabel(SPANISH, "versión del recurso");
        /**/
        codigoRecurso.setLocalizedDescription(ENGLISH, "code of the resource on which the report was executed");
        codigoRecurso.setLocalizedDescription(SPANISH, "código del recurso sobre el que se ejecutó el informe");
        codigoRecurso.setLocalizedLabel(ENGLISH, "resource");
        codigoRecurso.setLocalizedLabel(SPANISH, "recurso");
        /**/
        nombreRecurso.setLocalizedDescription(ENGLISH, "name of the resource on which the report was executed");
        nombreRecurso.setLocalizedDescription(SPANISH, "nombre del recurso sobre el que se ejecutó el informe");
        nombreRecurso.setLocalizedLabel(ENGLISH, "resource name");
        nombreRecurso.setLocalizedLabel(SPANISH, "nombre del recurso");
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
        // </editor-fold>
    }

    /**/
    protected Key key1;

    @Override
    protected void settleKeys() {
        super.settleKeys();
        key1.newKeyField(idClaseRecursoValor, recursoValor);
    }

    /**/
    protected Tab tab1, tab2;

    @Override
    protected void settleTabs() {
        super.settleTabs();
        tab1.newTabField(id, fechaHoraInicioEjecucion, fechaHoraFinEjecucion, nombreInforme, nombreArchivo, // 4
            formatoInforme, tipoInforme, tipoGrafico, subtipoGrafico, personal, // 5
            usuario, codigoUsuario, nombreUsuario, remitente, codigoRemitente, nombreRemitente, condicionEjeFun, descripcionError // 8
        );
        tab2.newTabField(funcion, codigoFuncion, nombreFuncion, descripcionFuncion, paginaFuncion, tipoFuncion, // 5
            idClaseRecursoValor, codigoClaseRecursoValor, nombreClaseRecursoValor, recursoValor, // 4
            idRecurso, versionRecurso, codigoRecurso, nombreRecurso, idPropietarioRecurso, idSegmentoRecurso, paginaRecurso, // 7
            criteriosBusqueda, instruccionSelect, selectRestringido, filasSeleccionadas, limiteFilas, etiquetaLenguaje // 5
        );
        // <editor-fold defaultstate="collapsed" desc="localization of RastroInforme's tabs">
        tab1.setLocalizedLabel(ENGLISH, "general");
        tab1.setLocalizedLabel(SPANISH, "general");
        /**/
        tab2.setLocalizedLabel(ENGLISH, "details");
        tab2.setLocalizedLabel(SPANISH, "detalles");
        // </editor-fold>
    }

    protected EnviarCopia enviarCopia;

    @OperationClass(access = OperationAccess.PROTECTED)
    public class EnviarCopia extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
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
        }

        @InstanceReference
//      @EntityReferenceSearch(searchType = SearchType.LIST)
        @Filter(segment = Kleenean.TRUE)
        protected RastroInforme informe;

        @ParameterField(required = Kleenean.TRUE)
        @EntityReferenceSearch(searchType = SearchType.LIST, listStyle = ListStyle.CHARACTER_KEY_AND_NAME)
        protected Usuario destinatario;

        @Override
        protected void settleParameters() {
            super.settleParameters();
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
        }

        protected Check check102;

        @Override
        protected void settleExpressions() {
            super.settleExpressions();
            /**/
            check102 = destinatario.esUsuarioEspecial.isFalse();
            /**/
            // <editor-fold defaultstate="collapsed" desc="localization of EnviarCopia's expressions">
            /**/
            check102.setLocalizedDescription(ENGLISH, "the recipient is not a special user");
            check102.setLocalizedDescription(SPANISH, "el destinatario no es un usuario especial");
            check102.setLocalizedErrorMessage(ENGLISH, "it is not allowed to send copies of reports to special users");
            check102.setLocalizedErrorMessage(SPANISH, "no está permitido enviar copias de informes a usuarios especiales");
            /**/
            // </editor-fold>
        }

        @Override
        protected void settleFilters() {
            super.settleFilters();
            /**/
            destinatario.setSearchQueryFilter(check102);
            /**/
        }

    }

}