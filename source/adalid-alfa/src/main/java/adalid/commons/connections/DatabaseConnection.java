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
package adalid.commons.connections;

/**
 * @author Jorge Campins
 */
public interface DatabaseConnection {

    default String getName() {
        return getClass().getSimpleName().toLowerCase();
    }

    String getDriver();

    String getDriverClass();

    String getDriverModule();

    String getUrl();

    default String getSchema() {
        return null;
    }

    default String getUser() {
        return null;
    }

    default String getPassword() {
        return null;
    }

}
