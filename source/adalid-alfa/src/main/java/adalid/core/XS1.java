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

import adalid.commons.util.*;
import adalid.core.annotations.*;
import adalid.core.enums.*;
import adalid.core.exceptions.*;
import adalid.core.expressions.*;
import adalid.core.interfaces.*;
import adalid.core.primitives.*;
import adalid.core.properties.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
class XS1 {

    // <editor-fold defaultstate="collapsed" desc="static field declarations">
    private static final Logger logger = Logger.getLogger(XS1.class);

    private static final String TAB = "\t";

    private static final String THAT_CLASS = XS2.class.getName();

    private static final String THIS_CLASS = XS1.class.getName();

    private static final String THIS_PACKAGE = XS1.class.getPackageName();

    private static final Map<KeyProperty, Class<? extends Annotation>> keyPropertyAnnotation;

    static final Map<KeyProperty, Class<?>[]> keyPropertyValidTypes; // package access since 20201214

    static {
        keyPropertyAnnotation = new LinkedHashMap<>();
        keyPropertyAnnotation.put(KeyProperty.PRIMARY_KEY, PrimaryKey.class);
        keyPropertyAnnotation.put(KeyProperty.SEQUENCE, SequenceProperty.class);
        keyPropertyAnnotation.put(KeyProperty.VERSION, VersionProperty.class);
        keyPropertyAnnotation.put(KeyProperty.NAME, NameProperty.class);
        keyPropertyAnnotation.put(KeyProperty.DESCRIPTION, DescriptionProperty.class);
        keyPropertyAnnotation.put(KeyProperty.IMAGE, ImageProperty.class);
        keyPropertyAnnotation.put(KeyProperty.INACTIVE_INDICATOR, InactiveIndicator.class);
        keyPropertyAnnotation.put(KeyProperty.URL, UrlProperty.class);
        keyPropertyAnnotation.put(KeyProperty.PARENT, ParentProperty.class);
        keyPropertyAnnotation.put(KeyProperty.OWNER, OwnerProperty.class);
        keyPropertyAnnotation.put(KeyProperty.USER, UserProperty.class);
        keyPropertyAnnotation.put(KeyProperty.SEGMENT, SegmentProperty.class);
        keyPropertyAnnotation.put(KeyProperty.UNIQUE_KEY, UniqueKey.class);
        keyPropertyAnnotation.put(KeyProperty.BUSINESS_KEY, BusinessKey.class);
        keyPropertyAnnotation.put(KeyProperty.DISCRIMINATOR, DiscriminatorColumn.class);
        keyPropertyAnnotation.put(KeyProperty.STATE, StateProperty.class);
        /**/
        keyPropertyValidTypes = new LinkedHashMap<>();
        keyPropertyValidTypes.put(KeyProperty.PRIMARY_KEY,
            new Class<?>[]{LongProperty.class, IntegerProperty.class});
        keyPropertyValidTypes.put(KeyProperty.SEQUENCE,
            new Class<?>[]{LongProperty.class});
        keyPropertyValidTypes.put(KeyProperty.VERSION,
            new Class<?>[]{LongProperty.class});
        keyPropertyValidTypes.put(KeyProperty.NUMERIC_KEY,
            new Class<?>[]{IntegerProperty.class});
        keyPropertyValidTypes.put(KeyProperty.CHARACTER_KEY,
            new Class<?>[]{StringProperty.class});
        keyPropertyValidTypes.put(KeyProperty.NAME,
            new Class<?>[]{StringProperty.class});
        keyPropertyValidTypes.put(KeyProperty.DESCRIPTION,
            new Class<?>[]{StringProperty.class});
        keyPropertyValidTypes.put(KeyProperty.IMAGE,
            new Class<?>[]{BinaryProperty.class});
        keyPropertyValidTypes.put(KeyProperty.INACTIVE_INDICATOR,
            new Class<?>[]{BooleanProperty.class});
        keyPropertyValidTypes.put(KeyProperty.URL,
            new Class<?>[]{StringProperty.class});
        keyPropertyValidTypes.put(KeyProperty.PARENT,
            new Class<?>[]{EntityReference.class});
        keyPropertyValidTypes.put(KeyProperty.OWNER,
            new Class<?>[]{EntityReference.class});
        keyPropertyValidTypes.put(KeyProperty.USER,
            new Class<?>[]{EntityReference.class});
        keyPropertyValidTypes.put(KeyProperty.SEGMENT,
            new Class<?>[]{EntityReference.class, LongProperty.class}); // since 20210218
        keyPropertyValidTypes.put(KeyProperty.UNIQUE_KEY,
            new Class<?>[]{BooleanPrimitive.class, CharacterPrimitive.class, NumericPrimitive.class, TemporalPrimitive.class, EntityReference.class});
        keyPropertyValidTypes.put(KeyProperty.BUSINESS_KEY,
            new Class<?>[]{StringProperty.class});
        /* only PersistentEnumerationEntityReference.class since 20201214
        keyPropertyValidTypes.put(KeyProperty.DISCRIMINATOR,
            new Class<?>[]{CharacterProperty.class, StringProperty.class, IntegerProperty.class, PersistentEnumerationEntityReference.class});
        /**/
        keyPropertyValidTypes.put(KeyProperty.DISCRIMINATOR,
            new Class<?>[]{PersistentEnumerationEntityReference.class});
        /* changed to PersistentEnumerationEntityReference.class since 20201214
        keyPropertyValidTypes.put(KeyProperty.STATE,
            new Class<?>[]{EntityReference.class});
        /**/
        keyPropertyValidTypes.put(KeyProperty.STATE,
            new Class<?>[]{PersistentEnumerationEntityReference.class});
    }
    // </editor-fold>

    /*
    private static final boolean ALLOCATION_STRINGS_LOGGING = true;

    /**/
    private static final boolean UNLIMITED_ACCESS = true;

    static boolean checkAccess() {
        if (UNLIMITED_ACCESS) {
            return true;
        }
        String method;
        String metodo = null;
        final StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        for (StackTraceElement element : stack) {
            method = element.getClassName() + "." + element.getMethodName();
            if (metodo == null) {
                if (method.startsWith(THIS_PACKAGE) && !method.startsWith(THAT_CLASS) && !method.startsWith(THIS_CLASS)) {
                    metodo = method;
                }
            } else if (method.startsWith(THIS_PACKAGE)) {
                break;
            } else {
                String message = "illegal invocation of \"" + StringUtils.substringAfterLast(metodo, ".") + "\"";
                message += " at " + method + "(" + element.getFileName() + ":" + element.getLineNumber() + ")";
                throw new IllegalAccessRuntimeException(message);
            }
        }
        return metodo != null;
    }

    static Class<?> getAnnotatedClass(Class<?> clazz, Class<? extends Annotation> annotationClass) {
        if (clazz == null || annotationClass == null) {
            return null;
        }
        if (clazz.isAnnotationPresent(annotationClass)) {
            return clazz;
        }
        return getAnnotatedClass(clazz.getSuperclass(), annotationClass);
    }

    static Class<?> getNamedClass(Object object) {
        assert object != null;
        return getNamedClass(object.getClass());
    }

    static Class<?> getNamedClass(Class<?> clazz) {
        assert clazz != null;
        return clazz.isAnonymousClass() ? clazz.getSuperclass() : clazz;
    }
//
    // <editor-fold defaultstate="collapsed" desc="static Class<?> getAbstractSuperclass(Class<?> c)">
//  static Class<?> getAbstractSuperclass(Class<?> c) {
//      if (c == null) {
//          return null;
//      }
//      Class<?> s = c.getSuperclass();
//      if (s == null) {
//          return null;
//      }
//      if (c.isAnonymousClass()) {
//          return getAbstractSuperclass(s);
//      }
//      int modifiers = s.getModifiers();
//      if (Modifier.isAbstract(modifiers)) {
//          return s;
//      }
//      return getAbstractSuperclass(s);
//  }
    // </editor-fold>

    static Class<?> getConcreteSuperclass(Class<?> c) {
        if (c == null) {
            return null;
        }
        Class<?> s = c.getSuperclass();
        if (s == null) {
            return null;
        }
        if (c.isAnonymousClass()) {
            return getConcreteSuperclass(s);
        }
        int modifiers = s.getModifiers();
        if (Modifier.isAbstract(modifiers)) {
            return null;
        }
        if (s.getSimpleName().equals(c.getSimpleName())) {
            return getConcreteSuperclass(s);
        }
        return s;
    }
//
    // <editor-fold defaultstate="collapsed" desc="private Class<?> getReturnClass(Method method)">
//  static Class<?> getReturnClass(Method method) {
//      Class<?> returnClass = method.getReturnType();
//      boolean isAssignable = Collection.class.isAssignableFrom(returnClass);
//      if (isAssignable) {
//          Type genericReturnType = method.getGenericReturnType();
//          if (genericReturnType instanceof ParameterizedType) {
//              ParameterizedType parameterizedType = (ParameterizedType) genericReturnType;
//              Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
//              if (actualTypeArguments != null && actualTypeArguments.length > 0) {
//                  for (Type actualTypeArgument : actualTypeArguments) {
//                      if (actualTypeArgument instanceof Class<?>) {
//                          returnClass = (Class<?>) actualTypeArgument;
//                      }
//                  }
//              }
//          }
//      }
//      return returnClass;
//  }
    // </editor-fold>
//
    // <editor-fold defaultstate="collapsed" desc="List<Class<?>> getClasses(...)">
//  /**
//   * Scans all classes accessible from the context class loader which belong to the given package and subpackages.
//   *
//   * @param packageName The base package
//   * @return The classes
//   * @throws ClassNotFoundException
//   * @throws IOException
//   */
//  static List<Class<?>> getClasses(String packageName) throws ClassNotFoundException, IOException, URISyntaxException {
//      ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//      assert classLoader != null;
//      String path = packageName.replace('.', '/');
//      Enumeration<URL> resources = classLoader.getResources(path);
//      List<File> dirs = new ArrayList<File>();
//      while (resources.hasMoreElements()) {
//          URL resource = resources.nextElement();
////        String fileName = resource.getFile();
////        String fileNameDecoded = URLDecoder.decode(fileName, "UTF-8");
////        dirs.add(new File(fileNameDecoded));
//          dirs.add(new File(resource.toURI()));
//      }
//      List<Class<?>> classes = new ArrayList<Class<?>>();
//      for (File directory : dirs) {
//          classes.addAll(getClasses(directory, packageName));
//      }
//      return classes;
//  }
//
//  /**
//   * Recursive method used to find all classes in a given directory and subdirs.
//   *
//   * @param directory The base directory
//   * @param packageName The package name for classes found inside the base directory
//   * @return The classes
//   * @throws ClassNotFoundException
//   */
//  static List<Class<?>> getClasses(File directory, String packageName) throws ClassNotFoundException {
//      List<Class<?>> classes = new ArrayList<Class<?>>();
//      if (directory.exists()) {
//          File[] files = directory.listFiles();
//          for (File file : files) {
//              String fileName = file.getName();
//              if (file.isDirectory()) {
//                  assert !fileName.contains(".");
//                  classes.addAll(getClasses(file, packageName + "." + fileName));
//              } else if (fileName.endsWith(".class") && !fileName.contains("$")) {
//                  String className = packageName + '.' + fileName.substring(0, fileName.length() - 6);
//                  logger.trace(className);
//                  Class<?> clazz;
//                  try {
//                      clazz = Class.forName(className);
//                  } catch (ExceptionInInitializerError e) {
//                      clazz = Class.forName(className, false, Thread.currentThread().getContextClassLoader());
//                  }
//                  classes.add(clazz);
//              }
//          }
//      }
//      return classes;
//  }
    // </editor-fold>

    static List<Field> getFields(Class<?> clazz, Class<?> top) throws SecurityException {
        return getFields(clazz, top, Object.class);
    }

    static List<Field> getFields(Class<?> clazz, Class<?> top, Class<?> fieldType) throws SecurityException {
        return getFields(clazz, top, fieldType, null);
    }

    static List<Field> getFields(Class<?> clazz, Class<?> top, Class<?> fieldType, Class<? extends Annotation> annotationClass) throws SecurityException {
        List<Field> fields = new ArrayList<>();
        if (clazz == null || top == null || fieldType == null || !top.isAssignableFrom(clazz)) {
            return fields;
        }
        Class<?> superClazz = clazz.getSuperclass();
        if (superClazz == null) {
            logger.trace(clazz.getName());
        } else {
            logger.trace(clazz.getName() + " extends " + superClazz.getName());
            if (top.isAssignableFrom(superClazz)) {
//              fields.addAll(getFields(superClazz, top));
                List<Field> superFields = getFields(superClazz, top, fieldType);
                for (Field field : superFields) {
                    if (valid(field, fieldType, annotationClass)) {
                        fields.add(field);
                    }
                }
            }
        }
        if (top.isAssignableFrom(clazz)) {
            logger.trace("adding fields declared at " + clazz.getName());
//          fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field field : declaredFields) {
                if (valid(field, fieldType, annotationClass)) {
                    fields.add(field);
                }
            }
        }
        return getRidOfDupFields(fields);
    }

    private static boolean valid(Field field, Class<?> fieldType, Class<? extends Annotation> annotationClass) {
        if (fieldType.isAssignableFrom(field.getType())) {
            if (annotationClass == null || field.isAnnotationPresent(annotationClass)) {
                int modifiers = field.getModifiers();
                boolean restricted = Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers);
                return !restricted;
            }
        }
        return false;
    }

    private static List<Field> getRidOfDupFields(List<Field> fields) {
        LinkedHashMap<String, Field> map = new LinkedHashMap<>();
        String key;
        for (Field field : fields) {
            key = field.getName();
            if (map.containsKey(key)) {
                TLC.getProject().getParser().error("Field " + key + " hides another field");
                logger.error(TAB + "hiding field: " + field);
                logger.error(TAB + "hidden field: " + map.get(key));
            }
            map.put(key, field);
        }
        return new ArrayList<>(map.values());
    }

    static List<Field> getEntityFields(Class<?> clazz, Class<?> top, Class<?> fieldType) throws SecurityException {
        return clazz == null || top == null || fieldType == null || !top.isAssignableFrom(clazz) ? new ArrayList<>() : efields(clazz, top, fieldType);
    }

    private static List<Field> efields(Class<?> clazz, Class<?> top, Class<?> fieldType) throws SecurityException {
        List<Field> fields = getFields(clazz, top, fieldType);
        if (fields.isEmpty()) {
            Class<?> superType = fieldType.getSuperclass();
            if (superType != null && superType.getSimpleName().equals(fieldType.getSimpleName())) {
                fields = efields(clazz, top, superType);
            }
        }
        return fields;
    }

    static Object initialiseField(Object declaringObject, Field declaringField) {
        return initialiseField(declaringObject, declaringField, null, true);
    }

    static Object initialiseField(Object declaringObject, Field declaringField, Class<?> declaringFieldClass) {
        return initialiseField(declaringObject, declaringField, declaringFieldClass, true);
    }

    private static Object initialiseField(Object declaringObject, Field declaringField, Class<?> declaringFieldClass, boolean casting) {
        if (declaringObject == null || declaringField == null) {
            return null;
        }
        String declaringFieldName = declaringField.getName();
        Class<?> declaringFieldType = declaringFieldClass != null ? declaringFieldClass : declaringField.getType();
        if (NamedValue.class.equals(declaringFieldType)) {
            return NamedValueCache.getInstance(declaringFieldName);
        }
        String fieldName = declaringFieldName;
        Class<?> fieldType = getFieldType(declaringFieldType);
        if (fieldType == null) {
            return null;
        }
        Object instance = null;
        Class<?> declaringClass = declaringObject.getClass();
        Class<?> enclosingClass = fieldType.getEnclosingClass();
        boolean memberClass = fieldType.isMemberClass();
        Entity declaringEntity = declaringObject instanceof Entity ? (Entity) declaringObject : null;
        Artifact declaringArtifact = declaringObject instanceof Artifact ? (Artifact) declaringObject : null;
        boolean declaringArtifactIsParameter = false;
        if (declaringObject instanceof DataArtifact declaringDataArtifact) {
            declaringArtifactIsParameter = declaringDataArtifact.isParameter();
        }
        String errmsg = "failed to create a new instance of field " + declaringField + " at " + declaringObject;
        try {
            CastingField castingFieldAnnotation = casting ? getCastingFieldAnnotation(declaringField, fieldType) : null;
            if (castingFieldAnnotation != null) {
                String name = castingFieldAnnotation.value();
                Field field = getFieldToBeCasted(true, fieldName, name, declaringClass, fieldType);
                if (field != null) {
                    errmsg = "failed to set casting field " + declaringField + " at " + declaringObject;
                    instance = field.get(declaringObject);
                    if (instance instanceof Artifact castedArtifact) {
                        Class<? extends Object> castedArtifactClass = castedArtifact.getClass();
                        if (fieldType.isAssignableFrom(castedArtifactClass)) {
                            // can return instance
                        } else {
                            instance = null;
                            if (castedArtifactClass.isAssignableFrom(fieldType)) {
                                Object castingInstance = initialiseField(declaringObject, declaringField, declaringFieldType, false);
                                if (castingInstance instanceof AbstractArtifact castingArtifact) {
                                    Field castedArtifactDeclaringField = castedArtifact.getDeclaringField();
                                    Artifact castedArtifactDeclaringArtifact = castedArtifact.getDeclaringArtifact();
                                    // do not declare here; it will be declared at the corresponding finalise method
                                    castingArtifact.setName(castedArtifact.getName());
                                    castingArtifact.setDeclaringArtifact(castedArtifactDeclaringArtifact);
                                    castingArtifact.setDeclaringField(castedArtifactDeclaringField);
                                    /**/
                                    castedArtifactDeclaringField.set(castedArtifactDeclaringArtifact, castingArtifact);
                                    instance = castingArtifact;
                                    /**/
                                }
                            }
                        }
                    }
                }
            } else if (memberClass && enclosingClass != null && enclosingClass.isAssignableFrom(declaringClass)) {
                if (Operation.class.isAssignableFrom(fieldType)) {
                    if (declaringArtifact == null) {
                        instance = fieldType.getConstructor(enclosingClass).newInstance(declaringObject);
                    } else if (declaringEntity == null) {
                        TLC.setDeclaringArtifact(declaringArtifact);
                        TLC.setDeclaringField(declaringField); // since 20200203
                        instance = fieldType.getConstructor(enclosingClass).newInstance(declaringObject);
                        TLC.removeDeclaringArtifact();
                        TLC.removeDeclaringField(); // since 20200203
                    } else {
                        String key = fieldType.getSimpleName();
                        Map<String, Class<?>> map = declaringEntity.getOperationClassesMap();
                        Class<?> fieldTypeX = map.containsKey(key) ? map.get(key) : fieldType;
                        Class<?> enclosingClassX = fieldTypeX.getEnclosingClass();
                        TLC.setDeclaringArtifact(declaringArtifact);
                        TLC.setDeclaringField(declaringField); // since 20200203
                        instance = fieldTypeX.getConstructor(enclosingClassX).newInstance(declaringObject);
                        TLC.removeDeclaringArtifact();
                        TLC.removeDeclaringField(); // since 20200203
                    }
                } else {
                    if (declaringArtifact == null) { // since 20200203
                        instance = fieldType.getConstructor(enclosingClass).newInstance(declaringObject);
                    } else { // since 20200203
                        TLC.setDeclaringArtifact(declaringArtifact); // since 20200203
                        TLC.setDeclaringField(declaringField); // since 20200203
                        instance = fieldType.getConstructor(enclosingClass).newInstance(declaringObject);
                        TLC.removeDeclaringArtifact(); // since 20200203
                        TLC.removeDeclaringField(); // since 20200203
                    }
                }
            } else if (enclosingClass == null) {
                if (Entity.class.isAssignableFrom(fieldType)) {
                    if (declaringArtifact == null) {
                        instance = fieldType.getDeclaredConstructor().newInstance();
                    } else {
                        Class<?> trueType = getTrueType(fieldType);
                        checkAbstractClassReference(declaringField, trueType);
                        String fullName = declaringArtifact.getFullName() + "." + fieldName;
                        String path = declaringArtifact.getClassPath();
                        int depth = declaringArtifact.depth() + 1;
                        int round = round(trueType, declaringArtifact);
                        TLC.getProject().getParser().setMaxDepthReached(depth);
                        TLC.getProject().getParser().setMaxRoundReached(round);
                        FieldAllocationSettings settings = new FieldAllocationSettings(declaringField, declaringObject, depth, round);
                        boolean fair = settings.isAllocatable(fullName);
                        int maxDepth = settings.getMaxDepth();
                        int maxRound = settings.getMaxRound();
                        String method1 = "allocate(maxDepth={0}, maxRound={1})";
                        String method2 = MessageFormat.format(method1, maxDepth, maxRound);
                        String remarks = fieldType.equals(trueType) ? null : "fieldType=" + fieldType.getName() + ", trueType=" + trueType.getName();
                        Class<?> type = fieldType; // since 20200905 - instantiate references using field type
                        if (fair || declaringArtifactIsParameter || declaringArtifact.depth() == 0 || (depth <= maxDepth && round <= maxRound)) {
                            TLC.getProject().getParser().track(depth, round, path, type, fieldName, method2, remarks);
                            instance = type.getConstructor(Artifact.class, Field.class).newInstance(declaringObject, declaringField);
                            TLC.getProject().getParser().addEntity((Entity) instance, fullName, depth, round, maxDepth, maxRound);
                        }
                    }
                } else {
                    if (declaringArtifact == null) { // since 20200203
                        instance = fieldType.getDeclaredConstructor().newInstance();
                    } else { // since 20200203
                        TLC.setDeclaringArtifact(declaringArtifact); // since 20200203
                        TLC.setDeclaringField(declaringField); // since 20200203
                        instance = fieldType.getDeclaredConstructor().newInstance();
                        TLC.removeDeclaringArtifact(); // since 20200203
                        TLC.removeDeclaringField(); // since 20200203
                    }
                }
            }
            if (instance instanceof AbstractArtifact abstractArtifact) {
                // do not declare here; it will be declared at the corresponding finalise method
                abstractArtifact.setName(fieldName);
                abstractArtifact.setDeclaringArtifact(declaringArtifact);
                abstractArtifact.setDeclaringField(declaringField);
                if (instance instanceof VariantX variantX) {
                    variantX.setDataType(declaringFieldType);
                }
            }
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InstantiationException | InvocationTargetException ex) {
            throw new InstantiationRuntimeException(errmsg, ex);
        }
        return instance;
    }

    private static void checkAbstractClassReference(Field declaringField, Class<?> type) {
        if (type.isAnnotationPresent(AbstractClass.class)) {
            String message = "Abstract Class Reference: " + declaringField + "; " + type.getSimpleName() + " is annotated with AbstractClass";
            if (type.isAnnotationPresent(InheritanceMapping.class)) {
                InheritanceMapping annotation = type.getAnnotation(InheritanceMapping.class);
                message += " and its inheritance mapping strategy is " + annotation.strategy();
                if (InheritanceMappingStrategy.TABLE_PER_CLASS.equals(annotation.strategy())) {
                    TLC.getProject().getParser().error(message);
                }
            }
        }
    }

    private static Class<?> getFieldType(Class<?> fieldType) {
        if (fieldType.isInterface()) {
            if (Expression.class.isAssignableFrom(fieldType)) {
                if (EntityExpression.class.isAssignableFrom(fieldType)) {
                    return EntityX.class;
                } else if (BooleanExpression.class.isAssignableFrom(fieldType)) {
                    return BooleanX.class;
                } else if (CharacterExpression.class.isAssignableFrom(fieldType)) {
                    return CharacterX.class;
                } else if (NumericExpression.class.isAssignableFrom(fieldType)) {
                    return NumericX.class;
                } else if (TemporalExpression.class.isAssignableFrom(fieldType)) {
                    return TemporalX.class;
                } else {
                    return VariantX.class;
                }
            }
        }
        return isRestrictedFieldType(fieldType) ? null : fieldType;
    }

    private static boolean isRestrictedFieldType(Class<?> fieldType) {
        int modifiers = fieldType.getModifiers();
        boolean b = fieldType.isPrimitive();
        b |= Modifier.isAbstract(modifiers) || !Modifier.isPublic(modifiers);
        b |= fieldType.isAnnotation();
        b |= fieldType.isAnonymousClass();
        b |= fieldType.isArray();
        b |= fieldType.isEnum();
        b |= fieldType.isLocalClass();
        b |= fieldType.isInterface();
        return b;
    }

    private static CastingField getCastingFieldAnnotation(Field declaringField, Class<?> fieldType) {
        if (Property.class.isAssignableFrom(fieldType)) { // || Parameter.class.isAssignableFrom(fieldType)
            if (declaringField.isAnnotationPresent(CastingField.class)) {
                return declaringField.getAnnotation(CastingField.class);
            }
        }
        return null;
    }

    private static Field getFieldToBeCasted(boolean log, String casting, String toBeCasted, Class<?> declaringClass, Class<?> fieldType) {
        String role = StringUtils.isBlank(casting) ? "casting" : casting;
        String name = StringUtils.trimToEmpty(toBeCasted);
        String annotation = CastingField.class.getSimpleName();
        String remarks;
        if (StringUtils.isBlank(toBeCasted)) {
            remarks = "null field name in " + annotation + " annotation";
            if (log) {
                logFieldErrorMessage(role, name, declaringClass, null, remarks);
            }
        } else if (toBeCasted.equals(casting)) {
            remarks = "same field name in " + annotation + " annotation";
            if (log) {
                logFieldErrorMessage(role, name, declaringClass, null, remarks);
            }
        } else {
            Class<?> top = getFieldToBeCastedTopClass(declaringClass);
            return getField(log, role, name, declaringClass, top, fieldType);
        }
        return null;
    }

    private static Class<?> getFieldToBeCastedTopClass(Class<?> declaringClass) {
        return declaringClass == null ? null
            : Entity.class.isAssignableFrom(declaringClass) ? Entity.class
            : Operation.class.isAssignableFrom(declaringClass) ? Operation.class
            : Artifact.class;
    }

    static Field getField(boolean log, String role, String name, Class<?> type, Class<?> top, Class<?>... validTypes) {
        String string;
        if (StringUtils.isBlank(role)) {
            string = "field role is missing or invalid";
            if (log) {
                logFieldErrorMessage(role, name, type, null, string);
            }
            return null;
        }
        if (StringUtils.isBlank(name)) {
            string = "field name is missing or invalid";
            if (log) {
                logFieldErrorMessage(role, name, type, null, string);
            }
            return null;
        }
        if (type == null) {
            string = "class is missing or invalid";
            if (log) {
                logFieldErrorMessage(role, name, type, null, string);
            }
            return null;
        }
        Field field = getField(name, type, top);
        if (field == null) {
            string = "field " + name + " not in class";
            if (log) {
                logFieldErrorMessage(role, name, type, field, string);
            }
            return null;
        }
        int modifiers = field.getModifiers();
        if (Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers)) {
            string = "field " + name + " has static and/or final modifier";
            if (log) {
                logFieldErrorMessage(role, name, type, field, string);
            }
            return null;
        }
        int length = validTypes == null ? 0 : validTypes.length;
        if (length < 1) {
            return field;
        }
        Class<?> ft = getTrueType(field.getType());
        String[] strings = new String[length];
        int i = 0;
        if (validTypes != null) {
            for (Class<?> vt : validTypes) {
                if (vt.isAssignableFrom(ft)) {
                    return field;
                }
                strings[i++] = vt.getSimpleName();
            }
        }
        string = "type of " + name + " is not " + StringUtils.join(strings, " or ");
        if (log) {
            logFieldErrorMessage(role, name, type, field, string);
        }
        return null;
    }

    private static Field getField(String name, Class<?> type, Class<?> top) {
        if (StringUtils.isNotBlank(name)) {
            if (top.isAssignableFrom(type)) {
                for (Field field : getFields(type, top)) {
                    field.setAccessible(true);
                    if (name.equals(field.getName())) {
                        return field;
                    }
                }
            }
        }
        return null;
    }

    static Property getProperty(Field field, Object declaringObject) {
        return getProperty(field, declaringObject, false);
    }

    static Property getProperty(Field field, Object declaringObject, boolean ignoreExceptions) {
        try {
            Object object = field.get(declaringObject);
            return object instanceof Property ? (Property) object : null;
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            if (ignoreExceptions) {
                return null;
            }
            Throwable cause = ThrowableUtils.getCause(ex);
            String message = ex.equals(cause) ? null : ex.getMessage();
            logger.error(message, cause);
            Project.increaseParserErrorCount();
            return null;
        }
    }

    static Parameter getParameter(Field field, Object declaringObject) {
        return getParameter(field, declaringObject, false);
    }

    static Parameter getParameter(Field field, Object declaringObject, boolean ignoreExceptions) {
        try {
            Object object = field.get(declaringObject);
            return object instanceof Parameter ? (Parameter) object : null;
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            if (ignoreExceptions) {
                return null;
            }
            Throwable cause = ThrowableUtils.getCause(ex);
            String message = ex.equals(cause) ? null : ex.getMessage();
            logger.error(message, cause);
            Project.increaseParserErrorCount();
            return null;
        }
    }

    static View getView(Field field, Object declaringObject) {
        Object object = null;
        try {
            object = field.get(declaringObject);
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            Throwable cause = ThrowableUtils.getCause(ex);
            String message = ex.equals(cause) ? null : ex.getMessage();
            logger.error(message, cause);
            Project.increaseParserErrorCount();
        }
        return object instanceof View ? (View) object : null;
    }

    static boolean checkKeyPropertyFieldAnnotation(boolean log, Field field, KeyProperty keyProperty) {
        Class<?>[] validTypes = keyPropertyValidTypes.get(keyProperty);
        return checkKeyPropertyFieldAnnotation(log, field, keyProperty, validTypes);
    }

    static boolean checkKeyPropertyFieldAnnotation(boolean log, Field field, KeyProperty keyProperty, Class<?>[] validTypes) {
        Class<? extends Annotation> annotation = keyPropertyAnnotation.get(keyProperty);
        return checkFieldAnnotation(log, field, annotation, validTypes);
    }

    static boolean checkFieldAnnotation(boolean log, Field field, Class<? extends Annotation> annotation, Class<?>[] validTypes) {
        String name = field.getName();
        Class<?> type = field.getDeclaringClass();
        String string;
        int modifiers = field.getModifiers();
        if (Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers)) {
            string = "field " + name + " has static and/or final modifier";
            if (log) {
                logFieldAnnotationErrorMessage(name, type, annotation, string);
            }
            return false;
        }
        int length = validTypes == null ? 0 : validTypes.length;
        if (length < 1) {
            return true;
        }
        Class<?> ft = getTrueType(field.getType());
        String[] strings = new String[length];
        int i = 0;
        if (validTypes != null) {
            for (Class<?> vt : validTypes) {
                if (vt.isAssignableFrom(ft)) {
                    return true;
                }
                strings[i++] = vt.getSimpleName();
            }
        }
        string = "type of " + name + " is not " + StringUtils.join(strings, " or ");
        if (log) {
            logFieldAnnotationErrorMessage(name, type, annotation, string);
        }
        return false;
    }

    private static void logFieldErrorMessage(String role, String name, Class<?> type, Field field, String string) {
        String pattern;
        String message;
        if (StringUtils.equals(role, name)) {
            pattern = "failed to get field {0} at {2}";
        } else {
            pattern = "failed to get {0} field at {2}";
        }
        if (StringUtils.isNotBlank(string)) {
            pattern += "; {4}";
        }
        message = MessageFormat.format(pattern, role, name, type, field, string);
        TLC.getProject().getParser().error(message);
        if (StringUtils.isNotBlank(name) && field != null) {
            pattern = "field {1} = {3}";
            message = TAB + MessageFormat.format(pattern, role, name, type, field, string);
            logger.error(message);
        }
    }

    static void logDuplicateAnnotation(Field field, Class<? extends Annotation> annotation, Field previous) {
        String name = field.getName();
        Class<?> type = field.getDeclaringClass();
        String string = previous + " has the same annotation";
        logFieldAnnotationErrorMessage(name, type, annotation, string);
    }

    static void logFieldAnnotationErrorMessage(Field field, Class<?> annotation, String string) {
        String name = field.getName();
        Class<?> type = field.getDeclaringClass();
        logFieldAnnotationErrorMessage(name, type, annotation, string);
    }

    private static void logFieldAnnotationErrorMessage(String name, Class<?> type, Class<?> annotation, String string) {
        String pattern;
        String message;
        pattern = "failed to annotate field {0} at {1} with {2}";
        if (StringUtils.isNotBlank(string)) {
            pattern += "; {3}";
        }
        message = MessageFormat.format(pattern, name, type, annotation.getSimpleName(), string);
        TLC.getProject().getParser().error(message);
    }

    private static Class<?> getTrueType(Class<?> type) {
        if (Entity.class.isAssignableFrom(type)) {
//          Project currentProject = TLC.getProject();
//          if (currentProject == null) {
//              throw new UnexpectedRuntimeException("null current project");
//          }
            Class<?> trueType = TLC.getProject().getTrueType(type);
            if (trueType == null) {
                throw new UnexpectedRuntimeException("null entity reference");
            }
            return trueType;
        }
        return type;
    }

    static Field getEntityField(String name, Class<?> type) {
        Class<?> top = Entity.class;
        return getField(name, type, top);
    }
//
    // <editor-fold defaultstate="collapsed" desc="static Field getOperationField(String name, Class<?> type)">
//  static Field getOperationField(String name, Class<?> type) {
//      Class<?> top = Operation.class;
//      return getField(name, type, top);
//  }
    // </editor-fold>

    static Class<?> getConstructorParameterType(Class<?> wrapper, Class<?> wrappable) {
        Class<?> parameterType = null;
        Constructor<?>[] constructors = wrapper.getConstructors();
        for (Constructor<?> constructor : constructors) {
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            if (parameterTypes.length == 1 && parameterTypes[0].isAssignableFrom(wrappable)) {
                if (parameterType == null || parameterType.isAssignableFrom(parameterTypes[0])) {
                    parameterType = parameterTypes[0];
                }
            }
        }
        return parameterType;
    }

    static boolean declare(Artifact artifact, Artifact declaringArtifact, Field declaringField) {
        if (artifact == null || declaringArtifact == null || declaringField == null) {
            return false;
        }
        if (artifact.isNotDeclared()) {
            if (artifact instanceof AbstractArtifact a) {
                a.setDeclared(declaringField.getName(), declaringArtifact, declaringField);
                if (artifact instanceof AnnotatableArtifact annotatableArtifact) { // AbstractDataArtifact (AbstractEntity or Primitive), EntityCollection
                    annotatableArtifact.annotate(); // 2020/07/28 - do not annotate while instantiating
                }
                if (artifact instanceof AbstractEntity e) {
                    e.initializeInheritanceFields(); // 2020/07/28 - do not initialize inheritance fields while instantiating
                }
                return true;
            }
        }
        return false;
    }

    static boolean postConstruct(Entity entity) {
        if (entity instanceof AbstractEntity e) {
            e.annotate(); // 2020/07/28 - do not annotate while instantiating
            e.initializeInheritanceFields(); // 2020/07/28 - do not initialize inheritance fields while instantiating
            return true;
        }
        return false;
    }

    static boolean setReferenceIndex(Entity entity, int referenceIndex) {
        if (entity instanceof AbstractEntity e) {
            e.setReferenceIndex(referenceIndex);
            return true;
        }
        return false;
    }

    static boolean annotateCastingField(Field declaringField, DataArtifact artifact) {
        if (artifact instanceof AbstractDataArtifact && declaringField.isAnnotationPresent(CastingField.class)) {
            AbstractDataArtifact d = (AbstractDataArtifact) artifact;
            d.annotate(declaringField);
            return true;
        }
        return false;
    }

    static int round(Class<?> clazz, Artifact artifact) {
        int hits = 0;
        if (clazz != null) {
            if (artifact != null) {
                if (clazz.isAssignableFrom(getNamedClass(artifact))) {
                    hits++;
                }
                Artifact declaringArtifact = artifact.getDeclaringArtifact();
                if (declaringArtifact != null) {
                    hits += round(clazz, declaringArtifact);
                }
            }
        }
        return hits;
    }

    static Object newInstance(String className) {
        Class<?> clazz = forName(className);
        if (clazz == null) {
            return null;
        }
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
            return null;
        }
    }

    static Class<?> forName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException ex) {
            return null;
        }
    }
//
    // <editor-fold defaultstate="collapsed" desc="static Object invoke(Object object, String methodName)">
//  static Object invoke(Object object, String methodName) {
//      if (object == null || StringUtils.isBlank(methodName)) {
//          return null;
//      }
//      String errmsg = "failed to invoke method " + methodName + " at " + object;
//      try {
//          Method method = object.getClass().getMethod(methodName, new Class<?>[]{});
//          method.setAccessible(true);
//          logger.trace(object.getClass().getName() + '.' + methodName + ':' + method.toString());
//          return method.invoke(object);
//      } catch (NoSuchMethodException ex) {
//          throw new InvocationRuntimeException(errmsg, ex);
//      } catch (SecurityException ex) {
//          throw new InvocationRuntimeException(errmsg, ex);
//      } catch (IllegalAccessException ex) {
//          throw new InvocationRuntimeException(errmsg, ex);
//      } catch (IllegalArgumentException ex) {
//          throw new InvocationRuntimeException(errmsg, ex);
//      } catch (InvocationTargetException ex) {
//          throw new InvocationRuntimeException(errmsg, ex);
//      }
//  }
    // </editor-fold>
//
    // <editor-fold defaultstate="collapsed" desc="static void printStackTrace()">
//  static void printStackTrace() {
//      StackTraceElement[] stack = Thread.currentThread().getStackTrace();
//      for (StackTraceElement element : stack) {
//          logger.trace(element.getClassName() + "." + element.getMethodName() + ":" + element.getLineNumber());
//      }
//  }
    // </editor-fold>
//
    // <editor-fold defaultstate="collapsed" desc="static int getLineNumber(String methodName, boolean first)">
//  static int getLineNumber(String methodName, boolean first) {
//      int lineNumber = 0;
//      String name;
//      StackTraceElement[] stack = Thread.currentThread().getStackTrace();
//      for (StackTraceElement element : stack) {
//          name = element.getClassName() + '.' + element.getMethodName();
//          if (methodName.equals(name)) {
//              lineNumber = element.getLineNumber();
//              if (first) {
//                  break;
//              }
//          }
//      }
//      return lineNumber;
//  }
    // </editor-fold>

}
