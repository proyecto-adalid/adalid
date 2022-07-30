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
package meta.entidad.comun.control.acceso;

import adalid.core.*;
import adalid.core.annotations.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import adalid.core.properties.*;
import java.lang.reflect.Field;
import meta.entidad.comun.configuracion.basica.Funcion;
import meta.entidad.comun.operacion.basica.FiltroFuncion;

/**
 * @author Jorge Campins
 */
@EntityClass(independent = Kleenean.FALSE, resourceType = ResourceType.OPERATION, resourceGender = ResourceGender.FEMININE)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE)
@EntityInsertOperation(enabled = Kleenean.TRUE)
@EntityUpdateOperation(enabled = Kleenean.TRUE)
@EntityDeleteOperation(enabled = Kleenean.TRUE)
@EntityTableView(enabled = Kleenean.TRUE)
@EntityDetailView(enabled = Kleenean.FALSE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
public class RolFiltroFuncion extends AbstractPersistentEntity {

    @Override
    protected void addAllocationStrings() {
        super.addAllocationStrings();
        super.addAllocationStrings(
            "rol.grupo",
            "filtroFuncion.funcion",
            "filtroFuncion.usuario"
        );
    }

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public RolFiltroFuncion(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public LongProperty id;

    @VersionProperty
    public LongProperty version;

    @ForeignKey(onDelete = OnDeleteAction.CASCADE, onUpdate = OnUpdateAction.CASCADE)
    @ManyToOne(navigability = Navigability.BIDIRECTIONAL, view = MasterDetailView.TABLE)
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(required = Kleenean.TRUE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public Rol rol;

    @ForeignKey(onDelete = OnDeleteAction.CASCADE, onUpdate = OnUpdateAction.CASCADE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE, quickAdding = QuickAddingFilter.MISSING)
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(required = Kleenean.TRUE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public FiltroFuncion filtroFuncion;

    @ColumnField(calculable = Kleenean.TRUE)
    @ManyToOne(view = MasterDetailView.NONE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE)
    public Funcion funcion;

    @ColumnField(calculable = Kleenean.TRUE)
    @ManyToOne(view = MasterDetailView.NONE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE)
    public Usuario propietario;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of RolFiltroFuncion's attributes">
        setLocalizedLabel(ENGLISH, "role/filter association");
        setLocalizedLabel(SPANISH, "asociación Rol/Filtro");
        setLocalizedCollectionLabel(ENGLISH, "Role/Filter Associations");
        setLocalizedCollectionLabel(SPANISH, "Asociaciones Rol/Filtro");
        /**/
        setLocalizedCollectionLabel(ENGLISH, rol, "Filters by Role");
        setLocalizedCollectionLabel(SPANISH, rol, "Filtros por Rol");
        setLocalizedCollectionShortLabel(ENGLISH, rol, "Filters");
        setLocalizedCollectionShortLabel(SPANISH, rol, "Filtros");
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        // <editor-fold defaultstate="collapsed" desc="localization of RolFiltroFuncion's properties">
        rol.setLocalizedLabel(ENGLISH, "role");
        rol.setLocalizedLabel(SPANISH, "rol");
        /**/
        filtroFuncion.setLocalizedLabel(ENGLISH, "query filter");
        filtroFuncion.setLocalizedLabel(SPANISH, "filtro de búsqueda");
        filtroFuncion.setLocalizedShortLabel(ENGLISH, "filter");
        filtroFuncion.setLocalizedShortLabel(SPANISH, "filtro");
        /**/
        funcion.setLocalizedLabel(ENGLISH, "function of the filter");
        funcion.setLocalizedLabel(SPANISH, "función del filtro");
        funcion.setLocalizedShortLabel(ENGLISH, "function");
        funcion.setLocalizedShortLabel(SPANISH, "función");
        /**/
        propietario.setLocalizedLabel(ENGLISH, "owner of the filter");
        propietario.setLocalizedLabel(SPANISH, "propietario del filtro");
        propietario.setLocalizedShortLabel(ENGLISH, "owner");
        propietario.setLocalizedShortLabel(SPANISH, "propietario");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleLinks() {
        super.settleLinks();
        linkForeignSegmentProperty(rol.grupo);
    }

    protected Key uk_rol_filtro_funcion_0001;

    @Override
    protected void settleKeys() {
        super.settleKeys();
        uk_rol_filtro_funcion_0001.setUnique(true);
        uk_rol_filtro_funcion_0001.newKeyField(rol, filtroFuncion);
    }

    @Override
    protected void settleExpressions() {
        super.settleExpressions();
        funcion.setCalculableValueEntityReference(filtroFuncion.funcion);
        propietario.setCalculableValueEntityReference(filtroFuncion.usuario);
    }

    @Override
    protected void settleFilters() {
        super.settleFilters();
//      filtroFuncion.setSearchQueryFilter(filtroFuncion.esPublico.isFalse());
    }

}
