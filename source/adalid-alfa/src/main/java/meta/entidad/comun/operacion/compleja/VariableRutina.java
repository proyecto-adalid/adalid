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
package meta.entidad.comun.operacion.compleja;

import adalid.core.*;
import adalid.core.annotations.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import adalid.core.properties.*;
import java.lang.reflect.Field;
import meta.entidad.comun.configuracion.basica.ClaseRecurso;

/**
 * VariableRutina Persistent Entity.
 *
 * @author Jorge Campins
 */
@EntityClass(base = Kleenean.TRUE, independent = Kleenean.FALSE, resourceType = ResourceType.OPERATION, resourceGender = ResourceGender.FEMININE)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE)
@EntityInsertOperation(enabled = Kleenean.TRUE)
@EntityUpdateOperation(enabled = Kleenean.TRUE)
@EntityDeleteOperation(enabled = Kleenean.TRUE)
@EntityTableView(enabled = Kleenean.TRUE)
@EntityDetailView(enabled = Kleenean.TRUE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
public class VariableRutina extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public VariableRutina(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Override
    protected void addAllocationStrings() {
        super.addAllocationStrings();
        super.addAllocationStrings(
            "rutina.variable"
        );
    }

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setOrderBy(rutina, nombre);
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of VariableRutina's attributes">
        setLocalizedLabel(ENGLISH, "user routine variable");
        setLocalizedLabel(SPANISH, "variable de rutina de usuario");
        setLocalizedShortLabel(ENGLISH, "routine variable");
        setLocalizedShortLabel(SPANISH, "variable de rutina");
        setLocalizedCollectionLabel(ENGLISH, "User Routine Variables");
        setLocalizedCollectionLabel(SPANISH, "Variables de Rutina de Usuario");
        setLocalizedCollectionShortLabel(ENGLISH, "Variables");
        setLocalizedCollectionShortLabel(SPANISH, "Variables");
        /**/
        setLocalizedDescription(ENGLISH, "Each instance of " + b("User Routine Variables") + " represents a "
            + "user routine variable that stores the result of executing one or more steps "
            + "in the path a user follows through your website interface to complete a task."
            + "");
        setLocalizedDescription(SPANISH, "Cada instancia de " + b("Variables de Rutina de Usuario") + " representa una "
            + "variable de una rutina de usuario que almacena el resultado de la ejecución de uno o mas pasos "
            + "en la ruta que sigue un usuario a través de la interfaz de su sitio web para completar una tarea."
            + "");
        /**/
        setLocalizedShortDescription(ENGLISH, "user routine variable that stores the result of executing one or more steps "
            + "in the path a user follows through your website interface to complete a task"
            + "");
        setLocalizedShortDescription(SPANISH, "variable de una rutina de usuario que almacena el resultado de la ejecución de uno o mas pasos "
            + "en la ruta que sigue un usuario a través de la interfaz de su sitio web para completar una tarea"
            + "");
        /**/
        // </editor-fold>
    }

    @PrimaryKey
    public LongProperty id;

    @VersionProperty
    public LongProperty version;

    @ForeignKey(onDelete = OnDeleteAction.CASCADE, onUpdate = OnUpdateAction.CASCADE)
    @ManyToOne(main = Kleenean.TRUE, navigability = Navigability.BIDIRECTIONAL, view = MasterDetailView.TABLE_AND_DETAIL)
    @ColumnField(nullable = Kleenean.FALSE)
    public RutinaUsuario rutina;

    @NameProperty
    public StringProperty nombre;

    @DescriptionProperty
    @PropertyField(create = Kleenean.TRUE)
    @StringField(maxLength = 0)
    public StringProperty descripcion;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(create = Kleenean.TRUE, table = Kleenean.TRUE, overlay = Kleenean.TRUE)
    public BooleanProperty coleccion;

    @ColumnField(nullable = Kleenean.FALSE)
//  20171213: remove foreign-key referring to ClaseRecurso
//  @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(create = Kleenean.TRUE, table = Kleenean.TRUE, overlay = Kleenean.TRUE)
    public ClaseRecurso claseRecurso;

    /*
    @ColumnField(calculable = Kleenean.TRUE)
    @PropertyField(table = Kleenean.FALSE, detail = Kleenean.FALSE, overlay = Kleenean.TRUE)
    public StringProperty claseVariable;

    /**/
    @Override
    protected void settleProperties() {
        super.settleProperties();
        /**/
        coleccion.setInitialValue(false);
        coleccion.setDefaultValue(false);
        /*
        claseVariable.setCalculableValueExpression(claseRecurso.codigoClaseRecurso.concat(coleccion.then("...")));
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of VariableRutina's properties">
        /**/
        rutina.setLocalizedLabel(ENGLISH, "routine");
        rutina.setLocalizedLabel(SPANISH, "rutina");
        rutina.setLocalizedDescription(ENGLISH, ""
            + "A user routine automates the path a user follows through your website interface to complete a task: "
            + "make a reservation, purchase a product, subscribe to something. "
            + "");
        rutina.setLocalizedDescription(SPANISH, ""
            + "Una rutina de usuario automatiza la ruta que sigue un usuario a través de la interfaz de su sitio web para completar una tarea: "
            + "hacer una reserva, comprar un producto, suscribirse a algo."
            + "");
        /**/
        nombre.setLocalizedLabel(ENGLISH, "variable name");
        nombre.setLocalizedLabel(SPANISH, "nombre de la variable");
        nombre.setLocalizedShortLabel(ENGLISH, "name");
        nombre.setLocalizedShortLabel(SPANISH, "nombre");
        /**/
        descripcion.setLocalizedLabel(ENGLISH, "routine variable description");
        descripcion.setLocalizedLabel(SPANISH, "descripción de la variable de rutina");
        descripcion.setLocalizedShortLabel(ENGLISH, "description");
        descripcion.setLocalizedShortLabel(SPANISH, "descripción");
        /**/
        coleccion.setLocalizedLabel(ENGLISH, "collection");
        coleccion.setLocalizedLabel(SPANISH, "colección");
        coleccion.setLocalizedDescription(ENGLISH, "indicator that shows whether or not the variable is a collection of instances of the specified class");
        coleccion.setLocalizedDescription(SPANISH, "indicador que muestra si la variable es, o no, una colección de instancias de la clase especificada");
        /**/
        claseRecurso.setLocalizedLabel(ENGLISH, "resource class");
        claseRecurso.setLocalizedLabel(SPANISH, "clase de recurso");
        /*
        claseVariable.setLocalizedLabel(ENGLISH, "variable class");
        claseVariable.setLocalizedLabel(SPANISH, "clase de variable");
        /**/
        // </editor-fold>
    }

    Key key101;

    @Override
    protected void settleKeys() {
        super.settleKeys();
        /**/
        key101.setUnique(true);
        key101.newKeyField(rutina, nombre);
        /**/
    }

    @Override
    protected void settleFilters() {
        super.settleFilters();
        /**/
        coleccion.setModifyingFilter(rutina.variable.isNullOrNotEqualTo(this));
        /**/
    }

}
