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
public class RolUsuario extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public RolUsuario(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Override
    protected void addAllocationStrings() {
        super.addAllocationStrings();
        super.addAllocationStrings("rol.grupo");
    }

    @PrimaryKey
    public LongProperty id;

    @VersionProperty
    public LongProperty version;

    @NameProperty
    @ColumnField(calculable = Kleenean.TRUE)
    public CloakedStringProperty nombre;

    @ForeignKey(onDelete = OnDeleteAction.CASCADE, onUpdate = OnUpdateAction.CASCADE)
    @ManyToOne(navigability = Navigability.BIDIRECTIONAL, view = MasterDetailView.TABLE, quickAdding = QuickAddingFilter.MISSING, viewSequence = 10)
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(required = Kleenean.TRUE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public Rol rol;

    @ForeignKey(onDelete = OnDeleteAction.CASCADE, onUpdate = OnUpdateAction.CASCADE)
    @ManyToOne(navigability = Navigability.BIDIRECTIONAL, view = MasterDetailView.TABLE, quickAdding = QuickAddingFilter.MISSING)
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(required = Kleenean.TRUE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public Usuario usuario;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of RolUsuario's attributes">
        setLocalizedLabel(ENGLISH, "role/user association");
        setLocalizedLabel(SPANISH, "asociación Rol/Usuario");
        setLocalizedCollectionLabel(ENGLISH, "Role/User Associations");
        setLocalizedCollectionLabel(SPANISH, "Asociaciones Rol/Usuario");
        /**/
        setLocalizedCollectionLabel(ENGLISH, rol, "Users by Role");
        setLocalizedCollectionLabel(SPANISH, rol, "Usuarios por Rol");
        setLocalizedCollectionShortLabel(ENGLISH, rol, "Users");
        setLocalizedCollectionShortLabel(SPANISH, rol, "Usuarios");
        /**/
        setLocalizedCollectionLabel(ENGLISH, usuario, "Roles by User");
        setLocalizedCollectionLabel(SPANISH, usuario, "Roles por Usuario");
        setLocalizedCollectionShortLabel(ENGLISH, usuario, "Roles");
        setLocalizedCollectionShortLabel(SPANISH, usuario, "Roles");
        /**/
        setLocalizedDescription(ENGLISH, "Each instance of " + b("Role/User Associations") + " represents a "
            + "user associated with an application role."
            + "");
        setLocalizedDescription(SPANISH, "Cada instancia de " + b("Asociaciones Rol/Usuario") + " representa un "
            + "usuario asociado con un rol de la aplicación."
            + "");
        /**/
        setLocalizedShortDescription(ENGLISH, "user associated with an application role");
        setLocalizedShortDescription(SPANISH, "usuario asociado con un rol de la aplicación");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        /**/
        nombre.setCalculableValueExpression(concatenate(usuario.codigoUsuario, SLASH, rol.codigoRol));
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of RolUsuario's properties">
        rol.setLocalizedLabel(ENGLISH, "role");
        rol.setLocalizedLabel(SPANISH, "rol");
        /**/
        usuario.setLocalizedLabel(ENGLISH, "user");
        usuario.setLocalizedLabel(SPANISH, "usuario");
        // </editor-fold>
    }

    @Override
    protected void settleLinks() {
        super.settleLinks();
        linkForeignSegmentProperty(rol.grupo);
    }

    protected Key uk_rol_usuario_0001;

    @Override
    protected void settleKeys() {
        super.settleKeys();
        uk_rol_usuario_0001.setUnique(true);
        uk_rol_usuario_0001.newKeyField(rol, usuario);
    }

    protected Check checkUsuarioOrdinario;

    @Override
    protected void settleExpressions() {
        super.settleExpressions();
        /**/
        checkUsuarioOrdinario = usuario.esUsuarioEspecial.isFalse();
        /**/
        checkUsuarioOrdinario.setLocalizedDescription(ENGLISH, "the user is not a special user");
        checkUsuarioOrdinario.setLocalizedDescription(SPANISH, "el usuario no es un usuario especial");
        checkUsuarioOrdinario.setLocalizedErrorMessage(ENGLISH, "the user is a special user");
        checkUsuarioOrdinario.setLocalizedErrorMessage(SPANISH, "el usuario es un usuario especial");
        /**/
    }

    @Override
    protected void settleFilters() {
        super.settleFilters();
        /**/
        setInsertFilter(usuario.usuariosOrdinarios);
        setMasterDetailFilter(usuario.usuariosOrdinarios);
        /*
////    rol.setSearchQueryFilter(rol.tipoRol.isNotNull().and(rol.tipoRol.isEqualTo(rol.tipoRol.OPERADOR)).implies(rol.esRolEspecial));
//      rol.setSearchQueryFilter(rol.tipoRol.isEqualTo(rol.tipoRol.OPERADOR).implies(rol.esRolEspecial));
        /**/
        usuario.setSearchQueryFilter(checkUsuarioOrdinario);
    }

    protected Agregar agregar;

    @OperationClass(access = OperationAccess.PRIVATE)
    @ProcessOperationClass(serviceable = Kleenean.FALSE)
    public class Agregar extends ProcessOperation {

        @ParameterField(required = Kleenean.TRUE)
        protected Rol rol;

        @ParameterField(required = Kleenean.TRUE)
        protected Usuario usuario;

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of Agregar's attributes">
            /**/
            setLocalizedLabel(ENGLISH, "add");
            setLocalizedLabel(SPANISH, "agregar");
            /**/
            setLocalizedSuccessMessage(ENGLISH, "a role/user association was successfully added");
            setLocalizedSuccessMessage(SPANISH, "una asociación rol/usuario se agregó con éxito");
            /**/
            // </editor-fold>
        }

        @Override
        protected void settleParameters() {
            super.settleParameters();
            // <editor-fold defaultstate="collapsed" desc="localization of Agregar's parameters">
            rol.setLocalizedLabel(ENGLISH, "role");
            rol.setLocalizedLabel(SPANISH, "rol");
            /**/
            usuario.setLocalizedLabel(ENGLISH, "user");
            usuario.setLocalizedLabel(SPANISH, "usuario");
            // </editor-fold>
        }

    }

}
