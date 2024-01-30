#set($dollar = '$')
#set($pound = '#')
#set($backslash = '\')
/*
 * Este programa es software libre; usted puede redistribuirlo y/o modificarlo bajo los terminos
 * de la licencia "GNU General Public License" publicada por la Fundacion "Free Software Foundation".
 * Este programa se distribuye con la esperanza de que pueda ser util, pero SIN NINGUNA GARANTIA;
 * vea la licencia "GNU General Public License" para obtener mas informacion.
 */
package ${package}.meta.util.config;

import adalid.util.*;

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
 * @author ADALID meta-jee2-archetype
 */
@RunnableClass(false)
public class CopyNetBeansFiles extends adalid.util.meta.config.CopyNetBeansFiles {

    public static void main(String[] args) {
        copy();
    }

}
