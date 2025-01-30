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

import adalid.commons.util.BitUtils;
import adalid.core.Constants;
import adalid.core.enums.PhoneNumberFormat;
import adalid.core.parameters.StringParameter;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public abstract class AbstractPhoneNumberParameter extends StringParameter {

    protected static final Logger logger = Logger.getLogger(AbstractPhoneNumberParameter.class);

    {
        setMaxLength(Constants.MAX_PHONE_NUMBER_LENGTH);
    }

    public abstract PhoneNumberFormat getPhoneNumberFormat();

    public final boolean isPhoneNumber() {
        return true;
    }

    private boolean mobileNumber;

    public boolean isMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(boolean b) {
        mobileNumber = b;
    }

    private boolean smartphoneNumber;

    public boolean isSmartphoneNumber() {
        return smartphoneNumber;
    }

    public void setSmartphoneNumber(boolean b) {
        smartphoneNumber = b;
    }

    private Boolean smsNumber;

    public boolean isSmsNumber() {
        return BitUtils.valueOf(smsNumber, isSmartphoneNumber() || isMobileNumber());
    }

    public void setSmsNumber(boolean b) {
        smsNumber = b;
    }

    private Boolean whatsAppNumber;

    public boolean isWhatsAppNumber() {
        return isSmartphoneNumber() && BitUtils.valueOf(whatsAppNumber, true);
    }

    public void setWhatsAppNumber(boolean b) {
        whatsAppNumber = b;
    }

}
