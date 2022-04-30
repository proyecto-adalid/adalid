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

import adalid.core.interfaces.*;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public class TabField extends AbstractArtifact {

    private static final String EOL = "\n";

    private final Property _property;

    private final EntityCollection _entityCollection;

    /**
     * @return the property
     */
    public Property getProperty() {
        return _property;
    }

    /**
     * @return the entity collection
     */
    public EntityCollection getEntityCollection() {
        return _entityCollection;
    }

    TabField(Tab tab, Property property) {
        super();
        _property = property;
        _entityCollection = null;
        init(tab);
    }

    TabField(Tab tab, EntityCollection collection) {
        super();
        _property = null;
        _entityCollection = collection;
        init(tab);
    }

    private void init(Tab tab) {
        initDeclaringArtifact(tab);
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
                String collection = _entityCollection == null ? "" : _entityCollection.getName();
                string += fee + tab + "property" + faa + collection + foo;
            }
        }
        return string;
    }
    // </editor-fold>

}
