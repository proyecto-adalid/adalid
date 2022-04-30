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

/**
 * @author Jorge Campins
 */
public class ListOptionBean implements ListOption {

    private Object optionValue;

    private String optionLabel;

    private String optionDescription;

    private boolean optionDisabled;

    private boolean noSelectionOption;

    public ListOptionBean(Object optionValue, String optionLabel) {
        init(optionValue, optionLabel, null, false, false);
    }

    public ListOptionBean(Object optionValue, String optionLabel, String optionDescription) {
        init(optionValue, optionLabel, optionDescription, false, false);
    }

    public ListOptionBean(Object optionValue, String optionLabel, String optionDescription, boolean optionDisabled) {
        init(optionValue, optionLabel, optionDescription, optionDisabled, false);
    }

    public ListOptionBean(Object optionValue, String optionLabel, String optionDescription, boolean optionDisabled, boolean noSelectionOption) {
        init(optionValue, optionLabel, optionDescription, optionDisabled, noSelectionOption);
    }

    private void init(Object optionValue, String optionLabel, String optionDescription, boolean optionDisabled, boolean noSelectionOption) {
        this.optionValue = optionValue;
        this.optionLabel = optionLabel;
        this.optionDescription = optionDescription;
        this.optionDisabled = optionDisabled;
        this.noSelectionOption = noSelectionOption;
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

    @Override
    public boolean isNoSelectionOption() {
        return noSelectionOption;
    }

    public void setNoSelectionOption(boolean noSelectionOption) {
        this.noSelectionOption = noSelectionOption;
    }

}
