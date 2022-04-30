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
package adalid.jee2;

import org.apache.commons.lang.StringUtils;
import org.apache.maven.artifact.versioning.ComparableVersion;

/**
 * @author Jorge Campins
 */
public class ProjectDependency {

    // <editor-fold defaultstate="collapsed" desc="static fields">
    private static final String COMPILE = ProjectDependencyScope.COMPILE.getMavenScope();

    private static final String PROVIDED = ProjectDependencyScope.PROVIDED.getMavenScope();

    private static final String RUNTIME = ProjectDependencyScope.RUNTIME.getMavenScope();

    private static final String TEST = ProjectDependencyScope.TEST.getMavenScope();

    private static final String JAR = ProjectDependencyType.JAR.getMavenType();
    // </editor-fold>

    private final String _groupId, _artifactId;

    private final boolean _valid;

    private String _version, _classifier, _scope, /*_systemPath,*/ _type; // SYSTEM is deprecated

    private boolean _optional;

    public static final ProjectDependency of(String groupId, String artifactId, String version) {
        return new ProjectDependency(groupId, artifactId, version);
    }

    private ProjectDependency(String groupId, String artifactId, String version) {
        _groupId = StringUtils.trimToNull(groupId);
        _artifactId = StringUtils.trimToNull(artifactId);
        _version = StringUtils.trimToNull(version);
        _valid = _groupId != null && _artifactId != null && _version != null;
    }

    /**
     * @return the dependency map key
     */
    public String getKey() {
        return _groupId + "§" + _artifactId + "§" + _classifier + "§" + (_type == null ? JAR : _type);
    }

    /**
     * @return the group ID
     */
    public String getGroupId() {
        return _groupId;
    }

    /**
     * @return the artifact ID
     */
    public String getArtifactId() {
        return _artifactId;
    }

    /**
     * @return the version
     */
    public String getVersion() {
        return _version;
    }

    /**
     * The classifier element distinguishes artifacts that were built from the same POM but differ in content. It is some optional and arbitrary
     * string that - if present - is appended to the artifact name just after the version number.
     *
     * As a motivation for this element, consider for example a project that offers an artifact targeting Java 11 but at the same time also an
     * artifact that still supports Java 1.8. The first artifact could be equipped with the classifier jdk11 and the second one with jdk8 such that
     * clients can choose which one to use.
     *
     * Another common use case for classifiers is to attach secondary artifacts to the project's main artifact. If you browse the Maven central
     * repository, you will notice that the classifiers sources and javadoc are used to deploy the project source code and API docs along with the
     * packaged class files.
     *
     * @return the classifier
     */
    public String getClassifier() {
        return _classifier;
    }

    /**
     * @param classifier the classifier to set
     * @return this
     */
    public ProjectDependency setClassifier(String classifier) {
        _classifier = StringUtils.trimToNull(classifier);
        return this;
    }

    /**
     * @param classifier the classifier to set
     * @return this
     */
    public ProjectDependency setClassifier(ProjectDependencyClassifier classifier) {
        _classifier = classifier == null ? null : classifier.getMavenClassifier();
        return this;
    }

    /**
     * The scope element refers to the classpath of the task at hand (compiling and runtime, testing, etc.) as well as how to limit the transitivity
     * of a dependency. There are five scopes available:
     *
     * <ul>
     * <li>compile - this is the default scope, used if none is specified. Compile dependencies are available in all classpaths. Furthermore, those
     * dependencies are propagated to dependent projects.
     * <li>provided - this is much like compile, but indicates you expect the JDK or a container to provide it at runtime. It is only available on the
     * compilation and test classpath, and is not transitive.
     * <li>runtime - this scope indicates that the dependency is not required for compilation, but is for execution. It is in the runtime and test
     * classpaths, but not the compile classpath.
     * <li>test - this scope indicates that the dependency is not required for normal use of the application, and is only available for the test
     * compilation and execution phases. It is not transitive.
     * <li>system - this scope is similar to provided except that you have to provide the JAR which contains it explicitly. The artifact is always
     * available and is not looked up in a repository.
     * </ul>
     *
     * @return the scope
     */
    public String getScope() {
        return _scope;
    }

    /**
     * The scope element refers to the classpath of the task at hand (compiling and runtime, testing, etc.) as well as how to limit the transitivity
     * of a dependency.
     *
     * @return the scope
     */
    public String getValidScope() {
        return _scope == null ? ProjectDependencyScope.COMPILE.getMavenScope() : _scope;
    }

    /**
     * @param scope the scope to set
     * @return this
     */
    public ProjectDependency setScope(String scope) {
        return setScope(ProjectDependencyScope.of(scope));
    }

    /**
     * @param scope the scope to set
     * @return this
     */
    public ProjectDependency setScope(ProjectDependencyScope scope) {
        _scope = scope == null ? null : scope.getMavenScope();
        return this;
    }

    // <editor-fold defaultstate="collapsed" desc="getSystemPath/setSystemPath">
    /**
     * The systemPath element is used only if the dependency scope is system. Otherwise, the build will fail if this element is set. The path must be
     * absolute, so it is recommended to use a property to specify the machine-specific path (more on properties below), such as ${java.home}/lib.
     * Since it is assumed that system scope dependencies are installed a priori, Maven does not check the repositories for the project, but instead
     * checks to ensure that the file exists. If not, Maven fails the build and suggests that you download and install it manually.
     *
     * @return the system path
     */
    /*
    public String getSystemPath() {
        return _systemPath;
    }
    /**/
    char getSystemPath;

    /**
     * @param systemPath the system path to set
     * @return this
     */
    /*
    public ProjectDependency setSystemPath(String systemPath) {
        _systemPath = StringUtils.trimToNull(systemPath);
        return this;
    }
    /**/
    char setSystemPath;
    // </editor-fold>

    /**
     * The type element corresponds to the chosen dependency type. This defaults to jar. While it usually represents the extension on the filename of
     * the dependency, that is not always the case: a type can be mapped to a different extension and a classifier. The type often corresponds to the
     * packaging used, though this is also not always the case. Some examples are jar, ejb-client and test-jar: see default artifact handlers for a
     * list. New types can be defined by plugins that set extensions to true, so this is not a complete list.
     *
     * @return the type
     */
    public String getType() {
        return _type;
    }

    /**
     * The type element corresponds to the chosen dependency type. This defaults to jar. While it usually represents the extension on the filename of
     * the dependency, that is not always the case: a type can be mapped to a different extension and a classifier. The type often corresponds to the
     * packaging used, though this is also not always the case. Some examples are jar, ejb-client and test-jar: see default artifact handlers for a
     * list. New types can be defined by plugins that set extensions to true, so this is not a complete list.
     *
     * @return the type
     */
    public String getValidType() {
        return _type == null ? ProjectDependencyType.JAR.getMavenType() : _type;
    }

    /**
     * @param type the type to set
     * @return this
     */
    public ProjectDependency setType(String type) {
        return setType(ProjectDependencyType.of(type));
    }

    /**
     * @param type the type to set
     * @return this
     */
    public ProjectDependency setType(ProjectDependencyType type) {
        _type = type == null ? null : type.getMavenType();
        return this;
    }

    /**
     * The optional element marks a dependency optional when this project itself is a dependency. For example, imagine a project A that depends upon
     * project B to compile a portion of code that may not be used at runtime, then we may have no need for project B for all project. So if project X
     * adds project A as its own dependency, then Maven does not need to install project B at all. In the shortest terms, optional lets other projects
     * know that, when you use this project, you do not require this dependency in order to work correctly.
     *
     * @return the optional indicator
     */
    public boolean isOptional() {
        return _optional;
    }

    /**
     * @param optional the optional indicator to set
     * @return this
     */
    public ProjectDependency setOptional(boolean optional) {
        _optional = optional;
        return this;
    }

    /**
     * @return the validity indicator
     */
    public boolean isValid() {
        return _valid;
    }

    /**
     * @param dependency the dependency to merge
     * @return this
     */
    public ProjectDependency merge(ProjectDependency dependency) {
        if (dependency != null) {
            mergeVersion(dependency.getVersion());
            mergeScope(ProjectDependencyScope.of(dependency.getScope()));
//          mergeSystemPath(dependency.getSystemPath());
            mergeOptional(dependency.isOptional());
        }
        return this;
    }

    /**
     * Sets the version if and only if the parameter is a newer version than the one already set
     *
     * @param version the version to set
     * @return this
     */
    private ProjectDependency mergeVersion(String version) {
        String aversion = StringUtils.trimToNull(version);
        if (aversion == null) {
            // keep current version
        } else if (_version == null) {
            _version = aversion;
        } else {
            ComparableVersion thatVersion = new ComparableVersion(aversion);
            ComparableVersion thisVersion = new ComparableVersion(_version);
            if (thatVersion.compareTo(thisVersion) > 0) {
                _version = aversion;
            }
        }
        return this;
    }

    /**
     * Sets the scope if and only if the parameter is a broader scope than the one already set
     *
     * @param scope the scope to set
     * @return this
     */
    private ProjectDependency mergeScope(ProjectDependencyScope scope) {
        String ascope = scope == null ? null : scope.getMavenScope();
        if (ascope == null || _scope == null) {
            _scope = null;
        } else if (ascope.equals(_scope)) {
            // keep current scope
        } else if (ascope.equals(COMPILE) || _scope.equals(COMPILE)) {
            _scope = COMPILE;
        } else if (ascope.equals(PROVIDED) && _scope.equals(RUNTIME)) {
            _scope = COMPILE;
        } else if (ascope.equals(RUNTIME) && _scope.equals(PROVIDED)) {
            _scope = COMPILE;
        } else if (ascope.equals(PROVIDED) && _scope.equals(TEST)) {
            _scope = PROVIDED;
        } else if (ascope.equals(TEST) && _scope.equals(PROVIDED)) {
            // keep current scope (PROVIDED)
        } else if (ascope.equals(RUNTIME) && _scope.equals(TEST)) {
            _scope = RUNTIME;
        } else if (ascope.equals(TEST) && _scope.equals(RUNTIME)) {
            // keep current scope (RUNTIME)
        } else {
            _scope = null;
        }
        return this;
    }

    /**
     * Sets the optional indicator if and only if both the parameter and the current value are true
     *
     * @param optional the optional indicator to set
     * @return this
     */
    public ProjectDependency mergeOptional(boolean optional) {
        _optional &= optional;
        return this;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + getKey() + ": " + _version + ", " + _scope + ", " + _optional + ")";
    }

}
