package adalid.util.meta;

/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */


import adalid.commons.util.ThrowableUtils;
import com.sun.source.tree.AnnotationTree;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.ImportTree;
import com.sun.source.tree.Tree;
import com.sun.source.util.JavacTask;
import com.sun.source.util.TreePath;
import com.sun.source.util.Trees;
import com.sun.tools.javac.code.Symbol.*;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import javax.lang.model.element.Element;
import javax.tools.DiagnosticListener;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class MetaJavaCompiler {

    private static final Logger logger = Logger.getLogger(MetaJavaCompiler.class);

    private static final String AT = "@";

    private static final String EQ = "=";

    private static final String CM = ", ";

    private static final String CL = ": ";

    private static final String SP = " ";

    private static final String LP = " (";

    private static final String RP = ") ";

    private static final String CR = "\r";

    private static final String LF = "\n";

    public static void compile() {
        String dp1 = System.getProperties().getProperty("user.dir");
        String sep = System.getProperties().getProperty("file.separator");
        File src = new File(dp1 + sep + "src");
        File trg = new File(dp1 + sep + "bin");
        boolean dir = trg.isDirectory() || trg.mkdirs();
        List<File> files = getJavaFiles(src);
        // Get the java compiler provided with this platform
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        // Get the writer for the compiler output
        StringWriter out = new StringWriter();
        // Get the diagnostic listener for non-fatal diagnostics; if null use the compiler's default method for reporting diagnostics
        DiagnosticListener<JavaFileObject> diagnosticListener1 = null; // new DiagnosticCollector<JavaFileObject>();
        // Get the locale to apply when formatting diagnostics; null means the default locale
        Locale locale = null;
        // Get the character set used for decoding bytes; if null use the platform default
        Charset charset = null;
        // Get an instance of the standard file manager implementation
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnosticListener1, locale, charset);
        // Get the diagnostic listener for non-fatal diagnostics; if null use the compiler's default method for reporting diagnostics
        DiagnosticListener<JavaFileObject> diagnosticListener2 = null; // new DiagnosticCollector<JavaFileObject>();
        // Get the list of compiler options, null means no options
        List<String> options = Arrays.asList(new String[]{"-g", "-source", "1.7", "-Xlint:unchecked", "-d", trg.getAbsolutePath()});
        // Get the list of class names (for annotation processing), null means no class names
        List<String> classes = null;
        // Get the list of java file objects to compile
        Iterable<? extends JavaFileObject> units = fileManager.getJavaFileObjectsFromFiles(files);
        // Create the compilation task
        CompilationTask task = compiler.getTask(out, fileManager, diagnosticListener2, options, classes, units);
        // Perform the compilation task
        // task.call(); // java.lang.NullPointerException at com.sun.tools.javac.api.JavacTaskImpl.parse(JavacTaskImpl.java:228)
        if (task instanceof JavacTask) {
            javacTask(task);
        }
    }

    private static List<File> getJavaFiles(File directory) {
        List<File> javaFiles = new ArrayList<>();
        if (directory.exists()) {
            File[] files = directory.listFiles();
            for (File file : files) {
                String fileName = file.getName();
                if (file.isDirectory()) {
                    assert !fileName.contains(".");
                    javaFiles.addAll(getJavaFiles(file));
                } else if (fileName.endsWith(".java")) {
                    javaFiles.add(file);
                    logger.info("file" + CL + file.getAbsolutePath());
                }
            }
        }
        return javaFiles;
    }

    private static void javacTask(CompilationTask task) {
//      JavacTask @ jdk1.6.0/lib/tools.jar
        JavacTask javacTask = (JavacTask) task;
        Iterable<? extends CompilationUnitTree> units;
        Iterable<? extends Element> elements;
        Iterable<? extends JavaFileObject> files;
        try {
            units = javacTask.parse();
            elements = javacTask.analyze();
            files = javacTask.generate();
            scanParsedTrees(units);
            scanAnalyzedElements(elements);
            scanGeneratedFiles(files);
//          visit(units, task);

        } catch (IOException ex) {
            logger.fatal(ThrowableUtils.getString(ex), ex);
        }
    }

    private static void scanParsedTrees(Iterable<? extends CompilationUnitTree> units) {
        logger.info("units");
        for (CompilationUnitTree unit : units) {
            logger.info("unit" + CL + unit.getKind() + SP + unit.getSourceFile().getName()); // + NL + unit
            List<? extends Tree> typeDecls = unit.getTypeDecls();
            logger.info("type declarations" + CL + StringUtils.join(typeDecls, CM));
            logger.info("package" + CL + unit.getPackageName());
            List<? extends AnnotationTree> annotationsTrees = unit.getPackageAnnotations();
            logger.info("package annotations" + CL + StringUtils.join(annotationsTrees, CM));
            List<? extends ImportTree> importTrees = unit.getImports();
            logger.info("imports" + CL + LF + StringUtils.join(importTrees, ""));
//          LineMap lineMap = unit.getLineMap();
//          logger.info("line map" + CL + lineMap);
            logger.info("");
        }
    }

    private static void scanAnalyzedElements(Iterable<? extends Element> elements) {
//      logger.info("elements" + EQ + elements);
        logger.info("elements");
        scanAnalyzedElements(elements, "");
    }

    private static void scanAnalyzedElements(Iterable<? extends Element> elements, String tabs) {
        for (Element element : elements) {
            logger.info(tabs + "element" + CL + element.getKind() + SP + element.getSimpleName() + LP + element.getClass() + RP + element);
            logger.info(tabs + "enclosing element" + CL + element.getEnclosingElement());
            if (element instanceof ClassSymbol) {
                ClassSymbol classSymbol = (ClassSymbol) element;
            }
            if (element instanceof MethodSymbol) {
                MethodSymbol methodSymbol = (MethodSymbol) element;
                logger.info(tabs + "method symbol" + CL + methodSymbol.getReturnType()
                    + SP + methodSymbol.getSimpleName() + LP + StringUtils.join(methodSymbol.getParameters(), CM) + RP
                    + StringUtils.join(methodSymbol.getThrownTypes(), CM));
            }
            if (element instanceof VarSymbol) {
                VarSymbol varSymbol = (VarSymbol) element;
            }
            List<? extends Element> enclosedElements = element.getEnclosedElements();
            if (enclosedElements != null && !enclosedElements.isEmpty()) {
                logger.info(tabs + "enclosed elements" + CL + StringUtils.join(enclosedElements, CM));
                scanAnalyzedElements(enclosedElements, tabs + "\t");
            }
            logger.info("");
        }
    }

    private static void scanGeneratedFiles(Iterable<? extends JavaFileObject> files) {
//      logger.info("files" + EQ + files);
        logger.info("files");
        for (JavaFileObject file : files) {
//          logger.info("file" + CL + file);
//          logger.info("kind" + CL + file.getKind());
            logger.info("file" + CL + file + CM + file.getKind() + CM + file.getName());
        }
    }

    private static void visit(Iterable<? extends CompilationUnitTree> units, CompilationTask task) {
        TreePath treePath;
        try {
            Trees trees = Trees.instance(task);
            logger.info("trees" + EQ + trees);
            CodeAnalyzerTreeVisitor visitor = new CodeAnalyzerTreeVisitor();
            for (CompilationUnitTree unit : units) {
                treePath = new TreePath(unit);
                visitor.scan(treePath, trees);
            }
        } catch (Throwable e) {
            logger.error(e);
        }

    }

}
