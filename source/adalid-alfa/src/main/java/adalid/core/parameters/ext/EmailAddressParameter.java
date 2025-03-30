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
package adalid.core.parameters.ext;

import adalid.core.constants.EmailConstants;
import adalid.core.enums.LetterCase;
import adalid.core.parameters.StringParameter;
import java.util.regex.Pattern;

/**
 * @author Jorge Campins
 */
public class EmailAddressParameter extends StringParameter {

    {
        /**/
        setLetterCase(LetterCase.LOWER);
        setMaxLength(EmailConstants.MAX_EMAIL_ADDRESS_LENGTH);
        setPattern(Pattern.compile(EmailConstants.EMAIL_REGEX));
        /**/
        setLocalizedDescription(ENGLISH, EmailConstants.EMAIL_REGEX_ENGLISH_DESCRIPTION);
        setLocalizedDescription(SPANISH, EmailConstants.EMAIL_REGEX_SPANISH_DESCRIPTION);
        setLocalizedRegexErrorMessage(ENGLISH, EmailConstants.EMAIL_REGEX_ENGLISH_ERROR_MESSAGE);
        setLocalizedRegexErrorMessage(SPANISH, EmailConstants.EMAIL_REGEX_SPANISH_ERROR_MESSAGE);
        /**/
    }

    public boolean isEmailAddress() {
        return true;
    }

}
