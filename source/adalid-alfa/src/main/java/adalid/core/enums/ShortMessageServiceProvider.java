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
package adalid.core.enums;

import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public enum ShortMessageServiceProvider {

    UNSPECIFIED, TWILIO, TELEMO_GROUP;

    public String getBeanClass() {
        String beanName = getBeanName();
        return beanName == null ? "null" : beanName + ".class";
    }

    public String getBeanName() {
        switch (this) {
            case UNSPECIFIED:
                return null;
            default:
                return defaultBeanName();
        }
    }

    private static final String DEFAULT_SMS_BEAN_NAME_SUFFIX = "TexterBean";

    private String defaultBeanName() {
        return StringUtils.capitalize(StringUtils.substringBefore(name().toLowerCase(), "_")) + DEFAULT_SMS_BEAN_NAME_SUFFIX;
    }

}
