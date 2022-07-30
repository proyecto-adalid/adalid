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
 * CopyGitFiles sirve para copiar archivos de configuración de Git del directorio de ejemplos al directorio raíz del proyecto.
 *
 * Los archivos del directorio src/main/resources/configuration/Git son ejemplos de archivos de configuración de Git. Puede copiarlos al directorio
 * raíz del proyecto y modificar las copias si fuese necesario.
 *
 * @author Jorge Campins
 */
public class CopyGitFiles extends CopyConfigFiles {

    private static final String SOURCE = SOURCE_DIR + FILE_SEP + "Git";

    private static final String TARGET = TARGET_DIR;

    public static void copy() {
        /**/
        final String file1 = "archetype.gitignore";
        final String file2 = ".gitignore";
        final String text1 = "El archivo .gitignore determina qué archivos ignorar al guardar su proyecto al repositorio de Git.\n\n";
        /**/
        copyFile(file1, file2, SOURCE, TARGET, text1);
        /**/
        updateProjectBuilderDictionary(CopyGitFiles.class);
        /**/
    }

}
