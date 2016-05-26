/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los términos
 * de la licencia "GNU General Public License" publicada por la Fundación "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser útil, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas información.
 */
package adalid.commons.velocity;

import adalid.commons.bundles.Bundle;
import adalid.commons.properties.PropertiesHandler;
import adalid.commons.util.FilUtils;
import adalid.commons.util.StrUtils;
import adalid.commons.util.ThrowableUtils;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

/**
 * @author Jorge Campins
 */
public class VelocityEngineer {

    private static final Logger logger = Logger.getLogger(VelocityEngineer.class);

    private static final String FILE_RESOURCE_LOADER_PATH = "file.resource.loader.path";

    private static final String USER_DIR = System.getProperties().getProperty("user.dir");

    private static final String FILE_SEPARATOR = System.getProperties().getProperty("file.separator");

//  private static final String USER_VELOCITY_RESOURCES_DIR = USER_DIR + FILE_SEPARATOR + "resources" + FILE_SEPARATOR + "velocity";
//
    private static final String VELOCIMACRO_LIBRARY = "velocimacro.library";

    private static final String VELOCITY_TEMPLATE_ENCODING = "velocity.template.encoding";

    private static final String VELOCITY_DOCUMENT_ENCODING = "velocity.document.encoding";

    private static final String VELOCITY_TEMPLATE_DEFAULT_ENCODING;

    private static final String VELOCITY_DOCUMENT_DEFAULT_ENCODING;

    private static String[] fileResourceLoaderPathArray;

    private static final Properties supplementaryProperties;

    static {
        init();
        Object property;
        String str;
        String defaultStr;
        supplementaryProperties = PropertiesHandler.getVelocitySupplementaryProperties();
        property = Velocity.getProperty("input.encoding");
        str = StrUtils.getString(property);
        defaultStr = Bundle.getString(VELOCITY_TEMPLATE_ENCODING);
        VELOCITY_TEMPLATE_DEFAULT_ENCODING = StringUtils.defaultIfBlank(str, defaultStr);
        property = Velocity.getProperty("output.encoding");
        str = StrUtils.getString(property);
        defaultStr = Bundle.getString(VELOCITY_DOCUMENT_ENCODING);
        VELOCITY_DOCUMENT_DEFAULT_ENCODING = StringUtils.defaultIfBlank(str, defaultStr);
    }

    private static void init() {
        File file;
        Properties properties;
//      ExtendedProperties extendedProperties;
//      String[] frlp;
        List<String> list;
        try {
            list = new ArrayList<>();
//          file = new File(USER_VELOCITY_RESOURCES_DIR);
//          if (FilUtils.isVisibleDirectory(file)) {
////            list.addAll(FilUtils.directoriesPathList(file));
//              list.add(file.getPath());
//          }
            File[] velocityFolders = PropertiesHandler.getVelocityFolders();
            if (velocityFolders != null && velocityFolders.length > 0) {
                for (File velocityFolder : velocityFolders) {
//                  list.addAll(FilUtils.directoriesPathList(velocityFolder));
                    list.add(velocityFolder.getPath());
                }
            }
            file = PropertiesHandler.getVelocityPropertiesFile();
            properties = PropertiesHandler.loadProperties(file);
//          extendedProperties = PropertiesHandler.getExtendedProperties(file);
//          frlp = extendedProperties.getStringArray(FILE_RESOURCE_LOADER_PATH);
//          if (frlp != null && frlp.length > 0) {
//              for (String path : frlp) {
//                  path = FilUtils.fixPath(path);
//                  file = new File(path);
//                  if (FilUtils.isVisibleDirectory(file)) {
////                    list.addAll(FilUtils.directoriesPathList(file));
//                      list.add(file.getPath());
//                  }
//              }
//          }
            if (list.isEmpty()) {
                file = new File(USER_DIR);
//              list.addAll(FilUtils.directoriesPathList(file));
                list.add(file.getPath());
            }
            setFileResourceLoaderPath(list);
            String value = StringUtils.join(list, ", ");
            properties.setProperty(FILE_RESOURCE_LOADER_PATH, value);
            String libraries = velocimacroLibraries(list);
            if (StringUtils.isNotBlank(libraries)) {
                properties.setProperty(VELOCIMACRO_LIBRARY, libraries);
            }
            Velocity.init(properties);
        } catch (Exception ex) {
            logger.fatal(ThrowableUtils.getString(ex), ex);
        }
    }

    private static void setFileResourceLoaderPath(List<String> list) {
        fileResourceLoaderPathArray = list.toArray(new String[0]);
        logger.trace(FILE_RESOURCE_LOADER_PATH + " = ");
        for (String dir : fileResourceLoaderPathArray) {
            logger.trace("\t" + dir);
        }
    }

    private static String velocimacroLibraries(List<String> list) {
        File folder;
        File[] files;
        FileFilter macrosFileFilter = FilUtils.nameEndsWithFilter(".vm");
        Set<String> libraries = new LinkedHashSet<>();
        String macros = "macros";
        for (String path : list) {
            folder = new File(path + FILE_SEPARATOR + macros);
            if (FilUtils.isVisibleDirectory(folder)) {
                files = folder.listFiles(macrosFileFilter);
                for (File file : files) {
                    if (FilUtils.isVisibleFile(file)) {
                        libraries.add(macros + "/" + file.getName());
                    }
                }
            }
        }
        String value = StringUtils.join(libraries, ", ");
        logger.trace(VELOCIMACRO_LIBRARY + " = " + value);
        return value;
    }

    /**
     * @return the file resource loader path array
     */
    public static String[] getFileResourceLoaderPathArray() {
        return fileResourceLoaderPathArray;
    }

    public static void check() {
    }

    public static StringWriter merge(VelocityContext context, String tempname) throws Exception {
        return merge(context, tempname, getTemplateEncoding(tempname));
    }

    public static StringWriter merge(VelocityContext context, String tempname, String encoding) throws Exception {
        encoding = StringUtils.defaultIfBlank(encoding, getTemplateEncoding(tempname));
        Template template = Velocity.getTemplate(tempname, encoding);
        StringWriter sw = new StringWriter();
        template.merge(context, sw);
        return sw;
    }

    public static void write(VelocityContext context, String tempname, String filename) throws Exception {
        write(context, tempname, filename, getTemplateEncoding(tempname), null);
    }

    public static void write(VelocityContext context, String tempname, String filename, String charset1, String charset2) throws Exception {
        charset1 = StringUtils.defaultIfBlank(charset1, getTemplateEncoding(tempname));
        charset2 = StringUtils.defaultIfBlank(charset2, getDocumentEncoding(filename));
//      if (!charset1.equals(VELOCITY_TEMPLATE_DEFAULT_ENCODING)) {
//          log(Level.INFO, "write", "template=" + tempname, "template-encoding=" + charset1, "document-encoding=" + charset2);
//      }
//      if (!charset1.equals(charset2)) {
//          log(Level.WARN, "write", "template=" + tempname, "template-encoding=" + charset1, "document-encoding=" + charset2);
//      }
        StringWriter sw = merge(context, tempname, charset1);
        if (sw != null) {
            FileOutputStream fileOutputStream;
            OutputStreamWriter fileWriter = null;
            BufferedWriter bufferedWriter = null;
            try {
                fileOutputStream = new FileOutputStream(filename);
                fileWriter = new OutputStreamWriter(fileOutputStream, charset2);
                bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(sw.toString());
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

    private static void log(Level level, String method, Object... parameters) {
        if (foul(logger, level)) {
            return;
        }
        String message = signature(method, parameters);
        logger.log(level, message);
    }

    private static boolean fair(Logger logger, Level level) {
        return logger != null && level != null && logger.isEnabledFor(level) && !level.equals(Level.OFF);
    }

    private static boolean foul(Logger logger, Level level) {
        return !fair(logger, level);
    }

    private static String signature(String method, Object... parameters) {
        String pattern = "{0}({1})";
        return MessageFormat.format(pattern, method, StringUtils.join(parameters, ", "));
    }

    private static String getTemplateEncoding(String filename) {
//      String substring = StringUtils.substringAfterLast(filename, "/");
//      if (StringUtils.isBlank(substring)) {
//          return VELOCITY_TEMPLATE_DEFAULT_ENCODING;
//      }
//      String extension = StringUtils.substringAfter(substring, ".");
        String extension = StringUtils.substringAfterLast(filename, ".");
        if (StringUtils.isBlank(extension)) {
            return VELOCITY_TEMPLATE_DEFAULT_ENCODING;
        }
        String key = VELOCITY_TEMPLATE_ENCODING + "." + extension.toLowerCase();
        String property = supplementaryProperties.getProperty(key, VELOCITY_TEMPLATE_DEFAULT_ENCODING);
        String encoding = StringUtils.isBlank(property) ? VELOCITY_TEMPLATE_DEFAULT_ENCODING : property;
        return encoding;
    }

    private static String getDocumentEncoding(String filename) {
//      String substring = StringUtils.substringAfterLast(filename, FILE_SEPARATOR);
//      if (StringUtils.isBlank(substring)) {
//          return VELOCITY_DOCUMENT_DEFAULT_ENCODING;
//      }
//      String extension = StringUtils.substringAfter(substring, ".");
        String extension = StringUtils.substringAfterLast(filename, ".");
        if (StringUtils.isBlank(extension)) {
            return VELOCITY_DOCUMENT_DEFAULT_ENCODING;
        }
        String key = VELOCITY_DOCUMENT_ENCODING + "." + extension.toLowerCase();
        String property = supplementaryProperties.getProperty(key, VELOCITY_DOCUMENT_DEFAULT_ENCODING);
        String encoding = StringUtils.isBlank(property) ? VELOCITY_DOCUMENT_DEFAULT_ENCODING : property;
        return encoding;
    }

}
