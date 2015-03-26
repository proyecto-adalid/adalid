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
public interface ListOption {

    public Object getOptionValue();

    public String getOptionLabel();

    public String getOptionDescription();

    public boolean isOptionDisabled();

}
