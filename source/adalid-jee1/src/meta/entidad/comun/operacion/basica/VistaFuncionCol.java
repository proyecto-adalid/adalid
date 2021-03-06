/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.entidad.comun.operacion.basica;

import adalid.core.AbstractPersistentEntity;
import adalid.core.annotations.Allocation;
import adalid.core.annotations.ColumnField;
import adalid.core.annotations.EntityClass;
import adalid.core.annotations.EntityConsoleView;
import adalid.core.annotations.EntityDeleteOperation;
import adalid.core.annotations.EntityDetailView;
import adalid.core.annotations.EntityInsertOperation;
import adalid.core.annotations.EntityReferenceSearch;
import adalid.core.annotations.EntitySelectOperation;
import adalid.core.annotations.EntityTableView;
import adalid.core.annotations.EntityTreeView;
import adalid.core.annotations.EntityTriggers;
import adalid.core.annotations.EntityUpdateOperation;
import adalid.core.annotations.EntityWarnings;
import adalid.core.annotations.ForeignKey;
import adalid.core.annotations.ManyToOne;
import adalid.core.annotations.NameProperty;
import adalid.core.annotations.PrimaryKey;
import adalid.core.annotations.PropertyField;
import adalid.core.annotations.StringField;
import adalid.core.annotations.VersionProperty;
import adalid.core.enums.DefaultCondition;
import adalid.core.enums.Kleenean;
import adalid.core.enums.ListStyle;
import adalid.core.enums.MasterDetailView;
import adalid.core.enums.Navigability;
import adalid.core.enums.OnDeleteAction;
import adalid.core.enums.OnUpdateAction;
import adalid.core.enums.ResourceGender;
import adalid.core.enums.ResourceType;
import adalid.core.enums.SearchType;
import adalid.core.interfaces.Artifact;
import adalid.core.properties.BooleanProperty;
import adalid.core.properties.IntegerProperty;
import adalid.core.properties.LongProperty;
import adalid.core.properties.StringProperty;
import java.lang.reflect.Field;
import meta.entidad.comun.configuracion.basica.FuncionParametro;
import meta.proyecto.base.ProyectoBase;

/**
 * @author Jorge Campins
 */
@EntityClass(independent = Kleenean.FALSE, resourceType = ResourceType.OPERATION, resourceGender = ResourceGender.FEMININE)
@EntitySelectOperation(enabled = Kleenean.FALSE)
@EntityInsertOperation(enabled = Kleenean.TRUE)
@EntityUpdateOperation(enabled = Kleenean.TRUE)
@EntityDeleteOperation(enabled = Kleenean.TRUE)
@EntityTableView(enabled = Kleenean.FALSE, heading = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.FALSE, heading = Kleenean.FALSE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
@EntityTriggers(afterValue = Kleenean.TRUE)
@EntityWarnings(enabled = Kleenean.FALSE)
public class VistaFuncionCol extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    @Deprecated
    private VistaFuncionCol() {
        this(null, null);
    }

    public VistaFuncionCol(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public LongProperty id;

    @VersionProperty
    public LongProperty version;

    @NameProperty
    @PropertyField(hidden = Kleenean.TRUE, defaultCondition = DefaultCondition.UNCONDITIONALLY)
    @StringField(maxLength = 200)
    public StringProperty nombre;

    @ForeignKey(onDelete = OnDeleteAction.CASCADE, onUpdate = OnUpdateAction.CASCADE)
    @ManyToOne(navigability = Navigability.BIDIRECTIONAL, view = MasterDetailView.TABLE_AND_DETAIL)
    @ColumnField(nullable = Kleenean.FALSE)
    @Allocation(maxDepth = 2, maxRound = 0)
    public VistaFuncion vista;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.FALSE)
    @EntityReferenceSearch(searchType = SearchType.LIST, listStyle = ListStyle.NAME)
    @PropertyField(required = Kleenean.TRUE, table = Kleenean.TRUE, create = Kleenean.TRUE, update = Kleenean.FALSE)
    @Allocation(maxDepth = 2, maxRound = 0)
    public FuncionParametro columna;

    /**
     * string property field
     */
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.TRUE, defaultCondition = DefaultCondition.UNCONDITIONALLY)
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
    @ColumnField(nullable = Kleenean.TRUE)
    @PropertyField(required = Kleenean.FALSE, table = Kleenean.TRUE, create = Kleenean.TRUE)
    public IntegerProperty secuencia;

    /**
     * many-to-one entity reference property field
     */
    @Allocation(maxDepth = 1, maxRound = 0)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(required = Kleenean.FALSE, table = Kleenean.TRUE, create = Kleenean.TRUE)
    public TipoAgregacion agregacion;

    /**
     * many-to-one entity reference property field
     */
    @Allocation(maxDepth = 2, maxRound = 1)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
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
    @PropertyField(required = Kleenean.FALSE, table = Kleenean.TRUE, create = Kleenean.TRUE)
    public IntegerProperty pixeles;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        setDefaultLabel("columna de vista");
        setDefaultShortLabel("columna");
        setDefaultCollectionLabel("Columnas de Vista");
        setDefaultCollectionShortLabel("Columnas");
        setOrderBy(vista, secuencia, id);
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        nombre.setDefaultValue(columna.nombreFuncionParametro);
        alias.setDefaultValue(columna.codigoFuncionParametro);
        secuencia.setMinValue(0);
        secuencia.setMaxValue(1000000000);
        orden.setInitialValue(false);
        orden.setDefaultValue(false);
        visible.setInitialValue(true);
        visible.setDefaultValue(true);
        graficable.setInitialValue(true);
        graficable.setDefaultValue(true);
        pixeles.setDefaultValue(columna.idParametro.pixeles);
        pixeles.setMinValue(0);
        pixeles.setMaxValue(960);
    }

    @Override
    protected void settleLinks() {
        super.settleLinks();
        linkForeignOwnerProperty(vista.propietario);
    }

    @Override
    protected void settleFilters() {
        super.settleFilters();
        columna.setSearchQueryFilter(columna.idFuncion.isEqualTo(vista.funcion).
            and(columna.idParametro.pixeles.isGreaterThan(0)));
        grupo.setSearchQueryFilter(grupo.agregacion.isEqualTo(agregacion.GRUPO).
            and(grupo.vista.isEqualTo(vista)));
    }

}
