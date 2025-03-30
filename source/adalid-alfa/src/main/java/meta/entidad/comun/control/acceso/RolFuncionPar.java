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
import meta.entidad.comun.configuracion.basica.Funcion;
import meta.entidad.comun.configuracion.basica.ext.FuncionParametro;

/**
 * @author Jorge Campins
 */
@EntityClass(independent = Kleenean.FALSE, resourceType = ResourceType.OPERATION, resourceGender = ResourceGender.FEMININE)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE)
@EntityInsertOperation(enabled = Kleenean.TRUE)
@EntityUpdateOperation(enabled = Kleenean.TRUE)
@EntityDeleteOperation(enabled = Kleenean.TRUE)
@EntityReportOperation(enabled = Kleenean.FALSE)
@EntityTableView(enabled = Kleenean.TRUE, responsiveMode = TableResponsiveMode.PRIORITY, rowsLimit = 100, rows = 100)
@EntityDetailView(enabled = Kleenean.FALSE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
public class RolFuncionPar extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public RolFuncionPar(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Override
    protected void addAllocationStrings() {
        super.addAllocationStrings();
        super.addAllocationStrings(
            "rolFuncion.rol.grupo",
            "rolFuncion.funcion",
            "funcionParametro.funcion"
        );
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
    @PropertyField(table = Kleenean.FALSE)
    public RolFuncion rolFuncion;

    @ColumnField(calculable = Kleenean.TRUE)
//  @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE, export = Kleenean.TRUE)
    public Rol rol;

    @ColumnField(calculable = Kleenean.TRUE)
//  20171213: remove foreign-key referring to Funcion
//  @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(table = Kleenean.TRUE, report = Kleenean.TRUE, export = Kleenean.TRUE)
    public Funcion funcion;

//  20171213: remove foreign-key referring to FuncionParametro
//  @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE, quickAdding = QuickAddingFilter.MISSING)
    @ColumnField(nullable = Kleenean.FALSE)
    @EntityReferenceSearch(searchType = SearchType.LIST, listStyle = ListStyle.NAME)
    @PropertyField(required = Kleenean.TRUE, table = Kleenean.TRUE, report = Kleenean.TRUE, access = PropertyAccess.RESTRICTED_WRITING)
    public FuncionParametro funcionParametro;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of RolFuncionPar's attributes">
        setLocalizedLabel(ENGLISH, "role/function/parameter association");
        setLocalizedLabel(SPANISH, "asociación Rol/Función/Parámetro");
        setLocalizedCollectionLabel(ENGLISH, "Role/Function/Parameter Associations");
        setLocalizedCollectionLabel(SPANISH, "Asociaciones Rol/Función/Parámetro");
        /**/
        setLocalizedCollectionLabel(ENGLISH, rolFuncion, "Function Parameters by Role");
        setLocalizedCollectionLabel(SPANISH, rolFuncion, "Parámetros de Funciones por Rol");
        setLocalizedCollectionShortLabel(ENGLISH, rolFuncion, "Parameters");
        setLocalizedCollectionShortLabel(SPANISH, rolFuncion, "Parámetros");
        /**/
        setLocalizedDescription(ENGLISH, "Each instance of " + b("Role/Function/Parameter Associations") + " represents a "
            + "parameter of a function associated with an application role."
            + "");
        setLocalizedDescription(SPANISH, "Cada instancia de " + b("Asociaciones Rol/Función/Parámetro") + " representa un "
            + "parámetro de una función asociada con un rol de la aplicación."
            + "");
        /**/
        setLocalizedShortDescription(ENGLISH, "parameter of a function associated with an application role");
        setLocalizedShortDescription(SPANISH, "parámetro de una función asociada con un rol de la aplicación");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        /**/
        nombre.setCalculableValueExpression(concatenate(funcionParametro.codigoFuncionParametro, SLASH, rolFuncion.rol.codigoRol));
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of RolFuncionPar's properties">
        rolFuncion.setLocalizedLabel(ENGLISH, "role/function association");
        rolFuncion.setLocalizedLabel(SPANISH, "asociación Rol/Función");
        /**/
        funcionParametro.setLocalizedLabel(ENGLISH, "function parameter");
        funcionParametro.setLocalizedLabel(SPANISH, "parámetro de función");
        funcionParametro.setLocalizedShortLabel(ENGLISH, "parameter");
        funcionParametro.setLocalizedShortLabel(SPANISH, "parámetro");
        // </editor-fold>
    }

    @Override
    protected void settleLinks() {
        super.settleLinks();
        linkForeignSegmentProperty(rolFuncion.rol.grupo);
        linkForeignQueryProperty(rolFuncion.rol.codigoRol, rolFuncion.rol.nombreRol);
        linkForeignQueryProperty(rolFuncion.funcion.codigoFuncion, rolFuncion.funcion.nombreFuncion);
        rol.linkCalculableValueEntityReference(rolFuncion.rol);
        funcion.linkCalculableValueEntityReference(rolFuncion.funcion);
    }

    protected Key uk_rol_funcion_par_0001;

    @Override
    protected void settleKeys() {
        super.settleKeys();
        uk_rol_funcion_par_0001.setUnique(true);
        uk_rol_funcion_par_0001.newKeyField(rolFuncion, funcionParametro);
    }

    protected Segment modificables;

    protected BooleanExpression claim101;

    @Override
    protected void settleExpressions() {
        super.settleExpressions();
        /**/
        modificables = rolFuncion.rol.id.isGreaterOrEqualTo(10000L);
        claim101 = funcionParametro.funcion.isEqualTo(rolFuncion.funcion).and(funcionParametro.accesoRestringido.isTrue());
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of RolFuncionPar's expressions">
        /**/
        modificables.setLocalizedDescription(ENGLISH, "the role is not a basic configuration role");
        modificables.setLocalizedDescription(SPANISH, "el rol no es un rol de configuración básica del sistema");
        modificables.setLocalizedErrorMessage(ENGLISH, "the role is a basic configuration role; "
            + "the parameters of its functions can't be modified or deleted");
        modificables.setLocalizedErrorMessage(SPANISH, "el rol es un rol de configuración básica del sistema; "
            + "no se permite modificar ni eliminar parámetros de sus funciones");
        /**/
        claim101.setLocalizedLabel(ENGLISH, "verify function parameter");
        claim101.setLocalizedLabel(SPANISH, "chequear parámetro de función");
        claim101.setLocalizedDescription(ENGLISH, "the parameter must have restricted access and its function must be equal to the function associated with the role");
        claim101.setLocalizedDescription(SPANISH, "el parámetro debe tener acceso restringido y su función debe ser igual a la función asociada al rol");
        claim101.setLocalizedErrorMessage(ENGLISH, "the parameter does not have restricted access or its function is not equal to the function associated with the role");
        claim101.setLocalizedErrorMessage(SPANISH, "el parámetro no tiene acceso restringido o su función no es igual a la función asociada al rol");
        /**/
        // </editor-fold>
        /**/
    }

    @Override
    protected void settleFilters() {
        super.settleFilters();
        setInsertFilter(rolFuncion.modificables);
        setUpdateFilter(modificables);
        setDeleteFilter(modificables);
        funcionParametro.setSearchQueryFilter(claim101);
    }

}
