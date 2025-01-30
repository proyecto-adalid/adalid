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

import adalid.util.*;

/**
 * SQLWriter genera meta-java de entidades a partir de las tablas de una base de datos. Para obtener información al respecto, consulte el apéndice
 * "Generación de meta-java a partir de una base de datos", de la "Guía de Meta-Programación" del Proyecto Adalid, Plataforma jee2.
 * <p>
 * SQLWriter es una helper class que utiliza la clase adalid.util.sql.SqlWriter, la cual a su vez utiliza la plataforma meta-java-sql, para generar
 * meta-java a partir de una base de datos. El constructor de adalid.util.sql.SqlWriter requiere los siguientes argumentos: manejador (dbms), servidor
 * (host), puerto (port), usuario (user), contraseña (password), base de datos (database) y esquema (schema). Para ejecutar SQLWriter, utilice el IDE
 * para especificar los argumentos del programa o agregue una propiedad al archivo private.properties o al archivo bootstrapping properties. En el
 * último caso, los argumentos deben escribirse como una lista de valores separada por comas en el valor de la propiedad; por ejemplo:
 * <ul>
 * <li>Para conectarse a PostgreSQL y generar a partir del esquema public de la base de datos zxcvbnm:
 * <ul>
 * <li>${package}.meta.util.SQLWriter.args=postgresql, localhost, 5432, postgres, postgres, zxcvbnm, public
 * </ul>
 * <li>Para conectarse a Oracle XE 18c y generar a partir del esquema zxcvbnm de la base de datos de tipo "pluggable" (pluggable database) XEPDB1:
 * <ul>
 * <li>${package}.meta.util.SQLWriter.args=oracle, localhost, 1521, ${artifactId.toLowerCase().replaceAll('^\Qmeta\E[\_\-\.]+', '').replaceAll('[\_\-\.]+\Qmeta\E$', '').replaceAll('[^a-z0-9]', '').toUpperCase()}AP112, oracle, XEPDB1, zxcvbnm
 * </ul>
 * </ul>
 * ADVERTENCIA: para conectarse a una base de datos Oracle es necesario agregar el correspondiente driver a las dependencias del proyecto. Revise los
 * comentarios generados en el archivo pom.xml.
 * <p>
 * Alternativamente, si se utiliza más de un manejador de base de datos con frecuencia, podría escribir una propiedad para cada manejador, de la
 * siguiente manera:
 * <ul>
 * <li>Para conectarse a PostgreSQL y generar a partir del esquema public de la base de datos zxcvbnm:
 * <ul>
 * <li>adalid.util.sql.SqlWriter.postgresql.args=localhost, 5432, postgres, postgres, zxcvbnm, public
 * </ul>
 * <li>Para conectarse a Oracle XE 18c y generar a partir del esquema zxcvbnm de la base de datos de tipo "pluggable" XEPDB1:
 * <ul>
 * <li>adalid.util.sql.SqlWriter.oracle.args=localhost, 1521, ${artifactId.toLowerCase().replaceAll('^\Qmeta\E[\_\-\.]+', '').replaceAll('[\_\-\.]+\Qmeta\E$', '').replaceAll('[^a-z0-9]', '').toUpperCase()}AP112, oracle, XEPDB1, zxcvbnm
 * </ul>
 * </ul>
 * En este caso, se le pedirá que elija el manejador en tiempo de ejecución para determinar la lista de argumentos a utilizar.
 * <p>
 * Las meta clases se generan en el paquete meta, el cual se almacena en el subdirectorio definido por la propiedad metajava.path del archivo
 * bootstrapping.properties, y cuyo valor predeterminado es src/test/java. Para obtener más información respecto al archivo bootstrapping.properties,
 * consulte el Manual de Referencia.
 * <p>
 * ADVERTENCIA: SQLWriter no procesa todas las tablas de la base de datos; excluye aquellas cuyo nombre comienza por ZYX_.
 *
 * @author ADALID meta-jee2-archetype, version 6.0.0
 */
@RunnableClass
public class SQLWriter extends adalid.util.sql.jee2.SQLWriter {

    public static void main(String[] args) {
        String[] arguments = args != null && args.length > 0 ? args : getArguments(SQLWriter.class);
        write(arguments);
    }

}
