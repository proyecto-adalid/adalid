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
import meta.entidad.comun.configuracion.basica.Funcion;
import meta.proyecto.base.ProyectoBase;

/**
 * @author Jorge Campins
 */
@EntityClass(independent = Kleenean.FALSE, resourceType = ResourceType.OPERATION, resourceGender = ResourceGender.FEMININE)
@EntitySelectOperation(enabled = Kleenean.TRUE)
@EntityInsertOperation(enabled = Kleenean.TRUE)
@EntityUpdateOperation(enabled = Kleenean.TRUE)
@EntityDeleteOperation(enabled = Kleenean.TRUE)
@EntityTableView(enabled = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.FALSE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.FALSE)
public class RolFuncion extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    @Deprecated
    private RolFuncion() {
        this(null, null);
    }

    public RolFuncion(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public LongProperty idRolFuncion;

    @VersionProperty
    public LongProperty versionRolFuncion;

    @ForeignKey(onDelete = OnDeleteAction.CASCADE, onUpdate = OnUpdateAction.CASCADE)
    @ManyToOne(navigability = Navigability.BIDIRECTIONAL, view = MasterDetailView.TABLE)
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(required = Kleenean.TRUE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    @Allocation(maxDepth = 1, maxRound = 0)
    public Rol idRol;

    @ForeignKey(onDelete = OnDeleteAction.CASCADE, onUpdate = OnUpdateAction.CASCADE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(required = Kleenean.TRUE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    @Allocation(maxDepth = 2, maxRound = 0)
    public Funcion idFuncion;

    @ForeignKey(onDelete = OnDeleteAction.CASCADE, onUpdate = OnUpdateAction.CASCADE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(required = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE, create = Kleenean.TRUE)
    @Allocation(maxDepth = 1, maxRound = 0)
    public ConjuntoSegmento idConjuntoSegmento;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(required = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE, create = Kleenean.TRUE)
    public BooleanProperty esAccesoPersonalizado;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(required = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE, create = Kleenean.TRUE)
    public BooleanProperty esTarea;

    protected Key uk_rol_funcion_0001;

    public Segment modificables;

//  protected Check checkAccesoPersonalizado;
//
    protected Check checkFuncionPersonalizada;

    protected Check checkFuncionSegmentada;

    protected Check checkTipoFuncionTarea;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        setDefaultLabel("asociación Rol/Función");
        setDefaultCollectionLabel("asociaciones Rol/Función");
//      setDefaultLabel(idRol, "función por rol");
//      setDefaultShortLabel(idRol, "función");
        setDefaultCollectionLabel(idRol, "funciones por rol");
        setDefaultCollectionShortLabel(idRol, "funciones");
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        esAccesoPersonalizado.setInitialValue(idFuncion.esPersonalizable);
        esAccesoPersonalizado.setDefaultValue(idFuncion.esPersonalizable);
        esTarea.setInitialValue(false);
        esTarea.setDefaultValue(idFuncion.numeroTipoFuncion.isEqualTo(idFuncion.numeroTipoFuncion.PROCESO).then(true).otherwise(false));
    }

    @Override
    protected void settleKeys() {
        super.settleKeys();
        uk_rol_funcion_0001.setUnique(true);
        uk_rol_funcion_0001.newKeyField(idRol, idFuncion);
    }

    @Override
    protected void settleExpressions() {
        super.settleExpressions();
        modificables = idRol.idRol.isGreaterOrEqualTo(10000L);
        modificables.setDefaultErrorMessage("el rol es un rol de configuración básica del sistema; "
            + "no se permite modificar ni eliminar sus funciones");
        setInsertFilter(idRol.modificables);
        setUpdateFilter(modificables);
        setDeleteFilter(modificables);
//      checkAccesoPersonalizado = esAccesoPersonalizado.isNotNull();
//      checkAccesoPersonalizado.setDefaultErrorMessage("la opción de acceso personalizado no tiene valor");
        checkFuncionPersonalizada = idFuncion.esPersonalizable.isFalse().implies(esAccesoPersonalizado.isNullOrFalse());
        checkFuncionPersonalizada.setDefaultErrorMessage("la función no permite acceso personalizado");
        checkFuncionSegmentada = idFuncion.esSegmentable.isFalse().implies(idConjuntoSegmento.isNull());
        checkFuncionSegmentada.setDefaultErrorMessage("la función no permite acceso segmentado");
        checkTipoFuncionTarea = idFuncion.numeroTipoFuncion.isNullOrNotEqualTo(idFuncion.numeroTipoFuncion.PROCESO).implies(esTarea.isFalse());
        checkTipoFuncionTarea.setDefaultErrorMessage("solo los procesos de negocio pueden ser tareas");
    }

}
