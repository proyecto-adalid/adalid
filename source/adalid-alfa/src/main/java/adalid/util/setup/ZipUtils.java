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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Zip and unzip files using Java core libraries.
 *
 * Based on <i>Zipping and Unzipping in Java</i> (https://www.baeldung.com/java-compress-and-uncompress) by baeldung
 *
 * @author Jorge Campins
 */
public class ZipUtils {

    public static boolean zipFile(String pathName) throws IOException {
        if (StringUtils.isNotBlank(pathName)) {
            File f = new File(pathName);
            if (f.isFile() && f.canRead() && !f.isHidden()) {
                return zip(f);
            }
        }
        return false;
    }

    public static boolean zipFolder(String pathName) throws IOException {
        if (StringUtils.isNotBlank(pathName)) {
            File f = new File(pathName);
            if (f.isDirectory() && f.canRead() && !f.isHidden()) {
                return zip(f);
            }
        }
        return false;
    }

    private static boolean zip(File f) throws IOException {
        String zipPathName = f.getCanonicalPath() + ".zip";
        try ( FileOutputStream fos = new FileOutputStream(zipPathName);  ZipOutputStream zipOut = new ZipOutputStream(fos)) {
            zip(f, f.getName(), zipOut);
        }
        return true;
    }

    public static boolean zipFileAndFolder(String filePathName) throws IOException {
        return zipFileAndFolder(filePathName, null);
    }

    public static boolean zipFileAndFolder(String filePathName, String folderPathName) throws IOException {
        return zipFileAndFolder(filePathName, folderPathName, false);
    }

    public static boolean zipFileAndFolder(String filePathName, String folderPathName, boolean siblings) throws IOException {
        if (StringUtils.isNotBlank(filePathName)) {
            File f = new File(filePathName);
            if (f.isFile() && f.canRead() && !f.isHidden()) {
                File d = oldFile(folderPathName, filePathName, "_files");
                if (d.isDirectory() && d.canRead() && !d.isHidden()) {
                    if (!siblings || (ObjectUtils.equals(f.getParent(), d.getParent()))) {
                        String zipPathName = f.getCanonicalPath() + ".zip";
                        return zip(zipPathName, f.getCanonicalPath(), d.getCanonicalPath());
                    }
                }
            }
        }
        return false;
    }

    public static boolean zip(String zipPathName, String... pathNames) throws IOException {
        if (StringUtils.isNotBlank(zipPathName)) {
            if (pathNames != null && pathNames.length > 0) {
                boolean zip = false;
                try ( FileOutputStream fos = new FileOutputStream(zipPathName);  ZipOutputStream zipOut = new ZipOutputStream(fos)) {
                    for (String pathName : pathNames) {
                        if (StringUtils.isNotBlank(pathName)) {
                            File f = new File(pathName);
                            zip |= zip(f, f.getName(), zipOut);
                        }
                    }
                }
                return zip;
            }
        }
        return false;
    }

    private static boolean zip(File file, String filePath, ZipOutputStream zipOut) throws IOException {
        if (file.exists() && file.canRead() && !file.isHidden()) {
            if (file.isDirectory()) {
                String dirName = filePath.endsWith("/") ? filePath : filePath + "/";
                zipOut.putNextEntry(new ZipEntry(dirName));
                zipOut.closeEntry();
                File[] children = file.listFiles();
                for (File child : children) {
                    zip(child, dirName + child.getName(), zipOut);
                }
            } else {
                try ( FileInputStream fis = new FileInputStream(file)) {
                    zipOut.putNextEntry(new ZipEntry(filePath));
                    byte[] bytes = new byte[1024];
                    int length;
                    while ((length = fis.read(bytes)) >= 0) {
                        zipOut.write(bytes, 0, length);
                    }
                }
            }
            return true;
        }
        return false;
    }

    public static boolean unzip(String zipPathName) throws IOException {
        return unzip(zipPathName, null);
    }

    public static boolean unzip(String zipPathName, String dirPathName) throws IOException {
        // ZipInputStream falla si hay zips dentro del zip -> java.util.zip.ZipException: only DEFLATED entries can have EXT descriptor
        if (StringUtils.isNotBlank(zipPathName)) {
            File zip = new File(zipPathName);
            if (zip.isFile() && zip.canRead() && !zip.isHidden()) {
                byte[] buffer = new byte[1024];
                File dir = newFile(dirPathName, zipPathName, ".dir");
                try ( ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipPathName))) {
                    ZipEntry zipEntry = zipIn.getNextEntry();
                    while (zipEntry != null) {
                        File file = newFile(dir, zipEntry);
                        if (zipEntry.isDirectory()) {
                            verifyDir(file);
                        } else {
                            // The following verification is necessary for archives created on Windows,
                            // where the root directories don't have a corresponding entry in the zip file.
                            verifyDir(file.getParentFile());
                            // write file content
                            try (
                                 FileOutputStream fos = new FileOutputStream(file)) {
                                int len;
                                while ((len = zipIn.read(buffer)) > 0) {
                                    fos.write(buffer, 0, len);
                                }
                            }
                        }
                        zipEntry = zipIn.getNextEntry();
                    }
                    zipIn.closeEntry();
                }
                return true;
            }
            throw new IOException("Failed to access file " + zip);
        }
        return false;
    }

    /**
     * External resource files are stored to a folder having the same name as the HTML file without extension plus the "_files" suffix. Alternatively,
     * the folder has the same name as the HTML file plus the "_files" suffix.
     *
     * @param pathName
     * @param rootPathName
     * @param suffix
     * @return file
     */
    private static File oldFile(String pathName, String rootPathName, String suffix) {
        // assert rootPathName != null && suffix != null;
        if (StringUtils.isBlank(pathName) || pathName.equals(rootPathName)) {
            String x1 = rootPathName + suffix; // append the suffix (JasperReports exporter default)
            String x2 = StringUtils.substringBeforeLast(rootPathName, ".") + suffix; // remove extension and append the suffix (browsers default)
            File file = new File(x1);
            return file.exists() || x1.equals(x2) ? file : new File(x2);
        }
        return new File(pathName);
    }

    private static File newFile(String pathName, String rootPathName, String suffix) {
        String pdq = StringUtils.isBlank(pathName) || pathName.equals(rootPathName) ? rootPathName + suffix : pathName;
        return new File(pdq);
    }

    /**
     * This method guards against writing files to the file system outside of the target folder. This vulnerability is called Zip Slip
     *
     * @param dir
     * @param zipEntry
     * @return file
     * @throws IOException
     */
    private static File newFile(File dir, ZipEntry zipEntry) throws IOException {
        File file = new File(dir, zipEntry.getName());
        String dirPath = dir.getCanonicalPath();
        String filePath = file.getCanonicalPath();
        if (filePath.startsWith(dirPath + File.separator)) {
            return file;
        }
        throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
    }

    private static boolean verifyDir(File file) throws IOException {
        if (file == null) {
            // throw new IOException?
        } else if (file.isDirectory()) {
            if (!file.canWrite() || file.isHidden()) {
                throw new IOException("Failed to access directory " + file);
            }
        } else if (!file.mkdirs()) {
            throw new IOException("Failed to create directory " + file);
        }
        return true;
    }

}
