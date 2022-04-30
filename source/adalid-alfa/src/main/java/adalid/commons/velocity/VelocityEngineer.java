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
package adalid.commons.velocity;

import adalid.commons.bundles.*;
import adalid.commons.properties.*;
import adalid.commons.util.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnmappableCharacterException;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

/**
 * @author Jorge Campins
 */
public class VelocityEngineer {

    private static final boolean V17 = true;

    private static final Logger logger = Logger.getLogger(VelocityEngineer.class);

    /*
    configuration key 'file.resource.loader.path' has been deprecated in favor of 'resource.loader.file.path'
    private static final String FILE_RESOURCE_LOADER_PATH = "file.resource.loader.path";
    /**/
    private static final String FILE_RESOURCE_LOADER_PATH = V17 ? "file.resource.loader.path" : "resource.loader.file.path";

    private static final String USER_DIR = System.getProperty("user.dir");

    private static final String FILE_SEPARATOR = System.getProperty("file.separator");

    private static final String LINE_SEPARATOR = System.lineSeparator();

    private static final String LF = "\n";

    private static final String CRLF = "\r\n";

    private static final String LF_REGEX = "(?<!\\r)\\n";

    private static final String CRLF_REGEX = "\\r\\n";

    /*
    private static final boolean LF_EQ_LINE_SEPARATOR = LF.equals(LINE_SEPARATOR);

    private static final boolean CRLF_EQ_LINE_SEPARATOR = CRLF.equals(LINE_SEPARATOR);

    /**/
//  private static final String USER_VELOCITY_RESOURCES_DIR = USER_DIR + FILE_SEPARATOR + "resources" + FILE_SEPARATOR + "velocity";
//
    private static final String VELOCITY_FILE_KEY = "velocity.properties.file";

    private static final String VELOCITY_SUP_FILE_KEY = "velocity.supplementary.properties.file";

    /*
    configuration key 'velocimacro.library' has been deprecated in favor of 'velocimacro.library.path'
    private static final String VELOCIMACRO_LIBRARY = "velocimacro.library";
    /**/
    private static final String VELOCIMACRO_LIBRARY = V17 ? "velocimacro.library" : "velocimacro.library.path";

    private static final String VELOCITY_TEMPLATE_ENCODING = "velocity.template.encoding";

    private static final String VELOCITY_DOCUMENT_ENCODING = "velocity.document.encoding";

    private static final String VELOCITY_DOCUMENT_EOL = "velocity.document.eol";

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
        /*
        configuration key 'input.encoding' has been deprecated in favor of 'resource.default_encoding'
        property = Velocity.getProperty("input.encoding");
        /**/
        property = Velocity.getProperty(V17 ? "input.encoding" : "resource.default_encoding");
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
        List<String> list;
        try {
            list = new ArrayList<>();
            File[] velocityFolders = PropertiesHandler.getVelocityFolders();
            if (velocityFolders != null && velocityFolders.length > 0) {
                for (File velocityFolder : velocityFolders) {
                    list.add(velocityFolder.getPath());
                }
            }
            file = PropertiesHandler.getVelocityPropertiesFile();
            properties = PropertiesHandler.loadProperties(file);
            if (list.isEmpty()) {
                file = new File(USER_DIR);
                list.add(file.getPath());
            }
            setFileResourceLoaderPath(list);
            String value = StringUtils.join(list, ", ");
            properties.setProperty(FILE_RESOURCE_LOADER_PATH, value);
            String libraries = velocimacroLibraries(list);
            if (StringUtils.isNotBlank(libraries)) {
                properties.setProperty(VELOCIMACRO_LIBRARY, libraries);
            }
            /**/
            logger.info(VELOCITY_FILE_KEY + "=" + file.getCanonicalPath());
            logger.info(VELOCITY_SUP_FILE_KEY + "=" + PropertiesHandler.getVelocitySupplementaryPropertiesFile().getCanonicalPath());
            /**/
            Velocity.init(properties);
        } catch (IOException ex) {
            logger.fatal(ThrowableUtils.getString(ex), ex);
        }
    }

    private static void setFileResourceLoaderPath(List<String> list) {
        fileResourceLoaderPathArray = list.toArray(ArrUtils.arrayOf(String.class));
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
        write(context, tempname, filename, charset1, charset2, false);
    }

    public static void write(VelocityContext context, String tempname, String filename, String charset1, String charset2, boolean pretty) throws Exception {
        charset1 = StringUtils.defaultIfBlank(charset1, getTemplateEncoding(tempname));
        charset2 = StringUtils.defaultIfBlank(charset2, getDocumentEncoding(filename));
//      if (!charset1.equals(VELOCITY_TEMPLATE_DEFAULT_ENCODING)) {
//          log(Level.INFO, "write", "template=" + tempname, "template-encoding=" + charset1, "document-encoding=" + charset2);
//      }
//      if (!charset1.equals(charset2)) {
//          log(Level.WARN, "write", "template=" + tempname, "template-encoding=" + charset1, "document-encoding=" + charset2);
//      }
        StringWriter sw = merge(context, tempname, charset1);
        write(sw, filename, charset2, pretty);
    }

    private static final boolean handwriting = false;

    public static void write(StringWriter sw, String filename, String charset2, boolean pretty) throws Exception {
        if (sw == null) {
            return;
        }
        charset2 = StringUtils.defaultIfBlank(charset2, getDocumentEncoding(filename));
        if (handwriting) {
            FileOutputStream fileOutputStream;
            OutputStreamWriter fileWriter = null;
            BufferedWriter bufferedWriter = null;
            try {
                FileUtils.deleteQuietly(new File(filename));
                fileOutputStream = new FileOutputStream(filename);
                fileWriter = new OutputStreamWriter(fileOutputStream, charset2);
                bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(stringOf(sw, filename, pretty));
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
        } else {
            // do not delete the file to keep its permissions
            try {
                Path path = Paths.get(filename);
                Charset charset = Charset.forName(charset2);
                StandardOpenOption[] options = {StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE};
                try (OutputStreamWriter fileWriter = new OutputStreamWriter(Files.newOutputStream(path, options), charset);) {
                    try (BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);) {
                        bufferedWriter.write(stringOf(sw, filename, pretty));
                        bufferedWriter.flush();
                    }
                }
            } catch (IllegalCharsetNameException | UnsupportedCharsetException | UnmappableCharacterException ex) {
                FileUtils.deleteQuietly(new File(filename));
                throw new Exception("charset " + charset2 + " -> " + ex.getClass().getSimpleName() + ": " + ThrowableUtils.getString(ex));
            } catch (IllegalArgumentException | IOException ex) {
                FileUtils.deleteQuietly(new File(filename));
                throw new Exception(ex.getClass().getSimpleName() + ": " + ThrowableUtils.getString(ex));
            }
        }
    }

    private static final String[] pretty_xml_extensions = {"xml", "xhtml", "jrxml", "jrtx"};

    private static String stringOf(StringWriter sw, String filename, boolean pretty) {
        String string = sw.toString();
        String lcname = filename.toLowerCase();
        String extension = StringUtils.substringAfterLast(lcname, ".");
        boolean extended = StringUtils.isNotBlank(extension);
        if (extended) {
            if (extension.equals("bat")) {
                return string.replaceAll(LF_REGEX, CRLF);
            }
            if (extension.equals("sh")) {
                return string.replaceAll(CRLF_REGEX, LF);
            }
            if (pretty && ArrayUtils.contains(pretty_xml_extensions, extension)) {
                string = XmlUtils.toPrettyString(string);
            }
        }
        String eol = getDocumentEndOfLine(filename);
        if (eol.equals(CRLF)) {
            return string.replaceAll(LF_REGEX, CRLF);
        }
        if (eol.equals(LF)) {
            return string.replaceAll(CRLF_REGEX, LF);
        }
        return string;
    }

    /*
    private static void log(Level level, String method, Object... parameters) {
        if (LogUtils.foul(logger, level)) {
            return;
        }
        String message = signature(method, parameters);
        logger.log(level, message);
    }

    private static String signature(String method, Object... parameters) {
        String pattern = "{0}({1})";
        return MessageFormat.format(pattern, method, StringUtils.join(parameters, ", "));
    }

    /**/
    private static String getTemplateEncoding(String filename) {
        String extension = StringUtils.substringAfterLast(filename, ".");
        if (StringUtils.isBlank(extension)) {
            return VELOCITY_TEMPLATE_DEFAULT_ENCODING;
        }
        String key = VELOCITY_TEMPLATE_ENCODING + "." + extension.toLowerCase();
        String property = supplementaryProperties.getProperty(key);
        return StringUtils.isBlank(property) ? VELOCITY_TEMPLATE_DEFAULT_ENCODING : property;
    }

    private static String getDocumentEncoding(String filename) {
        String extension = StringUtils.substringAfterLast(filename, ".");
        if (StringUtils.isBlank(extension)) {
            return VELOCITY_DOCUMENT_DEFAULT_ENCODING;
        }
        String key = VELOCITY_DOCUMENT_ENCODING + "." + extension.toLowerCase();
        String property = supplementaryProperties.getProperty(key);
        return StringUtils.isBlank(property) ? VELOCITY_DOCUMENT_DEFAULT_ENCODING : property;
    }

    private static String getDocumentEndOfLine(String filename) {
        String extension = StringUtils.substringAfterLast(filename, ".");
        if (StringUtils.isBlank(extension)) {
            return LINE_SEPARATOR;
        }
        String key = VELOCITY_DOCUMENT_EOL + "." + extension.toLowerCase();
        String property = supplementaryProperties.getProperty(key);
        return StringUtils.isBlank(property) ? LINE_SEPARATOR
            : property.equalsIgnoreCase("CRLF") ? CRLF
            : property.equalsIgnoreCase("LF") ? LF
            : LINE_SEPARATOR;
    }

}
