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
import adalid.core.parameters.*;
import adalid.core.properties.*;
import java.lang.reflect.Field;
import meta.entidad.comun.configuracion.basica.ClaseRecurso;

/**
 * @author Jorge Campins
 */
@EntityClass(independent = Kleenean.TRUE, resourceType = ResourceType.OPERATION, resourceGender = ResourceGender.MASCULINE)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE, onload = SelectOnloadOption.EXECUTE)
@EntityInsertOperation(enabled = Kleenean.TRUE)
@EntityUpdateOperation(enabled = Kleenean.TRUE)
@EntityDeleteOperation(enabled = Kleenean.TRUE)
@EntityTableView(enabled = Kleenean.TRUE, responsiveMode = TableResponsiveMode.PRIORITY, inserts = Kleenean.TRUE, updates = Kleenean.TRUE, quickFilter = Kleenean.TRUE)
@EntityDetailView(enabled = Kleenean.TRUE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.TRUE)
public class ConjuntoSegmento extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public ConjuntoSegmento(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public LongProperty id;

    @VersionProperty
    public LongProperty version;

    @BusinessKey
    @StringField(maxLength = 200)
    @PropertyField(overlay = Kleenean.TRUE)
    public StringProperty codigoConjuntoSegmento;

    @NameProperty
    @StringField(maxLength = 200)
    public StringProperty nombreConjuntoSegmento;

    @DescriptionProperty
    @StringField(maxLength = 0)
    public StringProperty descripcionConjuntoSegmento;

//  20171213: remove foreign-key referring to ClaseRecurso
//  @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(responsivePriority = 5, heading = Kleenean.TRUE, overlay = Kleenean.TRUE, update = Kleenean.FALSE)
    @EntityReferenceSearch(searchType = SearchType.LIST, listStyle = ListStyle.NAME)
    public ClaseRecurso claseRecurso;

    @ForeignKey(onDelete = OnDeleteAction.CASCADE, onUpdate = OnUpdateAction.CASCADE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(hidden = Kleenean.TRUE)
    public Usuario usuarioSupervisor;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(create = Kleenean.TRUE, overlay = Kleenean.TRUE)
    public ClaseFabricador claseFabricador;

    @PropertyField(hidden = Kleenean.TRUE, defaultCondition = DefaultCondition.UNCONDITIONALLY, defaultCheckpoint = Checkpoint.USER_INTERFACE)
    @StringField(maxLength = 200, regex = "([a-zA-Z_$][a-zA-Z\\d_$]*\\.)*[a-zA-Z_$][a-zA-Z\\d_$]*", validator = ClaseFabricador.FCSS_VALIDATOR)
    public StringProperty nombreClaseFabricador;

    @BooleanField(displayType = BooleanDisplayType.TOGGLE)
    @PropertyField(responsivePriority = 6, create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.TRUE, overlay = Kleenean.TRUE)
    public BooleanProperty esConjuntoEspecial;

    @ColumnField(calculable = Kleenean.TRUE)
    @BooleanField(displayType = BooleanDisplayType.TOGGLE)
    @PropertyField(overlay = Kleenean.TRUE)
    public BooleanProperty definicionExplicita;

    @SegmentProperty
    @EntityReferenceSearch(searchType = SearchType.LIST, listStyle = ListStyle.CHARACTER_KEY_AND_NAME, displayMode = DisplayMode.WRITING)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(view = MasterDetailView.TABLE_AND_DETAIL)
    @PropertyField(responsivePriority = 6, create = Kleenean.TRUE, update = Kleenean.TRUE, required = Kleenean.TRUE, table = Kleenean.TRUE, report = Kleenean.TRUE, heading = Kleenean.TRUE, overlay = Kleenean.TRUE)
    public GrupoUsuario grupo;

    /*
    @EntityCollectionField()
    @OneToMany(targetEntity = ElementoSegmento.class, mappedBy = "conjuntoSegmento")
    public EntityCollection elementos;

    /**/
    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of ConjuntoSegmento's attributes">
        setLocalizedLabel(ENGLISH, "segment set");
        setLocalizedLabel(SPANISH, "conjunto de segmentos");
        setLocalizedShortLabel(ENGLISH, "segment set");
        setLocalizedShortLabel(SPANISH, "conjunto");
        setLocalizedCollectionLabel(ENGLISH, "Segment Sets");
        setLocalizedCollectionLabel(SPANISH, "Conjuntos de Segmentos");
        setLocalizedCollectionShortLabel(ENGLISH, "Segment Sets");
        setLocalizedCollectionShortLabel(SPANISH, "Conjuntos");
        /**/
        setLocalizedDescription(ENGLISH, "Each instance of " + b("Segment Sets") + " represents a "
            + "set of instances of a resource class, which typically corresponds to a set of rows of a table in the database."
            + "");
        setLocalizedDescription(SPANISH, "Cada instancia de " + b("Conjuntos de Segmentos") + " representa un "
            + "conjunto de instancias de una clase de recursos, lo cual normalmente corresponde a un conjunto de filas de una tabla en la base de datos."
            + "");
        /**/
        setLocalizedShortDescription(ENGLISH, "set of instances of a resource class, which typically corresponds to a set of rows of a table in the database");
        setLocalizedShortDescription(SPANISH, "conjunto de instancias de una clase de recursos, lo cual normalmente corresponde a un conjunto de filas de una tabla en la base de datos");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        /**/
        setOrderBy(codigoConjuntoSegmento);
        /**/
        nombreClaseFabricador.setInitialValue(claseFabricador.nombreClaseFabricador);
        nombreClaseFabricador.setDefaultValue(claseFabricador.nombreClaseFabricador);
        /**/
        esConjuntoEspecial.setInitialValue(false);
        esConjuntoEspecial.setDefaultValue(false);
        /**/
        BooleanExpression elemental = /*esConjuntoEspecial.isFalse().and*/ (claseFabricador.isNull().or(claseFabricador.elemental));
        definicionExplicita.setCalculableValueExpression(elemental);
        /**/
//      grupo.setInitialValue(esConjuntoEspecial.then(grupo.USUARIOS_ESPECIALES).otherwise(grupo.USUARIOS_ORDINARIOS));
//      grupo.setDefaultValue(esConjuntoEspecial.then(grupo.USUARIOS_ESPECIALES).otherwise(grupo.USUARIOS_ORDINARIOS));
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of ConjuntoSegmento's properties">
        /**/
        codigoConjuntoSegmento.setLocalizedLabel(ENGLISH, "segment set code");
        codigoConjuntoSegmento.setLocalizedLabel(SPANISH, "código del conjunto de segmentos");
        codigoConjuntoSegmento.setLocalizedShortLabel(ENGLISH, "code");
        codigoConjuntoSegmento.setLocalizedShortLabel(SPANISH, "código");
        /**/
        nombreConjuntoSegmento.setLocalizedLabel(ENGLISH, "segment set name");
        nombreConjuntoSegmento.setLocalizedLabel(SPANISH, "nombre del conjunto de segmentos");
        nombreConjuntoSegmento.setLocalizedShortLabel(ENGLISH, "name");
        nombreConjuntoSegmento.setLocalizedShortLabel(SPANISH, "nombre");
        /**/
        descripcionConjuntoSegmento.setLocalizedLabel(ENGLISH, "segment set description");
        descripcionConjuntoSegmento.setLocalizedLabel(SPANISH, "descripción del conjunto de segmentos");
        descripcionConjuntoSegmento.setLocalizedShortLabel(ENGLISH, "description");
        descripcionConjuntoSegmento.setLocalizedShortLabel(SPANISH, "descripción");
        /**/
        claseRecurso.setLocalizedLabel(ENGLISH, "resource class");
        claseRecurso.setLocalizedLabel(SPANISH, "clase de recurso");
        claseRecurso.setLocalizedShortLabel(ENGLISH, "class");
        claseRecurso.setLocalizedShortLabel(SPANISH, "clase");
        /**/
        usuarioSupervisor.setLocalizedLabel(ENGLISH, "supervisor");
        usuarioSupervisor.setLocalizedLabel(SPANISH, "usuario supervisor");
        /**/
        claseFabricador.setLocalizedDescription(ENGLISH, "Java class used to determine the authorized segments; "
            + "the class must implement interface FabricadorConjuntoSegmentos");
        claseFabricador.setLocalizedDescription(SPANISH, "clase Java utilizada para determinar los segmentos autorizados; "
            + "la clase debe implementar la interfaz FabricadorConjuntoSegmentos");
        claseFabricador.setLocalizedLabel(ENGLISH, "factory class");
        claseFabricador.setLocalizedLabel(SPANISH, "clase de fabricador");
        claseFabricador.setLocalizedShortLabel(ENGLISH, "factory");
        claseFabricador.setLocalizedShortLabel(SPANISH, "fabricador");
        /**/
        nombreClaseFabricador.setLocalizedDescription(ENGLISH, "name of the Java class used to determine the authorized segments; "
            + "the class must implement interface FabricadorConjuntoSegmentos");
        nombreClaseFabricador.setLocalizedDescription(SPANISH, "nombre de la clase Java utilizada para determinar los segmentos autorizados; "
            + "la clase debe implementar la interfaz FabricadorConjuntoSegmentos");
        nombreClaseFabricador.setLocalizedLabel(ENGLISH, "factory class name");
        nombreClaseFabricador.setLocalizedLabel(SPANISH, "nombre clase fabricador");
        /**/
        nombreClaseFabricador.setLocalizedRegexErrorMessage(ENGLISH, "factory class does not meet the required pattern; "
            + "it must be a valid fully qualified Java class name");
        nombreClaseFabricador.setLocalizedRegexErrorMessage(SPANISH, "clase fabricador no cumple con el patrón requerido; "
            + "debe ser un nombre completo y válido de clase Java");
        /**/
        esConjuntoEspecial.setLocalizedDescription(ENGLISH, "a special set can be used as defined and can be copied, "
            + "but cannot be modified or deleted; copies can be modified, deleted, have elements added to them, etc.");
        esConjuntoEspecial.setLocalizedDescription(SPANISH, "un conjunto especial puede ser utilizado tal como está definido y puede ser copiado, "
            + "pero no puede ser modificado ni eliminado; a las copias se les puede modificar, eliminar, agregar elementos, etc.");
        esConjuntoEspecial.setLocalizedLabel(ENGLISH, "special set");
        esConjuntoEspecial.setLocalizedLabel(SPANISH, "conjunto especial");
        esConjuntoEspecial.setLocalizedShortLabel(ENGLISH, "special");
        esConjuntoEspecial.setLocalizedShortLabel(SPANISH, "especial");
        /**/
        definicionExplicita.setLocalizedDescription(ENGLISH, "the set requires the explicit definition of its elements; "
            + "all sets that do not use a factory class, and some that do, require explicit definition of their elements.");
        definicionExplicita.setLocalizedDescription(SPANISH, "el conjunto requiere la definición explicita de sus elementos; "
            + "todos los conjuntos que no utilizan un fabricador, y algunos que sí lo hacen, requieren la definición explicita de sus elementos");
        definicionExplicita.setLocalizedLabel(ENGLISH, "explicit definition");
        definicionExplicita.setLocalizedLabel(SPANISH, "definición explícita");
        definicionExplicita.setLocalizedShortLabel(ENGLISH, "explicit");
        definicionExplicita.setLocalizedShortLabel(SPANISH, "explícito");
        /**/
        grupo.setLocalizedLabel(ENGLISH, "user group");
        grupo.setLocalizedLabel(SPANISH, "grupo de usuarios");
        grupo.setLocalizedShortLabel(ENGLISH, "group");
        grupo.setLocalizedShortLabel(SPANISH, "grupo");
        grupo.setLocalizedDescription(ENGLISH, "group to which this set belongs");
        grupo.setLocalizedDescription(SPANISH, "grupo al que pertenece este conjunto");
        grupo.setLocalizedTooltip(ENGLISH, "code of the group to which this set belongs");
        grupo.setLocalizedTooltip(SPANISH, "código del grupo al que pertenece este conjunto");
        /**/
        // </editor-fold>
        /**/
    }

    @Override
    protected void settleCollections() {
        super.settleCollections();
        /*
        BooleanExpression elemental = esConjuntoEspecial.isFalse().and(claseFabricador.isNull().or(claseFabricador.elemental));
        EntityCollectionAggregate elementos_count = elementos.addCount(elemental.then(1).otherwise(0));
        elementos.setCascadeType(CascadeType.PERSIST, CascadeType.MERGE);
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of Usuario's collections">
        /*
        elementos.setLocalizedLabel(ENGLISH, "elements of the set");
        elementos.setLocalizedLabel(SPANISH, "elementos del conjunto");
        elementos.setLocalizedShortLabel(ENGLISH, "elements");
        elementos.setLocalizedShortLabel(SPANISH, "elementos");
        elementos.setLocalizedDescription(ENGLISH, "collection of elements of the set");
        elementos.setLocalizedDescription(SPANISH, "colección de elementos del conjunto");
        /*
        elementos_count.setLocalizedMinimumValueTag(ENGLISH, "the set must contain at least one element");
        elementos_count.setLocalizedMinimumValueTag(SPANISH, "el conjunto debe contener al menos un elemento");
        /**/
        // </editor-fold>
    }

    protected Segment modificables;

    protected Segment ordinarios;

    protected Check check101;

    protected Check check201;

    protected BooleanExpression claim201;

    @Override
    protected void settleExpressions() {
        super.settleExpressions();
        /**/
        modificables = not(esConjuntoEspecial);
        /**/
        ordinarios = modificables.and(claseFabricador.isNull().or(claseFabricador.elemental));
        /**/
        check101 = claseRecurso.esClaseRecursoSegmento.isTrue();
        /**/
        claim201 = claseFabricador.codigoClaseRecurso.isEqualTo(claseRecurso.codigoClaseRecurso);
        check201 = claseFabricador.isNotNull().implies(claim201);
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of ConjuntoSegmento's expressions">
        /**/
        modificables.setLocalizedDescription(ENGLISH, "the set is not a basic configuration set");
        modificables.setLocalizedDescription(SPANISH, "el conjunto no es un conjunto de configuración básica del sistema");
        modificables.setLocalizedErrorMessage(ENGLISH, "the set is a basic configuration set; "
            + "it can't be modified or deleted");
        modificables.setLocalizedErrorMessage(SPANISH, "el conjunto es un conjunto de configuración básica del sistema; "
            + "no se permite modificarlo ni eliminarlo");
        /**/
        ordinarios.setLocalizedDescription(ENGLISH, "the set is not a basic configuration set "
            + "and its elements are explicitly specified");
        ordinarios.setLocalizedDescription(SPANISH, "el conjunto no es un conjunto de configuración básica del sistema "
            + "y sus elementos son especificados explícitamente");
        ordinarios.setLocalizedErrorMessage(ENGLISH, "the set is a basic configuration set "
            + "or its elements are determined by a factory class that does not uses elements; "
            + "it cannot be modified or deleted, and its elements cannot be explicitly specified");
        ordinarios.setLocalizedErrorMessage(SPANISH, "el conjunto es un conjunto de configuración básica del sistema "
            + "o sus elementos son determinados por una clase de fabricador que no usa elementos; "
            + "no se permite modificarlo ni eliminarlo, y sus elementos no pueden ser especificados explícitamente");
        /**/
        check101.setLocalizedLabel(ENGLISH, "verify resource class");
        check101.setLocalizedLabel(SPANISH, "chequear clase de recurso");
        check101.setLocalizedDescription(ENGLISH, "the resource class must be a class used to segment");
        check101.setLocalizedDescription(SPANISH, "la clase de recurso debe ser una clase utilizada para segmentar");
        check101.setLocalizedErrorMessage(ENGLISH, "the resource class is not a class used to segment");
        check101.setLocalizedErrorMessage(SPANISH, "la clase de recurso no es una clase utilizada para segmentar");
        /**/
        check201.setLocalizedLabel(ENGLISH, "verify factory class");
        check201.setLocalizedLabel(SPANISH, "chequear clase de fabricador");
        check201.setLocalizedDescription(ENGLISH, "the factory class must be a class used to segment the resource class");
        check201.setLocalizedDescription(SPANISH, "la clase de fabricador debe ser una clase utilizada para segmentar la clase de recurso");
        check201.setLocalizedErrorMessage(ENGLISH, "the factory class is not a class used to segment the resource class");
        check201.setLocalizedErrorMessage(SPANISH, "la clase de fabricador no es una clase utilizada para segmentar la clase de recurso");
        /**/
        claim201.setLocalizedLabel(ENGLISH, "verify factory class");
        claim201.setLocalizedLabel(SPANISH, "chequear clase de fabricador");
        claim201.setLocalizedDescription(ENGLISH, "the factory class must be a class used to segment the resource class");
        claim201.setLocalizedDescription(SPANISH, "la clase de fabricador debe ser una clase utilizada para segmentar la clase de recurso");
        claim201.setLocalizedErrorMessage(ENGLISH, "the factory class is not a class used to segment the resource class");
        claim201.setLocalizedErrorMessage(SPANISH, "la clase de fabricador no es una clase utilizada para segmentar la clase de recurso");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleFilters() {
        super.settleFilters();
        /**/
        setUpdateFilter(modificables);
        setDeleteFilter(modificables);
        /**/
        claseRecurso.setSearchQueryFilter(check101);
        claseFabricador.setSearchQueryFilter(claim201);
        /*
        elementos.setRenderingFilter(ordinarios);
        /**/
    }

    protected Copiar copiar;

    @ProcessOperationClass
//  @OperationClass(dialogSize = 100)
    @ConstructionOperationClass(type = ConjuntoSegmento.class, onsuccess = OnConstructionOperationSuccess.DISPLAY_NEW_INSTANCE)
    public class Copiar extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of Copiar's attributes">
            /**/
            setLocalizedLabel(ENGLISH, "copy");
            setLocalizedLabel(SPANISH, "copiar");
            /**/
            setLocalizedDescription(ENGLISH, "copy a segment set with another code and name; "
                + "the copy includes the elements of the original segment set");
            setLocalizedDescription(SPANISH, "copiar un conjunto de segmentos con otro código y nombre; "
                + "la copia incluye los elementos del conjunto de segmentos original");
            /**/
            setLocalizedSuccessMessage(ENGLISH, "the segment set was successfully copied");
            setLocalizedSuccessMessage(SPANISH, "el conjunto de segmentos fue copiado con éxito");
            /**/
            // </editor-fold>
        }

        @InstanceReference
        protected ConjuntoSegmento conjunto;

        @ParameterField(required = Kleenean.TRUE)
        @StringField(maxLength = 200)
        protected StringParameter codigo;

        @ParameterField(required = Kleenean.FALSE)
        @StringField(maxLength = 200)
        protected StringParameter nombre;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            // <editor-fold defaultstate="collapsed" desc="localization of Copiar's parameters">
            conjunto.setLocalizedLabel(ENGLISH, "segment set");
            conjunto.setLocalizedLabel(SPANISH, "conjunto de segmentos");
            conjunto.setLocalizedDescription(ENGLISH, "Code of the segment set you want to copy; "
                + "it is a required field and has no default value");
            conjunto.setLocalizedDescription(SPANISH, "Código del conjunto de segmentos que desea copiar; "
                + "es un dato obligatorio y no tiene valor por omisión");
            /**/
            codigo.setLocalizedLabel(ENGLISH, "code");
            codigo.setLocalizedLabel(SPANISH, "código");
            codigo.setLocalizedDescription(ENGLISH, "Code of the new segment set produced when copying; "
                + "it is a required field and has no default value");
            codigo.setLocalizedDescription(SPANISH, "Código del nuevo conjunto de segmentos que produce la copia; "
                + "es un dato obligatorio y no tiene valor por omisión");
            /**/
            nombre.setLocalizedLabel(ENGLISH, "name");
            nombre.setLocalizedLabel(SPANISH, "nombre");
            nombre.setLocalizedDescription(ENGLISH, "Name of the new segment set produced when copying; "
                + "it is an optional field; by default, the name of the original segment set is used");
            nombre.setLocalizedDescription(SPANISH, "Nombre del nuevo conjunto de segmentos que produce la copia; "
                + "es un dato opcional; por omisión se utiliza el nombre del conjunto de segmentos original");
            // </editor-fold>
        }

    }

}
