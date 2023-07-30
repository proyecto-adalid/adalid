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
import java.lang.reflect.Field;
import meta.entidad.comun.configuracion.basica.ext.Funcion;

/**
 * @author Jorge Campins
 */
@EntityClass(independent = Kleenean.FALSE, resourceType = ResourceType.OPERATION, resourceGender = ResourceGender.FEMININE)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE)
@EntityInsertOperation(enabled = Kleenean.TRUE)
@EntityUpdateOperation(enabled = Kleenean.TRUE)
@EntityDeleteOperation(enabled = Kleenean.TRUE)
@EntityTableView(enabled = Kleenean.TRUE)
@EntityDetailView(enabled = Kleenean.FALSE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
public class RolFuncion extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public RolFuncion(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Override
    protected void addAllocationStrings() {
        super.addAllocationStrings();
        super.addAllocationStrings(
            "rol.grupo",
            "conjuntoSegmento.claseRecurso",
            "funcion.dominio.claseRecurso.claseRecursoSegmento",
            "funcion.tipoFuncion"
        );
    }

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of RolFuncion's attributes">
        setLocalizedLabel(ENGLISH, "role/function association");
        setLocalizedLabel(SPANISH, "asociación Rol/Función");
        setLocalizedCollectionLabel(ENGLISH, "Role/Function Associations");
        setLocalizedCollectionLabel(SPANISH, "Asociaciones Rol/Función");
        /**/
        setLocalizedCollectionLabel(ENGLISH, rol, "Functions by Role");
        setLocalizedCollectionLabel(SPANISH, rol, "Funciones por Rol");
        setLocalizedCollectionShortLabel(ENGLISH, rol, "Functions");
        setLocalizedCollectionShortLabel(SPANISH, rol, "Funciones");
        /**/
        setLocalizedDescription(ENGLISH, "Each instance of " + b("Role/Function Associations") + " represents a "
            + "function associated with an application role."
            + "");
        setLocalizedDescription(SPANISH, "Cada instancia de " + b("Asociaciones Rol/Función") + " representa una "
            + "función asociada con un rol de la aplicación."
            + "");
        /**/
        setLocalizedShortDescription(ENGLISH, "function associated with an application role");
        setLocalizedShortDescription(SPANISH, "función asociada con un rol de la aplicación");
        /**/
        // </editor-fold>
    }

    @PrimaryKey
    public LongProperty id;

    @VersionProperty
    public LongProperty version;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE) // CASCADE -> Mutatis mutandis
    @ManyToOne(navigability = Navigability.BIDIRECTIONAL, view = MasterDetailView.TABLE, viewSequence = 20)
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(required = Kleenean.TRUE, table = Kleenean.TRUE, report = Kleenean.TRUE, heading = Kleenean.TRUE, overlay = Kleenean.TRUE)
    public Rol rol;

//  20171213: remove foreign-key referring to Funcion
//  @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE, quickAdding = QuickAddingFilter.MISSING)
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(required = Kleenean.TRUE, table = Kleenean.TRUE, report = Kleenean.TRUE, heading = Kleenean.TRUE, overlay = Kleenean.TRUE, access = PropertyAccess.RESTRICTED_WRITING)
    public Funcion funcion;

    @ForeignKey(onDelete = OnDeleteAction.CASCADE, onUpdate = OnUpdateAction.CASCADE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(required = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE, create = Kleenean.TRUE, access = PropertyAccess.RESTRICTED_WRITING)
    public ConjuntoSegmento conjuntoSegmento;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(required = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE, create = Kleenean.TRUE, access = PropertyAccess.RESTRICTED_WRITING, defaultCheckpoint = Checkpoint.USER_INTERFACE)
    public BooleanProperty esAccesoPersonalizado;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(required = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE, create = Kleenean.TRUE)
    public BooleanProperty esTarea;

    @Override
    protected void settleProperties() {
        super.settleProperties();
        esAccesoPersonalizado.setInitialValue(funcion.esPersonalizable);
        esAccesoPersonalizado.setDefaultValue(funcion.esPersonalizable);
        esTarea.setInitialValue(false);
        esTarea.setDefaultValue(false);
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of RolFuncion's properties">
        rol.setLocalizedLabel(ENGLISH, "role");
        rol.setLocalizedLabel(SPANISH, "rol");
        /**/
        funcion.setLocalizedLabel(ENGLISH, "function");
        funcion.setLocalizedLabel(SPANISH, "función");
        /**/
        conjuntoSegmento.setLocalizedLabel(ENGLISH, "segment set");
        conjuntoSegmento.setLocalizedLabel(SPANISH, "conjunto de segmentos");
        conjuntoSegmento.setLocalizedShortLabel(ENGLISH, "set");
        conjuntoSegmento.setLocalizedShortLabel(SPANISH, "conjunto");
        /**/
        esAccesoPersonalizado.setLocalizedLabel(ENGLISH, "personalized access");
        esAccesoPersonalizado.setLocalizedLabel(SPANISH, "acceso personalizado");
        /**/
        esTarea.setLocalizedLabel(ENGLISH, "task");
        esTarea.setLocalizedLabel(SPANISH, "tarea");
        // </editor-fold>
    }

    @Override
    protected void settleLinks() {
        super.settleLinks();
        linkForeignSegmentProperty(rol.grupo);
    }

    protected Key ix_rol_funcion_0001;

    @Override
    protected void settleKeys() {
        super.settleKeys();
        ix_rol_funcion_0001.setUnique(false);
        ix_rol_funcion_0001.newKeyField(rol, funcion);
    }

    protected Segment modificables;

    protected Segment conjuntables;

//  protected Check checkAccesoPersonalizado;
//
//  protected Check checkAccesoExtremo;
//
    protected Check checkAccesoUsuario;

    protected Check checkFuncionPersonalizada;

    protected Check checkFuncionSegmentada;

    protected Check checkConjuntoSegmentos;

    protected Check checkTipoFuncionTarea;

    @Override
    protected void settleExpressions() {
        super.settleExpressions();
        modificables = rol.id.isGreaterOrEqualTo(10000L);
        conjuntables = conjuntoSegmento.claseRecurso.isEqualTo(funcion.dominio.claseRecurso.claseRecursoSegmento);
        checkAccesoUsuario = funcion.esProgramatica.isFalse();
        checkFuncionPersonalizada = funcion.esPersonalizable.isFalse().implies(esAccesoPersonalizado.isNotTrue());
        checkFuncionSegmentada = funcion.esSegmentable.isFalse().implies(conjuntoSegmento.isNull());
        checkConjuntoSegmentos = conjuntoSegmento.isNull().or(conjuntables);
        checkTipoFuncionTarea = funcion.tipoFuncion.isNullOrNotEqualTo(funcion.tipoFuncion.PROCESO).implies(esTarea.isNotTrue());
        // <editor-fold defaultstate="collapsed" desc="localization of RolFuncion's expressions">
        modificables.setLocalizedDescription(ENGLISH, "the role is not a basic configuration role");
        modificables.setLocalizedDescription(SPANISH, "el rol no es un rol de configuración básica del sistema");
        modificables.setLocalizedErrorMessage(ENGLISH, "the role is a basic configuration role; "
            + "its functions can't be modified or deleted");
        modificables.setLocalizedErrorMessage(SPANISH, "el rol es un rol de configuración básica del sistema; "
            + "no se permite modificar ni eliminar sus funciones");
        /**/
        conjuntables.setLocalizedDescription(ENGLISH, "the segment set is of the same resource class as the function");
        conjuntables.setLocalizedDescription(SPANISH, "el conjunto de segmentos es de la misma clase de recursos que la función");
        conjuntables.setLocalizedErrorMessage(ENGLISH, "the segment set is not of the same resource class as the function");
        conjuntables.setLocalizedErrorMessage(SPANISH, "el conjunto de segmentos no es de la misma clase de recursos que la función");
        /**/
        checkAccesoUsuario.setLocalizedDescription(ENGLISH, "access can only be granted to non-programmatic functions");
        checkAccesoUsuario.setLocalizedDescription(SPANISH, "solo se puede otorgar acceso a funciones no programáticas");
        checkAccesoUsuario.setLocalizedErrorMessage(ENGLISH, "the function is programmatic");
        checkAccesoUsuario.setLocalizedErrorMessage(SPANISH, "la función es programática");
        /**/
        checkFuncionPersonalizada.setLocalizedDescription(ENGLISH, "personalized access can only be granted to personalizable functions");
        checkFuncionPersonalizada.setLocalizedDescription(SPANISH, "solo se puede otorgar acceso personalizado a funciones personalizables");
        checkFuncionPersonalizada.setLocalizedErrorMessage(ENGLISH, "the function doesn't allow personalized access");
        checkFuncionPersonalizada.setLocalizedErrorMessage(SPANISH, "la función no permite acceso personalizado");
        /**/
        checkFuncionSegmentada.setLocalizedDescription(ENGLISH, "segmented access can only be granted to segmentable functions");
        checkFuncionSegmentada.setLocalizedDescription(SPANISH, "solo se puede otorgar acceso segmentado a funciones segmentables");
        checkFuncionSegmentada.setLocalizedErrorMessage(ENGLISH, "the function doesn't allow segmented access");
        checkFuncionSegmentada.setLocalizedErrorMessage(SPANISH, "la función no permite acceso segmentado");
        /**/
        checkConjuntoSegmentos.setLocalizedDescription(ENGLISH, "access can only be granted to a segment set of the same resource class as the function");
        checkConjuntoSegmentos.setLocalizedDescription(SPANISH, "solo se puede otorgar acceso a un conjunto de segmentos de la misma clase de recursos que la función");
        checkConjuntoSegmentos.setLocalizedErrorMessage(ENGLISH, "the segment set is not of the same resource class as the function");
        checkConjuntoSegmentos.setLocalizedErrorMessage(SPANISH, "el conjunto de segmentos no es de la misma clase de recursos que la función");
        /**/
        checkTipoFuncionTarea.setLocalizedDescription(ENGLISH, "only business processes can be tasks");
        checkTipoFuncionTarea.setLocalizedDescription(SPANISH, "solo los procesos de negocio pueden ser tareas");
        checkTipoFuncionTarea.setLocalizedErrorMessage(ENGLISH, "only business processes can be tasks");
        checkTipoFuncionTarea.setLocalizedErrorMessage(SPANISH, "solo los procesos de negocio pueden ser tareas");
        // </editor-fold>
    }

    @Override
    protected void settleFilters() {
        super.settleFilters();
        /**/
        setMasterDetailFilter(rol.rolesOrdinarios);
        setInsertFilter(rol.modificables);
        setUpdateFilter(modificables);
        setDeleteFilter(modificables);
        /**/
//      idFuncion.setSearchQueryFilter(checkAccesoExtremo);
        funcion.setSearchQueryFilter(checkAccesoUsuario);
        conjuntoSegmento.setSearchQueryFilter(conjuntables);
        conjuntoSegmento.setModifyingFilter(funcion.dominio.claseRecurso.claseRecursoSegmento.isNotNull());
        conjuntoSegmento.setNullifyingFilter(funcion.dominio.claseRecurso.claseRecursoSegmento.isNull());
        esAccesoPersonalizado.setModifyingFilter(funcion.esPersonalizable.isTrue());
        esAccesoPersonalizado.setNullifyingFilter(funcion.esPersonalizable.isNotTrue());
        esTarea.setModifyingFilter(funcion.tipoFuncion.isEqualTo(funcion.tipoFuncion.PROCESO));
        esTarea.setNullifyingFilter(funcion.tipoFuncion.isNullOrNotEqualTo(funcion.tipoFuncion.PROCESO));
    }

}
