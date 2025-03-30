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
package adalid.commons.util;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author Jorge Campins
 */
public class XmlUtils {

    private static final Logger logger = Logger.getLogger(XmlUtils.class);

    private static final String USER_DIR = System.getProperty("user.dir");

    private static final String FILE_SEP = System.getProperty("file.separator");

    private static final String XML1 = "<?xml", XML2 = "?>", LF = "\n";

    private static final String DOCTYPE = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\""
        + " \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">";

    public static File toXmlFile(Object object) {
        if (object == null) {
            return null;
        }
        String pathname = USER_DIR + FILE_SEP + "xml" + FILE_SEP + object.getClass().getSimpleName() + ".xml";
        return toXmlFile(object, pathname);
    }

    public static File toXmlFile(Object object, String pathname) {
        if (object == null || pathname == null) {
            return null;
        }
        File file = new File(pathname);
        if (file.getParentFile().isDirectory() || file.getParentFile().mkdirs()) {
            try {
                JAXBContext context = JAXBContext.newInstance(object.getClass());
                Marshaller marshaller = context.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                marshaller.marshal(object, file);
                return file;
            } catch (JAXBException ex) {
                logger.error(ex);
            }
        }
        return null;
    }

    public static String toXmlString(Object object) {
        return toXmlString(object, false);
    }

    public static String toXmlString(Object object, boolean prologless) {
        if (object == null) {
            return null;
        }
        try {
            StringWriter sw = new StringWriter();
            JAXBContext context = JAXBContext.newInstance(object.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(object, sw);
            String string = sw.toString();
            boolean prologued = StringUtils.startsWithIgnoreCase(string, XML1);
            return (prologued && prologless) ? StringUtils.removeStart(StringUtils.substringAfter(string, XML2), LF) : string;
        } catch (JAXBException ex) {
            logger.error(ex);
            return object.toString();
        }
    }

    public static String toPrettyString(String xml) {
        return toPrettyString(xml, 4);
    }

    public static String toPrettyString(String xml, int indent) {
        if (StringUtils.isBlank(xml)) {
            return null;
        }
        try {
            // Turn xml string into a document
            byte[] bytes = html(xml).getBytes("utf-8");
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            InputSource inputSource = new InputSource(byteArrayInputStream);
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputSource);
            // Remove whitespaces outside tags
            document.normalize();
            XPath xPath = XPathFactory.newInstance().newXPath();
            NodeList nodeList = (NodeList) xPath.evaluate("//text()[normalize-space()='']", document, XPathConstants.NODESET);
            for (int i = 0; i < nodeList.getLength(); ++i) {
                Node node = nodeList.item(i);
                node.getParentNode().removeChild(node);
            }
            // Setup pretty print options
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setAttribute("indent-number", indent);
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            // Return pretty print xml string
            StringWriter stringWriter = new StringWriter();
            transformer.transform(new DOMSource(document), new StreamResult(stringWriter));
            return stringWriter.toString();
        } catch (DOMException | IOException | IllegalArgumentException | ParserConfigurationException | SAXException | TransformerException | XPathExpressionException ex) {
            return xml;
        } catch (RuntimeException ex) {
            logger.error(ex);
            return xml;
        }
    }

    private static String html(String xml) {
        if (xml.endsWith("</html>")) {
            if (xml.startsWith("<html>") || xml.startsWith("<html ")) {
                // Hay que cambiar los "br", "meta", etc, porque transform falla si el string contiene "empty tags" de HTML.
                // Mensaje: [Fatal Error] El tipo de elemento "br" debe finalizar por la etiqueta final coincidente "</br>".
                xml = xml.replace("<br>", "<br/>");
                xml = xml.replaceAll("<meta ([^>]*)>", "<meta $1/>");
                // El DOCTYPE es necesario para evitar errores por entidades no declaradas.
                // Mensaje: [Fatal Error] Se hizo referencia a la entidad "nbsp", pero no se declaró.
                return DOCTYPE + xml;
            }
        }
        return xml;
    }

}
