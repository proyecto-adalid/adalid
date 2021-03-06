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
import adalid.core.annotations.EntityUpdateOperation;
import adalid.core.annotations.FileReference;
import adalid.core.annotations.ForeignKey;
import adalid.core.annotations.InstanceReference;
import adalid.core.annotations.ManyToOne;
import adalid.core.annotations.ParameterField;
import adalid.core.annotations.ParentProperty;
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
import adalid.core.enums.SpecialTemporalValue;
import adalid.core.interfaces.Artifact;
import adalid.core.interfaces.Check;
import adalid.core.parameters.StringParameter;
import adalid.core.properties.BooleanProperty;
import adalid.core.properties.IntegerProperty;
import adalid.core.properties.StringProperty;
import adalid.core.properties.TimestampProperty;
import java.lang.reflect.Field;
import meta.entidad.base.PersistentEntityBase;
import meta.entidad.comun.auditoria.RastroFuncion;
import meta.entidad.comun.configuracion.basica.CondicionEjeFun;
import meta.proyecto.base.ProyectoBase;

/**
 * @author Jorge Campins
 */
@EntityClass(independent = Kleenean.FALSE, resourceType = ResourceType.TESTING, resourceGender = ResourceGender.FEMININE)
@EntitySelectOperation(enabled = Kleenean.TRUE)
@EntityInsertOperation(enabled = Kleenean.FALSE)
@EntityUpdateOperation(enabled = Kleenean.TRUE)
@EntityDeleteOperation(enabled = Kleenean.FALSE)
@EntityTableView(enabled = Kleenean.TRUE, inserts = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.TRUE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.TRUE)
@EntityDataGen(start = 1, stop = 1000, step = 0)
public class EjecucionLineaPrueba extends PersistentEntityBase {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    @Deprecated
    private EjecucionLineaPrueba() {
        this(null, null);
    }

    public EjecucionLineaPrueba(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        setDefaultLabel("ejecuci??n de l??nea de programa de prueba");
        setDefaultShortLabel("ejecuci??n de l??nea");
        setDefaultCollectionLabel("Ejecuciones de L??neas de Programas de Prueba");
        setDefaultCollectionShortLabel("Ejecuciones de L??neas");
    }

    @BusinessKey
    @StringField(maxLength = 100)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.FALSE, defaultCondition = DefaultCondition.UNCONDITIONALLY)
    public StringProperty codigo;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public IntegerProperty numero;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public IntegerProperty nivel;

    @Allocation(maxDepth = 2, maxRound = 0)
    @ColumnField(nullable = Kleenean.FALSE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.TABLE_AND_DETAIL)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public EjecucionPrueba ejecucion;

    @Allocation(maxDepth = 1, maxRound = 0)
    @ColumnField(nullable = Kleenean.FALSE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public ProgramaPrueba programa;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public EscenarioPrueba escenario;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public ProgramaPrueba subprograma;

    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE)
    public StringProperty condiciones;

    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE)
    public StringProperty repeticiones;

    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE)
    public StringProperty comentarios;

    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE)
    public StringProperty resultados;

    @PropertyField(create = Kleenean.FALSE, update = Kleenean.TRUE)
    public StringProperty observaciones;

    @FileReference
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE)
    public StringProperty archivo;

//  @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE)
    public RastroFuncion rastro;

    @ColumnField(nullable = Kleenean.FALSE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public CondicionEjeFun condicionEjecucion;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE)
    public TimestampProperty fechaHoraCondicionEjecucion;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public TipoResultadoPrueba tipoResultado;

    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public BooleanProperty resultadoEsperado;

    @ParentProperty
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(hidden = Kleenean.TRUE)
    public EjecucionLineaPrueba superior;

    @Override
    protected void settleProperties() {
        super.settleProperties();
        codigo.setDefaultValue(ejecucion.codigo.concat("-").concat(numero.toCharString()));
        ejecucion.setDefaultLabel("ejecuci??n de programa");
//      ejecucion.setDefaultShortLabel("ejecuci??n");
        condicionEjecucion.setInitialValue(condicionEjecucion.EJECUCION_PENDIENTE);
        condicionEjecucion.setDefaultValue(condicionEjecucion.EJECUCION_PENDIENTE);
//      condicionEjecucion.setRemoveInstanceArray(condicionEjecucion.EJECUCION_EN_PROGRESO);
        fechaHoraCondicionEjecucion.setInitialValue(SpecialTemporalValue.CURRENT_TIMESTAMP);
        fechaHoraCondicionEjecucion.setDefaultValue(SpecialTemporalValue.CURRENT_TIMESTAMP);
    }

    @Override
    protected void settleLinks() {
        super.settleLinks();
        linkForeignOwnerProperty(ejecucion.responsable);
    }

    protected Key key1;

    @Override
    protected void settleKeys() {
        super.settleKeys();
        key1.newKeyField(ejecucion, numero, id);
        key1.setUnique(true);
        setOrderBy(key1);
    }

    protected Tab tab1, tab2, tab3, tab4;

    @Override
    protected void settleTabs() {
        super.settleTabs();
        tab1.setDefaultLabel("datos b??sicos");
        tab1.newTabField(codigo, ejecucion, numero, nivel, programa, escenario, subprograma);
        tab2.setDefaultLabel("datos adicionales");
        tab2.newTabField(condiciones, repeticiones, comentarios);
        tab3.setDefaultLabel("condici??n");
        tab3.newTabField(condicionEjecucion, fechaHoraCondicionEjecucion, tipoResultado, resultadoEsperado);
        tab4.setDefaultLabel("resultado");
        tab4.newTabField(resultados, observaciones, archivo, rastro);
    }

    protected Check check0101;

    @Override
    protected void settleExpressions() {
        super.settleExpressions();
        check0101 = escenario.isNotNull().xor(subprograma.isNotNull());
        check0101.setDefaultErrorMessage(""
            + "escenario y subprograma son mutuamente excluyentes"
            + "");
    }

    protected Cargar cargar;

    @ProcessOperationClass(overloading = Kleenean.FALSE)
    public class Cargar extends ProcessOperation {

        @InstanceReference
        @Allocation(maxDepth = 1, maxRound = 0)
        protected EjecucionLineaPrueba ejecucion;

        @FileReference
        @ParameterField(required = Kleenean.TRUE)
        protected StringParameter archivo;

        @ParameterField(required = Kleenean.FALSE)
        protected StringParameter descripcionArchivo;

    }

    protected RegistrarResultadoConRastro registrarResultadoConRastro;

    @ProcessOperationClass(overloading = Kleenean.FALSE)
    public class RegistrarResultadoConRastro extends ProcessOperation {

        @InstanceReference
        @Allocation(maxDepth = 4, maxRound = 0)
        protected EjecucionLineaPrueba ejecucion;

        @ParameterField(required = Kleenean.TRUE, linkedField = "rastro")
        @Allocation(maxDepth = 2, maxRound = 0)
        public RastroFuncion rastro;

        @ParameterField(required = Kleenean.FALSE, linkedField = "observaciones")
        protected StringParameter observaciones;

        @FileReference
        @ParameterField(required = Kleenean.FALSE, linkedField = "archivo")
        protected StringParameter archivo;

        @ParameterField(required = Kleenean.FALSE)
        protected StringParameter descripcionArchivo;

        Check check0101, check0102, check0103, check0104;

        Check check0201, check0202;

        @Override
        protected void settleExpressions() {
            super.settleExpressions();
            check0101 = ejecucion.condicionEjecucion.isEqualTo(condicionEjecucion.EJECUCION_PENDIENTE);
            check0101.setDefaultErrorMessage(""
                + "el resultado de la ejecuci??n de la l??nea ya est?? registrado"
                + "");
            check0102 = ejecucion.subprograma.isNull();
            check0102.setDefaultErrorMessage(""
                + "la l??nea corresponde a la ejecuci??n de un subprograma"
                + "");
            check0103 = ejecucion.escenario.isNotNull();
            check0103.setDefaultErrorMessage(""
                + "la l??nea no corresponde a la ejecuci??n de un escenario"
                + "");
            check0104 = ejecucion.escenario.isNotNull().implies(ejecucion.escenario.caso.funcion.isNotNull());
            check0104.setDefaultErrorMessage(""
                + "la l??nea corresponde a la ejecuci??n de un escenario cuyo caso no est?? enlazado a una funci??n"
                + "");
            check0201 = or(
                rastro.numeroCondicionEjeFun.isEqualTo(condicionEjecucion.EJECUTADO_SIN_ERRORES),
                rastro.numeroCondicionEjeFun.isEqualTo(condicionEjecucion.EJECUTADO_CON_ERRORES),
                rastro.numeroCondicionEjeFun.isEqualTo(condicionEjecucion.EJECUCION_CANCELADA));
            check0201.setDefaultErrorMessage(""
                + "el rastro corresponde a una ejecuci??n que no ha conclu??do (est?? pendiente o en progreso)"
                + "");
            check0202 = rastro.idFuncion.isEqualTo(ejecucion.escenario.caso.funcion);
            check0202.setDefaultErrorMessage(""
                + "el rastro corresponde a una funci??n diferente a la enlazada al caso del escenario de la l??nea"
                + "");
        }

        @Override
        protected void settleFilters() {
            super.settleFilters();
            ejecucion.setSearchQueryFilter(and(check0101, check0102, check0103, check0104));
            rastro.setSearchQueryFilter(and(check0201, check0202));
        }

    }

    protected RegistrarResultadoSinRastro registrarResultadoSinRastro;

    @ProcessOperationClass(overloading = Kleenean.FALSE)
    public class RegistrarResultadoSinRastro extends ProcessOperation {

        @InstanceReference
        @Allocation(maxDepth = 4, maxRound = 0)
        protected EjecucionLineaPrueba ejecucion;

        @ParameterField(required = Kleenean.TRUE, linkedField = "condicionEjecucion")
        @Allocation(maxDepth = 1, maxRound = 0)
        public CondicionEjeFun condicionEjecucion;

        @ParameterField(required = Kleenean.FALSE, linkedField = "resultados")
        protected StringParameter resultados;

        @ParameterField(required = Kleenean.FALSE, linkedField = "observaciones")
        protected StringParameter observaciones;

        @FileReference
        @ParameterField(required = Kleenean.FALSE, linkedField = "archivo")
        protected StringParameter archivo;

        @ParameterField(required = Kleenean.FALSE)
        protected StringParameter descripcionArchivo;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            ejecucion.fechaHoraCondicionEjecucion.setCurrentValue(SpecialTemporalValue.CURRENT_TIMESTAMP);
        }

        Check check0101, check0102, check0103, check0104;

        @Override
        protected void settleExpressions() {
            super.settleExpressions();
            check0101 = ejecucion.condicionEjecucion.isEqualTo(condicionEjecucion.EJECUCION_PENDIENTE);
            check0101.setDefaultErrorMessage(""
                + "el resultado de la ejecuci??n de la l??nea ya est?? registrado"
                + "");
            check0102 = ejecucion.subprograma.isNull();
            check0102.setDefaultErrorMessage(""
                + "la l??nea corresponde a la ejecuci??n de un subprograma"
                + "");
            check0103 = ejecucion.escenario.isNotNull();
            check0103.setDefaultErrorMessage(""
                + "la l??nea no corresponde a la ejecuci??n de un escenario"
                + "");
            check0104 = ejecucion.escenario.isNotNull().implies(ejecucion.escenario.caso.funcion.isNull());
            check0104.setDefaultErrorMessage(""
                + "la l??nea corresponde a la ejecuci??n de un escenario cuyo caso est?? enlazado a una funci??n"
                + "");
        }

        @Override
        protected void settleFilters() {
            super.settleFilters();
            ejecucion.setSearchQueryFilter(and(check0101, check0102, check0103, check0104));
            condicionEjecucion.setSearchInstanceArray(
                condicionEjecucion.EJECUTADO_SIN_ERRORES,
                condicionEjecucion.EJECUTADO_CON_ERRORES,
                condicionEjecucion.EJECUCION_CANCELADA);
        }

    }

    protected ReversarResultado reversarResultado;

    @ProcessOperationClass(overloading = Kleenean.FALSE)
    public class ReversarResultado extends ProcessOperation {

        @InstanceReference
//      @Allocation(maxDepth = 1, maxRound = 0)
        protected EjecucionLineaPrueba ejecucion;

        Check check0101, check0102, check0103;

        @Override
        protected void settleExpressions() {
            super.settleExpressions();
            check0101 = ejecucion.condicionEjecucion.isNotEqualTo(condicionEjecucion.EJECUCION_PENDIENTE);
            check0101.setDefaultErrorMessage(""
                + "el resultado de la ejecuci??n de la l??nea no est?? registrado"
                + "");
            check0102 = ejecucion.subprograma.isNull();
            check0102.setDefaultErrorMessage(""
                + "la l??nea corresponde a la ejecuci??n de un subprograma"
                + "");
            check0103 = ejecucion.escenario.isNotNull();
            check0103.setDefaultErrorMessage(""
                + "la l??nea no corresponde a la ejecuci??n de un escenario"
                + "");
        }

        @Override
        protected void settleFilters() {
            super.settleFilters();
            ejecucion.setSearchQueryFilter(and(check0101, check0102, check0103));
        }

    }

}
