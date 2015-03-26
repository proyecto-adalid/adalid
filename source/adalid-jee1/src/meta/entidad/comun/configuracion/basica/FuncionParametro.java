/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.entidad.comun.configuracion.basica;

import adalid.core.*;
import adalid.core.annotations.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import adalid.core.properties.*;
import java.lang.reflect.Field;
import meta.proyecto.base.ProyectoBase;

/**
 * @author Jorge Campins
 */
@EntityClass(independent = Kleenean.FALSE, resourceType = ResourceType.CONFIGURATION, resourceGender = ResourceGender.MASCULINE)
@EntitySelectOperation(enabled = Kleenean.TRUE, access = OperationAccess.PUBLIC, rowsLimit = 0)
@EntityInsertOperation(enabled = Kleenean.FALSE)
@EntityUpdateOperation(enabled = Kleenean.FALSE)
@EntityDeleteOperation(enabled = Kleenean.FALSE)
@EntityTableView(enabled = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.FALSE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
public class FuncionParametro extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    @Deprecated
    private FuncionParametro() {
        this(null, null);
    }

    public FuncionParametro(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public LongProperty idFuncionParametro;

    @VersionProperty
    public LongProperty versionFuncionParametro;

    @BusinessKey
    @StringField(maxLength = 200)
    public StringProperty codigoFuncionParametro;

    @NameProperty
    @StringField(maxLength = 200)
    public StringProperty nombreFuncionParametro;

    @StringField(maxLength = 200)
    public StringProperty detalleFuncionParametro;

    @DescriptionProperty
    public StringProperty descripcionFuncionParametro;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.FALSE)
    @Allocation(maxDepth = 1, maxRound = 0)
    public TipoDatoPar numeroTipoDatoPar;

    public LongProperty idListaValor;

    public LongProperty idClaseObjetoValor;

    @StringField(maxLength = 100)
    public StringProperty valorMinimo;

    @StringField(maxLength = 100)
    public StringProperty valorMaximo;

    @StringField(maxLength = 100)
    public StringProperty valorOmision;

    @ColumnField(nullable = Kleenean.FALSE)
    public BooleanProperty criterioBusqueda;

    @ColumnField(nullable = Kleenean.FALSE)
    public BooleanProperty accesoRestringido;

    @ColumnField(nullable = Kleenean.FALSE)
    public BooleanProperty esParametroSinRastro;

    @ColumnField(nullable = Kleenean.FALSE)
    public BooleanProperty esParametroSegmento;

    @ForeignKey(onDelete = OnDeleteAction.CASCADE, onUpdate = OnUpdateAction.CASCADE)
    @ManyToOne(navigability = Navigability.BIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.FALSE)
    @Allocation(maxDepth = 1, maxRound = 0)
    public Funcion idFuncion;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.CASCADE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.FALSE)
    @Allocation(maxDepth = 1, maxRound = 0)
    public Parametro idParametro;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.FALSE)
    @Allocation(maxDepth = 1, maxRound = 0)
    public TipoParametro numeroTipoParametro;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @Allocation(maxDepth = 1, maxRound = 0)
    public TipoComparacion numeroTipoComparacion;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @Allocation(maxDepth = 1, maxRound = 0)
    public Funcion idFuncionReferencia;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.FALSE)
    @Allocation(maxDepth = 1, maxRound = 0)
    public TipoValor numeroTipoValor;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @Allocation(maxDepth = 1, maxRound = 0)
    public ClaseRecurso idClaseRecursoValor;

    protected Key uk_funcion_parametro_0001;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        setDefaultLabel("parámetro de función");
        setDefaultShortLabel("parámetro");
        setDefaultCollectionLabel("parámetros de función");
        setDefaultCollectionShortLabel("parámetros");
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        idListaValor.setDefaultLabel("lista de valores");
        idClaseObjetoValor.setDefaultLabel("clase de objeto valor");
        accesoRestringido.setInitialValue(false);
        accesoRestringido.setDefaultValue(false);
        esParametroSinRastro.setInitialValue(false);
        esParametroSinRastro.setDefaultValue(false);
        esParametroSegmento.setInitialValue(false);
        esParametroSegmento.setDefaultValue(false);
        idFuncionReferencia.setDefaultLabel("función de referencia");
        idFuncionReferencia.setDefaultShortLabel("función de referencia");
        numeroTipoValor.setInitialValue(numeroTipoValor.CONTINUO);
        numeroTipoValor.setDefaultValue(numeroTipoValor.CONTINUO);
        idClaseRecursoValor.setDefaultLabel("clase de recurso");
        idClaseRecursoValor.setDefaultShortLabel("clase");
    }

    @Override
    protected void settleKeys() {
        super.settleKeys();
        uk_funcion_parametro_0001.setUnique(false);
        uk_funcion_parametro_0001.newKeyField(idFuncion, idParametro);
    }

}
