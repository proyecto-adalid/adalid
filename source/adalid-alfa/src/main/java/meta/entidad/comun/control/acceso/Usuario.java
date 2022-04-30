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
import java.lang.reflect.Field;

/**
 * @author Jorge Campins
 */
@EntityClass(independent = Kleenean.TRUE, resourceType = ResourceType.OPERATION, resourceGender = ResourceGender.MASCULINE)
@EntityCodeGen(fws = Kleenean.FALSE)
@EntityDocGen(stateDiagram = Kleenean.FALSE)
@EntitySelectOperation(enabled = Kleenean.TRUE, onload = SelectOnloadOption.EXECUTE)
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
    public Usuario(Artifact declaringArtifact, Field declaringField) {
        super(declaringArtifact, declaringField);
    }
    // </editor-fold>

    @PrimaryKey
    public LongProperty id;

    @VersionProperty
    public LongProperty version;

    @BusinessKey
    @PropertyField(update = Kleenean.FALSE)
    @StringField(maxLength = 36) // A UUID is made up of hex digits along with 4 hyphens, which make its length equal to 36 characters
    public StringProperty codigoUsuario;

    @NameProperty
    public StringProperty nombreUsuario;

    @PropertyField(auditable = Kleenean.FALSE, password = Kleenean.TRUE, hidden = Kleenean.TRUE, filter = Kleenean.FALSE, sort = Kleenean.FALSE)
    @StringField(maxLength = 128)
    public StringProperty passwordUsuario;

    @StringField(maxLength = 100, regex = EMAIL_REGEX)
    @PropertyField(create = Kleenean.TRUE, table = Kleenean.FALSE, report = Kleenean.FALSE)
    public StringProperty correoElectronico;

    @StringField(maxLength = 20, regex = PHONE_REGEX, validator = "phoneNumberValidator")
    @PropertyField(create = Kleenean.TRUE, table = Kleenean.FALSE, report = Kleenean.FALSE)
    public StringProperty numeroTelefonoMovil;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public BooleanProperty esSuperUsuario;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public BooleanProperty esSuperAuditor;

    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE)
    public BooleanProperty esUsuarioEspecial;

    @InactiveIndicator
    @ColumnField(nullable = Kleenean.FALSE)
    @PropertyField(create = Kleenean.FALSE, update = Kleenean.FALSE, table = Kleenean.TRUE, report = Kleenean.TRUE, overlay = Kleenean.TRUE)
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

    @Override
    protected void settleAttributes() {
        super.settleAttributes();
//      setOrderBy(codigoUsuario);
//      setSchema(ProyectoBase.getEsquemaEntidadesComunes());
        // <editor-fold defaultstate="collapsed" desc="localization of Usuario's attributes">
        setLocalizedLabel(ENGLISH, "user");
        setLocalizedLabel(SPANISH, "usuario");
        setLocalizedCollectionLabel(ENGLISH, "Users");
        setLocalizedCollectionLabel(SPANISH, "Usuarios");
        // </editor-fold>
    }

    @Override
    protected void settleProperties() {
        super.settleProperties();
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
        passwordUsuario.setLocalizedLabel(SPANISH, "password usuario");
        passwordUsuario.setLocalizedShortLabel(ENGLISH, "password");
        passwordUsuario.setLocalizedShortLabel(SPANISH, "contraseña");
        /**/
        correoElectronico.setLocalizedLabel(ENGLISH, "e-mail");
        correoElectronico.setLocalizedLabel(SPANISH, "correo electrónico");
        correoElectronico.setLocalizedRegexErrorMessage(ENGLISH, "e-mail does not meet the required pattern");
        correoElectronico.setLocalizedRegexErrorMessage(SPANISH, "correo electrónico no cumple con el patrón requerido");
        /**/
        numeroTelefonoMovil.setLocalizedLabel(ENGLISH, "SMS phone number");
        numeroTelefonoMovil.setLocalizedLabel(SPANISH, "número de teléfono SMS");
        numeroTelefonoMovil.setLocalizedDescription(ENGLISH, "mobile phone number capable of receiving SMS messages; "
            + "it must start with a country code, followed by a global subscriber number or an area code and a subscriber number; "
            + "for example, +58 4121234567, +58-412-1234567");
        numeroTelefonoMovil.setLocalizedDescription(SPANISH, "número de teléfono móvil capaz de recibir mensajes SMS; "
            + "debe comenzar con un código de país, seguido de un número de suscriptor global o un código de área y un número de suscriptor; "
            + "por ejemplo, +58 4121234567, +58-412-1234567");
        /**/
        numeroTelefonoMovil.setLocalizedRegexErrorMessage(ENGLISH, "mobile phone number does not meet the required pattern; "
            + "it must start with a country code, i.e. a plus sign and a group of 1 to 3 digits, "
            + "followed by a global subscriber number, i.e. a group of 7 to 14 digits; "
            + "country code and global subscriber number must be separated by a single white space or hyphen; "
            + "global subscriber number can be divided into area code, a group of 1 to 4 digits, and subscriber number, a group of 6 to 10 digits; "
            + "the area code and subscriber number must be separated by a single blank space or hyphen; "
            + "whatever their distribution among the groups, the total number of digits must be between 8 and 15.");
        numeroTelefonoMovil.setLocalizedRegexErrorMessage(SPANISH, "número de teléfono móvil no cumple con el patrón requerido; "
            + "éste debe comenzar con un código de país, es decir, un signo más y un grupo de 1 hasta 3 dígitos, "
            + "seguido de un número de suscriptor global, es decir, un grupo de 7 hasta 14 dígitos; "
            + "el código de país y el número de suscriptor global deben estar separados por un solo espacio en blanco o guión; "
            + "el número de suscriptor global se puede dividir en código de área, un grupo de 1 hasta 4 dígitos, y número de suscriptor, un grupo de 6 a 10 dígitos; "
            + "el código de área y el número de suscriptor deben estar separados por un solo espacio en blanco o guión; "
            + "cualquiera que sea su distribución entre los grupos, el número total de dígitos debe estar entre 8 y 15.");
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
        // </editor-fold>
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

    /**/
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

}
