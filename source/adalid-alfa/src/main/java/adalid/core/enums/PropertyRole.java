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

import adalid.core.data.types.*;
import adalid.core.interfaces.*;

/**
 * @author Jorge Campins
 */
public enum PropertyRole {

    ID("serial", "", LongData.class),
    VERSION("version", "", LongData.class),
    NUMBER("numero", "", IntegerData.class),
    CODE("codigo", "", StringData.class),
    NAME("nombre", "", StringData.class),
    DESCRIPTION("descripcion", "", StringData.class),
    IMAGE("imagen", "", BinaryData.class),
    OWNER("propietario", "", LongData.class),
    USER("usuario", "", LongData.class),
    SEGMENT("segmento", "", LongData.class),
    BASE_ENTITY("serial", "base", LongData.class),
    MASTER_ENTITY("serial", "dominante", LongData.class),
    SEGMENTING_ENTITY("serial", "segmento", LongData.class),
    SUPER_ENTITY("serial", "superior", LongData.class),
    PRIMARY_KEY("", "", LongData.class),
    BUSINESS_KEY("", "", StringData.class),
    FOREIGN_KEY("", "", LongData.class),
    ASSOCIATION_KEY_ELEMENT("", "", LongData.class),
    UNIQUE_KEY("", "", Property.class),
    UNIQUE_KEY_ELEMENT("", "", Property.class),
    RESULT_ELEMENT("", "", Property.class),
    ENTITY_MASTER("", "", LongData.class);

    private final String defaultPrefix;

    private final String defaultSuffix;

    private final Class<?> defaultType;

    PropertyRole(String prefix, String suffix, Class<?> type) {
        defaultPrefix = prefix;
        defaultSuffix = suffix;
        defaultType = type;
    }

    /**
     * @return the defaultPrefix
     */
    public String getDefaultPrefix() {
        return defaultPrefix;
    }

    /**
     * @return the defaultSuffix
     */
    public String getDefaultSuffix() {
        return defaultSuffix;
    }

    /**
     * @return the defaultType
     */
    public Class<?> getDefaultType() {
        return defaultType;
    }

}
