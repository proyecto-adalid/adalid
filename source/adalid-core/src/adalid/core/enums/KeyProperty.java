/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core.enums;

import adalid.commons.bundles.Bundle;

/**
 * @author Jorge Campins
 */
public enum KeyProperty {

    PRIMARY_KEY, VERSION, NUMERIC_KEY, CHARACTER_KEY, NAME, DESCRIPTION, INACTIVE_INDICATOR, URL, PARENT, OWNER, SEGMENT,
    UNIQUE_KEY, BUSINESS_KEY, DISCRIMINATOR;

    public String getLabel() {
        String tag = "label";
        String string = Bundle.getTrimmedToNullString(name() + "." + tag);
        return string == null ? name().toLowerCase() : string;
    }

    public String getLabelPattern() {
        String tag = "label.pattern";
        String string = Bundle.getTrimmedToNullString(name() + "." + tag);
        return string == null ? getLabel() : string;
    }

}
