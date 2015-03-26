/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.util.doclet;

import adalid.commons.util.StrUtils;
import adalid.commons.velocity.Writer;
import com.sun.javadoc.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public class MetaDoclet {

    // <editor-fold defaultstate="collapsed" desc="private static field declarations">
    private static int margin;

    private static boolean printOptions;

    private static boolean printPackages;

    private static boolean printAnnotations;

    private static boolean printElements;

    private static boolean printEnums;

    private static boolean printConstants;

    private static boolean printErrors;

    private static boolean printExceptions;

    private static boolean printInterfaces;

    private static boolean printClasses;

    private static boolean printFields;

    private static boolean printConstructors;

    private static boolean printMethods;

    private static boolean printComments;

    private static boolean printTags;

    private static boolean printRawTexts;

    private static final LanguageVersion LANGUAGE_VERSION = LanguageVersion.JAVA_1_5;

    private static final Logger logger = Logger.getLogger(MetaDoclet.class);

//  private static final String SPK = MetaDoclet.class.getSimpleName() + ".";
    private static final String SPK = "-";

    private static final String SPK_PRINT_OPTIONS = SPK + "printOptions";

    private static final String SPK_PRINT_PACKAGES = SPK + "printPackages";

    private static final String SPK_PRINT_ANNOTATIONS = SPK + "printAnnotations";

    private static final String SPK_PRINT_ELEMENTS = SPK + "printElements";

    private static final String SPK_PRINT_ENUMS = SPK + "printEnums";

    private static final String SPK_PRINT_CONSTANTS = SPK + "printConstants";

    private static final String SPK_PRINT_ERRORS = SPK + "printErrors";

    private static final String SPK_PRINT_EXCEPTIONS = SPK + "printExceptions";

    private static final String SPK_PRINT_INTERFACES = SPK + "printInterfaces";

    private static final String SPK_PRINT_CLASSES = SPK + "printClasses";

    private static final String SPK_PRINT_FIELDS = SPK + "printFields";

    private static final String SPK_PRINT_CONSTRUCTORS = SPK + "printConstructors";

    private static final String SPK_PRINT_METHODS = SPK + "printMethods";

    private static final String SPK_PRINT_COMMENTS = SPK + "printComments";

    private static final String SPK_PRINT_TAGS = SPK + "printTags";

    private static final String SPK_PRINT_RAW_TEXTS = SPK + "printRawTexts";

    private static final String EOL = "\n";

    private static final String TAB = "\t";

    private static final String PROPERTY = "PROPERTY";

    private static final String OPTION = "OPTION";

    private static final String PACKAGE = "PACKAGE";

    private static final String ANNOTATION = "ANNOTATION";

    private static final String ELEMENT = "ELEMENT";

    private static final String DEFAULT = "DEFAULT";

    private static final String ENUM = "ENUM";

    private static final String ERROR = "ERROR";

    private static final String EXCEPTION = "EXCEPTION";

    private static final String INTERFACE = "INTERFACE";

    private static final String CLASS = "CLASS";

    private static final String CONSTANT = "CONSTANT";

    private static final String FIELD = "FIELD";

    private static final String CONSTRUCTOR = "CONSTRUCTOR";

    private static final String METHOD = "METHOD";

    private static final String COMMENT = "COMMENT";

    private static final String TAG = "TAG";

    private static final String TEXT = "TEXT";

    private static final String GETTER = "GETTER";

    private static final String SETTER = "SETTER";

    private static final String VOID = "void";
    // </editor-fold>

    public static boolean start(RootDoc rootDoc) {
        logger.debug("start");
//      getProperties();
        printOptions(rootDoc);
        printPackages(rootDoc);
        write(rootDoc);
        return true;
    }

    public static int optionLength(String option) {
        logger.debug("optionLength" + StrUtils.enclose(option));
        int length = 0;
        switch (option) {
            case SPK_PRINT_OPTIONS:
            case SPK_PRINT_PACKAGES:
            case SPK_PRINT_ANNOTATIONS:
            case SPK_PRINT_ELEMENTS:
            case SPK_PRINT_ENUMS:
            case SPK_PRINT_CONSTANTS:
            case SPK_PRINT_ERRORS:
            case SPK_PRINT_EXCEPTIONS:
            case SPK_PRINT_INTERFACES:
            case SPK_PRINT_CLASSES:
            case SPK_PRINT_FIELDS:
            case SPK_PRINT_CONSTRUCTORS:
            case SPK_PRINT_METHODS:
            case SPK_PRINT_COMMENTS:
            case SPK_PRINT_TAGS:
            case SPK_PRINT_RAW_TEXTS:
                length = 1;
                break;
        }
        logger.debug(string("length", length));
        return length;
    }

    public static boolean validOptions(String[][] optionsArray, DocErrorReporter docErrorReporter) {
        logger.debug("validOptions");
        String[] strings;
        String option;
        for (int i = 0; i < optionsArray.length; i++) {
            strings = optionsArray[i];
            logger.debug(StringUtils.join(strings, " "));
            option = strings[0];
            switch (option) {
                case SPK_PRINT_OPTIONS:
                    printOptions = true;
                    break;
                case SPK_PRINT_PACKAGES:
                    printPackages = true;
                    break;
                case SPK_PRINT_ANNOTATIONS:
                    printAnnotations = true;
                    break;
                case SPK_PRINT_ELEMENTS:
                    printElements = true;
                    break;
                case SPK_PRINT_ENUMS:
                    printEnums = true;
                    break;
                case SPK_PRINT_CONSTANTS:
                    printConstants = true;
                    break;
                case SPK_PRINT_ERRORS:
                    printErrors = true;
                    break;
                case SPK_PRINT_EXCEPTIONS:
                    printExceptions = true;
                    break;
                case SPK_PRINT_INTERFACES:
                    printInterfaces = true;
                    break;
                case SPK_PRINT_CLASSES:
                    printClasses = true;
                    break;
                case SPK_PRINT_FIELDS:
                    printFields = true;
                    break;
                case SPK_PRINT_CONSTRUCTORS:
                    printConstructors = true;
                    break;
                case SPK_PRINT_METHODS:
                    printMethods = true;
                    break;
                case SPK_PRINT_COMMENTS:
                    printComments = true;
                    break;
                case SPK_PRINT_TAGS:
                    printTags = true;
                    break;
                case SPK_PRINT_RAW_TEXTS:
                    printRawTexts = true;
                    break;
            }
        }
        return true;
    }

    public static LanguageVersion languageVersion() {
        logger.debug("languageVersion");
        logger.debug(string("language version", LANGUAGE_VERSION));
        return LANGUAGE_VERSION;
    }

    public static void write(RootDoc rootDoc) {
        logger.debug("write");
        Writer writer = new Writer(rootDoc, "root");
        writer.write("meta-data-sql");
    }

    // <editor-fold defaultstate="collapsed" desc="getProperties">
//  private static void getProperties() {
//      logger.debug("getProperties");
//      printOptions = BitUtils.valueOf(getProperty(SPK_PRINT_OPTIONS));
//      printPackages = BitUtils.valueOf(getProperty(SPK_PRINT_PACKAGES));
//      printAnnotations = BitUtils.valueOf(getProperty(SPK_PRINT_ANNOTATIONS));
//      printElements = BitUtils.valueOf(getProperty(SPK_PRINT_ELEMENTS));
//      printEnums = BitUtils.valueOf(getProperty(SPK_PRINT_ENUMS));
//      printConstants = BitUtils.valueOf(getProperty(SPK_PRINT_CONSTANTS));
//      printClasses = BitUtils.valueOf(getProperty(SPK_PRINT_CLASSES));
//      printFields = BitUtils.valueOf(getProperty(SPK_PRINT_FIELDS));
//      printConstructors = BitUtils.valueOf(getProperty(SPK_PRINT_CONSTRUCTORS));
//      printMethods = BitUtils.valueOf(getProperty(SPK_PRINT_METHODS));
//      printComments = BitUtils.valueOf(getProperty(SPK_PRINT_COMMENTS));
//      printTags = BitUtils.valueOf(getProperty(SPK_PRINT_TAGS));
//      printRawTexts = BitUtils.valueOf(getProperty(SPK_PRINT_RAW_TEXTS));
//  }
//
//  private static String getProperty(String key) {
//      String property = System.getProperties().getProperty(key);
//      println(string(key, property));
//      return property;
//  }
    // </editor-fold>
//
    // <editor-fold defaultstate="collapsed" desc="print">
    private static void printOptions(RootDoc rootDoc) {
        logger.debug("printOptions");
        if (printOptions) {
            String[][] options = rootDoc.options();
            for (int i = 0; i < options.length; i++) {
                println(option(options[i]));
            }
        }
    }

    private static void printPackages(RootDoc rootDoc) {
        logger.debug("printPackages");
        if (printPackages) {
            PackageDoc[] packages = rootDoc.specifiedPackages();
            Arrays.sort(packages);
            for (PackageDoc packageDoc : packages) {
                println(string(packageDoc));
                margin++;
                println("all classes", packageDoc.allClasses(false).length);
                println("all included classes", packageDoc.allClasses().length);
                println("included annotation types", packageDoc.annotationTypes().length);
                println("included enum types", packageDoc.enums().length);
                println("included Error classes", packageDoc.errors().length);
                println("included Exception classes", packageDoc.exceptions().length);
                println("included interfaces", packageDoc.interfaces().length);
                println("included ordinary classes", packageDoc.ordinaryClasses().length);
                printAnnotations(packageDoc);
                printEnums(packageDoc);
                printErrors(packageDoc);
                printExceptions(packageDoc);
                printInterfaces(packageDoc);
                printClasses(packageDoc);
                margin--;
            }
            println("");
        }
    }

    private static void printAnnotations(PackageDoc packageDoc) {
        if (printAnnotations) {
            AnnotationTypeDoc[] annotations = packageDoc.annotationTypes();
            Arrays.sort(annotations);
            for (AnnotationTypeDoc annotationTypeDoc : annotations) {
                println(annotationTypeDoc);
                margin++;
                println("elements", annotationTypeDoc.elements().length);
                printElements(annotationTypeDoc);
                margin--;
            }
        }
    }

    private static void printElements(AnnotationTypeDoc annotationTypeDoc) {
        if (printElements) {
            AnnotationTypeElementDoc[] elements = annotationTypeDoc.elements();
            Arrays.sort(elements);
            for (AnnotationTypeElementDoc elementDoc : elements) {
                if (elementDoc.isSynthetic()) {
                    continue;
                }
                println(elementDoc);
            }
        }
    }

    private static void printEnums(PackageDoc packageDoc) {
        if (printEnums) {
            ClassDoc[] classes = packageDoc.enums();
            Arrays.sort(classes);
            for (ClassDoc classDoc : classes) {
                println(classDoc);
                margin++;
                println("constants", classDoc.enumConstants().length);
                printConstants(classDoc);
                margin--;
            }
        }
    }

    private static void printConstants(ClassDoc classDoc) {
        if (printConstants) {
            FieldDoc[] fields = classDoc.enumConstants();
            Arrays.sort(fields);
            for (FieldDoc fieldDoc : fields) {
                if (fieldDoc.isSynthetic()) {
                    continue;
                }
                println(fieldDoc);
            }
        }
    }

    private static void printErrors(PackageDoc packageDoc) {
        if (printErrors) {
            ClassDoc[] classes = packageDoc.errors();
            Arrays.sort(classes);
            for (ClassDoc classDoc : classes) {
                printClass(classDoc);
            }
        }
    }

    private static void printExceptions(PackageDoc packageDoc) {
        if (printExceptions) {
            ClassDoc[] classes = packageDoc.exceptions();
            Arrays.sort(classes);
            for (ClassDoc classDoc : classes) {
                printClass(classDoc);
            }
        }
    }

    private static void printInterfaces(PackageDoc packageDoc) {
        if (printInterfaces) {
            ClassDoc[] classes = packageDoc.interfaces();
            Arrays.sort(classes);
            for (ClassDoc classDoc : classes) {
                printClass(classDoc);
            }
        }
    }

    private static void printClasses(PackageDoc packageDoc) {
        if (printClasses) {
            ClassDoc[] classes = packageDoc.ordinaryClasses();
            Arrays.sort(classes);
            for (ClassDoc classDoc : classes) {
                if (classDoc.containingClass() != null) {
                    continue;
                }
                printClass(classDoc);
            }
        }
    }

    private static void printClass(ClassDoc classDoc) {
        println(classDoc);
        margin++;
        println("fields", classDoc.fields().length);
        println("constructors", classDoc.constructors().length);
        println("methods", classDoc.methods().length);
        printFields(classDoc);
        printConstructors(classDoc);
        printMethods(classDoc);
        margin--;
    }

    private static void printFields(ClassDoc classDoc) {
        if (printFields) {
            FieldDoc[] fields = classDoc.fields();
            Arrays.sort(fields);
            for (FieldDoc fieldDoc : fields) {
                if (fieldDoc.isSynthetic()) {
                    continue;
                }
                println(fieldDoc);
            }
        }
    }

    private static void printConstructors(ClassDoc classDoc) {
        if (printConstructors) {
            ConstructorDoc[] constructors = classDoc.constructors();
            Arrays.sort(constructors);
            for (ConstructorDoc constructorDoc : constructors) {
                if (constructorDoc.isSynthetic()) {
                    continue;
                }
                println(constructorDoc);
            }
        }
    }

    private static void printMethods(ClassDoc classDoc) {
        if (printMethods) {
            MethodDoc[] methods = classDoc.methods();
            Arrays.sort(methods);
            for (MethodDoc methodDoc : methods) {
                if (methodDoc.isSynthetic()) {
                    continue;
                }
                println(methodDoc);
            }
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="println">
    private static void println(Doc doc) {
        println(string(doc));
        margin++;
        println(comment(doc));
        println(doc.tags());
        println(text(doc));
        margin--;
    }

    private static void println(Tag[] tags) {
        if (printTags) {
            if (tags != null) {
                for (Tag tag : tags) {
                    println(string(tag));
                }
            }
        }
    }

    private static void println(String string) {
        if (string != null) {
            System.out.println(StringUtils.repeat(TAB, margin) + string);
        }
    }

    private static void println(String key, int value) {
        if (key != null && value > 0) {
            System.out.println(StringUtils.repeat(TAB, margin) + key + " = " + value);
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="string">
    private static String option(String[] value) {
        return OPTION + " " + StringUtils.join(value, " ");
    }

    private static String string(Doc doc) {
        return doc == null ? null
            : doc instanceof PackageDoc ? string((PackageDoc) doc)
            : doc.isAnnotationType() ? string((AnnotationTypeDoc) doc)
            : doc.isAnnotationTypeElement() ? string((AnnotationTypeElementDoc) doc)
            : doc.isEnum() ? string((ClassDoc) doc)
            : doc.isEnumConstant() ? string((FieldDoc) doc)
            : doc.isError() ? string((ClassDoc) doc)
            : doc.isException() ? string((ClassDoc) doc)
            : doc.isInterface() ? string((ClassDoc) doc)
            : doc.isOrdinaryClass() ? string((ClassDoc) doc)
            : doc.isClass() ? string((ClassDoc) doc)
            : doc.isField() ? string((FieldDoc) doc)
            : doc.isConstructor() ? string((ConstructorDoc) doc)
            : doc.isMethod() ? string((MethodDoc) doc)
            : "?";
    }

    private static String string(PackageDoc doc) {
        return PACKAGE + " " + doc.name();
    }

    private static String string(AnnotationTypeDoc doc) {
        return ANNOTATION + " " + doc.modifiers() + " " + doc.name();
    }

    private static String string(AnnotationTypeElementDoc doc) {
        String string = string(doc.defaultValue());
        String suffix = string == null ? "" : " " + DEFAULT + " " + string;
        return ELEMENT + " " + doc.modifiers() + " " + doc.returnType().simpleTypeName() + " " + doc.name() + doc.flatSignature() + suffix;
    }

    private static String string(AnnotationValue annotationValue) {
        /**
         * The type of the returned object is one of the following:
         * <p> a wrapper class for a primitive type
         * <p> String
         * <p> Type (representing a class literal)
         * <p> FieldDoc (representing an enum constant)
         * <p> AnnotationDesc
         * <p> AnnotationValue[]
         */
        if (annotationValue == null) {
            return null;
        }
        Object object = annotationValue.value();
        return object == null ? null
            : object instanceof Type ? ((Type) object).simpleTypeName()
            : object instanceof FieldDoc ? ((FieldDoc) object).name()
            : object instanceof AnnotationValue[] ? string((AnnotationValue[]) object)
            : object + "";
    }

    private static String string(AnnotationValue[] annotationValues) {
        if (annotationValues == null) {
            return null;
        }
        String[] strings = new String[annotationValues.length];
        for (int i = 0; i < annotationValues.length; i++) {
            strings[i] = string(annotationValues[i]);
        }
        return StrUtils.enclose(StringUtils.join(strings, ", "), '{', '}');
    }

    private static String string(ClassDoc doc) {
        return kind(doc) + " " + doc.modifiers() + " " + doc.name();
    }

    private static String string(FieldDoc doc) {
        String prefix;
        String suffix;
        if (doc.isStatic() && doc.isFinal()) {
            prefix = CONSTANT;
            suffix = doc.constantValueExpression();
            suffix = suffix == null ? "" : " = " + suffix;
        } else {
            prefix = FIELD;
            suffix = "";
        }
        String string = doc.isEnumConstant() ? doc.name() : doc.modifiers() + " " + doc.type().simpleTypeName() + " " + doc.name();
        return prefix + " " + string + suffix;
    }

    private static String string(ConstructorDoc doc) {
        return CONSTRUCTOR + " " + doc.modifiers() + " " + doc.name() + doc.flatSignature();
    }

    private static String string(MethodDoc doc) {
        String name = property(doc);
        String type = doc.returnType().simpleTypeName();
        String kind = name == null ? METHOD : type.equals(VOID) ? SETTER : GETTER;
        return kind + " " + doc.modifiers() + " " + type + " " + doc.name() + doc.flatSignature();
    }

    private static String string(Tag tag) {
        String text = tag.text();
        return TAG + " " + tag.name() + " " + text;
    }

    private static String string(String key, Object value) {
        return key + " = " + value;
    }

    private static String comment(Doc doc) {
        if (printComments) {
            String text = doc.commentText();
            return text == null || text.isEmpty() ? null : COMMENT + " " + text;
        } else {
            return null;
        }
    }

    private static String text(Doc doc) {
        if (printRawTexts) {
            String text = doc.getRawCommentText();
            return text == null || text.isEmpty() ? null : TEXT + text.replaceAll(EOL + EOL, EOL);
        } else {
            return null;
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="getters">
    public static MetaDocletProperty[] properties(ClassDoc classDoc) {
        MetaDocletProperty property;
        Map<String, MetaDocletProperty> properties = new TreeMap<>();
        MethodDoc[] methods = classDoc.methods();
        for (MethodDoc methodDoc : methods) {
            if (methodDoc.isSynthetic()) {
                continue;
            }
            String name = property(methodDoc);
            if (name == null) {
                continue;
            }
            property = properties.get(name);
            if (property == null) {
                property = new MetaDocletProperty(name);
                properties.put(name, property);
            }
            Type type = methodDoc.returnType();
            if (type.simpleTypeName().equals(VOID)) {
                property.setters.add(methodDoc);
            } else {
                property.getters.add(methodDoc);
                if (property.type == null) {
                    property.type = type;
                }
            }
        }
        return properties.values().toArray(new MetaDocletProperty[0]);
    }

    public static ClassDoc[] classes(ClassDoc classDoc) {
        List<ClassDoc> list = new ArrayList<>();
        add(classDoc, list);
        return list.toArray(new ClassDoc[0]);
    }

    private static void add(ClassDoc classDoc, List<ClassDoc> list) {
        ClassDoc superClassDoc = classDoc.superclass();
        if (superClassDoc != null && superClassDoc.isIncluded()) {
            add(superClassDoc, list);
        }
        list.add(classDoc);
    }

    public static String kind(Doc doc) {
        return doc == null ? null
            : doc instanceof PackageDoc ? PACKAGE
            : doc.isAnnotationType() ? ANNOTATION
            : doc.isAnnotationTypeElement() ? ELEMENT
            : doc.isEnum() ? ENUM
            : doc.isEnumConstant() ? CONSTANT
            : doc.isError() ? ERROR
            : doc.isException() ? EXCEPTION
            : doc.isInterface() ? INTERFACE
            : doc.isOrdinaryClass() ? CLASS
            : doc.isClass() ? CLASS
            : doc.isField() ? FIELD
            : doc.isConstructor() ? CONSTRUCTOR
            : doc.isMethod() ? METHOD
            : "?";
    }

    public static String kind(MethodDoc doc) {
        if (doc.isAnnotationTypeElement()) {
            return ELEMENT;
        }
        String name = property(doc);
        String type = doc.returnType().simpleTypeName();
        return name == null ? METHOD : type.equals(VOID) ? SETTER : GETTER;
    }

    public static String property(MethodDoc doc) {
        if (doc.isAnnotationTypeElement()) {
            return null;
        }
        String name = doc.name();
        String type = doc.returnType().simpleTypeName();
        return type.equals(VOID) && name.matches("set[A-Z].*") ? name.substring(3)
            : !type.equals(VOID) && name.matches("get[A-Z].*") ? name.substring(3)
            : !type.equals(VOID) && name.matches("is[A-Z].*") ? name.substring(2)
            : null;
    }

    public static String commentText(Doc doc) {
        String text = doc.commentText();
        if (text == null || text.isEmpty()) {
            return null;
        }
        return clean(text);
    }

    public static String rawCommentText(Doc doc) {
        String text = doc.getRawCommentText();
        if (text == null || text.isEmpty()) {
            return null;
        }
        return clean(text);
    }

    private static String clean(String text) {
        String rex = " +";
        text = text.replaceAll(rex + EOL, EOL);
        text = text.replaceAll(EOL + rex, EOL);
        text = text.replaceAll(EOL + EOL, EOL);
        return text.trim();
    }
    // </editor-fold>

}
