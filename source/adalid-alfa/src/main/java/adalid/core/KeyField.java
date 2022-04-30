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

import adalid.core.enums.*;
import adalid.core.interfaces.*;
import adalid.core.wrappers.*;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public class KeyField extends AbstractArtifact {

    private static final String EOL = "\n";

    private final Property _property;

    private final SortOption _sortOption;

    /**
     * @return the property
     */
    public Property getProperty() {
        return _property;
    }

    /**
     * @return the sort option
     */
    public SortOption getSortOption() {
        return _sortOption;
    }

    KeyField(Key key, Property property) {
        this(key, property, SortOption.ASC);
    }

    KeyField(Key key, Property property, SortOption sortOption) {
        super();
        _property = property;
        _sortOption = sortOption;
        init(key);
    }

    private void init(Key key) {
        initDeclaringArtifact(key);
    }

    /**
     * @return the default wrapper class
     */
    @Override
    public Class<? extends KeyFieldWrapper> getDefaultWrapperClass() {
        return KeyFieldWrapper.class;
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
                string += fee + tab + "sortOption" + faa + _sortOption + foo;
            }
        }
        return string;
    }
    // </editor-fold>

}
