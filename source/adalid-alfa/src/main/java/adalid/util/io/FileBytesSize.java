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
package adalid.util.io;

/**
 *
 * @author Jorge Campins
 */
public enum FileBytesSize {

    TINY(400L),
    SMALL(2000L),
    MEDIUM(8000L),
    LARGE(24000L),
    HUGE(Long.MAX_VALUE);

    final long limit;

    FileBytesSize(long l) {
        limit = l;
    }

    /**
     * @return the limit
     */
    public long getLimit() {
        return limit;
    }

}
