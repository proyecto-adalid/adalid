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
import adalid.core.parameters.*;
import adalid.core.properties.*;
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
        setDefaultCollectionLabel("roles");
//      setDefaultCollectionShortLabel("roles");
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
        SUPER_GESTOR.newInstanceField(nombreRol, "Súper Gestor");
        SUPER_GESTOR.newInstanceField(descripcionRol, "Permite ejecutar todas las funciones del sistema");
        SUPER_GESTOR.newInstanceField(esSuperRol, true);
        SUPER_GESTOR.newInstanceField(esRolEspecial, true);
        SUPER_GESTOR.newInstanceField(numeroTipoRol, numeroTipoRol.GESTOR);
//      SUPER_GESTOR.newInstanceField(idGrupoAplicacion, idGrupoAplicacion.SERVICIOS_COMUNES);
        SUPER_LECTOR.newInstanceField(idRol, 102);
        SUPER_LECTOR.newInstanceField(codigoRol, "SuperLector");
        SUPER_LECTOR.newInstanceField(nombreRol, "Súper Lector");
        SUPER_LECTOR.newInstanceField(descripcionRol, "Permite ejecutar todas las funciones de lectura del sistema");
        SUPER_LECTOR.newInstanceField(esSuperRol, true);
        SUPER_LECTOR.newInstanceField(esRolEspecial, true);
        SUPER_LECTOR.newInstanceField(numeroTipoRol, numeroTipoRol.LECTOR);
//      SUPER_LECTOR.newInstanceField(idGrupoAplicacion, idGrupoAplicacion.SERVICIOS_COMUNES);
        OPERADOR.newInstanceField(idRol, 103);
        OPERADOR.newInstanceField(codigoRol, "Operador");
        OPERADOR.newInstanceField(nombreRol, "Operador");
        OPERADOR.newInstanceField(descripcionRol, "Permite ejecutar todas las funciones públicas del sistema");
        OPERADOR.newInstanceField(esSuperRol, false);
        OPERADOR.newInstanceField(esRolEspecial, true);
        OPERADOR.newInstanceField(numeroTipoRol, numeroTipoRol.OPERADOR);
    }

    @Override
    protected void settleExpressions() {
        super.settleExpressions();
        modificables = idRol.isGreaterOrEqualTo(10000L);
        modificables.setDefaultErrorMessage("el rol es un rol de configuración básica del sistema; "
            + "no se permite modificarlo ni eliminarlo");
        setUpdateFilter(modificables);
        setDeleteFilter(modificables);
    }

    // <editor-fold defaultstate="collapsed" desc="Operations">
    public class Copiar extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            setDefaultDescription(""
                + "Copia un rol con otro código y nombre. "
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
                + "Código del rol que desea copiar. "
                + "Es un dato obligatorio y no tiene valor por omisión. "
                + "");
            codigo.setDefaultDescription(""
                + "Código del nuevo rol que produce la copia. "
                + "Es un dato obligatorio y no tiene valor por omisión. "
                + "");
            nombre.setDefaultDescription(""
                + "Nombre del nuevo rol que produce la copia. "
                + "Es un dato opcional. Por omisión se utiliza el nombre del rol original. "
                + "");
        }

    }

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
                + "Código del rol cuyas funciones asociadas desea modificar. "
                + "Es un dato obligatorio y no tiene valor por omisión. "
                + "");
            conjuntoSegmento.setDefaultDescription(""
                + "Código del nuevo conjunto de segmentos para las funciones. "
                + "Es un dato obligatorio y no tiene valor por omisión. "
                + "");
            soloSegmentadas.setDefaultDescription(""
                + "Determina si serán modificadas todas las funciones, o solo las que ya están autorizadas a un conjunto de segmentos. "
                + "Es un dato opcional. Por omisión se modifican todas las funciones asociadas. "
                + "");
        }

    }

    @OperationClass(asynchronous = Kleenean.TRUE)
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
                + "Código del rol cuyos filtros desea propagar. "
                + "Es un dato obligatorio y no tiene valor por omisión. "
                + "");
        }

    }

    @OperationClass(asynchronous = Kleenean.TRUE)
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
                + "Código del rol cuyos favoritos desea propagar. "
                + "Es un dato obligatorio y no tiene valor por omisión. "
                + "");
        }

    }
    // </editor-fold>

}
