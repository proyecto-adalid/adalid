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

import adalid.core.*;
import adalid.core.enums.*;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * La anotación UrlProperty se utiliza para designar una propiedad como propiedad URL de la entidad. Cada entidad puede tener una sola propiedad URL
 * (para especificar los atributos de otras propiedades de la entidad que contengan una URL se utiliza la anotación UniformResourceLocator). Solo se
 * puede designar como propiedad URL a propiedades de la clase StringProperty.
 *
 * @author Jorge Campins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface UrlProperty {

    /**
     * urlType indica el tipo de URL. Su valor es uno de los elementos de la enumeración UrlType. Seleccione INTERNAL si la URL corresponde a una
     * vista (página) de la aplicación generada; en caso contrario seleccione EXTERNAL. Alternativamente, omita el elemento o seleccione UNSPECIFIED
     * para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es EXTERNAL.
     *
     * @return urlType
     */
    UrlType urlType() default UrlType.UNSPECIFIED;

    /**
     * urlDisplayMode especifica el tipo de vista (página) correspondiente a la URL. Este elemento es relevante solo cuando la URL sea de una vista
     * (página) de la aplicación generada (si el elemento urlType es INTERNAL). Su valor es uno de los elementos de la enumeración DisplayMode.
     * Seleccione READING o WRITING para una vista de solo consulta o una vista (página) de registro, respectivamente. Seleccione PROCESSING para
     * consolas de procesamiento. Alternativamente, omita el elemento o seleccione UNSPECIFIED cuando la URL pueda ser de varios tipos de página.
     *
     * @return displayMode
     */
    DisplayMode urlDisplayMode() default DisplayMode.UNSPECIFIED;

    /**
     * urlDisplayType indica el tipo de componente que se utiliza para mostrar el valor de la propiedad en las vistas (páginas) de consulta y
     * registro. Su valor es uno de los elementos de la enumeración UrlDisplayType. Seleccione TEXT para utilizar un cuadro de texto. Seleccione
     * HYPERLINK para utilizar un hipervínculo. Seleccione BUTTON para utilizar botón de acción. Alternativamente, omita el elemento o seleccione
     * UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es HYPERLINK.
     *
     * @return urlDisplayType
     */
    UrlDisplayType urlDisplayType() default UrlDisplayType.UNSPECIFIED;

    /**
     * sourceURLs especifica una o más URL de sitios válidos para definir el valor de la propiedad. Este elemento es relevante solo si el valor
     * especificado, o determinado, para el elemento urlType es EXTERNAL. Para cada URL debe especificar al menos el protocolo y el servidor o host;
     * opcionalmente puede especificar el puerto de red y la ruta. Si este elemento no se especifica, entonces la propiedad podrá contener una URL de
     * cualquiera de los sitios en la lista general definida para la aplicación. El valor de este elemento también se puede especificar mediante el
     * método setSourceURLs.
     *
     * @return sourceURLs
     */
    String[] sourceURLs() default "";

    /**
     * searchURL especifica la URL de un sitio Web donde buscar el valor de la propiedad. Este elemento es relevante solo si el valor especificado, o
     * determinado, para el elemento urlType es EXTERNAL. El valor predeterminado es https://www.google.com. El valor de este elemento también se
     * puede especificar mediante el método setSearchURL.
     *
     * @return searchURL
     */
    String searchURL() default TrustedSites.GOOGLE;

    /**
     * encoding indica si se debe codificar, o no, el valor de la propiedad para almacenarlo en la base de datos. Su valor es uno de los elementos de
     * la enumeración Kleenean. Seleccione TRUE para codificar el valor; en caso contrario, seleccione FALSE. Alternativamente, omita el elemento o
     * seleccione UNSPECIFIED para utilizar el valor predeterminado del atributo. El valor predeterminado del atributo es FALSE.
     * <p>
     * <b>Advertencias</b>
     * <ul>
     * <li>La codificación limita significativamente el uso de la propiedad en consultas e informes.</li>
     * <li>El algoritmo de codificación utilizado es Base64, el cual no es un algoritmo de cifrado, se decodifica fácilmente y, por lo tanto, no debe
     * utilizarse como un método de cifrado seguro.</li>
     * <li>La longitud del valor codificado es un tercio mayor que la longitud del valor original; si, por ejemplo, la longitud máxima de la propiedad
     * se establece en 2000, entonces la propiedad solo puede contener valores de hasta 1500 caracteres.</li>
     * </ul><p>
     *
     * @return encoding
     * @see <a href="https://docs.oracle.com/javase/8/docs/api/java/util/Base64.html">class Base64</a>
     */
    Kleenean encoding() default Kleenean.UNSPECIFIED;

}
