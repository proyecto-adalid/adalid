/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.util.info;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import static java.lang.System.out;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class JavaInfo {

    private static final String USER_DIR = System.getProperties().getProperty("user.dir");

    private static final String FILE_SEP = System.getProperties().getProperty("file.separator");

    private static final String MANIFEST_NAME = "MANIFEST.MF";

    private static final String MANIFEST_PATH = USER_DIR + FILE_SEP + MANIFEST_NAME;

    private static final Logger logger = Logger.getLogger(JavaInfo.class);

    private static final Attributes.Name[] ATTRIBUTE_NAMES = {
        Attributes.Name.EXTENSION_NAME,
        Attributes.Name.IMPLEMENTATION_TITLE,
        Attributes.Name.IMPLEMENTATION_VENDOR,
        Attributes.Name.IMPLEMENTATION_VERSION,
        Attributes.Name.SPECIFICATION_TITLE,
        Attributes.Name.SPECIFICATION_VENDOR,
        Attributes.Name.SPECIFICATION_VERSION
    };

    public static void main(String[] args) {
        printManifestInfo();
        if (args == null || args.length == 0) {
            printMainManifest();
        }
    }

    public static void printMainManifest() {
        try {
            printManifestInfo(null, true, new File(MANIFEST_PATH));
        } catch (IOException ex) {
            logger.fatal(ex);
        }
    }

    public static void printManifestInfo() {
        printManifestInfo(null);
    }

    public static void printManifestInfo(String extension) {
        printManifestInfo(extension, false);
    }

    public static void printManifestInfo(String extension, boolean details) {
        ClassLoader loader;
        Enumeration<URL> resources;
        try {
//          loader = ClassLoader.getSystemClassLoader();
            loader = Thread.currentThread().getContextClassLoader();
            resources = loader.getResources(JarFile.MANIFEST_NAME);
            printManifestInfo(extension, details, resources);
        } catch (IOException ex) {
            logger.fatal(ex);
        }
    }

    public static void printManifestInfo(String extension, boolean details, Enumeration<URL> resources) {
        URL url;
        while (resources.hasMoreElements()) {
            try {
                url = resources.nextElement();
                printManifestInfo(extension, details, url);
            } catch (Exception ex) {
                logger.error(ex);
            }
        }
    }

    public static void printManifestInfo(String extension, boolean details, URL url) throws IOException {
        InputStream stream = url.openStream();
        if (stream == null) {
            return;
        }
//      String file = url.getFile();
        String path = StringUtils.removeEndIgnoreCase(url.getPath(), "!/" + JarFile.MANIFEST_NAME);
        String name = StringUtils.substringAfterLast(path, "/");
        Manifest manifest = new Manifest(stream);
        if (!extensionNameMatches(manifest, extension)) {
            return;
        }
//      out.println(file);
        printManifestInfo(path, name, details, manifest);
    }

    public static void printManifestInfo(String extension, boolean details, File file) throws IOException {
//      String absolutePath = file.getAbsolutePath();
//      String canonicalPath = file.getCanonicalPath();
        String path = StringUtils.removeEndIgnoreCase(file.getPath(), FILE_SEP + MANIFEST_NAME);
        String name = StringUtils.substringAfterLast(path, FILE_SEP);
        InputStream stream = new FileInputStream(file);
        Manifest manifest = new Manifest(stream);
        if (!extensionNameMatches(manifest, extension)) {
            return;
        }
//      out.println(absolutePath);
//      out.println(canonicalPath);
        printManifestInfo(path, name, details, manifest);
    }

    public static void printManifestInfo(String path, String name, boolean details, Manifest manifest) {
        Object object;
        String string;
        out.println(path);
//      out.println(StringUtils.leftPad(++fileCount + "", 4, '0') + ":" + name);
        out.println(name);
        Attributes attributes = manifest.getMainAttributes();
        Map<String, Object> sortedAttributes;
        if (details) {
            sortedAttributes = sort(attributes);
            for (String key : sortedAttributes.keySet()) {
                object = sortedAttributes.get(key);
                if (object != null) {
                    out.println("\t" + key + ": " + object);
                }
            }
        } else {
            for (Attributes.Name attribute : ATTRIBUTE_NAMES) {
                string = attributes.getValue(attribute);
                if (StringUtils.isNotBlank(string)) {
                    out.println("\t" + attribute + ": " + string);
                }
            }
        }
        out.println();
    }

    private static boolean extensionNameMatches(Manifest manifest, String regex) {
        if (StringUtils.isBlank(regex)) {
            return true;
        }
        Attributes attributes = manifest.getMainAttributes();
        Object object = attributes.get(Attributes.Name.EXTENSION_NAME);
        if (object instanceof String) {
            String string = (String) object;
            return string.matches(regex);
        }
        return false;
    }

    private static Map<String, Object> sort(Attributes attributes) {
        Object object;
        Map<String, Object> map = new TreeMap<>();
        for (Object key : attributes.keySet()) {
            object = attributes.get(key);
            map.put(key.toString(), object);
        }
        return map;
    }

    public void printPackageDetails() {
        printPackageDetails(getClass().getPackage().getName());
    }

    public void printPackageDetails(String name) {
        Package pack = Package.getPackage(name);
        out.println("Package " + name + ": " + pack);
        if (pack != null) {
            out.println("\t" + Attributes.Name.IMPLEMENTATION_TITLE + ":   " + pack.getImplementationTitle());
            out.println("\t" + Attributes.Name.IMPLEMENTATION_VENDOR + ":  " + pack.getImplementationVendor());
            out.println("\t" + Attributes.Name.IMPLEMENTATION_VERSION + ": " + pack.getImplementationVersion());
            out.println("\t" + Attributes.Name.SPECIFICATION_TITLE + ":    " + pack.getSpecificationTitle());
            out.println("\t" + Attributes.Name.SPECIFICATION_VENDOR + ":   " + pack.getSpecificationVendor());
            out.println("\t" + Attributes.Name.SPECIFICATION_VERSION + ":  " + pack.getSpecificationVersion());
            out.println();
        }
    }

}
