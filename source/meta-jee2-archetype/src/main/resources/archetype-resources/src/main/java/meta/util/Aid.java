#set($dollar = '$')
#set($pound = '#')
#set($backslash = '\')
/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package ${package}.meta.util;

/**
 * Aid contiene campos utilizables por las demás clases utilitarias.
 *
 * @author ADALID meta-jee2-archetype, version 6.0.0
 */
public class Aid {

    /**
     * FIRST_ALIAS contiene el alias del proyecto Maestro101.
     */
    public static final String FIRST_ALIAS = ${package}.meta.proyecto.Maestro101.ALIAS;

    /**
     * LAST_ALIAS contiene el alias del último proyecto maestro ejecutado.
     */
    public static final String LAST_ALIAS = adalid.util.Utility.getLastExecutedProjectAlias();

    /**
     * PROJECT_ALIAS contiene inicialmente el alias del proyecto Maestro101; posteriormente el alias del último proyecto maestro ejecutado.
     */
    public static final String PROJECT_ALIAS = LAST_ALIAS != null ? LAST_ALIAS : FIRST_ALIAS;

    /**
     * FIRST_BASE contiene el nombre predeterminado del directorio base del proyecto Maestro101.
     * <p>
     * El directorio base del proyecto se crea en el directorio raíz del workspace; su nombre predeterminado es el alias del proyecto.
     */
    public static final String FIRST_BASE = ${package}.meta.proyecto.Maestro101.ALIAS;

    /**
     * LAST_BASE contiene el nombre del directorio base del último proyecto maestro ejecutado.
     */
    public static final String LAST_BASE = adalid.util.Utility.getLastExecutedProjectBaseFolderName();

    /**
     * PROJECT_BASE contiene inicialmente el nombre predeterminado del directorio base del proyecto Maestro101; posteriormente el nombre del
     * directorio base del último proyecto maestro ejecutado.
     */
    public static final String PROJECT_BASE = LAST_BASE != null ? LAST_BASE : FIRST_BASE;

    /**
     * FIRST_CLASS contiene la clase del proyecto Maestro101.
     */
    public static final Class<? extends adalid.commons.ProjectBuilder> FIRST_CLASS = ${package}.meta.proyecto.Maestro101.class;

    /**
     * LAST_CLASS contiene la clase del último proyecto maestro ejecutado.
     */
    public static final Class<? extends adalid.commons.ProjectBuilder> LAST_CLASS = adalid.util.Utility.getLastExecutedProjectClass();

    /**
     * PROJECT_CLASS contiene inicialmente la clase del proyecto Maestro101; posteriormente la clase del último proyecto maestro ejecutado.
     */
    public static final Class<? extends adalid.commons.ProjectBuilder> PROJECT_CLASS = LAST_CLASS != null ? LAST_CLASS : FIRST_CLASS;

}
