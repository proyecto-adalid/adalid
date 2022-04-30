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
package adalid.core.annotations;

import adalid.core.enums.*;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * La anotación FileReference se utiliza para designar propiedades y parámetros String como referencias a archivos cargados en el servidor.
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FileReference {

    /**
     * autoStart específica si la operación de carga debe, o no, comenzar automáticamente al seleccionar los archivos. Especifique true si desea que
     * la operación de carga comience automáticamente; de lo contrario false. Alternativamente, omita el elemento para utilizar el valor
     * predeterminado del atributo. El valor predeterminado es false.
     *
     * @return autoStart
     */
    Kleenean autoStart() default Kleenean.UNSPECIFIED; // false;

    /**
     * fileLimit específica el número máximo de archivos que se pueden cargar en una misma operación. Su valor debe ser un número entero, mayor o
     * igual a 1 y menor o igual a 100. El valor predeterminado es 1.
     *
     * @return fileLimit
     */
    int fileLimit() default -1; // 1;

    /**
     * max específica el tamaño máximo (en bytes) de los archivos que se pueden cargar. Su valor debe ser un número entero, mayor o igual a 0.
     * Especifique 0 para permitir la carga de archivos hasta el tamaño máximo permitido por la configuración del servidor de aplicaciones.
     * Alternativamente, omita el elemento para utilizar el valor predeterminado del atributo. El valor predeterminado es 1.000.000 (1 MB).
     *
     * @return max
     */
    int max() default -1; // Constants.DEFAULT_MAX_INPUT_FILE_SIZE

    /**
     * types especifica una o más extensiones MIME (Multipurpose Internet Mail Extensions) que puede cargar la operación. Su valor es una lista de
     * elementos de la enumeración MimeType. Omita el elemento para permitir la carga de archivos con cualquier extensión. El valor de este elemento
     * también se puede especificar mediante el método setValidInputFileTypes.
     *
     * @return types
     */
    MimeType[] types() default {};

    /**
     * regex especifica la expresión regular que deben satisfacer los nombres de los archivos que se pueden cargar. Para más información sobre
     * expresiones regulares consulte la documentación de Java (la página Regular Expressions es un buen punto de partida). Advertencia: la expresión
     * debe ser válida tanto en Java como en JavaScript y, por lo tanto, no debe contener características específicas de ninguno de los dos lenguajes.
     *
     * @return regex
     */
    String regex() default "";

    /**
     * storage especifica el tipo de almacenamiento de los archivos cargados. Su valor es uno de los elementos de la enumeración UploadStorageOption.
     * Seleccione FILE, ROW o ROW_AND_FILE para almacenar el archivo en el servidor de aplicaciones (web), en la base de datos, o en ambos,
     * respectivamente. Alternativamente, omita el elemento para utilizar el valor predeterminado del atributo. El valor predeterminado es
     * ROW_AND_FILE.
     *
     * @return storage
     */
    UploadStorageOption storage() default UploadStorageOption.UNSPECIFIED;

    /**
     * blobField especifica el nombre de la propiedad donde se almacena el contenido del archivo. Este elemento es relevante solo si el valor
     * especificado, o determinado, para el elemento storage es ROW o ROW_AND_FILE.
     *
     * @return blobField
     */
    String blobField() default "";

    /**
     * joinField especifica el nombre de la propiedad que hace referencia a la tabla de la base de datos donde se almacena el contenido del archivo.
     * Este elemento es relevante solo si el valor especificado, o determinado, para el elemento storage es ROW o ROW_AND_FILE.
     *
     * @return joinField
     */
    String joinField() default "";

    /**
     * loadField especifica el nombre de la propiedad donde se almacena la fecha y la hora en que se cargó el archivo. La clase de la propiedad
     * especificada debe ser DateProperty o TimestampProperty.
     *
     * @return loadField
     */
    String loadField() default "";

    /**
     * textField especifica el nombre de la propiedad donde se almacena la descripción del archivo. La clase de la propiedad especificada debe ser
     * StringProperty.
     *
     * @return textField
     */
    String textField() default "";

}
