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

import adalid.commons.bundles.*;

/**
 * @author Jorge Campins
 */
public enum DisplayFormat {

    UNSPECIFIED, TABLE, DETAIL, TREE, CONSOLE;

    private static final String SIMPLE = DisplayFormat.class.getSimpleName();

    public String getOutcome() {
        String tag = "outcome";
        String string = Bundle.getTrimmedToNullString(SIMPLE + "." + name() + "." + tag);
        return string == null ? name().toLowerCase() : string;
    }

    public String getOutcomeLabel() {
        String tag = "outcome.label";
        String string = Bundle.getTrimmedToNullString(SIMPLE + "." + name() + "." + tag);
        return string == null ? name().toLowerCase() : string;
    }

}
