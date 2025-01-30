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
import adalid.core.properties.ext.*;
import java.lang.reflect.Field;
import meta.entidad.comun.configuracion.basica.Aplicacion;

/**
 * @author Jorge Campins
 */
@EntityClass(independent = Kleenean.FALSE, resourceType = ResourceType.OPERATION, resourceGender = ResourceGender.FEMININE)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE)
@EntityInsertOperation(enabled = Kleenean.TRUE)
@EntityUpdateOperation(enabled = Kleenean.TRUE)
@EntityDeleteOperation(enabled = Kleenean.TRUE)
@EntityTableView(enabled = Kleenean.TRUE, responsiveMode = TableResponsiveMode.PRIORITY)
@EntityDetailView(enabled = Kleenean.FALSE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
public class RolAplicacion extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public RolAplicacion(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Override
    protected void addAllocationStrings() {
        super.addAllocationStrings();
        super.addAllocationStrings(
            "rol.grupo"
        );
    }

    // <editor-fold defaultstate="collapsed" desc="MultiWebApplication">
    @Override
    public boolean isSelectEnabled() {
        return super.isSelectEnabled() && isRoleWebAppDissociationAllowed();
    }

    @Override
    public boolean isInsertEnabled() {
        return super.isInsertEnabled() && isRoleWebAppDissociationAllowed();
    }

    @Override
    public boolean isUpdateEnabled() {
        return super.isUpdateEnabled() && isRoleWebAppDissociationAllowed();
    }

    @Override
    public boolean isDeleteEnabled() {
        return super.isDeleteEnabled() && isRoleWebAppDissociationAllowed();
    }
    // </editor-fold>

    @PrimaryKey
    public LongProperty id;

    @VersionProperty
    public LongProperty version;

    @NameProperty
    @ColumnField(calculable = Kleenean.TRUE)
    public CloakedStringProperty nombre;

    @ForeignKey(onDelete = OnDeleteAction.CASCADE, onUpdate = OnUpdateAction.CASCADE)
    @ManyToOne(navigability = Navigability.BIDIRECTIONAL, view = MasterDetailView.TABLE, viewSequence = 30)
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(required = Kleenean.TRUE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public Rol rol;

//  20171213: remove foreign-key referring to Aplicacion
//  @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE, quickAdding = QuickAddingFilter.MISSING)
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(required = Kleenean.TRUE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public Aplicacion aplicacion;

    @ColumnField(nullable = Kleenean.FALSE)
    @BooleanField(displayType = BooleanDisplayType.TOGGLE)
    @PropertyField(create = Kleenean.TRUE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public BooleanProperty simple;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of RolPagina's attributes">
        /**/
        setLocalizedLabel(ENGLISH, "role/application dissociation");
        setLocalizedLabel(SPANISH, "disociación Rol/Aplicación");
        setLocalizedCollectionLabel(ENGLISH, "Role/Application Dissociations");
        setLocalizedCollectionLabel(SPANISH, "Disociaciones Rol/Aplicación");
        /**/
        setLocalizedCollectionLabel(ENGLISH, rol, "Dissociations by Role");
        setLocalizedCollectionLabel(SPANISH, rol, "Disociaciones por Rol");
        setLocalizedCollectionShortLabel(ENGLISH, rol, "Dissociations");
        setLocalizedCollectionShortLabel(SPANISH, rol, "Disociaciones");
        /**/
        setLocalizedDescription(ENGLISH, "By default, all roles are associated with all applications. "
            + "Instances of " + b("Role/Application Dissociations") + " are used to remove default associations, as follows: "
            + "a " + b("simple") + " dissociation removes the association between the role and the application; "
            + "a multiple (non-simple) dissociation removes the associations between the role and all applications "
            + "that do not have a multiple (non-simple) dissociation with the role. "
            + "");
        setLocalizedDescription(SPANISH, "De manera predeterminada, todos los roles están asociados con todas las aplicaciones. "
            + "Las instancias de " + b("Disociaciones Rol/Aplicación") + " sirven para eliminar asociaciones predefinidas, de la siguiente manera: "
            + "una disociación " + b("simple") + " elimina la asociación entre el rol y la aplicación; "
            + "una disociación múltiple (no simple) elimina las asociaciones entre el rol y todas las aplicaciones "
            + "que no tengan una disociación múltiple (no simple) con el rol. "
            + "");
        /*
        setLocalizedShortDescription(ENGLISH, "application associated with a role");
        setLocalizedShortDescription(SPANISH, "aplicación asociada con un rol");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        /**/
        nombre.setCalculableValueExpression(concatenate(aplicacion.codigoAplicacion, SLASH, rol.codigoRol));
        /**/
        simple.setDefaultValue(false);
        simple.setInitialValue(false);
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of RolPagina's properties">
        /**/
        rol.setLocalizedLabel(ENGLISH, "role");
        rol.setLocalizedLabel(SPANISH, "rol");
        /**/
        aplicacion.setLocalizedLabel(ENGLISH, "application");
        aplicacion.setLocalizedLabel(SPANISH, "aplicación");
        /**/
        simple.setLocalizedLabel(ENGLISH, "simple");
        simple.setLocalizedLabel(SPANISH, "simple");
        simple.setLocalizedDescription(ENGLISH, ""
            + "a simple dissociation removes the association between the role and the application; "
            + "a multiple (non-simple) dissociation removes the associations between the role and all applications "
            + "that do not have a multiple (non-simple) dissociation with the role. "
            + "");
        simple.setLocalizedDescription(SPANISH, ""
            + "una disociación simple elimina la asociación entre el rol y la aplicación; "
            + "una disociación múltiple (no simple) elimina las asociaciones entre el rol y todas las aplicaciones "
            + "que no tengan una disociación múltiple (no simple) con el rol. "
            + "");
        simple.setLocalizedTooltip(ENGLISH, simple.getLocalizedTooltip(ENGLISH));
        simple.setLocalizedTooltip(SPANISH, simple.getLocalizedTooltip(SPANISH));
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleLinks() {
        super.settleLinks();
        /**/
        linkForeignSegmentProperty(rol.grupo);
        /**/
    }

    protected Key uk_rol_aplicacion_0001;

    @Override
    protected void settleKeys() {
        super.settleKeys();
        /**/
        uk_rol_aplicacion_0001.setUnique(true);
        uk_rol_aplicacion_0001.newKeyField(rol, aplicacion);
        /**/
    }

    protected Segment rolOrdinario;

    @Override
    protected void settleExpressions() {
        super.settleExpressions();
        /**/
        rolOrdinario = rol.esRolEspecial.isFalse();
        rolOrdinario.setLocalizedDescription(ENGLISH, "the role is not a special role");
        rolOrdinario.setLocalizedDescription(SPANISH, "el rol no es un rol especial");
        rolOrdinario.setLocalizedErrorMessage(ENGLISH, "the role is a special role");
        rolOrdinario.setLocalizedErrorMessage(SPANISH, "el rol es un rol especial");
        /**/
    }

    @Override
    protected void settleFilters() {
        super.settleFilters();
        /**/
        setMasterDetailFilter(rol.rolesOrdinarios);
        setInsertFilter(rol.rolesOrdinarios);
        setUpdateFilter(rolOrdinario);
        setDeleteFilter(rolOrdinario);
        /**/
    }

}
