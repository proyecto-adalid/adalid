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
 * SQLMerger genera los archivos utilizados por el método alternativo para la migración de datos. Para obtener información sobre este método, consulte
 * el apéndice "Creación de la base de datos de aplicaciones jee2", de la "Guía de Meta-Programación" del Proyecto Adalid, Plataforma jee2.
 * <p>
 * SQLMerger es una helper class que utiliza la clase adalid.util.sql.SqlMerger. El constructor de adalid.util.sql.SqlMerger requiere los siguientes
 * argumentos: manejador (dbms), servidor (host), puerto (port), usuario (user), contraseña (password), base de datos (database), esquema (schema) y
 * esquema anterior (oldSchema). Después de oldSchema, el constructor toma los siguientes argumentos opcionales: servidor anterior (oldHost), puerto
 * del servidor anterior (oldPort), usuario del servidor anterior (oldUser), contraseña del usuario del servidor anterior (oldPassword) y base de
 * datos anterior (oldDatabase); por omisión, estos últimos argumentos toman el valor de host, user, password y database, respectivamente. Para
 * ejecutar SQLMerger, utilice el IDE para especificar los argumentos del programa o agregue una propiedad al archivo private.properties o al archivo
 * bootstrapping properties. En el último caso, los argumentos deben escribirse como una lista de valores separada por comas en el valor de la
 * propiedad, de la siguiente manera:
 * <p>
 * ${package}.meta.util.SQLMerger.args=postgresql, localhost, 5432, postgres, postgres, ${artifactId.toLowerCase().replaceAll('^\Qmeta\E[\_\-\.]+', '').replaceAll('[\_\-\.]+\Qmeta\E$', '').replaceAll('[^a-z0-9]', '')}ap101, public, former, , , , ,
 * <p>
 * O, para Oracle XE:
 * <p>
 * ${package}.meta.util.SQLMerger.args=oracle, localhost, 1521, ${artifactId.toLowerCase().replaceAll('^\Qmeta\E[\_\-\.]+', '').replaceAll('[\_\-\.]+\Qmeta\E$', '').replaceAll('[^a-z0-9]', '').toUpperCase()}AP112, oracle, XE, ${artifactId.toLowerCase().replaceAll('^\Qmeta\E[\_\-\.]+', '').replaceAll('[\_\-\.]+\Qmeta\E$', '').replaceAll('[^a-z0-9]', '')}ap112, former, , , , ,
 * <p>
 * Alternativamente, si se utiliza más de un manejador de base de datos con frecuencia, podría escribir una propiedad para cada manejador, de la
 * siguiente manera:
 * <p>
 * adalid.util.sql.SqlMerger.postgresql.args=localhost, 5432, postgres, postgres, ${artifactId.toLowerCase().replaceAll('^\Qmeta\E[\_\-\.]+', '').replaceAll('[\_\-\.]+\Qmeta\E$', '').replaceAll('[^a-z0-9]', '')}ap101, public, former, , , , ,
 * <p>
 * adalid.util.sql.SqlMerger.oracle.args=localhost, 1521, ${artifactId.toLowerCase().replaceAll('^\Qmeta\E[\_\-\.]+', '').replaceAll('[\_\-\.]+\Qmeta\E$', '').replaceAll('[^a-z0-9]', '').toUpperCase()}AP112, oracle, XE, ${artifactId.toLowerCase().replaceAll('^\Qmeta\E[\_\-\.]+', '').replaceAll('[\_\-\.]+\Qmeta\E$', '').replaceAll('[^a-z0-9]', '')}ap112, former, , , , ,
 * <p>
 * En este caso, se le pedirá que elija el manejador en tiempo de ejecución para determinar la lista de argumentos a utilizar.
 * <p>
 * Para obtener mas información, lea el <b>Apéndice 3: Creación de la base de datos de aplicaciones jee2</b> de la Guía de Meta Programación.
 * <p>
 * ADVERTENCIA: SQLMerger no procesa todas las tablas de la base de datos; excluye aquellas cuyo nombre comienza por ZYX_.
 *
 * @author ADALID meta-jee2-archetype
 */
public class SQLMerger extends adalid.util.sql.jee2.SQLMerger {

    public static void main(String[] args) {
        String[] arguments = args != null && args.length > 0 ? args : getArguments(SQLMerger.class);
        merge(arguments);
    }

}
