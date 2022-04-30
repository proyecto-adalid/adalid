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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class ManUtils {

    private static final Logger logger = Logger.getLogger(ManUtils.class);

    private static final String USER_DIR = System.getProperty("user.dir");

    private static final String FILE_SEP = System.getProperty("file.separator");

    private static final String MANIFEST_NAME = "MANIFEST.MF";

    private static final String MANIFEST_PATH = USER_DIR + FILE_SEP + MANIFEST_NAME;

    public static Manifest getManifest() {
        return getManifest(MANIFEST_PATH);
    }

    public static Manifest getManifest(String path) {
        File file = new File(path);
        if (FilUtils.isVisibleFile(file)) {
            try {
                InputStream stream = new FileInputStream(file);
                return new Manifest(stream);
            } catch (IOException ex) {
                logger.fatal(ThrowableUtils.getString(ex), ex);
            }
        }
        return null;
    }

    public static String getBuiltBy(Manifest manifest) {
        if (manifest == null) {
            manifest = getManifest();
        }
        if (manifest == null) {
            return null;
        }
        Attributes attributes = manifest.getMainAttributes();
        return attributes.getValue("Built-By");
    }

    public static String getManifestVersion(Manifest manifest) {
        if (manifest == null) {
            manifest = getManifest();
        }
        if (manifest == null) {
            return null;
        }
        Attributes attributes = manifest.getMainAttributes();
        return attributes.getValue(Attributes.Name.MANIFEST_VERSION);
    }

    public static String getSignatureVersion(Manifest manifest) {
        if (manifest == null) {
            manifest = getManifest();
        }
        if (manifest == null) {
            return null;
        }
        Attributes attributes = manifest.getMainAttributes();
        return attributes.getValue(Attributes.Name.SIGNATURE_VERSION);
    }

    public static String getContentType(Manifest manifest) {
        if (manifest == null) {
            manifest = getManifest();
        }
        if (manifest == null) {
            return null;
        }
        Attributes attributes = manifest.getMainAttributes();
        return attributes.getValue(Attributes.Name.CONTENT_TYPE);
    }

    public static String getClassPath(Manifest manifest) {
        if (manifest == null) {
            manifest = getManifest();
        }
        if (manifest == null) {
            return null;
        }
        Attributes attributes = manifest.getMainAttributes();
        return attributes.getValue(Attributes.Name.CLASS_PATH);
    }

    public static String getMainClass(Manifest manifest) {
        if (manifest == null) {
            manifest = getManifest();
        }
        if (manifest == null) {
            return null;
        }
        Attributes attributes = manifest.getMainAttributes();
        return attributes.getValue(Attributes.Name.MAIN_CLASS);
    }

    public static String getSealed(Manifest manifest) {
        if (manifest == null) {
            manifest = getManifest();
        }
        if (manifest == null) {
            return null;
        }
        Attributes attributes = manifest.getMainAttributes();
        return attributes.getValue(Attributes.Name.SEALED);
    }

    public static String getExtensionList(Manifest manifest) {
        if (manifest == null) {
            manifest = getManifest();
        }
        if (manifest == null) {
            return null;
        }
        Attributes attributes = manifest.getMainAttributes();
        return attributes.getValue(Attributes.Name.EXTENSION_LIST);
    }

    public static String getExtensionName(Manifest manifest) {
        if (manifest == null) {
            manifest = getManifest();
        }
        if (manifest == null) {
            return null;
        }
        Attributes attributes = manifest.getMainAttributes();
        return attributes.getValue(Attributes.Name.EXTENSION_NAME);
    }
//
//  public static String getExtensionInstallation(Manifest manifest) {
//      if (manifest == null) {
//          manifest = getManifest();
//      }
//      if (manifest == null) {
//          return null;
//      }
//      Attributes attributes = manifest.getMainAttributes();
//      return attributes.getValue(Attributes.Name.EXTENSION_INSTALLATION);
//  }

    public static String getImplementationTitle(Manifest manifest) {
        if (manifest == null) {
            manifest = getManifest();
        }
        if (manifest == null) {
            return null;
        }
        Attributes attributes = manifest.getMainAttributes();
        return attributes.getValue(Attributes.Name.IMPLEMENTATION_TITLE);
    }

    public static String getImplementationVersion(Manifest manifest) {
        if (manifest == null) {
            manifest = getManifest();
        }
        if (manifest == null) {
            return null;
        }
        Attributes attributes = manifest.getMainAttributes();
        return attributes.getValue(Attributes.Name.IMPLEMENTATION_VERSION);
    }

    public static String getImplementationVendor(Manifest manifest) {
        if (manifest == null) {
            manifest = getManifest();
        }
        if (manifest == null) {
            return null;
        }
        Attributes attributes = manifest.getMainAttributes();
        return attributes.getValue(Attributes.Name.IMPLEMENTATION_VENDOR);
    }
//
//  public static String getImplementationVendorId(Manifest manifest) {
//      if (manifest == null) {
//          manifest = getManifest();
//      }
//      if (manifest == null) {
//          return null;
//      }
//      Attributes attributes = manifest.getMainAttributes();
//      return attributes.getValue(Attributes.Name.IMPLEMENTATION_VENDOR_ID);
//  }
//
//  public static String getImplementationUrl(Manifest manifest) {
//      if (manifest == null) {
//          manifest = getManifest();
//      }
//      if (manifest == null) {
//          return null;
//      }
//      Attributes attributes = manifest.getMainAttributes();
//      return attributes.getValue(Attributes.Name.IMPLEMENTATION_URL);
//  }

    public static String getImplementationDate(Manifest manifest) {
        if (manifest == null) {
            manifest = getManifest();
        }
        if (manifest == null) {
            return null;
        }
        Attributes attributes = manifest.getMainAttributes();
        return attributes.getValue("Implementation-Date");
    }

    public static String getImplementationTimestamp(Manifest manifest) {
        if (manifest == null) {
            manifest = getManifest();
        }
        if (manifest == null) {
            return null;
        }
        Attributes attributes = manifest.getMainAttributes();
        return attributes.getValue("Implementation-Timestamp");
    }

    public static String getSpecificationTitle(Manifest manifest) {
        if (manifest == null) {
            manifest = getManifest();
        }
        if (manifest == null) {
            return null;
        }
        Attributes attributes = manifest.getMainAttributes();
        return attributes.getValue(Attributes.Name.SPECIFICATION_TITLE);
    }

    public static String getSpecificationVersion(Manifest manifest) {
        if (manifest == null) {
            manifest = getManifest();
        }
        if (manifest == null) {
            return null;
        }
        Attributes attributes = manifest.getMainAttributes();
        return attributes.getValue(Attributes.Name.SPECIFICATION_VERSION);
    }

    public static String getSpecificationVendor(Manifest manifest) {
        if (manifest == null) {
            manifest = getManifest();
        }
        if (manifest == null) {
            return null;
        }
        Attributes attributes = manifest.getMainAttributes();
        return attributes.getValue(Attributes.Name.SPECIFICATION_VENDOR);
    }

}
