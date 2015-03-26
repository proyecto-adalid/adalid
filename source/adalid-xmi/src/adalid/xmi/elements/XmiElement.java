/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package adalid.xmi.elements;

import adalid.xmi.util.UmlTags;
import adalid.xmi.util.XmiTags;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author cmedina
 */
public class XmiElement {

    protected XmiDomTree xmiDomTree;

    protected Element element;

    protected Map<String, String> taggedValuesMap = new LinkedHashMap<String, String>();

    private String packageContainer = "";

    private XmiElement() {
    }

    public XmiElement(Element _element, XmiDomTree _xmiDomTree) {
        element = _element;
        xmiDomTree = _xmiDomTree;
        taggedValuesMap = new LinkedHashMap<String, String>();
        fillTaggedValuesMap();
    }

    public XmiElement(XmiElement _xmiElement, XmiDomTree _xmiDomTree) {
        element = _xmiElement.element;
        packageContainer = _xmiElement.packageContainer;
        xmiDomTree = _xmiDomTree;
        taggedValuesMap = new LinkedHashMap<String, String>();
        fillTaggedValuesMap();
    }

    public String getAttribute(String tag) {
        return element.getAttribute(tag);
    }

    public String getXmiId() {
        return element.getAttribute(XmiTags.XMI_ID_TAG);
    }

    public String getIdRef() {
        return element.getAttribute(XmiTags.XMI_IDREF_TAG);
    }

    public String getType() {
        return element.getAttribute(XmiTags.TYPE_TAG);
    }

    public String getXmiType() {
        return element.getAttribute(XmiTags.XMI_TYPE_TAG);
    }

    public String getNameTag() {
        return element.getAttribute(XmiTags.NAME_TAG);
    }

    protected boolean hasStereotype(String stereotype) {
        NodeList stereotypeNodeList = element.getElementsByTagName(XmiTags.STEREOTYPE_TAG);
        for (int i = 0; i < stereotypeNodeList.getLength(); i++) {
            Element stereotypeNode = (Element) stereotypeNodeList.item(i);
            if (xmiDomTree.stereotypeIdMap.get(stereotypeNode.getAttribute(XmiTags.XMI_VALUE_TAG)).equalsIgnoreCase(stereotype)) {
                return true;
            }
        }
        return false;
    }

    protected final void fillTaggedValuesMap() {

        NodeList children = element.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            if (children.item(i).getNodeName().equals(XmiTags.XMI_EXTENSION_TAG)) {
                NodeList tags = ((Element) children.item(i)).getElementsByTagName(XmiTags.TAGGEDVALUE_TAG);
                for (int j = 0; j < tags.getLength(); j++) {
                    Element taggedValueNode = (Element) tags.item(j);
                    String tagName = StringUtils.remove(taggedValueNode.getAttribute(XmiTags.TAGNAME_TAG), " ");
                    String tagValue = taggedValueNode.getAttribute(XmiTags.VALUE_TAG);
                    if (tagValue.equalsIgnoreCase("true") || tagValue.equalsIgnoreCase("false")) {
                        tagValue = StringUtils.uncapitalize(tagValue);
                    }
                    taggedValuesMap.put(tagName, tagValue);
                }
            }
        }
    }

    public String getTaggedValue(String tag) {
        return (String) taggedValuesMap.get(tag);
    }

    public Map<String, String> getTaggedValuesMap() {
        return taggedValuesMap;
    }

    /**
     * @return the packageContainer
     */
    public String getPackageContainer() {
        return packageContainer;
    }

    /**
     * @param packageContainer the packageContainer to set
     */
    public void setPackageContainer(String packageContainer) {
        this.packageContainer = packageContainer;
    }

}
