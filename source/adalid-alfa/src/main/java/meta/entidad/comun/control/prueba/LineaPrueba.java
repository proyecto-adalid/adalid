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

/**
 * @author Jorge Campins
 */
@EntityClass(independent = Kleenean.FALSE, resourceType = ResourceType.TESTING, resourceGender = ResourceGender.FEMININE)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE, onload = SelectOnloadOption.EXECUTE)
@EntityInsertOperation(enabled = Kleenean.TRUE)
@EntityUpdateOperation(enabled = Kleenean.TRUE)
@EntityDeleteOperation(enabled = Kleenean.TRUE)
@EntityTableView(enabled = Kleenean.TRUE, inserts = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.TRUE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.TRUE)
@EntityTriggers(afterCheck = Kleenean.TRUE)
//@EntityDataGen(start = 1, stop = 100)
public class LineaPrueba extends PersistentEntityBase {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public LineaPrueba(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Override
    protected void addAllocationStrings() {
        super.addAllocationStrings();
        super.addAllocationStrings(
            "programa.caso",
            "programa.propietario",
            "escenario.caso"
        );
    }

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of LineaPrueba's attributes">
        setLocalizedLabel(ENGLISH, "test program line");
        setLocalizedLabel(SPANISH, "línea de programa de prueba");
        setLocalizedShortLabel(ENGLISH, "líne");
        setLocalizedShortLabel(SPANISH, "línea");
        setLocalizedCollectionLabel(ENGLISH, "Test Program Lines");
        setLocalizedCollectionLabel(SPANISH, "Líneas de Programas de Prueba");
        setLocalizedCollectionShortLabel(ENGLISH, "Lines");
        setLocalizedCollectionShortLabel(SPANISH, "Líneas");
        // </editor-fold>
    }

    @BusinessKey
    @StringField(maxLength = 70)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.FALSE, defaultCondition = DefaultCondition.UNCONDITIONALLY, defaultCheckpoint = Checkpoint.USER_INTERFACE)
    public StringProperty codigo;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(create = Kleenean.TRUE)
    public IntegerProperty numero;

    @ColumnField(nullable = Kleenean.FALSE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.TABLE_AND_DETAIL)
    @PropertyField(create = Kleenean.TRUE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public ProgramaPrueba programa;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(create = Kleenean.TRUE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public EscenarioPrueba escenario;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(create = Kleenean.TRUE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public ProgramaPrueba subprograma;

    @PropertyField(create = Kleenean.TRUE)
    @StringField(maxLength = 0)
    public StringProperty condiciones;

    @PropertyField(create = Kleenean.TRUE)
    @StringField(maxLength = 0)
    public StringProperty repeticiones;

    @PropertyField(create = Kleenean.TRUE)
    @StringField(maxLength = 0)
    public StringProperty comentarios;

    @Override
    protected void settleProperties() {
        super.settleProperties();
        codigo.setDefaultValue(programa.codigo.concat("-").concat(numero));
        numero.setMinValue(1);
        numero.setMaxValue(10000);
        // <editor-fold defaultstate="collapsed" desc="localization of LineaPrueba's properties">
        codigo.setLocalizedLabel(ENGLISH, "test program line code");
        codigo.setLocalizedLabel(SPANISH, "código de la línea de programa de prueba");
        codigo.setLocalizedShortLabel(ENGLISH, "code");
        codigo.setLocalizedShortLabel(SPANISH, "código");
        /**/
        numero.setLocalizedLabel(ENGLISH, "number");
        numero.setLocalizedLabel(SPANISH, "número");
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
        // </editor-fold>
    }

    @Override
    protected void settleLinks() {
        super.settleLinks();
        linkForeignOwnerProperty(programa.propietario);
    }

    Key key1;

    @Override
    protected void settleKeys() {
        super.settleKeys();
        key1.newKeyField(programa, numero, id);
        key1.setUnique(true);
        /**/
        setOrderBy(key1);
        /**/
    }

    protected Tab tab1, tab2;

    @Override
    protected void settleTabs() {
        super.settleTabs();
        tab1.newTabField(codigo, programa, numero, escenario, subprograma);
        tab2.newTabField(condiciones, repeticiones, comentarios);
        // <editor-fold defaultstate="collapsed" desc="localization of LineaPrueba's tabs">
        tab1.setLocalizedLabel(ENGLISH, "basic data");
        tab1.setLocalizedLabel(SPANISH, "datos básicos");
        /**/
        tab2.setLocalizedLabel(ENGLISH, "additional data");
        tab2.setLocalizedLabel(SPANISH, "datos adicionales");
        // </editor-fold>
    }

    protected Check check0101, check0102, check0103, check0104, check0105;

    @Override
    protected void settleExpressions() {
        super.settleExpressions();
        /**/
        check0101 = escenario.isNotNull().xor(subprograma.isNotNull());
        check0102 = programa.caso.isNotNull().implies(escenario.isNotNull());
        check0103 = programa.caso.isNotNull().and(escenario.isNotNull()).implies(programa.caso.isEqualTo(escenario.caso));
        check0104 = programa.caso.isNotNull().implies(subprograma.isNull());
        check0105 = subprograma.isNullOrNotEqualTo(programa);
        // <editor-fold defaultstate="collapsed" desc="localization of LineaPrueba's expressions">
        check0101.setLocalizedDescription(ENGLISH, "scenario and subprogram are mutually exclusive");
        check0101.setLocalizedDescription(SPANISH, "escenario y subprograma son mutuamente excluyentes");
        check0101.setLocalizedErrorMessage(ENGLISH, "scenario and subprogram are mutually exclusive");
        check0101.setLocalizedErrorMessage(SPANISH, "escenario y subprograma son mutuamente excluyentes");
        /**/
        check0102.setLocalizedDescription(ENGLISH, "the scenario is required when the program is linked to a test case");
        check0102.setLocalizedDescription(SPANISH, "el escenario es requerido cuando el programa está enlazado a un caso de prueba");
        check0102.setLocalizedErrorMessage(ENGLISH, "the scenario is required when the program is linked to a test case");
        check0102.setLocalizedErrorMessage(SPANISH, "el escenario es requerido porque el programa está enlazado a un caso de prueba");
        /**/
        check0103.setLocalizedDescription(ENGLISH, "the scenario must be from the same test case to which the program is linked");
        check0103.setLocalizedDescription(SPANISH, "el escenario debe ser del mismo caso de prueba al cual está enlazado el programa");
        check0103.setLocalizedErrorMessage(ENGLISH, "the scenario must be from the same test case to which the program is linked");
        check0103.setLocalizedErrorMessage(SPANISH, "el escenario debe ser del mismo caso de prueba al cual está enlazado el programa");
        /**/
        check0104.setLocalizedDescription(ENGLISH, "the line only supports references to linked case scenarios");
        check0104.setLocalizedDescription(SPANISH, "la línea solo admite referencias a escenarios del caso enlazado");
        check0104.setLocalizedErrorMessage(ENGLISH, "the reference to the subroutine is not valid because the program is linked to a test case "
            + "and only supports references to linked case scenarios");
        check0104.setLocalizedErrorMessage(SPANISH, "la referencia al subprograma no es válida porque el programa está enlazado a un caso de prueba "
            + "y solo admite referencias a escenarios del caso enlazado");
        /**/
        check0105.setLocalizedDescription(ENGLISH, "the subprogram is not equal to the program");
        check0105.setLocalizedDescription(SPANISH, "el subprograma no es igual al programa");
        check0105.setLocalizedErrorMessage(ENGLISH, "the reference to the subprogram is not valid because the subprogram is equal to the program");
        check0105.setLocalizedErrorMessage(SPANISH, "la referencia al subprograma no es válida porque el subprograma es igual al programa");
        // </editor-fold>
    }

    @Override
    protected void settleFilters() {
        super.settleFilters();
        escenario.setRequiringFilter(programa.caso.isNotNull());
    }

    protected Cargar cargar;

    @ProcessOperationClass
    public class Cargar extends ProcessOperation {

        @InstanceReference
        protected LineaPrueba linea;

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
            linea.setLocalizedLabel(ENGLISH, "line");
            linea.setLocalizedLabel(SPANISH, "línea");
            /**/
            archivo.setLocalizedLabel(ENGLISH, "file");
            archivo.setLocalizedLabel(SPANISH, "archivo");
            /**/
            descripcionArchivo.setLocalizedLabel(ENGLISH, "file description");
            descripcionArchivo.setLocalizedLabel(SPANISH, "descripción archivo");
            // </editor-fold>
        }

    }

}
