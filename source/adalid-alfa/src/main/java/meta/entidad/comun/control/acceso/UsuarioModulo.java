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
import meta.entidad.comun.configuracion.basica.ModuloAplicacion;

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
public class UsuarioModulo extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public UsuarioModulo(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Override
    protected void addAllocationStrings() {
        super.addAllocationStrings();
        super.addAllocationStrings("usuario.grupo");
    }

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of UsuarioModulo's attributes">
        /**/
        setLocalizedLabel(ENGLISH, "user/module association");
        setLocalizedLabel(SPANISH, "asociación Usuario/Módulo");
        setLocalizedCollectionLabel(ENGLISH, "User/Module Associations");
        setLocalizedCollectionLabel(SPANISH, "Asociaciones Usuario/Módulo");
        /**/
        setLocalizedCollectionLabel(ENGLISH, usuario, "Módulos by User");
        setLocalizedCollectionLabel(SPANISH, usuario, "Módulos por Usuario");
        setLocalizedCollectionShortLabel(ENGLISH, usuario, "Módulos");
        setLocalizedCollectionShortLabel(SPANISH, usuario, "Módulos");
        /**/
        setLocalizedDescription(ENGLISH, "Each instance of " + b("User/Module Associations") + " represents a "
            + "module associated with an application user."
            + "");
        setLocalizedDescription(SPANISH, "Cada instancia de " + b("Asociaciones Usuario/Módulo") + " representa un "
            + "módulo asociado con un usuario de la aplicación."
            + "");
        /**/
        setLocalizedShortDescription(ENGLISH, "module associated with an application user");
        setLocalizedShortDescription(SPANISH, "módulo asociado con un usuario de la aplicación");
        /**/
        // </editor-fold>
        /**/
    }

    @PrimaryKey
    public LongProperty id;

    @VersionProperty
    public LongProperty version;

    @NameProperty
    @ColumnField(calculable = Kleenean.TRUE)
    public CloakedStringProperty nombre;

    @ForeignKey(onDelete = OnDeleteAction.CASCADE, onUpdate = OnUpdateAction.CASCADE)
    @ManyToOne(navigability = Navigability.BIDIRECTIONAL, view = MasterDetailView.TABLE)
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.TRUE, detail = Kleenean.TRUE, report = Kleenean.TRUE, export = Kleenean.TRUE)
    public Usuario usuario;

//  20171213: remove foreign-key referring to catalog tables
//  @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE, quickAdding = QuickAddingFilter.MISSING)
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(table = Kleenean.TRUE, detail = Kleenean.TRUE, report = Kleenean.TRUE, export = Kleenean.TRUE)
    @EntityReferenceSearch(searchType = SearchType.LIST, listStyle = ListStyle.NAME)
    public ModuloAplicacion modulo;

    @Override
    protected void settleProperties() {
        super.settleProperties();
        /**/
        nombre.setCalculableValueExpression(concatenate(usuario.codigoUsuario, SLASH, modulo.codigoModulo));
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of UsuarioModulo's properties">
        /**/
        usuario.setLocalizedLabel(ENGLISH, "user");
        usuario.setLocalizedLabel(SPANISH, "usuario");
        /**/
        modulo.setLocalizedLabel(ENGLISH, "module");
        modulo.setLocalizedLabel(SPANISH, "módulo");
        /**/
        // </editor-fold>
        /**/
    }

    @Override
    protected void settleLinks() {
        super.settleLinks();
        linkForeignSegmentProperty(usuario.grupo);
    }

    protected Key uk_usuario_modulo_0001;

    @Override
    protected void settleKeys() {
        super.settleKeys();
        /**/
        uk_usuario_modulo_0001.setUnique(true);
        uk_usuario_modulo_0001.newKeyField(usuario, modulo);
        /**/
    }

    protected Check checkUsuarioOrdinario;

    protected Segment menusPredefinidos;

    @Override
    protected void settleExpressions() {
        super.settleExpressions();
        /**/
        checkUsuarioOrdinario = usuario.esUsuarioEspecial.isFalse();
        menusPredefinidos = modulo.menusPredefinidos.isTrue();
        /**/
        checkUsuarioOrdinario.setLocalizedDescription(ENGLISH, "the user is not a special user");
        checkUsuarioOrdinario.setLocalizedDescription(SPANISH, "el usuario no es un usuario especial");
        checkUsuarioOrdinario.setLocalizedErrorMessage(ENGLISH, "the user is a special user");
        checkUsuarioOrdinario.setLocalizedErrorMessage(SPANISH, "el usuario es un usuario especial");
        /**/
        menusPredefinidos.setLocalizedDescription(ENGLISH, "the module has predefined menus");
        menusPredefinidos.setLocalizedDescription(SPANISH, "el modulo tiene menús predefinidos");
        menusPredefinidos.setLocalizedErrorMessage(ENGLISH, "the module does not have predefined menus");
        menusPredefinidos.setLocalizedErrorMessage(SPANISH, "el modulo no tiene menús predefinidos");
        /**/
    }

    @Override
    protected void settleFilters() {
        super.settleFilters();
        /**/
        setInsertFilter(usuario.usuariosOrdinarios);
        setMasterDetailFilter(usuario.usuariosOrdinarios);
        /* 20/09/2024 - permitir módulos sin menú predefinido, porque podrían tener menú en otra aplicación
        modulo.setSearchQueryFilter(menusPredefinidos);
        /**/
    }

}
