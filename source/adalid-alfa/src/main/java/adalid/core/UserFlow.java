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

import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public class UserFlow extends AbstractArtifact implements Comparable<UserFlow> {

    public UserFlow(String name) {
        super();
        init(name);
    }

    private void init(String name) {
        setDeclared(name);
    }

    // <editor-fold defaultstate="collapsed" desc="Comparable">
    @Override
    public int compareTo(UserFlow o) {
        UserFlow that;
        if (o != null) {
            that = o;
            String thisName = StringUtils.trimToEmpty(this.getName());
            String thatName = StringUtils.trimToEmpty(that.getName());
            return thisName.compareTo(thatName);
        }
        return 0;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="toString">
    @Override
    protected String fieldsToString(int n, String key, boolean verbose, boolean fields, boolean maps) {
        String string = super.fieldsToString(n, key, verbose, fields, maps);
        return string;
    }
    // </editor-fold>

}
