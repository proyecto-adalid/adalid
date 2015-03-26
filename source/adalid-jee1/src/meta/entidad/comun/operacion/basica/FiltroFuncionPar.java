/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.entidad.comun.operacion.basica;

import adalid.core.*;
import adalid.core.annotations.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import adalid.core.properties.*;
import java.lang.reflect.Field;
import meta.entidad.comun.configuracion.basica.FuncionParametro;
import meta.proyecto.base.ProyectoBase;

/**
 * @author Jorge Campins
 */
@EntityClass(independent = Kleenean.FALSE, resourceType = ResourceType.OPERATION, resourceGender = ResourceGender.MASCULINE)
@EntitySelectOperation(enabled = Kleenean.TRUE)
@EntityInsertOperation(enabled = Kleenean.TRUE)
@EntityUpdateOperation(enabled = Kleenean.TRUE)
@EntityDeleteOperation(enabled = Kleenean.TRUE)
@EntityTableView(enabled = Kleenean.FALSE, heading = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.FALSE, heading = Kleenean.FALSE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
@PersistentEntityClass(dataProvider = 3)
@EntityTriggers(afterValue = Kleenean.TRUE)
public class FiltroFuncionPar extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    @Deprecated
    private FiltroFuncionPar() {
        this(null, null);
    }

    public FiltroFuncionPar(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public LongProperty idFiltroFuncionPar;

    @VersionProperty
    public LongProperty versionFiltroFuncionPar;

    @ForeignKey(onDelete = OnDeleteAction.CASCADE, onUpdate = OnUpdateAction.CASCADE)
    @ManyToOne(navigability = Navigability.BIDIRECTIONAL, view = MasterDetailView.TABLE_AND_DETAIL)
    @ColumnField(nullable = Kleenean.FALSE)
    @Allocation(maxDepth = 2, maxRound = 0)
    public FiltroFuncion idFiltroFuncion;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.FALSE)
    @EntityReferenceSearch(searchType = SearchType.LIST, listStyle = ListStyle.NAME)
    @PropertyField(required = Kleenean.TRUE, table = Kleenean.TRUE, detail = Kleenean.TRUE, report = Kleenean.TRUE, export = Kleenean.TRUE, submit = Kleenean.TRUE)
    @Allocation(maxDepth = 2, maxRound = 0)
    public FuncionParametro idFuncionParametro;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(required = Kleenean.TRUE, table = Kleenean.TRUE, detail = Kleenean.TRUE, report = Kleenean.TRUE, export = Kleenean.TRUE, submit = Kleenean.TRUE)
    @Allocation(maxDepth = 1, maxRound = 0)
    public OperadorCom numeroOperadorCom;

    @PropertyField(table = Kleenean.TRUE, detail = Kleenean.TRUE, create = Kleenean.TRUE, update = Kleenean.TRUE)
    @StringField(maxLength = 50)
    public StringProperty valor;

    @PropertyField(hidden = Kleenean.TRUE)
    public TimestampProperty valorFechaHora;

    @PropertyField(hidden = Kleenean.TRUE)
    public LongProperty idClaseRecursoValor;

    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(table = Kleenean.TRUE, detail = Kleenean.TRUE, create = Kleenean.TRUE, update = Kleenean.TRUE, search = Kleenean.FALSE, heading = Kleenean.FALSE)
    @Allocation(maxDepth = 1, maxRound = 0)
    public RecursoValor recursoValor;

    @PropertyField(hidden = Kleenean.TRUE)
    public LongProperty idRecursoValor;

    @PropertyField(table = Kleenean.FALSE, detail = Kleenean.FALSE, search = Kleenean.TRUE)
    public StringProperty codigoRecursoValor;

    @PropertyField(table = Kleenean.FALSE, detail = Kleenean.FALSE, search = Kleenean.TRUE)
    public StringProperty nombreRecursoValor;

    @PropertyField(hidden = Kleenean.TRUE)
    @StringField(maxLength = 100)
    public StringProperty paginaRecurso;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        setDefaultLabel("criterio de filtro de búsqueda");
        setDefaultShortLabel("criterio");
        setDefaultCollectionLabel("criterios de filtro de búsqueda");
        setDefaultCollectionShortLabel("criterios");
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        numeroOperadorCom.setInitialValue(numeroOperadorCom.ES_IGUAL);
        numeroOperadorCom.setDefaultValue(numeroOperadorCom.ES_IGUAL);
        valor.setDefaultLabel("valor");
        recursoValor.setDefaultLabel("código");
        recursoValor.setDefaultTooltip("código");
        recursoValor.codigo.setDefaultLabel("código");
        recursoValor.codigo.setDefaultTooltip("código");
        recursoValor.nombre.setDefaultLabel("nombre");
        recursoValor.nombre.setDefaultTooltip("nombre");
        codigoRecursoValor.setDefaultLabel("código");
        codigoRecursoValor.setDefaultTooltip("código");
        nombreRecursoValor.setDefaultLabel("nombre");
        nombreRecursoValor.setDefaultTooltip("nombre");
    }

    @Override
    protected void settleLinks() {
        super.settleLinks();
        linkForeignOwnerProperty(idFiltroFuncion.idUsuario);
    }

    @Override
    protected void settleFilters() {
        super.settleFilters();
        idFuncionParametro.setSearchQueryFilter(idFuncionParametro.idFuncion.isEqualTo(idFiltroFuncion.idFuncion).
            and(idFuncionParametro.criterioBusqueda.isTrue()));
    }

}
