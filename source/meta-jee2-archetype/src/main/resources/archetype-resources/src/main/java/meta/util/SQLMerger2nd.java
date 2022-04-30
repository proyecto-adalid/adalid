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
 * SQLMerger2nd genera los archivos utilizados por el método para la actualización incremental de la base de datos. Para obtener información sobre
 * este método, consulte el apéndice "Creación de la base de datos de aplicaciones jee2", de la "Guía de Meta-Programación" del Proyecto Adalid,
 * Plataforma jee2.
 * <p>
 * SQLMerger2nd es una helper class que utiliza la clase adalid.util.sql.SqlMerger2nd. El constructor de adalid.util.sql.SqlMerger2nd requiere los
 * siguientes argumentos: manejador (dbms), servidor (host), puerto (port), usuario (user), contraseña (password), base de datos (database), esquema
 * (schema) y esquema auxiliar (newSchema). Después de newSchema, el constructor toma los siguientes argumentos opcionales: servidor auxiliar
 * (newHost), puerto del servidor auxiliar (newPort), usuario del servidor auxiliar (newUser), contraseña del usuario del servidor auxiliar
 * (newPassword) y base de datos auxiliar (newDatabase); por omisión, estos últimos argumentos toman el valor de host, user, password y database,
 * respectivamente. Para ejecutar SQLMerger2nd, utilice el IDE para especificar los argumentos del programa o agregue una propiedad al archivo
 * private.properties o al archivo bootstrapping properties. En el último caso, los argumentos deben escribirse como una lista de valores separada por
 * comas en el valor de la propiedad, de la siguiente manera:
 * <p>
 * ${package}.meta.util.SQLMerger2nd.args=postgresql, localhost, 5432, postgres, postgres, ${artifactId.toLowerCase().replaceAll('^\Qmeta\E[\_\-\.]+', '').replaceAll('[\_\-\.]+\Qmeta\E$', '').replaceAll('[^a-z0-9]', '')}ap101, public, second, , , , ,
 * <p>
 * O, para Oracle XE:
 * <p>
 * ${package}.meta.util.SQLMerger2nd.args=oracle, localhost, 1521, ${artifactId.toLowerCase().replaceAll('^\Qmeta\E[\_\-\.]+', '').replaceAll('[\_\-\.]+\Qmeta\E$', '').replaceAll('[^a-z0-9]', '').toUpperCase()}AP112, oracle, XE, ${artifactId.toLowerCase().replaceAll('^\Qmeta\E[\_\-\.]+', '').replaceAll('[\_\-\.]+\Qmeta\E$', '').replaceAll('[^a-z0-9]', '')}ap112, ${artifactId.toLowerCase().replaceAll('^\Qmeta\E[\_\-\.]+', '').replaceAll('[\_\-\.]+\Qmeta\E$', '').replaceAll('[^a-z0-9]', '')}ap112_2nd, , , ${artifactId.toLowerCase().replaceAll('^\Qmeta\E[\_\-\.]+', '').replaceAll('[\_\-\.]+\Qmeta\E$', '').replaceAll('[^a-z0-9]', '').toUpperCase()}AP112_2ND, oracle,
 * <p>
 * Alternativamente, si se utiliza más de un manejador de base de datos con frecuencia, podría escribir una propiedad para cada manejador, de la
 * siguiente manera:
 * <p>
 * adalid.util.sql.SqlMerger2nd.postgresql.args=localhost, 5432, postgres, postgres, ${artifactId.toLowerCase().replaceAll('^\Qmeta\E[\_\-\.]+', '').replaceAll('[\_\-\.]+\Qmeta\E$', '').replaceAll('[^a-z0-9]', '')}ap101, public, second, , , , ,
 * <p>
 * adalid.util.sql.SqlMerger2nd.oracle.args=localhost, 1521, ${artifactId.toLowerCase().replaceAll('^\Qmeta\E[\_\-\.]+', '').replaceAll('[\_\-\.]+\Qmeta\E$', '').replaceAll('[^a-z0-9]', '').toUpperCase()}AP112, oracle, XE, ${artifactId.toLowerCase().replaceAll('^\Qmeta\E[\_\-\.]+', '').replaceAll('[\_\-\.]+\Qmeta\E$', '').replaceAll('[^a-z0-9]', '')}ap112, ${artifactId.toLowerCase().replaceAll('^\Qmeta\E[\_\-\.]+', '').replaceAll('[\_\-\.]+\Qmeta\E$', '').replaceAll('[^a-z0-9]', '')}ap112_2nd, , , ${artifactId.toLowerCase().replaceAll('^\Qmeta\E[\_\-\.]+', '').replaceAll('[\_\-\.]+\Qmeta\E$', '').replaceAll('[^a-z0-9]', '').toUpperCase()}AP112_2ND, oracle,
 * <p>
 * En este caso, se le pedirá que elija el manejador en tiempo de ejecución para determinar la lista de argumentos a utilizar.
 * <p>
 * Para obtener mas información, lea el <b>Apéndice 3: Creación de la base de datos de aplicaciones jee2</b> de la Guía de Meta Programación.
 * <p>
 * ADVERTENCIA: SQLMerger2nd no procesa todas las tablas de la base de datos; excluye aquellas cuyo nombre comienza por ZYX_.
 *
 * @author ADALID meta-jee2-archetype
 */
public class SQLMerger2nd extends adalid.util.sql.jee2.SQLMerger2nd {

    public static void main(String[] args) {
        String[] arguments = args != null && args.length > 0 ? args : getArguments(SQLMerger2nd.class);
        merge(arguments);
    }

}
