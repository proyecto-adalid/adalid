import adalid.xmi.model.XmiProject;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 * @author cmedina
 */
public class Tester {

    static public void main(String[] arg) throws IOException, ParserConfigurationException, SAXException {
        XmiProject p = new XmiProject();
        p.writeEntityFiles();
    }

}
