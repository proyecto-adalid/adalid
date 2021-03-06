/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.entidad.comun.control.acceso;

import adalid.core.AbstractPersistentEntity;
import adalid.core.Instance;
import adalid.core.ProcessOperation;
import adalid.core.annotations.Allocation;
import adalid.core.annotations.BusinessKey;
import adalid.core.annotations.ColumnField;
import adalid.core.annotations.DescriptionProperty;
import adalid.core.annotations.EntityClass;
import adalid.core.annotations.EntityConsoleView;
import adalid.core.annotations.EntityDeleteOperation;
import adalid.core.annotations.EntityDetailView;
import adalid.core.annotations.EntityInsertOperation;
import adalid.core.annotations.EntitySelectOperation;
import adalid.core.annotations.EntityTableView;
import adalid.core.annotations.EntityTreeView;
import adalid.core.annotations.EntityUpdateOperation;
import adalid.core.annotations.ForeignKey;
import adalid.core.annotations.InstanceReference;
import adalid.core.annotations.ManyToOne;
import adalid.core.annotations.NameProperty;
import adalid.core.annotations.OperationClass;
import adalid.core.annotations.ParameterField;
import adalid.core.annotations.PrimaryKey;
import adalid.core.annotations.ProcessOperationClass;
import adalid.core.annotations.PropertyField;
import adalid.core.annotations.StringField;
import adalid.core.annotations.VersionProperty;
import adalid.core.enums.Kleenean;
import adalid.core.enums.MasterDetailView;
import adalid.core.enums.Navigability;
import adalid.core.enums.OnDeleteAction;
import adalid.core.enums.OnUpdateAction;
import adalid.core.enums.ResourceGender;
import adalid.core.enums.ResourceType;
import adalid.core.interfaces.Artifact;
import adalid.core.interfaces.Segment;
import adalid.core.parameters.BooleanParameter;
import adalid.core.parameters.StringParameter;
import adalid.core.properties.BooleanProperty;
import adalid.core.properties.LongProperty;
import adalid.core.properties.StringProperty;
import java.lang.reflect.Field;
import meta.proyecto.base.ProyectoBase;

/**
 * @author Jorge Campins
 */
@EntityClass(independent = Kleenean.TRUE, resourceType = ResourceType.OPERATION, resourceGender = ResourceGender.MASCULINE)
@EntitySelectOperation(enabled = Kleenean.TRUE)
@EntityInsertOperation(enabled = Kleenean.TRUE)
@EntityUpdateOperation(enabled = Kleenean.TRUE)
@EntityDeleteOperation(enabled = Kleenean.TRUE)
@EntityTableView(enabled = Kleenean.TRUE, inserts = Kleenean.TRUE, updates = Kleenean.TRUE)
@EntityDetailView(enabled = Kleenean.TRUE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.TRUE)
public class Rol extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    @Deprecated
    private Rol() {
        this(null, null);
    }

    public Rol(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public LongProperty idRol;

    @VersionProperty
    public LongProperty versionRol;

    @BusinessKey
    @StringField(maxLength = 100)
    public StringProperty codigoRol;

    @NameProperty
    public StringProperty nombreRol;

    @DescriptionProperty
    public StringProperty descripcionRol;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public BooleanProperty esSuperRol;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public BooleanProperty esRolEspecial;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    @Allocation(maxDepth = 1, maxRound = 0)
    public TipoRol numeroTipoRol;

    public Instance SUPER_GESTOR;

    public Instance SUPER_LECTOR;

    public Instance OPERADOR;

    public Segment modificables;

    protected Copiar copiar;

    protected ModificarConjunto modificarConjunto;

    protected PropagarFiltros propagarFiltros;

    protected PropagarFavoritos propagarFavoritos;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        setDefaultLabel("rol");
//      setDefaultShortLabel("rol");
        setDefaultCollectionLabel("Roles");
//      setDefaultCollectionShortLabel("Roles");
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        esSuperRol.setInitialValue(false);
        esSuperRol.setDefaultValue(false);
        esRolEspecial.setInitialValue(false);
        esRolEspecial.setDefaultValue(false);
    }

    @Override
    protected void settleInstances() {
        super.settleInstances();
        SUPER_GESTOR.newInstanceField(idRol, 101);
        SUPER_GESTOR.newInstanceField(codigoRol, "SuperGestor");
        SUPER_GESTOR.newInstanceField(nombreRol, "S??per Gestor");
        SUPER_GESTOR.newInstanceField(descripcionRol, "Permite ejecutar todas las funciones del sistema");
        SUPER_GESTOR.newInstanceField(esSuperRol, true);
        SUPER_GESTOR.newInstanceField(esRolEspecial, true);
        SUPER_GESTOR.newInstanceField(numeroTipoRol, numeroTipoRol.GESTOR);
//      SUPER_GESTOR.newInstanceField(idGrupoAplicacion, idGrupoAplicacion.SERVICIOS_COMUNES);
        SUPER_LECTOR.newInstanceField(idRol, 102);
        SUPER_LECTOR.newInstanceField(codigoRol, "SuperLector");
        SUPER_LECTOR.newInstanceField(nombreRol, "S??per Lector");
        SUPER_LECTOR.newInstanceField(descripcionRol, "Permite ejecutar todas las funciones de lectura del sistema");
        SUPER_LECTOR.newInstanceField(esSuperRol, true);
        SUPER_LECTOR.newInstanceField(esRolEspecial, true);
        SUPER_LECTOR.newInstanceField(numeroTipoRol, numeroTipoRol.LECTOR);
//      SUPER_LECTOR.newInstanceField(idGrupoAplicacion, idGrupoAplicacion.SERVICIOS_COMUNES);
        OPERADOR.newInstanceField(idRol, 103);
        OPERADOR.newInstanceField(codigoRol, "Operador");
        OPERADOR.newInstanceField(nombreRol, "Operador");
        OPERADOR.newInstanceField(descripcionRol, "Permite ejecutar todas las funciones p??blicas del sistema");
        OPERADOR.newInstanceField(esSuperRol, false);
        OPERADOR.newInstanceField(esRolEspecial, true);
        OPERADOR.newInstanceField(numeroTipoRol, numeroTipoRol.OPERADOR);
    }

    @Override
    protected void settleExpressions() {
        super.settleExpressions();
        modificables = idRol.isGreaterOrEqualTo(10000L);
        modificables.setDefaultErrorMessage("el rol es un rol de configuraci??n b??sica del sistema; "
            + "no se permite modificarlo ni eliminarlo");
        setUpdateFilter(modificables);
        setDeleteFilter(modificables);
    }

    // <editor-fold defaultstate="collapsed" desc="Operations">
    @ProcessOperationClass(overloading = Kleenean.FALSE)
    public class Copiar extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            setDefaultDescription(""
                + "Copia un rol con otro c??digo y nombre. "
                + "La copia incluye los filtros, funciones y favoritos asociados al rol original, pero no los usuarios. "
                + "");
        }

        @InstanceReference
        @Allocation(maxDepth = 1, maxRound = 0)
        protected Rol rol;

        @ParameterField(required = Kleenean.TRUE)
        @StringField(maxLength = 30)
        protected StringParameter codigo;

        @ParameterField(required = Kleenean.FALSE)
        @StringField(maxLength = 100)
        protected StringParameter nombre;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            rol.setDefaultDescription(""
                + "C??digo del rol que desea copiar. "
                + "Es un dato obligatorio y no tiene valor por omisi??n. "
                + "");
            codigo.setDefaultDescription(""
                + "C??digo del nuevo rol que produce la copia. "
                + "Es un dato obligatorio y no tiene valor por omisi??n. "
                + "");
            nombre.setDefaultDescription(""
                + "Nombre del nuevo rol que produce la copia. "
                + "Es un dato opcional. Por omisi??n se utiliza el nombre del rol original. "
                + "");
        }

    }

    @ProcessOperationClass(overloading = Kleenean.FALSE)
    public class ModificarConjunto extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            setDefaultDescription(""
                + "Modifica el conjunto de segmentos de las funciones asociadas al rol. "
                + "");
        }

        @InstanceReference
        @Allocation(maxDepth = 1, maxRound = 0)
        protected Rol rol;

        @ParameterField(required = Kleenean.TRUE)
        @Allocation(maxDepth = 1, maxRound = 0)
        protected ConjuntoSegmento conjuntoSegmento;

        @ParameterField(required = Kleenean.TRUE)
        protected BooleanParameter soloSegmentadas;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            rol.setDefaultDescription(""
                + "C??digo del rol cuyas funciones asociadas desea modificar. "
                + "Es un dato obligatorio y no tiene valor por omisi??n. "
                + "");
            conjuntoSegmento.setDefaultDescription(""
                + "C??digo del nuevo conjunto de segmentos para las funciones. "
                + "Es un dato obligatorio y no tiene valor por omisi??n. "
                + "");
            soloSegmentadas.setDefaultDescription(""
                + "Determina si ser??n modificadas todas las funciones, o solo las que ya est??n autorizadas a un conjunto de segmentos. "
                + "Es un dato opcional. Por omisi??n se modifican todas las funciones asociadas. "
                + "");
        }

    }

    @OperationClass(asynchronous = Kleenean.TRUE)
    @ProcessOperationClass(overloading = Kleenean.FALSE)
    public class PropagarFiltros extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            setDefaultDescription(""
                + "Produce una copia de los filtros asociados al rol para cada usuario asociado al rol. "
                + "");
        }

        @InstanceReference
        @Allocation(maxDepth = 1, maxRound = 0)
        protected Rol rol;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            rol.setDefaultDescription(""
                + "C??digo del rol cuyos filtros desea propagar. "
                + "Es un dato obligatorio y no tiene valor por omisi??n. "
                + "");
        }

    }

    @OperationClass(asynchronous = Kleenean.TRUE)
    @ProcessOperationClass(overloading = Kleenean.FALSE)
    public class PropagarFavoritos extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            setDefaultDescription(""
                + "Produce una copia de los favoritos asociados al rol para cada usuario asociado al rol. "
                + "");
        }

        @InstanceReference
        @Allocation(maxDepth = 1, maxRound = 0)
        protected Rol rol;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            rol.setDefaultDescription(""
                + "C??digo del rol cuyos favoritos desea propagar. "
                + "Es un dato obligatorio y no tiene valor por omisi??n. "
                + "");
        }

    }
    // </editor-fold>

}
