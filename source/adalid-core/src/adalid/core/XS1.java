/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core;

import adalid.commons.util.ThrowableUtils;
import adalid.core.annotations.AbstractClass;
import adalid.core.annotations.BusinessKey;
import adalid.core.annotations.CastingField;
import adalid.core.annotations.CharacterKey;
import adalid.core.annotations.DescriptionProperty;
import adalid.core.annotations.DiscriminatorColumn;
import adalid.core.annotations.InactiveIndicator;
import adalid.core.annotations.InheritanceMapping;
import adalid.core.annotations.NameProperty;
import adalid.core.annotations.NumericKey;
import adalid.core.annotations.OwnerProperty;
import adalid.core.annotations.ParentProperty;
import adalid.core.annotations.PrimaryKey;
import adalid.core.annotations.SegmentProperty;
import adalid.core.annotations.UniqueKey;
import adalid.core.annotations.UrlProperty;
import adalid.core.annotations.VersionProperty;
import adalid.core.enums.InheritanceMappingStrategy;
import adalid.core.enums.KeyProperty;
import adalid.core.exceptions.IllegalAccessRuntimeException;
import adalid.core.exceptions.InstantiationRuntimeException;
import adalid.core.exceptions.UnexpectedRuntimeException;
import adalid.core.expressions.BooleanX;
import adalid.core.expressions.CharacterX;
import adalid.core.expressions.EntityX;
import adalid.core.expressions.NumericX;
import adalid.core.expressions.TemporalX;
import adalid.core.expressions.VariantX;
import adalid.core.interfaces.Artifact;
import adalid.core.interfaces.BooleanExpression;
import adalid.core.interfaces.CharacterExpression;
import adalid.core.interfaces.DataArtifact;
import adalid.core.interfaces.Entity;
import adalid.core.interfaces.EntityExpression;
import adalid.core.interfaces.EntityReference;
import adalid.core.interfaces.Expression;
import adalid.core.interfaces.NamedValue;
import adalid.core.interfaces.NumericExpression;
import adalid.core.interfaces.Parameter;
import adalid.core.interfaces.PersistentEnumerationEntityReference;
import adalid.core.interfaces.Property;
import adalid.core.interfaces.TemporalExpression;
import adalid.core.primitives.BooleanPrimitive;
import adalid.core.primitives.CharacterPrimitive;
import adalid.core.primitives.NumericPrimitive;
import adalid.core.primitives.TemporalPrimitive;
import adalid.core.properties.BooleanProperty;
import adalid.core.properties.CharacterProperty;
import adalid.core.properties.IntegerProperty;
import adalid.core.properties.LongProperty;
import adalid.core.properties.StringProperty;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
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

    private static final String THIS_CLASS = XS1.class.getName();

    private static final String ROOT_PACKAGE = StringUtils.substringBefore(THIS_CLASS, XS1.class.getSimpleName());

    private static final Map<KeyProperty, Class<? extends Annotation>> keyPropertyAnnotation;

    private static final Map<KeyProperty, Class<?>[]> keyPropertyValidTypes;

    static {
        keyPropertyAnnotation = new LinkedHashMap<>();
        keyPropertyAnnotation.put(KeyProperty.PRIMARY_KEY, PrimaryKey.class);
        keyPropertyAnnotation.put(KeyProperty.VERSION, VersionProperty.class);
        keyPropertyAnnotation.put(KeyProperty.NUMERIC_KEY, NumericKey.class);
        keyPropertyAnnotation.put(KeyProperty.CHARACTER_KEY, CharacterKey.class);
        keyPropertyAnnotation.put(KeyProperty.NAME, NameProperty.class);
        keyPropertyAnnotation.put(KeyProperty.DESCRIPTION, DescriptionProperty.class);
        keyPropertyAnnotation.put(KeyProperty.INACTIVE_INDICATOR, InactiveIndicator.class);
        keyPropertyAnnotation.put(KeyProperty.URL, UrlProperty.class);
        keyPropertyAnnotation.put(KeyProperty.PARENT, ParentProperty.class);
        keyPropertyAnnotation.put(KeyProperty.OWNER, OwnerProperty.class);
        keyPropertyAnnotation.put(KeyProperty.SEGMENT, SegmentProperty.class);
        keyPropertyAnnotation.put(KeyProperty.UNIQUE_KEY, UniqueKey.class);
        keyPropertyAnnotation.put(KeyProperty.BUSINESS_KEY, BusinessKey.class);
        keyPropertyAnnotation.put(KeyProperty.DISCRIMINATOR, DiscriminatorColumn.class);
        /**/
        keyPropertyValidTypes = new LinkedHashMap<>();
        keyPropertyValidTypes.put(KeyProperty.PRIMARY_KEY,
            new Class<?>[]{LongProperty.class, IntegerProperty.class});
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
        keyPropertyValidTypes.put(KeyProperty.INACTIVE_INDICATOR,
            new Class<?>[]{BooleanProperty.class});
        keyPropertyValidTypes.put(KeyProperty.URL,
            new Class<?>[]{StringProperty.class});
        keyPropertyValidTypes.put(KeyProperty.PARENT,
            new Class<?>[]{EntityReference.class});
        keyPropertyValidTypes.put(KeyProperty.OWNER,
            new Class<?>[]{EntityReference.class});
        keyPropertyValidTypes.put(KeyProperty.SEGMENT,
            new Class<?>[]{EntityReference.class});
        keyPropertyValidTypes.put(KeyProperty.UNIQUE_KEY,
            new Class<?>[]{BooleanPrimitive.class, CharacterPrimitive.class, NumericPrimitive.class, TemporalPrimitive.class, EntityReference.class});
        keyPropertyValidTypes.put(KeyProperty.BUSINESS_KEY,
            new Class<?>[]{StringProperty.class, IntegerProperty.class});
        keyPropertyValidTypes.put(KeyProperty.DISCRIMINATOR,
            new Class<?>[]{CharacterProperty.class, StringProperty.class, IntegerProperty.class, PersistentEnumerationEntityReference.class});
    }
    // </editor-fold>

    static boolean checkAccess() {
        String method;
        String caller = null;
        final StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        for (StackTraceElement element : stack) {
            method = element.getClassName() + "." + element.getMethodName();
            if (caller == null) {
                if (method.startsWith(ROOT_PACKAGE) && !method.startsWith(THIS_CLASS)) {
                    caller = method;
                }
            } else if (method.startsWith(ROOT_PACKAGE)) {
                break;
            } else {
                String message = "illegal invocation of \"" + StringUtils.substringAfterLast(caller, ".") + "\"";
                message += " at " + method + "(" + element.getFileName() + ":" + element.getLineNumber() + ")";
                throw new IllegalAccessRuntimeException(message);
            }
        }
        return caller != null;
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
//                  for (Type type : actualTypeArguments) {
//                      if (type instanceof Class<?>) {
//                          returnClass = (Class<?>) type;
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
//      ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
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

    static Collection<Field> getFields(Class<?> clazz, Class<?> top) throws SecurityException {
        ArrayList<Field> fields = new ArrayList<>();
        if (clazz == null || top == null) {
            return fields;
        }
        int modifiers;
        boolean restricted;
        Class<?> superClazz = clazz.getSuperclass();
        if (superClazz == null) {
            logger.trace(clazz.getName());
        } else {
            logger.trace(clazz.getName() + " extends " + superClazz.getName());
            if (top.isAssignableFrom(superClazz)) {
//              fields.addAll(getFields(superClazz, top));
                Collection<Field> superFields = getFields(superClazz, top);
                for (Field field : superFields) {
                    modifiers = field.getModifiers();
                    restricted = Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers);
                    if (restricted) {
                        continue;
                    }
                    fields.add(field);
                }
            }
        }
        if (top.isAssignableFrom(clazz)) {
            logger.trace("adding fields declared at " + clazz.getName());
//          fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field field : declaredFields) {
                modifiers = field.getModifiers();
                restricted = Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers);
                if (restricted) {
                    continue;
                }
                fields.add(field);
            }
        }
        return getRidOfDupFields(fields);
    }

    private static Collection<Field> getRidOfDupFields(Collection<Field> fields) {
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
        return map.values();
    }

    static Object initialiseField(Object declaringObject, Field declaringField) {
        if (declaringObject == null || declaringField == null) {
            return null;
        }
        String declaringFieldName = declaringField.getName();
        Class<?> declaringFieldType = declaringField.getType();
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
        if (declaringObject instanceof DataArtifact) {
            DataArtifact declaringDataArtifact = (DataArtifact) declaringObject;
            declaringArtifactIsParameter = declaringDataArtifact.isParameter();
        }
        String errmsg = "failed to create a new instance of field " + declaringField + " at " + declaringObject;
        try {
            CastingField castingFieldAnnotation = getCastingFieldAnnotation(declaringField, fieldType);
            if (castingFieldAnnotation != null) {
                String name = castingFieldAnnotation.value();
                Field field = getFieldToBeCasted(true, fieldName, name, declaringClass, fieldType);
                if (field != null) {
                    errmsg = "failed to set casting field " + declaringField + " at " + declaringObject;
                    instance = field.get(declaringObject);
//                  if (instance == null) {
//                      logFieldErrorMessage(fieldName, name, declaringClass, field, "field " + name + " is not allocated");
//                  }
                }
            } else if (memberClass && enclosingClass != null && enclosingClass.isAssignableFrom(declaringClass)) {
                if (Operation.class.isAssignableFrom(fieldType)) {
                    if (declaringArtifact == null) {
                        instance = fieldType.getConstructor(enclosingClass).newInstance(declaringObject);
                    } else if (declaringEntity == null) {
                        TLC.setDeclaringArtifact(declaringArtifact);
                        instance = fieldType.getConstructor(enclosingClass).newInstance(declaringObject);
                        TLC.removeDeclaringArtifact();
                    } else {
                        String key = fieldType.getSimpleName();
                        Map<String, Class<?>> map = declaringEntity.getOperationClassesMap();
                        Class<?> fieldTypeX = map.containsKey(key) ? map.get(key) : fieldType;
                        Class<?> enclosingClassX = fieldTypeX.getEnclosingClass();
                        TLC.setDeclaringArtifact(declaringArtifact);
                        instance = fieldTypeX.getConstructor(enclosingClassX).newInstance(declaringObject);
                        TLC.removeDeclaringArtifact();
                    }
                } else {
                    instance = fieldType.getConstructor(enclosingClass).newInstance(declaringObject);
                }
            } else if (enclosingClass == null) {
                if (Entity.class.isAssignableFrom(fieldType)) {
                    if (declaringArtifact == null) {
                        instance = fieldType.newInstance();
                    } else {
//                      Method method = fieldType.getMethod(GET_INSTANCE, new Class<?>[]{Artifact.class});
//                      instance = method.invoke(null, declaringObject);
                        Class<?> type = getTrueType(fieldType);
                        checkAbstractClassReference(declaringField, type);
                        String path = declaringArtifact.getClassPath();
                        int depth = declaringArtifact.depth() + 1;
                        int round = round(type, declaringArtifact);
                        TLC.getProject().getParser().setMaxDepthReached(depth);
                        TLC.getProject().getParser().setMaxRoundReached(round);
                        FieldAllocationSettings settings = new FieldAllocationSettings(declaringField, declaringObject, depth, round);
                        int maxDepth = settings.getMaxDepth();
                        int maxRound = settings.getMaxRound();
                        String method1 = "allocate(maxDepth={0}, maxRound={1})";
                        String method2 = MessageFormat.format(method1, maxDepth, maxRound);
                        String remark1 = "maxDepth<" + depth;
                        String remark2 = "maxRound<" + round;
                        String pattern;
                        String remarks;
                        if (declaringArtifactIsParameter || declaringArtifact.depth() == 0 || (depth <= maxDepth && round <= maxRound)) {
                            TLC.getProject().getParser().track(depth, round, path, type, fieldName, method2, null);
                            instance = type.getConstructor(Artifact.class, Field.class).newInstance(declaringObject, declaringField);
                            TLC.getProject().getParser().increaseEntityCount();
                        } else {
                            pattern = "{1}, {0} not allocated";
                            if (depth > maxDepth) {
                                remarks = MessageFormat.format(pattern, fieldName, remark1);
                                TLC.getProject().getParser().alert(depth, round, path, type, fieldName, method2, remarks);
                            }
                            if (round > maxRound) {
                                remarks = MessageFormat.format(pattern, fieldName, remark2);
                                TLC.getProject().getParser().alert(depth, round, path, type, fieldName, method2, remarks);
                            }
                        }
                    }
                } else {
                    instance = fieldType.newInstance();
                }
            }
            if (instance instanceof AbstractArtifact) {
                // do not declare here; it will be declared at the corresponding finalise method
                AbstractArtifact abstractArtifact = (AbstractArtifact) instance;
                abstractArtifact.setName(fieldName);
                abstractArtifact.setDeclaringArtifact(declaringArtifact);
                abstractArtifact.setDeclaringField(declaringField);
                if (instance instanceof VariantX) {
                    VariantX variantX = (VariantX) instance;
                    variantX.setDataType(declaringFieldType);
                }
            }
        } catch (IllegalArgumentException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException ex) {
            throw new InstantiationRuntimeException(errmsg, ex);
        }
        return instance;
    }

    private static void checkAbstractClassReference(Field declaringField, Class<?> type) {
        String errmsg = "Abstract Class Reference" + "; field " + declaringField + "; " + type + " is annotated with AbstractClass";
        if (type.isAnnotationPresent(AbstractClass.class)) {
            if (type.isAnnotationPresent(InheritanceMapping.class)) {
                InheritanceMapping annotation = type.getAnnotation(InheritanceMapping.class);
                if (InheritanceMappingStrategy.TABLE_PER_CLASS.equals(annotation.strategy())) {
//                  TODO: check if this should be an error or a just a warning
//                  TLC.getProject().getParser().error(errmsg);
                    TLC.getProject().getParser().warn(errmsg);
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
        if (Property.class.isAssignableFrom(fieldType) || Parameter.class.isAssignableFrom(fieldType)) {
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
        for (Class<?> vt : validTypes) {
            if (vt.isAssignableFrom(ft)) {
                return field;
            }
            strings[i++] = vt.getSimpleName();
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
        Object object = null;
        try {
            object = field.get(declaringObject);
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            Throwable cause = ThrowableUtils.getCause(ex);
            String message = ex.equals(cause) ? null : ex.getMessage();
            logger.error(message, cause);
            TLC.getProject().getParser().increaseErrorCount();
        }
        return object instanceof Property ? (Property) object : null;
    }

    static View getView(Field field, Object declaringObject) {
        Object object = null;
        try {
            object = field.get(declaringObject);
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            Throwable cause = ThrowableUtils.getCause(ex);
            String message = ex.equals(cause) ? null : ex.getMessage();
            logger.error(message, cause);
            TLC.getProject().getParser().increaseErrorCount();
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
        for (Class<?> vt : validTypes) {
            if (vt.isAssignableFrom(ft)) {
                return true;
            }
            strings[i++] = vt.getSimpleName();
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
        if (artifact instanceof AbstractArtifact) {
            AbstractArtifact a = (AbstractArtifact) artifact;
            if (a.isNotDeclared()) {
                a.setDeclared(declaringField.getName(), declaringArtifact, declaringField);
                return true;
            }
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
