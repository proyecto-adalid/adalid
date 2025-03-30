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
import meta.entidad.comun.configuracion.basica.Funcion;
import meta.entidad.comun.configuracion.basica.Parametro;
import meta.entidad.comun.control.acceso.Usuario;

/**
 * @author Jorge Campins
 */
@EntityClass(independent = Kleenean.FALSE, resourceType = ResourceType.OPERATION, resourceGender = ResourceGender.MASCULINE)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE, access = OperationAccess.RESTRICTED)
@EntityInsertOperation(enabled = Kleenean.FALSE)
@EntityUpdateOperation(enabled = Kleenean.FALSE)
@EntityDeleteOperation(enabled = Kleenean.FALSE)
@EntityReportOperation(enabled = Kleenean.FALSE)
@EntityTableView(enabled = Kleenean.FALSE, responsiveMode = TableResponsiveMode.PRIORITY)
@EntityDetailView(enabled = Kleenean.FALSE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.TRUE)
@EntityTriggers(afterValue = Kleenean.TRUE)
public class RastroFuncionPar extends AbstractPersistentEntity {

    private static final String CONVERTIDOR_VALOR = "convertidorValorRastroFuncionPar";

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public RastroFuncionPar(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Override
    protected void addAllocationStrings() {
        super.addAllocationStrings();
        super.addAllocationStrings(
            "rastroFuncion.usuario",
            "rastroFuncion.funcion",
            "rastroFuncion.tipoFuncion",
            "rastroFuncion.recursoValor",
            "rastroFuncion.condicionEjeFun"
        );
    }

    @PrimaryKey
    @PropertyField(hidden = Kleenean.FALSE, detail = Kleenean.TRUE, heading = Kleenean.TRUE)
    public LongProperty id;

    @ForeignKey(onDelete = OnDeleteAction.CASCADE, onUpdate = OnUpdateAction.CASCADE)
    @ManyToOne(navigability = Navigability.BIDIRECTIONAL, view = MasterDetailView.TABLE_AND_DETAIL)
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE)
    public RastroFuncion rastroFuncion;

//  20171213: remove foreign-key referring to Parametro
//  @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.TRUE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE)
    public Parametro parametro;

    @StringField(maxLength = 200)
    public StringProperty codigoParametro;

    @StringField(maxLength = 200)
    public StringProperty nombreParametro;

    @PropertyField(sequence = 110, table = Kleenean.FALSE, report = Kleenean.FALSE, export = Kleenean.FALSE)
    @StringField(converter = CONVERTIDOR_VALOR, maxLength = 0)
    public StringProperty valorParametro;

    @PropertyField(sequence = 120, table = Kleenean.FALSE, report = Kleenean.FALSE, export = Kleenean.FALSE)
    public StringProperty codigoRecursoParametro;

    @PropertyField(sequence = 130, table = Kleenean.FALSE, report = Kleenean.FALSE, export = Kleenean.FALSE)
    public StringProperty nombreRecursoParametro;

    @PropertyField(sequence = 100, table = Kleenean.TRUE, report = Kleenean.FALSE, export = Kleenean.FALSE)
    @StringField(converter = CONVERTIDOR_VALOR, maxLength = 0)
    public StringProperty valorAparenteParametro;

    @PropertyField(sequence = 110, table = Kleenean.FALSE, report = Kleenean.FALSE, export = Kleenean.FALSE)
    @StringField(converter = CONVERTIDOR_VALOR, maxLength = 0)
    public StringProperty valorAnterior;

    @PropertyField(sequence = 120, table = Kleenean.FALSE, report = Kleenean.FALSE, export = Kleenean.FALSE)
    public StringProperty codigoRecursoAnterior;

    @PropertyField(sequence = 130, table = Kleenean.FALSE, report = Kleenean.FALSE, export = Kleenean.FALSE)
    public StringProperty nombreRecursoAnterior;

    @PropertyField(sequence = 100, table = Kleenean.TRUE, report = Kleenean.FALSE, export = Kleenean.FALSE, responsivePriority = 6)
    @StringField(converter = CONVERTIDOR_VALOR, maxLength = 0)
    public StringProperty valorAparenteAnterior;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(sequence = 200, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public BooleanProperty diferenteValor;

    @PropertyField(sequence = 800, hidden = Kleenean.TRUE)
    public LongProperty idClaseRecursoValor;

    @UniformResourceLocator(urlType = UrlType.INTERNAL, urlDisplayType = UrlDisplayType.BUTTON)
    @PropertyField(sequence = 800, hidden = Kleenean.TRUE)
    public StringProperty paginaRecurso;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of RastroFuncionPar's attributes">
        setLocalizedLabel(ENGLISH, "function parameter audit trail");
        setLocalizedLabel(SPANISH, "rastro de auditoría de parámetro de función");
        setLocalizedShortLabel(ENGLISH, "parameter trail");
        setLocalizedShortLabel(SPANISH, "rastro de parámetro");
        setLocalizedCollectionLabel(ENGLISH, "Function Parameter Audit Trails");
        setLocalizedCollectionLabel(SPANISH, "Rastros de Auditoría de Parámetros");
        setLocalizedCollectionShortLabel(ENGLISH, "Parameter Trails");
        setLocalizedCollectionShortLabel(SPANISH, "Rastros de Parámetros");
        /**/
        setLocalizedDescription(ENGLISH, "Each instance of " + b("Function Parameter Audit Trails") + " represents an "
            + "audit trail of the parameters of the execution of CRUD operations and business processes."
            + "");
        setLocalizedDescription(SPANISH, "Cada instancia de " + b("Rastros de Auditoría de Parámetros") + " representa un "
            + "rastro de auditoría de los parámetros de la ejecución de operaciones de registro y procesos de negocio."
            + "");
        /**/
        setLocalizedShortDescription(ENGLISH, "audit trail of the parameters of the execution of CRUD operations and business processes");
        setLocalizedShortDescription(SPANISH, "rastro de auditoría de los parámetros de la ejecución de operaciones de registro y procesos de negocio");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        /**/
        diferenteValor.setInitialValue(false);
        diferenteValor.setDefaultValue(false);
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of RastroFuncionPar's properties">
        /**/
        final String suffix = "; only applies to update operations";
        final String sufijo = "; solo aplica a operaciones de actualización";
        /**/
        rastroFuncion.setLocalizedLabel(ENGLISH, "function trail");
        rastroFuncion.setLocalizedLabel(SPANISH, "rastro de función");
        rastroFuncion.setLocalizedShortLabel(ENGLISH, "trail");
        rastroFuncion.setLocalizedShortLabel(SPANISH, "rastro");
        /**/
        parametro.setLocalizedDescription(ENGLISH, "parameter");
        parametro.setLocalizedDescription(SPANISH, "parámetro");
        parametro.setLocalizedLabel(ENGLISH, "parameter");
        parametro.setLocalizedLabel(SPANISH, "parámetro");
        /**/
        codigoParametro.setLocalizedDescription(ENGLISH, "parameter code");
        codigoParametro.setLocalizedDescription(SPANISH, "código del parámetro");
        codigoParametro.setLocalizedLabel(ENGLISH, "parameter code");
        codigoParametro.setLocalizedLabel(SPANISH, "código del parámetro");
        /**/
        nombreParametro.setLocalizedDescription(ENGLISH, "parameter name");
        nombreParametro.setLocalizedDescription(SPANISH, "nombre del parámetro");
        nombreParametro.setLocalizedLabel(ENGLISH, "parameter name");
        nombreParametro.setLocalizedLabel(SPANISH, "nombre del parámetro");
        /**/
        valorParametro.setLocalizedDescription(ENGLISH, "unformatted text representing the parameter value");
        valorParametro.setLocalizedDescription(SPANISH, "texto sin formato que representa el valor del parámetro");
        valorParametro.setLocalizedLabel(ENGLISH, "unformatted value");
        valorParametro.setLocalizedLabel(SPANISH, "valor sin formato");
        /**/
        codigoRecursoParametro.setLocalizedDescription(ENGLISH, "code corresponding to the parameter value");
        codigoRecursoParametro.setLocalizedDescription(SPANISH, "código correspondiente al valor del parámetro");
        codigoRecursoParametro.setLocalizedLabel(ENGLISH, "value code");
        codigoRecursoParametro.setLocalizedLabel(SPANISH, "código del valor");
        /**/
        nombreRecursoParametro.setLocalizedDescription(ENGLISH, "name corresponding to the parameter value");
        nombreRecursoParametro.setLocalizedDescription(SPANISH, "nombre correspondiente al valor del parámetro");
        nombreRecursoParametro.setLocalizedLabel(ENGLISH, "value name");
        nombreRecursoParametro.setLocalizedLabel(SPANISH, "nombre del valor");
        /**/
        valorAparenteParametro.setLocalizedDescription(ENGLISH, "formatted text representing the parameter value");
        valorAparenteParametro.setLocalizedDescription(SPANISH, "texto con formato que representa el valor del parámetro");
        valorAparenteParametro.setLocalizedLabel(ENGLISH, "value");
        valorAparenteParametro.setLocalizedLabel(SPANISH, "valor");
        /**/
        valorAnterior.setLocalizedDescription(ENGLISH, "unformatted text representing the previous parameter value" + suffix);
        valorAnterior.setLocalizedDescription(SPANISH, "texto sin formato que representa el valor anterior del parámetro" + sufijo);
        valorAnterior.setLocalizedLabel(ENGLISH, "unformatted previous value");
        valorAnterior.setLocalizedLabel(SPANISH, "valor anterior sin formato");
        /**/
        codigoRecursoAnterior.setLocalizedDescription(ENGLISH, "code corresponding to the previous parameter value" + suffix);
        codigoRecursoAnterior.setLocalizedDescription(SPANISH, "código correspondiente al valor anterior del parámetro" + sufijo);
        codigoRecursoAnterior.setLocalizedLabel(ENGLISH, "previous value code");
        codigoRecursoAnterior.setLocalizedLabel(SPANISH, "código del valor anterior");
        /**/
        nombreRecursoAnterior.setLocalizedDescription(ENGLISH, "name corresponding to the previous parameter value" + suffix);
        nombreRecursoAnterior.setLocalizedDescription(SPANISH, "nombre correspondiente al valor anterior del parámetro" + sufijo);
        nombreRecursoAnterior.setLocalizedLabel(ENGLISH, "previous value name");
        nombreRecursoAnterior.setLocalizedLabel(SPANISH, "nombre del valor anterior");
        /**/
        valorAparenteAnterior.setLocalizedDescription(ENGLISH, "formatted text representing the previous parameter value" + suffix);
        valorAparenteAnterior.setLocalizedDescription(SPANISH, "texto con formato que representa el valor anterior del parámetro" + sufijo);
        valorAparenteAnterior.setLocalizedLabel(ENGLISH, "previous value");
        valorAparenteAnterior.setLocalizedLabel(SPANISH, "valor anterior");
        /**/
        diferenteValor.setLocalizedDescription(ENGLISH, "indicator that shows whether or not the value is different from the previous value" + suffix);
        diferenteValor.setLocalizedDescription(SPANISH, "indicador que muestra si el valor es, o no, diferente al valor anterior" + sufijo);
        diferenteValor.setLocalizedLabel(ENGLISH, "different");
        diferenteValor.setLocalizedLabel(SPANISH, "diferente");
        /**/
        idClaseRecursoValor.setLocalizedLabel(ENGLISH, "resource class");
        idClaseRecursoValor.setLocalizedLabel(SPANISH, "clase de recurso");
        /**/
        paginaRecurso.setLocalizedLabel(ENGLISH, "detail");
        paginaRecurso.setLocalizedLabel(SPANISH, "detalle");
        paginaRecurso.setLocalizedTooltip(ENGLISH, "open the detailed query page of the resource");
        paginaRecurso.setLocalizedTooltip(SPANISH, "abrir la página de consulta detallada del recurso");
        // </editor-fold>
    }

    @Override
    protected void settleLinks() {
        super.settleLinks();
        /**/
        linkForeignOwnerProperty(rastroFuncion.usuario);
        linkForeignSegmentProperty(rastroFuncion.usuario);
        /**/
        linkForeignQueryProperty(rastroFuncion.fechaHoraEjecucion);
        linkForeignQueryProperty(rastroFuncion.funcion);
        linkForeignQueryProperty(rastroFuncion.tipoFuncion);
        linkForeignQueryProperty(rastroFuncion.codigoFuncion);
        linkForeignQueryProperty(rastroFuncion.nombreFuncion);
        linkForeignQueryProperty(rastroFuncion.codigoRecurso);
        linkForeignQueryProperty(rastroFuncion.nombreRecurso);
        linkForeignQueryProperty(rastroFuncion.codigoClaseRecursoValor);
        linkForeignQueryProperty(rastroFuncion.nombreClaseRecursoValor);
        /**/
    }

    protected Segment dml, biz;

    @Override
    protected void settleExpressions() {
        super.settleExpressions();
        /**/
        biz = rastroFuncion.tipoFuncion.isIn(
            rastroFuncion.tipoFuncion.PROCEDIMIENTO_PARAMETROS,
            rastroFuncion.tipoFuncion.PROCESO
        );
        /**/
        dml = rastroFuncion.tipoFuncion.isIn(
            rastroFuncion.tipoFuncion.CREACION,
            rastroFuncion.tipoFuncion.MODIFICACION,
            rastroFuncion.tipoFuncion.ELIMINACION
        );
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of RastroFuncionPar's expressions">
        /**/
        biz.setLocalizedCollectionLabel(ENGLISH, "Business Procedures and Processes");
        biz.setLocalizedCollectionLabel(SPANISH, "Procedimientos y Procesos de Negocio");
        biz.setLocalizedCollectionShortLabel(ENGLISH, "Business Operations");
        biz.setLocalizedCollectionShortLabel(SPANISH, "Operaciones de Negocio");
        /**/
        dml.setLocalizedCollectionLabel(ENGLISH, "Registration Operations (Insert, Update and Delete)");
        dml.setLocalizedCollectionLabel(SPANISH, "Operaciones de Registro (Creación, Modificación y Eliminación)");
        dml.setLocalizedCollectionShortLabel(ENGLISH, "Registration Operations");
        dml.setLocalizedCollectionShortLabel(SPANISH, "Operaciones de Registro");
        /**/
        // </editor-fold>
        /**/
    }

    @Override
    protected void settleFilters() {
        super.settleFilters();
        /**/
        addSelectSegment(biz, dml);
        /**/
        final BooleanExpression modificacion = rastroFuncion.tipoFuncion.isEqualTo(rastroFuncion.tipoFuncion.MODIFICACION);
        /**/
        valorAnterior.setRenderingFilter(modificacion);
        codigoRecursoAnterior.setRenderingFilter(modificacion);
        nombreRecursoAnterior.setRenderingFilter(modificacion);
        valorAparenteAnterior.setRenderingFilter(modificacion);
        diferenteValor.setRenderingFilter(modificacion);
        /**/
    }

    protected View rastrosConParametros;

    @Override
    protected void settleViews() {
        super.settleViews();
        /**/
        rastrosConParametros.setPageFormatClass(DefaultPageFormat.class);
        /**/
        ViewField vf;
        final int controlGroupReportElementsWidth = 720;
        /**/
        rastrosConParametros.newControlField(rastroFuncion);
        /**/
        vf = rastrosConParametros.newHeadingField(rastroFuncion.fechaHoraEjecucion, rastroFuncion);
        vf.setPixels(controlGroupReportElementsWidth);
        /**/
        vf = rastrosConParametros.newHeadingField(rastroFuncion.codigoUsuario, rastroFuncion);
        vf.setPixels(controlGroupReportElementsWidth);
        /**/
        vf = rastrosConParametros.newHeadingField(rastroFuncion.nombreUsuario, rastroFuncion);
        vf.setPixels(controlGroupReportElementsWidth);
        /**/
        vf = rastrosConParametros.newHeadingField(rastroFuncion.codigoFuncion, rastroFuncion);
        vf.setPixels(controlGroupReportElementsWidth);
        /**/
        vf = rastrosConParametros.newHeadingField(rastroFuncion.nombreFuncion, rastroFuncion);
        vf.setPixels(controlGroupReportElementsWidth);
        /**/
        vf = rastrosConParametros.newHeadingField(rastroFuncion.codigoRecurso, rastroFuncion);
        vf.setPixels(controlGroupReportElementsWidth);
        /**/
        vf = rastrosConParametros.newHeadingField(rastroFuncion.nombreRecurso, rastroFuncion);
        vf.setPixels(controlGroupReportElementsWidth);
        /**/
        vf = rastrosConParametros.newHeadingField(rastroFuncion.condicionEjeFun.codigo, rastroFuncion);
        vf.setPixels(controlGroupReportElementsWidth);
        /**/
        vf = rastrosConParametros.newDetailField(id, SortOption.ASC);
        vf.setPixels(0);
        /**/
        vf = rastrosConParametros.newDetailField(parametro.nombreParametro);
        vf.setPixels(240);
        /**/
        vf = rastrosConParametros.newDetailField(valorAparenteParametro);
        vf.setPixels(320);
        /**/
        vf = rastrosConParametros.newDetailField(valorAparenteAnterior);
        vf.setPixels(320);
        /**/
        vf = rastrosConParametros.newDetailField(diferenteValor);
        vf.setPixels(60);
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of RastroFuncionPar's views">
        rastrosConParametros.setLocalizedLabel(ENGLISH, "Function Trail Parameters");
        rastrosConParametros.setLocalizedLabel(SPANISH, "Rastros de Parámetros de Función");
        ViewField vf001 = rastrosConParametros.getField(rastroFuncion);
        if (vf001 != null) {
            vf001.setLocalizedLabel(ENGLISH, "function trail");
            vf001.setLocalizedLabel(SPANISH, "rastro de función");
        }
        ViewField vf002 = rastrosConParametros.getField(rastroFuncion.fechaHoraEjecucion);
        if (vf002 != null) {
            vf002.setLocalizedLabel(ENGLISH, "timestamp");
            vf002.setLocalizedLabel(SPANISH, "fecha/hora");
        }
        ViewField vf003 = rastrosConParametros.getField(rastroFuncion.codigoUsuario);
        if (vf003 != null) {
            vf003.setLocalizedLabel(ENGLISH, "user");
            vf003.setLocalizedLabel(SPANISH, "usuario");
        }
        ViewField vf004 = rastrosConParametros.getField(rastroFuncion.nombreUsuario);
        if (vf004 != null) {
            vf004.setLocalizedLabel(ENGLISH, "user name");
            vf004.setLocalizedLabel(SPANISH, "nombre del usuario");
        }
        ViewField vf005 = rastrosConParametros.getField(rastroFuncion.codigoFuncion);
        if (vf005 != null) {
            vf005.setLocalizedLabel(ENGLISH, "function");
            vf005.setLocalizedLabel(SPANISH, "función");
        }
        ViewField vf006 = rastrosConParametros.getField(rastroFuncion.nombreFuncion);
        if (vf006 != null) {
            vf006.setLocalizedLabel(ENGLISH, "function name");
            vf006.setLocalizedLabel(SPANISH, "nombre de la función");
        }
        ViewField vf007 = rastrosConParametros.getField(rastroFuncion.codigoRecurso);
        if (vf007 != null) {
            vf007.setLocalizedLabel(ENGLISH, "resource");
            vf007.setLocalizedLabel(SPANISH, "recurso");
        }
        ViewField vf008 = rastrosConParametros.getField(rastroFuncion.nombreRecurso);
        if (vf008 != null) {
            vf008.setLocalizedLabel(ENGLISH, "resource name");
            vf008.setLocalizedLabel(SPANISH, "nombre del recurso");
        }
        ViewField vf009 = rastrosConParametros.getField(rastroFuncion.condicionEjeFun.codigo);
        if (vf009 != null) {
            vf009.setLocalizedLabel(ENGLISH, "condition");
            vf009.setLocalizedLabel(SPANISH, "condición");
        }
        ViewField vf010 = rastrosConParametros.getField(id);
        if (vf010 != null) {
            vf010.setLocalizedLabel(ENGLISH, "trail");
            vf010.setLocalizedLabel(SPANISH, "rastro");
        }
        ViewField vf011 = rastrosConParametros.getField(parametro.nombreParametro);
        if (vf011 != null) {
            vf011.setLocalizedLabel(ENGLISH, "parameter");
            vf011.setLocalizedLabel(SPANISH, "parámetro");
        }
        ViewField vf012 = rastrosConParametros.getField(valorAparenteParametro);
        if (vf012 != null) {
            vf012.setLocalizedLabel(ENGLISH, "value");
            vf012.setLocalizedLabel(SPANISH, "valor");
        }
        ViewField vf013 = rastrosConParametros.getField(valorAparenteAnterior);
        if (vf013 != null) {
            vf013.setLocalizedLabel(ENGLISH, "previous value");
            vf013.setLocalizedLabel(SPANISH, "valor anterior");
        }
        ViewField vf014 = rastrosConParametros.getField(diferenteValor);
        if (vf014 != null) {
            vf014.setLocalizedLabel(ENGLISH, "different");
            vf014.setLocalizedLabel(SPANISH, "diferente");
        }
        // </editor-fold>
    }

    protected ExportarArchivoRastrosConParametros archivoRastrosConParametros;

    @ExportOperationClass(viewField = "rastrosConParametros")
    @OperationClass(access = OperationAccess.PROTECTED)
    public class ExportarArchivoRastrosConParametros extends ExportOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of ExportarArchivoRastrosConParametros's attributes">
            setLocalizedLabel(ENGLISH, "generate function trail file with parameters");
            setLocalizedLabel(SPANISH, "generar archivo de rastros con parámetros");
            setLocalizedShortLabel(ENGLISH, "function trail file with parameters");
            setLocalizedShortLabel(SPANISH, "archivo de rastros con parámetros");
//          setLocalizedCollectionLabel(ENGLISH, "Function Trails with Parameters");
//          setLocalizedCollectionLabel(SPANISH, "Rastros de Función con Parámetros");
            setLocalizedDescription(ENGLISH, "export a file of function execution audit trails with parameters");
            setLocalizedDescription(SPANISH, "exportar un archivo de rastros de ejecución de función con sus parámetros");
            // </editor-fold>
        }

        @ParameterField(linkedColumn = "fecha_hora_ejecucion_1", operator = StandardRelationalOp.GTEQ)
        protected TimestampParameter desde;

        @ParameterField(linkedColumn = "fecha_hora_ejecucion_1", operator = StandardRelationalOp.LTEQ)
        protected TimestampParameter hasta;

        @ParameterField(linkedColumn = "usuario_1")
        protected Usuario usuario;

        @ParameterField(linkedColumn = "funcion_1")
        protected Funcion funcion;

        @ParameterField(linkedColumn = "codigo_recurso_1")
        protected StringParameter codigoRecurso;

        @ParameterField(linkedField = "diferenteValor")
        protected BooleanParameter diferente;

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
            // <editor-fold defaultstate="collapsed" desc="localization of ExportarArchivoRastrosConParametros's parameters">
            desde.setLocalizedDescription(ENGLISH, "date and time of the function execution");
            desde.setLocalizedDescription(SPANISH, "fecha y hora de la ejecución de la función");
            desde.setLocalizedLabel(ENGLISH, "from");
            desde.setLocalizedLabel(SPANISH, "desde");
            /**/
            hasta.setLocalizedDescription(ENGLISH, "date and time of the function execution");
            hasta.setLocalizedDescription(SPANISH, "fecha y hora de la ejecución de la función");
            hasta.setLocalizedLabel(ENGLISH, "to");
            hasta.setLocalizedLabel(SPANISH, "hasta");
            /**/
            usuario.setLocalizedDescription(ENGLISH, "user who executed the function");
            usuario.setLocalizedDescription(SPANISH, "usuario que ejecutó la función");
            usuario.setLocalizedLabel(ENGLISH, "user");
            usuario.setLocalizedLabel(SPANISH, "usuario");
            /**/
            funcion.setLocalizedDescription(ENGLISH, "function");
            funcion.setLocalizedDescription(SPANISH, "función");
            funcion.setLocalizedLabel(ENGLISH, "function");
            funcion.setLocalizedLabel(SPANISH, "función");
            /**/
            codigoRecurso.setLocalizedDescription(ENGLISH, "code of the resource on which the function was executed");
            codigoRecurso.setLocalizedDescription(SPANISH, "código del recurso sobre el que se ejecutó la función");
            codigoRecurso.setLocalizedLabel(ENGLISH, "resource code");
            codigoRecurso.setLocalizedLabel(SPANISH, "código del recurso");
            /**/
            diferente.setLocalizedLabel(ENGLISH, "different");
            diferente.setLocalizedLabel(SPANISH, "diferente");
            // </editor-fold>
        }

    }

    protected EmitirInformeRastrosConParametros informeRastrosConParametros;

    @ReportOperationClass(viewField = "rastrosConParametros")
    @OperationClass(access = OperationAccess.PROTECTED)
    public class EmitirInformeRastrosConParametros extends ReportOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of EmitirInformeRastrosConParametros's attributes">
            setLocalizedLabel(ENGLISH, "generate function trail report with parameters");
            setLocalizedLabel(SPANISH, "generar informe de rastros con parámetros");
            setLocalizedShortLabel(ENGLISH, "function trail report with parameters");
            setLocalizedShortLabel(SPANISH, "informe de rastros con parámetros");
//          setLocalizedCollectionLabel(ENGLISH, "Function Trails with Parameters");
//          setLocalizedCollectionLabel(SPANISH, "Rastros de Función con Parámetros");
            setLocalizedDescription(ENGLISH, "issue a report of function execution audit trails with parameters");
            setLocalizedDescription(SPANISH, "emitir un informe de rastros de ejecución de función con sus parámetros");
            // </editor-fold>
        }

        @ParameterField(linkedColumn = "fecha_hora_ejecucion_1", operator = StandardRelationalOp.GTEQ)
        protected TimestampParameter desde;

        @ParameterField(linkedColumn = "fecha_hora_ejecucion_1", operator = StandardRelationalOp.LTEQ)
        protected TimestampParameter hasta;

        @ParameterField(linkedColumn = "usuario_1")
        protected Usuario usuario;

        @ParameterField(linkedColumn = "funcion_1")
        protected Funcion funcion;

        @ParameterField(linkedColumn = "codigo_recurso_1")
        protected StringParameter codigoRecurso;

        @ParameterField(linkedField = "diferenteValor")
        protected BooleanParameter diferente;

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
            // <editor-fold defaultstate="collapsed" desc="localization of EmitirInformeRastrosConParametros's parameters">
            desde.setLocalizedDescription(ENGLISH, "date and time of the function execution");
            desde.setLocalizedDescription(SPANISH, "fecha y hora de la ejecución de la función");
            desde.setLocalizedLabel(ENGLISH, "from");
            desde.setLocalizedLabel(SPANISH, "desde");
            /**/
            hasta.setLocalizedDescription(ENGLISH, "date and time of the function execution");
            hasta.setLocalizedDescription(SPANISH, "fecha y hora de la ejecución de la función");
            hasta.setLocalizedLabel(ENGLISH, "to");
            hasta.setLocalizedLabel(SPANISH, "hasta");
            /**/
            usuario.setLocalizedDescription(ENGLISH, "user who executed the function");
            usuario.setLocalizedDescription(SPANISH, "usuario que ejecutó la función");
            usuario.setLocalizedLabel(ENGLISH, "user");
            usuario.setLocalizedLabel(SPANISH, "usuario");
            /**/
            funcion.setLocalizedDescription(ENGLISH, "function");
            funcion.setLocalizedDescription(SPANISH, "función");
            funcion.setLocalizedLabel(ENGLISH, "function");
            funcion.setLocalizedLabel(SPANISH, "función");
            /**/
            codigoRecurso.setLocalizedDescription(ENGLISH, "code of the resource on which the function was executed");
            codigoRecurso.setLocalizedDescription(SPANISH, "código del recurso sobre el que se ejecutó la función");
            codigoRecurso.setLocalizedLabel(ENGLISH, "resource code");
            codigoRecurso.setLocalizedLabel(SPANISH, "código del recurso");
            /**/
            diferente.setLocalizedLabel(ENGLISH, "different");
            diferente.setLocalizedLabel(SPANISH, "diferente");
            // </editor-fold>
        }

    }

}
