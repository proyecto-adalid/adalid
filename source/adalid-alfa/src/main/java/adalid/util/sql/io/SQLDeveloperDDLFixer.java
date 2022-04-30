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
package adalid.util.sql.io;

import adalid.commons.properties.*;
import adalid.commons.util.*;
import adalid.util.*;
import adalid.util.io.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.JOptionPane;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class SQLDeveloperDDLFixer extends Utility {

    // <editor-fold defaultstate="collapsed" desc="static fields">
    private static final Logger logger = Logger.getLogger(SQLDeveloperDDLFixer.class);

    private static final String B = "^";

    private static final String E = "$";

    private static final String X = ".*";
//
//  private static final String S = RegexPathFilter.SEPARATOR;

    private static final String D = "\\.";

    private static final String COMMENT_LINE_REGEX = "^--.*$";

    private static final String ALTER_TABLE_REGEX = "^.*\\bALTER TABLE\\b";

    private static final String TABLE_NAME_REGEX = "\\s*\\\"\\w+\\\"\\.\\\"\\w+\\\"\\s*";

    private static final String COLUMN_NAME_REGEX = "\\s*\\\"\\w+\\\"\\s*";

    private static final String MODIFY_REGEX = "\\bMODIFY\\b";

    private static final String COMMENT_COMMAND_REGEX = "^.*\\bCOMMENT ON\\b.*\\bIS\\b.*$";

    private static final String MODIFY_COMMAND_REGEX = ALTER_TABLE_REGEX + TABLE_NAME_REGEX + MODIFY_REGEX + ".*$";

    private static final String NOT_NULL_COMMAND_REGEX = ALTER_TABLE_REGEX + TABLE_NAME_REGEX + MODIFY_REGEX + "\\s*\\("
        + COLUMN_NAME_REGEX + "\\s*NOT NULL ENABLE\\);$";

    private static final String GRANT_COMMAND_REGEX = "^.*\\bGRANT\\b.*\\bON\\b.*\\bTO\\b.*$";

    private static final String CREATE_UNIQUE_INDEX_REGEX = "^.*\\bCREATE UNIQUE INDEX\\b";

    private static final String CREATE_PK_INDEX_COMMAND_REGEX_1 = CREATE_UNIQUE_INDEX_REGEX + ".*\\b\\w+PK___\\b.*\\bON\\b.*$";

    private static final String CREATE_PK_INDEX_COMMAND_REGEX_2 = CREATE_UNIQUE_INDEX_REGEX + ".*\\bPK_\\w+\\b.*\\bON\\b.*$";

    private static final String CREATE_UK_INDEX_COMMAND_REGEX_1 = CREATE_UNIQUE_INDEX_REGEX + ".*\\b\\w+UK_\\d{3}___\\b.*\\bON\\b.*$";

    private static final String CREATE_UK_INDEX_COMMAND_REGEX_2 = CREATE_UNIQUE_INDEX_REGEX + ".*\\bUK_\\w+\\b.*\\bON\\b.*$";

    private static final String LAST_COMMAND_LINE_REGEX = "^.*;$";

    /*
    private static final String[] CODERS = {
        "FUNCTIONS", "PACKAGES", "PACKAGE_BODIES", "PROCEDURES", "TRIGGERS"
    };

    /**/
    private static final String[] SECURE = {
        "FUNCTIONS", "PACKAGES", "PACKAGE_BODIES", "PROCEDURES", "TRIGGERS", "MATERIALIZED_VIEWS", "SEQUENCES", "TABLES", "VIEWS"
    };

    private static final String[] OTHERS = {
        "CONSTRAINTS", "DROPS", "INDEXES", "MATERIALIZED_VIEWS", "REF_CONSTRAINTS", "SEQUENCES", "TABLES", "VIEWS"
    };
    // </editor-fold>

    public static void main(String[] args) {
        replace(USER_DIR);
    }

    public static boolean replace(String path) {
        return SQLDeveloperDDLFixer.replace(path, false);
    }

    public static boolean replace(String path, boolean detail) {
        logger.info("replace" + StrUtils.enclose(path));
        SQLDeveloperDDLFixer fixer = new SQLDeveloperDDLFixer(path, detail);
        return fixer.replace();
    }

    public static boolean replace(String path, List<String> details) {
        logger.info("replace" + StrUtils.enclose(path));
        SQLDeveloperDDLFixer fixer = new SQLDeveloperDDLFixer(path, details);
        return fixer.replace();
    }

    // <editor-fold defaultstate="collapsed" desc="instance fields">
    private final File rootFolder;

    private final Path rootFolderPath;

    private final File resourcesFolder;

    private final Path resourcesFolderPath;

    private final File baseFolder;

    private final Path baseFolderPath;

    private final Map<Path, SmallFile> files;

    private final Map<String, Integer> fileTypes;

    private boolean detailAll;

    private List<String> detailPatterns;

    private int filteredCommands;

    private int readingWarnings, readingErrors;

    private int filesCopied, filesSkipped, writingErrors;

    private enum Sorting {

        NONE, ALPHABETICALLY, MODIFY_COMMANDS_FIRST

    }

    private Sorting sorting;

    private enum Filtering {

        NONE, COMMENT, GRANT, NOT_NULL_ENABLE, SEGMENT_CREATION_DEFERRED, PK_INDEX, UK_INDEX, ALL_OF_THE_ABOVE, ALL_BUT_NOT_NULL, ALL_BUT_PK_UK_IX

    }

    private Filtering filtering;
    // </editor-fold>

    private SQLDeveloperDDLFixer(String path) {
        rootFolder = PropertiesHandler.getRootFolder();
        if (rootFolder == null) {
            throw new RuntimeException("root folder is missing or invalid");
        }
        rootFolderPath = Paths.get(rootFolder.getPath());
        logger.info("root-folder=" + rootFolderPath);
        path = chooseDirectory(path);
        if (path == null) {
            throw new IllegalArgumentException("null folder path");
        }
        File file = new File(path);
        if (file.isAbsolute()) {
            resourcesFolder = file;
        } else {
            resourcesFolder = new File(rootFolder.getAbsolutePath(), path);
        }
        resourcesFolderPath = Paths.get(resourcesFolder.getPath());
        logger.info("resources-folder=" + resourcesFolderPath);
        if (resourcesFolder.isDirectory()) {
            if (resourcesFolder.isHidden()) {
                throw new IllegalArgumentException(resourcesFolderPath + " is a hidden directory");
            }
        } else {
            throw new IllegalArgumentException(resourcesFolderPath + " is not a directory");
        }
        baseFolder = resourcesFolder.getParentFile();
        baseFolderPath = Paths.get(baseFolder.getPath());
        logger.info("base-folder=" + baseFolderPath);
        files = new TreeMap<>();
        fileTypes = new TreeMap<>();
    }

    private SQLDeveloperDDLFixer(String path, boolean detail) {
        this(path);
        this.detailAll = detail;
    }

    private SQLDeveloperDDLFixer(String path, List<String> details) {
        this(path);
        this.detailAll = false;
        this.detailPatterns = details;
    }

    private boolean replace() {
//      logger.info("replace" + StrUtils.enclose(resourcesFolderPath.toString()));
        logger.info("replace" + StrUtils.enclose(resourcesFolderPath.getFileName().toString()));
        init();
        readFiles();
        printSummary();
        updateProjectBuilderDictionary(SQLDeveloperDDLFixer.class);
        return readingErrors == 0;
    }

    private void init() {
        filteredCommands = 0;
        readingWarnings = 0;
        readingErrors = 0;
        filesCopied = 0;
        filesSkipped = 0;
        writingErrors = 0;
        sorting();
        filtering();
    }

    private void sorting() {
//      final Component parent = null;
        final Object message = "Sort commands";
        final String title = "Choose";
        final int type = JOptionPane.QUESTION_MESSAGE;
//      final Icon icon = null;
        final Object[] values = Sorting.values();
        final Object initialValue = values[0];
        logger.info(title + " " + message + " " + Arrays.toString(values));
        Object value = JOptionPane.showInputDialog(null, message, title, type, null, values, initialValue);
        sorting = value == null ? Sorting.NONE : (Sorting) value;
        logger.info(sorting);
    }

    private void filtering() {
//      final Component parent = null;
        final Object message = "Filter commands";
        final String title = "Choose";
        final int type = JOptionPane.QUESTION_MESSAGE;
//      final Icon icon = null;
        final Object[] values = Filtering.values();
        final Object initialValue = values[0];
        logger.info(title + " " + message + " " + Arrays.toString(values));
        Object value = JOptionPane.showInputDialog(null, message, title, type, null, values, initialValue);
        filtering = value == null ? Filtering.NONE : (Filtering) value;
        logger.info(filtering);
    }

    private boolean readFiles() {
        Collection<File> list = FileUtils.listFiles(resourcesFolder, fileFilter(), dirFilter());
        SmallFile sf;
        Charset charset;
        String name;
        String extension;
        for (File file : list) {
            sf = new SmallFile(file.getPath());
            sf.read();
            charset = sf.getCharset();
            name = sf.getName();
            extension = StringUtils.defaultIfBlank(sf.getExtension().toLowerCase(), "?");
            if (charset == null) {
                readingErrors++;
                logger.error(name + " could not be read using any of the specified character sets ");
            } else if (sf.isEmpty()) {
                readingWarnings++;
                logger.warn(name + " is empty ");
            } else {
                files.put(sf.getPath(), sf);
                updateFileTypes(charset + "");
                updateFileTypes(charset + " / " + extension);
                rewrite(sf);
            }
        }
        return true;
    }

    private boolean rewrite(SmallFile smallSource) {
        List<String> sourceLines = smallSource.read();
        List<String> targetLines = new ArrayList<>();
        List<String> sortedLines = new ArrayList<>();
        List<String> modifyLines = new ArrayList<>();
        List<String> listOfLines = sortedLines;
        /**/
        String folder = smallSource.getPath().getParent().getFileName().toString();
        /**/
//      boolean coders = ArrayUtils.contains(CODERS, folder);
        boolean secure = ArrayUtils.contains(SECURE, folder);
        boolean others = ArrayUtils.contains(OTHERS, folder);
        boolean tables = folder.equals("TABLES");
        boolean constraints = folder.equals("CONSTRAINTS");
        boolean indexes = folder.equals("INDEXES");
        boolean triggers = folder.equals("TRIGGERS");
        boolean materialized_views = folder.equals("MATERIALIZED_VIEWS");
        /**/
        boolean header = true;
        boolean sorted = Sorting.ALPHABETICALLY.equals(sorting);
        boolean modify = Sorting.MODIFY_COMMANDS_FIRST.equals(sorting);
        /**/
        boolean filterAllAbove = Filtering.ALL_OF_THE_ABOVE.equals(filtering);
        boolean filterAllButNN = Filtering.ALL_BUT_NOT_NULL.equals(filtering);
        boolean filterAllButIX = Filtering.ALL_BUT_PK_UK_IX.equals(filtering);
        boolean filterComments = filterAllAbove || filterAllButNN || filterAllButIX || Filtering.COMMENT.equals(filtering);
        boolean filterGranting = filterAllAbove || filterAllButNN || filterAllButIX || Filtering.GRANT.equals(filtering);
        boolean filterNotNulls = filterAllAbove || filterAllAbove || filterAllButIX || Filtering.NOT_NULL_ENABLE.equals(filtering);
        boolean filterDeferred = filterAllAbove || filterAllButNN || filterAllButIX || Filtering.SEGMENT_CREATION_DEFERRED.equals(filtering);
        boolean filterPKyIndex = filterAllAbove || filterAllButNN || filterAllAbove || Filtering.PK_INDEX.equals(filtering);
        boolean filterUKyIndex = filterAllAbove || filterAllButNN || filterAllAbove || Filtering.UK_INDEX.equals(filtering);
        /**/
        sorted &= others & !materialized_views;
        modify &= constraints;
        /**/
        filterComments &= others;
        filterGranting &= secure;
        filterNotNulls &= constraints;
        filterDeferred &= tables;
        filterPKyIndex &= indexes;
        filterUKyIndex &= indexes;
        /**/
        String command = "";
        String newline;
        /**/
        boolean skipline, headline;
        boolean complete = true;
        if (smallSource.isNotEmpty()) {
            for (String line : sourceLines) {
                skipline = StringUtils.isBlank(line);
                headline = skipline || line.matches(COMMENT_LINE_REGEX);
                if (header && headline) {
                    targetLines.add(line);
//              } else if (coders && skipline) {
                } else if (others && headline) {
                } else {
                    header = false;
                    if (modify && command.isEmpty() && line.matches(MODIFY_COMMAND_REGEX)) {
                        listOfLines = modifyLines;
                    }
                    newline = complete ? "" : "\n";
                    if (line.equals("/")) {
                        if (triggers && command.trim().isEmpty()) {
                        } else if (command.isEmpty()) {
                            if (!complete) {
                                listOfLines.add(command);
                            }
                        } else {
                            add(command, listOfLines, filterComments, filterGranting, filterNotNulls, filterDeferred, filterPKyIndex, filterUKyIndex);
                        }
                        listOfLines.add(line);
                        complete = true;
                    } else if (line.matches(LAST_COMMAND_LINE_REGEX)) {
                        command += newline + split(line);
                        add(command, listOfLines, filterComments, filterGranting, filterNotNulls, filterDeferred, filterPKyIndex, filterUKyIndex);
                        complete = true;
                    } else {
                        command += newline + split(line);
                        complete = false;
                    }
                    if (complete) {
                        listOfLines = sortedLines;
                        command = "";
                    }
                }
            }
        }
        if (modify) {
            modifyLines.sort(null);
            targetLines.addAll(modifyLines);
        }
        if (modify || sorted) {
            sortedLines.sort(null);
        }
        targetLines.addAll(sortedLines);
        if (rewrite(smallSource, targetLines)) {
            filesCopied++;
        }
        return true;
    }

    private boolean rewrite(SmallFile smallSource, List<String> lines) {
        String target = smallSource.getName();
        String shorty = StringUtils.removeStartIgnoreCase(target, resourcesFolderPath.toString());
        logger.trace("rewrite " + shorty);
        try {
            Files.write(smallSource.getPath(), lines, smallSource.getCharset());
            return true;
        } catch (IOException ex) {
            writingErrors++;
            logger.fatal(ThrowableUtils.getString(ex), ex);
            logger.fatal("\t" + shorty + " could not be rewritten ");
        }
        return false;
    }

    private boolean add(String command, List<String> listOfLines,
        boolean filterComments,
        boolean filterGranting,
        boolean filterNotNulls,
        boolean filterDeferred,
        boolean filterPKyIndex,
        boolean filterUKyIndex) {
        if (command.isEmpty()) {
            return false;
        } else if (filterComments && matches(command, COMMENT_COMMAND_REGEX)) {
            filteredCommands++;
        } else if (filterGranting && matches(command, GRANT_COMMAND_REGEX)) {
            filteredCommands++;
        } else if (filterNotNulls && matches(command, NOT_NULL_COMMAND_REGEX)) {
            filteredCommands++;
        } else if (filterPKyIndex && matches(command, CREATE_PK_INDEX_COMMAND_REGEX_1)) {
            filteredCommands++;
        } else if (filterPKyIndex && matches(command, CREATE_PK_INDEX_COMMAND_REGEX_2)) {
            filteredCommands++;
        } else if (filterUKyIndex && matches(command, CREATE_UK_INDEX_COMMAND_REGEX_1)) {
            filteredCommands++;
        } else if (filterUKyIndex && matches(command, CREATE_UK_INDEX_COMMAND_REGEX_2)) {
            filteredCommands++;
        } else {
            if (filterDeferred) {
                command = command.replace("SEGMENT CREATION DEFERRED", "");
            }
            return listOfLines.add(command);
        }
        return false;
    }

    private boolean matches(String command, String regex) {
        // String.matches misbehaves if string contains new lines
        return command.replace('\n', ' ').trim().matches(regex);
    }

    private String split(String line) {
        // split the line to avoid error SP2-0027: Input is too long (> 2499 characters) - line ignored
        return line.length() < 2499 ? line : line.replaceAll(",\\s+", ",\n");
    }

    private void updateFileTypes(String type) {
        if (fileTypes.containsKey(type)) {
            int count = fileTypes.get(type);
            fileTypes.put(type, ++count);
        } else {
            fileTypes.put(type, 1);
        }
    }

    private static final boolean printDetail = false;

    private void printSummary() {
        if (printDetail) {
            String tab;
            logger.info(files.size() + " files ");
            logger.info(fileTypes.size() + " file types ");
            for (String type : fileTypes.keySet()) {
                tab = type.contains("/") ? "\t" : "";
                logger.info(tab + type + " = " + fileTypes.get(type));
                printDetail(type);
            }
        }
        logger.info(filteredCommands + " filtered commands ");
        logger.info(readingWarnings + " reading warnings ");
        logger.info(readingErrors + " reading errors ");
        logger.info(filesCopied + " files rewritten ");
        logger.info(filesSkipped + " files skipped ");
        logger.info(writingErrors + " writing errors ");
    }

    private void printDetail(String type) {
        if (detailAll || matches(type)) {
            Charset charset;
            String extension;
            String baseFolderPathString = baseFolderPath.toString();
            String tab = type.contains("/") ? "\t" : "";
            for (SmallFile sf : files.values()) {
                charset = sf.getCharset();
                extension = StringUtils.defaultIfBlank(sf.getExtension(), "?");
                if (charset != null && type.equals(charset + " / " + extension)) {
                    logger.info(tab + "\t" + StringUtils.removeStartIgnoreCase(sf.getName(), baseFolderPathString));
                }
            }
        }
    }

    private boolean matches(String type) {
        if (detailPatterns == null || detailPatterns.isEmpty()) {
            return false;
        }
        for (String regex : detailPatterns) {
            if (type.matches(regex)) {
                return true;
            }
        }
        return false;
    }

    private IOFileFilter fileFilter() {
        IOFileFilter fileFileFilter = FileFilterUtils.fileFileFilter();
        IOFileFilter[] ayes = new IOFileFilter[]{
            new RegexFileFilter(B + X + D + "sql" + E)
        };
        IOFileFilter ayesFileFilter = FileFilterUtils.or(ayes);
        IOFileFilter filter = FileFilterUtils.and(fileFileFilter, ayesFileFilter);
        return filter;
    }

    private IOFileFilter dirFilter() {
        IOFileFilter makeCVSSVNAware = FileFilterUtils.makeCVSAware(FileFilterUtils.makeSVNAware(null));
        IOFileFilter[] noes = new IOFileFilter[]{
            new RegexFileFilter(B + X + D + "git" + E),
            new RegexFileFilter(B + "build" + E),
            new RegexFileFilter(B + "dist" + E),
            new RegexFileFilter(B + "target" + E)
        };
        IOFileFilter noesFileFilter = FileFilterUtils.notFileFilter(FileFilterUtils.or(noes));
        IOFileFilter filter = FileFilterUtils.and(makeCVSSVNAware, noesFileFilter);
        return filter;
    }

    /**
     * @return the root folder
     */
    public File getRootFolder() {
        return rootFolder;
    }

    /**
     * @return the root folder path
     */
    public Path getRootFolderPath() {
        return rootFolderPath;
    }

    /**
     * @return the resources folder
     */
    public File getResourcesFolder() {
        return resourcesFolder;
    }

    /**
     * @return the resources folder path
     */
    public Path getResourcesFolderPath() {
        return resourcesFolderPath;
    }

    /**
     * @return the base folder
     */
    public File getBaseFolder() {
        return baseFolder;
    }

    /**
     * @return the base folder path
     */
    public Path getBaseFolderPath() {
        return baseFolderPath;
    }

    /**
     * @return the reading warnings count
     */
    public int getReadingWarnings() {
        return readingWarnings;
    }

    /**
     * @return the reading errors count
     */
    public int getReadingErrors() {
        return readingErrors;
    }

    /**
     * @return the files map
     */
    public Map<Path, SmallFile> getFiles() {
        return files;
    }

    /**
     * @return the file types map
     */
    public Map<String, Integer> getFileTypes() {
        return fileTypes;
    }

}
