/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.entidad.comun.control.acceso;

import adalid.core.AbstractPersistentEntity;
import adalid.core.Key;
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
import adalid.core.annotations.EntityUpdateOperation;
import adalid.core.annotations.ForeignKey;
import adalid.core.annotations.ManyToOne;
import adalid.core.annotations.PrimaryKey;
import adalid.core.annotations.PropertyField;
import adalid.core.annotations.VersionProperty;
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
import adalid.core.interfaces.Segment;
import adalid.core.properties.LongProperty;
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
@EntityTableView(enabled = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.FALSE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
public class RolFuncionPar extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    @Deprecated
    private RolFuncionPar() {
        this(null, null);
    }

    public RolFuncionPar(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public LongProperty idRolFuncionPar;

    @VersionProperty
    public LongProperty versionRolFuncionPar;

    @ForeignKey(onDelete = OnDeleteAction.CASCADE, onUpdate = OnUpdateAction.CASCADE)
    @ManyToOne(navigability = Navigability.BIDIRECTIONAL, view = MasterDetailView.TABLE)
    @ColumnField(nullable = Kleenean.FALSE)
    @Allocation(maxDepth = 2, maxRound = 0)
    public RolFuncion idRolFuncion;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.FALSE)
    @EntityReferenceSearch(searchType = SearchType.LIST, listStyle = ListStyle.NAME)
    @PropertyField(required = Kleenean.TRUE, table = Kleenean.TRUE, detail = Kleenean.TRUE, report = Kleenean.TRUE, export = Kleenean.TRUE)
    @Allocation(maxDepth = 2, maxRound = 0)
    public FuncionParametro idFuncionParametro;

    protected Key uk_rol_funcion_par_0001;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        setDefaultLabel("asociación Rol/Función/Parámetro");
        setDefaultCollectionLabel("Asociaciones Rol/Función/Parámetro");
        setDefaultCollectionLabel(idRolFuncion, "Parámetros de Funciones por Rol");
        setDefaultCollectionShortLabel(idRolFuncion, "Parámetros");
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        idFuncionParametro.setSearchQueryFilter(
            idFuncionParametro.idFuncion.isEqualTo(idRolFuncion.idFuncion).and(
                idFuncionParametro.accesoRestringido.isTrue()));
    }

    @Override
    protected void settleKeys() {
        super.settleKeys();
        uk_rol_funcion_par_0001.setUnique(true);
        uk_rol_funcion_par_0001.newKeyField(idRolFuncion, idFuncionParametro);
    }

    public Segment modificables;

    @Override
    protected void settleExpressions() {
        super.settleExpressions();
        modificables = idRolFuncion.idRol.idRol.isGreaterOrEqualTo(10000L);
        modificables.setDefaultErrorMessage("el rol es un rol de configuración básica del sistema; "
            + "no se permite modificar ni eliminar parámetros de sus funciones");
    }

    @Override
    protected void settleFilters() {
        super.settleFilters();
        setInsertFilter(idRolFuncion.modificables);
        setUpdateFilter(modificables);
        setDeleteFilter(modificables);
        idFuncionParametro.setSearchQueryFilter(idFuncionParametro.idFuncion.isEqualTo(idRolFuncion.idFuncion).
            and(idFuncionParametro.accesoRestringido.isTrue()));
    }

}
