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
package adalid.core.properties.ext;

import adalid.core.constants.PhoneConstants;
import adalid.core.enums.PhoneNumberFormat;
import java.util.regex.Pattern;

/**
 * @author Jorge Campins
 */
public class InternationalPhoneNumberProperty extends AbstractPhoneNumberProperty {

    {
        /**/
        setMaxLength(PhoneConstants.MAX_PHONE_NUMBER_LENGTH);
        setPattern(Pattern.compile(PhoneConstants.PHONE_REGEX));
        setSpecialValidatorName(PhoneConstants.PHONE_NUMBER_VALIDATOR);
        /**/
        setLocalizedDescription(ENGLISH, PhoneConstants.PHONE_REGEX_ENGLISH_DESCRIPTION);
        setLocalizedDescription(SPANISH, PhoneConstants.PHONE_REGEX_SPANISH_DESCRIPTION);
        setLocalizedRegexErrorMessage(ENGLISH, PhoneConstants.PHONE_REGEX_ENGLISH_ERROR_MESSAGE);
        setLocalizedRegexErrorMessage(SPANISH, PhoneConstants.PHONE_REGEX_SPANISH_ERROR_MESSAGE);
        /**/
    }

    @Override
    public final PhoneNumberFormat getPhoneNumberFormat() {
        return PhoneNumberFormat.INTERNATIONAL;
    }

}
