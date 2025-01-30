#set($dollar = '$')
#set($pound = '#')
#set($backslash = '\')
/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package ${package}.meta.util.extra;

import adalid.util.*;

/**
 * MetaJavaStates genera meta-java de máquinas de estado de su aplicación utilizando la plataforma meta-java-states.
 * <p>
 * Para cada entidad del proyecto que tenga una propiedad estado, es decir, una referencia a una enumeración decorada con la anotación StateProperty,
 * se genera una extensión con el código meta-java de la máquina de estado, a partir de las instancias definidas en la enumeración (cada instancia
 * corresponde a un estado). Las extensiones se generan en el paquete meta.entidad.ext, el cual se almacena en el subdirectorio definido por la
 * propiedad metajava.path del archivo bootstrapping.properties, y cuyo valor predeterminado es src/test/java. Para obtener más información respecto
 * al archivo bootstrapping.properties, consulte el Manual de Referencia.
 *
 * @author ADALID meta-jee2-archetype, version 6.0.0
 */
@RunnableClass
public class MetaJavaStates extends adalid.util.Utility {

    /**
     * MASTER_CLASS almacena la clase del proyecto maestro ejecutado para generar su aplicación.
     * <p>
     * El campo PROJECT_CLASS contiene inicialmente la clase del proyecto Maestro101; posteriormente la clase del último proyecto maestro ejecutado.
     */
    private static final Class<? extends adalid.commons.ProjectBuilder> MASTER_CLASS = ${package}.meta.util.Aid.PROJECT_CLASS;

    /**
     * PLATFORM almacena el nombre de la plataforma que genera meta-java de máquinas de estado de su aplicación.
     */
    private static final String PLATFORM = adalid.util.Platform.META_JAVA_STATES;

    public static void main(String[] args) throws Exception {
        newInstance(MASTER_CLASS).build(PLATFORM);
    }

}
