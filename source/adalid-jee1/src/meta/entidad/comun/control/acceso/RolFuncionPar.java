/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.entidad.comun.control.acceso;

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
        setDefaultCollectionLabel("asociaciones Rol/Función/Parámetro");
        setDefaultCollectionLabel(idRolFuncion, "parámetros de funciones por rol");
        setDefaultCollectionShortLabel(idRolFuncion, "parámetros");
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
