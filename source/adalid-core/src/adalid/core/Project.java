/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package adalid.core;

import adalid.commons.bundles.Bundle;
import adalid.commons.enums.LoggingLevel;
import adalid.commons.properties.BootstrappingFile;
import adalid.commons.util.ColUtils;
import adalid.commons.util.ThrowableUtils;
import adalid.commons.velocity.Writer;
import adalid.core.annotations.AddAttributesMethod;
import adalid.core.annotations.ProjectModule;
import adalid.core.comparators.ByMethodSequence;
import adalid.core.enums.DisplayFormat;
import adalid.core.enums.DisplayMode;
import adalid.core.exceptions.InstantiationRuntimeException;
import adalid.core.interfaces.Artifact;
import adalid.core.interfaces.Entity;
import adalid.core.interfaces.Expression;
import adalid.core.interfaces.PersistentEntity;
import adalid.core.wrappers.PersistentEntityWrapper;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * @author Jorge Campins
 */
public abstract class Project extends AbstractArtifact implements Comparable<Project> {

    // <editor-fold defaultstate="collapsed" desc="static fields">
    private final Logger logger = Logger.getLogger(Project.class);

    private static final String EOL = "\n";

    private static final String TAB = "\t";

    private static int _defaultMaxDepth = 1;

    private static int _defaultMaxRound = 0;

    private static boolean _verbose = false;

    private static Level _alertLevel = Level.WARN;

    private static Level _detailLevel = Level.OFF;

    private static Level _trackingLevel = Level.OFF;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="static fields' public getters and setters">
    public static void setBootstrappingFileName(String name) {
        BootstrappingFile.setName(name);
    }

    /**
     * @return the locale
     */
    public static Locale getLocale() {
        return Bundle.getLocale();
    }

    /**
     * @param locale the locale to set
     */
    public static void setLocale(Locale locale) {
        Bundle.setLocale(locale);
    }

    /**
     * @return the default max depth
     */
    public static int getDefaultMaxDepth() {
        return _defaultMaxDepth;
    }

    /**
     * @param depth the default max depth to set
     */
    public static void setDefaultMaxDepth(int depth) {
        _defaultMaxDepth = depth < 1 ? 1 : depth;
    }

    /**
     * @return the default max round
     */
    public static int getDefaultMaxRound() {
        return _defaultMaxRound;
    }

    /**
     * @param round the default max round to set
     */
    public static void setDefaultMaxRound(int round) {
        _defaultMaxRound = round < 0 ? 0 : round;
    }

    /**
     * @return the verbose
     */
    public static boolean isVerbose() {
        return _verbose;
    }

    /**
     * @param verbose the verbose indicator to set
     */
    public static void setVerbose(boolean verbose) {
        _verbose = verbose;
    }

    /**
     * @return the alert messages logging level
     */
    public static Level getAlertLevel() {
        return _alertLevel;
    }

    /**
     * @param level the alert messages logging level to set; WARN will be used if level is null; ERROR and FATAL are downgraded to WARN; OFF disables
     * alert messages logging
     */
    public static void setAlertLevel(Level level) {
        _alertLevel = check(level, Level.WARN, Level.WARN);
    }

    /**
     * @return the detail messages logging level
     */
    public static Level getDetailLevel() {
        return _detailLevel;
    }

    /**
     * @param level the detail messages logging level to set; TRACE will be used if level is null; WARN, ERROR and FATAL are downgraded to INFO; OFF
     * disables detail messages logging
     */
    public static void setDetailLevel(Level level) {
        _detailLevel = check(level, Level.OFF, Level.INFO);
    }

    /**
     * @return the tracking messages logging level
     */
    public static Level getTrackingLevel() {
        return _trackingLevel;
    }

    /**
     * @param level the tracking messages logging level to set; TRACE will be used if level is null; WARN, ERROR and FATAL are downgraded to INFO; OFF
     * disables tracking messages logging
     */
    public static void setTrackingLevel(Level level) {
        _trackingLevel = check(level, Level.OFF, Level.INFO);
    }

    /**
     * @return the alert messages logging logginglevel
     */
    public static LoggingLevel getAlertLoggingLevel() {
        return LoggingLevel.getLoggingLevel(_alertLevel);
    }

    /**
     * @param logginglevel the alert messages logging logginglevel to set; WARN will be used if logginglevel is null; ERROR and FATAL are downgraded
     * to WARN; OFF disables alert messages logging
     */
    public static void setAlertLoggingLevel(LoggingLevel logginglevel) {
        setAlertLevel(logginglevel.getLevel());
    }

    /**
     * @return the detail messages logging logginglevel
     */
    public static LoggingLevel getDetailLoggingLevel() {
        return LoggingLevel.getLoggingLevel(_detailLevel);
    }

    /**
     * @param logginglevel the detail messages logging logginglevel to set; TRACE will be used if logginglevel is null; WARN, ERROR and FATAL are
     * downgraded to INFO; OFF disables detail messages logging
     */
    public static void setDetailLoggingLevel(LoggingLevel logginglevel) {
        setDetailLevel(logginglevel.getLevel());
    }

    /**
     * @return the tracking messages logging logginglevel
     */
    public static LoggingLevel getTrackingLoggingLevel() {
        return LoggingLevel.getLoggingLevel(_trackingLevel);
    }

    /**
     * @param logginglevel the tracking messages logging logginglevel to set; TRACE will be used if logginglevel is null; WARN, ERROR and FATAL are
     * downgraded to INFO; OFF disables tracking messages logging
     */
    public static void setTrackingLoggingLevel(LoggingLevel logginglevel) {
        setTrackingLevel(logginglevel.getLevel());
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="static fields' private getters and setters">
    private static Level check(Level level, Level defaultLevel, Level maxLevel) {
        Level coalesced = coalesce(level, defaultLevel);
        return coalesced.equals(Level.OFF) ? coalesced : minimum(coalesced, maxLevel);
    }
//
//  private static Level maximum(Level level1, Level level2) {
//      Level l1 = coalesce(level1, Level.ALL);
//      Level l2 = coalesce(level2, Level.ALL);
//      return l1.isGreaterOrEqual(l2) ? l1 : l2;
//  }

    private static Level minimum(Level level1, Level level2) {
        Level l1 = coalesce(level1, Level.OFF);
        Level l2 = coalesce(level2, Level.OFF);
        return l1.isGreaterOrEqual(l2) ? l2 : l1;
    }

    private static Level coalesce(Level level1, Level level2) {
        return level1 == null ? level2 == null ? Level.ALL : level2 : level1;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="instance fields">
    private Project _master;

    private Parser _parser;

    private Writer _writer;

    private final Map<String, String> _environmentVariables = new LinkedHashMap<>();

    private final Map<String, ProjectEntityReference> _entityReferences = new LinkedHashMap<>();

    private final Map<String, ProjectReference> _projectReferences = new LinkedHashMap<>();

    private final Map<String, Display> _displays = new LinkedHashMap<>();

    private Class<? extends Entity> _userEntityClass;

    private final Set<Artifact> _artifacts = new LinkedHashSet<>();

    private final Set<Method> _addAttributesMethods = new LinkedHashSet<>();

    private final Set<String> _singularPlatforms = new LinkedHashSet<>();

    private final Set<String> _processingGroups = new TreeSet<>();

    private boolean _annotatedWithProjectModule;

    private boolean _menuModule;

    private boolean _roleModule;

    private String _helpFileName;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="instance fields' public getters and setters">
    /**
     * @return the environment variables map
     */
    public Map<String, String> getEnvironmentVariables() {
        return _environmentVariables;
    }

    /**
     * Associates the specified value with the specified key in the environment variables map. If the map previously contained a mapping for the key,
     * the old value is replaced by the specified value.
     *
     * @param key key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return the previous value associated with key, or null if there was no mapping for key.
     */
    public String putEnvironmentVariable(String key, String value) {
        return _environmentVariables.put(key, value);
    }

    /**
     * @return the master project if this is a nested project; null otherwise
     */
    public Project getMaster() {
        return _master;
    }

    /**
     * @param master the master project to set
     */
    private void setMaster(Project master) {
        _master = master;
    }

    /**
     * @param classSimpleName
     * @return true if the project references an entity of a class with the specified simple name
     */
    public boolean referencesEntity(String classSimpleName) {
        return _entityReferences.containsKey(classSimpleName);
    }

    /**
     * @param type
     * @return true if the project references an entity of the class specified by type
     */
    public boolean referencesEntity(Class<?> type) {
        Entity entity = getEntity(type);
        return entity != null;
    }

    /**
     * @param type
     * @return the root entity of the specified class
     */
    public Entity getEntity(Class<?> type) {
//      Class<?> clazz = XS1.getNamedClass(type);
//      return getEntitiesMap().get(clazz.getSimpleName());
        ProjectEntityReference reference = getEntityReference(type);
        return reference == null ? null : reference.getEntity();
    }

    /**
     * @return the entities
     */
    public List<Entity> getEntitiesList() {
        List<Entity> list = new ArrayList<>();
        for (ProjectEntityReference reference : _entityReferences.values()) {
            if (reference.getEntity() != null) {
                list.add(reference.getEntity());
            }
        }
        return list;
    }

    /**
     * @return the entities
     */
    public Map<String, Entity> getEntitiesMap() {
        Map<String, Entity> entities = new LinkedHashMap<>();
        for (ProjectEntityReference reference : _entityReferences.values()) {
            if (reference.getEntity() != null) {
                entities.put(reference.getEntityClass().getSimpleName(), reference.getEntity());
            }
        }
        return entities;
    }

    /**
     * @param className
     * @return true if the project references a module of a class with the specified name
     */
    public boolean referencesModule(String className) {
        ProjectReference reference = _projectReferences.get(className);
        Project project = reference == null ? null : reference.getProject();
        return project != null && project.getMaster() != null;
    }

    /**
     * @param type
     * @return true if the project references a module of the class specified by type
     */
    public boolean referencesModule(Class<?> type) {
        Project module = getModule(type);
        return module != null;
    }

    /**
     * @param type
     * @return the root module of the specified class
     */
    public Project getModule(Class<?> type) {
        Project project = getProject(type);
        return project == null || project.getMaster() == null ? null : project;
    }

    /**
     * @return the modules
     */
    public List<Project> getModulesList() {
        List<Project> list = new ArrayList<>();
        for (ProjectReference reference : _projectReferences.values()) {
            Project module = reference.getProject();
            if (module != null && module.getMaster() != null) {
                list.add(module);
            }
        }
        return list;
    }

    /**
     * @return the modules
     */
    public Map<String, Project> getModulesMap() {
        Map<String, Project> projects = new LinkedHashMap<>();
        for (ProjectReference reference : _projectReferences.values()) {
            Project module = reference.getProject();
            if (module != null && module.getMaster() != null) {
                projects.put(reference.getProjectClass().getName(), module);
            }
        }
        return projects;
    }

    /**
     * @param className
     * @return true if the project references a project of a class with the specified name
     */
    public boolean referencesProject(String className) {
        return _projectReferences.containsKey(className);
    }

    /**
     * @param type
     * @return true if the project references a project of the class specified by type
     */
    public boolean referencesProject(Class<?> type) {
        Project project = getProject(type);
        return project != null;
    }

    /**
     * @param type
     * @return the root project of the specified class
     */
    public Project getProject(Class<?> type) {
//      Class<?> clazz = XS1.getNamedClass(type);
//      return getProjectsMap().get(clazz.getName());
        ProjectReference reference = getProjectReference(type);
        return reference == null ? null : reference.getProject();
    }

    /**
     * @return the projects
     */
    public List<Project> getProjectsList() {
        List<Project> list = new ArrayList<>();
        for (ProjectReference reference : _projectReferences.values()) {
            if (reference.getProject() != null) {
                list.add(reference.getProject());
            }
        }
        return list;
    }

    /**
     * @return the projects
     */
    public Map<String, Project> getProjectsMap() {
        Map<String, Project> projects = new LinkedHashMap<>();
        for (ProjectReference reference : _projectReferences.values()) {
            if (reference.getProject() != null) {
                projects.put(reference.getProjectClass().getName(), reference.getProject());
            }
        }
        return projects;
    }

    /**
     * @return the displays list
     */
    public List<? extends Display> getDisplaysList() {
        return new ArrayList<>(getDisplaysMap().values());
    }

    /**
     * @return the displays map
     */
    public Map<String, ? extends Display> getDisplaysMap() {
        return _displays;
    }

    Set<String> crossReferencedExpressionsSet;

    /**
     * @return the cross-referenced expressions
     */
    public Set<String> getCrossReferencedExpressionsSet() {
        if (crossReferencedExpressionsSet == null) {
            crossReferencedExpressionsSet = new LinkedHashSet<>();
            PersistentEntity pent;
            List<Entity> entities = getEntitiesList();
            for (Entity entity : entities) {
                if (entity instanceof PersistentEntity) {
                    pent = (PersistentEntity) entity;
                    crossReferencedExpressionsSet.addAll(pent.getCrossReferencedExpressionsSet());
                }
            }
        }
        return crossReferencedExpressionsSet;
    }

    public boolean containsCrossReferencedExpression(Expression expression) {
        String key = expression == null ? null : expression.getCrossReferencedExpressionsKey();
        return key != null && getCrossReferencedExpressionsSet().contains(key);
    }

    Set<String> schemasSet;

    public Set<String> getSchemasSet() {
        String schema;
        if (schemasSet == null) {
            schemasSet = new LinkedHashSet<>();
            PersistentEntity pent;
            List<Entity> entities = getEntitiesList();
            for (Entity entity : entities) {
                if (entity instanceof PersistentEntity) {
                    pent = (PersistentEntity) entity;
                    schema = pent.getSchema();
                    if (StringUtils.isNotBlank(schema)) {
                        schemasSet.add(schema.trim());
                    }
                }
            }
        }
        return schemasSet;
    }

    Map<String, String> tablesMap;

    public Map<String, String> getTablesMap() {
        String table;
        if (tablesMap == null) {
            tablesMap = new LinkedHashMap<>();
            PersistentEntity pent;
            PersistentEntityWrapper wrapper;
            List<Entity> entities = getEntitiesList();
            for (Entity entity : entities) {
                if (entity instanceof PersistentEntity) {
                    pent = (PersistentEntity) entity;
                    wrapper = new PersistentEntityWrapper(pent);
                    table = wrapper.getSqlName();
                    if (StringUtils.isNotBlank(table)) {
                        tablesMap.put(table.trim(), pent.getClass().getName());
                    }
                }
            }
        }
        return tablesMap;
    }

    /**
     * @return the ProjectModule annotation indicator
     */
    public boolean isAnnotatedWithModule() {
        return _annotatedWithProjectModule;
    }

    /**
     * @return the menu module indicator
     */
    public boolean isMenuModule() {
        return _menuModule;
    }

    /**
     * @return the role module indicator
     */
    public boolean isRoleModule() {
        return _roleModule;
    }

    /**
     * @return the help file name
     */
    public String getHelpFileName() {
        return _helpFileName;
    }

    /**
     * sets the help file name.
     *
     * @param helpFileName
     */
    public void setHelpFileName(String helpFileName) {
        _helpFileName = helpFileName;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="instance fields' package getters and setters">
    Parser getParser() {
        if (_parser == null) {
            _parser = new Parser();
        }
        return _parser;
    }

    Writer getWriter() {
        if (_writer == null) {
            _writer = new Writer(this, "project");
        }
        return _writer;
    }

    /**
     * @return the entity reference of the specified class
     */
    ProjectEntityReference getEntityReference(Class<?> type) {
        Class<?> clazz = XS1.getNamedClass(type);
        return _entityReferences.get(clazz.getSimpleName());
    }

    /**
     * @return the true type of the specified class
     */
    Class<?> getTrueType(Class<?> type) {
        Class<?> clazz = XS1.getNamedClass(type);
        String key = clazz.getSimpleName();
        if (_entityReferences.containsKey(key)) {
            ProjectEntityReference reference = _entityReferences.get(key);
            return reference == null ? null : reference.getEntityClass();
        }
        return type;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="instance fields' private getters and setters">
    /**
     * @return the entity references map
     */
    public Map<String, ProjectEntityReference> getEntityReferences() {
        return _entityReferences;
    }

    /**
     * @return the project reference of the specified class
     */
    ProjectReference getProjectReference(Class<?> type) {
        Class<?> clazz = XS1.getNamedClass(type);
        return _projectReferences.get(clazz.getName());
    }

    /**
     * @return the project references map
     */
    public Map<String, ProjectReference> getProjectReferences() {
        return _projectReferences;
    }

    /**
     * @return the user entity class
     */
    public Class<? extends Entity> getUserEntityClass() {
        return _userEntityClass;
    }

    /**
     * @param clazz the user entity class to set
     */
    public void setUserEntityClass(Class<? extends Entity> clazz) {
        _userEntityClass = clazz;
    }

    /**
     * @return the user entity class
     */
    public Entity getUserEntity() {
        return getEntity(_userEntityClass);
    }

    /**
     * @return the artifacts set
     */
    public Set<Artifact> getArtifacts() {
        return _artifacts;
    }

    /**
     * clears the artifacts set
     */
    private void clearArtifacts() {
        _artifacts.clear();
    }

    /**
     * clears the attributes set of every artifact
     */
    private void clearArtifactsAttributes() {
        for (Artifact artifact : _artifacts) {
            artifact.clearAttributes();
        }
    }

    /**
     * adds an artifact to the set
     *
     * @param artifact
     * @return
     */
    public boolean addArtifact(Artifact artifact) {
        return _artifacts.add(artifact);
    }

    /**
     * @return the addAttributes methods set
     */
    public Set<Method> getAddAttributesMethods() {
        return _addAttributesMethods;
    }

    /**
     * clears the addAttributes methods set
     */
    public void clearAddAttributesMethods() {
        _addAttributesMethods.clear();
    }

    /**
     *
     * @param clazz
     */
    public void attachAddAttributesMethods(Class<?> clazz) {
        logger.debug(signature("attachAddAttributesMethods", clazz));
        String name;
        boolean addAttributesMethod;
        int modifiers;
        Class<?> returnType;
        Class<?>[] parameterTypes;
        Method[] methods = clazz.getDeclaredMethods();
        List<Method> list = Arrays.asList(methods);
        Comparator<Method> comparator = new ByMethodSequence();
        ColUtils.sort(list, comparator);
        for (Method method : list) {
            name = method.getName();
            addAttributesMethod = method.isAnnotationPresent(AddAttributesMethod.class);
            modifiers = method.getModifiers();
            returnType = method.getReturnType();
            parameterTypes = method.getParameterTypes();
            if (addAttributesMethod
                && Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers)
                && void.class.equals(returnType)
                && parameterTypes.length == 1 && Artifact.class.isAssignableFrom(parameterTypes[0])) {
                logger.debug(signature(clazz.getSimpleName() + "." + name, parameterTypes[0]));
                _addAttributesMethods.add(method);
            }
        }
    }

    /**
     *
     */
    private void invokeAddAttributesMethods() {
        String name;
        Class<?> clazz;
        Class<?> parameterType;
        for (Method method : _addAttributesMethods) {
            clazz = method.getDeclaringClass();
            name = method.getName();
            parameterType = method.getParameterTypes()[0];
            for (Artifact artifact : _artifacts) {
                if (parameterType.isAssignableFrom(artifact.getClass())) {
                    if (Entity.class.isAssignableFrom(artifact.getClass()) && artifact.depth() != 0) {
                        continue;
                    }
                    try {
                        logger.debug(signature(clazz.getSimpleName() + "." + name, parameterType + " " + artifact.getClassPath()));
                        method.invoke(null, artifact);
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                        fatal(ex);
                    }
                }
            }
        }
    }

    /**
     * @return the singular platforms set
     */
    public Set<String> getSingularPlatforms() {
        return _singularPlatforms;
    }

    /**
     * @return the processing groups set
     */
    public Set<String> getProcessingGroups() {
        return _processingGroups;
    }
    // </editor-fold>

    public Project() {
        super();
        init();
    }

    private void init() {
        Class<?> namedClass = getNamedClass();
        String className = namedClass.getSimpleName();
        setDeclared(className);
    }

    private void settle() {
        settleAttributes();
    }

    protected void settleAttributes() {
        track("settleAttributes");
    }

    // <editor-fold defaultstate="collapsed" desc="annotate">
    @Override
    void initializeAnnotations() {
        super.initializeAnnotations();
        _annotatedWithProjectModule = false;
        _menuModule = false;
        _roleModule = false;
        _helpFileName = "";
    }

    @Override
    void annotate(Class<?> type) {
        super.annotate(type);
        if (type != null) {
            annotateModule(type);
        }
    }

    @Override
    void annotate(Field field) {
        super.annotate(field);
        if (field != null) {
            annotateModule(field);
        }
    }

    @Override
    protected List<Class<? extends Annotation>> getValidTypeAnnotations() {
        List<Class<? extends Annotation>> valid = super.getValidTypeAnnotations();
        valid.add(ProjectModule.class);
        return valid;
    }

    @Override
    protected List<Class<? extends Annotation>> getValidFieldAnnotations() {
        List<Class<? extends Annotation>> valid = super.getValidFieldAnnotations();
        valid.add(ProjectModule.class);
        return valid;
    }

    private void annotateModule(Class<?> type) {
        Class<?> annotatedClass = XS1.getAnnotatedClass(type, ProjectModule.class);
        if (annotatedClass != null) {
            ProjectModule annotation = annotatedClass.getAnnotation(ProjectModule.class);
            if (annotation != null) {
                _annotatedWithProjectModule = true;
                _menuModule = annotation.menu().toBoolean(_menuModule);
                _roleModule = annotation.role().toBoolean(_roleModule);
                _helpFileName = StringUtils.defaultIfBlank(annotation.helpFile(), _helpFileName);
            }
        }
    }

    private void annotateModule(Field field) {
        _annotatedWithProjectModule = field.isAnnotationPresent(ProjectModule.class);
        if (_annotatedWithProjectModule) {
            ProjectModule annotation = field.getAnnotation(ProjectModule.class);
            _menuModule = annotation.menu().toBoolean(_menuModule);
            _roleModule = annotation.role().toBoolean(_roleModule);
            _helpFileName = StringUtils.defaultIfBlank(annotation.helpFile(), _helpFileName);
        }
    }
    // </editor-fold>

    public boolean build() {
        logger.info(signature("build", getClass().getName()));
        TLC.setProject(this);
        clearArtifacts();
        boolean built = parse() && analyze();
        return built;
    }

    public boolean parse() {
        logger.info(signature("parse", getClass().getName()));
        TLC.setProject(this);
        return getParser().parse();
    }

    public boolean analyze() {
        logger.info(signature("analyze", getClass().getName()));
        TLC.setProject(this);
        List<Project> modulesList = getModulesList();
        Collections.sort(modulesList);
        boolean analyzed = true;
        String name;
        for (Project module : modulesList) {
            analyzed &= module.assemble();
            if (analyzed) {
                List<? extends Display> displaysList = module.getDisplaysList();
                for (Display display : displaysList) {
                    name = display.getName();
                    if (_displays.containsKey(name)) {
                    } else {
                        _displays.put(name, display);
                    }
                }
                continue;
            }
            break;
        }
        return analyzed;
    }

    protected boolean assemble() {
        log(_detailLevel, signature("assemble", getClass().getName()));
        return true;
    }

    public boolean generate(String platform) {
        logger.info(signature("generate", "platform=" + platform));
        String alias = getAlias();
        if (StringUtils.isBlank(alias)) {
            logger.error("invalid project alias; generation aborted");
            return false;
        } else if (!alias.matches("^[a-z][a-z0-9]*$")) {
            logger.error(alias + " is an invalid project alias; generation aborted");
            return false;
        } else if (alias.equalsIgnoreCase("meta") || alias.equalsIgnoreCase("workspace")) {
            logger.error(alias + " is a restricted project alias; generation aborted");
            return false;
        } else if (_singularPlatforms.contains(platform) && platform.equals(alias)) {
            logger.error("project alias matches platform name; generation aborted");
            return false;
        }
        TLC.setProject(this);
        clearArtifactsAttributes();
        invokeAddAttributesMethods();
        configureWriter();
        Writer writer = getWriter();
        writer.setOptionalResourceNames(getEntitiesMap().keySet());
        return writer.write(platform);
    }

    private void configureWriter() {
        Writer.setAlertLevel(_alertLevel);
        Writer.setDetailLevel(_detailLevel);
        Writer.setTrackingLevel(_trackingLevel);
    }

    private String signature(String method, Object... parameters) {
        String pattern = "{0}({1})";
        return MessageFormat.format(pattern, method, StringUtils.join(parameters, ", "));
    }

    // <editor-fold defaultstate="collapsed" desc="entity display getters">
    public Display getReadingTableDisplayOf(Entity entity) {
        return getDisplayOf(entity, DisplayMode.READING, DisplayFormat.TABLE);
    }

    public Display getReadingDetailDisplayOf(Entity entity) {
        return getDisplayOf(entity, DisplayMode.READING, DisplayFormat.DETAIL);
    }

    public Display getReadingTreeDisplayOf(Entity entity) {
        return getDisplayOf(entity, DisplayMode.READING, DisplayFormat.TREE);
    }

    public Display getWritingTableDisplayOf(Entity entity) {
        return getDisplayOf(entity, DisplayMode.WRITING, DisplayFormat.TABLE);
    }

    public Display getWritingDetailDisplayOf(Entity entity) {
        return getDisplayOf(entity, DisplayMode.WRITING, DisplayFormat.DETAIL);
    }

    public Display getWritingTreeDisplayOf(Entity entity) {
        return getDisplayOf(entity, DisplayMode.WRITING, DisplayFormat.TREE);
    }

    public Display getProcessingConsoleDisplayOf(Entity entity) {
        return getDisplayOf(entity, DisplayMode.PROCESSING, DisplayFormat.CONSOLE);
    }

    private Display getDisplayOf(Entity entity, DisplayMode mode, DisplayFormat format) {
        if (entity == null) {
            return null;
        }
        Entity displayEntity;
        Entity displayMaster;
        DisplayMode displayMode;
        DisplayFormat displayFormat;
        List<? extends Display> displays = getDisplaysList();
        for (Display display : displays) {
            displayEntity = display.getEntity();
            displayMaster = display.getMaster();
            displayMode = display.getDisplayMode();
            displayFormat = display.getDisplayFormat();
            if (entity.equals(displayEntity) && displayMaster == null && mode.equals(displayMode) && format.equals(displayFormat)) {
                logger.debug(entity.getName() + " " + mode + " " + format + " display is " + display.getName() + " @ " + getName());
                return display;
            }
        }
        logger.debug(entity.getName() + " " + mode + " " + format + " display is not @ " + getName());
        return null;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="sibling display getters">
    public Display getTableSiblingOf(Display display) {
        return getSiblingOf(display, DisplayFormat.TABLE);
    }

    public Display getDetailSiblingOf(Display display) {
        return getSiblingOf(display, DisplayFormat.DETAIL);
    }

    public Display getTreeSiblingOf(Display display) {
        return getSiblingOf(display, DisplayFormat.TREE);
    }

    public Display getConsoleSiblingOf(Display display) {
        return getSiblingOf(display, DisplayFormat.CONSOLE);
    }

    private Display getSiblingOf(Display display, DisplayFormat format) {
        if (display == null) {
            return null;
        }
        Entity displayEntity = display.getEntity();
        Entity displayMaster = display.getMaster();
        if (displayEntity == null) {
            return null;
        }
        DisplayMode displayMode = display.getDisplayMode();
        DisplayFormat displayFormat = display.getDisplayFormat();
        if (displayMode == null || displayFormat == null) {
            return null;
        }
        DisplayMode mode = DisplayFormat.CONSOLE.equals(format) ? DisplayMode.PROCESSING
            : DisplayFormat.CONSOLE.equals(displayFormat) ? DisplayMode.UNSPECIFIED
            : displayMode;
        /**/
        Entity siblingEntity;
        Entity siblingMaster;
        DisplayMode siblingMode;
        DisplayFormat siblingFormat;
        List<? extends Display> siblings = getDisplaysList();
        for (Display sibling : siblings) {
            if (sibling.equals(display)) {
                continue;
            }
            siblingEntity = sibling.getEntity();
            siblingMaster = sibling.getMaster();
            siblingMode = sibling.getDisplayMode();
            siblingFormat = sibling.getDisplayFormat();
            if (displayEntity.equals(siblingEntity)) {
                if (format.equals(DisplayFormat.UNSPECIFIED) || format.equals(siblingFormat)) {
                    if (mode.equals(DisplayMode.UNSPECIFIED) || mode.equals(siblingMode)) {
                        if (siblingMaster == null && displayMaster == null) {
                            logger.debug(display.getName() + " " + format + " sibling is " + sibling.getName() + " @ " + getName());
                            return sibling;
                        }
                        if (siblingMaster != null && siblingMaster.equals(displayMaster)) {
                            logger.debug(display.getName() + " " + format + " sibling is " + sibling.getName() + " @ " + getName());
                            return sibling;
                        }
                    }
                }
            }
        }
        logger.debug(display.getName() + " " + format + " sibling is not @ " + getName());
        return null;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="cousin display getters">
    public Display getTableCousinOf(Display display) {
        return getCousinOf(display, DisplayFormat.TABLE);
    }

    public Display getDetailCousinOf(Display display) {
        return getCousinOf(display, DisplayFormat.DETAIL);
    }

    public Display getTreeCousinOf(Display display) {
        return getCousinOf(display, DisplayFormat.TREE);
    }

    public Display getConsoleCousinOf(Display display) {
        return getCousinOf(display, DisplayFormat.CONSOLE);
    }

    private Display getCousinOf(Display display, DisplayFormat format) {
        if (display == null) {
            return null;
        }
        Entity displayEntity = display.getEntity();
//      Entity displayMaster = display.getMaster();
        if (displayEntity == null) {
            return null;
        }
        DisplayMode displayMode = display.getDisplayMode();
        DisplayFormat displayFormat = display.getDisplayFormat();
        if (displayMode == null || displayFormat == null) {
            return null;
        }
        DisplayMode mode = DisplayFormat.CONSOLE.equals(format) ? DisplayMode.PROCESSING
            : DisplayFormat.CONSOLE.equals(displayFormat) ? DisplayMode.UNSPECIFIED
            : displayMode;
        /**/
        Entity cousinEntity;
        Entity cousinMaster;
        DisplayMode cousinMode;
        DisplayFormat cousinFormat;
        List<? extends Display> cousins = getDisplaysList();
        for (Display cousin : cousins) {
            if (cousin.equals(display)) {
                continue;
            }
            cousinEntity = cousin.getEntity();
            cousinMaster = cousin.getMaster();
            cousinMode = cousin.getDisplayMode();
            cousinFormat = cousin.getDisplayFormat();
            if (displayEntity.equals(cousinEntity)) {
                if (format.equals(DisplayFormat.UNSPECIFIED) || format.equals(cousinFormat)) {
                    if (mode.equals(DisplayMode.UNSPECIFIED) || mode.equals(cousinMode)) {
                        if (cousinMaster == null) {
                            logger.debug(display.getName() + " " + format + " cousin is " + cousin.getName() + " @ " + getName());
                            return cousin;
                        }
                    }
                }
            }
        }
        logger.debug(display.getName() + " " + format + " cousin is not @ " + getName());
        return null;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="reference display getters">
    public Display getReadingTableAlternativeTo(Display display, Entity entity) {
        return getAlternativeTo(display, entity, DisplayFormat.TABLE, DisplayMode.READING);
    }

    public Display getReadingDetailAlternativeTo(Display display, Entity entity) {
        return getAlternativeTo(display, entity, DisplayFormat.DETAIL, DisplayMode.READING);
    }

    public Display getReadingTreeAlternativeTo(Display display, Entity entity) {
        return getAlternativeTo(display, entity, DisplayFormat.TREE, DisplayMode.READING);
    }

    public Display getWritingTableAlternativeTo(Display display, Entity entity) {
        return getAlternativeTo(display, entity, DisplayFormat.TABLE, DisplayMode.WRITING);
    }

    public Display getWritingDetailAlternativeTo(Display display, Entity entity) {
        return getAlternativeTo(display, entity, DisplayFormat.DETAIL, DisplayMode.WRITING);
    }

    public Display getWritingTreeAlternativeTo(Display display, Entity entity) {
        return getAlternativeTo(display, entity, DisplayFormat.TREE, DisplayMode.WRITING);
    }

    public Display getProcessingConsoleAlternativeTo(Display display, Entity entity) {
        return getAlternativeTo(display, entity, DisplayFormat.CONSOLE, DisplayMode.PROCESSING);
    }

    private Display getAlternativeTo(Display display, Entity entity, DisplayFormat format, DisplayMode mode) {
        if (display == null || entity == null) {
            return null;
        }
        String name = display.getName() + "/" + entity.getName();
        Entity otherEntity;
        Entity otherMaster;
        DisplayMode otherMode;
        DisplayFormat otherFormat;
        List<? extends Display> others = getDisplaysList();
        for (Display other : others) {
            if (other.equals(display)) {
                continue;
            }
            otherEntity = other.getEntity();
            otherMaster = other.getMaster();
            otherMode = other.getDisplayMode();
            otherFormat = other.getDisplayFormat();
            if (entity.equals(otherEntity) && otherMaster == null && format.equals(otherFormat) && mode.equals(otherMode)) {
                logger.debug(name + " " + format + " alternative is " + other.getName() + " @ " + getName());
                return other;
            }
        }
        logger.debug(name + " " + format + " alternative is not @ " + getName());
        return null;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="locally declared entity classes getters">
    public Set<Class<?>> getLocallyDeclaredEntityClasses() {
        Set<Class<?>> classes = new LinkedHashSet<>();
        Class<?> namedClass = XS2.getNamedClass(this);
        for (Field field : XS2.getFields(namedClass)) {
            Class<?> type = field.getType();
            if (Entity.class.isAssignableFrom(type)) {
                classes.add(type);
            }
        }
        return classes;
    }

    public Set<String> getLocallyDeclaredEntityClassSimpleNames() {
        Set<Class<?>> classes = getLocallyDeclaredEntityClasses();
        Set<String> names = new LinkedHashSet<>();
        for (Class<?> clazz : classes) {
            names.add(clazz.getSimpleName());
        }
        return names;
    }

    public String[] getLocallyDeclaredEntityClassSimpleNamesArray() {
        Set<String> names = getLocallyDeclaredEntityClassSimpleNames();
        String[] array = new String[names.size()];
        return names.toArray(array);
    }
    // </editor-fold>

    private void fatal(Throwable throwable) {
        Throwable cause = ThrowableUtils.getCause(throwable);
        String message = throwable.equals(cause) ? throwable.getClass().getSimpleName() : throwable.getMessage();
        logger.fatal(message, cause);
    }

    private void log(Level level, String method, Object... parameters) {
        if (foul(logger, level)) {
            return;
        }
        String message = signature(method, parameters);
        logger.log(level, message);
    }

    private boolean fair(Logger logger, Level level) {
        return logger != null && level != null && logger.isEnabledFor(level) && !level.equals(Level.OFF);
    }

    private boolean foul(Logger logger, Level level) {
        return !fair(logger, level);
    }

    private void track(String method) {
        track(method, this);
    }

    private void track(String method, Object... parameters) {
        getParser().track(depth(), round(), getClassPath(), method, parameters);
    }

    // <editor-fold defaultstate="collapsed" desc="Comparable">
    @Override
    public int compareTo(Project o) {
        Project that;
        if (o != null) {
            that = o;
            String thisName = StringUtils.trimToEmpty(this.getName());
            String thatName = StringUtils.trimToEmpty(that.getName());
            return thisName.compareTo(thatName);
        }
        return 0;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="toString">
    @Override
    public String toString() {
        String str1 = getName();
        String str2 = getNamedClass().getSimpleName();
        String str3 = getAlias();
        String str4 = str1 == null || str1.equals(str2) ? str2 : str2 + "[" + str1 + "]";
        String str5 = str3 == null || str3.equals(str1) ? str4 : str4 + "[" + str3 + "]";
        String str6 = str5.replace("][", ", ");
//      return str6 + "@" + Integer.toHexString(hashCode());
        return str6;
    }

    @Override
    protected String fieldsToString(int n, String key, boolean verbose, boolean fields, boolean maps) {
        String tab = verbose ? StringUtils.repeat(" ", 4) : "";
        String fee = verbose ? StringUtils.repeat(tab, n) : "";
        String faa = " = ";
        String foo = verbose ? EOL : ", ";
        String string = super.fieldsToString(n, key, verbose, fields, maps);
        if (fields || verbose) {
            string += fee + tab + "entities" + faa + _entityReferences.size() + foo;
            string += fee + tab + "projects" + faa + _projectReferences.size() + foo;
        }
        return string;
    }

    @Override
    protected String mapsToString(int n, String key, boolean verbose, boolean fields, boolean maps) {
        String string = super.mapsToString(n, key, verbose, fields, maps);
        if (maps || verbose) {
            for (String clave : _entityReferences.keySet()) {
                ProjectEntityReference valor = _entityReferences.get(clave);
                if (valor.getEntity() != null) {
                    string += valor.getEntity().toString(n + 1, clave, false, fields, false);
                }
            }
            for (String clave : _projectReferences.keySet()) {
                ProjectReference valor = _projectReferences.get(clave);
                if (valor.getProject() != null && valor.getProject() != this) {
                    string += valor.getProject().toString(n + 1, clave, false, fields, maps);
                }
            }
        }
        return string;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Parser">
    class Parser {

        private final Logger logger = Logger.getLogger(Project.Parser.class);

        private int maxDepthReached = 0;

        private int maxRoundReached = 0;

        private int entities = 0;

        private int warnings = 0;

        private int errors = 0;

        private boolean parse() {
            log(_detailLevel, "parse");
            logJavaClassPath();
            resetCounters();
            try {
                printSettings();
                putReferences();
                printProjectSummary(Level.INFO);
                printProjectReferencesSummary(Level.INFO);
                printEntityReferencesSummary(Level.INFO);
                if (errors == 0) {
                    initialiseEntityReferences();
                }
                if (errors == 0) {
                    settleEntityReferences();
                }
                if (errors == 0) {
                    finaliseEntityReferences();
                }
                if (errors == 0) {
                    checkEntityReferences();
                }
                if (errors == 0) {
                    initialiseProjectReferences();
                }
                if (errors == 0) {
                    settleProjectReferences();
                }
                setMasterFields();
                printSummary();
                if (_verbose) {
                    printProjectReferencesDetail(_detailLevel);
                    printEntityReferencesDetail(_detailLevel);
                }
            } catch (Throwable throwable) {
                fatal(throwable);
            }
            return errors == 0;
        }

        private void logJavaClassPath() {
            String key = "java.class.path";
            String jcp = System.getProperty(key);
            if (jcp != null) {
//              logger.debug(key + "=" + EOL + jcp.replace(";", EOL));
                logger.debug(key);
                String[] strings = StringUtils.splitByWholeSeparator(jcp, ";");
                for (String string : strings) {
                    logger.debug(TAB + string);
                }
            }
        }

        // <editor-fold defaultstate="collapsed" desc="put references">
        private void putReferences() {
            log(_trackingLevel, "putReferences");
            Class<?> type = Project.this.getClass();
            Class<?> clazz = XS1.getNamedClass(type);
            Class<?> declaringType = null;
            putReferences(type, declaringType, Project.this, null);
            String key = clazz.getName();
            ProjectReference reference = _projectReferences.get(key);
            reference.setProject(Project.this);
        }

        private void putReferences(Class<?> type, Class<?> declaringType, Artifact declaringArtifact, Field declaringField) {
            String pattern;
            String remarks;
            Class<?> clazz = XS1.getNamedClass(type);
            int modifiers = clazz.getModifiers();
            boolean restricted = clazz.isPrimitive() || Modifier.isAbstract(modifiers) || !Modifier.isPublic(modifiers);
            if (restricted) {
            } else if (Entity.class.isAssignableFrom(clazz)) {
                String key = clazz.getSimpleName();
                if (_entityReferences.containsKey(key)) {
                    ProjectEntityReference reference = _entityReferences.get(key);
                    Class<?> entityClass = reference.getEntityClass();
                    if (clazz.equals(entityClass)) {
                        reference.putDeclaringType(declaringType);
                        reference.setExplicit(declaringType);
                        reference.setImplicit(declaringType);
                    } else if (clazz.isAssignableFrom(entityClass)) {
                        reference.putDeclaringType(declaringType);
                        reference.setExplicit(declaringType);
                        reference.setImplicit(declaringType);
                        logEntityReferenceOverride(_alertLevel, entityClass, clazz, declaringType, null);
                        warnings++;
                    } else if (entityClass.isAssignableFrom(clazz)) {
                        putEntityReference(clazz, declaringType, reference);
                        logEntityReferenceOverride(_alertLevel, clazz, entityClass, declaringType, null);
                        warnings++;
                    } else {
                        pattern = "{0} is not assignable from {1}";
                        remarks = MessageFormat.format(pattern, entityClass.getName(), clazz.getName());
                        logEntityReferenceOverride(Level.ERROR, clazz, entityClass, declaringType, remarks);
                        errors++;
                    }
                } else {
                    putEntityReference(clazz, declaringType);
                }
            } else if (Project.class.isAssignableFrom(clazz)) {
                if (declaringType == null || Project.class.isAssignableFrom(declaringType)) {
                    String key = clazz.getName();
                    if (_projectReferences.containsKey(key)) {
                        ProjectReference reference = _projectReferences.get(key);
                        reference.putDeclaringType(declaringType);
                    } else {
                        putProjectReference(clazz, declaringType, declaringArtifact, declaringField);
                    }
                }
            }
        }

        /**
         * @param riding = overriding class
         * @param ridden = overridden class
         * @param declaring = declaring class
         */
        private void logEntityReferenceOverride(Level level, Class<?> riding, Class<?> ridden, Class<?> declaring, String remarks) {
            if (foul(logger, level)) {
                return;
            }
            String pattern = level.isGreaterOrEqual(Level.ERROR) ? "failed to override" : "overriding";
            pattern += " reference to entity {0} at {1}";
            String name = riding.getSimpleName();
            String message = MessageFormat.format(pattern, name, typeTitleAndName(declaring));
            logger.log(level, message);
            //
            Level detailLevel = level.isGreaterOrEqual(Level.WARN) ? level : _detailLevel;
            if (foul(logger, detailLevel)) {
                return;
            }
            logger.log(detailLevel, TAB + "overriding class: " + riding.getName());
            logger.log(detailLevel, TAB + "overridden class: " + ridden.getName());
            if (StringUtils.isNotBlank(remarks)) {
                logger.log(detailLevel, TAB + remarks);
            }
        }

        private void putEntityReference(Class<?> type, Class<?> declaringType) {
            ProjectEntityReference previousReference = null;
            putEntityReference(type, declaringType, previousReference);
        }

        private void putEntityReference(Class<?> type, Class<?> declaringType, ProjectEntityReference previousReference) {
            String key = type.getSimpleName();
            Class<?> concreteSuperclass = XS1.getConcreteSuperclass(type);
            if (concreteSuperclass != null) {
                putReferences(concreteSuperclass, type, null, null);
            }
            boolean explicit = previousReference != null && previousReference.isExplicit();
            boolean implicit = previousReference != null && previousReference.isImplicit();
            ProjectEntityReference reference = new ProjectEntityReference(type, Project.this);
            reference.putDeclaringType(declaringType);
            reference.setExplicit(explicit);
            reference.setExplicit(declaringType);
            reference.setImplicit(implicit);
            reference.setImplicit(declaringType);
            _entityReferences.put(key, reference);
            for (Field field1 : XS1.getFields(type, Entity.class)) { // type.getDeclaredFields()
                Class<?> fieldType1 = field1.getType();
                if (Entity.class.isAssignableFrom(fieldType1)) {
                    putReferences(fieldType1, type, null, null);
                } else if (Operation.class.isAssignableFrom(fieldType1)) {
                    for (Field field2 : XS1.getFields(fieldType1, Operation.class)) { // type.getDeclaredFields()
                        Class<?> fieldType2 = field2.getType();
                        if (Entity.class.isAssignableFrom(fieldType2)) {
                            putReferences(fieldType2, type, null, null);
                        }
                    }
                }
            }
        }

        private void putProjectReference(Class<?> type, Class<?> declaringType, Artifact declaringArtifact, Field declaringField) {
            String key = type.getName();
            ProjectReference reference = new ProjectReference(type, Project.this);
            reference.putDeclaringType(declaringType);
            reference.setDeclaringArtifact(declaringArtifact);
            reference.setDeclaringField(declaringField);
            _projectReferences.put(key, reference);
            for (Field field : XS1.getFields(type, Project.class)) { // type.getDeclaredFields()
                putReferences(field.getType(), type, null, field);
            }
        }
        // </editor-fold>

        private void setMasterFields() {
            log(_trackingLevel, "setMasterFields");
            Class<?> type = Project.this.getClass();
            Class<?> fieldType;
//          String fieldName;
            Project project;
            Class<?> projectClass;
            for (ProjectReference reference : _projectReferences.values()) {
                project = reference.getProject();
                projectClass = reference.getProjectClass();
                for (Field field : XS1.getFields(type, Project.class)) {
                    fieldType = field.getType();
                    if (projectClass.equals(fieldType)) {
//                      fieldName = field.getName();
                        field.setAccessible(true);
                        try {
                            if (field.get(Project.this) == null) {
                                field.set(Project.this, project);
                            }
                        } catch (IllegalArgumentException | IllegalAccessException ex) {
                            fatal(ex);
                        }
                    }
                }
            }
        }

        // <editor-fold defaultstate="collapsed" desc="initialise, settle and finalise entity references">
        private void initialiseEntityReferences() {
            log(_trackingLevel, "initialiseEntityReferences");
            Entity entity;
            for (ProjectEntityReference reference : _entityReferences.values()) {
                if (reference.getEntity() == null) {
//                  Entity entity = (Entity) reference.getEntityClass().newInstance();
                    entity = getEntityInstance(reference.getEntityClass());
                    reference.setEntity(entity);
                }
            }
            for (ProjectEntityReference reference : _entityReferences.values()) {
                entity = reference.getEntity();
                if (entity != null) {
                    entity.initialise();
                }
            }
        }

        private void settleEntityReferences() {
            log(_trackingLevel, "settleEntityReferences");
            Entity entity;
            for (ProjectEntityReference reference : _entityReferences.values()) {
                entity = reference.getEntity();
                if (entity != null) {
                    entity.settle();
                }
            }
        }

        private void finaliseEntityReferences() {
            log(_trackingLevel, "finaliseEntityReferences");
            Entity entity;
            for (ProjectEntityReference reference : _entityReferences.values()) {
                entity = reference.getEntity();
                if (entity != null) {
                    entity.finalise();
                }
            }
        }

        private void checkEntityReferences() {
            log(_trackingLevel, "checkEntityReferences");
            Entity entity;
            List<Entity> extensionsList;
            boolean concreteless;
            for (ProjectEntityReference reference : _entityReferences.values()) {
                entity = reference.getEntity();
                if (entity != null) {
                    if (entity.isAbstractClass()) {
                        concreteless = true;
                        extensionsList = entity.getExtensionsList();
                        for (Entity extension : extensionsList) {
                            if (extension.isAbstractClass()) {
                                continue;
                            }
                            concreteless = false;
                            break;
                        }
                        if (concreteless) {
                            logger.error(entity.getName() + " is an abstract class without concrete extensions");
                            increaseErrorCount();
                        }
                    }
                }
            }
        }

        private Entity getEntityInstance(Class<?> type) {
            String errmsg = "failed to create a new instance of " + type;
            try {
                return (Entity) type.getConstructor(Artifact.class, Field.class).newInstance(Project.this, null);
            } catch (IllegalArgumentException | IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
                throw new InstantiationRuntimeException(errmsg, ex);
            }
        }
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="initialise, settle and finalise project references">
        private void initialiseProjectReferences() {
            log(_trackingLevel, "initialiseProjectReferences");
            Project project;
            for (ProjectReference reference : _projectReferences.values()) {
                if (reference.getProject() == null) {
//                  Project project = (Project) reference.getProjectClass().newInstance();
                    project = getProjectInstance(reference.getProjectClass());
                    project.setMaster(Project.this);
                    project.resetDeclaringArtifact(reference.getDeclaringArtifact());
                    project.resetDeclaringField(reference.getDeclaringField());
//                  project.initializeAnnotations();
//                  project.annotate(project.getNamedClass());
                    project.annotate(reference.getDeclaringField());
                    reference.setProject(project);
                }
            }
            String key;
            ProjectEntityReference entityReference;
            ProjectReference projectReference;
            Map<String, Class<?>> declaredTypes;
            Map<String, ProjectEntityReference> entityReferences;
            Map<String, ProjectReference> projectReferences;
            for (ProjectReference reference : _projectReferences.values()) {
                project = reference.getProject();
                if (project != null && project != Project.this) {
                    entityReferences = project.getEntityReferences();
                    projectReferences = project.getProjectReferences();
                    declaredTypes = reference.getDeclaredTypes();
                    for (Class<?> declaredType : declaredTypes.values()) {
                        if (Entity.class.isAssignableFrom(declaredType)) {
                            key = declaredType.getSimpleName();
                            if (_entityReferences.containsKey(key)) {
                                entityReference = _entityReferences.get(key);
                                entityReferences.put(key, entityReference);
                            }
                        } else if (Project.class.isAssignableFrom(declaredType)) {
                            key = declaredType.getName();
                            if (_projectReferences.containsKey(key)) {
                                projectReference = _projectReferences.get(key);
                                projectReferences.put(key, projectReference);
                            }
                        }
                    }
                    project.getParser().printProjectSummary(_detailLevel);
                    project.getParser().printProjectReferencesSummary(_detailLevel);
                    project.getParser().printEntityReferencesSummary(_detailLevel);
                }
            }
        }

        private void settleProjectReferences() {
            log(_trackingLevel, "settleProjectReferences");
            Project project;
            for (ProjectReference reference : _projectReferences.values()) {
                project = reference.getProject();
                if (project != null) {
                    project.settle();
                }
            }
        }

        private Project getProjectInstance(Class<?> type) {
            String errmsg = "failed to create a new instance of " + type;
            try {
                return (Project) type.newInstance();
            } catch (IllegalArgumentException | IllegalAccessException | InstantiationException | SecurityException ex) {
                throw new InstantiationRuntimeException(errmsg, ex);
            }
        }
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="print references">
        private void printEntityReferencesSummary(Level level) {
            if (foul(logger, level)) {
                return;
            }
            boolean explicit;
            boolean implicit;
            Map<String, Class<?>> references;
            Map<String, Class<?>> referenced;
            Entity e;
            String s;
            int i = _entityReferences.size();
            String pattern = i == 0
                ? "project {0} contains no references to entities"
                : "project {0} contains references to {1} distinct entities";
            String message = MessageFormat.format(pattern, Project.this.getClass().getName(), i);
            logger.log(level, message);
            //
            if (foul(logger, _detailLevel)) {
                return;
            }
            for (ProjectEntityReference reference : _entityReferences.values()) {
                explicit = reference.isExplicit();
                implicit = reference.isImplicit();
                references = reference.getDeclaredTypes();
                referenced = reference.getDeclaringTypes();
                e = reference.getEntity();
                s = reference.getEntityClass().getSimpleName();
                s += " {";
                s += reference.getEntityClass().getName();
                s += e == null ? "" : "@" + Integer.toHexString(e.hashCode());
                s += ", explicit=" + explicit;
                s += ", implicit=" + implicit;
                s += ", references=" + references.size();
                s += ", referenced=" + referenced.size();
                s += "} ";
                logger.log(_detailLevel, TAB + s);
                for (Class<?> declaredType : references.values()) {
                    logger.log(_detailLevel, TAB + TAB + "references " + typeTitleAndName(declaredType));
                }
                for (Class<?> declaringType : referenced.values()) {
                    logger.log(_detailLevel, TAB + TAB + "is referenced by " + typeTitleAndName(declaringType));
                }
            }
        }

        private void printEntityReferencesDetail(Level level) {
            if (foul(logger, level)) {
                return;
            }
            boolean initialised;
            String string;
            for (ProjectEntityReference reference : _entityReferences.values()) {
                initialised = reference.getEntity() != null;
                if (initialised) {
                    string = reference.getEntity().toString(0, null, _verbose, true, true);
                    logger.log(level, string);
                }
            }
        }

        private void printProjectSummary(Level level) {
            if (foul(logger, level)) {
                return;
            }
            String pattern;
            String message;
            if (_master == null) {
                pattern = "project {0} is the master project";
                message = MessageFormat.format(pattern, Project.this.getClass().getName());
            } else {
                pattern = "project {0} is nested within project {1}";
                message = MessageFormat.format(pattern, Project.this.getClass().getName(), _master.getClass().getName());
            }
            logger.log(level, message);
        }

        private void printProjectReferencesSummary(Level level) {
            if (foul(logger, level)) {
                return;
            }
            String pattern;
            String message;
            Map<String, Class<?>> references;
            Map<String, Class<?>> referenced;
            Project p;
            String s;
            Class<? extends Project> clazz = Project.this.getClass();
            int i = 0;
            for (ProjectReference reference : _projectReferences.values()) {
                if (!clazz.equals(reference.getProjectClass())) {
                    i++;
                }
            }
            pattern = i == 0
                ? "project {0} contains no references to other projects"
                : "project {0} contains references to {1} other projects";
            message = MessageFormat.format(pattern, Project.this.getClass().getName(), i);
            logger.log(level, message);
            //
            if (foul(logger, _detailLevel)) {
                return;
            }
            for (ProjectReference reference : _projectReferences.values()) {
                references = reference.getDeclaredTypes();
                referenced = reference.getDeclaringTypes();
                p = reference.getProject();
                s = reference.getProjectClass().getSimpleName();
                s += " {";
                s += reference.getProjectClass().getName();
                s += p == null ? "" : "@" + Integer.toHexString(p.hashCode());
                s += p == null ? "" : ", master=" + p.getMaster();
                s += ", references=" + references.size();
                s += ", referenced=" + referenced.size();
                s += "} ";
                logger.log(_detailLevel, TAB + s);
                for (Class<?> declaredType : references.values()) {
                    logger.log(_detailLevel, TAB + TAB + "references " + typeTitleAndName(declaredType));
                }
                for (Class<?> declaringType : referenced.values()) {
                    logger.log(_detailLevel, TAB + TAB + "is referenced by " + typeTitleAndName(declaringType));
                }
            }
        }

        private void printProjectReferencesDetail(Level level) {
            if (foul(logger, level)) {
                return;
            }
            boolean initialised;
            String string;
            for (ProjectReference reference : _projectReferences.values()) {
                initialised = reference.getProject() != null;
                if (initialised) {
                    string = reference.getProject().toString(0, null, _verbose, true, true);
                    logger.log(level, string);
                }
            }
        }
        // </editor-fold>

        private String typeTitleAndName(Class<?> type) {
            if (Entity.class.isAssignableFrom(type)) {
                return "entity " + type.getName();
            } else if (Project.class.isAssignableFrom(type)) {
                return "project " + type.getName();
            } else {
                return "" + type;
            }
        }

        private void printSettings() {
            logger.info("defaultMaxDepth=" + _defaultMaxDepth);
            logger.info("defaultMaxRound=" + _defaultMaxRound);
            logger.info("alertLevel=" + _alertLevel);
            logger.info("detailLevel=" + _detailLevel);
            logger.info("trackingLevel=" + _trackingLevel);
            logger.info("verbose=" + _verbose);
        }

        private void printSummary() {
            logger.info("maxDepthReached=" + maxDepthReached);
            logger.info("maxRoundReached=" + maxRoundReached);
            logger.info("artifacts=" + _artifacts.size());
            logger.info("entities=" + entities);
            if (warnings == 0) {
                logger.info("warnings=" + warnings);
            } else {
                logger.warn("warnings=" + warnings);
            }
            if (errors == 0) {
                logger.info("errors=" + errors);
            } else {
                logger.warn("errors=" + errors);
            }
        }

        private void resetCounters() {
            maxDepthReached = 0;
            maxRoundReached = 0;
            entities = 0;
            warnings = 0;
            errors = 0;
        }

        /**
         * @param depth the maxDepthReached to set
         */
        void setMaxDepthReached(int depth) {
            if (depth > maxDepthReached) {
                maxDepthReached = depth;
            }
        }

        /**
         * @param round the maxRoundReached to set
         */
        void setMaxRoundReached(int round) {
            if (round > maxRoundReached) {
                maxRoundReached = round;
            }
        }

        /**
         *
         */
        void increaseEntityCount() {
            entities++;
        }

        /**
         *
         */
        void increaseWarningCount() {
            warnings++;
        }

        /**
         *
         */
        void increaseErrorCount() {
            errors++;
        }

        void log(Level level, String message) {
            if (foul(logger, level)) {
                return;
            }
            logger.log(level, message);
        }

        private void log(Level level, String method, Object... parameters) {
            if (foul(logger, level)) {
                return;
            }
            String message = signature(method, parameters);
            logger.log(level, message);
        }

        private boolean fair(Logger logger, Level level) {
            return logger != null && level != null && logger.isEnabledFor(level) && !level.equals(Level.OFF);
        }

        private boolean foul(Logger logger, Level level) {
            return !fair(logger, level);
        }

        void warn(Object message) {
            logger.warn(message);
            warnings++;
        }

        void error(Object message) {
            logger.error(message);
            errors++;
        }

        /**
         * @see AbstractEntity
         * @see EntityFolderAtlas
         * @see Operation
         */
        void track(int depth, int round, String path, String method, Object... parameters) {
            track(_trackingLevel, depth, round, path, signature(method, parameters), null);
        }

        /**
         * @see XS1
         */
        void track(int depth, int round, String path, Class<?> type, String name, String method, String remarks) {
            String tipo = type == null ? "" : type.getSimpleName();
            String note = tipo + "[" + name + "]" + "." + method;
            track(_trackingLevel, depth, round, path, note, remarks);
        }

        /**
         * @see XS1
         */
        void alert(int depth, int round, String path, Class<?> type, String name, String method, String remarks) {
            String tipo = type == null ? "" : type.getSimpleName();
            String note = tipo + "[" + name + "]" + "." + method;
            track(_alertLevel, depth, round, path, note, remarks);
            warnings++;
        }

        private void track(Level level, int depth, int round, String path, String note, String remarks) {
            if (foul(logger, level)) {
                return;
            }
            boolean margin = fair(logger, _trackingLevel);
            String margin1 = margin ? StringUtils.repeat(" ", 5 * depth) : "";
            String margin2 = margin ? StringUtils.repeat(" ", 5 * (depth + 1)) : TAB;
            String message = margin1;
            message += "d=" + depth + ", r=" + round + ", ";
            if (StringUtils.isNotBlank(path)) {
                message += path + ".";
            }
            message += note;
            logger.log(level, message);
            if (StringUtils.isNotBlank(remarks)) {
                logger.log(level, margin2 + remarks);
            }
        }

        private void fatal(Throwable throwable) {
            Throwable cause = ThrowableUtils.getCause(throwable);
            String message = throwable.equals(cause) ? throwable.getClass().getSimpleName() : throwable.getMessage();
            logger.fatal(message, cause);
            errors++;
        }

    }
    // </editor-fold>

}
