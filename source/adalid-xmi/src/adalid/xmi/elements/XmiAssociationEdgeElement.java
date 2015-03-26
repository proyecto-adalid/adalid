/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adalid.xmi.elements;

import adalid.xmi.util.XmiTags;
import org.apache.log4j.Logger;
import org.w3c.dom.Element;

/**
 *
 * @author cmedina
 */
public class XmiAssociationEdgeElement extends XmiElement {

    private static Logger logger = Logger.getLogger(XmiAssociationEdgeElement.class);

    public XmiAssociationEdgeElement(Element _element, XmiDomTree _xmiDomTree) {
        super(_element, _xmiDomTree);
    }

    public XmiAssociationEdgeElement(XmiElement _xmiElement, XmiDomTree _xmiDomTree) {
        super(_xmiElement, _xmiDomTree);
    }

    public boolean isOneMultiplicity() {
        return (this.getUpperValue().equals(XmiTags.ONE_TAG) || (this.getLowerValue().equals(XmiTags.ONE_TAG) && this.getUpperValue().isEmpty()));
    }

    public Boolean isManyMultiplicity() {
        return (this.getUpperValue().equals(XmiTags.MANY_TAG) || (this.getLowerValue().equals(XmiTags.MANY_TAG) && this.getUpperValue().isEmpty()));
    }

    public String getLowerValue() {
        String lowerValue = "";
        if ((element.getElementsByTagName(XmiTags.LOWER_VALUE_TAG).getLength()) != 0) {
            lowerValue = ((Element) element.getElementsByTagName(XmiTags.LOWER_VALUE_TAG).item(0)).getAttribute(XmiTags.VALUE_TAG);
        }
        return lowerValue;
    }

    public String getUpperValue() {
        String upperValue = "";
        if ((element.getElementsByTagName(XmiTags.UPPER_VALUE_TAG).getLength()) != 0) {
            upperValue = ((Element) element.getElementsByTagName(XmiTags.UPPER_VALUE_TAG).item(0)).getAttribute(XmiTags.VALUE_TAG);
        }
        return upperValue;
    }

    public String getReferencedAttributeId() {
        String referencedAttributeId = "";
        if (element.getElementsByTagName(XmiTags.REFERENCED_ATTRIBUTE).getLength() != 0) {
            referencedAttributeId = ((Element) element.getElementsByTagName(XmiTags.REFERENCED_ATTRIBUTE).item(0)).getAttribute(XmiTags.XMI_VALUE_TAG);
        }
        return referencedAttributeId;
    }

    public boolean isNavigable() {

        return (Boolean.parseBoolean(element.getAttribute(XmiTags.NAVIGABLE_TAG)));

    }

    public boolean isNullable() {
        return (this.getLowerValue().equals("0") || this.getUpperValue().equals("0"));
    }

}
