/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adalid.xmi.types;

import adalid.xmi.util.Typenames;
import org.apache.log4j.Logger;

/**
 *
 * @author cmedina
 */
public class XmiRowField {

    private static Logger logger = Logger.getLogger(XmiRowField.class);

    public String name;

    private String value;

    private String type;

    private String valueString;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    public boolean isValidValue(String value) {
        if (type.equalsIgnoreCase(Typenames.StringProperty)) {
            return true;
        }
        if (type.equalsIgnoreCase(Typenames.IntegerProperty)) {
            try {
                Integer.parseInt(value);
            } catch (NumberFormatException ex) {
                return false;
            } finally {
                return true;
            }
        }
        if (type.equalsIgnoreCase(Typenames.BooleanProperty)) {
            return true;
        }
        return false;
    }

    /**
     * @return the valueString
     */
    public String getValueString() {
        if (type.equalsIgnoreCase(Typenames.StringProperty)) {
            valueString = "\"" + value + "\"";
        } else {
            valueString = value;
        }
        return valueString;
    }

}
