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
 * CopyGitFiles sirve para copiar archivos de configuración de Git del directorio de ejemplos al directorio raíz del proyecto.
 *
 * Los archivos del directorio src/main/resources/configuration/Git son ejemplos de archivos de configuración de Git. Puede copiarlos al directorio
 * raíz del proyecto y modificar las copias si fuese necesario.
 *
 * @author ADALID meta-jee2-archetype
 */
@RunnableClass(false)
public class CopyGitFiles extends adalid.util.meta.config.CopyGitFiles {

    public static void main(String[] args) {
        copy();
    }

}
