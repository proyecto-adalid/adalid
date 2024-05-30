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
package meta.entidad.comun.control.acceso;

import adalid.core.*;
import adalid.core.annotations.*;
import adalid.core.enums.*;
import adalid.core.interfaces.*;
import adalid.core.properties.*;
import adalid.core.properties.ext.*;
import java.lang.reflect.Field;

/**
 * @author Jorge Campins
 */
@EntityClass(independent = Kleenean.TRUE, resourceType = ResourceType.OPERATION, resourceGender = ResourceGender.MASCULINE)
@EntityCodeGen(bws = Kleenean.FALSE, fws = Kleenean.FALSE)
@EntityDocGen(stateDiagram = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE, onload = SelectOnloadOption.EXECUTE)
@EntityInsertOperation(enabled = Kleenean.TRUE)
@EntityUpdateOperation(enabled = Kleenean.TRUE)
@EntityDeleteOperation(enabled = Kleenean.TRUE)
@EntityTableView(enabled = Kleenean.TRUE, responsiveMode = TableResponsiveMode.PRIORITY, inserts = Kleenean.TRUE, updates = Kleenean.TRUE)
@EntityDetailView(enabled = Kleenean.TRUE)
@EntityTreeView(enabled = Kleenean.FALSE)
@EntityConsoleView(enabled = Kleenean.TRUE)
@EntityTriggers(afterValue = Kleenean.TRUE)
public class Usuario extends AbstractPersistentEntity {

    // <editor-fold defaultstate="collapsed" desc="class constructors">
    public Usuario(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public LongProperty id;

    @VersionProperty
    public LongProperty version;

    @BusinessKey
    @PropertyField(update = Kleenean.FALSE, overlay = Kleenean.FALSE)
    @StringField(maxLength = MAX_EMAIL_ADDRESS_LENGTH, displayLength = 36) // maxLength = 36 until 01/12/2023
    public StringProperty codigoUsuario;

    @NameProperty
    public StringProperty nombreUsuario;

    @PropertyField(auditable = Kleenean.FALSE, password = Kleenean.TRUE, hidden = Kleenean.TRUE, filter = Kleenean.FALSE, sort = Kleenean.FALSE)
    @StringField(maxLength = 128, displayLength = 36)
    public StringProperty passwordUsuario;

    /*
//  @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(view = MasterDetailView.NONE)
    @PropertyField(hidden = Kleenean.TRUE)
    public ArchivoAdjunto adjunto;

    /**/
//  @ColumnField(calculable = Kleenean.TRUE)
    @ImageProperty(displayWidth = {144, 96, 72}, displayHeight = {192, 128, 96}, avatarShape = AvatarShape.CIRCLE, avatarDefault = AvatarDefault.USER)
    @PropertyField(responsivePriority = 6, table = Kleenean.TRUE, overlay = Kleenean.TRUE)
    public BinaryProperty octetos;

//  @FileReference(types = MimeType.IMAGE, storage = UploadStorageOption.ROW, joinField = "adjunto")
    @FileReference(types = MimeType.IMAGE, storage = UploadStorageOption.ROW, blobField = "octetos")
    @PropertyField(create = Kleenean.TRUE, update = Kleenean.TRUE, table = Kleenean.FALSE, report = Kleenean.FALSE)
    public StringProperty archivo;

    @PropertyField(create = Kleenean.TRUE, overlay = Kleenean.TRUE, table = Kleenean.FALSE, report = Kleenean.FALSE)
    public EmailAddressProperty correoElectronico;

    @PropertyField(create = Kleenean.TRUE, table = Kleenean.FALSE, report = Kleenean.FALSE)
    public InternationalSmartphoneNumberProperty numeroTelefonoInteligente;

    @StringField(maxLength = 20, regex = PHONE_REGEX, validator = PHONE_NUMBER_VALIDATOR)
    @PropertyField(create = Kleenean.TRUE, table = Kleenean.FALSE, report = Kleenean.FALSE)
    public StringProperty numeroTelefonoMovil;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(responsivePriority = 6, create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public BooleanProperty esSuperUsuario;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(responsivePriority = 6, create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public BooleanProperty esSuperAuditor;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(responsivePriority = 6, create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public BooleanProperty esUsuarioEspecial;

    @InactiveIndicator
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(responsivePriority = 6, create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE, overlay = Kleenean.TRUE, heading = Kleenean.FALSE)
    public BooleanProperty esUsuarioInactivo;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, hidden = Kleenean.TRUE)
    public BooleanProperty esUsuarioModificado;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE)
    public BooleanProperty esUsuarioAutomatico;

    @ColumnField(nullable = Kleenean.TRUE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE)
    public TimestampProperty fechaHoraRegistro;

    @ColumnField(nullable = Kleenean.TRUE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE)
    public TimestampProperty fechaHoraSincronizacion;

    @SegmentProperty
    @EntityReferenceSearch(searchType = SearchType.LIST, listStyle = ListStyle.CHARACTER_KEY_AND_NAME, displayMode = DisplayMode.WRITING)
    @ForeignKey(onDelete = OnDeleteAction.NONE, onUpdate = OnUpdateAction.NONE)
    @ManyToOne(view = MasterDetailView.TABLE_AND_DETAIL)
    @PropertyField(responsivePriority = 6, create = Kleenean.TRUE, update = Kleenean.TRUE, required = Kleenean.TRUE, table = Kleenean.TRUE, report = Kleenean.TRUE, heading = Kleenean.TRUE, overlay = Kleenean.TRUE)
    public GrupoUsuario grupo;

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of Usuario's attributes">
        setLocalizedLabel(ENGLISH, "user");
        setLocalizedLabel(SPANISH, "usuario");
        setLocalizedCollectionLabel(ENGLISH, "Users");
        setLocalizedCollectionLabel(SPANISH, "Usuarios");
        /**/
        setLocalizedDescription(ENGLISH, "Each instance of " + b("Users") + " represents an "
            + "application user."
            + "");
        setLocalizedDescription(SPANISH, "Cada instancia de " + b("Usuarios") + " representa un "
            + "usuario de la aplicación."
            + "");
        /**/
        setLocalizedShortDescription(ENGLISH, "application user");
        setLocalizedShortDescription(SPANISH, "usuario de la aplicación");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
        /*
        setOrderBy(codigoUsuario);
        /*
        octetos.setCalculableValueExpression(adjunto.octetos);
        /**/
        esSuperUsuario.setInitialValue(false);
        esSuperUsuario.setDefaultValue(false);
        esSuperAuditor.setInitialValue(false);
        esSuperAuditor.setDefaultValue(false);
        esUsuarioEspecial.setInitialValue(false);
        esUsuarioEspecial.setDefaultValue(false);
        esUsuarioInactivo.setInitialValue(false);
        esUsuarioInactivo.setDefaultValue(false);
        esUsuarioModificado.setInitialValue(false);
        esUsuarioModificado.setDefaultValue(false);
        esUsuarioAutomatico.setInitialValue(false);
        esUsuarioAutomatico.setDefaultValue(false);
        /**/
//      grupo.setInitialValue(esUsuarioEspecial.then(grupo.USUARIOS_ESPECIALES).otherwise(grupo.USUARIOS_ORDINARIOS));
//      grupo.setDefaultValue(esUsuarioEspecial.then(grupo.USUARIOS_ESPECIALES).otherwise(grupo.USUARIOS_ORDINARIOS));
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of Usuario's properties">
        /**/
        codigoUsuario.setLocalizedLabel(ENGLISH, "user code");
        codigoUsuario.setLocalizedLabel(SPANISH, "código del usuario");
        codigoUsuario.setLocalizedShortLabel(ENGLISH, "code");
        codigoUsuario.setLocalizedShortLabel(SPANISH, "código");
        /**/
        nombreUsuario.setLocalizedLabel(ENGLISH, "user name");
        nombreUsuario.setLocalizedLabel(SPANISH, "nombre del usuario");
        nombreUsuario.setLocalizedShortLabel(ENGLISH, "name");
        nombreUsuario.setLocalizedShortLabel(SPANISH, "nombre");
        /**/
        passwordUsuario.setLocalizedLabel(ENGLISH, "user password");
        passwordUsuario.setLocalizedLabel(SPANISH, "contraseña usuario");
        passwordUsuario.setLocalizedShortLabel(ENGLISH, "password");
        passwordUsuario.setLocalizedShortLabel(SPANISH, "contraseña");
        /*
        adjunto.setLocalizedLabel(ENGLISH, "portrait attachment");
        adjunto.setLocalizedLabel(SPANISH, "adjunto de retrato");
        /**/
        octetos.setLocalizedLabel(ENGLISH, "portrait");
        octetos.setLocalizedLabel(SPANISH, "retrato");
        octetos.setLocalizedTooltip(ENGLISH, "user portrait");
        octetos.setLocalizedTooltip(SPANISH, "retrato del usuario");
        /**/
        archivo.setLocalizedLabel(ENGLISH, "portrait");
        archivo.setLocalizedLabel(SPANISH, "retrato");
        archivo.setLocalizedTooltip(ENGLISH, "user portrait");
        archivo.setLocalizedTooltip(SPANISH, "retrato del usuario");
        /**/
        correoElectronico.setLocalizedLabel(ENGLISH, "e-mail");
        correoElectronico.setLocalizedLabel(SPANISH, "correo electrónico");
        /*
        correoElectronico.setLocalizedRegexErrorMessage(ENGLISH, "e-mail does not meet the required pattern");
        correoElectronico.setLocalizedRegexErrorMessage(SPANISH, "correo electrónico no cumple con el patrón requerido");
        /**/
        numeroTelefonoInteligente.setLocalizedLabel(ENGLISH, "WhatsApp phone number");
        numeroTelefonoInteligente.setLocalizedLabel(SPANISH, "número de teléfono WhatsApp");
        numeroTelefonoInteligente.setLocalizedDescription(ENGLISH, "mobile phone number capable of running WhatsApp; " + PHONE_REGEX_ENGLISH_DESCRIPTION);
        numeroTelefonoInteligente.setLocalizedDescription(SPANISH, "número de teléfono móvil capaz de ejecutar WhatsApp; " + PHONE_REGEX_SPANISH_DESCRIPTION);
        numeroTelefonoInteligente.setLocalizedShortDescription(ENGLISH, "mobile phone number capable of running WhatsApp; " + PHONE_REGEX_ENGLISH_DESCRIPTION);
        numeroTelefonoInteligente.setLocalizedShortDescription(SPANISH, "número de teléfono móvil capaz de ejecutar WhatsApp; " + PHONE_REGEX_SPANISH_DESCRIPTION);
        /*
        numeroTelefonoInteligente.setLocalizedRegexErrorMessage(ENGLISH, PHONE_REGEX_ENGLISH_ERROR_MESSAGE);
        numeroTelefonoInteligente.setLocalizedRegexErrorMessage(SPANISH, PHONE_REGEX_SPANISH_ERROR_MESSAGE);
        /**/
        numeroTelefonoMovil.setDefaultValue(numeroTelefonoInteligente);
        /**/
        numeroTelefonoMovil.setLocalizedLabel(ENGLISH, "SMS phone number");
        numeroTelefonoMovil.setLocalizedLabel(SPANISH, "número de teléfono SMS");
        numeroTelefonoMovil.setLocalizedDescription(ENGLISH, "mobile phone number capable of receiving SMS messages; "
            + PHONE_REGEX_ENGLISH_DESCRIPTION);
        numeroTelefonoMovil.setLocalizedDescription(SPANISH, "número de teléfono móvil capaz de recibir mensajes SMS; "
            + PHONE_REGEX_SPANISH_DESCRIPTION);
        /**/
        numeroTelefonoMovil.setLocalizedRegexErrorMessage(ENGLISH, PHONE_REGEX_ENGLISH_ERROR_MESSAGE);
        numeroTelefonoMovil.setLocalizedRegexErrorMessage(SPANISH, PHONE_REGEX_SPANISH_ERROR_MESSAGE);
        /**/
        esSuperUsuario.setLocalizedLabel(ENGLISH, "super-user");
        esSuperUsuario.setLocalizedLabel(SPANISH, "súper-usuario");
        esSuperUsuario.setLocalizedShortLabel(ENGLISH, "super");
        esSuperUsuario.setLocalizedShortLabel(SPANISH, "súper");
        esSuperUsuario.setLocalizedDescription(ENGLISH, "designated as super-user");
        esSuperUsuario.setLocalizedDescription(SPANISH, "designado como súper-usuario");
        /**/
        esSuperAuditor.setLocalizedLabel(ENGLISH, "super-auditor");
        esSuperAuditor.setLocalizedLabel(SPANISH, "súper-auditor");
        esSuperAuditor.setLocalizedShortLabel(ENGLISH, "auditor");
        esSuperAuditor.setLocalizedShortLabel(SPANISH, "auditor");
        esSuperAuditor.setLocalizedDescription(ENGLISH, "designated as super-auditor");
        esSuperAuditor.setLocalizedDescription(SPANISH, "designado como súper-auditor");
        /**/
        esUsuarioEspecial.setLocalizedLabel(ENGLISH, "special user");
        esUsuarioEspecial.setLocalizedLabel(SPANISH, "usuario especial");
        esUsuarioEspecial.setLocalizedShortLabel(ENGLISH, "special");
        esUsuarioEspecial.setLocalizedShortLabel(SPANISH, "especial");
        esUsuarioEspecial.setLocalizedDescription(ENGLISH, "special user");
        esUsuarioEspecial.setLocalizedDescription(SPANISH, "usuario especial");
        /**/
        esUsuarioInactivo.setLocalizedLabel(ENGLISH, "inactive user");
        esUsuarioInactivo.setLocalizedLabel(SPANISH, "usuario inactivo");
        esUsuarioInactivo.setLocalizedShortLabel(ENGLISH, "inactive");
        esUsuarioInactivo.setLocalizedShortLabel(SPANISH, "inactivo");
        esUsuarioInactivo.setLocalizedDescription(ENGLISH, "inactive user");
        esUsuarioInactivo.setLocalizedDescription(SPANISH, "usuario inactivo");
        /**/
        esUsuarioModificado.setLocalizedLabel(ENGLISH, "modified user");
        esUsuarioModificado.setLocalizedLabel(SPANISH, "usuario modificado");
        esUsuarioModificado.setLocalizedShortLabel(ENGLISH, "modified");
        esUsuarioModificado.setLocalizedShortLabel(SPANISH, "modificado");
        esUsuarioModificado.setLocalizedDescription(ENGLISH, "modified user");
        esUsuarioModificado.setLocalizedDescription(SPANISH, "usuario modificado");
        /**/
        esUsuarioAutomatico.setLocalizedLabel(ENGLISH, "automatically registered user");
        esUsuarioAutomatico.setLocalizedLabel(SPANISH, "usuario registrado automáticamente");
        esUsuarioAutomatico.setLocalizedShortLabel(ENGLISH, "automatic");
        esUsuarioAutomatico.setLocalizedShortLabel(SPANISH, "automático");
        esUsuarioAutomatico.setLocalizedDescription(ENGLISH, "automatically registered user");
        esUsuarioAutomatico.setLocalizedDescription(SPANISH, "usuario registrado automáticamente");
        /**/
        fechaHoraRegistro.setLocalizedLabel(ENGLISH, "automatic registration timestamp");
        fechaHoraRegistro.setLocalizedLabel(SPANISH, "fecha/hora registro automático");
        fechaHoraRegistro.setLocalizedShortLabel(ENGLISH, "registration timestamp");
        fechaHoraRegistro.setLocalizedShortLabel(SPANISH, "fecha/hora registro");
        fechaHoraRegistro.setLocalizedDescription(ENGLISH, "automatic registration timestamp");
        fechaHoraRegistro.setLocalizedDescription(SPANISH, "fecha/hora de registro automático");
        /**/
        fechaHoraSincronizacion.setLocalizedLabel(ENGLISH, "last synchronization timestamp");
        fechaHoraSincronizacion.setLocalizedLabel(SPANISH, "fecha/hora última sincronización");
        fechaHoraSincronizacion.setLocalizedShortLabel(ENGLISH, "synchronization timestamp");
        fechaHoraSincronizacion.setLocalizedShortLabel(SPANISH, "fecha/hora sincronización");
        fechaHoraSincronizacion.setLocalizedDescription(ENGLISH, "timestamp of last synchronization");
        fechaHoraSincronizacion.setLocalizedDescription(SPANISH, "fecha/hora de la última sincronización");
        /**/
        grupo.setLocalizedLabel(ENGLISH, "user group");
        grupo.setLocalizedLabel(SPANISH, "grupo de usuarios");
        grupo.setLocalizedShortLabel(ENGLISH, "group");
        grupo.setLocalizedShortLabel(SPANISH, "grupo");
        grupo.setLocalizedDescription(ENGLISH, "group to which this user belongs");
        grupo.setLocalizedDescription(SPANISH, "grupo al que pertenece este usuario");
        grupo.setLocalizedTooltip(ENGLISH, "code of the group to which this user belongs");
        grupo.setLocalizedTooltip(SPANISH, "código del grupo al que pertenece este usuario");
        /**/
        // </editor-fold>
    }

    protected Key ix_usuario_0001;

    @Override
    protected void settleKeys() {
        super.settleKeys();
        /**/
        ix_usuario_0001.setUnique(false);
        ix_usuario_0001.newKeyField(correoElectronico);
        /**/
    }

    public Instance ADMINISTRADOR;

    public Instance AUDITOR;

    public Instance OPERADOR;

    /**
     * Administrator user instance getter (for velocity templates)
     *
     * @return the administrator user instance
     */
    public Instance getAdministratorUserInstance() {
        return ADMINISTRADOR;
    }

    /**
     * Auditor user instance getter (for velocity templates)
     *
     * @return the auditor user instance
     */
    public Instance getAuditorUserInstance() {
        return AUDITOR;
    }

    /**
     * Basic user instance getter (for velocity templates)
     *
     * @return the basic user instance
     */
    public Instance getBasicUserInstance() {
        return OPERADOR;
    }

    @Override
    protected void settleInstances() {
        super.settleInstances();
        /**/
        ADMINISTRADOR.newInstanceField(id, 101);
        ADMINISTRADOR.newInstanceField(codigoUsuario, "admin");
        ADMINISTRADOR.newInstanceField(nombreUsuario, "Administrador");
        ADMINISTRADOR.newInstanceField(passwordUsuario, "sesamo");
        ADMINISTRADOR.newInstanceField(esSuperUsuario, true);
        ADMINISTRADOR.newInstanceField(esSuperAuditor, false);
        ADMINISTRADOR.newInstanceField(esUsuarioEspecial, true);
        /**/
        AUDITOR.newInstanceField(id, 102);
        AUDITOR.newInstanceField(codigoUsuario, "audit");
        AUDITOR.newInstanceField(nombreUsuario, "Auditor");
        AUDITOR.newInstanceField(passwordUsuario, "sesamo");
        AUDITOR.newInstanceField(esSuperUsuario, false);
        AUDITOR.newInstanceField(esSuperAuditor, true);
        AUDITOR.newInstanceField(esUsuarioEspecial, true);
        /**/
        OPERADOR.newInstanceField(id, 103);
        OPERADOR.newInstanceField(codigoUsuario, "oper");
        OPERADOR.newInstanceField(nombreUsuario, "Operador");
//      OPERADOR.newInstanceField(passwordUsuario, "sesamo");
        OPERADOR.newInstanceField(esSuperUsuario, false);
        OPERADOR.newInstanceField(esSuperAuditor, false);
        OPERADOR.newInstanceField(esUsuarioEspecial, true);
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of Usuario's instances">
        ADMINISTRADOR.newInstanceField(codigoUsuario, "admin", ENGLISH);
        ADMINISTRADOR.newInstanceField(codigoUsuario, "admin", SPANISH);
        /**/
        ADMINISTRADOR.newInstanceField(nombreUsuario, "Administrator", ENGLISH);
        ADMINISTRADOR.newInstanceField(nombreUsuario, "Administrador", SPANISH);
        /**/
        AUDITOR.newInstanceField(codigoUsuario, "audit", ENGLISH);
        AUDITOR.newInstanceField(codigoUsuario, "audit", SPANISH);
        /**/
        AUDITOR.newInstanceField(nombreUsuario, "Auditor", ENGLISH);
        AUDITOR.newInstanceField(nombreUsuario, "Auditor", SPANISH);
        /**/
        OPERADOR.newInstanceField(codigoUsuario, "oper", ENGLISH);
        OPERADOR.newInstanceField(codigoUsuario, "oper", SPANISH);
        /**/
        OPERADOR.newInstanceField(nombreUsuario, "Operator", ENGLISH);
        OPERADOR.newInstanceField(nombreUsuario, "Operador", SPANISH);
        // </editor-fold>
    }

    protected Segment usuariosEspeciales, usuariosOrdinarios;

    @Override
    protected void settleExpressions() {
        super.settleExpressions();
        /**/
        usuariosEspeciales = esUsuarioEspecial.isTrue();
        usuariosOrdinarios = esUsuarioEspecial.isFalse();
        /**/
        // <editor-fold defaultstate="collapsed" desc="localization of Usuario's expressions">
        /**/
        usuariosEspeciales.setLocalizedDescription(ENGLISH, "the user is a special user");
        usuariosEspeciales.setLocalizedDescription(SPANISH, "el usuario es un usuario especial");
        usuariosEspeciales.setLocalizedErrorMessage(ENGLISH, "the user is not a special user");
        usuariosEspeciales.setLocalizedErrorMessage(SPANISH, "el usuario no es un usuario especial");
        /**/
        usuariosOrdinarios.setLocalizedDescription(ENGLISH, "the user is not a special user");
        usuariosOrdinarios.setLocalizedDescription(SPANISH, "el usuario no es un usuario especial");
        usuariosOrdinarios.setLocalizedErrorMessage(ENGLISH, "the user is a special user");
        usuariosOrdinarios.setLocalizedErrorMessage(SPANISH, "el usuario es un usuario especial");
        /**/
        // </editor-fold>
    }

    @Override
    protected void settleFilters() {
        super.settleFilters();
        archivo.setRenderingFilter(UNTRUTH, true);
    }

}
