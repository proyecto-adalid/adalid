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
package adalid.jee2.meta.proyecto.base;

/**
 * @author Jorge Campins
 */
public abstract class ProyectoPrime extends ProyectoMavenPrime {

    protected static final String PLATAFORMA_MAVEN_POSTGRESQL_GLASSFISH = "jee2af101";

    protected static final String PLATAFORMA_MAVEN_POSTGRESQL_WILDFLY = "jee2af102";

    protected static final String PLATAFORMA_MAVEN_ORACLE_GLASSFISH = "jee2af111";

    protected static final String PLATAFORMA_MAVEN_ORACLE_WILDFLY = "jee2af112";

    protected static final String PLATAFORMA_MAVEN_POSTGRESQL_GLASSFISH_SBDD = "jee2ag101";

    protected static final String PLATAFORMA_MAVEN_POSTGRESQL_WILDFLY_SBDD = "jee2ag102";

    protected static final String PLATAFORMA_MAVEN_ORACLE_GLASSFISH_SBDD = "jee2ag111";

    protected static final String PLATAFORMA_MAVEN_ORACLE_WILDFLY_SBDD = "jee2ag112";

    protected static final String PLATAFORMA_POSTGRESQL = "postgresql";

    protected static final String PLATAFORMA_ORACLE = "oracle";

    protected static final String POSTGRESQL_HOME_DIR_LINUX = "postgresql.home.dir.linux";

    protected static final String POSTGRESQL_HOME_DIR_WINDOWS = "postgresql.home.dir.windows";

    protected static final String POSTGRESQL_DRIVER_ARTIFACT_ID = "postgresql.driver.artifact.id";

    protected static final String POSTGRESQL_DRIVER_GROUP_ID = "postgresql.driver.group.id";

    protected static final String POSTGRESQL_DRIVER_ID = "postgresql.driver.id";

    protected static final String POSTGRESQL_DRIVER_JAR = "postgresql.driver.jar";

    protected static final String POSTGRESQL_DRIVER_VERSION = "postgresql.driver.version";

    protected static final String POSTGRESQL_VERSION = "postgresql.version";

    protected static final String VERSION_POSTGRESQL = POSTGRESQL_VERSION;

    protected static final String ORACLE_HOME_DIR_LINUX = "oracle.home.dir.linux";

    protected static final String ORACLE_HOME_DIR_WINDOWS = "oracle.home.dir.windows";

    protected static final String ORACLE_DRIVER_ARTIFACT_ID = "oracle.driver.artifact.id";

    protected static final String ORACLE_DRIVER_GROUP_ID = "oracle.driver.group.id";

    protected static final String ORACLE_DRIVER_ID = "oracle.driver.id";

    protected static final String ORACLE_DRIVER_JAR = "oracle.driver.jar";

    protected static final String ORACLE_DRIVER_VERSION = "oracle.driver.version";

    protected static final String ORACLE_SERVICE = "oracle.service";

    protected static final String ORACLE_VERSION = "oracle.version";

    protected static final String VERSION_ORACLE = ORACLE_VERSION;

    protected static final String ECLIPSELINK_VERSION = "eclipselink.version";

    protected static final String VERSION_ECLIPSELINK = ECLIPSELINK_VERSION;

    protected static final String GLASSFISH_HOME_DIR_LINUX = "glassfish.home.dir.linux";

    protected static final String GLASSFISH_HOME_DIR_WINDOWS = "glassfish.home.dir.windows";

    protected static final String GLASSFISH_VERSION = "glassfish.version";

    protected static final String VERSION_GLASSFISH = GLASSFISH_VERSION;

    protected static final String WILDFLY_HOME_DIR_LINUX = "jboss.home.dir.linux";

    protected static final String WILDFLY_HOME_DIR_WINDOWS = "jboss.home.dir.windows";

    protected static final String WILDFLY_VERSION = "jboss.version";

    protected static final String VERSION_WILDFLY = WILDFLY_VERSION;

}
