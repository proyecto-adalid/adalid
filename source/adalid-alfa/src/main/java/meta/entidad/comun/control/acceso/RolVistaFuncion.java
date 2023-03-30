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
import meta.entidad.comun.operacion.basica.VistaFuncion;

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
public class RolVistaFuncion extends AbstractPersistentEntity {

    @Override
    protected void addAllocationStrings() {
        super.addAllocationStrings();
        super.addAllocationStrings(
            "rol.grupo",
            "vistaFuncion.funcion",
            "vistaFuncion.propietario"
        );
    }

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public RolVistaFuncion(Artifact declaringArtifact, Field declaringField) {
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
    public VistaFuncion vistaFuncion;

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
        // <editor-fold defaultstate="collapsed" desc="localization of RolVistaFuncion's attributes">
        setLocalizedLabel(ENGLISH, "role/view association");
        setLocalizedLabel(SPANISH, "asociación Rol/Vista");
        setLocalizedCollectionLabel(ENGLISH, "Role/View Associations");
        setLocalizedCollectionLabel(SPANISH, "Asociaciones Rol/Vista");
        /**/
        setLocalizedCollectionLabel(ENGLISH, rol, "Views by Role");
        setLocalizedCollectionLabel(SPANISH, rol, "Vistas por Rol");
        setLocalizedCollectionShortLabel(ENGLISH, rol, "Views");
        setLocalizedCollectionShortLabel(SPANISH, rol, "Vistas");
        /**/
        setLocalizedDescription(ENGLISH, "Each instance of " + b("Role/View Associations") + " represents a "
            + "view associated with an application role."
            + "");
        setLocalizedDescription(SPANISH, "Cada instancia de " + b("Asociaciones Rol/Vista") + " representa una "
            + "vista asociada con un rol de la aplicación."
            + "");
        /**/
        setLocalizedShortDescription(ENGLISH, "view associated with an application role");
        setLocalizedShortDescription(SPANISH, "vista asociada con un rol de la aplicación");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        // <editor-fold defaultstate="collapsed" desc="localization of RolVistaFuncion's properties">
        rol.setLocalizedLabel(ENGLISH, "role");
        rol.setLocalizedLabel(SPANISH, "rol");
        /**/
        vistaFuncion.setLocalizedLabel(ENGLISH, "views");
        vistaFuncion.setLocalizedLabel(SPANISH, "vista");
        /**/
        funcion.setLocalizedLabel(ENGLISH, "function of the view");
        funcion.setLocalizedLabel(SPANISH, "función de la vista");
        funcion.setLocalizedShortLabel(ENGLISH, "function");
        funcion.setLocalizedShortLabel(SPANISH, "función");
        /**/
        propietario.setLocalizedLabel(ENGLISH, "owner of the view");
        propietario.setLocalizedLabel(SPANISH, "propietario de la vista");
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

    protected Key uk_rol_vista_funcion_0001;

    @Override
    protected void settleKeys() {
        super.settleKeys();
        uk_rol_vista_funcion_0001.setUnique(true);
        uk_rol_vista_funcion_0001.newKeyField(rol, vistaFuncion);
    }

    @Override
    protected void settleExpressions() {
        super.settleExpressions();
        funcion.setCalculableValueEntityReference(vistaFuncion.funcion);
        propietario.setCalculableValueEntityReference(vistaFuncion.propietario);
    }

    @Override
    protected void settleFilters() {
        super.settleFilters();
//      vistaFuncion.setSearchQueryFilter(vistaFuncion.publica.isFalse());
    }

}
