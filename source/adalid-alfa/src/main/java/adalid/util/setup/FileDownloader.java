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
package adalid.util.setup;

import adalid.commons.util.*;
import adalid.util.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.MessageFormat;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class FileDownloader extends Utility {

    private static final Logger logger = Logger.getLogger(FileDownloader.class);

    /*
    private static final File UD, PD, WS;

    private static final String VN;
    /**/
    private static final File UH, DD, WS;

    private static final String VN, ZN;

    static {
        /*
        UD = new File(System.getProperty("user.dir"));
        PD = UD.getParentFile();
        WS = PD == null ? UD : PD;
        /**/
        UH = new File(System.getProperty("user.home"));
        DD = new File(UH, "Downloads");
//      WS = new File(DD, "adalid");
        WS = DD;
        VN = getAdalidProjectVersion();
        ZN = "adalid-" + VN + ".zip";
        logAdalidProjectVersion();
    }

    // <editor-fold defaultstate="collapsed">
    /*
    https://www.dropbox.com/s/a2bdi7uxvm4rpmn/adalid-2.0.zip?dl=0 // change dl=0 to dl=1 to be able to download it
    https://www.dropbox.com/s/ghcx2rmusurndwi/third-party-2.0.zip?dl=0 // change dl=0 to dl=1 to be able to download it
    https://www.dropbox.com/s/swjoz4rcjsn03la/workspace?dl=0 // change dl=0 to dl=1 to be able to download it
    private static final String[] DROPBOX_LINKS = {
        "https://www.dropbox.com/s/a2bdi7uxvm4rpmn/adalid-" + VN + ".zip?dl=1",
        "https://www.dropbox.com/s/ghcx2rmusurndwi/third-party-" + VN + ".zip?dl=1",
        "https://www.dropbox.com/s/swjoz4rcjsn03la/workspace?dl=1"
    };

    https://www.dropbox.com/sh/fwr81f73wjy1ybi/AAChePmVCL-vDFe2heL8iJPJa?dl=0 // change dl=0 to dl=1 to be able to download it
    private static final String[] DROPBOX_LINKS = {
        "https://www.dropbox.com/sh/fwr81f73wjy1ybi/AAChePmVCL-vDFe2heL8iJPJa?dl=1"
    };
    /**/
    // </editor-fold>
/**/
    private static final String DROPBOX_LINK = "https://www.dropbox.com/sh/fwr81f73wjy1ybi/AAChePmVCL-vDFe2heL8iJPJa?dl=1";

    public static void main(String[] args) {
        download();
    }

    public static void download() {
        /*
        int downloaded = 0;
        for (String spec : DROPBOX_LINKS) {
            if (downloadDropboxFile(spec)) {
                downloaded++;
            }
        }
        info("{0} downloaded files", downloaded);
        info("{0} errors", DROPBOX_LINKS.length - downloaded);
        /**/
        boolean downloaded = downloadDropboxFile(DROPBOX_LINK);
        if (downloaded) {
            info("{0} downloaded", ZN);
            /*
            boolean unzipped = unzip();
            if (unzipped) {
                info("{0} unzipped", ZN);
            } else {
                info("{0} unzip failed", ZN);
            }
            /**/
        } else {
            info("{0} download failed", ZN);
        }
        updateProjectBuilderDictionary(FileDownloader.class);
    }

    /*
    private static final String FS = System.getProperty("file.separator");

    private static boolean unzip() {
        try {
            return ZipUtils.unzip(WS.getCanonicalPath() + FS + ZN);
        } catch (IOException ex) {
            logger.fatal(ThrowableUtils.getString(ex), ex);
        }
        return false;
    }

    /**/
    private static boolean downloadDropboxFile(String spec) {
//      info("downloading {0}", spec);
        /*
        String file = StringUtils.substringBefore(StringUtils.substringAfterLast(spec, "/"), "?dl=1");
        downloadDropboxFile(spec, file);
        /**/
        return downloadDropboxFile(spec, ZN);
    }

    private static boolean downloadDropboxFile(String spec, String file) {
//      info("downloading {0} to {1}", spec, file);
        try {
            URL source = new URI(spec).toURL(); // new URL(spec); is deprecated since JDK 20
            File target = new File(WS, file);
            return downloadDropboxFile(source, target);
        } catch (URISyntaxException | MalformedURLException ex) {
            logger.fatal(ThrowableUtils.getString(ex), ex);
        }
        return false;
    }

    private static boolean downloadDropboxFile(URL source, File target) {
//      info("downloading {0} to {1}", source, target);
        info("downloading {0} to {1}", target.getName(), target.getParent());
        try {
            FileUtils.copyURLToFile(source, target);
            return true;
        } catch (IOException ex) {
            logger.fatal(ThrowableUtils.getString(ex), ex);
        }
        return false;
    }

    private static void info(String pattern, Object... arguments) {
        logger.info(MessageFormat.format(pattern, arguments));
    }

}
