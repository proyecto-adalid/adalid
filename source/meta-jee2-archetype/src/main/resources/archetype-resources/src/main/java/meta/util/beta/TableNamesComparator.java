#set($dollar = '$')
#set($pound = '#')
#set($backslash = '\')
/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package ${package}.meta.util.beta;

import java.util.Comparator;

class TableNamesComparator implements Comparator<String> {

    @Override
    public int compare(String o1, String o2) {
        if (o1.length() != o2.length()) {
//          return o1.length() - o2.length(); // ascending
            return o2.length() - o1.length(); // descending
        }
        return o1.compareTo(o2);
    }

}
