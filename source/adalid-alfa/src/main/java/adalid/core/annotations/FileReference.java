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
     * autoStart específica si la operación de carga debe, o no, comenzar automáticamente al seleccionar los archivos. Su valor es uno de los
     * elementos de la enumeración Kleenean. Seleccione TRUE para comenzar la carga automáticamente; en caso contrario, seleccione FALSE.
     * Alternativamente, omita el elemento o seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del
     * atributo es FALSE.
     *
     * @return autoStart
     */
    Kleenean autoStart() default Kleenean.UNSPECIFIED; // false;

    /**
     * virusScan específica si la operación de carga debe, o no, escanear los archivos cargados. Su valor es uno de los elementos de la enumeración
     * Kleenean. Seleccione TRUE para escanear los archivos cargados; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o
     * seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es FALSE.
     *
     * @return autoStart
     */
    Kleenean virusScan() default Kleenean.UNSPECIFIED; // false;

    /**
     * fileLimit específica el número máximo de archivos que se pueden cargar en una misma operación. Su valor debe ser un número entero, mayor o
     * igual a 1 y menor o igual a 100. El valor predeterminado es 1.
     *
     * @return fileLimit
     */
    int fileLimit() default -1; // 1;

    /**
     * undoLimit específica el número máximo de veces que se puede eliminar y volver a cargar los archivos en una misma operación. Su valor debe ser
     * un número entero, mayor o igual a 0 y menor o igual a 4. El valor predeterminado es 2.
     *
     * @return undoLimit
     */
    int undoLimit() default -1; // 2;

    /**
     * max específica el tamaño máximo (en bytes) de los archivos que se pueden cargar. Su valor debe ser un número entero, mayor o igual a 0.
     * Especifique 0 para permitir la carga de archivos hasta el tamaño máximo permitido por la configuración del servidor de aplicaciones.
     * Alternativamente, omita el elemento para utilizar el valor predeterminado del atributo. El valor predeterminado es 100.000 (100 KB).
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
     * pathTemplate especifica la plantilla de ruta de los archivos cargados en el servidor de aplicaciones. La ruta del archivo se personaliza
     * mediante esta plantilla, la cual puede contener secuencias de letras, números, guiones y guiones bajos (underscores), y una o más claves de
     * elementos de datos. Las claves se deben encerrar entre llaves. La plantilla también puede contener nombres de propiedades de la entidad,
     * encerrados entre corchetes; estos nombres son reemplazados por el valor que tiene la propiedad en la instancia de la entidad que corresponde al
     * archivo cargado. Los elementos de la plantilla se pueden separar con puntos; esos puntos son reemplazados por el carácter de separación de
     * rutas propio del sistema operativo: barra diagonal para Linux y barra diagonal invertida para Windows.
     * <p>
     * Las claves de elementos de datos válidas son:
     * </p>
     * <ul>
     * <li><b>EntityName</b>: nombre de la entidad que corresponde al archivo cargado.</li>
     * <li><b>References</b>: nombre de la propiedad de la entidad, o de la operación y el parámetro, que corresponde al archivo cargado.</li>
     * <li><b>InstancePK</b>: clave primaria (id) de la instancia de la entidad que corresponde al archivo cargado.</li>
     * <li><b>InstanceBK</b>: clave de negocio (código) de la instancia de la entidad que corresponde al archivo cargado.</li>
     * <li><b>UploadDate</b>: fecha (yyyyMMdd) de carga del archivo.</li>
     * <li><b>UploadYear</b>: año (yyyy) de carga del archivo.</li>
     * <li><b>UploadMonth</b>: mes (MM) de carga del archivo.</li>
     * <li><b>UploadDay</b>: dia del mes (dd) de carga del archivo.</li>
     * <li><b>UploadTime</b>: hora (HHmm) de carga del archivo.</li>
     * <li><b>UploadUserPK</b>: clave primaria (id) del usuario que ejecuta la carga del archivo.</li>
     * <li><b>UploadUserBK</b>: clave de negocio (código) del usuario que ejecuta la carga del archivo.</li>
     * </ul>
     *
     * La plantilla predeterminada es <b>{EntityName}.{References}.{UploadDate}</b>
     *
     * @return path
     */
    String pathTemplate() default "";

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
