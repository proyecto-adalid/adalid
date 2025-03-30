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
package adalid.util.sql.jee2;

import adalid.util.*;
import adalid.util.sql.*;
import meta.proyecto.comun.EntidadesComunes;
import org.apache.log4j.Logger;

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
 * meta.util.SQLMerger.args=postgresql, localhost, 5432, postgres, postgres, jee2ap101, public, former, , , , ,
 * <p>
 * O, para Oracle XE:
 * <p>
 * meta.util.SQLMerger.args=oracle, localhost, 1521, JEE2AP112, oracle, XE, jee2ap112, former, , , , ,
 * <p>
 * Alternativamente, si se utiliza más de un manejador de base de datos con frecuencia, podría escribir una propiedad para cada manejador, de la
 * siguiente manera:
 * <p>
 * adalid.util.sql.SqlMerger.postgresql.args=localhost, 5432, postgres, postgres, jee2ap101, public, former, , , , ,
 * <p>
 * adalid.util.sql.SqlMerger.oracle.args=localhost, 1521, JEE2AP112, oracle, XE, jee2ap112, former, , , , ,
 * <p>
 * En este caso, se le pedirá que elija el manejador en tiempo de ejecución para determinar la lista de argumentos a utilizar.
 * <p>
 * Para obtener mas información, lea el <b>Apéndice 3: Creación de la base de datos de aplicaciones jee2</b> de la Guía de Meta Programación.
 * <p>
 * ADVERTENCIA: SQLMerger no procesa todas las tablas de la base de datos; excluye aquellas cuyo nombre comienza por ZYX_.
 *
 * @author Jorge Campins
 */
public class SQLMerger extends Utility {

    private static final Logger logger = Logger.getLogger(SqlMerger.class);

    public static void merge(String[] args) {
        SqlMerger merger = new SqlMerger(args);
        if (merger.isInitialised()) {
            // Use method setProjectAlias to specify the project alias used to define the folder where generated scripts will be written to.
            // The project alias is also used to define the default folder used by COPY commands (see the comments on method setOldDataFolder).
            // By default or by specifying a null value, the alias will be either the Oracle schema or the PostgreSQL database name.
            // merger.setProjectAlias("jee2ap101");
            logger.info("projectAlias=" + merger.getProjectAlias());
            // Use method setOldDataFolder to specify the absolute path of the folder used by PostgreSQL's COPY commands.
            // By default or by specifying a null value, the folder will be a subfolder of the workspace directory,
            // specifically /alias/source/management/backup/dbms/oldSchema, where alias is substituted by the project alias,
            // dbms and oldSchema are substituted by their corresponding arguments, and the rest is constant.
            // PostgreSQL user must be authorized to read and write files in the specified folder.
            // merger.setOldDataFolder(folder);
            logger.info("oldDataFolder=" + merger.getOldDataFolder());
            EntidadesComunes entidadesComunes = new EntidadesComunes();
            if (entidadesComunes.build()) {
                merger.clear();
//              merger.disableDetailLogging();
                merger.setCatalogTablesMap(entidadesComunes.getCatalogTablesMap());
                merger.getExcludableTableNames().add("call_stack");
                merger.getExcludableTableNames().add("recurso");
                merger.getCurrentKeyTableNames().add("rol");
                merger.getMutableKeyTableNames().add("opcion_menu");
                merger.merge();
            }
            updateProjectBuilderDictionary(SQLMerger.class);
        }
    }

}
