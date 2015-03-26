/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package meta.entidad.comun.control.prueba;

import adalid.core.*;
import adalid.core.annotations.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import adalid.core.parameters.StringParameter;
import adalid.core.properties.*;
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
        setDefaultLabel("ejecución de línea de programa de prueba");
        setDefaultShortLabel("ejecución de línea");
        setDefaultCollectionLabel("ejecuciones de líneas de programas de prueba");
        setDefaultCollectionShortLabel("ejecuciones de líneas");
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
        ejecucion.setDefaultLabel("ejecución de programa");
//      ejecucion.setDefaultShortLabel("ejecución");
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
        tab1.setDefaultLabel("datos básicos");
        tab1.newTabField(codigo, ejecucion, numero, nivel, programa, escenario, subprograma);
        tab2.setDefaultLabel("datos adicionales");
        tab2.newTabField(condiciones, repeticiones, comentarios);
        tab3.setDefaultLabel("condición");
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
                + "el resultado de la ejecución de la línea ya está registrado"
                + "");
            check0102 = ejecucion.subprograma.isNull();
            check0102.setDefaultErrorMessage(""
                + "la línea corresponde a la ejecución de un subprograma"
                + "");
            check0103 = ejecucion.escenario.isNotNull();
            check0103.setDefaultErrorMessage(""
                + "la línea no corresponde a la ejecución de un escenario"
                + "");
            check0104 = ejecucion.escenario.isNotNull().implies(ejecucion.escenario.caso.funcion.isNotNull());
            check0104.setDefaultErrorMessage(""
                + "la línea corresponde a la ejecución de un escenario cuyo caso no está enlazado a una función"
                + "");
            check0201 = or(
                rastro.numeroCondicionEjeFun.isEqualTo(condicionEjecucion.EJECUTADO_SIN_ERRORES),
                rastro.numeroCondicionEjeFun.isEqualTo(condicionEjecucion.EJECUTADO_CON_ERRORES),
                rastro.numeroCondicionEjeFun.isEqualTo(condicionEjecucion.EJECUCION_CANCELADA));
            check0201.setDefaultErrorMessage(""
                + "el rastro corresponde a una ejecución que no ha concluído (está pendiente o en progreso)"
                + "");
            check0202 = rastro.idFuncion.isEqualTo(ejecucion.escenario.caso.funcion);
            check0202.setDefaultErrorMessage(""
                + "el rastro corresponde a una función diferente a la enlazada al caso del escenario de la línea"
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
                + "el resultado de la ejecución de la línea ya está registrado"
                + "");
            check0102 = ejecucion.subprograma.isNull();
            check0102.setDefaultErrorMessage(""
                + "la línea corresponde a la ejecución de un subprograma"
                + "");
            check0103 = ejecucion.escenario.isNotNull();
            check0103.setDefaultErrorMessage(""
                + "la línea no corresponde a la ejecución de un escenario"
                + "");
            check0104 = ejecucion.escenario.isNotNull().implies(ejecucion.escenario.caso.funcion.isNull());
            check0104.setDefaultErrorMessage(""
                + "la línea corresponde a la ejecución de un escenario cuyo caso está enlazado a una función"
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
                + "el resultado de la ejecución de la línea no está registrado"
                + "");
            check0102 = ejecucion.subprograma.isNull();
            check0102.setDefaultErrorMessage(""
                + "la línea corresponde a la ejecución de un subprograma"
                + "");
            check0103 = ejecucion.escenario.isNotNull();
            check0103.setDefaultErrorMessage(""
                + "la línea no corresponde a la ejecución de un escenario"
                + "");
        }

        @Override
        protected void settleFilters() {
            super.settleFilters();
            ejecucion.setSearchQueryFilter(and(check0101, check0102, check0103));
        }

    }

}
