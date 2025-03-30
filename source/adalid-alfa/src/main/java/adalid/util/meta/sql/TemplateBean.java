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
package adalid.util.meta.sql;

import java.io.File;

/**
 * @author Jorge Campins
 */
public class TemplateBean {

    // <editor-fold defaultstate="collapsed" desc="instance fields">
    private final PlatformBean _platform;

    private final File _propertiesFile;

    private String _path;

    private String _type;

    private String _encoding;

    private String _forEach;

    private String _targetPath;

    private String _targetPackage;

    private String _targetFile;

    private String _targetFileEncoding;

    private String _executeCommand;

    private String _executeDirectory;

    private boolean _disabled;

    private boolean _disabledWhenMissing;

    private boolean _preserveExistingFile;

    private int _bytes;

    private int _lines;
    // </editor-fold>

    public TemplateBean(PlatformBean platform, File propertiesFile) {
        _platform = platform;
        _propertiesFile = propertiesFile;
        init();
    }

    private void init() {
        _platform.add(this);
    }

    // <editor-fold defaultstate="collapsed" desc="instance fields' public getters and setters">
    /**
     * @return the platform
     */
    public PlatformBean getPlatform() {
        return _platform;
    }

    /**
     * @return the properties file
     */
    public File getPropertiesFile() {
        return _propertiesFile;
    }

    /**
     * @return the path
     */
    public String getPath() {
        return _path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        _path = path;
    }

    /**
     * @return the type
     */
    public String getType() {
        return _type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        _type = type;
    }

    /**
     * @return the encoding
     */
    public String getEncoding() {
        return _encoding;
    }

    /**
     * @param encoding the encoding to set
     */
    public void setEncoding(String encoding) {
        _encoding = encoding;
    }

    /**
     * @return the forEach
     */
    public String getForEach() {
        return _forEach;
    }

    /**
     * @param forEach the forEach to set
     */
    public void setForEach(String forEach) {
        _forEach = forEach;
    }

    /**
     * @return the targetPath
     */
    public String getTargetPath() {
        return _targetPath;
    }

    /**
     * @param targetPath the targetPath to set
     */
    public void setTargetPath(String targetPath) {
        _targetPath = targetPath;
    }

    /**
     * @return the targetPackage
     */
    public String getTargetPackage() {
        return _targetPackage;
    }

    /**
     * @param targetPackage the targetPackage to set
     */
    public void setTargetPackage(String targetPackage) {
        this._targetPackage = targetPackage;
    }

    /**
     * @return the targetFile
     */
    public String getTargetFile() {
        return _targetFile;
    }

    /**
     * @param targetFile the targetFile to set
     */
    public void setTargetFile(String targetFile) {
        this._targetFile = targetFile;
    }

    /**
     * @return the targetFileEncoding
     */
    public String getTargetFileEncoding() {
        return _targetFileEncoding;
    }

    /**
     * @param targetFileEncoding the targetFileEncoding to set
     */
    public void setTargetFileEncoding(String targetFileEncoding) {
        this._targetFileEncoding = targetFileEncoding;
    }

    /**
     * @return the executeCommand
     */
    public String getExecuteCommand() {
        return _executeCommand;
    }

    /**
     * @param executeCommand the executeCommand to set
     */
    public void setExecuteCommand(String executeCommand) {
        this._executeCommand = executeCommand;
    }

    /**
     * @return the executeDirectory
     */
    public String getExecuteDirectory() {
        return _executeDirectory;
    }

    /**
     * @param executeDirectory the executeDirectory to set
     */
    public void setExecuteDirectory(String executeDirectory) {
        this._executeDirectory = executeDirectory;
    }

    /**
     * @return the disabled
     */
    public boolean isDisabled() {
        return _disabled;
    }

    /**
     * @param disabled the disabled to set
     */
    public void setDisabled(boolean disabled) {
        this._disabled = disabled;
    }

    /**
     * @return the disabledWhenMissing
     */
    public boolean isDisabledWhenMissing() {
        return _disabledWhenMissing;
    }

    /**
     * @param disabledWhenMissing the disabledWhenMissing to set
     */
    public void setDisabledWhenMissing(boolean disabledWhenMissing) {
        this._disabledWhenMissing = disabledWhenMissing;
    }

    /**
     * @return the preserveExistingFile
     */
    public boolean isPreserveExistingFile() {
        return _preserveExistingFile;
    }

    /**
     * @param preserveExistingFile the preserveExistingFile to set
     */
    public void setPreserveExistingFile(boolean preserveExistingFile) {
        this._preserveExistingFile = preserveExistingFile;
    }

    /**
     * @return the bytes
     */
    public int getBytes() {
        return _bytes;
    }

    /**
     * @param bytes the bytes to set
     */
    public void setBytes(int bytes) {
        _bytes = bytes;
    }

    /**
     * @return the lines
     */
    public int getLines() {
        return _lines;
    }

    /**
     * @param lines the lines to set
     */
    public void setLines(int lines) {
        _lines = lines;
    }
    // </editor-fold>

}
