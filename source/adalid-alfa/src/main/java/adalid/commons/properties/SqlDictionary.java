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
package adalid.commons.properties;

import org.apache.commons.collections.ExtendedProperties;

/**
 * @author Jorge Campins
 */
public class SqlDictionary extends Dictionary {

    private static final ExtendedProperties bootstrapping = PropertiesHandler.getBootstrapping();

    private static final String DFLS = bootstrapping.getString("sql.dictionary.file.line.separator", "UNSPECIFIED");

    private static final String RESOURCES = USER_DIR + FILE_SEP + bootstrapping.getString("sql.dictionary.path", "resources").replace("/", FILE_SEP);

    private static final String DICTIONARY_DIR = RESOURCES + FILE_SEP + "dictionary";

    private static final String DICTIONARY_FILE_LINE_SEPARATOR
        = DFLS.equalsIgnoreCase("CRLF") ? "\r\n"
        : DFLS.equalsIgnoreCase("LF") ? "\n"
        : System.lineSeparator();

    public static SqlDictionary load(Class<?> clazz, String subfolder) {
        return new SqlDictionary(clazz, subfolder);
    }

    SqlDictionary(Class<?> clazz, String subfolder) {
        super(clazz, DICTIONARY_DIR, subfolder, DICTIONARY_FILE_LINE_SEPARATOR, true);
    }

}
