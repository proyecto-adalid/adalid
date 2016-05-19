/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core;

import adalid.commons.TLB;
import adalid.commons.interfaces.Wrappable;
import adalid.commons.interfaces.Wrapper;
import adalid.commons.util.KVP;
import adalid.commons.util.ObjUtils;
import adalid.commons.util.StrUtils;
import adalid.commons.util.ThrowableUtils;
import adalid.core.expressions.VariantX;
import adalid.core.interfaces.Artifact;
import adalid.core.interfaces.DataArtifact;
import adalid.core.interfaces.Entity;
import adalid.core.interfaces.Expression;
import adalid.core.interfaces.Parameter;
import adalid.core.interfaces.PersistentEntity;
import adalid.core.interfaces.Property;
import adalid.core.wrappers.ArtifactWrapper;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public abstract class AbstractArtifact implements Artifact, Wrappable {

    /**
     *
     */
    private static final Logger logger = Logger.getLogger(Artifact.class);

    private static final String EOL = "\n";

    // <editor-fold defaultstate="collapsed" desc="field declarations">
    /**
     *
     */
    private boolean _declared;

    /**
     *
     */
    private Boolean _inherited;

    /**
     *
     */
    private String _name;

    /**
     *
     */
    private String _alias;

    /**
     *
     */
    private String _sqlName;

    /**
     *
     */
    private String _defaultLabel;

    /**
     *
     */
    private String _defaultShortLabel;

    /**
     *
     */
    private String _defaultCollectionLabel;

    /**
     *
     */
    private String _defaultCollectionShortLabel;

    /**
     *
     */
    private String _defaultDescription;

    /**
     *
     */
    private String _defaultShortDescription;

    /**
     *
     */
    private String _defaultTooltip;

    /**
     *
     */
    private Artifact _declaringArtifact;

    /**
     *
     */
    private Field _declaringField;

    /**
     *
     */
    private int _declaringFieldIndex;

    /**
     *
     */
    private int _depth;

    /**
     *
     */
    private int _round;

    /**
     *
     */
    private final Map<Class<? extends Annotation>, Field> _annotations = new LinkedHashMap<>();

    /**
     * platform-specific attributes
     */
    private final Map<String, Object> _attributes = new LinkedHashMap<>();
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="field getters and setters">
    /**
     * @return true if the artifact is declared
     */
    @Override
    public boolean isDeclared() {
        return _declared;
    }

    /**
     * @return true if the artifact is not declared
     */
    @Override
    public boolean isNotDeclared() {
        return !_declared;
    }

    /**
     *
     */
    void setDeclared(String name) {
        setDeclared(name, null);
    }

    /**
     *
     */
    void setDeclared(String name, Artifact declaringArtifact) {
        setDeclared(name, declaringArtifact, null);
    }

    /**
     *
     */
    void setDeclared(String name, Artifact declaringArtifact, Field declaringField) {
        setDeclared(name, declaringArtifact, declaringField, 0);
    }

    /**
     *
     */
    void setDeclared(String name, Artifact declaringArtifact, Field declaringField, int declaringFieldIndex) {
        if (_declared) {
            return;
        }
        setName(name);
        setDeclaringArtifact(declaringArtifact);
        setDeclaringField(declaringField);
        setDeclaringFieldIndex(declaringFieldIndex);
        initializeAnnotations();
        annotate(getNamedClass());
        annotate(getDeclaringField());
        _declared = true;
    }

    /**
     * @return true if the artifact is inherited
     */
    @Override
    public boolean isInherited() {
        return _declared && inherited();
    }

    /**
     * @return true if the artifact is not inherited
     */
    @Override
    public boolean isNotInherited() {
        return _declared && !inherited();
    }

    private boolean inherited() {
        if (_inherited == null) {
            Field declaringField = getDeclaringField();
            Artifact declaringArtifact = getDeclaringArtifact();
            Class<?> declaringFieldClass = declaringField.getDeclaringClass();
            Class<?> declaringFieldNamedClass = XS1.getNamedClass(declaringFieldClass);
            Class<?> declaringArtifactNamedClass = XS1.getNamedClass(declaringArtifact);
            String declaringFieldNamedClassSimpleName = declaringFieldNamedClass.getSimpleName();
            String declaringArtifactNamedClassSimpleName = declaringArtifactNamedClass.getSimpleName();
            _inherited = !declaringFieldNamedClassSimpleName.equals(declaringArtifactNamedClassSimpleName);
        }
        return _inherited;
    }

    /**
     * @return the name
     */
    @Override
    public String getName() {
        return _name;
    }

    /**
     * @param name the name to set
     */
    void setName(String name) {
        if (_declared) {
            return;
        }
        _name = name;
        if (_alias == null) {
            _alias = name;
        }
    }

    /**
     * @return the alias
     */
    @Override
    public String getAlias() {
        return _alias;
    }

    /**
     * @param alias the alias to set
     */
    @Override
    public void setAlias(String alias) {
        _alias = alias;
    }

    /**
     * @return the SQL name
     */
    @Override
    public String getSqlName() {
        return _sqlName;
    }

    /**
     * @param sqlName the SQL name to set
     */
    @Override
    public void setSqlName(String sqlName) {
        _sqlName = sqlName;
    }

    /**
     * @return the default label
     */
    @Override
    public String getDefaultLabel() {
        return _defaultLabel;
    }

    /**
     * @param defaultLabel the default label to set
     */
    @Override
    public void setDefaultLabel(String defaultLabel) {
        _defaultLabel = defaultLabel;
        _defaultShortLabel = defaultLabel;
    }

    /**
     * @return the default short label
     */
    @Override
    public String getDefaultShortLabel() {
        return _defaultShortLabel;
    }

    /**
     * @param defaultShortLabel the default short label to set
     */
    @Override
    public void setDefaultShortLabel(String defaultShortLabel) {
        _defaultShortLabel = defaultShortLabel;
    }

    /**
     * @return the default collection label
     */
    @Override
    public String getDefaultCollectionLabel() {
        return _defaultCollectionLabel;
    }

    /**
     * @param defaultCollectionLabel the default collection label to set
     */
    @Override
    public void setDefaultCollectionLabel(String defaultCollectionLabel) {
        _defaultCollectionLabel = defaultCollectionLabel;
        _defaultCollectionShortLabel = defaultCollectionLabel;
    }

    /**
     * @return the default collection short label
     */
    @Override
    public String getDefaultCollectionShortLabel() {
        return _defaultCollectionShortLabel;
    }

    /**
     * @param defaultCollectionShortLabel the default collection short label to set
     */
    @Override
    public void setDefaultCollectionShortLabel(String defaultCollectionShortLabel) {
        _defaultCollectionShortLabel = defaultCollectionShortLabel;
    }

    /**
     * @return the default description
     */
    @Override
    public String getDefaultDescription() {
        return _defaultDescription;
    }

    /**
     * @param defaultDescription the default description to set
     */
    @Override
    public void setDefaultDescription(String defaultDescription) {
        _defaultDescription = defaultDescription;
        _defaultShortDescription = defaultDescription;
    }

    /**
     * @return the default short description
     */
    @Override
    public String getDefaultShortDescription() {
        return _defaultShortDescription;
    }

    /**
     * @param defaultShortDescription the default short description to set
     */
    @Override
    public void setDefaultShortDescription(String defaultShortDescription) {
        _defaultShortDescription = defaultShortDescription;
    }

    /**
     * @return the default tooltip
     */
    @Override
    public String getDefaultTooltip() {
        return _defaultTooltip;
    }

    /**
     * @param defaultTooltip the default tooltip to set
     */
    @Override
    public void setDefaultTooltip(String defaultTooltip) {
        _defaultTooltip = defaultTooltip;
    }

    /**
     * @return the declaring artifact
     */
    @Override
    public Artifact getDeclaringArtifact() {
        return _declaringArtifact;
    }

    String getDeclaringArtifactClassName() {
        return _declaringArtifact == null ? null : _declaringArtifact.getClass().getName();
    }

    /**
     * @param declaringArtifact the declaring artifact to set
     */
    void setDeclaringArtifact(Artifact declaringArtifact) {
        if (_declared) {
            return;
        }
        resetDeclaringArtifact(declaringArtifact);
    }

    void resetDeclaringArtifact(Artifact declaringArtifact) {
        _declaringArtifact = declaringArtifact;
        if (declaringArtifact != null) {
            if (this instanceof Entity) {
                _depth = declaringArtifact.depth() + 1;
                _round = XS1.round(getNamedClass(), declaringArtifact);
            } else {
                _depth = declaringArtifact.depth();
            }
        }
    }

    /**
     * @return the declaring field
     */
    @Override
    public Field getDeclaringField() {
        return _declaringField;
    }

    /**
     * @param declaringField the declaring field to set
     */
    void setDeclaringField(Field declaringField) {
        if (_declared) {
            return;
        }
        resetDeclaringField(declaringField);
    }

    void resetDeclaringField(Field declaringField) {
        _declaringField = declaringField;
    }

    /**
     * @return the declaring field index
     */
    @Override
    public int getDeclaringFieldIndex() {
        return _declaringFieldIndex;
    }

    /**
     * @param declaringFieldIndex the declaring field index to set
     */
    void setDeclaringFieldIndex(int declaringFieldIndex) {
        if (_declared) {
            return;
        }
        _declaringFieldIndex = declaringFieldIndex;
    }

    /**
     * @return the declaring entity if the artifact directly declared by one, null otherwise
     */
    @Override
    public Entity getDeclaringEntity() {
        return _declaringArtifact instanceof Entity ? (Entity) _declaringArtifact : null;
    }

    /**
     * @return the declaring entity if the artifact directly declared by one, null otherwise
     */
    @Override
    public PersistentEntity getDeclaringPersistentEntity() {
        Entity declaringEntity = getDeclaringEntity();
        return declaringEntity instanceof PersistentEntity ? (PersistentEntity) declaringEntity : null;
    }

    /**
     * @return the declaring entity if the artifact directly declared by one, null otherwise
     */
    @Override
    public Entity getDeclaringEntityRoot() {
        Entity declaringEntity = getDeclaringEntity();
        return declaringEntity == null ? null : declaringEntity.getRoot();
    }

    /**
     * @return the declaring entity if the artifact directly declared by one, null otherwise
     */
    @Override
    public PersistentEntity getDeclaringPersistentEntityRoot() {
        Entity declaringEntityRoot = getDeclaringEntityRoot();
        return declaringEntityRoot instanceof PersistentEntity ? (PersistentEntity) declaringEntityRoot : null;
    }

    /**
     * @return the declaring entity if the artifact directly declared by one, null otherwise
     */
    @Override
    public Entity getDeclaringFieldEntityRoot() {
        Class<?> declaringClass = _declaringField == null ? null : _declaringField.getDeclaringClass();
        Entity declaringEntity = declaringClass == null ? null : getDeclaringEntity();
        return declaringEntity == null ? null : declaringEntity.getDeclaringProject().getEntity(declaringClass);
    }

    /**
     * @return the declaring entity if the artifact directly declared by one, null otherwise
     */
    @Override
    public PersistentEntity getDeclaringFieldPersistentEntityRoot() {
        Entity declaringFieldEntityRoot = getDeclaringFieldEntityRoot();
        return declaringFieldEntityRoot instanceof PersistentEntity ? (PersistentEntity) declaringFieldEntityRoot : null;
    }

    /**
     * @return the declaring operation if the artifact directly declared by one, null otherwise
     */
    @Override
    public Operation getDeclaringOperation() {
        return _declaringArtifact instanceof Operation ? (Operation) _declaringArtifact : null;
    }

    /**
     * @return the depth
     */
    @Override
    public int depth() {
        return _depth;
    }

    /**
     * @return the round
     */
    @Override
    public int round() {
        return _round;
    }

    /**
     * @return the annotations map
     */
    Map<Class<? extends Annotation>, Field> getAnnotations() {
        return _annotations;
    }

    @Override
    public void clearAttributes() {
        _attributes.clear();
    }

    @Override
    public void addAttributes() {
    }

    /**
     * @return the attributes map
     */
    public Map<String, Object> getAttributes() {
        return _attributes;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="annotate">
    void initializeAnnotations() {
    }

    void annotate(Class<?> type) {
        if (type == null) {
            return;
        }
        Annotation[] annotations = type.getAnnotations();
        List<Class<? extends Annotation>> valid = getValidTypeAnnotations();
        checkAnnotations(type, annotations, valid);
    }

    void annotate(Field field) {
        if (field == null) {
            return;
        }
        Annotation[] annotations = field.getAnnotations();
        List<Class<? extends Annotation>> valid = getValidFieldAnnotations();
        checkAnnotations(field, annotations, valid);
    }

    private void checkAnnotations(Object object, Annotation[] annotations, List<Class<? extends Annotation>> valid) {
        Project project = TLC.getProject();
//      if (project == null) {
//          return;
//      }
        String pattern = "@{0} is not valid for {1}; annotation ignored";
        String message;
        Class<? extends Annotation> annotationType;
        for (Annotation annotation : annotations) {
            annotationType = annotation.annotationType();
            if (valid.contains(annotationType)) {
                continue;
            }
            message = MessageFormat.format(pattern, annotationType.getSimpleName(), object);
            project.getParser().log(Level.ERROR, message);
            project.getParser().increaseErrorCount();
        }
    }

    protected List<Class<? extends Annotation>> getValidTypeAnnotations() {
        return new ArrayList<>();
    }

    protected List<Class<? extends Annotation>> getValidFieldAnnotations() {
        return new ArrayList<>();
    }

    @Override
    public Field put(Class<? extends Annotation> annotation, Field field) {
        XS1.checkAccess();
        return _annotations.put(annotation, field);
    }
    // </editor-fold>

    public AbstractArtifact() {
        add();
    }

    private void add() {
        Project project = TLC.getProject();
        if (project != null) {
            project.addArtifact(this);
        }
    }

    @Override
    public Object addAttribute(String name, Object value) {
        return value == null ? _attributes.remove(name) : _attributes.put(name, value);
    }

    @Override
    public Object addAttribute(String name, Object... value) {
        return value == null ? _attributes.remove(name) : _attributes.put(name, value);
    }

    @Override
    public Object getAttribute(String name) {
        return _attributes.get(name);
    }

    public Integer getIntegerAttribute(String name) {
        Object attribute = _attributes.get(name);
        return ObjUtils.toInteger(attribute);
    }

    public Integer getIntegerAttribute(String name, Integer min, Integer max) {
        Object attribute = _attributes.get(name);
        Integer integer = ObjUtils.toInteger(attribute);
        return ObjUtils.between(integer, min, max) ? integer : null;
    }

    public String getStringAttribute(String name) {
        return getStringAttribute(name, KVP.EQUALS, KVP.SEPARATOR, KVP.OPEN, KVP.CLOSE);
    }

    public String getStringAttribute(String name, String equals, String separator, String open, String close) {
        return StrUtils.getString(equals, separator, open, close, _attributes.get(name));
    }

    /**
     * @return the class path
     */
    @Override
    public String getClassPath() {
        return classPath(this);
    }

    private String classPath(Artifact a) {
        Artifact da = a.getDeclaringArtifact();
        String str1 = a.getName();
        String str2 = XS1.getNamedClass(a).getSimpleName();
        String str3 = str1 == null || str1.equals(str2) ? str2 : str2 + "[" + str1 + "]";
        return da == null ? str3 : classPath(da) + "." + str3;
    }

    /**
     * @param type
     * @return true if type is present in the class path
     */
    @Override
    public boolean isClassInPath(Class<?> type) {
        return isClassInPath(type, this);
    }

    private boolean isClassInPath(Class<?> clazz, Artifact artifact) {
        if (clazz != null) {
            if (artifact != null) {
                if (clazz.isAssignableFrom(XS1.getNamedClass(artifact))) {
                    return true;
                }
                Artifact declaringArtifact = artifact.getDeclaringArtifact();
                if (declaringArtifact != null) {
                    return isClassInPath(clazz, declaringArtifact);
                }
            }
        }
        return false;
    }

    /**
     * @return the path
     */
    @Override
    @SuppressWarnings("unchecked") // unchecked conversion
    public List<Artifact> getPathList() {
        List list = new ArrayList<>();
        addToPathList(list, this);
        return list;
    }

    private void addToPathList(List<Artifact> list, Artifact artifact) {
        Artifact declaringArtifact = artifact.getDeclaringArtifact();
        if (declaringArtifact != null) {
            addToPathList(list, declaringArtifact);
        }
        list.add(artifact);
    }

    /**
     * @return the path string
     */
    @Override
    public String getPathString() {
        return path(this);
    }

    private String path(Artifact a) {
        String p;
        if (a.getDeclaringField() == null || a.getDeclaringArtifact() == null) {
//          p = "_" + a.getAlias();
            p = XS1.getNamedClass(a).getSimpleName() + "." + "this";
        } else {
            p = path(a.getDeclaringArtifact()) + "." + a.getDeclaringField().getName();
            if (a.getDeclaringField().getType().isArray()) {
                p += "[" + a.getDeclaringFieldIndex() + "]";
            }
            p = StringUtils.removeStart(p, ".");
        }
        return p;
    }

    /**
     * @return the full name
     */
    @Override
    public String getFullName() {
        return StringUtils.remove(getPathString(), "." + "this");
    }

    /**
     * @return the partial name
     */
    @Override
    public String getPartialName() {
        return StringUtils.substringAfter(getPathString(), "." + "this" + ".");
    }

    protected String getValueString(Object value) {
        if (value == null) {
            return null;
        } else if (value instanceof Artifact) {
            Artifact artifact = (Artifact) value;
            return getValueString(value, artifact.getPathString());
        } else {
//          return getValueString(value, value);
            return value.toString();
        }
    }

    protected String getValueString(Object object, Object value) {
        return XS1.getNamedClass(object).getSimpleName() + "(" + value.toString() + ")";
    }

    /**
     * @return the named class
     */
    Class<?> getNamedClass() {
        return XS1.getNamedClass(this);
    }

    /**
     * @return true if is a Expression; otherwise false
     */
    @Override
    public boolean isExpression() {
        return this instanceof Expression;
    }

    protected void verifyExpression(Expression expression) {
        if (expression instanceof VariantX && depth() == 0) {
            Artifact artifact = (Artifact) expression;
            String expname = StringUtils.isBlank(expression.getName()) ? "" : expression.getName() + " ";
            String message = "invalid expression " + expname + "at " + getFullName();
            message += "; expression " + artifact.getFullName() + " is not properly defined";
            logger.error(message);
            TLC.getProject().getParser().increaseErrorCount();
        } else {
            verifyExpression(expression, this);
        }
    }

    private void verifyExpression(Expression expression, Artifact container) {
        if (expression != null && container != null) {
            Artifact artifact;
            List<Artifact> pathList;
            Expression operandExpression;
            String fullName;
            String containerFullName = container.getFullName();
            String expname = StringUtils.isBlank(expression.getName()) ? "" : expression.getName() + " ";
            String message = "invalid expression " + expname + "at " + containerFullName;
            Object[] operands = expression.getOperands();
            if (operands != null && operands.length > 0) {
                for (Object operand : operands) {
                    if (operand instanceof DataArtifact) {
                        artifact = (Artifact) operand;
                        pathList = artifact.getPathList();
                        if (pathList.contains(container)) {
                            continue;
                        }
                        fullName = artifact.getFullName();
                        message += "; operand " + fullName + " is out of scope";
                        logger.error(message);
                        TLC.getProject().getParser().increaseErrorCount();
                    } else if (operand instanceof VariantX) {
                        artifact = (Artifact) operand;
                        fullName = artifact.getFullName();
                        message += "; operand " + fullName + " is out of scope";
                        logger.error(message);
                        TLC.getProject().getParser().increaseErrorCount();
                    } else if (operand instanceof Expression) {
                        operandExpression = (Expression) operand;
                        verifyExpression(operandExpression, container);
                    }
                }
            }
        }
    }

//  protected void verifyNames(Class<?> top) {
//      verifyNames(top, Artifact.class);
//  }
//
    protected void verifyNames(Class<?> top, Class<?> clazz) {
        String message;
        String fieldName, artifactName;
        Object object;
        AbstractArtifact artifact;
        Class<?> fieldType;
        Class<?> dac = getClass();
        for (Field field : XS1.getFields(dac, top)) {
            field.setAccessible(true);
            fieldName = field.getName();
            fieldType = field.getType();
            if (isAssignableFrom(clazz, fieldType) && isNotRestricted(field)) {
                try {
                    object = field.get(this);
                    if (object instanceof AbstractArtifact) {
                        artifact = (AbstractArtifact) object;
                        artifactName = artifact.getName();
                        if (artifactName == null) {
                            artifact.setName(fieldName);
                        }
                    }
                } catch (IllegalArgumentException | IllegalAccessException ex) {
                    message = "failed to get field \"" + field + "\" at " + this;
                    logger.error(message, ThrowableUtils.getCause(ex));
                    TLC.getProject().getParser().increaseErrorCount();
                }
            }
        }
    }

    private boolean isAssignableFrom(Class<?> clazz, Class<?> type) {
        if (Expression.class.isAssignableFrom(type)) {
            if (Parameter.class.isAssignableFrom(type) || Property.class.isAssignableFrom(type)) {
                return false;
            }
        }
        return clazz.isAssignableFrom(type);
    }

    private boolean isNotRestricted(Field field) {
        int modifiers = field.getModifiers();
        return !(Modifier.isPrivate(modifiers) || Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers));
    }

    /**
     * the wrapper
     */
    private Wrapper _wrapper;

    /**
     * @return the wrapper
     */
    @Override
    public Wrapper getWrapper() {
        if (_wrapper == null) {
            Class<?> thisClass = getClass();
            Class<? extends Wrapper> wrapperClass = getWrapperClass(thisClass);
            if (wrapperClass != null) {
                Class<?> parameterType = XS1.getConstructorParameterType(wrapperClass, thisClass);
                if (parameterType != null) {
                    String pattern = "failed to wrap {0} using {1}({2})";
                    String message = MessageFormat.format(pattern, thisClass, wrapperClass, parameterType);
                    try {
                        _wrapper = wrapperClass.getConstructor(parameterType).newInstance(this);
                    } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                        throw new RuntimeException(message, ex);
                    }
                } else {
                    String pattern = "{1} is not a valid wrapper for {0}";
                    String message = MessageFormat.format(pattern, thisClass, wrapperClass);
                    throw new RuntimeException(message);
                }
            }
        }
        return _wrapper;
    }

    @SuppressWarnings("unchecked") // unchecked cast
    private Class<? extends Wrapper> getWrapperClass(Class<?> clazz) {
        if (clazz != null && Wrappable.class.isAssignableFrom(clazz)) {
            Class<? extends Wrappable> wrappableClass = (Class<? extends Wrappable>) clazz; // unchecked cast
            Class<? extends Wrapper> wrapperClass = TLB.getWrapperClass(wrappableClass);
            return wrapperClass != null ? wrapperClass : getWrapperClass(clazz.getSuperclass());
        }
        return getDefaultWrapperClass();
    }

    /**
     * @return the default wrapper class
     */
    @Override
    public Class<? extends ArtifactWrapper> getDefaultWrapperClass() {
        return ArtifactWrapper.class;
    }

    // <editor-fold defaultstate="collapsed" desc="toString">
    @Override
    public String toString() {
        String str1 = _name;
        String str2 = getNamedClass().getSimpleName();
        String str3 = str1 == null || str1.equals(str2) ? "" : "[" + str1 + "]";
//      return str2 + str3 + "@" + Integer.toHexString(hashCode());
        return str2 + str3;
    }

    @Override
    public String toString(int n) {
        return toString(n, null);
    }

    @Override
    public String toString(int n, String key) {
        return toString(n, key, Project.isVerbose());
    }

    @Override
    public String toString(int n, String key, boolean verbose) {
        return toString(n, key, verbose, verbose, verbose);
    }

    @Override
    public String toString(int n, String key, boolean verbose, boolean fields, boolean maps) {
        String r4n = StringUtils.repeat(" ", 4 * n);
        String tab = verbose ? StringUtils.repeat(" ", 4) : "";
        String fee = verbose ? StringUtils.repeat(tab, n) : "";
//      String faa = " = ";
        String foo = verbose ? EOL : "";
        String eol = verbose ? EOL : ", ";
        String string = EOL;
        String c = classToString(n, key, verbose);
        String f = fieldsToString(n, key, verbose, fields, maps);
        String m = mapsToString(n, key, verbose, fields, maps);
        string += c;
        string += " " + "{" + " " + this + eol;
        string += f;
        string += m;
        if (!verbose) {
            string = StringUtils.removeEnd(string, eol);
            if (c.contains(EOL) || f.contains(EOL) || m.contains(EOL)) {
                fee = r4n;
            }
        }
        string += fee + "}" + EOL;
        return string.replaceAll(EOL + EOL, EOL);
    }

    protected String classToString(int n, String key, boolean verbose) {
        String tab = StringUtils.repeat(" ", 4);
        String fee = StringUtils.repeat(tab, n);
//      String faa = " = ";
//      String foo = EOL;
        String string = "";
        String s1 = getDeclaringField() == null ? "" : getDeclaringField().getType().getSimpleName();
        String s2 = getNamedClass().getSimpleName();
        String s3 = StringUtils.isBlank(s1) || s1.equals(s2) ? s2 : StringUtils.removeEnd(s1, "[]") + " " + "(" + s2 + ")";
        String s4 = StringUtils.isBlank(key) ? "" : " " + key;
//      String s5 = "@" + Integer.toHexString(hashCode());
//      string += fee + s3 + s4 + s5;
        string += fee + s3 + s4;
        return string;
    }

    protected String fieldsToString(int n, String key, boolean verbose, boolean fields, boolean maps) {
        String tab = verbose ? StringUtils.repeat(" ", 4) : "";
        String fee = verbose ? StringUtils.repeat(tab, n) : "";
        String faa = " = ";
        String foo = verbose ? EOL : ", ";
        String string = "";
        if (fields || verbose) {
            if (verbose) {
                if (_declaringArtifact != null) {
                    string += fee + tab + "declaringArtifact" + faa + _declaringArtifact + foo;
                    if (_declaringField != null) {
                        string += fee + tab + "declaringField" + faa + _declaringField + foo;
                        if (_declaringField.getType().isArray()) {
                            string += fee + tab + "declaringFieldIndex" + faa + _declaringFieldIndex + foo;
                        }
                    }
                    string += fee + tab + "path" + faa + getPathString() + foo;
                }
                if (this instanceof Entity) {
                    string += fee + tab + "depth" + faa + _depth + foo;
                    string += fee + tab + "round" + faa + _round + foo;
                }
                if (_name != null) {
                    string += fee + tab + "name" + faa + _name + foo;
                }
                if (_alias != null) {
                    string += fee + tab + "alias" + faa + _alias + foo;
                }
//              string += fee + tab + "defaultLabel" + faa + _defaultLabel + foo;
//              string += fee + tab + "defaultShortLabel" + faa + _defaultShortLabel + foo;
//              string += fee + tab + "defaultDescription" + faa + _defaultDescription + foo;
//              string += fee + tab + "defaultShortDescription" + faa + _defaultShortDescription + foo;
//              string += fee + tab + "defaultTooltip" + faa + _defaultTooltip + foo;
            } else {
                string += fee + tab + "path" + faa + getPathString() + foo;
            }

        }
        return string;
    }

    protected String mapsToString(int n, String key, boolean verbose, boolean fields, boolean maps) {
        return "";
    }
    // </editor-fold>

}
