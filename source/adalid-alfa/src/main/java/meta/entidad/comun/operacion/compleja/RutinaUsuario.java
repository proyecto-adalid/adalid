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

/**
 * RutinaUsuario Persistent Entity.
 *
 * @author Jorge Campins
 */
@EntityClass(base = Kleenean.TRUE, independent = Kleenean.TRUE, resourceType = ResourceType.OPERATION, resourceGender = ResourceGender.FEMININE)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE, onload = SelectOnloadOption.EXECUTE)
@EntityInsertOperation(enabled = Kleenean.TRUE)
@EntityUpdateOperation(enabled = Kleenean.TRUE)
@EntityDeleteOperation(enabled = Kleenean.TRUE)
@EntityTableView(enabled = Kleenean.TRUE)
@EntityDetailView(enabled = Kleenean.TRUE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
public class RutinaUsuario extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public RutinaUsuario(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Override
    protected void addAllocationStrings() {
        super.addAllocationStrings();
        super.addAllocationStrings(
            "variable.rutina"
        );
    }

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setOrderBy(nombre);
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of RutinaUsuario's attributes">
        setLocalizedLabel(ENGLISH, "user routine");
        setLocalizedLabel(SPANISH, "rutina de usuario");
        setLocalizedShortLabel(ENGLISH, "routine");
        setLocalizedShortLabel(SPANISH, "rutina");
        setLocalizedCollectionLabel(ENGLISH, "User Routines");
        setLocalizedCollectionLabel(SPANISH, "Rutinas de Usuario");
        setLocalizedCollectionShortLabel(ENGLISH, "Routines");
        setLocalizedCollectionShortLabel(SPANISH, "Rutinas");
        /**/
        setLocalizedDescription(ENGLISH, "Each instance of " + b("User Routines") + " represents a "
            + "routine that automates the path a user follows through your website interface to complete a task: "
            + "make a reservation, purchase a product, subscribe to something. "
            + "");
        setLocalizedDescription(SPANISH, "Cada instancia de " + b("Rutinas de Usuario") + " representa una "
            + "rutina que automatiza la ruta que sigue un usuario a través de la interfaz de su sitio web para completar una tarea: "
            + "hacer una reserva, comprar un producto, suscribirse a algo."
            + "");
        /**/
        setLocalizedShortDescription(ENGLISH, "routine that automates the path a user follows through your website interface to complete a task: "
            + "make a reservation, purchase a product, subscribe to something"
            + "");
        setLocalizedShortDescription(SPANISH, "rutina que automatiza la ruta que sigue un usuario a través de la interfaz de su sitio web para completar una tarea: "
            + "make a reservation, purchase a product, subscribe to something"
            + "");
        /**/
        // </editor-fold>
    }

    @PrimaryKey
    public LongProperty id;

    @VersionProperty
    public LongProperty version;

    @BusinessKey
    public StringProperty codigo;

    @NameProperty
    public StringProperty nombre;

    @DescriptionProperty
    @PropertyField(create = Kleenean.TRUE)
    @StringField(maxLength = 0)
    public StringProperty descripcion;

    @ColumnField(nullable = Kleenean.TRUE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(create = Kleenean.FALSE, table = Kleenean.TRUE)
    @EntityReferenceDisplay(style = EntityReferenceStyle.NAME)
    @EntityReferenceSearch(searchType = SearchType.LIST, listStyle = ListStyle.NAME, displayMode = DisplayMode.WRITING)
    public VariableRutina variable;

    @OneToMany(targetEntity = VariableRutina.class)
    @EntityCollectionField(create = Kleenean.FALSE, update = Kleenean.FALSE, detail = Kleenean.FALSE)
    EntityCollection variables;

    @OneToMany(targetEntity = PasoRutina.class, mappedBy = "rutina")
    @EntityCollectionField(create = Kleenean.FALSE, update = Kleenean.FALSE, detail = Kleenean.FALSE)
    EntityCollection pasos;

    @Override
    protected void settleProperties() {
        super.settleProperties();
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of RutinaUsuario's properties">
        /**/
        codigo.setLocalizedLabel(ENGLISH, "routine code");
        codigo.setLocalizedLabel(SPANISH, "código de la rutina");
        codigo.setLocalizedShortLabel(ENGLISH, "code");
        codigo.setLocalizedShortLabel(SPANISH, "código");
        /**/
        nombre.setLocalizedLabel(ENGLISH, "routine name");
        nombre.setLocalizedLabel(SPANISH, "nombre de la rutina");
        nombre.setLocalizedShortLabel(ENGLISH, "name");
        nombre.setLocalizedShortLabel(SPANISH, "nombre");
        /**/
        descripcion.setLocalizedLabel(ENGLISH, "routine description");
        descripcion.setLocalizedLabel(SPANISH, "descripción de la rutina");
        descripcion.setLocalizedShortLabel(ENGLISH, "description");
        descripcion.setLocalizedShortLabel(SPANISH, "descripción");
        /**/
        variable.setLocalizedLabel(ENGLISH, "variable");
        variable.setLocalizedLabel(SPANISH, "variable");
        variable.setLocalizedDescription(ENGLISH, "variable that stores the instance for which the routine should be executed "
            + "when executed as a subroutine");
        variable.setLocalizedDescription(SPANISH, "variable que almacena la instancia para la que se debe ejecutar la rutina "
            + "cuando se ejecuta como subrutina");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleCollections() {
        super.settleCollections();
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of RutinaUsuario's collections">
        /**/
        variables.setLocalizedLabel(ENGLISH, "routine variables");
        variables.setLocalizedLabel(SPANISH, "variables de la rutina");
        variables.setLocalizedShortLabel(ENGLISH, "variables");
        variables.setLocalizedShortLabel(SPANISH, "variables");
        variables.setLocalizedDescription(ENGLISH, "routine variable collection");
        variables.setLocalizedDescription(SPANISH, "colección de variables de la rutina");
        /**/
        pasos.setLocalizedLabel(ENGLISH, "routine steps");
        pasos.setLocalizedLabel(SPANISH, "pasos de la rutina");
        pasos.setLocalizedShortLabel(ENGLISH, "steps");
        pasos.setLocalizedShortLabel(SPANISH, "pasos");
        pasos.setLocalizedDescription(ENGLISH, "routine step collection");
        pasos.setLocalizedDescription(SPANISH, "colección de pasos de la rutina");
        /**/
        // </editor-fold>
    }

    BooleanExpression assert0101;

    Check check0101;

    @Override
    protected void settleExpressions() {
        super.settleExpressions();
        /**/
        assert0101 = variable.rutina.isEqualTo(this).and(variable.coleccion.isFalse()); // y debe ser establecida en su primer paso
        /**/
        check0101 = variable.isNull().or(assert0101);
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of RutinaUsuario's expressions">
        /**/
        check0101.setLocalizedDescription(ENGLISH, "\"variable\" must be a variable of this routine "
            + "and must be set in its first step");
        check0101.setLocalizedDescription(SPANISH, "\"variable\" debe ser una variable de esta rutina "
            + "y debe ser establecida en su primer paso");
        check0101.setLocalizedErrorMessage(ENGLISH, "\"variable\" is not a variable of this routine "
            + "or not set in its first step");
        check0101.setLocalizedErrorMessage(SPANISH, "\"variable\" no es una variable de esta rutina "
            + "o no es establecida en su primer paso");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleFilters() {
        super.settleFilters();
        /**/
        variable.setSearchQueryFilter(assert0101);
        /**/
    }

}
