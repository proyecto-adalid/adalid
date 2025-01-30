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
package adalid.commons.list;

import java.util.Comparator;

/**
 * @author Jorge Campins
 */
public class ListOptionLabelComparator implements Comparator<ListOption> {

    @Override
    public int compare(ListOption opt1, ListOption opt2) {
        Long val1 = null;
        Long val2 = null;

        if (opt1.getOptionValue() instanceof Integer integer) {
            val1 = Long.valueOf(integer);
        } else if (opt1.getOptionValue() instanceof Long aLong) {
            val1 = aLong;
        }

        if (opt2.getOptionValue() instanceof Integer integer) {
            val2 = Long.valueOf(integer);
        } else if (opt2.getOptionValue() instanceof Long aLong) {
            val2 = aLong;
        }

        if (val1 == null && val2 != null) {
            return -1;
        }

        if (val2 == null && val1 != null) {
            return 1;
        }

        String lab1 = opt1.getOptionLabel();
        String lab2 = opt2.getOptionLabel();

        if (lab1 == null && lab2 == null) {
            return 0;
        }

        if (lab1 == null) {
            return -1;
        }

        if (lab2 == null) {
            return 1;
        }

        return lab1.compareToIgnoreCase(lab2);
    }

}
