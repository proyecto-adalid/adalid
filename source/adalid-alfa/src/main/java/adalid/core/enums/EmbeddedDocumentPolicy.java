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

/**
 * @author Jorge Campins
 */
public enum EmbeddedDocumentPolicy {

    UNSPECIFIED,
    NO_REFERRER,
    NO_REFERRER_WHEN_DOWNGRADE,
    ORIGIN,
    ORIGIN_WHEN_CROSS_ORIGIN,
    SAME_ORIGIN,
    STRICT_ORIGIN,
    STRICT_ORIGIN_WHEN_CROSS_ORIGIN,
    UNSAFE_URL;

    public String getAttributeValue() {
        String name = name();
        return this == UNSPECIFIED ? name : name.toLowerCase().replace('_', '-');
    }

}
