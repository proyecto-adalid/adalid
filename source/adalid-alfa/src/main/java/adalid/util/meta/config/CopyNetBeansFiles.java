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
package adalid.util.meta.config;

/**
 * CopyNetBeansFiles sirve para copiar archivos de configuración de NetBeans del directorio de ejemplos al directorio raíz del proyecto.
 *
 * Los archivos del directorio src/main/resources/configuration/NetBeans son ejemplos de archivos de configuración de NetBeans. Puede copiarlos al
 * directorio raíz del proyecto y modificar las copias si fuese necesario.
 *
 * Este proyecto requiere JDK 17. Aun cuando la plataforma Java predeterminada de NetBeans sea JDK 17, se recomienda definir la plataforma "JDK 17" y
 * configurar el proyecto para que utilice esa plataforma en lugar de la plataforma "JDK 17 (Default)". La plataforma se define con el "Java Platform
 * Manager" de NetBeans. La configuración se suele hacer en la ventana de propiedades del proyecto, con el elemento "Java Platform" de la categoría
 * "Build/Compile". Al configurar el proyecto, se agrega el elemento netbeans.hint.jdkPlatform al archivo nb-configuration.xml; el valor de
 * netbeans.hint.jdkPlatform es el nombre de la plataforma, con un guion bajo en lugar del espacio en blanco; es decir, JDK_17. En el archivo de
 * ejemplo nb-configuration.xml se define el valor del elemento netbeans.hint.jdkPlatform. Copiar el archivo equivale solo a la configuración; no
 * tendrá efecto si la plataforma no está definida.
 *
 * En el archivo de ejemplo nbactions.xml se define la acción "Clean and Build with all the fixings"; además se definen parámetros de las acciones
 * run, run.single.main, debug y debug.single.main. Revise y modifique el valor de los parámetros si fuese necesario; en particular, revise los
 * valores de -Xms y -Xmx en las propiedades exec.args; éstos se deben ajustar según la disponibilidad de memoria de su computador.
 *
 * @author Jorge Campins
 */
public class CopyNetBeansFiles extends CopyConfigFiles {

    private static final String SOURCE = SOURCE_DIR + FILE_SEP + "NetBeans";

    private static final String TARGET = TARGET_DIR;

    public static void copy() {
        /**/
        final String file1 = "nb-configuration.xml";
        final String text1 = "Este proyecto requiere JDK 17. Aun cuando la plataforma Java predeterminada de NetBeans sea JDK 17,\n"
            + "se recomienda definir la plataforma \"JDK 17\" y configurar el proyecto para que utilice esa plataforma en\n"
            + "lugar de la plataforma \"JDK 17 (Default)\".\n\n"
            + "La plataforma se define con el \"Java Platform Manager\" de NetBeans. La configuración se suele hacer en\n"
            + "la ventana de propiedades del proyecto, con el elemento \"Java Platform\" de la categoría \"Build/Compile\".\n\n"
            + "Al configurar el proyecto, se agrega el elemento netbeans.hint.jdkPlatform al archivo nb-configuration.xml;\n"
            + "el valor de netbeans.hint.jdkPlatform es el nombre de la plataforma, con un guion bajo en lugar del espacio\n"
            + "en blanco; es decir, JDK_17.\n\n"
            + "En el archivo de ejemplo nb-configuration.xml se define el valor del elemento netbeans.hint.jdkPlatform.\n"
            + "Copiar el archivo equivale solo a la configuración; no tendrá efecto si la plataforma no está definida.\n\n";
        /**/
        final String file2 = "nbactions.xml";
        final String text2 = "En el archivo de ejemplo nbactions.xml se define la acción \"Clean and Build with all the fixings\";\n"
            + "además se definen parámetros de las acciones run, run.single.main, debug y debug.single.main.\n\n"
            + "Revise y modifique el valor de los parámetros si fuese necesario; en particular, revise los valores\n"
            + "de -Xms y -Xmx en las propiedades exec.args;  éstos se deben ajustar según la disponibilidad de\n"
            + "memoria de su computador.\n\n";
        /**/
        copyFile(file1, SOURCE, TARGET, text1);
        copyFile(file2, SOURCE, TARGET, text2);
        /**/
        updateProjectBuilderDictionary(CopyNetBeansFiles.class);
        /**/
    }

}
