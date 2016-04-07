/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
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
