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
import adalid.core.Tab;
import adalid.core.Transition;
import adalid.core.annotations.Allocation;
import adalid.core.annotations.BusinessKey;
import adalid.core.annotations.ColumnField;
import adalid.core.annotations.EntityClass;
import adalid.core.annotations.EntityConsoleView;
import adalid.core.annotations.EntityDeleteOperation;
import adalid.core.annotations.EntityDetailView;
import adalid.core.annotations.EntityInsertOperation;
import adalid.core.annotations.EntitySelectOperation;
import adalid.core.annotations.EntityTableView;
import adalid.core.annotations.EntityTreeView;
import adalid.core.annotations.EntityTriggers;
import adalid.core.annotations.EntityUpdateOperation;
import adalid.core.annotations.ForeignKey;
import adalid.core.annotations.InactiveIndicator;
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
import adalid.core.enums.OperationAccess;
import adalid.core.enums.ResourceGender;
import adalid.core.enums.ResourceType;
import adalid.core.enums.SpecialCharacterValue;
import adalid.core.interfaces.Artifact;
import adalid.core.interfaces.Segment;
import adalid.core.interfaces.State;
import adalid.core.parameters.StringParameter;
import adalid.core.properties.BooleanProperty;
import adalid.core.properties.IntegerProperty;
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
@EntityTriggers(afterValue = Kleenean.TRUE)
public class Usuario extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    @Deprecated
    private Usuario() {
        this(null, null);
    }

    public Usuario(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public LongProperty idUsuario;

    @VersionProperty
    public LongProperty versionUsuario;

    @BusinessKey
    @PropertyField(update = Kleenean.FALSE)
    public StringProperty codigoUsuario;

    @NameProperty
    public StringProperty nombreUsuario;

    @PropertyField(auditable = Kleenean.FALSE, password = Kleenean.TRUE, hidden = Kleenean.TRUE, filter = Kleenean.FALSE)
    @StringField(maxLength = 32)
    public StringProperty passwordUsuario;

    @StringField(maxLength = 100)
    @PropertyField(table = Kleenean.FALSE, report = Kleenean.FALSE)
    public StringProperty correoElectronico;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public BooleanProperty esSuperUsuario;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public BooleanProperty esUsuarioEspecial;

    @InactiveIndicator
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public BooleanProperty esUsuarioInactivo;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, hidden = Kleenean.TRUE)
    public BooleanProperty esUsuarioModificado;

    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(navigability = Navigability.UNIDIRECTIONAL, view = MasterDetailView.NONE)
    @Allocation(maxDepth = 1, maxRound = 0)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.FALSE, report = Kleenean.FALSE)
    public Usuario idUsuarioSupervisor;

    @ColumnField(nullable = Kleenean.FALSE)
    public IntegerProperty limiteArchivoDetalle;

    @ColumnField(nullable = Kleenean.FALSE)
    public IntegerProperty limiteArchivoResumen;

    @ColumnField(nullable = Kleenean.FALSE)
    public IntegerProperty limiteInformeDetalle;

    @ColumnField(nullable = Kleenean.FALSE)
    public IntegerProperty limiteInformeResumen;

    @ColumnField(nullable = Kleenean.FALSE)
    public IntegerProperty limiteInformeGrafico;

    protected Tab tab1, tab2;

    public Instance ADMINISTRADOR;

    public Instance AUDITOR;

    public Instance OPERADOR;

    private Segment usuarioActual, demasUsuarios;

    private Segment usuariosEspeciales, usuariosOrdinarios;

    private Segment superUsuarios, usuariosEstandar;

    private Segment usuariosActivos, usuariosInactivos;

    protected State eliminable, modificable, designableComoSuper, superAnulable, desactivable, reactivable;

    protected Transition modificacion, designacion, anulacion, desactivacion, reactivacion;

    protected DesignarSuper designarSuper;

    protected AnularSuper anularSuper;

    protected Desactivar desactivar;

    protected Reactivar reactivar;

    protected Sincronizar sincronizar;

    protected AsignarPassword asignarPassword;

    protected AsignarSupervisor asignarSupervisor;

    protected CambiarPassword cambiarPassword;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
        setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        setDefaultLabel("usuario");
//      setDefaultShortLabel("usuario");
        setDefaultCollectionLabel("Usuarios");
//      setDefaultCollectionShortLabel("Usuarios");
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        esSuperUsuario.setInitialValue(false);
        esSuperUsuario.setDefaultValue(false);
        esUsuarioEspecial.setInitialValue(false);
        esUsuarioEspecial.setDefaultValue(false);
        esUsuarioInactivo.setInitialValue(false);
        esUsuarioInactivo.setDefaultValue(false);
        esUsuarioModificado.setInitialValue(false);
        esUsuarioModificado.setDefaultValue(false);
        idUsuarioSupervisor.setDefaultLabel("usuario supervisor");
        idUsuarioSupervisor.setDefaultShortLabel("supervisor");
        idUsuarioSupervisor.setDefaultTooltip("código del usuario supervisor");
        idUsuarioSupervisor.setDefaultDescription("usuario que supervisa a este usuario");
        limiteArchivoDetalle.setInitialValue(10000);
        limiteArchivoDetalle.setDefaultValue(10000);
        limiteArchivoDetalle.setMinValue(0);
        limiteArchivoDetalle.setMaxValue(1000000);
        limiteArchivoDetalle.setDefaultTooltip("límite de filas para archivos de tipo \"Detalle\"");
        limiteArchivoDetalle.setDefaultDescription("límite de filas al guardar los resultados de la consulta, "
            + "mediante vistas definidas por el usuario, en archivos de tipo \"Detalle\"; "
            + "si es 0 el usuario no tiene límite");
        limiteArchivoResumen.setInitialValue(10000);
        limiteArchivoResumen.setDefaultValue(10000);
        limiteArchivoResumen.setMinValue(0);
        limiteArchivoResumen.setMaxValue(1000000);
        limiteArchivoResumen.setDefaultTooltip("límite de filas para archivos de tipo \"Resumen\"");
        limiteArchivoResumen.setDefaultDescription("límite de filas al guardar los resultados de la consulta, "
            + "mediante vistas definidas por el usuario, en archivos de tipo \"Resumen\"; "
            + "si es 0 el usuario no tiene límite");
        limiteInformeDetalle.setInitialValue(10000);
        limiteInformeDetalle.setDefaultValue(10000);
        limiteInformeDetalle.setMinValue(0);
        limiteInformeDetalle.setMaxValue(1000000);
        limiteInformeDetalle.setDefaultTooltip("límite de filas para informes de tipo \"Detalle\"");
        limiteInformeDetalle.setDefaultDescription("límite de filas al guardar los resultados de la consulta, "
            + "mediante vistas definidas por el usuario, en informes de tipo \"Detalle\"; "
            + "si es 0 el usuario no tiene límite");
        limiteInformeResumen.setInitialValue(10000);
        limiteInformeResumen.setDefaultValue(10000);
        limiteInformeResumen.setMinValue(0);
        limiteInformeResumen.setMaxValue(1000000);
        limiteInformeResumen.setDefaultTooltip("límite de filas para informes de tipo \"Resumen\"");
        limiteInformeResumen.setDefaultDescription("límite de filas al guardar los resultados de la consulta, "
            + "mediante vistas definidas por el usuario, en informes de tipo \"Resumen\"; "
            + "si es 0 el usuario no tiene límite");
        limiteInformeGrafico.setInitialValue(10000);
        limiteInformeGrafico.setDefaultValue(10000);
        limiteInformeGrafico.setMinValue(0);
        limiteInformeGrafico.setMaxValue(1000000);
        limiteInformeGrafico.setDefaultTooltip("límite de filas para informes de tipo \"Gráfico\"");
        limiteInformeGrafico.setDefaultDescription("límite de filas al guardar los resultados de la consulta, "
            + "mediante vistas definidas por el usuario, en informes de tipo \"Gráfico\"; "
            + "si es 0 el usuario no tiene límite");
//
//      ArtifactWrapper.getSomeLabel() obtiene el valor por omisión de las etiquetas de código, nombre, etc.
//
//      idUsuarioSupervisor.codigoUsuario.setDefaultLabel("código supervisor");
//      idUsuarioSupervisor.codigoUsuario.setDefaultShortLabel("código supervisor");
//      idUsuarioSupervisor.nombreUsuario.setDefaultLabel("nombre supervisor");
//      idUsuarioSupervisor.nombreUsuario.setDefaultShortLabel("nombre supervisor");
    }

    @Override
    protected void settleTabs() {
        super.settleTabs();
        tab1.setDefaultLabel("general");
        tab1.newTabField(codigoUsuario, nombreUsuario, correoElectronico, esSuperUsuario, esUsuarioEspecial, esUsuarioInactivo, idUsuarioSupervisor);
        tab2.setDefaultLabel("límites");
        tab2.newTabField(limiteArchivoDetalle, limiteArchivoResumen, limiteInformeDetalle, limiteInformeResumen, limiteInformeGrafico);
    }

    @Override
    protected void settleInstances() {
        super.settleInstances();
        ADMINISTRADOR.newInstanceField(idUsuario, 101);
        ADMINISTRADOR.newInstanceField(codigoUsuario, "admin");
        ADMINISTRADOR.newInstanceField(nombreUsuario, "Administrador");
        ADMINISTRADOR.newInstanceField(passwordUsuario, "08b34b490b607342b007843310a284c6"); // sesamo
        ADMINISTRADOR.newInstanceField(esSuperUsuario, true);
        ADMINISTRADOR.newInstanceField(esUsuarioEspecial, true);
        AUDITOR.newInstanceField(idUsuario, 102);
        AUDITOR.newInstanceField(codigoUsuario, "audit");
        AUDITOR.newInstanceField(nombreUsuario, "Auditor");
        AUDITOR.newInstanceField(passwordUsuario, "08b34b490b607342b007843310a284c6"); // sesamo
        AUDITOR.newInstanceField(esSuperUsuario, false);
        AUDITOR.newInstanceField(esUsuarioEspecial, true);
        OPERADOR.newInstanceField(idUsuario, 103);
        OPERADOR.newInstanceField(codigoUsuario, "oper");
        OPERADOR.newInstanceField(nombreUsuario, "Operador");
        OPERADOR.newInstanceField(passwordUsuario, "08b34b490b607342b007843310a284c6"); // sesamo
        OPERADOR.newInstanceField(esSuperUsuario, false);
        OPERADOR.newInstanceField(esUsuarioEspecial, true);
    }

    @Override
    protected void settleExpressions() {
        super.settleExpressions();
        usuarioActual = codigoUsuario.isEqualTo(SpecialCharacterValue.CURRENT_USER_CODE);
        usuarioActual.setDefaultErrorMessage("el usuario no es su propio usuario");
        /**/
        demasUsuarios = codigoUsuario.isNotEqualTo(SpecialCharacterValue.CURRENT_USER_CODE);
        demasUsuarios.setDefaultErrorMessage("el usuario es su propio usuario");
        /**/
        usuariosEspeciales = esUsuarioEspecial.isTrue();
        usuariosEspeciales.setDefaultErrorMessage("el usuario no es un usuario especial");
        /**/
        usuariosOrdinarios = esUsuarioEspecial.isFalse();
        usuariosOrdinarios.setDefaultErrorMessage("el usuario es un usuario especial");
        /**/
        superUsuarios = esSuperUsuario.isTrue();
        superUsuarios.setDefaultErrorMessage("el usuario no es un súper usuario");
        /**/
        usuariosEstandar = esSuperUsuario.isFalse();
        usuariosEstandar.setDefaultErrorMessage("el usuario es un súper usuario");
        /**/
        usuariosActivos = esUsuarioInactivo.isFalse();
        usuariosActivos.setDefaultErrorMessage("el usuario es un usuario inactivo");
        /**/
        usuariosInactivos = esUsuarioInactivo.isTrue();
        usuariosInactivos.setDefaultErrorMessage("el usuario es un usuario activo");
        /**/
        eliminable = usuariosOrdinarios.and(demasUsuarios);
        modificable = eliminable.and(usuariosActivos);
        designableComoSuper = modificable.and(usuariosEstandar);
        superAnulable = modificable.and(superUsuarios);
        desactivable = modificable.isTrue();
        reactivable = eliminable.and(usuariosInactivos);
    }

    @Override
    protected void settleFilters() {
        super.settleFilters();
        setUpdateFilter(modificable);
        setDeleteFilter(eliminable);
    }

    @Override
    protected void settleTransitions() {
        super.settleTransitions();
        designacion.settle(designableComoSuper, superAnulable);
        anulacion.settle(superAnulable, designableComoSuper);
        desactivacion.settle(desactivable, reactivable);
        reactivacion.settle(reactivable, desactivable);
        modificacion.settle(modificable, modificable);
    }

    @Override
    protected void settleOperations() {
        super.settleOperations();
        designarSuper.addTransition(designacion);
        anularSuper.addTransition(anulacion);
        desactivar.addTransition(desactivacion);
        reactivar.addTransition(reactivacion);
        asignarPassword.addTransition(modificacion);
        asignarSupervisor.addTransition(modificacion);
    }

    // <editor-fold defaultstate="collapsed" desc="Operations">
    @OperationClass(access = OperationAccess.PROTECTED)
    @ProcessOperationClass(overloading = Kleenean.FALSE)
    public class DesignarSuper extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            setDefaultDescription(""
                + "Designa un usuario como súper-usuario. "
                + "");
        }

        @InstanceReference
        @Allocation(maxDepth = 1, maxRound = 0)
        protected Usuario usuario;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            usuario.setDefaultDescription(""
                + "Código del usuario que desea designar como súper-usuario. "
                + "Es un dato obligatorio y no tiene valor por omisión. "
                + "");
            usuario.setSearchQueryFilter(usuario.esUsuarioEspecial.isFalse()
                .and(usuario.esSuperUsuario.isFalse())
                .and(usuario.codigoUsuario.isNotEqualTo(SpecialCharacterValue.CURRENT_USER_CODE)));
            usuario.getSearchQueryFilter().setDefaultErrorMessage(""
                + "el usuario es un usuario especial, es su propio usuario, o ya es un súper-usuario"
                + "");
        }

    }

    @OperationClass(access = OperationAccess.PROTECTED)
    @ProcessOperationClass(overloading = Kleenean.FALSE)
    public class AnularSuper extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            setDefaultDescription(""
                + "Anula la designación de un usuario como súper-usuario. "
                + "");
        }

        @InstanceReference
        @Allocation(maxDepth = 1, maxRound = 0)
        protected Usuario usuario;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            usuario.setDefaultDescription(""
                + "Código del súper-usuario cuya designación desea anular. "
                + "Es un dato obligatorio y no tiene valor por omisión. "
                + "");
            usuario.setSearchQueryFilter(usuario.esUsuarioEspecial.isFalse()
                .and(usuario.esSuperUsuario.isTrue())
                .and(usuario.codigoUsuario.isNotEqualTo(SpecialCharacterValue.CURRENT_USER_CODE)));
            usuario.getSearchQueryFilter().setDefaultErrorMessage(""
                + "el usuario es un usuario especial, es su propio usuario, o no es un súper-usuario"
                + "");
        }

    }

    @OperationClass(access = OperationAccess.PROTECTED)
    @ProcessOperationClass(overloading = Kleenean.FALSE)
    public class Desactivar extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            setDefaultDescription(""
                + "Desactiva un usuario activo. "
                + "");
        }

        @InstanceReference
        @Allocation(maxDepth = 1, maxRound = 0)
        protected Usuario usuario;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            usuario.setDefaultDescription(""
                + "Código del usuario que desea desactivar. "
                + "Es un dato obligatorio y no tiene valor por omisión. "
                + "");
            usuario.setSearchQueryFilter(usuario.esUsuarioEspecial.isFalse()
                .and(usuario.esUsuarioInactivo.isFalse())
                .and(usuario.codigoUsuario.isNotEqualTo(SpecialCharacterValue.CURRENT_USER_CODE)));
            usuario.getSearchQueryFilter().setDefaultErrorMessage(""
                + "el usuario es un usuario especial, es su propio usuario, o ya es un usuario inactivo"
                + "");
        }

    }

    @OperationClass(access = OperationAccess.PROTECTED)
    @ProcessOperationClass(overloading = Kleenean.FALSE)
    public class Reactivar extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            setDefaultDescription(""
                + "Reactiva un usuario inactivo. "
                + "");
        }

        @InstanceReference
        @Allocation(maxDepth = 1, maxRound = 0)
        protected Usuario usuario;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            usuario.setDefaultDescription(""
                + "Código del usuario que desea reactivar. "
                + "Es un dato obligatorio y no tiene valor por omisión. "
                + "");
            usuario.setSearchQueryFilter(usuario.esUsuarioEspecial.isFalse()
                .and(usuario.esUsuarioInactivo.isTrue())
                .and(usuario.codigoUsuario.isNotEqualTo(SpecialCharacterValue.CURRENT_USER_CODE)));
            usuario.getSearchQueryFilter().setDefaultErrorMessage(""
                + "el usuario es un usuario especial, es su propio usuario, o no es un usuario inactivo"
                + "");
        }

    }

    @OperationClass(access = OperationAccess.PROTECTED)
    @ProcessOperationClass(overloading = Kleenean.FALSE)
    public class Sincronizar extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            setDefaultDescription(""
                + "Sincroniza las autorizaciones de un usuario. "
                + "Las autorizaciones del usuario serán actualizadas la próxima vez que el usuario inicie una sesión de trabajo. "
                + "");
        }

        @InstanceReference
        @Allocation(maxDepth = 1, maxRound = 0)
        protected Usuario usuario;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            usuario.setDefaultDescription(""
                + "Código del usuario que desea sincronizar. "
                + "Es un dato obligatorio y no tiene valor por omisión. "
                + "");
        }

    }

    @OperationClass(access = OperationAccess.PROTECTED)
    @ProcessOperationClass(overloading = Kleenean.FALSE)
    public class AsignarPassword extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            setDefaultDescription(""
                + "Asigna una nueva contraseña a un usuario. "
                + "");
        }

        @InstanceReference
        @Allocation(maxDepth = 1, maxRound = 0)
        protected Usuario usuario;

        @ParameterField(auditable = Kleenean.FALSE, password = Kleenean.TRUE, required = Kleenean.TRUE)
        @StringField(maxLength = 32)
        protected StringParameter password;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            usuario.setDefaultDescription(""
                + "Código del usuario al que desea asignar una nueva contraseña. "
                + "Es un dato obligatorio y no tiene valor por omisión. "
                + "");
            usuario.setSearchQueryFilter(usuario.esUsuarioEspecial.isFalse()
                .and(usuario.codigoUsuario.isNotEqualTo(SpecialCharacterValue.CURRENT_USER_CODE)));
            usuario.getSearchQueryFilter().setDefaultErrorMessage(""
                + "el usuario es un usuario especial, o es su propio usuario"
                + "");
            password.setDefaultDescription(""
                + "Nueva contraseña. "
                + "Es un dato obligatorio y no tiene valor por omisión. "
                + "");
        }

    }

    @OperationClass(access = OperationAccess.PROTECTED)
    @ProcessOperationClass(overloading = Kleenean.FALSE)
    public class AsignarSupervisor extends ProcessOperation {

        @Override
        protected void settleAttributes() {
            super.settleAttributes();
            setDefaultDescription(""
                + "Asigna un nuevo supervisor a un usuario. "
                + "");
        }

        @InstanceReference
        @Allocation(maxDepth = 1, maxRound = 0)
        protected Usuario usuario;

        @ParameterField(required = Kleenean.TRUE)
        @Allocation(maxDepth = 1, maxRound = 0)
        protected Usuario supervisor;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            usuario.setDefaultDescription(""
                + "Código del usuario al que desea asignar un nuevo supervisor. "
                + "Es un dato obligatorio y no tiene valor por omisión. "
                + "");
            usuario.setSearchQueryFilter(usuario.esUsuarioEspecial.isFalse()
                .and(usuario.codigoUsuario.isNotEqualTo(SpecialCharacterValue.CURRENT_USER_CODE)));
            usuario.getSearchQueryFilter().setDefaultErrorMessage(""
                + "el usuario es un usuario especial, o es su propio usuario"
                + "");
            supervisor.setDefaultDescription(""
                + "Código de usuario del nuevo supervisor. "
                + "Es un dato obligatorio y no tiene valor por omisión. "
                + "");
        }

    }

    @OperationClass(access = OperationAccess.PRIVATE)
    @ProcessOperationClass(overloading = Kleenean.FALSE)
    public class CambiarPassword extends ProcessOperation {

        @InstanceReference
        @Allocation(maxDepth = 1, maxRound = 0)
        protected Usuario usuario;

        @ParameterField(auditable = Kleenean.FALSE, password = Kleenean.TRUE, required = Kleenean.TRUE)
        @StringField(maxLength = 32)
        protected StringParameter password;

        @ParameterField(auditable = Kleenean.FALSE, password = Kleenean.TRUE, required = Kleenean.TRUE)
        @StringField(maxLength = 32)
        protected StringParameter nuevoPassword;

        @ParameterField(auditable = Kleenean.FALSE, password = Kleenean.TRUE, required = Kleenean.TRUE)
        @StringField(maxLength = 32)
        protected StringParameter confirmacionPassword;

        @Override
        protected void settleParameters() {
            super.settleParameters();
            password.setDefaultLabel("contraseña");
            password.setDefaultDescription("contraseña actual");
            nuevoPassword.setDefaultLabel("nueva contraseña");
            confirmacionPassword.setDefaultLabel("nueva contraseña");
            confirmacionPassword.setDefaultDescription("confirmación de la nueva contraseña");
        }

    }
    // </editor-fold>

}
