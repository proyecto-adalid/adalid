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
package adalid.util.velocity;

import adalid.util.meta.sql.*;
import java.nio.file.Path;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class Matchmaker {

    private static final Logger logger = Logger.getLogger(Matchmaker.class);

    private static final String separator = System.getProperty("file.separator");

    private static final Set<String> keys1 = new TreeSet<>();

    public static void main(String[] args) {
        String path1 = "adalid\\source\\adalid-oracle\\resources\\velocity";
        String path2 = "adalid\\source\\development\\resources\\velocity";
        match(path1, path2);
    }

    private static void match(String path1, String path2) {
        MetaFolderSql meta1 = new MetaFolderSql(path1);
        MetaFolderSql meta2 = new MetaFolderSql(path2);
        boolean read1 = meta1.read();
        boolean read2 = meta2.read();
        if (read1 && read2) {
            String key1;
            String sep1 = meta1.getMetaFolderPath().toString() + separator;
            logger.info("folder = " + sep1);
            for (Path path : meta1.getFiles().keySet()) {
                key1 = StringUtils.substringAfter(path.toString(), sep1);
                keys1.add(key1);
            }
            String key2;
            String sep2 = meta2.getMetaFolderPath().toString() + separator;
            logger.info("folder = " + sep2);
            for (Path path : meta2.getFiles().keySet()) {
                key2 = StringUtils.substringAfter(path.toString(), sep2);
                if (keys1.contains(key2)) {
                    logger.info("file = " + key2);
                }
            }
        }
    }

}
