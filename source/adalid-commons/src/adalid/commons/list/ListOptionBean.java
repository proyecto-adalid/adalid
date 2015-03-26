/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los términos
 * de la licencia "GNU General Public License" publicada por la Fundación "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser útil, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas información.
 */
package adalid.commons.list;

/**
 * @author Jorge Campins
 */
public class ListOptionBean implements ListOption {

    private Object optionValue;

    private String optionLabel;

    private String optionDescription;

    private boolean optionDisabled;

    public ListOptionBean(Object optionValue, String optionLabel) {
        init(optionValue, optionLabel, null, false);
    }

    public ListOptionBean(Object optionValue, String optionLabel, String optionDescription) {
        init(optionValue, optionLabel, optionDescription, false);
    }

    public ListOptionBean(Object optionValue, String optionLabel, String optionDescription, boolean optionDisabled) {
        init(optionValue, optionLabel, optionDescription, optionDisabled);
    }

    private void init(Object optionValue, String optionLabel, String optionDescription, boolean optionDisabled) {
        this.optionValue = optionValue;
        this.optionLabel = optionLabel;
        this.optionDescription = optionDescription;
        this.optionDisabled = optionDisabled;
    }

    @Override
    public Object getOptionValue() {
        return optionValue;
    }

    public void setOptionValue(Object optionValue) {
        this.optionValue = optionValue;
    }

    @Override
    public String getOptionLabel() {
        return optionLabel;
    }

    public void setOptionLabel(String optionLabel) {
        this.optionLabel = optionLabel;
    }

    @Override
    public String getOptionDescription() {
        return optionDescription;
    }

    public void setOptionDescription(String optionDescription) {
        this.optionDescription = optionDescription;
    }

    @Override
    public boolean isOptionDisabled() {
        return optionDisabled;
    }

    public void setOptionDisabled(boolean optionDisabled) {
        this.optionDisabled = optionDisabled;
    }

}
