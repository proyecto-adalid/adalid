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
import meta.entidad.comun.configuracion.basica.ext.Funcion;

/**
 * PasoRutina Persistent Entity.
 *
 * @author Jorge Campins
 */
@EntityClass(base = Kleenean.TRUE, independent = Kleenean.FALSE, resourceType = ResourceType.OPERATION, resourceGender = ResourceGender.MASCULINE)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE)
@EntityInsertOperation(enabled = Kleenean.TRUE)
@EntityUpdateOperation(enabled = Kleenean.TRUE)
@EntityDeleteOperation(enabled = Kleenean.TRUE)
@EntityTableView(enabled = Kleenean.TRUE)//, inserts = Kleenean.FALSE, updates = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.TRUE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
public class PasoRutina extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public PasoRutina(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Override
    protected void addAllocationStrings() {
        super.addAllocationStrings();
        super.addAllocationStrings(
            "funcion.tipoFuncion",
            "funcion.rango",
            "para.rutina",
            "variable.rutina",
            "variable.claseRecurso"
        );
    }

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of PasoRutina's attributes">
        setLocalizedLabel(ENGLISH, "user routine step");
        setLocalizedLabel(SPANISH, "paso de rutina de usuario");
        setLocalizedShortLabel(ENGLISH, "routine step");
        setLocalizedShortLabel(SPANISH, "paso de rutina");
        setLocalizedCollectionLabel(ENGLISH, "User Routine Steps");
        setLocalizedCollectionLabel(SPANISH, "Pasos de Rutina de Usuario");
        setLocalizedCollectionShortLabel(ENGLISH, "Steps");
        setLocalizedCollectionShortLabel(SPANISH, "Pasos");
        /**/
        setLocalizedDescription(ENGLISH, "Each instance of " + b("User Routine Steps") + " represents a "
            + "user routine step that implements a specific action "
            + "in the path a user follows through your website interface to complete a task."
            + "");
        setLocalizedDescription(SPANISH, "Cada instancia de " + b("Pasos de Rutina de Usuario") + " representa un "
            + "paso de una rutina de usuario que implementa una acción específica "
            + "en la ruta que sigue un usuario a través de la interfaz de su sitio web para completar una tarea."
            + "");
        /**/
        setLocalizedShortDescription(ENGLISH, "user routine step that implements a specific action "
            + "in the path a user follows through your website interface to complete a task"
            + "");
        setLocalizedShortDescription(SPANISH, "paso de una rutina de usuario que implementa una acción específica "
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

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(create = Kleenean.TRUE, table = Kleenean.TRUE, required = Kleenean.FALSE)
    @MasterSequence(masterField = "rutina", start = 10, step = 10, nextValueRule = NextValueRule.CEILING)
    public IntegerProperty numero;

    @DescriptionProperty
    @PropertyField(create = Kleenean.TRUE)
    @StringField(maxLength = 0)
    public StringProperty descripcion;

    @ColumnField(nullable = Kleenean.TRUE)
//  20171213: remove foreign-key referring to Funcion
//  @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
//  @PropertyField(create = Kleenean.TRUE, table = Kleenean.TRUE)
    @PropertyField(create = Kleenean.TRUE, table = Kleenean.TRUE, required = Kleenean.TRUE) // required while subroutines are not implemented
    public Funcion funcion;

    @ColumnField(nullable = Kleenean.TRUE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(create = Kleenean.TRUE, table = Kleenean.TRUE)
    public RutinaUsuario subrutina;

    @PropertyField(create = Kleenean.TRUE)
    @StringField(maxLength = 0)
    public StringProperty precondiciones;

    @PropertyField(create = Kleenean.TRUE)
    @StringField(maxLength = 0)
    public StringProperty poscondiciones;

    @PropertyField(create = Kleenean.TRUE)
    @StringField(maxLength = 0)
    public StringProperty invariantes;

    @PropertyField(create = Kleenean.TRUE)
    public StringProperty filtro;

    @ColumnField(calculable = Kleenean.TRUE)
//  @PropertyField(table = Kleenean.TRUE, detail = Kleenean.FALSE)
    @PropertyField(hidden = Kleenean.TRUE)
    public BooleanProperty condicionado;

    @ColumnField(calculable = Kleenean.TRUE)
//  @PropertyField(table = Kleenean.TRUE, detail = Kleenean.FALSE)
    @PropertyField(hidden = Kleenean.TRUE)
    public BooleanProperty filtrado;

    @ColumnField(nullable = Kleenean.TRUE)
    @PropertyField(create = Kleenean.TRUE, table = Kleenean.TRUE)
    public IntegerProperty hasta;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(create = Kleenean.TRUE, table = Kleenean.TRUE, required = Kleenean.TRUE)
    public IntegerProperty desde;

    @ColumnField(calculable = Kleenean.TRUE)
    @PropertyField(hidden = Kleenean.TRUE)
    public BooleanProperty coleccion;

    @ColumnField(nullable = Kleenean.TRUE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(create = Kleenean.TRUE, table = Kleenean.TRUE)
    @EntityReferenceDisplay(style = EntityReferenceStyle.NAME)
    @EntityReferenceSearch(searchType = SearchType.LIST, listStyle = ListStyle.NAME)
    public VariableRutina para;

    @ColumnField(nullable = Kleenean.TRUE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(create = Kleenean.TRUE, table = Kleenean.TRUE)
    @EntityReferenceDisplay(style = EntityReferenceStyle.NAME)
    @EntityReferenceSearch(searchType = SearchType.LIST, listStyle = ListStyle.NAME, displayMode = DisplayMode.WRITING)
    public VariableRutina variable;

    @Override
    protected void settleProperties() {
        super.settleProperties();
        /**/
        setOrderBy(rutina, numero, id);
        /**/
        BooleanExpression iterable = funcion.esConstructor.or(funcion.tipoFuncion.isEqualTo(funcion.tipoFuncion.CONSULTA));
        /**/
        numero.setMinValue(0);
        numero.setMaxValue(10000);
        /**/
        condicionado.setCalculableValueExpression(precondiciones.isNotNull().or(invariantes.isNotNull()));
        /**/
        filtrado.setCalculableValueExpression(filtro.isNotNull());
        /**/
        hasta.setInitialValue(1);
        hasta.addInitialValueReferencedProperties(funcion, subrutina);
//      hasta.setDefaultValue(1);
        hasta.setMinValue(1);
        hasta.setMaxValue(iterable.then(Integer.MAX_VALUE).otherwise(1));
        /**/
        desde.setInitialValue(1);
        desde.addInitialValueReferencedProperties(funcion, subrutina);
        desde.setDefaultValue(1);
        desde.setMinValue(0);
        desde.setMaxValue(iterable.then(hasta.coalesce(Integer.MAX_VALUE)).otherwise(1));
        /**/
        coleccion.setCalculableValueExpression(hasta.isNullOrGreaterThan(1));
        coleccion.setInitialValue(hasta.isNullOrGreaterThan(1));
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of PasoRutina's properties">
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
        numero.setLocalizedLabel(ENGLISH, "step number");
        numero.setLocalizedLabel(SPANISH, "número del paso");
        numero.setLocalizedShortLabel(ENGLISH, "number");
        numero.setLocalizedShortLabel(SPANISH, "número");
        /**/
        descripcion.setLocalizedLabel(ENGLISH, "routine step description");
        descripcion.setLocalizedLabel(SPANISH, "descripción del paso de rutina");
        descripcion.setLocalizedShortLabel(ENGLISH, "description");
        descripcion.setLocalizedShortLabel(SPANISH, "descripción");
        /**/
        funcion.setLocalizedLabel(ENGLISH, "function");
        funcion.setLocalizedLabel(SPANISH, "función");
        /**/
        subrutina.setLocalizedLabel(ENGLISH, "subroutine");
        subrutina.setLocalizedLabel(SPANISH, "subrutina");
        subrutina.setLocalizedDescription(ENGLISH, ""
            + "A user routine automates the path a user follows through your website interface to complete a task: "
            + "make a reservation, purchase a product, subscribe to something. "
            + "");
        subrutina.setLocalizedDescription(SPANISH, ""
            + "Una rutina de usuario automatiza la ruta que sigue un usuario a través de la interfaz de su sitio web para completar una tarea: "
            + "hacer una reserva, comprar un producto, suscribirse a algo."
            + "");
        /**/
        precondiciones.setLocalizedLabel(ENGLISH, "preconditions");
        precondiciones.setLocalizedLabel(SPANISH, "precondiciones");
//      precondiciones.setLocalizedDescription(ENGLISH, "conditions that must be met to execute this and the following steps with the same number");
        precondiciones.setLocalizedDescription(ENGLISH, "conditions that must be met to execute this step");
//      precondiciones.setLocalizedDescription(SPANISH, "condiciones que se deben cumplir para ejecutar éste y los siguientes pasos con el mismo número");
        precondiciones.setLocalizedDescription(SPANISH, "condiciones que se deben cumplir para ejecutar este paso");
        /**/
        poscondiciones.setLocalizedLabel(ENGLISH, "postconditions");
        poscondiciones.setLocalizedLabel(SPANISH, "poscondiciones");
        poscondiciones.setLocalizedDescription(ENGLISH, "conditions that should be met after executing this step");
        poscondiciones.setLocalizedDescription(SPANISH, "condiciones que deberían cumplirse después de ejecutar este paso");
        /**/
        invariantes.setLocalizedLabel(ENGLISH, "invariants");
        invariantes.setLocalizedLabel(SPANISH, "invariantes");
        invariantes.setLocalizedDescription(ENGLISH, "conditions that should be met before and after executing this step");
        invariantes.setLocalizedDescription(SPANISH, "condiciones que deberían cumplirse antes y después de ejecutar este paso");
        /**/
        filtro.setLocalizedLabel(ENGLISH, "filter");
        filtro.setLocalizedLabel(SPANISH, "filtro");
        filtro.setLocalizedDescription(ENGLISH, "criteria to be met by the instance(s) added and/or selected by the function");
        filtro.setLocalizedDescription(SPANISH, "criterios que debe(n) cumplir la(s) instancia(s) agregada(s) y/o seleccionada(s) por la función");
        /**/
        condicionado.setLocalizedLabel(ENGLISH, "conditioned");
        condicionado.setLocalizedLabel(SPANISH, "condicionado");
        /**/
        filtrado.setLocalizedLabel(ENGLISH, "filtered");
        filtrado.setLocalizedLabel(SPANISH, "filtrado");
        /**/
        hasta.setLocalizedLabel(ENGLISH, "up to");
        hasta.setLocalizedLabel(SPANISH, "hasta");
        hasta.setLocalizedDescription(ENGLISH, "maximum number of times the step can be executed; "
            + "if not specified, the step could be executed any number of times, starting from the specified minimum");
        hasta.setLocalizedDescription(SPANISH, "máximo número de veces que se puede ejecutar el paso; "
            + "si no se especifica, el paso podrá ser ejecutado cualquier número de veces, a partir del mínimo especificado");
        hasta.setLocalizedRangeErrorMessage(ENGLISH, "maximum number of times the step can be executed out of range {1} : {2}");
        hasta.setLocalizedRangeErrorMessage(SPANISH, "máximo número de veces que se puede ejecutar el paso fuera del rango {1} : {2}");
        /**/
        desde.setLocalizedLabel(ENGLISH, "from");
        desde.setLocalizedLabel(SPANISH, "desde");
        desde.setLocalizedDescription(ENGLISH, "minimum number of times the step must be executed; "
            + "is a number from 0 to the value of \"up to\"; 0 means it is an optional step");
        desde.setLocalizedDescription(SPANISH, "mínimo número de veces que se debe ejecutar el paso; "
            + "es un número del 0 al valor de \"hasta\"; el 0 significa que es un paso opcional");
        desde.setLocalizedRangeErrorMessage(ENGLISH, "minimum number of times the step must be executed out of range {1} : {2}");
        desde.setLocalizedRangeErrorMessage(SPANISH, "mínimo número de veces que se debe ejecutar el paso fuera del rango {1} : {2}");
        /**/
        coleccion.setLocalizedLabel(ENGLISH, "collection");
        coleccion.setLocalizedLabel(SPANISH, "colección");
        coleccion.setLocalizedDescription(ENGLISH, "indicator that shows whether or not the variable is a collection of instances of the specified class");
        coleccion.setLocalizedDescription(SPANISH, "indicador que muestra si la variable es, o no, una colección de instancias de la clase especificada");
        /**/
        para.setLocalizedLabel(ENGLISH, "for");
        para.setLocalizedLabel(SPANISH, "para");
        para.setLocalizedDescription(ENGLISH, "variable that stores the instance(s) for which the step should be executed");
        para.setLocalizedDescription(SPANISH, "variable que almacena la(s) instancia(s) para la(s) que se debe ejecutar el paso");
        /**/
        variable.setLocalizedLabel(ENGLISH, "variable");
        variable.setLocalizedLabel(SPANISH, "variable");
        variable.setLocalizedDescription(ENGLISH, "variable where the result of the step execution is stored");
        variable.setLocalizedDescription(SPANISH, "variable donde se almacena el resultado de la ejecución del paso");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleLinks() {
        super.settleLinks();
    }

    Key key1;

    @Override
    protected void settleKeys() {
        super.settleKeys();
        /**/
        key1.setUnique(false);
        key1.newKeyField(rutina, numero);
        /**/
    }

    @Override
    protected void settleTabs() {
        super.settleTabs();
    }

    @Override
    protected void settleViews() {
        super.settleViews();
    }

    BooleanExpression assert0101, assert0102, assert0103;

    BooleanExpression assert0301, assert0401;

    Check check0101, check0102, check0103;

    Check check0201, check0301, check0401;

    @Override
    protected void settleExpressions() {
        super.settleExpressions();
        /**/
        assert0101 = funcion.esConstructor.or(funcion.tipoFuncion.isEqualTo(funcion.tipoFuncion.CONSULTA));
        assert0102 = not(funcion.esProgramatica).and(funcion.tipoFuncion.isNotEqualTo(funcion.tipoFuncion.ELIMINACION));
        assert0103 = subrutina.isNotEqualTo(rutina); // y subrutina no debe ejecutar rutina
        /**/
        assert0301 = para.rutina.isEqualTo(rutina) // y debe ser una variable establecida por un paso anterior de la misma rutina
            .and(para.coleccion.isFalse().or(hasta.isEqualTo(1)));
        /**/
        assert0401 = variable.rutina.isEqualTo(rutina)
            .and(variable.claseRecurso.isEqualTo(funcion.rango))
            .and(variable.coleccion.xnor(hasta.isNullOrGreaterThan(1)));
        /**/
        check0101 = funcion.isNull().xor(subrutina.isNull());
        check0102 = funcion.isNull().or(assert0102);
        check0103 = subrutina.isNull().or(assert0103);
        /**/
        check0201 = hasta.isNull().or(desde.isLessOrEqualTo(hasta));
        /**/
        check0301 = para.isNull().or(assert0301);
        /**/
        check0401 = variable.isNull().or(assert0401);
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of PasoRutina's expressions">
        /**/
        check0101.setLocalizedDescription(ENGLISH, "function and subroutine are mutually exclusive");
        check0101.setLocalizedDescription(SPANISH, "función y subrutina son mutuamente excluyentes");
        check0101.setLocalizedErrorMessage(ENGLISH, "function and subroutine are mutually exclusive");
        check0101.setLocalizedErrorMessage(SPANISH, "función y subrutina son mutuamente excluyentes");
        /**/
        check0102.setLocalizedDescription(ENGLISH, "function cannot be a delete function");
        check0102.setLocalizedDescription(SPANISH, "función no puede ser una función de eliminación");
        check0102.setLocalizedErrorMessage(ENGLISH, "function is a delete function");
        check0102.setLocalizedErrorMessage(SPANISH, "función es una función de eliminación");
        /**/
        check0103.setLocalizedDescription(ENGLISH, "routine and subroutine must be different and subroutine must not execute routine");
        check0103.setLocalizedDescription(SPANISH, "rutina y subrutina deben ser diferentes y subrutina no debe ejecutar rutina");
        check0103.setLocalizedErrorMessage(ENGLISH, "routine and subroutine are the same or subroutine executes routine");
        check0103.setLocalizedErrorMessage(SPANISH, "rutina y subrutina son iguales o subrutina ejecuta rutina");
        /**/
        check0201.setLocalizedDescription(ENGLISH, "\"from\" must be less than or equal to \"up to\"");
        check0201.setLocalizedDescription(SPANISH, "\"desde\" debe ser menor o igual que \"hasta\"");
        check0201.setLocalizedErrorMessage(ENGLISH, "\"from\" is greater than \"up to\"");
        check0201.setLocalizedErrorMessage(SPANISH, "\"desde\" es mayor que \"hasta\"");
        /**/
        check0301.setLocalizedDescription(ENGLISH, "\"for\" must be a variable set by a previous step in the same routine "
            + "and it cannot be a collection if the step can be executed more than once");
        check0301.setLocalizedDescription(SPANISH, "\"para\" debe ser una variable establecida por un paso anterior de la misma rutina "
            + "y no puede ser una colección si el paso se puede ejecutar mas de una vez");
        check0301.setLocalizedErrorMessage(ENGLISH, "\"for\" is not a variable set by a previous step in the same routine "
            + "or it is a collection and the step can be executed more than once");
        check0301.setLocalizedErrorMessage(SPANISH, "\"para\" no es una variable establecida por un paso anterior de la misma rutina"
            + "o es una colección y el paso se puede ejecutar mas de una vez");
        /**/
        check0401.setLocalizedDescription(ENGLISH, "\"variable\" must be a variable of the same class and cardinality of the function and step");
        check0401.setLocalizedDescription(SPANISH, "\"variable\" debe ser una variable de la misma clase y cardinalidad de la función y el paso");
        check0401.setLocalizedErrorMessage(ENGLISH, "\"variable\" is not a variable of the same class and cardinality of the function and step");
        check0401.setLocalizedErrorMessage(SPANISH, "\"variable\" no es una variable de la misma clase y cardinalidad de la función y el paso");
        /*
        check0402.setLocalizedDescription(ENGLISH, "\"variable\" must be a collection when the step can be executed more than one time");
        check0402.setLocalizedDescription(SPANISH, "\"variable\" debe ser una colección si el paso se puede ejecutar mas de una vez");
        check0402.setLocalizedErrorMessage(ENGLISH, "\"variable\" must be a collection when the step can be executed more than one time");
        check0402.setLocalizedErrorMessage(SPANISH, "\"variable\" debe ser una colección si el paso se puede ejecutar mas de una vez");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleFilters() {
        super.settleFilters();
        /**/
        filtro.setRenderingFilter(assert0101, true);
        filtro.setModifyingFilter(assert0101);
        filtro.setNullifyingFilter(not(assert0101));
        /**/
//      hasta.setRenderingFilter(assert0101, true);
        hasta.setModifyingFilter(assert0101);
//      hasta.setNullifyingFilter(not(assert0101));
        /**/
        variable.setRenderingFilter(assert0101, true);
        variable.setRequiringFilter(assert0101);
        variable.setModifyingFilter(assert0101);
        variable.setNullifyingFilter(not(assert0101));
        /**/
        funcion.setSearchQueryFilter(assert0102);
        subrutina.setSearchQueryFilter(assert0103);
        para.setSearchQueryFilter(assert0301);
        variable.setSearchQueryFilter(assert0401);
        /**/
        variable.addSearchValueFilterProperty(variable.rutina, rutina);
        variable.addSearchValueFilterProperty(variable.claseRecurso, funcion.rango);
        variable.addSearchValueFilterProperty(variable.coleccion, coleccion);
        /**/
    }

    @Override
    protected void settleOperations() {
        super.settleOperations();
    }

}
