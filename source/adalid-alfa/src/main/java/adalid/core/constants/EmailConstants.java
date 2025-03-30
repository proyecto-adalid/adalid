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
package adalid.core.constants;

import adalid.core.Constants;

/**
 * @author Jorge Campins
 */
public class EmailConstants {

    public static final int MAX_EMAIL_ADDRESS_LENGTH = Constants.MAX_EMAIL_ADDRESS_LENGTH;

    public static final String EMAIL_REGEX = Constants.EMAIL_REGEX;

    public static final String EMAIL_REGEX_ENGLISH_DESCRIPTION = ""
        + "this is an address that identifies the e-mail box to which messages are delivered; as a general rule, "
        + "an e-mail address must start with a user name, followed by an @ sign and a domain name; "
        + "for example: john.doe@gmail.com"
        + "";

    public static final String EMAIL_REGEX_SPANISH_DESCRIPTION = ""
        + "esta es una dirección que identifica la casilla de correo electrónico a la que se entregan los mensajes; por regla general, "
        + "una dirección de correo electrónico debe comenzar con un nombre de usuario, seguido de un signo @ y un nombre de dominio; "
        + "por ejemplo: juan.bimba@gmail.com"
        + "";

    public static final String EMAIL_REGEX_ENGLISH_ERROR_MESSAGE = ""
        + "the e-mail address does not meet the required pattern; "
        + "it must start with a user name, followed by an @ sign and a domain name."
        + "";

    public static final String EMAIL_REGEX_SPANISH_ERROR_MESSAGE = ""
        + "la dirección de correo electrónico no cumple con el patrón requerido; "
        + "debe comenzar con un nombre de usuario, seguido de un signo @ y un nombre de dominio."
        + "";

}
