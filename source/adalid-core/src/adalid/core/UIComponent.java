/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core;

import adalid.commons.bundles.Bundle;
import adalid.commons.util.StrUtils;
import org.apache.commons.lang.StringUtils;

public abstract class UIComponent extends AbstractArtifact {

    public UIComponent(DisplayField field) {
        super.setDeclared(name(field));
        _field = field;
    }

    private DisplayField _field;

    /**
     * @return the linked field
     */
    public DisplayField getField() {
        return _field;
    }

    // <editor-fold defaultstate="collapsed" desc="initialization">
    private String name(DisplayField field) {
        String name = field.getName();
        String prefix = string("prefix");
        String suffix = string("suffix");
        return StrUtils.getCamelCase(prefix + name + suffix, "", true);
    }

    private String string(String keyword) {
        String uic = getClass().getSimpleName();
        String key = uic + ".name." + keyword;
        return Bundle.getTrimmedToEmptyString(key);
    }
    // </editor-fold>

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
                string += fee + tab + "field" + faa + _field + foo;
            }

        }
        return string;
    }
    // </editor-fold>
}
