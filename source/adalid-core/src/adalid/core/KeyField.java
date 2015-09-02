/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core;

import adalid.core.enums.SortOption;
import adalid.core.interfaces.Property;
import adalid.core.wrappers.KeyFieldWrapper;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public class KeyField extends AbstractArtifact {

    private Property _property;

    private SortOption _sortOption = SortOption.ASC;

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

    private KeyField() {
        super();
    }

    KeyField(Property property) {
        this(property, SortOption.ASC);
    }

    KeyField(Property property, SortOption sortOption) {
        super();
        _property = property;
        _sortOption = sortOption;
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
