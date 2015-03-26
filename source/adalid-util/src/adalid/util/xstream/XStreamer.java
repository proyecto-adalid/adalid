/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.util.xstream;

import adalid.commons.util.ThrowableUtils;
import com.thoughtworks.xstream.XStream;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class XStreamer {

    static final Logger logger = Logger.getLogger(XStreamer.class);

    public static void write(XStream xstream, Object obj, String filename) throws Exception {
        if (xstream == null || obj == null || filename == null) {
            return;
        }
        String string = xstream.toXML(obj);
        if (string != null) {
            FileWriter fileWriter = null;
            BufferedWriter bufferedWriter = null;
            try {
                fileWriter = new FileWriter(filename);
                bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(string);
            } catch (IOException ex) {
                throw ex;
            } finally {
                if (bufferedWriter != null) {
                    try {
                        bufferedWriter.close();
                    } catch (IOException ex) {
                        logger.fatal(ThrowableUtils.getString(ex), ex);
                    }
                }
                if (fileWriter != null) {
                    try {
                        fileWriter.close();
                    } catch (IOException ex) {
                        logger.fatal(ThrowableUtils.getString(ex), ex);
                    }
                }
            }
        }
    }

}
