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
public class PhoneConstants {

    public static final int MAX_PHONE_NUMBER_LENGTH = Constants.MAX_PHONE_NUMBER_LENGTH;

    public static final String PHONE_REGEX = Constants.PHONE_REGEX;

    public static final String PHONE_NUMBER_VALIDATOR = "phoneNumberValidator";

    public static final String PHONE_REGEX_ENGLISH_DESCRIPTION = ""
        + "this is an international phone number; "
        + "it must start with a country code, "
        + "followed by a global subscriber number or an area code and a subscriber number; "
        + "for example: +58 4121234567, +58-412-1234567"
        + "";

    public static final String PHONE_REGEX_SPANISH_DESCRIPTION = ""
        + "este es un número de teléfono internacional; "
        + "debe comenzar con un código de país, "
        + "seguido de un número de suscriptor global o un código de área y un número de suscriptor; "
        + "por ejemplo: +58 4121234567, +58-412-1234567"
        + "";

    public static final String PHONE_REGEX_ENGLISH_ERROR_MESSAGE = ""
        + "the phone number does not meet the required pattern; "
        + "it must start with a country code, i.e. a plus sign and a group of 1 to 3 digits, "
        + "followed by a global subscriber number, i.e. a group of 7 to 14 digits; "
        + "country code and global subscriber number must be separated by a single white space or hyphen; "
        + "global subscriber number can be divided into area code, a group of 1 to 4 digits, and subscriber number, a group of 6 to 10 digits; "
        + "the area code and subscriber number must be separated by a single blank space or hyphen; "
        + "whatever their distribution among the groups, the total number of digits must be between 8 and 15."
        + "";

    public static final String PHONE_REGEX_SPANISH_ERROR_MESSAGE = ""
        + "el número de teléfono no cumple con el patrón requerido; "
        + "éste debe comenzar con un código de país, es decir, un signo más y un grupo de 1 hasta 3 dígitos, "
        + "seguido de un número de suscriptor global, es decir, un grupo de 7 hasta 14 dígitos; "
        + "el código de país y el número de suscriptor global deben estar separados por un solo espacio en blanco o guión; "
        + "el número de suscriptor global se puede dividir en código de área, un grupo de 1 hasta 4 dígitos, y número de suscriptor, un grupo de 6 a 10 dígitos; "
        + "el código de área y el número de suscriptor deben estar separados por un solo espacio en blanco o guión; "
        + "cualquiera que sea su distribución entre los grupos, el número total de dígitos debe estar entre 8 y 15."
        + "";

    public static final int MAX_LOCAL_PHONE_NUMBER_LENGTH = Constants.MAX_LOCAL_PHONE_NUMBER_LENGTH;

    public static final String LOCAL_PHONE_REGEX = Constants.LOCAL_PHONE_REGEX;

    public static final String LOCAL_PHONE_NUMBER_VALIDATOR = "localPhoneNumberValidator";

    public static final String LOCAL_PHONE_REGEX_ENGLISH_DESCRIPTION = ""
        + "this is a local phone number; "
        + "it must be a global subscriber number or an area code and a subscriber number; "
        + "for example: 4121234567, 412-1234567"
        + "";

    public static final String LOCAL_PHONE_REGEX_SPANISH_DESCRIPTION = ""
        + "este es un número de teléfono local; "
        + "debe ser un número de suscriptor global o un código de área y un número de suscriptor; "
        + "por ejemplo: 4121234567, 412-1234567"
        + "";

    public static final String LOCAL_PHONE_REGEX_ENGLISH_ERROR_MESSAGE = ""
        + "the phone number does not meet the required pattern; "
        + "it must be a global subscriber number, i.e. a group of 7 to 14 digits; "
        + "or it can be divided into area code, a group of 1 to 4 digits, and subscriber number, a group of 6 to 10 digits; "
        + "the area code and subscriber number must be separated by a single blank space or hyphen; "
        + "whatever their distribution among the groups, the total number of digits must be between 7 and 14."
        + "";

    public static final String LOCAL_PHONE_REGEX_SPANISH_ERROR_MESSAGE = ""
        + "el número de teléfono no cumple con el patrón requerido; "
        + "éste debe ser un número de suscriptor global, es decir, un grupo de 7 hasta 14 dígitos; "
        + "o se puede dividir en código de área, un grupo de 1 hasta 4 dígitos, y número de suscriptor, un grupo de 6 a 10 dígitos; "
        + "el código de área y el número de suscriptor deben estar separados por un solo espacio en blanco o guión; "
        + "cualquiera que sea su distribución entre los grupos, el número total de dígitos debe estar entre 7 y 14."
        + "";

}
