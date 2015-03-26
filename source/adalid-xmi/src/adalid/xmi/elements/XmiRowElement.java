/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adalid.xmi.elements;

import adalid.xmi.types.XmiPersistentEntity;
import adalid.xmi.types.XmiProperty;
import adalid.xmi.types.XmiRow;
import adalid.xmi.types.XmiRowField;
import adalid.xmi.util.Typenames;
import adalid.xmi.util.XmiTags;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import java.util.StringTokenizer;
import org.w3c.dom.NodeList;

/**
 *
 * @author cmedina
 */
public class XmiRowElement extends XmiElement {

    private static Logger logger = Logger.getLogger(XmiRowElement.class);

    public XmiRowElement(Element _element, XmiDomTree _xmiDomTree) {
        super(_element, _xmiDomTree);
        //fillTaggedValuesMap();
    }

    public XmiRowElement(XmiElement _xmiElement, XmiDomTree _xmiDomTree) {
        super(_xmiElement, _xmiDomTree);
        //fillTaggedValuesMap();
    }

    public boolean validateRowElement() {
        return (element.getAttribute(XmiTags.XMI_TYPE_TAG).equals(XmiTags.UML_PROPERTY_TAG)
            && element.getAttribute(XmiTags.ASSOCIATION_TAG).isEmpty()
            && validateRowStereotype());
    }

    private boolean validateRowStereotype() {
        NodeList stereotypeNodeList = element.getElementsByTagName(XmiTags.STEREOTYPE_TAG);
        for (int i = 0; i < stereotypeNodeList.getLength(); i++) {
            Element stereotypeNode = (Element) stereotypeNodeList.item(i);
            if (xmiDomTree.stereotypeIdMap.get(stereotypeNode.getAttribute(XmiTags.XMI_VALUE_TAG)).equalsIgnoreCase(XmiTags.CONSTANT_STEREOTYPE)) {
                return true;
            }
        }
        return false;
    }

    //es el unico que recibe entity porque debe conocer el orden de los properties
    //recibe counter porque debe saber cuantas row llevan en caso de que haya que cumplir orden
    public XmiRow getRowFromElement(XmiPersistentEntity entity, int rowsCounter) {
        XmiRow row = null;
        List<String> initialValuesList = new ArrayList<String>();
        //fill initial values list
        String initialValueString = "";
        String token;
        if (element.getElementsByTagName(XmiTags.INITIAL_VALUE_TAG).getLength() == 1) {
            Element newElement = (Element) element.getElementsByTagName(XmiTags.INITIAL_VALUE_TAG).item(0);
            initialValueString = newElement.getAttribute(XmiTags.VALUE_TAG);
            StringTokenizer tokens = new StringTokenizer(initialValueString, ",");
            while (tokens.hasMoreTokens()) {
                token = tokens.nextToken();
                initialValuesList.add(token.trim());
            }
        }
        if (!this.getNameTag().isEmpty()) {
            XmiRowField rowField;
            String value;
            row = new XmiRow();
            row.setName(this.getNameTag());
            //Como minimo todo row tiene dos valores
            if (entity.getPropertiesMap().isEmpty() && entity.isEnumeration()) {
                rowField = new XmiRowField();
                rowField.setName("numero");
                rowField.setType(Typenames.IntegerProperty);
                if (initialValuesList.size() >= 1 && rowField.isValidValue(initialValuesList.get(0))) {
                    rowField.setValue(initialValuesList.get(0));
                    row.getRowFieldsMap().put(rowField.getName(), rowField);
                    //System.out.println("value "+row.getName()+"."+rowField.getName()+"= "+rowField.getValue());
                } else {
                    Integer size = rowsCounter + 1;
                    rowField.setValue(size.toString());
                    row.getRowFieldsMap().put(rowField.getName(), rowField);
                    //System.out.println("value "+row.getName()+"."+rowField.getName()+"= "+rowField.getValue());
                }
            }
//                rowField=new XmiRowField();
//                rowField.setName("codigo");
//                rowField.setType(Typenames.StringProperty);
//                if(initialValuesList.size()>=2 && rowField.isValidValue(initialValuesList.get(1))){
//                    rowField.setValue(initialValuesList.get(1));
//                    row.getRowFieldsMap().put(rowField.getName(), rowField);
//                    //System.out.println("value "+row.getName()+"."+rowField.getName()+"= "+rowField.getValue());
//                }else{
//                   rowField.setValue(row.getName());
//                   row.getRowFieldsMap().put(rowField.getName(), rowField);
//                   //System.out.println("value "+row.getName()+"."+rowField.getName()+"= "+rowField.getValue());
//                }
//            }else {
//                int i = 0;
//                for (XmiProperty property : entity.getPropertiesMap().values()) {
//                    if (property != null) {
//                        rowField = new XmiRowField();
//                        rowField.setName(property.getName());
//                        rowField.setType(property.getPropertyType());
//                        value = initialValuesList.get(i);
//                        if (rowField.isValidValue(value) && !rowField.getName().isEmpty() && !rowField.getType().isEmpty()) {
//                            rowField.setValue(initialValuesList.get(i));
//                            row.getRowFieldsMap().put(rowField.getName(), rowField);
//                        } else {
//                            logger.error("Invalid row value " + value + " for rowField " + rowField.getName() + " : " + rowField.getType());
//                            break;
//                        }
//                        i++;
            //row.getRowFieldsList().add(rowField);
//                    }
        }
        if (row != null) {
            setTaggedValues(row);
        }
        return row;
    }

    private void setTaggedValues(XmiRow row) {
        //hasta los momentos no se procesa ningun tagged value de row
    }

}
