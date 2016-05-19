/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los términos
 * de la licencia "GNU General Public License" publicada por la Fundación "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser útil, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas información.
 */
package adalid.commons.list;

import adalid.commons.bundles.Bundle;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Jorge Campins
 */
public class ListOptions {

    private static final String NULL_VALUE_STRING = Bundle.getString("null_value.string");

    private ArrayList<ListOption> list;

    private boolean addNullOption;

    private boolean sortedByLabel;

    boolean alreadySorted;

    public ListOptions(boolean anulable, boolean ordenado) {
        init(anulable, ordenado);
    }

    private void init(boolean anulable, boolean ordenado) {
        list = new ArrayList<>();
        addNullOption = anulable;
        sortedByLabel = ordenado;
        alreadySorted = true;
        if (addNullOption) {
            add("", NULL_VALUE_STRING);
        }
    }

    public boolean isAnulable() {
        return addNullOption;
    }

    public boolean isOrdenado() {
        return sortedByLabel;
    }

    public boolean add(ListOption option) {
        alreadySorted = false;
        return add(option.getOptionValue(), option.getOptionLabel(), option.getOptionDescription(), option.isOptionDisabled());
    }

    public boolean add(ListOption[] options) {
        boolean ok = true;
        for (ListOption option : options) {
            ok &= add(option);
        }
        return ok;
    }

    public boolean add(Object value, String label) {
        alreadySorted = false;
        return add(value, label, null);
    }

    public boolean add(Object value, String label, String description) {
        alreadySorted = false;
        return add(value, label, description, false);
    }

    public boolean add(Object value, String label, String description, boolean disabled) {
        alreadySorted = false;
        return list.add(new ListOptionBean(value, label, description, disabled));
    }

    public ArrayList<ListOption> getList() {
        sort();
        return list;
    }

    private void sort() {
        if (list.size() > 1 && !alreadySorted) {
            if (sortedByLabel) {
                Collections.sort(list, new ListOptionLabelComparator());
            } else {
                Collections.sort(list, new ListOptionValueComparator());
            }
            alreadySorted = true;
        }
    }

}
