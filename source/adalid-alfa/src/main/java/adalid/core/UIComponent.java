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
package adalid.core;

import adalid.commons.bundles.*;
import adalid.commons.util.*;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public abstract class UIComponent extends AbstractArtifact {

    private static final String EOL = "\n";

    public UIComponent(DisplayField field) {
        super();
        _field = field;
        init(field);
    }

    private void init(DisplayField field) {
        setDeclared(name(field));
    }

    private final DisplayField _field;

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
