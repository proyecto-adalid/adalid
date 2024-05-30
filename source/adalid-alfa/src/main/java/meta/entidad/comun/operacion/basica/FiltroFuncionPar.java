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

import adalid.core.*;
import adalid.core.annotations.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import adalid.core.properties.*;
import java.lang.reflect.Field;
import meta.entidad.comun.configuracion.basica.ext.FuncionParametro;

/**
 * @author Jorge Campins
 */
@EntityClass(base = Kleenean.TRUE, independent = Kleenean.FALSE, resourceType = ResourceType.OPERATION, resourceGender = ResourceGender.MASCULINE)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE)
@EntityInsertOperation(enabled = Kleenean.TRUE)
@EntityUpdateOperation(enabled = Kleenean.TRUE)
@EntityDeleteOperation(enabled = Kleenean.TRUE)
@EntityTableView(enabled = Kleenean.FALSE, responsiveMode = TableResponsiveMode.PRIORITY, rowsLimit = 100, rows = 100, writingViewWesternToolbarSnippet = "/resources/snippets/base/entity/FiltroFuncionPar/botonOpenMasterUpdateDialog")
@EntityDetailView(enabled = Kleenean.FALSE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
@EntityTriggers(afterValue = Kleenean.TRUE)
public class FiltroFuncionPar extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public FiltroFuncionPar(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Override
    protected void addAllocationStrings() {
        super.addAllocationStrings();
        super.addAllocationStrings(
            "filtroFuncion.usuario",
            "filtroFuncion.funcion",
            "funcionParametro.claseJava",
            "funcionParametro.claseRecursoValor",
            "funcionParametro.funcion",
            "funcionParametro.rangoComparacion",
            "tipoValorCriterio.claseJava"
        );
    }

    @PrimaryKey
    public LongProperty id;

    @VersionProperty
    public LongProperty version;

    @ForeignKey(onDelete = OnDeleteAction.CASCADE, onUpdate = OnUpdateAction.CASCADE)
    @ManyToOne(navigability = Navigability.BIDIRECTIONAL, view = MasterDetailView.TABLE_AND_DETAIL)
    @ColumnField(nullable = Kleenean.FALSE)
    public FiltroFuncion filtroFuncion;

//  20171213: remove foreign-key referring to FuncionParametro
//  @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE, quickAdding = QuickAddingFilter.ANY)
    @ColumnField(nullable = Kleenean.FALSE)
    @EntityReferenceSearch(searchType = SearchType.LIST, listStyle = ListStyle.NAME)
    @PropertyField(required = Kleenean.TRUE, table = Kleenean.TRUE, detail = Kleenean.TRUE, report = Kleenean.TRUE, export = Kleenean.TRUE)
    public FuncionParametro funcionParametro;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(required = Kleenean.TRUE, table = Kleenean.TRUE, detail = Kleenean.TRUE, report = Kleenean.TRUE, export = Kleenean.TRUE)
    public OperadorCom operadorCom;

    /*
    @ColumnField(calculable = Kleenean.TRUE)
    @PropertyField(hidden = Kleenean.TRUE)
    public StringProperty claseJava;

    /**/
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.TRUE)
    @PropertyField(table = Kleenean.TRUE, detail = Kleenean.TRUE, create = Kleenean.TRUE, update = Kleenean.TRUE)
    public TipoValorCriterio tipoValorCriterio;

    @VariantString
    @PropertyField(table = Kleenean.TRUE, detail = Kleenean.TRUE, create = Kleenean.TRUE, update = Kleenean.TRUE, anchor = "tipoValorCriterio", anchorType = AnchorType.INLINE)
    public StringProperty valor;

    @ColumnField(nullable = Kleenean.TRUE)
    @PropertyField(responsivePriority = 6, table = Kleenean.TRUE, detail = Kleenean.TRUE, create = Kleenean.TRUE, update = Kleenean.TRUE, anchor = "tipoValorCriterio", anchorType = AnchorType.INLINE)
    public IntegerProperty cifraValorTemporal;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.TRUE)
    @PropertyField(responsivePriority = 6, table = Kleenean.TRUE, detail = Kleenean.TRUE, create = Kleenean.TRUE, update = Kleenean.TRUE, anchor = "tipoValorCriterio", anchorType = AnchorType.INLINE)
    public CampoValorTemporal campoValorTemporal;

    @PropertyField(hidden = Kleenean.TRUE)
    public TimestampProperty valorFechaHora;

    @PropertyField(hidden = Kleenean.TRUE, defaultCondition = DefaultCondition.UNCONDITIONALLY, defaultCheckpoint = Checkpoint.USER_INTERFACE)
    public LongProperty idClaseRecursoValor;

    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
//  @PropertyField(table = Kleenean.TRUE, detail = Kleenean.TRUE, create = Kleenean.TRUE, update = Kleenean.TRUE, required = Kleenean.TRUE, search = Kleenean.FALSE, filter = Kleenean.FALSE, sort = Kleenean.FALSE)
    @PropertyField(hidden = Kleenean.TRUE)
    public RecursoValor recursoValor;

    @PropertyField(hidden = Kleenean.TRUE)
    public LongProperty idRecursoValor;

//  @PropertyField(table = Kleenean.FALSE, detail = Kleenean.FALSE, search = Kleenean.TRUE)
    @PropertyField(hidden = Kleenean.TRUE)
    public StringProperty codigoRecursoValor;

//  @PropertyField(table = Kleenean.FALSE, detail = Kleenean.FALSE, search = Kleenean.TRUE)
    @PropertyField(hidden = Kleenean.TRUE)
    public StringProperty nombreRecursoValor;

    @PropertyField(hidden = Kleenean.TRUE)
    @StringField(maxLength = 200)
    public StringProperty paginaRecurso;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of FiltroFuncionPar's attributes">
        setLocalizedLabel(ENGLISH, "query filter criterion");
        setLocalizedLabel(SPANISH, "criterio de filtro de búsqueda");
        setLocalizedShortLabel(ENGLISH, "criterion");
        setLocalizedShortLabel(SPANISH, "criterio");
        setLocalizedCollectionLabel(ENGLISH, "Query Filter Criteria");
        setLocalizedCollectionLabel(SPANISH, "Criterios de Filtro de Búsqueda");
        setLocalizedCollectionShortLabel(ENGLISH, "Criteria");
        setLocalizedCollectionShortLabel(SPANISH, "Criterios");
        /**/
        setLocalizedDescription(ENGLISH, "Each instance of " + b("Query Filter Criteria") + " represents a "
            + "criterion of query filter defined by the user."
            + "");
        setLocalizedDescription(SPANISH, "Cada instancia de " + b("Criterios de Filtro de Búsqueda") + " representa un "
            + "criterio de filtro de búsqueda definido por el usuario."
            + "");
        /**/
        setLocalizedShortDescription(ENGLISH, "criterion of query filter defined by the user");
        setLocalizedShortDescription(SPANISH, "criterio de filtro de búsqueda definido por el usuario");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        /*
        claseJava.setCalculableValueExpression(funcionParametro.claseJavaFuncionParametro);
        claseJava.setDefaultValue(funcionParametro.claseJavaFuncionParametro);
        claseJava.setInitialValue(funcionParametro.claseJavaFuncionParametro);
        /**/
        tipoValorCriterio.setInitialValue(operadorCom.necesitaValor.then(tipoValorCriterio.SIMPLE));
        tipoValorCriterio.setDefaultValue(operadorCom.necesitaValor.then(tipoValorCriterio.SIMPLE));
        /**/
        cifraValorTemporal.setMinValue(0);
        cifraValorTemporal.setMaxValue(6000); // TemporalAddend.LIMITS = {6700, 6700, 6700 * 12, 6700 * 365, 6700 * 365 * 24};
        /**/
        BooleanExpression temporalAddend = tipoValorCriterio.isIn(
            tipoValorCriterio.HOY_MAS,
            tipoValorCriterio.HOY_MENOS,
            tipoValorCriterio.AHORA_MAS,
            tipoValorCriterio.AHORA_MENOS
        );
        /**/
        CharacterExpression cifraX = cifraValorTemporal.isNull().then("0").otherwise(cifraValorTemporal.toCharString());
        CharacterExpression campoX = campoValorTemporal.isNull().then("D").otherwise(campoValorTemporal.letra);
        CharacterExpression valorX = temporalAddend.then(concat(tipoValorCriterio.signo, cifraX, campoX));
        /**/
        valor.setInitialValue(valorX);
        valor.setDefaultValue(valorX);
        /**/
        valor.setTypeNameExpression(funcionParametro.claseJavaFuncionParametro);
        /**/
        idClaseRecursoValor.setDefaultValue(funcionParametro.claseRecursoValor.id);
        idClaseRecursoValor.setInitialValue(funcionParametro.claseRecursoValor.id);
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of FiltroFuncionPar's properties">
        /**/
        String english, spanish;
        /**/
        filtroFuncion.setLocalizedLabel(ENGLISH, "query filter");
        filtroFuncion.setLocalizedLabel(SPANISH, "filtro de búsqueda");
        filtroFuncion.setLocalizedShortLabel(ENGLISH, "filter");
        filtroFuncion.setLocalizedShortLabel(SPANISH, "filtro");
        /**/
        funcionParametro.setLocalizedLabel(ENGLISH, "function parameter");
        funcionParametro.setLocalizedLabel(SPANISH, "parámetro de función");
        funcionParametro.setLocalizedShortLabel(ENGLISH, "parameter");
        funcionParametro.setLocalizedShortLabel(SPANISH, "parámetro");
        /**/
        operadorCom.setLocalizedLabel(ENGLISH, "comparison operator");
        operadorCom.setLocalizedLabel(SPANISH, "operador de comparación");
        operadorCom.setLocalizedShortLabel(ENGLISH, "operator");
        operadorCom.setLocalizedShortLabel(SPANISH, "operador");
        /**/
        tipoValorCriterio.setLocalizedLabel(ENGLISH, "value type");
        tipoValorCriterio.setLocalizedLabel(SPANISH, "tipo de valor");
        tipoValorCriterio.setLocalizedAnchorLabel(ENGLISH, "value");
        tipoValorCriterio.setLocalizedAnchorLabel(SPANISH, "valor");
        tipoValorCriterio.setLocalizedAnchoredLabel(ENGLISH, "type");
        tipoValorCriterio.setLocalizedAnchoredLabel(SPANISH, "tipo");
        /**/
        english = "If " + b("operator") + " needs a value, then SIMPLE";
        spanish = "Sí " + b("operador") + " necesita un valor, entonces SIMPLE";
        /**/
        tipoValorCriterio.setLocalizedInitialValueTag(ENGLISH, english);
        tipoValorCriterio.setLocalizedInitialValueTag(SPANISH, spanish);
        tipoValorCriterio.setLocalizedDefaultValueTag(ENGLISH, english);
        tipoValorCriterio.setLocalizedDefaultValueTag(SPANISH, spanish);
        /**/
        cifraValorTemporal.setLocalizedLabel(ENGLISH, "quantity");
        cifraValorTemporal.setLocalizedLabel(SPANISH, "cantidad");
        /**/
        campoValorTemporal.setLocalizedLabel(ENGLISH, "unit");
        campoValorTemporal.setLocalizedLabel(SPANISH, "unidad");
        /**/
        valor.setLocalizedLabel(ENGLISH, "simple value");
        valor.setLocalizedLabel(SPANISH, "valor simple");
        /**/
        english = "If " + b("value type") + " is not SIMPLE, then " + b("quantity") + " concatenated with " + b("unit");
        spanish = "Si " + b("tipo de valor") + " no es SIMPLE, entonces " + b("cantidad") + " concatenada con " + b("unidad");
        /**/
        valor.setLocalizedInitialValueTag(ENGLISH, english);
        valor.setLocalizedInitialValueTag(SPANISH, spanish);
        valor.setLocalizedDefaultValueTag(ENGLISH, english);
        valor.setLocalizedDefaultValueTag(SPANISH, spanish);
        /**/
        valorFechaHora.setLocalizedLabel(ENGLISH, "timestamp value");
        valorFechaHora.setLocalizedLabel(SPANISH, "valor fecha hora");
        /**/
        idClaseRecursoValor.setLocalizedLabel(ENGLISH, "resource class");
        idClaseRecursoValor.setLocalizedLabel(SPANISH, "clase de recurso");
        /**/
        recursoValor.setLocalizedLabel(ENGLISH, "resource");
        recursoValor.setLocalizedLabel(SPANISH, "recurso");
        /**/
        idRecursoValor.setLocalizedLabel(ENGLISH, "resource value");
        idRecursoValor.setLocalizedLabel(SPANISH, "recurso valor");
        /**/
        codigoRecursoValor.setLocalizedLabel(ENGLISH, "code");
        codigoRecursoValor.setLocalizedLabel(SPANISH, "código");
        /**/
        nombreRecursoValor.setLocalizedLabel(ENGLISH, "name");
        nombreRecursoValor.setLocalizedLabel(SPANISH, "nombre");
        /**/
        paginaRecurso.setLocalizedLabel(ENGLISH, "resource page");
        paginaRecurso.setLocalizedLabel(SPANISH, "página recurso");
        // </editor-fold>
    }

    @Override
    protected void settleLinks() {
        super.settleLinks();
        linkForeignOwnerProperty(filtroFuncion.usuario);
    }

    protected Segment privados;

    protected BooleanExpression claim101, claim102, claim103, claim104;

    @Override
    protected void settleExpressions() {
        super.settleExpressions();
        /**/
        privados = filtroFuncion.esPublico.isFalse();
        /*
        privados = filtroFuncion.esPublico.isFalse().and(filtroFuncion.esEspecial.isFalse());
        /**/
        claim101 = funcionParametro.funcion.isEqualTo(filtroFuncion.funcion).and(funcionParametro.criterioBusqueda.isTrue());
        claim102 = operadorCom.rangos.contains(funcionParametro.rangoComparacion.numero);
        claim103 = tipoValorCriterio.clases.isNull().or(tipoValorCriterio.clases.contains(funcionParametro.claseJava.letra));
        claim104 = campoValorTemporal.tipos.contains(tipoValorCriterio.numero);
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of FiltroFuncionPar's expressions">
        /**/
        privados.setLocalizedErrorMessage(ENGLISH, "the filter is public");
        privados.setLocalizedErrorMessage(SPANISH, "el filtro es público");
        /*
        privados.setLocalizedErrorMessage(ENGLISH, "the filter is public or special");
        privados.setLocalizedErrorMessage(SPANISH, "el filtro es público o especial");
        /**/
        claim101.setLocalizedLabel(ENGLISH, "verify function parameter");
        claim101.setLocalizedLabel(SPANISH, "chequear parámetro de función");
        claim101.setLocalizedDescription(ENGLISH, "the parameter must be a search criterion and its function must be equal to the function associated with the filter");
        claim101.setLocalizedDescription(SPANISH, "el parámetro debe ser un criterio de búsqueda y su función debe ser igual a la función asociada al filtro");
        claim101.setLocalizedErrorMessage(ENGLISH, "the parameter is not a search criterion or its function is not equal to the function associated with the filter");
        claim101.setLocalizedErrorMessage(SPANISH, "el parámetro no es un criterio de búsqueda o su función no es igual a la función asociada al filtro");
        /**/
        claim102.setLocalizedLabel(ENGLISH, "verify comparison operator");
        claim102.setLocalizedLabel(SPANISH, "chequear operador de comparación");
        claim102.setLocalizedDescription(ENGLISH, "the comparison operator is compatible with the parameter");
        claim102.setLocalizedDescription(SPANISH, "el operador de comparación es compatible con el parámetro");
        claim102.setLocalizedErrorMessage(ENGLISH, "the comparison operator is not compatible with the parameter");
        claim102.setLocalizedErrorMessage(SPANISH, "el operador de comparación es incompatible con el parámetro");
        /**/
        claim103.setLocalizedLabel(ENGLISH, "verify value type");
        claim103.setLocalizedLabel(SPANISH, "chequear tipo de valor");
        claim103.setLocalizedDescription(ENGLISH, "the value type is compatible with the parameter");
        claim103.setLocalizedDescription(SPANISH, "el tipo de valor es compatible con el parámetro");
        claim103.setLocalizedErrorMessage(ENGLISH, "the value type is not compatible with the parameter");
        claim103.setLocalizedErrorMessage(SPANISH, "el tipo de valor es incompatible con el parámetro");
        /**/
        claim104.setLocalizedLabel(ENGLISH, "verify temporal value");
        claim104.setLocalizedLabel(SPANISH, "chequear valor temporal");
        claim104.setLocalizedDescription(ENGLISH, "the temporal value is compatible with the parameter value type");
        claim104.setLocalizedDescription(SPANISH, "el valor temporal es compatible con el tipo de valor del parámetro");
        claim104.setLocalizedErrorMessage(ENGLISH, "the temporal value is not compatible with the parameter value type");
        claim104.setLocalizedErrorMessage(SPANISH, "el valor temporal es incompatible con el tipo de valor del parámetro");
        /**/
        // </editor-fold>
        /**/
    }

    @Override
    protected void settleFilters() {
        super.settleFilters();
        /**/
        setInsertFilter(filtroFuncion.privados);
        setUpdateFilter(privados);
        setDeleteFilter(privados);
        /**/
        funcionParametro.setSearchQueryFilter(claim101);
        /**/
        operadorCom.setSearchQueryFilter(claim102);
        operadorCom.setModifyingFilter(funcionParametro.isNotNull());
        /**/
        BooleanExpression necesitaValor = and(funcionParametro.isNotNull(), operadorCom.isNotNull(), operadorCom.necesitaValor.isTrue());
        BooleanExpression valorTemporal = funcionParametro.claseJava.isIn(
            funcionParametro.claseJava.JAVA_DATE,
            funcionParametro.claseJava.JAVA_TIMESTAMP
        );
        BooleanExpression necesitaValorTemporal = and(necesitaValor, valorTemporal);
        BooleanExpression necesitaValorSimple = and(necesitaValor, tipoValorCriterio.isEqualTo(tipoValorCriterio.SIMPLE));
        BooleanExpression necesitaValorCompuesto = and(necesitaValorTemporal,
            tipoValorCriterio.isIn(
                tipoValorCriterio.HOY_MAS,
                tipoValorCriterio.HOY_MENOS,
                tipoValorCriterio.AHORA_MAS,
                tipoValorCriterio.AHORA_MENOS
            )
        );
        /**/
        tipoValorCriterio.setModifyingFilter(necesitaValorTemporal);
        tipoValorCriterio.setRenderingFilter(necesitaValor);
        tipoValorCriterio.setRequiringFilter(necesitaValorTemporal);
        tipoValorCriterio.setNullifyingFilter(not(necesitaValor));
        tipoValorCriterio.setSearchQueryFilter(claim103);
        /**/
        cifraValorTemporal.setModifyingFilter(necesitaValorCompuesto);
        cifraValorTemporal.setRenderingFilter(necesitaValorCompuesto);
        cifraValorTemporal.setRequiringFilter(necesitaValorCompuesto);
        cifraValorTemporal.setNullifyingFilter(not(necesitaValorCompuesto));
        /**/
        campoValorTemporal.setModifyingFilter(necesitaValorCompuesto);
        campoValorTemporal.setRenderingFilter(necesitaValorCompuesto);
        campoValorTemporal.setRequiringFilter(necesitaValorCompuesto);
        campoValorTemporal.setNullifyingFilter(not(necesitaValorCompuesto));
        campoValorTemporal.setSearchQueryFilter(claim104);
        /* until 05/03/2023
//      valor.setModifyingFilter(necesitaValor.and(funcionParametro.tipoValor.isEqualTo(funcionParametro.tipoValor.CONTINUO)));
        valor.setModifyingFilter(necesitaValor);
        valor.setNullifyingFilter(not(necesitaValor));
        /**/
        valor.setModifyingFilter(necesitaValorSimple);
        valor.setRenderingFilter(necesitaValor);
        valor.setRequiringFilter(necesitaValorSimple);
        valor.setNullifyingFilter(not(necesitaValor));
        /*
        recursoValor.setModifyingFilter(necesitaValor.and(funcionParametro.tipoValor.isEqualTo(funcionParametro.tipoValor.RECURSO)));
        recursoValor.setNullifyingFilter(not(necesitaValor));
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of FiltroFuncionPar's filters">
        /**/
        String english = "If " + b("operator") + " needs a value";
        String spanish = "Sí " + b("operador") + " necesita un valor";
        /**/
        tipoValorCriterio.setLocalizedRequiringFilterTag(ENGLISH, english);
        tipoValorCriterio.setLocalizedRequiringFilterTag(SPANISH, spanish);
        /**/
        english = english + " and " + b("value type"); // is or is not SIMPLE
        spanish = spanish + " y " + b("tipo de valor"); // es o no es SIMPLE
        /**/
        cifraValorTemporal.setLocalizedRequiringFilterTag(ENGLISH, english + " is not SIMPLE");
        cifraValorTemporal.setLocalizedRequiringFilterTag(SPANISH, spanish + " no es SIMPLE");
        /**/
        campoValorTemporal.setLocalizedRequiringFilterTag(ENGLISH, english + " is not SIMPLE");
        campoValorTemporal.setLocalizedRequiringFilterTag(SPANISH, spanish + " no es SIMPLE");
        /**/
        valor.setLocalizedRequiringFilterTag(ENGLISH, english + " is SIMPLE");
        valor.setLocalizedRequiringFilterTag(SPANISH, spanish + " es SIMPLE");
        /**/
        // </editor-fold>
        /**/
    }

}
