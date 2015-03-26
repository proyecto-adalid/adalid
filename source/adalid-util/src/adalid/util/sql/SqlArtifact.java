/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.util.sql;

import adalid.commons.util.StrUtils;
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
