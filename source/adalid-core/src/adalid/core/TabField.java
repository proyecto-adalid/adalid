/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core;

import adalid.core.interfaces.Property;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public class TabField extends AbstractArtifact {

    private static final String EOL = "\n";

    private Property _property;

    /**
     * @return the property
     */
    public Property getProperty() {
        return _property;
    }

    private TabField() {
        super();
    }

    TabField(Property property) {
        super();
        _property = property;
    }

    // <editor-fold defaultstate="collapsed" desc="toString">
    @Override
    protected String fieldsToString(int n, String key, boolean verbose, boolean fields, boolean maps) {
        String tab = verbose ? StringUtils.repeat(" ", 4) : "";
        String fee = verbose ? StringUtils.repeat(tab, n) : "";
        String faa = " = ";
        String foo = verbose ? EOL : ", ";
        String string = super.fieldsToString(n, key, verbose, fields, maps);
        if (fields || verbose) {
            if (verbose) {
                String property = _property == null ? "" : _property.getName();
                string += fee + tab + "property" + faa + property + foo;
            }
        }
        return string;
    }
    // </editor-fold>

}
