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
package adalid.core;

import adalid.core.exceptions.*;
import adalid.core.expressions.*;
import adalid.core.interfaces.*;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang.StringUtils;

/**
 * @author Jorge Campins
 */
public class XS2 {

    public static boolean checkAccess() {
        return XS1.checkAccess();
    }

    public static List<String> invalidURLs(String... specs) {
        List<String> invalid = new ArrayList<>();
        if (specs != null && specs.length > 0) {
            for (String spec : specs) {
                if (getURL(spec) == null) {
                    invalid.add(spec);
                }
            }
        }
        return invalid;
    }

    /**
     * Creates a {@code URL} object from the {@code String} representation ignoring MalformedURLException.
     *
     * @param spec the {@code String} to parse as a URL.
     * @return a new URL by parsing the given spec or null if spec in invalid
     */
    public static URL getURL(String spec) {
        try {
            return spec == null ? null : new URI(spec).toURL(); // new URL(spec); is deprecated since JDK 20
        } catch (URISyntaxException | MalformedURLException ex) {
            return null;
        }
    }

    public static String iframe(String src, int width, int height) {
        if (StringUtils.isBlank(src)) {
            return null;
        }
        int w = width > 100 ? width : width > 0 ? 100 : 300;
        int h = height > 50 ? height : height > 0 ? 50 : 150;
        return MessageFormat.format(Constants.IFRAME_SIMPLE_PATTERN, src.trim(), w, h);
    }

    public static Set<String> canonicalNames(Set<Class<?>> classes) {
        String name;
        Set<String> set = new LinkedHashSet<>();
        for (Class<?> clazz : classes) {
            name = clazz.getCanonicalName();
            if (name != null) {
                set.add(name);
            }
        }
        return set;
    }

    public static Set<String> classNames(Set<Class<?>> classes) {
        String name;
        Set<String> set = new LinkedHashSet<>();
        for (Class<?> clazz : classes) {
            name = clazz.getName();
            if (name != null) {
                set.add(name);
            }
        }
        return set;
    }

    public static Set<String> simpleNames(Set<Class<?>> classes) {
        String name;
        Set<String> set = new LinkedHashSet<>();
        for (Class<?> clazz : classes) {
            name = clazz.getSimpleName();
            if (name != null) {
                set.add(name);
            }
        }
        return set;
    }

    public static Set<Class<?>> getLocallyDeclaredEntityClasses(Class<?> clazz) {
        Set<Class<?>> classes = new LinkedHashSet<>();
        for (Field field : getFields(clazz)) {
            Class<?> type = field.getType();
            if (Entity.class.isAssignableFrom(type)) {
                classes.add(type);
            }
        }
        return classes;
    }

    public static Class<?> getNamedClass(Object object) {
        return XS1.getNamedClass(object);
    }

    public static Class<?> getNamedClass(Class<?> clazz) {
        return XS1.getNamedClass(clazz);
    }

    public static Collection<Field> getFields(Class<?> clazz) throws SecurityException {
        return XS1.getFields(clazz, clazz);
    }

    public static Collection<Field> getFields(Class<?> clazz, Class<?> top) throws SecurityException {
        return XS1.getFields(clazz, top);
    }

    public static Property getProperty(Field field, Object declaringObject) {
        return XS1.getProperty(field, declaringObject);
    }

    public static Property getProperty(Field field, Object declaringObject, boolean ignoreExceptions) {
        return XS1.getProperty(field, declaringObject, ignoreExceptions);
    }

    public static void setDataClass(AbstractDataArtifact artifact, Class<?> clazz) {
        XS1.checkAccess();
        artifact.setDataClass(clazz);
    }

    public static void setDataType(AbstractDataArtifact artifact, Class<?> type) {
        XS1.checkAccess();
        artifact.setDataType(type);
    }

    public static VariantX getForeignExpression(Class<? extends VariantX> type, String name, Class<? extends Entity> declaringClass) {
        Field field = getForeignExpressionField(name, declaringClass);
        if (field != null) {
            Class<?> fieldType = field.getType();
            if (fieldType.isInterface() && fieldType.isAssignableFrom(type)) {
                VariantX expression;
                if (EntityExpression.class.isAssignableFrom(fieldType)) {
                    expression = new EntityX();
                } else if (BooleanExpression.class.isAssignableFrom(fieldType)) {
                    expression = new BooleanX();
                } else if (CharacterExpression.class.isAssignableFrom(fieldType)) {
                    expression = new CharacterX();
                } else if (NumericExpression.class.isAssignableFrom(fieldType)) {
                    expression = new NumericX();
                } else if (TemporalExpression.class.isAssignableFrom(fieldType)) {
                    expression = new TemporalX();
                } else {
                    expression = new VariantX();
                }
                expression.setForeignExpressionField(field);
                return expression;
            }
        }
        String errmsg = "failed to get foreign expression " + name + " at " + declaringClass;
        throw new InstantiationRuntimeException(errmsg);
    }

    private static Field getForeignExpressionField(String name, Class<?> type) {
        String role = "foreign expression";
        Class<?> top = Entity.class;
        Class<?>[] validTypes = null;
        return XS1.getField(true, role, name, type, top, validTypes);
    }

}
