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
package adalid.util.sql;

import adalid.commons.util.*;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public class SqlArtifact {

    private String _name;

    /**
     * @return the row name
     */
    public String getName() {
        return _name;
    }

    /**
     * @param name the row name to set
     */
    void setName(String name) {
        _name = name;
    }

    /**
     * @return the row name
     */
    public String getJavaName() {
        return StrUtils.getCamelCase(_name);
    }

    public String getCapitalizedJavaName() {
        return StringUtils.capitalize(getJavaName());
    }

    public String getDecapitalizedJavaName() {
        return StringUtils.uncapitalize(getJavaName());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " " + _name + " "
            + "(" + super.toString() + ")";
    }

}
