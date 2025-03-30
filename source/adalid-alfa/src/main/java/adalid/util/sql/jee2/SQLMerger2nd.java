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
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

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
 * meta.util.SQLMerger2nd.args=postgresql, localhost, 5432, postgres, postgres, jee2ap101, public, second, , , , ,
 * <p>
 * O, para Oracle XE:
 * <p>
 * meta.util.SQLMerger2nd.args=oracle, localhost, 1521, JEE2AP112, oracle, XE, jee2ap112, jee2ap112_2nd, , , JEE2AP112_2ND, oracle,
 * <p>
 * Alternativamente, si se utiliza más de un manejador de base de datos con frecuencia, podría escribir una propiedad para cada manejador, de la
 * siguiente manera:
 * <p>
 * adalid.util.sql.SqlMerger2nd.postgresql.args=localhost, 5432, postgres, postgres, jee2ap101, public, second, , , , ,
 * <p>
 * adalid.util.sql.SqlMerger2nd.oracle.args=localhost, 1521, JEE2AP112, oracle, XE, jee2ap112, jee2ap112_2nd, , , JEE2AP112_2ND, oracle,
 * <p>
 * En este caso, se le pedirá que elija el manejador en tiempo de ejecución para determinar la lista de argumentos a utilizar.
 * <p>
 * Para obtener mas información, lea el <b>Apéndice 3: Creación de la base de datos de aplicaciones jee2</b> de la Guía de Meta Programación.
 * <p>
 * ADVERTENCIA: SQLMerger2nd no procesa todas las tablas de la base de datos; excluye aquellas cuyo nombre comienza por ZYX_.
 *
 * @author Jorge Campins
 */
public class SQLMerger2nd extends Utility {

    private static final Logger logger = Logger.getLogger(SqlMerger2nd.class);

    private static String _projectAlias;

    public static String getProjectAlias() {
        return _projectAlias;
    }

    public static void setProjectAlias(String alias) {
        _projectAlias = alias;
    }

    public static void merge(String[] args) {
        SqlMerger2nd merger = new SqlMerger2nd(args);
        if (merger.isInitialised()) {
            // Use method setProjectAlias to specify the project alias used to define the folder where generated scripts will be written to.
            // The project alias is also used to define the default folder used by COPY commands (see the comments on method setOldDataFolder).
            // By default or by specifying a null value, the alias will be either the Oracle schema or the PostgreSQL database name.
            // merger.setProjectAlias("jee2ap101");
            if (StringUtils.isNotBlank(_projectAlias)) {
                merger.setProjectAlias(_projectAlias);
            }
            logger.info("projectAlias=" + merger.getProjectAlias());
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
            updateProjectBuilderDictionary(SQLMerger2nd.class);
        }
    }

}
