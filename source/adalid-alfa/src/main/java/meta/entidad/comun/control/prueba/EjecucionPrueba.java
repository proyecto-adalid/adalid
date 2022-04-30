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
import meta.entidad.comun.configuracion.basica.CondicionEjeFun;
import meta.entidad.comun.control.acceso.Usuario;

/**
 * @author Jorge Campins
 */
@EntityClass(independent = Kleenean.TRUE, resourceType = ResourceType.TESTING, resourceGender = ResourceGender.FEMININE)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE, onload = SelectOnloadOption.EXECUTE)
@EntityInsertOperation(enabled = Kleenean.TRUE)
@EntityUpdateOperation(enabled = Kleenean.TRUE)
@EntityDeleteOperation(enabled = Kleenean.TRUE)
@EntityTableView(enabled = Kleenean.TRUE, inserts = Kleenean.FALSE)
@EntityDetailView(enabled = Kleenean.TRUE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.TRUE)
@EntityTriggers(afterValue = Kleenean.TRUE)
//@EntityDataGen(start = 1, stop = 100)
public class EjecucionPrueba extends PersistentEntityBase {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public EjecucionPrueba(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of EjecucionPrueba's attributes">
        setLocalizedLabel(ENGLISH, "test program execution");
        setLocalizedLabel(SPANISH, "ejecución de programa de prueba");
        setLocalizedShortLabel(ENGLISH, "program execution");
        setLocalizedShortLabel(SPANISH, "ejecución de programa");
        setLocalizedCollectionLabel(ENGLISH, "Test Program Executions");
        setLocalizedCollectionLabel(SPANISH, "Ejecuciones de Programas de Prueba");
        setLocalizedCollectionShortLabel(ENGLISH, "Program Executions");
        setLocalizedCollectionShortLabel(SPANISH, "Ejecuciones de Programas");
        // </editor-fold>
    }

    @BusinessKey
    @StringField(maxLength = 80)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.FALSE, defaultCondition = DefaultCondition.UNCONDITIONALLY, defaultCheckpoint = Checkpoint.USER_INTERFACE)
    public StringProperty codigo;

    @NameProperty
    @PropertyField(table = Kleenean.FALSE)
    public StringProperty nombre;

    @DescriptionProperty
    @PropertyField(create = Kleenean.TRUE)
    @StringField(maxLength = 0)
    public StringProperty descripcion;

    @PropertyField(create = Kleenean.TRUE)
    @StringField(maxLength = 0)
    public StringProperty comentarios;

    @ColumnField(nullable = Kleenean.FALSE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(required = Kleenean.FALSE, create = Kleenean.TRUE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public AmbientePrueba ambiente;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.TABLE)
    @PropertyField(create = Kleenean.TRUE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public PaquetePrueba paquete;

    @ColumnField(nullable = Kleenean.FALSE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.TABLE_AND_DETAIL)
    @PropertyField(create = Kleenean.TRUE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public ProgramaPrueba programa;

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

    @OwnerProperty
    @ColumnField(nullable = Kleenean.FALSE)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.FALSE, report = Kleenean.FALSE) //, defaultCheckpoint = Checkpoint.USER_INTERFACE)
    public Usuario responsable;

    @Override
    protected void settleProperties() {
        super.settleProperties();
        codigo.setDefaultValue(programa.codigo.concat("-").concat(id));
        condicionEjecucion.setInitialValue(condicionEjecucion.EJECUCION_PENDIENTE);
        condicionEjecucion.setDefaultValue(condicionEjecucion.EJECUCION_PENDIENTE);
        fechaHoraCondicionEjecucion.setInitialValue(SpecialTemporalValue.CURRENT_TIMESTAMP);
        fechaHoraCondicionEjecucion.setDefaultValue(SpecialTemporalValue.CURRENT_TIMESTAMP);
        // <editor-fold defaultstate="collapsed" desc="localization of EjecucionPrueba's properties">
        codigo.setLocalizedLabel(ENGLISH, "test program execution code");
        codigo.setLocalizedLabel(SPANISH, "código de la ejecución de programa de prueba");
        codigo.setLocalizedShortLabel(ENGLISH, "code");
        codigo.setLocalizedShortLabel(SPANISH, "código");
        /**/
        nombre.setLocalizedLabel(ENGLISH, "test program execution name");
        nombre.setLocalizedLabel(SPANISH, "nombre de la ejecución de programa de prueba");
        nombre.setLocalizedShortLabel(ENGLISH, "name");
        nombre.setLocalizedShortLabel(SPANISH, "nombre");
        /**/
        descripcion.setLocalizedLabel(ENGLISH, "test program execution description");
        descripcion.setLocalizedLabel(SPANISH, "descripción de la ejecución de programa de prueba");
        descripcion.setLocalizedShortLabel(ENGLISH, "description");
        descripcion.setLocalizedShortLabel(SPANISH, "descripción");
        /**/
        comentarios.setLocalizedLabel(ENGLISH, "comments");
        comentarios.setLocalizedLabel(SPANISH, "comentarios");
        /**/
        ambiente.setLocalizedLabel(ENGLISH, "environment");
        ambiente.setLocalizedLabel(SPANISH, "ambiente");
        /**/
        paquete.setLocalizedLabel(ENGLISH, "package");
        paquete.setLocalizedLabel(SPANISH, "paquete");
        /**/
        programa.setLocalizedLabel(ENGLISH, "program");
        programa.setLocalizedLabel(SPANISH, "programa");
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
        responsable.setLocalizedLabel(ENGLISH, "responsible for the execution");
        responsable.setLocalizedLabel(SPANISH, "responsable de la ejecución");
        responsable.setLocalizedShortLabel(ENGLISH, "responsible");
        responsable.setLocalizedShortLabel(SPANISH, "responsable");
        // </editor-fold>
    }

    protected Tab tab1, tab2, tab3;

    @Override
    protected void settleTabs() {
        super.settleTabs();
        tab1.newTabField(descripcion, comentarios);
        tab2.newTabField(ambiente, paquete, programa, responsable);
        tab3.newTabField(condicionEjecucion, fechaHoraCondicionEjecucion, tipoResultado, resultadoEsperado);
        // <editor-fold defaultstate="collapsed" desc="localization of EjecucionPrueba's tabs">
        tab1.setLocalizedLabel(ENGLISH, "basic data");
        tab1.setLocalizedLabel(SPANISH, "datos básicos");
        /**/
        tab2.setLocalizedLabel(ENGLISH, "links");
        tab2.setLocalizedLabel(SPANISH, "enlaces");
        /**/
        tab3.setLocalizedLabel(ENGLISH, "condition");
        tab3.setLocalizedLabel(SPANISH, "condición");
        // </editor-fold>
    }

    protected Cargar cargar;

    @ProcessOperationClass
    public class Cargar extends ProcessOperation {

        @InstanceReference
//      @Allocation(maxDepth = 1, maxRound = 0)
        protected EjecucionPrueba ejecucion;

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

    protected AsignarResponsable asignarResponsable;

    @ProcessOperationClass
    public class AsignarResponsable extends ProcessOperation {

        @InstanceReference
//      @Allocation(maxDepth = 1, maxRound = 0)
        protected EjecucionPrueba ejecucion;

        @ParameterField(required = Kleenean.TRUE, linkedField = "responsable")
        protected Usuario responsable;

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            // <editor-fold defaultstate="collapsed" desc="localization of AsignarResponsable's attributes">
            setLocalizedLabel(ENGLISH, "assign responsible");
            setLocalizedLabel(SPANISH, "asignar responsable");
            // </editor-fold>
        }

        @Override
        protected void settleParameters() {
            super.settleParameters();
            // <editor-fold defaultstate="collapsed" desc="localization of AsignarResponsable's parameters">
            ejecucion.setLocalizedLabel(ENGLISH, "execution");
            ejecucion.setLocalizedLabel(SPANISH, "ejecución");
            /**/
            responsable.setLocalizedLabel(ENGLISH, "responsible");
            responsable.setLocalizedLabel(SPANISH, "responsable");
            // </editor-fold>
        }

    }

}
