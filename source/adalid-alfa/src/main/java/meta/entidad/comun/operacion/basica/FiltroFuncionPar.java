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
@EntityTableView(enabled = Kleenean.FALSE)
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

    @PrimaryKey
    public LongProperty id;

    @VersionProperty
    public LongProperty version;

    @ForeignKey(onDelete = OnDeleteAction.CASCADE, onUpdate = OnUpdateAction.CASCADE)
    @ManyToOne(navigability = Navigability.BIDIRECTIONAL, view = MasterDetailView.TABLE_AND_DETAIL)
    @ColumnField(nullable = Kleenean.FALSE)
    @Allocation(maxDepth = 2, maxRound = 0)
    @Filter(owner = Kleenean.FALSE, segment = Kleenean.FALSE)
    public FiltroFuncion filtroFuncion;

//  20171213: remove foreign-key referring to FuncionParametro
//  @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE, quickAdding = QuickAddingFilter.ANY)
    @ColumnField(nullable = Kleenean.FALSE)
    @EntityReferenceDisplay(style = EntityReferenceStyle.NAME_AND_CHARACTER_KEY)
    @EntityReferenceSearch(searchType = SearchType.LIST, listStyle = ListStyle.NAME)
    @PropertyField(required = Kleenean.TRUE, table = Kleenean.TRUE, detail = Kleenean.TRUE, report = Kleenean.TRUE, export = Kleenean.TRUE)
    @Allocation(maxDepth = 2, maxRound = 0)
    public FuncionParametro funcionParametro;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(required = Kleenean.TRUE, table = Kleenean.TRUE, detail = Kleenean.TRUE, report = Kleenean.TRUE, export = Kleenean.TRUE)
    @Allocation(maxDepth = 1, maxRound = 0)
    public OperadorCom operadorCom;

    /*
    @ColumnField(calculable = Kleenean.TRUE)
    @PropertyField(hidden = Kleenean.TRUE)
    public StringProperty claseJava;

    /**/
    @VariantString
    @PropertyField(table = Kleenean.TRUE, detail = Kleenean.TRUE, create = Kleenean.TRUE, update = Kleenean.TRUE, required = Kleenean.TRUE)
    public StringProperty valor;

    @PropertyField(hidden = Kleenean.TRUE)
    public TimestampProperty valorFechaHora;

    @PropertyField(hidden = Kleenean.TRUE, defaultCondition = DefaultCondition.UNCONDITIONALLY, defaultCheckpoint = Checkpoint.USER_INTERFACE)
    public LongProperty idClaseRecursoValor;

    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
//  @PropertyField(table = Kleenean.TRUE, detail = Kleenean.TRUE, create = Kleenean.TRUE, update = Kleenean.TRUE, required = Kleenean.TRUE, search = Kleenean.FALSE, filter = Kleenean.FALSE, sort = Kleenean.FALSE)
    @PropertyField(hidden = Kleenean.TRUE)
    @Allocation(maxDepth = 1, maxRound = 0)
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
        setLocalizedDescription(ENGLISH, "criterion of query filter defined by the end user");
        setLocalizedDescription(SPANISH, "criterio de filtro de búsqueda definido por el usuario final");
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
        valor.setTypeNameExpression(funcionParametro.claseJavaFuncionParametro);
        /**/
        idClaseRecursoValor.setDefaultValue(funcionParametro.claseRecursoValor.id);
        idClaseRecursoValor.setInitialValue(funcionParametro.claseRecursoValor.id);
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of FiltroFuncionPar's properties">
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
        valor.setLocalizedLabel(ENGLISH, "value");
        valor.setLocalizedLabel(SPANISH, "valor");
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

    @Override
    protected void settleExpressions() {
        super.settleExpressions();
        /**/
        privados = filtroFuncion.esPublico.isFalse();
        /*
        privados = filtroFuncion.esPublico.isFalse().and(filtroFuncion.esEspecial.isFalse());
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of VistaFuncionCol's expressions">
        /**/
        privados.setLocalizedErrorMessage(ENGLISH, "the filter is public");
        privados.setLocalizedErrorMessage(SPANISH, "el filtro es público");
        /*
        privados.setLocalizedErrorMessage(ENGLISH, "the filter is public or special");
        privados.setLocalizedErrorMessage(SPANISH, "el filtro es público o especial");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleFilters() {
        super.settleFilters();
        /**/
        setInsertFilter(filtroFuncion.privados);
        setUpdateFilter(privados);
        setDeleteFilter(privados);
        /**/
        funcionParametro.setSearchQueryFilter(funcionParametro.funcion.isEqualTo(filtroFuncion.funcion).
            and(funcionParametro.criterioBusqueda.isTrue()));
        /**/
        operadorCom.setSearchQueryFilter(operadorCom.rangos.contains(funcionParametro.rangoComparacion.numero));
        operadorCom.setModifyingFilter(funcionParametro.isNotNull());
        BooleanExpression necesitaValor = and(funcionParametro.isNotNull(), operadorCom.isNotNull(), operadorCom.necesitaValor.isTrue());
//      valor.setModifyingFilter(necesitaValor.and(funcionParametro.tipoValor.isEqualTo(funcionParametro.tipoValor.CONTINUO)));
        valor.setModifyingFilter(necesitaValor);
        valor.setNullifyingFilter(not(necesitaValor));
        /*
        recursoValor.setModifyingFilter(necesitaValor.and(funcionParametro.tipoValor.isEqualTo(funcionParametro.tipoValor.RECURSO)));
        recursoValor.setNullifyingFilter(not(necesitaValor));
        /**/
    }

}
