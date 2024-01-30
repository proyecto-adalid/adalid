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
import meta.entidad.comun.configuracion.basica.ClaseJava;
import meta.entidad.comun.configuracion.basica.Parametro;
import meta.entidad.comun.configuracion.basica.RangoAgregacion;
import meta.entidad.comun.configuracion.basica.RangoComparacion;
import meta.entidad.comun.configuracion.basica.TipoComparacion;
import meta.entidad.comun.configuracion.basica.TipoDatoPar;
import meta.entidad.comun.configuracion.basica.TipoParametro;
import meta.entidad.comun.configuracion.basica.TipoValor;

/**
 * @author Jorge Campins
 */
@EntityClass(catalog = Kleenean.TRUE, independent = Kleenean.FALSE, resourceType = ResourceType.CONFIGURATION, resourceGender = ResourceGender.MASCULINE)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE, access = OperationAccess.PUBLIC, onload = SelectOnloadOption.EXECUTE, rowsLimit = 500)
@EntityInsertOperation(enabled = Kleenean.FALSE)
@EntityUpdateOperation(enabled = Kleenean.FALSE)
@EntityDeleteOperation(enabled = Kleenean.FALSE)
@EntityTableView(enabled = Kleenean.TRUE, rowsLimit = 500, rows = 500)
@EntityDetailView(enabled = Kleenean.TRUE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
@EntityReferenceDisplay(style = EntityReferenceStyle.NAME_AND_CHARACTER_KEY)
public class FuncionParametro extends meta.entidad.comun.configuracion.basica.FuncionParametro {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public FuncionParametro(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.FALSE)
    public ClaseJava claseJava;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.FALSE)
    public TipoDatoPar tipoDatoPar;

    @PropertyField(hidden = Kleenean.TRUE)
    public LongProperty idListaValor;

    @PropertyField(hidden = Kleenean.TRUE)
    public LongProperty idClaseObjetoValor;

    @PropertyField(hidden = Kleenean.TRUE)
    @StringField(maxLength = 100)
    public StringProperty valorMinimo;

    @PropertyField(hidden = Kleenean.TRUE)
    @StringField(maxLength = 100)
    public StringProperty valorMaximo;

    @PropertyField(hidden = Kleenean.TRUE)
    @StringField(maxLength = 100)
    public StringProperty valorOmision;

    @ColumnField(nullable = Kleenean.FALSE)
    public BooleanProperty criterioBusqueda;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.TRUE)
    public BooleanProperty accesoRestringido;

    @ColumnField(nullable = Kleenean.FALSE)
    public BooleanProperty esParametroBusqueda;

    @ColumnField(nullable = Kleenean.FALSE)
    public BooleanProperty esParametroSinRastro;

    @ColumnField(nullable = Kleenean.FALSE)
    public BooleanProperty esParametroSegmento;

    @ColumnField(nullable = Kleenean.FALSE)
    public BooleanProperty esParametroHeredado;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.TRUE)
    public BooleanProperty esParametroVinculado;

    @ColumnField(nullable = Kleenean.FALSE)
    public BooleanProperty esPassword;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.FALSE)
    public IntegerProperty indice;

    @ForeignKey(onDelete = OnDeleteAction.CASCADE, onUpdate = OnUpdateAction.CASCADE)
    @ManyToOne(navigability = Navigability.BIDIRECTIONAL, view = MasterDetailView.TABLE_AND_DETAIL)
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.FALSE, search = Kleenean.TRUE)
    public Funcion funcion;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.CASCADE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.FALSE, search = Kleenean.TRUE)
    public Parametro parametro;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.FALSE)
    public TipoParametro tipoParametro;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(hidden = Kleenean.TRUE)
    public TipoComparacion tipoComparacion;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.FALSE)
    public TipoValor tipoValor;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.FALSE)
    public RangoAgregacion rangoAgregacion;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.FALSE)
    public RangoComparacion rangoComparacion;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    public Funcion funcionReferencia;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(table = Kleenean.TRUE)
    public ClaseRecurso claseRecursoValor;

    @Override
    protected void settleProperties() {
        super.settleProperties();
        /**/
        criterioBusqueda.setInitialValue(false);
        criterioBusqueda.setDefaultValue(false);
        accesoRestringido.setInitialValue(false);
        accesoRestringido.setDefaultValue(false);
        esParametroBusqueda.setInitialValue(false);
        esParametroBusqueda.setDefaultValue(false);
        esParametroSinRastro.setInitialValue(false);
        esParametroSinRastro.setDefaultValue(false);
        esParametroSegmento.setInitialValue(false);
        esParametroSegmento.setDefaultValue(false);
        esParametroHeredado.setInitialValue(false);
        esParametroHeredado.setDefaultValue(false);
        esParametroVinculado.setInitialValue(false);
        esParametroVinculado.setDefaultValue(false);
        esPassword.setInitialValue(false);
        esPassword.setDefaultValue(false);
        indice.setInitialValue(0);
        indice.setDefaultValue(0);
        tipoValor.setInitialValue(tipoValor.CONTINUO);
        tipoValor.setDefaultValue(tipoValor.CONTINUO);
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of FuncionParametro's properties">
        /**/
        claseJava.setLocalizedLabel(ENGLISH, "data class");
        claseJava.setLocalizedLabel(SPANISH, "clase de dato");
        claseJava.setLocalizedShortLabel(ENGLISH, "class");
        claseJava.setLocalizedShortLabel(SPANISH, "clase");
        /**/
        tipoDatoPar.setLocalizedLabel(ENGLISH, "parameter data type");
        tipoDatoPar.setLocalizedLabel(SPANISH, "tipo de dato de parámetro");
        tipoDatoPar.setLocalizedShortLabel(ENGLISH, "data type");
        tipoDatoPar.setLocalizedShortLabel(SPANISH, "tipo de dato");
        /**/
        idListaValor.setLocalizedLabel(ENGLISH, "value list");
        idListaValor.setLocalizedLabel(SPANISH, "lista de valores");
        /**/
        idClaseObjetoValor.setLocalizedLabel(ENGLISH, "value object class");
        idClaseObjetoValor.setLocalizedLabel(SPANISH, "clase de objeto valor");
        /**/
        valorMinimo.setLocalizedLabel(ENGLISH, "minimum value");
        valorMinimo.setLocalizedLabel(SPANISH, "valor minimo");
        /**/
        valorMaximo.setLocalizedLabel(ENGLISH, "maximum value");
        valorMaximo.setLocalizedLabel(SPANISH, "valor maximo");
        /**/
        valorOmision.setLocalizedLabel(ENGLISH, "default value");
        valorOmision.setLocalizedLabel(SPANISH, "valor por omisión");
        /**/
        criterioBusqueda.setLocalizedLabel(ENGLISH, "search criterion");
        criterioBusqueda.setLocalizedLabel(SPANISH, "criterio de búsqueda");
        criterioBusqueda.setLocalizedShortLabel(ENGLISH, "criterion");
        criterioBusqueda.setLocalizedShortLabel(SPANISH, "criterio");
        /**/
        accesoRestringido.setLocalizedLabel(ENGLISH, "restricted access");
        accesoRestringido.setLocalizedLabel(SPANISH, "acceso restringido");
        accesoRestringido.setLocalizedShortLabel(ENGLISH, "restricted");
        accesoRestringido.setLocalizedShortLabel(SPANISH, "restringido");
        /**/
        esParametroBusqueda.setLocalizedLabel(ENGLISH, "search parameter");
        esParametroBusqueda.setLocalizedLabel(SPANISH, "parámetro de búsqueda");
        esParametroBusqueda.setLocalizedShortLabel(ENGLISH, "search parameter");
        esParametroBusqueda.setLocalizedShortLabel(SPANISH, "parámetro de búsqueda");
        /**/
        esParametroSinRastro.setLocalizedLabel(ENGLISH, "parameter without trail");
        esParametroSinRastro.setLocalizedLabel(SPANISH, "parámetro sin rastro");
        esParametroSinRastro.setLocalizedShortLabel(ENGLISH, "without trail");
        esParametroSinRastro.setLocalizedShortLabel(SPANISH, "sin rastro");
        /**/
        esParametroSegmento.setLocalizedLabel(ENGLISH, "parameter segment");
        esParametroSegmento.setLocalizedLabel(SPANISH, "parámetro segmento");
        esParametroSegmento.setLocalizedShortLabel(ENGLISH, "segment");
        esParametroSegmento.setLocalizedShortLabel(SPANISH, "segmento");
        /**/
        esParametroHeredado.setLocalizedLabel(ENGLISH, "inherited parameter");
        esParametroHeredado.setLocalizedLabel(SPANISH, "parámetro heredado");
        esParametroHeredado.setLocalizedShortLabel(ENGLISH, "inherited");
        esParametroHeredado.setLocalizedShortLabel(SPANISH, "heredado");
        /**/
        esParametroVinculado.setLocalizedLabel(ENGLISH, "linked parameter");
        esParametroVinculado.setLocalizedLabel(SPANISH, "parámetro vinculado");
        esParametroVinculado.setLocalizedShortLabel(ENGLISH, "linked");
        esParametroVinculado.setLocalizedShortLabel(SPANISH, "vinculado");
        /**/
        esPassword.setLocalizedLabel(ENGLISH, "password");
        esPassword.setLocalizedLabel(SPANISH, "contraseña");
        /**/
        indice.setLocalizedLabel(ENGLISH, "index");
        indice.setLocalizedLabel(SPANISH, "índice");
        indice.setLocalizedShortLabel(ENGLISH, "index");
        indice.setLocalizedShortLabel(SPANISH, "índice");
        /**/
        funcion.setLocalizedLabel(ENGLISH, "function");
        funcion.setLocalizedLabel(SPANISH, "función");
        /**/
        parametro.setLocalizedLabel(ENGLISH, "parameter");
        parametro.setLocalizedLabel(SPANISH, "parámetro");
        /**/
        tipoParametro.setLocalizedLabel(ENGLISH, "parameter type");
        tipoParametro.setLocalizedLabel(SPANISH, "tipo de parámetro");
        /**/
        tipoComparacion.setLocalizedLabel(ENGLISH, "comparison type");
        tipoComparacion.setLocalizedLabel(SPANISH, "tipo de comparación");
        /**/
        tipoValor.setLocalizedLabel(ENGLISH, "value type");
        tipoValor.setLocalizedLabel(SPANISH, "tipo de valor");
        /**/
        rangoAgregacion.setLocalizedLabel(ENGLISH, "aggregation operator range");
        rangoAgregacion.setLocalizedLabel(SPANISH, "rango de operadores de agregación");
        /**/
        rangoComparacion.setLocalizedLabel(ENGLISH, "comparison operator range");
        rangoComparacion.setLocalizedLabel(SPANISH, "rango de operadores de comparación");
        /**/
        funcionReferencia.setLocalizedLabel(ENGLISH, "reference function");
        funcionReferencia.setLocalizedLabel(SPANISH, "función de referencia");
        /**/
        claseRecursoValor.setLocalizedLabel(ENGLISH, "referenced resource class");
        claseRecursoValor.setLocalizedLabel(SPANISH, "clase de recurso referenciada");
        claseRecursoValor.setLocalizedShortLabel(ENGLISH, "referenced class");
        claseRecursoValor.setLocalizedShortLabel(SPANISH, "clase referenciada");
        /**/
        // </editor-fold>
    }

    protected Key ix_funcion_parametro_0001, ix_funcion_parametro_0002;

    @Override
    protected void settleKeys() {
        super.settleKeys();
        /**/
        ix_funcion_parametro_0001.setUnique(false);
        ix_funcion_parametro_0001.newKeyField(funcion, parametro);
        /**/
        ix_funcion_parametro_0002.setUnique(false);
        ix_funcion_parametro_0002.newKeyField(funcion, nombreFuncionParametro);
        /**/
    }

    protected Tab tab110, tab120;

    @Override
    protected void settleTabs() {
        super.settleTabs();
        /**/
        tab110.newTabField(aliasFuncionParametro, columnaFuncionParametro, detalleFuncionParametro, descripcionFuncionParametro);
        tab110.newTabField(funcion, parametro, tipoParametro, tipoDatoPar, claseJava, claseRecursoValor, funcionReferencia);
        /**/
        tab120.newTabField(claseJavaFuncionParametro, idListaValor, idClaseObjetoValor, valorMinimo, valorMaximo, valorOmision);
        tab120.newTabField(criterioBusqueda, accesoRestringido, esParametroBusqueda, esParametroSinRastro, esParametroSegmento, esParametroHeredado, esParametroVinculado, esPassword);
        tab120.newTabField(indice, tipoComparacion, tipoValor, rangoAgregacion, rangoComparacion);
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of Funcion's tabs">
        /**/
        tab110.setLocalizedLabel(ENGLISH, "general");
        tab110.setLocalizedLabel(SPANISH, "general");
        /**/
        tab120.setLocalizedLabel(ENGLISH, "details");
        tab120.setLocalizedLabel(SPANISH, "detalles");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleFilters() {
        super.settleFilters();
        /**/
        setMasterDetailFilter(funcion.CRUD);
        /**/
    }

}
