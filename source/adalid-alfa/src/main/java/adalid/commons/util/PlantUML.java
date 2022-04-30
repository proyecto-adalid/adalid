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

import java.io.File;
import java.io.IOException;
import java.util.List;
import net.sourceforge.plantuml.GeneratedImage;
import net.sourceforge.plantuml.SourceFileReader;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class PlantUML {

    private static final Logger logger = Logger.getLogger(PlantUML.class);

    public static File generateImage(File file) {
        try {
            SourceFileReader reader = new SourceFileReader(file);
            List<GeneratedImage> list = reader.getGeneratedImages();
            if (list != null && !list.isEmpty()) {
                return list.get(0).getPngFile();
            }
        } catch (IOException ex) {
            logger.fatal(ThrowableUtils.getString(ex), ex);
        }
        return null;
    }

}
