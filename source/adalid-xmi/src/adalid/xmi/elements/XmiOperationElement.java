/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adalid.xmi.elements;

import adalid.xmi.types.XmiOperation;
import adalid.xmi.types.XmiParameter;
import adalid.xmi.util.UmlTags;
import adalid.xmi.util.XmiTags;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author cmedina
 */
public class XmiOperationElement extends XmiElement {

    private static Logger logger = Logger.getLogger(XmiOperationElement.class);

    private List<XmiParameterElement> parameterElementList = new ArrayList<XmiParameterElement>();

    ;

    public XmiOperationElement(Element _element, XmiDomTree xmiDomTree) {
        super(_element, xmiDomTree);
        fillParameterElementList();
        //fillTaggedValuesMap();
    }

    public XmiOperationElement(XmiElement _xmiElement, XmiDomTree xmiDomTree) {
        super(_xmiElement, xmiDomTree);
        parameterElementList = new ArrayList<XmiParameterElement>();
        fillParameterElementList();
        //fillTaggedValuesMap();
    }

    private void fillParameterElementList() {
        NodeList nodeList = element.getElementsByTagName(XmiTags.OWNED_PARAMETER_TAG);
        for (int i = 0; i < nodeList.getLength(); i++) {
            XmiElement currNode = new XmiElement((Element) nodeList.item(i), xmiDomTree);
            if (XmiDomTree.isParameterElement(currNode)) {
                XmiParameterElement xmiParameterElement = new XmiParameterElement(currNode, xmiDomTree);
                parameterElementList.add(xmiParameterElement);
            }
        }
    }

    public XmiOperation getOperationFromElement() {
        XmiOperation operation = null;
        if (!this.getNameTag().isEmpty()) {
            operation = new XmiOperation();
            operation.setOperationName(this.getNameTag());
            operation.setScopeString(this.getScopeString());
            for (int i = 0; i < parameterElementList.size(); i++) {
                XmiParameterElement parameterElement = parameterElementList.get(i);
                XmiParameter parameter = parameterElement.getParameterFromElement();
                if (parameter != null) {
                    operation.getParametersMap().put(parameterElement.getNameTag(), parameter);
                    logger.debug("Adding Parameter (" + parameterElement.getNameTag() + "," + parameter.getName() + ") to ParametersMap");
                }
            }
        }
        if (operation != null) {
            setTaggedValues(operation);
        }
        return operation;
    }

    private void setTaggedValues(XmiOperation operation) {
        Map<String, String> taggedValues = this.getTaggedValuesMap();
        try {
            operation.setAlias(taggedValues.get(UmlTags.alias));
        } catch (NullPointerException e) {
            logger.error("Undefined Tagged Value for Operation");
        }
    }

    /**
     * @return the scopeString
     */
    public String getScopeString() {
        return element.getAttribute(XmiTags.OWNER_SCOPE_TAG);
    }

    public boolean isInstanceScope() {
        return element.getAttribute(XmiTags.OWNER_SCOPE_TAG).equals(XmiTags.INSTANCE_SCOPE_TAG);
    }

    public boolean isClassifierScope() {
        return element.getAttribute(XmiTags.OWNER_SCOPE_TAG).equals(XmiTags.CLASSIFIER_SCOPE_TAG);
    }

}
