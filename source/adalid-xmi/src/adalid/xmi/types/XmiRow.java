/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adalid.xmi.types;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author cmedina
 */
public class XmiRow {

    private String name = "";

    private Map<String, XmiRowField> rowFieldsMap = new LinkedHashMap<>();

    private List<XmiRowField> rowFieldsList = new ArrayList<>();

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
     * @return the rowFieldsMap
     */
    public Map<String, XmiRowField> getRowFieldsMap() {
        return rowFieldsMap;
    }

    /**
     * @param rowFieldsMap the rowFieldsMap to set
     */
    public void setRowFieldsMap(Map<String, XmiRowField> rowFieldsMap) {
        this.rowFieldsMap = rowFieldsMap;
    }

    /**
     * @return the rowFieldsList
     */
    public List<XmiRowField> getRowFieldsList() {
        if (rowFieldsList.isEmpty()) {
            for (XmiRowField rowField : this.getRowFieldsMap().values()) {
                if (rowField != null) {
                    rowFieldsList.add(rowField);
                }
            }
        }
        return rowFieldsList;
    }

    /**
     * @param rowFieldsList the rowFieldsList to set
     */
    public void setRowFieldsList(List<XmiRowField> rowFieldsList) {
        this.rowFieldsList = rowFieldsList;
    }

}
