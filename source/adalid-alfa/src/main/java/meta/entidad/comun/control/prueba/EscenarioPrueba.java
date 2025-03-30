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
@EntityClass(independent = Kleenean.FALSE, resourceType = ResourceType.TESTING, resourceGender = ResourceGender.MASCULINE)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE, onload = SelectOnloadOption.EXECUTE)
@EntityInsertOperation(enabled = Kleenean.TRUE)
@EntityUpdateOperation(enabled = Kleenean.TRUE)
@EntityDeleteOperation(enabled = Kleenean.TRUE)
@EntityTableView(enabled = Kleenean.TRUE, inserts = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.TRUE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.TRUE)
//@EntityDataGen(start = 1, stop = 100)
public class EscenarioPrueba extends PersistentEntityBase {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public EscenarioPrueba(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Override
    protected void addAllocationStrings() {
        super.addAllocationStrings();
        super.addAllocationStrings(
            "caso.propietario"
        );
    }

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of EscenarioPrueba's attributes">
        setLocalizedLabel(ENGLISH, "test case scenario");
        setLocalizedLabel(SPANISH, "escenario de caso de prueba");
        setLocalizedShortLabel(ENGLISH, "scenario");
        setLocalizedShortLabel(SPANISH, "escenario");
        setLocalizedCollectionLabel(ENGLISH, "Test Case Scenarios");
        setLocalizedCollectionLabel(SPANISH, "Escenarios de Casos de Prueba");
        setLocalizedCollectionShortLabel(ENGLISH, "Scenarios");
        setLocalizedCollectionShortLabel(SPANISH, "Escenarios");
        // </editor-fold>
    }

    @BusinessKey
    @StringField(maxLength = 70)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.FALSE, defaultCondition = DefaultCondition.UNCONDITIONALLY) //, defaultCheckpoint = Checkpoint.USER_INTERFACE)
    public StringProperty codigo;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(create = Kleenean.TRUE)
    public IntegerProperty numero;

    @NameProperty
    public StringProperty nombre;

    @DescriptionProperty
    @PropertyField(create = Kleenean.TRUE)
    @StringField(maxLength = 0)
    public StringProperty descripcion;

    @PropertyField(create = Kleenean.TRUE)
    @StringField(maxLength = 0)
    public StringProperty comentarios;

    @PropertyField(create = Kleenean.TRUE)
    @StringField(maxLength = 0)
    public StringProperty precondiciones;

    @PropertyField(create = Kleenean.TRUE)
    @StringField(maxLength = 0)
    public StringProperty validaciones;

    @PropertyField(create = Kleenean.TRUE)
    @StringField(maxLength = 0)
    public StringProperty acciones;

    @PropertyField(create = Kleenean.TRUE)
    @StringField(maxLength = 0)
    public StringProperty poscondiciones;

    @PropertyField(create = Kleenean.TRUE)
    @StringField(maxLength = 0)
    public StringProperty invariantes;

    @PropertyField(create = Kleenean.TRUE)
    @StringField(maxLength = 0)
    public StringProperty pseudocodigo;

    @PropertyField(create = Kleenean.TRUE)
    @StringField(maxLength = 0)
    public StringProperty resultados;

    @ColumnField(nullable = Kleenean.FALSE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(create = Kleenean.TRUE, update = Kleenean.TRUE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public TipoResultadoPrueba tipoResultado;

    @ColumnField(nullable = Kleenean.FALSE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.TABLE_AND_DETAIL, viewSequence = 10)
    @PropertyField(create = Kleenean.TRUE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public CasoPrueba caso;

    @Override
    protected void settleProperties() {
        super.settleProperties();
        codigo.setDefaultValue(concat(caso.codigo, HYPHEN, numero));
        numero.setMinValue(1);
        numero.setMaxValue(10000);
        tipoResultado.setInitialValue(tipoResultado.EXITO);
        tipoResultado.setDefaultValue(tipoResultado.EXITO);
        // <editor-fold defaultstate="collapsed" desc="localization of EscenarioPrueba's properties">
        codigo.setLocalizedLabel(ENGLISH, "test case scenario code");
        codigo.setLocalizedLabel(SPANISH, "código del escenario de caso de prueba");
        codigo.setLocalizedShortLabel(ENGLISH, "code");
        codigo.setLocalizedShortLabel(SPANISH, "código");
        /**/
        numero.setLocalizedLabel(ENGLISH, "number");
        numero.setLocalizedLabel(SPANISH, "número");
        /**/
        nombre.setLocalizedLabel(ENGLISH, "test case scenario name");
        nombre.setLocalizedLabel(SPANISH, "nombre del escenario de caso de prueba");
        nombre.setLocalizedShortLabel(ENGLISH, "name");
        nombre.setLocalizedShortLabel(SPANISH, "nombre");
        /**/
        descripcion.setLocalizedLabel(ENGLISH, "test case scenario description");
        descripcion.setLocalizedLabel(SPANISH, "descripción del escenario de caso de prueba");
        descripcion.setLocalizedShortLabel(ENGLISH, "description");
        descripcion.setLocalizedShortLabel(SPANISH, "descripción");
        /**/
        comentarios.setLocalizedLabel(ENGLISH, "comments");
        comentarios.setLocalizedLabel(SPANISH, "comentarios");
        /**/
        precondiciones.setLocalizedLabel(ENGLISH, "pre-conditions");
        precondiciones.setLocalizedLabel(SPANISH, "precondiciones");
        /**/
        validaciones.setLocalizedLabel(ENGLISH, "validations");
        validaciones.setLocalizedLabel(SPANISH, "validaciones");
        /**/
        acciones.setLocalizedLabel(ENGLISH, "actions");
        acciones.setLocalizedLabel(SPANISH, "acciones");
        /**/
        poscondiciones.setLocalizedLabel(ENGLISH, "post-conditions");
        poscondiciones.setLocalizedLabel(SPANISH, "poscondiciones");
        /**/
        invariantes.setLocalizedLabel(ENGLISH, "invariants");
        invariantes.setLocalizedLabel(SPANISH, "invariantes");
        /**/
        pseudocodigo.setLocalizedLabel(ENGLISH, "pseudo-code");
        pseudocodigo.setLocalizedLabel(SPANISH, "pseudocódigo");
        /**/
        resultados.setLocalizedLabel(ENGLISH, "results");
        resultados.setLocalizedLabel(SPANISH, "resultados");
        /**/
        tipoResultado.setLocalizedLabel(ENGLISH, "result type");
        tipoResultado.setLocalizedLabel(SPANISH, "tipo resultado");
        /**/
        caso.setLocalizedLabel(ENGLISH, "case");
        caso.setLocalizedLabel(SPANISH, "caso");
        // </editor-fold>
    }

    @Override
    protected void settleLinks() {
        super.settleLinks();
        linkForeignOwnerProperty(caso.propietario);
    }

    Key key1;

    @Override
    protected void settleKeys() {
        super.settleKeys();
        key1.newKeyField(caso, numero, id);
        key1.setUnique(true);
        /**/
        setOrderBy(key1);
        /**/
    }

    protected Tab tab1, tab2, tab3;

    @Override
    protected void settleTabs() {
        super.settleTabs();
        tab1.newTabField(caso, numero, descripcion, comentarios);
        tab2.newTabField(precondiciones, validaciones, acciones, poscondiciones, invariantes, pseudocodigo);
        tab3.newTabField(resultados, tipoResultado);
        // <editor-fold defaultstate="collapsed" desc="localization of EscenarioPrueba's tabs">
        tab1.setLocalizedLabel(ENGLISH, "basic data");
        tab1.setLocalizedLabel(SPANISH, "datos básicos");
        /**/
        tab2.setLocalizedLabel(ENGLISH, "additional data");
        tab2.setLocalizedLabel(SPANISH, "datos adicionales");
        /**/
        tab3.setLocalizedLabel(ENGLISH, "expected result");
        tab3.setLocalizedLabel(SPANISH, "resultado esperado");
        // </editor-fold>
    }

    protected Cargar cargar;

    @ProcessOperationClass
    public class Cargar extends ProcessOperation {

        @InstanceReference
        protected EscenarioPrueba escenario;

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
            escenario.setLocalizedLabel(ENGLISH, "scenario");
            escenario.setLocalizedLabel(SPANISH, "escenario");
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
