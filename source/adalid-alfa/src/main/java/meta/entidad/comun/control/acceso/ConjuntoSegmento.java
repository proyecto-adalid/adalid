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
@EntityTableView(enabled = Kleenean.TRUE, inserts = Kleenean.TRUE, updates = Kleenean.TRUE)
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
    @PropertyField(heading = Kleenean.TRUE)
    public ClaseRecurso claseRecurso;

    @ForeignKey(onDelete = OnDeleteAction.CASCADE, onUpdate = OnUpdateAction.CASCADE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(hidden = Kleenean.TRUE)
    public Usuario usuarioSupervisor;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(create = Kleenean.TRUE)
    public ClaseFabricador claseFabricador;

    @PropertyField(hidden = Kleenean.TRUE, defaultCondition = DefaultCondition.UNCONDITIONALLY, defaultCheckpoint = Checkpoint.USER_INTERFACE)
    @StringField(maxLength = 200, regex = "([a-zA-Z_$][a-zA-Z\\d_$]*\\.)*[a-zA-Z_$][a-zA-Z\\d_$]*", validator = ClaseFabricador.FCSS_VALIDATOR)
    public StringProperty nombreClaseFabricador;

    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.TRUE)
    public BooleanProperty esConjuntoEspecial;

    @SegmentProperty
    @EntityReferenceSearch(searchType = SearchType.LIST, listStyle = ListStyle.CHARACTER_KEY_AND_NAME, displayMode = DisplayMode.WRITING)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(view = MasterDetailView.TABLE_AND_DETAIL)
    @PropertyField(create = Kleenean.TRUE, update = Kleenean.TRUE, required = Kleenean.TRUE, table = Kleenean.TRUE, report = Kleenean.TRUE, overlay = Kleenean.TRUE)
    public GrupoUsuario grupo;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setOrderBy(codigoConjuntoSegmento);
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of ConjuntoSegmento's attributes">
        setLocalizedLabel(ENGLISH, "segment set");
        setLocalizedLabel(SPANISH, "conjunto de segmentos");
        setLocalizedShortLabel(ENGLISH, "segment set");
        setLocalizedShortLabel(SPANISH, "conjunto");
        setLocalizedCollectionLabel(ENGLISH, "Segment Sets");
        setLocalizedCollectionLabel(SPANISH, "Conjuntos de Segmentos");
        setLocalizedCollectionShortLabel(ENGLISH, "Segment Sets");
        setLocalizedCollectionShortLabel(SPANISH, "Conjuntos");
        setLocalizedDescription(ENGLISH, "segment set of a resource class; typically represents a set of rows of a table in the database");
        setLocalizedDescription(SPANISH, "conjunto de segmentos de una clase de recursos; normalmente representa un conjunto de filas de una tabla en la base de datos");
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        /**/
        nombreClaseFabricador.setInitialValue(claseFabricador.nombreClaseFabricador);
        nombreClaseFabricador.setDefaultValue(claseFabricador.nombreClaseFabricador);
        /**/
        esConjuntoEspecial.setInitialValue(false);
        esConjuntoEspecial.setDefaultValue(false);
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
        claseFabricador.setLocalizedLabel(SPANISH, "clase fabricador");
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
        esConjuntoEspecial.setLocalizedLabel(ENGLISH, "special set");
        esConjuntoEspecial.setLocalizedLabel(SPANISH, "conjunto especial");
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
    }

    protected Segment modificables;

    protected Segment ordinarios;

    protected Check check101;

    protected Check check201;

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
        check201 = claseFabricador.isNotNull().implies(claseFabricador.codigoClaseRecurso.isEqualTo(claseRecurso.codigoClaseRecurso));
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
        // </editor-fold>
    }

    @Override
    protected void settleFilters() {
        super.settleFilters();
        setUpdateFilter(modificables);
        setDeleteFilter(modificables);
        claseRecurso.setSearchQueryFilter(check101);
        claseFabricador.setSearchQueryFilter(claseFabricador.codigoClaseRecurso.isEqualTo(claseRecurso.codigoClaseRecurso));
    }

    protected Copiar copiar;

    @ProcessOperationClass
    @ConstructionOperationClass(type = ConjuntoSegmento.class)
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
