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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

/**
 * @author Jorge Campins
 */
public class JavaUtils {

    private static final Logger logger = Logger.getLogger(JavaUtils.class);

    // <editor-fold defaultstate="collapsed" desc="keywords">
    private static final String[] KEYWORDS = {
        "abstract",
        "assert",
        "boolean",
        "break",
        "byte",
        "case",
        "catch",
        "char",
        "class",
        "const",
        "continue",
        "default",
        "do",
        "double",
        "else",
        "enum",
        "extends",
        "final",
        "finally",
        "float",
        "for",
        "goto",
        "if",
        "implements",
        "import",
        "instanceof",
        "int",
        "interface",
        "long",
        "native",
        "new",
        "package",
        "private",
        "protected",
        "public",
        "return",
        "short",
        "static",
        "strictfp",
        "super",
        "switch",
        "synchronized",
        "this",
        "throw",
        "throws",
        "transient",
        "try",
        "void",
        "volatile",
        "while"
    };
    // </editor-fold>

    public static String[] getJavaKeywordArray() {
        return KEYWORDS;
    }

    public static Set<String> getJavaKeywordSet() {
        return new TreeSet<>(Arrays.asList(KEYWORDS));
    }

    public static Set<String> getCanonicalNames(Collection<Class<?>> types) {
        Set<String> names = new TreeSet<>();
        for (Class<?> type : types) {
            names.add(type.getCanonicalName());
        }
        return names;
    }

    @SuppressWarnings("unchecked")
    public static <T extends Enum<T>> T getEnumOfEquals(String name, T... values) {
        if (name != null && values != null) {
            for (T t : values) {
                if (name.equals(t.name())) {
                    return t;
                }
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <T extends Enum<T>> T getEnumOfEqualsIgnoreCase(String name, T... values) {
        if (name != null && values != null) {
            for (T t : values) {
                if (name.equalsIgnoreCase(t.name())) {
                    return t;
                }
            }
        }
        return null;
    }

    public static Boolean getStaticBooleanValue(Object object, String fieldName) {
        if (object == null || fieldName == null) {
            return null;
        }
        Class<?> clazz = object instanceof Class<?> ? (Class<?>) object : object.getClass();
        Object value;
        try {
            Field field = clazz.getField(fieldName);
            Class<?> fieldType = field.getType();
            value = fieldType == boolean.class ? field.getBoolean(null) : fieldType == Boolean.class ? field.get(null) : null;
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            value = null;
        }
        return (Boolean) value;
    }

    public static String getStaticStringValue(Object object, String fieldName) {
        if (object == null || fieldName == null) {
            return null;
        }
        Class<?> clazz = object instanceof Class<?> ? (Class<?>) object : object.getClass();
        Object value;
        try {
            Field field = clazz.getField(fieldName);
            Class<?> fieldType = field.getType();
            value = fieldType == String.class ? field.get(null) : null;
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
            value = null;
        }
        return (String) value;
    }

    public static Set<Class<?>> getTypes(String prefix, Class<? extends Annotation> annotation) {
        logger.trace("getTypes(" + prefix + ", " + annotation + ")");
        Reflections reflections = new Reflections(prefix, Scanners.TypesAnnotated);
        return reflections.getTypesAnnotatedWith(annotation).stream().collect(Collectors.toSet());
    }

    public static Set<Class<?>> getSubTypes(String prefix, Class<?> type) {
        logger.trace("getSubTypes(" + prefix + ", " + type + ")");
        /*
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.forPackages(prefix);
        configurationBuilder.setScanners(Scanners.SubTypes);
        Reflections reflections = new Reflections(configurationBuilder);
        /**/
        Reflections reflections = new Reflections(prefix, Scanners.SubTypes.filterResultsBy(s -> true));
        return reflections.getSubTypesOf(type).stream().collect(Collectors.toSet());
    }

    public static Set<Method> getVoidMethods(String prefix) {
        logger.trace("getVoidMethods(" + prefix + ")");
        Reflections reflections = new Reflections(prefix, Scanners.MethodsReturn);
        return reflections.getMethodsReturn(void.class).stream().collect(Collectors.toSet());
    }

}
