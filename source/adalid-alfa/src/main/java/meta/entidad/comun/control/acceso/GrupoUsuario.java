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

@EntityClass(independent = Kleenean.TRUE, resourceType = ResourceType.OPERATION, resourceGender = ResourceGender.MASCULINE)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntityDocGen(stateDiagram = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE, onload = SelectOnloadOption.EXECUTE)
@EntityInsertOperation(enabled = Kleenean.TRUE)
@EntityUpdateOperation(enabled = Kleenean.TRUE)
@EntityDeleteOperation(enabled = Kleenean.TRUE)
@EntityTableView(enabled = Kleenean.TRUE, inserts = Kleenean.TRUE, updates = Kleenean.TRUE, quickFilter = Kleenean.TRUE)
@EntityDetailView(enabled = Kleenean.TRUE)
@EntityTreeView(enabled = Kleenean.TRUE)
@EntityConsoleView(enabled = Kleenean.TRUE)
@EntityReferenceDisplay(style = EntityReferenceStyle.NAME)
@EntityReferenceSearch(searchType = SearchType.LIST, listStyle = ListStyle.CHARACTER_KEY_AND_NAME)
public class GrupoUsuario extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public GrupoUsuario(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of GrupoUsuario's attributes">
        /**/
        setLocalizedLabel(ENGLISH, "user group");
        setLocalizedLabel(SPANISH, "grupo de usuarios");
        setLocalizedCollectionLabel(ENGLISH, "User Groups");
        setLocalizedCollectionLabel(SPANISH, "Grupos de Usuarios");
        /**/
        setLocalizedDescription(ENGLISH, "Each instance of " + b("User Groups") + " represents an "
            + "application user group."
            + "");
        setLocalizedDescription(SPANISH, "Cada instancia de " + b("Grupos de Usuarios") + " representa un "
            + "grupo de usuarios de la aplicación."
            + "");
        /**/
        setLocalizedShortDescription(ENGLISH, "application user group");
        setLocalizedShortDescription(SPANISH, "grupo de usuarios de la aplicación");
        /**/
        // </editor-fold>
        /**/
    }

    @PrimaryKey
    @SegmentProperty
    public LongProperty id;

    @VersionProperty
    public LongProperty version;

    @BusinessKey
    public StringProperty codigo;

    @NameProperty
    public StringProperty nombre;

    @ParentProperty
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(view = MasterDetailView.NONE)
    @PropertyField(table = Kleenean.TRUE, create = Kleenean.TRUE)
    public GrupoUsuario contenedor;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public BooleanProperty esGrupoEspecial;

    @Override
    protected void settleProperties() {
        super.settleProperties();
        /**/
        esGrupoEspecial.setInitialValue(false);
        esGrupoEspecial.setDefaultValue(false);
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of GrupoUsuario's properties">
        /**/
        codigo.setLocalizedLabel(ENGLISH, "group code");
        codigo.setLocalizedLabel(SPANISH, "código del grupo");
        codigo.setLocalizedShortLabel(ENGLISH, "code");
        codigo.setLocalizedShortLabel(SPANISH, "código");
        /**/
        nombre.setLocalizedLabel(ENGLISH, "group name");
        nombre.setLocalizedLabel(SPANISH, "nombre del grupo");
        nombre.setLocalizedShortLabel(ENGLISH, "name");
        nombre.setLocalizedShortLabel(SPANISH, "nombre");
        /**/
        contenedor.setLocalizedLabel(ENGLISH, "containing group");
        contenedor.setLocalizedLabel(SPANISH, "grupo contenedor");
        contenedor.setLocalizedShortLabel(ENGLISH, "container");
        contenedor.setLocalizedShortLabel(SPANISH, "contenedor");
        contenedor.setLocalizedDescription(ENGLISH, "group containing this group");
        contenedor.setLocalizedDescription(SPANISH, "grupo que contiene este grupo");
        contenedor.setLocalizedTooltip(ENGLISH, "code of the group that contains this group");
        contenedor.setLocalizedTooltip(SPANISH, "código del grupo que contiene este grupo");
        /**/
        esGrupoEspecial.setLocalizedLabel(ENGLISH, "special group");
        esGrupoEspecial.setLocalizedLabel(SPANISH, "grupo especial");
        esGrupoEspecial.setLocalizedShortLabel(ENGLISH, "special");
        esGrupoEspecial.setLocalizedShortLabel(SPANISH, "especial");
        /**/
        // </editor-fold>
        /**/
    }

    protected Segment gruposEspeciales, gruposOrdinarios;

    @Override
    protected void settleExpressions() {
        super.settleExpressions();
        /**/
        gruposEspeciales = esGrupoEspecial.isTrue();
        gruposOrdinarios = esGrupoEspecial.isFalse();
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of GrupoUsuario's expressions">
        /**/
        gruposEspeciales.setLocalizedDescription(ENGLISH, "the group is a special group");
        gruposEspeciales.setLocalizedDescription(SPANISH, "el grupo es un grupo especial");
        gruposEspeciales.setLocalizedErrorMessage(ENGLISH, "the group is not a special group");
        gruposEspeciales.setLocalizedErrorMessage(SPANISH, "el grupo no es un grupo especial");
        /**/
        gruposOrdinarios.setLocalizedDescription(ENGLISH, "the group is not a special group");
        gruposOrdinarios.setLocalizedDescription(SPANISH, "el grupo no es un grupo especial");
        gruposOrdinarios.setLocalizedErrorMessage(ENGLISH, "the group is a special group");
        gruposOrdinarios.setLocalizedErrorMessage(SPANISH, "el grupo es un grupo especial");
        /**/
        // </editor-fold>
        /**/
    }

    @Override
    protected void settleFilters() {
        super.settleFilters();
        /**/
        setUpdateFilter(gruposOrdinarios);
        setDeleteFilter(gruposOrdinarios);
        /**/
    }

    /*
    public Instance USUARIOS_ESPECIALES, USUARIOS_ORDINARIOS;

    /**/
    @Override
    protected void settleInstances() {
        super.settleInstances();
        /*
        USUARIOS_ESPECIALES.newInstanceField(id, 100);
        USUARIOS_ESPECIALES.newInstanceField(codigo, "special_users", ENGLISH);
        USUARIOS_ESPECIALES.newInstanceField(nombre, "Special Users", ENGLISH);
        USUARIOS_ESPECIALES.newInstanceField(codigo, "usuarios_especiales", SPANISH);
        USUARIOS_ESPECIALES.newInstanceField(nombre, "Usuarios Especiales", SPANISH);
        /*
        USUARIOS_ORDINARIOS.newInstanceField(id, 200);
        USUARIOS_ORDINARIOS.newInstanceField(codigo, "ordinary_users", ENGLISH);
        USUARIOS_ORDINARIOS.newInstanceField(nombre, "Ordinary Users", ENGLISH);
        USUARIOS_ORDINARIOS.newInstanceField(codigo, "usuarios_ordinarios", SPANISH);
        USUARIOS_ORDINARIOS.newInstanceField(nombre, "Usuarios Ordinarios", SPANISH);
        /**/
    }

}
