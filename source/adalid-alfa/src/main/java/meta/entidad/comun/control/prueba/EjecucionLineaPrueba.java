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
package meta.entidad.comun.control.prueba;

import adalid.core.*;
import adalid.core.annotations.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import adalid.core.parameters.*;
import adalid.core.properties.*;
import java.lang.reflect.Field;
import meta.entidad.base.PersistentEntityBase;
import meta.entidad.comun.auditoria.RastroFuncion;
import meta.entidad.comun.configuracion.basica.CondicionEjeFun;

/**
 * @author Jorge Campins
 */
@EntityClass(independent = Kleenean.FALSE, resourceType = ResourceType.TESTING, resourceGender = ResourceGender.FEMININE)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE, onload = SelectOnloadOption.EXECUTE)
@EntityInsertOperation(enabled = Kleenean.FALSE)
@EntityUpdateOperation(enabled = Kleenean.TRUE)
@EntityDeleteOperation(enabled = Kleenean.FALSE)
@EntityTableView(enabled = Kleenean.TRUE, inserts = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.TRUE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.TRUE)
//@EntityDataGen(start = 1, stop = 1000)
public class EjecucionLineaPrueba extends PersistentEntityBase {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public EjecucionLineaPrueba(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Override
    protected void addAllocationStrings() {
        super.addAllocationStrings();
        super.addAllocationStrings(
            "ejecucion.responsable",
            "escenario.caso.funcion"
        );
    }

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of EjecucionLineaPrueba's attributes">
        setLocalizedLabel(ENGLISH, "test program line execution");
        setLocalizedLabel(SPANISH, "ejecución de línea de programa de prueba");
        setLocalizedShortLabel(ENGLISH, "line execution");
        setLocalizedShortLabel(SPANISH, "ejecución de línea");
        setLocalizedCollectionLabel(ENGLISH, "Test Program Line Executions");
        setLocalizedCollectionLabel(SPANISH, "Ejecuciones de Líneas de Programas de Prueba");
        setLocalizedCollectionShortLabel(ENGLISH, "Line Executions");
        setLocalizedCollectionShortLabel(SPANISH, "Ejecuciones de Líneas");
        // </editor-fold>
    }

    @BusinessKey
    @StringField(maxLength = 100)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.FALSE, defaultCondition = DefaultCondition.UNCONDITIONALLY, defaultCheckpoint = Checkpoint.USER_INTERFACE)
    public StringProperty codigo;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public IntegerProperty numero;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public IntegerProperty nivel;

//->@Allocation(maxDepth = 2, maxRound = 0)
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

//->@Allocation(maxDepth = 3, maxRound = 0)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public EscenarioPrueba escenario;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public ProgramaPrueba subprograma;

    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE)
    @StringField(maxLength = 0)
    public StringProperty condiciones;

    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE)
    @StringField(maxLength = 0)
    public StringProperty repeticiones;

    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE)
    @StringField(maxLength = 0)
    public StringProperty comentarios;

    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE)
    @StringField(maxLength = 0)
    public StringProperty resultados;

    @PropertyField(create = Kleenean.FALSE, update = Kleenean.TRUE)
    @StringField(maxLength = 0)
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
        codigo.setDefaultValue(ejecucion.codigo.concat("-").concat(numero));
        condicionEjecucion.setInitialValue(condicionEjecucion.EJECUCION_PENDIENTE);
        condicionEjecucion.setDefaultValue(condicionEjecucion.EJECUCION_PENDIENTE);
//      condicionEjecucion.setRemoveInstanceArray(condicionEjecucion.EJECUCION_EN_PROGRESO);
        fechaHoraCondicionEjecucion.setInitialValue(SpecialTemporalValue.CURRENT_TIMESTAMP);
        fechaHoraCondicionEjecucion.setDefaultValue(SpecialTemporalValue.CURRENT_TIMESTAMP);
        // <editor-fold defaultstate="collapsed" desc="localization of EjecucionLineaPrueba's properties">
        codigo.setLocalizedLabel(ENGLISH, "test program line execution code");
        codigo.setLocalizedLabel(SPANISH, "código de la ejecución de línea de programa de prueba");
        codigo.setLocalizedShortLabel(ENGLISH, "code");
        codigo.setLocalizedShortLabel(SPANISH, "código");
        /**/
        numero.setLocalizedLabel(ENGLISH, "number");
        numero.setLocalizedLabel(SPANISH, "número");
        /**/
        nivel.setLocalizedLabel(ENGLISH, "level");
        nivel.setLocalizedLabel(SPANISH, "nivel");
        /**/
        ejecucion.setLocalizedLabel(ENGLISH, "program execution");
        ejecucion.setLocalizedLabel(SPANISH, "ejecución de programa");
        /**/
        programa.setLocalizedLabel(ENGLISH, "program");
        programa.setLocalizedLabel(SPANISH, "programa");
        /**/
        escenario.setLocalizedLabel(ENGLISH, "scenario");
        escenario.setLocalizedLabel(SPANISH, "escenario");
        /**/
        subprograma.setLocalizedLabel(ENGLISH, "subprogram");
        subprograma.setLocalizedLabel(SPANISH, "subprograma");
        /**/
        condiciones.setLocalizedLabel(ENGLISH, "conditions");
        condiciones.setLocalizedLabel(SPANISH, "condiciones");
        /**/
        repeticiones.setLocalizedLabel(ENGLISH, "repetitions");
        repeticiones.setLocalizedLabel(SPANISH, "repeticiones");
        /**/
        comentarios.setLocalizedLabel(ENGLISH, "comments");
        comentarios.setLocalizedLabel(SPANISH, "comentarios");
        /**/
        resultados.setLocalizedLabel(ENGLISH, "results");
        resultados.setLocalizedLabel(SPANISH, "resultados");
        /**/
        observaciones.setLocalizedLabel(ENGLISH, "remarks");
        observaciones.setLocalizedLabel(SPANISH, "observaciones");
        /**/
        archivo.setLocalizedLabel(ENGLISH, "file");
        archivo.setLocalizedLabel(SPANISH, "archivo");
        archivo.setLocalizedTooltip(ENGLISH, "open the file");
        archivo.setLocalizedTooltip(SPANISH, "abrir el archivo");
        /**/
        rastro.setLocalizedLabel(ENGLISH, "trail");
        rastro.setLocalizedLabel(SPANISH, "rastro");
        /**/
        condicionEjecucion.setLocalizedLabel(ENGLISH, "condition");
        condicionEjecucion.setLocalizedLabel(SPANISH, "condición ejecución");
        /**/
        fechaHoraCondicionEjecucion.setLocalizedLabel(ENGLISH, "condition timestamp");
        fechaHoraCondicionEjecucion.setLocalizedLabel(SPANISH, "fecha hora condición ejecución");
        /**/
        tipoResultado.setLocalizedLabel(ENGLISH, "result type");
        tipoResultado.setLocalizedLabel(SPANISH, "tipo resultado");
        /**/
        resultadoEsperado.setLocalizedLabel(ENGLISH, "expected result");
        resultadoEsperado.setLocalizedLabel(SPANISH, "resultado esperado");
        /**/
        superior.setLocalizedLabel(ENGLISH, "test program line execution parent");
        superior.setLocalizedLabel(SPANISH, "superior de la ejecución de línea de programa de prueba");
        superior.setLocalizedShortLabel(ENGLISH, "parent");
        superior.setLocalizedShortLabel(SPANISH, "superior");
        // </editor-fold>
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
        tab1.newTabField(codigo, ejecucion, numero, nivel, programa, escenario, subprograma);
        tab2.newTabField(condiciones, repeticiones, comentarios);
        tab3.newTabField(condicionEjecucion, fechaHoraCondicionEjecucion, tipoResultado, resultadoEsperado);
        tab4.newTabField(resultados, observaciones, archivo, rastro);
        // <editor-fold defaultstate="collapsed" desc="localization of EjecucionLineaPrueba's tabs">
        tab1.setLocalizedLabel(ENGLISH, "basic data");
        tab1.setLocalizedLabel(SPANISH, "datos básicos");
        /**/
        tab2.setLocalizedLabel(ENGLISH, "additional data");
        tab2.setLocalizedLabel(SPANISH, "datos adicionales");
        /**/
        tab3.setLocalizedLabel(ENGLISH, "condition");
        tab3.setLocalizedLabel(SPANISH, "condición");
        /**/
        tab4.setLocalizedLabel(ENGLISH, "result");
        tab4.setLocalizedLabel(SPANISH, "resultado");
        // </editor-fold>
    }

    protected Segment funcional;

    protected Check check0101;

    @Override
    protected void settleExpressions() {
        super.settleExpressions();
        /**/
        funcional = escenario.caso.funcion.isNotNull();
        check0101 = escenario.isNotNull().xor(subprograma.isNotNull());
        // <editor-fold defaultstate="collapsed" desc="localization of EjecucionLineaPrueba's expressions">
        funcional.setLocalizedLabel(ENGLISH, "check the function of the case of the scenario");
        funcional.setLocalizedLabel(SPANISH, "chequear la función del caso del escenario");
        funcional.setLocalizedDescription(ENGLISH, "the function of the case of the scenario should be defined");
        funcional.setLocalizedDescription(SPANISH, "la función del caso del escenario debería estar definida");
        funcional.setLocalizedErrorMessage(ENGLISH, "the function of the case of the scenario is not defined");
        funcional.setLocalizedErrorMessage(SPANISH, "la función del caso del escenario no está definida");
        /**/
        check0101.setLocalizedDescription(ENGLISH, "scenario and subprogram are mutually exclusive");
        check0101.setLocalizedDescription(SPANISH, "escenario y subprograma son mutuamente excluyentes");
        check0101.setLocalizedErrorMessage(ENGLISH, "scenario and subprogram are mutually exclusive");
        check0101.setLocalizedErrorMessage(SPANISH, "escenario y subprograma son mutuamente excluyentes");
        // </editor-fold>
    }

    protected Cargar cargar;

    @ProcessOperationClass
    public class Cargar extends ProcessOperation {

        @InstanceReference
//      @Allocation(maxDepth = 1, maxRound = 0)
        protected EjecucionLineaPrueba ejecucion;

        @FileReference
        @ParameterField(required = Kleenean.TRUE)
        protected StringParameter archivo;

        @ParameterField(required = Kleenean.FALSE)
        protected StringParameter descripcionArchivo;

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of Cargar's attributes">
            setLocalizedLabel(ENGLISH, "upload");
            setLocalizedLabel(SPANISH, "cargar");
            // </editor-fold>
        }

        @Override
        protected void settleParameters() {
            super.settleParameters();
            // <editor-fold defaultstate="collapsed" desc="localization of Cargar's parameters">
            ejecucion.setLocalizedLabel(ENGLISH, "execution");
            ejecucion.setLocalizedLabel(SPANISH, "ejecución");
            /**/
            archivo.setLocalizedLabel(ENGLISH, "file");
            archivo.setLocalizedLabel(SPANISH, "archivo");
            /**/
            descripcionArchivo.setLocalizedLabel(ENGLISH, "file description");
            descripcionArchivo.setLocalizedLabel(SPANISH, "descripción archivo");
            // </editor-fold>
        }

    }

    protected RegistrarResultadoConRastro registrarResultadoConRastro;

    @ProcessOperationClass
    public class RegistrarResultadoConRastro extends ProcessOperation {

        @InstanceReference
        protected EjecucionLineaPrueba ejecucion;

        @ParameterField(required = Kleenean.TRUE, linkedField = "rastro")
        protected RastroFuncion rastro;

        @ParameterField(required = Kleenean.FALSE, linkedField = "observaciones")
        protected StringParameter observaciones;

        @FileReference
        @ParameterField(required = Kleenean.FALSE, linkedField = "archivo")
        protected StringParameter archivo;

        @ParameterField(required = Kleenean.FALSE)
        protected StringParameter descripcionArchivo;

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of RegistrarResultadoConRastro's attributes">
            setLocalizedLabel(ENGLISH, "register result with trail");
            setLocalizedLabel(SPANISH, "registrar resultado con rastro");
            // </editor-fold>
        }

        @Override
        protected void settleParameters() {
            super.settleParameters();
            // <editor-fold defaultstate="collapsed" desc="localization of RegistrarResultadoConRastro's parameters">
            ejecucion.setLocalizedLabel(ENGLISH, "execution");
            ejecucion.setLocalizedLabel(SPANISH, "ejecución");
            /**/
            rastro.setLocalizedLabel(ENGLISH, "trail");
            rastro.setLocalizedLabel(SPANISH, "rastro");
            /**/
            observaciones.setLocalizedLabel(ENGLISH, "remarks");
            observaciones.setLocalizedLabel(SPANISH, "observaciones");
            /**/
            archivo.setLocalizedLabel(ENGLISH, "file");
            archivo.setLocalizedLabel(SPANISH, "archivo");
            archivo.setLocalizedTooltip(ENGLISH, "file");
            archivo.setLocalizedTooltip(SPANISH, "archivo");
            /**/
            descripcionArchivo.setLocalizedLabel(ENGLISH, "file description");
            descripcionArchivo.setLocalizedLabel(SPANISH, "descripción archivo");
            // </editor-fold>
        }

        protected Check check0101, check0102, check0103, check0104;

        protected Check check0201, check0202;

        @Override
        protected void settleExpressions() {
            super.settleExpressions();
            check0101 = ejecucion.condicionEjecucion.isEqualTo(condicionEjecucion.EJECUCION_PENDIENTE);
            check0102 = ejecucion.subprograma.isNull();
            check0103 = ejecucion.escenario.isNotNull();
            check0104 = ejecucion.escenario.isNotNull().implies(ejecucion.escenario.caso.funcion.isNotNull());
            check0201 = rastro.condicionEjeFun.isIn(
                (condicionEjecucion.EJECUTADO_SIN_ERRORES),
                (condicionEjecucion.EJECUTADO_CON_ERRORES),
                (condicionEjecucion.EJECUCION_CANCELADA)
            );
            check0202 = rastro.funcion.isEqualTo(ejecucion.escenario.caso.funcion);
            // <editor-fold defaultstate="collapsed" desc="localization of RegistrarResultadoConRastro's expressions">
            check0101.setLocalizedDescription(ENGLISH, "check0101");
            check0101.setLocalizedDescription(SPANISH, "check0101");
            check0101.setLocalizedErrorMessage(ENGLISH, "the result of the execution of the line is already registered");
            check0101.setLocalizedErrorMessage(SPANISH, "el resultado de la ejecución de la línea ya está registrado");
            /**/
            check0102.setLocalizedDescription(ENGLISH, "check0102");
            check0102.setLocalizedDescription(SPANISH, "check0102");
            check0102.setLocalizedErrorMessage(ENGLISH, "the line corresponds to the execution of a subprogram");
            check0102.setLocalizedErrorMessage(SPANISH, "la línea corresponde a la ejecución de un subprograma");
            /**/
            check0103.setLocalizedDescription(ENGLISH, "check0103");
            check0103.setLocalizedDescription(SPANISH, "check0103");
            check0103.setLocalizedErrorMessage(ENGLISH, "the line does not correspond to the execution of a scenario");
            check0103.setLocalizedErrorMessage(SPANISH, "la línea no corresponde a la ejecución de un escenario");
            /**/
            check0104.setLocalizedDescription(ENGLISH, "check0104");
            check0104.setLocalizedDescription(SPANISH, "check0104");
            check0104.setLocalizedErrorMessage(ENGLISH, "the line corresponds to the execution of a scenario whose case is not linked to a function");
            check0104.setLocalizedErrorMessage(SPANISH, "la línea corresponde a la ejecución de un escenario cuyo caso no está enlazado a una función");
            /**/
            check0201.setLocalizedDescription(ENGLISH, "check0201");
            check0201.setLocalizedDescription(SPANISH, "check0201");
            check0201.setLocalizedErrorMessage(ENGLISH, "the trail corresponds to an execution that has not been completed (is pending or in progress)");
            check0201.setLocalizedErrorMessage(SPANISH, "el rastro corresponde a una ejecución que no ha concluído (está pendiente o en progreso)");
            /**/
            check0202.setLocalizedDescription(ENGLISH, "check0202");
            check0202.setLocalizedDescription(SPANISH, "check0202");
            check0202.setLocalizedErrorMessage(ENGLISH, "the trail corresponds to a function different from the one linked to the case of the line scenario");
            check0202.setLocalizedErrorMessage(SPANISH, "el rastro corresponde a una función diferente a la enlazada al caso del escenario de la línea");
            // </editor-fold>
        }

        @Override
        protected void settleFilters() {
            super.settleFilters();
            ejecucion.setSearchQueryFilter(and(check0101, check0102, check0103, check0104));
            rastro.setSearchQueryFilter(and(check0201, check0202));
        }

    }

    protected RegistrarResultadoSinRastro registrarResultadoSinRastro;

    @ProcessOperationClass
    public class RegistrarResultadoSinRastro extends ProcessOperation {

        @InstanceReference
        protected EjecucionLineaPrueba ejecucion;

        @ParameterField(required = Kleenean.TRUE, linkedField = "condicionEjecucion")
        protected CondicionEjeFun condicionEjecucion;

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
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of RegistrarResultadoSinRastro's attributes">
            setLocalizedLabel(ENGLISH, "record result without a trail");
            setLocalizedLabel(SPANISH, "registrar resultado sin rastro");
            // </editor-fold>
        }

        @Override
        protected void settleParameters() {
            super.settleParameters();
            ejecucion.fechaHoraCondicionEjecucion.setCurrentValue(SpecialTemporalValue.CURRENT_TIMESTAMP);
            // <editor-fold defaultstate="collapsed" desc="localization of RegistrarResultadoSinRastro's parameters">
            ejecucion.setLocalizedLabel(ENGLISH, "execution");
            ejecucion.setLocalizedLabel(SPANISH, "ejecución");
            /**/
            condicionEjecucion.setLocalizedLabel(ENGLISH, "condition");
            condicionEjecucion.setLocalizedLabel(SPANISH, "condición ejecución");
            /**/
            resultados.setLocalizedLabel(ENGLISH, "results");
            resultados.setLocalizedLabel(SPANISH, "resultados");
            /**/
            observaciones.setLocalizedLabel(ENGLISH, "remarks");
            observaciones.setLocalizedLabel(SPANISH, "observaciones");
            /**/
            archivo.setLocalizedLabel(ENGLISH, "file");
            archivo.setLocalizedLabel(SPANISH, "archivo");
            archivo.setLocalizedTooltip(ENGLISH, "file");
            archivo.setLocalizedTooltip(SPANISH, "archivo");
            /**/
            descripcionArchivo.setLocalizedLabel(ENGLISH, "file description");
            descripcionArchivo.setLocalizedLabel(SPANISH, "descripción archivo");
            // </editor-fold>
        }

        protected Check check0101, check0102, check0103, check0104;

        @Override
        protected void settleExpressions() {
            super.settleExpressions();
            check0101 = ejecucion.condicionEjecucion.isEqualTo(condicionEjecucion.EJECUCION_PENDIENTE);
            check0102 = ejecucion.subprograma.isNull();
            check0103 = ejecucion.escenario.isNotNull();
            check0104 = ejecucion.escenario.isNotNull().implies(ejecucion.escenario.caso.funcion.isNull());
            // <editor-fold defaultstate="collapsed" desc="localization of RegistrarResultadoSinRastro's expressions">
            check0101.setLocalizedDescription(ENGLISH, "check0101");
            check0101.setLocalizedDescription(SPANISH, "check0101");
            check0101.setLocalizedErrorMessage(ENGLISH, "the result of the execution of the line is already registered");
            check0101.setLocalizedErrorMessage(SPANISH, "el resultado de la ejecución de la línea ya está registrado");
            /**/
            check0102.setLocalizedDescription(ENGLISH, "check0102");
            check0102.setLocalizedDescription(SPANISH, "check0102");
            check0102.setLocalizedErrorMessage(ENGLISH, "the line corresponds to the execution of a subprogram");
            check0102.setLocalizedErrorMessage(SPANISH, "la línea corresponde a la ejecución de un subprograma");
            /**/
            check0103.setLocalizedDescription(ENGLISH, "check0103");
            check0103.setLocalizedDescription(SPANISH, "check0103");
            check0103.setLocalizedErrorMessage(ENGLISH, "the line does not correspond to the execution of a scenario");
            check0103.setLocalizedErrorMessage(SPANISH, "la línea no corresponde a la ejecución de un escenario");
            /**/
            check0104.setLocalizedDescription(ENGLISH, "check0104");
            check0104.setLocalizedDescription(SPANISH, "check0104");
            check0104.setLocalizedErrorMessage(ENGLISH, "the line corresponds to the execution of a scenario whose case is linked to a function");
            check0104.setLocalizedErrorMessage(SPANISH, "la línea corresponde a la ejecución de un escenario cuyo caso está enlazado a una función");
            // </editor-fold>
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

    @ProcessOperationClass
    public class ReversarResultado extends ProcessOperation {

        @InstanceReference
//      @Allocation(maxDepth = 1, maxRound = 0)
        protected EjecucionLineaPrueba ejecucion;

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of ReversarResultado's attributes">
            setLocalizedLabel(ENGLISH, "reverse result");
            setLocalizedLabel(SPANISH, "reversar resultado");
            // </editor-fold>
        }

        @Override
        protected void settleParameters() {
            super.settleParameters();
            // <editor-fold defaultstate="collapsed" desc="localization of ReversarResultado's parameters">
            ejecucion.setLocalizedLabel(ENGLISH, "execution");
            ejecucion.setLocalizedLabel(SPANISH, "ejecución");
            // </editor-fold>
        }

        protected Check check0101, check0102, check0103;

        @Override
        protected void settleExpressions() {
            super.settleExpressions();
            check0101 = ejecucion.condicionEjecucion.isNotEqualTo(condicionEjecucion.EJECUCION_PENDIENTE);
            check0102 = ejecucion.subprograma.isNull();
            check0103 = ejecucion.escenario.isNotNull();
            // <editor-fold defaultstate="collapsed" desc="localization of ReversarResultado's expressions">
            check0101.setLocalizedDescription(ENGLISH, "check0101");
            check0101.setLocalizedDescription(SPANISH, "check0101");
            check0101.setLocalizedErrorMessage(ENGLISH, "the result of the execution of the line is not registered");
            check0101.setLocalizedErrorMessage(SPANISH, "el resultado de la ejecución de la línea no está registrado");
            /**/
            check0102.setLocalizedDescription(ENGLISH, "check0102");
            check0102.setLocalizedDescription(SPANISH, "check0102");
            check0102.setLocalizedErrorMessage(ENGLISH, "the line corresponds to the execution of a subprogram");
            check0102.setLocalizedErrorMessage(SPANISH, "la línea corresponde a la ejecución de un subprograma");
            /**/
            check0103.setLocalizedDescription(ENGLISH, "check0103");
            check0103.setLocalizedDescription(SPANISH, "check0103");
            check0103.setLocalizedErrorMessage(ENGLISH, "the line does not correspond to the execution of a scenario");
            check0103.setLocalizedErrorMessage(SPANISH, "la línea no corresponde a la ejecución de un escenario");
            // </editor-fold>
        }

        @Override
        protected void settleFilters() {
            super.settleFilters();
            ejecucion.setSearchQueryFilter(and(check0101, check0102, check0103));
        }

    }

}
