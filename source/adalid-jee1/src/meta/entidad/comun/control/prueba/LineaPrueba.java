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
import adalid.core.annotations.EntityClass;
import adalid.core.annotations.EntityConsoleView;
import adalid.core.annotations.EntityDataGen;
import adalid.core.annotations.EntityDeleteOperation;
import adalid.core.annotations.EntityDetailView;
import adalid.core.annotations.EntityInsertOperation;
import adalid.core.annotations.EntitySelectOperation;
import adalid.core.annotations.EntityTableView;
import adalid.core.annotations.EntityTreeView;
import adalid.core.annotations.EntityTriggers;
import adalid.core.annotations.EntityUpdateOperation;
import adalid.core.annotations.FileReference;
import adalid.core.annotations.ForeignKey;
import adalid.core.annotations.InstanceReference;
import adalid.core.annotations.ManyToOne;
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
import adalid.core.interfaces.Check;
import adalid.core.parameters.StringParameter;
import adalid.core.properties.IntegerProperty;
import adalid.core.properties.StringProperty;
import java.lang.reflect.Field;
import meta.entidad.base.PersistentEntityBase;
import meta.proyecto.base.ProyectoBase;

/**
 * @author Jorge Campins
 */
@EntityClass(independent = Kleenean.FALSE, resourceType = ResourceType.TESTING, resourceGender = ResourceGender.FEMININE)
@EntitySelectOperation(enabled = Kleenean.TRUE)
@EntityInsertOperation(enabled = Kleenean.TRUE)
@EntityUpdateOperation(enabled = Kleenean.TRUE)
@EntityDeleteOperation(enabled = Kleenean.TRUE)
@EntityTableView(enabled = Kleenean.TRUE, inserts = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.TRUE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.TRUE)
@EntityTriggers(afterCheck = Kleenean.TRUE)
@EntityDataGen(start = 1, stop = 100, step = 0)
public class LineaPrueba extends PersistentEntityBase {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    @Deprecated
    private LineaPrueba() {
        this(null, null);
    }

    public LineaPrueba(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        setDefaultLabel("línea de programa de prueba");
        setDefaultShortLabel("línea");
        setDefaultCollectionLabel("Líneas de Programas de Prueba");
        setDefaultCollectionShortLabel("Líneas");
    }

    @BusinessKey
    @StringField(maxLength = 70)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.FALSE, defaultCondition = DefaultCondition.UNCONDITIONALLY)
    public StringProperty codigo;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(create = Kleenean.TRUE)
    public IntegerProperty numero;

    @Allocation(maxDepth = 2, maxRound = 0)
    @ColumnField(nullable = Kleenean.FALSE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.TABLE_AND_DETAIL)
    @PropertyField(create = Kleenean.TRUE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public ProgramaPrueba programa;

    @Allocation(maxDepth = 2, maxRound = 0)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(create = Kleenean.TRUE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public EscenarioPrueba escenario;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(create = Kleenean.TRUE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public ProgramaPrueba subprograma;

    @PropertyField(create = Kleenean.TRUE)
    public StringProperty condiciones;

    @PropertyField(create = Kleenean.TRUE)
    public StringProperty repeticiones;

    @PropertyField(create = Kleenean.TRUE)
    public StringProperty comentarios;

    @Override
    protected void settleProperties() {
        super.settleProperties();
        codigo.setDefaultValue(programa.codigo.concat("-").concat(numero.toCharString()));
        numero.setMinValue(1);
        numero.setMaxValue(10000);
    }

    @Override
    protected void settleLinks() {
        super.settleLinks();
        linkForeignOwnerProperty(programa.propietario);
    }

    protected Key key1;

    @Override
    protected void settleKeys() {
        super.settleKeys();
        key1.newKeyField(programa, numero, id);
        key1.setUnique(true);
        setOrderBy(key1);
    }

    protected Tab tab1, tab2;

    @Override
    protected void settleTabs() {
        super.settleTabs();
        tab1.setDefaultLabel("datos básicos");
        tab1.newTabField(codigo, programa, numero, escenario, subprograma);
        tab2.setDefaultLabel("datos adicionales");
        tab2.newTabField(condiciones, repeticiones, comentarios);
    }

    protected Check check0101, check0102, check0103, check0104, check0105;

    @Override
    protected void settleExpressions() {
        super.settleExpressions();
        /**/
        check0101 = escenario.isNotNull().xor(subprograma.isNotNull());
        check0101.setDefaultErrorMessage(""
            + "escenario y subprograma son mutuamente excluyentes"
            + "");
        /**/
        check0102 = programa.caso.isNotNull().implies(escenario.isNotNull());
        check0102.setDefaultErrorMessage(""
            + "el escenario es requerido porque el programa está enlazado a un caso de prueba"
            + "");
        /**/
        check0103 = programa.caso.isNotNull().and(escenario.isNotNull()).implies(programa.caso.isEqualTo(escenario.caso));
        check0103.setDefaultErrorMessage(""
            + "el escenario debe ser del mismo caso de prueba al cual está enlazado el programa"
            + "");
        /**/
        check0104 = programa.caso.isNotNull().implies(subprograma.isNull());
        check0104.setDefaultErrorMessage(""
            + "la referencia al subprograma no es válida porque el programa está enlazado a un caso de prueba"
            + " y solo admite referencias a escenarios del caso enlazado"
            + "");
        /**/
        check0105 = subprograma.isNullOrNotEqualTo(programa);
        check0105.setDefaultErrorMessage(""
            + "la referencia al subprograma no es válida porque el subprograma es igual al programa"
            + "");
    }

    @Override
    protected void settleFilters() {
        super.settleFilters();
        escenario.setRequiringFilter(programa.caso.isNotNull());
    }

    protected Cargar cargar;

    @ProcessOperationClass(overloading = Kleenean.FALSE)
    public class Cargar extends ProcessOperation {

        @InstanceReference
//      @Allocation(maxDepth = 1, maxRound = 0)
        protected LineaPrueba linea;

        @FileReference
        @ParameterField(required = Kleenean.TRUE)
        protected StringParameter archivo;

        @ParameterField(required = Kleenean.FALSE)
        protected StringParameter descripcionArchivo;

    }

}
