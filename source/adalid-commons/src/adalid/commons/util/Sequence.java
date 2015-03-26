/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.commons.util;

/**
 * @author Jorge Campins
 */
public class Sequence {

    private int _i;

    private Sequence(int i) {
        _i = i < 0 ? 0 : i;
    }

    public static Sequence startWith(int i) {
        return new Sequence(i);
    }

    public int next() {
        return _i++;
    }

}
