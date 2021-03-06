/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.entidad.comun.control.prueba;

import adalid.core.Key;
import adalid.core.ProcessOperation;
import adalid.core.Tab;
import adalid.core.annotations.Allocation;
import adalid.core.annotations.BusinessKey;
import adalid.core.annotations.ColumnField;
import adalid.core.annotations.DescriptionProperty;
import adalid.core.annotations.EntityClass;
import adalid.core.annotations.EntityConsoleView;
import adalid.core.annotations.EntityDataGen;
import adalid.core.annotations.EntityDeleteOperation;
import adalid.core.annotations.EntityDetailView;
import adalid.core.annotations.EntityInsertOperation;
import adalid.core.annotations.EntitySelectOperation;
import adalid.core.annotations.EntityTableView;
import adalid.core.annotations.EntityTreeView;
import adalid.core.annotations.EntityUpdateOperation;
import adalid.core.annotations.FileReference;
import adalid.core.annotations.ForeignKey;
import adalid.core.annotations.InstanceReference;
import adalid.core.annotations.ManyToOne;
import adalid.core.annotations.NameProperty;
import adalid.core.annotations.ParameterField;
import adalid.core.annotations.ProcessOperationClass;
import adalid.core.annotations.PropertyField;
import adalid.core.annotations.StringField;
import adalid.core.enums.DefaultCondition;
import adalid.core.enums.Kleenean;
import adalid.core.enums.MasterDetailView;
import adalid.core.enums.Navigability;
import adalid.core.enums.OnDeleteAction;
import adalid.core.enums.OnUpdateAction;
import adalid.core.enums.ResourceGender;
import adalid.core.enums.ResourceType;
import adalid.core.interfaces.Artifact;
import adalid.core.parameters.StringParameter;
import adalid.core.properties.IntegerProperty;
import adalid.core.properties.StringProperty;
import java.lang.reflect.Field;
import meta.entidad.base.PersistentEntityBase;
import meta.proyecto.base.ProyectoBase;

/**
 * @author Jorge Campins
 */
@EntityClass(independent = Kleenean.FALSE, resourceType = ResourceType.TESTING, resourceGender = ResourceGender.MASCULINE)
@EntitySelectOperation(enabled = Kleenean.TRUE)
@EntityInsertOperation(enabled = Kleenean.TRUE)
@EntityUpdateOperation(enabled = Kleenean.TRUE)
@EntityDeleteOperation(enabled = Kleenean.TRUE)
@EntityTableView(enabled = Kleenean.TRUE, inserts = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.TRUE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.TRUE)
@EntityDataGen(start = 1, stop = 100, step = 0)
public class EscenarioPrueba extends PersistentEntityBase {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    @Deprecated
    private EscenarioPrueba() {
        this(null, null);
    }

    public EscenarioPrueba(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        setDefaultLabel("escenario de caso de prueba");
        setDefaultShortLabel("escenario");
        setDefaultCollectionLabel("Escenarios de Casos de Prueba");
        setDefaultCollectionShortLabel("Escenarios");
    }

    @BusinessKey
    @StringField(maxLength = 70)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.FALSE, defaultCondition = DefaultCondition.UNCONDITIONALLY)
    public StringProperty codigo;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(create = Kleenean.TRUE)
    public IntegerProperty numero;

    @NameProperty
    public StringProperty nombre;

    @DescriptionProperty
    @PropertyField(create = Kleenean.TRUE)
    public StringProperty descripcion;

    @PropertyField(create = Kleenean.TRUE)
    public StringProperty comentarios;

    @PropertyField(create = Kleenean.TRUE)
    public StringProperty precondiciones;

    @PropertyField(create = Kleenean.TRUE)
    public StringProperty validaciones;

    @PropertyField(create = Kleenean.TRUE)
    public StringProperty acciones;

    @PropertyField(create = Kleenean.TRUE)
    public StringProperty poscondiciones;

    @PropertyField(create = Kleenean.TRUE)
    public StringProperty invariantes;

    @PropertyField(create = Kleenean.TRUE)
    public StringProperty pseudocodigo;

    @PropertyField(create = Kleenean.TRUE)
    public StringProperty resultados;

    @ColumnField(nullable = Kleenean.FALSE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(create = Kleenean.TRUE, update = Kleenean.TRUE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public TipoResultadoPrueba tipoResultado;

    @Allocation(maxDepth = 2, maxRound = 0)
    @ColumnField(nullable = Kleenean.FALSE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.TABLE_AND_DETAIL)
    @PropertyField(create = Kleenean.TRUE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public CasoPrueba caso;

    @Override
    protected void settleProperties() {
        super.settleProperties();
        codigo.setDefaultValue(caso.codigo.concat("-").concat(numero.toCharString()));
        numero.setMinValue(1);
        numero.setMaxValue(10000);
        tipoResultado.setInitialValue(tipoResultado.EXITO);
        tipoResultado.setDefaultValue(tipoResultado.EXITO);
    }

    @Override
    protected void settleLinks() {
        super.settleLinks();
        linkForeignOwnerProperty(caso.propietario);
    }

    protected Key key1;

    @Override
    protected void settleKeys() {
        super.settleKeys();
        key1.newKeyField(caso, numero, id);
        key1.setUnique(true);
        setOrderBy(key1);
    }

    protected Tab tab1, tab2, tab3;

    @Override
    protected void settleTabs() {
        super.settleTabs();
        tab1.setDefaultLabel("datos b??sicos");
        tab1.newTabField(caso, numero, descripcion, comentarios);
        tab2.setDefaultLabel("datos adicionales");
        tab2.newTabField(precondiciones, validaciones, acciones, poscondiciones, invariantes, pseudocodigo);
        tab3.setDefaultLabel("resultado esperado");
        tab3.newTabField(resultados, tipoResultado);
    }

    protected Cargar cargar;

    @ProcessOperationClass(overloading = Kleenean.FALSE)
    public class Cargar extends ProcessOperation {

        @InstanceReference
        @Allocation(maxDepth = 1, maxRound = 0)
        protected EscenarioPrueba escenario;

        @FileReference
        @ParameterField(required = Kleenean.TRUE)
        protected StringParameter archivo;

        @ParameterField(required = Kleenean.FALSE)
        protected StringParameter descripcionArchivo;

    }

}
