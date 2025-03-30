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

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.GeneratedImage;
import net.sourceforge.plantuml.SourceFileReader;
import net.sourceforge.plantuml.SourceStringReader;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class PlantUML {

    private static final Logger logger = Logger.getLogger(PlantUML.class);

    public static File generateImage(File file) {
        return generateImage(file, null);
    }

    public static File generateImage(File file, String format) {
        return file == null ? null : "svg".equalsIgnoreCase(format) ? svg(file) : png(file);
    }

    private static File svg(File file) {
        try {
            String output = StringUtils.substringBeforeLast(file.getName(), ".") + ".svg";
            File outputFile = new File(file.getAbsoluteFile().getParent(), output);
            String string = Files.readString(file.toPath(), StandardCharsets.UTF_8);
            SourceStringReader reader = new SourceStringReader(string);
            /*
            try (OutputStream outputStream = new FileOutputStream(outputFile.getAbsolutePath())) {
                reader.outputImage(outputStream, 0, new FileFormatOption(FileFormat.SVG));
            }
            /**/
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                reader.outputImage(outputStream, 0, new FileFormatOption(FileFormat.SVG));
                String top = outputStream.toString("UTF-8");
                String svg = top.replace("target=\"_top\"", "target=\"_parent\"")/*.replace("><", ">\n<")*/;
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
                    writer.write(svg);
                    writer.close();
                }
            }
            return outputFile;
        } catch (IOException ex) {
            logger.fatal(ThrowableUtils.getString(ex), ex);
            return null;
        }
    }

    private static File png(File file) {
        try {
            SourceFileReader reader = new SourceFileReader(file, file.getAbsoluteFile().getParentFile(), StandardCharsets.UTF_8.name());
            List<GeneratedImage> list = reader.getGeneratedImages();
            return list == null || list.isEmpty() ? null : list.get(0).getPngFile();
        } catch (IOException ex) {
            logger.fatal(ThrowableUtils.getString(ex), ex);
            return null;
        }
    }

}
