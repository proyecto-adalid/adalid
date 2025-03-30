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
package meta.entidad.comun.configuracion.basica;

import adalid.core.*;
import adalid.core.annotations.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import adalid.core.properties.*;
import java.lang.reflect.Field;

/**
 * @author Jorge Campins
 */
@EntityClass(catalog = Kleenean.TRUE, independent = Kleenean.TRUE, resourceType = ResourceType.CONFIGURATION, resourceGender = ResourceGender.MASCULINE)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE, access = OperationAccess.PUBLIC, onload = SelectOnloadOption.EXECUTE, rowsLimit = 500)
@EntityInsertOperation(enabled = Kleenean.FALSE)
@EntityUpdateOperation(enabled = Kleenean.FALSE)
@EntityDeleteOperation(enabled = Kleenean.FALSE)
@EntityTableView(enabled = Kleenean.TRUE, responsiveMode = TableResponsiveMode.PRIORITY, rowsLimit = 500)
@EntityDetailView(enabled = Kleenean.TRUE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
public class ModuloAplicacion extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public ModuloAplicacion(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public LongProperty id;

    @VersionProperty
    public LongProperty version;

    @BusinessKey
    @StringField(maxLength = 100)
    @PropertyField(update = Kleenean.FALSE/*, overlay = Kleenean.FALSE*/)
    public StringProperty codigoModulo;

    @NameProperty
    @PropertyField(update = Kleenean.FALSE)
    public StringProperty nombreModulo;

    @DescriptionProperty
    @PropertyField(update = Kleenean.FALSE)
    @StringField(maxLength = 0)
    public StringProperty descripcionModulo;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(responsivePriority = 6, update = Kleenean.FALSE, table = Kleenean.TRUE)
    public BooleanProperty menusPredefinidos;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(responsivePriority = 6, update = Kleenean.FALSE, table = Kleenean.TRUE)
    public BooleanProperty rolesPredefinidos;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(update = Kleenean.FALSE)
    public BooleanProperty entidadesForaneas;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(update = Kleenean.FALSE)
    public BooleanProperty entidadesPrivadas;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(update = Kleenean.FALSE)
    public BooleanProperty inmutable;

    @ForeignKey(onDelete = OnDeleteAction.CASCADE, onUpdate = OnUpdateAction.CASCADE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(hidden = Kleenean.TRUE)
    public Aplicacion aplicacion;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of ModuloAplicacion's attributes">
        /**/
        setLocalizedLabel(ENGLISH, "module");
        setLocalizedLabel(SPANISH, "módulo");
        setLocalizedCollectionLabel(ENGLISH, "Modules");
        setLocalizedCollectionLabel(SPANISH, "Módulos");
        /**/
        setLocalizedDescription(ENGLISH, "Each instance of " + b("Modules") + " represents an "
            + "application module."
            + "");
        setLocalizedDescription(SPANISH, "Cada instancia de " + b("Módulos") + " representa un "
            + "módulo de la aplicación."
            + "");
        /**/
        setLocalizedShortDescription(ENGLISH, "application module");
        setLocalizedShortDescription(SPANISH, "módulo de la aplicación");
        /**/
        // </editor-fold>
        /**/
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        /**/
        setOrderBy(codigoModulo);
        /**/
        menusPredefinidos.setInitialValue(false);
        menusPredefinidos.setDefaultValue(false);
        /**/
        rolesPredefinidos.setInitialValue(false);
        rolesPredefinidos.setDefaultValue(false);
        /**/
        entidadesForaneas.setInitialValue(false);
        entidadesForaneas.setDefaultValue(false);
        /**/
        entidadesPrivadas.setInitialValue(false);
        entidadesPrivadas.setDefaultValue(false);
        /**/
        inmutable.setInitialValue(false);
        inmutable.setDefaultValue(false);
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of ModuloAplicacion's properties">
        /**/
        codigoModulo.setLocalizedLabel(ENGLISH, "module code");
        codigoModulo.setLocalizedLabel(SPANISH, "código del módulo");
        codigoModulo.setLocalizedShortLabel(ENGLISH, "code");
        codigoModulo.setLocalizedShortLabel(SPANISH, "código");
        /**/
        nombreModulo.setLocalizedLabel(ENGLISH, "module name");
        nombreModulo.setLocalizedLabel(SPANISH, "nombre del módulo");
        nombreModulo.setLocalizedShortLabel(ENGLISH, "name");
        nombreModulo.setLocalizedShortLabel(SPANISH, "nombre");
        /**/
        descripcionModulo.setLocalizedLabel(ENGLISH, "module description");
        descripcionModulo.setLocalizedLabel(SPANISH, "descripción del módulo");
        descripcionModulo.setLocalizedShortLabel(ENGLISH, "description");
        descripcionModulo.setLocalizedShortLabel(SPANISH, "descripción");
        /**/
        menusPredefinidos.setLocalizedLabel(ENGLISH, "predefined menus");
        menusPredefinidos.setLocalizedLabel(SPANISH, "menús predefinidos");
        menusPredefinidos.setLocalizedDescription(ENGLISH, ""
            + "predefined menus indicates if the main menu of the application includes views (pages) of the entities that make up the module"
            + "");
        menusPredefinidos.setLocalizedDescription(SPANISH, ""
            + "menús predefinidos indica si el menú principal de la aplicación incluye vistas (páginas) de las entidades que integran el módulo"
            + "");
        /**/
        rolesPredefinidos.setLocalizedLabel(ENGLISH, "predefined roles");
        rolesPredefinidos.setLocalizedLabel(SPANISH, "roles predefinidos");
        rolesPredefinidos.setLocalizedDescription(ENGLISH, ""
            + "predefined roles indicates if there are specific roles for the operations of the entities that make up the module"
            + "");
        rolesPredefinidos.setLocalizedDescription(SPANISH, ""
            + "roles predefinidos indica si existen roles específicos para las operaciones de las entidades que integran el módulo"
            + "");
        /**/
        entidadesForaneas.setLocalizedLabel(ENGLISH, "foreign entities");
        entidadesForaneas.setLocalizedLabel(SPANISH, "entidades foráneas");
        entidadesForaneas.setLocalizedDescription(ENGLISH, ""
            + "foreign entities indicates whether the entities that make up the module belong to the set of foreign entities of the application; "
            + "foreign entities are entities whose corresponding tables are not defined in the application database, "
            + "but in another database that typically resides on a different server"
            + "");
        entidadesForaneas.setLocalizedDescription(SPANISH, ""
            + "entidades foráneas indica si las entidades que integran el módulo pertenecen al conjunto de entidades foráneas de la aplicación; "
            + "las entidades foráneas son entidades cuyas correspondientes tablas no están definidas en la base de datos de la aplicación, "
            + "sino en otra base de datos que tipicamente reside en un servidor diferente"
            + "");
        /**/
        entidadesPrivadas.setLocalizedLabel(ENGLISH, "private entities");
        entidadesPrivadas.setLocalizedLabel(SPANISH, "entidades privadas");
        entidadesPrivadas.setLocalizedDescription(ENGLISH, ""
            + "private entities indicates whether the entities that make up the module belong to the set of private entities of the application;"
            + "private entities are entities that do not have views (pages) available"
            + "");
        entidadesPrivadas.setLocalizedDescription(SPANISH, ""
            + "entidades privadas indica si las entidades que integran el módulo pertenecen al conjunto de entidades privadas de la aplicación; "
            + "las entidades privadas son entidades que no tienen vistas (páginas) disponibles"
            + "");
        /**/
        inmutable.setLocalizedLabel(ENGLISH, "immutable");
        inmutable.setLocalizedLabel(SPANISH, "inmutable");
        /**/
        aplicacion.setLocalizedLabel(ENGLISH, "application");
        aplicacion.setLocalizedLabel(SPANISH, "aplicación");
        /**/
        // </editor-fold>
    }

}
