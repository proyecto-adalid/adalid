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

import adalid.core.constants.GoogleMapsConstants;
import adalid.core.enums.EmbeddedDocumentStyle;
import adalid.core.enums.EmbeddedDocumentType;
import adalid.core.properties.StringProperty;

/**
 * @author Jorge Campins
 */
public class GoogleMapProperty extends StringProperty {

    {
        /**/
        setEmbeddedDocumentStyle(EmbeddedDocumentStyle.FIELD);
        setEmbeddedDocumentType(EmbeddedDocumentType.IFRAME);
        setEmbeddedDocumentURLs(GoogleMapsConstants.EMBED_MAPS);
        setSearchURL(GoogleMapsConstants.GOOGLE_MAPS);
        setSpecialConverterName(GoogleMapsConstants.GOOGLE_MAPS_EMBED_CONVERTER);
        /**/
        setLocalizedDescription(ENGLISH, GoogleMapsConstants.GOOGLE_MAPS_EMBED_ENGLISH_DESCRIPTION);
        setLocalizedDescription(SPANISH, GoogleMapsConstants.GOOGLE_MAPS_EMBED_SPANISH_DESCRIPTION);
        setLocalizedShortDescription(ENGLISH, GoogleMapsConstants.GOOGLE_MAPS_EMBED_ENGLISH_SHORT_DESCRIPTION);
        setLocalizedShortDescription(SPANISH, GoogleMapsConstants.GOOGLE_MAPS_EMBED_SPANISH_SHORT_DESCRIPTION);
        setLocalizedTooltip(ENGLISH, GoogleMapsConstants.GOOGLE_MAPS_EMBED_ENGLISH_TOOLTIP);
        setLocalizedTooltip(SPANISH, GoogleMapsConstants.GOOGLE_MAPS_EMBED_SPANISH_TOOLTIP);
        /**/
    }

}
