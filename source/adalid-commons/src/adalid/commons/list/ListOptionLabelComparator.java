/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los términos
 * de la licencia "GNU General Public License" publicada por la Fundación "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser útil, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas información.
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

        if (opt1.getOptionValue() instanceof Integer) {
            val1 = new Long((Integer) opt1.getOptionValue());
        } else if (opt1.getOptionValue() instanceof Long) {
            val1 = (Long) opt1.getOptionValue();
        }

        if (opt2.getOptionValue() instanceof Integer) {
            val2 = new Long((Integer) opt2.getOptionValue());
        } else if (opt2.getOptionValue() instanceof Long) {
            val2 = (Long) opt2.getOptionValue();
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
