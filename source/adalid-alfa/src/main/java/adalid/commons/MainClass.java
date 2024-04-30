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
package adalid.commons;

/**
 * @author Jorge Campins
 */
public class MainClass {

    /*
    https://logging.apache.org/log4j/2.x/manual/migration.html
    Enabling the Log4j 1.x bridge
    Set the system property log4j1.compatibility to a value of true.
    Log4j 2 will then add log4j.properties and log4j.xml to the configuration files it searches for on the class path.
    /**/
    private static final String log4j1c = "log4j1.compatibility";

    static {
        System.setProperty(log4j1c, "false");
//      System.out.println(log4j1c + "=" + System.getProperty(log4j1c));
    }

    public static void main(String[] args) {
        init();
    }

    public static void init() {
        /*
        Logger logger = Logger.getLogger(MainClass.class);
        logger.trace(MainClass.class + "{" + log4j1c + "=" + System.getProperty(log4j1c) + "}");
        /**/
    }

}
