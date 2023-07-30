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
@EntityClass(base = Kleenean.TRUE, independent = Kleenean.FALSE, resourceType = ResourceType.OPERATION, resourceGender = ResourceGender.FEMININE)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE)
@EntityInsertOperation(enabled = Kleenean.TRUE)
@EntityUpdateOperation(enabled = Kleenean.TRUE)
@EntityDeleteOperation(enabled = Kleenean.TRUE)
@EntityTableView(enabled = Kleenean.FALSE, rowsLimit = 100, rows = 100, writingViewWesternToolbarSnippet = "/resources/snippets/base/entity/VistaFuncionCol/botonOpenMasterUpdateDialog")
@EntityDetailView(enabled = Kleenean.FALSE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
@EntityTriggers(afterValue = Kleenean.TRUE)
public class VistaFuncionCol extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public VistaFuncionCol(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Override
    protected void addAllocationStrings() {
        super.addAllocationStrings();
        super.addAllocationStrings(
            "vista.funcion",
            "vista.propietario",
            "columna.funcion",
            "columna.parametro.tipoParametroDom",
            "columna.rangoAgregacion",
            "grupo.agregacion",
            "grupo.vista"
        );
    }

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of VistaFuncionCol's attributes">
        setLocalizedLabel(ENGLISH, "view column");
        setLocalizedLabel(SPANISH, "columna de vista");
        setLocalizedShortLabel(ENGLISH, "column");
        setLocalizedShortLabel(SPANISH, "columna");
        setLocalizedCollectionLabel(ENGLISH, "View Columns");
        setLocalizedCollectionLabel(SPANISH, "Columnas de Vista");
        setLocalizedCollectionShortLabel(ENGLISH, "Columns");
        setLocalizedCollectionShortLabel(SPANISH, "Columnas");
        /**/
        setLocalizedDescription(ENGLISH, "Each instance of " + b("View Columns") + " represents a "
            + "column of a view defined by the user."
            + "");
        setLocalizedDescription(SPANISH, "Cada instancia de " + b("Columnas de Vista") + " representa una "
            + "columna de una vista definida por el usuario."
            + "");
        /**/
        setLocalizedShortDescription(ENGLISH, "column of view defined by the user");
        setLocalizedShortDescription(SPANISH, "columna de vista definida por el usuario");
        /**/
        // </editor-fold>
    }

    @PrimaryKey
    public LongProperty id;

    @VersionProperty
    public LongProperty version;

    @NameProperty
    @PropertyField(hidden = Kleenean.TRUE, defaultCondition = DefaultCondition.UNCONDITIONALLY, defaultCheckpoint = Checkpoint.USER_INTERFACE)
    @StringField(maxLength = 200)
    public StringProperty nombre;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.BIDIRECTIONAL, view = MasterDetailView.TABLE_AND_DETAIL)
    @ColumnField(nullable = Kleenean.FALSE)
    public VistaFuncion vista;

//  20171213: remove foreign-key referring to FuncionParametro
//  @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE, quickAdding = QuickAddingFilter.MISSING)
    @ColumnField(nullable = Kleenean.FALSE)
    @EntityReferenceDisplay(style = EntityReferenceStyle.NAME)
    @EntityReferenceSearch(searchType = SearchType.LIST, listStyle = ListStyle.NAME)
    @PropertyField(required = Kleenean.TRUE, table = Kleenean.TRUE, create = Kleenean.TRUE, update = Kleenean.FALSE)
    public FuncionParametro columna;

    /**
     * string property field
     */
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.FALSE, defaultCondition = DefaultCondition.UNCONDITIONALLY, defaultCheckpoint = Checkpoint.USER_INTERFACE)
    @StringField(maxLength = 200)
    public StringProperty alias;

    /**
     * string property field
     */
    @PropertyField(table = Kleenean.TRUE, create = Kleenean.TRUE)
    @StringField(maxLength = 30)
    public StringProperty etiqueta;

    /**
     * integer property field
     */
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(required = Kleenean.FALSE, table = Kleenean.TRUE, create = Kleenean.TRUE)
    @MasterSequence(masterField = "vista", start = 10, step = 10, nextValueRule = NextValueRule.CEILING)
    public IntegerProperty secuencia;

    /**
     * many-to-one entity reference property field
     */
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(required = Kleenean.FALSE, table = Kleenean.TRUE, create = Kleenean.TRUE)
    public TipoAgregacion agregacion;

    /**
     * many-to-one entity reference property field
     */
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @EntityReferenceDisplay(style = EntityReferenceStyle.NAME)
    @EntityReferenceSearch(searchType = SearchType.LIST, listStyle = ListStyle.NAME)
    @PropertyField(required = Kleenean.FALSE, table = Kleenean.TRUE, create = Kleenean.TRUE)
    public VistaFuncionCol grupo;

    /**
     * boolean property field
     */
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(required = Kleenean.FALSE, table = Kleenean.TRUE, create = Kleenean.TRUE)
    public BooleanProperty orden;

    /**
     * boolean property field
     */
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(hidden = Kleenean.TRUE)
    public BooleanProperty visible;

    /**
     * boolean property field
     */
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(hidden = Kleenean.TRUE)
    public BooleanProperty graficable;

    /**
     * integer property field
     */
    @ColumnField(nullable = Kleenean.TRUE)
    @PropertyField(table = Kleenean.TRUE, create = Kleenean.TRUE, update = Kleenean.TRUE, defaultCheckpoint = Checkpoint.USER_INTERFACE)
    @PropertyAggregation(function = AggregateFunction.CUSTOM_MADE)
    public IntegerProperty pixeles;

    /**
     * integer property field
     */
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(hidden = Kleenean.TRUE, defaultCondition = DefaultCondition.UNCONDITIONALLY)
    public IntegerProperty detalle;

    /**
     * integer property field
     */
    @ColumnField(nullable = Kleenean.TRUE)
    @PropertyField(hidden = Kleenean.TRUE, defaultCheckpoint = Checkpoint.USER_INTERFACE, defaultCondition = DefaultCondition.UNCONDITIONALLY)
    @PropertyAggregation(function = AggregateFunction.CUSTOM_MADE)
    public IntegerProperty anchoPorMil;

    @Override
    protected void settleProperties() {
        super.settleProperties();
        /**/
        setOrderBy(vista, secuencia, id);
        /**/
        nombre.setDefaultValue(columna.nombreFuncionParametro);
        alias.setDefaultValue(columna.codigoFuncionParametro);
        /**/
        secuencia.setMinValue(0);
        secuencia.setMaxValue(10000);
        /**/
        agregacion.setInitialValue(columna.indice.isGreaterThan(0).
            and(columna.parametro.tipoParametroDom.isEqualTo(columna.parametro.tipoParametroDom.CODIGO)).then(agregacion.GRUPO)
        );
        /**/
        orden.setInitialValue(false);
        orden.setDefaultValue(false);
        /**/
        visible.setInitialValue(true);
        visible.setDefaultValue(true);
        /**/
        graficable.setInitialValue(true);
        graficable.setDefaultValue(true);
        /**/
        NumericExpression anchoColumna = vista.anchoPagina.minus(vista.margenIzquierdo.plus(vista.margenDerecho));
        /**/
        pixeles.setInitialValue(columna.parametro.pixeles);
        pixeles.setDefaultValue(columna.parametro.pixeles);
        pixeles.setMinValue(0);
        pixeles.setMaxValue(anchoColumna);
        pixeles.setPrimalMinValue(0);
        pixeles.setPrimalMaxValue(1260); // Tabloid Extra (12x18) -> 1296 - 18 - 18
        /**/
        detalle.setInitialValue(agregacion.isEqualTo(agregacion.GRUPO).or(grupo.isNotNull()).then(0).otherwise(pixeles));
        detalle.setDefaultValue(agregacion.isEqualTo(agregacion.GRUPO).or(grupo.isNotNull()).then(0).otherwise(pixeles));
        detalle.keepSumOn(vista.anchoDetalle);
        /**/
        anchoPorMil.setInitialValue(pixeles.times(1000).over(anchoColumna).toInteger());
        anchoPorMil.setDefaultValue(pixeles.times(1000).over(anchoColumna).toInteger());
        anchoPorMil.setMinValue(0);
        anchoPorMil.setMaxValue(1000);
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of VistaFuncionCol's properties">
        /**/
        String english, spanish;
        /**/
        nombre.setLocalizedLabel(ENGLISH, "view column name");
        nombre.setLocalizedLabel(SPANISH, "nombre de la columna de vista");
        nombre.setLocalizedShortLabel(ENGLISH, "name");
        nombre.setLocalizedShortLabel(SPANISH, "nombre");
        /**/
        vista.setLocalizedLabel(ENGLISH, "view");
        vista.setLocalizedLabel(SPANISH, "vista");
        /**/
        columna.setLocalizedLabel(ENGLISH, "column");
        columna.setLocalizedLabel(SPANISH, "columna");
        /**/
        columna.nombreFuncionParametro.setLocalizedLabel(ENGLISH, "column name");
        columna.nombreFuncionParametro.setLocalizedLabel(SPANISH, "nombre de la columna");
        /**/
        alias.setLocalizedLabel(ENGLISH, "alias");
        alias.setLocalizedLabel(SPANISH, "alias");
        /**/
        etiqueta.setLocalizedLabel(ENGLISH, "label");
        etiqueta.setLocalizedLabel(SPANISH, "etiqueta");
        /**/
        secuencia.setLocalizedLabel(ENGLISH, "sequence");
        secuencia.setLocalizedLabel(SPANISH, "secuencia");
        /**/
        agregacion.setLocalizedLabel(ENGLISH, "aggregation");
        agregacion.setLocalizedLabel(SPANISH, "agregación");
        agregacion.setLocalizedInitialValueTag(ENGLISH, "If " + b("index") + _of_ + b("column") + " > 0"
            + " and " + b("type") + _of_ + b("parameter") + _of_ + b("column") + " = CODE then GROUP");
        agregacion.setLocalizedInitialValueTag(SPANISH, "Sí " + b("índice") + _de_ + b("columna") + " > 0"
            + " y " + b("tipo") + _de_ + b("parámetro") + _de_ + b("columna") + " = CODIGO entonces GRUPO");
        /**/
        agregacion.nombre.setLocalizedLabel(ENGLISH, "aggregation name");
        agregacion.nombre.setLocalizedLabel(SPANISH, "nombre de la agregación");
        /**/
        grupo.setLocalizedLabel(ENGLISH, "group");
        grupo.setLocalizedLabel(SPANISH, "grupo");
        /**/
        grupo.nombre.setLocalizedLabel(ENGLISH, "group name");
        grupo.nombre.setLocalizedLabel(SPANISH, "nombre del grupo");
        /**/
        orden.setLocalizedLabel(ENGLISH, "order");
        orden.setLocalizedLabel(SPANISH, "orden");
        /**/
        visible.setLocalizedLabel(ENGLISH, "visible");
        visible.setLocalizedLabel(SPANISH, "visible");
        /**/
        graficable.setLocalizedLabel(ENGLISH, "chartable");
        graficable.setLocalizedLabel(SPANISH, "graficable");
        /**/
        pixeles.setLocalizedLabel(ENGLISH, "width");
        pixeles.setLocalizedLabel(SPANISH, "ancho");
        pixeles.setLocalizedTooltip(ENGLISH, "width of the column in pixels");
        pixeles.setLocalizedTooltip(SPANISH, "anchura de la columna expresada en pixeles");
        pixeles.setLocalizedAggregateTitle(ENGLISH, "pixels available for detail columns");
        pixeles.setLocalizedAggregateTitle(SPANISH, "pixeles disponibles para columnas del detalle");
        english = b("pixels") + _of_ + b("parameter") + _of_ + b("column");
        spanish = b("píxeles") + _de_ + b("parámetro") + _de_ + b("columna");
        pixeles.setLocalizedInitialValueTag(ENGLISH, english);
        pixeles.setLocalizedInitialValueTag(SPANISH, spanish);
        pixeles.setLocalizedDefaultValueTag(ENGLISH, english);
        pixeles.setLocalizedDefaultValueTag(SPANISH, spanish);
        pixeles.setLocalizedMaximumValueTag(ENGLISH, b("print area width") + _of_ + b("view"));
        pixeles.setLocalizedMaximumValueTag(SPANISH, b("ancho de impresión") + _de_ + b("vista"));
        /**/
        detalle.setLocalizedLabel(ENGLISH, "width in detail");
        detalle.setLocalizedLabel(SPANISH, "ancho en el detalle");
        detalle.setLocalizedShortLabel(ENGLISH, "@detail");
        detalle.setLocalizedShortLabel(SPANISH, "@detalle");
        /**/
        anchoPorMil.setLocalizedLabel(ENGLISH, "width \u2030");
        anchoPorMil.setLocalizedLabel(SPANISH, "ancho \u2030");
        anchoPorMil.setLocalizedShortLabel(ENGLISH, "\u2030");
        anchoPorMil.setLocalizedShortLabel(SPANISH, "\u2030");
        anchoPorMil.setLocalizedTooltip(ENGLISH, "column width as a fraction of 1000 (\u2030)");
        anchoPorMil.setLocalizedTooltip(SPANISH, "anchura de la columna expresada como una fracción de 1000 (\u2030)");
        anchoPorMil.setLocalizedAggregateTitle(ENGLISH, "per mille (\u2030) of the width available for detail columns");
        anchoPorMil.setLocalizedAggregateTitle(SPANISH, "por mil (\u2030) del ancho disponible para columnas del detalle");
        english = "1000 * " + b("pixels") + " / " + b("print area width") + _of_ + b("view");
        spanish = "1000 * " + b("píxeles") + " / " + b("ancho de impresión") + _de_ + b("vista");
        anchoPorMil.setLocalizedInitialValueTag(ENGLISH, english);
        anchoPorMil.setLocalizedInitialValueTag(SPANISH, spanish);
        anchoPorMil.setLocalizedDefaultValueTag(ENGLISH, english);
        anchoPorMil.setLocalizedDefaultValueTag(SPANISH, spanish);
        /**/
        // </editor-fold>
    }

    protected Key uk_vista_funcion_col_0001;

    @Override
    protected void settleKeys() {
        super.settleKeys();
        uk_vista_funcion_col_0001.setUnique(true);
        uk_vista_funcion_col_0001.newKeyField(vista, columna);
    }

    @Override
    protected void settleLinks() {
        super.settleLinks();
        linkForeignOwnerProperty(vista.propietario);
    }

    protected Check check101;

    protected Segment conGrupo, sinGrupo;

    protected Segment privadas;

    protected BooleanExpression claim101, claim102, claim103;

    @Override
    protected void settleExpressions() {
        super.settleExpressions();
        /*
        check011 = columna.esParametroVinculado.implies(agregacion.isNullOrEqualTo(agregacion.GRUPO));
        /**/
        check101 = grupo.isNotNull().implies(grupo.isNotEqualTo(this));
        /**/
        conGrupo = agregacion.isEqualTo(agregacion.GRUPO).or(grupo.isNotNull());
        sinGrupo = agregacion.isNullOrNotEqualTo(agregacion.GRUPO).and(grupo.isNull());
        privadas = vista.publica.isFalse().and(vista.especial.isFalse());
        /**/
        claim101 = columna.funcion.isEqualTo(vista.funcion).and(columna.parametro.pixeles.isGreaterThan(0));
        claim102 = agregacion.rangos.contains(columna.rangoAgregacion.numero);
        claim103 = grupo.agregacion.isEqualTo(grupo.agregacion.GRUPO).and(grupo.vista.isEqualTo(vista));
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of VistaFuncionCol's expressions">
        /*
        check011.setLocalizedLabel(ENGLISH, "check aggregation");
        check011.setLocalizedLabel(SPANISH, "chequear agregacion");
        check011.setLocalizedDescription(ENGLISH, "only Group can be specified if the column is linked");
        check011.setLocalizedDescription(SPANISH, "solo se puede especificar Grupo si la columna es vinculada");
        check011.setLocalizedErrorMessage(ENGLISH, "invalid aggregation: only Group can be specified if the column is linked");
        check011.setLocalizedErrorMessage(SPANISH, "agregación inválida: solo se puede especificar Grupo si la columna es vinculada");
        /**/
        check101.setLocalizedLabel(ENGLISH, "check group");
        check101.setLocalizedLabel(SPANISH, "chequear grupo");
        check101.setLocalizedDescription(ENGLISH, "the group can not be the same column");
        check101.setLocalizedDescription(SPANISH, "el grupo no puede ser la misma columna");
        check101.setLocalizedErrorMessage(ENGLISH, "the group is the same column");
        check101.setLocalizedErrorMessage(SPANISH, "el grupo es la misma columna");
        /**/
        conGrupo.setLocalizedCollectionLabel(ENGLISH, "columns that belong to one of the report groups");
        conGrupo.setLocalizedCollectionLabel(SPANISH, "columnas que pertenecen a uno de los grupos del informe");
        conGrupo.setLocalizedCollectionShortLabel(ENGLISH, "Group columns");
        conGrupo.setLocalizedCollectionShortLabel(SPANISH, "Columnas de grupo");
        /**/
        sinGrupo.setLocalizedCollectionLabel(ENGLISH, "columns that belong to the report detail");
        sinGrupo.setLocalizedCollectionLabel(SPANISH, "columnas que pertenecen al detalle del informe");
        sinGrupo.setLocalizedCollectionShortLabel(ENGLISH, "Detail columns");
        sinGrupo.setLocalizedCollectionShortLabel(SPANISH, "Columnas del detalle");
        /**/
        privadas.setLocalizedErrorMessage(ENGLISH, "the view is public or special");
        privadas.setLocalizedErrorMessage(SPANISH, "la vista es pública o especial");
        /**/
        claim101.setLocalizedLabel(ENGLISH, "verify column");
        claim101.setLocalizedLabel(SPANISH, "chequear columna");
        claim101.setLocalizedDescription(ENGLISH, "the column size (pixels) must be greater than 0 and its function must be equal to the function associated with the view");
        claim101.setLocalizedDescription(SPANISH, "el tamaño (pixeles) de la columna debe ser mayor que 0 y su función debe ser igual a la función asociada a la vista");
        claim101.setLocalizedErrorMessage(ENGLISH, "the column size (pixels) is 0 or its function is not equal to the function associated with the view");
        claim101.setLocalizedErrorMessage(SPANISH, "el tamaño (pixeles) de la columna es 0 o su función no es igual a la función asociada a la vista");
        /**/
        claim102.setLocalizedLabel(ENGLISH, "verify aggregation");
        claim102.setLocalizedLabel(SPANISH, "chequear agregación");
        claim102.setLocalizedDescription(ENGLISH, "the aggregation is compatible with the column");
        claim102.setLocalizedDescription(SPANISH, "la agregación es compatible con el columna");
        claim102.setLocalizedErrorMessage(ENGLISH, "the aggregation is not compatible with the column");
        claim102.setLocalizedErrorMessage(SPANISH, "la agregación es incompatible con el columna");
        /**/
        claim103.setLocalizedLabel(ENGLISH, "verify group");
        claim103.setLocalizedLabel(SPANISH, "chequear grupo");
        claim103.setLocalizedDescription(ENGLISH, "the group belongs to the view");
        claim103.setLocalizedDescription(SPANISH, "el grupo pertenece a la vista");
        claim103.setLocalizedErrorMessage(ENGLISH, "the group does not belong to the view");
        claim103.setLocalizedErrorMessage(SPANISH, "el grupo no pertenece a la vista");
        /**/
        // </editor-fold>
        /**/
    }

    @Override
    protected void settleFilters() {
        super.settleFilters();
        /**/
        setInsertFilter(vista.privadas);
        setUpdateFilter(privadas);
        setDeleteFilter(privadas);
        /**/
        addSelectSegment(conGrupo, sinGrupo);
        /**/
        columna.setSearchQueryFilter(claim101);
        agregacion.setSearchQueryFilter(claim102);
        grupo.setSearchQueryFilter(claim103);
    }

}
